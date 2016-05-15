package model;

public class DiffBlock {
    private int startIndex;
    private int endIndex;
    private int lineNumber;

    public DiffBlock (int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            //에러 발생
            return;
        }
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.lineNumber = endIndex - startIndex + 1;
    }

    public void changeStartIndex (int startIndex) {
        if (startIndex > this.endIndex) {
            //에러 발생
            return;
        }
        this.startIndex = startIndex;
        this.lineNumber = this.endIndex - startIndex + 1;
    }

    public void changeEndIndex (int endIndex) {
        if (this.startIndex > endIndex) {
            //에러 발생
            return;
        }
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
}
