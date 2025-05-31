package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BSTTest {

    @Test
    void test() throws TreeException {
        System.out.println(" Prueba a) Creación e inserción de árboles ");
        BST bst = new BST();
        
        BTree bTree1 = new BTree();
        for (int i = 0; i < 100; i++) {
            int val = util.Utility.random(300) + 200;
            bTree1.add(val);
        }
        
        BST bst2 = new BST();
        for (char c = 'A'; c <= 'Z'; c++) {
            bst2.add(c);
        }
        
        BTree bTree2 = new BTree();
        String[] personNames = {"Evans", "Alejandro", "Varela", "Alex", "Jose Pablo", "Ilda", "Rebeca", "Oscar", "Getsel", "Yendrick"};
        for (String name : personNames) {
            bTree2.add(name);
        }
        
        bst.add(bTree1);
        bst.add(bst2);
        bst.add(bTree2);
        
        System.out.println("\n Prueba b) Recorridos con toString() ");
        System.out.println(bst);
        
        System.out.println("\n Prueba c) size, min, max ");
        System.out.println("Size: " + bst.size());
        System.out.println("Min: " + bst.min());
        System.out.println("Max: " + bst.max());
        
        System.out.println("\n Prueba d) contains en subárboles ");
       
        int[] nums = {205, 310, 492, 400, 286};
        for (int num : nums) {
            System.out.println("¿El arbol binario contiene "+num+" ? = "+bTree1.contains(num) );
        }
        
        char[] letters = {'F', 'M', 'Z', 'Q', 'G'};
        for (char ch : letters) {
            System.out.println("¿El arbol binario de busqueda contiene "+ch+" ? = " + bst2.contains(ch));
        }
       
        String[] checkNames = {"Evans", "Alejandro", "Varela", "Susana", "Fabian"};
        for (String nm : checkNames) {
            System.out.println("¿El arbol binario contiene "+nm+" ? = "+bTree2.contains(nm));
        }
        
        System.out.println("\n Prueba e) Insertar 10 números aleatorios [10,50] ");
        List<Integer> added = new ArrayList<>();
        while (added.size() < 10) {
            int v = util.Utility.random(40) + 10;
            if (!added.contains(v)) {
                added.add(v);
                bst.add(v);
                System.out.println("Agregado: " + v);
            }
        }
        
        System.out.println("\n Prueba f) isBalanced antes de eliminar ");
        System.out.println("Is balanced: " + bst.isBalanced());
        
        System.out.println("\n Prueba g) Eliminar 5 elementos (4 números + bTree1) ");
        for (int i = 0; i < 4; i++) {
            int value;
            do {
                value = util.Utility.random(40) + 10;
            } while (!bst.contains(value));
            bst.remove(value);
            System.out.println("El objeto " + value + " ha sido eliminado");
        }
        bst.remove(bTree1);
        System.out.println("Eliminado subárbol bTree1");
        
        System.out.println("\n Prueba h) toString tras eliminaciones ");
        System.out.println(bst);
        
        System.out.println("\n Prueba i) isBalanced después de eliminar ");
        System.out.println("Is balanced: " + bst.isBalanced());
        
        System.out.println("\n Prueba j) Altura de cada elemento ");
        bst.printAllHeights();
    }
}