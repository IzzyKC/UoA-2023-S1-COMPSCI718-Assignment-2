package industry.assignment02.game;

public class WordleFileNotFoundException extends Exception{
    /**
     * constructor of WordleFileNotFoundException
     *
     * @param message the String of error message
     */
    public WordleFileNotFoundException(String message){
        super(message);
    }
}
