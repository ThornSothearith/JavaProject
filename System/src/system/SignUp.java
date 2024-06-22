package system;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SignUp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField passwordField;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUp frame = new SignUp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SignUp() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 794, 483);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(0, 0, 344, 446);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome");
        lblNewLabel.setForeground(new Color(255, 102, 51));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Papyrus", Font.PLAIN, 50));
        lblNewLabel.setBounds(55, 40, 242, 44);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-01-15.jpg"));
        lblNewLabel_1.setLabelFor(panel);
        lblNewLabel_1.setBounds(55, 136, 225, 250);
        panel.add(lblNewLabel_1);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.CENTER, TitledBorder.ABOVE_BOTTOM, null, new Color(102, 51, 255)));
        panel_1.setRequestFocusEnabled(false);
        panel_1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        panel_1.setBackground(new Color(51, 255, 255));
        panel_1.setBounds(344, 0, 436, 446);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Create Account");
        lblNewLabel_2.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(96, 43, 286, 63);
        panel_1.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Email");
        lblNewLabel_3.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(21, 233, 103, 29);
        panel_1.add(lblNewLabel_3);

        textField = new JTextField();
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        textField.setBounds(152, 236, 247, 26);
        panel_1.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_3_1 = new JLabel("Password");
        lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_1.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel_3_1.setBounds(21, 302, 103, 29);
        panel_1.add(lblNewLabel_3_1);

        passwordField = new JPasswordField();
        passwordField.setBounds(152, 302, 247, 29);
        panel_1.add(passwordField);

        JButton btnNewButton = new JButton("Create");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUser();
               
            }         
        });
        btnNewButton.setBorderPainted(false);
        btnNewButton.setBackground(new Color(102, 153, 255));
        btnNewButton.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 20));
        btnNewButton.setBounds(275, 371, 125, 35);
        panel_1.add(btnNewButton);

        JLabel lblNewLabel_3_2 = new JLabel("First Name");
        lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_2.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel_3_2.setBounds(0, 164, 116, 29);
        panel_1.add(lblNewLabel_3_2);

        JLabel lblNewLabel_3_2_1 = new JLabel("Last Name");
        lblNewLabel_3_2_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_2_1.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel_3_2_1.setBounds(221, 164, 103, 29);
        panel_1.add(lblNewLabel_3_2_1);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        textField_1.setBounds(115, 169, 96, 26);
        panel_1.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        textField_2.setColumns(10);
        textField_2.setBounds(330, 169, 96, 26);
        panel_1.add(textField_2);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login frame = new Login();
                frame.setVisible(true);
                dispose();
            }
        });
        btnBack.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 20));
        btnBack.setBorderPainted(false);
        btnBack.setBackground(new Color(102, 153, 255));
        btnBack.setBounds(58, 371, 125, 35);
        panel_1.add(btnBack);
    }

    private void createUser() {
        String firstName = textField_1.getText();
        String lastName = textField_2.getText();
        String email = textField.getText();
        String password = new String(passwordField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // MongoDB connection
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase database = mongoClient.getDatabase("my_database");
            MongoCollection<Document> collection = database.getCollection("users");

            Document user = new Document("first_name", firstName)
                    .append("last_name", lastName)
                    .append("email", email)
                    .append("password", password);

            collection.insertOne(user);
            JOptionPane.showMessageDialog(this, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        Login frame = new Login();
        frame.setVisible(true);
        dispose();
     }
   }