package com.aqua.ludum.growth.map;

import com.aqua.ludum.growth.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
        this.nodeConnectors = new ArrayList<>();
        this.growths = new ArrayList<>();
        this.removals = new ArrayList<>();
        this.rootRemovals = new ArrayList<>();
        this.root = new Node(position, new Node[] { } );
        this.nodes = new ArrayList<>();
        this.nodes.add(this.root);
        
        for (int i = 0; i < Constants.PLANT_START_ARM_COUNT; ++i) {
            for (int j = 0; j < Constants.PLANT_START_ARM_LENGTH; ++j) {
                Node last = j == 0 ? this.root : this.nodes.get(this.nodes.size() - 1);
                Node next = new Node(new Point(
                        position.x + (j + 1) * Constants.PLANT_NODE_DISTANCE * Math.cos((double) i / Constants.PLANT_START_ARM_COUNT * 2.0 * Math.PI + Constants.PLANT_START_ARM_SPIRAL * j),
                        position.y + (j + 1) * Constants.PLANT_NODE_DISTANCE * Math.sin((double) i / Constants.PLANT_START_ARM_COUNT * 2.0 * Math.PI + Constants.PLANT_START_ARM_SPIRAL * j)),
                        new Node[] { last });
                insertNeighbour(last, next);
                this.nodes.add(next);
            }
        }
    }
    
    public final void render(SpriteBatch batch) {
    	for(NodeConnector nodeConnector : nodeConnectors){
    		nodeConnector.render(batch);
    	}
    	for(Node node : this.nodes){
    		double x = node.position.x, y = node.position.y;
    		batch.draw(PLANT_TEXTURE, (float)x, (float)y);
    	}
    	double rootx = this.root.position.x, rooty = this.root.position.y;
    	batch.draw(PLANT_ROOT_TEXTURE, (float)rootx, (float)rooty);
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
            Node next = null;
            if (atTarget) {
                if (growth.isAttack) {
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
            }
            else {
                next = new Node(nextPosition, new Node[] { node });
                this.nodes.add(next);
            }
            if (next != null) {
                insertNeighbour(node, next);
            }
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
    
    private void addConnection(Node a, Node b) {
    	nodeConnectors.add(new NodeConnector(a, b));
    	
        Node[] neighboursA = a.neighbours;
        Node[] neighboursB = b.neighbours;
        a.neighbours = new Node[a.neighbours.length + 1];
        b.neighbours = new Node[b.neighbours.length + 1];
        System.arraycopy(neighboursA, 0, a.neighbours, 1, neighboursA.length);
        System.arraycopy(neighboursB, 0, b.neighbours, 1, neighboursB.length);
        a.neighbours[0] = b;
        b.neighbours[0] = a;
    }
    
    private void removeConnection(Node a, Node b) {
        int bIndex = -1;
        for (int i = 0; i < a.neighbours.length; ++i) {
            if (a.neighbours[i] == b) {
                bIndex = i;
                break;
            }
        }
        int aIndex = -1;
        for (int i = 0; i < b.neighbours.length; ++i) {
            if (b.neighbours[i] == a) {
                aIndex = i;
                break;
            }
        }
        if (aIndex == -1) {
            return;
        }
        
        ListIterator<NodeConnector> it = nodeConnectors.listIterator();
        while(it.hasNext()){
        	NodeConnector nodeConnector = it.next();
        	if(nodeConnector.node1 == a || nodeConnector.node1 == b){
        		if(nodeConnector.node2 == a || nodeConnector.node2 == b){
            		it.remove();
            	}
        	}
        }
        
        Node[] neighboursA = a.neighbours;
        Node[] neighboursB = b.neighbours;
        a.neighbours = new Node[a.neighbours.length - 1];
        b.neighbours = new Node[b.neighbours.length - 1];
        System.arraycopy(neighboursA, 0, a.neighbours, 0, bIndex);
        System.arraycopy(neighboursB, 0, b.neighbours, 0, aIndex);
        System.arraycopy(neighboursA, bIndex + 1, a.neighbours, bIndex, neighboursA.length - bIndex - 1);
        System.arraycopy(neighboursB, aIndex + 1, b.neighbours, aIndex, neighboursB.length - aIndex - 1);
    }
    
    private void addNode(Node a) {
        this.nodes.add(a);
    }
    
    private void removeNode(Node a) {
        for (Node neighbour : a.neighbours) {
            removeConnection(a, neighbour);
        }
        this.nodes.remove(a);
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
    
    private static boolean areConnected(Node a, Node b) {
        Set<Node> visited = new HashSet<>();
        Set<Node> toVisit = new HashSet<>();
        toVisit.add(a);
        while (!toVisit.isEmpty()) {
            Set<Node> nextToVisit = new HashSet<>();
            for (Node next : toVisit) {
                if (next == b) {
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
    
    private boolean connectedToRoot(Node node) {
        return areConnected(node, this.root);
    }
    
    protected final void growTowards(Node from, Node target) {
        this.growths.add(new Growth(from, target));
    }
    
    protected final void destroy(List<Node> nodes) {
        this.removals.add(new Removal(nodes));
    }
    
    protected final List<Node> getNodes() {
        return this.nodes;
    }
    
    protected final Node getRoot() {
        return this.root;
    }
    
    public abstract void control(float delta);
    
    private final Terrain terrain;
    private final Node root;
    private final List<Node> nodes;
    private final List<Growth> growths;
    private final List<Removal> removals;
    private final List<NotConnectedToRootRemoval> rootRemovals;
    private final List<NodeConnector> nodeConnectors;
    public static final Texture PLANT_TEXTURE = new Texture(Gdx.files.internal("../LDGrowth/data/plant.png")),
    		PLANT_ROOT_TEXTURE = new Texture(Gdx.files.internal("../LDGrowth/data/plant_root.png"));
    
    protected class Node {
        
        public Node(Point position, Node[] neighbours) {
            this.nutrients = 0.0;
            this.light = 0.0;
            this.water = 0.0;
            this.position = position;
            this.neighbours = neighbours;
            this.size = 0.0;
        }
        
        public void update(float delta) {
            for (Light light : terrain.getLights()) {
                if (light.contains(this.position)) {
                    this.light += light.getAmount() * Constants.PLANT_LIGHT_PER_SIZE * this.size;
                }
            }
            for (Nutrients nutrients : terrain.getNutrients()) {
                if (nutrients.contains(this.position)) {
                    this.nutrients += nutrients.getAmount() * Constants.PLANT_NUTRIENTS_PER_SIZE * this.size;
                }
            }
            for (Water water : terrain.getWaters()) {
                if (water.contains(this.position)) {
                    this.water += water.getAmount() * Constants.PLANT_WATER_PER_SIZE * this.size;
                }
            }
            this.light += Constants.PLANT_MIN_LIGHT * Constants.PLANT_LIGHT_PER_SIZE * this.size;
            this.water += Constants.PLANT_MIN_WATER * Constants.PLANT_WATER_PER_SIZE * this.size;
            this.nutrients += Constants.PLANT_MIN_NUTRIENTS * Constants.PLANT_NUTRIENTS_PER_SIZE * this.size;
            double lightGift = this.light * Constants.PLANT_TRANSFER_LIGHT;
            double waterGift = this.water * Constants.PLANT_TRANSFER_WATER;
            double nutrientsGift = this.nutrients * Constants.PLANT_TRANSFER_NUTRIENTS;
            this.light -= lightGift;
            this.water -= waterGift;
            this.nutrients -= nutrientsGift;
            for (Node neighbour : this.neighbours) {
                neighbour.light += lightGift / this.neighbours.length;
                neighbour.water += waterGift / this.neighbours.length;
                neighbour.nutrients += nutrientsGift / this.neighbours.length;
            }
            this.light -= this.size * Constants.PLANT_COST_LIGHT_PER_SIZE;
            this.water -= this.size * Constants.PLANT_COST_WATER_PER_SIZE;
            this.nutrients -= this.size * Constants.PLANT_COST_NUTRIENTS_PER_SIZE;
            
            if (this.light < 0 || this.water < 0 || this.nutrients < 0) {
                List<Node> list = new ArrayList<>(1);
                list.add(this);
                removals.add(new Removal(list));
            }
        }
        
        public double nutrients;
        public double light;
        public double water;
        public double size;
        
        public Point position;
        public Node[] neighbours;
        
    }
    
    private static class Growth {
        
        public Growth(Node node, Node target) {
            this.node = node;
            this.target = target;
            this.isAttack = target.neighbours.length != 0 && !areConnected(node, target);
        }
        
        public Node node;
        public Node target;
        public boolean isAttack;
        
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
