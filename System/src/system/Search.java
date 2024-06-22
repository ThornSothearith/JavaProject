package system;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton, saveButton, searchButton, showAllButton, backButton;
    private JTextField nameField;

    public Search() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-03-05.jpg"));
    	getContentPane().setBackground(Color.CYAN);
        // Set up the JFrame
        setTitle("Search");
        setSize(1044, 619);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setBackground(Color.CYAN);
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Sex", "Qty_Product", "Price", "Phone Number"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 117, 877, 402);
        getContentPane().setLayout(null);

        getContentPane().add(scrollPane);
        backButton = new JButton("Back");
        backButton.setBackground(Color.MAGENTA);
        backButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        backButton.setBounds(899, 514, 121, 33);
        getContentPane().add(backButton);
        saveButton = new JButton("Save to File");
        saveButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        saveButton.setBackground(Color.MAGENTA);
        saveButton.setBounds(899, 463, 121, 33);
        getContentPane().add(saveButton);
        nameField = new JTextField(20);
        nameField.setFont(new Font("Khmer OS Battambang", Font.PLAIN, 16));
        nameField.setBounds(249, 51, 284, 33);
        getContentPane().add(nameField);
        showAllButton = new JButton("Show All Documents");
        showAllButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        showAllButton.setBackground(Color.MAGENTA);
        showAllButton.setBounds(10, 540, 284, 33);
        getContentPane().add(showAllButton);
        searchButton = new JButton("Search by Name");
        searchButton.setBackground(Color.MAGENTA);
        searchButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        searchButton.setBounds(549, 52, 180, 30);
        getContentPane().add(searchButton);
        
        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 18));
        lblNewLabel.setBounds(162, 49, 77, 35);
        getContentPane().add(lblNewLabel);
        searchButton.addActionListener(e -> searchDocumentByName());
        showAllButton.addActionListener(e -> loadDocuments());
        
                saveButton.addActionListener(e -> saveDocuments());
        backButton.addActionListener(e -> navigateToHome());
    }

    private void loadDocuments() {
        String uri = "mongodb://localhost:27017/";
        String databaseName = "my_database";
        String collectionName = "seller";

        try (var mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                tableModel.setRowCount(0); // Clear the table before loading new data
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    tableModel.addRow(new Object[]{
                            doc.getString("ID"),
                            doc.getString("Name"),
                            doc.getString("Sex"),
                            doc.getString("Qty_Product"),
                            doc.getString("Price"),
                            doc.getString("Phone_Number")
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading documents: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void saveDocuments() {
        String uri = "mongodb://localhost:27017/";
        String databaseName = "my_database";
        String collectionName = "seller";

        List<Document> documents = new ArrayList<>();
        try (var mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    documents.add(doc);
                }
            }

            try (FileWriter file = new FileWriter("output.json")) {
                for (Document doc : documents) {
                    file.write(doc.toJson() + "\n");
                }
                JOptionPane.showMessageDialog(this, "Data exported successfully to output.json", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to MongoDB: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void searchDocumentByName() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a document Name", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String uri = "mongodb://localhost:27017/";
        String databaseName = "my_database";
        String collectionName = "seller";

        try (var mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            Document query = new Document("Name", name);
            MongoCursor<Document> cursor = collection.find(query).iterator();

            tableModel.setRowCount(0); // Clear the table before displaying
            while (cursor.hasNext()) {
                Document document = cursor.next();
                tableModel.addRow(new Object[]{
                        document.getString("ID"),
                        document.getString("Name"),
                        document.getString("Sex"),
                        document.getString("Qty_Product"),
                        document.getString("Price"),
                        document.getString("Phone_Number")
                });
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No documents found with Name: " + name, "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching document: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void navigateToHome() {
        // Assuming you have a Home class for the main window
        dispose(); // Close the current window
        SwingUtilities.invokeLater(() -> {
            Home homeFrame = new Home();
            homeFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Search frame = new Search();
            frame.setVisible(true);
        });
    }
}
