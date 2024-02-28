package GraphicalUserInterface;

public class Model {
	private String result;
	
	public Model() {
		result = "";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		if (!result.equals("")) {
			this.result = result;
		}else {
			this.result = "0";
		}
	}
}
