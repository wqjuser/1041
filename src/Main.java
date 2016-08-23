
import java.awt.geom.GeneralPath;
import java.io.IOException;
import java.util.*;
import java.util.List;

class Point implements Comparable<Point> {
    int x, y;

    public int compareTo(Point p) {
        if (this.x == p.x) {
            return this.y - p.y;
        } else {
            return this.x - p.x;
        }
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

}

public class Main {
    static Point[] hull;

    public static long cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    public static Point[] convex_hull(Point[] P) {

        if (P.length > 1) {
            int n = P.length, k = 0;
            Point[] H = new Point[2 * n];
            Arrays.sort(P);
            // Build lower hull
            for (int i = 0; i < n; ++i) {
                while (k >= 2 && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
                H[k++] = P[i];
            }

            // Build upper hull
            for (int i = n - 2, t = k + 1; i >= 0; i--) {
                while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
                H[k++] = P[i];
            }
            if (k > 1) {
                H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices
                // after k; remove k - 1
                // which is a duplicate
            }
            return H;
        } else if (P.length <= 1) {
            return P;
        } else {
            return null;
        }

    }

    public static void main(String[] args) throws IOException {
        long time1 = System.currentTimeMillis();
        Scanner s = new Scanner(System.in);
        List<Integer> S = new ArrayList<>();
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        List<Point> P = new ArrayList<>();
        int numof1 = 0;
        int num = s.nextInt();
        Point[] p1 = new Point[3];

        for (int i = 0; i < 3; i++) {
            int status = s.nextInt();
            int x = s.nextInt();
            int y = s.nextInt();
            p1[i] = new Point();
            p1[i].x = x;
            p1[i].y = y;
        }
        hull = convex_hull(p1).clone();
        for (int i = 0; i < num - 3; i++) {
            int status = s.nextInt();
            S.add(status);
            int x = s.nextInt();
            int y = s.nextInt();
            X.add(x);
            Y.add(y);
        }
        for (int i = 0; i < S.size(); i++) {
            if (S.get(i) == 1) {
                numof1++;
                p1 = Arrays.copyOf(p1, p1.length + 1);
                p1[2 + numof1] = new Point();
                p1[2 + numof1].x = X.get(i);
                p1[2 + numof1].y = Y.get(i);
                hull = convex_hull(p1).clone();
            }
            if (S.get(i) == 2) {
                Point p = new Point();
                p.x = X.get(i);
                p.y = Y.get(i);
                int n = hull.length;

                if (checkWithJdkGeneralPath(p, hull) || InPolygon(hull, p)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }

            }
        }
        long time2 = System.currentTimeMillis();
        System.out.print(time2-time1);
    }

    public static boolean checkWithJdkGeneralPath(Point point, Point[] polygon) {
        GeneralPath s = new GeneralPath();
        Point first = polygon[0];
        s.moveTo(first.x, first.y);
        for (Point d : polygon) {
            s.lineTo(d.x, d.y);
        }
        s.lineTo(first.x, first.y);
        s.closePath();
        return s.contains(point.x, point.y);
    }

    static double ESP = 1e-5;

    static double Multiply(Point p1, Point p2, Point p0) {
        return ((p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y));
    }

    static boolean on_segment(Point p1, Point p2, Point p) {
        double max = p1.x > p2.x ? p1.x : p2.x;
        double min = p1.x < p2.x ? p1.x : p2.x;
        double max1 = p1.y > p2.y ? p1.y : p2.y;
        double min1 = p1.y < p2.y ? p1.y : p2.y;
        if (p.x >= min && p.x <= max && p.y >= min1 && p.y <= max1)
            return true;
        else
            return false;
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

    public static boolean InPolygon(Point[] polygon, Point point) {
        int n = polygon.length;
        LineSegment line = new LineSegment();
        LineSegment side = new LineSegment();
        line.pt1 = point;
        for (int i = 0; i < n; i++) {
            // 得到多边形的一条边
            side.pt1 = polygon[i];
            side.pt2 = polygon[(i + 1) % n];
            if (IsOnline(point, side) && on_segment(side.pt1, side.pt2, point)) {
                return true;
            }

        }
        return false;
    }

}