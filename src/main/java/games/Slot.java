package games;

import org.slf4j.Logger;

class Slot {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);
  private static final int REEL_SIZE = 7;
  private static final int AMOUNT_OF_REELS = 3;
  private static int reelValue;
  private static final int START_AMOUNT = 100;
  private static final int MAX_EFFORT = 100;
  private static int amount = START_AMOUNT;
  private static final int BET = 10;
  private static final int WIN = 1000;
  private static int[] line = new int[AMOUNT_OF_REELS];

  static void main() {
    while (amount > 0) {

      log.info("Your amount is {}$, Your bet is {}$", amount, BET);
      log.info("Spinning the reels! The round has the following results:");

      for (int i = 0; i < AMOUNT_OF_REELS; i++) {
        reelValue = (reelValue + ((int) Math.round(Math.random() * MAX_EFFORT))) % REEL_SIZE;
        line[i] = reelValue;
      }

      log.info("First reel - {}. ", line[0]);
      log.info("Second reel - {}. ", line[1]);
      log.info("Third reel - {}.", line[2]);

      amount -= BET;

      if (line[0] == line[1] && line[0] == line[2]) {
        amount = amount + WIN;
        log.info("CONGRATULATION!!! YOU WIN {}$", WIN);
      } else {
        log.info("Your line did not win. You lose {}$", BET);
      }

      log.info("Your current amount is {}$\n", amount);

      if (amount == 0) {
        log.info("Game Over :(");
      }
    }
  }
}