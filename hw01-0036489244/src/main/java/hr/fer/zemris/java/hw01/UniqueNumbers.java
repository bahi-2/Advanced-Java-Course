package hr.fer.zemris.java.hw01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Blaz Bagic
 * @version 1.0
 */
public class UniqueNumbers {
	/**
	 * Struktura koja predstavlja jedan cvor u binarnonm stablu.
	 *
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Metoda koja se poziva na pocetku izvodenja programa. Trazi od korisnika da
	 * joj preda cijelobrojne vrijednosti za dodavanje u binarno stablo. Samo
	 * dodavanje obavlja pomocna metoda addNode. Rad programa se prekida kada
	 * korisnik unese "kraj", te se tada ispisuju rastuce i padajuce poredani
	 * elementi novonastalog binarnog stabla.
	 * 
	 * @param args
	 *            Argumenti naredbenog retka.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		TreeNode glava = null;
		while (true) {
			System.out.print("Unesite broj > ");

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = reader.readLine();

			if (line.equals("kraj")) {
				System.out.printf("Ispis od najmanjeg: ");
				ascendingConsoleLog(glava);
				System.out.printf("\nIspis od najvećeg: ");
				descendingConsoleLog(glava);
				break;
			}

			try {
				// pokusavam parsirati string u cijeli broj
				int number = Integer.parseInt(line);

				glava = addNode(glava, number);
			} catch (NumberFormatException ex) {
				// ovo se izvodi u slucaju da nije upisan cijeli broj
				System.out.printf("'%s' nije cijeli broj.\n", line);
			}
		}
	}

	/**
	 * Metoda za dodavanje cvora u binarno stablo.
	 * 
	 * @param glava
	 *            Vrsni cvor stabla u koje se zeli dodati novi cvor.
	 * @param value
	 *            Vrijednost koja se dodaje u stablo.
	 * @return Vraca vrsni cvor izmijenjenog stabla.
	 */
	static TreeNode addNode(TreeNode glava, int value) {
		if (glava == null) {
			glava = new TreeNode();
			glava.value = value;
			System.out.println("Dodano.");
			return glava;
		}
		if (value == glava.value) {
			System.out.println("Broj već postoji. Preskačem.");
			return glava;
		}
		if (value > glava.value) {
			glava.right = addNode(glava.right, value);
		} else {
			glava.left = addNode(glava.left, value);
		}
		return glava;
	}

	/**
	 * Metoda za dohvacanje broja cvorova u binarnom stablu.
	 * 
	 * @param glava
	 *            Vrsni cvor stabla.
	 * @return Vraca velicinu stabla.
	 */
	static int treeSize(TreeNode glava) {
		if (glava == null) {
			return 0;
		}
		return treeSize(glava.left) + treeSize(glava.right) + 1;
	}

	/**
	 * Metoda koja odgovara na pitanje postoji li cvor odredene vrijednosti u
	 * binarnom stablu.
	 * 
	 * @param glava
	 *            Vrsni cvor stabla.
	 * @param value
	 *            Vrijednost koja se trazi u stablu.
	 * @return Vraca true ako stablo sadrzi cvor s vrijednosti value ili false ako
	 *         ga ne sadrzi.
	 */
	static boolean containsValue(TreeNode glava, int value) {
		if (glava == null) {
			return false;
		}
		if (glava.value == value) {
			return true;
		} else if (value < glava.value) {
			return containsValue(glava.left, value);
		} else {
			return containsValue(glava.right, value);
		}
	}

	/**
	 * Metoda ispisuje vrijednosti cvorova binarnog stabla sortirano od manjeg prema vecem.
	 * @param glava Vrsni cvor stabla.
	 */
	static void ascendingConsoleLog(TreeNode glava) {
		if (glava == null) {
			return;
		}
		ascendingConsoleLog(glava.left);
		System.out.print(glava.value + " ");
		ascendingConsoleLog(glava.right);
	}

	/**
	 * Metoda ispisuje vrijednosti cvorova binarnog stabla sortirano od veceg prema manjem.
	 * @param glava Vrsni cvor stabla.
	 */
	static void descendingConsoleLog(TreeNode glava) {
		if (glava == null) {
			return;
		}
		descendingConsoleLog(glava.right);
		System.out.print(glava.value + " ");
		descendingConsoleLog(glava.left);
	}
}
