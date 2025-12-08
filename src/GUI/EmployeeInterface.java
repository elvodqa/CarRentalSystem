package GUI;

import Database.Database;
import Models.Car;
import Models.RentalPeriod;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeInterface extends JFrame implements ActionListener {

    JComboBox<String> userCombo;
    DefaultListModel<String> userListModel;
    JList<String> userList;
    JButton banUserBtn, loadUsersBtn;

    JComboBox<String> carCombo;
    DefaultListModel<String> carListModel;
    JList<String> carList;
    JButton loadCarsBtn, addCarBtn, updatePriceBtn;

    JComboBox<Integer> rentalIdCombo;
    JButton loadRentalsBtn, genInvoiceBtn, cancelRentalBtn;
    JTextArea invoiceArea;

    Database db;

    public EmployeeInterface(Database database) {
        this.db = database;

        setTitle("Employee Interface");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        userCombo = new JComboBox<>();
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setVisibleRowCount(5);
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(200, 100));

        banUserBtn = new JButton("Ban Selected User");
        loadUsersBtn = new JButton("Load Users");

        carCombo = new JComboBox<>();
        carListModel = new DefaultListModel<>();
        carList = new JList<>(carListModel);
        carList.setVisibleRowCount(5);
        JScrollPane carScroll = new JScrollPane(carList);
        carScroll.setPreferredSize(new Dimension(200, 100));

        loadCarsBtn = new JButton("Load Cars");
        addCarBtn = new JButton("Add Car");
        updatePriceBtn = new JButton("Update Car Price");

        rentalIdCombo = new JComboBox<>();
        rentalIdCombo.setSelectedIndex(-1);
        loadRentalsBtn = new JButton("Load Rentals");
        genInvoiceBtn = new JButton("Generate Invoice");
        cancelRentalBtn = new JButton("Cancel Rental");
        invoiceArea = new JTextArea(6, 30);
        invoiceArea.setEditable(false);
        JScrollPane invoiceScroll = new JScrollPane(invoiceArea);

        g.gridx = 0;
        g.gridy = 0;
        add(new JLabel("Users"), g);
        g.gridx = 1;
        add(userCombo, g);

        g.gridx = 0;
        g.gridy = 1;
        add(new JLabel("User List"), g);
        g.gridx = 1;
        add(userScroll, g);

        g.gridx = 0;
        g.gridy = 2;
        add(loadUsersBtn, g);
        g.gridx = 1;
        add(banUserBtn, g);

        g.gridx = 0;
        g.gridy = 3;
        add(new JLabel("Cars"), g);
        g.gridx = 1;
        add(carCombo, g);

        g.gridx = 0;
        g.gridy = 4;
        add(new JLabel("Car List"), g);
        g.gridx = 1;
        add(carScroll, g);

        g.gridx = 0;
        g.gridy = 5;
        add(loadCarsBtn, g);

        g.gridx = 1;
        JPanel carBtnPanel = new JPanel();
        carBtnPanel.add(addCarBtn);
        carBtnPanel.add(updatePriceBtn);
        add(carBtnPanel, g);

        g.gridx = 0;
        g.gridy = 6;
        add(new JLabel("Rental ID"), g);
        g.gridx = 1;
        add(rentalIdCombo, g);
        g.gridx = 2;
        add(loadRentalsBtn, g);

        g.gridx = 0;
        g.gridy = 7;
        add(genInvoiceBtn, g);
        g.gridx = 1;
        add(cancelRentalBtn, g);

        g.gridx = 0;
        g.gridy = 8;
        g.gridwidth = 3;
        add(invoiceScroll, g);

        loadUsersBtn.addActionListener(this);
        banUserBtn.addActionListener(this);
        loadCarsBtn.addActionListener(this);
        addCarBtn.addActionListener(this);
        updatePriceBtn.addActionListener(this);
        loadRentalsBtn.addActionListener(this);
        genInvoiceBtn.addActionListener(this);
        cancelRentalBtn.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadUsersBtn) {
            userCombo.removeAllItems();
            userListModel.clear();
            List<User> users = db.getAllUsers();
            for (User u : users) {
                String email = u.email;
                userCombo.addItem(email);
                userListModel.addElement(email + " (" + u.firstName + " " + u.lastName + ")");
            }
        } else if (e.getSource() == banUserBtn) {
            int idx = userList.getSelectedIndex();
            if (idx == -1) {
                JOptionPane.showMessageDialog(this, "Select a user to ban.");
                return;
            }
            List<User> users = db.getAllUsers();
            User user = users.get(idx);
            if (db.banUser(user.id)) {
                JOptionPane.showMessageDialog(this, "User banned.");
                actionPerformed(new ActionEvent(loadUsersBtn, 0, ""));
            } else {
                JOptionPane.showMessageDialog(this, "Failed to ban user.");
            }
        } else if (e.getSource() == loadCarsBtn) {
            carCombo.removeAllItems();
            carListModel.clear();
            List<Car> cars = db.getAllCars();
            for (Car c : cars) {
                String name = c.name + " " + c.model;
                carCombo.addItem(name);
                carListModel.addElement(name + " - $" + c.pricePerDay);
            }
        } else if (e.getSource() == addCarBtn) {
            JTextField name = new JTextField();
            JTextField model = new JTextField();
            JTextField plate = new JTextField();
            JTextField color = new JTextField();
            JTextField price = new JTextField();
            Object[] message = {
                    "Name:", name,
                    "Model:", model,
                    "Plate:", plate,
                    "Color:", color,
                    "Price:", price
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Add Car", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    double p = Double.parseDouble(price.getText());
                    if (db.createCar(name.getText(), model.getText(), plate.getText(), color.getText(), p)) {
                        JOptionPane.showMessageDialog(this, "Car added.");
                        actionPerformed(new ActionEvent(loadCarsBtn, 0, ""));
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add car.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price.");
                }
            }
        } else if (e.getSource() == updatePriceBtn) {
            int idx = carList.getSelectedIndex();
            if (idx == -1) {
                JOptionPane.showMessageDialog(this, "Select a car to update.");
                return;
            }
            List<Car> cars = db.getAllCars();
            Car car = cars.get(idx);
            String price = JOptionPane.showInputDialog(this, "Enter new price for " + car.name + ":");
            if (price != null) {
                try {
                    double newPrice = Double.parseDouble(price);
                    if (db.updateCarPrice(car.id, newPrice)) {
                        JOptionPane.showMessageDialog(this, "Price updated.");
                        actionPerformed(new ActionEvent(loadCarsBtn, 0, ""));
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update price.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price.");
                }
            }
        } else if (e.getSource() == loadRentalsBtn) {
            rentalIdCombo.removeAllItems();
            List<RentalPeriod> rentals = db.getAllRentals();
            for (RentalPeriod r : rentals) {
                rentalIdCombo.addItem(r.id);
            }
        } else if (e.getSource() == genInvoiceBtn) {
            Integer rentalId = (Integer) rentalIdCombo.getSelectedItem();
            if (rentalId != null) {
                String invoice = db.generateInvoice(rentalId);
                invoiceArea.setText(invoice);
            } else {
                JOptionPane.showMessageDialog(this, "Select a rental ID.");
            }
        } else if (e.getSource() == cancelRentalBtn) {
            Integer rentalId = (Integer) rentalIdCombo.getSelectedItem();
            if (rentalId != null) {
                if (db.cancelRental(rentalId)) {
                    JOptionPane.showMessageDialog(this, "Rental cancelled.");
                    invoiceArea.setText("");
                    rentalIdCombo.removeItem(rentalId);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to cancel rental.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a rental ID.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Database db = new Database();
                new EmployeeInterface(db);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error initializing GUI:\n" + e.getMessage());
            }
        });
    }
}
