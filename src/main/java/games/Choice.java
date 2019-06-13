package games;

import org.slf4j.Logger;
import java.io.IOException;

public class Choice {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(Choice.class);

  private static final String LINE_SEPARATOR = System.lineSeparator();

  public static void main(String[] args) throws IOException {
    log.info("Chose the game:\n1 - \"Slots\", 2 - \"Drunkard\", 3 - \"BlackJack\"");
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
    log.info("{} \"Y\" - Yes, {any other char} - No (To exit the game enter Ctrl + C)", message);
    switch (getCharacterFromUser()) {
      case 'Y':
      case 'y': return true;
      default: return false;
    }
  }
}