package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Junit tests for {@link ObjectMultistack}.
 * 
 * @author Blaz Bagic
 * @version 1.0
 */
public class ObjectMultistackTest {

  ObjectMultistack multiStack = new ObjectMultistack();
  ValueWrapper value = new ValueWrapper("often_used_value");

  @Test
  public void isEmptyTest() {
    multiStack.push("key", value);
    assertTrue(multiStack.isEmpty("aa"));
    assertFalse(multiStack.isEmpty("key"));
    multiStack.pop("key");
    assertTrue(multiStack.isEmpty("key"));
  }

  @Test
  public void pushTest1() {
    multiStack.push("key", value);
    assertEquals(value, multiStack.peek("key"));
  }

  @Test
  public void pushTest2() {
    ValueWrapper v2 = new ValueWrapper(1);
    multiStack.push("key", value);
    multiStack.push("key", v2);
    assertEquals(v2, multiStack.peek("key"));
  }

  @Test
  public void pushTest3() {
    ValueWrapper v2 = new ValueWrapper(1);
    ValueWrapper v3 = new ValueWrapper("2");
    ValueWrapper v4 = new ValueWrapper("ankica");

    multiStack.push("multiple", value);
    multiStack.push("multiple", v2);
    multiStack.push("multiple", v3);
    multiStack.push("multiple", v4);

    assertEquals(v4, multiStack.pop("multiple"));
    assertEquals(v3, multiStack.pop("multiple"));
    assertEquals(v2, multiStack.pop("multiple"));
    assertEquals(value, multiStack.pop("multiple"));
  }

  @Test(expected = EmptyStackException.class)
  public void peekEmptyStack() {
    multiStack.peek("new_key");
  }

  @Test(expected = EmptyStackException.class)
  public void popEmptyStack() {
    multiStack.pop("new_key");
  }

}