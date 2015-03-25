package knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Knapsack {

    // Eingabe: U, B, w, v wie oben beschrieben
    // R := [1…(n+1), 0…B]-Matrix, mit Einträgen 0
    // FOR i = n … 1
    // FOR j = 1 … B
    // IF w(i) <= j
    // R[i,j] := max( v(i) + R[i+1, j-w(i)], R[i+1,j] )
    // ELSE
    // R[i,j] := R[i+1,j]
    // Ausgabe: R[1,B]

    private int n;
    private int B;
    private KnapsackItem[] U;
    private int[][] R;
    private Map<Integer,ArrayList<Integer>> rIndexToIndices = new HashMap<Integer, ArrayList<Integer>>();

    public KnapsackItem[] doIt(KnapsackItem[] items, int knapsackSize)
	    throws IllegalArgumentException {

	if (items == null)
	    throw new IllegalArgumentException("The 'items' can not be null");
	if (items.length == 0)
	    throw new IllegalArgumentException(
		    "The length of 'items' can not be 0");

	// the algorithm (dynamic programming)
	n = items.length;
	B = knapsackSize;
	U = items;
	R = new int[n + 1][B + 1];
	for (int[] arr : R)
	    Arrays.fill(arr, 0);
	int d = 1;
	for (int i = n - 1; i >= 0; i--) {
	    for (int j = 1; j <= B; j++) {
		// System.out.println("w(i)="+w(i)+" <= j+1="+(j+1)+" : "+(w(i)
		// <= j+1)+" --- j-w(i) : "+(j-w(i)));
		if (w(i) <= j)
		{
		    int newVal = v(i) + R[i + 1][j - w(i)];
		    int oldVal = R[i + 1][j];
		    if(newVal > oldVal)
		    {
			// new comp
			if(rIndexToIndices.get((j - w(i))*((i+1)+1)) != null)
			{
			    // add new i-Index to List
			    ArrayList<Integer> tmpList = rIndexToIndices.get((j - w(i))*((i+1)+1));
			    tmpList.add(i);
			    rIndexToIndices.put(j*(i+1), tmpList);
			}
			else
			{
			    // new List
			    ArrayList<Integer> tmpList = new ArrayList<Integer>();
			    tmpList.add(i);
			    rIndexToIndices.put(j*(i+1), tmpList);
			}
		    }
		    else
		    {
			// just copy the old comp (if it exists)
			if(rIndexToIndices.get(j*((i+1)+1)) != null)
			    rIndexToIndices.put(j*(i+1), rIndexToIndices.get(j*((i+1)+1)));
		    }
			
		    R[i][j] = Math.max(newVal,oldVal);
		}
		else
		    R[i][j] = R[i + 1][j];
	    }
//	    System.out.println("Nach Durchgang: " + d);
//	    d++;
//	    printR();
	}

	System.out.println("-------------------");
	printR();
	System.out.println("best erreichbarer Wert: " + R[0][B]);
	System.out.println("rIndexToIndices: "+rIndexToIndices);
	return items;
    }

    private int w(int i) {
	return U[i].getWeigth();
    }

    private int v(int i) {
	return U[i].getValue();
    }

    private void printR() {
	for (int i = 0; i < R.length; i++) {
	    // print the first line
	    if (i == 0) {
		for (int j = 0; j < R[0].length; j++) {
		    if (j == 0)
		    {
			System.out.print(" |"); 			
		    }
		    System.out.printf("%5d ",j);
		}
		System.out.println();
		System.out.println("-------------------------------------");
		
	    }
	    for (int j = 0; j < R[0].length; j++) {
		if (j == 0)
		    System.out.print(i + "|");
		System.out.printf("%5d ", R[i][j]);
	    }
	    System.out.println();
	}
	System.out.println("-------------------------------------");
    }

}
