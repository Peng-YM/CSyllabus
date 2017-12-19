package com.peng1m.springboot.model;

import java.util.*;

public class CourseTree {
    // graph
    //still need because the graph may not connect
    private List<Integer> nodes;

    //private LinkedHashMap<Integer, Integer> edges;

    private LinkedList<Integer[]> edges;

    public CourseTree() {
    }

    public CourseTree(List<Integer> nodes, LinkedList<Integer[]> edges) {
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
        Integer[] edge = new Integer[2];
        edge[0] = source;
        edge[1] = target;
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

    public LinkedList<Integer[]> getEdges() {
        return edges;
    }
}
