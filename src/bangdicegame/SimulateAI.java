/*
 * Sadman Ahmed Shanto
 * CS 2365
 */
package bangdicegame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Initializes the AI turn, probabilities, and goes through AI turn
 */


/*
 * Purpose: 
 * arranges all players in an array 
 * lets both user and ai to communicate with itself
 * determines when game is ended
 */

public class SimulateAI {

    /**
     *  array of players in playing order
     */
    public Character [] playerOrder;

    /**
     *  arrowPile object
     */
    public ArrowPile arrowPile;
    
    /**
     * total amount of players in game
     */
    int totalPlayers;
    		
    /**
     * initiates simulation of AI
     *
     * @param players array of players
     * @param totalPlayers total number of players
     * @param arrowPile arrowPile object
     */
    public SimulateAI (Character [] players, int totalPlayers, ArrowPile arrowPile){
        this.playerOrder = players;
        this.totalPlayers = totalPlayers;
        this.arrowPile = arrowPile;
        for(int i=0;i<totalPlayers;i++) {
        	players[i].ProbabilityVector = createProbabilityVector(totalPlayers);
        }
        
    }

    /**
     * runs an AI turn
     *
     * @param game GameFunctions object
     * @param i position in array
     * @param arrowPile arrowPile object
     */
    public void AITurn(GameFunctions game, int i, ArrowPile arrowPile) {
            AI aiPlayer = new AI(game, i, arrowPile);
            aiPlayer.turn();
            aiPlayer.rollDice();
            System.out.println();
        }
	
	/*
     * Game Mechanics algorithm:
     	* game termination conditions:
         	* 1)Sheriff dies (0)
         	* 2)All Outlaws and Renegades Die (1)
     	* determine_winner:
         	* for case 1) if only renegade is alive it wins or outlaws win (0)
         	* for case 2) sheriff and deputy wins (1)
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
		
    /**
     * creates starting probability vector for role, based on how many players there are
     *
     * @param numPlayers number of players in game
     * @return a probability vector
     */
    public static ArrayList<Double> createProbabilityVector(int numPlayers) {
		ArrayList<Double>  vector = new ArrayList<Double>();
		//[sheriff,renegade,outlaws,deputy]
		switch(numPlayers) {
		case 4:
			vector.addAll(Arrays.asList(1./4.,1./4.,1./2.,0.0));
			break;
		case 5:
			vector.addAll(Arrays.asList(1./5, 1./5, 2./5, 1./5));
			break;
		case 6:
			vector.addAll(Arrays.asList(1./6, 1./6, 1./2, 1./6));
			break;
		case 7:
			vector.addAll(Arrays.asList(1./7, 1./7, 3./7, 2./7));
			break;
		case 8:
			vector.addAll(Arrays.asList(1./8, 2./8, 3./8, 2./8));
			break;
		default:
			System.out.println("Invalid Number of Players!");
			break;
		}
		return vector;
	}
}