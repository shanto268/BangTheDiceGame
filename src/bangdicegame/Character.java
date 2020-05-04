/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Builds each character with their attributes, and takes care of character based actions
 */


/*
 *Changes made by SAS:
 *included isAi boolean in attributes and constructor
 *included shotSheriff
 *included helpedSheriff
 *gave each player a ProbabilityVector attribute (arraylist)
 */

public class Character {

    /**
     *  how many life points the character has
     */
    public int lifePoints,

    /**
     *  how many arrows the character has
     */
    arrows,

    /**
     *  the maximum amount of life the character can have
     */
    maxLife,

    /**
     *  the amount of times the player shot the sheriff intentionally (not gatling)
     */
    numShotSheriff,

    /**
     *  the amount of times the player healed the sheriff
     */
    numHelpSheriff;

    /**
     *  the characters name
     */
    public String name,

    /**
     *  the characters role
     */
    role,

    /**
     *  the role that the AI has guessed, based on actions against sheriff and probability
     */
    aiGuessRole;

    /**
     *  true if AI, false if real player
     */
    public boolean isAi,

    /**
     *  true if the player has the chief arrow, false if not
     */
    chiefArrow,

    /**
     * true if dead, false otherwise
     */
    isDead;

    /**
     * probability vector used in determining AI actions
     */
    public ArrayList<Double> ProbabilityVector;
    
    /**
     * Creates each character
     *
     * @param selection from a randomized array, makes sure player assignment is random
     * @param isAI set AI variable accordingly
     */
    public Character(int selection, boolean isAI){
        this.arrows = 0;
        this.role = "";
        this.isAi = isAI;
        this.chiefArrow = false;
        this.isDead = false;
        this.numShotSheriff = 0;
        this.numHelpSheriff = 0;
        this.ProbabilityVector =  new ArrayList<Double>();
        this.ProbabilityVector.addAll(Arrays.asList(0.0,0.0,0.0,0.0));
        
        switch (selection){
            case 1:
                this.name = "Bart Cassidy";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 2:
                this.name = "Black Jack";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 3:
                this.name = "El Gringo";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
            case 4:
                this.name = "Jesse Jones";
                this.lifePoints = 9;
                this.maxLife = 9;
                return;
            case 5:
                this.name = "Jourdonnais";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
            case 6:
                this.name = "Lucky Duke";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 7:
                this.name = "Paul Regret";
                this.lifePoints = 9;
                this.maxLife = 9;
                return;
            case 8:
                this.name = "Pedro Ramirez";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 9:
                this.name = "Suzy Lafayette";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 10:
                this.name = "Vulture Sam";
                this.lifePoints = 9;
                this.maxLife = 9;
                return;
            case 11:
                this.name = "Jose Delgado";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
            case 12:
                this.name = "Tequila Joe";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
            case 13:
                this.name = "Belle Star";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 14:
                this.name = "Greg Digger";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
       }
    }
    
    /**
     * makes sure that the character assignment is random for each new game
     *
     * @param randomSelection takes array of numbers 1-14 to be shuffled
     * @return  array that has been shuffled
     */
    public static int [] shuffle_character (int [] randomSelection){
        Random rand = new Random(); //random number for shuffling
        int random;
        for (int i = 0; i < 14; i++){
            random = rand.nextInt(14);
            int temp1 = randomSelection[random];
            int temp2 = randomSelection [i];
            randomSelection[i] = temp1;
            randomSelection[random] = temp2;
        }
        return randomSelection;
    }
    
    /**
     * makes sure the roles used for each game are shuffled
     *
     * @param roles takes array of roles, length dependent on how many players
     * @param num equal to how many roles there are to shuffle
     * @return the array shuffled
     */
    public static String [] shuffle_roles (String [] roles, int num){
        Random rand = new Random(); //random number for shuffling
        int random;
        for (int i = 0; i <= num; i++){
            random = rand.nextInt(num);
            String temp1 = roles[random];
            String temp2 = roles [i];
            roles[i] = temp1;
            roles[random] = temp2;
        }
        
        return roles;
    }
    
    /**
     * obtains the roles needed for the game to run by the rules
     *
     * @param num the amount of players there are
     * @return a string with the appropriate roles for the amount of players
     */
    public static String [] select_role (int num){
        switch (num){
            case 3:
                String roles3 [] = {"Sheriff", "Renegade", "Outlaw", "Outlaw"};
                roles3 = Character.shuffle_roles(roles3, num);
                return roles3;
            case 4:
                String roles4 [] = {"Sheriff", "Renegade", "Outlaw", "Outlaw", "Deputy"};
                roles4 = Character.shuffle_roles(roles4, num);
                return roles4;
            case 5:
                String roles5 [] = {"Sheriff", "Renegade", "Outlaw", "Outlaw", "Outlaw", "Deputy"};
                roles5 = Character.shuffle_roles(roles5, num);
                return roles5;
            case 6:
                String roles6 [] = {"Sheriff", "Renegade", "Outlaw", "Outlaw", "Outlaw", "Deputy", "Deputy"};
                roles6 = Character.shuffle_roles(roles6, num);
                return roles6;
            default:
                String roles7 [] = {"Sheriff", "Renegade", "Renegade", "Outlaw", "Outlaw", "Outlaw", "Deputy", "Deputy"};
                roles7 = Character.shuffle_roles(roles7, num);
                return roles7;
        }  
    }
    
    /**
     * sets character's role
     *
     * @param role a string corresponding to the character's assigned role
     */
    public void set_role (String role){
        this.role = role;
    }
    
    /**
     *  adds 1 to player's arrow count
     */
    public void gain_arrow (){
        this.arrows += 1;
    }
    
    /**
     *  subtracts 1 from player's arrow count
     */
    public void lose_arrow (){
        this.arrows -= 1;
    }
    
    /**
     *  adds 1 to players life if they are not max health and not a zombie
     */
    public void gain_life (){
        if ("Zombie".equals(this.role)){
            System.out.println(this.name + " is a zombie and cannot be healed.");
        }
        else if (this.lifePoints < this.maxLife){
            this.lifePoints += 1;
            System.out.println(this.name + " gained 1 life point.");
        }
        else {
            System.out.println(this.name + " has full life points, so they could not gain another life point.");
        }
    }

    /**
     * subtracts a life point from a character, and eliminates the player if they reach 0
     *
     * @param game game object
     * @param arrowPile arrowPile object
     * @param arrowOrDynamite true if killed by arrow or dynamite, false otherwise
     */

    
    public void lose_life(GameFunctions game, ArrowPile arrowPile, Boolean arrowOrDynamite){
        String choice; //choice for special abilities
        
        Scanner input = new Scanner(System.in);
        
        if (this.lifePoints == 0){
            System.out.println("\n-----\nERROR, LOSE_LIFE(): " + this.name + "\n-----\n");
        }
        else{
            //Bart Cassidy special ability - can gain arrow instead of losing life from another player
            if ("Bart Cassidy".equals(this.name) && !arrowOrDynamite && !this.isAi){
                if (arrowPile.remaining > 1){
                    System.out.print("Bart Cassidy, would you like to lose a 'life point' or take an 'arrow'? : ");
                    choice = input.nextLine();
                    
                    choice = choice.toLowerCase();
            
                    while (!"arrow".equals(choice) && !"life point".equals(choice)){
                        System.out.print("Invalid input. Please enter 'life point' or 'arrow': ");
                        choice = input.nextLine();
                        choice = choice.toLowerCase();
                    }
                
                    if ("arrow".equals(choice)){
                        arrowPile.remove_arrow(game);
                    }
                    else {
                        this.lifePoints -= 1;
                    }
                }
            }
            this.lifePoints --;
            
            //Pedro Ramirez special ability - Discards arrow if he loses life
            if ("Pedro Ramirez".equals(this.name)){
                if (this.arrows > 0){
                    arrowPile.add_arrow(this);
                    System.out.println("Pedro Ramirez lost a life point, so he discarded an arrow.");
                }
            }
        
            //If the player is now dead, eliminates them from playing
            if (this.lifePoints < 1){
                System.out.println(this.name + " has run out of life points and has lost the game.");
                System.out.println("Their role was " + this.role);
                game.eliminate_player(this, arrowPile, !arrowOrDynamite);
            }
        }
    	
    }
    
  
}