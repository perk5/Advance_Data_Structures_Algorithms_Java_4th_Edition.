public class LastNodeLinkedListPriorityQ {

    private static class HeapNode {
        int key;

        HeapNode left;
        HeapNode right;
        HeapNode parent;

        // Points to next external node
        HeapNode nextRight;

        HeapNode(int key) {
            this.key = key;
        }
    }

    private HeapNode root;
    private HeapNode lastNode;

    public void insert(int key) {

        HeapNode n = new HeapNode(key);

        // First node
        if (root == null) {
            root = n;
            lastNode = n;

            // Circular reference
            n.nextRight = n;
            return;
        }

        // Parent where next insertion should occur
        HeapNode p = lastNode.nextRight;

        if (p.left == null) {

            // Insert as left child
            p.left = n;
            n.parent = p;

            /*
             * p remains external because
             * it still has no right child.
             */

            n.nextRight = p.nextRight;
            p.nextRight = n;

        } else {

            // Insert as right child
            p.right = n;
            n.parent = p;

            /*
             * p is no longer external.
             * Replace p with n in the list.
             */

            n.nextRight = p.nextRight;
            p.nextRight = n;
        }

        lastNode = n;
    }

    // Level-order display
    public void printLevelOrder() {

        if (root == null) {
            System.out.println("Heap is empty");
            return;
        }

        java.util.Queue<HeapNode> q = new java.util.LinkedList<>();

        q.add(root);

        while (!q.isEmpty()) {

            HeapNode current = q.remove();

            System.out.print(current.key + " ");

            if (current.left != null)
                q.add(current.left);

            if (current.right != null)
                q.add(current.right);
        }

        System.out.println();
    }

    // Display nextRight chain
    public void printNextRightChain(int count) {

        if (root == null)
            return;

        HeapNode current = lastNode;

        System.out.print("nextRight chain: ");

        for (int i = 0; i < count; i++) {
            System.out.print(current.key + " -> ");
            current = current.nextRight;
        }

        System.out.println("...");
    }

    public static void main(String[] args) {

        LastNodeLinkedListPriorityQ h = new LastNodeLinkedListPriorityQ();

        h.insert(10);
        h.insert(20);
        h.insert(30);
        h.insert(40);
        h.insert(50);
        h.insert(60);
        h.insert(70);

        System.out.println("Level Order:");
        h.printLevelOrder();

        h.printNextRightChain(10);
    }

}