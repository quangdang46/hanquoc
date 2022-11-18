public class Car {
  protected String id;
  protected String name;
  protected int capacity;
  protected double weight;

  public Car() {

  }

  public Car(String id, String name, int capacity) {
    this.id = id;
    this.name = name;
    this.capacity = capacity;
  }

  public Car(String id, String name, double weight) {
    this.id = id;
    this.name = name;
    this.weight = weight;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int solvePrice(int days, int currentPrice, int nextPrice) {
    if (days <= 5) {
      return days * currentPrice;
    }
    return currentPrice * 5 + (days - 5) * nextPrice;

  }

  public boolean checkCapacity(int range, int minCap, int maxCap) {
    if (range > minCap && range <= maxCap) {
      return true;
    }
    return false;
  }

  public boolean checkCapacity(double range, double minCap, double maxCap) {
    if (range > minCap && range <= maxCap) {
      return true;
    }
    return false;
  }

  public int getPriceBySuv(int days, int currentPrice, int nextPrice, int endPrice) {
    if (days <= 5) {
      return days * currentPrice;
    } else if (days <= 10) {
      return currentPrice * 5 + (days - 5) * nextPrice;
    }
    return currentPrice * 5 + 5 * nextPrice + (days - 10) * endPrice;

  }

  public int getPriceByTruck(int days, int currentPrice) {
    return days * currentPrice;
  }

  @Override
  public String toString() {
    return this.id + "_"
        + this.name + "_"
        + this.capacity;
  }
}