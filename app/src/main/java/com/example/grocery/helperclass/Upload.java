package com.example.grocery.helperclass;
public class Upload {
    private String brandName;
    private String modelName;
    private String price;
    private String image;
    private String productColor;
    private String productSize;
    private String category;
    private String description;

    public Upload() {
    }

    public Upload(String brandName, String modelName, String price, String image, String productColor, String productSize, String category, String description) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.price = price;
        this.image = image;
        this.productColor = productColor;
        this.productSize = productSize;
        this.category = category;
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
