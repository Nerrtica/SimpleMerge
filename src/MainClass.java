import model.LCSAlgo;
import model.DiffBlock;
import model.ImportedFile;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;

public class MainClass {
    public static void main (String args[]) {
        ImportedFile file1 = new ImportedFile("TempFile/factorial.c");
        ImportedFile file2 = new ImportedFile("TempFile/factorial_2.c");


        List<String> A = file1.getText();
        List<String> B = file2.getText();

        LCSAlgo.LCSLength(A, B);
        List<String> LCS = LCSAlgo.readLCS(A, B, A.size(), B.size());

        LCSAlgo.printDiff(A, B, A.size(), B.size());

        List<DiffBlock> tempList = file1.compare(file2);
        List<DiffBlock> tempList2 = file2.compare(file1);

        System.out.printf("%d, %d\n", tempList.size(), tempList2.size());

        for(int i = 0; i < tempList.size(); i++) {
            System.out.println(A.get(tempList.get(i).getStartIndex()));
        }
        System.out.println("--------------------------------------------------------------");
        for(int i = 0; i < tempList2.size(); i++) {
            System.out.println(B.get(tempList2.get(i).getStartIndex()));
        }

        //LCSAlgo.makeDiffList(C, A, B, A.size(), B.size());


        /*
        DiffBlock block1 = new DiffBlock(14, 14);
        DiffBlock block2 = new DiffBlock(14, 16);

        file1.merge(file2, block1, block2);

        List<String> A = file1.getText();

        for (int i = 0; i < A.size(); i++) {
            System.out.println(A.get(i));
        }
        */
    }
}
