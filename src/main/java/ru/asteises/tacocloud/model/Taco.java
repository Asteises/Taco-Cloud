package ru.asteises.tacocloud.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity // Объявляем класс сущностью JPA
//@Table
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min=5, message = "Name must be at least 5 character long")
    private String name;

     /* Объект Taco может включать в список несколько объектов Ingredient,
        а один объект Ingredient может быть частью списков в нескольких объектах Taco;
      */
    @ManyToMany
    @Size(min=1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    public void addIngredients(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

}
