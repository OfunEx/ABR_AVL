import java.io.Console;

public class main {

	public static void main(String[] args) {
		
		AVL<Integer> a = new AVL<Integer>(12);
		
		AVL<Integer> b = a.insertionAVL(14);
		
		AVL<Integer> c = a.insertionAVL(13);
		
		System.out.println(a.afficher());
		System.out.println(b.afficher());
		System.out.println(c.afficher());

	}

}
