package controller;

import domain.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class GraphicBST_AVLController {

    @FXML
    private RadioButton buttonBST;
    @FXML
    private RadioButton buttonAVL;
    @FXML
    private Pane pane;
    @FXML
    private Text balanced;
    AVL avl = new AVL();
    BST bst = new BST();
    int x = 408, y = 59, hgap = 150;

    BTree tree = new BTree();
    Line levelLine = null;
    Text levelText = null;

    ToggleGroup toggleGroup = new ToggleGroup();

    public void initialize() {
        buttonBST.setToggleGroup(toggleGroup);
        buttonAVL.setToggleGroup(toggleGroup);
        randomizeOnAction(new ActionEvent());
    }

    private void drawTree(Pane pane, BTreeNode node, double x, double y, double hGap) {
        if (node != null) {
            // Dibuja la línea hacia el nodo hijo izquierdo.
            if (node.left != null) {
                Line leftLine = new Line(x - hGap, y + 50, x, y);
                pane.getChildren().add(leftLine);

                drawTree(pane, node.left, x - hGap, y + 50, hGap / 2);
            }// End of 'if'.

            // Dibuja la línea hacia el nodo hijo derecho.
            if (node.right != null) {
                Line rightLine = new Line(x + hGap, y + 50, x, y);
                pane.getChildren().add(rightLine);

                drawTree(pane, node.right, x + hGap, y + 50, hGap / 2);
            }// End of 'if'.

            Circle circle = new Circle(x, y, 15);
            // Dibuja el círculo para el nodo actual
            circle.setFill(Color.valueOf("#00d8dd"));
            circle.setStroke(Color.BLACK);
            pane.getChildren().add(circle);

            // Dibuja el texto para el nodo actual.
            Text text = new Text(String.valueOf(node.data));
            text.setX(x - 5);  // Ajustar posición del texto
            text.setY(y + 5);  // Ajustar posición del texto
            pane.getChildren().add(text);


        }// End of 'if'.
    }// End of method [drawTree].

    @FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        // Crea un nuevo árbol con valores aleatorios.
        avl.clear(); // Limpia ambos árboles para no crear árboles sobrepuestos entre otros.
        bst.clear();

        if (buttonAVL.isSelected()) { // Caso donde el árbol AVL esté seleccionado.
            pane.getChildren().clear();
            avl.clear();
            for (int i = 0; i <= 20; i++)
                avl.add(util.Utility.random(50));
            BTreeNode root = avl.getRoot();
            drawTree(pane, root, x, y, hgap);
        }else if (buttonBST.isSelected()) { // Caso donde el árbol BST esté seleccionado.
            pane.getChildren().clear();
            bst.clear();
            for (int i = 0; i <= 20; i++)
                bst.add(util.Utility.random(50));
            BTreeNode root = bst.getRoot();
            drawTree(pane, root, x, y, hgap);
        }// End of 'if'.
        checkBalance(); // Verifica si el árbol generado está balanceado, para cambiar el texto 'balanced' acordemente.
    }// End of method [randomizeOnAction].

    @FXML
    public void avlOnAction(ActionEvent actionEvent){
        // Al seleccionar este RadioButton, se genera aleatoriamente un nuevo árbol AVL.

        pane.getChildren().clear();
        avl.clear();
        for (int i = 0; i <= 20; i++)
            avl.add(util.Utility.random(50));
        BTreeNode root = avl.getRoot();
        drawTree(pane, root, x, y, hgap);
        checkBalance(); // Se verifica si el árbol está balanceado o no para cambiar el texto 'balanced' acordemente.
    }// End of method [avlOnAction].

    @FXML
    public void bstOnAction(ActionEvent actionEvent) {
        // Al seleccionar este RadioButton, se genera aleatoriamente un nuevo árbol BST.

        pane.getChildren().clear();
        bst.clear();
        for (int i = 0; i <= 20; i++)
            bst.add(util.Utility.random(50));
        BTreeNode root = bst.getRoot();
        drawTree(pane, root, x, y, hgap);
        checkBalance();  // Se verifica si el árbol está balanceado o no para cambiar el texto 'balanced' acordemente.
    }// End of method [bstOnAction].

    @FXML
    public void levelsOnAction(ActionEvent actionEvent) {
        try {
            if (buttonAVL.isSelected()) {
                // Obtener la altura del árbol
                int maxLevel = avl.height();

                // Dibujar las líneas de los niveles en el Pane
                double y = 95; // Altura inicial
                double lineSpacing = 55; // Espaciado vertical entre las líneas de nivel

                for (int i = 0; i <= maxLevel; i++) {
                    double yPos = y + i * lineSpacing;
                    levelLine = new Line(0, yPos, pane.getWidth(), yPos);
                    levelLine.setStroke(Color.GRAY);
                    pane.getChildren().add(levelLine);

                    levelText = new Text("Nivel " + i);
                    levelText.setX(5);
                    levelText.setY(yPos - 5); // Ajustar posición del texto
                    pane.getChildren().add(levelText);
                }
            }else if (buttonBST.isSelected()) {
                // Obtener la altura del árbol
                int maxLevel = bst.height();

                // Dibujar las líneas de los niveles en el Pane
                double y = 95; // Altura inicial
                double lineSpacing = 55; // Espaciado vertical entre las líneas de nivel

                for (int i = 0; i <= maxLevel; i++) {
                    double yPos = y + i * lineSpacing;
                    levelLine = new Line(0, yPos, pane.getWidth(), yPos);
                    levelLine.setStroke(Color.GRAY);
                    pane.getChildren().add(levelLine);

                    levelText = new Text("Nivel " + i);
                    levelText.setX(5);
                    levelText.setY(yPos - 5); // Ajustar posición del texto
                    pane.getChildren().add(levelText);
                }
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void balancedOnAction(ActionEvent actionEvent) throws TreeException {
        if (buttonAVL.isSelected()) {
            if (avl.isBalanced()) {
                util.FXUtility.alert("Is Balanced", "El árbol AVL está balanceado.");
            } else util.FXUtility.alert("Is Balanced", "El árbol AVL no está balanceado.");
        }else if (buttonBST.isSelected())
            if (bst.isBalanced()) {
                util.FXUtility.alert("Is Balanced", "El árbol BST está balanceado.");
            } else util.FXUtility.alert("Is Balanced", "El árbol BST no está balanceado.");
    }

    @FXML
    public void infoOnAction(ActionEvent actionEvent) {
        try {
            String preOrder, inOrder, postOrder;
            int height;

            if (buttonAVL.isSelected()) {
                preOrder = avl.preOrder();
                inOrder = avl.inOrder();
                postOrder = avl.postOrder();
                height = avl.height();
            } else if (buttonBST.isSelected()) {
                preOrder = bst.preOrder();
                inOrder = bst.inOrder();
                postOrder = bst.postOrder();
                height = bst.height();
            } else {
                throw new TreeException("No tree type selected");
            }

            String message = "PreOrder: " + preOrder + "\nInOrder: " + inOrder + "\nPostOrder: " + postOrder + "\nHeight: " + height;
            util.FXUtility.alert("Tour Information", message);

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkBalance(){
        // LLama a los métodos 'isBalanced' de cada clase para verificar si los árboles están balanceados o no.
        Platform.runLater(() -> { // Verifica que el cambio de colores según el balanceo se esté aplicando bien.
            balanced.getStyleClass().removeAll("blueText", "redText");
            if (buttonBST.isSelected()) // Caso donde el árbol BST está seleccionado.
                try {
                    if (bst.isBalanced()) { // Cambia propiedades del texto si el árbol generado está balanceado.
                        balanced.setText("BST is balanced.");
                        balanced.setFill(Color.BLUE);
                    } else {
                        balanced.setText("BST is NOT balanced."); // Cambia propiedades del texto si el árbol generado no está balanceado.
                        balanced.setFill(Color.RED);
                    }// End of 'else'.
                } catch (TreeException e) {
                    throw new RuntimeException(e);
                }// End of 'catch'.
            else if (buttonAVL.isSelected()) // Caso donde el árbol AVL está seleccionado.
                try {
                    if (avl.isBalanced()) {
                        balanced.setText("AVL is balanced.");
                        balanced.setFill(Color.BLUE);
                    } else {
                        while (!avl.isBalanced())
                            randomizeOnAction(new ActionEvent());
                        checkBalance();
                    }// End of 'else'.
                } catch (TreeException e) {
                    throw new RuntimeException(e);
                }// End of 'catch'.
            balanced.setVisible(true);
        });// End of [Platform.runLater].
    }// End of method [checkBalance].
}// End of class [GraphicBST_AVLController].
