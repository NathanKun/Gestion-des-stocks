package src.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Mouse Position Tracker
 * usage : 
 		MouseTracker mt = new MouseTracker();
		mt.setBounds(0, 0, 1024, 768);
		mt.setOpaque(false);
		jp_main.add(mt);
 *
 * @author HE Junyang
 *
 */

/**
 * My mouse motion listener
 * @author HE Junyang
 *
 */
class MouseListener implements MouseMotionListener {
	/**
	 * mouse moved event
	 */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		String s = "now:" + x + ',' + y;
		MouseTracker.jl.setText(s);
	}
	/**
	 * mouse dragged event
	 */
	public void mouseDragged(MouseEvent e) {
	};
}

/**
 * My Mouse Tracker, show the position of mouse
 * use to position a component
 * @author HE Junyang
 *
 */
public class MouseTracker extends JPanel{
	/**
	 * label of position of mouse
	 */
	public static JLabel jl = new JLabel();
	/**
	 * constructor
	 */
	public MouseTracker() {
		this.setPreferredSize(new Dimension(100, 30));
		jl.setBounds(10, 5, 80, 20);
		this.add(jl);
		this.addMouseMotionListener((MouseMotionListener) new MouseListener());
		this.setVisible(true);
	}
	/**
	 * main method, use to testing
	 * @param args for main
	 */
	public static void main(String[] args) {
		JFrame fm = new JFrame("test");
		JPanel fp = new MouseTracker();
		Container con = fm.getContentPane();
		con.add(fp);
		fm.setSize(500, 400);
		fm.setVisible(true);
		fm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
