package com.wqj;

/**
 * Created by MR.WEN on 2016/8/22.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TreeSet;

public class test1041_4 {
    final static double EPS = 1e-10;

    public static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return x + " " + y;
        }

        public int compareTo(Point p) {
            return x != p.x ? Integer.compare(x, p.x) : Integer.compare(y, p.y);
        }
    }

    static long cross(Point origin, Point a, Point b) {
        return (long) (a.x - origin.x) * (b.y - origin.y) -
                (long) (a.y - origin.y) * (b.x - origin.x);
    }

    static void removeLeft(TreeSet<Point> hull, Point p, int sign) {
        Point a = hull.floor(p);
        if (a == null) return;
        if (a.x == p.x) {
            hull.remove(a);
            a = hull.floor(p);
        }
        if(a == null) return;
        Point b = hull.lower(a);
        while (b != null && cross(p, a, b) * sign <= 0) {
            hull.remove(a);
            a = b;
            b = hull.lower(a);
        }
    }

    static void removeRight(TreeSet<Point> hull, Point p, int sign) {
        Point a = hull.ceiling(p);
        if (a == null) return;
        if (a.x == p.x) {
            hull.remove(a);
            a = hull.ceiling(p);
        }
        if(a == null) return;
        Point b = hull.higher(a);
        while (b != null && cross(p, a, b) * sign >= 0) {
            hull.remove(a);
            a = b;
            b = hull.higher(a);
        }
    }

    public static class PointContainer {
        Point e;
    }

    private static double slope(Point a, Point b) {
        return (a.x == b.x) ? 1e99 : (double) (a.y - b.y) / (a.x - b.x);
    }

    private static int signCrossProduct(Point origin, Point a, Point b) {
        long c = (long) (a.x - origin.x) * (b.y - origin.y) -
                (long) (a.y - origin.y) * (b.x - origin.x);
        return (c < 0) ? -1 :
                (c > 0) ? 1 : 0;
    }

    private static boolean between(Point a, Point b, Point c) {
        if (a.x == c.x) {
            return a.y <= b.y && b.y <= c.y;
        }
        return a.x <= b.x && b.x <= c.x;
    }

    private static void workHull(TreeSet<Point> hull, Point p) {
        while (hull.size() >= 2) {
            Point f = hull.first();
            Point s = hull.higher(f);
            if (slope(p, f) + EPS > slope(p, s)) {
                hull.pollFirst();
            } else {
                break;
            }
        }

        while (hull.size() >= 2) {
            Point s = hull.last();
            Point f = hull.lower(s);
            if (slope(p, f) + EPS > slope(p, s)) {
                hull.pollLast();
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int N = Integer.parseInt(br.readLine());
        TreeSet<Point> upper = new TreeSet<>((a, b) -> a.x - b.x);
        TreeSet<Point> lower = new TreeSet<>((a, b) -> a.x - b.x);
        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            int type = Integer.parseInt(input[0]);
            Point p = new Point(Integer.parseInt(input[1]), Integer.parseInt(input[2]));

            Point a = upper.floor(p);
            Point b = upper.ceiling(p);
            Point c = lower.floor(p);
            Point d = lower.ceiling(p);

            boolean outUpper = (a == null || b == null
                    || a == b && a.y < p.y || cross(a, b, p) > 0);
            boolean outLower = (c == null || d == null
                    || c == d && c.y > p.y || cross(c, d, p) < 0);

            if (type == 1) {
                if (outUpper) {
                    removeLeft(upper, p, 1);
                    removeRight(upper, p, 1);
                    upper.add(p);
                }
                if (outLower) {
                    removeLeft(lower, p, -1);
                    removeRight(lower, p, -1);
                    lower.add(p);
                }
            } else {
                pw.println(outUpper || outLower ? "NO" : "YES");
            }
        }
        pw.close();
    }
}
