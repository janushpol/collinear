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

public class FastCollinearPoints {
    private List<LineSegment> colSegement = new ArrayList<>();
    private List<Double> slopeList = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        checkDuplicate(points);

        Point[] pointsClone = points.clone();


        for (int i = 0; i < points.length; i++) {
            Arrays.sort(pointsClone, points[i].slopeOrder());
            int colinearCount = 1;
            for (int j = 0; j < points.length - 1; j++) {
                if (pointsClone[0].slopeTo(pointsClone[j]) == pointsClone[0].slopeTo(pointsClone[j + 1])) {
                    colinearCount++;
                } else if (colinearCount >= 3 && !slopeList.contains(pointsClone[0].slopeTo(pointsClone[j]))) {

                        Point min = min(pointsClone, j - colinearCount + 1, j + 1, pointsClone[0]);
                        Point max = max(pointsClone, j - colinearCount + 1, j + 1, pointsClone[0]);
                        colSegement.add(new LineSegment(min, max));
                        slopeList.add(pointsClone[0].slopeTo(pointsClone[j]));
                        colinearCount = 1;
                    } else {
                    colinearCount = 1;
                }

                }
            }
        } // finds all line segments containing 4 or more points
    public int numberOfSegments() {
        return colSegement.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] lineSegment = new LineSegment[this.numberOfSegments()];
        lineSegment = colSegement.toArray(lineSegment);
        return lineSegment;

    } // the line segments

    private static Point max(Point[] t, int minIndex, int maxIndex, Point max) {
        Point maximum = max;   // start with the first value
        for (int i = minIndex; i <= maxIndex; i++) {
            if (t[i].compareTo(maximum) > 0) {
                maximum = t[i];   // new maximum
            }
        }
        return maximum;
    }

    private static Point min(Point[] t, int minIndex, int maxIndex, Point min) {
        Point minimum = min;   // start with the first value
        for (int i = minIndex; i <= maxIndex; i++) {
            if (t[i].compareTo(minimum) < 0) {
                minimum = t[i];   // new maximum
            }
        }
        return minimum;
    }

    private void checkDuplicate(Point[] points) {
        if (points.length > 0) {
            Point[] pointsCopy = new Point[points.length];
            System.arraycopy(points, 0, pointsCopy, 0, points.length);
            Arrays.sort(pointsCopy);
            Point currentPoint = pointsCopy[0];
            for (int i = 1; i < pointsCopy.length; i++) {
                if (pointsCopy[i].compareTo(currentPoint) == 0) {
                    throw new IllegalArgumentException("Cannot have duplicate points.");
                } else {
                    currentPoint = pointsCopy[i];
                }
            }
        }
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new NullPointerException("The Point array cannot be null.");
        } else {
            for (Point p : points) {
                if (p == null) {
                    throw new NullPointerException("One of the points is null.");
                }
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
