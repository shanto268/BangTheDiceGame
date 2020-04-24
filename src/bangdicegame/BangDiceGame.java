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
        
        //Creates all of the players
        while (aiPlayers >= 0){
            players[i] = new Character(randomSelection[aiPlayers]);
            players[i].set_role(roles[i]);
            
            //Increases life points if they are the sheriff
            if ("Sheriff".equals(players[i].role)){
                players[i].lifePoints += 2;
                players[i].maxLife += 2;
            }
            
            //Temporary output, just showing players and order
            System.out.println("Player " + i + " name is: " + players[i].name);
            System.out.println("Player " + i + " role is: " + players[i].role);
            aiPlayers -= 1;
            i += 1;
        }
        
        
        GameFunctions playerOrder = new GameFunctions (players, totalPlayers);
        
        i = 0;
        
        //Creating Dice
        while (i < 5){
            allDice[i] = new Dice();
            i++;
        }
        
        
        //
        //START OF GAME
        //
        
        i = 0;
        
        //Making the sheriff the first player to go
        if (playerOrder.numOfPlayers > 3){
            while (!"Sheriff".equals(players[i].role)){
                playerOrder.next_turn();
                i++;
            }
        }
        
        System.out.println();
        
        
        
        while (!playerOrder.game_over){
            System.out.println();
            
            //Prints players current turn
            System.out.println("It is currently " + playerOrder.get_current_player().name + "'s turn\n");
            
            if (playerOrder.get_current_player() == players[0]){
                GameFunctions.player_turn(playerOrder, allDice, arrowPile);
            }
            else{
                // AI turn goes here
                //
                //
                //
                //
            	System.out.println("This works");
            	
            }
            
            System.out.println("\nThe current standings are: ");
            
            //Shows standing of life points and arrows at end of round
            for (i = 0; i < playerOrder.numOfPlayers; i++){
                System.out.println(players[i].name + " has " + players[i].lifePoints + " life and " + players[i].arrows + " arrow(s)");
            }
            
            //Goes to next player
            playerOrder.next_turn();
            input.nextLine();
            System.out.println("\n*** Press enter to progress to the next turn. ***");
            input.nextLine();
            System.out.println("\n--------------------------------------------------\n");
        }
    }
    
}