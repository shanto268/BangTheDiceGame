/*
 * Cierra Ditmore
 * CS 2365
 */
package bangdicegame;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * 
 */

public class Character {
    public int lifePoints, arrows, maxLife;
    public String name, role;
    
    
    public Character(int selection){
        this.arrows = 0;
        this.role = "";
        
        
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
                this.name = "Calamity Janet";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 4:
                this.name = "El Gringo";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
            case 5:
                this.name = "Jesse Jones";
                this.lifePoints = 9;
                this.maxLife = 9;
                return;
            case 6:
                this.name = "Jourdonnais";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
            case 7:
                this.name = "Kit Carlson";
                this.lifePoints = 7;
                this.maxLife = 7;
                return;
            case 8:
                this.name = "Lucky Duke";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 9:
                this.name = "Paul Regret";
                this.lifePoints = 9;
                this.maxLife = 9;
                return;
            case 10:
                this.name = "Pedro Ramirez";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 11:
                this.name = "Rose Doolan";
                this.lifePoints = 9;
                this.maxLife = 9;
                return;
            case 12:
                this.name = "Sid Ketchum";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 13:
                this.name = "Slab the Killer";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 14:
                this.name = "Suzy Lafayette";
                this.lifePoints = 8;
                this.maxLife = 8;
                return;
            case 15:
                this.name = "Vulture Sam";
                this.lifePoints = 9;
                this.maxLife = 9;
                return;
            case 16:
                this.name = "Willy the Kid";
                this.lifePoints = 8;
                this.maxLife = 8;
       }
    }
    
    public static int [] shuffle_character (int [] randomSelection){
        Random rand = new Random();
        int random;
        for (int i = 0; i < 16; i++){
            random = rand.nextInt(16);
            int temp1 = randomSelection[random];
            int temp2 = randomSelection [i];
            randomSelection[i] = temp1;
            randomSelection[random] = temp2;
        }
        return randomSelection;
    }
    
    public static String [] shuffle_roles (String [] roles, int num){
        Random rand = new Random();
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
    
    public static String [] select_role (int num){
        switch (num){
            case 2:
                String roles2 [] = {"Deputy", "Renegade", "Outlaw"};
                roles2 = Character.shuffle_roles(roles2, num);
                return roles2;
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
    
    public void set_role (String role){
        this.role = role;
    }
    
    public void gain_arrow (){
        this.arrows += 1;
    }
    
    public void lose_arrow (){
        this.arrows -= 1;
    }
    
    public void gain_life (){
        if (this.lifePoints < this.maxLife){
            this.lifePoints += 1;
            System.out.println(this.name + " gained 1 life point.");
        }
        else {
            System.out.println(this.name + " has full life points, so they could not gain another life point.");
        }
    }
    
    public void lose_life (GameFunctions playerOrder, ArrowPile arrowPile, Boolean arrowOrDynamite){
        String choice;
        
        Scanner input = new Scanner(System.in);
        
        if ("Bart Cassidy".equals(this.name) && !arrowOrDynamite){
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
                    this.gain_arrow();
                    arrowPile.remove_arrow(playerOrder);
                }
                else {
                    this.lifePoints -= 1;
                }
            }
        }
        
        else{ 
            this.lifePoints -= 1;
        }
        
        if ("Pedro Ramirez".equals(this.name)){
            if (this.arrows > 0){
                arrowPile.add_arrow(this);
                System.out.println("Pedro Ramirez lost a life point, so he discarded an arrow.");
            }
        }
        
        if (this.lifePoints < 1){
            System.out.println(this.name + " has run out of life points and has lost the game.");
            System.out.println("Their role was " + this.role);
            playerOrder.eliminate_player(this, arrowPile, !arrowOrDynamite);
        }
    }
}
