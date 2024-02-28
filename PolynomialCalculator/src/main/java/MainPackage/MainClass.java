package MainPackage;

import BusinessLogic.Controller;
import GraphicalUserInterface.Model;
import GraphicalUserInterface.View;

public class MainClass {
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View(model);
		Controller controller = new Controller(model, view);

		view.setVisible(true);

	}
}
