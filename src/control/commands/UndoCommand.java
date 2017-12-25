package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla que se pueda volver a un estado mas antiguo del juego. Maximo 20 undos.
 */

import control.Controller;
import exceptions.CommandExecuteException;
import logic.multigames.games.Game;

import java.util.EmptyStackException;

public class UndoCommand extends NoParamsCommand {

    private static final String CommandInfo = "undo";
    private static final String HelpInfo = ": Undo the last command. (max: 20)";
    /**
     * Constructor de UndoCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public UndoCommand() {
        super(CommandInfo, HelpInfo);
    }

    /**
     * Metodo que comprueba que se pueda volver a un estado anterior del juego.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException{
        try {
            game.undo();
            return true;
        }
        catch(EmptyStackException e){
            throw new CommandExecuteException("The undo command could not be completed.\n");
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
