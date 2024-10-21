import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PantallaDentroPlaylist extends JFrame {

    private Font poppinsFont;
    private JPanel songsPanel;
    private JScrollPane scrollPane;
    private JTextField searchField;
    private List<String> canciones; // Lista original de canciones

    public PantallaDentroPlaylist() {
        // Carga de la fuente Poppins
        loadCustomFont();

        // Configuración del JFrame
        setTitle("DAMusic");
        setSize(1000, 700);  // Tamaño base, redimensionable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(28, 28, 28));  // Fondo negro

        // Panel superior (header)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(28, 28, 28));  // Color fondo oscuro
        headerPanel.setPreferredSize(new Dimension(1000, 80));

        // Logo y Nombre DAMusic
        JLabel logoLabel = new JLabel("🎵 DAMusic");
        logoLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Campo de búsqueda
        searchField = new JTextField("Buscar...");
        searchField.setPreferredSize(new Dimension(250, 40));
        searchField.setFont(poppinsFont.deriveFont(Font.PLAIN, 18));
        searchField.setForeground(Color.WHITE);
        searchField.setBackground(new Color(50, 50, 50));
        headerPanel.add(searchField, BorderLayout.CENTER);

        // Iconos de Menú
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconPanel.setBackground(new Color(28, 28, 28));
        JButton searchButton = new JButton("🔍");
        JButton menuButton = new JButton("☰");
        stylizeButton(searchButton);
        stylizeButton(menuButton);
        iconPanel.add(searchButton);
        iconPanel.add(menuButton);
        headerPanel.add(iconPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Panel central para Playlist principal
        JPanel playlistPanel = new JPanel();
        playlistPanel.setBackground(new Color(28, 28, 28));
        playlistPanel.setLayout(new BoxLayout(playlistPanel, BoxLayout.Y_AXIS));
        playlistPanel.setBorder(new EmptyBorder(20, 0, 20, 0));  // Espacio en el panel

        // Imagen de playlist y botón de reproducción
        JLabel playlistImage = new JLabel(new ImageIcon("src/images/playlist_cover.jpg"));
        playlistImage.setPreferredSize(new Dimension(300, 150));
        playlistPanel.add(playlistImage);

        JLabel playlistTitle = new JLabel("Chill Vibes");
        playlistTitle.setFont(poppinsFont.deriveFont(Font.BOLD, 24));
        playlistTitle.setForeground(Color.WHITE);
        playlistTitle.setAlignmentX(CENTER_ALIGNMENT);
        playlistPanel.add(playlistTitle);

        JButton playButton = new JButton("▶");
        stylizeOvalButton(playButton);
        playlistPanel.add(playButton);

        add(playlistPanel, BorderLayout.CENTER);

        // Panel inferior con la lista de canciones
        canciones = new ArrayList<>();
        canciones.add("Amorfoda");
        canciones.add("Si veo a tu mamá");
        canciones.add("Un coco");

        JPanel songsPanelWrapper = new JPanel();
        songsPanelWrapper.setBackground(new Color(28, 28, 28));
        songsPanelWrapper.setLayout(new GridBagLayout());

        songsPanel = new JPanel();
        songsPanel.setBackground(new Color(28, 28, 28));  // Fondo negro
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));

        // Cargar canciones iniciales
        updateSongList(canciones);

        // Envolver en JScrollPane
        scrollPane = new JScrollPane(songsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(new Color(28, 28, 28));  // Fondo negro

        songsPanelWrapper.add(scrollPane);
        add(songsPanelWrapper, BorderLayout.SOUTH);

        // Listener para buscar canciones
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().toLowerCase();
                filterSongs(searchTerm);
            }
        });
    }

    // Método para filtrar canciones según el término de búsqueda
    private void filterSongs(String searchTerm) {
        List<String> filteredSongs = new ArrayList<>();
        for (String song : canciones) {
            if (song.toLowerCase().contains(searchTerm)) {
                filteredSongs.add(song);
            }
        }
        updateSongList(filteredSongs);
    }

    // Método para actualizar la lista de canciones en el panel
    private void updateSongList(List<String> songsToShow) {
        songsPanel.removeAll();  // Limpiar las canciones previas
        for (String cancion : songsToShow) {
            JPanel songItem = createSongItem(cancion, "Bad Bunny", "src/images/" + cancion.toLowerCase().replace(" ", "_") + ".jpg");
            songsPanel.add(songItem);
        }
        songsPanel.revalidate();
        songsPanel.repaint();  // Refrescar el panel
    }

    private void stylizeOvalButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 40));
        button.setBackground(new Color(255, 200, 0));  // Amarillo
        button.setForeground(Color.BLACK);
        button.setFont(poppinsFont.deriveFont(Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));  // Borde vacío
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));  // Borde redondeado
    }

    private void stylizeButton(JButton button) {
        button.setPreferredSize(new Dimension(50, 40));
        button.setBackground(new Color(255, 200, 0));  // Amarillo
        button.setForeground(Color.BLACK);
        button.setFont(poppinsFont.deriveFont(Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    // Método para crear el menú emergente con opciones de añadir a playlist, favoritos y propiedades
    private JPopupMenu createPopupMenu(String songName) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem addToPlaylist = new JMenuItem("Añadir a playlist");
        JMenuItem addToFavorites = new JMenuItem("Añadir a favoritos");
        JMenuItem properties = new JMenuItem("Propiedades");

        // Acciones de las opciones
        addToPlaylist.addActionListener(e -> System.out.println("Añadir a playlist: " + songName));
        addToFavorites.addActionListener(e -> System.out.println("Añadir a favoritos: " + songName));
        properties.addActionListener(e -> System.out.println("Propiedades de: " + songName));

        menu.add(addToPlaylist);
        menu.add(addToFavorites);
        menu.add(properties);

        return menu;
    }

    // Método para crear un item de canción con imagen, botón de reproducción y tres puntos
    private JPanel createSongItem(String songName, String artist, String imagePath) {
        JPanel songPanel = new JPanel();
        songPanel.setBackground(new Color(50, 50, 50));  // Gris oscuro
        songPanel.setLayout(new BorderLayout());
        songPanel.setPreferredSize(new Dimension(800, 80));  // Tamaño reducido
        songPanel.setMaximumSize(new Dimension(800, 80));  // Limitar el tamaño máximo
        songPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Espacio interno
        songPanel.setBorder(BorderFactory.createLineBorder(new Color(28, 28, 28), 10, true));  // Borde redondeado

        // Imagen del álbum
        JLabel songImage = new JLabel(new ImageIcon(imagePath));
        songImage.setPreferredSize(new Dimension(80, 80));
        songPanel.add(songImage, BorderLayout.WEST);

        // Título de la canción y artista
        JPanel songInfo = new JPanel(new GridLayout(2, 1));
        songInfo.setBackground(new Color(50, 50, 50));
        JLabel songTitle = new JLabel(songName);
        songTitle.setForeground(Color.WHITE);
        songTitle.setFont(poppinsFont.deriveFont(Font.BOLD, 18));
        JLabel songArtist = new JLabel(artist);
        songArtist.setForeground(new Color(200, 200, 200));  // Gris claro para el artista
        songArtist.setFont(poppinsFont.deriveFont(Font.PLAIN, 14));
        songInfo.add(songTitle);
        songInfo.add(songArtist);
        songPanel.add(songInfo, BorderLayout.CENTER);

        // Panel para agrupar el botón de reproducción y los tres puntos
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(new Color(50, 50, 50)); // Fondo igual al del item

        // Botón de reproducción ovalado
        JButton playButton = new JButton("Play");
        stylizeOvalButton(playButton);
        playButton.addActionListener(e -> System.out.println("Reproduciendo: " + songName));
        buttonsPanel.add(playButton);

        // Botón de tres puntos para mostrar el menú emergente
        JButton optionsButton = new JButton("⋮");
        optionsButton.setPreferredSize(new Dimension(50, 40));
        stylizeButton(optionsButton);
        buttonsPanel.add(optionsButton);

        // Mostrar menú emergente al hacer clic en los tres puntos
        JPopupMenu popupMenu = createPopupMenu(songName);
        optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth()/2, optionsButton.getHeight()/2));

        // Añadir el panel con los botones a la derecha del item de canción
        songPanel.add(buttonsPanel, BorderLayout.EAST);

        return songPanel;
    }

    private void loadCustomFont() {
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/Poppins-Regular.ttf")).deriveFont(Font.PLAIN, 18);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar la fuente Poppins.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaDentroPlaylist app = new PantallaDentroPlaylist();
                app.setVisible(true);
            }
        });
    }
}
