package com.example.fyp_app;

public class FoodModal {
    // variables for our first name,
    // last name, email and avatar
    private String title;
    private String image;
    private Integer amount;
    //private String avatar;
/*
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
*/

    public Integer getAmount() {
        return amount;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FoodModal(String title, String image, Integer amount) {
        this.title = title;
        this.image = image;
        this.amount = amount;
    }
}
