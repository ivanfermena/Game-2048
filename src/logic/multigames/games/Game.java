package logic.multigames.games;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Representar el estado de una partida: Inicio de partida, movimientos, scores, gestionar el comportamiento aleatorio del juego...)
 */

import java.util.Random;

import logic.ArrayGameState;
import logic.Board;
import logic.GameState;
import logic.MoveResults;
import logic.multigames.GameRules;
import util.*;
public class Game {

    /** -Atributtes- */
    private Board board;
    private int size;
    private int initCells;
    private Random myRandom;
    private int best, score;
    private boolean endGame;
    private boolean lostGame;
    private boolean wonGame;
    private ArrayGameState arrayGameState;
    private GameRules currentRules;
    private long seed;

    /** -Constructor- */
    public Game(int size, int initCells, long seed, GameRules currentRules) {
        this.currentRules = currentRules;
        this.best = 0;
        this.size = size;
        this.initCells = initCells;
        this.seed = seed;
        this.myRandom = new Random(this.seed);
        this.board = this.currentRules.createBoard(this.size);
        this.best = 0;
        this.score = 0;
        this.endGame = false;
        this.wonGame = false;
        this.lostGame = false;
        this.arrayGameState = new ArrayGameState();
        this.reset();
    }

    /**Getters and setters*/
    public ArrayGameState getArrayGameState() { return arrayGameState; }
    public boolean isEndGame() {
        return endGame;
    }
    public boolean isLostGame(){return lostGame;}
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
    public boolean isWonGame() { return wonGame; }
    public void setCurrentRules(GameRules currentRules) { this.currentRules = currentRules; }

    /**
     * Método que ejecuta un movimiento en la dirección dir sobre el tablero, actualiza marcador Score y el valor Best
     * @param dir -> Direccion del movimiento.
     */
    public boolean move(Direction dir){
        MoveResults results = this.board.executeMove(dir, this.currentRules);
        this.score += results.getPoints();
        this.best = this.currentRules.getWinValue(this.board);
        if(results.isMoved())
        {
                this.currentRules.addNewCell(this.board, this.myRandom);
                this.endGame  = this.lostGame = this.currentRules.lose(this.board);
                this.endGame = this.wonGame = this.currentRules.win(this.board);
                return true;
        }
        return false;
    }

    /**
     * Método responsable de inicializar la partida actual y restaurarlo al estado inicial
     */
    public void reset(){
        this.score = 0;
        this.arrayGameState = new ArrayGameState();
        this.board = this.currentRules.createBoard(this.size);
        this.currentRules.initBoard(this.board,this.initCells,this.myRandom);
        this.best = this.currentRules.getWinValue(this.board);
        this.arrayGameState.push(this.getState());
    }

    /**
     * Metodo que muestra el mejor valor usado y el score actual.
     * @return String --> Muestra los dos valores tratados.
     */
    @Override
    public String toString() {
            return this.board.toString() +"Best Value: " + this.best +  "\t score: " + this.score + "\n";
    }

    /**
     * Metodo que trata la realizacion del comando undo de los estados de juego.
     */
    public void undo(){
        GameState prevGameState = this.arrayGameState.getStates()[this.arrayGameState.getSize() - 2 - this.arrayGameState.getNumUndo()]; // Problema, ya que en el enunciado expone la utilidad de pop (aparentemente inutil) y dice que no hacen falta mas metodos
        this.setState(prevGameState);
        this.arrayGameState.setNumUndo(this.arrayGameState.getNumUndo() + 1);
    }

    /**
     *  Metodo que trata la realizacion del comando redo de los estados de juego.
     */
    public void redo(){
        GameState prevGameState = this.arrayGameState.getStates()[this.arrayGameState.getSize() - this.arrayGameState.getNumUndo()]; // Problema, ya que en el enunciado expone la utilidad de pop (aparentemente inutil) y dice que no hacen falta mas metodos
        this.setState(prevGameState);
        this.arrayGameState.setNumUndo(this.arrayGameState.getNumUndo() - 1);
    }

    /**Getters and Setters*/
    public GameState getState(){
        return new GameState(this.score, this.board.getState() );
    }
    public void setState(GameState aState){
        this.board.setState(aState.getBoardState());
        this.score = aState.getScore();
    }
    public void setSeed(long seed) {
        this.seed = seed;
        this.myRandom = new Random(this.seed);
    }
    public long setNewRandom(int numberBound) {
        this.seed = new Random().nextInt(numberBound);
        this.myRandom = new Random(seed);
        return this.seed;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int setInitCells(int initCells) {
        return this.initCells = initCells;
    }
}

