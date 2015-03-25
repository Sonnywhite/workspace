package knapsack;

import java.util.Arrays;

public class Application {
    
    public static void main(String[] args) {
	
	Knapsack knapsack = new Knapsack();
	
	// Example
	KnapsackItem[] sampleItems = {
			//	w,v
		new KnapsackItem(12, 13),
		new KnapsackItem(10, 8),
		new KnapsackItem(13,7)
	};
	int sampleKnapsackSize = 25;
	
	KnapsackItem[] filledKnapsack = knapsack.doIt(sampleItems, sampleKnapsackSize);
	
	System.out.println(Arrays.toString(filledKnapsack));
	
	// Do what you want
	
    }

}
