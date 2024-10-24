import javax.swing.*;
import java.awt.*;

public class PantallaPista extends JFrame {

    private String usuario = "UsuarioEjemplo";  // Puedes cambiar esta variable por el nombre del usuario real

    public PantallaPista() {
        // Configuración de la ventana
        setTitle("Reservar Pista");
        setSize(1000, 1000);  // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana
        setLayout(new BorderLayout());

        // Título
        JLabel tituloLabel = new JLabel("RESERVA YA", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(tituloLabel, BorderLayout.NORTH);

        // Panel principal para las pistas
        JPanel panelPistas = new JPanel();
        panelPistas.setLayout(new GridLayout(2, 2, 20, 20));  // 2 filas, 2 columnas, espacio entre los componentes

        // Agregar paneles de cada pista
        panelPistas.add(crearPanelPista("Pista 1", "/imagenes/pista1.png", "Cristal"));
        panelPistas.add(crearPanelPista("Pista 2", "/imagenes/pista2.png", "Muro"));
        panelPistas.add(crearPanelPista("Pista 3", "/imagenes/pista3.png", "Cristal"));
        panelPistas.add(crearPanelPista("Pista 4", "/imagenes/pista4.png", "Cristal"));

        add(panelPistas, BorderLayout.CENTER);

        // Hacer visible la ventana
        setVisible(true);
    }

    private JPanel crearPanelPista(String nombrePista, String imagenRuta, String tipoPista) {
        // Crear un panel para cada pista
        JPanel panelPista = new JPanel();
        panelPista.setLayout(new BorderLayout());

        // Etiqueta con el nombre de la pista
        JLabel nombreLabel = new JLabel(nombrePista, SwingConstants.CENTER);
        panelPista.add(nombreLabel, BorderLayout.NORTH);

        // Imagen de la pista
        ImageIcon imagenPista = new ImageIcon(getClass().getResource(imagenRuta));  // Ruta de la imagen
        JLabel imagenLabel = new JLabel(imagenPista);
        panelPista.add(imagenLabel, BorderLayout.CENTER);

        // Etiqueta con el tipo de pista
        JLabel tipoLabel = new JLabel(tipoPista, SwingConstants.CENTER);
        panelPista.add(tipoLabel, BorderLayout.SOUTH);

        // Añadir listener para llevar a la pantalla de reservas
        panelPista.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new PantallaReservas(nombrePista, imagenRuta, usuario);  // Abrir la ventana de reservas con los detalles
                dispose();  // Cerrar la pantalla actual
            }
        });

        return panelPista;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaPista());
    }
}


