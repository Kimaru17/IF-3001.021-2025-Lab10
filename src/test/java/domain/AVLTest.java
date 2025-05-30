package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {

    @Test
    void test() {
        AVL avl = new AVL();
        for (int i = 0; i < 30; i++) {
            avl.add(util.Utility.random(180)+20);
        }
        System.out.println(avl); //toString
        try {
            System.out.println("Size: " + avl.size());
            System.out.println("Min: " + avl.min());
            System.out.println("Max: " + avl.max());
            System.out.println("Is balanced: " + avl.isBalanced());
            avl.remove(avl.min());
            avl.remove(avl.max());
            avl.remove(avl.min());
            avl.remove(avl.max());
            avl.remove(avl.min());
            System.out.println(avl);
            System.out.println("Is balanced: " + avl.isBalanced());
            System.out.println(avl);
            System.out.println("Is balanced: " + avl.isBalanced());
            for (int i = 0; i < 10 ; i++) {
                int randomValue = util.Utility.random(180)+20;
                System.out.println("\nRandom Value: " + randomValue);
                System.out.println("father: "+avl.father(randomValue));
                System.out.println("brother: "+avl.brother(randomValue));
                System.out.println("children: "+avl.children(randomValue));
            }



        } catch (TreeException e) {
            e.printStackTrace();
        }
    }
}