package sqlite;

import models.Contact;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Select {

    /**
     * Connect to the some.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jbdc:sdulite:some.db";
        Connection conn = null;
        try {
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        } finally {

        }
    }

    /**
     * select all users
     */
    public void selectAll() {
        String sql = "select u.id as \"Users->id\", u.name as \"Users->name\", c.id as \"contactId\", c.\"contactName\", c.phone, c.email from \"Users\" u left join \"Contacts\" c ON c.\"customerId\" = u.id order by \"Users->id\";";
        List<User> users = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            User tmpUser = null;
            List<Contact> tmpContacts = new ArrayList<>();
            while (rs.next()) {
                if (tmpUser != null && tmpUser.id != rs.getInt("Users->id")) {
                    tmpUser.contacts = tmpContacts;
                    users.add(tmpUser);
                    tmpUser = null;
                }
                tmpUser = new User(rs.getInt("Users->id"), rs.getString("Users->name"));
                tmpContacts.add(new Contact(rs.getInt("contactId"), rs.getInt("Users->id"), rs.getString("contactName"), rs.getString("phone"), rs.getString("email")));

            if (tmpUser != null) {
                tmpUser.contacts = tmpContacts;
                users.add(tmpUser);
            }
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("phone"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(users);
    }

    public static void main(String[] args) {
        Select app = new Select();
        app.selectAll();
    }
}

