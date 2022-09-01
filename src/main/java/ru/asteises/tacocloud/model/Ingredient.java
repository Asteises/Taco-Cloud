package ru.asteises.tacocloud.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity // Объявляем класс сущностью JPA
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
//@Table // При использовании JPA не актуально, так как в качестве имени таблицы используется имя класса;
public class Ingredient {

    @Id
    private final String id;
    private final String name;
    private final Type type;
    public enum Type {
        WRAP,
        PROTEIN,
        VEGGIES,
        CHEESE,
        SAUCE
    }

}
