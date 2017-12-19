
// Dylan Morgen

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class StreetMap {

	public static void main(String[] args) {
		Canvas main = new Canvas(args[0]); //pass through "NYS.txt" etc etc
		boolean show = false;
		//argument logic
		for (int i = 1; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("--show")) {
				show = true;
			}
			else if (args[i].equalsIgnoreCase("--directions")) {
				main.plot(args[i + 1] + " " + args[i + 2]);
				i += 2;
			}
			else System.out.println("Sorry \"" + args[i] + "\" is not a valid arguement");
		}
		if (show) {
			JFrame frame = new JFrame("Map"); //  Window
			frame.setPreferredSize(new Dimension(800, 600)); // size of the window
			frame.getContentPane().add(main, BorderLayout.CENTER); // Add canvas to Jframe
			frame.pack(); // shrink size to contents as needed
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // close on close (duh)
			frame.setVisible(true); // make window visible
		}
	}

}
