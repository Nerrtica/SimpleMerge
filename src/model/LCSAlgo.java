package model;

import java.util.List;
import java.util.ArrayList;

public class LCSAlgo {
    public static int[][] LCSLength(List<String> A, List<String> B) {
        int ASize = A.size(), BSize = B.size();
        int[][] C = new int[ASize + 1][BSize + 1];

        for (int i = 0; i <= ASize; i++) { C[i][0] = 0; }
        for (int j = 0; j <= BSize; j++) { C[0][j] = 0; }
        for (int i = 1; i <= ASize; i++) {
            for (int j = 1; j <= BSize; j++) {
                if (A.get(i - 1).equals(B.get(j - 1))) {
                    C[i][j] = C[i - 1][j - 1] + 1;
                } else {
                    C[i][j] = Math.max(C[i][j - 1], C[i - 1][j]);
                }
            }
        }

        return C;
    }

    public static List<String> readLCS(int[][] C, List<String> A, List<String> B, int i, int j) {
        if (i == 0 || j == 0) { return new ArrayList<String>(); }
        else if (A.get(i - 1).equals(B.get(j - 1))) {
            List<String> newList = readLCS(C, A, B, i - 1, j - 1);
            newList.add(A.get(i - 1));
            return newList;
        } else {
            if (C[i][j - 1] > C[i - 1][j]) {
                return readLCS(C, A, B, i, j - 1);
            } else {
                return readLCS(C, A, B, i - 1, j);
            }
        }
    }

    public static void printDiff(int[][] C, List<String> A, List<String> B, int i, int j) {
        if (i > 0 && j > 0 && A.get(i - 1).equals(B.get(j - 1))) {
            printDiff(C, A, B, i - 1, j - 1);
            System.out.println("  " + A.get(i - 1));
        } else if (j > 0 && (i == 0 || C[i][j - 1] >= C[i - 1][j])) {
            printDiff(C, A, B, i, j - 1);
            System.out.println("+ " + B.get(j - 1));
        } else if (i > 0 && (j == 0 || C[i][j - 1] < C[i - 1][j])) {
            printDiff(C, A, B, i - 1, j);
            System.out.println("- " + A.get(i - 1));
        } else {
            System.out.println("");
        }
    }
}
