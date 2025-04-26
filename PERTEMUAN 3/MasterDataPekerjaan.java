import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MasterDataPekerjaan extends JFrame {
    private JTextField textKode, textNama;
    private JComboBox<String> comboJumlah;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<String[]> dataList = new ArrayList<>();

    public MasterDataPekerjaan() {
        setTitle("Master Data Pekerjaan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelAtas = new JPanel(null);
        panelAtas.setBackground(Color.GREEN);

        JLabel labelKode = new JLabel("Kode Pekerjaan");
        labelKode.setBounds(20, 20, 100, 20);
        panelAtas.add(labelKode);

        textKode = new JTextField();
        textKode.setBounds(130, 20, 100, 20);
        panelAtas.add(textKode);

        JButton btnLihat = new JButton("Lihat");
        btnLihat.setBounds(240, 20, 80, 20);
        btnLihat.addActionListener(e -> tampilkanPopup());
        panelAtas.add(btnLihat);

        JLabel labelNama = new JLabel("Nama Pekerjaan");
        labelNama.setBounds(20, 60, 100, 20);
        panelAtas.add(labelNama);

        textNama = new JTextField();
        textNama.setBounds(130, 60, 190, 20);
        panelAtas.add(textNama);

        JLabel labelJumlah = new JLabel("Jumlah Tugas");
        labelJumlah.setBounds(20, 100, 100, 20);
        panelAtas.add(labelJumlah);

        comboJumlah = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            comboJumlah.addItem(String.valueOf(i));
        }
        comboJumlah.setBounds(130, 100, 50, 20);
        panelAtas.add(comboJumlah);

        add(panelAtas, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel();
        panelBawah.setBackground(Color.PINK);

        JButton btnSimpan = new JButton("Simpan");
        JButton btnHapus = new JButton("Hapus");
        JButton btnTutup = new JButton("Tutup");

        btnSimpan.addActionListener(e -> simpanData());
        btnHapus.addActionListener(e -> hapusData());
        btnTutup.addActionListener(e -> dispose());

        panelBawah.add(btnSimpan);
        panelBawah.add(btnHapus);
        panelBawah.add(btnTutup);

        add(panelBawah, BorderLayout.SOUTH);
    }

    private void tampilkanPopup() {
        JFrame popup = new JFrame("Data Pekerjaan");
        popup.setSize(400, 300);
        popup.setLayout(new BorderLayout());

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setBackground(Color.GREEN);

        String[] kolom = {"Kode", "Nama Pekerjaan"};
        Object[][] data = {
            {"1415", "admin"},
            {"2231", "sales"},
            {"4444", "direktur"},
            {"4545", "kuli"}
        };

        tableModel = new DefaultTableModel(data, kolom);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        panelAtas.add(scrollPane, BorderLayout.CENTER);

        popup.add(panelAtas, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel();
        panelBawah.setBackground(Color.PINK);

        JButton btnPilih = new JButton("Pilih");
        JButton btnTutup = new JButton("Tutup");

        btnPilih.addActionListener(e -> pilihData());
        btnTutup.addActionListener(e -> popup.dispose());

        panelBawah.add(btnPilih);
        panelBawah.add(btnTutup);

        popup.add(panelBawah, BorderLayout.SOUTH);

        popup.setLocationRelativeTo(this);
        popup.setVisible(true);
    }

    private void pilihData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            textKode.setText(table.getValueAt(selectedRow, 0).toString());
            textNama.setText(table.getValueAt(selectedRow, 1).toString());
        }
    }

    private void simpanData() {
        String kode = textKode.getText();
        String nama = textNama.getText();
        String jumlah = (String) comboJumlah.getSelectedItem();

        if (!kode.isEmpty() && !nama.isEmpty()) {
            dataList.add(new String[]{kode, nama, jumlah});
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            System.out.println("Data saat ini:");
            for (String[] data : dataList) {
                System.out.println("Kode: " + data[0] + ", Nama: " + data[1] + ", Jumlah: " + data[2]);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lengkapi data terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hapusData() {
        textKode.setText("");
        textNama.setText("");
        comboJumlah.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MasterDataPekerjaan().setVisible(true);
        });
    }
}
