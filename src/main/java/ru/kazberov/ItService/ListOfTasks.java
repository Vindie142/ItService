package ru.kazberov.ItService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ru.kazberov.ItService.models.Task;
import ru.kazberov.ItService.models.TaskMagicSquare;
import ru.kazberov.ItService.models.TaskTwoArrays;

public final class ListOfTasks {
	
	// the map of the program name and the show name
	private static final Map<String, String> TASKS = new HashMap<String, String>();
	
	static { // here, when adding a new task, you need to add it 
		TASKS.put("taskTwoArrays", "\"Two arrays\"");
		TASKS.put("taskMagicSquare", "\"Magic square\"");
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
		return TASKS.get(programName);
	}

	public static String getFirstProgramName() {
		Entry<String, String> firstValue = TASKS.entrySet().stream().findFirst().get();
		return firstValue.getKey();
	}
	
	public static Map<String, String> getTasks(){
		return Collections.unmodifiableMap(TASKS);
	}
}
