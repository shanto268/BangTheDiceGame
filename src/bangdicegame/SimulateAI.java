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
	
	public void revealResults() {
		for (int i=0;i<this.totalPlayers;i++) {
			if (this.playerOrder[i].isAi)
				System.out.println(this.playerOrder[i].name + " was the " + this.playerOrder[i].role + " and was an AI.");
			else
				System.out.println(this.playerOrder[i].name + " was the " + this.playerOrder[i].role + " and was the player");
		}
	}
	
	public void congrats(int result) {
		//if results = 0-> winner outlaw or renegade
		boolean aiWon = false;
		boolean userWon = false;
		boolean outlawWon = false;
		boolean renegadeWon = false;	
		if (result==0) {
			for (int i=0;i<this.totalPlayers;i++) {
				if (this.playerOrder[i].role=="Outlaw") {
					outlawWon = true;
					if (this.playerOrder[i].isAi)
						aiWon = true;
					else if (!this.playerOrder[i].isAi)
						userWon = true;
				}
				else {
					if (this.playerOrder[i].role=="Renegade") {
						renegadeWon = true;
						if (this.playerOrder[i].isAi)
							aiWon = true;
						else if (!this.playerOrder[i].isAi)
							userWon = true;
						
					}
				}		
			}//for loop ends
			if (outlawWon) {
				if (aiWon && userWon)
					System.out.println("Congratulations!! Both the Player and AI have won as Outlaws!");
				else if (aiWon && !userWon)
					System.out.println("You have lost the game and the AI have won as Outlaws!");
				else if (!aiWon && userWon)
					System.out.println("Congratulations!! You have won the game as an Outlaw!");
			}
			else if (renegadeWon){
				if (aiWon && userWon)
					System.out.println("Congratulations!! Both the Player and AI have won as Renegades!");
				else if (aiWon && !userWon)
					System.out.println("You have lost the game and the AI have won as Renagade!");
				else if (!aiWon && userWon)
					System.out.println("Congratulations!! You have won the game as an Renagade!");
			}
			else
				System.out.println("Well this is embarasing! I need to check the congrats method logic");
			
		} //end of results 0

		else if (result==1) {
			//if results = 1->winner sheriff and deputy
	    	//determine if sheriff or deputy is ai or human
			for (int i=0;i<this.totalPlayers;i++) {
				if ((this.playerOrder[i].role=="Sheriff") || (this.playerOrder[i].role=="Deputy")) {
					if (this.playerOrder[i].isAi)
						aiWon=true;
					else if (!this.playerOrder[i].isAi)
						userWon=true;
				}	
			}//end of for loop
			if (aiWon && userWon)
				System.out.println("Congratulations!! Both the Player and AI have won as Sheriff and Deputy!");
			else if (aiWon && !userWon)
				System.out.println("You have lost the game and the AI have won as Sheriff and Deputy!");
			else if (!aiWon && userWon)
				System.out.println("Congratulations!! You have won the game!");			
		}
	} //end of results 1
	
	public int GameOver(int numOutlaw, int numRenegade) { //0 -> sheriff dead, 1-> outlaw and renegade dead, 2->gamecontinues
		int deadOutlaw = 0;
		int deadRenegade = 0;
		
		for (int i=0;i<this.totalPlayers;i++) {
			if (this.playerOrder[i].role=="Sheriff")
				if (this.playerOrder[i].lifePoints <= 0)
					return 0; //sheriff dead
			else if (this.playerOrder[i].role=="Outlaw")
				if (this.playerOrder[i].lifePoints <= 0)
					deadOutlaw++;
			else if (this.playerOrder[i].role=="Renegade")
				if (this.playerOrder[i].lifePoints <= 0)
					deadRenegade++;
		}
		if (deadOutlaw==numOutlaw && deadRenegade==numRenegade)
			return 1;
		else
			return 2;
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