


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER this class is only to be extended
 */
public class Person {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private AccessLevel accessLevel;

    private void setId(String id) {
        
        this.id = id;
        
    }

    public String getId() {

        return this.id;
    }

    private void setAccessLevel(int accesscode) {

        /**
         * 0 = student 1 = teacher 2 = admin
         *
         */
        switch (accesscode) {
            case 0:
                this.accessLevel = AccessLevel.STUDENT;
                break;
            case 1:
                this.accessLevel = AccessLevel.TEACHER;
                break;
            case 2:
                this.accessLevel = AccessLevel.ADMIN;
                break;

        }

    }

    public AccessLevel getAccessLevel() {

        return this.accessLevel;
    }

    //this is called while login in
    public boolean login(String username, String password) throws SQLException, ClassNotFoundException {

        Connection connection = SimpleConnection.getConnection();

        String sqlQuery = "SELECT id, first_name, last_name, middle_name, access_level  "
                + "FROM person "
                + "WHERE id = ? and password = sha1(?) where access_level = '1' LIMIT 1";
        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
        pStatement.setString(1, username);
        pStatement.setString(2, password);

        ResultSet resultSet = pStatement.executeQuery();

        if (resultSet.next()) {

            setId(resultSet.getString("id"));
            setFirstName(resultSet.getString("first_name"));
            setLastName(resultSet.getString("last_name"));
            setMiddleName(resultSet.getString("middle_name"));
            setAccessLevel(resultSet.getInt("access_level"));

            connection.close();
            return true;
        }
        
        connection.close();

        return false;
    }

    //this is called to see results either by class or by student
    public void viewRecords(String subject) {

    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    private void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return lastName+" "+firstName+" "+middleName;
    }

}
