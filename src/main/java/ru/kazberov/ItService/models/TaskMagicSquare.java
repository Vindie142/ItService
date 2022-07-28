package ru.kazberov.ItService.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class TaskMagicSquare extends Task {
	
	private static final int DEGREE = 3; // the degree of the array row is 3x3
	private static final int LINE_SUM = 15; // the sum of the array row is 3x3
	private static final int SQUARE_OF_DEGREE = DEGREE*DEGREE; // the square of degree of the array row is 3x3
	private int[] inputArray; // the entered array array
	private int[] finalArray; // array for the response
	private int cost; // the cost of converting an array
	
	public TaskMagicSquare () {
		inputArray = new int[SQUARE_OF_DEGREE];
		finalArray = new int[SQUARE_OF_DEGREE];
		cost = 0;
	}
	
	@Override
	public void write(String ...args) throws IllegalArgumentException {
		String input = args[0];
		// removes the spaces and create a char from the letters	
		input = input.replaceAll("\\s+","");
		
		String[] words = input.split(",");
		
		List<String> correctArray = new ArrayList<String>();
		Arrays.stream(words).forEach(e -> correctArray.add(e));
		
		ifArrayIsCorrect(correctArray);
		
		for (int i = 0; i < SQUARE_OF_DEGREE; i++) {
			inputArray[i] = Integer.parseInt(correctArray.get(i));
		}
	}
	
	@Override
	public void calculate() {
		List<int[]> withMagicArrays = new ArrayList<int[]>();
		int[] testArray = new int[SQUARE_OF_DEGREE];
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
						
						List<Integer> delitedRemainingDigits = remainingDigits.stream().collect(Collectors.toList());
						remainingDigits.remove(i4);
						
						// has the figure for the magic square been found
						boolean ifFound = true;
						// the remaining cells are filled in
						ifFound = componentCalculate(ifFound, testArray, remainingDigits, 2, 0, 1);
						ifFound = componentCalculate(ifFound, testArray, remainingDigits, 5, 3, 4);
						ifFound = componentCalculate(ifFound, testArray, remainingDigits, 6, 0, 3);
						ifFound = componentCalculate(ifFound, testArray, remainingDigits, 7, 1, 4);
						ifFound = componentCalculate(ifFound, testArray, remainingDigits, 8, 2, 5);
						
						// if the array is magic, then it is written to an array with magic arrays
						if (ifFound && checkingForMagic(testArray)) {
							int[] magicArray = new int[SQUARE_OF_DEGREE];
							System.arraycopy(testArray, 0, magicArray, 0, SQUARE_OF_DEGREE);
							withMagicArrays.add(magicArray);							
						}
						
						remainingDigits = delitedRemainingDigits;
					}
					remainingDigits.add(forAdd3);
				}
				remainingDigits.add(forAdd1);
			}
			remainingDigits.add(forAdd0);
		}
		
		// writes the best array in response
		int[] finalArray = chooseOneMagicArray(withMagicArrays);
		System.arraycopy(finalArray, 0, this.finalArray, 0, SQUARE_OF_DEGREE);
	}
	
	@Override
	public String getAnswer() {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < SQUARE_OF_DEGREE; i++) {
			output.append(finalArray[i]);
			output.append(", ");
			if (i == 2 || i == 5) {
				output.append("\n");
			}
		}
		output.append("\nCost =");
		output.append(cost);
		return output.toString();
	}
	
	// fills in the remaining cells in the row
	private boolean componentCalculate (boolean ifFound, int[] testArray, List<Integer> remainingDigits, int main, int a1, int a2) {
		if (ifFound) {
			Integer num = LINE_SUM - (testArray[a1]+testArray[a2]);
			if (remainingDigits.contains(num)) {
				int index = remainingDigits.indexOf(num);
				remainingDigits.remove(index);
				testArray[main] = num;
				return true;
			}
		}
		return false;
	}
	
	// creates an array of natural numbers up to the desired
	private List<Integer> newRemainingDigits(){
		List<Integer> remainingDigits = new ArrayList<Integer>();
			for (int i = 0; i < SQUARE_OF_DEGREE; i++) {
				remainingDigits.add(i+1);
			}
		return remainingDigits;
	}
	
	// selects the magic array and the lowest cost
	private int[] chooseOneMagicArray (List<int[]> withMagicArrays) {
		// an array with the values of each array
		int[] cost = new int[withMagicArrays.size()];
		// calculates the cost of each array
		for (int i = 0; i < withMagicArrays.size(); i++) {
			for (int j = 0; j < SQUARE_OF_DEGREE; j++) {
				cost[i] += Math.abs(inputArray[j] - withMagicArrays.get(i)[j]);
			}
		}
		
		// finds an array with the minimum cost
		List<Integer> listOfCost = Arrays.stream(cost).boxed().collect(Collectors.toList());
		int minCost = Collections.min(listOfCost);
		int idMinCost = listOfCost.indexOf(minCost);
		
	    this.cost = minCost;
	    return withMagicArrays.get(idMinCost);
	}
	
	// checks the array for magic according to the remaining conditions
	private boolean checkingForMagic (int[] testArray) {
		boolean duplicate = false;
		// checks the array for repeatability of elements
		for(int i = 0; i< SQUARE_OF_DEGREE; i++) {
	         for (int j = i+1; j < SQUARE_OF_DEGREE; j++) {
	            if(testArray[i] == testArray[j]) {
	            	duplicate = true;
	            }
	         }
	      }
		
		// checks the array for magic according to the remaining conditions
		if (testArray[6]+testArray[7]+testArray[8]  == LINE_SUM && !duplicate) {
			return true;
		}
		return false;
	}
	
	// checks the array for correctness checks the array for the correctness of the data
	private void ifArrayIsCorrect(List<String> array) throws IllegalArgumentException {
		for (String string : array) {
			 try { 
				 Integer.parseInt(string); 
			 } catch (Exception e) { 
				 throw new IllegalArgumentException("Incorrect values!");
			 } 
		}
		if (array.size() != SQUARE_OF_DEGREE) {
			throw new IllegalArgumentException("The values should be "+SQUARE_OF_DEGREE+"!");
		}
	}
	
}
