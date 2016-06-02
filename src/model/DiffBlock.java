package model;

public class DiffBlock implements DiffBlockInterface{
    private int startIndex;         //Block의 시작 Index
    private int endIndex;           //Block의 끝 Index
    private int lineNumber;         //Block의 라인 수

    /* Constructor */
    public DiffBlock (int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.lineNumber = endIndex - startIndex + 1;
    }

    /* 시작 Index 변경 method */
    public void changeStartIndex (int startIndex) {
        this.startIndex = startIndex;
        this.lineNumber = this.endIndex - startIndex + 1;
    }

    /* 끝 Index 변경 method */
    public void changeEndIndex (int endIndex) {
        this.endIndex = endIndex;
        this.lineNumber = endIndex - this.startIndex + 1;
    }

    public int getStartIndex () {
        return startIndex;
    }

    public int getEndIndex () {
        return endIndex;
    }

    public int getLineNumber () {
        return lineNumber;
    }
    
    public int getBlockLineDiff (DiffBlock targetBlock) {
        return targetBlock.getLineNumber() - this.getLineNumber();
    }
}
