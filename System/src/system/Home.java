package system;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JTable;

@SuppressWarnings("unused")
public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-02-56.jpg"));
		setFont(new Font("Cambria Math", Font.BOLD | Font.ITALIC, 20));
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 867, 466);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Create frame = new Create();
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBorder(UIManager.getBorder("ToolTip.border"));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 25));
		btnNewButton.setBounds(36, 60, 197, 53);
		contentPane.add(btnNewButton);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setBorder(UIManager.getBorder("ToolTip.border"));
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 25));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search frame = new Search();
				frame.setVisible(true);
				dispose();
			}
		});
		btnSearch.setBounds(36, 156, 197, 53);
		contentPane.add(btnSearch);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update frame = new Update();
	            frame.setVisible(true);
	            dispose();
			}
		});
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setBorder(UIManager.getBorder("ToolTip.border"));
		btnUpdate.setBackground(Color.WHITE);
		btnUpdate.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 25));
		btnUpdate.setBounds(36, 245, 197, 53);
		contentPane.add(btnUpdate);
		
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Delete frame = new Delete();
		            frame.setVisible(true);
		            dispose();
			}
		});
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBorder(UIManager.getBorder("ToolTip.border"));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 25));
		btnDelete.setBounds(36, 338, 197, 53);
		contentPane.add(btnDelete);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 255));
		panel.setBounds(0, 0, 264, 429);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(176, 196, 222));
		panel_1.setBounds(260, 0, 593, 429);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-02-48.jpg"));
		lblNewLabel.setBounds(165, 119, 230, 235);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome To System");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 28));
		lblNewLabel_1.setBounds(140, 51, 308, 29);
		panel_1.add(lblNewLabel_1);
		
		 // Initialize the table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] { "ID", "Name", "Qty_Product", "Price", "Phone Number" });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 397, 884, 118);
        contentPane.add(scrollPane);
		
	}
}
