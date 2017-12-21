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
    public void setSize(int size) { this.size = size; }
    public static int getCAPACITY() { return CAPACITY; }
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
        if(this.getSize() < ArrayGameState.getCAPACITY() ){
            this.states[this.size] = state;
            this.size++;
        } else if( this.getSize() == ArrayGameState.getCAPACITY()){
            for (int i = this.size - 2; i >= 0; i--) {
                this.states[i] = this.states[i+1];
            }
            this.states[this.size-1] = state;
        }
    }
    public boolean isEmpty(){
        return this.size == 0;
    }
}
