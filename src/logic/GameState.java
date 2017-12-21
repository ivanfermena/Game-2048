package logic;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que expresa la estrucutras del tratamiento de estados.
 */

public class GameState {

    /**Atributtes */
    private int score;
    private int highest;
    private int[][] boardState;

    /**
     * Constructor de GameStates
     * @param score int --> Almacena el estado del score del juego
     * @param boardState int[][] --> Almacena el estado actual del board
     */
    public GameState(int score, int[][] boardState) {
        this.score = score;
        this.boardState = boardState;
    }

    /**Getters*/
    public int getScore() {
        return score;
    }
    public int[][] getBoardState() {
        return boardState;
    }
}
