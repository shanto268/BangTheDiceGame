/*
 * Sadman Ahmed Shanto
 * CS 2365
 */

package bangdicegame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 *new functionality needed: 
 *game termination loop
 *fix life factor
 *check mechanics of when someone dies
 * update totalPlayer number and EveryoneAlive function
	 *eliminate players needs to be added
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
	private int startedWith;
        private GameFunctions game;
	
	private ArrayList<Double> ProbabilityVector;
	public Character [] playerOrder;
	public Character currentPlayer;
	public ArrowPile arrowPile;
	public int remainingArrows;
	
//=================  Constructor =====================================
	@SuppressWarnings("unchecked")
	public AI(GameFunctions game, int i, ArrowPile arrowPile) {
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
        
        this.startedWith = game.originalNumOfPlayers;
        this.playerOrder = game.playerOrder;
        this.currentPlayer = game.get_current_player();
        this.role = game.get_current_player().role;
        this.health = game.get_current_player().lifePoints;
        this.thresholdHealth = this.health - this.subtractHealth;
        this.name = game.get_current_player().name;
        this.totalPlayers = game.numOfPlayers;
        this.position = i;
        this.targetRole = new ArrayList<String>(); 
        this.arrowPile = arrowPile;
        this.remainingArrows = this.arrowPile.remaining;
        this.game = game;
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

	public void trackProbabilityVector() {
		for (int i=0;i<this.totalPlayers;i++) {
			System.out.println(this.playerOrder[i].name + " P Vector is " + this.playerOrder[i].ProbabilityVector);
		}
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
		if (this.playersAlive==this.startedWith) 
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
		if (this.currentPlayer.lifePoints < this.thresholdHealth)
			return true;
		else if (Math.random() <= this.willingToKeepHealth)
			return true;
		else
			return false;
	}
	
	public boolean keepShot1() {
		int toLeft = Math.floorMod(this.position-1, this.totalPlayers);
		int toRight = Math.floorMod(this.position+1, this.totalPlayers);
		
		if (this.targetRole.contains(this.playerOrder[toLeft].aiGuessRole)  ||  this.targetRole.contains(this.playerOrder[toRight].aiGuessRole) )
			return true;
		else if (Math.random() <= this.willingToKeepShots)
			return true;
		else
			return false;
	}
	
	public boolean keepShot2() {
		int toLeft = Math.floorMod(this.position-2, this.totalPlayers);
		int toRight = Math.floorMod(this.position+2, this.totalPlayers);
		
		if (this.targetRole.contains(this.playerOrder[toLeft].aiGuessRole)  ||  this.targetRole.contains(this.playerOrder[toRight].aiGuessRole) )
			return true;
		else if (Math.random() <= this.willingToKeepShots)
			return true;
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
	
	public void resolveBeers() { //cannot give beer if max life 
		boolean sheriffHelper = false;
		boolean sheriffShooter = false;
		boolean helped = false;
		
		for (int i=0;i<this.totalPlayers;i++) {
			if (this.playerOrder[i].numShotSheriff>0)
				sheriffShooter = true;
			}
		
		for (int i=0;i<this.totalPlayers;i++) {
			if (this.playerOrder[i].numHelpSheriff>0)
				sheriffHelper = true;
			}
		
		if (this.currentPlayer.lifePoints <= this.thresholdHealth) {
			this.currentPlayer.gain_life();
			System.out.println(this.name + " drank the beer!" );
			helped = true;
			return;
		}
		
		
		if (sheriffShooter && !helped) {
			if (this.currentPlayer.role=="Outlaw") {
				//help who shot the sheriff if role=outlaw
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i].numShotSheriff>0) {
						this.playerOrder[i].gain_life();
						System.out.println(this.name + " gave the beer to " + this.playerOrder[i].name);
						helped = true;
						return;
					}
				}
			}
			
			else if (this.currentPlayer.role=="Renegade") {
				 //help sheriff is role=renegade or someone else at random if everyone is alive
				 //else help who shot sheriff
					if (EveryoneIsAlive()) {
						for (int i=0;i<this.totalPlayers;i++) {
							if (this.playerOrder[i].role=="Sheriff") {
								this.playerOrder[i].gain_life();
								this.currentPlayer.numHelpSheriff++;
								System.out.println(this.name + " gave the beer to " + this.playerOrder[i].name);
								helped = true;
								return;
							}
						}	
					}
					else {
						for (int i=0;i<this.totalPlayers;i++) {
							if (this.playerOrder[i].numShotSheriff>0) {
								this.playerOrder[i].gain_life();
								System.out.println(this.name + " gave the beer to " + this.playerOrder[i].name);
								helped = true;
								return;
							}
						}
					}
				}	
		
		}
		
		else if (!sheriffShooter && !helped) {
				//randomly give help	
				int maxi = 0;
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i] != null)
						maxi++;
				}
				int rand = (int)(Math.random()*(maxi));
				if (this.playerOrder[rand].role=="Sheriff")
					this.currentPlayer.numHelpSheriff++;
			//	System.out.println("random number " + rand);
			//	System.out.println("random player name " + this.playerOrder[rand].name);
				this.playerOrder[rand].gain_life();
				System.out.println(this.name + " gave the beer to " + this.playerOrder[rand].name);
				helped = true;
				return;
				}
		
		if (sheriffHelper && !helped) {
			 if (this.currentPlayer.role=="Sheriff") {
				//help who gave you health if role=sheriff 
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i].numHelpSheriff>0) {
						this.playerOrder[i].gain_life();
						System.out.println(this.name + " gave the beer to " + this.playerOrder[i].name);
						helped = true;
						return;
					}
				}
			}
		}
		
		else if (!sheriffHelper && !helped) {
			//randomly give help	
			int maxi = 0;
			for (int i=0;i<this.totalPlayers;i++) {
				if (this.playerOrder[i] != null)
					maxi++;
			}
			int rand = (int)(Math.random()*(maxi));
		//	System.out.println("random number " + rand);
		//	System.out.println("random player name " + this.playerOrder[rand].name);
			this.playerOrder[rand].gain_life();
			if (this.playerOrder[rand].role=="Sheriff")
				this.currentPlayer.numHelpSheriff++;
			System.out.println(this.name + " gave the beer to " + this.playerOrder[rand].name);
			helped = true;
			return;
			}
		
		if (this.currentPlayer.role=="Deputy" && !helped) {
			//help sheriff is role=deputy
			for (int i=0;i<this.totalPlayers;i++) {
				if (this.playerOrder[i].role=="Sheriff") {
					this.playerOrder[i].gain_life();
					this.currentPlayer.numHelpSheriff++;
					System.out.println(this.name + " gave the beer to " + this.playerOrder[i].name);
					helped = true;
					return;
				}
			}
		}
		
		
		
	}
	
	public void resolveBullsEye1() {
		Random r = new Random();
		int chance = r.nextInt(2); //0 or 1
		if (chance == 1) {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_next_player().name + " who is one position"
					+ " to the right." );
                        this.game.get_next_player().lose_life(game, arrowPile, false);
		}
		else {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_previous_player().name + " who is one position"
					+ " to the left." );
                        this.game.get_previous_player().lose_life(game, arrowPile, false);
		}
	}
	
	public void resolveBullsEye2() {
		Random r = new Random();
		int chance = r.nextInt(2); //0 or 1
		if (chance == 1) {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_away_player(this.game.get_current_player()).name + " who is two positions"
					+ " to the right." );
                        this.game.get_two_away_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
		else {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_before_player(this.game.get_current_player()).name + " who is two positions"
					+ " to the left." );
                        this.game.get_two_before_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
	}
	
	public void shootRandomly1() {
		Random r = new Random();
		int chance = r.nextInt(2); //0 or 1
		if (chance == 1) {
                        int j = Math.floorMod(this.position-1, this.totalPlayers);
			if (this.playerOrder[j].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_next_player().name + " who is one position"
					+ " to the right." );
                        this.game.get_next_player().lose_life(game, arrowPile, false);
		}
		else {
			int j = Math.floorMod(this.position-1, this.totalPlayers);
			if (this.playerOrder[j].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_previous_player().name + " who is one position"
					+ " to the left." );
                        this.game.get_previous_player().lose_life(game, arrowPile, false);
		}
	}
	
	public void shootRandomly2() {
		Random r = new Random();
		int chance = r.nextInt(2); //0 or 1
		if (chance == 1) {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_away_player(this.game.get_current_player()).name + " who is two positions"
					+ " to the right." );
                        this.game.get_two_away_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
		else {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_before_player(this.game.get_current_player()).name + " who is two positions"
					+ " to the left." );
                        this.game.get_two_before_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
	}
	
	public void resolveKeptDice(ArrayList<String> keptDice,int numGatling){
		System.out.println("Resolving the 5 kept dices!");
		//Gatlings
		resolveGatling(numGatling);
		for(int i=0;i<keptDice.size();i++) {
			if (keptDice.get(i)=="B" && !this.game.game_over) {
				resolveBeers();
			}
			else if (keptDice.get(i)=="S1" && !this.game.game_over) {
				resolveShot1();
			}
			else if (keptDice.get(i)=="S2" && !this.game.game_over) {
				resolveShot2();
			}
			
		}
	}
	
	public void resolveShot2() {
		int toLeft = Math.floorMod(this.position-2, this.totalPlayers);
		int toRight = Math.floorMod(this.position+2, this.totalPlayers);
		
		if (this.targetRole.contains(this.playerOrder[toLeft].aiGuessRole)) {
			if (this.playerOrder[toLeft].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_before_player(this.game.get_current_player()).name + " who is two position"
					+ " to the left." );
                        this.game.get_two_before_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
		else if (this.targetRole.contains(this.playerOrder[toRight].aiGuessRole) ) {
			if (this.playerOrder[toRight].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_away_player(this.game.get_current_player()).name + " who is two position"
					+ " to the right." );
                        this.game.get_two_away_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
		else 
			shootRandomly2();
	}
	
	public void resolveShot1() {		
		int toLeft = Math.floorMod(this.position-1, this.totalPlayers);
		int toRight = Math.floorMod(this.position+1, this.totalPlayers);
		
		if (this.targetRole.contains(this.playerOrder[toLeft].aiGuessRole)) {
			if (this.playerOrder[toLeft].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_previous_player().name + " who is one position"
					+ " to the left." );
                        this.game.get_previous_player().lose_life(game, arrowPile, false);
		}
		else if (this.targetRole.contains(this.playerOrder[toRight].aiGuessRole) ) {
			if (this.playerOrder[toRight].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_next_player().name + " who is one position"
					+ " to the right." );
                        this.game.get_next_player().lose_life(game, arrowPile, false);
		}
		else 
			shootRandomly1();
	}
		
	public void resolveGatling(int numGatling) {
		if(numGatling>= 3) {
			for(int i=0;i<this.totalPlayers;i++) {
                            if(i!=this.position)
				this.playerOrder[i].lose_life(this.game, this.arrowPile, false);
                            else {
                                this.arrowPile.remaining = this.arrowPile.remaining + this.playerOrder[i].arrows;
				this.playerOrder[i].arrows=0;
                            }
			}
		}
	}
	
	//decision to keep dice -> "A", "D", "S1", "S2", "B", "G"
	public void keepDice(ArrayList<String> diceResults) {
		this.keptDice = new ArrayList<String>();
		int maxRolls = this.maxRolls; 
		int numDynamites = 0;
		int numGatling = 0;
		System.out.println("Dices rolled: " + diceResults);
		
		for(int i=0;i<diceResults.size();i++){
			//resolve all arrows
			if (diceResults.get(i)=="A" && !this.game.game_over) {
                            System.out.println(this.name + " rolled an arrow. " + this.name + " must pick up an arrow before continuing.");
                            this.arrowPile.remove_arrow(this.game);
                            System.out.println(this.name + " has " + this.currentPlayer.arrows + " arrow(s).");
                            System.out.println("ArrowPile has " + this.arrowPile.remaining + " remaining.");
			}
			else if (diceResults.get(i)=="D" && !this.game.game_over) {
				System.out.println(this.name + " rolled a dynamite. It cannot be re-rolled.");
				this.keptDice.add(diceResults.get(i));
				diceResults.remove(i);
				numDynamites++;
			}
			else if (diceResults.get(i)=="G" && !this.game.game_over) {
				numGatling++;
			}
		}
		
		System.out.println(this.name + " rolled " + numGatling + " Gatling(s). " + this.name +   " rolled " + numDynamites + " Dynamite(s).");
		//more than 3 dynamites
		if (numDynamites>=3) {
			System.out.println("Since, " + this.name + " rolled " + numDynamites + " dynamites. " + this.name + "'s turn is over.");
			this.currentPlayer.lose_life(this.game, this.arrowPile, true);
			for(int i=0;i<diceResults.size();i++) {
				this.keptDice.add(diceResults.get(i));
				if(diceResults.get(i)=="B" && !this.game.game_over) {
					if (this.currentPlayer.lifePoints < this.thresholdHealth) {
						this.currentPlayer.lifePoints++; //apply to itself
						System.out.println(this.name + " drank the beer!");
					}
					else {
						resolveBeers();
					}
				}
				else if(diceResults.get(i)=="S1"  && !this.game.game_over) {
					resolveShot1();
				}
				else if(diceResults.get(i)=="S2" && !this.game.game_over) {
					resolveShot2();
				}
			}//end of for loop
			System.out.println(this.name+"'s final dices are " + this.keptDice); //need to replace DiceResults with keptDices
		}//end of 3+ dynamite condition
		else { //if not 3 dynamites
			int numRolls = 0;
			for(int i=0;i<diceResults.size();i++) {
				keepDices(i, numGatling, diceResults);
			}//end of for loop
			System.out.println(this.name+" kept the following dice " + this.keptDice); 
			if (this.keptDice.size()==5) {
				//resolve the dices
				resolveKeptDice(this.keptDice, numGatling);
			}
			else {
				while (numRolls!=maxRolls) {
					int diceLeft = 5-this.keptDice.size();
					System.out.println(this.currentPlayer.name + " re-rolled " + diceLeft + " dice(s).");
					//get diceLeft number of dice
					AIDice d2 = new AIDice();
					ArrayList<String> newDice = d2.rollThemDice(diceLeft);
					System.out.println("Newly rolled dices " + newDice);
					//update number of Gatling
					for(int i=0;i<newDice.size();i++) {
						if (newDice.get(i)=="G") {
							numGatling++;
						}
					}
					//keep using keepDices and add to keptDice
					for(int i=0;i<newDice.size();i++) {
						keepDices(i, numGatling, newDice);
					}
					System.out.println(this.currentPlayer.name + " rolled " + (numRolls+1) + " times this round!");
					if ( (this.keptDice.size() == 5))
						numRolls = maxRolls;
					else
						numRolls++;
				}
				System.out.println(this.name+" kept the following dice " + this.keptDice); 
				resolveKeptDice(this.keptDice, numGatling);
			}
			
			
		} //end of not 3 dynamites condition
	}//end of method
	
	public void keepDices(int i, int numGatling, ArrayList<String> diceResults) {
		if ((diceResults.get(i)=="B") && keepBeer()) {
			System.out.println(this.currentPlayer.name + " kept the beer." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="S1") && keepShot1()) {
			System.out.println(this.currentPlayer.name + " kept the Bulls' Eye 1." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="S2") && keepShot2()) {
			System.out.println(this.currentPlayer.name + " kept the Bulls' Eye 2." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="A") && keepArrow()) {
			System.out.println(this.currentPlayer.name + " kept the Arrow." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="G") && keepGatling(numGatling)){
			System.out.println(this.currentPlayer.name + " kept the Gatling." );
			this.keptDice.add(diceResults.get(i));
		}
	}
	
	/* 4) _____Dice Interactions______*/
	public void rollDice() {
	//Predict the roles of each player using the Probability Vector associated with that player
		assignOpponents();
	//role is allocated by matching the max probability to the roles
		updateProbabilityVector();
		guessRoles();
		AIDice d = new AIDice();
		this.diceResults = d.rollThemDice(5);
		keepDice(this.diceResults);
	//	trackProbabilityVector();
	}

	public void turn() {
		System.out.println("It is "+this.currentPlayer.name+"'s turn and he/she will now roll the dice.");
	}
	
	public int playersAlive() {
		int total = 0;
		for (int i=0;i<this.startedWith;i++) {
			if (this.playerOrder[i]!=null)
				if (this.playerOrder[i].lifePoints>0)
					total++;
				else if (this.playerOrder[i].lifePoints<=0 && this.playerOrder[i].role!="Sheriff") {
					System.out.println(this.playerOrder[i].name + " has died!");
					System.out.println(this.playerOrder[i].name + " was the " + this.playerOrder[i].role);
					this.playerOrder[i]=null;
				}	
		}
		return total;
	}
	
	public void test() {
		assignOpponents();
	//role is allocated by matching the max probability to the roles
		guessRoles();
	//	getGuessRole();
		for(int i=0;i<this.totalPlayers;i++) {
	//		System.out.println(this.currentPlayer.name + " is at pos: " + this.position + " and " + this.playerOrder[(this.position+2)%this.totalPlayers].name + " is at 2 pos away at " +  (this.position+2)%this.totalPlayers);
			//System.out.println(this.name + " is at pos: " + i + " and " + this.playerOrder[(i+2)].name + " is at pos: " +  (i+2));
			System.out.println("target of " + this.currentPlayer.name + " is " + this.targetRole + 
					" and player " + this.playerOrder[i].name + " is " + this.playerOrder[i].aiGuessRole);
		}
		
	}
	//method to evaluate dice roll
	
}