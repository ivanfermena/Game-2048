package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla que se pueda volver a un estado mas reciente del juego.
 */

import control.Controller;
import logic.multigames.games.Game;

public class RedoCommand extends NoParamsCommand{

    /**
     * Constructor de RedoCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public RedoCommand() {
        super("redo", ": Redo the last undone command.");
    }

    /**
     * Metodo que realiza la accion de comprobar si se puede hacer redo, si es asi lo lanza, si no lo notifica.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    @Override
    public void execute(Game game, Controller controller) {
        if(game.getArrayGameState().isEmpty() || game.getArrayGameState().getNumUndo() == 0){
            controller.setAuxText("The redo command could not be completed.");
            controller.setDoPrintAuxText(true);
            controller.setDoPrintGame(false);
        }else{
            game.redo();
            controller.setDoPrintGame(true);
        }
    }

    /**
     * Metodo que genera una nueva instancia de RedoCommand.
     * @return Command --> Devualve aquella instancia de RedoCommand creada.
     */
    public Command createCommand(){
        return new RedoCommand();
    }
}
