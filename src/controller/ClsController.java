package controller;

import view.ClsView;
import model.ClsModel;
import java.awt.EventQueue;
import java.util.ArrayList;


public class ClsController {
    ClsView window;
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
        model.load(fileRoute, isLeftFile);
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
    
    public void mergeAll (boolean isRightToLeft)
    {
        model.mergeAll(isRightToLeft);
    }

    //return value : Target Block Line # - Original Block Line #
    public int getBlockLineDiff (int blockIndex, boolean isLeftFile) {
        return model.getBlockLineDiff(blockIndex, isLeftFile);
    }

    //left file의 text를 return해줌
    public ArrayList<String> getLeftFileText () {
        return model.getLeftFile().getText();
    }

    //right file의 text를 return해줌
    public ArrayList<String> getRightFileText () {
        return model.getRightFile().getText();
    }

    //왼쪽 diffBlockList에서 특정 blockIndex의 startIndex를 return해줌
    public int getLeftBlockStartIndex (int blockIndex) {
        return model.getLeftDiffBlockList().get(blockIndex).getStartIndex();
    }

    //왼쪽 diffBlockList에서 특정 blockIndex의 endIndex를 return해줌
    public int getLeftBlockEndIndex (int blockIndex) {
        return model.getLeftDiffBlockList().get(blockIndex).getEndIndex();
    }
    
}
