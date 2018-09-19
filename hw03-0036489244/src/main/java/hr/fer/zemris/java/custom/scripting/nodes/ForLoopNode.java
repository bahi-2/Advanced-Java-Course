package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * @author Blaz Bagic
 * @version 1.0
 */

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {
  private ElementVariable variable;
  private Element startExpression;
  private Element endExpression;
  private Element stepExpresssion;

  public ElementVariable getVariable() {
    return variable;
  }

  public Element getStartExpression() {
    return startExpression;
  }

  public Element getEndExpression() {
    return endExpression;
  }

  public Element getStepExpresssion() {
    return stepExpresssion;
  }

  public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
    this(variable, startExpression, endExpression, null);
  }

  public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
      Element stepExpresssion) {
    super();
    this.variable = Objects.requireNonNull(variable);
    this.startExpression = Objects.requireNonNull(startExpression);
    this.endExpression = Objects.requireNonNull(endExpression);
    this.stepExpresssion = stepExpresssion;
  }
}
