package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class UniqueNumbersTest {
	/**
	 * Metoda koja testira metode binarnog stabla sadržanih u razredu UniqueNumbers.
	 * Metode koje se testiraju su: addNode, containsValue, treeSize.
	 * 
	 * @result Popunjeno binarno stablo, velicine 4.
	 */
	@Test
	public void binaryTreeMethodTests() {
		TreeNode glava = null;
		assertEquals(0, treeSize(glava));

		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);

		assertEquals(42, glava.value);
		assertEquals(21, glava.left.value);
		assertEquals(35, glava.left.right.value);
		assertEquals(76, glava.right.value);

		assertEquals(4, treeSize(glava));

		assertTrue(containsValue(glava, 42));
		assertTrue(containsValue(glava, 76));
		assertTrue(containsValue(glava, 21));
		assertTrue(containsValue(glava, 35));

		assertFalse(containsValue(glava, 10));
		assertFalse(containsValue(glava, 24));
		assertFalse(containsValue(glava, -192));

	}
}
