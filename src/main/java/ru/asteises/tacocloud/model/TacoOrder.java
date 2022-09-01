package ru.asteises.tacocloud.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity // Объявляем класс сущностью JPA
//@Table // Аннотация для класса, указывающая название таблицы. По умолчанию будет Taco_Order;
public class TacoOrder {

    private static final long serialVersionUID = 1L;
    @Id // Чтобы Spring понял какое поле уникально идентифицирует объект;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date placedAt;
    @NotBlank
    private String deliveryName;
    @NotBlank
    private String deliveryStreet;
    @NotBlank
    private String deliveryCity;
    @NotBlank
    private String deliveryState;
    @NotBlank
    private String deliveryZip;
    @CreditCardNumber
    private String ccNumber;
    @Future
    @Pattern(regexp = "^(?:0[1-9]|1[0-2])/[0-9]{2}")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0)
    private String ccCVV;

    /*
        Все Taco в этом списке относятся к одному TacoOrder,
        cascade = CascadeType.ALL - означает, что при удалении объекта TacoOrder,
        все связанные с ним Taco тоже будут удалены;
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

}
