package ru.geekbrains.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;

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

    @Column (name="title", nullable = false, unique = true)
    private String title;

    @Column(name = "cost", nullable = false)
    private float cost;

    public Product (String title, float cost) {
        this.title = title;
        this.cost = cost;
    }

    public Product(Long id, String title, float cost) {
        this.id=id;
        this.title = title;
        this.cost = cost;
    }
}
