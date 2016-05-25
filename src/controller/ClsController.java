package controller;

import view.ClsView;
import model.ClsModel;
import java.awt.EventQueue;
import java.util.ArrayList;

public class ClsController {
    ClsView view;
    ClsModel model;

    public ClsController (ClsModel model)
    {
        this.model = model;

        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
               show();
            }
        });

    }

    private void show ()
    {
        try
        {
        	view = new ClsView(this, model);
        }
        catch (Exception e)
        {
        	System.out.println("Highest Exception catcher.");
            e.printStackTrace();
        }
    }

    public void load (String fileRoute, boolean isLeftFile)
    {
        model.load(fileRoute, isLeftFile);
        view.setText(getTextList(isLeftFile), isLeftFile);
        view.setText(getTextList(!isLeftFile), !isLeftFile);
    }
    
    public ArrayList<String> getTextList(boolean isLeftFile)
    {
    	if(isLeftFile)
    		return model.getLeftFileText();
    	else
    		return model.getRightFileText();
    }

    public void save (boolean isLeftFile) {
        model.save(isLeftFile);
    }

    public void saveAs (String fileRoute, boolean isLeftFile) {
        model.saveAs(fileRoute, isLeftFile);
    }

    public void edit (String document, ArrayList<Boolean> isTrueLineList, boolean isLeftFile) {
        model.edit(document, isTrueLineList, isLeftFile);
    }

    public void compare () {
        model.compare();

    }

    public void merge (int blockIndex, boolean isRightToLeft) {
        model.merge(blockIndex, isRightToLeft);
    }
    
    public void mergeAll (boolean isRightToLeft) {
        model.mergeAll(isRightToLeft);
    }
}
