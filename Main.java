package com.wqj;
/**
 * Created by MR.WEN on 2016/8/5.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main systemTaskJob = new Main();
        List<Point> polygon = new ArrayList<Point>();
        List<Point> checkpointlb = new ArrayList<Point>();
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
                polygon.add(new Point(X.get(j), Y.get(j)));
            }
            if (N.get(j) == 2) {
                numof2++;
                checkpointlb.add(new Point(X.get(j), Y.get(j)));
            }
        }
//        if (status==2){
//            Point checkpoint = checkpointlb.get(i);
//            systemTaskJob.InPolygon(polygon, checkpoint);
//        }
//        System.out.println(N);
//        System.out.println(X);
//        System.out.println(Y);
//        System.out.println(polygon);
//        System.out.println(checkpointlb);
////        Point checkpoint = new Point(7, 11);
////        polygon.add(point1);
////        polygon.add(point2);
////        polygon.add(point3);
////        polygon.add(point4);
////        polygon.add(point5);
        for (int i = 0; i < numof2; i++) {
            Point checkpoint = checkpointlb.get(i);
            systemTaskJob.InPolygon(polygon, checkpoint);
        }

//        int m = systemTaskJob.InPolygon(polygon, checkpoint);
//        System.out.println("=========" + m);
    }

    double INFINITY = 1e10;
    double ESP = 1e-5;
    int MAX_N = 1000;
    List<Point> Polygon;

    //点集的乘法，叉积
    double Multiply(Point p1, Point p2, Point p0) {
        return ((p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y));
    }

    // 判断线段是否包含点point
    private boolean IsOnline(Point point, LineSegment line) {
        return ((Math.abs(Multiply(line.pt1, line.pt2, point)) < ESP) &&

                ((point.x - line.pt1.x) * (point.x - line.pt2.x) <= 0) &&

                ((point.y - line.pt1.y) * (point.y - line.pt2.y) <= 0));
    }

    // 判断线段相交
    private boolean Intersect(LineSegment L1, LineSegment L2) {
        return ((Math.max(L1.pt1.x, L1.pt2.x) >= Math.min(L2.pt1.x, L2.pt2.x)) &&

                (Math.max(L2.pt1.x, L2.pt2.x) >= Math.min(L1.pt1.x, L1.pt2.x)) &&

                (Math.max(L1.pt1.y, L1.pt2.y) >= Math.min(L2.pt1.y, L2.pt2.y)) &&

                (Math.max(L2.pt1.y, L2.pt2.y) >= Math.min(L1.pt1.y, L1.pt2.y)) &&

                (Multiply(L2.pt1, L1.pt2, L1.pt1) * Multiply(L1.pt2, L2.pt2, L1.pt1) >= 0) &&

                (Multiply(L1.pt1, L2.pt2, L2.pt1) * Multiply(L2.pt2, L1.pt2, L2.pt1) >= 0)

        );
    }

	/* 射线法判断点q与多边形polygon的位置关系，要求polygon为简单多边形，顶点逆时针排列

    运用了叉积，了解一下叉积的知识

	如果点在多边形内： 输出YES

	如果点在多边形边上： 输出YES

	如果点在多边形外： 输出NO

	*/

    public int InPolygon(List<Point> polygon, Point point)

    {
        int n = polygon.size();
        int count = 0;
        LineSegment line = new LineSegment();

        line.pt1 = point;
        line.pt2.y = point.y;
        line.pt2.x = (int)-INFINITY;
        for (int i = 0; i < n; i++) {
            // 得到多边形的一条边
            LineSegment side = new LineSegment();
            side.pt1 = polygon.get(i);
            side.pt2 = polygon.get((i + 1) % n);
            if (IsOnline(point, side)) {
                String result = "YES";
                System.out.println(result);
                return 1;

            }

            // 如果side平行x轴则不作考虑
            if (Math.abs(side.pt1.y - side.pt2.y) < ESP) {
                continue;
            }

            if (IsOnline(side.pt1, line)) {
                if (side.pt1.y > side.pt2.y)//此时是相交的，将交叉点数加一
                    count++;
            } else if (IsOnline(side.pt2, line)) {

                if (side.pt2.y > side.pt1.y)
                    count++;
            } else if (Intersect(line, side)) {
                count++;

            }
        }

        if (count % 2 == 1) {//如果交叉点数为奇数，一定在多边形内部
           String result = "YES";
            System.out.println(result);
            return 0;

        } else {//为偶数说明一个点发出的射线与多边形至少相交两次，一定在外部
            String result = "NO";
            System.out.println(result);
            return 2;
        }

    }
}

class Point1 {
    public double x;
    public double y;

    public Point1() {
    }

    public Point1(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class LineSegment {
    public Point pt1;
    public Point pt2;

    public LineSegment() {
        this.pt1 = new Point();
        this.pt2 = new Point();
    }
}

