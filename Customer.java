public class Customer {
  private String phone;
  private String name;

  public Customer(String name, String phone) {
    this.phone = phone;
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public String getName() {
    return name;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name + "_" + this.phone;
  }
}
