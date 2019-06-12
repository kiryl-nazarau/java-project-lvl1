package games;

import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;


public class Drunkard {

  private static final int NUMBER_OF_PLAYERS = 2;
  private static final int PLAYER_1_INDEX = 0;
  private static final int PLAYER_2_INDEX = 1;
  private static final int PARS_TOTAL_COUNT = Par.values().length;
  private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;
  private static int[] cardsArray = new int[CARDS_TOTAL_COUNT];
  private static int[][] playersCards = new int[NUMBER_OF_PLAYERS][CARDS_TOTAL_COUNT + 1];
  private static int[] playerCardTails = new int[NUMBER_OF_PLAYERS];
  private static int[] playerCardHeads = new int[NUMBER_OF_PLAYERS];
  private static int[] cardsForIteration = new int[NUMBER_OF_PLAYERS];
  private static int iteration;



  public static void main() {

    dealCardsToPlayers();

    while (!playerCardsIsEmpty(PLAYER_1_INDEX) || !playerCardsIsEmpty(PLAYER_2_INDEX)) {

      System.out.println("Iteration N " + (++iteration) + ":");

      for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {

        cardsForIteration[i] = putCard(i);
        System.out.println("Player " + (i + 1) + " card is " + toString(cardsForIteration[i]));

      }

      int winner = determineWinner();

      if (winner == PLAYER_1_INDEX) {
        collectCards(PLAYER_1_INDEX);
        System.out.println("Player 1 wins the round!");
        } else if (winner == PLAYER_2_INDEX) {
            collectCards(PLAYER_2_INDEX);
            System.out.println("Player 2 wins the round!");
          } else {
              returnCardsToPlayers();
              System.out.println("No one wins. It's a draw!");
            }

      System.out.print("Player 1 has " + countPlayerCards(PLAYER_1_INDEX) + " cards. ");
      System.out.println("Player 2 has " + countPlayerCards(PLAYER_2_INDEX) + " cards.");

      if (countPlayerCards(PLAYER_1_INDEX) == CARDS_TOTAL_COUNT) {
        System.out.println("Player 1 wins the game. The number of iterations is " + iteration);
      } else if (countPlayerCards(PLAYER_2_INDEX) == CARDS_TOTAL_COUNT) {
        System.out.println("Player 2 wins the game. The number of iterations is " + iteration);
      }

      System.out.println("--------------------------------------------");

    }
  }

  private static Suit getSuit(int cardNumber) {
    return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
  }

  private static Par getPar(int cardNumber) {
    return Par.values()[cardNumber % PARS_TOTAL_COUNT];
  }

  private static String toString(int cardNumber) {
    return getPar(cardNumber) + " " + getSuit(cardNumber);
  }

  private static boolean playerCardsIsEmpty(int playerIndex) {
    int tail = playerCardTails[playerIndex];
    int head = playerCardHeads[playerIndex];

    return tail == head;
  }

  private static int incrementIndex(int i) {
    return (i + 1) % CARDS_TOTAL_COUNT;
  }


  // Init, shuffle and deal cards between the players, the blank places in playersCards array is set to -1
  // playerCardTails and playerCardHeads are also set up while dealing.
  private static void dealCardsToPlayers() {

    for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
      cardsArray[i] = i;
    }

    MathArrays.shuffle(cardsArray);

    playersCards[PLAYER_1_INDEX] = Arrays.copyOfRange(cardsArray, 0, CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS);
    playersCards[PLAYER_2_INDEX] = Arrays.copyOfRange(cardsArray, CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS, CARDS_TOTAL_COUNT);

    for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
      playersCards[i] = Arrays.copyOf(playersCards[i], CARDS_TOTAL_COUNT + 1);
      playerCardTails[i] = 0;
      playerCardHeads[i] = CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS;

      for (int j = CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS; j < playersCards[i].length; j++) {
        playersCards[i][j] = -1;

      }
    }
  }

  //Winner determiner, returns player id or -1 in draw case
  private static int determineWinner() {

    int[] cardsEnumIndex = new int[NUMBER_OF_PLAYERS];

    for (int i = 0; i < cardsForIteration.length; i++) {
      cardsEnumIndex[i] = Par.valueOf(getPar(cardsForIteration[i]).toString()).ordinal();
    }

    if (getPar(cardsForIteration[PLAYER_1_INDEX]) == Par.SIX && getPar(cardsForIteration[PLAYER_2_INDEX]) == Par.ACE) {
      return PLAYER_1_INDEX;
    } else if (getPar(cardsForIteration[PLAYER_1_INDEX]) == Par.ACE && getPar(cardsForIteration[PLAYER_2_INDEX]) == Par.SIX) {
      return PLAYER_2_INDEX;
    }

    if (cardsEnumIndex[PLAYER_1_INDEX] > cardsEnumIndex[PLAYER_2_INDEX]) {
      return PLAYER_1_INDEX;
    } else if (cardsEnumIndex[PLAYER_1_INDEX] < cardsEnumIndex[PLAYER_2_INDEX]) {
        return PLAYER_2_INDEX;
    }
    return -1;
  }

  //Method implements the action of taking first/next cards from the top of player's card stack.
  //When the card was put, it's array place becomes -1 and the playerCardTail is increased
  private static int putCard(int playerIndex) {
    int currentCard = playersCards[playerIndex][playerCardTails[playerIndex]];
    playersCards[playerIndex][playerCardTails[playerIndex]] = -1;
    playerCardTails[playerIndex] = incrementIndex(playerCardTails[playerIndex]);
    return currentCard;
  }

  //Method for collecting the cards which were used in the round. While adding the cards playerCardHead is also incremented
  private static void collectCards(int playerIndex) {

    for (int value : cardsForIteration) {
      playersCards[playerIndex][playerCardHeads[playerIndex]] = value;
      playerCardHeads[playerIndex] = incrementIndex(playerCardHeads[playerIndex]);
    }
  }

  //Method for returning the cards to the players in the case of draw round
  private static void returnCardsToPlayers() {

    for (int i = 0; i < cardsForIteration.length; i++) {
      playersCards[i][playerCardHeads[i]] = cardsForIteration[i];
      playerCardHeads[i] = incrementIndex(playerCardHeads[i]);

    }
  }

  //Counting the player's cards. As blank spaces in the array is marked as -1, it count the places which are != -1
  private static int countPlayerCards(int playerIndex) {
    int cardAmount = 0;
    for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
      if (playersCards[playerIndex][i] >= 0) {
        cardAmount++;
      }
    }
    return cardAmount;
  }

  enum Suit {
    SPADES,
    HEARTS,
    CLUBS,
    DIAMONDS
  }

  enum Par {
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE
  }

}
