public class SlicingFloorPlan {

    public static class DNode {

        DNode parent, left, right;
        int height;
        int width;

        char type;

        public DNode(int width, int height, char type) {
            this.height = height;
            this.width = width;
            this.type = type;
        }

    }

    public interface trees {
        int size();

        boolean isEmpty();

        DNode root();

        DNode parent(DNode p);

        boolean isExternal(DNode p);

    }

    public interface BTree extends trees {
        DNode left(DNode p);

        DNode right(DNode p);

        boolean hasLeft(DNode p);

        boolean hasRight(DNode p);

    }

    public static class SlicingFP implements BTree {

        private int size;
        private DNode root;

        public SlicingFP(int initialWidth, int initialHeight) {
            this.root = new DNode(initialHeight, initialWidth, 'L');
            this.size = 1;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public DNode root() {
            return root;
        }

        public DNode parent(DNode p) {
            return p.parent;
        }

        public DNode left(DNode p) {
            return p.left;
        }

        public DNode right(DNode p) {
            return p.right;
        }

        public boolean hasLeft(DNode p) {
            return p.left != null;
        }

        public boolean hasRight(DNode p) {
            return p.right != null;
        }

        public boolean isExternal(DNode p) {
            return p.left == null && p.right == null;
        }

        // Decompose a basic rectangle by means of a vertical cut.

        public void splitVertical(DNode p) {

            if (!isExternal(p))
                throw new IllegalArgumentException("can only decompose a basic rectengle(leaf node)!");

            p.type = 'V';

            p.left = new DNode(0, 0, 'L');
            p.right = new DNode(0, 0, 'L');
            size += 2;
            p.left.parent = p;
            p.right.parent = p;
        }

        // Decompose a basic rectangle by means of a horizontal cut.
        public void splitHorizontal(DNode p) {
            if (!isExternal(p))
                throw new IllegalArgumentException("Can only decompose a basic rectangle (leaf node)!");

            p.type = 'H';

            p.left = new DNode(0, 0, 'L');

            p.right = new DNode(0, 0, 'L');
            size += 2;
            p.left.parent = p;
            p.right.parent = p;
        }

        // Assign minimum height and width to a basic rectangle.
        public void assignDimensions(DNode v, int width, int height) {
            if (!isExternal(v)) {
                throw new IllegalArgumentException("Can only assign dimensions to basic rectangles (leaves)!");
            }
            v.width = width;
            v.height = height;
        }

        // Draw the slicing tree associated with the floorplan.

        public void drawSlicingFP(DNode n, int depth) {

            if(n == null){
                return;
            }

            for (int i = 0; i < depth; i++) {
                System.out.print(" ");
            }

            if (n.type == 'V') {
                System.out.println("Cut Node 'V' (|) Bounding:" + n.width + "*" + n.height);
            }
            if (n.type == 'H') {
                System.out.println("Cut Node 'H' (-) Bounding:" + n.width + "*" + n.height);
            }
            if (n.type == 'L') {
                System.out.println("[Leaf Box] Target Size: " + n.width + "*" + n.height);
            }

            drawSlicingFP(n.left, depth + 1);
            drawSlicingFP(n.right, depth + 1);

        }

    }

    public static void main(String args[]) {
        SlicingFP SFP = new SlicingFP(20, 40);
    }
}