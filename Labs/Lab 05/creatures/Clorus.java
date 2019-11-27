package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        b = 231;
        g = 0;
        energy = e;
    }

    public Color color() {
        return color(r, g, b);
    }

    public Clorus() {
        this(1);
    }

    public String name() {
        return "clorus";
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();

        for (Map.Entry<Direction, Occupant> i:neighbors.entrySet()) {
            if (i.getValue().name().equals("empty")) {
                emptyNeighbors.addLast(i.getKey());
            } else if (i.getValue().name().equals("plip")) {
                plips.addLast(i.getKey());
            }
        }

        // Rule 1
        if (emptyNeighbors.isEmpty() && plips.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        Object[] p = plips.toArray();
        if (!plips.isEmpty()) {
            int rnd = new Random().nextInt(plips.size());
            return new Action(Action.ActionType.ATTACK, (Direction) p[rnd]);
        }

        // Rule 3
        Object[] d = emptyNeighbors.toArray();
        if (!emptyNeighbors.isEmpty() && energy >= 1.0) {
            int rnd = new Random().nextInt(emptyNeighbors.size());
            return new Action(Action.ActionType.REPLICATE, (Direction) d[rnd]);
        }

        // Rule 4
        int rnd = new Random().nextInt(emptyNeighbors.size());
        return new Action(Action.ActionType.MOVE, (Direction) d[rnd]);

    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }
}
