package com.peng1m.springboot.model;

import java.util.List;

public class CourseTree {
    class Edge{
        private int source;
        private int target;

        private Edge(int source, int target){
            this.source = source;
            this.target = target;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }
    }

    // graph
    private List<Integer> nodes;
    private List<Edge> edges;

    public void addEdge(int source, int target){
        this.edges.add(new Edge(source, target));
    }

    public void addNode(int id){
        this.nodes.add(id);
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(List<Integer> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
