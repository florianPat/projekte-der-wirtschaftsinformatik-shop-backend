package fhdw.pdw.model.dto;

public class OrderItemDto {
  protected int quantity;
  protected int productVariantId;

  public OrderItemDto() {}

  public OrderItemDto(int quantity, int productVariantId) {
    this.quantity = quantity;
    this.productVariantId = productVariantId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getProductVariantId() {
    return productVariantId;
  }

  public void setProductVariantId(int productVariantId) {
    this.productVariantId = productVariantId;
  }
}
