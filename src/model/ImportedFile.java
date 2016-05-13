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
    	this.fileRoute = fileRoute;	//fileRoute ����
    
    	try{
    		BufferedReader br = new BufferedReader(new FileReader(fileRoute));	//���� reader	
    	
	    	String line;	//�� ���ڿ��� �ӽ÷� ������ String
	    	
	    	while(true){
	    		line = br.readLine();
	    		if(line == null)	//�� �̻� �о�� ���ڿ� ������ break;
	    			break;
	    		text.add(line);	//text�� ���ڿ� �߰�
	    	}
	    	
	    	br.close();
    	}
    	catch(FileNotFoundException e){
    		System.out.println("������ ã�� �� �����ϴ�.");
    	}
    	catch(IOException e){
    		System.out.println("����� ��Ȳ���� ������ �߻��Ͽ����ϴ�.");
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
    		System.out.println("������ ã�� �� �����ϴ�.");
    	}
    }

    void modify (List<String> modifiedText) {

    }

    returnType compare (ImportedFile oppositeFile) {

    }

    returnType merge (parameter) {

    }

}