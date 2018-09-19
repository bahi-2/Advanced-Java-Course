package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command lists the content of a directory.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "ls";
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION
        .add("Command ls takes a single argument directory and writes a directory listing");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.equals("")) {
      env.writeln("You must provide exactly one argument: the path of the directory to list.");
      return ShellStatus.CONTINUE;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Path path = Paths.get(arguments);
    if (!Files.isDirectory(path)) {
      env.writeln("Given path isn't a directory on your system.");
      return ShellStatus.CONTINUE;
    }
    try {
      for (Path file : Files.newDirectoryStream(path)) {
        BasicFileAttributeView faView = Files.getFileAttributeView(path,
            BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attributes = faView.readAttributes();
        FileTime fileTime = attributes.creationTime();
        String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
        StringBuilder sb = new StringBuilder();
        // @formatter:off
        String modBits = sb.append(Files.isDirectory(file) ? "d" : "-")
                           .append(Files.isReadable(file) ? "r" : "-")
                           .append(Files.isWritable(file) ? "w" : "-")
                           .append(Files.isExecutable(file) ? "x" : "-")
                           .toString();
        // @formatter:on
        env.write(modBits + "\t"); 
        env.write(String.valueOf(Files.size(file)) + "\t");
        env.write(formattedDateTime + "\t");
        env.writeln(file.getFileName().toString());
      }
    } catch (IOException e) {
      env.writeln("Error while reading file attributes");
      return ShellStatus.CONTINUE;
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
