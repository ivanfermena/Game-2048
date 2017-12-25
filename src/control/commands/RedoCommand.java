package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla que se pueda volver a un estado mas reciente del juego.
 */

import control.Controller;
import exceptions.CommandExecuteException;
import logic.multigames.games.Game;

import java.util.EmptyStackException;

public class RedoCommand extends NoParamsCommand{

    private static final String CommandInfo = "redo";
    private static final String HelpInfo = ": Redo the last undone command.";
    /**
     * Constructor de RedoCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public RedoCommand() {
        super(CommandInfo, HelpInfo);
    }

    /**
     * Metodo que realiza la accion de comprobar si se puede hacer redo, si es asi lo lanza, si no lo notifica.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException {
        try {
            game.redo();
            return true;
        }
        catch(EmptyStackException e){
            throw new CommandExecuteException("The redo command could not be completed.\n");
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
