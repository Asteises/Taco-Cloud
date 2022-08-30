package ru.asteises.tacocloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.asteises.tacocloud.model.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @GetMapping("/current")
    public String orderFrom() {
        return "orderFrom";
    }

    @PostMapping
    public String processOrder(TacoOrder order, SessionStatus sessionStatus) {
        log.info("Order submitted: {}, order");
        sessionStatus.setComplete();

        return "redirect:/";
    }
}