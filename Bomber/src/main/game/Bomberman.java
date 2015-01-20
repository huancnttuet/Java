package main.game;

import java.util.ArrayList;

import main.both.components.Log;
import main.both.components.Map;
import main.both.components.MyPlayer;
import main.both.components.PlayerForDraw;
import main.both.core.Game;
import main.both.core.GameObject;
import main.both.core.utils.Logs;
import main.both.core.utils.Window;
import main.both.multiplayer.Client;
import main.both.multiplayer.OtherPlayer;
import main.both.multiplayer.Server;

public class Bomberman extends Game{
	private Client client;
	public ArrayList<PlayerForDraw> players = new ArrayList<PlayerForDraw>();
	
	public int pocetHracov = 0;
	
	public Bomberman(){
	}
	
	public void updateClient(){
		client.write(Server.PLAYER_POSITION+" "+player.getPosition().getX()+" "+player.getPosition().getY());
		player.moved = false;
	}
	
	public void init(){
		player = new MyPlayer("Marko");
		client = new Client(this);
		
		Window.keyboard.addPlayer(player);
		//GameObject mapa = new GameObject().addComponent(new Map(30,30,p));
		GameObject mapa = new GameObject().addComponent(new Map("mapa1",player));
		addObject(mapa);
		addObject(new GameObject().addComponent(player));
		
		addObject(new GameObject().addComponent(new Log(this)));
	}
	
	public void createPlayer(PlayerForDraw p){
		players.add(p);
		addObject(new GameObject().addComponent(p));
	}
}