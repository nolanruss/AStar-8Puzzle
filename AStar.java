import java.util.*;
import java.io.*;

public class AStar {
    /* Global variables. */
    /* Debug statement flag. */
    private static final boolean debug = true;
    /* Puzzle. */
    private static EightPuzzle ep = new EightPuzzle();
    /* Nodes. */
    private static final EightNode start = ep.getStart();
    private static final EightNode goal = ep.getGoal();
    /* States. */
    private static final ArrayList<Integer> startState = start.getState();
    private static final ArrayList<Integer> goalState = goal.getState();
    /* State lists. */
    private static PriorityQueue<EightNode> open =
        new PriorityQueue<EightNode>();
    private static ArrayList<EightNode> closed = 
        new ArrayList<EightNode>();

    /* Main method. */
    public static void main(String[] args) {
        open.add(start);
        
        System.out.println("START: ");
        open.peek().printState();
        System.out.println("\n");

        search();
        
        for (int i = 0; i < closed.size(); i++) {
            if (i == 0)
                System.out.println("SHORTEST PATH: ");
            
            EightNode node = closed.get(i);
            node.printState();
        }
    }

    /* Search method. */
    public static void search() {
        /* Print starting... */
        System.out.println("A* search has begun.\n");

        /* Perform search while there are more nodes to be processed. */
        while (open.size() > 0) {
            /* Get the best node (parent) from open list (least f(n)). */
            EightNode parent = getBestNode();
            /* Get states. */
            ArrayList<Integer> parentState = parent.getState();

            /* Print parent state. */
            if (debug) {
                System.out.println("PARENT: ");
                parent.printState();
                System.out.println("");
            }
            
            /* Check if parent is the goal. */
            if (parentState.equals(goalState)) {
                /* Done. */
                break;
            }
            
            /* Add parent to closed list. */
            closed.add(parent);
            
            /* Expand parent to all adjacent nodes. */
            ArrayList<EightNode> moves = ep.getMoves(parent);


            /* Print parent's children. */
            if (debug) {
                System.out.println("moves.size() == " + moves.size());
                for (int i = 0; i < moves.size(); i++) {
                    if (i == 0)
                        System.out.println("CHILDREN: ");
                    moves.get(i).printState();
                }
            }
           
            /* Loop through the adjacent nodes. */
            for (int i = 0; i < moves.size(); i++) {
                /* Assign adjNode. */
                EightNode adjNode = moves.get(i);
                /* Assign adjNodeState. */
                ArrayList<Integer> adjNodeState = adjNode.getState();

                /* If adjacent node is already in closed set... */
                if (closed.contains(adjNode)) {
                    /* Discard move. */ 
                    moves.remove(adjNode);
                    /* Continue next iteration of for loop. */
                    continue;
                }
                /* Else, if adjacent node is already in open set... */
                else if (open.contains(adjNode)) {
                    /* Assign openNode. */
                    EightNode openNode = open.peek();

                    /* Get g(n) for adjNode and open nodes. */
                    int adjNode_g = adjNode.getG();
                    int open_g = openNode.getG();

                    /* If adjNode g(n) < open g(n)... */
                    if (adjNode_g < open_g) {
                        /* Discard head move from open list. */
                        open.poll();
                        
                        /* Add adjNode to open list. */
                        open.add(adjNode);
                        continue;
                    }
                }
                else {
                    /* Add adjNode to open list. */
                    open.add(adjNode);
                }
            }
        }
    }

    /* Get best node by evaluating each node's f(n). */
    public static EightNode getBestNode() {
        /* Return 0th element in the PriorityQueue. */
        return open.poll(); 
    }
}
