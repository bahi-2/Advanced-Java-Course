package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Prints out an intented recursive listing of a directory.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "tree";
  /**
   * Description of the command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command expects a single argument: directory name and prints a tree.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.equals("")) {
      env.writeln("You must provide exactly one argument: the path of the directory to list.");
      return ShellStatus.CONTINUE;
    }

    Path path = Paths.get(arguments);
    if (!Files.isDirectory(path)) {
      env.writeln("Given path isn't a directory on your system.");
      return ShellStatus.CONTINUE;
    }
    try {
      printTree(env, path.toFile(), 0);
    } catch (IOException e) {
      env.writeln("Error while traversing the directory.");
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

  /**
   * Auxiliary recursive method for printing the recursive listing of a directory.
   * 
   * @param env
   *          instance of the Environment
   * @param directory
   *          directory to traverse
   * @param depth
   *          starting depth (should be 0)
   * @throws IOException
   *           in case of file access errors
   */
  private void printTree(Environment env, File directory, int depth) throws IOException {
    final int INDENT_SIZE = 2;

    if (depth == 0) {
      env.writeln(String.format("%s", directory.getAbsolutePath()));
    } else {
      env.write(String.format("%" + (INDENT_SIZE * depth) + "s%s%n", "", directory.getName()));
    }

    File[] children = directory.listFiles();
    if (children != null) {
      for (File child : children) {
        if (child.isDirectory()) {
          printTree(env, child, depth + 1);
        } else {
          env.write(
              String.format("%" + (INDENT_SIZE * (depth + 1)) + "s%s%n", "", child.getName()));
        }
      }
    }
  }
}
