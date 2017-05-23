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

    public static String testingQuery(String sourceName, String destName) {
        String graph = "v0 Harrisburg\n" +
                "v1 Baltimore\n" +
                "v2 Washington\n" +
                "v3 Philadelphia\n" +
                "v4 Binghamton\n" +
                "v5 Allentown\n" +
                "v6 New York\n" +
                "#\n" +
                "v0 v1 79.83\n" +
                "v0 v5 81.15\n" +
                "v1 v0 79.75\n" +
                "v1 v2 39.42\n" +
                "v1 v3 103.00\n" +
                "v2 v1 38.65\n" +
                "v3 v1 102.53\n" +
                "v3 v5 61.44\n" +
                "v3 v6 96.79\n" +
                "v4 v5 133.04\n" +
                "v5 v0 81.77\n" +
                "v5 v3 62.05\n" +
                "v5 v4 134.47\n" +
                "v5 v6 91.63\n" +
                "v6 v3 97.24\n" +
                "v6 v5 87.94";
        Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new StringReader(graph));
            String line;
            boolean inVertex = true;
            while ((line = in.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    inVertex = false;
                    continue;
                } else if (inVertex) {
                    //store the vertices
                    int indexOfSpace = line.indexOf(' ');
                    String vertexId = line.substring(0, indexOfSpace);
                    String vertexName = line.substring(indexOfSpace + 1);
                    Vertex v = new Vertex(vertexName);
                    vertexMap.put(vertexId, v);
                } else {
                    //store the edges
                    String[] parts = line.split(" ");
                    String vFrom = parts[0];
                    Log.e(LOG_TAG, vFrom );
                    String vTo = parts[1];
                    Log.e(LOG_TAG, vTo );
                    double timeTaken = Double.parseDouble(parts[2]);
                    Log.e(LOG_TAG, timeTaken + "" );
                    Vertex v = vertexMap.get(vFrom);
                    if (v != null) {
                        v.addEdge(new Edge(vertexMap.get(vTo), timeTaken));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException ignore) {
                }
        }

        return "asdf _ asdfa";
    }

    //takes in names of the 2 locations and returns the time and path in 1 string
    public static String shortestPathQuery(String sourceName, String destName) {

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
                "TLAB EA 1.0\n" +
                "EA E1A 1.0\n" +
                "EA E1 1.0\n" +
                "EA E3 1.0\n" +
                "EA E3A 1.0\n" +
                "E1 E1A 1.0\n" +
                "E1 E2 1.0\n" +
                "E1A EA 1.0\n" +
                "E1A E1 1.0\n" +
                "E1A E2 1.0\n" +
                "E2 EA 1.0\n" +
                "E2 E1 1.0\n" +
                "E2 E1A 1.0\n" +
                "E2 E3 1.0\n" +
                "E2A E2 1.0\n" +
                "E2A E4 1.0\n" +
                "E3 TLAB 1.0\n" +
                "E3 E1 1.0\n" +
                "E3 E2 1.0\n" +
                "E3 E4 1.0\n" +
                "E3 LT6 1.0\n" +
                "E3A EA 1.0\n" +
                "E4 E3 1.0\n" +
                "E4 E4A 1.0\n" +
                "E4 E5 1.0\n" +
                "E4A E4 1.0\n" +
                "E5 E4 1.0\n" +
                "LT6 E4 1.0";


        //reading in from the text file and creating the map
        Map<String,Vertex> vertexMap = new HashMap<String, Vertex>();

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
                    vertexMap.put(vertexId,v);
                    Log.v(LOG_TAG, vertexId + " " + vertexName);
                } else {
                    //store the edges
                    String[] parts = line.split(" ");
                    String vFrom = parts[0];
                    String vTo = parts[1];
                    double timeTaken = Double.parseDouble(parts[2]);
//                    //TODO: add more weights
//                    //rating from 1 to 10, no stairs = 0, max upstairs = 10, max downstairs = 0
//                    double amountStairs = Double.parseDouble(parts[3])
//                    //sheltered should be a boolean but may not always be able to provide a fully sheltered path so just give most sheltered
//                    //no shelter = 0, max shelter = 10, max shelter = 0
//                    double shelteredRating = Double.parseDouble(parts[4])
                    Vertex v = vertexMap.get(vFrom);
                    if (v != null) {
                        v.addEdge(new Edge(vertexMap.get(vTo), timeTaken));
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

        //create 2 copies for2 other queries
        //TODO:create 2 copies for other queries
        //Map<String, Vertex> map2 = vertexMap;
        //Map<String, Vertex> map3 = vertexMap;

        //running Dijkstra's algo here
        Vertex source = vertexMap.get(sourceName);
        Vertex destination = vertexMap.get(destName);

        if (source != null && destination != null) {
            computePaths(source);
            List<Vertex> shortestPath = getShortestPathTo(destination);

            String time = destination.getWeight() + "min _";
            String path = printPath(shortestPath);
            return time + "Path: " + path; //combined into 1 string, split later
        }else
            return "NA _ Location not found";
    }

    //takes in the entry vertex and ends up with a priority queue with shortest path to every vertex
    public static void computePaths(Vertex origin) {

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
                    double edgeTimeTaken = e.getTimeTaken();
                    double timeToV = u.getWeight() + edgeTimeTaken;

                    //if vertex is unvisited, change the label of v to the time taken from the start
                    //if visited, label will be updated with the shorter time taken
                    if (timeToV < v.getWeight()) {
                        vertexQueue.remove(v);
                        v.setWeight(timeToV); //updates the shorter time
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
                formattedPath += " => ";
        }
        return formattedPath;
    }


}