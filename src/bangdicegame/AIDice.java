package bangdicegame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AIDice {
	public ArrayList<String> faces;
	
	private int numDice;
	
	public AIDice() {
		this.faces = new ArrayList<String>();
		this.faces.addAll(Arrays.asList("A", "D", "S1", "S2", "B", "G"));
		this.numDice = 5;
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
		return results;
	}
	
}