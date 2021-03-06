package ru.tinkoff.acquiring.sdk;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Vitaliy Markus
 *         ���������� � ������.
 */
public class Item implements Serializable {

    private static final int QUANTITY_SCALE_FACTOR = 3;

    @SerializedName("Name")
    private String name;

    @SerializedName("Price")
    private Long price;

    @SerializedName("Quantity")
    private double quantity;

    @SerializedName("Amount")
    private Long amount;

    @SerializedName("Tax")
    private Tax tax;

    @SerializedName("Ean13")
    private String ean13;

    @SerializedName("ShopCode")
    private String shopCode;

    /**
     * @param name     ������������ ������. ������������ ����� ������ � 128 ��������.
     * @param price    ����� � ��������. ������������� �������� �� ����� 10 ������.
     * @param quantity ����������/���. ����� ����� �� ����� 8 ������.
     * @param amount   ����� � ��������. ������������� �������� �� ����� 10 ������.
     * @param tax      ������ ������.
     */
    public Item(String name, Long price, double quantity, Long amount, Tax tax) {
        this.name = name;
        this.price = price;
        this.quantity = round(quantity, QUANTITY_SCALE_FACTOR);
        this.amount = amount;
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public Long getAmount() {
        return amount;
    }

    public Tax getTax() {
        return tax;
    }

    public String getEan13() {
        return ean13;
    }

    public String getShopCode() {
        return shopCode;
    }

    /**
     * @param ean13 �����-���.
     */
    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    /**
     * @param shopCode ��� ��������
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    private static double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }
}
