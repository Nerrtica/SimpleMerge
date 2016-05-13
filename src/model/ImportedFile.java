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

    public ImportedFile (String fileRoute) throws FileNotFoundException, IOException {
    	this.fileRoute = fileRoute;	//fileRoute 저장
    
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

    public void save () throws FileNotFoundException {
    	PrintWriter pw = new PrintWriter(fileRoute);
    	
    	for(int i = 0 ; i<text.size() ; i++){
    		pw.println(text.get(i));
    	}
    	
    	pw.close();
    }

    void modify (List<String> modifiedText) {

    }

    returnType compare (ImportedFile oppositeFile) {

    }

    returnType merge (parameter) {

    }

}