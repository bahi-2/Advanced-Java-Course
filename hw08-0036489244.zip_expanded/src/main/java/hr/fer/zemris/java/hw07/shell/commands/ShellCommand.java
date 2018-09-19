package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Interface for console commands.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public interface ShellCommand {
  /**
   * Executes the command.
   * 
   * @param env
   *          environment implementation
   * @param arguments
   *          arguments with which to execute a given command
   * @return status of the shell after execution
   */
  ShellStatus executeCommand(Environment env, String arguments);

  /**
   * Returns the command name.
   * 
   * @return the command name.
   */
  String getCommandName();

  /**
   * Returns the command's description.
   * 
   * @return the command's description
   */
  List<String> getCommandDescription();
}
