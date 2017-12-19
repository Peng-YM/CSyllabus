package com.peng1m.springboot.model;

import java.util.*;

public class CourseTree {
    // graph
    //still need because the graph may not connect
    private List<Integer> nodes;

    private LinkedHashMap<Integer, Integer> edges;

    public CourseTree() {
    }

    public CourseTree(List<Integer> nodes, LinkedHashMap<Integer, Integer> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public CourseTree(List<CourseEdge> courseEdges, List<Integer> courses) {
        nodes = new LinkedList<>();
        edges = new LinkedHashMap<>();

        for (CourseEdge courseEdge : courseEdges) {
            addEdge(courseEdge.getSourceId(), courseEdge.getTargetId());
        }
        nodes = courses;
    }


    public void addEdge(int source, int target) {
        this.edges.put(source, target);
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

    public LinkedHashMap<Integer, Integer> getEdges() {
        return edges;
    }
}
