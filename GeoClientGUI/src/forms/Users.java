package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.UserRemote;
import entities.User;
import tools.DateLabelFormatter;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Users extends JFrame {
	
	public static UserRemote lookUpUserRemote() throws NamingException {
		Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(jndiProperties);

		return (UserRemote) context.lookup("ejb:GeoEAR/GeoEJB/USR!dao.UserRemote");
	}

	private JPanel contentPane;
	private JTable table;
	private JTextField textMail;
	private JPasswordField passwordField;
	private JTextField textName;
	private JDatePickerImpl datePicker;
	private JButton btnAdd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Users frame = new Users();
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
	public Users() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(325, 42, 541, 341);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		JScrollPane scrollPane= new  JScrollPane(table);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				textName.setText(table.getModel().getValueAt(row, 1).toString());
				textMail.setText(table.getModel().getValueAt(row, 2).toString());
				passwordField.setText(table.getModel().getValueAt(row, 3).toString());
			}
		});
		
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"id", "name", "email", "password", "BirthDay"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		
		try {
			UserRemote ur = lookUpUserRemote();
			List<User> users = ur.findAll();
			for(int i = 0; i < users.size(); i++) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {users.get(i).getId() ,users.get(i).getName(), users.get(i).getEmail(), users.get(i).getPassword(), users.get(i).getBirthDate().toString()});
			}
			
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		panel.add(scrollPane, BorderLayout.CENTER);
		
		
		
		textMail = new JTextField();
		textMail.setBounds(137, 156, 163, 20);
		contentPane.add(textMail);
		textMail.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(137, 187, 163, 20);
		contentPane.add(passwordField);
		
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(137, 125, 163, 20);
		contentPane.add(textName);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(137, 218, 163, 20);
		contentPane.add(datePicker);
		
		btnAdd = new JButton("Add User");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UserRemote ur = lookUpUserRemote();
					Date birthdate = (Date) datePicker.getModel().getValue();
					String email = textMail.getText();
					String password = String.valueOf(passwordField.getPassword());
					String name = textName.getText();
					User u = ur.create(new User(name, birthdate, email, password));
					if(u != null) {
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[] {u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getBirthDate().toString()});
					}else {
						JOptionPane.showMessageDialog(null, "Error !!!");
					}
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAdd.setBounds(137, 249, 89, 23);
		contentPane.add(btnAdd);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(47, 128, 80, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(47, 159, 80, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(47, 190, 80, 14);
		contentPane.add(lblPassword);
		
		JLabel lblBirthdate = new JLabel("BirthDate");
		lblBirthdate.setBounds(47, 224, 80, 14);
		contentPane.add(lblBirthdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, column).toString();
				try {
					UserRemote ur = lookUpUserRemote();
					ur.delete(Integer.valueOf(value));
					JOptionPane.showMessageDialog(null, "Deleted");
					((DefaultTableModel)table.getModel()).removeRow(row);
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(137, 278, 89, 21);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UserRemote ur = lookUpUserRemote();
					int row = table.getSelectedRow();
					ur.update(new User(Integer.valueOf(table.getModel().getValueAt(row, 0).toString()), textName.getText().toString(),(Date) datePicker.getModel().getValue(), textMail.getText().toString(), String.valueOf(passwordField.getPassword())));
					JOptionPane.showMessageDialog(null, "Updated");
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(137, 309, 89, 21);
		contentPane.add(btnUpdate);
	}
}
