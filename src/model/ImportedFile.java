package model;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImportedFile {
    private List<String> text = new ArrayList<String>();
    private String fileRoute;

    public ImportedFile (String fileRoute){
    	this.fileRoute = fileRoute;	//fileRoute 저장
    
    	try{
    		BufferedReader br = new BufferedReader(new FileReader(fileRoute));	// 파일 reader	
    		
	    	String line;	//파일에서 불러온 문자열을 임시로 저장할 String
	    	
	    	while(true){
	    		line = br.readLine();
	    		if(line == null)	//더 이상 불러올 문자열 없으면 break;
	    			break;
	    		text.add(line);	//text 리스트에 문자열 추가
	    	}
	    	
	    	br.close();
    	}
    	catch(FileNotFoundException e){
    		System.out.println("파일을 찾을 수 없습니다..");
    	}
    	catch(IOException e){
    		System.out.println("입출력 오류가 발생하였습니다.");
    	}

    }
    public void convert(String document, ArrayList<Boolean> bool){
    	String[] temp = document.split("\\n");	//개행 문자를 기준으로 문자열 나눔
    	text = new ArrayList<String>();			//text 초기화
    	
    	for(int i = 0 ; i < temp.length ; i++){
    		if(bool.get(i)==false){			// temp라인은 저장 안한다.
    			continue;
    		}
    		text.add(temp[i]);				// text에 저장
    	}
    }

    public void save (){
    	try{
	    	PrintWriter pw = new PrintWriter(fileRoute);
	    	
	    	for(int i = 0 ; i<text.size() ; i++){
	    		pw.println(text.get(i));
	    	}
	    	
	    	pw.close();
    	}
    	catch(FileNotFoundException e){
    		System.out.println("파일을 찾을 수 없습니다.");
    	}
    }

    public void modify (List<String> modifiedText) {

    }

    public List<DiffBlock> compare (ImportedFile oppositeFile) {
		List<DiffBlock> diffBlockList = new ArrayList<DiffBlock>();



		return diffBlockList;
    }

    public void merge (ImportedFile oppositeFile, DiffBlock originalBlock, DiffBlock targetBlock) {

    }

    public List<String> getText () {
        return text;
    }


}
