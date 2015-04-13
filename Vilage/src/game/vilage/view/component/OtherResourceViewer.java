package game.vilage.view.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.vilage.buldings.BasicBuilding;
import game.vilage.resources.Suroviny;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OtherResourceViewer extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JLabel owned;
	private JLabel required;
	private JButton buy;
	private byte type;
	private BasicBuilding parent;
	
	//ACTIONS
	
	private ActionListener buyEvent = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			parent.wantBuy(type, Integer.parseInt(required.getText()) - Integer.parseInt(owned.getText()));
		}
	};
	
	//CONSTRUCTORS
	
	public OtherResourceViewer(byte type, int need, int have, BasicBuilding parent){
		this.parent = parent;
		this.type = type;
		
		add(new JLabel(Suroviny.getName(type)+": "));
		add(owned = new JLabel(String.valueOf(have)));
		add(new JLabel(" / "));
		add(required = new JLabel(String.valueOf(need)));
		
		buy = new JButton("Dok�pi�");
		buy.addActionListener(buyEvent);
		buy.setVisible(have < need);
		
		add(buy);
	}

	//OTHERS

	public void updateValue() {
		if(parent.getResources().getOwned().containsKey(type))
			owned.setText(String.valueOf(parent.getResources().getOwned().get(type)));
		
		buy.setVisible(parent.getResources().getOwned(type) < parent.getResources().getRequired(type));
	}
	
	public void clear(){
		removeAll();
	}
	
	//GETTERS
	
	public byte getType() {
		return type;
	}
	
	//SETTERS
	
	public void setValue(int val){
		owned.setText(val+"");
	}
}