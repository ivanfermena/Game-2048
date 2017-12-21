package control;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase para controlar la ejecución del juego, preguntando al usuario qué quiere hacer y actualizando la partida de acuerdo a lo que éste indique.
 */

import control.commands.Command;
import logic.multigames.games.Game;
import util.CommandParser;
import java.util.Scanner;

public class Controller {

    /** -Atributtes- */
    private Game game;
    private Scanner in;
    private boolean doPrintGame;
    private boolean doPrintAuxText;
    private String auxText;

    /**
     * Constructor de Controller
     * @param game Game --> Juego determinado que vamos a jugar y tratar.
     * @param in --> Scanner usado para la entrada estandar
     */
    public Controller(Game game, Scanner in) {
        this.game = game;
        this.in = in;
        this.doPrintGame = true;
        this.doPrintAuxText = false;
        this.auxText = "Unknown command.  Use ’help’ to see the available commands.";
    }

    /**Getters and Setters*/
    public void setDoPrintGame(boolean doPrintGame) {
        this.doPrintGame = doPrintGame;
    }
    public void setDoPrintAuxText(boolean doPrintAuxText) {
        this.doPrintAuxText = doPrintAuxText;
    }
    public void setAuxText(String auxText) {
        this.auxText = auxText;
    }
    public boolean isDoPrintAuxText() {
        return doPrintAuxText;
    }

    /**
     * Metodo que controla  que la partida no esté finalizada, solicita una orden al usuario (move, reset, exit) y
     * la ejecuta invocando a algún método de la clase game. Comando help que imprime la ayuda del juego.
     */
    public void run(){
        if(this.doPrintGame)
            System.out.println(this.game);
        while (!this.game.isEndGame())
        {
            iniTextUnknown();
            setDoPrintAuxText(false);
            System.out.print("Command >> ");
            String userComand = this.in.nextLine().trim().toLowerCase();
            String[] userCommands = userComand.split(" ");
            Command finalCommand = CommandParser.parseCommand(userCommands, this);

            if (finalCommand == null){
                this.setDoPrintGame(false);
            }
            else {
                finalCommand.execute(this.game, this);
                if (this.game.isWonGame()) {
                    this.setDoPrintAuxText(true);
                    this.setAuxText("You won ! :)");
                    this.game.setEndGame(true);
                }
                else if(this.game.isLostGame()){
                    this.setDoPrintAuxText(true);
                    this.setAuxText("You lost ! :(");
                    this.game.setEndGame(true);
                }
            }
            if(this.doPrintGame)
                System.out.println(this.game);
            if(this.doPrintAuxText){
                System.out.println(this.auxText);
                this.setDoPrintAuxText(false);}

        }

    }

    /**
     * Metodo que inicia por defecto y activa el texto a mostrar si no existe el comando introducido.
     */
    public void iniTextUnknown(){
        this.setAuxText("Unknown command.  Use ’help’ to see the available commands.");
        this.setDoPrintAuxText(true);
    }

    /**
     * Controla la gestion de volver a iniciar o acabar la partida determinada.
     */
    /*private void gameOver() {   PENDIENTE DE BORRAR

       System.out.println(this.game);
       System.out.print("\nDo you want to play a new game?\t>>\t");
       String userComand = this.in.nextLine();
       String userFirst = userComand.split(" ")[0].trim().toLowerCase();

       switch (userFirst){
           case "yes":
               System.out.println("\n\n\n\n\n\n\n\n\n\n");
               this.game.reset();
               this.run();
               break;
           case "no":
               System.out.println("~ ByeBye ~");
               break;
           default:
               System.out.println("Unknown command");
               this.gameOver();
       }

   }*/
}
