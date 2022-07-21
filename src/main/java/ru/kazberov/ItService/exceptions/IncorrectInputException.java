package ru.kazberov.ItService.exceptions;

public class IncorrectInputException extends Exception{

	private static final long serialVersionUID = 2L;

	public IncorrectInputException(){}
	
	public IncorrectInputException(String message){
        super(message);
    }
	
}
