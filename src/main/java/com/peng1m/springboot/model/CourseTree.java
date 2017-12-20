package com.peng1m.springboot.model;

import java.util.*;

public class CourseTree {
    // graph
    //still need because the graph may not connect
    private List<Integer> nodes;

    //private LinkedHashMap<Integer, Integer> edges;

    private LinkedList<Map<String, Integer>> edges;

    public CourseTree() {
    }

    public CourseTree(List<Integer> nodes, LinkedList<Map<String, Integer>> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public CourseTree(List<CourseEdge> courseEdges, List<Integer> courses) {
        nodes = new LinkedList<>();
        edges = new LinkedList<>();

        for (CourseEdge courseEdge : courseEdges) {
            addEdge(courseEdge.getSourceId(), courseEdge.getTargetId());
        }
        nodes = courses;
    }


    public void addEdge(int source, int target) {

        HashMap<String, Integer> edge = new HashMap<>();
        edge.put("source", source);
        edge.put("target", target);
        this.edges.add(edge);
    }

    public void addNode(int id) {
        this.nodes.add(id);
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(List<Integer> nodes) {
        this.nodes = nodes;
    }

    public LinkedList<Map<String, Integer>> getEdges() {
        return edges;
    }
}
