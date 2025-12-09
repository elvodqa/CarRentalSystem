package GUI;
import Database.Database;
import Models.Car;
import Models.RentalPeriod;
import Models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;


public class UserInterface extends JFrame
{
    private static Database db;
    private static User user;
    private static JPanel cardPanel, card1, card2, card3, card4;

    private static JLabel emailLabel, passwordLabel, firstNameLabel, lastNameLabel, secondEmailLabel, secondPasswordLabel,
            vehicleListLabel, invoiceLabel, vehicleSelectionLabel;

    private static JTextField emailText, passwordText, firstNameText, lastNameText, secondEmailText, secondPasswordText;

    private static JButton createAccountScreenButton, loginButton, createAccountButton, fromAccountToHomeButton, rentVehicleButton,
            searchVehiclesButton, userInfoButton, generateInvoiceButton, backToHomeLogin;

    // private static JList <Car> listBox;
    private static JList <String> invoice;
    private static JComboBox<Integer> vehicleSelectionComboBox;
    private static DefaultListModel<Car> vehicles = new DefaultListModel<>();
    private static DefaultListModel<String> invoiceModel = new DefaultListModel<>();
    private static JTextArea vehicleArea, invoiceArea;
    private static java.awt.CardLayout cardLayout = new java.awt.CardLayout();


    private UserInterface(Database db, User initialUser)
    {
        this.db = db;
        this.user = initialUser;

        setTitle("User Interface");
        setSize(900,600);

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        card1 = new JPanel(new GridBagLayout());
        card2 = new JPanel(new GridBagLayout());
        card3 = new JPanel(new GridBagLayout());

        emailLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        secondEmailLabel = new JLabel("Email Name");
        secondPasswordLabel = new JLabel("Password");
        vehicleListLabel = new JLabel("List of Vehicle(s)");
        invoiceLabel = new JLabel("Invoice(s)");
        vehicleSelectionLabel = new JLabel("Vehicle Selection by ID");

        createAccountScreenButton = new JButton("Go to Create Account");
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");
        fromAccountToHomeButton = new JButton("Back to Home");
        rentVehicleButton = new JButton("Rent Vehicle");
        searchVehiclesButton = new JButton("Search Vehicles");
        userInfoButton = new JButton("User Info");
        generateInvoiceButton = new JButton("Generate Invoice");
        backToHomeLogin = new JButton("Back to Login");

        emailText = new JTextField("",15);
        passwordText = new JTextField("",15);
        firstNameText = new JTextField("",15);
        lastNameText = new JTextField("",15);
        secondEmailText = new JTextField("",15);
        secondPasswordText = new JTextField("",15);

        /*


        listBox = new JList<>(vehicles);
        listBox.setVisibleRowCount(-1);
        listBox.setVisible(true);
        listBox.setSize(100, 100);

         */

        vehicleArea = new JTextArea();
        vehicleArea.setVisible(true);
        vehicleArea.setEditable(false);

        JScrollPane vehicleScroll = new JScrollPane(vehicleArea);
        vehicleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        vehicleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        vehicleScroll.setSize(100,100);

        invoiceArea = new JTextArea();
        invoiceArea.setVisible(true);
        invoiceArea.setEditable(false);

        /*
        invoice = new JList<>(invoiceModel);
        invoice.setVisibleRowCount(-1);
        invoice.setVisible(true);
        invoice.setSize(100, 100);
        */

        JScrollPane invoiceScroll = new JScrollPane(invoiceArea);
        invoiceScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        invoiceScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        invoiceScroll.setSize(100,100);

        vehicleSelectionComboBox = new JComboBox<>();
        vehicleSelectionComboBox.setSelectedIndex(-1);
        vehicleSelectionComboBox.setVisible(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Insets in = new Insets(10,10,10,10);
        gbc.insets = in;

        Dimension fieldSize = new Dimension(200, 30);

        gbc.gridx = 0; gbc.gridy = 0;
        card1.add(emailLabel,gbc);
        emailLabel.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 0;
        card1.add(emailText,gbc);
        emailText.setPreferredSize(fieldSize);
        gbc.gridx = 0; gbc.gridy = 1;
        card1.add(passwordLabel,gbc);
        passwordLabel.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 1;
        card1.add(passwordText,gbc);
        passwordText.setPreferredSize(fieldSize);

        gbc.gridx = 0; gbc.gridy = 0;
        card2.add(firstNameLabel,gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        card2.add(firstNameText,gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        card2.add(lastNameLabel,gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        card2.add(lastNameText,gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        card2.add(secondEmailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        card2.add(secondEmailText, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        card2.add(secondPasswordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        card2.add(secondPasswordText, gbc);


        createAccountScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Go to Create Account")
                {
                    cardLayout.show(cardPanel, "2");
                    emailText.setText("");
                    passwordText.setText("");
                    firstNameText.setText("");
                    lastNameText.setText("");
                    secondEmailText.setText("");
                    secondPasswordText.setText("");
                }
            }
        });
        createAccountScreenButton.setPreferredSize(fieldSize);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Login")
                {
                    boolean validation;
                    if (emailText.getText().isEmpty() || passwordText.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Error found in adding account.");
                        return;
                    }

                    validation = db.validateUser(emailText.getText(), passwordText.getText());
                    if (validation == true)
                    {
                        JOptionPane.showMessageDialog(null, "Successfully logged in!");
                        initialUser.email = emailText.getText();
                        user = db.getUserByEmail(initialUser.email);
                        cardLayout.show(cardPanel, "3");
                        emailText.setText("");
                        passwordText.setText("");
                        firstNameText.setText("");
                        lastNameText.setText("");
                        secondEmailText.setText("");
                        secondPasswordText.setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Login not found. Verify information or create an account.");
                    }
                }
            }
        });
        loginButton.setPreferredSize(fieldSize);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Create Account")
                {
                    boolean validation;
                    if (firstNameText.getText().isEmpty() || lastNameText.getText().isEmpty() || secondEmailText.getText().isEmpty() || secondPasswordText.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Error found in adding account.");
                    }
                    else
                    {
                        validation = db.createUser(firstNameText.getText(), lastNameText.getText(), secondEmailText.getText(), secondPasswordText.getText());

                        if (validation == true)
                        {
                            JOptionPane.showMessageDialog(null, "Account sucessfully created!");
                            cardLayout.show(cardPanel, "1");
                            emailText.setText("");
                            passwordText.setText("");
                            firstNameText.setText("");
                            lastNameText.setText("");
                            secondEmailText.setText("");
                            secondPasswordText.setText("");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Error found in adding account.");
                        }
                    }
                }
            }
        });
        createAccountButton.setPreferredSize(fieldSize);

        fromAccountToHomeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Back to Home")
                {
                    cardLayout.show(cardPanel, "1");
                    emailText.setText("");
                    passwordText.setText("");
                    firstNameText.setText("");
                    lastNameText.setText("");
                    secondEmailText.setText("");
                    secondPasswordText.setText("");
                }
            }
        });
        fromAccountToHomeButton.setPreferredSize(fieldSize);

        rentVehicleButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Rent Vehicle")
                {
                    JTextField startDate = new JTextField();
                    JTextField endDate = new JTextField();

                    Object[] message = {"Start date:", startDate, "End date:", endDate};

                    int option = JOptionPane.showConfirmDialog(null, message, "Enter Values",
                            JOptionPane.OK_CANCEL_OPTION);

                    // Date.valueOf("2025-01-15");
                    // should be in this format

                    if (option == JOptionPane.OK_OPTION)
                    {
                        Date startDateSql = java.sql.Date.valueOf(startDate.getText());
                        Date endDateSql = java.sql.Date.valueOf(endDate.getText());

                        int carID = (int) vehicleSelectionComboBox.getSelectedItem();
                        boolean rentalCreated = db.createRentalPeriod(user.id, carID, startDateSql, endDateSql);

                        if (rentalCreated)
                            {
                                JOptionPane.showMessageDialog(null, "Rental created successfully!");
                            }
                        else
                            {
                                JOptionPane.showMessageDialog(null, "Error creating rental.");
                            }
                    }

                    else
                    {
                        System.out.println("User canceled");
                    }
                }
            }
        });
        rentVehicleButton.setPreferredSize(fieldSize);

        searchVehiclesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Search Vehicles")
                {

                    // vehicles.clear();
                    vehicleSelectionComboBox.removeAllItems();
                    List<Car> cars = db.getAllCars();
                    StringBuilder sb = new StringBuilder();

                    for (Car car : cars)
                    {
                        sb.append(car).append("\n");
                        vehicleSelectionComboBox.addItem(car.id);
                    }

                    vehicleArea.setText(sb.toString());
                }
            }
        });
        searchVehiclesButton.setPreferredSize(fieldSize);

        userInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "User Info")
                {
                    Object[] message = {"First Name: ", user.firstName, "Last Name: ", user.lastName,
                    "Email: ", user.email, "Password: ", user.password};

                    int option = JOptionPane.showConfirmDialog(null, message, "Account Information", JOptionPane.DEFAULT_OPTION);
                }
            }
        });
        userInfoButton.setPreferredSize(fieldSize);

        generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Generate Invoice")
                {
                    List<RentalPeriod> period = db.getAllRentalByUserId(user.id);
                    StringBuilder sb = new StringBuilder();

                    for (RentalPeriod rental : period) {
                        sb.append(db.generateInvoice(rental.id)).append("\n");
                    }

                    invoiceArea.setText(sb.toString());
                }
            }
        });
        generateInvoiceButton.setPreferredSize(fieldSize);


        backToHomeLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Back to Login")
                {
                    JOptionPane.showMessageDialog(null, "Logging out.");
                   // vehicles.clear();
                    vehicleScroll.removeAll();
                    invoiceScroll.removeAll();
                    // invoiceScroll.removeAll();
                    vehicleSelectionComboBox.removeAllItems();
                    cardLayout.show(cardPanel, "1");
                }
            }
        });


        fieldSize = new Dimension(200, 30);


        gbc.gridx = 0; gbc.gridy = 2;
        card1.add(createAccountScreenButton,gbc);
        createAccountScreenButton.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 2;
        card1.add(loginButton,gbc);
        loginButton.setPreferredSize(fieldSize);

        gbc.gridx = 0; gbc.gridy = 4;
        card2.add(createAccountButton,gbc);
        createAccountScreenButton.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 4;
        card2.add(fromAccountToHomeButton,gbc);
        loginButton.setPreferredSize(fieldSize);

        gbc.gridx = 0; gbc.gridy = 0;
        card3.add(vehicleListLabel, gbc);
        gbc.gridx = 2; gbc.gridy = 0;
        card3.add(invoiceLabel, gbc);
        fieldSize = new Dimension (200, 200);
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0; gbc.gridy = 1;
        card3.add(vehicleScroll, gbc);
        vehicleScroll.setPreferredSize(fieldSize);
        gbc.gridx = 2; gbc.gridy = 1;
        card3.add(invoiceScroll, gbc);
        invoiceScroll.setPreferredSize(fieldSize);
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fieldSize = new Dimension (200, 30);
        gbc.gridx = 0; gbc.gridy = 2;
        card3.add(vehicleSelectionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        card3.add(vehicleSelectionComboBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        card3.add(rentVehicleButton, gbc);
        rentVehicleButton.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 3;
        card3.add(searchVehiclesButton,gbc);
        searchVehiclesButton.setPreferredSize(fieldSize);
        gbc.gridx = 2; gbc.gridy = 3;
        card3.add(userInfoButton, gbc);
        userInfoButton.setPreferredSize(fieldSize);
        gbc.gridx = 3; gbc.gridy = 3;
        card3.add(generateInvoiceButton, gbc);
        generateInvoiceButton.setPreferredSize(fieldSize);
        gbc.gridwidth = 2;
        gbc.gridx = 1; gbc.gridy = 4;
        card3.add(backToHomeLogin, gbc);
        backToHomeLogin.setPreferredSize(fieldSize);
        gbc.gridwidth = 1;

        // Ask for a initialUser's info function in backend

        cardPanel.add(card1, "1");
        cardPanel.add(card2, "2");
        cardPanel.add(card3, "3");
        add(cardPanel, BorderLayout.CENTER);
    }



    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Database db = new Database();
                User user = new User();
                UserInterface frame = new UserInterface(db, user);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    public void show (JPanel cards, String one)
    {

    }
}
