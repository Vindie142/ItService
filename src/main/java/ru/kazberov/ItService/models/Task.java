package ru.kazberov.ItService.models;

import ru.kazberov.ItService.exceptions.IncorrectInputException;

public abstract class Task {
	public abstract void write(String a1, String a2) throws IncorrectInputException;
	public abstract void calculate();
	public abstract String getAnswer();
}
