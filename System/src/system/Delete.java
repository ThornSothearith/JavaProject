package system;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Delete extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton, saveButton, searchButton, showAllButton, backButton, deleteButton;
    private JTextField nameField, idField;

    public Delete() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-03-05.jpg"));
    	getContentPane().setBackground(Color.CYAN);
        // Set up the JFrame
        setTitle("Delete");
        setSize(976, 614);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Sex", "Qty_Product", "Price", "Phone Number"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 101, 786, 402);
        nameField = new JTextField(20);
        getContentPane().setLayout(null);

        getContentPane().add(scrollPane);
        saveButton = new JButton("Save File");
        saveButton.setBackground(Color.MAGENTA);
        saveButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        saveButton.setBounds(813, 446, 127, 33);
        getContentPane().add(saveButton);
        idField = new JTextField(20);
        idField.setBounds(213, 48, 245, 33);
        getContentPane().add(idField);
        searchButton = new JButton("Search by ID");
        searchButton.setBackground(Color.MAGENTA);
        searchButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        searchButton.setBounds(480, 46, 152, 33);
        getContentPane().add(searchButton);
        deleteButton = new JButton("Delete by ID");
        deleteButton.setBackground(Color.MAGENTA);
        deleteButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        deleteButton.setBounds(652, 46, 134, 33);
        getContentPane().add(deleteButton);
        JLabel label = new JLabel("ID:");
        label.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 18));
        label.setBounds(165, 48, 38, 33);
        getContentPane().add(label);
        backButton = new JButton("Back");
        backButton.setBounds(813, 509, 127, 33);
        getContentPane().add(backButton);
        backButton.setBackground(Color.MAGENTA);
        backButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        showAllButton = new JButton("Show All Documents");
        showAllButton.setBounds(10, 523, 245, 35);
        getContentPane().add(showAllButton);
        showAllButton.setBackground(Color.MAGENTA);
        showAllButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 16));
        showAllButton.addActionListener(e -> loadDocuments());
        backButton.addActionListener(e -> navigateToHome());
        deleteButton.addActionListener(e -> deleteDocumentByID());
        searchButton.addActionListener(e -> searchDocumentByID());
        
                saveButton.addActionListener(e -> saveDocuments());
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

    private void searchDocumentByID() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String uri = "mongodb://localhost:27017/";
        String databaseName = "my_database";
        String collectionName = "seller";

        try (var mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            Document query = new Document("ID", id);
            try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
                tableModel.setRowCount(0); // Clear the table before displaying
                if (cursor.hasNext()) {
                    Document document = cursor.next();
                    tableModel.addRow(new Object[]{
                            document.getString("ID"),
                            document.getString("Name"),
                            document.getString("Sex"),
                            document.getString("Qty_Product"),
                            document.getString("Price"),
                            document.getString("Phone_Number")
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "No document found with ID: " + id, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching document: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void deleteDocumentByID() {
        String ID = idField.getText().trim();
        if (ID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String uri = "mongodb://localhost:27017/";
        String databaseName = "my_database";
        String collectionName = "seller";

        try (var mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            Document query = new Document("ID", ID);
            Document deletedDocument = collection.findOneAndDelete(query);

            if (deletedDocument != null) {
                JOptionPane.showMessageDialog(this, "Document deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDocuments(); // Reload the documents to reflect the changes
            } else {
                JOptionPane.showMessageDialog(this, "No document found with ID: " + ID, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting document: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void navigateToHome() {
        dispose(); // Close the current window
        SwingUtilities.invokeLater(() -> {
            Home homeFrame = new Home();
            homeFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Delete frame = new Delete();
            frame.setVisible(true);
        });
    }
}
