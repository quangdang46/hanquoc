
import java.util.*;

public class Views {
  public Views() {

  }

  public void printListCars(String[] listCars) {
    for (int i = 0; i < listCars.length; i++) {
      System.out.println(i + 1 + " " + listCars[i]);
    }
  }

  public void printListData(ArrayList<String> listsData) {
    for (int i = 0; i < listsData.size(); i++) {
      System.out.println(i + 1 + " " + listsData.get(i));
    }
  }
}
