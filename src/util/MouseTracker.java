package src.util;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Mouse Position Tracker
 * 
 		MouseTracker mt = new MouseTracker();
		mt.setBounds(0, 0, 1024, 768);
		mt.setOpaque(false);
		jp_main.add(mt);
 *
 * @author HE Junyang
 *
 */


class MouseListener implements MouseMotionListener {
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		String s = "now:" + x + ',' + y;
		MouseTracker.jl.setText(s);
	}

	public void mouseDragged(MouseEvent e) {
	};
}

public class MouseTracker extends JPanel{
	public static JLabel jl = new JLabel();
	
	public MouseTracker() {
		this.setPreferredSize(new Dimension(100, 30));
		jl.setBounds(10, 5, 80, 20);
		this.add(jl);
		addMouseMotionListener(new MouseListener());
		this.setVisible(true);
	}
	
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
