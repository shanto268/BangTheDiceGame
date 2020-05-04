/*
 * Sadman Ahmed Shanto
 * CS 2365
 */

package bangdicegame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Houses all AI player information and simulates their turns
 */

public class AI {
	//Behavior defining parameters
        /**
         * value equivalent to their willingness to trick other players
         */
	private final double willingToTrick;
        /**
         * value equivalent to their aggressiveness
         */
	private final double Aggressiveness;
        /**
         * value equivalent to their likelihood to play safe
         */
	private final double Safetiness;
        /**
         * value equivalent to their likelihood to be nice
         */
	private final double Niceness;
        /**
         * value equivalent to their likelihood to keep dice
         */
	private final double willingToKeepDice;
        /**
         * value equivalent to their likelihood to keep health
         */
	private final double willingToKeepHealth;
        /**
         * value equivalent to their likelihood to keep shots
         */
	private final double willingToKeepShots;
        /**
         * value equivalent to their likelihood to keep arrows
         */
	private final double willingToKeepArrow;
        /**
         * value equivalent to their likelihood to keep gatling
         */
	private final double willingToKeepGatling;
        /**
         * value equivalent to their likelihood to be a skeptic
         */
	private final double SkepticProbability;
        /**
         * value equivalent to their likelihood to keep normal dice
         */
	private final double willingToKeepNormalDice;
        /**
         * value equivalent to their likelihood to keep coward dice
         */
	private final double willingToKeepCowardDice;
        /**
         * value equivalent to their likelihood to keep broken arrow
         */
	private final double willingToKeepBrokenArrow;
        /**
         * value equivalent to their likelihood to keep a fight roll
         */
	private final double willingToKeepFight;
        /**
         * value equivalent to their likelihood to keep a bullet roll
         */
	private final double willingToKeepBullet;

        /**
         * AI name
         */
	private final String name;
        /**
         * AI role
         */
	private final String role;  
        /**
         * array list of their target roles based on player role
         */
	private ArrayList<String> targetRole;
        /**
         * array list of dice results for each roll
         */
	private ArrayList<String> diceResults;
        /**
         * array list for kept dice after each roll
         */
	private ArrayList<String> keptDice;
        /**
         * the amount of health they have
         */
	private int health;
        /**
         * how much health will be subtracted
         */
	private final int subtractHealth = 5;
        /**
         * threshold health for decision making
         */
	private final int thresholdHealth; 
        /**
         * position in player order array
         */
	private int position;
        /**
         * total number of players
         */
	private final int totalPlayers;
        /**
         * total number of players alive
         */
	private int playersAlive;
        /**
         * max amount of dice rerolls
         */
	private int maxRolls = 3;
        /**
         * number of players started with
         */
	private int startedWith;
        /**
         * gameFunctions object
         */
        private GameFunctions game;

    /**
     *  array of current player order
     */
    public Character [] playerOrder;

    /**
     *  current player whose turn it is
     */
    public Character currentPlayer;

    /**
     * arrowPile object
     */
    public ArrowPile arrowPile;

    /**
     *  number of arrows remaining in pile
     */
    public int remainingArrows;
	
//=================  Constructor =====================================

    /**
     * constructor for AI
     *
     * @param game gameFunctions object
     * @param i position in array
     * @param arrowPile arrowPile object
     */
    @SuppressWarnings("unchecked")
    public AI(GameFunctions game, int i, ArrowPile arrowPile) {
        this.SkepticProbability = getProbability(0.0,0.4);
        this.Aggressiveness = getProbability(0.0,0.4);
        this.Safetiness = getProbability(0.0,0.4);
        this.Niceness  = getProbability(0.0,0.5);
        
        this.willingToKeepCowardDice =  getProbability(0.0,0.1);
        this.willingToTrick = getProbability(0.0,0.2);
        this.willingToKeepNormalDice = getProbability(0.6,1.0);
        this.willingToKeepDice  = getProbability(0.0,1.0);
        this.willingToKeepHealth  = getProbability(0.5,1.0);
        this.willingToKeepArrow = getProbability(0.0,1.0);
        this.willingToKeepGatling = getProbability(0.3,0.7);
        this.willingToKeepShots = getProbability(0.5,1.0);
        this.willingToKeepFight = getProbability(0.0,0.4);
        this.willingToKeepBrokenArrow = getProbability(0.5,1.0);
        this.willingToKeepBullet = getProbability(0.5,1.0);
        
        this.startedWith = game.numOfPlayers;
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

    /**
     * outputs player name and current position
     */
    public void getPlayer() {
        System.out.println("AI " + this.position + " is " + this.currentPlayer);
    }
	
    /**
     * outputs player name
     */
    public void getPlayerName() {
            System.out.println("AI " + this.position + " is " + this.currentPlayer.name);
	}
	
    /**
     * outputs player health
     */
    public void getPlayerHealth() {
            System.out.println("AI " + this.position + " is " + this.currentPlayer.lifePoints);
	}
	
    /**
     * outputs player role
     */
    public void getPlayerRole() {
            System.out.println("AI " + this.position + " is " + this.currentPlayer.role);
	}
	
    /**
     * outputs willingness for different tasks based on probability
     */
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

	}

    /**
     *  outputs probability vector values
     */
    public void trackProbabilityVector() {
            for (int i=0;i<this.totalPlayers;i++) {
		System.out.println(this.playerOrder[i].name + " P Vector is " + this.playerOrder[i].ProbabilityVector);
            }
	}
	
    /**
     *  outputs proabilty vector values
     */
    public void getPlayerProbabilityVector() {
            System.out.println("AI " + this.role + " P(vector) " + this.currentPlayer.ProbabilityVector);
	}
	
    /**
     *  outputs guessed role of AI player
     */
    public void getGuessRole(){
            for (int i=0;i<this.totalPlayers;i++){
                if ( i!=this.position ) { 
                    System.out.println("guessed role of " + this.playerOrder[i].role + " is " + this.playerOrder[i].aiGuessRole);
		}
            }
	}
	
	/*Probability Generator*/

    /**
     * determines probability with min and max
     *
     * @param minnum minimum number for probability
     * @param maxnum maximum number for probability
     * @return probability
     */

	public double getProbability(double minnum, double maxnum) {
	    Random rand = new Random();
	    return rand.nextFloat() * (maxnum - minnum) + minnum;
	}
	
	/* 1) _____Method to track which players shot sheriff______*/
	//returns array of position of players that shot sheriff

    /**
     * calculates who has shot the sheriff
     *
     * @return positions of sheriff shooters
     */
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

    /**
     * determines who has helped the sheriff
     *
     * @return the positions of sheriff helpers
     */
	public ArrayList<Integer> SheriffHelpers(){
            ArrayList<Integer> positionOfSheriffHelpers = new ArrayList<>();
            for (int i=0;i<this.totalPlayers;i++) {
		if ((this.playerOrder[i].numHelpSheriff > 0) && i!=this.position)
                    positionOfSheriffHelpers.add(i);
            }
            return positionOfSheriffHelpers;
	}
	
    /**
     * determines if everyone is alive
     *
     * @return true if everyone is alive
     */
    public boolean EveryoneIsAlive() {
            int i;
            int temp = 0;
                
            for (i = 0; i < this.startedWith; i++){
                if (!this.playerOrder[i].isDead){
                    temp++;
                }
            }
            
            this.playersAlive = temp;
            if (this.playersAlive==this.startedWith) 
		return true;
            else
		return false;
	}
	
	/* 3) _____Method to update Probability vector______*/
	//vector format: [S,R,O,D]

    /**
     *  updates all of the probability vectors as the game progresses 
     */
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
	
    /**
     *  assigns opponents for each AI based on their starting role
     */
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
	
    /**
     *  guesses the role of an AI player
     */
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
	
    /**
     * determines if an AI will keep a beer roll
     *
     * @return true if beer is being kept
     */
    public boolean keepBeer() {
		if (this.currentPlayer.lifePoints < this.thresholdHealth)
			return true;
		else if (Math.random() <= this.willingToKeepHealth)
			return true;
		else
			return false;
	}
	
    /**
     * determines if an AI will keep a bull's eye 1
     *
     * @return true if bull's eye 1 will be kept
     */
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
	
    /**
     * determines if an AI will keep a bull's eye 2
     *
     * @return true if bull's eye 2 will be kept
     */
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

    /**
     * determines if an AI will keep a gatling roll
     *
     * @param numGatling number of gatlings rolled so far
     * @return true if gatling will be kept
     */
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
	
    /**
     * determines if AI will keep an arrow roll
     *
     * @return true if arrow will be kept
     */
    public boolean keepArrow() {
		//arrow-> keep with willing probability
		if (Math.random() <= this.willingToKeepArrow)
			return true;
		else
			return false;
	}
	
    /**
     * resolves beer rolls depending on AI roll, their enemies, and the guessed roles of other players
     */
    public void resolveBeers() { //cannot give beer if max life 
		boolean sheriffHelper = false;
		boolean sheriffShooter = false;
		boolean helped = false;
		
                //if statements to decide on who to help based on interactions with sheriffs
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
		
		//all possible options for who the AI will serve the beer to
		if (sheriffShooter && !helped) {
			if (this.currentPlayer.role=="Outlaw") {
				//help who shot the sheriff if role=outlaw
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i].numShotSheriff>0 && !this.playerOrder[i].isDead) {
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
							if (this.playerOrder[i].role=="Sheriff" && !this.playerOrder[i].isDead) {
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
							if (this.playerOrder[i].numShotSheriff>0 && !this.playerOrder[i].isDead) {
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
                                while (!helped){
                                    int maxi = 0;
                                    for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i] != null)
                                            maxi++;
                                    }
                                    int rand = (int)(Math.random()*(maxi));
                                    if (!this.playerOrder[rand].isDead){
                                        if (this.playerOrder[rand].role=="Sheriff")
                                            this.currentPlayer.numHelpSheriff++;
            				this.playerOrder[rand].gain_life();
                			System.out.println(this.name + " gave the beer to " + this.playerOrder[rand].name);
                        		helped = true;
                                	return;
                                    }
                                }
                }
              
		if (sheriffHelper && !helped) {
			 if (this.currentPlayer.role=="Sheriff") {
				//help who gave you health if role=sheriff 
				for (int i=0;i<this.totalPlayers;i++) {
					if (this.playerOrder[i].numHelpSheriff>0 && !this.playerOrder[i].isDead) {
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
			while (!helped){
                            int maxi = 0;
                            for (int i=0;i<this.totalPlayers;i++) {
                                if (this.playerOrder[i] != null)
                                    maxi++;
                            }
                            int rand = (int)(Math.random()*(maxi));
                            if (!this.playerOrder[rand].isDead){
                                if (this.playerOrder[rand].role=="Sheriff")
                                    this.currentPlayer.numHelpSheriff++;
                                this.playerOrder[rand].gain_life();
                		System.out.println(this.name + " gave the beer to " + this.playerOrder[rand].name);
                        	helped = true;
                                return;
                            }
                        }
                }
		
		if (this.currentPlayer.role=="Deputy" && !helped) {
			//help sheriff is role=deputy
			for (int i=0;i<this.totalPlayers;i++) {
				if (this.playerOrder[i].role=="Sheriff" && !this.playerOrder[i].isDead) {
					this.playerOrder[i].gain_life();
					this.currentPlayer.numHelpSheriff++;
					System.out.println(this.name + " gave the beer to " + this.playerOrder[i].name);
					helped = true;
					return;
				}
			}
		}
		
		
		
	}
	
    /**
     *  shoots randomly when a bull's eye 1 is rolled
     */
    public void shootRandomly1() {
		Random r = new Random(); //random int
		int chance = r.nextInt(2); //0 or 1
                //makes chosen player lose a life point based on random selection
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
	
    /**
     * shoots randomly when a bull's eye 2 is rolled
     */
    public void shootRandomly2() {
		Random r = new Random(); //random int
		int chance = r.nextInt(2); //0 or 1
                //makes chosen player lose a life point based on random selection
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
	
    /**
     * resolves all of the kept dice after rerolling is finished
     *
     * @param keptDice array list of kept dice
     * @param numGatling number of gatlings rolled
     */
    public void resolveKeptDice(ArrayList<String> keptDice,int numGatling){
                int diceToRoll = 5;
                if (this.game.get_current_player().role == "Zombie"){
                    diceToRoll = 3;
                }
		System.out.println("Resolving the " + diceToRoll + " kept dices!");
		//Gatlings
                numGatling = 0;
                for (int i=0; i < keptDice.size(); i++){
                    if (keptDice.get(i)=="G"){
                        numGatling++;
                    }
                    
                    else if (keptDice.get(i)=="DG"){
                        numGatling = numGatling + 2;
                    }
                    
                }
		for(int i=0;i<keptDice.size();i++) {
			if (keptDice.get(i)=="W" && !this.game.game_over) {
				resolveWhiskey();
			}
			else if (keptDice.get(i)=="S1" && !this.game.game_over) {
				resolveShot1();
			}
			else if (keptDice.get(i)=="S2" && !this.game.game_over) {
				resolveShot2();
			}	
			
			else if (keptDice.get(i)=="DB1" && !this.game.game_over) {
				resolveDoubleShot1();
			}
			else if (keptDice.get(i)=="DB2" && !this.game.game_over) {
				resolveDoubleShot2();
			}
			
			else if (keptDice.get(i)=="B" && !this.game.game_over) {
				resolveBeers();
			}

			else if (keptDice.get(i)=="DBr" && !this.game.game_over) {
				resolveDoubleBeer();
			}
			
			else if (keptDice.get(i)=="G" && !this.game.game_over) {
				resolveGatling(numGatling); 
			}
			
			else if (keptDice.get(i)=="DG" && !this.game.game_over) {
				resolveGatling(numGatling); 
			}
			
			else if (keptDice.get(i)=="F" && !this.game.game_over) {
				resolveFight();
			}
		
		}
                 
	}
	
    /**
     *  resolves bull's eye 2 rolls
     */
    public void resolveShot2() {
		int toLeft = Math.floorMod(this.position-2, this.totalPlayers);
		int toRight = Math.floorMod(this.position+2, this.totalPlayers);
		
                //if the AI has a target, shoots them
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
                //otherwise shoots randomly
		else 
			shootRandomly2();
	}
	
    /**
     * resolves bull's eye 1 rolls
     */
    public void resolveShot1() {		
		int toLeft = Math.floorMod(this.position-1, this.totalPlayers);
		int toRight = Math.floorMod(this.position+1, this.totalPlayers);
		
                //if the AI has a target, shoots them
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
                //otherwise shoots randomly
		else 
			shootRandomly1();
	}
		
    /**
     * resolves gatling dice if they have rolled 3 or more
     *
     * @param numGatling number of gatlings rolled
     */
    public void resolveGatling(int numGatling) {
		if(numGatling>= 3) {
			for(int i=0;i<this.totalPlayers;i++) {
                            if(i!=this.position && !this.playerOrder[i].isDead){
				this.playerOrder[i].lose_life(this.game, this.arrowPile, false);
                            }
                            else if (i == this.position){
                                this.arrowPile.remaining = this.arrowPile.remaining + this.playerOrder[i].arrows;
				this.playerOrder[i].arrows=0;
                            }
			}
		}
	}
	
	//decision to keep dice -> "A", "D", "S1", "S2", "B", "G"

    /**
     * determines if dice will be kept and what actions needs to be taken
     *
     * @param diceResults arraylist of results of dice from being rolled
     */
	public void keepDice(ArrayList<String> diceResults) {
		this.keptDice = new ArrayList<String>();
		int maxRolls = this.maxRolls; 
		int numDynamites = 0;
		int numGatling = 0;
                int diceToRoll = 5;
                if (this.game.get_current_player().role == "Zombie"){
                    diceToRoll = 3;
                }
		System.out.println("Dices rolled: " + diceResults);
		
                if (!game.game_over && !game.get_current_player().isDead){
                    for(int i=0;i<diceResults.size();i++){
			//resolve all arrows
			if (diceResults.get(i)=="A" && !this.game.game_over) {
                            if (game.get_current_player().arrows >= 3 && this.arrowPile.chiefArrow != 0){
                                System.out.println(this.name + "rolled an arrow and has taken the chief's arrow.");
                                game.get_current_player().gain_arrow();
                                game.get_current_player().gain_arrow();
                                game.get_current_player().chiefArrow = true;
                                this.arrowPile.chiefArrow = 0;
                            }
                            else{
                                System.out.println(this.name + " rolled an arrow. " + this.name + " must pick up an arrow before continuing.");
                                this.arrowPile.remove_arrow(this.game);
                                System.out.println(this.name + " has " + this.currentPlayer.arrows + " arrow(s).");
                                System.out.println("ArrowPile has " + this.arrowPile.remaining + " remaining.");
                            }
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
						this.currentPlayer.gain_life();
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
			if (this.keptDice.size()==diceToRoll) {
                            //resolve the dices
                            resolveKeptDice(this.keptDice, numGatling);
			}
			else {
				while (numRolls!=maxRolls) {
                                    int diceLeft = diceToRoll-this.keptDice.size();
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
                                    if ( (this.keptDice.size() == diceToRoll))
					numRolls = maxRolls;
                                    else
                                        numRolls++;
                                    }
                                System.out.println(this.name+" kept the following dice " + this.keptDice); 
                                resolveKeptDice(this.keptDice, numGatling);
                            }      
          		}//end of not 3 dynamites condition
                }
	}//end of method
	
    /**
     * determines if dice will be kept and what actions needs to be taken
     *
     * @param i position of dice
     * @param numGatling  number of gatlings rolled
     * @param diceResults array of dice results
     */
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
		
		//new dice faces
		// Bt, DB1, DB2, DG -> loud mouth
		// DBr, BA -> coward
		// W, F -> duel
		
		else if ((diceResults.get(i)=="Bt") && keepBullet()) {
			System.out.println(this.currentPlayer.name + " kept the Bullet." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="DB1") && keepDoubleShot1()) {
			System.out.println(this.currentPlayer.name + " kept the Double Bulls' Eye 1." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="DB2") && keepDoubleShot2()) {
			System.out.println(this.currentPlayer.name + " kept the Double Bulls' Eye 2." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="DG") && keepDoubleGatling(numGatling)){
			System.out.println(this.currentPlayer.name + " kept the Double Gatling." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="DBr") && keepDoubleBeer()) {
			System.out.println(this.currentPlayer.name + " kept the Double Beer." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="BA") && keepBrokenArrow()) {
			System.out.println(this.currentPlayer.name + " kept the Broken Arrow." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="W") && keepWhiskey()) {
			System.out.println(this.currentPlayer.name + " kept the Whiskey." );
			this.keptDice.add(diceResults.get(i));
		}
		else if ((diceResults.get(i)=="G") && keepFight()){
			System.out.println(this.currentPlayer.name + " kept the Duel(Fight)." );
			this.keptDice.add(diceResults.get(i));
		}

	}
	
    /**
     * determines if AI will keep bullet roll
     *
     * @return true if bullet dice will be kept
     */
    public boolean keepBullet() {
		if (Math.random() <= this.willingToKeepBullet)
			return true;
		else
			return false;
	}
	
    /**
     * determines if AI will keep double shot 1
     *
     * @return true if double shot 1 will be kept
     */
    public boolean keepDoubleShot1() {
		return keepShot1();
	}
	
    /**
     * determines if AI will keep double shot 2
     *
     * @return true if double shot 2 will be kept
     */
    public boolean keepDoubleShot2() {
		return keepShot2();
	}
	
    /**
     * determines if AI will keep double gatling
     *
     * @param numGatling number of gatlings rolled so far
     * @return true if double gatling will be kept
     */
    public boolean keepDoubleGatling(int numGatling) {
		return keepGatling(numGatling);
	}
	
    /**
     * determines if AI will keep double beer
     *
     * @return true if double beer will be kept
     */
    public boolean keepDoubleBeer() {
		return keepBeer();
	}
	
    /**
     * determines if AI will keep broken arrow
     *
     * @return true if broken arrow will be kept
     */
    public boolean keepBrokenArrow() {
		if (Math.random() <= this.willingToKeepBrokenArrow)
			return true;
		else
			return false;
	}
	
    /**
     * determines if AI will keep whiskey roll
     *
     * @return true if whiskey will be kept
     */
    public boolean keepWhiskey() {
		Random r = new Random();
		int rlife = r.nextInt(3); //0,1,2
		if (this.currentPlayer.lifePoints < this.thresholdHealth+rlife)
			return true;
		else
			return false;
	}
	
    /**
     * determines if AI will keep fight roll
     *
     * @return true if fight will be kept
     */
    public boolean keepFight() {
		if (Math.random() <= this.willingToKeepFight)
			return true;
		else
			return false;
	}

    /**
     * resolves the double bulls eye 1 by shooting randomly
     */
    public void DoubleShootRandomly1() {
		Random r = new Random();
		int chance = r.nextInt(2); //0 or 1
		if (chance == 1) {
                        int j = Math.floorMod(this.position-1, this.totalPlayers);
			if (this.playerOrder[j].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_next_player().name + " who is one position"
					+ " to the right." );
                        this.game.get_next_player().lose_life(game, arrowPile, false);
                        this.game.get_next_player().lose_life(game, arrowPile, false);
		}
		else {
			int j = Math.floorMod(this.position-1, this.totalPlayers);
			if (this.playerOrder[j].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_previous_player().name + " who is one position"
					+ " to the left." );
                        this.game.get_previous_player().lose_life(game, arrowPile, false);
                        this.game.get_previous_player().lose_life(game, arrowPile, false);
		}
	}
	
    /**
     *  resolves the double bulls eye 2 by shooting randomly
     */
    public void DoubleShootRandomly2() {
		Random r = new Random();
		int chance = r.nextInt(2); //0 or 1
		if (chance == 1) {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_away_player(this.game.get_current_player()).name + " who is two positions"
					+ " to the right." );
                        this.game.get_two_away_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
                        this.game.get_two_away_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
		else {
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_before_player(this.game.get_current_player()).name + " who is two positions"
					+ " to the left." );
                        this.game.get_two_before_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
                        this.game.get_two_before_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
	}
	
    /**
     *  resolves a bullet when rolled
     */
    public void resolveBullet() {
		System.out.println(this.name + " rolled a Bullet. " + this.name + " lost one life point.");
		this.currentPlayer.lose_life(this.game, this.arrowPile, true);
	}
	
    /**
     *  resolves a double bulls eye 1
     */
    public void resolveDoubleShot1() {
		int toLeft = Math.floorMod(this.position-1, this.totalPlayers);
		int toRight = Math.floorMod(this.position+1, this.totalPlayers);
		
                //if player has a target, shoot them
		if (this.targetRole.contains(this.playerOrder[toLeft].aiGuessRole)) {
			if (this.playerOrder[toLeft].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_previous_player().name + " who is one position"
					+ " to the left." );
                        this.game.get_previous_player().lose_life(game, arrowPile, false);
                        this.game.get_previous_player().lose_life(game, arrowPile, false);
		}
		else if (this.targetRole.contains(this.playerOrder[toRight].aiGuessRole) ) {
			if (this.playerOrder[toRight].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_next_player().name + " who is one position"
					+ " to the right." );
                        this.game.get_next_player().lose_life(game, arrowPile, false);
                        this.game.get_next_player().lose_life(game, arrowPile, false);
		}
                //otherwise shoots randomly
		else 
			DoubleShootRandomly1();
	}
	
    /**
     *  resolves a double bulls eye 2
     */
    public void resolveDoubleShot2() {
		int toLeft = Math.floorMod(this.position-2, this.totalPlayers);
		int toRight = Math.floorMod(this.position+2, this.totalPlayers);
		
                //if player has a target, shoots them
		if (this.targetRole.contains(this.playerOrder[toLeft].aiGuessRole)) {
			if (this.playerOrder[toLeft].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_before_player(this.game.get_current_player()).name + " who is two position"
					+ " to the left." );
                        this.game.get_two_before_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
                        this.game.get_two_before_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
		else if (this.targetRole.contains(this.playerOrder[toRight].aiGuessRole) ) {
			if (this.playerOrder[toRight].aiGuessRole=="Sheriff")
				this.currentPlayer.numShotSheriff++;
			System.out.println(this.currentPlayer.name + " shot " + this.game.get_two_away_player(this.game.get_current_player()).name + " who is two position"
					+ " to the right." );
                        this.game.get_two_away_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
                        this.game.get_two_away_player(this.game.get_current_player()).lose_life(game, arrowPile, false);
		}
                //otherwise shoots randomly
		else 
			DoubleShootRandomly2();
	}
	
    /**
     *  resolves double beer roll
     */
    public void resolveDoubleBeer() { //random
		resolveBeers();
		resolveBeers();
	}
	
    /**
     * resolves a broken arrow roll
     */
    public void resolveBrokenArrow() {  
		Random r = new Random();
		int pplwithArrows = 0;
		int rArrows = r.nextInt(4); //0,1,2,3
		int rIndex;
		//removes arrow from itself
		if (this.currentPlayer.arrows >= rArrows) {
			this.arrowPile.add_arrow(this.currentPlayer);
			System.out.println(this.name + " removed an arrow!" );
			return;
		}
		else {
			//randomly remove an arrow from someone who has at least one arrow
			for (int i=0;i<this.totalPlayers;i++) {
				if (this.playerOrder[i].arrows>0 && !this.playerOrder[i].isDead)
					pplwithArrows++;
			}
			rIndex = r.nextInt(pplwithArrows);
			this.arrowPile.add_arrow(this.playerOrder[rIndex]);
			System.out.println(this.name + " removed an arrow from " + this.playerOrder[rIndex].name);
			return;
		}
	}

    /**
     *  resolves a whiskey roll
     */
    public void resolveWhiskey() {
		this.currentPlayer.gain_life();
		System.out.println(this.name + " drank the whiskey!" );
	}
	
    /**
     *  resolves a fight roll
     */
    public void resolveFight() {
		int pplAlive = 0;
		Random r = new Random();
		int rIndex = 0;
		
		//randomly chooses opponent
		for (int i=0;i<this.totalPlayers;i++) {
			if (!this.playerOrder[i].isDead)
				pplAlive++;
		}
		
		while (rIndex == this.position)
			rIndex = r.nextInt(pplAlive);
		//starts duel with opponent
		System.out.println(this.currentPlayer.name + " chose to duel with " + this.playerOrder[rIndex].name);
		duel(this.currentPlayer, this.playerOrder[rIndex]);
	}
	
    /**
     * initiates a duel between two players
     *
     * @param self current player
     * @param opponent player chosen to duel
     */
    public void duel(Character self, Character opponent) {
		AIDice oppoDice = new AIDice();
		//opponent rolls the duel dice
		//if not fight
		if (oppoDice.rollDuelDice() != "F") {
			System.out.println(opponent.name + " did not roll Fight and hence lost the duel.");
			opponent.lose_life(this.game, this.arrowPile, false);
			return; //exit condition
		}
		//opponent.health--
		//if fight
		//self toss
		else if (oppoDice.rollDuelDice() == "F") {
			System.out.println("Since, " + opponent.name + " rolled Fight. It is " + self.name + "'s turn to roll the dice.");
			duel(opponent, self);
		}
	}	
	
	/* 4) _____Dice Interactions______*/

    /**
     * lets the AI player roll the dice
     */

	public void rollDice() {
		assignOpponents();
		updateProbabilityVector();
		guessRoles();
                int diceToRoll = 5;
                
                //only rolls 3 dice if a zombie
                if (this.game.get_current_player().role == "Zombie"){
                    diceToRoll = 3;
                }
		if (!this.game.expansions) {
			AIDice d = new AIDice();
			//System.out.println(this.currentPlayer.name+ " has decided to not use either of the Coward's or Loudmouth's dice");
			this.diceResults = d.rollThemDice(diceToRoll);
			keepDice(this.diceResults);
			return;
		}
		else {
			AIDice d = new AIDice();
			if (Math.random() <= this.willingToKeepNormalDice) {
				System.out.println(this.currentPlayer.name+ " has decided to use the regular dies");
				this.diceResults = d.rollDiceExpansion(diceToRoll, 'R');
				keepDiceExpansion(this.diceResults, 'R');
				return;
			}
			
			else if (Math.random() <= this.willingToKeepCowardDice) {
				System.out.println(this.currentPlayer.name+ " has decided to use the Coward's dice");
				this.diceResults = d.rollDiceExpansion(diceToRoll, 'C');
				keepDiceExpansion(this.diceResults, 'C');
				return;
			}
			else {
				System.out.println(this.currentPlayer.name+ " has decided to use the Loudmouth's dice");
				this.diceResults = d.rollDiceExpansion(diceToRoll, 'L');
				keepDiceExpansion(this.diceResults, 'L');
				return;
			}
		}
	//	trackProbabilityVector();
	}
	
	//keepDiceExpansion
		//make sure order is correct
		//same structure as keepDice with new additions
		//re-shuffling correct die
		//numGatling number
	
    /**
     * determines the type of the dice kept
     *
     * @param keptDice arraylist of kept dice
     * @param DiceType char to represent dice type
     * @param diceLeft dice left to work with
     * @return array list of dice to roll
     */
    public ArrayList<Integer> determineDiceType(ArrayList<String> keptDice, char DiceType, int diceLeft){
		ArrayList<Integer> numDiceToRoll = new ArrayList<Integer>();
		int numKeptDice = keptDice.size();
		int numR = 0;
		int numD = 0;
		int numL = 0;
		int numC = 0;
		int counter = 0;
        boolean isWonky =false;
		Random r = new Random();
        		
		if (DiceType == 'R') {
			//guess numD
			if (keptDice.contains("W") || keptDice.contains("F"))
				numD = 1;
			if (keptDice.contains("W") && keptDice.contains("F"))
				numD = 2;
			else
				numD = 0;
			
			numR = numKeptDice - numD;
			numDiceToRoll.add(0, 3-numR);
			numDiceToRoll.add(1, 0);
			numDiceToRoll.add(2, 2-numD);	
			
	        for(int s = 0; s < numDiceToRoll.size(); s++){
	            if(numDiceToRoll.get(s) < 0)
		          	isWonky = true; //flag variable
	         }
	        //if wonky
	        if (isWonky) {
	        	numD = 0;
	        	numR = 0;
	        	numDiceToRoll.clear();
	        	
		        //number of dice left
		        //randomly generate that number of rolls
	        	while (counter!=diceLeft) {
		    		int die = r.nextInt(2); //0 or 1	
		    		if (die==0)
		    			numD++;
		    		else
		    			numR++;
		    		counter++;
	        	}
				numDiceToRoll.add(0, numR);
				numDiceToRoll.add(1, 0);
				numDiceToRoll.add(2, numD);	
	        }

	        

		}
		else if (DiceType == 'L') {	
			//guess numD
			if (keptDice.contains("W") || keptDice.contains("F"))
				numD = 1;
			if (keptDice.contains("W") && keptDice.contains("F"))
				numD = 2;
			else
				numD = 0;
			//guess numL
			if (keptDice.contains("DB1") || keptDice.contains("Bt") || keptDice.contains("DB2") || keptDice.contains("DG"))
				numL=1;
			else
				numL=0;
			
			numR = numKeptDice - numD - numL;
			numDiceToRoll.add(0, 2-numR);
			numDiceToRoll.add(1, 1 - numL);
			numDiceToRoll.add(2, 2-numD);
			
			
	        for(int s = 0; s < numDiceToRoll.size(); s++){
	            if(numDiceToRoll.get(s) < 0)
		          	isWonky = true; //flag variable
	         }
	      //if wonky
	        if (isWonky) {
	        	numD = 0;
	        	numR = 0;
	        	numL=0;
	        	numDiceToRoll.clear();
	        	
		        //number of dice left
		        //randomly generate that number of rolls
	        	while (counter!=diceLeft) {
		    		int die = r.nextInt(3); //0 or 1 or 2	
		    		
		    		if (die==0)
		    			numD++;
		    		else if (die==1)
		    			numR++;
		    		else
		    			numL++;
		    		counter++;
	        	}
				numDiceToRoll.add(0, numR);
				numDiceToRoll.add(1, numL);
				numDiceToRoll.add(2, numD);	
	        }
			
		}
		
		else if (DiceType == 'C') {
			//guess numD
			if (keptDice.contains("W") || keptDice.contains("F"))
				numD = 1;
			if (keptDice.contains("W") && keptDice.contains("F"))
				numD = 2;
			else
				numD = 0;
			//guess numC
			if (keptDice.contains("DBr") || keptDice.contains("BA"))
				numC = 1;
			else
				numC = 0;
			
			numR = numKeptDice - numD - numC;
			numDiceToRoll.add(0, 2-numR);
			numDiceToRoll.add(1, 1 - numC);
			numDiceToRoll.add(2, 2-numD);
			
			
	        for(int s = 0; s < numDiceToRoll.size(); s++){
	            if(numDiceToRoll.get(s) < 0)
		          	isWonky = true; //flag variable
	         }
	        if (isWonky) {
	        	numD = 0;
	        	numR = 0;
	        	numC=0;
	        	numDiceToRoll.clear();
	        	
		        //number of dice left
		        //randomly generate that number of rolls
	        	while (counter!=diceLeft) {
		    		int die = r.nextInt(3); //0 or 1 or 2	
		    		
		    		if (die==0)
		    			numD++;
		    		else if (die==1)
		    			numR++;
		    		else
		    			numC++;
		    		counter++;
	        	}	        	
				numDiceToRoll.add(0, numR);
				numDiceToRoll.add(1, numC);
				numDiceToRoll.add(2, numD);	
	        }
		}
		
		return numDiceToRoll;
	}
	
    /**
     * allows rerolls of dice
     *
     * @param keptDice arraylist of dice that are kept
     * @param diceUsed type of dice used
     * @param numNeeded number needed
     * @return results of dice rolled
     */
    public ArrayList<String> reRoll(ArrayList<String> keptDice, char diceUsed, int numNeeded){
		ArrayList<String> results = new ArrayList<String>();
		int numD = 0;

		AIDice dice = new AIDice();
		
		//determine how many times Duel was used
		if (keptDice.contains("W") || keptDice.contains("F"))
			numD = 1;
		if (keptDice.contains("W") && keptDice.contains("F"))
			numD = 2;
		else
			numD = 0;
		
		if (diceUsed == 'R') {
			results.addAll(dice.generateRolls(numNeeded, diceUsed, false, numD));
		}
		
		else if (diceUsed == 'L') {
			if (keptDice.contains("DB1") || keptDice.contains("Bt") || keptDice.contains("DB2") || keptDice.contains("DG"))
				results.addAll(dice.generateRolls(numNeeded, diceUsed, true, numD));
			else
				results.addAll(dice.generateRolls(numNeeded, diceUsed, false, numD));
		}
		
		else if (diceUsed == 'C') {
			if (keptDice.contains("DBr") || keptDice.contains("BA"))
				results.addAll(dice.generateRolls(numNeeded, diceUsed, true, numD));
			else
				results.addAll(dice.generateRolls(numNeeded, diceUsed, false, numD));
		}
		
		return results;
	}
	
    /**
     * determines what dice will be kept from expansion
     *
     * @param diceResults arraylist of dice results
     * @param diceUsed type of dice used
     */
    public void keepDiceExpansion(ArrayList<String> diceResults, char diceUsed) {
		this.keptDice = new ArrayList<String>();
		ArrayList<Integer> diceChoice = new ArrayList<Integer>();
		int maxRolls = this.maxRolls; 
		int numDynamites = 0;
		int numGatling = 0;
                int diceToRoll = 5;
                if (this.game.get_current_player().role == "Zombie"){
                    diceToRoll = 3;
                }
		System.out.println("Dices rolled: " + diceResults);
		//Resolving order
		//Arrows, Bullet, Dynamite, Others
                if (!game.game_over && !game.get_current_player().isDead){
                    for(int i=0;i<diceResults.size();i++){
			//resolve all arrows
			if (diceResults.get(i)=="A" && !this.game.game_over) {
                            //NEW CODE
                            if (game.get_current_player().arrows >= 3 && this.arrowPile.chiefArrow != 0){
                                System.out.println(this.name + "rolled an arrow and has taken the chief's arrow.");
                                game.get_current_player().gain_arrow();
                                game.get_current_player().gain_arrow();
                                game.get_current_player().chiefArrow = true;
                                this.arrowPile.chiefArrow = 0;
                            }
                            //END OF NEW CODE
                            else{
                                System.out.println(this.name + " rolled an arrow. " + this.name + " must pick up an arrow before continuing.");
                                this.arrowPile.remove_arrow(this.game);
                                System.out.println(this.name + " has " + this.currentPlayer.arrows + " arrow(s).");
                                System.out.println("ArrowPile has " + this.arrowPile.remaining + " remaining.");
                            }
			}
			
            //Broken Arrow                            
			if (diceResults.get(i)=="BA" && !this.game.game_over) {
            	System.out.println(this.name + "rolled a Broken Arrow and must resolve it.");
            	resolveBrokenArrow();
            }
			
			else if(diceResults.get(i)=="Bt" && !this.game.game_over) {
				resolveBullet();
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
			
			else if (diceResults.get(i)=="DG" && !this.game.game_over) {
				numGatling = numGatling + 2;
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
						this.currentPlayer.gain_life();
						System.out.println(this.name + " drank the beer!");
					}
					else {
						resolveBeers();
					}
				}
				
				else if(diceResults.get(i)=="W" && !this.game.game_over) {
					resolveWhiskey();
				}
				
				else if(diceResults.get(i)=="S1"  && !this.game.game_over) {
					resolveShot1();
				}
				else if(diceResults.get(i)=="S2" && !this.game.game_over) {
					resolveShot2();
				}
				
				else if(diceResults.get(i)=="DB1" && !this.game.game_over) {
					resolveDoubleShot1();
				}
				
				else if(diceResults.get(i)=="DB2" && !this.game.game_over) {
					resolveDoubleShot2();
				}
				
				else if(diceResults.get(i)=="DBr" && !this.game.game_over) {
					resolveDoubleBeer();
				}
				
				else if(diceResults.get(i)=="F" && !this.game.game_over) {
					resolveFight();
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
			if (this.keptDice.size()==diceToRoll) {
                            //resolve the dices
                            resolveKeptDice(this.keptDice, numGatling);
			}
			else {
				while (numRolls!=maxRolls) {
		
									//determine how many dice needs to be re rolled
                                    int diceLeft = diceToRoll-this.keptDice.size();
                                    int regular = 0;
                                    int saloon = 0;
                                    int duel = 0;
                                    AIDice d2 = new AIDice();
                                    ArrayList<String> newDice =  new ArrayList<String>();//summation of all dices
                                    
                                    System.out.println(this.currentPlayer.name + " re-rolled " + diceLeft + " dice(s).");
                                    newDice.addAll(reRoll(this.keptDice, diceUsed, diceLeft));
                                    
                                    System.out.println("Newly rolled dices " + newDice);
                                                                    
                                    //update number of Gatling
                                    for(int i=0;i<newDice.size();i++) {
					if (newDice.get(i)=="G") {
						numGatling++;
					}
					else if (newDice.get(i)=="DG") {
						numGatling = numGatling+2;
					}
                                    }
                                    //keep using keepDices and add to keptDice
                                    for(int i=0;i<newDice.size();i++) {
					keepDices(i, numGatling, newDice);
                                    }
                                    System.out.println(this.currentPlayer.name + " rolled " + (numRolls+1) + " times this round!");
                                    if ( (this.keptDice.size() == diceToRoll))
					numRolls = maxRolls;
                                    else
                                        numRolls++;
                                    } //end of while loop
                                System.out.println(this.name+" kept the following dice " + this.keptDice); 
                                resolveKeptDice(this.keptDice, numGatling);
                            }      
          		}//end of not 3 dynamites condition
                }
	}//end of method

    /**
     *  outputs player name of current turn
     */
    public void turn() {
		System.out.println("It is "+this.currentPlayer.name+"'s turn and he/she will now roll the dice.");
	}
	
    /**
     * testing certain AI functions
     */
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