package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command for terminating the shell session.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {
  
  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "exit";
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command terminates the shell.");
  }
  
  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    return ShellStatus.TERMINATE;
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
