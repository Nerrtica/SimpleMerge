package controller;

import view.ClsView;
import model.ImportedFile;
import java.awt.EventQueue;
import java.util.ArrayList;

public class ClsController {
    ImportedFile leftFile = new ImportedFile();
    ImportedFile rightFile = new ImportedFile();
    ClsView window;

    public ClsController()
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
               show();
            }
        });

    }

    private void show() {
        try
        {
            window = new ClsView(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> load(String fileRoute, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.load(fileRoute);
            return leftFile.getText();
        } else {
            rightFile.load(fileRoute);
            return rightFile.getText();
        }
    }



}
