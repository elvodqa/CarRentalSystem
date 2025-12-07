package GUI;
import Database.Database;
import Models.Car;
import Models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserInterface extends JFrame
{
    private static Database db;
    private static JPanel cardPanel, card1, card2, card3, card4;
    private static JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9;
    private static JTextField text1, text2, text3, text4, text5, text6, text7;
    private static JButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private static JList <Car> listBox;
    private static JComboBox<Car> comboBox;
    private static DefaultListModel<Car> vehicles = new DefaultListModel<>();
    private static java.awt.CardLayout cardLayout = new java.awt.CardLayout();


    private UserInterface(Database db)
    {
        this.db = db;

        setTitle("User Interface");
        setSize(900,600);

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        card1 = new JPanel(new GridBagLayout());
        card2 = new JPanel(new GridBagLayout());
        card3 = new JPanel(new GridBagLayout());

        label1 = new JLabel("Email");
        label2 = new JLabel("Password");
        label3 = new JLabel("First Name");
        label4 = new JLabel("Last Name");
        label5 = new JLabel("Email Name");
        label6 = new JLabel("Password");
        label7 = new JLabel("List of Vehicles");
        label8 = new JLabel("Invoice");
        label9 = new JLabel("Vehicle Selection");

        button1 = new JButton("Go to Create Account");
        button2 = new JButton("Login");
        button3 = new JButton("Create Account");
        button4 = new JButton("Back to Home");
        button5 = new JButton("Rent Vehicle");
        button6 = new JButton("Search Vehicles");
        button7 = new JButton("Cancel Rental");
        button8 = new JButton("Generate Invoice");
        button9 = new JButton("Back to Login");

        text1 = new JTextField("",15);
        text2 = new JTextField("",15);
        text3 = new JTextField("",15);
        text4 = new JTextField("",15);
        text5 = new JTextField("",15);
        text6 = new JTextField("",15);
        text7 = new JTextField("",15);

        listBox = new JList<>(vehicles);
        listBox.setVisibleRowCount(-1);
        listBox.setVisible(true);
        listBox.setSize(100, 100);
        JScrollPane scrollPane = new JScrollPane(listBox);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(100,100);

        comboBox = new JComboBox<>();
        comboBox.setSelectedIndex(-1);
        comboBox.setVisible(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Insets in = new Insets(10,10,10,10);
        gbc.insets = in;

        Dimension fieldSize = new Dimension(200, 30);

        gbc.gridx = 0; gbc.gridy = 0;
        card1.add(label1,gbc);
        label1.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 0;
        card1.add(text1,gbc);
        text1.setPreferredSize(fieldSize);
        gbc.gridx = 0; gbc.gridy = 1;
        card1.add(label2,gbc);
        label2.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 1;
        card1.add(text2,gbc);
        text2.setPreferredSize(fieldSize);


        gbc.gridx = 0; gbc.gridy = 0;
        card2.add(label3,gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        card2.add(text3,gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        card2.add(label4,gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        card2.add(text4,gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        card2.add(label5, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        card2.add(text5, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        card2.add(label6, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        card2.add(text6, gbc);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Go to Create Account")
                {
                    cardLayout.show(cardPanel, "2");
                    text1.setText("");
                    text2.setText("");
                    text3.setText("");
                    text4.setText("");
                    text5.setText("");
                    text6.setText("");
                }
            }
        });
        button1.setPreferredSize(fieldSize);


        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Login")
                {
                    boolean validation;
                    if (text1.getText().isEmpty() || text2.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Error found in adding account.");
                        return;
                    }

                    validation = db.validateUser(text1.getText(), text2.getText());

                    if (validation == true)
                    {
                        cardLayout.show(cardPanel, "3");
                        // System.out.print(text1.getText());
                        text1.setText("");
                        text2.setText("");
                        text3.setText("");
                        text4.setText("");
                        text5.setText("");
                        text6.setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Login not found. Verify information or create an account.");
                    }
                }
            }
        });
        button2.setPreferredSize(fieldSize);

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Create Account")
                {
                    boolean validation;
                    if (text3.getText().isEmpty() || text4.getText().isEmpty() || text5.getText().isEmpty() || text6.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Error found in adding account.");
                    }
                    else
                    {
                        validation = db.createUser(text3.getText(), text4.getText(), text5.getText(), text6.getText());

                        if (validation == true)
                        {
                            JOptionPane.showMessageDialog(null, "Account sucessfully created!");
                            cardLayout.show(cardPanel, "1");
                            text1.setText("");
                            text2.setText("");
                            text3.setText("");
                            text4.setText("");
                            text5.setText("");
                            text6.setText("");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Error found in adding account.");
                        }
                    }
                }
            }
        });
        button3.setPreferredSize(fieldSize);

        button4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Back to Home")
                {
                    cardLayout.show(cardPanel, "1");
                    text1.setText("");
                    text2.setText("");
                    text3.setText("");
                    text4.setText("");
                    text5.setText("");
                    text6.setText("");
                }
            }
        });
        button4.setPreferredSize(fieldSize);

        button5.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Rent Vehicle")
                {
                    // comboBox.getSelectedItem()

                }
            }
        });
        button5.setPreferredSize(fieldSize);

        button6.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Search Vehicles")
                {
                    vehicles.clear();
                    comboBox.removeAllItems();
                    List<Car> cars = new ArrayList<>();
                    cars = db.getAllCars();

                    for (Car car : cars)
                    {
                        vehicles.addElement(car);
                        comboBox.addItem(car);
                    }

                    listBox.setModel(vehicles);
                }
            }
        });
        button6.setPreferredSize(fieldSize);

        button9.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String command = e.getActionCommand();

                if (command == "Back to Login")
                {

                }
            }
        });


        fieldSize = new Dimension(200, 30);


        gbc.gridx = 0; gbc.gridy = 2;
        card1.add(button1,gbc);
        button1.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 2;
        card1.add(button2,gbc);
        button2.setPreferredSize(fieldSize);

        gbc.gridx = 0; gbc.gridy = 4;
        card2.add(button3,gbc);
        button1.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 4;
        card2.add(button4,gbc);
        button2.setPreferredSize(fieldSize);

        gbc.gridx = 0; gbc.gridy = 0;
        card3.add(label7, gbc);
        gbc.gridx = 2; gbc.gridy = 0;
        card3.add(label8, gbc);
        fieldSize = new Dimension (100, 100);
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 1;
        card3.add(scrollPane, gbc);
        scrollPane.setPreferredSize(fieldSize);
        gbc.gridx = 2; gbc.gridy = 1;
        card3.add(text7, gbc);
        text7.setPreferredSize(fieldSize);
        gbc.gridwidth = 1;
        fieldSize = new Dimension (200, 30);
        gbc.gridx = 0; gbc.gridy = 2;
        card3.add(comboBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        card3.add(button5, gbc);
        button5.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 3;
        card3.add(button6,gbc);
        button6.setPreferredSize(fieldSize);
        gbc.gridx = 2; gbc.gridy = 3;
        card3.add(button7, gbc);
        button7.setPreferredSize(fieldSize);
        gbc.gridx = 3; gbc.gridy = 3;
        card3.add(button8, gbc);
        button8.setPreferredSize(fieldSize);
        gbc.gridwidth = 2;
        gbc.gridx = 1; gbc.gridy = 4;
        card3.add(button9, gbc);
        button9.setPreferredSize(fieldSize);
        gbc.gridwidth = 1;

        // Ask for a user's info function in backend

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
                UserInterface frame = new UserInterface(db);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    public void show (JPanel cards, String one)
    {

    }
}
