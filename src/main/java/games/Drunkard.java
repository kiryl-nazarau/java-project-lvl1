package games;

import org.slf4j.Logger;
import java.util.Arrays;

class Drunkard {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);
  private static final int NUMBER_OF_PLAYERS = 2;
  private static final int PLAYER_1_INDEX = 0;
  private static final int PLAYER_2_INDEX = 1;
  private static int[][] playersCards = new int[NUMBER_OF_PLAYERS][CardUtils.CARDS_TOTAL_COUNT + 1];
  private static int[] playerCardTails = new int[NUMBER_OF_PLAYERS];
  private static int[] playerCardHeads = new int[NUMBER_OF_PLAYERS];
  private static int[] cardsForIteration = new int[NUMBER_OF_PLAYERS];
  private static int iteration;

  static void main() {
    dealCardsToPlayers(CardUtils.getShuffledCards());

    while (!playerCardsIsEmpty(PLAYER_1_INDEX) || !playerCardsIsEmpty(PLAYER_2_INDEX)) {
      log.info("Iteration N {}:", ++iteration);

      for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
        putCard(i);
      }

      determineRoundWinner();
      log.info("Player 1 has {} cards. Player 2 has {} cards.\n", countPlayerCards(PLAYER_1_INDEX), countPlayerCards(PLAYER_2_INDEX));

      if (countPlayerCards(PLAYER_1_INDEX) == CardUtils.CARDS_TOTAL_COUNT) {
        log.info("Player 1 wins the game. The number of iterations is {}", iteration);
      } else if (countPlayerCards(PLAYER_2_INDEX) == CardUtils.CARDS_TOTAL_COUNT) {
        log.info("Player 2 wins the game. The number of iterations is {}", iteration);
      }
    }
  }

  private static boolean playerCardsIsEmpty(int playerIndex) {
    int tail = playerCardTails[playerIndex];
    int head = playerCardHeads[playerIndex];
    return tail == head;
  }

  private static int incrementIndex(int i) {
    return (i + 1) % CardUtils.CARDS_TOTAL_COUNT;
  }

  private static void dealCardsToPlayers(int[] shuffledCards) {

    for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
        Arrays.fill(playersCards[i], -1);
      }

    for (int i = 0; i < shuffledCards.length; i++) {
      if (i < shuffledCards.length / NUMBER_OF_PLAYERS) {
        addCardToPlayer(shuffledCards[i], PLAYER_1_INDEX);
      } else {
        addCardToPlayer(shuffledCards[i], PLAYER_2_INDEX);
      }
    }
  }

  private static void putCard(int playerIndex) {
    cardsForIteration[playerIndex] = playersCards[playerIndex][playerCardTails[playerIndex]];
    playersCards[playerIndex][playerCardTails[playerIndex]] = -1;
    playerCardTails[playerIndex] = incrementIndex(playerCardTails[playerIndex]);
    log.info("Player {} card is {}", playerIndex + 1, CardUtils.toString(cardsForIteration[playerIndex]));
  }

  private static void addCardToPlayer(int card, int playerIndex) {
    playersCards[playerIndex][playerCardHeads[playerIndex]] = card;
    playerCardHeads[playerIndex] = incrementIndex(playerCardHeads[playerIndex]);
  }

  private static void collectCards(int playerIndex) {
    for (int card : cardsForIteration) {
      addCardToPlayer(card, playerIndex);
    }
  }

  private static void returnCardsToPlayers() {
    for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
      addCardToPlayer(cardsForIteration[i], i);
    }
  }

  private static void determineRoundWinner() {
    int[] parEnumIndex = new int[NUMBER_OF_PLAYERS];

    for (int i = 0; i < cardsForIteration.length; i++) {
      parEnumIndex[i] = CardUtils.Par.valueOf(CardUtils.getPar(cardsForIteration[i]).toString()).ordinal();
    }

      if (isPlayerWin(parEnumIndex, PLAYER_1_INDEX)) {
      collectCards(PLAYER_1_INDEX);
      log.info("Player 1 wins the round!");
    } else if (isPlayerWin(parEnumIndex, PLAYER_2_INDEX)) {
      collectCards(PLAYER_2_INDEX);
      log.info("Player 2 wins the round!");
    } else {
      returnCardsToPlayers();
      log.info("No one wins. It's a draw!");
    }
  }

  private static boolean isPlayerWin(int[] parEnumIndex, int playerIndex) {
    int oppositePlayerIndex;
    if (playerIndex == PLAYER_1_INDEX) {
      oppositePlayerIndex = PLAYER_2_INDEX;
    } else oppositePlayerIndex = PLAYER_1_INDEX;

    if (parEnumIndex[oppositePlayerIndex] == 0 && parEnumIndex[playerIndex] == 8) {
      return false;
    }

    return parEnumIndex[playerIndex] == 0 && parEnumIndex[oppositePlayerIndex] == 8 || parEnumIndex[playerIndex] > parEnumIndex[oppositePlayerIndex];
  }

  private static int countPlayerCards(int playerIndex) {
    int cardAmount = 0;
    for (int i = 0; i < CardUtils.CARDS_TOTAL_COUNT; i++) {
      if (playersCards[playerIndex][i] >= 0) {
        cardAmount++;
      }
    }
    return cardAmount;
  }
}