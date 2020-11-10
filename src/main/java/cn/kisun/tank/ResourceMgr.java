package cn.kisun.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 季海龙
 */
public class ResourceMgr {
	public static BufferedImage goodTankL, goodTankU, goodTankR, goodTankD; 
	public static BufferedImage badTankL, badTankU, badTankR, badTankD; 
	public static BufferedImage bulletL, bulletU, bulletR, bulletD; 
	public static BufferedImage[] explodes = new BufferedImage[16];

	static {
		try {
			goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
			goodTankL = ImageUtil.rotateImage(goodTankU, -90);
			goodTankR = ImageUtil.rotateImage(goodTankU, 90);
			goodTankD = ImageUtil.rotateImage(goodTankU, 180);

			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
			bulletL = ImageUtil.rotateImage(bulletU, -90);
			bulletR = ImageUtil.rotateImage(bulletU, 90);
			bulletD = ImageUtil.rotateImage(bulletU, 180);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}