package aplikasipembayaran12.java; // <- ini sekarang sesuai project kamu!

import javax.swing.*;
import java.awt.event.*;

public class AplikasiPembayaran12 { // <- perhatikan, ini juga harus sama dengan nama file

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pembayaran");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);

        JLabel mejaLabel = new JLabel("NO. MEJA:");
        JLabel totalLabel = new JLabel("TOTAL PESANAN:");
        JLabel ppnLabel = new JLabel("PPN:");
        JLabel bayarLabel = new JLabel("UANG PEMBAYARAN:");
        JLabel kembaliLabel = new JLabel("UANG KEMBALI:");

        JTextField mejaField = new JTextField();
        JTextField totalField = new JTextField();
        JTextField ppnField = new JTextField();
        JTextField bayarField = new JTextField();
        JTextField kembaliField = new JTextField();

        JButton strukButton = new JButton("STRUK");
        JButton bayarButton = new JButton("BAYAR");

        JTextArea hasilArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(hasilArea);

        mejaLabel.setBounds(30, 20, 100, 25);
        mejaField.setBounds(150, 20, 150, 25);
        totalLabel.setBounds(30, 60, 100, 25);
        totalField.setBounds(150, 60, 150, 25);
        ppnLabel.setBounds(30, 100, 100, 25);
        ppnField.setBounds(150, 100, 150, 25);
        bayarLabel.setBounds(30, 140, 120, 25);
        bayarField.setBounds(150, 140, 150, 25);
        kembaliLabel.setBounds(30, 180, 120, 25);
        kembaliField.setBounds(150, 180, 150, 25);

        strukButton.setBounds(150, 220, 100, 30);
        bayarButton.setBounds(270, 220, 100, 30);

        scrollPane.setBounds(30, 270, 400, 100);

        frame.add(mejaLabel);
        frame.add(mejaField);
        frame.add(totalLabel);
        frame.add(totalField);
        frame.add(ppnLabel);
        frame.add(ppnField);
        frame.add(bayarLabel);
        frame.add(bayarField);
        frame.add(kembaliLabel);
        frame.add(kembaliField);
        frame.add(strukButton);
        frame.add(bayarButton);
        frame.add(scrollPane);

        bayarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int total = Integer.parseInt(totalField.getText());
                    int ppn = (int)(total * 0.2); // PPN 20%
                    int bayar = Integer.parseInt(bayarField.getText());
                    int kembali = bayar - (total + ppn);

                    ppnField.setText(String.valueOf(ppn));
                    kembaliField.setText(String.valueOf(kembali));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Input harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        strukButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hasil = "Total Pesanan: " + totalField.getText() + "\n"
                            + "PPN: " + ppnField.getText() + "\n"
                            + "Uang Pembayaran: " + bayarField.getText() + "\n"
                            + "Uang Kembali: " + kembaliField.getText() + "\n"
                            + "STRUK";
                hasilArea.setText(hasil);
            }
        });

        frame.setVisible(true);
    }
}