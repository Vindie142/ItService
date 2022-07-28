package ru.kazberov.ItService.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class TaskTwoArrays extends Task {
	
	private List<String> array1; // the first array entered
	private List<String> array2; // the second array entered
	private List<String> finalArray; // array for the response
	
	public TaskTwoArrays () {
		array1 = new ArrayList<String>();
		array2 = new ArrayList<String>();
		finalArray = new ArrayList<String>();
	}
	
	@Override
	public void write(String ...args) {
		String input1 = args[0];
		String input2 = args[1];
		// turning a string into an array of correct strings
		array1 = convertToCorrectArray(input1);
		array2 = convertToCorrectArray(input2);
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
		finalArray = finalArray.stream().distinct().collect(Collectors.toList());
		Collections.sort(finalArray);
		this.finalArray = finalArray;
	}
	
	// returns a response
	@Override
	public String getAnswer() {
		return finalArray.stream().collect(Collectors.joining(", "));
	}
	
	private List<String> convertToCorrectArray(String input) {
		// removes the spaces and creates a char from the letters
		input = input.replaceAll("\\s+","");
		
		String[] words = input.split(",");
		
		List<String> correctArray = new ArrayList<String>();
		Arrays.asList(words).stream().forEach(e -> correctArray.add(e));
		
		return correctArray;
	}

}
