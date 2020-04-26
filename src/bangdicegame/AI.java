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

/*
 *new functionality needed:
 *turn function
	 *need to include game termination check
	 *eliminate players needs to be added
	 *check stats after each round
	 *reset all arraylists that use add after turn
 * update totalPlayer number and EveryoneAlive function
 * randomize the number of turns
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
	private ArrayList<String> targetRole;
	private ArrayList<String> diceResults;
	private ArrayList<String> keptDice;
	private int health;
	private final int subtractHealth = 5;
	private final int thresholdHealth;  
	private int position;
	private final int totalPlayers;
	private int playersAlive;
	private int maxRolls = 3;
	
	private ArrayList<Double> ProbabilityVector;
	public Character [] playerOrder;
	public Character currentPlayer;
	public ArrowPile arrowPile;
	public int remainingArrows;
	public AI() {
	}
	
//=================  Constructor =====================================
	@SuppressWarnings("unchecked")
	public AI(Character [] players, int numPlayers, int pos, ArrowPile arrowPile) {
		//this.ProbabilityVector = createProbabilityVector(numPlayers);
		this.SkepticProbability = getProbability(0.0,0.4);
        this.Aggressiveness = getProbability(0.0,0.4);
        this.Safetiness = getProbability(0.0,0.4);
        this.Niceness  = getProbability(0.0,0.5);
        
		this.willingToTrick = getProbability(0.0,0.2);
        this.willingToKeepDice  = getProbability(0.0,1.0);
        this.willingToKeepHealth  = getProbability(0.5,1.0);
        this.willingToKeepArrow = getProbability(0.0,1.0);
        this.willingToKeepGatling = getProbability(0.3,0.7);
        this.willingToKeepShots = getProbability(0.5,1.0);
        
        this.playerOrder = players;
        this.position = pos;
        this.currentPlayer = players[pos];
        this.role = players[pos].role;
        this.health = players[pos].lifePoints;
        this.thresholdHealth = this.health - this.subtractHealth;
        this.name = players[pos].name;
        this.totalPlayers = numPlayers;
        this.targetRole = new ArrayList<String>(); 
        this.arrowPile = arrowPile;
        this.remainingArrows = this.arrowPile.remaining;
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
	
	public void getGuessRole(){
		for (int i=0;i<this.totalPlayers;i++){
			if ( i!=this.position ) { 
				System.out.println("guessed role of " + this.playerOrder[i].role + " is " + this.playerOrder[i].aiGuessRole);
			}
		}
	}
	
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
			}//end of for loop (players)
		} //end of conditional	
	}//end of method
	
	public void assignOpponents(){
		//Assign enemy to AI
		if (this.role == "Sheriff") {
			this.targetRole.add("Outlaw");
			this.targetRole.add("Renegade");
		}
		else if (this.role == "Outlaw") {
			this.targetRole.add("Sheriff");
		}
		else if (this.role == "Renegade") {
			if (EveryoneIsAlive()) {
				this.targetRole.add("Outlaw");
				this.targetRole.add("Deputy");
			}
			else {
				this.targetRole.clear();
				this.targetRole.add("Sheriff");
			}
		}
		else { //Deputy
			this.targetRole.add("Outlaw");
			this.targetRole.add("Renegade");
		}
	}
	
	public void guessRoles() {
		if (EveryoneIsAlive()) {
			for (int i=0;i<this.totalPlayers;i++){
				if ( i!=this.position ) { //not the present AI
					Double maxProb = Collections.max(this.playerOrder[i].ProbabilityVector);
					Integer maxIdx = this.playerOrder[i].ProbabilityVector.indexOf(maxProb);
					switch(maxIdx) {
					case 0: //S
						this.playerOrder[i].aiGuessRole = "Sheriff";
						break;
					case 1: //R
						this.playerOrder[i].aiGuessRole = "Renegade";
						break;
					case 2: //O
						this.playerOrder[i].aiGuessRole = "Outlaw";
						break;
					case 3: //D
						this.playerOrder[i].aiGuessRole = "Deputy";
						break;
					default:
						break;
					}
				}				
			} 
		} 
		else {
			for (int i=0;i<this.playersAlive;i++){
				if ( i!=this.position ) { //not the present AI
					Double maxProb = Collections.max(this.playerOrder[i].ProbabilityVector);
					Integer maxIdx = this.playerOrder[i].ProbabilityVector.indexOf(maxProb);
					switch(maxIdx) {
					case 0: //S
						this.playerOrder[i].aiGuessRole = "Sheriff";
						break;
					case 1: //R
						this.playerOrder[i].aiGuessRole = "Renegade";
						break;
					case 2: //O
						this.playerOrder[i].aiGuessRole = "Outlaw";
						break;
					case 3: //D
						this.playerOrder[i].aiGuessRole = "Deputy";
						break;
					default:
						break;
					}
				}	
			}
		}
	}
	
	public boolean keepBeer() {
		//beer -> always willing if health < threshold
		if (this.currentPlayer.lifePoints < this.thresholdHealth) {
			//apply to itself
			this.currentPlayer.lifePoints++;
			return true;
		}
		else if (Math.random() <= this.willingToKeepHealth){	
			
			if (this.currentPlayer.role=="Outlaw") {
				//help who shot the sheriff if role=outlaw
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i].numShotSheriff>0) {
						this.playerOrder[i].lifePoints++;
						return true;
					}
				}
			}
			else if (this.currentPlayer.role=="Renegade") {
			 //help sheriff is role=renegade or someone else at random if everyone is alive
			 //else help who shot sheriff
				if (EveryoneIsAlive()) {
					for (int i=0;i<this.totalPlayers;i++) {
						if (this.playerOrder[i].role=="Sheriff") {
							this.playerOrder[i].lifePoints++;
							return true;
						}
					}	
				}
				else {
					for (int i=0;i<this.totalPlayers;i++) {
						if (this.playerOrder[i].numShotSheriff>0) {
							this.playerOrder[i].lifePoints++;
							return true;
						}
					}
				}
			}
			else if (this.currentPlayer.role=="Deputy") {
				//help sheriff is role=deputy
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i].role=="Sheriff") {
						this.playerOrder[i].lifePoints++;
						return true;
					}
				}
			}
			else if (this.currentPlayer.role=="Sheriff") {
				//help who gave you health if role=sheriff 
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i].numHelpSheriff>0) {
						this.playerOrder[i].lifePoints++;
						return true;
					}
				}
			}
					
		}
		else if (Math.random() <= this.willingToTrick) {
			//help whoever
			int maxi = 0;
			for (int i=0;i<this.totalPlayers;i++) {
				if (this.playerOrder[i] != null)
					maxi++;
			}
			int rand = AIDice.randInt(0, maxi-1);
			this.playerOrder[rand].lifePoints++;
			return true;
		}
		else
			return false;
		return false;
	}
	
	public boolean toLeft(int i) {
	//	System.out.println("player 1 left role: " + this.playerOrder[(i-1)%this.totalPlayers].aiGuessRole);
		System.out.println("player pos " + i);
		System.out.println("player 1 left pos: " + (i-1)%this.totalPlayers);
		if (this.targetRole.contains(this.playerOrder[(i-1)%this.totalPlayers].aiGuessRole))
			return true;
		else
			return false;
	}
	
	public boolean toRight(int i) {
	//	System.out.println("player 1 right role: " + this.playerOrder[(i+1)%this.totalPlayers].aiGuessRole);
		System.out.println("player pos " + i);
		System.out.println("player 1 right pos: " + (i+1)%this.totalPlayers);
		if (this.targetRole.contains(this.playerOrder[(i+1)%this.totalPlayers].aiGuessRole) )
			return true;
		else
			return false;
	}
	
	public boolean twoLeft(int i) {
	//	System.out.println("player 2 left role: " + this.playerOrder[(i-2)%this.totalPlayers].aiGuessRole);
		System.out.println("player pos " + i);
		System.out.println("player 2 left pos: " + (i-2)%this.totalPlayers);
		if (this.targetRole.contains(this.playerOrder[(i-2)%this.totalPlayers].aiGuessRole))
			return true;
		else
			return false;
	}
	
	public boolean twoRight(int i) {
	//	System.out.println("player 2 right role: " + this.playerOrder[(i+2)%this.totalPlayers].aiGuessRole);
		System.out.println("player pos " + i);
		System.out.println("player 2 right pos: " + (i+2)%this.totalPlayers);
		if (this.targetRole.contains(this.playerOrder[(i+2)%this.totalPlayers].aiGuessRole) )
			return true;
		else
			return false;
	}
	
	public boolean keepShot1() {
		//shot1 -> always willing if target on pos++ or pos-- 
		int i = this.position;
		//shoot who shot sheriff if role=deputy
		if (this.currentPlayer.role=="Outlaw") {
			//shoot sheriff if role=outlaw	
			if (toLeft(i)) {
				this.playerOrder[(i-1)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (toRight(i)) {
				this.playerOrder[(i+1)%this.totalPlayers].lifePoints--;
				return true;
			}	
			else
				return false;
		}
		else if (this.currentPlayer.role=="Sheriff") {
			//shoot who shot you if role=sheriff 
			if (toLeft(i)) {
				this.playerOrder[(i-1)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (toRight(i)) {
				this.playerOrder[(i+1)%this.totalPlayers].lifePoints--;
				return true;
			}
			else 
				return false;
		}
		else if (this.currentPlayer.role=="Deputy") {
			//shoot who shot sheriff if role=deputy 
			if (toLeft(i)) {
				this.playerOrder[(i-1)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (toRight(i)) {
				this.playerOrder[(i+1)%this.totalPlayers].lifePoints--;
				return true;
			}
			else 
				return false;
		}
		else if (this.currentPlayer.role=="Renagade") {
			//shoot who shot sheriff if role=renegade 
			if (toLeft(i)) {
				this.playerOrder[(i-1)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (toRight(i)) {
				this.playerOrder[(i+1)%this.totalPlayers].lifePoints--;
				return true;
			}
			else
				return false;
		}
		//shot1 -> else keep with willing to trickProbability 
		else if (Math.random() <= this.willingToTrick){
			//shoot whoever
			int rand = AIDice.randInt(0, 1);
			if (rand==1) {
				this.playerOrder[(i-1)%this.totalPlayers].lifePoints--;
				return true;
				}
			else {
				this.playerOrder[(i+1)%this.totalPlayers].lifePoints--;
				return true;
			}
		}
		else
			return false;
	}
	
	public boolean keepShot2() {
		//shot1 -> always willing if target on pos++ or pos-- 
		int i = this.position;
		//shoot who shot sheriff if role=deputy
		if (this.currentPlayer.role=="Outlaw") {
			//shoot sheriff if role=outlaw	
			if (twoLeft(i)) {
				this.playerOrder[(i-2)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (twoRight(i)) {
				this.playerOrder[(i+2)%this.totalPlayers].lifePoints--;
				return true;
			}	
			else
				return false;
		}
		else if (this.currentPlayer.role=="Sheriff") {
			//shoot who shot you if role=sheriff 
			if (twoLeft(i)) {
				this.playerOrder[(i-2)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (twoRight(i)) {
				this.playerOrder[(i+2)%this.totalPlayers].lifePoints--;
				return true;
			}
			else 
				return false;
		}
		else if (this.currentPlayer.role=="Deputy") {
			//shoot who shot sheriff if role=deputy 
			if (twoLeft(i)) {
				this.playerOrder[(i-2)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (twoRight(i)) {
				this.playerOrder[(i+2)%this.totalPlayers].lifePoints--;
				return true;
			}
			else 
				return false;
		}
		else if (this.currentPlayer.role=="Renagade") {
			//shoot who shot sheriff if role=renegade 
			if (twoLeft(i)) {
				this.playerOrder[(i-2)%this.totalPlayers].lifePoints--;
				return true;
			}
			else if (twoRight(i)) {
				this.playerOrder[(i+2)%this.totalPlayers].lifePoints--;
				return true;
			}
			else
				return false;
		}
		//shot1 -> else keep with willing to trickProbability 
		else if (Math.random() <= this.willingToTrick){
			//shoot whoever
			int rand = AIDice.randInt(0, 1);
			if (rand==1) {
				this.playerOrder[(i-2)%this.totalPlayers].lifePoints--;
				return true;
				}
			else {
				this.playerOrder[(i+2)%this.totalPlayers].lifePoints--;
				return true;
			}
		}
		else
			return false;
	}
	
	public boolean keepGatling(int numGatling) {
		if (numGatling == 1) {
			if (Math.random() <= this.willingToKeepGatling)
				return true;
			else
				return false;
		}
		else if (numGatling == 2) {
			if (Math.random() <= (this.willingToKeepGatling + 0.1 ))
				return true;
			else
				return false;
		}
		else if (numGatling == 3) {
			if (Math.random() <= (this.willingToKeepGatling + 0.2))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean keepArrow() {
		//arrow-> keep with willing probability
		if (Math.random() <= this.willingToKeepArrow)
			return true;
		else
			return false;
	}
	
	//decision to keep dice -> "A", "D", "S1", "S2", "B", "G"
	public void keepDice(ArrayList<String> diceResults) {
		this.keptDice = new ArrayList<String>();
		int maxRolls = this.maxRolls; 
		int numDynamites = 0;
		int numGatling = 0;
		System.out.println("Dices rolled: " + diceResults);
		/*
		 * resolve arrows
		 * resolve dynamites
			 	*if dynamites > 3
			 	*turn ends 
			 	*resolve all dices
		 	 *else
		 	 	*put dynamites to keptDice
		 	 	*re-roll dices (max 3 times) 
		 * 
		 */
		for(int i=0;i<diceResults.size();i++){
			//resolve all arrows
			if (diceResults.get(i)=="A") {
                System.out.println("You rolled an arrow. You must pick up an arrow before continuing.");
                this.arrowPile.remove_arrow(this.currentPlayer, this.playerOrder);
                System.out.println("AI " + this.position + " (" + this.name + ") has " + this.currentPlayer.arrows + " arrow(s).");
                System.out.println("ArrowPile has " + this.arrowPile.remaining + " remaining.");
			}
			
			else if (diceResults.get(i)=="D") {
				System.out.println("You rolled a dynamite. It cannot be re-rolled.");
				this.keptDice.add(diceResults.get(i));
				diceResults.remove(i);
				numDynamites++;
			}
			else if (diceResults.get(i)=="G") {
				numGatling++;
			}
		}
		System.out.println("You rolled " + numGatling + " Gatlings. You rolled " + numDynamites + " Dynamites.");
	}
		/*
		while(maxRolls!=0) {
			//keep all the dynamite
			System.out.println("Roll number: " + maxRolls);
			for(int i=0;i<diceResults.size();i++){
				if (diceResults.get(i)=="D") {
					this.keptDice.add(diceResults.get(i));
					diceResults.remove(i);
					numDynamites++;
				}
				//resolve all arrows
				if (diceResults.get(i)=="A") {
                    System.out.println("You rolled an arrow. You must pick up an arrow before continuing.");
                    this.arrowPile.remove_arrow(this.currentPlayer, this.playerOrder);
                    System.out.println("AI " + this.position + " (" + this.name + ") has " + this.currentPlayer.arrows + " arrow(s).");
                    System.out.println("ArrowPile has " + this.arrowPile.remaining + " remaining.");
				}
				if (diceResults.get(i)=="G") {
					numGatling++;
				}
				
			}
			System.out.println("Dynamite number: " + numDynamites);
			System.out.println("Gatling number: " + numGatling);
			System.out.println("Resolved dice " + this.keptDice);
			//if 3+ dynamites
			if (numDynamites >= 3) {
				//self health --
				this.currentPlayer.lifePoints--;
				//other dices resolved 
				maxRolls = 0;
			}
			else {
				//assess willing to keep each of the dice
				for(int i=0;i<diceResults.size();i++) {
					
					if ((diceResults.get(i)=="B") && keepBeer()) {
						this.keptDice.add(diceResults.get(i));
						maxRolls--;
					}
					else if ((diceResults.get(i)=="S1") && keepShot1()) {
						this.keptDice.add(diceResults.get(i));
						maxRolls--;
					}
					else if ((diceResults.get(i)=="S2") && keepShot2()){
						this.keptDice.add(diceResults.get(i));
						maxRolls--;
					}
					else if ((diceResults.get(i)=="G") && keepGatling(numGatling)){
						this.keptDice.add(diceResults.get(i));
						maxRolls--;
					}
					else {
						if (keepArrow())
							this.keptDice.add(diceResults.get(i));
							maxRolls--;
					}
				}

			}
			System.out.println("Resolved dice " + this.keptDice);
		}
		
		//determine if game is over after turn is over
		//stop whenever numRolls == 0
		//look at all the dice kept and resolve them
			//gatling -> if 3 or more (all other players health-- and all arrows current player removed)
			//arrow-> resolved on roll
		//reset keptDice after turn
		 */
	
		
	/* 4) _____Dice Interactions______*/
	public void rollDice() {
	//Predict the roles of each player using the Probability Vector associated with that player
		assignOpponents();
	//role is allocated by matching the max probability to the roles
		guessRoles();
		//getGuessRole();
	//roll the dice  "A", "D", "S1", "S2", "B", "G
		AIDice d = new AIDice();
		this.diceResults = d.rollThemDice(5);
		keepDice(this.diceResults);
	}

	public void test() {
		for(int i=0;i<this.totalPlayers;i++)
			System.out.println("self pos: " + i + " next 2 pos: " + (i+2)%this.totalPlayers);
		System.out.println();
	}
	//method to evaluate dice roll
	
	//method to simulate one AI turn
}
