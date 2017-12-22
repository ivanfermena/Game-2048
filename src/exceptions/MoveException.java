package exceptions;

public class MoveException extends Exception {

    public MoveException(){
        super("Unknown direction for move command. (up, down, left or right)");
    }

}
