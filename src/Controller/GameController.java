package Controller;

import Model.GameModel;
import Model.Item;
import View.GameView;

import java.awt.*;
import java.awt.List;
import java.io.*;
import java.util.*;

/**
 * Created by javier on 5/2/16.
 */

public class GameController {
	GameView gameView;
	GameModel gameModel;

	Thread armyThread;

	Rectangle rectanglePONR = new Rectangle(new Point(0,532), new Dimension(600,2));

	int sectorX = 0;
	int fila = 0;
	int wave = 2;
	int lifes = 3;
	int score = 0;

	boolean pause = false;
	boolean gameOver = false;
	boolean savedGame;
	boolean newGame;


	public GameController(GameView gameView, GameModel gameModel) {
		this.gameView = gameView;
		this.gameModel = gameModel;
	}

	public void initGame() {
		gameView.setGameController(this);
		getSavedGame();
		if(!getSavedParty()) {
			gameModel.setArmyGrid(new Vector<>());
			gameModel.setMissilEnemyGrid(new Vector<>());
			gameModel.setMissilDefenderGrid(new Vector<>());
			gameModel.initArmy(2);

			newGame = true;
			savedGame = false;

		} else {
			newGame = false;
			savedGame = true;
		}
	}

	public void setDefenderDx(int dx) {
		if(!pause) {
			gameModel.moveDefender(dx * 12);
		}
	}

	public synchronized void moveArmy() {
		int dx=0;
		int dy=0;
		int posX;
		int posY;

		if(sectorX < 6) {
			dx = 2;
		}
		else if(sectorX < 12)
		{
			dy = 2;
		}
		else if(sectorX < 18) {
			dx = -2;
		}
		else if(sectorX < 24) {
			dx = -2;
		}
		else if(sectorX < 30) {
			dy = 2;

		}
		else {
			dx = 2;
		}


		for (Item item : gameModel.getArmyGrid())
		{
			posX = item.getPosX();
			posX += dx;

			posY = item.getPosY();
			posY += dy;


			if(item.getWave() == 2) {
				item.setPosX( posX * -1 );
				item.translate( (dx * -1) , dy);
			}
			else if(item.getWave() == 3) {
				item.setPosX( posX * -1 );
				item.translate( (dx * -1) , dy);
			}
			else {
				item.setPosX( posX );
				item.translate( dx , dy);
			}

			item.setPosY(posY);

		}

		if( ( sectorX + 1 ) < 36 )
		{
			sectorX++;
		}

		else
		{
			sectorX = 0;
			fila++;
		}

		if(fila>wave) {

			gameModel.increaseArmy(1, wave);
			wave++;

		}
	}

	public synchronized void moveMissils() {
		int dy;
		int posY;

		for (Item item : gameModel.getMissilDefenderGrid())
		{

			dy = -4;
			posY = item.getPosY();
			posY += dy;

			item.setPosY(posY);
			item.translate(0,dy);
		}

		for (Item item : gameModel.getMissilEnemyGrid()) {
			dy = 2;
			posY = item.getPosY();
			posY += dy;

			item.setPosY(posY);
			item.translate(0,dy);
		}
	}

	public void setPause() {
		if(pause) {
			pause = false;
		}
		else {
			pause = true;
		}
	}

	public void fireFriendlyMissil() {
		if(!pause) {
			gameModel.addFriendlyMissil(gameModel.getDefender());
		}
	}

	public void fireAtWill() {
		int intRandom;

		Random random = new Random();

		intRandom = random.nextInt(gameModel.getArmyGrid().size() - 8);

		gameModel.addEnemyMissil(gameModel.getArmyGrid().get(intRandom),2);

		System.out.println("Disparo el malo");
	}

	public void checkPONR() {
		if(lifes<1) armyThread.interrupt();
	}

	public void checkMissilDefenderCollision() {
		for(int i=0; i < getArmyGrid().size(); i++) {
			for(int j = 0; j < getMissilDefenderGrid().size(); j++) {
				for (Rectangle rectangle : getArmyGrid().get(i).getRectangleList()) {
 					if(rectangle.intersects(getMissilDefenderGrid().get(j).getRectangle())) {
						getArmyGrid().remove(i);
						getMissilDefenderGrid().remove(j);


						if((j-1) < 0 ) j=0;
						else j--;

						if((i-1) < 0 ) i=0;
						else i--;

					    score += 10;
						continue;
					}
				}
			}
		}
	}

	public void checkMissilArmyCollision() {
		for(int i = 0; i < getDefender().getRectangleList().size(); i++) {
			for(int j = 0; j<getMissilEnemyGrid().size(); j++) {
				if(getDefender().getRectangleList().get(i).intersects(
						getMissilEnemyGrid().get(j).getRectangle())
						) {
					getMissilEnemyGrid().remove(j);
					if((j-1) < 0 ) j=0;
					else j--;

					lifes--;

					System.out.println("Mori");

					continue;
				}
			}
		}
	}

	public void checkArmyPosition() {
		for (Item item : getArmyGrid()) {
			for (Rectangle rectangle : item.getRectangleList()) {
				if(rectanglePONR.intersects(rectangle))  {
					armyThread.interrupt();
					System.out.println("Barrera PONR");
				}
			}
		}
	}

	public Item getDefender() {
		return gameModel.getDefender();
	}

	public Vector<Item> getArmyGrid() {
		return gameModel.getArmyGrid();
	}

	public Vector<Item> getMissilEnemyGrid() {
		return gameModel.getMissilEnemyGrid();
	}

	public Vector<Item> getMissilDefenderGrid() {
		return gameModel.getMissilDefenderGrid();
	}

	public int getScore() {
		return score;
	}

	public int getLifes() {
		return lifes;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean isSavedGame() { return savedGame; }

	public boolean isNewGame() { return newGame; }

	public boolean isPause() {
		return pause;
	}

	public void setSavedGame(boolean savedGame) {
		this.savedGame = savedGame;
	}

	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void getSavedGame() {
		ObjectInputStream objectInputStream = null;

		try {
			FileInputStream streamIn = new FileInputStream("preferenceList.file");
			objectInputStream = new ObjectInputStream(streamIn);

			sectorX = objectInputStream.readInt();
			fila = objectInputStream.readInt();
			wave = objectInputStream.readInt();
			lifes = objectInputStream.readInt();
			score = objectInputStream.readInt();


		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(objectInputStream != null){
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void saveGame() {
			ObjectOutputStream oos = null;
			FileOutputStream fout;
			try{
				fout = new FileOutputStream("preferenceList.file", false);
				oos = new ObjectOutputStream(fout);
				oos.writeInt(sectorX);
				oos.writeInt(fila);
				oos.writeInt(wave);
				oos.writeInt(lifes);
				oos.writeInt(score);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if(oos != null){
					try {
						oos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		saveParty();
	}

	public boolean getSavedParty() {

		ObjectInputStream objectInputStream = null;

		boolean flag = false;

		try {
			FileInputStream streamIn = new FileInputStream("itemList.file");
			objectInputStream = new ObjectInputStream(streamIn);

			java.util.List<Item> readCase = (java.util.List<Item>) objectInputStream.readObject();

			gameModel.setArmyGrid((Vector<Item>)readCase);

			readCase = (java.util.List<Item>) objectInputStream.readObject();

			gameModel.setMissilEnemyGrid((Vector<Item>)readCase);

			readCase = (java.util.List<Item>) objectInputStream.readObject();
			gameModel.setMissilDefenderGrid((Vector<Item>)readCase);

			flag = true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		finally {
			if(objectInputStream != null){
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return flag;
	}

	public void saveParty() {
		ObjectOutputStream oos = null;
		FileOutputStream fout;
		try{
			fout = new FileOutputStream("itemList.file", false);
			oos = new ObjectOutputStream(fout);

			oos.writeObject(getArmyGrid());
			oos.writeObject(getMissilEnemyGrid());
			oos.writeObject(getMissilDefenderGrid());

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(oos  != null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized void flushGame() {
		sectorX = 0;
		fila = 0;
		wave = 2;
		lifes = 3;
		score = 0;
		pause = false;

		gameModel.getArmyGrid().clear();
		gameModel.getMissilDefenderGrid().clear();
		gameModel.getMissilEnemyGrid().clear();
		gameModel.initArmy(2);

		saveGame();
	}

	public void initArmyThread() {
		armyThread = new Thread(new ArmyThread());
		armyThread.start();
	}

	class ArmyThread implements Runnable {

		@Override
		public void run() {
			int localWave = 0;
			int localTime = 0;
			long time = 64;
			while(true) {
				try {
					Thread.sleep(time);

					if(!pause) {

						System.out.println("Mueve");
						checkPONR();
						moveArmy();
						moveMissils();

						checkArmyPosition();
						checkMissilDefenderCollision();
						checkMissilArmyCollision();
						if (Thread.currentThread().isInterrupted()) {
							setGameOver(true);
							gameView.updateCanvas();
							flushGame();
							break;
						}

						if(localTime % (time*3/wave) == 0) fireAtWill();

						if( ( wave > 1 && localWave!=wave ) && wave % 16 == 0 && time > 16) {
							System.out.println("Time goes by so slowly");
							time -= 4;
						}
						localTime++;
						localWave = wave;
					}
					gameView.updateCanvas();
				}catch (InterruptedException iex) {

				}
			}

		}
	}
}
