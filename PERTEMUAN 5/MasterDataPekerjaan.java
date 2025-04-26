import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class AplikasiMahasiswa extends JFrame {
    // Deklarasi Komponen
    private DefaultTableModel model;
    private JTable table;
    private JTextField nimField, namaField, nilaiField;
    private JButton tambahBtn, hapusBtn, refreshBtn;
    private JLabel judulLabel, statistikLabel;
    
    // Warna Tema
    private Color warnaPrimer = new Color(58, 83, 155);
    private Color warnaSekunder = new Color(255, 193, 7);
    private Color warnaBackground = new Color(250, 250, 250);
    
    public AplikasiMahasiswa() {
        // 1. SETTING FRAME UTAMA
        setTitle("APLIKASI MANAJEMEN MAHASISWA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // 2. BUAT PANEL UTAMA
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(warnaBackground);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // 3. HEADER DENGAN JUDUL
        judulLabel = new JLabel("DATA MAHASISWA", JLabel.CENTER);
        judulLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        judulLabel.setForeground(warnaPrimer);
        judulLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        // 4. BUAT TABEL DATA
        String[] kolom = {"No", "NIM", "Nama Mahasiswa", "Nilai", "Grade"};
        model = new DefaultTableModel(kolom, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Double.class : String.class;
            }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(207, 216, 220));
        
        // Style Header Tabel
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(warnaPrimer);
        header.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(189, 189, 189)));
        
        // 5. PANEL INPUT DATA
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(warnaBackground);
        inputPanel.setBorder(new TitledBorder(
            new LineBorder(warnaPrimer, 2), 
            "Input Data Mahasiswa",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            warnaPrimer
        ));
        
        // Komponen Input
        nimField = buatTextField("Masukkan NIM");
        namaField = buatTextField("Masukkan Nama");
        nilaiField = buatTextField("Masukkan Nilai");
        
        // Label Input
        JLabel nimLabel = buatLabel("NIM:");
        JLabel namaLabel = buatLabel("Nama:");
        JLabel nilaiLabel = buatLabel("Nilai:");
        
        // Tambahkan ke Panel
        inputPanel.add(nimLabel); inputPanel.add(nimField);
        inputPanel.add(namaLabel); inputPanel.add(namaField);
        inputPanel.add(nilaiLabel); inputPanel.add(nilaiField);
        
        // 6. PANEL TOMBOL AKSI
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(warnaBackground);
        
        tambahBtn = buatTombol("TAMBAH DATA", warnaPrimer, Color.WHITE);
        hapusBtn = buatTombol("HAPUS DATA", new Color(198, 40, 40), Color.WHITE);
        refreshBtn = buatTombol("REFRESH", warnaSekunder, Color.BLACK);
        
        // Tambahkan Action Listener
        tambahBtn.addActionListener(e -> tambahData());
        hapusBtn.addActionListener(e -> hapusData());
        refreshBtn.addActionListener(e -> refreshData());
        
        buttonPanel.add(tambahBtn);
        buttonPanel.add(hapusBtn);
        buttonPanel.add(refreshBtn);
        
        // 7. PANEL STATISTIK
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(warnaBackground);
        statsPanel.setBorder(new TitledBorder(
            new LineBorder(new Color(189, 189, 189)), 
            "Statistik",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(69, 90, 100)
        ));
        
        statistikLabel = new JLabel("Total: 0 mahasiswa | Rata-rata: 0 | Tertinggi: 0 | Terendah: 0");
        statistikLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statsPanel.add(statistikLabel);
        
        // 8. GABUNGKAN SEMUA KOMPONEN
        mainPanel.add(judulLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(warnaBackground);
        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(statsPanel, BorderLayout.SOUTH);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        // 9. TAMBAHKAN DATA CONTOH
        tambahDataContoh();
        updateStatistik();
        
        add(mainPanel);
    }
    
    // METHOD PEMBANTU
    private JTextField buatTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
            new LineBorder(new Color(189, 189, 189)), 
            new EmptyBorder(5, 10, 5, 10)
        ));
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
        
        return field;
    }
    
    private JLabel buatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(69, 90, 100));
        return label;
    }
    
    private JButton buatTombol(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(
            new LineBorder(bg.darker(), 1), 
            new EmptyBorder(8, 20, 8, 20)
        ));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.brighter());
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        
        return btn;
    }
    
    private String hitungGrade(double nilai) {
        if (nilai >= 85) return "A";
        if (nilai >= 75) return "B";
        if (nilai >= 65) return "C";
        if (nilai >= 50) return "D";
        return "E";
    }
    
    // METHOD UTAMA
    private void tambahData() {
        try {
            String nim = nimField.getText().trim();
            String nama = namaField.getText().trim();
            double nilai = Double.parseDouble(nilaiField.getText().trim());
            
            // Validasi
            if (nim.isEmpty() || nama.isEmpty()) {
                tampilkanPesan("NIM dan Nama tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (nilai < 0 || nilai > 100) {
                tampilkanPesan("Nilai harus antara 0-100!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tambahkan ke tabel
            int noUrut = model.getRowCount() + 1;
            String grade = hitungGrade(nilai);
            model.addRow(new Object[]{noUrut, nim, nama, nilai, grade});
            
            // Reset form
            nimField.setText("Masukkan NIM");
            nimField.setForeground(Color.GRAY);
            namaField.setText("Masukkan Nama");
            namaField.setForeground(Color.GRAY);
            nilaiField.setText("Masukkan Nilai");
            nilaiField.setForeground(Color.GRAY);
            
            updateStatistik();
            tampilkanPesan("Data berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException ex) {
            tampilkanPesan("Nilai harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusData() {
        int barisTerpilih = table.getSelectedRow();
        if (barisTerpilih == -1) {
            tampilkanPesan("Pilih data yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int konfirmasi = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin menghapus data ini?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            model.removeRow(barisTerpilih);
            updateNomorUrut();
            updateStatistik();
            tampilkanPesan("Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void refreshData() {
        model.setRowCount(0);
        tambahDataContoh();
        updateStatistik();
        tampilkanPesan("Data telah direfresh!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateNomorUrut() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i+1, i, 0);
        }
    }
    
    private void updateStatistik() {
        int jumlah = model.getRowCount();
        if (jumlah == 0) {
            statistikLabel.setText("Total: 0 mahasiswa | Rata-rata: 0 | Tertinggi: 0 | Terendah: 0");
            return;
        }
        
        double total = 0;
        double tertinggi = Double.MIN_VALUE;
        double terendah = Double.MAX_VALUE;
        
        for (int i = 0; i < jumlah; i++) {
            double nilai = (double) model.getValueAt(i, 3);
            total += nilai;
            if (nilai > tertinggi) tertinggi = nilai;
            if (nilai < terendah) terendah = nilai;
        }
        
        double rataRata = total / jumlah;
        statistikLabel.setText(String.format(
            "Total: %d mahasiswa | Rata-rata: %.2f | Tertinggi: %.2f | Terendah: %.2f",
            jumlah, rataRata, tertinggi, terendah
        ));
    }
    
    private void tambahDataContoh() {
        String[][] contohData = {
            {"10118001", "Andi Wijaya", "85.5"},
            {"10118002", "Budi Santoso", "77.0"},
            {"10118003", "Citra Dewi", "92.5"},
            {"10118004", "Dian Pratama", "65.0"},
            {"10118005", "Eka Putri", "88.5"}
        };
        
        for (int i = 0; i < contohData.length; i++) {
            double nilai = Double.parseDouble(contohData[i][2]);
            String grade = hitungGrade(nilai);
            model.addRow(new Object[]{i+1, contohData[i][0], contohData[i][1], nilai, grade});
        }
    }
    
    private void tampilkanPesan(String pesan, String judul, int tipe) {
        JOptionPane.showMessageDialog(this, pesan, judul, tipe);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new AplikasiMahasiswa().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}