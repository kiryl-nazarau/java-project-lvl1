package games;

import java.io.IOException;

public class Choice {

  private static final String LINE_SEPARATOR = System.lineSeparator();


  public static void main(String[] args) throws IOException {
    System.out.println("Chose the game:\n1 - \"Slots\", 2 - \"Drunkard\"");
    switch (System.in.read()) {
      case '1': Slot.main(); break;
      case '2': Drunkard.main(); break;
      default: System.out.println("There is no game with this number!");
    }
  }

  static char getCharacterFromUser() throws IOException {
    byte[] input = new byte[1 + LINE_SEPARATOR.length()];
    if (System.in.read(input) != input.length)
      throw new RuntimeException("The user has entered an insufficient number of characters.");
    return (char) input[0];
  }
}