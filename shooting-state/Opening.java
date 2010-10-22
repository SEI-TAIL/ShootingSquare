package shooting;

public class Opening {
	enum Menu {
		START, EXIT
	}
	Menu cursole;
	
	Opening() {
		this.cursole = Menu.START;
	}
}
