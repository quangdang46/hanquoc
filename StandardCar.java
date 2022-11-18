public class StandardCar extends Car {

  public StandardCar() {
    super();
  }

  public StandardCar(String id, String name, int capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 1000, 2000)) {
      throw new IllegalArgumentException("Capacity must be between 1000 and 2000");
    }
  }

  public int getPrice(int days) {
    return super.solvePrice(days, 40000, 30000);

  }

  public String toString() {
    return super.toString() + "_" + "Standard Car";
  }

}
