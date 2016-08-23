package Melkman;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Melkman求凸包算法
 *
 * @author zl
 */
public class Main {
    private Point[] pointArray;//坐标数组
    private final int N;//数据个数
    private int D[]; // 数组索引
    static List<Point> convex_hull = new ArrayList<>();//凸包列表

    public Main(List<Point> pList) {
        this.pointArray = new Point[pList.size()];
        N = pList.size();
        int k = 0;
        for (Point p : pList) {
            pointArray[k++] = p;
        }
        D = new int[2 * N];
    }

    /**
     * 求凸包点
     *
     * @return 所求凸包点
     */

    public Point[] getTubaoPoint() {

        int minY = pointArray[0].getY();
        int j = 0;
        for (int i = 1; i < N; i++) {
            if (pointArray[i].getY() < minY) {
                minY = pointArray[i].getY();
                j = i;
            }
        }
        swap(0, j);
        // 计算除第一顶点外的其余顶点到第一点的线段与x轴的夹角
        for (int i = 1; i < N; i++) {
            pointArray[i].setArCos(angle(i));
        }
        quickSort(1, N - 1); // 根据所得到的角度进行快速排序
        int bot = N - 1;
        int top = N;
        D[top++] = 0;
        D[top++] = 1;
        int i;
        for (i = 2; i < N; i++) {// 寻找第三个点 要保证3个点不共线！！
            if (isLeft(pointArray[D[top - 2]], pointArray[D[top - 1]],
                    pointArray[i]) != 0)
                break;
            D[top - 1] = i; // 共线就更换顶点
        }
        D[bot--] = i;
        D[top++] = i; // i是第三个点 不共线！！
        int t;
        if (isLeft(pointArray[D[N]], pointArray[D[N + 1]], pointArray[D[N + 2]]) < 0) {
            // 此时队列中有3个点，要保证3个点a,b,c是成逆时针的，不是就调换ab
            t = D[N];
            D[N] = D[N + 1];
            D[N + 1] = t;
        }

        for (i++; i < N; i++) {
            // 如果成立就是i在凸包内，跳过 //top=n+3 bot=n-2
            if (isLeft(pointArray[D[top - 2]], pointArray[D[top - 1]],
                    pointArray[i]) > 0
                    && isLeft(pointArray[D[bot + 1]], pointArray[D[bot + 2]],
                    pointArray[i]) > 0) {
                continue;
            }

            //非左转 则退栈
            while (isLeft(pointArray[D[top - 2]], pointArray[D[top - 1]],
                    pointArray[i]) <= 0) {
                top--;
            }
            D[top++] = i;

            //反向表非左转 则退栈
            while (isLeft(pointArray[D[bot + 1]], pointArray[D[bot + 2]],
                    pointArray[i]) <= 0) {
                bot++;
            }
            D[bot--] = i;
        }
        // 凸包构造完成，D数组里bot+1至top-1内就是凸包的序列(头尾是同一点)
        Point[] resultPoints = new Point[top - bot - 1];
        int index = 0;
        convex_hull.clear();
        for (i = bot + 1; i < top - 1; i++) {
            Point p = new Point();
            p.setX(pointArray[D[i]].getX());
            p.setY(pointArray[D[i]].getY());
            convex_hull.add(p);
            resultPoints[index++] = pointArray[D[i]];
        }
        return resultPoints;

    }


    public static void main(String args[]) {
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        List<Integer> N = new ArrayList<>();
        List<Point> pointList = new ArrayList<Point>();
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        for (int i = 0; i < 3; i++) {
           int status = s.nextInt();
//            N.add(status);
            int x = s.nextInt();
            int y = s.nextInt();
//            X.add(x);
//            Y.add(y);
            Point p = new Point();
            p.setX(x);
            p.setY(y);
            pointList.add(p);
        }
        Main m = new Main(pointList);
        m.getTubaoPoint();
        for (int i = 0; i < a - 3; i++) {
            int status = s.nextInt();
            int x = s.nextInt();
            int y = s.nextInt();
            N.add(status);
            X.add(x);
            Y.add(y);
        }
        for (int j = 0; j < N.size(); j++) {
            if (N.get(j) == 1) {
                Point p = new Point();
                p.setX(X.get(j));
                p.setY(Y.get(j));
                pointList.add(p);
                Main m1 = new Main(pointList);
                m1.getTubaoPoint();
            }
            if (N.get(j) == 2) {
                Point p = new Point();
                p.setX(X.get(j));
                p.setY(Y.get(j));
//                System.out.println(convex_hull.size());
                if (checkWithJdkGeneralPath(p, convex_hull) || InPolygon(convex_hull, p)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }


        }


//        for (int i = 0; i < convex_hull.size(); i++) {
//            System.out.print(convex_hull.get(i).getX()+" "+convex_hull.get(i).getY()+" ");
//        }

//        if (checkWithJdkGeneralPath(p, convex_hull)|| InPolygon(convex_hull, p) ) {
//            System.out.println("YES");
//        } else {
//            System.out.println("NO");
//        }


    }

    public static boolean checkWithJdkGeneralPath(Point point, List<Point> polygon) {
        GeneralPath s = new GeneralPath();
        Point first = polygon.get(0);
        s.moveTo(first.x, first.y);
        for (Point d : polygon) {
            s.lineTo(d.x, d.y);
        }
        s.lineTo(first.x, first.y);
        s.closePath();
        return s.contains(point.getX(), point.getY());
    }


//        for (int i = 0; i < a; i++) {
//            int status = s.nextInt();
//            int x = s.nextInt();
//            int y = s.nextInt();
//            N.add(status);
//            X.add(x);
//            Y.add(y);
//
//        }
//        for (int j = 0; j < N.size(); j++) {
//            if (N.get(j) == 1) {
//                Point p = new Point();
//                p.setX(X.get(j));
//                p.setY(Y.get(j));
//                pointList.add(p);
//                pointList1.add(p);
//            }
//            if (N.get(j) == 2) {
//                numof2++;
//                Point p = new Point();
//                p.setX(X.get(j));
//                p.setY(Y.get(j));
//                checkpointlb.add(p);
//            }
//        }


    /**
     * 判断ba相对ao是不是左转
     *
     * @return 大于0则左转
     */
    private float isLeft(Point o, Point a, Point b) {
        float aoX = a.getX() - o.getX();
        float aoY = a.getY() - o.getY();
        float baX = b.getX() - a.getX();
        float baY = b.getY() - a.getY();

        return aoX * baY - aoY * baX;
    }

    /**
     * 实现数组交换
     *
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        Point tempPoint = new Point();
        tempPoint.setX(pointArray[j].getX());
        tempPoint.setY(pointArray[j].getY());
        tempPoint.setArCos(pointArray[j].getArCos());

        pointArray[j].setX(pointArray[i].getX());
        pointArray[j].setY(pointArray[i].getY());
        pointArray[j].setArCos(pointArray[i].getArCos());

        pointArray[i].setX(tempPoint.getX());
        pointArray[i].setY(tempPoint.getY());
        pointArray[i].setArCos(tempPoint.getArCos());
    }

    /**
     * 快速排序
     *
     * @param top
     * @param bot
     */
    private void quickSort(int top, int bot) {
        int pos;
        if (top < bot) {
            pos = loc(top, bot);
            quickSort(top, pos - 1);
            quickSort(pos + 1, bot);
        }
    }

    /**
     * 移动起点，左侧为小，右侧为大
     *
     * @param top
     * @param bot
     * @return 移动后的位置
     */
    private int loc(int top, int bot) {
        double x = pointArray[top].getArCos();
        int j, k;
        j = top + 1;
        k = bot;
        while (true) {
            while (j < bot && pointArray[j].getArCos() < x)
                j++;
            while (k > top && pointArray[k].getArCos() > x)
                k--;
            if (j >= k)
                break;
            swap(j, k);
        }
        swap(top, k);
        return k;
    }

    /**
     * 角度计算
     *
     * @param i 指针
     * @return
     */
    private double angle(int i) {
        double j, k, m, h;
        j = pointArray[i].getX() - pointArray[0].getX();
        k = pointArray[i].getY() - pointArray[0].getY();
        m = Math.sqrt(j * j + k * k); // 得到顶点i 到第一顶点的线段长度
        if (k < 0)
            j = (-1) * Math.abs(j);
        h = Math.acos(j / m); // 得到该线段与x轴的角度
        return h;
    }


    static class Point {
        int x;        //X坐标
        int y;        //Y坐标
        double arCos;    //与P0点的角度

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getArCos() {
            return arCos;
        }

        public void setArCos(double arCos) {
            this.arCos = arCos;
        }
    }

    static double ESP = 1e-5;

    static double Multiply(Point p1, Point p2, Point p0) {
        return ((p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y));
    }
    static boolean on_segment( Point p1,Point p2 ,Point p )
    {
        double max=p1.x > p2.x ? p1.x : p2.x ;
        double min =p1.x < p2.x ? p1.x : p2.x ;
        double max1=p1.y > p2.y ? p1.y : p2.y ;
        double min1=p1.y < p2.y ? p1.y : p2.y ;
        if( p.x >=min && p.x <=max &&
                p.y >=min1 && p.y <=max1 )
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

    public static boolean InPolygon(List<Point> polygon, Point point) {
        int n = polygon.size();
        LineSegment line = new LineSegment();
        LineSegment side = new LineSegment();
        line.pt1 = point;
        for (int i = 0; i < n; i++) {
            // 得到多边形的一条边
            side.pt1 = polygon.get(i);
            side.pt2 = polygon.get((i + 1) % n);
            if (IsOnline(point, side)&&on_segment(side.pt1,side.pt2,point)) {
                return true;
            }
        }
        return false;
    }

}

