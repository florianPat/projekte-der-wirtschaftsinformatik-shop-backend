package fhdw.pdw.controller;

import fhdw.pdw.email.EmailService;
import fhdw.pdw.model.Order;
import fhdw.pdw.model.OrderItem;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.repository.OrderRepository;
import fhdw.pdw.security.UserDetail;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class OrderController {
  protected OrderRepository orderRepository;
  protected EmailService emailService;

  public OrderController(OrderRepository orderRepository, EmailService emailService) {
    this.orderRepository = orderRepository;
    this.emailService = emailService;
  }

  @PostMapping("/order")
  @Secured("ROLE_USER")
  public ResponseEntity<?> createOrder(@Valid @RequestBody Order order) {
    Object userPrinciple = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(userPrinciple instanceof UserDetail)) {
      throw new RuntimeException("The logged in user needs to be of type UserDetail!");
    }
    UserDetail userDetail = (UserDetail) userPrinciple;

    String orderEmailDetails = "";
    float price = 0.0f;
    for (OrderItem orderItem : order.getOrderItemList()) {
      orderEmailDetails +=
          "- "
              + orderItem.getProductVariant().getProduct().getName()
              + " ("
              + orderItem.getQuantity()
              + "x): "
              + orderItem.getProductVariant().getPrice()
              + "€\n";
      price += orderItem.getQuantity() * orderItem.getProductVariant().getPrice();
    }
    orderEmailDetails += "\nGesamtpreis: " + price;

    emailService.sendSimpleMessage(
        userDetail.getEmail(),
        "Bestellbestätigung sip.shop",
        "Sehr geehrte Kundein,\nSehr geehrter Kunde,\n\n"
            + "vielen Dank für Ihre Bestellung! Die Ware wird in spätestens 24 Stunden bei Ihnen eintreffen.\n"
            + "Eine kurze Übersicht über die von Ihnen bestellten Produkte:\n\n"
            + orderEmailDetails
            + "\n\nIhr sip.shop!");

    orderRepository.save(order);

    return new ResponseEntity<>(
        new ApiResponse(true, "Order saved successfully"), HttpStatus.CREATED);
  }

  @GetMapping("/orders")
  @Secured("ROLE_ADMIN")
  public List<Order> getOrders() {
    return orderRepository.findAll();
  }

  @GetMapping("/orders/{id}")
  @Secured("ROLE_ADMIN")
  public Order getOrder(@PathVariable int id) {
    return orderRepository.findById(id).get();
  }
}
