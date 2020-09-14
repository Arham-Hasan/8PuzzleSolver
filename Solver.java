import java.util.LinkedList;
import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private class Node implements Comparable<Node> {
        private Node previousNode;
        private Board board;
        private int numMoves;

        public Node(Board board,int moves, Node previous) {
            this.board = board;
            this.previousNode = previous;
            this.numMoves = moves;
        }

        public int compareTo(Node node) {
            return this.board.manhattan() + this.numMoves - node.board.manhattan()  - node.numMoves;
        }
    }

    private Node lastNode;

    private Node aAlgorithm(MinPQ<Node> nodes) {
        //if(nodes.isEmpty()) return null;
        Node selectedNode = nodes.delMin();
        if (selectedNode.board.isGoal()) return selectedNode;
        for (Board neighbor : selectedNode.board.neighbors()) {
            if (selectedNode.previousNode == null || !neighbor.equals(selectedNode.previousNode.board)) {
                nodes.insert(new Node(neighbor, selectedNode.numMoves+1, selectedNode));
            }
        }
        return null;
    }

    public Solver(Board initial) {
        MinPQ<Node> mainPQ = new MinPQ<Node>();
        mainPQ.insert(new Node(initial,0,null));

        MinPQ<Node> twinPQ = new MinPQ<Node>();
        twinPQ.insert(new Node(initial.twin(),0,null));

        while(true) {
            lastNode = aAlgorithm(mainPQ);
            if (lastNode != null || aAlgorithm(twinPQ) != null) return;
        }
    }


    public boolean isSolvable() {
        return (lastNode != null);
    }

    public int moves() {
        if (isSolvable()) return lastNode.numMoves;
        return -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        LinkedList<Board> steps = new LinkedList<Board>();
        Node latestNode = lastNode;
        while(latestNode != null) {
            steps.addFirst(latestNode.board);
            latestNode = latestNode.previousNode;
        }
        return steps;
    }
}
