package model;

public class DiffBlock {
    private int startIndex;         //Block의 시작 Index
    private int endIndex;           //Block의 끝 Index
    private int lineNumber;         //Block의 라인 수

    /* Constructor */
    public DiffBlock (int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            //에러 발생
            return;
        }
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.lineNumber = endIndex - startIndex + 1;
    }

    /* 시작 Index 변경 method */
    public void changeStartIndex (int startIndex) {
        if (startIndex > this.endIndex) {
            //에러 발생
            return;
        }
        this.startIndex = startIndex;
        this.lineNumber = this.endIndex - startIndex + 1;
    }

    /* 끝 Index 변경 method */
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
