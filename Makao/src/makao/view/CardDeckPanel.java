package makao.view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class CardDeckPanel extends JPanel{
	private Image bg = null;
	public CardDeckPanel(){
		super();
	}
	
	public Image getBackgroundImage() {
		return bg;
	}

	public void setBackgroundImage(Image bg) {
		this.bg = bg;
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg!=null) {
            int iw = bg.getWidth(this);
            int ih = bg.getHeight(this);
            if (iw > 0 && ih > 0) {
                for (int x = 0; x < getWidth(); x += iw) {
                    for (int y = 0; y < getHeight(); y += ih) {
                        g.drawImage(bg, x, y, iw, ih, this);
                    }
                }
            }
        }
    }
}
