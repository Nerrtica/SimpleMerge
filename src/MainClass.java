/**
 * Created by Nerrtica on 2016. 5. 14..
 */

import model.LCSAlgo;
import model.ImportedFile;
import java.util.List;
import java.util.ArrayList;

public class MainClass {
    public static void main (String args[]) {
        ImportedFile file1 = new ImportedFile("TempFile/bsearch.c");
        ImportedFile file2 = new ImportedFile("TempFile/lsearch.c");

        List<String> A = file1.getText();
        List<String> B = file2.getText();

        int[][] C = LCSAlgo.LCSLength(A, B);
        List<String> LCS = LCSAlgo.readLCS(C, A, B, A.size(), B.size());

        LCSAlgo.printDiff(C, A, B, A.size(), B.size());

    }
}
