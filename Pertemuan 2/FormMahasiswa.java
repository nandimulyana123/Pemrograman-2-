import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class FormKaryawanLengkap extends JFrame {

    // Komponen utama
    private JPanel panelInput, panelButton;
    private JTextField ktpTextField, namaTextField;
    private JComboBox<String> ruangComboBox;
    private JPasswordField passwordField;
    private JButton lihatButton, simpanButton, hapusButton, tutupButton;

    // Data Model untuk tabel
    private DefaultTableModel modelKaryawan;

    public FormKaryawanLengkap() {
        setTitle("Master Data Karyawan");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panelInput = new JPanel();
        panelInput.setBorder(BorderFactory.createTitledBorder("Data Karyawan"));
        panelInput.setBounds(10, 10, 400, 200);
        panelInput.setLayout(null);
        add(panelInput);

        JLabel labelKTP = new JLabel("KTP");
        labelKTP.setBounds(20, 30, 100, 25);
        panelInput.add(labelKTP);

        JLabel labelNama = new JLabel("Nama");
        labelNama.setBounds(20, 60, 100, 25);
        panelInput.add(labelNama);

        JLabel labelRuang = new JLabel("Ruang");
        labelRuang.setBounds(20, 90, 100, 25);
        panelInput.add(labelRuang);

        JLabel labelPassword = new JLabel("Password");
        labelPassword.setBounds(20, 120, 100, 25);
        panelInput.add(labelPassword);

        ktpTextField = new JTextField();
        ktpTextField.setBounds(130, 30, 200, 25);
        panelInput.add(ktpTextField);

        namaTextField = new JTextField();
        namaTextField.setBounds(130, 60, 200, 25);
        panelInput.add(namaTextField);

        ruangComboBox = new JComboBox<>();
        ruangComboBox.setBounds(130, 90, 200, 25);
        ruangComboBox.addItem("Programmer");
        ruangComboBox.addItem("Designer");
        ruangComboBox.addItem("Manager");
        panelInput.add(ruangComboBox);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 120, 200, 25);
        panelInput.add(passwordField);

        panelButton = new JPanel();
        panelButton.setBounds(420, 10, 150, 200);
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
        add(panelButton);

        lihatButton = new JButton("Lihat");
        simpanButton = new JButton("Simpan");
        hapusButton = new JButton("Hapus");
        tutupButton = new JButton("Tutup");

        panelButton.add(lihatButton);
        panelButton.add(Box.createVerticalStrut(10));
        panelButton.add(simpanButton);
        panelButton.add(Box.createVerticalStrut(10));
        panelButton.add(hapusButton);
        panelButton.add(Box.createVerticalStrut(10));
        panelButton.add(tutupButton);

        // Model tabel data karyawan
        modelKaryawan = new DefaultTableModel(new Object[]{"KTP", "Nama"}, 0);

        // Event tombol
        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanData();
            }
        });

        lihatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormLihatKaryawan(FormKaryawanLengkap.this, modelKaryawan).setVisible(true);
            }
        });

        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusForm();
            }
        });

        tutupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void simpanData() {
        String ktp = ktpTextField.getText();
        String nama = namaTextField.getText();
        if (!ktp.isEmpty() && !nama.isEmpty()) {
            modelKaryawan.addRow(new Object[]{ktp, nama});
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            hapusForm();
        } else {
            JOptionPane.showMessageDialog(this, "KTP dan Nama harus diisi!");
        }
    }

    private void hapusForm() {
        ktpTextField.setText("");
        namaTextField.setText("");
        ruangComboBox.setSelectedIndex(0);
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FormKaryawanLengkap().setVisible(true);
            }
        });
    }
}

// Form Lihat Data Karyawan
class FormLihatKaryawan extends JDialog {

    private JTable karyawanTable;
    private JButton pilihButton, tutupButton;
    private DefaultTableModel model;

    public FormLihatKaryawan(JFrame parent, DefaultTableModel modelKaryawan) {
        super(parent, "Data Karyawan", true);
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(parent);

        JPanel panelTabel = new JPanel();
        panelTabel.setBorder(BorderFactory.createTitledBorder("Data Karyawan"));
        panelTabel.setBounds(10, 10, 360, 200);
        panelTabel.setLayout(null);
        add(panelTabel);

        karyawanTable = new JTable(modelKaryawan);
        JScrollPane scrollPane = new JScrollPane(karyawanTable);
        scrollPane.setBounds(10, 20, 340, 170);
        panelTabel.add(scrollPane);

        JPanel panelButton = new JPanel();
        panelButton.setBounds(10, 220, 360, 40);
        add(panelButton);

        pilihButton = new JButton("Pilih");
        tutupButton = new JButton("Tutup");

        panelButton.add(pilihButton);
        panelButton.add(tutupButton);

        pilihButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pilihData();
            }
        });

        tutupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void pilihData() {
        int selectedRow = karyawanTable.getSelectedRow();
        if (selectedRow >= 0) {
            String ktp = (String) karyawanTable.getValueAt(selectedRow, 0);
            String nama = (String) karyawanTable.getValueAt(selectedRow, 1);
            JOptionPane.showMessageDialog(this, "Data dipilih:\nKTP: " + ktp + "\nNama: " + nama);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris terlebih dahulu!");
        }
    }
}
