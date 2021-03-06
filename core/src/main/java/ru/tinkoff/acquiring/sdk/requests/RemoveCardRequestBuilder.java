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

/**
 * @author Mikhail Artemyev
 */
final public class RemoveCardRequestBuilder extends AcquiringRequestBuilder<RemoveCardRequest> {

    private RemoveCardRequest request = new RemoveCardRequest();

    /**
     * ������ ��� ������� RemoveCard
     *
     * @param password    ������. �������� ������ �� ������ �������.
     * @param terminalKey ���������� ������������� ���������. �������� ������ �� ������ �������.
     */
    public RemoveCardRequestBuilder(final String password, final String terminalKey) {
        super(password, terminalKey);
    }

    /**
     * @param value ������������� �����
     */
    public RemoveCardRequestBuilder setCardId(final String value) {
        request.setCardId(value);
        return this;
    }

    /**
     * @param value ������������� ���������� � ������� ��������, � �������� ��������� �����.
     */
    public RemoveCardRequestBuilder setCustomerKey(final String value) {
        request.setCustomerKey(value);
        return this;
    }

    @Override
    protected RemoveCardRequest getRequest() {
        return request;
    }

    @Override
    protected void validate() {
        validateNonEmpty(request.getCardId(), "Card id");
        validateNonEmpty(request.getCustomerKey(), "Customer key");
    }
}
