package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

/**
 * Shell program with a set of supported commands for dealing with ones
 * file-system.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class MyShell {

  /**
   * Message to print out at the start of the program.
   */
  private static final String HELLO_MESSAGE = "Welcome to MyShell v 1.0";
  /**
   * Status of the shell.
   */
  private static ShellStatus status;

  /**
   * An interface to the user.
   */
  private static final Environment env = new EnvironmentImpl();

  /**
   * Main program which reads commands from the standard input and calls on
   * appropriate commands.
   * 
   * @param args
   */
  public static void main(String[] args) {
    // write hello message
    env.writeln(HELLO_MESSAGE);

    String line;
    do {
      env.write(env.getPromptSymbol() + " "); // print prompt symbol
      line = env.readLine(); // user input

      // concatenate multiple lines
      while (line.trim().endsWith(env.getMorelinesSymbol().toString())) {
        line = line.substring(0, line.length() - 1);
        env.write(env.getMultilineSymbol() + " ");
        line += env.readLine();
      }

      // skip empty lines
      if (line.length() == 0) {
        continue;
      }

      // parse the command and it's arguments
      String commandName = line.split(" ")[0];
      String arguments = line.substring(commandName.length()).trim();

      // skip unrecognized commands with a message to the user
      if (!env.commands().containsKey(commandName)) {
        env.writeln("Unrecognized command: '" + commandName + "'");
        continue;
      }

      // execute
      ShellCommand command = env.commands().get(commandName);
      try {
        status = command.executeCommand(env, arguments);
      } catch (ShellIOException ex) {
        status = ShellStatus.TERMINATE;
      }
    } while (status != ShellStatus.TERMINATE);
  }

}
