package logic.multigames.games;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Representar el estado de una partida: Inicio de partida, movimientos, scores, gestionar el comportamiento aleatorio del juego...)
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Random;

import exceptions.CommandExecuteException;
import exceptions.GameOverException;
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
    private ArrayGameState arrayGameState;
    private GameRules currentRules;
    private long seed;
    private GameType currentGameType;

    /** -Constructor- */
    public Game(int size, int initCells, long seed, GameRules currentRules, GameType currentGameType) {
        this.currentRules = currentRules;
        this.currentGameType = currentGameType;
        this.best = 0;
        this.size = size;
        this.initCells = initCells;
        this.seed = seed;
        this.myRandom = new Random(this.seed);
        this.board = this.currentRules.createBoard(this.size);
        this.best = 0;
        this.score = 0;
        this.endGame = false;
        this.arrayGameState = new ArrayGameState();
        this.reset();
    }

    /**Getters and setters*/
    public boolean isEndGame() {
        return endGame;
    }
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
    public void setCurrentRules(GameRules currentRules) { this.currentRules = currentRules; }
    public void setCurrentGameType(GameType currentGameType) { this.currentGameType = currentGameType; }

    /**
     * Método que ejecuta un movimiento en la dirección dir sobre el tablero, actualiza marcador Score y el valor Best
     * @param dir -> Direccion del movimiento.
     */
    public boolean move(Direction dir) throws GameOverException{
        MoveResults results = this.board.executeMove(dir, this.currentRules);
        this.score += results.getPoints();
        this.best = this.currentRules.getWinValue(this.board);
        if(results.isMoved())
        {
            this.currentRules.addNewCell(this.board, this.myRandom); // Añade una nueva celda
            this.arrayGameState.moveUpdate(this.getState()); // Actualiza el array de estados dado el movimiento realizado
            if(this.currentRules.lose(this.board)){
                this.setEndGame(true);
                throw new GameOverException("You lost ! :(");
            } else if(this.currentRules.win(this.board)) {
                this.setEndGame(true);
                throw new GameOverException("You won ! :)");
            } else return true;
        } else return false;
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
    public void undo() throws EmptyStackException{
        if(!this.arrayGameState.possibleUndo()){
            throw new EmptyStackException();
        }else{
            this.setState(this.arrayGameState.undoUpdate());
        }
    }

    /**
     *  Metodo que trata la realizacion del comando redo de los estados de juego.
     */
    public void redo() throws EmptyStackException{
        if(!this.arrayGameState.possibleRedo()){
            throw new EmptyStackException();
        }else{
            this.setState(this.arrayGameState.redoUpdate());
        }
    }

    /**Getters and Setters*/
    private GameState getState(){
        return new GameState(this.score, this.board.getState(), this.best );
    }
    private void setState(GameState aState){
        this.board.setState(aState.getBoardState());
        this.score = aState.getScore();
        this.best = aState.getBest();
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
    public void setInitCells(int initCells) {
        this.initCells = initCells;
    }
    private String parseGameInfo(){
        return this.initCells + "\t" + this.score + "\t" + this.currentGameType.externalise();
    }
    public void store(BufferedWriter bufInput) throws IOException{
        bufInput.write("This file stores a saved 2048 game");
        bufInput.newLine();
        bufInput.newLine();
        this.board.store(bufInput); // Guarda en el bufInput el board
        bufInput.write(this.parseGameInfo());
    }

    public GameType load(BufferedReader bufInput) throws CommandExecuteException, IOException, NumberFormatException{
        this.board.load(bufInput);
        this.size = this.board.get_boardSize();
        String[] infoGame = bufInput.readLine().split("\t");
        this.initCells = Integer.parseInt(infoGame[0]);
        this.score = Integer.parseInt(infoGame[1]);
        GameType gameType = GameType.parse(infoGame[2]);
        if(gameType == null) throw new CommandExecuteException("The file does not contain a saved game with a valid format.\n");
        else {
            this.currentGameType = gameType;
            this.currentRules = gameType.getRules();
            this.best = this.currentRules.getWinValue(this.board);
            return gameType;
        }
    }

}

