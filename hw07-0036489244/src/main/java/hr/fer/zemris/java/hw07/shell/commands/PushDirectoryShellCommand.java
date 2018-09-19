package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which pushes the current working directory on the stack and sets a
 * new one.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class PushDirectoryShellCommand implements ShellCommand {

  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "pushd";

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
    COMMAND_DESCRIPTION.add("Command expects a single argument: directory name.");
    COMMAND_DESCRIPTION.add("This directory becomes the working directory.");
    COMMAND_DESCRIPTION
        .add("The previous working directory gets stored in the shell's shared memory.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.isEmpty()) {
      env.writeln("Method expects an argument: directory name.");
      return ShellStatus.CONTINUE;
    }
    Path workingDirectory = env.getCurrentDirectory();
    Path path = workingDirectory.resolve(Util.parseArgument(arguments));
    if (!Files.isDirectory(path)) {
      env.writeln("The given directory is invalid or doesn't exist.");
      return ShellStatus.CONTINUE;
    }

    @SuppressWarnings("unchecked")
    Stack<Path> stack = (Stack<Path>) env.getSharedData(STACK_KEY);
    if (stack == null) {
      stack = new Stack<>();
    }
    stack.push(env.getCurrentDirectory().toAbsolutePath().normalize());
    env.setSharedData(STACK_KEY, stack);
    env.commands().get("cd").executeCommand(env, arguments);
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
