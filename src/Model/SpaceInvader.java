package Model;

import java.awt.*;
import java.util.Vector;

/**
 * Created by javier on 5/2/16.
 */
public class SpaceInvader implements Item {
	private int posX;
	private int posY;
	private int wave;

	private Vector<Rectangle> RectangleVector;

	public SpaceInvader(int posX, int posY) {

		this.posX = posX;
		this.posY = posY;

		RectangleVector = new Vector<>();
	}

	public SpaceInvader(int posX, int posY, int wave) {
		this(posX,posY);

		if(wave % 3 == 0) {
			initSpaceInvaderViewFirstWave();
			this.wave = 1;
		}
		else if(wave % 5 == 0) {
			initSpaceInvaderViewSecondWave();
			this.wave = 2;
		}
		else if(wave % 7 == 0) {
			initSpaceInvaderViewThirdWave();
			this.wave = 3;
		}
		else {
			initSpaceInvaderViewFirstWave();
		}
	}

	public SpaceInvader(Item item) {
		this(item.getPosX(), item.getPosY());
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWave() {
		return wave;
	}

	public void initSpaceInvaderViewFirstWave() {
		// x = 0
		RectangleVector.add(new Rectangle( (posX + 4) , posY, 2, 2 ));
		RectangleVector.add(new Rectangle( (posX + ( 24 - 6 )) , posY, 2, 2 ));

		// x = 1
		RectangleVector.add(new Rectangle( ( posX + 6 ) , ( posY + 2 ) , 2, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 24 - 8 ) ) , ( posY + 2 ) , 2, 2 ));

		// x = 2
		RectangleVector.add(new Rectangle( ( posX + ( 4) ) , ( posY + 4 ) , 16, 2 ));

		// x = 3
		RectangleVector.add(new Rectangle( ( posX + ( 2 ) ) , ( posY + 6 ) , 4, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 8 ) ) , ( posY + 6 ) , 8, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 24 - 6 ) ) , ( posY + 6 ) , 4, 2 ));

		// x = 4
		RectangleVector.add(new Rectangle( ( posX + ( 0 ) ) , ( posY + 8 ) , 24, 2 ));

		// x = 5
		RectangleVector.add(new Rectangle( ( posX + ( 0 ) ) , ( posY + 10 ) , 4, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 4 ) ) , ( posY + 10 ) , 16, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 24 - 2 ) ) , ( posY + 10 ) , 2, 2 ));

		// x = 6
		RectangleVector.add(new Rectangle( ( posX + ( 0 ) ) , ( posY + 12 ) , 2, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 4 ) ) , ( posY + 12 ) , 2, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 24 - 6 ) ) , ( posY + 12 ) , 2, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 24 - 2 ) ) , ( posY + 12 ) , 2, 2 ));

		// x = 6
		RectangleVector.add(new Rectangle( ( posX + ( 6 ) ) , ( posY + 14 ) , 5, 2 ));
		RectangleVector.add(new Rectangle( ( posX + ( 24 - 11 ) ) , ( posY + 14 ) , 5, 2 ));
	}

	public void initSpaceInvaderViewSecondWave() {
		// y = 0
		RectangleVector.add(new Rectangle( (posX + 8) , posY, 8, 2 ));

		// y = 1
		RectangleVector.add(new Rectangle( (posX + 2) , (posY + 2) , 20, 2 ));

		// y = 2
		RectangleVector.add(new Rectangle( (posX + 0) , (posY + 4), 24, 2 ));

		// y = 3
		RectangleVector.add(new Rectangle( (posX + 0) , (posY + 6), 6, 2 ));
		RectangleVector.add(new Rectangle( (posX + 10) , (posY + 6), 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 18) , (posY + 6), 6, 2 ));

		// y = 4
		RectangleVector.add(new Rectangle( (posX + 0) , (posY + 8), 24, 2 ));

		// y = 5
		RectangleVector.add(new Rectangle( (posX + 6) , (posY + 10), 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 14) , (posY + 10), 4, 2 ));

		// y = 6

		RectangleVector.add(new Rectangle( (posX + 4) , (posY + 12), 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 10) , (posY + 12), 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 16) , (posY + 12), 4, 2 ));

		// y = 7

		RectangleVector.add(new Rectangle( (posX + 0) , (posY + 14), 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 20) , (posY + 14), 4, 2 ));

	}

	public void initSpaceInvaderViewThirdWave() {

		// y = 0
		RectangleVector.add(new Rectangle( (posX + 10) , posY, 12, 2 ));

		// y = 1
		RectangleVector.add(new Rectangle( (posX + 6) , (posY + 2) , 20, 2 ));

		// y = 2
		RectangleVector.add(new Rectangle( (posX + 4) , (posY + 4) , 24, 2 ));

		// y = 3
		RectangleVector.add(new Rectangle( (posX + 2) , (posY + 6) , 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 8) , (posY + 6) , 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 14) , (posY + 6) , 4, 2 ));

		RectangleVector.add(new Rectangle( (posX + 20) , (posY + 6) , 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 26) , (posY + 6) , 4, 2 ));

		// y = 4
		RectangleVector.add(new Rectangle( (posX + 0) , (posY + 8) , 32, 2 ));

		// y = 5
		RectangleVector.add(new Rectangle( (posX + 4) , (posY + 10) , 6, 2 ));
		RectangleVector.add(new Rectangle( (posX + 14) , (posY + 10) , 4, 2 ));
		RectangleVector.add(new Rectangle( (posX + 22) , (posY + 10) , 6, 2 ));

		// y = 6

		RectangleVector.add(new Rectangle( (posX + 6) , (posY + 12) , 2, 2 ));
		RectangleVector.add(new Rectangle( (posX + 24) , (posY + 12) , 2, 2 ));


	}

	public Vector<Rectangle> getRectangleList() {
		return RectangleVector;
	}

	@Override
	public Rectangle getRectangle() {
		return null;
	}

	public void translate(int dx, int dy) {
		for (Rectangle rectangle : RectangleVector) {
			rectangle.translate(dx, dy);
		}
	}

}
