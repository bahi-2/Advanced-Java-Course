package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command pops the path from the stack and sets it as the new working directory.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class DropDirectoryShellCommand implements ShellCommand {

  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "dropd";

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

    if (stack == null || stack.isEmpty()) {
      env.writeln("Stack is empty, nothing to drop.");
      return ShellStatus.CONTINUE;
    }

    String dir = stack.pop().toString();
    env.writeln("Dropped directory: " + dir);
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
