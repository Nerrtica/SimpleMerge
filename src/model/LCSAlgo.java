package model;

import java.util.List;
import java.util.ArrayList;

public class LCSAlgo {
    private static int[][] C;
    private static int[] leftDiffList;
    private static int[] rightDiffList;

    /* LCS의 최대 길이를 구하는 method */
    public static int LCSLength(List<String> A, List<String> B) {
        int ASize = A.size(), BSize = B.size();
        C = new int[ASize + 1][BSize + 1];

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
        return C[ASize][BSize];
    }

    /* LCS 배열값을 구하는 method */
    public static List<String> readLCS(List<String> A, List<String> B, int i, int j) {
        if (i == 0 || j == 0) { return new ArrayList<String>(); }
        else if (A.get(i - 1).equals(B.get(j - 1))) {
            List<String> newList = readLCS(A, B, i - 1, j - 1);
            newList.add(A.get(i - 1));
            return newList;
        } else {
            if (C[i][j - 1] > C[i - 1][j]) {
                return readLCS(A, B, i, j - 1);
            } else {
                return readLCS(A, B, i - 1, j);
            }
        }
    }

    public static void makeDiffList(List<String> A, List<String> B, int i, int j) {
        if (i == A.size() && j == B.size()) {
            leftDiffList = new int[A.size()];
            rightDiffList = new int[B.size()];
        }

        if (i > 0 && j > 0 && A.get(i - 1).equals(B.get(j - 1))) {
            makeDiffList(A, B, i - 1, j - 1);
            leftDiffList[i - 1] = j - 1;
            rightDiffList[j - 1] = i - 1;
        } else if (j > 0 && (i == 0 || C[i][j - 1] >= C[i - 1][j])) {
            makeDiffList(A, B, i, j - 1);
            rightDiffList[j - 1] = -1;
        } else if (i > 0 && (j == 0 || C[i][j - 1] < C[i - 1][j])) {
            makeDiffList(A, B, i - 1, j);
            leftDiffList[i - 1] = -1;
        }
    }

    public static int[] getLeftDiffList() {
        return leftDiffList;
    }

    public static int[] getRightDiffList() { return rightDiffList; }

}
