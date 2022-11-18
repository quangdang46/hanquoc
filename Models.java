import java.util.*;
import java.io.*;
import java.text.*;

public class Models {

  public Models() {

  }

  public Object createClassByName(String className, Object... args) {
    switch (className) {
      case "ECAR":
        return new EconomyCar((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "SCAR":
        return new StandardCar((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "PCAR":
        return new PremiumCar((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "LCAR":
        return new LuxuryCar((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "ESUV":
        return new EconomySuv((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "SSUV":
        return new StandardSuv((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "LSUV":
        return new LuxurySuv((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "PSUV":
        return new PremiumSuv((String) args[0], (String) args[1], Integer.parseInt((String) args[2]));
      case "LSTRUCK":
        return new LargeTruck((String) args[0], (String) args[1], Double.parseDouble(String.valueOf((args[2]))));
      case "MSTRUCK":
        return new MediumTruck((String) args[0], (String) args[1], Double.parseDouble(String.valueOf((args[2]))));
      case "SSTRUCK":
        return new SmallTruck((String) args[0], (String) args[1], Double.parseDouble(String.valueOf((args[2]))));
      default:
        return null;
    }

  }

  public int getPrice(String className, int days) {
    switch (className) {
      case "ECAR":
        return new EconomyCar().getPrice(days);
      case "SCAR":
        return new StandardCar().getPrice(days);
      case "PCAR":
        return new PremiumCar().getPrice(days);
      case "LCAR":
        return new LuxuryCar().getPrice(days);
      case "ESUV":
        return new EconomySuv().getPrice(days);
      case "SSUV":
        return new StandardSuv().getPrice(days);
      case "LSUV":
        return new LuxurySuv().getPrice(days);
      case "PSUV":
        return new PremiumSuv().getPrice(days);
      case "LSTRUCK":
        return new LargeTruck().getPrice(days);
      case "MSTRUCK":
        return new MediumTruck().getPrice(days);
      case "SSTRUCK":
        return new SmallTruck().getPrice(days);
      default:
        return 0;
    }

  }

  public ArrayList<Object> initListCars(ArrayList<Object> listCars, String path) {
    try {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] arr = line.split("_");
        String nameClass = arr[0].toUpperCase();
        Object[] args = Arrays.copyOfRange(arr, 1, arr.length);
        Object obj = createClassByName(nameClass, args);
        listCars.add(obj);
      }
      sc.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return listCars;
  }

  public void writeRental(Rental rental, String path) {
    try {
      FileWriter fileWriter = new FileWriter(path, true);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(rental.toString());
      bufferedWriter.newLine();
      bufferedWriter.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public ArrayList<String> initListIdRental(String path) {
    try {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      ArrayList<String> listIdRental = new ArrayList<>();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] arr = line.split(",");
        String id = arr[0].split("_")[0];
        listIdRental.add(id);
      }
      sc.close();
      return listIdRental;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public void deleteCarById(String carId, String path) {
    try {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      ArrayList<String> listCars = new ArrayList<>();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] arr = line.split("_");
        String id = arr[1];
        if (!id.equalsIgnoreCase(carId)) {
          listCars.add(line);
        }
      }
      sc.close();
      handelWriteFile(listCars, path, false);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void handelWriteFile(ArrayList<String> lines, String path, boolean isContinue) {
    try {
      File file = new File(path);
      FileWriter fileWriter = new FileWriter(file, isContinue);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      for (String line : lines) {
        bufferedWriter.write(line);
        bufferedWriter.newLine();
      }
      bufferedWriter.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public ArrayList<String> handleCancelRental(String path, String phone, String timeRetal) {
    try {
      Date checkDate = new SimpleDateFormat("dd/MM/yyyy").parse(timeRetal);
      File file = new File(path);
      Scanner sc = new Scanner(file);
      ArrayList<String> listReturn = new ArrayList<>();
      ArrayList<String> listRental = new ArrayList<>();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] arr = line.split(",");
        String customer = arr[1];
        String dateRental = arr[2];
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateRental);
        String phoneRental = customer.split("_")[1];
        if (phoneRental.equalsIgnoreCase(phone) && date.equals(checkDate)) {
          listReturn.add(line);
        } else {
          listRental.add(line);
        }
      }
      sc.close();
      handelWriteFile(listRental, path, false);
      return listReturn;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public ArrayList<String> handelReadFile(String path) {
    try {
      File file = new File(path);
      Scanner sc = new Scanner(file);
      ArrayList<String> listRental = new ArrayList<>();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        listRental.add(line);
      }
      sc.close();
      return listRental;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

}
