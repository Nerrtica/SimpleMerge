package model;

public interface DiffBlockInterface {
	
	public void changeStartIndex (int startIndex);
	public void changeEndIndex (int endIndex);
	public int getStartIndex ();
	public int getEndIndex ();
	public int getLineNumber ();
	public int getBlockLineDiff (DiffBlock targetBlock);
	
}
