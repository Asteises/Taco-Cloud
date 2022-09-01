package ru.asteises.tacocloud.controller;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.asteises.tacocloud.model.Ingredient;
import ru.asteises.tacocloud.repository.IngredientRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer { // Определяет несколько методов настройки SpringMVC

    //TODO Для чего нужны файлы конфигурации?

    /**
     * ViewControllerRegistry - помогает зарегистрировать контроллеры представлений;
     * Определяем путь ("/") в GET запросах и указываем имя представления ("home") которому должны передаваться запросы;
     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("home");
//    }

    //TODO Стоит ли вот так писать простые контроллеры?
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
    }

    //TODO Не понятно для чего это нужно... Заполняем предварительно БД?
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo) {
        return args -> {
            repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
        };
    }

//    @Bean
//    public ApplicationRunner dataLoader(IngredientRepository repo) {
//        return args -> {
//            repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
//            repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
//            repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
//            repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
//            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
//            repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
//            repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
//            repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
//            repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
//            repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
//        };
//    }

}
