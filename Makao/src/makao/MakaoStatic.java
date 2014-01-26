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
	
	public static final String defoltPlayerName = "GRACZ";
	public static final TextMessage notEnoughPlayers = new TextMessage(Type.SERVER_MESSAGE, null, "Za mało graczy by rozpocząć grę");
	public static final String nextRound ="Rozpoczyna następną kolejkę";
	public static final String newUserMsg = "Do gry dołączył/a";
	public static final String cantGetAfterChoose = "Nie można dobrać po pobraniu karty";
	public static final String notGoodCard = "Nie można dołożyć tej karty";
	public static final String nextPlayerGet = "Następny gracz pobiera";
	public static final String nextPlayerWait = "Następny gracz czeka";
	public static final String notYourTurn = "Teraz nie twoja kolej";
	public static final String endOfTheGameYouWin = "KONIEC GRY\nWYGRYWASZ";
	public static final String requestColor = "Żądam koloru: ";
	public static final String requestNumber = "Żądam ";
	public static final String noRequest = "Nic nie żądam"; 
	public static final String notThatNumber = "Żądana jest inna figura";
	public static final String notThatColor = "Żądany jest inny color";
	public static final String cardAlredyServerd = "Karta już wyłożona";
	public static final String endToWait = "Nie pobierasz. Zakończ kolejkę żeby czekać ";
	public static final String cardAlreadyGet = "Karta już pobrana";
	public static final String rememberToMakao = "Nie powiedziałaś/eś MAKAO\nCiągniesz 5 kart";
	public static final String moreCardToGet = "Musisz pobrać jeszcze ";
	public static final String userBlocked = "Jesteś zablokowana/y"; 
	public static final String sayMakao = "Mówi MAKAO";
	public static final String enyCardOutOrMoreToGet = "Nie wystawiłaś/eś albo nie pobrałaś/eś karty";
	public static final String serverConnectioLost = "Przerwano połączenie z serwerem";
	
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
