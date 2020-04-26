/*
 * Sadman Ahmed Shanto
 * CS 2365
 */
package bangdicegame;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Purpose: 
 * arranges all players in an array 
 * lets both user and ai to communicate with itself
 * determines when game is ended
 */

public class SimulateAI {
    public Character [] playerOrder;
    public ArrowPile arrowPile;
    int totalPlayers;
    		
    public SimulateAI (Character [] players, int totalPlayers, ArrowPile arrowPile){
        this.playerOrder = players;
        this.totalPlayers = totalPlayers;
        this.arrowPile = arrowPile;
        for(int i=0;i<totalPlayers;i++) {
        	players[i].ProbabilityVector = createProbabilityVector(totalPlayers);
        }
        
    }

	public void AITurn() {
		
		for(int i=1;i<this.totalPlayers;i++) {
			AI aiPlayer = new AI(this.playerOrder, this.totalPlayers, i, this.arrowPile);
		//	aiPlayer.getPlayerRole();
		//	aiPlayer.getPlayerProbabilityVector();
		//	aiPlayer.updateProbabilityVector();
		//	System.out.println();
		//	aiPlayer.getPlayerBehavior();
			aiPlayer.getPlayerName();
			aiPlayer.rollDice();
			System.out.println();

		//	aiPlayer.test();
		}
	}
	
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