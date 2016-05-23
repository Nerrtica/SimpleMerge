package controller;

import view.ClsView;
import model.ImportedFile;
import model.DiffBlock;
import java.awt.EventQueue;
import java.util.ArrayList;


public class ClsController {
    ImportedFile leftFile = new ImportedFile();
    ImportedFile rightFile = new ImportedFile();
    ArrayList<DiffBlock> leftDiffBlockList;
    ArrayList<DiffBlock> rightDiffBlockList;
    ClsView window;

    public ClsController ()
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
               show();
            }
        });

    }

    private void show () {
        try
        {
            window = new ClsView(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> load (String fileRoute, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.load(fileRoute);
            return leftFile.getText();
        } else {
            rightFile.load(fileRoute);
            return rightFile.getText();
        }
    }

    public void save (boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.save();
        } else {
            rightFile.save();
        }
    }

    public void saveAs (String fileRoute, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.saveAs(fileRoute);
        } else {
            rightFile.saveAs(fileRoute);
        }
    }

    public void edit (String document, ArrayList<Boolean> bool, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.convert(document, bool);
        } else {
            rightFile.convert(document, bool);
        }
    }

    public void compare (boolean isLeftFile) {
        if (isLeftFile) {
            leftDiffBlockList = leftFile.compare(rightFile);
        } else {
            rightDiffBlockList = rightFile.compare(leftFile);
        }
    }

    public ArrayList<String> merge (int blockIndex, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.merge(rightFile, leftDiffBlockList, rightDiffBlockList, blockIndex);
            return leftFile.getText();
        } else {
            rightFile.merge(leftFile, rightDiffBlockList, leftDiffBlockList, blockIndex);
            return rightFile.getText();
        }
    }
    
    public ArrayList<String> mergeAll (boolean isLeftFile)
    {
        ArrayList<String> temp = null;
    	for (int i = 0; i < leftDiffBlockList.size(); i++) {
            temp = merge(i, isLeftFile);
        }
        return temp;
    }

    public int compareBlockLine (int blockIndex, boolean isLeftFile) {
        if (isLeftFile) {
            return leftDiffBlockList.get(blockIndex).compareBlockLine(rightDiffBlockList.get(blockIndex));
        } else {
            return rightDiffBlockList.get(blockIndex).compareBlockLine(leftDiffBlockList.get(blockIndex));
        }
    }

    public ArrayList<DiffBlock> getLeftDiffBlockList () {
        return leftDiffBlockList;
    }

    public ArrayList<DiffBlock> getRightDiffBlockList () {
        return rightDiffBlockList;
    }
    
}
