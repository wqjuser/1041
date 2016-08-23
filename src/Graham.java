/**
 * Created by MR.WEN on 2016/8/15.
 */

import com.wqj.*;

import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Graham {
    static List<Graham.point> po = new ArrayList();
    static int MAX = 100015;
    static point[] point = new point[MAX];
    static int[] stack = new int[MAX];
    static int n;
    static int top;


    static class point {
        public double x, y;

        public point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //(p1-p0)x(p2-p0),判断两直线的相对位置
    static double xmul(point p1, point p2, point p0) {
        return (p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y);
    }

    static double distance(point p1, point p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
    }

    static void swap(int i, int j) {
        point tmp = point[i];
        point[i] = point[j];
        point[j] = tmp;
    }

    static void grahamscan(int n) {

        int u = 0, i = 0;
        for (; i < n; i++)
            if ((point[i].y < point[u].y) || (point[i].y == point[u].y && point[i].x < point[u].x))
                u = i;
        swap(u, 0);
        Arrays.sort(point, 0, n, new MyComp());
        for (i = 0; i < 3; i++)
            stack[i] = i; //point0 point1 point2入栈
        top = 2;
        for (i = 3; i < n; i++) {
            while (xmul(point[i], point[stack[top]], point[stack[top - 1]]) >= 0) {//弹出非左转的点
                if (top == 0) {
                    break;
                }
                top--;
            }
            stack[++top] = i;
            
        }
    }

    static class MyComp implements Comparator<point> {
        public int compare(point p1, point p2) {
            double xm = xmul(p1, p2, point[0]);
            if (xm < 0) return 1;
            else if (xm == 0 && distance(p1, point[0]) > distance(p2, point[0])) return 1;
            else return -1;
        }
    }

//    static double area(int n) {
//        double area = 0;
//        for (int i = 2; i <= n - 1; i++) {
//            area += xmul(point[stack[i]], point[stack[i - 1]], point[0]);
//        }
//        return Math.abs(area) / 2;
//    }

    static void input() {
        int x = 0, y = 0;
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            x = cin.nextInt();
            y = cin.nextInt();
            point[i] = new point(x, y);
        }
    }

    static void print() {
        for (int i = 0; i <= top; i++) {
            System.out.print(stack[i] + "-");
        }
        System.out.println("0");
        System.out.print(po.size());
    }

    public static void main(String[] args) {
        input();
        grahamscan(n);
        print();
    }
}
