package sample;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TableView<users> table_users;

    @FXML
    private TableColumn<users, String> col_Name;

    @FXML
    private TableColumn<users, Integer> col_Age;

    @FXML
    private TableColumn<users, String> col_FVac;

    @FXML
    private TableColumn<users, String> col_SVac;

    @FXML
    private TableColumn<users, String> col_PInfec;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField no_age;

    @FXML
    private TextField txt_FVac;

    @FXML
    private TextField txt_SVac;

    @FXML
    private TextField txt_PrevI;

    @FXML
    private TextField filterField;

    ObservableList<users> listM;
    ObservableList<users> dataList;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;




    public void Add_users (){

        conn = mysqlconnect.ConnectDb();
        String sql = "insert into users (name, age, FVac, SVac, PrevI) values(?,?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setString(2, no_age.getText());
            pst.setString(3, txt_FVac.getText());
            pst.setString(4, txt_SVac.getText());
            pst.setString(5, txt_PrevI.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null,"User Successfully Added");
            UpdateTable();
            search_user();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /////method to select users//////
    @FXML
    void getSelected() {
        index = table_users.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }
        txt_name.setText(col_Name.getCellData(index).toString());
        no_age.setText(String.valueOf(col_Age.getCellData(index).intValue()));
        txt_FVac.setText(col_FVac.getCellData(index).toString());
        txt_SVac.setText(col_SVac.getCellData(index).toString());
        txt_PrevI.setText(col_PInfec.getCellData(index).toString());
    }


    public void Edit() {

        try{
            conn = mysqlconnect.ConnectDb();
            String value1 = txt_name.getText();
            int value2 = Integer.parseInt(no_age.getText());
            String value3 = txt_FVac.getText();
            String value4 = txt_SVac.getText();
            String value5 = txt_PrevI.getText();

            String sql = "update users set name='"+value1+"',age='"+value2+"',FVac='"+value3+"',SVac='"+value4+"',PrevI='"+value5+"' where name='"+value1+"'  ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"User Updated Successfully");
            UpdateTable();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Delete(){
        conn = mysqlconnect.ConnectDb();
        String sql = "delete from users where name = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Record Deleted Successfully");
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void search_user(){
        col_Age.setCellValueFactory(new PropertyValueFactory<users,Integer>("age"));
        col_Name.setCellValueFactory(new PropertyValueFactory<users,String>("name"));
        col_FVac.setCellValueFactory(new PropertyValueFactory<users,String>("FVac"));
        col_SVac.setCellValueFactory(new PropertyValueFactory<users,String>("SVac"));
        col_PInfec.setCellValueFactory(new PropertyValueFactory<users,String>("PrevI"));

        dataList = mysqlconnect.getDatausers();
        table_users.setItems(dataList);
        FilteredList<users> filteredData = new FilteredList<>(dataList,b -> true);

    filterField.textProperty().addListener((observable,oldValue,newValue) -> {
    filteredData.setPredicate(person -> {
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }
        String lowerCaseFilter = newValue.toLowerCase();

        if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1){
            return true;
        }
        else return false;
    });
    });

        SortedList<users> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    public void UpdateTable(){
        col_Age.setCellValueFactory(new PropertyValueFactory<users,Integer>("age"));
        col_Name.setCellValueFactory(new PropertyValueFactory<users,String>("name"));
        col_FVac.setCellValueFactory(new PropertyValueFactory<users,String>("FVac"));
        col_SVac.setCellValueFactory(new PropertyValueFactory<users,String>("SVac"));
        col_PInfec.setCellValueFactory(new PropertyValueFactory<users,String>("PrevI"));

        listM = mysqlconnect.getDatausers();
        table_users.setItems(listM);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UpdateTable();
        search_user();

    }
}
