package logic;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que dicta la logica del array de estados para realizar los undos y redos.
 */

public class ArrayGameState {

    /**Atributtes*/
    private static final int CAPACITY = 20;
    private int size;
    private GameState[] states;
    private int numUndo;

    /**
     * Constructor de ArrayGameState
     */
    public ArrayGameState() {
        this.size = 0;
        this.states = new GameState[CAPACITY];
        this.numUndo = 0;
    }

    /**Getters and Setters*/
    public int getSize() { return size; }
    public int getNumUndo() { return numUndo; }
    public void setNumUndo(int numUndo) { this.numUndo = numUndo; }
    public GameState[] getStates() {
        return states;
    }

    /**
     * Metodo que trata la inserccion y el juego de indices dentro de los estados de juegos guardados.
     * @param state
     */
    public void push(GameState state){
        if(this.size < CAPACITY ){
            this.states[this.size] = state;
            this.size++;
        } else if( this.size == CAPACITY){
            for (int i = this.size - 2; i >= 0; i--) {
                this.states[i] = this.states[i+1];
            }
            this.states[this.size-1] = state;
        }
    }
    private boolean isEmpty(){
        return this.size == 0;
    }
    public boolean possibleUndo(){ return !(this.isEmpty() || this.size - 2 - this.numUndo < 0); }
    public boolean possibleRedo(){ return !(this.isEmpty() || this.numUndo == 0); }
    public void moveUpdate(GameState newCurrentState) {
        this.size = (this.size - this.numUndo);
        this.push(newCurrentState);
        this.numUndo = 0;
    }
    public GameState undoUpdate(){
        GameState prevGameState = this.states[this.size - 2 - this.numUndo];
        this.numUndo += 1;
        return prevGameState;
    }
    public GameState redoUpdate(){
        GameState prevGameState = this.states[this.size - this.numUndo];
        this.numUndo -= 1;
        return prevGameState;
    }
}
