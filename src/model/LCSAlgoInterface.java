package model;

import java.util.List;

public interface LCSAlgoInterface {
	public int LCSLength(List<String> A, List<String> B);
	public List<String> readLCS(List<String> A, List<String> B, int i, int j);
	public void printDiff(List<String> A, List<String> B, int i, int j);
	public void makeDiffList(List<String> A, List<String> B, int i, int j);
	public int[] getLeftDiffList();
	public int[] getRightDiffList();
}
