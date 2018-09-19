package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command copies the files and folders from directory A to directory B
 * recursively.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class CpTreeShellCommand implements ShellCommand {

  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "cptree";
  /**
   * Description of the command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  /**
   * Destination file path.
   */
  private Path destination;

  static {
    COMMAND_DESCRIPTION.add("Command expects two arguments: directory names.");
    COMMAND_DESCRIPTION.add("The first argument is the source directory.");
    COMMAND_DESCRIPTION.add(
        "The second argument is the destination directory where you want to copy source's content.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    String[] parameters = Util.splitArguments(arguments);
    if (arguments.equals("") || parameters.length < 2) {
      env.writeln("You must provide exactly two arguments:"
          + " the path of the directory to copy and the destination path.");
      return ShellStatus.CONTINUE;
    }

    Path workingDirectory = env.getCurrentDirectory();
    Path source = workingDirectory.resolve(parameters[0]);
    destination = workingDirectory.resolve(parameters[1]);
    if (!Files.isDirectory(source)) {
      env.writeln("Given source path isn't a directory on your system.");
      return ShellStatus.CONTINUE;
    }

    if (!Files.isDirectory(destination) && !Files.isDirectory(destination.getParent())) {
      env.writeln("Given destination path isn't a directory on your system nor is it's parent.");
      return ShellStatus.CONTINUE;
    }

    try {
      Files.walkFileTree(source, new FileVisitor<Path>() {

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException arg1) throws IOException {
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes arg1)
            throws IOException {

          Path newDirectory = destination.resolve(source.relativize(dir));
          if (newDirectory.equals(destination)) {
            if (!Files.isDirectory(destination)) {
              Files.createDirectory(destination);
              env.writeln("Created directory: " + destination);
            } else {
              Path topDirectory = destination.resolve(source.getParent().relativize(dir));
              Files.createDirectory(topDirectory);
              env.writeln("Created directory: " + topDirectory);
              destination = topDirectory;
            }
            return FileVisitResult.CONTINUE;
          }
          Files.createDirectory(newDirectory);
          env.writeln("Created directory: " + newDirectory);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
          Path newFile = destination.resolve(source.relativize(file));
          Files.copy(file, newFile);
          env.writeln("Created file: " + newFile);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException arg1) throws IOException {
          env.writeln(
              "Failed to copy file or folder: " + file.toAbsolutePath().normalize().toString());
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e1) {
      env.writeln("Error while traversing the directory. File or folder already exists.");
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
