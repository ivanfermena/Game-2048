package control.commands;

import control.Controller;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.GameOverException;
import logic.multigames.games.Game;
import util.GameType;

import java.io.*;

public class SaveCommand extends Command{

    private static final String CommandInfo = "save";
    private static final String HelpInfo = ": Save help.";

    private File inputFile;

    /**
     * Constructor default: super --> Command
     */
    public SaveCommand(File inputFile){
        super(CommandInfo, HelpInfo);
        this.inputFile = inputFile;
    }

    @Override
    public Command parse(String[] commandWords, Controller controller) throws CommandParserException {

        if (!this.commandName.equals(commandWords[0])) {
            return null;
        }else {
            if(commandWords.length == 1) throw new CommandParserException("Load command must be followed by a filename.\n");
            else if (commandWords.length==2) {
                String auxStr = GameType.conﬁrmFileNameStringForWrite(commandWords[1], controller);
                if (auxStr != null) {
                    this.inputFile = new File(auxStr);
                    return new SaveCommand(inputFile);
                } else throw new CommandParserException("Save must be followed by a valid filename.\n");
            } else throw new CommandParserException("Invalid Command: the filename contains spaces.\n");
        }
    }

    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException {

        try(FileWriter input = controller.isBoolOverwrite() ? new FileWriter(inputFile, controller.isBoolOverwrite()) : new FileWriter(inputFile);
            BufferedWriter bufInput = new BufferedWriter(input)){

            game.store(bufInput);
            controller.printSoutText("Game successfully saved to file; use load command to reload it.\n");

        }catch(IOException | NullPointerException ioe){
            throw new CommandExecuteException("The save command could not be completed because of a file fail.\n");
        }
        return true;
    }


}
