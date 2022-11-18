public class LuxuryCar extends Car {
  public LuxuryCar() {
    super();
  }

  public LuxuryCar(String id, String name, int capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 3000, 3500)) {
      throw new IllegalArgumentException("Capacity must be between 3000 and 3500");
    }
  }



  public int getPrice(int days) {
    return super.solvePrice(days,60000, 50000);
  }

  public String toString() {
    return super.toString() + "_" + "Luxury Car";
  }
}
