package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Prints out a hex dump on the console.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class HexDumpShellCommand implements ShellCommand {

  /**
   * Name of this command.
   */
  private static final String COMMAND_NAME = "hexdump";
  /**
   * Description of this command.
   */
  private static final List<String> COMMAND_DESCRIPTION = new ArrayList<>();
  /**
   * The size of the buffer used for reading/writing to files.
   */
  private static final int BUFFER_SIZE = 8;

  static {
    COMMAND_DESCRIPTION
        .add("Command expects a single argument: file name, and produces hex-output.");
    COMMAND_DESCRIPTION.add("On the right most column the human readable text is displayed.");
  }

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    Path workingDirectory = env.getCurrentDirectory();
    Path file = workingDirectory.resolve(Util.parseArgument(arguments));
    if (!Files.isReadable(file)) {
      env.writeln("File doesn't exist or isn't readable.");
      return ShellStatus.CONTINUE;
    }

    int lineCount = 0x0;
    try {
      InputStream reader = Files.newInputStream(file);
      byte[] buffer = new byte[BUFFER_SIZE];
      while (reader.read(buffer) > 0) {
        env.write(String.format("%08x: | %s", lineCount, byteToHex(buffer)));
        env.write(String.format("| %s", byteToHex(buffer)));
        for (int i = 0; i < buffer.length; i++) {
          if (buffer[i] < 32 || buffer[i] > 127) {
            buffer[i] = 46;
          }
        }
        env.write(new String(buffer, "UTF-8") + "\n");
        lineCount += 16;

      }
    } catch (IOException e) {
      env.writeln("Error while reading file.");
    }

    return ShellStatus.CONTINUE;
  }

  /**
   * Returns a hex {@link String} with spaces in between a group based on an array
   * of bytes.
   * 
   * @param keyBytes aray of bytes
   * @return a hex {@link String} with spaces in between a group
   */
  private static String byteToHex(byte[] keyBytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : keyBytes) {
      sb.append(String.format("%02x ", b));
    }
    return sb.toString();
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
