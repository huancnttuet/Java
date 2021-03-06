package Chat.own;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class View extends JFrame{
	private static final long serialVersionUID = 1L;

	private ActionListener checkBoxListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			ip.setEnabled(guestOption.isSelected());
		}
	};
	
	private ActionListener sendMessageListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			parent.sendMessage(message.getText(), Server.CLIENT_SEND_MSG);
			message.setText("");
		}
	};
	
	private WindowListener onWindowChange = new WindowListener(){
		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowClosed(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent e) {
			if(parent.isConnected())
				parent.stop(true);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowOpened(WindowEvent e) {}
	};
	
	private Chat parent;
	
	private JPanel contentPanel;
	//LOGIN VIEW
	
	private JTextField login;
	private JTextField ip;
	private JTextField port;
	
	private JButton start;
	
	private JRadioButton guestOption;
	private JRadioButton hostOption;
	
	//CHAT VIEW
	
	JTextArea chatHistory = new JTextArea(10,25);
	JTextArea message;
	
	JButton sentMessage;
	JButton logout;
	
	//CONSTRUCTORS
	
	public View(Chat chat){
		parent = chat;
		
		setTitle("Gabos Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(onWindowChange);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		add(contentPanel);
	}
	
	//OTHERS
	
	public void appendText(String txt){
		chatHistory.append(txt+"\n");
	}
	
	//SHOWS
	
	public void showChatView(String name){
		contentPanel.removeAll();
		
		contentPanel.add(createLogoutPanel(name), BorderLayout.NORTH);
		contentPanel.add(createMessagePanel(), BorderLayout.SOUTH);
		contentPanel.add(createTextAreaPanel(), BorderLayout.CENTER);
		
		setVisible(true);
		pack();
	}
	
	public void showLoginView(){
		contentPanel.removeAll();
		
		contentPanel.add(createLoginPanel(), BorderLayout.NORTH);
		contentPanel.add(createAdressPanel(), BorderLayout.SOUTH);
		contentPanel.add(createCheckBoxesPanel(), BorderLayout.CENTER);
		setVisible(true);
		pack();
	}
	
	//CREATORS
	
	private JPanel createTextAreaPanel(){
		JPanel panel = new JPanel();
		
		chatHistory.setLineWrap(true);
		chatHistory.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(chatHistory);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll);

		return panel;
	}
	
	private JPanel createMessagePanel(){
		JPanel panel = new JPanel();
		panel.add(message = new JTextArea(2, 19));
		panel.add(sentMessage = new JButton("Send"));
		
		sentMessage.addActionListener(sendMessageListener);

		
		return panel;
	}
	
	private JPanel createLogoutPanel(String name){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Login: "+name),BorderLayout.WEST);

		panel.add(logout = new JButton("Logout"),BorderLayout.EAST);
		logout.addActionListener(a -> parent.stop(true));
		
		return panel;
	}
	
	private JPanel createLoginPanel(){
		JPanel panel = new JPanel();
		panel.add(new JLabel("Login"));
		
		panel.add(login = new JTextField("userName", 15));
	
		panel.add(start = new JButton("Start"));
		start.addActionListener(a -> parent.start(login.getText(), ip.getText(),port.getText(), hostOption.isSelected()));
		
		return panel;
	}
	
	private JPanel createCheckBoxesPanel(){
		JPanel panel = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		
		guestOption = new JRadioButton("Guest", true);
		hostOption = new JRadioButton("Host", false);
		
		guestOption.addActionListener(checkBoxListener);
		hostOption.addActionListener(checkBoxListener);
		
		bg.add(guestOption);
		bg.add(hostOption);
		
		panel.add(guestOption);
		panel.add(hostOption);
		
		return panel;
	}

	private JPanel createAdressPanel(){
		JPanel panel = new JPanel();
		
		panel.add(new JLabel("IP Adress: "));
		panel.add(ip = new JTextField("localhost", 10));
		panel.add(new JLabel("PORT: "));
		panel.add(port = new JTextField("1234", 3));
		
		return panel;
	}
}

