package com.example.android.shortcutsprototype;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {

    private static final String LOG_TAG = "Vertex";

    private final String name;
    private List<Edge> adjacencies = new ArrayList<>();
    private double weight = Double.POSITIVE_INFINITY;
    private Vertex previous;
/*
    TODO: places should have lat, long coordinates for searching proximity
    private double latitude;
    private  double longitude;
*/

    //basic constructor
    public Vertex(String argName) {
        name = argName;
    }

    //hardcoded constructor
    public Vertex(String name, List<Edge> adjacencies) {
        this.name = name;
        this.adjacencies = adjacencies;
    }

    //full constructor
    public Vertex(String name, List<Edge> adjacencies, double weight, Vertex previous) {
        this.name = name;
        this.adjacencies = adjacencies;
        this.weight = weight;
        this.previous = previous;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public List<Edge> getAdjacencies() {
        return adjacencies;
    }

    public void setAdjacencies(List<Edge> adjacencies) {
        this.adjacencies = adjacencies;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    //compares the min time
    public int compareTo(Vertex other) {
        return Double.compare(weight, other.weight);
    }

    //add edge to Vertex
    public void addEdge(Edge e) {
            adjacencies.add(e);
    }


}