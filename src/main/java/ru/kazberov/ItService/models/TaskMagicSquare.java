package ru.kazberov.ItService.models;

import java.util.ArrayList;
import java.util.List;

public class TaskMagicSquare implements Task {
	
	private static final int degree = 3; // the degree of the array row is 3x3
	private static final int squareOfDegree = degree*degree; // the square of degree of the array row is 3x3
	private static final int lineSum = 15; // the sum of the array row is 3x3
	private int[] array; // the entered array array
	private int[] finalArray; // array for the response
	private String error; // string with errors
	private int cost; // the cost of converting an array
	
	public TaskMagicSquare () {
		this.array = new int[squareOfDegree];
		this.finalArray = new int[squareOfDegree];
		this.error = "";
		this.cost = 0;
	}
	
	@Override
	public String getAnswer() {
		// if there is an error message
		if (!this.error.equals("")) {
			return this.error;
		} else { // if everything is calculated correctly
			String output = "";
			for (int i = 0; i < squareOfDegree; i++) {
				output += this.finalArray[i]+", ";
				if (i == 2 || i == 5) {
					output += "\n";
				}
			}
			output += "\nCost ="+this.cost;
			return output;
		}
	}
	
	@Override
	public void calculate() {
		List<int[]> withMagicArrays = new ArrayList<int[]>();
		int[] testArray = new int[squareOfDegree];
		List<Integer> remainingDigits = newRemainingDigits();
		
		// search for semi-magical arrays (first, the upper left corner of 2x2 is filled in)
		for (int i0 = 0; i0 < remainingDigits.size(); i0++) {
			testArray[0] = remainingDigits.get(i0);
			int forAdd0 = remainingDigits.get(i0);
			remainingDigits.remove(i0);
			for (int i1 = 0; i1 < remainingDigits.size(); i1++) {
				testArray[1] = remainingDigits.get(i1);
				int forAdd1 = remainingDigits.get(i1);
				remainingDigits.remove(i1);
				for (int i3 = 0; i3 < remainingDigits.size(); i3++) {
					testArray[3] = remainingDigits.get(i3);
					int forAdd3 = remainingDigits.get(i3);
					remainingDigits.remove(i3);
					for (int i4 = 0; i4 < remainingDigits.size(); i4++) {
						testArray[4] = remainingDigits.get(i4);
						int forAdd4 = remainingDigits.get(i4);
						remainingDigits.remove(i4);
						
						// has the figure for the magic square been found
						boolean found = true;
						// the remaining cells are filled in
						found = componentCalculate(found, testArray, remainingDigits, 2, 0, 1);
						found = componentCalculate(found, testArray, remainingDigits, 5, 3, 4);
						found = componentCalculate(found, testArray, remainingDigits, 6, 0, 3);
						found = componentCalculate(found, testArray, remainingDigits, 7, 1, 4);
						found = componentCalculate(found, testArray, remainingDigits, 8, 2, 5);
						
						// if the array is magic, then it is written to an array with magic arrays
						if (found && checkingForMagic(testArray)) {
							int[] magicArray = new int[squareOfDegree];
							for (int i = 0; i < squareOfDegree; i++) {
								magicArray[i] = testArray[i];
							}
							withMagicArrays.add(magicArray);							
						}
						
						remainingDigits.add(forAdd4);
					}
					remainingDigits.add(forAdd3);
				}
				remainingDigits.add(forAdd1);
			}
			remainingDigits.add(forAdd0);
		}
		
		// writes the best array in response
		int[] finalArray = chooseOneMagicArray(withMagicArrays);
		for (int i = 0; i < squareOfDegree; i++) {
			this.finalArray[i] = finalArray[i];
		}
	}
	
	// fills in the remaining cells in the row
	public boolean componentCalculate (boolean found, int[] testArray, List<Integer> remainingDigits, int main, int a1, int a2) {
		if (found) {
			for (int i = 0; i < remainingDigits.size(); i++) {
				testArray[main] = remainingDigits.get(i);
				if (testArray[a1]+testArray[a2]+testArray[main] == lineSum) {
					return true;
				} else {
					testArray[main] = -1;
				}
				
			}
		}
		return false;
	}
	
	// creates an array of natural numbers up to the desired
	public List<Integer> newRemainingDigits(){
		List<Integer> remainingDigits = new ArrayList<Integer>();
			for (int i = 0; i < squareOfDegree; i++) {
				remainingDigits.add(i+1);
			}
		return remainingDigits;
	}
	
	// selects the magic array and the lowest cost
	public int[] chooseOneMagicArray (List<int[]> withMagicArrays) {
		// an array with the values of each array
		int[] cost = new int[withMagicArrays.size()];
		// calculates the cost of each array
		for (int i = 0; i < withMagicArrays.size(); i++) {
			for (int j = 0; j < squareOfDegree; j++) {
				cost[i] += Math.abs(this.array[j] - withMagicArrays.get(i)[j]);
			}
		}
		
		// finds an array with the minimum cost
		int minCost = cost[0];
		int idMinCost = 0;
	    for(int i = 1; i < cost.length; i++){ 
	    	if(cost[i] < minCost){ 
	        minCost = cost[i]; 
	        idMinCost = i;
	    	} 
	    }
	    this.cost = minCost;
	    return withMagicArrays.get(idMinCost);
	}
	
	// checks the array for magic according to the remaining conditions
	public boolean checkingForMagic (int[] testArray) {
		boolean duplicate = false;
		// checks the array for repeatability of elements
		for(int i = 0; i< squareOfDegree; i++) {
	         for (int j = i+1; j < squareOfDegree; j++) {
	            if(testArray[i] == testArray[j]) {
	            	duplicate = true;
	            }
	         }
	      }
		
		// checks the array for magic according to the remaining conditions
		if (testArray[6]+testArray[7]+testArray[8]  == lineSum && !duplicate) {
			return true;
		}
		return false;
	}
	
	@Override
	public void write(String array, String unusedVariable) {
		List<String> arrayList = new ArrayList<String>();
		String currentString = ""; 
		
		// removes the spaces and create a char from the letters
		array = array.replaceAll("\\s+","");
		char[] arrayInChar = array.toCharArray();
		
		for (int i = 0; i < arrayInChar.length; i++) {
			if (arrayInChar[i] == ',') {
				if (!currentString.equals("")) {
					arrayList.add(currentString);
					currentString = "";
				}
				continue;
			}
			currentString += arrayInChar[i];
			// if this is the last element of the char
			if (i == arrayInChar.length-1) {
				arrayList.add(currentString);
				currentString = "";
			}
		}
		ifArrayIsCorrect(arrayList);
		
		if (!this.error.equals("")) {
			return;
		}
		
		for (int i = 0; i < squareOfDegree; i++) {
			this.array[i] = Integer.parseInt(arrayList.get(i));
		}
	}
	
	// checks the array for correctness checks the array for the correctness of the data
	public void ifArrayIsCorrect(List<String> array) {
		for (String string : array) {
			 try { 
				 Integer.parseInt(string); 
			 } catch (Exception e) { 
				 this.error = "Incorrect values!";
			 } 
		}
		if (array.size() != squareOfDegree) {
			this.error = "The values should be "+squareOfDegree+"!";
		}
	}
	
}
