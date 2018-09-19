package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Copies a file to another location on the disk.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "copy";
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();
  /**
   * The size of the buffer used for reading/writing to files.
   */
  private static final int BUFFER_SIZE = 4096;

  static {
    COMMAND_DESCRIPTION.add("Expects two arguments.");
    COMMAND_DESCRIPTION.add("Source file name and destination file name");
    COMMAND_DESCRIPTION.add("If the second argument is a directory the command"
        + " copies the original file into that directory using the original file name.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {

    String[] parameters = Util.splitArguments(arguments);

    if (arguments.equals("") || parameters.length != 2) {
      env.writeln("You must provide exactly two arguments:"
          + " the path to the source and the destination file.");
      return ShellStatus.CONTINUE;
    }

    Path workingDirectory = env.getCurrentDirectory();
    Path source = workingDirectory.resolve(parameters[0]);
    
    if (!Files.isRegularFile(source)) {
      env.writeln(source + " isn't a file on your system.");
      return ShellStatus.CONTINUE;
    }
    Path destination = workingDirectory.resolve(parameters[1]);
    
    if (Files.isDirectory(destination)) {
      destination = Paths.get(destination.toString() + "/" + source.getFileName());
    }
    if (Files.exists(destination)) {
      env.writeln("File " + destination.toString() + " already exists, overwrite? (yes/no)");
      String answer = env.readLine();
      if (answer.equals("yes")) {
        // nothing
      } else if (answer.equals("no")) {
        return ShellStatus.CONTINUE;
      } else {
        env.writeln("Wrong answer specified, skipping.");
        return ShellStatus.CONTINUE;
      }
    }
    try {
      InputStream reader = Files.newInputStream(source);
      OutputStream writter = Files.newOutputStream(destination);
      byte[] buffer = new byte[BUFFER_SIZE];
      while (reader.read(buffer) > 0) {
        writter.write(buffer);
      }
    } catch (IOException e) {
      env.writeln("Error while copying.");
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
