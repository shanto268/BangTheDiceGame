/*
 * Sadman Ahmed Shanto
 * CS 2365
 */

package bangdicegame;

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
	private String role;
	private int thresholdHealth;
	
	private double [] ProbabilityVector;
	
	//============  Constructor =======================
	//Create probability vector from number of players =  [S, R, O, D]
	//Initialize the vector with input probability
	//e.g. if 5 players, the initial vector = [1/5, 1/5, 2/5, 1/5]
	//Assign all other players this vector 
	//Set the private probability values stochastically for each AI (bounded uniform distribution)
	
	//=========== Methods =============================
	
	/*_____Method to update Probability vector______*/
	//Update the vector for each player as follows:
	//While (no one died):
		//if player is sheriff
		//	vector = [1,0,0,0] fixed
		//  all other players = [0,R,O,D]
		//if player gave health to sheriff:
		//	increase the R and D probability in the vector and decrease D by SkepticProbability
		//if player shot sheriff:
		//	increase the O probability in the vector and decrease R and D by SkepticProbability
	//After someone died:
	//Update the vector to new initialization based on new information with Stubbornness probability
	//else disregard new information
	//if player previously gave beer to sheriff now shoots at him, then increase R with SkepticProbability
	//if player previously gave beer to sheriff still provide beers, then increase D with SkepticProbability
	//Constraint all probabilities in the vector to 0 and 1
	//Update previous record of Probability Vector
	
	/*_____Method to track which players shot sheriff______*/
	
	/*_____Method to track which players gave beers to sheriff______*/
	
	/*_____Dice Interactions______*/
	//Predict the roles of each player using the Probability Vector associated with that player
		//role is allocated by matching the max probability to the role 
	//After dices are rolled:
	//Case: Beers
		//Keep beers with willingToKeepBeers probability
			//if own health >= thresholdHealth and with Niceness probability
				//help sheriff is role=deputy
				//help sheriff is role=renegade or someone else at random with willingToTrick probability
				//help who gave you health if role=sheriff 
				//help who is shooting sheriff if role=outlaw
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
		//if three 
			//keeps with max(1, (willingToKeepShots + 2*random(willingToKeepShots, 1-willingToKeepShots)) probability
	//Case: Dynamite
		//needs to keep
	//Case: Arrow
		//keeps with willingToKeepArrow probability
	
}
