import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.regex.Pattern;

public class PantallaRegistro extends JFrame {

    private ImagePanel imagePanel;
    private JButton flechaButton;  // Flecha como un objeto, no una imagen
    private BaseDatos baseDatos;  // Base de datos para gestionar usuarios

    public PantallaRegistro() {
        // Configuración de la ventana
        setTitle("Registrarse");
        setSize(1000, 1000);  // Tamaño inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana

        // Inicializar la base de datos
        baseDatos = new BaseDatos();

        // Crear el panel de imagen con fondo (usando la misma imagen de PantallaInicioSesion)
        imagePanel = new ImagePanel(new ImageIcon("src/imagenes/pantallainiciosesion.png").getImage());
        imagePanel.setLayout(null);  // Usar posicionamiento manual

        // Botón de flecha para volver atrás (representada como un objeto)
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

        // Campos de texto para el registro
        JLabel nameLabel = new JLabel("Nombre y Apellidos:");
        JTextField nameText = new JTextField(20);
        JLabel emailLabel = new JLabel("Correo Electrónico:");
        JTextField emailText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordText = new JPasswordField(20);
        JLabel confirmPasswordLabel = new JLabel("Confirmar Contraseña:");
        JPasswordField confirmPasswordText = new JPasswordField(20);
        JLabel phoneLabel = new JLabel("Teléfono:");
        JTextField phoneText = new JTextField(20);
        JLabel birthdateLabel = new JLabel("Fecha de Nacimiento (dd/mm/yyyy):");
        JTextField birthdateText = new JTextField(20);

        // Botón de registro
        JButton registerButton = new JButton("Registrarse");
        registerButton.setBackground(Color.BLACK);  // Fondo negro
        registerButton.setForeground(Color.WHITE);  // Texto blanco
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));  // Tamaño y estilo de fuente
        registerButton.setBorderPainted(false);  // Quitar borde del botón

        // Acción al presionar el botón de registro
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());
                String phone = phoneText.getText();
                String birthdate = birthdateText.getText();

                // Validación del nombre y apellidos (al menos dos espacios)
                if (name.chars().filter(ch -> ch == ' ').count() < 2) {
                    JOptionPane.showMessageDialog(null, "El nombre y apellidos están incompletos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación del correo electrónico
                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(null, "El correo electrónico debe contener un '@'.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación de la contraseña
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación del número de teléfono (exactamente 9 dígitos)
                if (!phone.matches("\\d{9}")) {
                    JOptionPane.showMessageDialog(null, "El número de teléfono debe contener 9 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación de la fecha de nacimiento (formato dd/mm/yyyy)
                if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", birthdate)) {
                    JOptionPane.showMessageDialog(null, "La fecha de nacimiento debe tener el formato dd/mm/yyyy.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar usuario en la base de datos
                baseDatos.insertUser(name, email, password, phone, birthdate);
                baseDatos.close(); // Cerrar la conexión a la base de datos

                // Si todas las validaciones pasan, mostrar un mensaje de éxito
                JOptionPane.showMessageDialog(null, "Registro exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Ir a la pantalla de inicio de sesión
                dispose();
                new PantallaInicioSesion();
            }
        });

        // Añadir los componentes al panel de imagen
        imagePanel.add(flechaButton);
        imagePanel.add(nameLabel);
        imagePanel.add(nameText);
        imagePanel.add(emailLabel);
        imagePanel.add(emailText);
        imagePanel.add(passwordLabel);
        imagePanel.add(passwordText);
        imagePanel.add(confirmPasswordLabel);
        imagePanel.add(confirmPasswordText);
        imagePanel.add(phoneLabel);
        imagePanel.add(phoneText);
        imagePanel.add(birthdateLabel);
        imagePanel.add(birthdateText);
        imagePanel.add(registerButton);

        // Añadir el panel de imagen a la ventana
        add(imagePanel, BorderLayout.CENTER);

        // Listener para redimensionar componentes según el tamaño de la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();

                // Reposicionar y redimensionar los componentes
                int offsetY = 100; // Desplazamos más los campos hacia abajo
                flechaButton.setBounds(10, 10, 40, 40);  // Flecha en la esquina superior izquierda
                nameLabel.setBounds((width - 300) / 2, height / 4 + offsetY, 300, 30);
                nameText.setBounds((width - 300) / 2, height / 4 + 40 + offsetY, 300, 30);
                emailLabel.setBounds((width - 300) / 2, height / 4 + 80 + offsetY, 300, 30);
                emailText.setBounds((width - 300) / 2, height / 4 + 120 + offsetY, 300, 30);
                passwordLabel.setBounds((width - 300) / 2, height / 4 + 160 + offsetY, 300, 30);
                passwordText.setBounds((width - 300) / 2, height / 4 + 200 + offsetY, 300, 30);
                confirmPasswordLabel.setBounds((width - 300) / 2, height / 4 + 240 + offsetY, 300, 30);
                confirmPasswordText.setBounds((width - 300) / 2, height / 4 + 280 + offsetY, 300, 30);
                phoneLabel.setBounds((width - 300) / 2, height / 4 + 320 + offsetY, 300, 30);
                phoneText.setBounds((width - 300) / 2, height / 4 + 360 + offsetY, 300, 30);
                birthdateLabel.setBounds((width - 300) / 2, height / 4 + 400 + offsetY, 300, 30);
                birthdateText.setBounds((width - 300) / 2, height / 4 + 440 + offsetY, 300, 30);
                registerButton.setBounds((width - 150) / 2, height / 4 + 500 + offsetY, 150, 40);
            }
        });

        // Hacer visible la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaRegistro());
    }
}



