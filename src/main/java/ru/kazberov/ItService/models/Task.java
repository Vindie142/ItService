package ru.kazberov.ItService.models;

public abstract class Task {
	public abstract void write(String ...args) throws IllegalArgumentException;
	public abstract void calculate();
	public abstract String getAnswer();
}
