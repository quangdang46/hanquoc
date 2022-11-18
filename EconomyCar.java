public class EconomyCar extends Car {

  public EconomyCar() {
    super();
  }

  public EconomyCar(String id, String name, int capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 0, 1000)) {
      throw new IllegalArgumentException("Capacity must be between 0 and 1000");
    }
  }

  public int getPrice(int days) {
    return super.solvePrice(days, 30000, 20000);
  }

  public String toString() {
    return super.toString() + "_" + "Economy Car";
  }

}
