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
            System.out.println("Player " + i + " name is: " + players[i].name);
            System.out.println("Player " + i + " role is: " + players[i].role);
            System.out.println("Player " + i + " is an AI? " + players[i].isAi);
            System.out.println();
            aiPlayers -= 1;
            i += 1;
        }
              
        i = 0;
        //Creating Dice
        while (i < 5){
            allDice[i] = new Dice();
            i++;
        }
        
        /*
         * Need to do: 
         * 		game condition start needs to change
         * 		game termination condition needs to be added to AI class as well
         * 		game initialization needs to change as well
         */
        
        
        //
        //START OF GAME
        //
      //  Table table = new Table(players, totalPlayers);
        GameFunctions Game = new GameFunctions (players, totalPlayers);
        SimulateAI AI = new SimulateAI(players, totalPlayers, arrowPile);
        i = 0;
        
        //Making the sheriff the first player to go
        if (Game.numOfPlayers > 3){
            while (!"Sheriff".equals(players[i].role)){
                Game.next_turn();
                i++;
            }
        }
        
        System.out.println();
        
        /*
         * Game Mechanics algorithm:
         	* game termination conditions:
	         	* 1)Sheriff dies
	         	* 2)All Outlaws and Renegades Die
         	* determine_winner:
	         	* for case 1) if only renegade is alive it wins or outlaws win
	         	* for case 2) sheriff and deputy wins
         	* ================================================================
         	* while !gameover
         		* if sheriff.isAi
         			*AI goes first
         			*human next
         			*display results
     			*else
     				*human goes first
     				*AI next
     				*display results
			*process winner!
     	* 
        */
        
        
        while (!Game.game_over){
            System.out.println();
            
            //Prints players current turn
            System.out.println("It is currently " + Game.get_current_player().name + "'s turn\n");
            //players[0] is the user controlled player
            if (Game.get_current_player() == players[0]){
                GameFunctions.player_turn(Game, allDice, arrowPile);
            }
            else{
                // AI turn goes here
            	AI.AITurn();
          //  	System.out.println("AI turn");
            	
            }
            
            System.out.println("\nThe current standings are: ");
            
            //Shows standing of life points and arrows at end of round
            for (i = 0; i < Game.numOfPlayers; i++){
                System.out.println(players[i].name + " has " + players[i].lifePoints + " life and " + players[i].arrows + " arrow(s)");
            }
            
            //Goes to next player
            Game.next_turn();
            input.nextLine();
            System.out.println("\n*** Press enter to progress to the next turn. ***");
            input.nextLine();
            System.out.println("\n--------------------------------------------------\n");
        }
    }
    
}