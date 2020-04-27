



import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Europe/Moscow&useSSL=false";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "password";

    static Connection conn;


    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        initDB();
        while (true) {
            System.out.println("1: add client");
            System.out.println("2: add product");
            System.out.println("3: add orders");

            System.out.print("-> ");

            String s = sc.nextLine();
            switch (s) {
                case "1":
                    addClient();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    addOrders();
                    break;
                default:
                    return;
            }
        }
    }

    private static void initDB() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("drop table  if exists Customers, Products, Orders");
            st.execute("create  table Customers(id int not null AUTO_INCREMENT   primary key, name  varchar (100) not null,phone  varchar (13) not null,adress varchar (150))");
            st.execute("create table Products(id int not null AUTO_INCREMENT primary key , Description varchar (100), Detalis text, Price double )");
            st.execute("create table Orders(id int not null AUTO_INCREMENT primary key ,product_id int not null,qty int,customer_id int not null ,FOREIGN KEY (product_id) REFERENCES Products (id), foreign key (customer_id) references Customers(id))");
        }
    }

    private static void addProduct() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter description: ");
        String description = sc.nextLine();
        System.out.print("Enter detalis: ");
        String detalis = sc.nextLine();
        Scanner sc1 = new Scanner(System.in);
        System.out.print("Enter price: ");
        double price = sc1.nextDouble();
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (description, detalis,price) VALUES(?, ?, ?)")) {
            ps.setString(1, description);
            ps.setString(2, detalis);
            ps.setDouble(3, price);
            ps.executeUpdate();
        }
    }

    private static void addClient() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter client name: ");
        String name = sc.nextLine();
        System.out.print("Enter client phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter client adress: ");
        String adress = sc.nextLine();
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Customers (name, phone,adress) VALUES(?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, adress);
            ps.executeUpdate();
        }
    }



    private static void addOrders() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter yours id ");
        int id = sc.nextInt();
        try (Statement st = conn.createStatement()){
                    System.out.println("select a product and enter a product id");
                    int productid = sc.nextInt();
                    System.out.println("enter qty");
                    int qty = sc.nextInt();
                    st.execute("INSERT INTO orders(product_id,qty,customer_id ) value (" + "'" + productid + "', " + "'" + qty + "'," + "'" + id + "')");
                }

    }
}
