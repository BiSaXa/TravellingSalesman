import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Place> places = new ArrayList<>();
        drawCanvas(600, 600);
        waitInput(places);
        boolean[] visited = new boolean[places.size()];
        visited[0] = true;
        // ONLY DO DYNAMIC SEARCH IF THERE ARE LESS THAN 14 POINTS OR IT WILL TAKE A REALLY LONG TIME
        if (places.size() < 14) {
            double distances[][] = new double[places.size()][places.size()];
            distancesArray(places, distances);
            double finalWeight = Integer.MAX_VALUE;
            finalWeight = findTSPDynamic(places, distances, visited, 0, places.size(), 1, 0, finalWeight);
            System.out.println("Dynamic : " + finalWeight);
        } else {
            System.out.println("Dynamic : NOT CALCULATED");
        }
        findTSPNN(places, visited);
        for (Place p : places) {
            p.draw();
            p.drawOrder();
        }
    }

    private static void findTSPNN(ArrayList<Place> places, boolean[] visited) {
        Place currentPlace = places.get(0);
        double totalWeight = 0;
        for (int i = 1; i <= places.size(); i++) {
            double minDist = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < places.size(); j++) {
                if (currentPlace.distanceToPlace(places.get(j)) < minDist && visited[j] == false) {
                    minDist = currentPlace.distanceToPlace(places.get(j));
                    minIndex = j;
                }
            }
            if (minDist < Integer.MAX_VALUE) {
                totalWeight += minDist;
            }
            if (i == places.size()) {
                currentPlace.road(places.get(0));
                totalWeight += currentPlace.distanceToPlace(places.get(0));
            } else {
                visited[minIndex] = true;
                currentPlace.road(places.get(minIndex));
                places.get(minIndex).order = i;
                currentPlace = places.get(minIndex);
            }
        }
        StdDraw.text(300, 20, "Distance : " + totalWeight);
        System.out.println("Nearest Neighbor : " + totalWeight);
    }

    private static double findTSPDynamic(ArrayList<Place> places, double[][] distances, boolean[] visited, int cPos, int size, int count, double cost, double finalWeight) {
        if (count == size && distances[cPos][0] > 0) {
            finalWeight = Math.min(finalWeight, cost + distances[cPos][0]);
            return finalWeight;
        }
        for (int i = 0; i < size; i++) {
            if (visited[i] == false && distances[cPos][i] > 0) {
                //places.get(cPos).road(places.get(i));
                // Mark as visited
                visited[i] = true;
                finalWeight = findTSPDynamic(places, distances, visited, i, size, count + 1, cost + distances[cPos][i], finalWeight);
                // Mark ith node as unvisited
                visited[i] = false;
            }
        }
        return finalWeight;
    }

    private static void distancesArray(ArrayList<Place> places, double[][] distances) {
        for (int i = 0; i < places.size(); i++) {
            for (int j = 0; j < places.size(); j++) {
                distances[i][j] = places.get(i).distanceToPlace(places.get(j));
            }
        }
    }

    private static void waitInput(ArrayList<Place> places) {
        boolean startPlaced = false;
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
                // INDICATOR FOR PAUSES
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.filledSquare(20, 20, 10);
                StdDraw.pause(500);
            } else if (StdDraw.isKeyPressed(83) && !startPlaced) {
                System.out.println("DEBUG: PLACED STARTING POINT");
                // STARTING PLACE - CHECKING FOR THE KEY 'S'
                Place start = new Place(1, mX, mY);
                start.draw();
                places.add(start);
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

    private static void drawCanvas(int x, int y) {
        StdDraw.setCanvasSize(x, y);
        StdDraw.setScale(0, x);
        //StdDraw.enableDoubleBuffering();
    }

}
