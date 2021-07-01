import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String message = """
                Press 'S' in order to place starting point first.
                Press 'D' in order to place destination points.
                You can place up-to 10 destination points.
                If you don't want to fill up every destination slot,
                you can press 'X' to calculate early.
                Program will automatically start to calculate -
                when all 10 slots are placed.
                """;
        JOptionPane.showMessageDialog(new JFrame(), message, "Travelling Salesman", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<Place> places = new ArrayList<>();
        drawCanvas();
        waitInput(places);
        int[] k = new int[1];
        k[0] = -1;
        int[] allRoutes = new int[places.size()];
        int[][] possibleRoutes = new int[factorial(places.size() - 1)][places.size() + 1];
        int[] finalRoute = new int[places.size() + 1];
        for (int i = 0; i < places.size(); i++) {
            allRoutes[i] = places.get(i).order;
        }
        generatePermutations(places.size(), allRoutes, possibleRoutes, k);
        findTSP(places, possibleRoutes, finalRoute);
        for (Place p : places) {
            p.draw();
            p.drawOrder();
        }
    }

    private static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    private static void generatePermutations(int n, int[] allRoutes, int[][] possibleRoutes, int[] k) {
        if (n == 1) {
            for (int i = 0; i < allRoutes.length; i++) {
                //System.out.print(allRoutes[i]);
                if (i == 0 && allRoutes[0] == 0) {
                    k[0]++;
                }
                if (k[0] != -1 && allRoutes[0] == 0) {
                    possibleRoutes[k[0]][i] = allRoutes[i];
                    //System.out.print(possibleRoutes[k[0]][i] + "k: " + k[0] + " i: " + i + " " + allRoutes[i] + "\n");
                }
            }
            possibleRoutes[k[0]][allRoutes.length] = 0;
        } else {
            generatePermutations(n - 1, allRoutes, possibleRoutes, k);
            for (int i = 0; i < n - 1; i++) {
                if (n % 2 == 0) {
                    swap(allRoutes, i, n - 1);
                } else {
                    swap(allRoutes, 0, n - 1);
                }
                generatePermutations(n - 1, allRoutes, possibleRoutes, k);
            }
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private static void findTSP(ArrayList<Place> places, int[][] possibleRoutes, int[] finalRoute) {
        double minWeight = Integer.MAX_VALUE;
        double cost = 0;
        for (int[] possibleRoute : possibleRoutes) {
            Place currentPlace = places.get(0);
            for (int j = 0; j < possibleRoute.length - 1; j++) {
                Place nextPlace = places.get(possibleRoute[j + 1]);
                cost += currentPlace.distanceToPlace(nextPlace);
                //System.out.println("Going from " + currentPlace.order + " to " + nextPlace.order);
                currentPlace = nextPlace;
            }
            if (cost < minWeight) {
                minWeight = cost;
                if (places.size() + 1 >= 0) {
                    System.arraycopy(possibleRoute, 0, finalRoute, 0, places.size() + 1);
                }
            }
            cost = 0;
        }
        System.out.println("Calculated minimum weight: " + minWeight);
        System.out.print("Calculated final route: ");
        for (int i = 0; i < places.size(); i++) {
            System.out.print(finalRoute[i] + " -> ");
        }
        System.out.print("0");
        for (int o = 0; o < finalRoute.length - 1; o++) {
            Place currentPlace = places.get(finalRoute[o]);
            Place nextPlace = places.get(finalRoute[o + 1]);
            currentPlace.road(nextPlace);
        }
        StdDraw.text(300, 20, "Distance: " + minWeight);
    }

    private static void waitInput(ArrayList<Place> places) {
        boolean startPlaced = false;
        int orderNum = 0;
        int dNum = 0;
        while (true) {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledSquare(20, 20, 10);
            double mX = StdDraw.mouseX();
            double mY = StdDraw.mouseY();
            if (StdDraw.isKeyPressed(68) && startPlaced && dNum < 10) {
                System.out.println("DEBUG: PLACED DESTINATION");
                // PLACE DESTINATION - CHECKING FOR THE KEY 'D'
                Place dest = new Place(2, mX, mY);
                dest.draw();
                places.add(dest);
                dest.order = orderNum;
                orderNum++;
                dNum++;
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
            } else if (dNum == 10) {
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
