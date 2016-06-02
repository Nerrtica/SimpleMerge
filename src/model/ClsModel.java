package model;

import java.io.FileNotFoundException;
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

    public void load (String fileRoute, boolean isLeftFile) throws FileNotFoundException{
        if (isLeftFile) {
            leftFile.load(fileRoute);
        } else {
            rightFile.load(fileRoute);
        }
    }

    public void save (boolean isLeftFile) throws NullPointerException{
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
        LCSAlgo.LCSLength(leftFile.getText(), rightFile.getText());
        LCSAlgo.makeDiffList(leftFile.getText(), rightFile.getText(), leftFile.getText().size(), rightFile.getText().size());
        leftDiffBlockList = leftFile.compare(rightFile, LCSAlgo.getLeftDiffList());
        rightDiffBlockList = rightFile.compare(leftFile, LCSAlgo.getRightDiffList());
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
        int diffBlockListSize = leftDiffBlockList.size();
        for (int i = 0; i < diffBlockListSize; i++) {
            merge(0, isRightToLeft);
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

    public ArrayList<String> getFileText (boolean isLeftFile) {
        if (isLeftFile) { return leftFile.getText(); }
        else { return rightFile.getText(); }
    }

    public ArrayList<Integer> getDiffBlockStartIndexList (boolean isLeftBlock) {
        ArrayList<Integer> diffBlockStartIndex = new ArrayList<Integer>();

        for (int i = 0; i < leftDiffBlockList.size(); i++) {
            if (isLeftBlock) {
                diffBlockStartIndex.add(leftDiffBlockList.get(i).getStartIndex());
            } else {
                diffBlockStartIndex.add(rightDiffBlockList.get(i).getStartIndex());
            }
        }

        return diffBlockStartIndex;
    }

    public ArrayList<Integer> getDiffBlockLineNumberList (boolean isLeftBlock) {
        ArrayList<Integer> diffBlockLineNumber = new ArrayList<Integer>();

        for (int i = 0; i < leftDiffBlockList.size(); i++) {
            if (isLeftBlock) {
                diffBlockLineNumber.add(leftDiffBlockList.get(i).getLineNumber());
            } else {
                diffBlockLineNumber.add(rightDiffBlockList.get(i).getLineNumber());
            }
        }

        return diffBlockLineNumber;
    }
}
