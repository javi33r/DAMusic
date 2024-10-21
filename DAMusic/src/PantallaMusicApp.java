import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PantallaMusicApp extends JFrame {

    private JTextField searchBar;
    private JPanel songListPanel;
    private ArrayList<Song> songs;  // Lista para almacenar las canciones

    public PantallaMusicApp() {
        setTitle("DAMusic");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Layout para el marco principal
        setLayout(new BorderLayout());

        // Panel para el menú superior
        JPanel topMenuPanel = new JPanel(new BorderLayout());
        topMenuPanel.setBackground(Color.DARK_GRAY);
        topMenuPanel.setPreferredSize(new Dimension(900, 60));

        // Logo de la empresa en el menú superior (izquierda)
        JLabel logoLabel = new JLabel(new ImageIcon("src/Fotos/Logo.PNG")); // Añade el logo
        topMenuPanel.add(logoLabel, BorderLayout.WEST);

        // Panel central con los botones de imagen
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton button1 = new JButton(new ImageIcon("src/Fotos/botonCanciones.PNG")); // Añade las imágenes
        JButton button2 = new JButton(new ImageIcon("src/Fotos/botonAniadir.PNG"));
        JButton button3 = new JButton(new ImageIcon("src/Fotos/botonPlaylist.PNG"));

        // Ajustar tamaños de los botones
        button1.setPreferredSize(new Dimension(60, 40));
        button2.setPreferredSize(new Dimension(60, 40));
        button3.setPreferredSize(new Dimension(60, 40));

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        topMenuPanel.add(buttonPanel, BorderLayout.CENTER);

        // Botón de ayuda (derecha)
        JButton helpButton = new JButton("Ayuda");
        helpButton.setBackground(Color.LIGHT_GRAY);
        helpButton.setMnemonic('F');
        helpButton.setPreferredSize(new Dimension(100, 40));  // Ajuste de tamaño
        helpButton.addActionListener(e -> showHelpDialog());  // Mostrar menú de ayuda
        topMenuPanel.add(helpButton, BorderLayout.EAST);

        // Panel inferior con el buscador y logo
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.GRAY);
        searchPanel.setPreferredSize(new Dimension(900, 50));

        // Logo de la empresa en el buscador (izquierda)
        JLabel logoSmallLabel = new JLabel(new ImageIcon("src/Fotos/Logo.PNG"));
        searchPanel.add(logoSmallLabel, BorderLayout.WEST);

        // Barra de búsqueda (derecha)
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Crear un panel centrado
        searchContainer.setBackground(Color.GRAY);  // Mismo color que el fondo
        searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(300, 25));  // Ajustar el ancho a 300px y más fino
        searchBar.setFont(new Font("Arial", Font.PLAIN, 16));
        searchContainer.add(searchBar);  // Añadir la barra al panel contenedor
        searchPanel.add(searchContainer, BorderLayout.CENTER);  // Añadir el panel centrado al buscador
        searchBar.addActionListener(e -> searchSongs());  // Añadir acción al buscar

        // Panel para la lista de canciones
        songListPanel = new JPanel();
        songListPanel.setLayout(new BoxLayout(songListPanel, BoxLayout.Y_AXIS));
        songListPanel.setBackground(Color.BLACK);

        // Inicializar la lista de canciones
        songs = new ArrayList<>();
        songs.add(new Song("Sunshine", "Ava Max", "sunshine.png"));
        songs.add(new Song("Moonlight", "The Weeknd", "moonlight.png"));
        songs.add(new Song("Stardust", "Coldplay", "stardust.png"));

        // Mostrar canciones iniciales
        updateSongList("");

        // Scroll para la lista de canciones
        JScrollPane scrollPane = new JScrollPane(songListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añadir los paneles al marco principal
        add(topMenuPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Añadir listener para detectar la tecla F1
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    showHelpDialog();  // Mostrar ayuda al pulsar F1
                }
            }
        });

        setFocusable(true);  // Hacer que el frame pueda recibir eventos de teclado

        setVisible(true);

        // Implementar las acciones de los botones
        button2.addActionListener(e -> new PantallaModificarMp3()); // Abre PantallaModificarMp3
        button3.addActionListener(e -> new PantallaPlaylist());     // Abre PantallaPlaylist
    }

    // Método para mostrar el menú de ayuda
    private void showHelpDialog() {
        String helpMessage = """
        Bienvenido a DAMusic:
        
        1. Barra de búsqueda: Usa la barra de búsqueda para encontrar canciones por su nombre.
        2. Botón "Play": Reproduce la canción seleccionada.
        3. Botón "...": Muestra más opciones para cada canción (añadir a favoritos, agregar a playlist, ver propiedades).
        4. Icono de añadir: Añade nuevas canciones a tu lista.
        5. Icono de playlist: Accede a tus playlists.
        6. Ayuda (F1): Abre este menú de ayuda.
        """;

        JOptionPane.showMessageDialog(this, helpMessage, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para actualizar la lista de canciones según la búsqueda
    private void updateSongList(String query) {
        songListPanel.removeAll();  // Limpiar las canciones actuales
        for (Song song : songs) {
            if (song.getName().toLowerCase().contains(query.toLowerCase())) {
                addSongPanel(songListPanel, song.getName(), song.getArtist(), song.getImagePath());
            }
        }
        songListPanel.revalidate();  // Refrescar el panel
        songListPanel.repaint();
    }

    // Acción del buscador para filtrar las canciones
    private void searchSongs() {
        String query = searchBar.getText();
        updateSongList(query);  // Filtrar las canciones
    }

    // Añadir cada canción al panel
    private void addSongPanel(JPanel parentPanel, String songName, String artistName, String songImage) {
        JPanel songPanel = new JPanel(new BorderLayout());
        songPanel.setPreferredSize(new Dimension(750, 60));
        songPanel.setBackground(Color.DARK_GRAY);

        // Imagen de la canción
        JLabel songImageLabel = new JLabel(new ImageIcon(songImage));
        songPanel.add(songImageLabel, BorderLayout.WEST);

        // Botón de reproducir (izquierda)
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(80, 40)); // Ajustar tamaño si es necesario
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlayMenu(songName);  // Crear y mostrar el menú de reproducción
            }
        });
        songPanel.add(playButton, BorderLayout.LINE_END); // Añadir a la izquierda

        // Nombre de la canción y artista
        JLabel songInfoLabel = new JLabel(songName + " - " + artistName);
        songInfoLabel.setForeground(Color.WHITE);
        songPanel.add(songInfoLabel, BorderLayout.CENTER);

        // Botón de opciones con desplegable
        JButton optionsButton = new JButton("...");
        optionsButton.addActionListener(e -> showSongOptions());
        songPanel.add(optionsButton, BorderLayout.LINE_END);

        // Panel derecho con los botones
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alineación a la derecha
        rightPanel.add(playButton); // Botón de reproducir
        rightPanel.add(optionsButton); // Botón de opciones

        songPanel.add(rightPanel, BorderLayout.EAST); // Panel que contiene los botones

        parentPanel.add(songPanel);
    }

    // Crear el menú de reproducción al darle al botón de "Play"
    private void showPlayMenu(String songName) {
        JDialog playMenu = new JDialog(this, "Reproduciendo: " + songName, true);
        playMenu.setSize(400, 150);
        playMenu.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBackground(Color.DARK_GRAY);

        JLabel songLabel = new JLabel(songName);
        songLabel.setForeground(Color.WHITE);
        controlPanel.add(songLabel, BorderLayout.WEST);

        JSlider songSlider = new JSlider(0, 100, 0);
        songSlider.setBackground(Color.DARK_GRAY);
        controlPanel.add(songSlider, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        JButton pauseButton = new JButton("Pausar");
        JButton stopButton = new JButton("Detener");

        buttonPanel.add(pauseButton);
        buttonPanel.add(stopButton);

        playMenu.add(controlPanel, BorderLayout.NORTH);
        playMenu.add(buttonPanel, BorderLayout.SOUTH);

        playMenu.setLocationRelativeTo(this);  // Centrar el diálogo en la ventana principal
        playMenu.setVisible(true);
    }

    // Menú de opciones de canción
    private void showSongOptions() {
        JPopupMenu optionsMenu = new JPopupMenu();
        JMenuItem favoriteOption = new JMenuItem("Añadir a Favoritos");
        JMenuItem playlistOption = new JMenuItem("Añadir a Playlist");
        JMenuItem propertiesOption = new JMenuItem("Ver Propiedades");

        optionsMenu.add(favoriteOption);
        optionsMenu.add(playlistOption);
        optionsMenu.add(propertiesOption);

        optionsMenu.show(this, 100, 100); // Mostrar menú en la posición (x, y)
    }

    // Clase interna para representar una canción
    class Song {
        private String name;
        private String artist;
        private String imagePath;

        public Song(String name, String artist, String imagePath) {
            this.name = name;
            this.artist = artist;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public String getArtist() {
            return artist;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PantallaMusicApp::new);
    }
}
