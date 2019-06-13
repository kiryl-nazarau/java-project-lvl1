package games;

import org.slf4j.Logger;
import java.io.IOException;

class BlackJack {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);
  private static final int NUMBER_OF_PLAYERS = 2;
  private static final int PLAYER_1_INDEX = 0;
  private static final int CRU_INDEX = 1;
  private static final int START_AMOUNT = 100;
  private static final int BET = 10;
  private static final int MAX_VALUE = 21;
  private static final int MAX_CARDS_COUNT = 8;
  private static int[] cards;
  private static int cursor;
  private static int[][] playersCards = new int[NUMBER_OF_PLAYERS][MAX_CARDS_COUNT];
  private static int[] playersCursors = new int[NUMBER_OF_PLAYERS];
  private static int[] playersMoney = {START_AMOUNT, START_AMOUNT};

  static void main() throws IOException {
    while (playersMoney[PLAYER_1_INDEX] != 0 && playersMoney[CRU_INDEX] != 0) {
      initRound();
      placeBets();
      addCard2Player(PLAYER_1_INDEX);
      addCard2Player(PLAYER_1_INDEX);

      while (sum(PLAYER_1_INDEX) < 20) {
        boolean playerChoice;
        playerChoice = Choice.confirm("Do you want to take one more card?\n");
        if (playerChoice) {
          addCard2Player(PLAYER_1_INDEX);
        } else break;
      }

      addCard2Player(CRU_INDEX);
      addCard2Player(CRU_INDEX);

      while (sum(CRU_INDEX) <= 17) {
        addCard2Player(CRU_INDEX);
      }

      int playerPoints = getFinalSum(PLAYER_1_INDEX);
      int cruPoints = getFinalSum(CRU_INDEX);
      log.info("Your points - {}, CRU's points - {}", playerPoints, cruPoints);
      determineWinner(playerPoints, cruPoints);
    }

    if (playersMoney[PLAYER_1_INDEX] > 0)
      log.info("You win! Congratulations!");
    else
      log.info("You lose the game. Sorry ...");
  }

  private static void addCard2Player(int playerIndex) {
    int currentCard = cards[cursor];
    playersCards[playerIndex][playersCursors[playerIndex]] = currentCard;
    if (playerIndex == PLAYER_1_INDEX) {
      log.info("Your card is {}", CardUtils.toString(playersCards[playerIndex][playersCursors[playerIndex]]));
    } else log.info("CRU card is {}", CardUtils.toString(playersCards[playerIndex][playersCursors[playerIndex]]));
    cursor++;
    playersCursors[playerIndex]++;
  }

  private static int sum(int playerIndex) {
    int sum = 0;
    for (int i = 0; i < playersCursors[playerIndex]; i++) {
      sum = sum + value(playersCards[playerIndex][i]);
    }
    return sum;
  }

  private static int getFinalSum(int playerIndex) {
    int finalSum = sum(playerIndex);
    if (finalSum <= MAX_VALUE) {
      return finalSum;
    }
    return 0;
  }

  //Method to take the bet amount from the players account each round
  private static void placeBets() {
    for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
      playersMoney[i] = playersMoney[i] - BET;
    }
  }

  //Method to add win to player's account. BET * 2 - because previously we deduct BET from the account
  private static void addRoundWinToPlayer(int playerIndex) {
    playersMoney[playerIndex] = playersMoney[playerIndex] + (BET * 2);
  }

  //Method to return the BET to accounts in a case of a draw
  private static void returnBets() {
    for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
      playersMoney[i] = playersMoney[i] + BET;
    }
  }

  //Winner determiner, add the win to the winner ammount and print the result. In draw case returns bets to players
  private static void determineWinner(int playerPoints, int cruPoints) {
    if (playerPoints > cruPoints) {
      addRoundWinToPlayer(PLAYER_1_INDEX);
      log.info("You win this round! You get {}$\n", BET);
    } else if (playerPoints < cruPoints) {
      addRoundWinToPlayer(CRU_INDEX);
      log.info("You lose this round! You lose {}$\n", BET);
    } else {
      returnBets();
      log.info("No one wins. It's a draw!\n");
    }
  }

  private static void initRound() {
    log.info("You have {}$, and CRU has {}$. We are starting new round!", playersMoney[PLAYER_1_INDEX], playersMoney[CRU_INDEX]);
    cards = CardUtils.getShuffledCards();
    playersCards = new int[2][MAX_CARDS_COUNT];
    playersCursors = new int[]{0, 0};
    cursor = 0;
  }

  private static int value(int card) {
    switch (CardUtils.getPar(card)) {
      case JACK: return 2;
      case QUEEN: return 3;
      case KING: return 4;
      case SIX: return 6;
      case SEVEN: return 7;
      case EIGHT: return 8;
      case NINE: return 9;
      case TEN: return 10;
      case ACE:
      default: return 11;
    }
  }
}