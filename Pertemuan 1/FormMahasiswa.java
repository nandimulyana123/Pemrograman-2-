package aplikasigajikaryawanptsintory;

import java.util.Scanner;

public class AplikasiConsole {
    private static String[] nimArray;
    private static String[] namaArray;
    private static double[] utsArray;
    private static double[] uasArray;
    private static double[] nilaiAkhirArray;
    private static char[] gradeArray;
    private static int jumlahData = 0;
    private static final int MAX_DATA = 100;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        nimArray = new String[MAX_DATA];
        namaArray = new String[MAX_DATA];
        utsArray = new double[MAX_DATA];
        uasArray = new double[MAX_DATA];
        nilaiAkhirArray = new double[MAX_DATA];
        gradeArray = new char[MAX_DATA];

        int pilihan;
        do {
            tampilkanMenu();
            System.out.print("Pilih menu (1-3): ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            switch (pilihan) {
                case 1:
                    inputData();
                    break;
                case 2:
                    tampilData();
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan aplikasi ini.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 3);
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== MENU APLIKASI ===");
        System.out.println("1. Input Data");
        System.out.println("2. Tampil Data");
        System.out.println("3. Keluar");
    }

    private static void inputData() {
        if (jumlahData >= MAX_DATA) {
            System.out.println("Kapasitas data penuh!");
            return;
        }

        System.out.println("\n=== INPUT DATA ===");
        System.out.print("NIM: ");
        nimArray[jumlahData] = scanner.nextLine();

        System.out.print("Nama: ");
        namaArray[jumlahData] = scanner.nextLine();

        System.out.print("Nilai UTS: ");
        utsArray[jumlahData] = scanner.nextDouble();

        System.out.print("Nilai UAS: ");
        uasArray[jumlahData] = scanner.nextDouble();
        scanner.nextLine(); // membersihkan newline

        // Hitung nilai akhir (40% UTS + 60% UAS)
        nilaiAkhirArray[jumlahData] = 0.4 * utsArray[jumlahData] + 0.6 * uasArray[jumlahData];

        // Tentukan grade
        if (nilaiAkhirArray[jumlahData] >= 85) {
            gradeArray[jumlahData] = 'A';
        } else if (nilaiAkhirArray[jumlahData] >= 75) {
            gradeArray[jumlahData] = 'B';
        } else if (nilaiAkhirArray[jumlahData] >= 65) {
            gradeArray[jumlahData] = 'C';
        } else if (nilaiAkhirArray[jumlahData] >= 50) {
            gradeArray[jumlahData] = 'D';
        } else {
            gradeArray[jumlahData] = 'E';
        }

        jumlahData++;
        System.out.println("Data berhasil disimpan!");
    }

    private static void tampilData() {
        if (jumlahData == 0) {
            System.out.println("\nBelum ada data yang dimasukkan!");
            return;
        }

        System.out.println("\n=== DAFTAR DATA ===");
        System.out.println("==================================================================");
        System.out.println("No.  NIM\tNama\t\tUTS\tUAS\tNilai Akhir\tGrade");
        System.out.println("==================================================================");

        for (int i = 0; i < jumlahData; i++) {
            System.out.printf("%-4d %-8s\t%-15s\t%.1f\t%.1f\t%.1f\t\t%c\n",
                    (i + 1), nimArray[i], namaArray[i], 
                    utsArray[i], uasArray[i], nilaiAkhirArray[i], gradeArray[i]);
        }
    }
}