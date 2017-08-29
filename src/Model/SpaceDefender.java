package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by javier on 5/2/16.
 */
public class SpaceDefender implements Item {
	int posX;
	int posY;


	public SpaceDefender(int posX, int posY) {

		this.posX = posX;
		this.posY = posY;

		RectangleVector = new Vector<>();
		// x = 0
		RectangleVector.add(new Rectangle( (posX + 11) , posY, 2, 6 ));

		RectangleVector.add(new Rectangle( (posX + 4) , posY + 6, 16, 2 ));

		RectangleVector.add(new Rectangle( (posX + 2) , posY + 8, 20, 2 ));


		RectangleVector.add(new Rectangle( (posX) , (posY + 10), 24, 6 ));

	}


	@Override
	public int getPosX() {
		return posX;
	}

	@Override
	public void setPosX(int posX) {
		this.posX = posX;
	}

	@Override
	public int getPosY() {
		return posY;
	}

	@Override
	public void setPosY(int posY) {
		this.posY = posY;
	}

	Vector<Rectangle> RectangleVector;

	public Vector<Rectangle> getRectangleList() {
		return RectangleVector;
	}

	@Override
	public Rectangle getRectangle() {
		return null;
	}

	public int getWave() { return 0; }

	public void translate(int dx, int dy) {
		for (Rectangle rectangle : RectangleVector) {
			rectangle.translate(dx, dy);
		}
	}


}
