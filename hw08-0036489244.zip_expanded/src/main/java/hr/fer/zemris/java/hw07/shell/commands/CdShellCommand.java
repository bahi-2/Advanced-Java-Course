package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which changes the current working directory.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CdShellCommand implements ShellCommand {

  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "cd";

  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION
        .add("Changes the current working directory to the one given in the argument.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {

    Path workingDirectory = env.getCurrentDirectory();
    Path newWorkingDirectory = workingDirectory.resolve(Util.parseArgument(arguments));
    if (Files.isDirectory(newWorkingDirectory)) {
      env.setCurrentDirectory(newWorkingDirectory);
    } else {
      env.writeln("No such directory.");
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
