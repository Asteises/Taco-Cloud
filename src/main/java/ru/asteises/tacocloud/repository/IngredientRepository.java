package ru.asteises.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.asteises.tacocloud.model.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

}
