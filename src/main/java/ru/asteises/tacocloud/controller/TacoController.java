package ru.asteises.tacocloud.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.asteises.tacocloud.model.Ingredient;
import ru.asteises.tacocloud.model.Taco;
import ru.asteises.tacocloud.model.TacoOrder;
import ru.asteises.tacocloud.repository.OrderRepository;
import ru.asteises.tacocloud.repository.TacoRepository;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/tacos",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {
    private final TacoRepository tacoRepository;
    private final OrderRepository orderRepository;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepository.findById(id);
        return optTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId,
                                @RequestBody TacoOrder patch) {
        TacoOrder order = orderRepository.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return orderRepository.save(order);
    }

    //TODO REST запросы - не пойму, это запросы на стороне клиентского приложения? Которые вроде как требуют что-то сделать...
    public Ingredient getIngredientById(String ingredientId) {
        return restTemplate().getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

    public Ingredient getIngredientByIdResponseEntity(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate().getForEntity("http://localhost:8080/ingredients/{id}",
                        Ingredient.class, ingredientId);
        //Тут мы еще получаем время запроса
        log.info("Fetched time: {}", responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        restTemplate().put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        restTemplate().delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate().postForObject("http://localhost:8080/ingredients/{id}", ingredient, Ingredient.class);
    }

    public java.net.URI createIngredientAndShoeLocation(Ingredient ingredient) {
        return restTemplate().postForLocation("http://localhost:8080/ingredients/", ingredient);
    }

    public ResponseEntity<Ingredient> createIngredientResponseEntity(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate().postForEntity("http://localhost:8080/ingredients/{id}", ingredient, Ingredient.class);
        log.info("New resource created at {}", responseEntity.getHeaders().getLocation());
        return responseEntity;
    }
}
