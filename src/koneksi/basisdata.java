/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author astse
 */
public class basisdata {
    public static Connection koneksi;
    public static Connection getKoneksi() {
        if(koneksi == null){
            try {
                String url = "jdbc:mysql://localhost/kasir_tu";
                String username = "root";
                String password = "";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, username, password);
                JOptionPane.showMessageDialog(null, "Koneksi Berhasil!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Koneksi Gagal");
                System.out.println("Error "+e.getMessage());
            }
        }
        return koneksi;
    }
}
