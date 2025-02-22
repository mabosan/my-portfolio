package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ResourceManager {
	private static Map<String, BufferedImage> imageCache = new HashMap<>();

	public static BufferedImage getImage(String path) {
		if (!imageCache.containsKey(path)) {
			try {
				BufferedImage image = ImageIO.read(ResourceManager.class.getResourceAsStream(path));
				imageCache.put(path, image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imageCache.get(path);
	}
}
