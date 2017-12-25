package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando de ayuda de la aplicacion.
 */

import control.Controller;
import logic.multigames.games.Game;

import static util.CommandParser.commandHelp;

public class HelpCommand extends NoParamsCommand {


    private static final String CommandInfo = "help";
    private static final String HelpInfo = ": Print this help message.";
    /**
     * Constructor de HelpCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public HelpCommand() {
        super(CommandInfo, HelpInfo);
    }

    /**
     * Metodo que realiza la accion de lanzar todos los comandos de help de los comandos y activar el texto.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    public boolean execute(Game game, Controller controller)
    {
        String helpText = commandHelp();
        controller.setAuxText(helpText);
        controller.setDoPrintAuxText(true);
        return false;
    }

    /**
     * Metodo que genera una nueva instancia de HelpCommand.
     * @return Command --> Devuelve aquella instancia de HelpCommand creada.
     */
    public Command createCommand(){
        return new HelpCommand();
    }
}
