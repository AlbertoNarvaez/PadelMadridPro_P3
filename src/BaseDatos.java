import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {

    private Connection connect() {
        // Ruta a la base de datos SQLite
        String url = "jdbc:sqlite:usuarios.db"; // Asegúrate de que la ruta sea la correcta
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void crearTablaUsuarios() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nombre TEXT NOT NULL,\n"
                + " correo TEXT NOT NULL,\n"
                + " contrasena TEXT NOT NULL,\n"
                + " telefono TEXT NOT NULL,\n"
                + " fechaNacimiento TEXT NOT NULL\n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUser(String nombre, String correo, String contrasena, String telefono, String fechaNacimiento) {
        String sql = "INSERT INTO usuarios(nombre, correo, contrasena, telefono, fechaNacimiento) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, contrasena);
            pstmt.setString(4, telefono);
            pstmt.setString(5, fechaNacimiento);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean loginUsuario(String usuario, String contrasena) {
        boolean existe = false;
        String query = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Establecer los parámetros del query
            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);

            ResultSet rs = pstmt.executeQuery();

            // Si se encuentra un resultado, el usuario existe
            if (rs.next()) {
                existe = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return existe;
    }

    // Método para consultar todos los usuarios registrados
    public void consultarUsuarios() {
        String sql = "SELECT nombre, correo, telefono, fechaNacimiento FROM usuarios";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Usuarios registrados:");
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Correo: " + rs.getString("correo"));
                System.out.println("Teléfono: " + rs.getString("telefono"));
                System.out.println("Fecha de Nacimiento: " + rs.getString("fechaNacimiento"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        // Este método se puede usar para cerrar la conexión o limpiar si es necesario
    }
}

