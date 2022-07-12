package ru.kazberov.ItService;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ru.kazberov.ItService.models.Task;
import ru.kazberov.ItService.models.TaskMagicSquare;
import ru.kazberov.ItService.models.TaskTwoArrays;

public class ListOfTasks {
	
	// the map of the program name and the show name
	private static Map<String, String> tasks = new HashMap<String, String>();
	
	static { // here, when adding a new task, you need to add it 
		tasks.put("taskTwoArrays", "\"Two arrays\"");
		tasks.put("taskMagicSquare", "\"Magic square\"");
	}

	public static Task getTask(String programName) {
		Task task = null;
		switch (programName) { // write a new task here
			case "taskTwoArrays": task = new TaskTwoArrays(); break;
			case "taskMagicSquare": task = new TaskMagicSquare(); break;
		}
		return task;
	}
	
	public static String getShowNameFrom(String programName) {
		return tasks.get(programName);
	}

	public static String getFirstProgramName() {
		Entry<String, String> firstValue = tasks.entrySet().stream().findFirst().get();
		return firstValue.getKey();
	}
	
	public static Map<String, String> getTasks(){
		return tasks;
	}
}
