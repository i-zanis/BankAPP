package org.uwl.cs.controller;

public class RegistrationController {

    /**
    public void addAccount (){
        Connection connection = ConnectionClass.connectDB();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "insert into users (username,password,email,type)values(?,?,?,? )";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_password.getText());
            pst.setString(3, txt_email.getText());
            pst.setString(4, txt_type.getText());
            pst.execute();

            /*
            JOptionPane.showMessageDialog(null, "Users Add succes");
            UpdateTable();
            search_user();



        } catch (Exception e) {
            // Dialog.show(null, e);

        }
     */
}
