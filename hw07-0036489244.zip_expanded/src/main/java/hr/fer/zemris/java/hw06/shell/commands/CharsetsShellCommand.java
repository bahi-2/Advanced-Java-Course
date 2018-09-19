package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command lists all available charsets from the system.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {
  
  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "charsets";
  
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command lists names of supported charsets for your Java platform.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    Charset.availableCharsets().keySet().forEach(env::writeln);
    return ShellStatus.CONTINUE;
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
