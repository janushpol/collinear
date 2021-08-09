/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null || Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException("argument is null");
        }


        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("same points");
                }
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (this.isCollinear(pointsClone[i], pointsClone[k], pointsClone[j],
                                             pointsClone[m])) {
                            this.lineSegments.add(new LineSegment(pointsClone[i], pointsClone[m]));
                        }
                    }
                }
            }
        }
    }

    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        if (p.slopeTo(q) == p.slopeTo(r)) {
            return p.slopeTo(q) == p.slopeTo(s);
        }
        return false;
    }

    public int numberOfSegments() {
        return this.lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] lineSegment = new LineSegment[this.numberOfSegments()];
        lineSegment = lineSegments.toArray(lineSegment);
        return lineSegment;
    }

    public static void main(String[] args) {

        // read the n points from a file

        int n = 8;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
