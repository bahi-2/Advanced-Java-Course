package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

/**
 * NameBuilder which executes all builders from the constructor.
 * @author Blaz Bagic
 * @version 1.0
 */
public class NameBuilderFinal implements NameBuilder {
  
  /**
   * Internal list of {@link NameBuilder} instances.
   */
  private List<NameBuilder> builders;
  
  /**
   * Constructor which initializes builders.
   * @param builders list of {@link NameBuilder} instances to execute.
   */
  public NameBuilderFinal(List<NameBuilder> builders) {
    this.builders = builders;
  }

  @Override
  public void execute(NameBuilderInfo info) {
    builders.forEach(b -> b.execute(info));
  }

}
