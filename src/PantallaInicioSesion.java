import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PantallaInicioSesion extends JFrame {

    private ImagePanel imagePanel;
    private JButton flechaButton;  // Flecha como un objeto, no una imagen
    private BaseDatos baseDatos;  // Base de datos para validar usuarios

    public PantallaInicioSesion() {
        // Configuración de la ventana
        setTitle("Iniciar Sesión");
        setSize(1000, 1000);  // Tamaño inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana

        // Inicializar la base de datos
        baseDatos = new BaseDatos();

        // Crear el panel de imagen con fondo (usando ImagePanel)
        imagePanel = new ImagePanel(new ImageIcon("src/imagenes/pantallainiciosesion.png").getImage());
        imagePanel.setLayout(null);  // Usar posicionamiento manual

        // Crear la flecha personalizada con gráficos
        flechaButton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.BLACK);
                // Dibujar la flecha
                int[] xPoints = {15, 5, 15}; // Coordenadas X de los puntos
                int[] yPoints = {5, 15, 25}; // Coordenadas Y de los puntos
                g2d.fillPolygon(xPoints, yPoints, 3); // Dibujar la flecha como un triángulo
                g2d.drawLine(15, 15, 30, 15); // Línea horizontal que sigue la flecha
                g2d.dispose();
            }
        };
        flechaButton.setPreferredSize(new Dimension(40, 40)); // Tamaño de la flecha
        flechaButton.setBorderPainted(false);  // Sin borde
        flechaButton.setContentAreaFilled(false);  // Sin relleno
        flechaButton.setFocusPainted(false);  // Sin borde de enfoque

        // Listener para volver a la pantalla inicial
        flechaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PantallaInicial();  // Volver a la pantalla inicial
                dispose();  // Cerrar la pantalla actual
            }
        });

        // Campo de texto para el nombre de usuario
        JLabel userLabel = new JLabel("Nombre de Usuario:");
        JTextField userText = new JTextField(20);

        // Campo de texto para la contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordText = new JPasswordField(20);

        // Botón de inicio de sesión
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(Color.BLACK);  // Fondo negro
        loginButton.setForeground(Color.WHITE);  // Texto blanco
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));  // Tamaño y estilo de fuente
        loginButton.setBorderPainted(false);  // Quitar borde del botón

        // Añadir la acción para validar el inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = userText.getText();
                String contrasena = new String(passwordText.getPassword());

                // Validar si los campos están vacíos
                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Comprobar si el usuario existe en la base de datos
                boolean usuarioExiste = baseDatos.loginUsuario(usuario, contrasena);

                if (usuarioExiste) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Aquí puedes redirigir al usuario a la siguiente pantalla si lo deseas
                    dispose();  // Cerrar la pantalla actual
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Añadir los componentes al panel de imagen
        imagePanel.add(flechaButton);
        imagePanel.add(userLabel);
        imagePanel.add(userText);
        imagePanel.add(passwordLabel);
        imagePanel.add(passwordText);
        imagePanel.add(loginButton);

        // Añadir el panel de imagen a la ventana
        add(imagePanel, BorderLayout.CENTER);

        // Listener para redimensionar componentes según el tamaño de la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();

                // Reposicionar y redimensionar los componentes
                flechaButton.setBounds(10, 10, 40, 40);  // Flecha en la esquina superior izquierda
                userLabel.setBounds((width - 300) / 2, height / 3, 300, 30);
                userText.setBounds((width - 300) / 2, height / 3 + 40, 300, 30);
                passwordLabel.setBounds((width - 300) / 2, height / 3 + 90, 300, 30);
                passwordText.setBounds((width - 300) / 2, height / 3 + 130, 300, 30);
                loginButton.setBounds((width - 150) / 2, height / 3 + 180, 150, 40);
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicioSesion());
    }
}





