package controller;

import view.ClsView;
import java.awt.EventQueue;

public class ClsController {


    public void ClsController()
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    ClsView window = new ClsView();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
