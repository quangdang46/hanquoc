public class EconomySuv extends Car {

  public EconomySuv() {
    super();
  }
  
  public EconomySuv(String id, String name, int capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 0, 150)) {
      throw new IllegalArgumentException("Capacity must be between 0 and 150");
    }
  }

  public int getPrice(int days) {
    return super.getPriceBySuv(days,20000, 20000, 10000);
  }

  public String toString() {
    return super.toString() + "_" + "Economy Suv";
  }
}