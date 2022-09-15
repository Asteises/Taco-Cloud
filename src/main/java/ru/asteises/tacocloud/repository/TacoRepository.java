package ru.asteises.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.asteises.tacocloud.model.Taco;

import java.util.List;
import java.util.Optional;

@Repository
public interface TacoRepository extends CrudRepository<Taco, Long> {

    List<Taco> findAll();

    Optional<Taco> findById(Long id);

}
