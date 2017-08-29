package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by javier on 5/9/16.
 */
public class Missil implements Item{
	private int posX;
	private int posY;
	private int dy;

	private Rectangle rectangle;

	public Missil(int posX, int posY) {
		if(dy == 2) {
			this.posY = posY + 16;
		}

		else {
			this.posY = posY;
		}

		this.posX = posX;


		rectangle = new Rectangle( ( this.posX + 11 ), this.posY , 2, 12);
	}

	public Missil(Item item) {
		this(item.getPosX(),item.getPosY());
	}

	public Missil(Item item, int dy) {
		this(item);
		this.dy = dy;
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

	public int getWave() { return 0; }

	@Override
	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void translate(int dx, int dy) {
		rectangle.translate(dx, dy);
	}
	@Override
	public Vector<Rectangle> getRectangleList() {
		return null;
	}
}
