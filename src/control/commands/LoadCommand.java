package control.commands;

import control.Controller;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.GameOverException;
import logic.multigames.games.Game;
import util.GameType;
import util.MyStringUtils;

import java.io.*;

public class LoadCommand extends Command {

    private static final String CommandInfo = "load";
    private static final String HelpInfo = ": Load.";

    private File inputFile;

    /**
     * Constructor default: super --> Command
     */
    public LoadCommand(File inputFile) {
        super(CommandInfo, HelpInfo);
        this.inputFile = inputFile;
    }

    @Override
    public Command parse(String[] commandWords, Controller controller) throws CommandParserException {

        if (!this.commandName.equals(commandWords[0])) {
            return null;
        }else {
            if(commandWords.length == 1)
                 throw new CommandParserException("Load must be followed by a filename.\n");
            else if (commandWords.length==2) {
                this.inputFile = new File(commandWords[1]);
                LoadCommand loadCommand = null;

                if (!inputFile.exists())
                    throw new CommandParserException("File not found.\n");
                else if (!MyStringUtils.canReaderLocal(inputFile))
                    throw new CommandParserException("File with restricted permissions.\n");
                else
                    loadCommand = new LoadCommand(inputFile);
                return loadCommand;
            }
            else throw new CommandParserException("Invalid filename: the filename contains spaces.\n");
        }

    }

    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException, GameOverException, CommandExecuteException {

        try(FileReader input = new FileReader(this.inputFile);
            BufferedReader bufInput = new BufferedReader(input)){ // Habria que hacerlo resource pero habria que ver como controlar que sobreescriba bien

            game.load(bufInput);

        }catch ( FileNotFoundException e){
            throw new CommandExecuteException("The redo command could not be completed.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
