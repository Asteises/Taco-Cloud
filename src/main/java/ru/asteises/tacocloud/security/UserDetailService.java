package ru.asteises.tacocloud.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailService {

    //TODO Не пойму где это используется
    /**
     * Функциональный интерфейс, который можно использовать как лямбду;
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;



}
