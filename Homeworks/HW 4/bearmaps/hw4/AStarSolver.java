package bearmaps.hw4;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution = new ArrayList<>();
    private double timeSpent = 0;
    private int statesExplored = 0;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        HashMap<Vertex, Double> distTo = new HashMap<>();
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();
        List<WeightedEdge<Vertex>> neighborEdges;

        fringe.add(start, 0.0);
        distTo.put(start, 0.0);

        while (fringe.size() != 0) {
            if (fringe.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                Vertex v = end;
                solutionWeight += distTo.get(v);
                while (!v.equals(start)) {
                    Vertex prev = edgeTo.get(v);
                    solution.add(0, v);
                    v = prev;
                }
                solution.add(0, start);
                return;
            } else if (sw.elapsedTime() >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                timeSpent = sw.elapsedTime();
                return;
            }

            Vertex smallest = fringe.removeSmallest();
            statesExplored += 1;
            neighborEdges = input.neighbors(smallest);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                Vertex p = e.from();
                Vertex q = e.to();
                double w = e.weight();
                double prev = distTo.get(p);

                if (prev + w < distTo.getOrDefault(q, Double.POSITIVE_INFINITY)) {
                    distTo.put(q, prev + w);
                    edgeTo.put(q, p);

                    if (fringe.contains(q)) {
                        fringe.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    } else {
                        fringe.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return statesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
