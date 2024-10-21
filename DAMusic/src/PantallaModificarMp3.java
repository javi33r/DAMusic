import com.mpatric.mp3agic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PantallaModificarMp3 extends JPanel {

    private JTextField songTitleField;
    private JTextField artistField;
    private JTextField imageField;
    private JButton addButton;
    private JButton browseMp3Button;
    private JButton browseImageButton;
    private File selectedMp3File = null;
    private File selectedImageFile = null;

    public PantallaModificarMp3() {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
    }

    // Crear el panel principal con los campos de texto y botones
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Configurar el fondo del panel en color negro
        panel.setBackground(Color.BLACK);

        // Etiqueta para el título de la canción
        JLabel songTitleLabel = new JLabel("Song Title");
        songTitleLabel.setForeground(Color.WHITE);  // Texto blanco
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(songTitleLabel, gbc);

        // Campo de texto para el título de la canción
        songTitleField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(songTitleField, gbc);

        // Etiqueta para el artista
        JLabel artistLabel = new JLabel("Artist");
        artistLabel.setForeground(Color.WHITE);  // Texto blanco
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(artistLabel, gbc);

        // Campo de texto para el artista
        artistField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(artistField, gbc);

        // Etiqueta para la imagen
        JLabel imageLabel = new JLabel("Album Cover Image");
        imageLabel.setForeground(Color.WHITE);  // Texto blanco
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(imageLabel, gbc);

        // Campo de texto para la imagen
        imageField = new JTextField(20);
        imageField.setEditable(false);  // Campo no editable
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(imageField, gbc);

        // Botón para buscar la imagen
        browseImageButton = new JButton("Browse Image");
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(browseImageButton, gbc);
        browseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });

        // Botón para buscar el archivo MP3
        browseMp3Button = new JButton("Browse MP3");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(browseMp3Button, gbc);
        browseMp3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseMp3File();
            }
        });

        // Botón "ADD" en amarillo con texto negro
        addButton = new JButton("ADD");
        addButton.setBackground(Color.YELLOW);  // Fondo amarillo
        addButton.setForeground(Color.BLACK);   // Letras negras
        addButton.setFont(new Font("POPPINS", Font.BOLD, 16));  // Fuente grande y negrita
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;  // Centrar botón
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMp3MetadataAndRename();
            }
        });

        return panel;
    }

    // Método para elegir una imagen (png o jpg)
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "png", "jpg"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            imageField.setText(selectedImageFile.getAbsolutePath());
        }
    }

    // Método para elegir un archivo MP3
    private void chooseMp3File() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Audio files", "mp3"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedMp3File = fileChooser.getSelectedFile();
        }
    }

    // Método para actualizar los metadatos del archivo MP3 y renombrarlo
    private void updateMp3MetadataAndRename() {
        if (selectedMp3File == null || selectedImageFile == null) {
            JOptionPane.showMessageDialog(this, "Please select both an MP3 file and an image file.");
            return;
        }

        try {
            Mp3File mp3File = new Mp3File(selectedMp3File);

            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3File.getId3v2Tag();

                // Modificar el título de la canción y el artista
                String newTitle = songTitleField.getText();
                id3v2Tag.setTitle(newTitle);
                id3v2Tag.setArtist(artistField.getText());

                // Cargar la imagen seleccionada y actualizarla
                byte[] imageData = Files.readAllBytes(selectedImageFile.toPath());
                id3v2Tag.setAlbumImage(imageData, "image/jpeg");

                // Crear el archivo modificado con el nuevo nombre
                String newFileName = selectedMp3File.getParent() + File.separator + newTitle.replaceAll("[^a-zA-Z0-9]", "_") + ".mp3";
                mp3File.save(newFileName);

                // Verificar si el archivo con el nuevo nombre ya existe
                File newFile = new File(newFileName);
                if (newFile.exists()) {
                    // Eliminar el archivo original
                    if (selectedMp3File.delete()) {
                        JOptionPane.showMessageDialog(this, "MP3 metadata updated and file renamed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Could not delete the old MP3 file.");
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "The selected MP3 file does not have ID3v2 tags.");
            }
        } catch (IOException | UnsupportedTagException | InvalidDataException | NotSupportedException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while updating the MP3 file.");
        }
    }

    // Método principal para ejecutar la interfaz
    public static void main(String[] args) {
        JFrame frame = new JFrame("MP3 Metadata Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PantallaModificarMp3());
        frame.pack();
        frame.setLocationRelativeTo(null);  // Centrar ventana
        frame.setVisible(true);
    }
}