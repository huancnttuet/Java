package com.voxel.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JPanel;

import com.voxel.core.Game;
import com.voxel.gui.menu.BMenu;
import com.voxel.gui.menu.RMenu;
import com.voxel.gui.menu.TMenu;

public class Gui extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private BMenu bmenu;
	private RMenu rmenu;
	private TMenu tmenu;
	private Canvas canvas;
	private Game game;
	
	public Gui(Game game) {
		setLayout(new BorderLayout());
		setBackground(Color.red);
		this.game = game;
		init();
	}
	
	public void init() {
		
		tmenu = new TMenu();
		add(tmenu,BorderLayout.NORTH);
		
		bmenu = new BMenu();
		add(bmenu,BorderLayout.SOUTH);
		
		rmenu = new RMenu(game.getRootObject());
		add(rmenu,BorderLayout.EAST);
		
		canvas = new Canvas();
		add(canvas,BorderLayout.CENTER);
		
	}

	public Canvas getCanvas(){
		return canvas;
	}

	public RMenu getRmenu() {
		return rmenu;
	}
	
}
