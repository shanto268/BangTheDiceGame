/*
 * Sadman Ahmed Shanto
 * CS 2365
 */

package bangdicegame;

public class AI {
	private double trickingProbability;
	private double ShootingProbability;
	private double HelpingProbability;
	private double ReRollProbability;
	private double SkepticProbability;
	private double [] ProbabilityVector;
	
	//============  In the constructor ===============
	//Create probability vector from number of players =  [S, R, O, D]
	//Initialize the vector with input probability
	//e.g. if 5 players, the initial vector = [1/5, 1/5, 2/5, 1/5]
	//Assign all other players this vector 
	//Set the private probability values stochastically for each AI (bounded uniform distribution)
	
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
	//Update the vector to new 
	
	
}
