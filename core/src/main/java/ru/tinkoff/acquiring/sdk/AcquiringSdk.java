/*
 * Copyright � 2016 Tinkoff Bank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.tinkoff.acquiring.sdk;

import java.security.PublicKey;

import ru.tinkoff.acquiring.sdk.requests.ChargeRequest;
import ru.tinkoff.acquiring.sdk.requests.ChargeRequestBuilder;
import ru.tinkoff.acquiring.sdk.requests.FinishAuthorizeRequest;
import ru.tinkoff.acquiring.sdk.requests.FinishAuthorizeRequestBuilder;
import ru.tinkoff.acquiring.sdk.requests.GetCardListRequest;
import ru.tinkoff.acquiring.sdk.requests.GetCardListRequestBuilder;
import ru.tinkoff.acquiring.sdk.requests.GetStateRequest;
import ru.tinkoff.acquiring.sdk.requests.GetStateRequestBuilder;
import ru.tinkoff.acquiring.sdk.requests.InitRequest;
import ru.tinkoff.acquiring.sdk.requests.InitRequestBuilder;
import ru.tinkoff.acquiring.sdk.requests.RemoveCardRequest;
import ru.tinkoff.acquiring.sdk.requests.RemoveCardRequestBuilder;
import ru.tinkoff.acquiring.sdk.responses.GetCardListResponse;

/**
 * <p>
 * ����� ��������� ��������������� SDK � ������������ �������������� � �������� ��������� API.
 * ������, �������������� ��������� � API, ���������� ��������� � ������ ��������� ����������
 * ������� ��� ������� ���������� (������ ��� iOS) AcquringSdkException (AcquiringSdkError).
 *
 * @author Mikhail Artemyev
 */
public class AcquiringSdk extends Journal {

    private static final int PAY_FORM_MAX_LENGTH = 20;

    private final AcquiringApi api;
    private final String terminalKey;
    private final String password;
    private final PublicKey publicKey;

    /**
     * ������� ����� ��������� SDK
     *
     * @param terminalKey ���� ���������. �������� ����� ����������� � �������� ���������
     * @param password    ������ �� ���������. �������� ������ � terminalKey
     * @param publicKey   ��������� PublicKey ��������� �� ���������� �����, ����������� ������ �
     *                    terminalKey
     */
    public AcquiringSdk(final String terminalKey,
                        final String password,
                        final PublicKey publicKey) {

        this.terminalKey = terminalKey;
        this.password = password;
        this.publicKey = publicKey;

        this.api = new AcquiringApi();
    }

    /**
     * ������� ����� ��������� SDK
     *
     * @param terminalKey ���� ���������. �������� ����� ����������� � �������� ���������
     * @param password    ������ �� ���������. �������� ������ � terminalKey
     * @param publicKey   ��������� ����. �������� ������ � terminalKey
     */
    public AcquiringSdk(final String terminalKey,
                        final String password,
                        final String publicKey) {
        this(terminalKey, password, new StringKeyCreator(publicKey));
    }


    public AcquiringSdk(final String terminalKey,
                        final String password,
                        final KeyCreator keyCreator) {
        this(terminalKey, password, keyCreator.create());
    }

    public String getTerminalKey() {
        return terminalKey;
    }

    public String getPassword() {
        return password;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * ���������� �������� ������
     *
     * @param builder       ������ ��������� � �������� Init
     * @return ���������� ������������� ���������� � ������� �����
     */
    public Long init(InitRequestBuilder builder) {
        InitRequest request = builder.build();
        return executeInitRequest(request);
    }

    /**
     * ������������ �������������� ������ ��������� ��������� ������
     *
     * @param paymentId ���������� ������������� ���������� � ������� �����
     * @param cardData  ������ �����
     * @param infoEmail email, �� ������� ����� ���������� ��������� �� ������
     * @return ������ ThreeDsData ���� �������� ������� ����������� 3DS, ����� null
     */
    public ThreeDsData finishAuthorize(final long paymentId,
                                       final CardData cardData,
                                       final String infoEmail) {
        final FinishAuthorizeRequest request = new FinishAuthorizeRequestBuilder(password, terminalKey)
                .setPaymentId(paymentId)
                .setSendEmail(infoEmail != null)
                .setCardData(cardData.encode(publicKey))
                .setEmail(infoEmail)
                .build();

        try {
            return api.finishAuthorize(request).getThreeDsData();
        } catch (AcquiringApiException | NetworkException e) {
            throw new AcquiringSdkException(e);
        }
    }

    /**
     * ���������� ������ ����������� ����
     *
     * @param customerKey ������������� ���������� � ������� ��������
     * @return ������ ����������� ����
     */
    public Card[] getCardList(final String customerKey) {
        final GetCardListRequest request = new GetCardListRequestBuilder(password, terminalKey)
                .setCustomerKey(customerKey)
                .build();

        try {
            GetCardListResponse response = api.getCardList(request);
            Journal.log("GetCardListResponse " + response);
            return response.getCard();
        } catch (AcquiringApiException | NetworkException e) {
            throw new AcquiringSdkException(e);
        }
    }

    /**
     * ������������ ������������ (���������) ������ � ������������ �������� �������� ������� ��
     * ����� ���������� ����� ����������. ��� ����������� ��� ������������� ���������� ������
     * ��������� ���� �� ���� ������ � ������ ��������, ������� ������ ���� ������ ��� ������������
     * (��. �������� recurrent � ������ Init), ���������� ���������� ���������.
     * <p>
     * ������� �������, ��� ������������� ������������ �������� ���������� ���������
     * ������������������ ��������: <ol> <li>��������� ������������ ������ ����� ������ Init �
     * ��������� ��������������� ��������� Recurrent=Y.</li> <li>�������� RebillId, ��������������
     * ������ ����� GetCardList</li> <li>������ ��������� ����� ��� ���������� ������������� �������
     * ���������� ������� ����� Init �� ����������� ������� ���������� (�������� Recurrent ����� ��
     * �����).</li> <li>�������� � ����� �� Init �������� PaymentId.</li> <li>������� ����� Charge �
     * ���������� RebillId ���������� � �.2 � ���������� PaymentId ���������� � �.4.</li> </ol>
     *
     * @param paymentId
     * @param rebillId
     * @return
     */
    public PaymentInfo charge(final long paymentId, final String rebillId) {
        final ChargeRequest request = new ChargeRequestBuilder(password, terminalKey)
                .setPaymentId(paymentId)
                .setRebillId(rebillId)
                .build();

        try {
            return api.charge(request).getPaymentInfo();
        } catch (AcquiringApiException | NetworkException e) {
            throw new AcquiringSdkException(e);
        }
    }

    /**
     * ���������� ������ �������
     *
     * @param paymentId ���������� ������������� ���������� � ������� �����
     * @return ������ �������
     */
    public PaymentStatus getState(final long paymentId) {
        final GetStateRequest request = new GetStateRequestBuilder(password, terminalKey)
                .setPaymentId(paymentId)
                .build();

        try {
            return api.getState(request).getStatus();
        } catch (AcquiringApiException | NetworkException e) {
            throw new AcquiringSdkException(e);
        }
    }

    /**
     * ������� ����������� �����
     *
     * @param customerKey ������������� ���������� � ������� ��������
     * @param cardId      ������������� ����� � ������� �����
     * @return �������, ������� �� �����
     */
    public boolean removeCard(final String customerKey, final String cardId) {
        final RemoveCardRequest request = new RemoveCardRequestBuilder(password, terminalKey)
                .setCustomerKey(customerKey)
                .setCardId(cardId)
                .build();

        try {
            return api.removeCard(request).isSuccess();
        } catch (AcquiringApiException | NetworkException e) {
            throw new AcquiringSdkException(e);
        }
    }

    /**
     * @return ���������� URL ����� API, � ����������� �� �������� ������
     */
    public String getUrl(String apiMethod) {
        return AcquiringApi.getUrl(apiMethod);
    }

    private Long executeInitRequest(InitRequest request) {
        try {
            return api.init(request).getPaymentId();
        } catch (AcquiringApiException | NetworkException e) {
            throw new AcquiringSdkException(e);
        }
    }

}
