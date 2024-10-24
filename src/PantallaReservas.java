import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PantallaReservas extends JFrame {
    private String selectedDay = null;  // Día seleccionado
    private String selectedHour = null; // Hora seleccionada

    public PantallaReservas(String nombrePista, String imagenRuta, String usuario) {
        // Configuración de la ventana
        setTitle("Reservar " + nombrePista);
        setSize(1000, 1000);  // Ajustamos el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana
        setLayout(new BorderLayout());

        // Panel superior con el mensaje de selección
        JLabel seleccionLabel = new JLabel("Has seleccionado la " + nombrePista, SwingConstants.CENTER);
        seleccionLabel.setFont(new Font("Arial", Font.BOLD, 20));  // Estilo y tamaño de fuente
        add(seleccionLabel, BorderLayout.NORTH);

        // Panel de la imagen de la pista
        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new FlowLayout(FlowLayout.CENTER));  // Centramos la imagen

        // Imagen de la pista seleccionada (más pequeña)
        ImageIcon imagenPista = new ImageIcon(getClass().getResource(imagenRuta));
        Image imagen = imagenPista.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);  // Redimensionar la imagen
        JLabel imagenLabel = new JLabel(new ImageIcon(imagen));
        panelImagen.add(imagenLabel);

        // Añadir la imagen al panel superior
        add(panelImagen, BorderLayout.CENTER);

        // Panel para el mensaje "¡Escoge día/hora y juegue ya!" justo debajo de la imagen
        JLabel mensajeLabel = new JLabel("¡Escoge día/hora y juegue ya!", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(mensajeLabel, BorderLayout.AFTER_LAST_LINE);

        // Panel con el horario (ajustado)
        JPanel panelHorario = new JPanel();
        panelHorario.setLayout(new GridLayout(8, 7, 5, 5));  // 8 filas, 7 columnas, espacio entre celdas

        // Días de la semana
        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        for (String dia : diasSemana) {
            JLabel diaLabel = new JLabel(dia, SwingConstants.CENTER);
            diaLabel.setFont(new Font("Arial", Font.BOLD, 14));  // Texto de los días en negrita
            diaLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));  // Borde para hacer clic

            // Listener para seleccionar el día
            diaLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedDay = diaLabel.getText();
                    JOptionPane.showMessageDialog(null, "Has seleccionado el día: " + selectedDay);
                }
            });

            panelHorario.add(diaLabel);
        }

        // Horas para cada día (solo permite seleccionar si se ha seleccionado un día)
        String[][] horasPorDia = {
                {"9:00", "12:30", "16:00", "20:00",}, // Lunes
                {"9:30", "13:00", "16:30", "20:30",}, // Martes
                {"10:00","13:30", "17:00", "21:00",}, // Miércoles
                {"10:30", "14:00", "17:30", "21:30",}, // Jueves
                {"11:00", "14:30", "18:00", "22:00",}, // Viernes
                {"11:30", "15:00", "18:30", "22:30",}, // Sábado
                {"12:00", "15:30", "19:00", "23:00",}  // Domingo
        };

        // Añadir las horas al panel como etiquetas clicables solo si se selecciona un día
        for (int i = 0; i < horasPorDia.length; i++) {
            for (int j = 0; j < horasPorDia[i].length; j++) {
                JLabel horaLabel = new JLabel(horasPorDia[i][j], SwingConstants.CENTER);
                horaLabel.setFont(new Font("Arial", Font.PLAIN, 14));  // Fuente ajustada para las horas
                horaLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));  // Borde alrededor de la celda
                horaLabel.setOpaque(true);  // Para poder darle color en el clic

                // Listener para hacer las horas clicables solo si se ha seleccionado un día
                horaLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (selectedDay == null) {
                            JOptionPane.showMessageDialog(null, "Primero debes seleccionar un día.");
                        } else {
                            selectedHour = horaLabel.getText();  // Almacenar la hora seleccionada
                            // Mostrar mensaje de confirmación
                            int confirm = JOptionPane.showConfirmDialog(null, "Has seleccionado: " + selectedDay + " a las " + selectedHour + ". ¿Deseas continuar?", "Confirmar selección", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                // Ir a la pantalla de confirmación
                                new PantallaConfirmacion(usuario, nombrePista, selectedDay, selectedHour, imagenRuta);
                                dispose();  // Cerrar la pantalla de reservas
                            }
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        horaLabel.setBackground(Color.LIGHT_GRAY);  // Color al pasar el mouse
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        horaLabel.setBackground(null);  // Volver al color original
                    }
                });

                panelHorario.add(horaLabel);  // Añadir la etiqueta al panel
            }
        }

        // Añadir el panel de horario en la parte inferior
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(mensajeLabel, BorderLayout.NORTH);
        panelCentro.add(panelHorario, BorderLayout.CENTER);
        add(panelCentro, BorderLayout.SOUTH);

        // Hacer visible la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaReservas("Pista 4", "/imagenes/pista4.png", "UsuarioEjemplo"));
    }
}

