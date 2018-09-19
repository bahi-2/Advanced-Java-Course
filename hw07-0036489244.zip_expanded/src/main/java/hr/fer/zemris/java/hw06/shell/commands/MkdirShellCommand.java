package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Creates a directory on the disk.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {
  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "mkdir";
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command expects a single argument: directory path "
        + " and creates that directory on the disk.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.length() == 0) {
      env.writeln("Command expects an argument. Directory to create.");
      return ShellStatus.CONTINUE;
    }

    Path dir = Paths.get(arguments);
    try {
      Files.createDirectory(dir);
    } catch (IOException e) {
      env.writeln("Unable to create the directory, make sure the path is valid");
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
