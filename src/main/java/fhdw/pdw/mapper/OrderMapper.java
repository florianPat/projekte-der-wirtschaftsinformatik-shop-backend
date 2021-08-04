package fhdw.pdw.mapper;

import fhdw.pdw.model.Order;
import fhdw.pdw.model.OrderItem;
import fhdw.pdw.model.dto.OrderItemDto;
import fhdw.pdw.repository.ProductVariantRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
  protected ProductVariantRepository productVariantRepository;

  public OrderMapper(ProductVariantRepository productVariantRepository) {
    this.productVariantRepository = productVariantRepository;
  }

  public Order mapFrom(List<OrderItemDto> orderItemDtoList) {
    Order result = new Order();
    List<OrderItem> orderItemList = new ArrayList<>();
    for (OrderItemDto itemDto : orderItemDtoList) {
      OrderItem item = new OrderItem(itemDto.getQuantity());
      item.setProductVariant(
          productVariantRepository.findById(itemDto.getProductVariantId()).orElseThrow());
      item.setOrder(result);
      orderItemList.add(item);
    }
    result.setOrderItemList(orderItemList);
    return result;
  }
}
