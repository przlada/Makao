package makao;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class MakaoStatic{
	public static final Dimension MAIN_FRAME_SIZE = new Dimension(800, 600);
	public static final ImageIcon CARD_BACK;
	public static final ImageIcon[][] CARD_ICONS;
	static{
		CARD_ICONS = new ImageIcon[4][13];
		File imageFile = new File("images/card.png");
		File cardsFile = new File("images/cards.png");
		BufferedImage img = null;
		ImageIcon image = null;
		try {
		    image = new ImageIcon(ImageIO.read(imageFile));
		    img = ImageIO.read(cardsFile);
		    for(int i=0; i<4; i++){
		    	for(int j=0; j<13; j++){
		    		CARD_ICONS[i][j] = new ImageIcon(img.getSubimage(j*73, i*98, 73, 98));
		    	}
		    }
		} catch (IOException ex) {}
		finally{
			CARD_BACK = image;
		}
	}
	 
	
}
