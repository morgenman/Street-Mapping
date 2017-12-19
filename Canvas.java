import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.Timer;

public class Canvas extends JComponent {
	private Graph			g1;																		//New instance of graph Class
	private float			minX	= 0f, minY = 100f;
	private float			maxX	= -100.0f, maxY = 0.0f;
	int						width	= 1920;
	int						height	= 1080;
	private BufferedImage	i		= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	private static final long serialVersionUID = -123380675811003418L;

	public Canvas(String in) {
		g1 = new Graph(in); //Pass through text file name (if its in a folder, pass through that too (eg "src/ur.txt")
		addComponentListener(new ComponentAdapter() {
			// Resize map as needed
			@Override
			public void componentResized(ComponentEvent e) {
				update();
				draw();
				repaint();
			}
		});

	}

	public void plot(String in) {
		g1.path(in); //pass through
	}

	private void update() {
		//set maximum and minimum points for all the coordinates
		if (minX == 0) {
			for (Node i : g1.getIntersections()) {
				if (i.getCord1() < minX) minX = i.getCord1();
				if (i.getCord1() > maxX) maxX = i.getCord1();
				if (i.getCord2() < minY) minY = i.getCord2();
				if (i.getCord2() > maxY) maxY = i.getCord2();
			}
		}
		//update window coordinates
		for (Node i : g1.getIntersections()) {
			i.setPoint(coord(i.getCord1(), i.getCord2(), minX, minY, maxX, maxY, getWidth(), getHeight()));
		}

	}

	private void draw() {
		//draw the map to an image file. 
		Graphics g = i.getGraphics();
		g.setColor(new Color(235, 235, 235));
		g.fillRect(0, 0, width, height); //background
		Graphics2D g2 = (Graphics2D) g;
		//for every intersection, get the color and draw the point. if drawwing the path (color is preset), make a bigger dot
		for (Node i : g1.getIntersections()) {
			g.setColor(i.getC());
			if (g.getColor().equals(Color.green) || g.getColor().equals(Color.BLUE)) g.fillOval(i.getPoint().x - 3, i
			        .getPoint().y - 3, 6, 6); //bigger
			else g.fillOval(i.getPoint().x, i.getPoint().y, 2, 2);
		}
		//for every road, draw it, unless its on the direction path, in which case make the strok bigger.
		for (Edge i : g1.getRoads()) {
			g.setColor(Color.DARK_GRAY);
			g2.setStroke(new BasicStroke(1));
			if (!i.isTraveled()) g.drawLine(i.getNode1().getPoint().x, i.getNode1().getPoint().y, i.getNode2()
			        .getPoint().x, i.getNode2().getPoint().y);
			else {
				g2.setColor(Color.blue);
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(i.getNode1().getPoint().x, i.getNode1().getPoint().y, i.getNode2().getPoint().x, i
				        .getNode2().getPoint().y);
			}
		}
	}

	protected void paintComponent(Graphics g) {
		//draw the image
		g.drawImage(i, 0, 0, width, height, null);
	}

	private Point coord(float x, float y, float minX, float minY, float maxX, float maxY, int winX, int winY) {
		//get on screen coordinates from longitude and latitude
		int scaleX, scaleY;
		int windowX = (int) (winX * .95), windowY = (int) (winY * .95); //make it so that there is at least 2.5% border on all sides
		float offset1 = minX, offset2 = minY; //offset x and offset y (so every point is on screen)
		scaleX = (int) (windowX / (maxX - minX)); //
		scaleY = (int) (windowY / (maxY - minY));
		int oX, oY;//secondary offset (for the padding around the map)
		if (scaleX > scaleY) {
			//if the scale X is bigger than y, offset as so
			scaleX = (int) (scaleY);
			oY = -(int) (getHeight() * .025);  
			oX = (windowX - (int) ((maxX - offset1) * scaleX)) / 2 + (int) (getWidth() * .025);

		}
		else {
			//if the scale Y is bigger than x, offset as so
			scaleY = (int) (scaleX);
			oY = (windowY - (int) ((maxY - offset2) * scaleY)) / 2 - (int) (getHeight() * .025);
			oX = (int) (getWidth() * .025);
		}

		return new Point((int) ((x - offset1) * scaleX) + oX, (windowY - (int) ((y - offset2) * scaleY)) - oY);
	}

}
