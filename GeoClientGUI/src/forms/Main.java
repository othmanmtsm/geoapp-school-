package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JTextField;

import dao.UserRemote;
import entities.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 448, 291);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Gestion Utilisateurs");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Users u = new Users();
				u.setVisible(true);
			}
		});
		btnNewButton.setBounds(147, 72, 125, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnGestionPhones = new JButton("Gestion Phones");
		btnGestionPhones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Phones p = new Phones();
				p.setVisible(true);
			}
		});
		btnGestionPhones.setBounds(147, 126, 125, 23);
		frame.getContentPane().add(btnGestionPhones);
	}
}
