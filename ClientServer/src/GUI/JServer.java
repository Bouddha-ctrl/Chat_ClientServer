package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.Server;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class JServer extends JFrame {

	private JPanel contentPane;
	private JTextField tPort;
	private JButton start ;
	
	private Server S = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JServer frame = new JServer();
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
	public JServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(35, 21, 351, 209);
		contentPane.add(panel);
		panel.setLayout(null);

		

		
		tPort = new JTextField();
		tPort.setBounds(180, 33, 86, 20);
		panel.add(tPort);
		tPort.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter a port : (great than 1023) :");
		lblNewLabel.setBounds(0, 35, 180, 17);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Status :");
		lblNewLabel_1.setBounds(27, 135, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel status = new JLabel("Not connected");
		status.setForeground(Color.RED);
		status.setVerticalAlignment(SwingConstants.TOP);
		status.setBounds(93, 135, 248, 63);
		status.setOpaque(true);
		panel.add(status);
		
		JLabel lblNewLabel_3 = new JLabel("Ip server : ");
		lblNewLabel_3.setBounds(49, 11, 86, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("127.0.0.1");
		lblNewLabel_3_1.setBounds(180, 11, 86, 14);
		panel.add(lblNewLabel_3_1);
		

		JButton close = new JButton("Close Server");
		close.setEnabled(false);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				S.stop();
				status.setForeground(Color.RED);
				status.setText("Not connected");
				start.setEnabled(true);
				close.setEnabled(false);
			}
		});
		close.setBounds(224, 135, 117, 63);
		panel.add(close);
		
		
		start = new JButton("Start the server");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String port = tPort.getText();
				int num;
				try 
				{
					num = Integer.parseInt(port);
					if (num<1024) JOptionPane.showMessageDialog( null, "port too small" );
					
					S = new Server(num); 
					close.setEnabled(true);

					status.setForeground(Color.GREEN);
					status.setText("Connected");
					start.setEnabled(false);
				}
				catch(java.net.BindException ex){
					JOptionPane.showMessageDialog( null, "Address already in use: JVM_Bind" );
				
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog( null, "Number Format Exception" );
				}
			}
		});
		start.setBounds(126, 63, 140, 28);
		panel.add(start);
		
	}
}
