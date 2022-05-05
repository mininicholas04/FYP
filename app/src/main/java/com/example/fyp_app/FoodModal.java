package com.example.fyp_app;

public class FoodModal {
    private String title;
    private String image;
    private Integer Calories;
    private Integer Protein;
    private Integer Fat;
    private Integer Carbohydrates;

    public Integer getCalories() {
        return Calories;
    }

    public Integer getProtein() {
        return Protein;
    }

    public Integer getCarbohydrates() {
        return Carbohydrates;
    }

    public Integer getFat() {
        return Fat;
    }

    public void setCalories(Integer calories) {
        Calories = calories;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        Carbohydrates = carbohydrates;
    }

    public void setFat(Integer fat) {
        Fat = fat;
    }

    public void setProtein(Integer protein) {
        Protein = protein;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FoodModal(String title, String image, Integer Calories,Integer Protein,Integer Fat,Integer Carbohydrates) {
        this.title = title;
        this.image = image;
        this.Calories = Calories;
        this.Protein = Protein;
        this.Fat = Fat;
        this.Carbohydrates = Carbohydrates;
    }
}
