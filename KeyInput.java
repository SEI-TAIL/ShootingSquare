import java.awt.event.KeyEvent;


public class KeyInput {
	private boolean isUp = false;
	private boolean isDown = false;
	private boolean isLeft = false;
	private boolean isRight = false;
	private boolean isZ = false;
	private boolean isEsc = false;
	
	KeyInput(){}
	
	public void Pressed(int KeyCode) {
		switch (KeyCode) {
		case KeyEvent.VK_UP :
			isUp = true;
			break;
		case KeyEvent.VK_DOWN :
			isDown = true;
			break;
		case KeyEvent.VK_LEFT :
			isLeft = true;
			break;
		case KeyEvent.VK_RIGHT :
			isRight = true;
			break;
		case KeyEvent.VK_Z :
			isZ = true;
			break;
		case KeyEvent.VK_ESCAPE :
			isEsc = true;
			break;
		}
	}
	
	public void Released(int KeyCode) {
		switch (KeyCode) {
		case KeyEvent.VK_UP :
			isUp = false;
			break;
		case KeyEvent.VK_DOWN :
			isDown = false;
			break;
		case KeyEvent.VK_LEFT :
			isLeft = false;
			break;
		case KeyEvent.VK_RIGHT :
			isRight = false;
			break;
		case KeyEvent.VK_Z :
			isZ = false;
			break;
		}
	}
	
	public boolean isUp() {
		return isUp;
	}
	public boolean isDown() {
		return isDown;
	}
	public boolean isLeft() {
		return isLeft;
	}
	public boolean isRight() {
		return isRight;
	}
	public boolean isZ() {
		return isZ;
	}
	public boolean isEsc() {
		return isEsc;
	}
	
}
