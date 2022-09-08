package ru.geekbrains.persist;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
@Entity
@Table(name = "product")
@Getter
@Setter
@Validated
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotBlank
    @Column (name="title", nullable = false, unique = true)
    private String title;
//    @DecimalMin("0")
//    @DecimalMax("100000")
    @Column(name = "cost", nullable = false)
    private float cost;
    public Product (String title, float cost) {
        this.title = title;
        this.cost = cost;
    }

}
