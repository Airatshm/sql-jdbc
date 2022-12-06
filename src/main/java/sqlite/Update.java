package sqlite;

import java.sql.*;

public class Update {

    /**
     * Connect to the sq.db database
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
        String sql = "select * from Users";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

                 // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("phone"));
                rs.updateString("phone", "212646565");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  void update() throws SQLException {
        String sql =  "update Users set name =? where id=?";
        try (Connection conn = this.connect()) {
        PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "Henry");
            stmt.setInt(2, 2);
            stmt.executeUpdate();
            System.out.println("Database updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Update app = new Update();
        app.selectAll();
    }
}

