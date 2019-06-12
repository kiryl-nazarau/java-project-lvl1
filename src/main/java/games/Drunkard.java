package games;

import java.util.Arrays;


class Drunkard {

  private static final int NUMBER_OF_PLAYERS = 2;
  private static final int PLAYER_1_INDEX = 0;
  private static final int PLAYER_2_INDEX = 1;
  private static int[][] playersCards = new int[NUMBER_OF_PLAYERS][CardUtils.CARDS_TOTAL_COUNT + 1];
  private static int[] playerCardTails = new int[NUMBER_OF_PLAYERS];
  private static int[] playerCardHeads = new int[NUMBER_OF_PLAYERS];
  private static int[] cardsForIteration = new int[NUMBER_OF_PLAYERS];
  private static int iteration;



  static void main() {

    dealCardsToPlayers();

    while (!playerCardsIsEmpty(PLAYER_1_INDEX) || !playerCardsIsEmpty(PLAYER_2_INDEX)) {

      System.out.println("Iteration N " + (++iteration) + ":");

      for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {

        cardsForIteration[i] = putCard(i);
        System.out.println("Player " + (i + 1) + " card is " + CardUtils.toString(cardsForIteration[i]));

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

      if (countPlayerCards(PLAYER_1_INDEX) == CardUtils.CARDS_TOTAL_COUNT) {
        System.out.println("Player 1 wins the game. The number of iterations is " + iteration);
      } else if (countPlayerCards(PLAYER_2_INDEX) == CardUtils.CARDS_TOTAL_COUNT) {
        System.out.println("Player 2 wins the game. The number of iterations is " + iteration);
      }

      System.out.println("--------------------------------------------");

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


  // Deal cards between the players, the blank places in playersCards array is set to -1
  // playerCardTails and playerCardHeads are also set up while dealing.
  private static void dealCardsToPlayers() {

    int[] cardsArray = CardUtils.getShuffledCards();

    playersCards[PLAYER_1_INDEX] = Arrays.copyOfRange(cardsArray, 0, CardUtils.CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS);
    playersCards[PLAYER_2_INDEX] = Arrays.copyOfRange(cardsArray, CardUtils.CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS, CardUtils.CARDS_TOTAL_COUNT);

    for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
      playersCards[i] = Arrays.copyOf(playersCards[i], CardUtils.CARDS_TOTAL_COUNT + 1);
      playerCardTails[i] = 0;
      playerCardHeads[i] = CardUtils.CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS;

      for (int j = CardUtils.CARDS_TOTAL_COUNT / NUMBER_OF_PLAYERS; j < playersCards[i].length; j++) {
        playersCards[i][j] = -1;

      }
    }
  }

  //Winner determiner, returns player id or -1 in draw case
  private static int determineWinner() {

    int[] cardsEnumIndex = new int[NUMBER_OF_PLAYERS];

    for (int i = 0; i < cardsForIteration.length; i++) {
      cardsEnumIndex[i] = CardUtils.Par.valueOf(CardUtils.getPar(cardsForIteration[i]).toString()).ordinal();
    }

    if (CardUtils.getPar(cardsForIteration[PLAYER_1_INDEX]) == CardUtils.Par.SIX && CardUtils.getPar(cardsForIteration[PLAYER_2_INDEX]) == CardUtils.Par.ACE) {
      return PLAYER_1_INDEX;
    } else if (CardUtils.getPar(cardsForIteration[PLAYER_1_INDEX]) == CardUtils.Par.ACE && CardUtils.getPar(cardsForIteration[PLAYER_2_INDEX]) == CardUtils.Par.SIX) {
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
    for (int i = 0; i < CardUtils.CARDS_TOTAL_COUNT; i++) {
      if (playersCards[playerIndex][i] >= 0) {
        cardAmount++;
      }
    }
    return cardAmount;
  }



}
