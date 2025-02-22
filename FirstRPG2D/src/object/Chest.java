package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Chest extends SuperObject{

	public Chest() {
		
		objectName = "Chest";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
