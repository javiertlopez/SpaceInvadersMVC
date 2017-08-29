package Model;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

/**
 * Created by javier on 5/2/16.
 */
public interface Item extends Serializable {
	int getPosX();
	int getPosY();
	int getWave();
	void setPosX(int posX);
	void setPosY(int posY);
	void translate(int dx, int dy);
	Vector<Rectangle> getRectangleList();
	Rectangle getRectangle();
}
