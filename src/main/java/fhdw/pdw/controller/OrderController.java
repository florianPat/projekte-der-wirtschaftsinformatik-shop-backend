package fhdw.pdw.controller;

import fhdw.pdw.email.EmailService;
import fhdw.pdw.mapper.OrderMapper;
import fhdw.pdw.model.Order;
import fhdw.pdw.model.OrderItem;
import fhdw.pdw.model.User;
import fhdw.pdw.model.dto.ApiResponse;
import fhdw.pdw.model.dto.OrderItemDto;
import fhdw.pdw.repository.OrderRepository;
import fhdw.pdw.repository.UserRepository;
import fhdw.pdw.security.UserDetail;
import java.util.Arrays;
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
  protected OrderMapper orderMapper;
  protected UserRepository userRepository;

  public OrderController(
      OrderRepository orderRepository,
      EmailService emailService,
      OrderMapper orderMapper,
      UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.emailService = emailService;
    this.orderMapper = orderMapper;
    this.userRepository = userRepository;
  }

  @PostMapping("/order")
  @Secured("ROLE_USER")
  public ResponseEntity<?> createOrder(@Valid @RequestBody OrderItemDto[] orderItemDtoList) {
    Object userPrinciple = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(userPrinciple instanceof UserDetail)) {
      throw new RuntimeException("The logged in user needs to be of type UserDetail!");
    }
    UserDetail userDetail = (UserDetail) userPrinciple;

    Order order = orderMapper.mapFrom(Arrays.asList(orderItemDtoList));
    User user = userRepository.findByEmail(userDetail.getEmail()).orElseThrow();
    order.setUser(user);

    String orderEmailDetails = "";
    float price = 0.0f;
    for (OrderItem orderItem : order.getOrderItemList()) {
      orderEmailDetails +=
          "- "
              + orderItem.getProductVariant().getProduct().getName()
              + " - "
              + orderItem.getProductVariant().getUnit().getNumberOfContainer()
              + " x "
              + orderItem.getProductVariant().getUnit().getAmount()
              + " "
              + orderItem.getProductVariant().getUnit().getTitle()
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

  @GetMapping("/orders/me")
  @Secured("ROLE_USER")
  public List<Order> getOrderByLoggedInUser() {
    Object userPrinciple = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(userPrinciple instanceof UserDetail)) {
      throw new RuntimeException("The logged in user needs to be of type UserDetail!");
    }
    UserDetail userDetail = (UserDetail) userPrinciple;

    User user = userRepository.findByEmail(userDetail.getEmail()).orElseThrow();

    return orderRepository.findByUserId(user.getId());
  }
}
