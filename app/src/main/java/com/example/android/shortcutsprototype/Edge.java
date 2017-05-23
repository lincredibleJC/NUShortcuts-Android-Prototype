package com.example.android.shortcutsprototype;

public class Edge {

    private Vertex target;
    private double timeTaken;
    private double amountStairs;
    private double shelterRating;
    private String message;

    //TODO: sheltered(weight or boolean?), and how to insert pictures?

    //constructor

    public Edge(Vertex target, double timeTaken, double amountStairs, double shelterRating) {
        this.target = target;
        this.timeTaken = timeTaken;
        this.amountStairs = amountStairs;
        this.shelterRating = shelterRating;
    }

    //getters and setters
    public Vertex getTarget() {
        return target;
    }

    public void setTarget(Vertex target) {
        this.target = target;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

    public double getAmountStairs() {
        return amountStairs;
    }

    public double getShelterRating() {
        return shelterRating;
    }

    public String getMessage() {
        return message;
    }
}
