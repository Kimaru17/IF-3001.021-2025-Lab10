package domain;

/* *
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * Binary Search Tree AVL (Arbol de Búsqueda Binaria AVL)
 * AVL = Arbol de busqueda binaria auto balanceado
 * */
public class AVL implements  Tree {
    private BTreeNode root; //se refiere a la raiz del arbol

    @Override
    public int size() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null) return 0;
        else return 1 + size(node.left) + size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null) return false;
        else if(util.Utility.compare(node.data, element)==0) return true;
        else if(util.Utility.compare(element, node.data)<0)
            return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
       this.root = add(root, element, "root");
    }

    private BTreeNode add(BTreeNode node, Object element, String path){
        if(node==null)
            node = new BTreeNode(element, path);
        else if(util.Utility.compare(element, node.data)<0)
            node.left = add(node.left, element, path+"/left");
        else if(util.Utility.compare(element, node.data)>0)
            node.right = add(node.right, element, path+"/right");

        //una vez agregado el nuevo nodo, debemos determinar si se requiere rebalanceo para siga siendo BST-AVL
        node = reBalance(node);
        return node;
    }

    private BTreeNode reBalance(BTreeNode node) {
        if (node == null) return null;
        int balance = getBalanceFactor(node);

        // Caso 1: Desequilibrio izquierda-izquierda
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            node.path += "/Simple-Right-Rotate";
            return rightRotate(node);
        }
        // Caso 2: Desequilibrio izquierda-derecha
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.path += "/Double-Left-Right-Rotate";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Caso 3: Desequilibrio derecha-derecha
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            node.path += "/Simple-Left-Rotate";
            return leftRotate(node);
        }
        // Caso 4: Desequilibrio derecha-izquierda
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.path += "/Double-Right-Left-Rotate";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    //retorna el factor de balanceo del árbol a partir del nodo nado
    private int getBalanceFactor(BTreeNode node){
        if(node==null){
            return 0;
        }else{
            return height(node.left) - height(node.right);
        }
    }

    private BTreeNode leftRotate(BTreeNode node) {
        BTreeNode node1 = node.right;
        if (node1 != null){ //importante para evitar NullPointerException
            BTreeNode node2 = node1.left;
            //se realiza la rotacion (perform rotation)
            node1.left = node;
            node.right = node2;
        }
        return node1;
    }

    private BTreeNode rightRotate(BTreeNode node) {
        BTreeNode node1 = node.left;
        if (node1 != null) { //importante para evitar NullPointerException
            BTreeNode node2 = node1.right;
            //se realiza la rotacion (perform rotation)
            node1.right = node;
            node.left = node2;
        }
        return node1;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element) throws TreeException {
        if (node == null) return null;

        if (util.Utility.compare(element, node.data) < 0) {
            node.left = remove(node.left, element);
        } else if (util.Utility.compare(element, node.data) > 0) {
            node.right = remove(node.right, element);
        } else {
            // Casos de eliminación
            if (node.left == null && node.right == null) return null;
            else if (node.left != null && node.right == null) return node.left;
            else if (node.left == null && node.right != null) return node.right;
            else {
                Object value = min(node.right);
                node.data = value;
                node.right = remove(node.right, value);
            }
        }
        return reBalance(node); // Reequilibra después de eliminar
    }

    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return height(root, element, 0);
    }

    //devuelve la altura de un nodo (el número de ancestros)
    private int height(BTreeNode node, Object element, int level){
        if(node==null) return 0;
        else if(util.Utility.compare(node.data, element)==0) return level;
        else return Math.max(height(node.left, element, ++level),
                    height(node.right, element, level));
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        //return height(root, 0); //opción-1
        return height(root); //opción-2
    }

    //devuelve la altura del árbol (altura máxima de la raíz a
    //cualquier hoja del árbol)
    private int height(BTreeNode node, int level){
        if(node==null) return level-1;//se le resta 1 al nivel pq no cuente el nulo
        return Math.max(height(node.left, ++level),
                height(node.right, level));
    }

    //opcion-2
    private int height(BTreeNode node){
        if(node==null) return -1; //retorna valor negativo para eliminar el nivel del nulo
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public Object min() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return min(root);
    }

    private Object min(BTreeNode node){
        if(node.left!=null)
            return min(node.left);
        return node.data;
    }

    @Override
    public Object max() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return max(root);
    }

    private Object max(BTreeNode node){
        if(node.right!=null)
            return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return preOrder(root);
    }

    //recorre el árbol de la forma: nodo-hijo izq-hijo der
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result = node.data+" ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return  result;
    }

    //recorre el árbol de la forma: nodo-hijo izq-hijo der
    private String preOrderPath(BTreeNode node){
        String result="";
        if(node!=null){
            result  = node.data+"("+node.path+")"+" ";
            result += preOrderPath(node.left);
            result += preOrderPath(node.right);
        }
        return  result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return inOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-nodo-hijo der
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+" ";
            result += inOrder(node.right);
        }
        return  result;
    }

    //para mostrar todos los elementos existentes
    @Override
    public String postOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return postOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-hijo der-nodo,
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+" ";
        }
        return result;
    }

    @Override
    public String toString() {
        String result="AVL Binary Search Tree Content:";
        try {
            result = "PreOrder: "+preOrderPath(root);
            result+= "\nPreOrder: "+preOrder();
            result+= "\nInOrder: "+inOrder();
            result+= "\nPostOrder: "+postOrder();

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean isBalanced() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty.");
        }
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode node) {
        if (node == null) {
            return true;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return Math.abs(leftHeight - rightHeight) <= 1
                && isBalanced(node.left)
                && isBalanced(node.right);
    }

    public BTreeNode getRoot () {
        return root;
    }
    public Object father(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        // Si es la raíz, no tiene padre
        if (util.Utility.compare(root.data, element) == 0)
            return null;
        return father(root, element, null);
    }

    private Object father(BTreeNode node, Object element, BTreeNode parent) {
        if (node == null) {
            // elemento no encontrado
            return null;
        }
        int cmp = util.Utility.compare(element, node.data);
        if (cmp == 0) {
            return (parent != null) ? parent.data : null;
        } else if (cmp < 0) {
            return father(node.left, element, node);
        } else {
            return father(node.right, element, node);
        }
    }


// ----------------- BROTHER -----------------

    /**
     * Devuelve el hermano (sibling) del elemento dado en el árbol.
     * @throws TreeException si el árbol está vacío
     * @return dato del hermano, o null si raíz, sin hermano, o elemento no existe
     */
    public Object brother(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return brother(root, element, null);
    }

    private Object brother(BTreeNode node, Object element, BTreeNode parent) {
        if (node == null) {
            // elemento no encontrado
            return null;
        }
        int cmp = util.Utility.compare(element, node.data);
        if (cmp == 0) {
            if (parent == null) return null;      // es la raíz
            // si node es left de parent, devolvemos right, y viceversa
            if (parent.left == node) {
                return (parent.right != null) ? parent.right.data : null;
            } else {
                return (parent.left != null) ? parent.left.data : null;
            }
        } else if (cmp < 0) {
            return brother(node.left, element, node);
        } else {
            return brother(node.right, element, node);
        }
    }


// ----------------- CHILDREN -----------------

    /**
     * Devuelve los hijos del elemento dado en el árbol.
     * @throws TreeException si el árbol está vacío
     * @return cadena con datos de los hijos, o "" si no tiene o no existe
     */
    public String children(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return children(root, element);
    }

    private String children(BTreeNode node, Object element) {
        if (node == null) {
            // elemento no encontrado
            return "";
        }
        int cmp = util.Utility.compare(element, node.data);
        if (cmp < 0) {
            return children(node.left, element);
        } else if (cmp > 0) {
            return children(node.right, element);
        } else {
            String result = "";
            if (node.left != null)  result = node.left.data.toString();
            if (node.right != null) {
                if (!result.isEmpty()) result += " ";
                result += node.right.data.toString();
            }
            return result;
        }
    }

}
