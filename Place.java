public class Place {

    public int placeType;
    public double x;
    public double y;
    public int order;

    public Place() {
    }

    public Place(int placeType, double x, double y) {
        this.placeType = placeType;
        this.x = x;
        this.y = y;
    }

    public void draw() {
        switch (placeType) {
            case 1:
                StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                StdDraw.filledCircle(x, y, 25);
                break;
            case 2:
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.filledCircle(x, y, 15);
                break;
        }
    }

    public void road(Place place) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(this.x, this.y, place.x, place.y);
    }

    public double distanceToPlace(Place place){
        double x = Math.abs(this.x - place.x);
        double y = Math.abs(this.y - place.y);
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void drawOrder() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x, y, Integer.toString(order));
    }
}
