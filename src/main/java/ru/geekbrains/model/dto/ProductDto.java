package ru.geekbrains.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "can not be empty!!!")
    private String title;

    @NotNull
    private float cost;

    public ProductDto (String title, float cost){
        this.title=title;
        this.cost=cost;
    }

    public ProductDto (Long id, String title, float cost){
        this.id=id;
        this.title=title;
        this.cost=cost;
    }
}
