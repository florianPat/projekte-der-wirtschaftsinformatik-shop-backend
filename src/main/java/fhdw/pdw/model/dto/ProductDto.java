package fhdw.pdw.model.dto;

public class ProductDto {
  protected String name;
  protected String category;
  protected String producer;
  protected float amount;
  protected int numberOfContainer;
  protected String unitTitle;
  protected float price;
  protected String allergens;
  protected String cover;

  public ProductDto(
      String name,
      String category,
      String producer,
      float amount,
      int numberOfContainer,
      String unitTitle,
      float price,
      String allergens,
      String cover) {
    this.name = name;
    this.category = category;
    this.producer = producer;
    this.amount = amount;
    this.numberOfContainer = numberOfContainer;
    this.unitTitle = unitTitle;
    this.price = price;
    this.allergens = allergens;
    this.cover = cover;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public int getNumberOfContainer() {
    return numberOfContainer;
  }

  public void setNumberOfContainer(int numberOfContainer) {
    this.numberOfContainer = numberOfContainer;
  }

  public String getUnitTitle() {
    return unitTitle;
  }

  public void setUnitTitle(String unitTitle) {
    this.unitTitle = unitTitle;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getAllergens() {
    return allergens;
  }

  public void setAllergens(String allergens) {
    this.allergens = allergens;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }
}
