package ru.kazberov.ItService.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
		return this.finalArray.stream().collect(Collectors.joining(", "));
	}
	
	// performs the calculation
	@Override
	public void calculate() {
		List<String> finalArray = new ArrayList<String>();
		for (String stringFrom2 : this.array2) {
			for (String stringFrom1 : this.array1) {
				// string comparison
				if (stringFrom2.contains(stringFrom1)) {
					finalArray.add(stringFrom1);
				}
			}
		}
		finalArray = finalArray.stream().distinct().collect(Collectors.toList());
		Collections.sort(finalArray);
		this.finalArray = finalArray;
	}	
	
	public void write(String inputArray1, String inputArray2) {
		// turning a string into an array of correct strings
		this.array1 = convertToCorrectArray(inputArray1);
		this.array2 = convertToCorrectArray(inputArray2);
	}
	
	public List<String> convertToCorrectArray(String array) {
		List<String> correctArray = new ArrayList<String>();
		String currentString = ""; 
		
		// removes the spaces and creates a char from the letters
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
