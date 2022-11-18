public class LargeTruck extends Car {

  public LargeTruck() {
    super();
  }

  public LargeTruck(String id, String name, double capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 4, Double.MAX_VALUE)) {
      throw new IllegalArgumentException("Capacity must be greater than 4");
    }
  }

  public int getPrice(int days) {
    return super.getPriceByTruck(days, 60000);
  }

  public String toString() {
    return super.toString() + "_Large Truck";
  }
}
