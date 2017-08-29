package Model;

import java.io.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by javier on 5/2/16.
 */
public class GameModel {
	Vector<Item> armyGrid;
	Vector<Item> missilEnemyGrid;
	Vector<Item> missilDefenderGrid;

	Item defender;

	public GameModel() {
		defender = new SpaceDefender(288, 720-168);
	}


	public void initArmy(int filas) {

		for(int i = 1; i <= filas; i++) {
			for(int j=0;j<8;j++) {
				armyGrid.add(new SpaceInvader( ( ( j * 64 ) + 64) ,( -1 * ( ( i-1 ) * 48 ) + 24 ), 2) );
			}
		}
	}

	public void increaseArmy(int filas, int wave) {
		int dy = armyGrid.get(armyGrid.size()-1).getPosY();
		dy -= 48;

		for(int i = 1 ; i <= filas ; i++) {
			for(int j = 0 ; j < 8 ; j++) {
				armyGrid.add(new SpaceInvader( ( ( j * 64 ) + 64) , dy, wave) );
			}
		}

	}

	public void addEnemyMissil(Item item, int dy) {
		missilEnemyGrid.add(new Missil(item, dy));
	}

	public void addFriendlyMissil(Item item) {
		missilDefenderGrid.add(new Missil(item, -1));
	}

	public Vector<Item> getArmyGrid() {
		return armyGrid;
	}

	public Vector<Item> getMissilEnemyGrid() {
		return missilEnemyGrid;
	}

	public Vector<Item> getMissilDefenderGrid() {
		return missilDefenderGrid;
	}

	public void setArmyGrid(Vector<Item> armyGrid) {
		this.armyGrid = armyGrid;
	}

	public void setMissilEnemyGrid(Vector<Item> missilEnemyGrid) {
		this.missilEnemyGrid = missilEnemyGrid;
	}

	public void setMissilDefenderGrid(Vector<Item> missilDefenderGrid) {
		this.missilDefenderGrid = missilDefenderGrid;
	}

	public void moveDefender(int dx) {
		int posX = defender.getPosX();

		if( ( posX + dx ) < 40 || (posX + dx) > (600 - 40 - 24) ) {
			dx = 0;
		}
		posX += dx;
		defender.setPosX( posX );
		defender.translate(dx,0);
	}

	public Item getDefender() {
		return defender;
	}

}
