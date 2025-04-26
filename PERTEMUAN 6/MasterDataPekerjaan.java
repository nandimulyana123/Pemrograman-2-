package aplikasimahasiswaa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AplikasiMahasiswaa extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField nimField, namaField, nilaiField;
    private JButton tambahBtn, updateBtn, hapusBtn, clearBtn;
    private ArrayList<Mahasiswa> dataMahasiswa;
    private int selectedRow = -1;

    // Class untuk menyimpan data mahasiswa
    private class Mahasiswa {
        String nim;
        String nama;
        int nilai;

        public Mahasiswa(String nim, String nama, int nilai) {
            this.nim = nim;
            this.nama = nama;
            this.nilai = nilai;
        }
    }

    public AplikasiMahasiswaa() {
        // Inisialisasi ArrayList untuk menyimpan data
        dataMahasiswa = new ArrayList<>();

        // Setup frame
        setTitle("Aplikasi Nilai Mahasiswa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Model tabel
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"NIM", "Nama", "Nilai", "Grade"});
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Scroll pane untuk tabel
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Mahasiswa"));

        // Komponen input
        inputPanel.add(new JLabel("NIM:"));
        nimField = new JTextField();
        inputPanel.add(nimField);

        inputPanel.add(new JLabel("Nama:"));
        namaField = new JTextField();
        inputPanel.add(namaField);

        inputPanel.add(new JLabel("Nilai:"));
        nilaiField = new JTextField();
        inputPanel.add(nilaiField);

        // Panel button
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        tambahBtn = new JButton("Tambah");
        updateBtn = new JButton("Update");
        hapusBtn = new JButton("Hapus");
        clearBtn = new JButton("Clear");

        // Styling button
        for (JButton btn : new JButton[]{tambahBtn, updateBtn, hapusBtn, clearBtn}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setFocusPainted(false);
        }

        tambahBtn.setBackground(new Color(76, 175, 80));
        tambahBtn.setForeground(Color.WHITE);
        updateBtn.setBackground(new Color(33, 150, 243));
        updateBtn.setForeground(Color.WHITE);
        hapusBtn.setBackground(new Color(244, 67, 54));
        hapusBtn.setForeground(Color.WHITE);
        clearBtn.setBackground(new Color(255, 152, 0));
        clearBtn.setForeground(Color.WHITE);

        buttonPanel.add(tambahBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(hapusBtn);
        buttonPanel.add(clearBtn);

        // Gabungkan panel input dan button
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Tambahkan ke frame
        add(mainPanel);

        // Event listeners
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                selectedRow = table.getSelectedRow();
                nimField.setText(model.getValueAt(selectedRow, 0).toString());
                namaField.setText(model.getValueAt(selectedRow, 1).toString());
                nilaiField.setText(model.getValueAt(selectedRow, 2).toString());
            }
        });

        tambahBtn.addActionListener(e -> tambahData());
        updateBtn.addActionListener(e -> updateData());
        hapusBtn.addActionListener(e -> hapusData());
        clearBtn.addActionListener(e -> clearFields());

        // Validasi input nilai
        nilaiField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        // Tambahkan beberapa data contoh
        tambahDataContoh();
    }

    private String hitungGrade(int nilai) {
        if (nilai >= 85) return "A";
        else if (nilai >= 75) return "B";
        else if (nilai >= 65) return "C";
        else if (nilai >= 50) return "D";
        else return "E";
    }

    private void refreshTable() {
        model.setRowCount(0); // Clear existing data
        for (Mahasiswa mhs : dataMahasiswa) {
            model.addRow(new Object[]{
                mhs.nim, 
                mhs.nama, 
                mhs.nilai, 
                hitungGrade(mhs.nilai)
            });
        }
    }

    private void tambahDataContoh() {
        dataMahasiswa.add(new Mahasiswa("221011402463", "Favid", 85));
        dataMahasiswa.add(new Mahasiswa("221011402464", "Budi", 75));
        dataMahasiswa.add(new Mahasiswa("221011402465", "Ani", 65));
        refreshTable();
    }

    private void tambahData() {
        String nim = nimField.getText().trim();
        String nama = namaField.getText().trim();
        String nilaiStr = nilaiField.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || nilaiStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int nilai;
        try {
            nilai = Integer.parseInt(nilaiStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai harus angka!", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nilai < 0 || nilai > 100) {
            JOptionPane.showMessageDialog(this, "Nilai harus antara 0-100!", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek apakah NIM sudah ada
        for (Mahasiswa mhs : dataMahasiswa) {
            if (mhs.nim.equals(nim)) {
                JOptionPane.showMessageDialog(this, "NIM sudah terdaftar!", 
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Tambahkan data baru
        dataMahasiswa.add(new Mahasiswa(nim, nama, nilai));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateData() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate!", 
                    "Update Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nim = nimField.getText().trim();
        String nama = namaField.getText().trim();
        String nilaiStr = nilaiField.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || nilaiStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int nilai;
        try {
            nilai = Integer.parseInt(nilaiStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai harus angka!", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nilai < 0 || nilai > 100) {
            JOptionPane.showMessageDialog(this, "Nilai harus antara 0-100!", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update data
        Mahasiswa mhs = dataMahasiswa.get(selectedRow);
        mhs.nim = nim;
        mhs.nama = nama;
        mhs.nilai = nilai;

        refreshTable();
        clearFields();
        selectedRow = -1;
        JOptionPane.showMessageDialog(this, "Data berhasil diupdate!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    private void hapusData() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", 
                    "Hapus Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nim = nimField.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus data dengan NIM " + nim + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dataMahasiswa.remove(selectedRow);
            refreshTable();
            clearFields();
            selectedRow = -1;
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        nimField.setText("");
        namaField.setText("");
        nilaiField.setText("");
        selectedRow = -1;
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplikasiMahasiswaa app = new AplikasiMahasiswaa();
            app.setVisible(true);
        });
    }
}