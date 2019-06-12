package games;

import java.io.IOException;

public class Choice {

  private static final String LINE_SEPARATOR = System.lineSeparator();


  public static void main(String[] args) throws IOException {
    System.out.println("Chose the game:\n1 - \"Slots\", 2 - \"Drunkard\", 3 - \"BlackJack\"");
    switch (getCharacterFromUser()) {
      case '1': Slot.main(); break;
      case '2': Drunkard.main(); break;
      case '3': BlackJack.main(); break;
      default: System.out.println("There is no game with this number!");
    }
  }

  private static char getCharacterFromUser() throws IOException {
    byte[] input = new byte[1 + LINE_SEPARATOR.length()];
    if (System.in.read(input) != input.length)
      throw new RuntimeException("The user has entered an insufficient number of characters.");
    return (char) input[0];
  }

  static boolean confirm(String message) throws IOException {
    System.out.println(message + " \"Y\" - Yes, {any other char} - No (To exit the game enter Ctrl + C)");
    switch (getCharacterFromUser()) {
      case 'Y':
      case 'y': return true;
      default: return false;
    }
  }
}