package control;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase para controlar la ejecución del juego, preguntando al usuario qué quiere hacer y actualizando la partida de acuerdo a lo que éste indique.
 */

import control.commands.Command;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.GameOverException;
import logic.multigames.games.Game;
import util.CommandParser;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

    /** -Atributtes- */
    private Game game;
    private Scanner in;
    private boolean doPrintAuxText;
    private String auxText;
    private boolean boolOverwrite;

    /**
     * Constructor de Controller
     * @param game Game --> Juego determinado que vamos a jugar y tratar.
     * @param in --> Scanner usado para la entrada estandar
     */
    public Controller(Game game, Scanner in) {
        this.game = game;
        this.in = in;
        this.doPrintAuxText = false;
        this.auxText = "Unknown command.  Use ’help’ to see the available commands.";
        this.boolOverwrite = true;
    }

    /**Getters and Setters*/
    public void setDoPrintAuxText(boolean doPrintAuxText) {
        this.doPrintAuxText = doPrintAuxText;
    }
    public void setAuxText(String auxText) {
        this.auxText = auxText;
    }

    // ROMPE EL ENCAMPSULAMIENTO???

    public boolean isBoolOverwrite() {
        return boolOverwrite;
    }
    public void setBoolOverwrite(boolean boolOverwrite) {
        this.boolOverwrite = boolOverwrite;
    }

    public boolean isDoPrintAuxText() {
        return doPrintAuxText;
    }

    /**
     * Metodo que controla  que la partida no esté finalizada, solicita una orden al usuario (move, reset, exit) y
     * la ejecuta invocando a algún método de la clase game. Comando help que imprime la ayuda del juego.
     */
    public void run(){
        this.printGame();
        while (!this.game.isEndGame()) {
            try{
                iniTextUnknown();
                setDoPrintAuxText(false);
                System.out.print("Command >> ");
                String userCommand = this.in.nextLine().trim().toLowerCase();
                String[] userCommands = userCommand.split(" ");
                Command finalCommand = CommandParser.parseCommand(userCommands, this);
                if(finalCommand.execute(this.game, this))
                    this.printGame();
                if(this.doPrintAuxText){
                    System.out.println(this.auxText);
                    this.setDoPrintAuxText(false);}
            }catch (CommandParserException | CommandExecuteException  e){
                this.printSoutText(e.getMessage());
            }
            catch ( GameOverException goe){
                this.printGame();
                this.printSoutText(goe.getMessage());
            }
        }
    }

    /**
     * Metodo que inicia por defecto y activa el texto a mostrar si no existe el comando introducido.
     */
    public void iniTextUnknown(){
        this.setAuxText("Unknown command.  Use ’help’ to see the available commands.\n");
        this.setDoPrintAuxText(true);
    }

    /**
     * Metodo que controla la entrada de texto por Scanner, solicita la linea entera.
     * @return String --> Texto recogido por teclado.
     */
    public String readLineScanner(){
        return this.in.nextLine();
    }
    public void printSoutText(String tx){
        System.out.print(tx);
    }
    private void printGame(){ System.out.println(this.game);}
}
