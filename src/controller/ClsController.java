package controller;

import view.ClsView;
import java.awt.EventQueue;

public class ClsController {

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
            ClsView window = new ClsView(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
