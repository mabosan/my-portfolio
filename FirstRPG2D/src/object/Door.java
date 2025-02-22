package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Door extends SuperObject{

	public Door() {
		
		objectName = "Door";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
