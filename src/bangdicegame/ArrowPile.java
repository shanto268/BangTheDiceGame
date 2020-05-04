/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;
import java.util.Scanner;

/**
 * Builds the arrow pile for use throughout game, and takes care of any arrow based actions
 */

public class ArrowPile {

    /**
     *  how many arrows are remaining in pile
     */
    public int remaining;

    /**
     *  1 if chief arrow is remaining, 0 if not
     */
    public int chiefArrow;
    
    /**
     *  initialize arrowPile
     */
    public ArrowPile(){
        this.remaining = 9;
        this.chiefArrow = 1;
    }
    
    /**
     * removes an arrow from the pile, and if the pile = 0, initiates indian attack
     *
     * @param game gameFunctions object
     */
    public void remove_arrow (GameFunctions game){
        Scanner input = new Scanner(System.in);
        char decision = 'n';
        
        if (this.remaining > 0){
            //allows user to take chief arrow if expansions are enabled
            if (this.chiefArrow == 1 && game.expansions && !game.get_current_player().isAi){
                System.out.print("Would you like to draw the chief's arrow? (Y/N): ");
                decision = input.nextLine().charAt(0);
                
                while (decision != 'y' && decision != 'Y' && decision != 'N' && decision != 'n'){
                    System.out.print("Invalid input. Please enter Y or N: ");
                    decision = input.nextLine().charAt(0);
                }
            }
            
            //if chief arrow is taken
            if (decision == 'y' || decision == 'Y'){
                System.out.println("You have taken the chief's arrow.");
                game.get_current_player().gain_arrow();
                game.get_current_player().gain_arrow();
                game.get_current_player().chiefArrow = true;
                this.chiefArrow = 0;
            }
            
            //otherwise, adds 1 arrow to player and sub 1 from remaining in pile
            else{
                this.remaining -= 1;
                game.get_current_player().gain_arrow();
                //if pile is now empty, indian attack
                if (this.pileIsEmpty()){
                    this.empty_pile(game, game.numOfPlayers);
                }
            }
        }
        else {
            System.out.println("Remove_arrow Error, no arrows remaining");
        }
    }

    /**
     * adds arrow back onto pile
     *
     * @param player current player
     */

    
    public void add_arrow (Character player){
        this.remaining += 1;
        player.lose_arrow();
    }
    
    /**
     * determines if pile is empty
     *
     * @return true if pile is empty, false otherwise
     */
    public Boolean pileIsEmpty(){
        if (this.remaining == 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Indian attack when pile is empty
     *
     * @param game GameFunctions object
     * @param totalPlayers total players alive
     */
    public void empty_pile (GameFunctions game, int totalPlayers){
        int i; //counter
        int playersDead; //number of players that will be killed
        int mostArrowsPosition; //position of player with most arrows
        int mostArrows; //largest amount of arrows any one player has
        
        playersDead = 0;
        mostArrowsPosition = 0;
        mostArrows = 0;
        
        System.out.println("\nThe last arrow was drawn and the indians attacked.\n");
        
        //counts how many players will die, and where the player with the most arrows is
        for (i = 0; i < game.numOfPlayers; i++){
            if (game.playerOrder[i].arrows >= game.playerOrder[i].lifePoints && !game.playerOrder[i].isDead){
                playersDead++;
            }
            if (game.playerOrder[i].arrows > mostArrows){
                mostArrowsPosition = i;
                mostArrows = game.playerOrder[i].arrows;
            }
        }
        
        //if the player with the most arrows has the cheif arrow, they do not take damage
        if (game.playerOrder[mostArrowsPosition].chiefArrow == true){
            if (game.playerOrder[mostArrowsPosition].arrows >= game.playerOrder[mostArrowsPosition].lifePoints){
                playersDead--;
            }
            System.out.println(game.playerOrder[mostArrowsPosition].name + " had the most arrows and the chief's arrow, so they will take no damage.");
            game.playerOrder[mostArrowsPosition].arrows = 0;
        }
        
        //if the indians will kill all the players, the game is over
        if (playersDead >= game.numAlivePlayers){
            game.game_over = true;
            System.out.println("All players are dead, so the outlaws win.");
        }
        
        //takes life from each player equal to how many arrows they have, then sets their arrows back to 0
        if (!game.game_over){
            for (i = 0; i < totalPlayers; i++){
                if (!game.game_over){
                   while (game.playerOrder[i].arrows > 0){
                        if ("Jourdonnais".equals(game.playerOrder[i].name)){
                            game.playerOrder[i].lose_life(game, this, true);
                            game.playerOrder[i].arrows = 0;
                        }
                        else if (game.playerOrder[i].lifePoints > 0){
                            game.playerOrder[i].lose_arrow();
                            game.playerOrder[i].lose_life(game, this, true);
                        }
                    }
                    game.playerOrder[i].chiefArrow = false; 
                }
            }
        }
        
        //refreshes arrow pile variables
        this.remaining = 9;
        this.chiefArrow = 1;
    }
}