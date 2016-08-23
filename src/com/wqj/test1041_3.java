package com.wqj;

/**
 * Created by MR.WEN on 2016/8/9.
 */

import java.util.Scanner;

public class test1041_3 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int flag = 1;
        while (cin.hasNext()) {
            int t = cin.nextInt();
            if (t == 0) break;
            int[][] arr = new int[t][2];
            int t2 = t;
            for (int i = 0; i < t2; i++) {
                arr[i][0] = cin.nextInt();
                arr[i][1] = cin.nextInt();
            }
            flag = 1;
            int a = arr[1][1] - arr[0][1];
            int b = arr[0][0] - arr[1][0];
            int c = arr[0][0] * arr[1][1];
            int d = arr[1][0] * arr[0][1];
            for (int i = 2; i < t2; i++) {
                int m = arr[i][0] * a + arr[i][1] * b - c + d;
                if (i == 2) {
                    if (m > 0) flag = 1;
                    else flag = -1;
                }
                if (m > 0 && flag < 0 || m < 0 && flag > 0) {
                    flag = 0;
                }
            }
            if (flag == 0) {
                System.out.println("concave");
                break;
            } else {
                System.out.println("convex");
                break;
            }
        }

    }

}
