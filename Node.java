public class Node implements Comparable<Node> {
    private int xPos;
    private int yPos;
    private int gCost;
    private int hCost;
    private Node parent;

    public Node(int xPos, int yPos, int gCost, int hCost, Node parent) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }

    public int getXPos() { return xPos; }
    public int getYPos() { return yPos; }
    public int getGCost() { return gCost; }
    public int getHCost() { return hCost; }
    public int getCost() { return gCost + hCost; }
    public Node getParent() { return parent; }

    public void setCost(int gCost, int hCost) {
        this.gCost = gCost;
        this.hCost = hCost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    // Ordering the nodes compares the costs of each node
    @Override public int compareTo(Node N) {
        // if the costs are equal compare H costs,
        // which is the distance from the target
        if (this.getCost() == N.getCost()) {
            return this.getHCost() - N.getHCost();

        // Else return the difference in cost
        } else{
            return this.getCost() - N.getCost();
        }

    }

    // Checking if two nodes are equals compares the coordinates of the node
    public boolean equals(Node N) {
        return (this.getXPos() == N.getXPos()) && (this.getYPos() == N.getYPos());
    }

}