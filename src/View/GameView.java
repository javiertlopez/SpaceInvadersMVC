package View;

import Controller.GameController;
import Model.Item;
import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by javier on 5/2/16.
 */

public class GameView extends Frame implements KeyListener{
	private Color colorBG;
	private Color colorBGCanvas;
	private Color fontColor;
	private Panel controlPanel;
	private Panel footerPanel;
	private MyCanvas canvas;
	private Dimension frameDimension;
	private Dimension canvasDimension;
	private Dimension screenDimension;
	private Dimension footerDimension;
	private Font fontCondensed = new Font("DIN Condensed",Font.PLAIN,48);
	private Font fontAlternate = new Font("DIN Alternated",Font.PLAIN,21);

	private Label lbTitle;
	private Label lbScore;
	private Label lbLives;


	private GameController gameController;

	public GameView() {

		initGameView();

	}

	public void initGameView() {

		Toolkit tk = getToolkit();

		colorBG = new Color(0x2B2B2B);
		colorBGCanvas = new Color(0x000000);
		fontColor = new Color(0x2C93D1);

		controlPanel = new Panel(null);
		footerPanel = new Panel(null);

		screenDimension = tk.getScreenSize();
		frameDimension = new Dimension(624,720);
		canvasDimension = new Dimension(600, 600);
		footerDimension = new Dimension(600, 66);
		try {
			canvas = new MyCanvas();
		} catch (IOException e) {
			e.printStackTrace();
		}

		lbTitle = new Label("Space Invaders");
		lbTitle.setFont(fontCondensed);
		lbTitle.setForeground(fontColor);
		lbTitle.setBounds(new Rectangle(new Point(180,0), new Dimension(240,60)));

		lbScore = new Label("Score: ");
		lbScore.setFont(fontAlternate);
		lbScore.setForeground(fontColor);
		lbScore.setBounds(new Rectangle(new Point(0,12), new Dimension(140,21)));


		lbLives = new Label("Lives: ");
		lbLives.setFont(fontAlternate);
		lbLives.setForeground(fontColor);
		lbLives.setBounds(new Rectangle(new Point(520, 12), new Dimension(140,21)));

		footerPanel.setBounds(new Rectangle(new Point(12, 624), footerDimension));

		Application a = Application.getApplication();


		setTitle("Space Invaders");
		addKeyListener(this);
		setBackground(colorBG);
		setFocusable(true);
		setPreferredSize(frameDimension);
		setLocation( ( ( screenDimension.width - frameDimension.width ) / 2 ),
				( (
						screenDimension.height - frameDimension.height ) / 2));
		setResizable(false);


		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				gameController.saveGame();
				System.exit(0);
			}
		});

		a.setQuitHandler(new QuitHandler() {


			@Override
			public void handleQuitRequestWith(com.apple.eawt.AppEvent.QuitEvent qe, com.apple.eawt.QuitResponse qr) {
				// TODO Auto-generated method stub
				gameController.saveGame();
				qr.performQuit();
			}

		});

		controlPanel.add(canvas);
		footerPanel.add(lbTitle);
		footerPanel.add(lbScore);
		footerPanel.add(lbLives);
		controlPanel.add(footerPanel);

		add(controlPanel);
		pack();
		setVisible(true);

	}


	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int intKey = e.getKeyCode();

		if(KeyEvent.VK_LEFT == intKey || KeyEvent.VK_J == intKey) {
			System.out.println("Izquierda");
			gameController.setDefenderDx(-1);
		}

		else if(KeyEvent.VK_RIGHT == intKey || KeyEvent.VK_L == intKey) {
			System.out.println("Derecha");
			gameController.setDefenderDx(1);
		}

		else if(KeyEvent.VK_K == intKey || KeyEvent.VK_UP == intKey) {
			System.out.println("Dispara");
			gameController.fireFriendlyMissil();
		}

		else if(KeyEvent.VK_SPACE == intKey) {
			gameController.setPause();
		}

		else if(KeyEvent.VK_R == intKey) {
			gameController.initArmyThread();
		}
		else if(KeyEvent.VK_N == intKey) {
			gameController.flushGame();
			gameController.initArmyThread();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void updateCanvas() {
		canvas.repaint();
		lbScore.setText("Score: \n"+gameController.getScore());
		lbScore.repaint();
		lbLives.setText("Lifes: "+gameController.getLifes());
	}


	class MyCanvas extends Canvas {

		final BufferedImage gameOverImage = ImageIO.read(getClass().getResource("/View/gameOver.png"));
		final BufferedImage newGame = ImageIO.read(getClass().getResource("/View/newGame.png"));
		final BufferedImage savedGame = ImageIO.read(getClass().getResource("/View/savedGame.png"));
		final BufferedImage pausedGame = ImageIO.read(getClass().getResource("/View/pausedGame.png"));


		public MyCanvas() throws IOException {
			setBackground( colorBGCanvas );
			setPreferredSize( canvasDimension );
			setBounds(new Rectangle(new Point(12,12), getPreferredSize()));
		}

		public void paint(Graphics graphics) {
			Graphics2D itemPainter;
			itemPainter = (Graphics2D) graphics;

			paintMissilGrid(itemPainter);
			paintDefender(itemPainter);
			paintArmy(itemPainter);
			if(gameController.isGameOver()) {
				itemPainter.drawImage(gameOverImage, 0, 225, null);
				gameController.setGameOver(false);
			}
			if(gameController.isNewGame()) {
				itemPainter.drawImage(newGame, 0, 120, null);
				gameController.setNewGame(false);
			}
			else if(gameController.isSavedGame()) {
				itemPainter.drawImage(savedGame, 0, 200, null);
				gameController.setSavedGame(false);
			}
			else if(gameController.isPause()) {
				itemPainter.drawImage(pausedGame, 0, 250, null);
			}
		}


		public void paintDefender(Graphics2D itemPainter) {
			itemPainter.setColor(new Color(0xFFFFFF));

			Iterator<Rectangle> rectangleIterator = gameController.getDefender().getRectangleList().iterator();
			while(rectangleIterator.hasNext()) {
				itemPainter.fill(rectangleIterator.next());
			}
		}

		public synchronized void paintMissilGrid(Graphics2D itemPainter) {
			itemPainter.setColor(new Color(0xFFFFFF));
			Iterator<Item> itemIterator = gameController.getMissilDefenderGrid().iterator();
			while(itemIterator.hasNext()) {
				itemPainter.fill(itemIterator.next().getRectangle());
			}

			itemPainter.setColor(new Color(0xFF3A5F));
			itemIterator = gameController.getMissilEnemyGrid().iterator();
			while(itemIterator.hasNext()) {
				itemPainter.fill(itemIterator.next().getRectangle());
			}
		}

		public synchronized void paintArmy(Graphics2D itemPainter) {

			itemPainter.setColor(new Color(0x5FFF41));

			Iterator<Item> itemIterator = gameController.getArmyGrid().iterator();
			Iterator<Rectangle> rectangleIterator;

			while(itemIterator.hasNext()) {
				rectangleIterator = itemIterator.next().getRectangleList().iterator();
				while(rectangleIterator.hasNext()) {
					itemPainter.fill(rectangleIterator.next());
				}
			}
		}
	}
}
