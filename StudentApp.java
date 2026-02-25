
import javax.swing.*;
import java.sql.*;

public class StudentApp extends JFrame {

    JTextField idField, nameField, courseField, marksField;
    JButton addBtn, viewBtn, updateBtn, deleteBtn;

     
    public static Connection getConnection() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC",
            "root",
            "1234"   // NO PASSWORD (you confirmed this)
        );

        System.out.println("DATABASE CONNECTED SUCCESSFULLY");
        return con;

    } catch (Exception e) {
        System.out.println("DATABASE CONNECTION FAILED");
        e.printStackTrace();
        return null;
    }
}

    public StudentApp() {
        setTitle("Student Management System");
        setSize(500, 400);
        setLayout(null);

        JLabel l0 = new JLabel("ID:");
        l0.setBounds(20, 20, 80, 30);
        add(l0);

        idField = new JTextField();
        idField.setBounds(100, 20, 200, 30);
        add(idField);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(20, 60, 80, 30);
        add(l1);

        nameField = new JTextField();
        nameField.setBounds(100, 60, 200, 30);
        add(nameField);

        JLabel l2 = new JLabel("Course:");
        l2.setBounds(20, 100, 80, 30);
        add(l2);

        courseField = new JTextField();
        courseField.setBounds(100, 100, 200, 30);
        add(courseField);

        JLabel l3 = new JLabel("Marks:");
        l3.setBounds(20, 140, 80, 30);
        add(l3);

        marksField = new JTextField();
        marksField.setBounds(100, 140, 200, 30);
        add(marksField);

        addBtn = new JButton("Add");
        addBtn.setBounds(50, 200, 80, 30);
        add(addBtn);

        viewBtn = new JButton("View");
        viewBtn.setBounds(150, 200, 80, 30);
        add(viewBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(250, 200, 90, 30);
        add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(360, 200, 90, 30);
        add(deleteBtn);

        addBtn.addActionListener(e -> addStudent());
        viewBtn.addActionListener(e -> viewStudents());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());

        setVisible(true);
    }

   void addStudent() {
    try (Connection con = getConnection()) {

        if (con == null) {
            JOptionPane.showMessageDialog(this, "Database not connected!");
            return;
        }

        String q = "INSERT INTO students(name, course, marks) VALUES(?,?,?)";
        PreparedStatement pst = con.prepareStatement(q);

        pst.setString(1, nameField.getText());
        pst.setString(2, courseField.getText());
        pst.setInt(3, Integer.parseInt(marksField.getText()));

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Student Added Successfully!");

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }
}

    void viewStudents() {
        try (Connection con = getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            StringBuilder sb = new StringBuilder("ID | Name | Course | Marks\n");
            while (rs.next()) {
                sb.append(rs.getInt("id")).append(" | ")
                  .append(rs.getString("name")).append(" | ")
                  .append(rs.getString("course")).append(" | ")
                  .append(rs.getInt("marks")).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void updateStudent() {
        try (Connection con = getConnection()) {
            String q = "UPDATE students SET name=?, course=?, marks=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, nameField.getText());
            pst.setString(2, courseField.getText());
            pst.setInt(3, Integer.parseInt(marksField.getText()));
            pst.setInt(4, Integer.parseInt(idField.getText()));

            int rows = pst.executeUpdate();
            if (rows > 0)
                JOptionPane.showMessageDialog(this, "Student Updated!");
            else
                JOptionPane.showMessageDialog(this, "ID not found!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void deleteStudent() {
        try (Connection con = getConnection()) {
            String q = "DELETE FROM students WHERE id=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setInt(1, Integer.parseInt(idField.getText()));

            int rows = pst.executeUpdate();
            if (rows > 0)
                JOptionPane.showMessageDialog(this, "Student Deleted!");
            else
                JOptionPane.showMessageDialog(this, "ID not found!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StudentApp();
    }
}
    

