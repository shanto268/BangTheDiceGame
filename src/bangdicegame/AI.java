/*
 * Sadman Ahmed Shanto
 * CS 2365
 */

package bangdicegame;
import java.util.Random;

/**
 *new functionality needed:
 *include arrowpile shit
 *
 */

public class AI {
	//Behavior defining parameters
	private double willingToTrick;
	private double Aggressiveness;
	private double Safetiness;
	private double Niceness;
	private double willingToKeepDice;
	private double willingToKeepHealth;
	private double willingToKeepShots;
	private double willingToKeepArrow;
	private double willingToKeepGatling;
	private double SkepticProbability;
	private double Stubbornness;
	
	private String name;
	private String role;      
	private int health;
	private int subtractHealth = 5;
	private int thresholdHealth;  
	private int position;

	
	private double [] ProbabilityVector;
	public Character [] playerOrder;
	public Character currentPlayer;
	
	public AI() {
	}
	//============  Constructor =======================
	//Assign all other players this vector 
	
	public AI(Character [] players, int numPlayers, int pos) {
		this.ProbabilityVector = createProbabilityVector(numPlayers);
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
        
        this.Stubbornness = getProbability(0.0,0.4);
        this.playerOrder = players;
        this.position = pos;
        this.currentPlayer = players[pos];
        this.role = players[pos].role;
        this.health = players[pos].lifePoints;
        this.thresholdHealth = this.health - this.subtractHealth;
        this.name = players[pos].name;
	}
	
	//getters
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
	
	/* Method to create the initial probability vector*/
	public double[] createProbabilityVector(int numPlayers) {
		double[] vector = null;

		switch(numPlayers) {
		case 4:
			vector = new double[] {1./4.,1./4.,1./2.,0.0}; //[sheriff,renegade,outlaws,deputy]
			break;
		case 5:
			vector = new double[] {1./5, 1/5, 2./5, 1/5};//[sheriff,renegade,outlaws,deputy]
			break;
		case 6:
			vector = new double[] {1./6, 1/6, 1/2, 1/6}; //[sheriff,renegade,outlaws,deputy]
			break;
		case 7:
			vector = new double[] {1./7, 1/7, 3./7, 2./7}; //[sheriff,renegade,outlaws,deputy]
			break;
		case 8:
			vector = new double[] {1./8, 2./8, 3./8, 2/8}; //[sheriff,renegade,outlaws,deputy]
			break;
		default:
			System.out.println("Invalid Number of Players!");
			break;
		}

		return vector;
	}
	
	/*Probability Generator*/
	public double getProbability(double minnum, double maxnum) {
	    Random rand = new Random();
	    return rand.nextFloat() * (maxnum - minnum) + minnum;
	}
	
	/* 1) _____Method to track which players shot sheriff______*/
	//returns array of position of players that shot sheriff
	//public int[] SheriffShooters()
	
	
	/* 2) _____Method to track which players gave beers to sheriff______*/
	//returns array of position of players that helped sheriff
	//public int[] SheriffHelpers()
	
	/* 3) _____Method to update Probability vector______*/
	//Update the vector for each player as follows:
	//While (no one died):
		//if player is sheriff
		//	vector = [1,0,0,0] fixed
		//  all other players = [0,R,O,D]
		//if player gave health to sheriff:
		//	increase the R and D probability in the vector and decrease O by SkepticProbability
		//if player shot sheriff:
		//	increase the O probability in the vector and decrease R and D by SkepticProbability
	//After someone died:
	//Update the vector to new initialization based on new information with Stubbornness probability
        // e.g. 5 players =  1S, 1R, 2O, 1D = [1,1./5,2./5,6/7]
        // e.g. 4 players = 1S, 1R, 1O, 1D = [1,1./3,1/3,1/3]
	//else disregard new information
	//if player previously gave beer to sheriff now shoots at him, then increase R with SkepticProbability
	//if player previously gave beer to sheriff still provide beers, then increase D with SkepticProbability
	//Constraint all probabilities in the vector to 0 and 1
	//Update previous record of Probability Vector
	
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
