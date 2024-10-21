import javax.swing.*;
import java.awt.*;

public class PantallaPlaylist extends JFrame {
    
    public PantallaPlaylist() {
        setTitle("DAMusic");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Configurar el layout principal
        setLayout(new BorderLayout());
        
        // Barra de navegación superior
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(33, 33, 33));
        navBar.setLayout(new BorderLayout());
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Icono de Música y título
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(33, 33, 33));
        JLabel logoLabel = new JLabel("♪ DAMusic");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        logoPanel.add(logoLabel);
        
        // Botones de navegación (tres botones)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(33, 33, 33));
        buttonPanel.add(createNavButton("\uD83C\uDFB5")); // Música
        buttonPanel.add(createNavButton("\uD83D\uDD0D")); // Búsqueda
        buttonPanel.add(createNavButton("\u2630"));       // Menú
        
        // Etiqueta de ayuda
        JLabel helpLabel = new JLabel("ayuda");
        helpLabel.setForeground(new Color(255, 204, 0)); // Color amarillo similar
        helpLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        navBar.add(logoPanel, BorderLayout.WEST);
        navBar.add(buttonPanel, BorderLayout.CENTER);
        navBar.add(helpLabel, BorderLayout.EAST);
        
        add(navBar, BorderLayout.NORTH);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(24, 24, 24));
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JTextField searchField = new JTextField(25);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)));
        
        JButton searchBtn = new JButton("🔍");
        searchBtn.setPreferredSize(new Dimension(50, 30));
        searchBtn.setBackground(new Color(70, 70, 70));
        searchBtn.setForeground(Color.WHITE);
        
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        
        add(searchPanel, BorderLayout.CENTER);
        
        // Panel de playlists
        JPanel playlistPanel = new JPanel();
        playlistPanel.setBackground(new Color(24, 24, 24));
        playlistPanel.setLayout(new BoxLayout(playlistPanel, BoxLayout.Y_AXIS));
        playlistPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Agregar cada playlist
        playlistPanel.add(createPlaylistItem("Chill Vibes", "ruta/a/imagen1.jpg"));
        playlistPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre playlists
        playlistPanel.add(createPlaylistItem("Workout Hits", "ruta/a/imagen2.jpg"));
        playlistPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        playlistPanel.add(createPlaylistItem("Party Mix", "ruta/a/imagen3.jpg"));
        
        JScrollPane scrollPane = new JScrollPane(playlistPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(new Color(24, 24, 24));
        
        add(scrollPane, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(33, 33, 33));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }
    
    private JPanel createPlaylistItem(String title, String imagePath) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(45, 45, 45));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 120));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setPreferredSize(new Dimension(120, 120));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        // Botón "Play" con apariencia rectangular y funcional
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(100, 25));
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        playButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        playButton.setOpaque(true);
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Posicionamiento de los elementos dentro del panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(new Color(45, 45, 45));
        infoPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel playButtonContainer = new JPanel();
        playButtonContainer.setBackground(new Color(45, 45, 45));
        playButtonContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        playButtonContainer.add(playButton);
        
        infoPanel.add(playButtonContainer, BorderLayout.SOUTH);
        
        panel.add(imageLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PantallaPlaylist::new);
    }
}
