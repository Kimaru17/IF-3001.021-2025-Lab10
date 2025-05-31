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

public class BST_AVL_TourController {

    @FXML
    private Pane pane;
    // coordenadas del arbol
    int x = 531;
    int y = 83;
    int hgap = 225;
    int nodeCounter = 0;
    @FXML
    private RadioButton buttonBST;
    @FXML
    private RadioButton buttonAVL;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    AVL avl = new AVL();
    BST bst = new BST();
    @FXML
    private Text nameTag;

    @FXML
    public void initialize(){

        randomizeOnAction(new ActionEvent());
        buttonBST.setToggleGroup(toggleGroup);
        buttonAVL.setToggleGroup(toggleGroup);
        nameTag.setVisible(true);
        nameTag.setText("");

    }

    private void drawTree(Pane pane, BTreeNode node, double x, double y, double hGap) {
        if (node != null) {
            // Dibujar la línea hacia el nodo hijo izquierdo
            if (node.left != null) {
                Line leftLine = new Line(x - hGap, y + 50, x, y);
                pane.getChildren().add(leftLine);
                drawTree(pane, node.left, x - hGap, y + 50, hGap / 2);
            }

            // Dibujar la línea hacia el nodo hijo derecho
            if (node.right != null) {
                Line rightLine = new Line(x + hGap, y + 50, x, y);
                pane.getChildren().add(rightLine);
                drawTree(pane, node.right, x + hGap, y + 50, hGap / 2);
            }

            Circle circle = new Circle(x, y, 15);
            // Dibujar el círculo para el nodo actual
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            pane.getChildren().add(circle);

            // Dibujar el texto para el nodo actual
            Text text = new Text(String.valueOf(node.data)); // Asigna la data al texto
            text.setX(x - 5);  // Ajustar posición del texto
            text.setY(y + 5);  // Ajustar posición del texto
            pane.getChildren().add(text);

        }
    }

    @FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()) {
            pane.getChildren().clear();
            avl.clear();
            // Generar el árbol con valores aleatorios
            for (int i = 0; i < 10; i++)
                avl.add(util.Utility.random(50));


            if (buttonAVL.isSelected())
                avlOnAction(new ActionEvent());

            if (buttonBST.isSelected())
                bstOnAction(new ActionEvent());

            BTreeNode root = avl.getRoot();

            drawTree(pane, root, x, y, hgap);
        }else if (buttonBST.isSelected()) {
            pane.getChildren().clear();
            bst.clear();
            // Generar el árbol con valores aleatorios
            for (int i = 0; i < 10; i++)
                bst.add(util.Utility.random(50));


            if (buttonAVL.isSelected())
                avlOnAction(new ActionEvent());

            if (buttonBST.isSelected())
                bstOnAction(new ActionEvent());

            BTreeNode root = bst.getRoot();

            drawTree(pane, root, x, y, hgap);
        }
    }

    @FXML
    public void postOrderOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()) {
            pane.getChildren().clear();
            drawTree(pane, avl.getRoot(), x, y, hgap);
            nodeCounter = 0;  // Reiniciar el contador de nodos
            postOrderUsage(avl.getRoot(), x, y, hgap);

            pane.getChildren().add(nameTag);
            nameTag.setVisible(true);
            nameTag.setText("PostOrder I-D-R");
        }else if (buttonBST.isSelected()) {
            pane.getChildren().clear();
            drawTree(pane, bst.getRoot(), x, y, hgap);
            nodeCounter = 0;  // Reiniciar el contador de nodos
            postOrderUsage(bst.getRoot(), x, y, hgap);

            pane.getChildren().add(nameTag);
            nameTag.setVisible(true);
            nameTag.setText("PostOrder I-D-R");
        }
    }
    private void postOrderUsage(BTreeNode node, double x, double y, double hGap) {

        if (node != null) {
            if (node.left != null) {
                postOrderUsage(node.left, x - hGap, y + 50, hGap / 2);
            }
            if (node.right != null) {
                postOrderUsage(node.right, x + hGap, y + 50, hGap / 2);
            }
            Text text = new Text(String.valueOf(nodeCounter));
            text.setX(x - 5);  // Ajustar posición del texto
            text.setY(y + 30);  // Ajustar posición del texto
            pane.getChildren().add(text);
            nodeCounter++;
        }
        nameTag.setVisible(true);
        nameTag.setText("Holi");

    }

    @FXML
    public void inOrderOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()) {
            pane.getChildren().clear();
            drawTree(pane, avl.getRoot(), x, y, hgap);
            nodeCounter = 0;  // Reiniciar el contador de nodos
            inOrderUsage(avl.getRoot(), x, y, hgap);

            pane.getChildren().add(nameTag);
            nameTag.setVisible(true);
            nameTag.setText("InOrder I-R-D");
        }else if (buttonBST.isSelected()) {
            pane.getChildren().clear();
            drawTree(pane, bst.getRoot(), x, y, hgap);
            nodeCounter = 0;  // Reiniciar el contador de nodos
            inOrderUsage(bst.getRoot(), x, y, hgap);

            pane.getChildren().add(nameTag);
            nameTag.setVisible(true);
            nameTag.setText("InOrder I-R-D");
        }
    }
    private void inOrderUsage(BTreeNode node, double x, double y, double hGap) {

        if (node != null) {
            if (node.left != null) {
                inOrderUsage(node.left, x - hGap, y + 50, hGap / 2);
            }
            Text text = new Text(String.valueOf(nodeCounter));
            text.setX(x - 5);  // Ajustar posición del texto
            text.setY(y + 30);  // Ajustar posición del texto
            pane.getChildren().add(text);
            nodeCounter++;
            if (node.right != null) {
                inOrderUsage(node.right, x + hGap, y + 50, hGap / 2);
            }
        }
    }

    @FXML
    public void preOrderOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()) {
            pane.getChildren().clear();
            drawTree(pane, avl.getRoot(), x, y, hgap);
            nodeCounter = 0;  // Reiniciar el contador de nodos
            preOrderUsage(avl.getRoot(), x, y, hgap);

            pane.getChildren().add(nameTag);
            nameTag.setVisible(true);
            nameTag.setText("PreOrder R-I-D");
        }else if (buttonBST.isSelected()) {
            pane.getChildren().clear();
            drawTree(pane, bst.getRoot(), x, y, hgap);
            nodeCounter = 0;  // Reiniciar el contador de nodos
            preOrderUsage(bst.getRoot(), x, y, hgap);

            pane.getChildren().add(nameTag);
            nameTag.setVisible(true);
            nameTag.setText("PreOrder R-I-D");
        }
    }
    private void preOrderUsage(BTreeNode node, double x, double y, double hGap) {

        if (node != null) {
            Text text = new Text(String.valueOf(nodeCounter));
            text.setX(x - 5);  // Ajustar posición del texto
            text.setY(y + 30);  // Ajustar posición del texto
            pane.getChildren().add(text);
            nodeCounter++;
            if (node.left != null) {
                preOrderUsage(node.left, x - hGap, y + 50, hGap / 2);
            }
            if (node.right != null) {
                preOrderUsage(node.right, x + hGap, y + 50, hGap / 2);
            }
        }

    }

    @FXML
    public void avlOnAction(ActionEvent actionEvent){
        // Al seleccionar este RadioButton, se genera aleatoriamente un nuevo árbol AVL.

        pane.getChildren().clear();
        avl.clear();
        pane.getChildren().add(nameTag);
        for (int i = 0; i < 10; i++)
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
        pane.getChildren().add(nameTag);

        for (int i = 0; i < 10; i++)
            bst.add(util.Utility.random(50));
        BTreeNode root = bst.getRoot();
        drawTree(pane, root, x, y, hgap);
        checkBalance();  // Se verifica si el árbol está balanceado o no para cambiar el texto 'balanced' acordemente.
    }// End of method [bstOnAction].

    private void checkBalance(){
        // LLama a los métodos 'isBalanced' de cada clase para verificar si los árboles están balanceados o no.
        Platform.runLater(() -> { // Verifica que el cambio de colores según el balanceo se esté aplicando bien.
            nameTag.getStyleClass().removeAll("blueText", "redText");
            if (buttonBST.isSelected()) // Caso donde el árbol BST está seleccionado.
                try {
                    if (bst.isBalanced()) { // Cambia propiedades del texto si el árbol generado está balanceado.
                        nameTag.setText("BST is balanced.");
                        nameTag.setFill(Color.BLUE);
                    } else {
                        nameTag.setText("BST is NOT balanced."); // Cambia propiedades del texto si el árbol generado no está balanceado.
                        nameTag.setFill(Color.RED);
                    }// End of 'else'.
                } catch (TreeException e) {
                    throw new RuntimeException(e);
                }// End of 'catch'.
            else if (buttonAVL.isSelected()) // Caso donde el árbol AVL está seleccionado.
                try {
                    if (avl.isBalanced()) {
                        nameTag.setText("AVL is balanced.");
                        nameTag.setFill(Color.BLUE);
                    } else {
                        while (!avl.isBalanced())
                            randomizeOnAction(new ActionEvent());
                        checkBalance();
                    }// End of 'else'.
                } catch (TreeException e) {
                    throw new RuntimeException(e);
                }// End of 'catch'.
            nameTag.setVisible(true);
        });// End of [Platform.runLater].
    }// End of method [checkBalance].

}// End of class [BST_AVLTourController].
