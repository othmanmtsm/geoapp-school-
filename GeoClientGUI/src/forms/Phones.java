package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.PhoneRemote;
import dao.UserRemote;
import entities.Phone;
import entities.User;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Phones extends JFrame {
	public static PhoneRemote lookUpPhoneRemote() throws NamingException {
		Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(jndiProperties);

		return (PhoneRemote) context.lookup("ejb:GeoEAR/GeoEJB/PH!dao.PhoneRemote");
	}
	public static UserRemote lookUpUserRemote() throws NamingException {
		Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(jndiProperties);

		return (UserRemote) context.lookup("ejb:GeoEAR/GeoEJB/USR!dao.UserRemote");
	}

	private JPanel contentPane;
	private JTable table;
	private JTextField textIMEI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Phones frame = new Phones();
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
	public Phones() {
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
				textIMEI.setText(table.getModel().getValueAt(row, 1).toString());
			}
		});
		
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"id", "imei", "user"
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
			PhoneRemote pr = lookUpPhoneRemote();
			List<Phone> phones = pr.findAll();
			for(int i = 0; i < phones.size(); i++) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {phones.get(i).getId() ,phones.get(i).getImei(), phones.get(i).getUser().toString()});
			}
			
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		panel.add(scrollPane, BorderLayout.CENTER);
		
		textIMEI = new JTextField();
		textIMEI.setBounds(130, 170, 171, 22);
		contentPane.add(textIMEI);
		textIMEI.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(130, 214, 171, 22);
		try {
			UserRemote ur = lookUpUserRemote();
			List<User> users = ur.findAll();
			for(User u : users) {
				comboBox.addItem(u.getId());
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("IMEI");
		lblNewLabel.setBounds(45, 174, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("User");
		lblNewLabel_1.setBounds(45, 218, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Add Phone");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PhoneRemote pr = lookUpPhoneRemote();
					String imei = textIMEI.getText();
					int user_id = Integer.valueOf(comboBox.getSelectedItem().toString());
					pr.create(new Phone(imei), user_id);
					UserRemote ur = lookUpUserRemote();
					User u = ur.findById(user_id);
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.addRow(new Object[] {imei, u});
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(130, 254, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, column).toString();
				try {
					PhoneRemote pr = lookUpPhoneRemote();
					pr.delete(Integer.valueOf(value));
					JOptionPane.showMessageDialog(null, "Deleted");
					((DefaultTableModel)table.getModel()).removeRow(row);
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(130, 287, 85, 21);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PhoneRemote pr = lookUpPhoneRemote();
					int row = table.getSelectedRow();
					pr.update(new Phone(Integer.valueOf(table.getModel().getValueAt(row, 0).toString()), textIMEI.getText().toString()));
					JOptionPane.showMessageDialog(null, "Updated");
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(130, 318, 85, 21);
		contentPane.add(btnUpdate);
	}
}
