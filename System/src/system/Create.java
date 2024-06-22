package system;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.awt.Toolkit;

public class Create extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtID;
    private JTextField txtName;
    private JTextField txtSex;
    private JTextField txtQtyProduct;
    private JTextField txtPrice;
    private JTextField txtPhoneNumber;
    private JTable table;
    private DefaultTableModel tableModel;
    private MongoCollection<Document> collection;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Create frame = new Create();
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
    public Create() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-03-05.jpg"));
        setTitle("Create");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1049, 646);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(176, 196, 222));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("IMPORT DATA FOR SELLER");
        lblNewLabel.setForeground(new Color(255, 255, 0));
        lblNewLabel.setFont(new Font("Khmer Pen SvR", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel.setBounds(352, 21, 406, 47);
        contentPane.add(lblNewLabel);

        JLabel lblID = new JLabel("ID");
        lblID.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblID.setBounds(72, 104, 59, 40);
        contentPane.add(lblID);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblName.setBounds(58, 169, 107, 40);
        contentPane.add(lblName);

        JLabel lblSex = new JLabel("Sex");
        lblSex.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblSex.setBounds(58, 242, 88, 40);
        contentPane.add(lblSex);

        JLabel lblQtyProduct = new JLabel("Qty_Product");
        lblQtyProduct.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblQtyProduct.setBounds(30, 310, 130, 40);
        contentPane.add(lblQtyProduct);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblPrice.setBounds(46, 376, 71, 40);
        contentPane.add(lblPrice);

        JLabel lblPhoneNumber = new JLabel("Phone Number");
        lblPhoneNumber.setFont(new Font("Rockwell Nova", Font.BOLD | Font.ITALIC, 18));
        lblPhoneNumber.setBounds(10, 445, 156, 40);
        contentPane.add(lblPhoneNumber);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Home frame = new Home();
                frame.setVisible(true);
                dispose();
            }
        });
        btnBack.setForeground(new Color(0, 0, 0));
        btnBack.setBackground(new Color(51, 153, 255));
        btnBack.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 18));
        btnBack.setBounds(884, 541, 141, 40);
        contentPane.add(btnBack);

        JButton btnAddData = new JButton("Add Data");
        btnAddData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = txtID.getText();
                String name = txtName.getText();
                String sex = txtSex.getText();
                String qtyProduct = txtQtyProduct.getText();
                String price = txtPrice.getText();
                String phoneNumber = txtPhoneNumber.getText();

                if (!id.isEmpty() && !name.isEmpty() && !qtyProduct.isEmpty() && !price.isEmpty() && !phoneNumber.isEmpty() && !sex.isEmpty()) {
                    Document document = new Document("ID", id)
                            .append("Name", name)
                            .append("Sex", sex)
                            .append("Qty_Product", qtyProduct)
                            .append("Price", price)
                            .append("Phone_Number", phoneNumber);
                    collection.insertOne(document);
                    tableModel.addRow(new Object[]{id, name, sex, qtyProduct, price, phoneNumber});
                    clearFields();
                }
            }
        });
        btnAddData.setForeground(new Color(0, 0, 0));
        btnAddData.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 18));
        btnAddData.setBackground(new Color(51, 153, 255));
        btnAddData.setBounds(694, 542, 136, 39);
        contentPane.add(btnAddData);

        txtID = new JTextField();
        txtID.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtID.setBounds(170, 106, 205, 40);
        contentPane.add(txtID);
        txtID.setColumns(10);

        txtName = new JTextField();
        txtName.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtName.setColumns(10);
        txtName.setBounds(170, 171, 205, 40);
        contentPane.add(txtName);

        txtSex = new JTextField();
        txtSex.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtSex.setColumns(10);
        txtSex.setBounds(170, 244, 205, 40);
        contentPane.add(txtSex);

        txtQtyProduct = new JTextField();
        txtQtyProduct.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtQtyProduct.setColumns(10);
        txtQtyProduct.setBounds(168, 312, 207, 40);
        contentPane.add(txtQtyProduct);

        txtPrice = new JTextField();
        txtPrice.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtPrice.setColumns(10);
        txtPrice.setBounds(168, 378, 207, 40);
        contentPane.add(txtPrice);

        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtPhoneNumber.setColumns(10);
        txtPhoneNumber.setBounds(168, 447, 205, 40);
        contentPane.add(txtPhoneNumber);

        // Initialize MongoDB connection
        try {
            MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase database = mongoClient.getDatabase("my_database");
            collection = database.getCollection("seller");

            // Initialize the table model
            tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Sex", "Qty_Product", "Price", "Phone Number"});

            table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(404, 104, 621, 381);
            contentPane.add(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtSex.setText("");
        txtQtyProduct.setText("");
        txtPrice.setText("");
        txtPhoneNumber.setText("");
    }
}
