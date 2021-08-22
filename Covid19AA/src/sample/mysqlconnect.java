package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mysqlconnect {
    Connection conn = null;

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/person1","root","password");
            JOptionPane.showMessageDialog(null,"ConnectionEstablished");
            return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }

    public  static ObservableList<users> getDatausers(){

        Connection conn = ConnectDb();
        ObservableList<users> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new users(Integer.parseInt(rs.getString("age")), rs.getString("name"), rs.getString("FVac"), rs.getString("SVac"), rs.getString("PrevI")));
            }
        } catch (Exception e){

        }
        return list;
    }
}
