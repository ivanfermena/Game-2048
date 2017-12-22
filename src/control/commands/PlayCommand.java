package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando que realiza el cambio de juego y las especificaciones de estos dentro la aplicacion.
 */

import control.Controller;
import logic.multigames.games.Game;
import logic.multigames.games.Rules2048;
import logic.multigames.games.RulesFib;
import logic.multigames.games.RulesInverse;
import util.GameType;
import java.util.Scanner;

public class PlayCommand extends Command {

    /** -Atributtes- */
    private GameType gameType;
    private int initCells;
    private long rand;
    private final int defaultBoardSize = 4;
    private final int defaultInitCells = 2;

    /**
     * Constructor de PlayCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public PlayCommand() {
        super("play", " <game>: Start a new game of one of the game types: original, fib, inverse.");
    }

    /**
     * Metodo que controla si se introduce bien el comando play y los trata para realizarlos correctamente.
     * @param commandWords String[] --> Array de candenas que tiene el comando lanzado para tratarlo
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     * @return Command --> Devuelve el objeto Command tratado y si no es perteneciente a este commando return: null
     */
    @Override
    public Command parse(String[] commandWords, Controller controller){
        if (!this.commandName.equals(commandWords[0]) && commandWords.length == 1) {
            if(!controller.isDoPrintAuxText()){
                controller.iniTextUnknown();
            }
            return null;
        }else {
            if (this.commandName.equals(commandWords[0]) && commandWords.length >= 2) {
                PlayCommand playCommand = new PlayCommand();
                playCommand.gameType = GameType.validGT(commandWords[1]);
                if (playCommand.gameType == null){
                    controller.setDoPrintAuxText(true);
                    controller.setAuxText("Unknown game type for play command. ( original(orig), fibonacci(fib) or inverse(inv))");
                    playCommand = null;
                }
                return playCommand;
            } else {
                if(!controller.isDoPrintAuxText()) {
                    controller.setAuxText("Play must be followed by a game type: original(orig), fibonacci(fib), inverse(inv)");
                    controller.setDoPrintAuxText(true);
                }
                return null;
            }
        }
    }

    /**
     * Metodo que trata que juego se va a ejecutar y recoge las especificaciones de este por teclado. Tras esto lanza el juego nuevo y resetea con las nuevas normas.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    public void execute(Game game, Controller controller) {

        boolean validInput = true;

        // CONTROL DE SIZE
        controller.printSoutText("Please enter the size of the board: ");
        String auxBoardSize = controller.readLineScanner();

        if(auxBoardSize.isEmpty()){
            game.setSize(this.defaultBoardSize);
            auxBoardSize = String.valueOf(this.defaultBoardSize);
            controller.printSoutText("Using the default size of the board: " + this.defaultBoardSize);
        }
        else {
            do {
                try{
                    game.setSize(Integer.parseInt(auxBoardSize));
                    validInput = Integer.parseInt(auxBoardSize) <= 1;
                }catch (NumberFormatException e){
                    controller.printSoutText("ERROR: The size of the board must be positive and larger than 1.");
                    controller.printSoutText("Please enter the size of the board again: ");
                    auxBoardSize = controller.readLineScanner();
                }

            }while(!validInput);
        }

        // CONTROL DE INICELL
        validInput = true;
        controller.printSoutText("Please enter the number of initial cells: ");
        String auxInitCells =  controller.readLineScanner();

        if (auxInitCells.isEmpty()) {
            this.initCells = game.setInitCells(this.defaultInitCells);
            controller.printSoutText("Using the default number of initial cells: " + this.initCells);
        }else {
            do {
                validInput = (Integer.parseInt(auxInitCells) > Integer.parseInt(auxBoardSize) * Integer.parseInt(auxBoardSize));
                try{
                    game.setSize(Integer.parseInt(auxInitCells));

                }catch (NumberFormatException e){
                    validInput = false;
                    controller.printSoutText("ERROR: The number of initial cells must be less than the number of cells on the board");
                    controller.printSoutText("Please enter the size of the board again: ");
                    auxBoardSize = controller.readLineScanner();
                }

            }while(!validInput);
        }

        // CONTROL DE SEED
        validInput = true;
        controller.printSoutText("Please enter the seed for the pseudo-random number generator: ");
        String auxRand =  controller.readLineScanner();
        try {
            if (auxRand.isEmpty()) {
                this.rand = game.setNewRandom(1000);
                controller.printSoutText("Using the default seed for the pseudo-random number generator: " + this.rand);
            } else game.setSeed(Long.parseLong(auxRand));
        }catch (NumberFormatException e){
            throw new NumberFormatException("The seed for the pseudo-random number generator must be a valid number. ");
        }

        switch(this.gameType){
            case ORIG: game.setCurrentRules(new Rules2048()); break;
            case FIB: game.setCurrentRules(new RulesFib()); break;
            case INV: game.setCurrentRules(new RulesInverse()); break;
        }
        game.reset();
        controller.setAuxText("Has cambiado el juego actual\n");
        controller.setDoPrintAuxText(true);
        controller.setDoPrintGame(true);
    }
}
