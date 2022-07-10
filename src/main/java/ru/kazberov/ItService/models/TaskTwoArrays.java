package ru.kazberov.ItService.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskTwoArrays implements Task {
	
	private List<String> array1; // the first array entered
	private List<String> array2; // the second array entered
	private List<String> finalArray; // array for the response
	
	public TaskTwoArrays () {
		this.array1 = new ArrayList<String>();
		this.array2 = new ArrayList<String>();
		this.finalArray = new ArrayList<String>();
	}
	
	// returns a response
	@Override
	public String getAnswer() {
		String output = "";
		for (String string : this.finalArray) {
			output += string+", ";
		}
		return output;
	}
	
	// performs the calculation
	@Override
	public void calculate() {
		List<String> finalArray = new ArrayList<String>();
		for (String stringFrom2 : array2) {
			for (String stringFrom1 : array1) {
				// string comparison
				if (stringFrom2.contains(stringFrom1)) {
					finalArray.add(stringFrom1);
				}
			}
		}
		finalArray = removingDuplicates(finalArray);
		Collections.sort(finalArray);
		this.finalArray = finalArray;
	}
	
	public List<String> removingDuplicates(List<String> array) {
		Set<String> set = new HashSet<String>();
        for (String string : array) {
            set.add(string);
        }
        List<String> arrayWithoutDuplicates = new ArrayList<String>();
        for (String string : set) {
        	arrayWithoutDuplicates.add(string);
		}
		return arrayWithoutDuplicates;
		
	}
	
	
	public void write(String inputArray1, String inputArray2) {
		// turning a string into an array of correct strings
		this.array1 = convertToCorrectArray(inputArray1);
		this.array2 = convertToCorrectArray(inputArray2);
	}
	
	public List<String> convertToCorrectArray(String array) {
		List<String> correctArray = new ArrayList<String>();
		String currentString = ""; 
		
		// we remove the spaces and create a char from the letters
		array = array.replaceAll("\\s+","");
		char[] arrayInChar = array.toCharArray();
		
		for (int i = 0; i < arrayInChar.length; i++) {
			if (arrayInChar[i] == ',') {
				if (!currentString.equals("")) {
					correctArray.add(currentString);
					currentString = "";
				}
				continue;
			}
			currentString += arrayInChar[i];
			// if this is the last element of the char
			if (i == arrayInChar.length-1) {
				correctArray.add(currentString);
				currentString = "";
			}
		}
		return correctArray;
	}
	
}
