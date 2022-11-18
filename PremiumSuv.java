public class PremiumSuv extends Car {

  public PremiumSuv() {
    super();
  }

  public PremiumSuv(String id, String name, int capacity) {
    super(id, name, capacity);
    if (!checkCapacity(capacity, 250, 350)) {
      throw new IllegalArgumentException("Capacity must be between 250 and 350");
    }
  }

  public int getPrice(int days) {
    return super.getPriceBySuv(days, 60000, 60000, 50000);
  }

  public String toString() {
    return super.toString() + "_" + "Premium Suv";
  }

}
