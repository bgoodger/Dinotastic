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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MenuBoard extends Board {

    private JPanel mB;
	private JButton startButtonOneP;
    private JButton startButtonTwoP;
    private JRadioButton trainingSelector;
    private JRadioButton gameSelector;
    private ButtonGroup group;

    private boolean trainingMode = true;;

	public MenuBoard(JPanel cardBoard ) {

        this.setLayout(new FlowLayout( FlowLayout.CENTER));


        JPanel topBox = new JPanel(new FlowLayout( FlowLayout.CENTER));
        JPanel bottomBox = new JPanel(new FlowLayout( FlowLayout.CENTER));
        topBox.setPreferredSize(new Dimension(600,50));
		setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        mB = cardBoard;

        startButtonOneP = new JButton("START ONE PLAYER");
        startButtonOneP.addActionListener(this);
        bottomBox.add(startButtonOneP);

        startButtonTwoP = new JButton("START TWO PLAYER");
        startButtonTwoP.addActionListener(this);
        bottomBox.add(startButtonTwoP);

        trainingSelector = new JRadioButton("Training");
        trainingSelector.setActionCommand("Training");
        trainingSelector.setSelected(true);
        gameSelector = new JRadioButton("Play Game");
        gameSelector.setActionCommand("Play Game");

        group = new ButtonGroup();
        group.add(trainingSelector);
        group.add(gameSelector);

        trainingSelector.addActionListener(this);
        gameSelector.addActionListener(this);

        topBox.add(trainingSelector);
        topBox.add(gameSelector);

        JPanel mainBox = new JPanel(new FlowLayout( FlowLayout.CENTER));
        mainBox.setPreferredSize(new Dimension(600,450));

        mainBox.add(new JLabel (new ImageIcon("artwork/menu.png")));
        this.add(mainBox);
        this.add(topBox);
        this.add(bottomBox);
	} 


    public void actionPerformed (ActionEvent e) {

        CardLayout cl = (CardLayout)(mB.getLayout());
    
        if (e.getSource() == startButtonOneP) { 
                mB.add( new PlayBoard (mB, false, trainingMode), "game" );
                cl.show(mB,"game");
        } else if (e.getSource() == startButtonTwoP) {
                mB.add( new PlayBoard (mB, true, trainingMode), "game" );                
                cl.show(mB,"game");
        } else if (e.getSource() ==  trainingSelector) {
                trainingMode = true;
        } else if (e.getSource() == gameSelector) {
                trainingMode = false;
        }
    
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        //g2d.drawImage(new ImageIcon("artwork/menu.png").getImage(), 100, 50, mB); 
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }



    
}