package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which writes out file content.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "cat";
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command cat takes one or two arguments.");
    COMMAND_DESCRIPTION.add("The first argument is path to some file and is mandatory.");
    COMMAND_DESCRIPTION.add(
        "The second argument is charset name that should be used to interpret chars from bytes.");
    COMMAND_DESCRIPTION.add("If not provided, a default platform charset should be used.");
    COMMAND_DESCRIPTION.add("This command opens given file and writes its content to console");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.equals("")) {
      env.writeln("You must provide at least one argument: the path of the file to print.");
      return ShellStatus.CONTINUE;
    }

    String[] parameters = Util.splitArguments(arguments);

    Charset charset;
    if (parameters.length == 2) {
      try {
        charset = Charset.forName(parameters[1]);
      } catch (Exception ex) {
        env.writeln("Unsupported charset chosen. Using system default charset.\n");
        charset = Charset.defaultCharset();
      }
    } else {
      charset = Charset.defaultCharset();
    }

    Path workingDirectory = env.getCurrentDirectory();
    Path path = workingDirectory.resolve(Util.parseArgument(parameters[0]));
    if (!(Files.isRegularFile(path))) {
      env.writeln("Cannot read from the file specified. Make sure the path is correct.");
      return ShellStatus.CONTINUE;
    }
    try {
      BufferedReader reader = Files.newBufferedReader(path, charset);
      reader.lines().forEach(env::writeln);
    } catch (IOException e) {
      env.writeln("Error occured while reading from the file.");
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
