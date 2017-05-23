package com.example.android.shortcutsprototype;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

    private static final String LOG_TAG = "Dijkstra";

    //takes in names of the 2 locations and returns the time and path in 1 string
    public static String runQuery(String sourceName, String destName) {

        //hardcoded
        String graph = "TLAB TLAB\n" +
                "EA EA\n" +
                "E1 E1\n" +
                "E1A E1A\n" +
                "E2 E2\n" +
                "E2A E2A\n" +
                "E3 E3\n" +
                "E3A E3A\n" +
                "E4 E4\n" +
                "E4A E4A\n" +
                "E5 E5\n" +
                "LT6 LT6 \n" +
                "# FROM|TO|Timetaken|AmountStairs|Sheltered\n" +
                "TLAB EA 1.0 5 0\n" +
                "EA E1A 1.0 5 0\n" +
                "EA E1 1.0 5 0\n" +
                "EA E3 1.0 5 0\n" +
                "EA E3A 1.0 5 0\n" +
                "E1 E1A 1.0 5 0\n" +
                "E1 E2 1.0 5 0\n" +
                "E1A EA 1.0 5 0\n" +
                "E1A E1 1.0 5 0\n" +
                "E1A E2 1.0 5 0\n" +
                "E2 EA 1.0 5 0\n" +
                "E2 E1 1.0 5 0\n" +
                "E2 E1A 1.0 5 0\n" +
                "E2 E3 1.0 5 0\n" +
                "E2A E2 1.0 5 0\n" +
                "E2A E4 1.0 5 0\n" +
                "E3 TLAB 1.0 5 0\n" +
                "E3 E1 1.0 5 0\n" +
                "E3 E2 1.0 5 0\n" +
                "E3 E4 1.0 5 0\n" +
                "E3 LT6 1.0 5 0\n" +
                "E3A EA 1.0 5 0\n" +
                "E4 E3 1.0 5 0\n" +
                "E4 E4A 1.0 5 0\n" +
                "E4 E5 1.0 5 0\n" +
                "E4A E4 1.0 5 0\n" +
                "E5 E4 1.0 5 0\n" +
                "LT6 E4 1.0 5 0";


        //reading in from the text file and creating the map
        Map<String,Vertex> vertexMap1 = new HashMap<String, Vertex>();

        BufferedReader buff = null;
        try {
            buff = new BufferedReader(new StringReader(graph));
            //buff = new BufferedReader(new FileReader("graph.txt"));   //TODO: solve how to read from this txt file
            String line;
            boolean inVertex = true;

            while ((line = buff.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    inVertex = false;
                    continue;
                }
                if (inVertex) {
                    //store the vertices
                    int indexOfSpace = line.indexOf(' ');
                    String vertexId = line.substring(0, indexOfSpace);
                    String vertexName = line.substring(indexOfSpace + 1);
                    Vertex v = new Vertex(vertexName);
                    vertexMap1.put(vertexId,v);
                    Log.v(LOG_TAG, vertexId + " " + vertexName);
                } else {
                    //store the edges
                    String[] parts = line.split(" ");
                    String vFrom = parts[0];
                    String vTo = parts[1];
                    double timeTaken = Double.parseDouble(parts[2]);
                    double amountStairs = Double.parseDouble(parts[3]); //max downstairs = 0, no stairs = 5, max upstairs = 10
                    double shelteredRating = Double.parseDouble(parts[4]); //totally unsheltered = 0, run for short distance under rain = 3, fully sheltered = 5
                    Vertex v = vertexMap1.get(vFrom);
                    if (v != null) {
                        v.addEdge(new Edge(vertexMap1.get(vTo), timeTaken, amountStairs, shelteredRating));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "IOException detected while parsing the txt file");
        } finally {
            if (buff != null)
                try {
                    buff.close();
                } catch (IOException ignore) {
                }
        }

        //create 2 copies for other queries
        Map<String, Vertex> vertexMap2 = new HashMap<String, Vertex>(vertexMap1);
        Map<String, Vertex> vertexMap3 = new HashMap<String, Vertex>(vertexMap1);

        Vertex source1 = vertexMap1.get(sourceName);
        Vertex destination1 = vertexMap1.get(destName);

        Vertex source2 = vertexMap2.get(sourceName);
        Vertex destination2 = vertexMap2.get(destName);

        Vertex source3 = vertexMap3.get(sourceName);
        Vertex destination3 = vertexMap3.get(destName);

        String finalString = "";    //final string to be returned

        if (source1 != null && destination1 != null) {
            //fastest path query
            computePaths(source1, 1);
            List<Vertex> shortestPath1 = getShortestPathTo(destination1);
            finalString += destination1.getWeight() + "min_Path: " + printPath(shortestPath1) + "_";
            //least stairs query
            computePaths(source2, 2);
            List<Vertex> shortestPath2 = getShortestPathTo(destination2);
            finalString += destination2.getWeight() + "min_Path: " + printPath(shortestPath2) + "_";    //TODO: solve this problem
            //most sheltered
            computePaths(source3, 3);
            List<Vertex> shortestPath3 = getShortestPathTo(destination3);
            finalString += destination3.getWeight() + "min_Path: " + printPath(shortestPath3);
        }else
            finalString = "The location you entered cannot be found";

        return finalString;
    }

    //takes in the entry vertex and ends up with a priority queue with fastest path to every vertex
    public static void computePaths(Vertex origin, int queryNumber) {

        if (origin != null) {       //handle exceptions
            PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
            origin.setWeight(0.0);//double
            vertexQueue.add(origin);

            //while the queue is not empty, poll from queue and
            while (!vertexQueue.isEmpty()) {
                Vertex u = vertexQueue.poll();

                //for every vertex linked to u
                for (Edge e : u.getAdjacencies()) {
                    Vertex v = e.getTarget();
                    double edgeWeight = 0.0;
                    switch (queryNumber){   //settles the different queries
                        case 1: edgeWeight = e.getTimeTaken(); break;
                        case 2: edgeWeight = e.getAmountStairs(); break;
                        case 3: edgeWeight = e.getShelterRating(); break;
                    }
                    double labelWeight = u.getWeight() + edgeWeight;

                    //if vertex is unvisited, change the label of v to the time taken from the start
                    //if visited, label will be updated with the shorter time taken
                    if (labelWeight < v.getWeight()) {
                        vertexQueue.remove(v);
                        v.setWeight(labelWeight); //updates the shorter time
                        v.setPrevious(u);   //updates the shorter path
                        vertexQueue.add(v);
                    }
                }
            }
        }else{
            Log.e(LOG_TAG, "NullPointerException caught: null input received");
        }
    }

    //takes in the end vertex and returns the shortest path from start to end
    public static List<Vertex> getShortestPathTo(Vertex target) {

        List<Vertex> path = new ArrayList<Vertex>();
        //from the target reverse back to the source
        for (Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex);

        //reverse the order of the reverse path
        Collections.reverse(path);
        return path;
    }

    //formats and returns a string of the path
    public static String printPath(List<Vertex> path) {
        String formattedPath = "";
        for (int i = 0; i < path.size(); i++) {
            formattedPath += path.get(i).getName();
            if (i != path.size() - 1)
                formattedPath += " -> ";
        }
        return formattedPath;
    }


}