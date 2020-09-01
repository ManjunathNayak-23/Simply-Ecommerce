package com.example.grocery.helperclass;

public class CartModel {
    String Brand;
    String Color;
    String ImageUrl;
    String Size;
    String Price;
    int Quantity;
String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CartModel() {
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public CartModel(String brand, String color, String imageUrl, String size, String price, int quantity) {
        Brand = brand;
        Color = color;
        ImageUrl = imageUrl;
        Size = size;
        Price = price;
        Quantity = quantity;
    }
}
