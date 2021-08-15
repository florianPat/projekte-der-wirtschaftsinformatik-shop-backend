package fhdw.pdw.model.dto;

public class ProductDto {
  protected String name;
  protected String category;
  protected String producer;
  protected String amount; // "6 x 0.5 L"
  protected float price;
  protected String allergens;
  protected String cover;
  protected int minAge;

  public ProductDto() {}

  public ProductDto(
      String name,
      String category,
      String producer,
      String amount,
      float price,
      String allergens,
      String cover,
      int minAge) {
    this.name = name;
    this.category = category;
    this.producer = producer;
    this.amount = amount;
    this.price = price;
    this.allergens = allergens;
    this.cover = cover;
    this.minAge = minAge;
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
    return Float.parseFloat(amount.split(" ")[2]);
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public int getNumberOfContainer() {
    return Integer.parseInt(amount.split(" ")[0]);
  }

  public String getUnitTitle() {
    return amount.split(" ")[3];
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

  public int getMinAge() {
    return minAge;
  }

  public void setMinAge(int minAge) {
    this.minAge = minAge;
  }
}
