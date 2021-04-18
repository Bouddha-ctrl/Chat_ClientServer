package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.Client;
import connection.Server;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.TextArea;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class JClient extends JFrame {

	 JPanel contentPane;
	 JTextField tport;
	 JTextField tuser;
	 String ip ="127.0.0.1";
	 String user = null;
	 Client C;
	 String line;
	 public JButton send;
	 JTextArea chat;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JClient frame = new JClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 0, 517, 291);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		panel_2.setVisible(false);
		
		JTextArea msg = new JTextArea();
		msg.setRows(2);
		msg.setBounds(10, 219, 365, 42);
		panel_2.add(msg);
		
		send = new JButton("send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				line = msg.getText();
				if (line.length()>0) {
					C.send(line);
					msg.setText("");
				}
			}
		});
		send.setBounds(385, 233, 109, 32);
		panel_2.add(send);
		
		JLabel lblNewLabel = new JLabel("Username :");
		lblNewLabel.setBounds(23, 11, 71, 14);
		panel_2.add(lblNewLabel);
		
		JLabel username = new JLabel("");
		username.setForeground(Color.GREEN);
		username.setBounds(124, 11, 133, 14);
		panel_2.add(username);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 365, 179);
		panel_2.add(scrollPane);
		
		
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});

		
		chat = new JTextArea();
		scrollPane.setViewportView(chat);
		chat.setEditable(false);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(24, 11, 494, 269);
		contentPane.add(panel_1);
		panel_1.setLayout(null); 	
		
		JButton btnNewButton = new JButton("connect");
		btnNewButton.setBounds(177, 142, 94, 41);
		panel_1.add(btnNewButton);
		
		JLabel userlabel = new JLabel("Username");
		userlabel.setBounds(33, 84, 120, 25);
		panel_1.add(userlabel);
		
		tport = new JTextField();
		tport.setBounds(185, 23, 86, 20);
		panel_1.add(tport);
		tport.setColumns(10);
		
		tuser = new JTextField();
		tuser.setBounds(185, 86, 86, 20);
		panel_1.add(tuser);
		tuser.setColumns(10);
		
		JLabel JPort = new JLabel("Port :");
		JPort.setBounds(33, 21, 120, 25);
		panel_1.add(JPort);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String port = tport.getText();
				user = tuser.getText();
				
				int num;
				try 
				{
					num = Integer.parseInt(port);
					if (num<1024) JOptionPane.showMessageDialog( null, "port too small" );
					
					C = new Client(ip,num,user,JClient.this); 
		            panel_1.setVisible(false);
		            panel_2.setVisible(true);
		            username.setText(user);
		            
				}
				catch(java.net.ConnectException ex){
					JOptionPane.showMessageDialog( null, "Connection refused , try another port! " );
				
				}catch(Exception z)
				{
					JOptionPane.showMessageDialog( null, "Number Format Exception" );
				}
			}
		});
	}
	public void setChat(String s) {
	    chat.setText(chat.getText() + "\n" + s );
	}
	public String getMSG() {
		return line;
	}
}
