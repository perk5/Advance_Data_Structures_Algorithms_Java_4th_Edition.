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

    public interface Tree {
        int size();

        boolean isEmpty();

        DNode root();

        DNode parent(DNode p);

        boolean isExternal(DNode p);

    }

    public interface BTree extends Tree {
        DNode left(DNode p);

        DNode right(DNode p);

        boolean hasLeft(DNode p);

        boolean hasRight(DNode p);

    }

    public static class SlicingFP implements BTree {

        private int size;
        private DNode root;

        public SlicingFP(int initialWidth, int initialHeight) {
            if (initialWidth < 0 || initialHeight < 0)
                throw new IllegalArgumentException("Dimensions must be non-negative");
            this.root = new DNode(initialWidth, initialHeight, 'L');
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
                throw new IllegalArgumentException("can only decompose a basic rectangle(leaf node)!");

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
        public void setDimensions(DNode v, int width, int height) {
            // if (!isExternal(v)) {
            // throw new IllegalArgumentException("Can only assign dimensions to basic
            // rectangles (leaves)!");
            // }
            if (width < 0 || height < 0)
                throw new IllegalArgumentException("Dimensions must be non-negative");
            v.width = width;
            v.height = height;
        }

        // Draw the slicing tree associated with the floorplan.
        public void drawSlicingFP(DNode n, int depth) {

            if (n == null) {
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

        // Draw Compact SlicingFloorPlan..
        public void drawCompactSlicingFP(DNode n, int depth) {

            if (n == null) {
                return;
            }

            drawCompactSlicingFP(n.left, depth + 1);
            drawCompactSlicingFP(n.right, depth + 1);

            for (int i = 0; i < depth; i++) {
                System.out.print(" ");
            }
            if (n.type == 'V') {
                if (n.left == null || n.right == null) {
                    throw new IllegalArgumentException("V Doesn't have childrens..");
                }
                int width = n.left.width + n.right.width;
                int height = Math.max(n.left.height, n.right.height);
                System.out.println("Cut Node 'V' (|) Bounding:" + width + "*" + height);
                setDimensions(n, width, height);
            }
            if (n.type == 'H') {
                if (n.left == null || n.right == null) {
                    throw new IllegalArgumentException("H Doesn't have children..");
                }
                int width = Math.max(n.left.width, n.right.width);
                int height = n.left.height + n.right.height;
                System.out.println("Cut Node 'H' (-) Bounding:" + width + "*" + height);
                setDimensions(n, width, height);
            }
            if (n.type == 'L') {
                System.out.println("[Leaf Box] Target Size: " + n.width + "*" + n.height);
            }

        }

    }

    public static void main(String args[]) {
        SlicingFP sfp = new SlicingFP(0, 0);
        sfp.splitHorizontal(sfp.root());
        sfp.splitVertical(sfp.root.left);
        sfp.splitVertical(sfp.root.right);
        sfp.splitHorizontal(sfp.root.left.right);
        sfp.splitVertical(sfp.root.left.right.right);
        sfp.setDimensions(sfp.root.left.left, 5, 25);

        sfp.setDimensions(sfp.root.left.right.left, 15, 10);

        sfp.setDimensions(sfp.root.left.right.right.left, 5, 15);
        sfp.setDimensions(sfp.root.left.right.right.right, 10, 15);
        sfp.setDimensions(sfp.root.right.left, 5, 15);
        sfp.setDimensions(sfp.root.right.right, 15, 15);

        sfp.drawCompactSlicingFP(sfp.root, 0);
        System.out.println("");
        sfp.drawSlicingFP(sfp.root, 0);
    }
}