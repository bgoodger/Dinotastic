import java.awt.Color;		
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Rectangle; 
import java.awt.event.KeyEvent;
import java.awt.Image;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;


public abstract class Board extends JPanel implements ActionListener,  MouseMotionListener, MouseListener{
	public Board() {

	}

	public void actionPerformed(ActionEvent e) {

        repaint();  
    }

    @Override
	public void mouseMoved(MouseEvent me) {} 
	@Override
    public void mouseDragged(MouseEvent me) {} 
    @Override
    public void mouseExited(MouseEvent me) {} 
    @Override
    public void mouseEntered(MouseEvent me) {} 
    @Override
    public void mouseReleased(MouseEvent me) {} 
    @Override
    public void mousePressed(MouseEvent me) {} 
    @Override
    public void mouseClicked(MouseEvent me) {} 

}

