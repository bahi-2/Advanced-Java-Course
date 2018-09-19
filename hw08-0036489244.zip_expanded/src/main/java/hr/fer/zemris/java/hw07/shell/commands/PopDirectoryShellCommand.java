package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Pops the path from the stack and sets it as the new working directory.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class PopDirectoryShellCommand implements ShellCommand {

  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "popd";

  /**
   * Key for the shared memory data <code>Object</code> containing previous
   * working directories.
   */
  private static final String STACK_KEY = "cdstack";
  /**
   * Description of the command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command expects no arguments.");
    COMMAND_DESCRIPTION
        .add("Pops the path from the stack and sets it as the new working directory.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    @SuppressWarnings("unchecked")
    Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK_KEY);
    String dir = null;
    try {
      dir = stack.pop().toString();
    } catch (EmptyStackException | NullPointerException ex) {
      env.writeln("Can't pop from an empty stack");
      return ShellStatus.CONTINUE;
    }
    env.commands().get("cd").executeCommand(env, dir);
    env.writeln("Working directory changed to  " + env.getCurrentDirectory());
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
