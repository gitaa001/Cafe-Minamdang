import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class TambahResep extends JDialog {
    JTextField idInvoiceField, namaResepField, hargaField, idGudangField;
    JTextArea deskripsiArea, preskripsiArea;
    JButton simpanButton;

    public TambahResep(JFrame parent) {
        super(parent, "Tambah Resep", true);

        // Set dialog fullscreen sesuai layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        setLocationRelativeTo(parent);

        // Panel dengan background gambar
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Tambah.png"); // Pastikan path gambar benar
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color labelColor = new Color(102, 0, 0); // dark red

        // ID Invoice
        c.gridx = 0; c.gridy = 0; c.weightx = 0.3;
        JLabel idInvoiceLabel = new JLabel("ID Invoice");
        idInvoiceLabel.setFont(labelFont);
        idInvoiceLabel.setForeground(labelColor);
        panel.add(idInvoiceLabel, c);

        c.gridx = 1; c.gridy = 0; c.weightx = 0.7;
        idInvoiceField = new JTextField();
        setRoundedBorder(idInvoiceField);
        panel.add(idInvoiceField, c);

        // Nama Resep
        c.gridx = 2; c.gridy = 0; c.weightx = 0.3;
        JLabel namaResepLabel = new JLabel("Nama resep");
        namaResepLabel.setFont(labelFont);
        namaResepLabel.setForeground(labelColor);
        panel.add(namaResepLabel, c);

        c.gridx = 3; c.gridy = 0; c.weightx = 0.7;
        namaResepField = new JTextField();
        setRoundedBorder(namaResepField);
        panel.add(namaResepField, c);

        // Deskripsi (multiline)
        c.gridx = 0; c.gridy = 1; c.gridwidth = 4; c.weightx = 1;
        JLabel deskripsiLabel = new JLabel("Deskripsi");
        deskripsiLabel.setFont(labelFont);
        deskripsiLabel.setForeground(labelColor);
        panel.add(deskripsiLabel, c);

        c.gridy = 1;
        deskripsiArea = new JTextArea(3, 30);
        deskripsiArea.setLineWrap(true);
        deskripsiArea.setWrapStyleWord(true);
        deskripsiArea.setOpaque(false);
        setRoundedBorder(deskripsiArea);

        JScrollPane deskripsiScroll = new JScrollPane(deskripsiArea);
        deskripsiScroll.setOpaque(false);
        deskripsiScroll.getViewport().setOpaque(false);
        deskripsiScroll.setBorder(BorderFactory.createEmptyBorder());

// Panel custom untuk border melengkung
JPanel roundedPanel = new JPanel(new BorderLayout()) {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // radius 30
        g2.setColor(new Color(102, 0, 0));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
    }
};
roundedPanel.setOpaque(false);
roundedPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
roundedPanel.add(deskripsiScroll, BorderLayout.CENTER);
panel.add(roundedPanel, c);

        // Preskripsi (multiline)
        c.gridy = 3;
        JLabel preskripsiLabel = new JLabel("Preskripsi");
        preskripsiLabel.setFont(labelFont);
        preskripsiLabel.setForeground(labelColor);
        panel.add(preskripsiLabel, c);

        c.gridy = 4;
        preskripsiArea = new JTextArea(3, 30);
        setRoundedBorder(preskripsiArea);
        preskripsiArea.setLineWrap(true);
        preskripsiArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(preskripsiArea), c);

        // Harga dan ID Gudang (di satu baris)
        JPanel hargaGudangPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        hargaGudangPanel.setOpaque(false);

        JLabel hargaLabel = new JLabel("Harga");
        hargaLabel.setFont(labelFont);
        hargaLabel.setForeground(labelColor);
        hargaGudangPanel.add(hargaLabel);

        hargaField = new JTextField();
        setRoundedBorder(hargaField);
        hargaGudangPanel.add(hargaField);

        JLabel idGudangLabel = new JLabel("ID Gudang");
        idGudangLabel.setFont(labelFont);
        idGudangLabel.setForeground(labelColor);
        hargaGudangPanel.add(idGudangLabel);

        idGudangField = new JTextField();
        setRoundedBorder(idGudangField);
        hargaGudangPanel.add(idGudangField);

        c.gridy = 5; c.gridwidth = 4;
        panel.add(hargaGudangPanel, c);

        // Tombol Simpan
        c.gridy = 6;
        simpanButton = new JButton("Simpan");
        simpanButton.setBackground(new Color(193, 0, 0));
        simpanButton.setForeground(Color.WHITE);
        simpanButton.setFocusPainted(false);
        simpanButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        simpanButton.setBorder(new RoundedBorder(15));
        simpanButton.setPreferredSize(new Dimension(400, 40));
        panel.add(simpanButton, c);

        // Tambahkan panel ke JScrollPane supaya bisa scroll kalau terlalu besar
        JScrollPane scrollPane = new JScrollPane(panel);
        setContentPane(scrollPane);
    }

    // Helper buat border rounded
    private void setRoundedBorder(JComponent comp) {
        comp.setBorder(new CompoundBorder(
            new LineBorder(new Color(102, 0, 0), 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }

    // Border rounded custom
    static class RoundedBorder extends LineBorder {
        RoundedBorder(int radius) {
            super(new Color(193, 0, 0), 1, true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummy = new JFrame();
            dummy.setUndecorated(true);
            dummy.setExtendedState(JFrame.MAXIMIZED_BOTH);
            dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dummy.setVisible(true);

            TambahResep dialog = new TambahResep(dummy);
            dialog.setVisible(true);
        });
    }
}
