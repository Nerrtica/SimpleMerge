package model;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Created by Nerrtica on 2016. 5. 10..
 */
public class ImportedFile {
    private List<String> text = new ArrayList<String>();
    private String fileRoute;

    public ImportedFile (String fileRoute){
    	this.fileRoute = fileRoute;	//fileRoute 저장
    
    	try{
    		BufferedReader br = new BufferedReader(new FileReader(fileRoute));	//파일 reader	
    	
	    	String line;	//각 문자열을 임시로 저장할 String
	    	
	    	while(true){
	    		line = br.readLine();
	    		if(line == null)	//더 이상 읽어올 문자열 없으면 break;
	    			break;
	    		text.add(line);	//text에 문자열 추가
	    	}
	    	
	    	br.close();
    	}
    	catch(FileNotFoundException e){
    		System.out.println("파일을 찾을 수 없습니다.");
    	}
    	catch(IOException e){
    		System.out.println("입출력 상황에서 에러가 발생하였습니다.");
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

    void modify (List<String> modifiedText) {

    }

    returnType compare (ImportedFile oppositeFile) {

    }

    returnType merge (parameter) {

    }

}