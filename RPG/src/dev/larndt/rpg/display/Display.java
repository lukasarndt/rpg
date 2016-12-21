package dev.larndt.rpg.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Display extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Image screen;
	
	private int width, height;
	private String title;
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	
	private void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // So the window appears on the center of the screen.
		frame.setVisible(true);
		
		//canvas = new JPanel();
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		//this.setFocusable(false); // So the JFrame has focus.
		
		this.getInputMap().put(KeyStroke.getKeyStroke("W"), "w");
		this.getInputMap().put(KeyStroke.getKeyStroke("A"), "a");
		this.getInputMap().put(KeyStroke.getKeyStroke("S"), "s");
		this.getInputMap().put(KeyStroke.getKeyStroke("D"), "d");
		this.getActionMap().put("w", new MoveAction("w"));
		this.getActionMap().put("a", new MoveAction("a"));
		this.getActionMap().put("s", new MoveAction("s"));
		this.getActionMap().put("d", new MoveAction("d"));
		
		this.getInputMap().put(KeyStroke.getKeyStroke("released W"), "rw");
		this.getInputMap().put(KeyStroke.getKeyStroke("released A"), "ra");
		this.getInputMap().put(KeyStroke.getKeyStroke("released S"), "rs");
		this.getInputMap().put(KeyStroke.getKeyStroke("released D"), "rd");
		this.getActionMap().put("rw", new ReleaseAction("w"));
		this.getActionMap().put("ra", new ReleaseAction("a"));
		this.getActionMap().put("rs", new ReleaseAction("s"));
		this.getActionMap().put("rd", new ReleaseAction("d"));
		
		frame.add(this);
		frame.pack(); // Makes the frame fit the canvas.
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(screen != null) {
			g.drawImage(screen, 0, 0, null);
		}
	}
	
	private class MoveAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String cmd;
		
		public MoveAction(String cmd) {
			this.cmd = cmd;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(cmd + " pressed.");
		}
	}
	
	private class ReleaseAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String cmd;
		
		public ReleaseAction(String cmd) {
			this.cmd = cmd;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(cmd + " released.");
			
		}
		
	}


	// GETTERS & SETTERS
	public Display getDisplay() {
		return this;
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setScreen(Image screen) {
		this.screen = screen;
	}
}
