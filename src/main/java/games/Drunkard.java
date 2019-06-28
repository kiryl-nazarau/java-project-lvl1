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
  private static int[] cardsInRound = new int[NUMBER_OF_PLAYERS];
  private static int round;

  static void main() {
    dealCardsToPlayers(CardUtils.getShuffledCards());

    while (!playerCardsIsEmpty(PLAYER_1_INDEX) || !playerCardsIsEmpty(PLAYER_2_INDEX)) {
      log.info("Round N {}:", ++round);

      for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
        putCard(i);
      }

      determineRoundWinner();

      int player1CardsAmount = countPlayerCards(PLAYER_1_INDEX);
      int player2CardsAmount = countPlayerCards(PLAYER_2_INDEX);
      log.info("Player 1 has {} cards. Player 2 has {} cards.\n", player1CardsAmount, player2CardsAmount);
      if (player1CardsAmount == CardUtils.CARDS_TOTAL_COUNT) {
        log.info("Player 1 wins the game. The number of rounds is {}", round);
      } else if (player2CardsAmount == CardUtils.CARDS_TOTAL_COUNT) {
        log.info("Player 2 wins the game. The number of rounds is {}", round);
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
    cardsInRound[playerIndex] = playersCards[playerIndex][playerCardTails[playerIndex]];
    playersCards[playerIndex][playerCardTails[playerIndex]] = -1;
    playerCardTails[playerIndex] = incrementIndex(playerCardTails[playerIndex]);
    log.info("Player {} card is {}", playerIndex + 1, CardUtils.toString(cardsInRound[playerIndex]));
  }

  private static void addCardToPlayer(int card, int playerIndex) {
    playersCards[playerIndex][playerCardHeads[playerIndex]] = card;
    playerCardHeads[playerIndex] = incrementIndex(playerCardHeads[playerIndex]);
  }

  private static void collectCards(int playerIndex) {
    for (int card : cardsInRound) {
      addCardToPlayer(card, playerIndex);
    }
  }

  private static void returnCardsToPlayers() {
    for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
      addCardToPlayer(cardsInRound[i], i);
    }
  }

  private static void determineRoundWinner() {
    int cardsComparisonResult = compareCardsInRound();
    if (cardsComparisonResult > 0) {
      collectCards(PLAYER_1_INDEX);
      log.info("Player 1 wins the round!");
    } else if (cardsComparisonResult < 0) {
      collectCards(PLAYER_2_INDEX);
      log.info("Player 2 wins the round!");
    } else {
      returnCardsToPlayers();
      log.info("No one wins. It's a draw!");
    }
  }

  private static int compareCardsInRound() {
    int result = cardsInRound[PLAYER_1_INDEX] % CardUtils.PARS_TOTAL_COUNT - cardsInRound[PLAYER_2_INDEX] % CardUtils.PARS_TOTAL_COUNT;
    return Math.abs(result) == 8 ? -result : result;
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