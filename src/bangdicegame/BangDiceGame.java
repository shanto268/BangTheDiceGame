/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import java.util.Scanner;

/**
 *
 * @author cmdma
 */

/**
 *Changes made by SAS:
 *called AI class
 * players[0] is the user controlled player
 * all others are AI -> created character class with isAI attribute
 * 
 */

public class BangDiceGame {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        int [] randomSelection = {1, 2, 3, 4, 5, 6, 7, 8, 9 ,10, 11, 12, 13, 14, 15, 16};
        int aiPlayers;
        int totalPlayers;
        Character [] players = new Character [8];
        String [] roles;
        Dice [] allDice = new Dice [5];
        ArrowPile arrowPile = new ArrowPile();
        
        
        randomSelection = Character.shuffle_character(randomSelection);
        
        Scanner input = new Scanner(System.in);
        
        //Gets how many AI players there are
        System.out.print("How many AI players would you like to play with? (2-7): ");
        aiPlayers = input.nextInt();
        while (aiPlayers < 2 || aiPlayers > 7){
            System.out.print("Invalid input. Enter a number 2-7: ");
            aiPlayers = input.nextInt();
        }
        
        totalPlayers = aiPlayers + 1;
        
        roles = Character.select_role(aiPlayers);
        int i = 0;
        boolean isAi = false;
        //Creates all of the players
        System.out.println();
        while (aiPlayers >= 0){
        	if (i>0)
        		isAi = true;
            players[i] = new Character(randomSelection[aiPlayers],isAi);
            players[i].set_role(roles[i]);
            
            //Increases life points if they are the sheriff
            if ("Sheriff".equals(players[i].role)){
                players[i].lifePoints += 2;
                players[i].maxLife += 2;
            }
            
            //Temporary output, just showing players and order
            //System.out.println("Player " + i + " name is: " + players[i].name);
            //System.out.println("Player " + i + " role is: " + players[i].role);
            //System.out.println("Player " + i + " is an AI? " + players[i].isAi);
            //System.out.println();
            aiPlayers -= 1;
            i += 1;
        }
              
        i = 0;
        //Creating Dice
        while (i < 5){
            allDice[i] = new Dice();
            i++;
        }
                
        
        //
        //START OF GAME
        //
      //  Table table = new Table(players, totalPlayers);
        GameFunctions Game = new GameFunctions (players, totalPlayers);
        i = 0;
                
        int numOutlaw=0;
        int numRenegade=0;
        boolean AIsheriff=false;
        for(int j =0; j<totalPlayers;j++) {
        	if (players[j].role=="Outlaw")
        		numOutlaw++;
        	else if (players[j].role=="Renegade")
        		numRenegade++;
        }
        
        int sheriffPos = 0;
        
       	for(int j =0; j<totalPlayers;j++) {
        	if (!players[j].isAi)
        		System.out.println("You are " + players[j].name + " and you are the " + players[j].role);
        		System.out.println();
        	
        	if (players[j].role=="Sheriff")
        		if (players[j].isAi) {
        			AIsheriff=true;
        			sheriffPos = i;
        			System.out.println(players[j].name + " is the " + players[j].role + " and will start the game.");
        			System.out.println();
        		}
        		else {
        			AIsheriff=false;
        			sheriffPos = i;
        			System.out.println(players[j].name + " is the " + players[j].role+ " and will start the game.");
        			System.out.println();
        		}
        }
        
       	//move sheriff to players[0]
       	Character temp = players[0];
       	players[0] = players[sheriffPos];
       	players[sheriffPos] = temp;
        SimulateAI AI = new SimulateAI(players, totalPlayers, arrowPile);

        while(AI.GameOver(numOutlaw, numRenegade)==2) {
        	if (AIsheriff) {
        		//aiturn
        		AI.AITurn();
        		//playerturn
        		GameFunctions.player_turn(Game, allDice, arrowPile);
        		//stats
        		System.out.println("\nThe current standings are: ");    
                //Shows standing of life points and arrows at end of round
                for (i = 0; i < Game.numOfPlayers; i++){
                    System.out.println(players[i].name + " has " + players[i].lifePoints + " life and " + players[i].arrows + " arrow(s)");
                }
                System.out.println("Number of players in the game: " + i);
                System.out.println("\n*** Press enter to progress to the next turn. ***");
                input.nextLine();
                System.out.println("\n--------------------------------------------------\n");
        	}
        	else {
        		//playerturn	
        		GameFunctions.player_turn(Game, allDice, arrowPile);
        		//aiturn
        		AI.AITurn();
        		//stats
        		System.out.println("\nThe current standings are: ");    
                //Shows standing of life points and arrows at end of round
                for (i = 0; i < Game.numOfPlayers; i++){
                    System.out.println(players[i].name + " has " + players[i].lifePoints + " life and " + players[i].arrows + " arrow(s)");
                }
                System.out.println("Number of players in the game: " + i);
                System.out.println("\n*** Press enter to progress to the next turn. ***");
                input.nextLine();
                System.out.println("\n--------------------------------------------------\n");
        	}
        } //end of game loop
		//process winner!
      //0 -> sheriff dead, 1-> outlaw and renegade dead, 2->gamecontinues
        if (AI.GameOver(numOutlaw, numRenegade)==0) {
        	int result = 0;
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("||The game is over. The Sheriff died!||");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println();
        	AI.revealResults();
        	AI.congrats(result);
        }
        	
        else if (AI.GameOver(numOutlaw, numRenegade)==1) {
        	int result = 1;
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("||The game is over. The Outlaws and Renegades died!||");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println();
        	AI.revealResults();
        	System.out.println("This means that the Sheriff and Deputy won the game.");
        	AI.congrats(result);

        }
    }
    
}
