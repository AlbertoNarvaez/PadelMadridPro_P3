import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaInicial extends JFrame {

    private ImagePanel imagePanel;

    public PantallaInicial() {
        // Configuración de la ventana
        setTitle("Pantalla Inicial");
        setSize(1000, 1000);  // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana

        // Crear el panel de imagen personalizado usando la clase ImagePanel
        imagePanel = new ImagePanel(new ImageIcon("src/imagenes/pantallainicial.png").getImage());
        imagePanel.setLayout(new GridBagLayout());  // Usar GridBagLayout para centrar los botones

        // Configurar GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);  // Espaciado entre botones
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;  // Posición relativa (una debajo de la otra)
        gbc.anchor = GridBagConstraints.CENTER;  // Centrar los botones

        // Crear el botón de "Iniciar Sesión"
        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.setBackground(new Color(255, 87, 34));  // Fondo naranja
        iniciarSesionButton.setForeground(Color.WHITE);  // Texto blanco+
        iniciarSesionButton.setFont(new Font("Arial", Font.BOLD, 16));  // Tamaño de fuente
        iniciarSesionButton.setBorderPainted(false);  // Quitar borde
        iniciarSesionButton.setFocusPainted(false);  // Quitar borde de enfoque

        // Listener para cambiar a la pantalla de inicio de sesión
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PantallaInicioSesion();  // Ir a PantallaInicioSesion
                dispose();  // Cerrar la pantalla actual
            }
        });

        // Crear el botón de "Registrarse"
        JButton registrarseButton = new JButton("Registrarse");
        registrarseButton.setBackground(new Color(255, 87, 34));  // Fondo naranja
        registrarseButton.setForeground(Color.WHITE);  // Texto blanco
        registrarseButton.setFont(new Font("Arial", Font.BOLD, 16));  // Tamaño de fuente
        registrarseButton.setBorderPainted(false);  // Quitar borde
        registrarseButton.setFocusPainted(false);  // Quitar borde de enfoque

        // Acción del botón "Registrarse" para abrir la pantalla de registro
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PantallaRegistro();  // Abrir la ventana de registro
                dispose();  // Cerrar la pantalla actual
            }
        });

        // Añadir los botones al panel con las restricciones del GridBagLayout
        imagePanel.add(iniciarSesionButton, gbc);
        imagePanel.add(registrarseButton, gbc);

        // Añadir el panel a la ventana
        add(imagePanel);

        // Hacer visible la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ejecutar la aplicación
        SwingUtilities.invokeLater(() -> new PantallaInicial());
    }
}
