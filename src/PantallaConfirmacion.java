import javax.swing.*;
import java.awt.*;

public class PantallaConfirmacion extends JFrame {

    private String nombrePista;
    private String imagenRuta;
    private String dia;
    private String hora;
    private String usuario;

    public PantallaConfirmacion(String nombrePista, String imagenRuta, String dia, String hora, String usuario) {
        this.nombrePista = nombrePista;
        this.imagenRuta = imagenRuta;
        this.dia = dia;
        this.hora = hora;
        this.usuario = usuario;

        // Configuración de la ventana
        setTitle("Confirmación de Reserva");
        setSize(800, 600);  // Ajustamos el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana
        setLayout(new BorderLayout());

        // Panel superior con la pregunta
        JLabel preguntaLabel = new JLabel("¿Estás seguro de que quieres reservar?", SwingConstants.CENTER);
        preguntaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(preguntaLabel, BorderLayout.NORTH);

        // Panel central para la imagen y los detalles
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Imagen de la pista seleccionada
        ImageIcon imagenPista = new ImageIcon(getClass().getResource(imagenRuta));
        Image imagen = imagenPista.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);  // Redimensionar la imagen
        JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
        panelCentral.add(imagenLabel, BorderLayout.CENTER);

        // Panel inferior para los detalles y el botón
        JPanel panelDetalles = new JPanel(new GridLayout(4, 1, 10, 10));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Etiquetas para los detalles de la reserva
        JLabel pistaLabel = new JLabel("Pista seleccionada: " + nombrePista, SwingConstants.CENTER);
        JLabel diaLabel = new JLabel("Día: " + dia, SwingConstants.CENTER);
        JLabel horaLabel = new JLabel("Hora: " + hora, SwingConstants.CENTER);
        JLabel usuarioLabel = new JLabel("Usuario: " + usuario, SwingConstants.CENTER);

        // Añadir las etiquetas al panel
        panelDetalles.add(pistaLabel);
        panelDetalles.add(diaLabel);
        panelDetalles.add(horaLabel);
        panelDetalles.add(usuarioLabel);

        // Añadir el panel de detalles al panel central
        panelCentral.add(panelDetalles, BorderLayout.SOUTH);
        add(panelCentral, BorderLayout.CENTER);

        // Botón de confirmar reserva
        JButton confirmarButton = new JButton("Confirmar Reserva");
        confirmarButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmarButton.setFocusPainted(false);
        confirmarButton.setContentAreaFilled(false);  // Sin relleno
        confirmarButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // Solo el borde
        confirmarButton.addActionListener(e -> {
            // Acción de confirmación de reserva
            JOptionPane.showMessageDialog(null, "Reserva confirmada para la " + nombrePista + " el día " + dia + " a las " + hora);

            // Restablecer los valores después de la confirmación
            resetData();

            // Cerrar la ventana actual después de confirmar
            dispose();
        });

        // Añadir el botón a la parte inferior de la ventana
        add(confirmarButton, BorderLayout.SOUTH);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para restablecer los valores seleccionados
    private void resetData() {
        this.nombrePista = null;
        this.imagenRuta = null;
        this.dia = null;
        this.hora = null;
        this.usuario = null;

        // Aquí podrías restablecer cualquier otro valor que necesites
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaConfirmacion("Pista 2", "/imagenes/pista2.png", "Lunes", "13:30", "UsuarioEjemplo"));
    }
}
