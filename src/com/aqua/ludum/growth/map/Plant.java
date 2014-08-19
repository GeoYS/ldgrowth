package com.aqua.ludum.growth.map;

import com.aqua.ludum.growth.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 *
 * @author Duane Byer
 */
public abstract class Plant {
    
    public Plant(Terrain terrain, Point position) {
        this.terrain = terrain;
        this.growths = new ArrayList<>();
        this.removals = new ArrayList<>();
        this.rootRemovals = new ArrayList<>();
        this.root = new Node(position, new Node[] { } );
        this.nodes = new ArrayList<>();
        this.nodes.add(this.root);
    }
    
    public final void render(SpriteBatch batch) {
    }
    
    public final void update(float delta) {
        control(delta);
        for (Node node : this.nodes) {
            node.update(delta);
        }
        ListIterator<Growth> itGrowth = this.growths.listIterator();
        while (itGrowth.hasNext()) {
            Growth growth = itGrowth.next();
            Node node = growth.node;
            Node target = growth.target;
            Point nextPosition = nextGrowPosition(node.position, target.position);
            boolean atTarget =
                    (target.position.x - node.position.x) * (target.position.x - node.position.x) +
                    (target.position.y - node.position.y) * (target.position.y - node.position.y) <
                    Constants.PLANT_NODE_DISTANCE * Constants.PLANT_NODE_DISTANCE;
            Node next;
            if (!atTarget) {
                next = new Node(nextPosition, new Node[] { node });
                this.nodes.add(next);
            }
            else {
                next = new Node(nextPosition, new Node[] { node, target });
                this.nodes.add(next);
                if (!this.nodes.contains(target)) {
                    this.nodes.add(target);
                }
                insertNeighbour(target, next);
                itGrowth.remove();
            }
            insertNeighbour(node, next);
            growth.node = next;
        }
        
        for (Removal removal : this.removals) {
            for (Node node : removal.nodes) {
                if (!connectedToRoot(node)) {
                    for (Node neighbour : node.neighbours) {
                        if (!removal.nodes.contains(neighbour)) {
                            this.rootRemovals.add(new NotConnectedToRootRemoval(neighbour));
                        }
                    }
                }
                if (node != this.root) {
                    this.nodes.remove(node);
                }
            }
        }
        this.removals.clear();
        
        ListIterator<NotConnectedToRootRemoval> itRemoval = this.rootRemovals.listIterator();
        while (itRemoval.hasNext()) {
            NotConnectedToRootRemoval removal = itRemoval.next();
            this.nodes.remove(removal.node);
            for (Node neighbour : removal.node.neighbours) {
                if (this.nodes.contains(neighbour)) {
                    itRemoval.add(new NotConnectedToRootRemoval(neighbour));
                }
            }
            itRemoval.remove();
        }
    }
    
    private void insertNeighbour(Node node, Node newNeighbour) {
        Node[] neighbours = node.neighbours;
        node.neighbours = new Node[node.neighbours.length + 1];
        System.arraycopy(neighbours, 0, node.neighbours, 1, neighbours.length);
        node.neighbours[0] = newNeighbour;
    }
    
    private Point nextGrowPosition(Point position, Point target) {
        double deltaX = target.x - position.x;
        double deltaY = target.y - position.y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double normX = deltaX / distance;
        double normY = deltaY / distance;
        return new Point(position.x + normX * Constants.PLANT_NODE_DISTANCE, position.y + normY * Constants.PLANT_NODE_DISTANCE);
    }
    
    private boolean connectedToRoot(Node node) {
        Set<Node> visited = new HashSet<>();
        Set<Node> toVisit = new HashSet<>();
        toVisit.add(node);
        while (!toVisit.isEmpty()) {
            Set<Node> nextToVisit = new HashSet<>();
            for (Node next : toVisit) {
                if (next == this.root) {
                    return true;
                }
                else {
                    for (Node neighbour : next.neighbours) {
                        if (!visited.contains(neighbour)) {
                            nextToVisit.add(neighbour);
                        }
                    }
                    visited.add(next);
                }
            }
            toVisit = nextToVisit;
        }
        return false;
    }
    
    protected final void growTowards(Node from, Node target) {
        this.growths.add(new Growth(from, target));
    }
    
    protected final void destroy(List<Node> nodes) {
        this.removals.add(new Removal(nodes));
    }
    
    public abstract void control(float delta);
    
    private Terrain terrain;
    private Node root;
    private List<Node> nodes;
    private List<Growth> growths;
    private List<Removal> removals;
    private List<NotConnectedToRootRemoval> rootRemovals;
    
    private class Node {
        
        public Node(Point position, Node[] neighbours) {
            this.nutrients = 0.0;
            this.light = 0.0;
            this.water = 0.0;
            this.position = position;
            this.neighbours = neighbours;
        }
        
        public void update(float delta) {
        }
        
        public double nutrients;
        public double light;
        public double water;
        
        public Point position;
        public Node[] neighbours;
        
    }
    
    private static class Growth {
        
        public Growth(Node node, Node target) {
            this.node = node;
            this.target = target;
        }
        
        public Node node;
        public Node target;
        
    }
    
    private static class Removal {
        
        public Removal(List<Node> nodes) {
            this.nodes = nodes;
        }
        
        public List<Node> nodes;
        
    }
    
    private static class NotConnectedToRootRemoval {
        
        public NotConnectedToRootRemoval(Node node) {
            this.node = node;
        }
        
        public Node node;
        
    }
    
}
