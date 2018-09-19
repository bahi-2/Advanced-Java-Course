package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * These enumerations represent the states the Lexer can be in.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public enum SmartScriptLexerState {
  /**
   * State in which every character except the tag is interpreted as part of a
   * text.
   */
  TEXT,
  /**
   * State while within the tags.
   */
  TAG,
  /**
   * State while within apostrophes.
   */
  STRING,
  /**
   * State when the tag has been opened but it's name hasn't been tokenized.
   */
  TAG_NAME;
}
