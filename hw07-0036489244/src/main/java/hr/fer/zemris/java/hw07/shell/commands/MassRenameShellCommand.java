package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command that renames all files in a directory using a regular expression, and
 * moves those files to another directory with new names.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class MassRenameShellCommand implements ShellCommand {

  /**
   * Name of the command.
   */
  private static final String COMMAND_NAME = "massrename";
  /**
   * Description of the command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add("Command expects at least four arguments. Usage example: ");
    COMMAND_DESCRIPTION.add("massrename directory1 directory2 CMD MASK [MASK2].");
    COMMAND_DESCRIPTION.add(
        "command renames multiple files based on a regex and moves those files to a new directory.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.equals("")) {
      env.writeln("You must provide  at least four arguments. See: help massrename.");
      return ShellStatus.CONTINUE;
    }

    String[] parameters = splitArguments(arguments);
    Path workingDirectory = env.getCurrentDirectory();
    Path dir1 = workingDirectory.resolve(parameters[0]);
    Path dir2 = workingDirectory.resolve(parameters[1]);

    if (!Files.isDirectory(dir1) || !Files.isDirectory(dir2)) {
      env.writeln("Given path isn't a directory on your system.");
      return ShellStatus.CONTINUE;
    }

    String regex = parameters[3];
    Pattern pattern = Pattern.compile(regex);
    String command = parameters[2];
    switch (command) {
      case "filter":
        try {
          // @formatter:off
          Files.list(dir1)
               .filter(path -> pattern.matcher(path.getFileName().toString()).matches())
               .forEach(path -> env.writeln(path.getFileName().toString()));
          // @formatter:on
        } catch (IOException e) {
          env.writeln("Something went wrong while reading from the directory");
          return ShellStatus.CONTINUE;
        }
        break;

      case "groups":
        try {
          // @formatter:off
          List<String> files = Files.list(dir1)
                                    .map(path->path.getFileName().toString())
                                    .filter(name -> pattern.matcher(name).matches())
                                    .collect(Collectors.toList());
          for (String fileName :files) {
            Matcher matcher = pattern.matcher(fileName);
            matcher.matches();
            env.write(fileName + " ");
            for (int i=0; i<=matcher.groupCount();i++) {
              env.write(i + ": " + matcher.group(i) + " ");
            }
            env.write("\n");
          }
          // @formatter:on
        } catch (IOException e) {
          env.writeln("Something went wrong while reading from the directory");
          return ShellStatus.CONTINUE;
        }
        break;

      case "show":
      case "execute":
        List<String> files;
        try {
          files = Files.list(dir1).map(path -> path.getFileName().toString())
              .filter(name -> pattern.matcher(name).matches()).collect(Collectors.toList());
        } catch (IOException e) {
          env.writeln("Something went wrong while reading from the directory");
          return ShellStatus.CONTINUE;
        }
        NameBuilderParser parser = null;
        try {
          parser = new NameBuilderParser(parameters[4]);
        } catch (IllegalArgumentException ex) {
          env.writeln("A parsing error occured, invalid expression.");
          return ShellStatus.CONTINUE;
        }
        NameBuilder builder = parser.getNameBuilder();
        for (String file : files) {
          Matcher matcher = pattern.matcher(file);
          if (!matcher.matches()) {
            continue;
          }
          NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
          builder.execute(info);
          String newName = info.getStringBuilder().toString();
          if (command.equals("execute")) {
            Path sourceFile = dir1.resolve(Paths.get(file));
            Path destinationFile = dir2.resolve(newName);
            try {
              Files.move(sourceFile, destinationFile);
            } catch (IOException e) {
              env.writeln("Error occured while moving the file, maybe it already exists: " + newName);
            }
            env.writeln(sourceFile + " => " + destinationFile);
          } else {
            env.writeln(file + " => " + newName);
          }
        }
        break;

      default:
        env.writeln("Unsupported command: " + command);
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
   * Method takes a line of text and extracts arguments into a string array.
   * String literals enclosed in quotes are supported.
   * 
   * @param arguments
   *          line from which to extract shell arguments
   * @return a string array of shell arguments
   */
  public static String[] splitArguments(String arguments) {
    String rx = "[^\"\\s]+|\"(\\\\.|[^\\\\\"])*\"";
    Scanner sc = new Scanner(arguments);
    List<String> result = new ArrayList<>();
    while (sc.hasNext()) {
      result.add((sc.findInLine(rx).replaceAll("\"", "")));
    }

    sc.close();
    return result.toArray(new String[0]);
  }

}
