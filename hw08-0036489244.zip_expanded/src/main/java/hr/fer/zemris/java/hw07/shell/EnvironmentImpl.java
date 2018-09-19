package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CpTreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropDirectoryShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListDirectoriesShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassRenameShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopDirectoryShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushDirectoryShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmTreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Implementation of the {@link Environment}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class EnvironmentImpl implements Environment {

  /**
   * Symbol that is written at the beginning of a multi-line block.
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
   * Field represent the current working directory on the file system.
   */
  private Path currentDirectory = Paths.get(".");

  /**
   * Internal map of data objects shared between different {@link ShellCommand}
   * implementations.
   */
  private Map<String, Object> sharedData = new HashMap<>();

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
    commands.put("pwd", new PwdShellCommand());
    commands.put("cd", new CdShellCommand());
    commands.put("pushd", new PushDirectoryShellCommand());
    commands.put("popd", new PopDirectoryShellCommand());
    commands.put("listd", new ListDirectoriesShellCommand());
    commands.put("dropd", new DropDirectoryShellCommand());
    commands.put("rmtree", new RmTreeShellCommand());
    commands.put("cptree", new CpTreeShellCommand());
    commands.put("massrename", new MassRenameShellCommand());
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

  @Override
  public Path getCurrentDirectory() {
    return currentDirectory;
  }

  @Override
  public void setCurrentDirectory(Path path) {
    currentDirectory = path;
  }

  @Override
  public Object getSharedData(String key) {
    return sharedData.get(key);
  }

  @Override
  public void setSharedData(String key, Object value) {
    sharedData.put(key, value);
  }
  
}
