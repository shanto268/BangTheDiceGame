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
        boolean realPlayerDead;
        
        
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
                
        
        if (Game.numOfPlayers > 3){
            while (!"Sheriff".equals(players[i].role)){
                Game.next_turn();
                i++;
            }
        }
        
        SimulateAI AI = new SimulateAI(players, totalPlayers, arrowPile);
       	

        while(!Game.game_over) {
            System.out.println();
            
            //Prints players current turn
            System.out.println("It is currently " + Game.get_current_player().name + "'s turn\n");
            
            if (Game.get_current_player() == players[0] && !Game.realPlayerDead){
                GameFunctions.player_turn(Game, allDice, arrowPile);
            }
            
            else{
                int j;
                i = 0;
                System.out.println("Attempted AI Turn");
                for (j = 0; j < Game.numOfPlayers; j++){
                    if (!players[j].name.equals(Game.get_current_player().name)){
                        i++;
                    }
                }
                i++;
                AI.AITurn(Game, i, arrowPile);
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