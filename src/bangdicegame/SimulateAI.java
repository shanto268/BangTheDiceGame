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
    int totalPlayers;
    		
    public SimulateAI (Character [] players, int totalPlayers){
        this.playerOrder = players;
        this.totalPlayers = totalPlayers;
        for(int i=0;i<totalPlayers;i++) {
        	players[i].ProbabilityVector = createProbabilityVector(totalPlayers);
        }
    }

	public void AITurn() {
		
		for(int i=1;i<this.totalPlayers;i++) {
			AI aiPlayer = new AI(this.playerOrder, this.totalPlayers, i);
			//aiPlayer.getPlayerName();
			//aiPlayer.getPlayerProbabilityVector();
			//aiPlayer.updateProbabilityVector();
			//aiPlayer.getPlayerBehavior();
		}
	}
	
	public static ArrayList<Double> createProbabilityVector(int numPlayers) {
		ArrayList<Double>  vector = new ArrayList<Double>();
		
		switch(numPlayers) {
		case 4:
			vector.addAll(Arrays.asList(1./4.,1./4.,1./2.,0.0));
		//	vector = new double[] {1./4.,1./4.,1./2.,0.0}; //[sheriff,renegade,outlaws,deputy]
			break;
		case 5:
			vector.addAll(Arrays.asList(1./5, 1./5, 2./5, 1./5));
		//	vector = new double[] {1./5, 1/5, 2./5, 1/5};//[sheriff,renegade,outlaws,deputy]
			break;
		case 6:
			vector.addAll(Arrays.asList(1./6, 1./6, 1./2, 1./6));
			//	vector = new double[] {1./6, 1/6, 1/2, 1/6}; //[sheriff,renegade,outlaws,deputy]
			break;
		case 7:
			vector.addAll(Arrays.asList(1./7, 1./7, 3./7, 2./7));
		//	vector = new double[] {1./7, 1/7, 3./7, 2./7}; //[sheriff,renegade,outlaws,deputy]
			break;
		case 8:
			vector.addAll(Arrays.asList(1./8, 2./8, 3./8, 2./8));
		//	vector = new double[] {1./8, 2./8, 3./8, 2/8}; //[sheriff,renegade,outlaws,deputy]
			break;
		default:
			System.out.println("Invalid Number of Players!");
			break;
		}
		//System.out.println(vector);
		return vector;
	}
}