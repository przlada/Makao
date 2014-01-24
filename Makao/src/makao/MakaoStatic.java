package makao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import makao.model.TextMessage;
import makao.model.TextMessage.Type;

public final class MakaoStatic{
	public static final Dimension MAIN_FRAME_SIZE = new Dimension(800, 600);
	public static final Dimension CARD_SIZE = new Dimension(73, 98);
	public static final Color BACKGROUND_GREEN = new Color(12,176,50);
	public static final Image CARD_DOCK_BACKGROUND_IMAGE;
	public static final ImageIcon CARD_BACK;
	public static final ImageIcon LOAD_IMAGE;
	public static final ImageIcon[][] CARD_ICONS;
	public static final int MIN_PLAYERS = 2;
	public static final int PORT_NUMBER = 4321;
	public static final int MAX_PLAYERS = 4;
	public static final int CARD_NUMBERS = 13;
	public static final int CARD_COLORS = 4;
	public static final int CARD_HAND_START = 5;
	
	public static final TextMessage notEnoughPlayers = new TextMessage(Type.SERVER_MESSAGE, null, "Za ma∏o graczy by rozpoczàç gr´");
	public static final String nextRound ="Kolejk´ rozpoczyna nast´pny gracz";
	static{
		CARD_ICONS = new ImageIcon[4][13];
		File imageFile = new File("images/card.png");
		File cardsFile = new File("images/cards.png");
		File loadFile = new File("images/loader.gif");
		BufferedImage img = null;
		ImageIcon image = null;
		ImageIcon load = null;
		Image bg = null;
		try {
		    image = new ImageIcon(ImageIO.read(imageFile));
		    load = new ImageIcon(ImageIO.read(loadFile));
		    img = ImageIO.read(cardsFile);
		    bg = ImageIO.read(new File("images/wood_bg.jpg"));
		    for(int i=0; i<4; i++){
		    	for(int j=0; j<13; j++){
		    		CARD_ICONS[i][j] = new ImageIcon(img.getSubimage(j*73, i*98, 73, 98));
		    	}
		    }
		} catch (IOException ex) {}
		finally{
			CARD_BACK = image;
			CARD_DOCK_BACKGROUND_IMAGE = bg;
			LOAD_IMAGE = load;
		}
	}
	 
	
}
