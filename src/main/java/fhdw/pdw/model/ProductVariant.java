package fhdw.pdw.model;

import javax.persistence.*;

@Entity
public class ProductVariant extends AbstractEntity {
  protected int numberOfBottles;
  protected float quantity;
  protected float price;

  @ManyToOne
  @JoinColumn(name = "product_id")
  protected Product product;

  public ProductVariant() {}

  public ProductVariant(int numberOfBottles, float quantity, float price, Product product) {
    this.numberOfBottles = numberOfBottles;
    this.quantity = quantity;
    this.price = price;
    this.product = product;
  }

  public int getNumberOfBottles() {
    return numberOfBottles;
  }

  public void setNumberOfBottles(int numberOfBottles) {
    this.numberOfBottles = numberOfBottles;
  }

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
