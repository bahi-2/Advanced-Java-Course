package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Prints out description of a given command, or lists all supported commands.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "help";
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();

  static {
    COMMAND_DESCRIPTION.add(
        "Command expects 1 or 0 arguments: argument can be the command which description should be displayed.");
    COMMAND_DESCRIPTION
        .add("If called without an argument it lists names of all supported commands.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if (arguments.isEmpty()) {
      env.writeln("Available commands are:");
      env.commands().forEach(new BiConsumer<String, ShellCommand>() {
        @Override
        public void accept(String name, ShellCommand command) {
          env.writeln(name);
        }
      });
    }
    arguments = arguments.trim();
    if (!env.commands().containsKey(arguments)) {
      env.writeln("No such command, was: " + arguments);
    }
    ShellCommand command = env.commands().get(arguments);
    env.writeln(command.getCommandName());
    command.getCommandDescription().forEach(env::writeln);
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
