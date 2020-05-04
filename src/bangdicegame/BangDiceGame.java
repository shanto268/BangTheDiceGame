/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import java.util.Scanner;

/**
 * Contains Main, starts and runs the program
 * 
 */



/*
 *  Changes made by SAS:
 *  called AI class
 *  players[0] is the user controlled player
 *  all others are AI - created character class with isAI attribute
 * 
 */

public class BangDiceGame {

    /**
     * creates and runs the dice game
     * 
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        int [] randomSelection = {1, 2, 3, 4, 5, 6, 7, 8, 9 ,10, 11, 12, 13, 14};   //Array that will decide which of the 14 characters are assigned to players
        int aiPlayers; //number of ai players
        int totalPlayers; //number of ai players + 1
        char tempExpansions; //temporary variable to hold Y or N for if they want to use expansions
        boolean expansions; //true/false for expansions
        Character [] players = new Character [8]; //array to hold the players in order of creation
        String [] roles; //hold the roles for the players, size depends on how many players there are
        Dice [] allDice = new Dice [6]; //array for dice, up to 6 dice can be used
        ArrowPile arrowPile = new ArrowPile(); //arrowPile creation
        
        //shuffles the array, each number corresponds to a player name
        randomSelection = Character.shuffle_character(randomSelection);
        
        Scanner input = new Scanner(System.in);
        
        //Gets how many AI players there are
        System.out.print("How many AI players would you like to play with? (3-7): ");
        aiPlayers = input.nextInt();
        
        while (aiPlayers < 3 || aiPlayers > 7){
            System.out.print("Invalid input. Enter a number 3-7: ");
            aiPlayers = input.nextInt();
        }
        
        // gets Y or N for expansions
        input.nextLine();
        System.out.print("Would you like to play with the expansion packs? (Yes or No): ");
        tempExpansions = input.nextLine().charAt(0);
        
        while (tempExpansions != 'y' && tempExpansions != 'Y' && tempExpansions != 'n' && tempExpansions != 'N'){
            System.out.print("Invalid input. Enter a number 3-7: ");
            aiPlayers = input.nextInt();
        }
        
        if (tempExpansions == 'y' || tempExpansions == 'Y'){
            expansions = true;
        }
        else {
            expansions = false;
        }
        
        totalPlayers = aiPlayers + 1;
        
        //gets the correct amount of roles for the amount of players
        roles = Character.select_role(aiPlayers);
        int i = 0;
        boolean isAi = false;
        
        //Creates all of the players
        System.out.println();
        while (aiPlayers >= 0){
            if (i>0){
                isAi = true;
            }
            players[i] = new Character(randomSelection[aiPlayers],isAi);
            players[i].set_role(roles[i]);
            
            //Increases life points if they are the sheriff
            if ("Sheriff".equals(players[i].role)){
                players[i].lifePoints += 2;
                players[i].maxLife += 2;
            }
            
            System.out.println("Player " + i + " name is: " + players[i].name);
            System.out.println("Player " + i + " role is: " + players[i].role);
            System.out.println("Player " + i + " is an AI? " + players[i].isAi);
            System.out.println();
            aiPlayers -= 1;
            i += 1;
        }
              
        i = 0;
        
        //Creating Dice
        while (i < 6){
            allDice[i] = new Dice();
            i++;
        }
                
        
        //
        //  START OF GAME
        //
        GameFunctions Game = new GameFunctions (players, totalPlayers, expansions);
        
        i = 0;
                
        //makes the Sheriff the first player to go
        if (Game.numOfPlayers > 3){
            while (!"Sheriff".equals(players[i].role)){
                Game.next_turn();
                i++;
            }
        }
        
        SimulateAI AI = new SimulateAI(players, totalPlayers, arrowPile);
       	
        //stops while loop when game is over
        while(!Game.game_over) {
            System.out.println();
            
            //will refresh the dice on each turn
            while (i < 6){
                allDice[i] = new Dice();
                i++;
            } 
            
            //Prints players current turn
            System.out.println("It is currently " + Game.get_current_player().name + "'s turn\n");
            
            //if expansions are on and the player is dead, performs boneyard tasks
            if (Game.expansions && Game.get_current_player().isDead && !Game.outbreak){
                System.out.println(Game.get_current_player().name + " drew a boneyard card.");
                Game.draw_boneyard_card(Game);
            }
            
            //human player turn
            else if (Game.get_current_player() == players[0] && !Game.get_current_player().isDead){
                GameFunctions.player_turn(Game, allDice, arrowPile);
            }
            
            //AI player turn
            else if (!Game.get_current_player().isDead) {
                int j;
                i = 0;
                for (j = 0; j < Game.numOfPlayers; j++){
                    if (!players[j].name.equals(Game.get_current_player().name)){
                        i++;
                    }
                }
                i++;
                AI.AITurn(Game, i, arrowPile);
            }
            
            //prints out current standings: life and arrows
            System.out.println("\nThe current standings are: ");
            
            //Shows standing of life points and arrows at end of round
            for (i = 0; i < Game.numOfPlayers; i++){
                System.out.println(players[i].name + " has " + players[i].lifePoints + " life and " + players[i].arrows + " arrow(s)");
            }
            
            //goes to next turn
            Game.next_turn();
            
            System.out.println("\n*** Press enter to progress to the next turn. ***");
            input.nextLine();
            
            System.out.println("\n--------------------------------------------------\n");
        }
    }
}