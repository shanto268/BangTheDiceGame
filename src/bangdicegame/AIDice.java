package bangdicegame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AIDice {
	public ArrayList<String> faces;
	public ArrayList<String> loudMouth;
	public ArrayList<String> coward;
	public ArrayList<String> duel;
	
	private int numDice;
	
	public AIDice() {
		this.faces = new ArrayList<String>();
		this.loudMouth = new ArrayList<String>();
		this.duel = new ArrayList<String>();
		this.coward = new ArrayList<String>();
		
		this.faces.addAll(Arrays.asList("A", "D", "S1", "S2", "B", "G"));
		this.numDice = 5;
        this.loudMouth.addAll(Arrays.asList("D", "Bt", "DB1", "DB2", "A", "DG"));
        this.coward.addAll(Arrays.asList("D", "DBr", "BA", "S1", "A", "B"));
        this.duel.addAll(Arrays.asList("D", "W", "F", "G", "A", "F"));
	}
	
	public static int randInt(int minnum, int maxnum) {
		Random rand = new Random();
	    return rand.nextInt() * (maxnum - minnum) + minnum;
	}
	
	public ArrayList<String> rollThemDice(int num){
		ArrayList<String> results = new ArrayList<String>();
		for (int i=0;i<num;i++) {
			int roll = (int)(Math.random()*6);
			results.add(this.faces.get(roll));
		}
		if (num==0)
			results.add("Empty");
		return results;
	}
	
	public String rollDuelDice() {
		int roll = (int)(Math.random()*6);
		return this.duel.get(roll);
	}
	
	public ArrayList<String> rollThemDuelDice(int num){
		ArrayList<String> results = new ArrayList<String>();
		for (int i=0;i<num;i++) {
			int roll = (int)(Math.random()*6);
			results.add(this.duel.get(roll));
		}
		if (num==0)
			results.add("Empty");
		return results;
	}
	
	public ArrayList<String> rollThemLoudMouthDice(int num){
		ArrayList<String> results = new ArrayList<String>();
		for (int i=0;i<num;i++) {
			int roll = (int)(Math.random()*6);
			results.add(this.coward.get(roll));
		}
		if (num==0)
			results.add("Empty");
		return results;
	}
	
	
	public ArrayList<String> rollThemCowardDice(int num){
		ArrayList<String> results = new ArrayList<String>();
		for (int i=0;i<num;i++) {
			int roll = (int)(Math.random()*6);
			results.add(this.loudMouth.get(roll));
		}
		if (num==0)
			results.add("Empty");
		return results;
	}
	
	
	public ArrayList<String> generateRolls(int numNeeded, char diceUsed, boolean SUsed, int numD) {
		ArrayList<String> results = new ArrayList<String>();
		int maxD = 2;
		int maxR = 3;
		int numR = 0;
		int numS = 0;
		
		if (diceUsed == 'R') {
			numR = Math.min(numNeeded-numD, maxR);
			numD = Math.max(numR-maxD,numD);
			results.addAll(rollThemDice(numR));
			results.addAll(rollThemDuelDice(numD));
		}
		
		else if (diceUsed == 'L') {
			maxR = 2;
			if (SUsed) {
				numR = Math.min(numNeeded-numD, maxR);
				numD = Math.min(numNeeded-numD,numNeeded-numR);
				results.addAll(rollThemDice(numR));
				results.addAll(rollThemDuelDice(numD));
			}
			else {
				numS = 1;
				results.addAll(rollThemLoudMouthDice(numS));
				numR = Math.min(numNeeded-numD, maxR);
				numD = Math.min(numNeeded-numD,numNeeded-numR);
				results.addAll(rollThemDice(numR));
				results.addAll(rollThemDuelDice(numD));
			}
								
		}
		
		else if (diceUsed == 'C') {
			maxR = 2;
			if (SUsed) {
				numR = Math.min(numNeeded-numD, maxR);
				numD = Math.min(numNeeded-numD,numNeeded-numR);
				results.addAll(rollThemDice(numR));
				results.addAll(rollThemDuelDice(numD));
			}
			else {
				numS = 1;
				results.addAll(rollThemCowardDice(numS));
				numR = Math.min(numNeeded-numD, maxR);
				numD = Math.min(numNeeded-numD,numNeeded-numR);
				results.addAll(rollThemDice(numR));
				results.addAll(rollThemDuelDice(numD));
			}
		}
		
		results.removeIf( face -> face.equals("Empty"));  
		return results;
	}
	
	
	
	public ArrayList<String> rollDiceExpansion(int num, char choice){
		
		ArrayList<String> results = new ArrayList<String>();
		for (int i=0;i<num-3;i++) {
			int roll = (int)(Math.random()*6);
			results.add(this.faces.get(roll));
		} //2 normal dice
		
		for (int i=0;i<num-3;i++) {
			int roll = (int)(Math.random()*6);
			results.add(this.duel.get(roll));
		} //2 duel dice
		
		//choice = 'L' //loud mouth
		if (choice=='L') {
			int roll = (int)(Math.random()*6);
			results.add(this.loudMouth.get(roll));
		}
		//choice = 'R' //regular
		else if (choice=='R') {
			int roll = (int)(Math.random()*6);
			results.add(this.faces.get(roll));
		}
		//choice = 'C' //coward
		else {
			int roll = (int)(Math.random()*6);
			results.add(this.coward.get(roll));
		}
		return results;
	}
	
}