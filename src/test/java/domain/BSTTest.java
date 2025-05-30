package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BSTTest {

    @Test
    void test() throws TreeException {
        BST bst = new BST();

        //Arbol binario simple que contendrá 100 numeros aleatorios entre 200 y 500
        BTree bTree1 = new BTree();
        int[] array = new int[100];

        for (int i = 0; i < 100; i++){

            int values = util.Utility.random(300)+200;
            bTree1.add(values);
            array[i] = values;
        }

        //Arbol binario de búsqueda que contendrá las letras del abecedario
        BST bst2 = new BST();
        ArrayList<Character> array2 = new ArrayList<>();

        for (char alphabethLetters = 'A'; alphabethLetters <= 'Z'; alphabethLetters++){
            bst2.add(alphabethLetters);
            array2.add(alphabethLetters);

        }

        //Arbol binario simple que contendrá el nombre de 10 personas
        BTree bTree2 = new BTree();
        String []personNames = {"Michael", "Katherine", "José David", "Gilbert", "Daniela", "Susana", "Alejandro", "Fiorella", "Fabian", "Pablo"};

        ArrayList<String> array3 = new ArrayList<>();

        for (int names = 0; names < 10; names++){
            bTree2.add(personNames[names]);
            array3.add(personNames[names]);
        }
        bst.add(bTree1);
        bst.add(bst2);
        bst.add(bTree2);
        //Prueba toString
        System.out.println(bst);

        //Prueba del método size, min y max
        try {
            System.out.println(bst.size());
            System.out.println(bst.min());
            System.out.println(bst.max());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        //Prueba del método contains

        //Prueba del metodo contains con el arbol de los numeros del 1 al 100
        System.out.println("\nPrueba del metodo contains con el arbol de los numeros del 1 al 100 \n");

        if (!bst.contains(bTree1)) {

            if (bTree1.contains(8)) System.out.println("El árbol binario contiene el número 8");
            else System.out.println("El numero 8 no se encuenta en el arbol");

            if (bTree1.contains(14)) System.out.println("El árbol binario contiene el número 14");
            else System.out.println("El numero 14 no se encuenta en el arbol");

            if (bTree1.contains(50)) System.out.println("El árbol binario contiene el número 50");
            else System.out.println("El numero 50 no se encuenta en el arbol");

            if (bTree1.contains(88)) System.out.println("El árbol binario contiene el número 88");
            else System.out.println("El numero 88 no se encuenta en el arbol");

            if (bTree1.contains(72)) System.out.println("El árbol binario contiene el número 72");
            else System.out.println("El numero 72 no se encuenta en el arbol");

        }

        //Prueba del metodo contains con el arbol que contiene las letras

        System.out.println("\nPrueba del metodo contains con el arbol que contiene las letras\n");
        if (!bst.contains(bst2)) {

            if (bst2.contains('E')) System.out.println("El árbol binario contiene la letra E");
            else System.out.println("La letra E no se encuenta en el arbol");

            if (bst2.contains('S')) System.out.println("El árbol binario contiene la letra S");
            else System.out.println("La letra S no se encuenta en el arbol");

            if (bst2.contains('D')) System.out.println("El árbol binario contiene la letra D");
            else System.out.println("La letra D no se encuenta en el arbol");

            if (bst2.contains('T')) System.out.println("El árbol binario contiene la letra T");
            else System.out.println("La letra T no se encuenta en el arbol");

            if (bst2.contains('W')) System.out.println("El árbol binario contiene la letra W");
            else System.out.println("la letra W no se encuenta en el arbol");

        }

        System.out.println("\nPrueba del metodo contains con el arbol que los nombres\n");

        if (!bst.contains(bTree2)) {

            if (bTree2.contains("Alejandro")) System.out.println("El árbol binario contiene el nombre Alejandro");
            else System.out.println("El nombre Alejandro no se encuenta en el arbol");

            if (bTree2.contains("Susana")) System.out.println("El árbol binario contiene el nombre Susana");
            else System.out.println("El nombre Susana no se encuenta en el arbol");

            if (bTree2.contains("José David")) System.out.println("El árbol binario contiene el nombre Jose David");
            else System.out.println("El nombre Jose David no se encuenta en el arbol");

            if (bTree2.contains("Fiorella")) System.out.println("El árbol binario contiene el nombre Fiorella");
            else System.out.println("El nombre Fiorella no se encuenta en el arbol");

            if (bTree2.contains("Gilbert")) System.out.println("El árbol binario contiene el nombre Gilbert");
            else System.out.println("El nombre Gilbert no se encuenta en el arbol");

        }
        //Prueba de agregar 10 numeros nuevos al arbol BST
        ArrayList<Integer> arrayList = new ArrayList<>();

        while (arrayList.size() < 10) {

            int value = util.Utility.random(40)+10;

            // Verifica si el número ya está en el array
            boolean isDuplicate = false;
            for (int num : arrayList) {
                if (util.Utility.compare(num, value) == 0) {
                    isDuplicate = true;
                    break;
                }
            }

            // Si no es duplicado, lo agrega al array
            if (!isDuplicate) {
                arrayList.add(value);
            }
        }

        // Agrega los elementos únicos al árbol AVL
        for (int value : arrayList) {
            bst.add(value);
        }

        //Prueba si el arbol esta balanceado
        bst.isBalanced();
        if (bst.isBalanced()) System.out.println("El arbol esta balanceado \n");
        else System.out.println("El arbol no esta balanceado \n");

       //Prueba de comprobacion del metodo remove

        for (int i = 0; i < 4; i++) {

            int value = util.Utility.random(40)+10;

            if (bst.contains(value)) {
                bst.remove(value);
                System.out.println("El objeto " + value + " ha sido eliminado");
            } else System.out.println("El elemento " + value + " no se encuenta en el arbol");

        }
        bst.remove(bTree1); //remove de un arbol del arbol
        System.out.println(bst); //toString
        //Segunda prueba si el arbol esta balanceado
        bst.isBalanced();
        if (bst.isBalanced()) System.out.println("El arbol esta balanceado \n");
        else System.out.println("El arbol no esta balanceado \n");
    }



}