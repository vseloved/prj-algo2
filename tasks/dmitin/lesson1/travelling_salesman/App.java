package lesson1.travelling_salesman;

import java.util.Arrays;

/**
 * Created by dmitin on 22.09.16.
 */
public class App {
    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        Point[] points = new Point[]{
                new Point(0.0, 0.0),
                new Point(1_000.0, 1_000.0),
                new Point(100.0, 100.0),
                new Point(1.0, 1.0),
                new Point(10_000.0, 10_000.0),
                new Point(10.0, 10.0)
        };
        System.out.println(Arrays.toString(traverse(points)));
    }

    private Point[] traverse(Point[] points) {
        Point[] res = new Point[points.length];
        Point point = null;
        for (int i = 0; i < points.length; i++) {
            if (i == 0) {
                point = points[0];
            } else {
                point = findNearestNotVisited(points, point);
            }
            point.setVisited(true);
            res[i] = point;
        }
        return res;
    }

    private Point findNearestNotVisited(Point[] points, Point from) {
        double minDistance = Double.POSITIVE_INFINITY;
        Point nearestPoint = null;
        for (Point point : points) {
            double distance = findDistance(from, point);
            if (distance < minDistance && !point.isVisited()) {
                minDistance = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    private double findDistance(Point point1, Point point2) {
        double dx = point2.getX() - point1.getX();
        double dy = point2.getY() - point1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private class Point {
        private double x;
        private double y;
        private boolean visited;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", visited=" + visited +
                    '}';
        }
    }
}
