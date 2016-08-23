package com.wqj;


import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by MR.WEN on 2016/8/7.
 */
public class test1041_1 {
    static int n;

    public static void main(String[] args) {
        List<Point2D.Double> polygon = new ArrayList<>();
        List<Point> polygon1 = new ArrayList<>();
        List<Point2D.Double> checkpointlb = new ArrayList<>();
        List<Point> checkpointlb1 = new ArrayList<>();
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        List<Integer> N = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int numof2 = 0;
        for (int i = 0; i < a; i++) {
            int status = s.nextInt();
            int x = s.nextInt();
            int y = s.nextInt();
            N.add(status);
            X.add(x);
            Y.add(y);
        }
        for (int j = 0; j < N.size(); j++) {
            if (N.get(j) == 1) {
                polygon1.add(new Point(X.get(j), Y.get(j)));
                polygon.add(new Point2D.Double(X.get(j), Y.get(j)));
            }
            if (N.get(j) == 2) {
                numof2++;
                checkpointlb.add(new Point2D.Double(X.get(j), Y.get(j)));
                checkpointlb1.add(new Point(X.get(j), Y.get(j)));
            }
        }
        for (int i = 0; i < numof2; i++) {
            Point2D.Double checkpoint = checkpointlb.get(i);
            Point checkpoint1 = checkpointlb1.get(i);
            if (checkWithJdkGeneralPath(checkpoint, polygon)||InPolygon(polygon1,checkpoint1)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    public static boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {
        GeneralPath s = new GeneralPath();
        Point2D.Double first = polygon.get(0);
        s.moveTo(first.x, first.y);
        for (Point2D.Double d : polygon) {
            s.lineTo(d.x, d.y);
        }
        s.lineTo(first.x, first.y);
        s.closePath();
        return s.contains(point);
    }

    static double ESP = 1e-5;

    static double Multiply(Point p1, Point p2, Point p0) {
        return ((p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y));
    }

    // 判断线段是否包含点point
    private static boolean IsOnline(Point point, LineSegment line) {

        return ((Math.abs(Multiply(line.pt1, line.pt2, point)) < ESP) &&

                ((point.x - line.pt1.x) * (point.x - line.pt2.x) <= 0) &&

                ((point.y - line.pt1.y) * (point.y - line.pt2.y) <= 0));
    }

    static class LineSegment {
        public Point pt1;
        public Point pt2;

        public LineSegment() {
            this.pt1 = new Point();
            this.pt2 = new Point();
        }
    }

    static class Point {
        public double x;
        public double y;

        public Point() {
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static boolean InPolygon(List<Point> polygon1, Point point) {
        n = polygon1.size();
        LineSegment line = new LineSegment();
        LineSegment side = new LineSegment();
        line.pt1 = point;
        for (int i = 0; i < n; i++) {
            // 得到多边形的一条边
            side.pt1 = polygon1.get(i);
            side.pt2 = polygon1.get((i + 1) % n);
            if (IsOnline(point, side)) {
                return true;
            }
        }
        return false;
    }
}

