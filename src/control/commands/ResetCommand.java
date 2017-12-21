package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando de reset de la aplicacion.
 */

import control.Controller;
import logic.multigames.games.Game;

public class ResetCommand extends NoParamsCommand {

    /**
     * Constructor de ResetCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public ResetCommand() {
        super("reset", ": Start a new game.");
    }

    /**
     * Metodo que lanza la accion reset y activa los mensaje determinados.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    @Override
    public void execute(Game game, Controller controller) {
        game.reset();
        controller.setDoPrintGame(true);
        controller.setDoPrintAuxText(false);
    }

    /**
     * Metodo que genera una nueva instancia de ResetCommand.
     * @return Command --> Devuelve aquella instancia de ResetCommand creada.
     */
    public Command createCommand(){
        return new ResetCommand();
    }
}
