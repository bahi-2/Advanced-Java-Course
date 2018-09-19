package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Displays symbols used for PROMPT, MORE-LINES, MULTI-LINE, and can modify
 * them.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

  /**
   * Symbol used for PROMPT.
   */
  private static final String PROMPT = "PROMPT";
  /**
   * Symbol used for MORE-LINES.
   */
  private static final String MORE_LINES = "MORELINES";
  /**
   * Symbol used for MULTI-LINE.
   */
  private static final String MULTILINE = "MULTILINE";
  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "symbol";
  /**
   * Description of the command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command can modify or display the current value of a symbol.");
    COMMAND_DESCRIPTION.add("symbol SYMBOL_NAME [NEW_SYMBOL]");
    COMMAND_DESCRIPTION
        .add("Supported symbol names are (CASE SENSITIVE): PROMPT, MORELINES, MULTILINE");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.equals("")) {
      env.writeln("Symbol command expects 1 or 2 arguments.");
      return ShellStatus.CONTINUE;
    }

    String[] parameters = arguments.split(" ");
    if (parameters.length > 2) {
      env.writeln("Invalid number of arguments for command SYMBOL, expected 1 or 2, but got: "
          + parameters.length);
      return ShellStatus.CONTINUE;
    }

    String symbolName = parameters[0];

    if (parameters.length == 1) {
      switch (symbolName) {
        case PROMPT:
          printCurrentSymbol(env, symbolName, env.getPromptSymbol());
          break;
        case MORE_LINES:
          printCurrentSymbol(env, symbolName, env.getMorelinesSymbol());
          break;
        case MULTILINE:
          printCurrentSymbol(env, symbolName, env.getMultilineSymbol());
          break;
        default:
          env.writeln("Symbol " + symbolName + " doesn't exist.");
          break;
      }
    } else if (parameters.length == 2) {
      if (parameters[1].length() > 1) {
        env.writeln("Symbol must be one character long.");
        return ShellStatus.CONTINUE;
      }
      char newSymbol = parameters[1].charAt(0);
      switch (symbolName) {
        case PROMPT:
          printSymbolChange(env, symbolName, env.getPromptSymbol(), newSymbol);
          env.setPromptSymbol(newSymbol);
          break;
        case MORE_LINES:
          printSymbolChange(env, symbolName, env.getMorelinesSymbol(), newSymbol);
          env.setMorelinesSymbol(newSymbol);
          break;
        case MULTILINE:
          printSymbolChange(env, symbolName, env.getMultilineSymbol(), newSymbol);
          env.setMultilineSymbol(newSymbol);
          break;
        default:
          env.writeln("Symbol " + symbolName + " doesn't exist.");
          break;
      }
    }
    return ShellStatus.CONTINUE;
  }

  /**
   * Auxiliary method to write out to console the value of the symbol.
   * @param env Environmet implementation
   * @param symbolName name of the symbol
   * @param symbol value of the symbol
   */
  private void printCurrentSymbol(Environment env, String symbolName, Character symbol) {
    env.writeln("Symbol for " + symbolName + " is '" + symbol + "'");
  }

  /**
   * Auxiliary method to write out to console the value change of the symbol.
   * 
   * @param env Environmet implementation
   * @param symbolName name of the symbol
   * @param oldSymbol old value
   * @param newSymbol new value
   */
  private void printSymbolChange(Environment env, String symbolName, char oldSymbol,
      char newSymbol) {
    env.writeln(
        "Symbol for " + symbolName + " changed from '" + oldSymbol + "' to '" + newSymbol + "'");
  }

  @Override
  public String getCommandName() {
    return COMMAND_NAME;
  }

  @Override
  public List<String> getCommandDescription() {
    return COMMAND_DESCRIPTION;
  }

}
