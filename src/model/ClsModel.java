package model;

import java.util.ArrayList;

public class ClsModel {
    ImportedFile leftFile;
    ImportedFile rightFile;
    ArrayList<DiffBlock> leftDiffBlockList;
    ArrayList<DiffBlock> rightDiffBlockList;

    public ClsModel () {
        leftFile = new ImportedFile();
        rightFile = new ImportedFile();
    }

    public void load (String fileRoute, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.load(fileRoute);
        } else {
            rightFile.load(fileRoute);
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

    public void edit (String document, ArrayList<Boolean> isTrueLineList, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.convert(document, isTrueLineList);
        } else {
            rightFile.convert(document, isTrueLineList);
        }
    }

    public void compare () {
        leftDiffBlockList = leftFile.compare(rightFile);
        rightDiffBlockList = rightFile.compare(leftFile);
    }

    public void merge (int blockIndex, boolean isRightToLeft) {
        if (isRightToLeft) {
            leftFile.merge(rightFile, leftDiffBlockList, rightDiffBlockList, blockIndex);
        } else {
            rightFile.merge(leftFile, rightDiffBlockList, leftDiffBlockList, blockIndex);
        }
    }

    public void mergeAll (boolean isRightToLeft)
    {
        for (int i = 0; i < leftDiffBlockList.size(); i++) {
            merge(i, isRightToLeft);
        }
    }

    //return value : Target Block Line # - Original Block Line #
    public int getBlockLineDiff (int blockIndex, boolean isLeftFile) {
        if (isLeftFile) {
            return leftDiffBlockList.get(blockIndex).getBlockLineDiff(rightDiffBlockList.get(blockIndex));
        } else {
            return rightDiffBlockList.get(blockIndex).getBlockLineDiff(leftDiffBlockList.get(blockIndex));
        }
    }

    public ImportedFile getLeftFile () { return leftFile; }
    public ImportedFile getRightFile () { return rightFile; }
    public ArrayList<DiffBlock> getLeftDiffBlockList () { return leftDiffBlockList; }
    public ArrayList<DiffBlock> getRightDiffBlockList () { return rightDiffBlockList; }
}
