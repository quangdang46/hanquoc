import java.text.*;
import java.util.*;

public class Controller {
  private ArrayList<Object> listCars;
  private Models model;
  private Scanner scanner;
  private Views view;

  public Controller() {
    this.listCars = new ArrayList<Object>();
    this.model = new Models();
    this.scanner = new Scanner(System.in);
    this.view = new Views();
  }

  public void handleReadCars(String path) {
    this.listCars = model.initListCars(this.listCars, path);
  }

  public String[] getListCars() {
    ArrayList<Object> listCars = this.listCars;
    String[] listCarsString = new String[listCars.size()];
    for (int i = 0; i < listCars.size(); i++) {
      listCarsString[i] = listCars.get(i).toString();
    }
    return listCarsString;
  }

  private boolean isContainsRental(String id, String pathRental) {
    ArrayList<String> listIdRental = model.initListIdRental(pathRental);
    if (listIdRental.contains(id)) {
      return true;
    }
    return false;
  }

  private void deleteCarInStore(String id, String path) {
    for (int i = 0; i < listCars.size(); i++) {
      if (listCars.get(i).toString().contains(id)) {
        listCars.remove(i);
      }
    }
    model.deleteCarById(id, path);
  }

  public void reservation(String pathListCars, String pathRental) {
    System.out.println("Enter your name: ");
    String name = scanner.nextLine();
    System.out.println("Enter your phone: ");
    String phone = scanner.nextLine();
    Customer customer = new Customer(name, phone);
    System.out.println("Enter your car id: ");
    String carName = scanner.nextLine();
    // check reserved
    // if (isContainsRental(carName, pathRental)) {
    // System.out.println("This car is reserved");
    // return;
    // }

    // filter car by id
    Object car = null;
    for (int i = 0; i < listCars.size(); i++) {
      if (listCars.get(i).toString().contains(carName)) {
        car = listCars.get(i);
      }
    }
    if (car == null) {
      System.out.println("This car is not exist");
      return;
    }

    Date startDate = new Date();
    System.out.println("Enter your rental time: ");
    int timeRental = scanner.nextInt();
    Rental rental = new Rental(car, customer, startDate, timeRental);
    model.writeRental(rental, pathRental);
    // deleteCarInStore(carName, pathListCars);
  }

  public String getStringInString(String str) {
    return str.replaceAll("\\d", "");
  }

  public void cancellation(String pathRental, String pathListCars, String phone, String timeRental) {
    ArrayList<String> listRentalCancel = model.handleCancelRental(pathRental, phone, timeRental);
    if (listRentalCancel.size() == 0) {
      System.out.println("This rental is not exist");
      return;
    }
    ArrayList<String> rental = new ArrayList<String>();
    for (String line : listRentalCancel) {
      String arr = line.split(",")[0];
      String[] tokens = arr.split("_");
      String type = this.getStringInString(tokens[0]);
      String res = type + "_" + tokens[0] + "_" + tokens[1] + "_" + tokens[2];
      rental.add(res);
    }
    // model.handelWriteFile(rental, pathListCars, true);

  }

  public void solveProblem1(String pathListCars, String pathRental) {
    this.handleReadCars(pathListCars);
    String[] listCars = this.getListCars();
    view.printListCars(listCars);
    this.reservation(pathListCars, pathRental);
  }

  public void solveProblem2(String pathRental, String pathListCars) {
    System.out.println("Enter your phone: ");
    String phone = scanner.nextLine();
    System.out.println("Enter your time rental(MM/dd/yyyy) : ");
    String timeRental = scanner.nextLine();
    this.cancellation(pathRental, pathListCars, phone, timeRental);

  }

  public HashMap<String, String> splitString(String string) {
    HashMap<String, String> map = new HashMap<String, String>();
    String[] tokens = string.split(",");

    String[] car = tokens[0].split("_");
    String type = this.getStringInString(car[0]);

    String id = car[0];
    String[] customer = tokens[1].split("_");
    // name
    String name = customer[0];
    // phone
    String phone = customer[1];
    // start date
    String startDate = tokens[2];
    // time rental
    String timeRental = tokens[3];

    String[] detailCar = tokens[1].split("_");
    String nameCar = detailCar[0];
    String capacity = detailCar[1];

    map.put("id", id);
    map.put("type", type);
    map.put("name", name);
    map.put("phone", phone);
    map.put("startDate", startDate);
    map.put("timeRental", timeRental);
    map.put("nameCar", nameCar);
    map.put("capacity", capacity);
    return map;
  }

  public boolean checkContains(ArrayList<String> arrays, String check) {
    for (String arr : arrays) {
      if (arr.contains(check)) {
        return true;
      }
    }
    return false;
  }

  public boolean equalsDate(Date date1, Date date2) {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);
    boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

    return sameDay;

  }

  public void solveProblem3(String pathRental, String pathReceive) {
    try {

      System.out.println("Enter your phone: ");
      String phone = scanner.nextLine();
      ArrayList<String> listRental = model.handelReadFile(pathRental);
      ArrayList<String> listReceive = model.handelReadFile(pathReceive);
      ArrayList<String> listRentalCancel = new ArrayList<String>(listRental);
      boolean isContainsPhone = false;
      for (String line : listRental) {
        HashMap<String, String> map = this.splitString(line);
        String phoneRental = map.get("phone");
        String id = map.get("id");
        String startDate = map.get("startDate");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        // get current date dd/MM/yyyy
        Date currentDate = new Date();
        // equals day month year
        if (phoneRental.equals(phone) && this.equalsDate(date, currentDate) && !this.checkContains(listReceive, id)) {
          listReceive.add(line);
          isContainsPhone = true;
          listRentalCancel.remove(line);
        }
      }
      if (!isContainsPhone) {
        System.out.println("Error when receive car");
        return;
      }
      model.handelWriteFile(listRentalCancel, pathRental, false);
      model.handelWriteFile(listReceive, pathReceive, false);

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public ArrayList<String> formatDataReceive(ArrayList<String> data) {
    ArrayList<String> list = new ArrayList<String>();
    for (String line : data) {
      HashMap<String, String> map = this.splitString(line);
      String type = map.get("type");
      String name = map.get("name");
      String phone = map.get("phone");
      String startDate = map.get("startDate");
      String timeRental = map.get("timeRental");
      // car
      int price = model.getPrice(type.toUpperCase(), Integer.parseInt(timeRental));
      String res = type + "," + name + "," + phone + "," + startDate + "," + timeRental + "," + price;
      list.add(res);
    }
    return list;
  }

  public ArrayList<String> formatDataRental(ArrayList<String> data) {
    ArrayList<String> list = new ArrayList<String>();
    for (String line : data) {
      HashMap<String, String> map = this.splitString(line);
      String type = map.get("type");
      String name = map.get("name");
      String phone = map.get("phone");
      String startDate = map.get("startDate");
      String timeRental = map.get("timeRental");
      String res = type + "," + name + "," + phone + "," + startDate + "," + timeRental;
      list.add(res);
    }
    return list;
  }

  public ArrayList<String> format(ArrayList<String> data) {
    ArrayList<String> list = new ArrayList<String>();
    for (String line : data) {
      HashMap<String, String> map = this.splitString(line);
      String type = map.get("type");
      String startDate = map.get("startDate");
      String timeRental = map.get("timeRental");
      String nameCar = map.get("nameCar");
      // car
      int price = model.getPrice(type.toUpperCase(), Integer.parseInt(timeRental));
      String res = type + "," + nameCar + "," + startDate + "," + price;
      list.add(res);
    }
    return list;
  }

  public ArrayList<String> handleSortData(ArrayList<String> listData) {
    // sort list rental by start date
    Collections.sort(listData, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        HashMap<String, String> map1 = splitString(o1);
        HashMap<String, String> map2 = splitString(o2);
        String startDate1 = map1.get("startDate");
        String startDate2 = map2.get("startDate");
        try {
          Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(startDate1);
          Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(startDate2);
          return date1.compareTo(date2);
        } catch (Exception e) {
          System.out.println(e);
        }
        return 0;
      }
    });
    return listData;
  }

  public ArrayList<String> callback(String path) {
    ArrayList<String> listData = model.handelReadFile(path);

    ArrayList<String> listRental = this.handleSortData(listData);
    ArrayList<String> list = new ArrayList<String>(listRental);
    try {
      for (String line : listRental) {
        Date currentDate = new Date();
        HashMap<String, String> map = this.splitString(line);
        String startDate = map.get("startDate");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        if (!equalsDate(currentDate, date)) {
          list.remove(line);
        }
      }
      return list;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public void solveProblem5(String pathRental) {
    ArrayList<String> listRental = this.callback(pathRental);
    view.printListData(formatDataRental(listRental));
  }

  public void solveProblem6(String pathReceive) {
    ArrayList<String> listReceive = this.callback(pathReceive);
    view.printListData(formatDataReceive(listReceive));
  }

  public boolean equalsMonth(Date date1, Date date2) {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);
    boolean sameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

    return sameMonth;

  }

  public void solveProblem7(String pathReceive, String pathRental) {
    ArrayList<String> listReceive = model.handelReadFile(pathReceive);
    ArrayList<String> listRental = model.handelReadFile(pathRental);
    // add list rental to list receive
    listReceive.addAll(listRental);
    ArrayList<String> list = new ArrayList<String>(listReceive);
    for (String line : listReceive) {
      HashMap<String, String> map = this.splitString(line);
      String startDate = map.get("startDate");
      try {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        Date currentDate = new Date();
        if (!equalsMonth(date, currentDate)) {
          list.remove(line);
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    ArrayList<String> listFormat = handleSortData(list);
    view.printListData(format(listFormat));
  }

  public boolean equalsId(ArrayList<String> ids, String checkId) {
    for (String id : ids) {
      if (checkId.equals(id)) {
        return true;
      }
    }
    return false;

  }

  public void solveProblem4(String pathRental, String pathReceive) {
    try {

      System.out.println("Enter your phone: ");
      String phone = scanner.nextLine();
      ArrayList<String> listRental = model.handelReadFile(pathRental);
      ArrayList<String> listReceive = model.handelReadFile(pathReceive);
      ArrayList<String> listCloneRental = new ArrayList<String>(listRental);
      ArrayList<String> listCloneReceive = new ArrayList<String>(listReceive);
      ArrayList<String> desire = new ArrayList<String>();
      boolean isContainsPhone = false;
      for (String line : listRental) {
        HashMap<String, String> map = this.splitString(line);
        String phoneRental = map.get("phone");
        String id = map.get("id");
        String startDate = map.get("startDate");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        // get current date dd/MM/yyyy
        Date currentDate = new Date();
        // equals day month year
        if (phoneRental.equals(phone) && this.equalsDate(date, currentDate)) {
          isContainsPhone = true;
          if (!checkContains(listReceive, id)) {
            listCloneRental.remove(line);
            listCloneReceive.add(line);
          } else {
            listCloneReceive.add(line);
            listCloneRental.remove(line);
            desire.add(id);
          }
        }
      }
      if (!isContainsPhone) {
        System.out.println("Error when receive car");
        return;
      }
      for (String line : listReceive) {
        HashMap<String, String> map = this.splitString(line);
        String id = map.get("id");
        if (equalsId(desire, id)) {
          listCloneReceive.remove(line);
          listCloneRental.add(line);
        }
      }
      model.handelWriteFile(listCloneRental, pathRental, false);
      model.handelWriteFile(listCloneReceive, pathReceive, false);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
