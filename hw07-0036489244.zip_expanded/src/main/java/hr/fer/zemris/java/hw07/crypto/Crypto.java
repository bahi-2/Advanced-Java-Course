package hr.fer.zemris.java.hw07.crypto;

import static hr.fer.zemris.java.hw07.crypto.Util.byteToHex;
import static hr.fer.zemris.java.hw07.crypto.Util.hexToByte;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class meant be used as a command line program. Supported actions on binary
 * files are checksha, encrypt and decrypt.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class Crypto {

  /**
   * Supported action that can be chosen by the user to compare a sha digest to
   * the actual digest of the file.
   */
  private static final String CHECK_SHA = "checksha";
  /**
   * Supported action that can be chosen by the user to encrypt a file using
   * {@value #CIPHER_ALGORITHM} encryption.
   */
  private static final String ENCRYPTION = "encrypt";
  /**
   * Supported action that can be chosen by the user to decrypt a file that is
   * encrypted using {@value #CIPHER_ALGORITHM} encryption.
   */
  private static final String DECRYPTION = "decrypt";
  /**
   * Algorithm used for calculating the digest of the file.
   */
  private static final String DIGEST_ALGORITHM = "SHA-256";
  /**
   * Algorithm used for encrypting and decrypting binary files.
   */
  private static final String CIPHER_ALGORITHM = "AES";
  /**
   * Algorithm properties used for encrypting and decrypting binary files in a
   * format of name/mode/options.
   */
  private static final String CIPHER_INSTANCE = "AES/CBC/PKCS5Padding";
  /**
   * Size of a buffer for reading from and writing to files.
   */
  private static final int BUFFER_SIZE = 4096;

  /**
   * Main method which runs at the beginning of the program. Performs actions on
   * binary files. Method expects command line arguments to contain an action to
   * perform, supported actions are: checksha, encrypt, decrypt. Each action also
   * requires file name(s) as it's parameters.
   * 
   * @param args
   *          command line arguments, first argument specifies an action to
   *          perform, and the rest are parameters for that action
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException(
          "\n\nMissing command line arguments. Specify an action and it's supported parameters.");
    }

    String action = args[0];
    switch (action) {
      case CHECK_SHA:
        if (args.length != 2) {
          throw new IllegalArgumentException(
              "\n\nYou must provide exactly 1 argument for action checksha, the name of the file.");
        }

        String fileName = args[1];
        checkSha(fileName);
        break;

      case ENCRYPTION:
      case DECRYPTION:
        if (args.length != 3) {
          throw new IllegalArgumentException(
              "\n\nYou must provide exactly 2 arguments for action " + action
                  + ", the name of the original file and the name for the new decrypted file.");
        }

        String originalFileName = args[1];
        String outputFileName = args[2];
        applyAes(action.equals(ENCRYPTION), originalFileName, outputFileName);
        break;

      default:
        throw new IllegalArgumentException("\n\nAction \"" + action + "\"isn't supported.");
    }
  }

  /**
   * Method reads a digest from the standard input and compares it to the digest
   * calculated for the file on the project path whose name equals the name from
   * the method's parameter.
   * 
   * @param fileName
   *          name of the file on the projects path whose digest is checked
   */
  private static void checkSha(String fileName) {
    // user input digest
    System.out.print("Please provide expected sha-256 digest for " + fileName + ":\n> ");
    Scanner sc = new Scanner(System.in);
    String expectedDigest = sc.nextLine();
    sc.close();

    // calculating the actual file digest
    String actualDigest;
    try {
      actualDigest = calculateDigest(fileName);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    // comparing the two digests
    if (expectedDigest.equals(actualDigest)) {
      System.out
          .println("Digesting completed. Digest of " + fileName + " matches expected digest.");
    } else {
      System.out.println("Digesting completed. Digest of " + fileName
          + " does not match the expected digest. Digest was: " + actualDigest);
    }
  }

  /**
   * Method applies AES cipher to decrypt or encrypt the original file and saves
   * the output to the output file. The strategy is defined by the boolean
   * argument encrypt. During this process the user is prompted for a password and
   * an initialization vector needed for this encryption.
   * 
   * @param encrypt
   *          if <code>true</code> the file is encrypted, otherwise it is
   *          decrypted
   * @param originalFileName
   *          file name of the binary file on the disk
   * @param outputFileName
   *          file name by which to save the resulting file
   */
  private static void applyAes(boolean encrypt, String originalFileName, String outputFileName) {
    // reading user input
    Scanner scanner = new Scanner(System.in);
    System.out
        .print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
    String keyText = scanner.nextLine();
    System.out
        .print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
    String ivText = scanner.nextLine();
    scanner.close();

    // decrypting or encrypting based on boolean strategy
    try {
      aesEncryptDecrypt(encrypt, keyText, ivText, originalFileName, outputFileName);

      String action = encrypt ? "Encryption" : "Decryption";
      System.out.println(action + " completed. Generated file " + outputFileName + " based on file "
          + originalFileName);

    } catch (InvalidKeyException e) {
      System.err.println("\nInvalid key supplied. Please try again with the correct key.");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
      System.err.println("Exception caused by an error in code. "
          + "Change the algorithm parameters for encrypting/decrypting files.");
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Exception caused by an error in code. "
          + "Change the algorithm for encrypting/decrypting files.");
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      System.err.println("This exception is thrown because the requested padding mechanism "
          + " is not available in the environment. Change the padding mechanism option.");
      e.printStackTrace();
    }
  }

  /**
   * Method applies AES cipher to decrypt or encrypt the original file and saves
   * the output to the output file. The strategy is defined by the boolean
   * argument encrypt.
   * 
   * @param encrypt
   *          if <code>true</code> the file is encrypted, otherwise it is
   *          decrypted
   * @param keyText
   *          password used by the AES algorithm
   * @param ivText
   *          initialization vector used by the AES algorithm
   * @param originalFileName
   *          file name of the binary file on the disk
   * @param outputFileName
   *          file name by which to save the resulting file
   * @throws InvalidKeyException
   *           in case the password is of the invalid format
   * @throws InvalidAlgorithmParameterException
   *           in case a parameter for the algorithm is invalid
   * @throws NoSuchAlgorithmException
   *           in case the chosen algorithm is invalid
   * @throws NoSuchPaddingException
   *           in case the padding mechanism for the algorithm isn't implemented
   * @throws IOException
   *           in case an error occurs while writing to or reading from a file
   */
  private static void aesEncryptDecrypt(boolean encrypt, String keyText, String ivText,
      String originalFileName, String outputFileName)
      throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
      NoSuchPaddingException, IOException {

    SecretKeySpec keySpec = new SecretKeySpec(hexToByte(keyText), CIPHER_ALGORITHM);
    AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(ivText));
    Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
    cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

    // reading the file in a buffer and applying the action bit by bit
    try (InputStream input = Files.newInputStream((Paths.get(originalFileName)));
        OutputStream output = Files.newOutputStream(Paths.get(outputFileName))) {
      byte[] inputBuffer = new byte[BUFFER_SIZE];
      byte[] outputBuffer = new byte[BUFFER_SIZE];
      int bytesRead = 0;
      while (true) {
        bytesRead = input.read(inputBuffer);
        if (bytesRead < 0) {
          break;
        }
        outputBuffer = cipher.update(inputBuffer, 0, bytesRead);
        output.write(outputBuffer);
      }
    } catch (IOException ex) {
      throw new IOException("Error occured while reading from file: " + originalFileName
          + " or writing to file: " + outputFileName
          + ". Make sure the original file name is correct and that it is on the project path "
          + "and that you have write privilages on the file system.");
    }
  }

  /**
   * Calculates the SHA-256 digest of the file whose name is given as the method
   * parameter.
   * 
   * @param fileName
   *          file for which to calculate the digest
   * @return the SHA-256 digest of the file
   * @throws IOException
   *           in case there is an error while reading from the file
   */
  private static String calculateDigest(String fileName) throws IOException {

    MessageDigest md = null;
    try {
      // initializing the digest with the instance of the algorithm, can throw if the
      // supplied argument isn't supported
      md = MessageDigest.getInstance(DIGEST_ALGORITHM);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Exception caused by an error in code. "
          + "Change the algorithm for encrypting/decrypting files.");
    }

    // reading the file in a buffer and digesting bit by bit
    try (InputStream input = Files.newInputStream((Paths.get(fileName)))) {
      byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead = 0;
      while (true) {
        bytesRead = input.read(buffer);
        if (bytesRead < 0) {
          break;
        }
        md.update(buffer, 0, bytesRead);
      }
    } catch (IOException ex) {
      throw new IOException("Error occured while reading from file: " + fileName
          + ". Make sure the file name is correct and that the file is on the project path.");
    }

    // returning the digested output
    return byteToHex(md.digest());
  }

}
