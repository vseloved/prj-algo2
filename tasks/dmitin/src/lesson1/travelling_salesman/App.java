package lesson1.travelling_salesman;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by dmitin on 22.09.16.
 */
public class App {
    private static final int POINT_COUNT = 48;
    private static final String FILE_NAME = "src/lesson1/travelling_salesman/capitals.txt";
    private static final int FRAME_SIZE = 1000;
    private static final int SCALE = 13;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        Point[] points = init();
        Point[] traversedPoints = traverse(points);

        for (Point point : traversedPoints) {
            System.out.println(point);
        }

        visualize(traversedPoints);
    }

    private void visualize(final Point[] traversedPoints) {
        JFrame frame = new JFrame();
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 1; i < POINT_COUNT; i++) {
                    Point point1 = traversedPoints[i - 1];
                    Point point2 = traversedPoints[i];
                    g.drawLine((int) (point1.getX() / SCALE), (int) (point1.getY() / SCALE),
                            (int) (point2.getX() / SCALE), (int) (point2.getY() / SCALE));
                }
            }
        };
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private Point[] init() {
        try {
            Point[] points = new Point[POINT_COUNT];
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                int lineSize = words.length;
                points[i++] = new Point(Double.parseDouble(words[lineSize - 2]),
                        Double.parseDouble(words[lineSize - 1]));
            }
            return points;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
