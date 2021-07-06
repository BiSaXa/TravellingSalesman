import javax.swing.*;
import java.util.ArrayList;

public class TravellingSalesman {
    public static void main(String[] args) {
        // DIALOGUE BOX AT THE START IN ORDER TO SHOW INFO
        String message = """
                Press 'S' in order to place starting point first.
                Press 'D' in order to place destination points.
                Press 'X' in order to start calculating.
                """;
        JOptionPane.showMessageDialog(new JFrame(), message, "TravellingSalesman", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<Place> places = new ArrayList<>();
        drawCanvas();
        waitInput(places);
        // NECESSARY ARRAYS TO STORE OUR CALCULATIONS
        int[] allRoutes = new int[places.size()];
        int[] possibleRoutes = new int[places.size() + 1];
        int[] finalRoute = new int[places.size() + 1];
        double[] minWeight = new double[1];
        double[] cost = new double[1];
        cost[0] = 0;
        minWeight[0] = Integer.MAX_VALUE;
        // GIVING THE INITIAL ORDER IN ORDER TO PERMUTE LATER ON
        for (int i = 0; i < places.size(); i++) {
            allRoutes[i] = places.get(i).order;
        }
        // WHERE CALCULATION STARTS - ALSO RECORDING THE ELAPSED TIME
        long startTime = System.nanoTime();
        generatePermutations(finalRoute, minWeight, cost, places, places.size(), allRoutes, possibleRoutes);
        generateRoads(places, minWeight, finalRoute);
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Time elapsed: " + elapsedTime / 1000000 + "ms");
    }

    private static void generateRoads(ArrayList<Place> places, double[] minWeight, int[] finalRoute) {
        // OUTPUT CALCULATED DATA INTO CONSOLE
        System.out.println("Calculated minimum weight: " + minWeight[0]);
        System.out.print("Calculated final route: ");
        for (int i = 0; i < places.size(); i++) {
            System.out.print(finalRoute[i] + " -> ");
        }
        System.out.print("0\n");
        // WHERE THE ROADS GET DRAWN
        for (int j = 0; j < finalRoute.length - 1; j++) {
            Place currentPlace = places.get(finalRoute[j]);
            Place nextPlace = places.get(finalRoute[j + 1]);
            currentPlace.road(nextPlace);
        }
        StdDraw.text(300, 20, "Distance: " + minWeight[0]);
        // REDRAW ALL DOTS AND THEIR ORDER NUMBER
        for (Place p : places) {
            p.draw();
            p.drawOrder();
        }
    }

    private static void generatePermutations(int[] finalRoute, double[] minWeight, double[] cost, ArrayList<Place> places, int n, int[] allRoutes, int[] possibleRoutes) {
        if (n == 1) {
            for (int i = 0; i < allRoutes.length; i++) {
                if (allRoutes[0] == 0) {
                    possibleRoutes[i] = allRoutes[i];
                }
            }
            possibleRoutes[allRoutes.length] = 0;
            Place currentPlace = places.get(0);
            for (int j = 0; j < possibleRoutes.length - 1; j++) {
                Place nextPlace = places.get(possibleRoutes[j + 1]);
                cost[0] += currentPlace.distanceToPlace(nextPlace);
                currentPlace = nextPlace;
            }
            if (cost[0] < minWeight[0]) {
                minWeight[0] = cost[0];
                for (int k = 0; k < possibleRoutes.length; k++) {
                    finalRoute[k] = possibleRoutes[k];
                }
            }
            cost[0] = 0;
        } else {
            generatePermutations(finalRoute, minWeight, cost, places, n - 1, allRoutes, possibleRoutes);
            for (int i = 0; i < n - 1; i++) {
                if (n % 2 == 0) {
                    swap(allRoutes, i, n - 1);
                } else {
                    swap(allRoutes, 0, n - 1);
                }
                generatePermutations(finalRoute, minWeight, cost, places, n - 1, allRoutes, possibleRoutes);
            }
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private static void waitInput(ArrayList<Place> places) {
        boolean startPlaced = false;
        int orderNum = 0;
        while (true) {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledSquare(20, 20, 10);
            double mX = StdDraw.mouseX();
            double mY = StdDraw.mouseY();
            if (StdDraw.isKeyPressed(68) && startPlaced) {
                System.out.println("DEBUG: PLACED DESTINATION");
                // PLACE DESTINATION - CHECKING FOR THE KEY 'D'
                Place dest = new Place(2, mX, mY);
                dest.draw();
                places.add(dest);
                dest.order = orderNum;
                orderNum++;
                // INDICATOR FOR PAUSES
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.filledSquare(20, 20, 10);
                StdDraw.pause(500);
            } else if (StdDraw.isKeyPressed(83) && !startPlaced) {
                System.out.println("DEBUG: PLACED STARTING POINT");
                // STARTING PLACE - CHECKING FOR THE KEY 'S'
                Place start = new Place(1, mX, mY);
                StdDraw.clear();
                start.draw();
                places.add(start);
                start.order = orderNum;
                orderNum++;
                startPlaced = true;
                // INDICATOR FOR PAUSES
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.filledSquare(20, 20, 10);
                StdDraw.pause(500);
            } else if (StdDraw.isKeyPressed(88)) {
                System.out.println("DEBUG: PLACEMENTS ARE OVER");
                System.out.println("DEBUG: " + places.size() + " HAVE BEEN FOUND");
                // ENDING SELECTION - CHECKING FOR THE KEY 'X'
                // INDICATOR FOR PAUSES
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledSquare(20, 20, 10);
                break;
            }
        }
    }

    private static void drawCanvas() {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setScale(0, 600);
        StdDraw.setPenRadius(0.003);
        StdDraw.text(300, 20, "Press 'S' to place starting point");
    }

}
