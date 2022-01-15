/*
 * Derek Lin
 * 2022-01-07
 * Culminating Project
 * War (card game)
 * This program simulates the gameplay of War, with additional features such as display speed, statistics, and turn counters.
 */
import java.util.*;
import java.io.*;
public class WarProject {
	static Scanner scan = new Scanner(System.in);

	static final int DECK_SIZE = 52;
	static final int PLAYER_DECK_SIZE = 26;

	static boolean loopGame = true;
	static String player1Name;
	static String player2Name;
	static int displaySpeed = 150;
	static int turnCounter = 1;
	static String winner;
	
	// Statistics variables
	static int totalGames = 0;
	static int highestTurns = 0;
	static int lowestTurns = 999999; // the first game has to be lower than this
	
	// Cards:
	// 0 - no card at this spot
	// 1 - ace card 
	// 2 - 2 card
	// ...
	// 13 - king card
	// Each player can have up to 52 cards (the entire deck)
	static int player1Cards[] = new int[DECK_SIZE + 1];
	static int player2Cards[] = new int[DECK_SIZE + 1];

	// Sleep method https://www.journaldev.com/1020/thread-sleep-java
	public static void sleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) throws Exception {	
		System.out.println("Welcome to War!!!");
		sleep(displaySpeed);
		displaySpeed = getDisplaySpeed(); // the display speed 
		
		while(loopGame == true) {
			int selection = greetUser();

			// If user wants to read out rules
			if (selection == 1) {
				System.out.println("Setting up: ");
				sleep(displaySpeed*3);
				// Multiplied display speed to slow text for readability
				System.out.println("Two players are required to play.");
				sleep(displaySpeed*3);
				System.out.println("There are 52 cards in the deck.");
				sleep(displaySpeed*3);
				System.out.println("- 13 different cards");
				sleep(displaySpeed*3);
				System.out.println("- The ranking from highest to lowest: A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2");
				sleep(displaySpeed*3);
				System.out.println("Each player gets 26 cards randomly drawn.");
				sleep(displaySpeed*3);
				System.out.println();
				sleep(displaySpeed*3);

				System.out.println("Playing the game: ");
				sleep(displaySpeed*3);
				System.out.println("1. Each player draws a card from their deck.");
				sleep(displaySpeed*3);
				System.out.println("	The player with the lower card goes first.");
				sleep(displaySpeed*3);
				System.out.println("2. Both players draw cards from their deck.");
				sleep(displaySpeed*3);
				System.out.println("	Whoever has the highest card takes both cards and puts it at the bottom of their deck.");
				sleep(displaySpeed*3);
				System.out.println("3. If the cards are the same, each player draws 2 more cards (1 face down, 1 face up).");
				sleep(displaySpeed*3);
				System.out.println("	The player with the higher 'facing-up' card takes all 4 cards.");
				sleep(displaySpeed*3);
				System.out.println("	If the cards are the same, repeat until a player wins 6, 8, 10, or 12 cards, etc...");
				sleep(displaySpeed*3);
				System.out.println("4. Continue the game until one player runs out of cards.");
				sleep(displaySpeed*3);
				System.out.println("	The last player standing is the winner.");
				sleep(displaySpeed*3);
				System.out.println();
				
			} else if (selection == 2) { // If start game,
				startGameAnimation(); 
				System.out.println("Game has started!");
				sleep(displaySpeed);
				getNames();
				sleep(displaySpeed);
				System.out.println(player1Name + " vs. " + player2Name);
				readCards();
				
				// loop until the game ends
				while (true) {
					
					if (!compareCard()) { // if returns false
						break;
					}
					turnCounter++;
					
				}
				writeStatistics();
				loopGame = continueGameLoop(loopGame); // Sets loopGame to what value the method returns (if false, the program will end)
			} else if (selection == 3) { // if show statistics,
				readStatistics();
				System.out.println("Total amount of games played: " + totalGames);
				System.out.println("Highest turns to win a game: " + highestTurns);
				
				if (lowestTurns == 999999) { // to make sure it doesn't print base value
					System.out.println("Lowest turns to win a game: 0");
				} else {
					System.out.println("Lowest turns to win a game: " + lowestTurns);
				}
				System.out.println();
				
			}
		}
	}
	public static boolean continueGameLoop(boolean loopGame) {
		System.out.println("Would you like to end the game? (Yes, no)");
		String input = scan.next();
		if(input.equalsIgnoreCase("yes")) {
			loopGame = false;
			System.out.println("Closing program!");
			sleep(displaySpeed);
			System.out.println("Thank you for playing War!");
			
			
		} else if (input.equalsIgnoreCase("no")){
			System.out.println("Continuing...");
			loopGame = true;
		} else {
			System.out.println("That's not an option!");
		}
		return loopGame;

	}
	public static void writeStatistics() throws Exception{ // method for output statistics to file
		PrintWriter output = new PrintWriter(new FileWriter("statistics.txt", true));
		
		// Total games played
		output.println("TotalGame "); // every "TotalGame" is one game played
		
		// User data
		output.println("Turn " + turnCounter);
		output.close();
		
	}
	
	public static void readStatistics() throws Exception{ // Method for reading from file
		File file = new java.io.File("statistics.txt");
		Scanner read = new Scanner(file);
		
		while (read.hasNext()) {
			String data = read.next();
			if (data.equals("TotalGame")) { // counting the amount of games played
				totalGames++;
			} else if (data.equals("Turn")) {
				int turn = read.nextInt();
				if (turn > highestTurns) { // seeing if this beat the previous highest amount of turns
					highestTurns = turn;
				}
				if (turn < lowestTurns) { // seeing if this beat the previous lowest amount of turns
					lowestTurns = turn;
				}
			}
				
		}
		
	}
	public static void startGameAnimation() {
		System.out.print("L"); sleep(200);
		System.out.print("o"); sleep(200);
		System.out.print("a"); sleep(300);
		System.out.print("d"); sleep(200);
		System.out.print("i"); sleep(300);
		System.out.print("n"); sleep(350);
		System.out.print("g"); sleep(200);
		System.out.print(" "); sleep(200);

		System.out.print("G"); sleep(250);
		System.out.print("a"); sleep(450);
		System.out.print("m"); sleep(350);
		System.out.print("e"); sleep(250);

		System.out.print("."); sleep(500);
		System.out.print("."); sleep(400);
		System.out.println("."); sleep(500);
	}
	
	public static int getDisplaySpeed() {
		System.out.println("First, let us set your display speed! (Fast, medium, slow): ");
		sleep(displaySpeed);
		System.out.println("Instant (0ms)");
		sleep(displaySpeed);
		System.out.println("Fast (50ms)");
		sleep(displaySpeed);
		System.out.println("Medium (300ms)");
		sleep(displaySpeed);
		System.out.println("Slow (500ms)");

		int speed = 0;
		boolean cont = true;
		while (cont == true) {
			String input = scan.next();
			if (input.equalsIgnoreCase("instant")) {
				speed = 0;
				cont = false;
			} else if (input.equalsIgnoreCase("fast")) {
				speed = 50;
				cont = false;
			} else if (input.equalsIgnoreCase("medium")) {
				speed = 300;
				cont = false;
			} else if (input.equalsIgnoreCase("slow")){
				speed = 500;
				cont = false;
			} else {
				System.out.println("That's not an option!");
				// returns to while loop
			}
		}
		return speed;
	}
	
	// Method for menu
	public static int greetUser() {
		System.out.println("Main menu: ");
		sleep(displaySpeed);
		System.out.print("1. Show Rules, ");
		sleep(displaySpeed);
		System.out.print("2. Start Game, ");
		sleep(displaySpeed);
		System.out.print("3. Show Statistics ");
		sleep(displaySpeed);

		int selection = scan.nextInt();
		return selection;
	}

	// Method for finding amount of player and name
	public static void getNames() throws Exception {

		boolean cont = true;
		while (cont == true) {
			System.out.println("1 or 2 players?");
			sleep(displaySpeed);
			String playersAmount = scan.next();

			System.out.print("Player one username: ");
			sleep(displaySpeed);
			
			if (playersAmount.equals("1")) {
				player1Name = scan.next();
				
				System.out.print("Player two username: ");
				sleep(displaySpeed);
				System.out.println("Computer");
				sleep(displaySpeed);
				player2Name = "Computer";
				
				cont = false;

			} else if (playersAmount.equals("2")) {
				player1Name = scan.next();
				System.out.print("Player two username: ");
				sleep(displaySpeed);
				player2Name = scan.next();
				cont = false;
			} else {
				System.out.println("That's not an option!");
			}
		}
		

	}
	
	// Method for handing out each players' cards
	public static void readCards() throws Exception {
		File file = new File("cards.txt");
		Scanner scanFile = new Scanner(file);
		
		// Shuffling card array (Referenced from: https://www.journaldev.com/32661/shuffle-array-java)
		List<Integer> cards = new ArrayList<>();
		while (scanFile.hasNext()) { //  This file must have DECK_SIZE (52) cards
			cards.add(scanFile.nextInt()); // .add appends value in file to the end of the list (Referenced from: https://www.journaldev.com/33297/java-list-add-addall-methods)
		}
		
		// shuffle the cards
		Collections.shuffle(cards);
		
		// The loops below converts the List in to array.
		// Player 1 cards
		for (int i = 0; i < PLAYER_DECK_SIZE; i++) {
			player1Cards[i] = cards.get(i); // .get finds the element in the list (Referenced from: https://www.geeksforgeeks.org/list-get-method-in-java-with-examples/)
		}
		
		// Player 2 cards
		for (int i = 0; i < PLAYER_DECK_SIZE; i++) {
			player2Cards[i] = cards.get(i + PLAYER_DECK_SIZE); 
		}
		
	}

	// This method returns whether the game continues and simulates the card battles
	public static boolean compareCard() throws Exception{
		int p1card = player1Cards[0]; // compares the first index of each array
		int p2card = player2Cards[0];
		
		System.out.println();
		System.out.println("===== Turn " + turnCounter +  " =====");
		sleep(displaySpeed);

		System.out.println(player1Name + " draws card " + p1card);
		sleep(displaySpeed);
		System.out.println(player2Name + " draws card " + p2card);
		sleep(displaySpeed);
		
		System.out.println("Comparing cards...");
		sleep(displaySpeed*2);
		if (p1card < p2card) { // p2 wins p1's card
			takeP1Card();
			System.out.println(player2Name + " wins this turn!");
			sleep(displaySpeed);
			System.out.println(player2Name + " takes " +player1Name +"'s " + p1card + "!");
			sleep(displaySpeed);
		} else if (p2card < p1card) { // p1 wins p2's card
			takeP2Card();
			System.out.println(player1Name + " wins this turn!");
			sleep(displaySpeed);
			System.out.println(player1Name + " takes " + player2Name +"'s " + p2card + "!");
			sleep(displaySpeed);
		} else { // cards are the same (war)
			int index = 1; // start at second card because I only need to compare those (one card is face down other is face up)
			System.out.println();
			sleep(displaySpeed);
			System.out.println("The cards are the same! This means WAR!");
			sleep(displaySpeed);
			
			while (true) { 
				
				// check if player lost (has no cards)
				if (!isCard(player1Cards[index]) || !isCard(player1Cards[index + 1])) { // check is player 1 has enough cards to complete war
					System.out.println(player1Name + " has no cards left, so " + player2Name + " wins!");
					sleep(displaySpeed);
					return false; // ends game
				} else if (!isCard(player2Cards[index]) || !isCard(player2Cards[index + 1])) { // check if player 2 has enough cards to complete war
					System.out.println(player2Name + " has no cards left, so " + player1Name + " wins!");
					sleep(displaySpeed);
					return false;
				}
				
				System.out.println("Comparing cards...");
				sleep(displaySpeed);
				System.out.println(player1Name + " has card " + player1Cards[index]);
				sleep(displaySpeed);
				System.out.println(player2Name + " has card " + player2Cards[index]);
				sleep(displaySpeed);
				
				// compare cards for war
				if (player1Cards[index] < player2Cards[index]) { // p2 wins p1's cards
					for (int i = 0; i <= index + 1; i++) {
						takeP1Card();
					}
					System.out.println(player2Name + " takes " + player1Name + "'s cards.");
					break;
				} else if (player2Cards[index] < player1Cards[index]) { // p1 wins p2's cards
					for (int i = 0; i <= index + 1; i++) {
						takeP2Card();
					}
					System.out.println(player1Name + " takes " + player2Name + "'s cards.");
					break;
				} else { // cards are same again, repeat war
					System.out.println("Another tie! Battling again...");
				}
				
				index += 2; // place another 2 cards (one down, one up)
			}
		}
		
		if (!isCard(player1Cards[0])) {
			// player 2 wins
			System.out.println(player1Name + " has no cards left, so " + player2Name + " wins!");
			return false;
		} else if (!isCard(player2Cards[0])) {
			// player 1 wins
			System.out.println(player2Name + " has no cards left, so " + player1Name + " wins!");
			return false;
		}
		return true;
	}
		
	public static void takeP1Card() {
		// add player 1's card to player 2's deck
		addCardToDeck(player1Cards[0], player2Cards);
		
		// move player 2's card to the end of the deck
		addCardToDeck(player2Cards[0], player2Cards);
		
		// move cards left by 1
		replaceFirstCardOfDeck(player1Cards);
		replaceFirstCardOfDeck(player2Cards);
	}
	
	public static void takeP2Card() {
		// add player 2's card to player 1's deck
		addCardToDeck(player2Cards[0], player1Cards);
		
		// move player 1's card to the end of the deck
		addCardToDeck(player1Cards[0], player1Cards);
		
		// move cards left by 1
		replaceFirstCardOfDeck(player1Cards);
		replaceFirstCardOfDeck(player2Cards);
	}
	
	public static void replaceFirstCardOfDeck(int[] deck) {
		// move cards left by 1
		for (int i = 0; i < deck.length - 1; i++) {
			deck[i] = deck[i + 1];
		}
		deck[deck.length - 1] = 0;
	}
	
	public static void addCardToDeck(int card, int[] deck) {
		// add p1card to p2
		for (int i = 0; i < deck.length; i++) { // replace the first index with 0 value (first index that is not occupied with a card)
			if (!isCard(deck[i])) {
				deck[i] = card;
				break;
			}
		}
	}
	
	public static boolean isCard(int card) { // check if card is 0 (not a card)
		return card != 0;
	}

}
