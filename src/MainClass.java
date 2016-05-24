import controller.ClsController;
import model.ClsModel;

public class MainClass {
	public static void main (String args[]) {
        ClsModel model = new ClsModel();
        ClsController controller = new ClsController(model);
    }
}
