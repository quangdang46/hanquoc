public class StandardSuv extends Car {

  public StandardSuv() {
    super();
  }

  public StandardSuv(String id, String name, int capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 150, 250)) {
      throw new IllegalArgumentException("Capacity must be between 150 and 250");
    }
  }


  public int getPrice(int days) {
    return super.getPriceBySuv(days,40000, 40000, 30000);
  }

  public String toString() {
    return super.toString() + "_" + "Standard Suv";
  }

}
