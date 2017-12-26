package control.commands;

import control.Controller;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.GameOverException;
import logic.multigames.games.Game;

public class LoadCommand extends Command {

    private static final String CommandInfo = "load";
    private static final String HelpInfo = ": Load.";

    /**
     * Constructor default: super --> Command
     */
    public LoadCommand() {
        super(CommandInfo, HelpInfo);
    }

    @Override
    public Command parse(String[] commandWords, Controller controller) throws CommandParserException {

        //comprobará que el ﬁchero proporcionado es válido y existe

        return null;
    }

    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException, GameOverException {
        return false;
    }

}
