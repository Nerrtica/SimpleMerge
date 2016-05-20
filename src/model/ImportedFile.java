package model;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ImportedFile {
    private ArrayList<String> text = new ArrayList<String>();
    private String fileRoute;
	private String checkCode = null;
    
    public ImportedFile (){
    	
    }

    public void load (String fileRoute){
    	this.fileRoute = fileRoute;	//fileRoute 저장
    	ArrayList<String> temp = new ArrayList<String>();
    		
    
    	try{	
			FileInputStream check = new FileInputStream(fileRoute);
			
			byte[] BOM = new byte[4];
			check.read(BOM, 0, 4);
			         
			// 파일 인코딩 확인하기
			if( (BOM[0] & 0xFF) == 0xEF && (BOM[1] & 0xFF) == 0xBB && (BOM[2] & 0xFF) == 0xBF ){
				checkCode = "UTF-8";
			    System.out.println("UTF-8");
			}
			else if( (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF ){
				checkCode = "UTF-16BE";
			    System.out.println("UTF-16BE");
			}
			else if( (BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE ){
				checkCode = "UTF-16LE";
			    System.out.println("UTF-16LE");
			}
			else if( (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00 && (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF ){
				checkCode = "UTF-32BE";
			    System.out.println("UTF-32BE");
			}
			else if( (BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE && (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00 ){
				checkCode = "UTF-32LE";
			    System.out.println("UTF-32LE");
			}
			else{
				checkCode = "EUC-KR";
			    System.out.println("EUC-KR");   
			}
			
			check.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// BufferedReader br = new BufferedReader(new
			// FileReader(fileRoute)); // 파일 reader
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileRoute), checkCode));
				String line;
				while (true) {
					line = br.readLine();
					if (line == null) // 더 이상 불러올 문자열 없으면 break;
						break;
					temp.add(line); // text 리스트에 문자열 추가
				}
				text = temp;
				temp = new ArrayList<String>();
				br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < text.size(); i++) {
			System.out.println(text.get(i));
		}

    }
    public void convert(String document, ArrayList<Boolean> bool){
    	String[] temp = document.split(System.getProperty("line.separator"));	//개행 문자를 기준으로 문자열 나눔
    	text = new ArrayList<String>();			//text 초기화
    	
    	for(int i = 0 ; i < temp.length ; i++){
    		if(bool.get(i)==false){			// temp라인은 저장 안한다.
    			continue;
    		}
    		text.add(temp[i]);				// text에 저장
    	}
    }

    public void save (){

//    	PrintWriter pw = new PrintWriter(fileRoute);
		try {
			BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileRoute), checkCode));

			for (int i = 0; i < text.size(); i++) {
				pw.write(text.get(i));
				pw.write(System.getProperty("line.separator"));
			}

			pw.close();
    	}
    	catch(FileNotFoundException e){
    		e.printStackTrace();
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void saveAs (String asFileRoute){

		try {
			BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(asFileRoute), checkCode));

			for (int i = 0; i < text.size(); i++) {
				pw.write(text.get(i));
				pw.write(System.getProperty("line.separator"));
			}

			pw.close();
    	}
    	catch(FileNotFoundException e){
    		e.printStackTrace();
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public ArrayList<DiffBlock> compare (ImportedFile oppositeFile) {
		ArrayList<DiffBlock> diffBlockList = new ArrayList<DiffBlock>();

		LCSAlgo.LCSLength(this.getText(), oppositeFile.getText());
		LCSAlgo.makeDiffList(this.getText(), oppositeFile.getText(), this.getText().size(), oppositeFile.getText().size());
		int[] diffList = LCSAlgo.getDiffList();
		for (int i = 0; i < diffList.length; i++) {
			// 연속된 -1을 찾아 Block으로 만듬
			if (diffList[i] == -1) {
				DiffBlock tempBlock;
				int startIndex = i;
				for (; i < diffList.length; i++) {
					if (diffList[i] == -1) { continue; }
					else { break; }
				}
				int endIndex = i - 1;
				tempBlock = new DiffBlock(startIndex, endIndex);
				diffBlockList.add(tempBlock);
				i--;
				continue;
			}
			//값이 건너뛰어지는 부분을 찾아 Block으로 만듬
			if ((i != diffList.length - 1 && diffList[i + 1] - diffList[i] != 1 && diffList[i + 1] != -1)) {
				DiffBlock tempBlock = new DiffBlock(i + 1, i);
				diffBlockList.add(tempBlock);
				continue;
			}
			//처음에 값이 건너뛰어지는 블록이 생성될 경우
			if (i == 0 && diffList[i] != 0) {
				DiffBlock tempBlock = new DiffBlock(0, -1);
				diffBlockList.add(tempBlock);
				continue;
			}

			//마지막에 값이 건너뛰어지는 블록이 생성될 경우
			if (i == diffList.length - 1 && diffList[i] != oppositeFile.getText().size() - 1) {
				DiffBlock tempBlock = new DiffBlock(i + 1, i);
				diffBlockList.add(tempBlock);
				continue;
			}
		}

		return diffBlockList;
    }

    public void merge (ImportedFile oppositeFile, List<DiffBlock> originalBlockList, List<DiffBlock> targetBlockList, int blockIndex) {
		int startIndex = originalBlockList.get(blockIndex).getStartIndex();
		int changedIndexNumber = targetBlockList.get(blockIndex).getLineNumber() - originalBlockList.get(blockIndex).getLineNumber();

		for(int i = 0; i < originalBlockList.get(blockIndex).getLineNumber(); i++) {
			text.remove(startIndex);
		}
		for(int i = 0; i < targetBlockList.get(blockIndex).getLineNumber(); i++) {
			text.add(startIndex + i, oppositeFile.getText().get(targetBlockList.get(blockIndex).getStartIndex() + i));
		}

		for (int i = blockIndex + 1; i < originalBlockList.size(); i++) {
			originalBlockList.get(i).changeStartIndex(originalBlockList.get(i).getStartIndex() + changedIndexNumber);
			originalBlockList.get(i).changeEndIndex(originalBlockList.get(i).getEndIndex() + changedIndexNumber);
		}
    }

    public ArrayList<String> getText () {
        return text;
    }

}
