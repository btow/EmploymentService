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

package ru.tinkoff.acquiring.sdk.requests;

import java.util.Map;

import ru.tinkoff.acquiring.sdk.PayType;
import ru.tinkoff.acquiring.sdk.Receipt;

/**
 * @author Mikhail Artemyev
 */
final public class InitRequestBuilder extends AcquiringRequestBuilder<InitRequest> {

    private InitRequest request = new InitRequest();

    /**
     * ������ ��� ������� Init
     *
     * @param password    ������. �������� ������ �� ������ �������.
     * @param terminalKey ���������� ������������� ���������. �������� ������ �� ������ �������.
     */
    public InitRequestBuilder(final String password, final String terminalKey) {
        super(password, terminalKey);
    }

    /**
     * @param value ����� � ��������
     */
    public InitRequestBuilder setAmount(final long value) {
        request.setAmount(value);
        return this;
    }

    /**
     * @param value ����� ������ � ������� ��������
     */
    public InitRequestBuilder setOrderId(final String value) {
        request.setOrderId(value);
        return this;
    }

    /**
     * @param value ������������� ���������� � ������� ��������.
     *              ���� ����������, �� ��� ������� ���������� ����� ������������ �������� ����� � ������� �������������� ������� CustomerKey.
     *              � ����������� �� AUTHORIZED ����� ������� �������� CardId, ��������� ��. ����� GetGardList {@link GetCardListRequestBuilder}.
     *              �������� ����������, ���� Recurrent = Y
     */
    public InitRequestBuilder setCustomerKey(final String value) {
        request.setCustomerKey(value);
        return this;
    }

    /**
     * @param value ������� ��������
     */
    public InitRequestBuilder setDescription(final String value) {
        request.setDescription(value);
        return this;
    }

    /**
     * @param value ��������� �����, �� ����� 20 ��������
     */
    public InitRequestBuilder setPayForm(final String value) {
        request.setPayForm(value);
        return this;
    }

    /**
     * @param value ���� ���������� � ���������� � Y, �� ������������ ����� ��� ������������. � ���� ������ ����� ������ � ����������� �� AUTHORIZED ����� ������� �������� RebillId ��� ������������� � ������ Charge
     */
    public InitRequestBuilder setRecurrent(final boolean value) {
        request.setRecurrent(value);
        return this;
    }

    /**
     * @param language ���� �������� �����.
     *                 ru - ����� ������ �� ������� �����;
     *                 en - ����� ������ �� ���������� �����.
     *                 �� ��������� (���� �������� �� �������) - ����� ������ �� ������� �����.
     */
    public InitRequestBuilder setLanguage(final String language) {
        request.setLanguage(language);
        return this;
    }

    /**
     * @param payType ��� ������
     */
    public InitRequestBuilder setPayType(PayType payType) {
        request.setPayType(payType.toString());
        return this;
    }

    /**
     * @param receipt ������ � ������� ����
     */
    public InitRequestBuilder setReceipt(Receipt receipt) {
        request.setReceipt(receipt);
        return this;
    }

    /**
     * @param data ������ ���������� �������������� ��������� � ���� ������:���������. ������ ��������� ����� �������� �� �������� ������ (� ������ �� ������������). ������������ ����� ��� ������� ������������� ���������:
     *             ���� � 20 ������,
     *             �������� � 100 ������.
     *             ������������ ���������� ��� �����-�������� �� ����� ��������� 20.
     */
    public InitRequestBuilder setData(Map<String, String> data) {
        request.setData(data);
        return this;
    }

    /**
     * @param chargeFlag ����, � ���, ��� ���������� ������ � ���������� ������, � ������ ������ FinishAuthorize ���������� ������� Charge
     */
    public InitRequestBuilder setChargeFlag(boolean chargeFlag) {
        request.setChargeFlag(chargeFlag);
        return this;
    }

    @Override
    protected void validate() {
        validateNonEmpty(request.getOrderId(), "Order ID");
        validateZeroOrPositive(request.getAmount(), "Amount");
    }

    @Override
    protected InitRequest getRequest() {
        return request;
    }
}
