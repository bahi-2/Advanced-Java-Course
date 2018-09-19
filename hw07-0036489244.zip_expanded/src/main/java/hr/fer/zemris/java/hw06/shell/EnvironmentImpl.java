package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Implementation of the {@link Environment}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class EnvironmentImpl implements Environment {

  /**
   * Symbol that is written at the beggining of a multi-line block.
   */
  private Character multilineSymbol = '|';
  /**
   * Prompt symbol for the console program.
   */
  private Character promptSymbol = '>';
  /**
   * Symbol for starting a multi-line block.
   */
  private Character morelinesSymbol = '\\';
  
  /**
   * A list of supported shell commands.
   */
  private final SortedMap<String, ShellCommand> commands = new TreeMap<>();
  
  /**
   * Filling the shell commands map.
   */
  {
    commands.put("symbol", new SymbolShellCommand());
    commands.put("exit", new ExitShellCommand());
    commands.put("charsets", new CharsetsShellCommand());
    commands.put("cat", new CatShellCommand());
    commands.put("ls", new LsShellCommand());
    commands.put("tree", new TreeShellCommand());
    commands.put("copy", new CopyShellCommand());
    commands.put("mkdir", new MkdirShellCommand());
    commands.put("hexdump", new HexDumpShellCommand());
    commands.put("help", new HelpShellCommand());
  }

  @Override
  public String readLine() throws ShellIOException {
    try {
      @SuppressWarnings("resource")
      Scanner sc = new Scanner(System.in);
      String line = sc.nextLine();
      return line;
    } catch (Exception ex) {
      throw new ShellIOException(ex.getMessage());
    }
  }

  @Override
  public void write(String text) throws ShellIOException {
    try {
      System.out.print(text);
    } catch (Exception ex) {
      throw new ShellIOException();
    }
  }

  @Override
  public void writeln(String text) throws ShellIOException {
    try {
      System.out.println(text);
    } catch (Exception ex) {
      throw new ShellIOException();
    }
  }

  @Override
  public SortedMap<String, ShellCommand> commands() {
    return commands;
  }

  @Override
  public Character getMultilineSymbol() {
    return multilineSymbol;
  }

  @Override
  public void setMultilineSymbol(Character symbol) {
    this.multilineSymbol = symbol;
  }

  @Override
  public Character getPromptSymbol() {
    return promptSymbol;
  }

  @Override
  public void setPromptSymbol(Character symbol) {
    this.promptSymbol = symbol;
  }

  @Override
  public Character getMorelinesSymbol() {
    return morelinesSymbol;
  }

  @Override
  public void setMorelinesSymbol(Character symbol) {
    this.morelinesSymbol = symbol;
  }

}
