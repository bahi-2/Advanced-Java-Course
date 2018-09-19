package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command lists directories from the directory stack.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ListDirectoriesShellCommand implements ShellCommand {


  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "listd";

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
    COMMAND_DESCRIPTION.add("Lists all directories on the stack.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    @SuppressWarnings("unchecked")
    Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK_KEY);
    if (stack==null || stack.isEmpty()) {
      env.writeln("Stack is empty. No directories stored.");
      return ShellStatus.CONTINUE;
    }
    while (!stack.isEmpty()) {
      Path dir = stack.pop();
      env.writeln(dir.toString());
    }
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
