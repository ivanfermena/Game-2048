package util;

import control.Controller;
import exceptions.CommandParserException;
import logic.multigames.GameRules;
import logic.multigames.games.Rules2048;
import logic.multigames.games.RulesFib;
import logic.multigames.games.RulesInverse;

import java.io.File;

public enum GameType
{
    ORIG("2048, original version", "original", new Rules2048()),
    FIB("2048, Fibonacci version", "fib", new RulesFib()),
    INV("2048, inverse version", "inverse", new RulesInverse());

    private String userFriendlyName;
    private String parameterName;
    private GameRules correspondingRules;

    public static final String ﬁlenameInUseMsg = "The ﬁle already exists ; do you want to overwrite it ? (Y/N)";

    GameType(String friendly, String param, GameRules rules){
        userFriendlyName = friendly;
        parameterName = param;
        correspondingRules = rules;
    }
    //

    /**
     * precondition : param string contains only lower−case characters
     * used in PlayCommand and Game, in parse method and load method, respectively
     * @param param
     * @return
     */
    public static GameType parse(String param) {
        for (GameType gameType : GameType.values()) {
            if (gameType.parameterName.equals(param))
                return gameType;
        }
        return null;
    }

    /**
     * Used in PlayCommand to build help message, and in parse method exception ms
     * @return
     */
    public static String externaliseAll () {
        String s = "";
        for (GameType type : GameType.values())
            s = s + " " + type.parameterName + ",";
        return s.substring(1, s.length() - 1);
    }

    /**
     * Used in Game when constructing object and when executing play command
     */
    public GameRules getRules() { return correspondingRules; }

    /**
     *  Used in Game in store method
     */
    public String externalise () { return parameterName; }

    /**
     * Used PlayCommand and LoadCommand, in parse methods
     * in ack message and success message, respectively
     */
    public String toString() {return userFriendlyName; }

    /**
     * Metodo que gestiona la confirmacion del nombre de fichero
     * @param ﬁlenameString
     * @param controller
     * @return
     * @throws CommandParserException
     */
    public static String conﬁrmFileNameStringForWrite(String ﬁlenameString, Controller controller) throws CommandParserException {
        String loadName = ﬁlenameString;
        boolean ﬁlename_conﬁrmed = false;
        while (!ﬁlename_conﬁrmed) {
            if (MyStringUtils.validFileName(loadName)) {
                File ﬁle = new File(loadName);
                if (!ﬁle.exists())
                    ﬁlename_conﬁrmed = true;
                else {
                    loadName = getLoadName(ﬁlenameString, controller);
                    ﬁlename_conﬁrmed = true;
                }
            } else {
                throw new CommandParserException("Invalid filename: the filename contains invalid characters.\n");
            }
        }
        return loadName;
    }

    /**
     * Metodo que controla y gestiona si se sobreescribe un fichero si este existe
     * @param filenameString
     * @param controller
     * @return
     * @throws CommandParserException
     */
    private static String getLoadName(String filenameString, Controller controller) throws CommandParserException {
        String newFilename = null;
        boolean yesOrNo = false;
        while (!yesOrNo) {
            System.out.print(ﬁlenameInUseMsg + ": ");
            String[] responseYorN = controller.readLineScanner().toLowerCase().trim().split("\\s+");
            if (responseYorN.length == 1) {
                switch (responseYorN[0]) {
                    case "y":
                        yesOrNo = true;
                        newFilename = filenameString;
                        controller.setBoolOverwrite(false);
                        break;
                    case "n":
                        yesOrNo = true;
                        controller.printSoutText("Please enter another filename: ");
                        newFilename = controller.readLineScanner();
                        controller.setBoolOverwrite(true);
                        responseYorN = newFilename.toLowerCase().trim().split("\\s+");
                        if (responseYorN.length != 1) throw new CommandParserException("Invalid filename: the filename contains spaces.\n");
                        newFilename = GameType.conﬁrmFileNameStringForWrite(newFilename, controller);
                        break;
                    default:
                        controller.printSoutText("Please answer ’Y’ or ’N’\n");
                }
            } else {
                throw new CommandParserException("Invalid filename: the filename contains spaces.\n");
            }
        }
        return newFilename;
    }
}
