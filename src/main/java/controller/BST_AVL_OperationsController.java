package controller;

import domain.AVL;
import domain.BST;
import domain.BTreeNode;
import domain.TreeException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class BST_AVL_OperationsController {
    @javafx.fxml.FXML
    private Pane pane;
    @javafx.fxml.FXML
    private Text balanced;
    AVL avl = new AVL();
    BST bst = new BST();
    int x = 531, y = 83, hgap = 225;
    @javafx.fxml.FXML
    private RadioButton buttonBST;
    @javafx.fxml.FXML
    private RadioButton buttonAVL;
    ToggleGroup toggleGroup = new ToggleGroup();

    public void initialize() {
        buttonBST.setToggleGroup(toggleGroup);
        buttonAVL.setToggleGroup(toggleGroup);
        randomizeOnAction(new ActionEvent());
    }

    private void drawTree(Pane pane, BTreeNode node, double x, double y, double hGap, String route) {
        if (node != null) {
            // Dibuja la línea hacia el nodo hijo izquierdo.
            if (node.left != null) {
                Line leftLine = new Line(x - hGap, y + 50, x, y);
                pane.getChildren().add(leftLine);
                String newRoute = route.equals("root") ? "root/left" : route + "/left";
                drawTree(pane, node.left, x - hGap, y + 50, hGap / 2, newRoute);
            }// End of 'if'.

            // Dibuja la línea hacia el nodo hijo derecho.
            if (node.right != null) {
                Line rightLine = new Line(x + hGap, y + 50, x, y);
                pane.getChildren().add(rightLine);
                String newRoute = route.equals("root") ? "root/right" : route + "/right";
                drawTree(pane, node.right, x + hGap, y + 50, hGap / 2, newRoute);
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

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        // Crea un nuevo árbol con valores aleatorios.
        avl.clear(); // Limpia ambos árboles para no crear árboles sobrepuestos entre otros.
        bst.clear();

        if (buttonAVL.isSelected()) { // Caso donde el árbol AVL esté seleccionado.
            pane.getChildren().clear();
            avl.clear();
            for (int i = 0; i <= 20; i++)
                avl.add(util.Utility.random(99));
            BTreeNode root = avl.getRoot();
            drawTree(pane, root, x, y, hgap, "root");
        }else if (buttonBST.isSelected()) { // Caso donde el árbol BST esté seleccionado.
            pane.getChildren().clear();
            bst.clear();
            for (int i = 0; i <= 20; i++)
                bst.add(util.Utility.random(99));
            BTreeNode root = bst.getRoot();
            drawTree(pane, root, x, y, hgap, "root");
        }// End of 'if'.
        checkBalance(); // Verifica si el árbol generado está balanceado, para cambiar el texto 'balanced' acordemente.
    }// End of method [randomizeOnAction].

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()){
            // Realiza los procedimientos si el árbol AVL fue seleccionado por medio del RadioButton.
            String result = String.valueOf(util.FXUtility.dialog("Eliminar", "¿Qué elemento desea eliminar?"));;

            if (!result.isEmpty())
                if (!result.trim().isEmpty())
                    try {
                        int value = Integer.parseInt(result);
                        if (avl.contains(value)) { // Verifica que el elemento se encuentre en el árbol para eliminarlo.
                            avl.remove(value);
                            util.FXUtility.alert("Eliminar", "Elemento eliminado exitosamente.");
                            pane.getChildren().clear(); // Resetea el pane principal.
                            drawTree(pane, avl.getRoot(), x, y, hgap, "root"); // Dibuja el árbol con valores actualizados.
                        } else util.FXUtility.alert("¡Ups!", "El elemento no se encuentra en el árbol.");
                    } catch (NumberFormatException e) {util.FXUtility.alert("¡Ups!", "Entrada inválida.");} // Lanza una alerta si se introducen caracteres no numéricos.
                    catch (TreeException e) {util.FXUtility.alert("¡Ups!", "Ocurrió un error al eliminar el elemento.");} // Maneja otras excepciones del árbol.
                else util.FXUtility.alert("¡Ups!", "Entrada inválida."); // Maneja una entrada vacía.
        }else if (buttonBST.isSelected()){
            // Realiza lo mismo, solo que con el árbol BST.
            String result = String.valueOf(util.FXUtility.dialog("Eliminar", "¿Qué elemento desea eliminar?"));;

            if (!result.isEmpty())
                if (!result.trim().isEmpty())
                    try {
                        int value = Integer.parseInt(result);
                        if (bst.contains(value)) {
                            bst.remove(value);
                            util.FXUtility.alert("Eliminar", "Elemento eliminado exitosamente.");
                            pane.getChildren().clear();
                            drawTree(pane, bst.getRoot(), x, y, hgap, "root");
                        } else util.FXUtility.alert("¡Ups!", "El elemento no se encuentra en el árbol.");
                    } catch (NumberFormatException e) {util.FXUtility.alert("¡Ups!", "Entrada inválida.");}
                    catch (TreeException e) {util.FXUtility.alert("¡Ups!", "Ocurrió un error al eliminar el elemento.");}
                else util.FXUtility.alert("¡Ups!", "Entrada inválida.");
            checkBalance(); // Verifica si el árbol generado está balanceado, para cambiar el texto 'balanced' acordemente.
        }// End of 'if'.
    }// End of method [removeOnAction].

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()){
            // Realiza los procedimientos si el árbol AVL fue seleccionado por medio del RadioButton.

            String addThis = String.valueOf(util.FXUtility.dialog("Add","¿Cuál elemento desea agregar?"));
            try {
                int value = Integer.parseInt(addThis); // Crea un nuevo valor a ingresar.
                avl.add(value);
                pane.getChildren().clear(); // Resetea el pane principal.
                drawTree(pane, avl.getRoot(), x, y, hgap,"root"); // Dibuja el árbol con los valores actualizados.
            } catch (NumberFormatException e) {
                util.FXUtility.alert("¡Ups!","Entrada inválida."); // Lanza una alerta si se introducen carácteres no numéricos o se cancela la entrada de datos.
            }// End of 'catch'.
        }else if (buttonBST.isSelected()){
            // Realiza lo mismo si el árbol BST fue seleccionado.
            String addThis = String.valueOf(util.FXUtility.dialog("Add","¿Cuál elemento desea agregar?"));
            try {
                int value = Integer.parseInt(addThis);
                bst.add(value);
                pane.getChildren().clear();
                drawTree(pane, bst.getRoot(), x, y, hgap,"root");

            } catch (NumberFormatException e) {
                util.FXUtility.alert("¡Ups!","Entrada inválida.");
            }// End of 'catch'.
            checkBalance(); // Verifica si el árbol generado está balanceado, para cambiar el texto 'balanced' acordemente.
        }// End of 'if'.
    }// End of method [addOnAction].

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()){
            // Realiza los procedimientos si el árbol AVL fue seleccionado por medio del RadioButton.

            String contains = String.valueOf(util.FXUtility.dialog("Contains", "¿Cuál elemento desea consultar?"));
            try {
                if (avl.contains(Integer.parseInt(contains))) util.FXUtility. // Corrobora que el elemento sí se encuentre en el árbol.
                        alert("Contains", "El objeto está incluido en el árbol AVL.");
                else util.FXUtility.
                        alert("Contains", "El objeto no está incluido en el árbol AVL.");
            } catch (TreeException | NumberFormatException e) {
                util.FXUtility.alert("¡Ups!", "Entrada inválida."); // Lanza una alerta si se introducen carácteres no numéricos o se cancela la entrada de datos.
            }// End of 'catch'.
        }else if (buttonBST.isSelected()){
            // Realiza lo mismo si el árbol BST fue seleccionado.

            String contains = String.valueOf(util.FXUtility.dialog("Contains", "¿Cuál elemento desea consultar?"));
            try {
                if (bst.contains(Integer.parseInt(contains))) util.FXUtility.
                        alert("Contains", "El objeto está incluido en el árbol BST.");
                else util.FXUtility.
                        alert("Contains", "El objeto no está incluido en el árbol BST.");
            } catch (TreeException | NumberFormatException e) {
                util.FXUtility.alert("¡Ups!", "Entrada inválida.");
            }// End of 'catch'.
        }// End of 'if'.
    }// End of method [containsOnAction].

    @javafx.fxml.FXML
    public void treeHeightOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected())
            // Verifica la altura del árbol AVL (al estar este seleccionado en los RadioButton).
            try {
                if (!avl.isEmpty()) { // Si el árbol no está vacío, muestra la altura.
                    int height = avl.height();
                    util.FXUtility.alert("Height", "La altura del árbol AVL es: " + height);
                }// End of 'if'.
            } catch (TreeException e) {
                util.FXUtility.alert("¡Ups!", "El árbol AVL está vacío."); // Lanza una alerta si el árbol está vacío.
            }// End of 'catch'.
        else if (buttonBST.isSelected())
            //Realiza lo mismo, solo que con el árbol BST.
            try {
                if (!bst.isEmpty()) {
                    int height = bst.height();
                    util.FXUtility.alert("Height", "La altura del árbol BST es: " + height);
                }// End of 'if'.
            } catch (TreeException e) {
                util.FXUtility.alert("¡Ups!", "El árbol BST está vacío.");
            }// End of 'catch'.
    }// End of method [treeHeightOnAction].

    @javafx.fxml.FXML
    public void heightOnAction(ActionEvent actionEvent) {
        if (buttonAVL.isSelected()){
            // Verifica el número de ancestros de un nodo en el árbol AVL.
            String element = String.valueOf(util.FXUtility.dialog("Height", "¿Cuál elemento desea evaluar?"));
            try {
                int measureThis = Integer.parseInt(element); // Valor del que se va a determinar el valor de ancestros.
                if (avl.contains(measureThis))  // Verifica que el árbol contenga a 'measureThis'.
                    util.FXUtility.alert("Height","La altura del elemento es: " + avl.height(measureThis));
                else util.FXUtility.alert("Height", "El objeto no está incluido en el árbol.");
            } catch (TreeException | NumberFormatException e) {
                util.FXUtility.alert("¡Ups!", "Entrada inválida."); // Lanza una alerta si se introducen carácteres no numéricos o se cancela la entrada de datos.
            }// End of 'catch'.
        }else if (buttonBST.isSelected()){
            // Hace lo mismo, solo que considerando el árbol BST.
            String element = String.valueOf(util.FXUtility.dialog("Height", "¿Cuál elemento desea evaluar?"));
            try {
                int measureThis = Integer.parseInt(element);
                if (bst.contains(measureThis))
                    util.FXUtility.alert("Height","La altura del elemento es: " + bst.height(measureThis));
                else util.FXUtility.alert("Height", "El objeto no está incluido en el árbol.");
            } catch (TreeException | NumberFormatException e) {
                util.FXUtility.alert("¡Ups!", "Entrada inválida.");
            }// End of 'catch'.
        }// End of 'if'.
    }// End of method [heightOnAction].

    @javafx.fxml.FXML
    public void avlOnAction(ActionEvent actionEvent){
        // Al seleccionar este RadioButton, se genera aleatoriamente un nuevo árbol AVL.

        pane.getChildren().clear();
        avl.clear();
        for (int i = 0; i <= 20; i++)
            avl.add(util.Utility.random(99));
        BTreeNode root = avl.getRoot();
        drawTree(pane, root, x, y, hgap, "root");
        checkBalance(); // Se verifica si el árbol está balanceado o no para cambiar el texto 'balanced' acordemente.
    }// End of method [avlOnAction].

    @javafx.fxml.FXML
    public void bstOnAction(ActionEvent actionEvent) {
        // Al seleccionar este RadioButton, se genera aleatoriamente un nuevo árbol BST.

        pane.getChildren().clear();
        bst.clear();
        for (int i = 0; i <= 20; i++)
            bst.add(util.Utility.random(99));
        BTreeNode root = bst.getRoot();
        drawTree(pane, root, x, y, hgap, "root");
        checkBalance();  // Se verifica si el árbol está balanceado o no para cambiar el texto 'balanced' acordemente.
    }// End of method [bstOnAction].

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
}// End of class [BST_AVL_OperationsController].
