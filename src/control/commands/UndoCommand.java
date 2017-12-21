package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla que se pueda volver a un estado mas antiguo del juego. Maximo 20 undos.
 */

import control.Controller;
import logic.multigames.games.Game;

public class UndoCommand extends NoParamsCommand {

    /**
     * Constructor de UndoCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public UndoCommand() {
        super("undo", ": Undo the last command. (max: 20)");
    }

    /**
     * Metodo que comprueba que se pueda volver a un estado anterior del juego.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    @Override
    public void execute(Game game, Controller controller) {
        if(game.getArrayGameState().isEmpty() || game.getArrayGameState().getSize() - 2 - game.getArrayGameState().getNumUndo() < 0){
            controller.setAuxText("The undo command could not be completed.");
            controller.setDoPrintAuxText(true);
            controller.setDoPrintGame(false);
        }else{
            game.undo();
            controller.setDoPrintGame(true);
        }
    }

    /**
     * Metodo que genera una nueva instancia de UndoCommand.
     * @return Command --> Devuelve aquella instancia de UndoCommand creada.
     */
    public Command createCommand(){
        return new UndoCommand();
    }
}
