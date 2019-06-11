package games;

import java.io.IOException;

public class Choice {

  public static void main(String[] args) throws IOException {
    System.out.println("Chose the game:\n1 - \"Slots\", 2 - \"Drunkard\"");
    switch (System.in.read()) {
      case '1': Slot.main(); break;
      case '2': Drunkard.main(); break;
      default: System.out.println("There is no game with this number!");
    }
  }
}