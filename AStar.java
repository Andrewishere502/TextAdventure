import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.Stack;


public class AStar {
    private Node source;
    private Node target;
    private int[][] grid;

    private LinkedList<Node> closed = new LinkedList<Node>();
    private MinPQ<Node> open = new MinPQ<Node>();

    public AStar(int[] source, int[] target, int[][] grid) {
        this.source = new Node(source[0], source[1], 0, 0, null);
        this.target = new Node(target[0], target[1], 0, 0, null);

        // passable tiles are 0, nonpassable tiles are 1
        this.grid = grid;

        // Add the source node to the target
        open.insert(this.source);
    }

    // Return a stack of coordinates representing the path to take from
    // the source to the target.
    public Stack<int[]> solve() {
        while (true) {
            // Grab the lowest cost node from the open nodes
            Node current = open.delMin();
            // Close the curent node
            closed.add(current);

            // Check if we've found the target
            if (current.equals(target)) {
                // Found the target so end loop
                break;
            }

            // Add all of the current node's valid neighbors to open, or update
            // their current representation in open.
            for (Node neighbor : getNeighbors(current)) {
                // If the neighbor is already closed, skip this neighbor
                if (isClosed(neighbor)) continue;

                // If the neighbor is open already
                Node existingNode = findExistingNode(neighbor);
                if (existingNode != null) {
                    // If the neighbor has a better cost than the already existing
                    // representation of it in open, then update that existing node's
                    // g and h cost.
                    if (neighbor.getCost() < existingNode.getCost()) {
                        existingNode.setCost(neighbor.getGCost(), neighbor.getHCost());
                    }
                } else { // Node is not open yet
                    // Add the neighbor to open
                    open.insert(neighbor);
                }

            }
        }

        return traverse();
    }

    // Return a linked list of neighboring cells that are passable
    private LinkedList<Node> getNeighbors(Node node) {
        LinkedList<Node> neighbors = new LinkedList<Node>();

        // Northern neighbor
        int northXPos = node.getXPos();
        int northYPos = node.getYPos() - 1;
        if (validateCoords(northXPos, northYPos)) {
            int northGCost = calcGCost(northXPos, northYPos);
            int northHCost = calcHCost(northXPos, northYPos);
            Node northNode = new Node(
                        northXPos, northYPos,
                        northGCost, northHCost,
                        node
                    );
            neighbors.add(northNode);
        }

        // Southern neighbor
        int southXPos = node.getXPos();
        int southYPos = node.getYPos() + 1;
        if (validateCoords(southXPos, southYPos)) {
            int southGCost = calcGCost(southXPos, southYPos);
            int southHCost = calcHCost(southXPos, southYPos);
            Node southNode = new Node(
                        southXPos, southYPos,
                        southGCost, southHCost,
                        node
                    );
            neighbors.add(southNode);
        }

        // Eastern neighbor
        int eastXPos = node.getXPos() + 1;
        int eastYPos = node.getYPos();
        if (validateCoords(eastXPos, eastYPos)) {
            int eastGCost = calcGCost(eastXPos, eastYPos);
            int eastHCost = calcHCost(eastXPos, eastYPos);
            Node eastNode = new Node(
                        eastXPos, eastYPos,
                        eastGCost, eastHCost,
                        node
                    );
            neighbors.add(eastNode);
        }

        // Western neighbor
        int westXPos = node.getXPos() - 1;
        int westYPos = node.getYPos();
        if (validateCoords(westXPos, westYPos)) {
            int westGCost = calcGCost(westXPos, westYPos);
            int westHCost = calcHCost(westXPos, westYPos);
            Node westNode = new Node(
                        westXPos, westYPos,
                        westGCost, westHCost,
                        node
                    );
            neighbors.add(westNode);
        }

        return neighbors;
    }

    // Return true if the given coordinates are valid,
    // i.e. within the grid and are passable.
    private boolean validateCoords(int xPos, int yPos) {
        try {
            // Try accessing these coordinates, checking in they are
            // passable
            return grid[yPos][xPos] == 0;

        } catch (ArrayIndexOutOfBoundsException e) {
            // The coordinates are out of bounds of the grid
            return false;
        }
    }

    // Return true if the node is in closed
    private boolean isClosed(Node node) {
        for (Node closedNode : closed) {
            // If the closed node and the node in question have
            // the same x and y position.
            if (closedNode.equals(node)) {
                return true;
            }
        }
        return false;
    }

    // Return true is the node is in open
    private boolean isOpen(Node node) {
        for (Node openNode : open) {
            // If the open node and the node in question have
            // the same x and y position.
            if (openNode.equals(node)) {
                return true;
            }
        }
        return false;
    }

    // Update a node already in open
    private Node findExistingNode(Node node) {
        // Find the node within open
        for (Node openNode : open) {
            // Node and open node have the same x and y position
            if (openNode.equals(node)) {
                return openNode;
            }
        }

        return null;
    }

    // Return gcost of a tile, the manhattan distance between the
    // source tile and the tile in question.
    private int calcGCost(int xPos, int yPos) {
        return Math.abs(source.getXPos() - xPos) + Math.abs(source.getYPos() - yPos);
    }

    // Return hcost of a tile, the manhattan distance between the
    // target tile and the tile in question.
    private int calcHCost(int xPos, int yPos) {
        return Math.abs(target.getXPos() - xPos) + Math.abs(target.getYPos() - yPos);
    }

    // Traverse back from the target to the source
    private Stack<int[]> traverse() {
        Node nextNode = null;
        for (Node node : closed) {
            if (node.equals(target)) {
                nextNode = node;
            }
        }

        Stack<int[]> path = new Stack<int[]>();
        while (true) {
            // Add the coordinates of the next node to the path
            path.push(new int[] {nextNode.getXPos(), nextNode.getYPos()});
            // Set the next next node to the parent of the current next node
            nextNode = nextNode.getParent();
            // If the next node is the source, return
            if (nextNode.equals(source)) {
                return path;
            }
        }
    }

}
