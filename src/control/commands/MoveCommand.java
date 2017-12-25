package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando que realiza los movimientos de los juegos de la aplicacion.
 */

import control.Controller;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.GameOverException;
import logic.multigames.games.Game;
import util.Direction;

public class MoveCommand extends Command {

    /** -Atributtes- */
    private Direction dir;
    private static final String CommandInfo = "move";
    private static final String HelpInfo = " <direction>: Execute a move in one of the directions: <up>, <down>, <left>, <right>.";

    /**
     * Constructor de MoveCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public MoveCommand(Direction dir) {
        super(CommandInfo, HelpInfo);
        this.dir = dir;
    }

    /**
     * Metodo que realiza la accion de comprobar si se va realizadr moviemnto o no.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException, GameOverException{
            if (game.move(this.dir)) {
                return true;
            } else { throw new CommandExecuteException("No movement has been made."); }
    }

    /**
     * Metodo que controla el tratamiento del comando lanzado - Tratamiento especifico de Move por dos palabras.
     * @param commandWords String[] --> Array de candenas que tiene el comando lanzado para tratarlo
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     * @return Command --> Devuelve el objeto Command tratado y si no es perteneciente a este commando return: null
     */
    @Override
    public Command parse(String[] commandWords, Controller controller) throws CommandParserException {
        if (!this.commandName.equals(commandWords[0])) {
            return null;
        }else{
            if (commandWords.length == 2) {
                MoveCommand moveCommand = new MoveCommand(Direction.validDir(commandWords[1]));
                if (moveCommand.dir == null){
                    throw new CommandParserException("Unknown command, you must set a valid direction(<right>, <left>, <up>, <down>)") ;
                }
                else return moveCommand;
            } else { // Se podria anadir el caso en el cual hay mas de 2 palabras
                throw new CommandParserException("Move must be followed only by a direction: <up>, <down>, <left>, <right>.");
            }
        }
    }
}
