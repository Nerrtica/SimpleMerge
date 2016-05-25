package model;

import java.util.ArrayList;

public class ClsModel {
    private ImportedFile leftFile;
    private ImportedFile rightFile;
    private ArrayList<DiffBlock> leftDiffBlockList;
    private ArrayList<DiffBlock> rightDiffBlockList;

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
    
    public ArrayList<Integer> getBlockLineDiffList(boolean isLeftFile)
    {
    	
    	ArrayList<Integer> blockLineDiffList = new ArrayList<Integer>();
    	int i;
    	
    	for(i = 0; i < leftDiffBlockList.size(); i++)
    		blockLineDiffList.add(getBlockLineDiff(i, isLeftFile));
    	
    	return blockLineDiffList;
    	
    }

    public ArrayList<String> getLeftFileText () { return leftFile.getText(); }
    public ArrayList<String> getRightFileText () { return rightFile.getText(); }

    public ArrayList<Integer> getLeftDiffBlockStartIndexList () {
        ArrayList<Integer> leftDiffBlockStartIndex = new ArrayList<Integer>();

        for (int i = 0; i < leftDiffBlockList.size(); i++) {
            leftDiffBlockStartIndex.add(leftDiffBlockList.get(i).getStartIndex());
        }

        return leftDiffBlockStartIndex;
    }

    public ArrayList<Integer> getLeftDiffBlockLineNumberList () {
        ArrayList<Integer> leftDiffBlockLineNumber = new ArrayList<Integer>();

        for (int i = 0; i < leftDiffBlockList.size(); i++) {
            leftDiffBlockLineNumber.add(leftDiffBlockList.get(i).getLineNumber());
        }

        return leftDiffBlockLineNumber;
    }

    public ArrayList<Integer> getRightDiffBlockStartIndexList () {
        ArrayList<Integer> rightDiffBlockStartIndex = new ArrayList<Integer>();

        for (int i = 0; i < rightDiffBlockList.size(); i++) {
            rightDiffBlockStartIndex.add(rightDiffBlockList.get(i).getStartIndex());
        }

        return rightDiffBlockStartIndex;
    }

    public ArrayList<Integer> getRightDiffBlockLineNumberList () {
        ArrayList<Integer> rightDiffBlockLineNumber = new ArrayList<Integer>();

        for (int i = 0; i < rightDiffBlockList.size(); i++) {
            rightDiffBlockLineNumber.add(rightDiffBlockList.get(i).getLineNumber());
        }

        return rightDiffBlockLineNumber;
    }
}
