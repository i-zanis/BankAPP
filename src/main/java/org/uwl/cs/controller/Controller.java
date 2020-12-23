package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uwl.cs.ConnectionClass;

import java.sql.*;

public  class Controller {
    public TextField textfield;
    public Label label;


    public void press(ActionEvent event) throws Exception {
        label.setText(textfield.getText());
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.initializeDB();
        // the textfield will convert into sql
        String sql = "INSERT INTO USER VALUES('" + textfield.getText() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        sql = "SELECT * FROM USER;";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            // statement execute updates database, when
            label.setText(resultSet.getString(1));
        }
    }
}

