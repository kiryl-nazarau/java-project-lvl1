package games;

public class Slot {
  private static final int REEL_SIZE = 7;
  private static final int AMOUNT_OF_REELS = 3;
  private static int reelValue;
  private static final int START_AMOUNT = 100;
  private static final int MAX_EFFORT = 100;
  private static int amount = START_AMOUNT;
  private static final int BET = 10;
  private static final int WIN = 1000;
  private static int[] line = new int[AMOUNT_OF_REELS];


  public static void main() {

    while (amount > 0) {

      System.out.println("Your amount is " + amount + "$, Your bet is " + BET + "$");
      System.out.println("Spinning the reels! The round has the following results:");

      for (int i = 0; i < AMOUNT_OF_REELS; i++) {
        reelValue = (reelValue + ((int) Math.round(Math.random() * MAX_EFFORT))) % REEL_SIZE;
        line[i] = reelValue;
      }

      System.out.print("First reel - " + line[0] + ". ");
      System.out.print("Second reel - " + line[1] + ". ");
      System.out.print("Third reel - " + line[2] + ".\n");

      amount -= BET;

      if (line[0] == line[1] && line[0] == line[2]) {
        amount = amount + WIN;
        System.out.println("CONGRATULATION!!! YOU WIN " + WIN + "$");
      } else {
        System.out.println("Your line did not win. You lose " + BET + "$");
      }

      System.out.println("Your current amount is " + amount + "$");

      if (amount == 0) {
        System.out.println("Game Over :(");
      }

      System.out.println("________________________________________________________");

    }
  }
}