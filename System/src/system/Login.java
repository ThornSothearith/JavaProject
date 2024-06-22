package system;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtemail;
    private JPasswordField txtpassword;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setTitle("Login");
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
        lblNewLabel.setBounds(54, 41, 242, 44);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-01-15.jpg"));
        lblNewLabel_1.setLabelFor(panel);
        lblNewLabel_1.setBounds(71, 131, 225, 250);
        panel.add(lblNewLabel_1);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "", TitledBorder.CENTER, TitledBorder.ABOVE_BOTTOM, null, new Color(102, 51, 255)));
        panel_1.setRequestFocusEnabled(false);
        panel_1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        panel_1.setBackground(new Color(51, 255, 255));
        panel_1.setBounds(344, 0, 436, 446);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Log in");
        lblNewLabel_2.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(152, 41, 160, 63);
        panel_1.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Email");
        lblNewLabel_3.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(21, 147, 103, 29);
        panel_1.add(lblNewLabel_3);

        txtemail = new JTextField();
        txtemail.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtemail.setBounds(152, 152, 247, 26);
        panel_1.add(txtemail);
        txtemail.setColumns(10);

        JLabel lblNewLabel_3_1 = new JLabel("Password");
        lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_1.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel_3_1.setBounds(21, 240, 103, 29);
        panel_1.add(lblNewLabel_3_1);

        txtpassword = new JPasswordField();
        txtpassword.setBounds(152, 244, 247, 29);
        panel_1.add(txtpassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = txtemail.getText();
                String password = String.valueOf(txtpassword.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (authenticate(email, password)) {
                        JOptionPane.showMessageDialog(null, "Log in successfully !!!");
                        Home frame = new Home();
                        frame.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid email or password !!!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            private boolean authenticate(String email, String password) {
                MongoClient mongoClient = null;
                try {
                    mongoClient = new MongoClient("localhost", 27017);
                    MongoDatabase database = mongoClient.getDatabase("my_database");
                    MongoCollection<Document> collection = database.getCollection("users");

                    Document query = new Document("email", email).append("password", password);
                    Document result = collection.find(query).first();

                    return result != null;
                } catch (MongoException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                } finally {
                    if (mongoClient != null) {
                        mongoClient.close();
                    }
                }
            }
        });
        btnLogin.setBorderPainted(false);
        btnLogin.setBackground(new Color(102, 153, 255));
        btnLogin.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 20));
        btnLogin.setBounds(297, 371, 103, 35);
        panel_1.add(btnLogin);

        JLabel lblNewLabel_4 = new JLabel("I don't have an account");
        lblNewLabel_4.setFont(new Font("Rockwell Nova", Font.ITALIC, 13));
        lblNewLabel_4.setBounds(38, 322, 147, 20);
        panel_1.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Sign up");
        lblNewLabel_5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SignUp frame = new SignUp();
                frame.setVisible(true);
                dispose();
            }
        });
        lblNewLabel_5.setBackground(new Color(0, 0, 255));
        lblNewLabel_5.setForeground(new Color(51, 102, 255));
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_5.setFont(new Font("Romnea", Font.PLAIN, 18));
        lblNewLabel_5.setBounds(176, 322, 81, 21);
        panel_1.add(lblNewLabel_5);
    }
}
