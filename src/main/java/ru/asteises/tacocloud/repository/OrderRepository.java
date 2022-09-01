package ru.asteises.tacocloud.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.asteises.tacocloud.model.TacoOrder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findAll();

    Optional<TacoOrder> findById(Long id);

    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    /*
        Методы могут быть весьма сложными;
     */
    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

    /*
        Методы могут быть весьма сложными;
    */
    // НЕ РАБОТАЕТ
//    List<TacoOrder> findByDeliveryStreetAndDeliveryCityAllIgnoresCase(String deliveryStreet, String deliveryCity);

    /*
        Методы могут быть весьма сложными;
    */
    List<TacoOrder> findByDeliveryCityOrderByDeliveryStreet(String city);

    /*
        Так можно писать собственные методы для получения данных из БД;
     */
    @Query("select '*' from TacoOrder where deliveryCity='Seattle'")
    List<TacoOrder> readOrdersDeliveredInSeattle();

    //TODO Как будет выглядеть метод, если нужно сделать выборку по полю сущности,
    // которая сама находится в поле основной сущности?
    // К примеру: найти район с наибольшим потреблением Taco с соусом Salsa?
}
