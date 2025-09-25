public class Point implements Comparable<Point> {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point other) {
        if (this.x != other.x) {
            return Double.compare(this.x, other.x);
        }
        return Double.compare(this.y, other.y);
    }
}