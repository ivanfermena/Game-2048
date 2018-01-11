package logic.multigames;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Interface J8 que dicta como ha de ser la estructura de la logica de los juegos.
 */

import logic.Board;
import logic.Cell;
import logic.multigames.games.Rules2048;
import logic.multigames.games.RulesFib;
import logic.multigames.games.RulesInverse;
import util.ArrayAsList;
import util.GameType;
import util.Position;

import java.util.Random;

public interface GameRules {

    /**
     * Metodo que dicta la estructura de que numero se inserta en una celda determinada.
     * @param board Board --> Tablero con el estado actual del juego
     * @param pos Position --> Posicion aleatoria a la que se quiere insertar el nuevo numero
     * @param rand Random --> Objeto Random para la seleccion aleatoria
     */
    void addNewCellAt(Board board, Position pos, Random rand);

    /**
     * Metodo que se encarga de la logica de la union entre dos celdas
     * @param self Cell --> Celda uno a tratar
     * @param other Cell --> Celda dos a tratar
     * @return int --> Numero despues de la union.
     */
    int merge(Cell self, Cell other);

    /**
     * Metodo que trata cuando se gana en cada juego.
     * @param board Board --> Tablero con el estado actual del juego.
     * @return int --> Devuelve el valor con el cual se gana la partida.
     */
    int getWinValue(Board board);

    /**
     * Metodo que trata si se ha llega a la condicion de victoria
     * @param board Board --> Estado actual del tablero
     * @return boolean --> Devuleve si se ha ganado el juego o no.
     */
    boolean win(Board board);

    /**
     * Metodo que comprueba si quedan movimientos posibles en el tablero
     * @param board Board --> Estado actual del tablero
     * @return boolean --> Devuelve si se ha perdido la partida o no.
     */
    default boolean lose(Board board){ return !board.possibleMov(); }

    /**
     * Metodo que crea un tablero nuevo con un tamanio especifico.
     * @param size int --> Tamanio especifico del nuevo tablero
     * @return Board --> Devuelve ese tablero espefico
     */
    default Board createBoard(int size){
        return new Board(size);
    }

    /**
     * Metodo que sirve para aniadir una nueva celda de manera aleatoria en cada movimiento
     * @param board Board --> Estado actual del tablero
     * @param rand Random --> Objeto Random para la seleccion aleatoria
     */
    default void addNewCell(Board board, Random rand){
        ArrayAsList listPosLib = new ArrayAsList();
        board.checkLib(listPosLib);
        if(listPosLib.size() != 0){
            Position position = (Position) ArrayAsList.choice(listPosLib, rand);
            addNewCellAt(board, position, rand);
        }
    }

    /**
     * Metodo que inicia el tablero con unas condiciones especificas de casa juego
     * @param board Board --> Estado actual del tablero
     * @param numCells int --> Numero de celdas que se van a rellenar de comienzo.
     * @param rand Random --> Objeto Random para la seleccion aleatoria
     */
    default void initBoard(Board board,int numCells,Random rand){
        ArrayAsList listPosLib = new ArrayAsList();
        board.checkLib(listPosLib);
        ArrayAsList.shuffle(listPosLib, rand);
        for(int i = 0; i < numCells; i++) {
            Position position = (Position) listPosLib.get(i);
            addNewCellAt(board, position, rand);
        }
    }
    String RulesName();
}
