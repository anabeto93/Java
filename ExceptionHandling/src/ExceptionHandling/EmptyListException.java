package ExceptionHandling;

public class EmptyListException extends RuntimeException{
	public EmptyListException(){
		super("The List Is Empty! Try Filling it.");
	}
}