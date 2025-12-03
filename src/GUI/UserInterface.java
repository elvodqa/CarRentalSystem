package GUI;


import Database.Database;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface implements ActionListener {
    private Database db;

    public UserInterface(Database db){
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
