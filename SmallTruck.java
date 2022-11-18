public class SmallTruck extends Car {

  public SmallTruck() {
    super();
  }

  public SmallTruck(String id, String name, double capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 0, 1)) {
      throw new IllegalArgumentException("Capacity must be less than 1");
    }
  }

  public int getPrice(int days) {
    return super.getPriceByTruck(days,20000);
  }

  public String toString() {
    return super.toString() + "_Small Truck";
  }
}
