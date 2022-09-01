package ru.asteises.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.asteises.tacocloud.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
