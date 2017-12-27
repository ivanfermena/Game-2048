package logic;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que expresa la estrucutras del tratamiento de estados.
 */

public class GameState {

    /**Atributtes */
    private int score;
    private int best;
    private int[][] boardState;

    /**
     * Constructor de GameStates
     * @param score int --> Almacena el estado del score del juego
     * @param boardState int[][] --> Almacena el estado actual del board
     */
    public GameState(int score, int[][] boardState, int best) {
        this.score = score;
        this.boardState = boardState;
        this.best = best;
    }

    /**Getters*/
    public int getScore() {
        return score;
    }
    public int[][] getBoardState() {
        return boardState;
    }
    public int getBest() { return best;}
}
