/*
 * Sadman Ahmed Shanto
 * CS 2365
 */

package bangdicegame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *new functionality needed:
 *include arrowpile shit
 *need to create one method that does one turn
 *need to include game termination check
 *need to include dice shit
 *need to normalize probabilities
 */

public class AI {
	//Behavior defining parameters
	private final double willingToTrick;
	private final double Aggressiveness;
	private final double Safetiness;
	private final double Niceness;
	private final double willingToKeepDice;
	private final double willingToKeepHealth;
	private final double willingToKeepShots;
	private final double willingToKeepArrow;
	private final double willingToKeepGatling;
	private final double SkepticProbability;
	
	private final String name;
	private final String role;      
	private int health;
	private final int subtractHealth = 5;
	private final int thresholdHealth;  
	private int position;
	private final int totalPlayers;
	private int playersAlive;
	
	private ArrayList<Double> ProbabilityVector;
	public Character [] playerOrder;
	public Character currentPlayer;
	
	public AI() {
	}
	
//=================  Constructor =====================================
	@SuppressWarnings("unchecked")
	public AI(Character [] players, int numPlayers, int pos) {
		//this.ProbabilityVector = createProbabilityVector(numPlayers);
		this.SkepticProbability = getProbability(0.0,0.4);
        this.Aggressiveness = getProbability(0.0,0.4);
        this.Safetiness = getProbability(0.0,0.4);
        this.Niceness  = getProbability(0.0,0.5);
        
		this.willingToTrick = getProbability(0.0,0.2);
        this.willingToKeepDice  = getProbability(0.0,1.0);
        this.willingToKeepHealth  = getProbability(0.3,1.0);
        this.willingToKeepArrow = getProbability(0.0,1.0);
        this.willingToKeepGatling = getProbability(0.3,1.0);
        this.willingToKeepShots = getProbability(0.3,1.0);
        
        this.playerOrder = players;
        this.position = pos;
        this.currentPlayer = players[pos];
        this.role = players[pos].role;
        this.health = players[pos].lifePoints;
        this.thresholdHealth = this.health - this.subtractHealth;
        this.name = players[pos].name;
        this.totalPlayers = numPlayers;
     }
	
//==================  Getters =======================================	
	public void getPlayer() {
		System.out.println("AI " + this.position + " is " + this.currentPlayer);
	}
	
	public void getPlayerName() {
		System.out.println("AI " + this.position + " is " + this.currentPlayer.name);
	}
	
	public void getPlayerHealth() {
		System.out.println("AI " + this.position + " is " + this.currentPlayer.lifePoints);
	}
	
	public void getPlayerRole() {
		System.out.println("AI " + this.position + " is " + this.currentPlayer.role);
	}
	
	public void getPlayerBehavior() {
		System.out.println("AI " + this.position);
		System.out.println("Willing to trick: " + this.willingToTrick);
		System.out.println("Willing to keep dice: " + this.willingToKeepDice);
		System.out.println("Willing to keep beer: " + this.willingToKeepHealth);
		System.out.println("Willing to keep arrow: " + this.willingToKeepArrow);
		System.out.println("Willing to keep Gatling: " + this.willingToKeepGatling);
		System.out.println("Willing to keep gun: " + this.willingToKeepShots);		
		System.out.println("Willing to be skeptic: " +this.SkepticProbability); 
		System.out.println("Willing to be aggressive: " +this.Aggressiveness); 
		System.out.println("Willing to safe: " +this.Safetiness);
		System.out.println("Willing to nice: " +this.Niceness); 
//		System.out.println("Probability Vector: " +this.ProbabilityVector);

	}

	public void getPlayerProbabilityVector() {
		System.out.println("AI " + this.role + " P(vector) " + this.currentPlayer.ProbabilityVector);
	}
	
	/* Method to create the initial probability vector*/
	
	
	/*Probability Generator*/
	public double getProbability(double minnum, double maxnum) {
	    Random rand = new Random();
	    return rand.nextFloat() * (maxnum - minnum) + minnum;
	}
	
	/* 1) _____Method to track which players shot sheriff______*/
	//returns array of position of players that shot sheriff
	public ArrayList<Integer> SheriffShooters(){
		ArrayList<Integer> positionOfSheriffShooters = new ArrayList<Integer>();
		for (int i=0;i<this.totalPlayers;i++) {
			//System.out.println(this.playerOrder[i].numShotSheriff); 
			if ((this.playerOrder[i].numShotSheriff > 0) && i!=this.position)
				positionOfSheriffShooters.add(i);
		}
		return positionOfSheriffShooters;
	}
	
	
	/* 2) _____Method to track which players gave beers to sheriff______*/
	//returns array of position of players that helped sheriff
	public ArrayList<Integer> SheriffHelpers(){
		ArrayList<Integer> positionOfSheriffHelpers = new ArrayList<>();
		for (int i=0;i<this.totalPlayers;i++) {
			if ((this.playerOrder[i].numHelpSheriff > 0) && i!=this.position)
				positionOfSheriffHelpers.add(i);
		}
		return positionOfSheriffHelpers;
	}
	
	public boolean EveryoneIsAlive() {
		int i = 0;
		for (Character element : this.playerOrder) {
		//	System.out.println(element);
			if (element != null)
				i++;
		}
	//	System.out.println("players Alive " + i);
		this.playersAlive = i;
		if (this.playersAlive==this.totalPlayers) 
			return true;
		else
			return false;
	}
	
	/* 3) _____Method to update Probability vector______*/
	//vector format: [S,R,O,D]
	public void updateProbabilityVector() {
			if (EveryoneIsAlive()) {
			for (int i=0;i<this.totalPlayers;i++) {
				//if player is sheriff
				if (this.playerOrder[i].role == "Sheriff") {
					this.playerOrder[i].ProbabilityVector.set(0,1.0);
					this.playerOrder[i].ProbabilityVector.set(1,0.0);
					this.playerOrder[i].ProbabilityVector.set(2,0.0);
					this.playerOrder[i].ProbabilityVector.set(3,0.0);
				}
				//if player is not sheriff
				else if (this.playerOrder[i].role != "Sheriff")
					this.playerOrder[i].ProbabilityVector.set(0, 0.0);
				//if player gave health to sheriff:
				if (this.playerOrder[i].numHelpSheriff > 0) {
					double R = this.playerOrder[i].ProbabilityVector.get(1) + this.SkepticProbability;
					double D = this.playerOrder[i].ProbabilityVector.get(3) + this.SkepticProbability;
					double O = this.playerOrder[i].ProbabilityVector.get(2) - this.SkepticProbability;
					this.playerOrder[i].ProbabilityVector.set(1, R);
					this.playerOrder[i].ProbabilityVector.set(2, O);
					this.playerOrder[i].ProbabilityVector.set(3, D);
				}					
				//if player shot sheriff:
				else if (this.playerOrder[i].numShotSheriff > 0) {
					double R = this.playerOrder[i].ProbabilityVector.get(1) - this.SkepticProbability;
					double D = this.playerOrder[i].ProbabilityVector.get(3) - this.SkepticProbability;
					double O = this.playerOrder[i].ProbabilityVector.get(2) + this.SkepticProbability;	
					this.playerOrder[i].ProbabilityVector.set(1, R);
					this.playerOrder[i].ProbabilityVector.set(2, O);
					this.playerOrder[i].ProbabilityVector.set(3, D);
				}
				
				//Constraint all probabilities in the vector to 0 and 1
	        	for (int j=0;j<4;j++) {
	        		if (this.playerOrder[i].ProbabilityVector.get(j) > 1.0)
	        			this.playerOrder[i].ProbabilityVector.set(j, 1.0);
	        		if (this.playerOrder[i].ProbabilityVector.get(j) < 0.0)
	        			this.playerOrder[i].ProbabilityVector.set(j, 0.0);
	        	}
				
			} //end of for loop
		}//end of if condition
		//After someone died:
		else {
			double R = 0;
			double O = 0;
			double D = 0;
			double total = (double)this.playersAlive;
			for (int i=0;i<this.playersAlive;i++) {
				//calculate new probability
				if (this.playerOrder[i].role == "Outlaw")
					O++;
				else if (this.playerOrder[i].role == "Renegade")
					R++;
				else if (this.playerOrder[i].role == "Deputy")
					D++;
				}
			for (int i=0;i<this.playersAlive;i++) {
				//if player is sheriff
				if (this.playerOrder[i].role == "Sheriff") {
					this.playerOrder[i].ProbabilityVector.set(0,1.0);
					this.playerOrder[i].ProbabilityVector.set(1,0.0);
					this.playerOrder[i].ProbabilityVector.set(2,0.0);
					this.playerOrder[i].ProbabilityVector.set(3,0.0);
				}
				//if player is not sheriff
				else if (this.playerOrder[i].role != "Sheriff") {
					this.playerOrder[i].ProbabilityVector.set(0, 0.0);
					this.playerOrder[i].ProbabilityVector.set(1, R/total);
					this.playerOrder[i].ProbabilityVector.set(2, O/total);
					this.playerOrder[i].ProbabilityVector.set(3, D/total);
				}
				//if player previously gave beer to sheriff now shoots at him, then increase R with SkepticProbability
				if ((this.playerOrder[i].numHelpSheriff > 0) && (this.playerOrder[i].numShotSheriff > 0) ){
					this.playerOrder[i].ProbabilityVector.set(1, (R/total + this.SkepticProbability));
					this.playerOrder[i].ProbabilityVector.set(2, (O/total - this.SkepticProbability));
					this.playerOrder[i].ProbabilityVector.set(3, (D/total - this.SkepticProbability));
				}
				//if player previously gave beer to sheriff still provide beers, then increase D with SkepticProbabil
				if ((this.playerOrder[i].numHelpSheriff > 0) && (this.playerOrder[i].numShotSheriff == 0) ){
					this.playerOrder[i].ProbabilityVector.set(1, (R/total - this.SkepticProbability));
					this.playerOrder[i].ProbabilityVector.set(2, (O/total - this.SkepticProbability));
					this.playerOrder[i].ProbabilityVector.set(3, (D/total + this.SkepticProbability));
				}
				//if keeps on shooting sheriff
				if ((this.playerOrder[i].numHelpSheriff == 0) && (this.playerOrder[i].numShotSheriff > 0) ){
					this.playerOrder[i].ProbabilityVector.set(1, (R/total - this.SkepticProbability));
					this.playerOrder[i].ProbabilityVector.set(2, (O/total + this.SkepticProbability));
					this.playerOrder[i].ProbabilityVector.set(3, (D/total - this.SkepticProbability));
				}
				
				//Constraint all probabilities in the vector to 0 and 1
				for (int j=0;j<4;j++) {
	        		if (this.playerOrder[i].ProbabilityVector.get(j) > 1.0)
	        			this.playerOrder[i].ProbabilityVector.set(j, 1.0);
	        		if (this.playerOrder[i].ProbabilityVector.get(j) < 0.0)
	        			this.playerOrder[i].ProbabilityVector.set(j, 0.0);
	        	}
			}
		} //end of conditional	
	}
	
	
	
	/* 4) _____Dice Interactions______*/
	//Predict the roles of each player using the Probability Vector associated with that player
		//role is allocated by matching the max probability to the roles
                // [0,1./3,2./3,0.8]
	//After dices are rolled:
	//Case: Beers
		//Keep beers with willingToKeepBeers probability
			//if own health >= thresholdHealth and with Niceness probability
				//help sheriff is role=deputy
				//help sheriff is role=renegade or someone else at random with willingToTrick probability
				//help who gave you health if role=sheriff 
				//help who shot the sheriff if role=outlaw
			//else:
				//keep health
	//Case: Shot 
		//Keep shots if target coincides with shot range or with willingToKeepShots probabilty
				//shoot who shot sheriff if role=deputy
				//shoot who shot sheriff if role=renegade or someone else at random with willingToTrick probability
				//shoot who shot you if role=sheriff 
				//shoot sheriff if role=outlaw	
				//shoots whoever with Aggressive probability
	//Case: Gatling
		//if one gatling: 
			//keeps with willingToKeepGatling probability
		//if two gatling:
			//keeps with willingToKeepShots + random(willingToKeepShots, 1-willingToKeepShots) probability
                        // 0.3 + random(0.3, 0.7) = 0.3 + 0.35 = 0.65
		//if three 
			//keeps with min(1, (willingToKeepShots + 1.5*random(willingToKeepShots, 1-willingToKeepShots)) probability
	//Case: Dynamite
		//needs to keep
	//Case: Arrow
		//keeps with willingToKeepArrow probability
	
}
