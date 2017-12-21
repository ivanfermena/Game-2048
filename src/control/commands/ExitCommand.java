package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando para salir de la aplicacion.
 */

import control.Controller;
import logic.multigames.games.Game;

public class ExitCommand extends NoParamsCommand {

    /**
     * Constructor de ExitCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public ExitCommand() {
        super("exit", ": Terminate the program.");
    }

    /**
     * Metodo que realiza la accion de mostrar el texto expecifico y lanzar el final del juego.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    public void execute(Game game, Controller controller)
    {
        controller.setAuxText("Bye bye !");
        controller.setDoPrintAuxText(true);
        controller.setDoPrintGame(false);
        game.setEndGame(true);
    }

    /**
     * Metodo que genera una nueva instancia de ExitCommnans.
     * @return Command --> Devuelve aquella instancia del objeto creado.
     */
    public Command createCommand(){
        return new ExitCommand();
    }
}
