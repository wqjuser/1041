package com.wqj;

/**
 * Created by MR.WEN on 2016/8/7.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 判断一个点，是否在一个多边形区域内
 */
public class test1041 {
    //    static int x;
//    static int y;
    static double x;
    static double y;

    public static void main(String[] args) {

        double px = 113.705835;
        double py = 34.787479;
//        List<Double> checkpointx = new ArrayList<Double>();
//        List<Double> checkpointy = new ArrayList<Double>();
//        List<Integer> X = new ArrayList<>();
//        List<Integer> Y = new ArrayList<>();
        List<Integer> N = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int numof2 = 0;
        ArrayList<Double> polygonXA = new ArrayList<Double>();
        ArrayList<Double> polygonYA = new ArrayList<Double>();
//        for (int i = 0; i < a; i++) {
//            int status = s.nextInt();
//            x = s.nextInt();
//            y = s.nextInt();
//            N.add(status);
////            X.add(x);
////            Y.add(y);
//        }

//        for (int j = 0; j < N.size(); j++) {
//            if (N.get(j) == 1) {
//                polygonXA.add(x);
//                polygonYA.add(y);
//            }
//            if (N.get(j) == 2) {
//                numof2++;
//                checkpointx.add(x);
//                checkpointy.add(y);
//            }
//        }

//        polygonXA.add(113.700213);// 北京
//        polygonXA.add(113.706651);// 石家庄
//        polygonXA.add(113.706608);// 德州
//        polygonXA.add(113.707337);// 天津
//        polygonXA.add(113.701587);// 烟台
//        polygonXA.add(113.700128);// 唐山jdal
//
//        polygonYA.add(34.791532);
//        polygonYA.add(34.791568);
//        polygonYA.add(34.789242);
//        polygonYA.add(34.786633);
//        polygonYA.add(34.786669);
//        polygonYA.add(34.789242);

        test1041 test = new test1041();
//        for (int i = 0; i <numof2 ; i++) {
//
//        }
        System.out.println(test.isPointInPolygon(px, py, polygonXA, polygonYA));
    }

    public boolean isPointInPolygon(double px, double py, ArrayList<Double> polygonXA, ArrayList<Double> polygonYA) {
        boolean isInside = false;
        String str ="NO";
        double ESP = 1e-9;
        int count = 0;
        double linePoint1x;
        double linePoint1y;
        double linePoint2x = 180;
        double linePoint2y;

        linePoint1x = px;
        linePoint1y = py;
        linePoint2y = py;

        for (int i = 0; i < polygonXA.size() - 1; i++) {
            double cx1 = polygonXA.get(i);
            double cy1 = polygonYA.get(i);
            double cx2 = polygonXA.get(i + 1);
            double cy2 = polygonYA.get(i + 1);
            if (isPointOnLine(px, py, cx1, cy1, cx2, cy2)) {
                str="YES";
                System.out.println(str);
                return true;

            }
            if (Math.abs(cy2 - cy1) < ESP) {
                continue;
            }

            if (isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
                if (cy1 > cy2)
                    count++;
            } else if (isPointOnLine(cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
                if (cy2 > cy1)
                    count++;
            } else if (isIntersect(cx1, cy1, cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
                count++;
            }
        }
//        System.out.println(count);
        if (count % 2 == 1) {
            str="YES";
            System.out.println(str);
            isInside = true;
        }
        return isInside;
    }

    public double Multiply(double px0, double py0, double px1, double py1, double px2, double py2) {
        return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));
    }

    public boolean isPointOnLine(double px0, double py0, double px1, double py1, double px2, double py2) {
        boolean flag = false;
        double ESP = 1e-9;
        if ((Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP) && ((px0 - px1) * (px0 - px2) <= 0)
                && ((py0 - py1) * (py0 - py2) <= 0)) {
            flag = true;
        }
        return flag;
    }

    public boolean isIntersect(double px1, double py1, double px2, double py2, double px3, double py3, double px4,
                               double py4) {
        boolean flag = false;
        double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
        if (d != 0) {
            double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3)) / d;
            double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1)) / d;
            if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
                flag = true;
            }
        }
        return flag;
    }
}
