package ru.geekbrains.persist;


import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Validated
public class Product {
    private Long id;
    @NotBlank
    private String title;
    @DecimalMin("0")
    @DecimalMax("100000")
    private float cost;
    public Product (String title, float cost) {
        this.title = title;
        this.cost = cost;
    }
    public Product() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
}
