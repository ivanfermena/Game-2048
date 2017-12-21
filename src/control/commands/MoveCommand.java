package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando que realiza los movimientos de los juegos de la aplicacion.
 */

import control.Controller;
import logic.multigames.games.Game;
import util.Direction;

public class MoveCommand extends Command {

    /** -Atributtes- */
    private Direction dir;

    /**
     * Constructor de MoveCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public MoveCommand() {
        super("move", " <direction>: Execute a move in one of the directions: up, down, left, right.");
        this.dir = null;
    }

    /**
     * Metodo que realiza la accion de comprobar si se va realizadr moviemnto o no.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    @Override
    public void execute(Game game, Controller controller) {
            if(game.move(this.dir)) {
                game.getArrayGameState().setSize(game.getArrayGameState().getSize() - game.getArrayGameState().getNumUndo());
                game.getArrayGameState().push(game.getState() );
                game.getArrayGameState().setNumUndo(0);
                controller.setDoPrintGame(true);
            }else{
                controller.setDoPrintGame(false);
                controller.setAuxText("No movement has been made.");
                controller.setDoPrintAuxText(true);
            }
    }

    /**
     * Metodo que controla el tratamiento del comando lanzado - Tratamiento especifico de Move por dos palabras.
     * @param commandWords String[] --> Array de candenas que tiene el comando lanzado para tratarlo
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     * @return Command --> Devuelve el objeto Command tratado y si no es perteneciente a este commando return: null
     */
    @Override
    public Command parse(String[] commandWords, Controller controller) {
        if (!this.commandName.equals(commandWords[0]) && commandWords.length == 1) {
            controller.setDoPrintAuxText(false);
            controller.setAuxText("Unknown command.  Use ’help’ to see the available commands.");
            return null;
        }else{
            if (this.commandName.equals(commandWords[0]) && commandWords.length >= 2) {
                // Parsear segunda palabra y meterla como direccion ( atributo de la clase move, crear nuevo objeto)
                MoveCommand moveCommand = new MoveCommand();

                moveCommand.dir = Direction.validDir(commandWords[1]);

                if (moveCommand.dir == null){
                    controller.setDoPrintAuxText(true);
                    controller.setAuxText("Unknown direction for move command. (up, down, left or right)");
                    moveCommand = null;
                }

                return moveCommand;
            } else {
                controller.setDoPrintAuxText(true);
                controller.setAuxText("Move must be followed by a direction: up, down, left, right.");
                return null;
            }
        }
    }
}
