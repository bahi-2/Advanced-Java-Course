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
 * Command recursively removes every file and directory in a given directory.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class RmTreeShellCommand implements ShellCommand {

  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "rmtree";
  /**
   * Description of the command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command expects a single argument: directory name.");
    COMMAND_DESCRIPTION.add("Removes every file/folder in that directory.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.equals("")) {
      env.writeln("You must provide exactly one argument: the path of the directory to clear.");
      return ShellStatus.CONTINUE;
    }

    Path workingDirectory = env.getCurrentDirectory();
    Path path = workingDirectory.resolve(Util.parseArgument(arguments));
    if (!Files.isDirectory(path)) {
      env.writeln("Given path isn't a directory on your system.");
      return ShellStatus.CONTINUE;
    }
    try {
      Files.walkFileTree(path, new FileVisitor<Path>() {

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException arg1) throws IOException {
          if (dir.equals(path)) {
            return FileVisitResult.CONTINUE;
          }

          Files.delete(dir);
          env.writeln("Deleted directory " + dir.toAbsolutePath().normalize().toString());
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes arg1)
            throws IOException {
          if (dir.equals(path)) {
            return FileVisitResult.CONTINUE;
          }

          env.writeln("Entering directory " + dir.toAbsolutePath().normalize().toString());
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
          Files.delete(file);
          env.writeln("Deleted file " + file.toAbsolutePath().normalize().toString());
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException arg1) throws IOException {
          env.writeln("Failed to delete file: " + file.toAbsolutePath().normalize().toString());
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e1) {
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

}
