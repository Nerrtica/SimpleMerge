package model;

import java.util.ArrayList;
import java.util.List;

public interface ImportedFileInterface {
	public void load (String fileRoute);
	public void convert(String document, ArrayList<Boolean> bool);
	public void save ();
	public void saveAs (String asFileRoute);
	public ArrayList<DiffBlock> compare (ImportedFile oppositeFile, int[] diffList);
	public void merge (ImportedFile oppositeFile, List<DiffBlock> destBlockList, List<DiffBlock> srcBlockList, int blockIndex);
	public ArrayList<String> getText ();
}
