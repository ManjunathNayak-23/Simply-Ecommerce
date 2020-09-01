package com.example.grocery.helperclass;

public class ProductModel {
    String image;
    String brandName;
    String modelName;
    String price;
    String description;
    String productColor;
    String productSize;
    String key;
    String category;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ProductModel() {
    }

    public ProductModel(String image, String brandName, String modelName, String price, String description, String productColor, String productSize,String category) {
        this.image = image;
        this.brandName = brandName;
        this.modelName = modelName;
        this.price = price;
        this.description = description;
        this.productColor = productColor;
        this.productSize = productSize;
        this.category=category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }
}
