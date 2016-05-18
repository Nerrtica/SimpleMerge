package controller;

import view.ClsView;
import model.ImportedFile;
import model.DiffBlock;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;


public class ClsController {
    ImportedFile leftFile = new ImportedFile();
    ImportedFile rightFile = new ImportedFile();
    ClsView window;

    public ClsController ()
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
               show();
            }
        });

    }

    private void show () {
        try
        {
            window = new ClsView(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> load (String fileRoute, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.load(fileRoute);
            return leftFile.getText();
        } else {
            rightFile.load(fileRoute);
            return rightFile.getText();
        }
    }

    public void save (boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.save();
        } else {
            rightFile.save();
        }
    }

    public void saveAs (String fileRoute, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.saveAs(fileRoute);
        } else {
            rightFile.saveAs(fileRoute);
        }
    }

    public void edit (String document, ArrayList<Boolean> bool, boolean isLeftFile) {
        if (isLeftFile) {
            leftFile.convert(document, bool);
        } else {
            rightFile.convert(document, bool);
        }
    }

    public ArrayList<DiffBlock> compare (boolean isLeftFile) {
        if (isLeftFile) {
            return leftFile.compare(rightFile);
        } else {
            return rightFile.compare(leftFile);
        }
    }

    public void merge (List<DiffBlock> originalBlockList, List<DiffBlock> targetBlockList, int blockIndex, boolean isLeftfile) {
        if (isLeftfile) {
            leftFile.merge(rightFile, originalBlockList, targetBlockList, blockIndex);
        } else {
            rightFile.merge(leftFile, originalBlockList, targetBlockList, blockIndex);
        }
    }

}
