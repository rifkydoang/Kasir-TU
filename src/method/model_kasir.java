/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package method;

import con_controller.Interfaces2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import koneksi.basisdata;
import view.form_kasir;

/**
 *
 * @author astse
 */
public class model_kasir implements Interfaces2{
int stok, harga, jumlah;
    @Override
    public void Simpan(form_kasir kasir) throws SQLException {
        try {
            Connection con = basisdata.getKoneksi();
            String sql = "INSERT INTO data_penjualan VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement prr = con.prepareStatement(sql);
            
            prr.setString(1, kasir.txtnoTransaksi.getText());
            prr.setString(2, kasir.txtnamaCustomer.getText());
            prr.setString(3, (String) kasir.cmbnamaBarang.getSelectedItem());
            prr.setInt(4, Integer.parseInt(kasir.txtHarga.getText()));
            prr.setInt(5, Integer.parseInt((String) kasir.cmbJumlah.getSelectedItem()));
            prr.setInt(6, Integer.parseInt(kasir.txttotalBayar.getText()));
            prr.setInt(7, Integer.parseInt(kasir.txtBayar.getText()));
            prr.setInt(8, Integer.parseInt(kasir.txtKembalian.getText()));
            prr.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil di simpan");
            prr.close();
            Batal(kasir);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal di simpan");
            System.err.println("Error "+ e);
        } finally{
            Tampil(kasir);
            kasir.setLebarKolom();
        } 
    }

    @Override
    public void Batal(form_kasir kasir) throws SQLException {
        kasir.txtnamaCustomer.setText("");
        kasir.cmbnamaBarang.setSelectedIndex(0);
        kasir.txtHarga.setText("");
        kasir.cmbJumlah.setSelectedIndex(0);
        kasir.txttotalBayar.setText("");
        kasir.txtBayar.setText("");
        kasir.txtBiaya.setText("");
        kasir.txtKembalian.setText("");
    }
    
    public void tampilComboBarang(form_kasir kasir) throws SQLException{
        try {
            Connection con = basisdata.getKoneksi();
            String sql = "SELECT * FROM data_barang";
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            
            while (rs.next()) {                
                kasir.cmbnamaBarang.addItem(rs.getString("nama_barang"));
            }
            
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void tampilDataBarang(form_kasir kasir) throws SQLException{
        String tmp = (String) kasir.cmbnamaBarang.getSelectedItem();
        try {
            Connection con = basisdata.getKoneksi();
            String sql = "SELECT harga, stok FROM data_barang WHERE nama_barang=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, tmp);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                harga = Integer.valueOf(rs.getString("harga"));
                stok = Integer.valueOf(rs.getString("stok"));
                kasir.txtHarga.setText(String.valueOf(harga));
                kasir.labelStok.setText(String.valueOf(stok));
            }
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void totalTransaksi(form_kasir kasir){
        int harga, total;
        
        harga = Integer.parseInt(kasir.txtHarga.getText());
        jumlah = Integer.parseInt((String) kasir.cmbJumlah.getSelectedItem());
        total = jumlah * harga;
        
        if (jumlah > stok) {
            JOptionPane.showMessageDialog(null, "Stock Hannya Tersisa "+stok);
            
        }
        
        kasir.txttotalBayar.setText(String.valueOf(total));
        kasir.txtBiaya.setText(String.valueOf("Rp."+total));
    }
    
    public void autonumber(form_kasir kasir) {
        PreparedStatement statement = null;
        Connection con = basisdata.getKoneksi();
       int nomor_berikutnya = 0;
       String urutan = "";
        try {
            
            String COUNTER = "SELECT ifnull(max(convert(right(no_transaksi,5), signed integer)),0) as kode,"
            + "ifnull(length(max(convert(right(no_transaksi,5), signed integer))),0) as panjang FROM data_penjualan";
            
            statement = con.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if(rs2.next()){
                nomor_berikutnya = rs2.getInt("kode") + 1;
               if (rs2.getInt("kode") != 0) {
                            if (rs2.getInt("panjang") == 1) {
                                urutan = "NTM" + "0000" + nomor_berikutnya;
                            } else if (rs2.getInt("panjang") == 2) {
                               urutan = "NTM" + "000" + nomor_berikutnya;
                            }else if (rs2.getInt("panjang") == 3) {
                               urutan = "NTM" + "00" + nomor_berikutnya;
                            }else if (rs2.getInt("panjang") == 4) {
                               urutan = "NTM" + "0" + nomor_berikutnya;
                            }else if (rs2.getInt("panjang") == 5) {
                               urutan = "NTM"+ nomor_berikutnya;
                            }
                   kasir.txtnoTransaksi.setText(urutan);
                }
               else {
                   urutan = "NTM"+ "00001";
                   kasir.txtnoTransaksi.setText(urutan);
               }
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    @Override
    public void Tampil(form_kasir kasir) throws SQLException {
        kasir.tblmodel.getDataVector().removeAllElements();
        kasir.tblmodel.fireTableDataChanged();
        
        try {
            Connection con = basisdata.getKoneksi();
            Statement stt = con.createStatement();
            // Query menampilkan semua data pada tabel siswa
            // dengan urutan NIS dari kecil ke besar
            String sql = "SELECT * FROM data_penjualan ORDER BY no_transaksi ASC";
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[8];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                ob[3] = res.getString(4);
                ob[4] = res.getString(5);
                ob[5] = res.getString(6);
                ob[6] = res.getString(7);
                ob[7] = res.getString(8);
                kasir.tblmodel.addRow(ob);
            }        
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void KlikTable(form_kasir kasir) throws SQLException {
        try {
            int pilih = kasir.table.getSelectedRow();
            if (pilih == -1) {
               return;
            }
            kasir.txtnoTransaksi.setText(kasir.tblmodel.getValueAt(pilih, 0).toString());
            kasir.txtnamaCustomer.setText(kasir.tblmodel.getValueAt(pilih, 1).toString());
            kasir.cmbnamaBarang.setSelectedItem(kasir.tblmodel.getValueAt(pilih, 2));
            kasir.txtHarga.setText(kasir.tblmodel.getValueAt(pilih, 3).toString());
            kasir.cmbJumlah.setSelectedItem(kasir.tblmodel.getValueAt(pilih, 4));
            kasir.txttotalBayar.setText(kasir.tblmodel.getValueAt(pilih, 5).toString());
            kasir.txtBayar.setText(kasir.tblmodel.getValueAt(pilih, 6).toString());
            kasir.txtKembalian.setText(kasir.tblmodel.getValueAt(pilih, 7).toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void UpdateDataStokJam(form_kasir kasir) throws SQLException{
      String tmp = (String) kasir.cmbnamaBarang.getSelectedItem();
      stok -= jumlah;
       try {
            Connection con = basisdata.getKoneksi();
            String sql = "UPDATE data_barang SET stok=? WHERE nama_barang=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, stok);
            pst.setString(2, tmp);
            pst.executeUpdate();
            kasir.labelStok.setText(String.valueOf(stok));
            pst.close();
       } catch (Exception e) {
            System.out.println(e);
       }
   }
}
