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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Update extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton, saveButton, searchButton, showAllButton, updateButton;
    private JTextField searchNameField, idField, nameField, sexField, qtyField, priceField, phoneField;
    private JLabel idLabel, nameLabel, sexLabel, qtyLabel, priceLabel, phoneLabel;

    // MongoDB Configuration
    private static final String MONGO_URI = "mongodb://localhost:27017/";
    private static final String DATABASE_NAME = "my_database";
    private static final String COLLECTION_NAME = "seller";

    public Update() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS-DM\\Pictures\\Saved Pictures\\photo_2024-06-19_15-03-05.jpg"));
        // Set up the JFrame
        setTitle("Update ");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Sex", "Qty_Product", "Price", "Phone Number"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 33, 886, 312);
        saveButton = new JButton("Save to File");
        searchButton = new JButton("Search by Name");
        searchButton.setBackground(new Color(255, 128, 64));
        showAllButton = new JButton("Show All Documents");
        showAllButton.setForeground(new Color(255, 255, 255));
        showAllButton.setBackground(new Color(0, 0, 255));
        updateButton = new JButton("Update Data");
        updateButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 18));
        updateButton.setBackground(Color.MAGENTA);
        updateButton.setBounds(683, 175, 167, 36);
        searchNameField = new JTextField(20);
        idField = new JTextField(20);
        idField.setBounds(294, 0, 223, 23);
        nameField = new JTextField(20);
        nameField.setBounds(294, 48, 223, 23);
        sexField = new JTextField(20);
        sexField.setBounds(294, 95, 223, 23);
        qtyField = new JTextField(20);
        qtyField.setBounds(294, 143, 223, 23);
        priceField = new JTextField(20);
        priceField.setBounds(294, 188, 223, 23);
        phoneField = new JTextField(20);
        phoneField.setBounds(294, 233, 223, 23);

        // Add listeners
        searchButton.addActionListener(e -> searchDocumentByName());
        showAllButton.addActionListener(e -> loadDocuments());
        updateButton.addActionListener(e -> updateSelectedDocument());
        saveButton.addActionListener(e -> saveDocuments());

        // Handle table row selection to populate fields
        table.getSelectionModel().addListSelectionListener(e -> populateFieldsFromSelectedRow());

        // Layout panel
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBounds(0, 0, 886, 33);
        panel.add(saveButton);
        panel.add(new JLabel("Search by Name:"));
        panel.add(searchNameField);
        panel.add(searchButton);
        panel.add(showAllButton);

        JPanel updatePanel = new JPanel();
        updatePanel.setBounds(0, 356, 886, 307);
        idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 14));
        idLabel.setBackground(new Color(128, 128, 64));
        idLabel.setBounds(124, -1, 223, 23);
        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 14));
        nameLabel.setBounds(124, 47, 223, 23);
        sexLabel = new JLabel("Sex:");
        sexLabel.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 14));
        sexLabel.setBounds(124, 94, 223, 23);
        qtyLabel = new JLabel("Qty_Product:");
        qtyLabel.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 14));
        qtyLabel.setBounds(124, 142, 223, 23);
        priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 14));
        priceLabel.setBounds(124, 187, 223, 23);
        phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 14));
        phoneLabel.setBounds(124, 232, 223, 23);
        updatePanel.setLayout(null);

        updatePanel.add(idLabel);
        updatePanel.add(idField);
        updatePanel.add(nameLabel);
        updatePanel.add(nameField);
        updatePanel.add(sexLabel);
        updatePanel.add(sexField);
        updatePanel.add(qtyLabel);
        updatePanel.add(qtyField);
        updatePanel.add(priceLabel);
        updatePanel.add(priceField);
        updatePanel.add(phoneLabel);
        updatePanel.add(phoneField);
        updatePanel.add(updateButton);
        getContentPane().setLayout(null);

        getContentPane().add(scrollPane);
        getContentPane().add(panel);
        getContentPane().add(updatePanel);
        
        JButton btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Home frame = new Home();
				frame.setVisible(true);
			   dispose();
        	}
        });
        btnNewButton.setBackground(Color.MAGENTA);
        btnNewButton.setFont(new Font("Rockwell", Font.BOLD | Font.ITALIC, 18));
        btnNewButton.setBounds(683, 232, 167, 36);
        updatePanel.add(btnNewButton);

        loadButton = new JButton("Load Documents");
        loadButton.setForeground(new Color(0, 128, 255));
        scrollPane.setColumnHeaderView(loadButton);

        loadButton.addActionListener(e -> loadDocuments());
    }

    private void loadDocuments() {
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                tableModel.setRowCount(0); // Clear table before loading
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
            showErrorDialog("Error loading documents", e);
        }
    }

    private void saveDocuments() {
        List<Document> documents = new ArrayList<>();
        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

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
                showErrorDialog("Error writing to file", e);
            }
        } catch (Exception e) {
            showErrorDialog("Error connecting to MongoDB", e);
        }
    }

    private void searchDocumentByName() {
        String name = searchNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a document Name", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("Name", name);
            try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
                tableModel.setRowCount(0); // Clear table before displaying
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
            }
        } catch (Exception e) {
            showErrorDialog("Error searching document", e);
        }
    }

    private void updateSelectedDocument() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a document to update.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String name = nameField.getText().trim();
        String sex = sexField.getText().trim();
        String qty = qtyField.getText().trim();
        String price = priceField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || sex.isEmpty() || qty.isEmpty() || price.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Document updatedDocument = new Document();
        if (!id.isEmpty()) updatedDocument.append("ID", id);
        if (!name.isEmpty()) updatedDocument.append("Name", name);
        if (!sex.isEmpty()) updatedDocument.append("Sex", sex);
        if (!qty.isEmpty()) updatedDocument.append("Qty_Product", qty);
        if (!price.isEmpty()) updatedDocument.append("Price", price);
        if (!phone.isEmpty()) updatedDocument.append("Phone_Number", phone);

        try (var mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Only update the document fields that have been changed
            Document updateFields = new Document("$set", updatedDocument);
            collection.updateOne(new Document("ID", id), updateFields);
            JOptionPane.showMessageDialog(this, "Document updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Refresh only the selected row in the table
            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(sex, selectedRow, 2);
            tableModel.setValueAt(qty, selectedRow, 3);
            tableModel.setValueAt(price, selectedRow, 4);
            tableModel.setValueAt(phone, selectedRow, 5);
        } catch (Exception e) {
            showErrorDialog("Error updating document", e);
        }
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            sexField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            qtyField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            priceField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            phoneField.setText(tableModel.getValueAt(selectedRow, 5).toString());
        }
    }

    private void showErrorDialog(String message, Exception e) {
        JOptionPane.showMessageDialog(this, message + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Update frame = new Update();
            frame.setVisible(true);
        });
    }
}
