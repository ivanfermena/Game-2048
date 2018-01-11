package logic;

import exceptions.CommandExecuteException;
import logic.multigames.GameRules;
import logic.multigames.games.Game;
import util.ArrayAsList;
import util.Direction;
import util.MyStringUtils;
import util.Position;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que almacena el estado  un tablero y proporciona metodos necesarios para la gestion de dicho estado.
 */

public class Board {

    /** -Atributtes- */
    private Cell[][] _board;
    private int _boardSize;

    /** -Constructor- */
    public Board(int _boardSize) {
        this._board = new Cell[_boardSize][_boardSize];
        this._boardSize = _boardSize;
        for(int i = 0; i < _boardSize; i++) {
            for(int j = 0; j < _boardSize; j++) {
               this._board[i][j] = new Cell(0);
            }
        }
    }

    public int get_boardSize() { return _boardSize; }

    /**
     * Método usado para modificar el valor de la baldosa de la posición pos con el valor value
     * @param pos -> Posicion a modificar.
     * @param value -> Valor a insertar.
     */
    public void setCell(Position pos, int value){
        this._board[pos.getX()][pos.getY()].setValue(value);
    }

    /**
     * Ejecuta las dos primeras acciones de un movimiento (desplazar y fusionar) en la dirección dir del tablero
     * @param dir -> Direccion a la que realiza el movimiento.
     * @return Object -> logic.MoveResults - Objeto que se gestionara en el controlMov
     */
    public MoveResults executeMove(Direction dir, GameRules rules) {
        MoveResults results = new MoveResults(false,0);
        switch (dir){
            case UP:
                 results = controlMov(dir,0, 0, 1, rules);
                break;
            case DOWN:
                results = controlMov( dir,_boardSize-1,_boardSize-1, -1,rules);
                break;
            case LEFT:
                results = controlMov( dir, 0, 0,1 , rules);
                break;
            case RIGHT:
                results = controlMov( dir, _boardSize-1, _boardSize -1, -1, rules);
                break;
        }

        return results;
    }

    /**
     * Gestiona los movimientos en las cuatro direcciones posibles. Trata el desplazamiento y la fusion de posiciones.
     * @param dir -> Direccion a la que se realizara el movimiento.
     * @param startX -> Inicio de eje X para su control dependiendo de movimiento a hacer
     * @param startY -> Inicio de eje y para su control dependiendo de movimiento a hacer
     * @param controlDir -> Variable auxiliar para controlar direccion.
     * @return -> Devuelve el objeto MoveResult despues de gestionarlo.
     */
    public MoveResults controlMov(Direction dir, int startX, int startY, int controlDir, GameRules rules)
    {
        boolean setMoved = false;
        int setPoints = 0;

        for(int i = startX; i < this._boardSize && i >= 0; i = i + controlDir) {
            int j = startY;
            while(j < this._boardSize && j >= 0) {
                Position position = new Position(i,j,dir);
                int auxX = position.getX();
                int auxY = position.getY();
                position.neighbour(dir);
                while (position.posValid(_boardSize) && this._board[position.getX()][position.getY()].isEmpty())
                {
                    position.neighbour(dir);
                }
                if (position.posValid(_boardSize))
                {
                    int merge = this._board[auxX][auxY].doMerge(this._board[position.getX()][position.getY()], rules);
                    if(merge == 0){
                        if (this._board[auxX][auxY].isEmpty()){
                            this._board[auxX][auxY].setValue(this._board[position.getX()][position.getY()].getValue());
                            this._board[position.getX()][position.getY()].setValue(0);
                            setMoved = true;
                        }
                        else
                        {
                            j = j + controlDir;
                        }
                    }
                    else
                    {
                        setPoints +=  merge;
                        setMoved = true;
                        j = j + controlDir;
                    }
                }
                else
                {
                    j = j + controlDir;
                }
            }
        }
        return new MoveResults(setMoved, setPoints);
    }
    /**
     * Imprimir el estado del tablero _board.
     * @return String -> Imprime el tablero cuando es necesario
     */
    @Override
    public String toString(){
        String viewBoard = "\n ";
        MyStringUtils myStr = new MyStringUtils(this._boardSize);

        viewBoard += MyStringUtils.repeat(myStr.gethDelimeter(),this._boardSize*10 - 1 );
        viewBoard += "\n";

        for (int i = 0; i < this._boardSize; i++) {
            viewBoard += myStr.getvDelimeter();

            for (int j = 0; j < this._boardSize; j++) {
                viewBoard += MyStringUtils.centre(this._board[i][j].toString(), 9) + myStr.getvDelimeter();
            }

            viewBoard += "\n ";
            viewBoard += MyStringUtils.repeat(myStr.gethDelimeter(),this._boardSize*10 - 1 );
            viewBoard += "\n";
        }
        return viewBoard;
    }

    /**
     * Chequea Gestiona el reyeno del util.ArrayAsList que almacena las posiciones que esten vacias.
     * @param list -> Lista de de posiciones vacias
     */
    public void checkLib(ArrayAsList list) {
        for(int i = 0; i < this._boardSize; i++) {
            for(int j = 0; j < this._boardSize; j++) {
                if(this._board[i][j].isEmpty()) {
                    Position posLib = new Position(i,j);
                    list.addElem(posLib);
                }
            }
        }
    }

    /**
     * Gestiona la posibilidad de que se pueda realizar movimientos
     * @return: True -> Si hay movimiento posible; False -> No hay movimientos posibles(Has perdido)
     */
    public boolean possibleMov() {
        ArrayAsList listPosLib = new ArrayAsList();
        this.checkLib(listPosLib);
        if(listPosLib.size() == 0 ) return ( possibleH() || possibleV() ) ;
        else return true;
    }

    /**
     * Gestiona si hay pisiciones horizontales contiguas con el mismo valor, es decir, si hay movimientos posibles.
     * @return: True -> Si hay movimiento posible; False -> No hay movimientos posibles
     */
    private boolean possibleH() {
        boolean possible = false;
        int i = 0;
        while(i < this._boardSize && !possible) {
            int j = 0;
            while (j < this._boardSize - 1 && !possible) {
                if(this._board[i][j].getValue() == this._board[i][j+1].getValue()) {
                    possible = true;
                }
                j++;
            }
            i++;
        }
        return possible;
    }

    /**
     * Gestiona si hay pisiciones verticales contiguas con el mismo valor, es decir, si hay movimientos posibles.
     * @return: True -> Si hay movimiento posible; False -> No hay movimientos posibles
     */
    private boolean possibleV() {
        boolean possible = false;
        int y = 0;
        while(y < this._boardSize && !possible) {
            int x = 0;
            while (x < this._boardSize - 1 && !possible) {
                if(this._board[x][y].getValue() == this._board[x+1][y].getValue()) {
                    possible = true;
                }
                x++;
            }
            y++;
        }
        return possible;
    }

    public int[][] getState(){
        int[][] state = new int[this._boardSize][this._boardSize];
        for(int i = 0; i < this._boardSize; i++){
            for(int j = 0; j < this._boardSize; j++){
                state[i][j] = this._board[i][j].getValue();
            }
        }
        return state;
    }

    public void setState(int[][] aState){
        for (int i = 0; i < aState.length; i++) {
            for (int j = 0; j < aState[0].length; j++) {
                this._board[i][j].setValue(aState[i][j]);
            }
        }
    }

    public int highestValue(){
        int highestValue = this._board[0][0].getValue();
        for (int i = 0; i < this._boardSize; i++) {
            for (int j = 0; j < this._boardSize; j++) {
                if(highestValue < this._board[i][j].getValue()){
                    highestValue = this._board[i][j].getValue();
                }
            }
        }
        return highestValue;
    }

    public int lowestValue(){
        int lowestValue = this._board[0][0].getValue();
        for (int i = 0; i < this._boardSize; i++) {
            for (int j = 0; j < this._boardSize; j++) {
                if(lowestValue > this._board[i][j].getValue() && this._board[i][j].getValue() != 0)
                    lowestValue = this._board[i][j].getValue();
                else if (lowestValue == 0 && this._board[i][j].getValue() > 0)
                    lowestValue = this._board[i][j].getValue();
            }
        }
        return lowestValue;
    }

    private String parseGetState(int[][] state){
        String vectorStr = "";
        for(int i = 0; i < state.length; i++){
            for(int j = 0; j < state.length; j++){
                vectorStr += state[i][j] + "\t";
            }
            vectorStr += "\r\n";
        }
        vectorStr += "\r\n";
        return vectorStr;
    }


    public void store(BufferedWriter bufInput) throws IOException{
        bufInput.write(parseGetState(getState()));
    }

    public void load(BufferedReader bufInput) throws IOException, CommandExecuteException{
        if (!bufInput.readLine().equals("This file stores a saved 2048 game")) throw new CommandExecuteException("Invalid filename: The file does not use a specific structure.\n");
        bufInput.readLine(); // Linea de blancos

        String[] auxReadStr = bufInput.readLine().split("\t");
        // Cambia el Board actual por completo con uno nuevo
        this._board  = new Cell[auxReadStr.length][auxReadStr.length];
        this._boardSize = auxReadStr.length;
        for (int i = 0; i < this._boardSize; i++) {
            for (int j = 0; j < this._boardSize; j++) {
                this._board[i][j] = new Cell(Integer.parseInt(auxReadStr[j]) );
            }
            auxReadStr = bufInput.readLine().split("\t");
        }
    }
}


