    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package method;

import con_controller.Interfaces;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import koneksi.basisdata;
import view.barang_sekolah;

/**
 *
 * @author astse
 */
public class method_barang implements Interfaces{

    @Override
    public void Simpan(barang_sekolah barang) throws SQLException {
        try {
            Connection con = basisdata.getKoneksi();
            String sql = "INSERT INTO data_barang VALUES (?,?,?)";
            PreparedStatement prr = con.prepareStatement(sql);
            prr.setInt(1, Integer.parseInt(barang.txtidBarang.getText()));
            prr.setString(2, barang.txtnamaBarang.getText());
            prr.setInt(3, Integer.valueOf(barang.txthargaBarang.getText()));
            prr.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil di simpan");
            prr.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal di simpan");
            System.err.println("Error "+ e);
        } finally{
            Tampil(barang);
            Reset(barang);
            barang.setLebarKolom();
        }  
    }

    @Override
    public void Ubah(barang_sekolah barang) throws SQLException {
        try {
            Connection con = basisdata.getKoneksi();
            String sql = "UPDATE data_barang SET nama_barang=?, harga=? WHERE id_barang=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setInt(3, Integer.valueOf(barang.txtidBarang.getText()));
            prepare.setString(1, barang.txtnamaBarang.getText());
            prepare.setInt(2, Integer.valueOf(barang.txthargaBarang.getText()));
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
            } finally{
                Tampil(barang);
                barang.setLebarKolom();
                Reset(barang);
        }
    }

    @Override
    public void Hapus(barang_sekolah barang) throws SQLException {
        try {
            Connection con = basisdata.getKoneksi();
            String sql = "DELETE FROM data_barang WHERE id_barang=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setInt(1, Integer.valueOf(barang.txtidBarang.getText()));
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally{
            Tampil(barang);
            barang.setLebarKolom();
            Reset(barang);
        }
    }

    @Override
    public void Tampil(barang_sekolah barang) throws SQLException {
        barang.tblmodel.getDataVector().removeAllElements();
        barang.tblmodel.fireTableDataChanged();
        
        try {
            Connection con = basisdata.getKoneksi();
            Statement stt = con.createStatement();
            // Query menampilkan semua data pada tabel siswa
            // dengan urutan NIS dari kecil ke besar
            String sql = "SELECT * FROM data_barang ORDER BY id_barang ASC";
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[8];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                barang.tblmodel.addRow(ob);
            }        
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void KlikTable(barang_sekolah barang) throws SQLException {
        try {
            int pilih = barang.table2.getSelectedRow();
            if (pilih == -1) {
               return;
            }
            barang.txtidBarang.setText(barang.tblmodel.getValueAt(pilih, 0).toString());
            barang.txtnamaBarang.setText(barang.tblmodel.getValueAt(pilih, 1).toString());
            barang.txthargaBarang.setText(barang.tblmodel.getValueAt(pilih, 2).toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Reset(barang_sekolah barang) throws SQLException {
        barang.txtnamaBarang.setText("");
        barang.txthargaBarang.setText("");
    }
    
    public void AutoNumber(barang_sekolah barang) throws SQLException {
        try {
            Connection con = basisdata.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "SELECT MAX(id_barang) FROM data_barang";
            ResultSet res = stt.executeQuery(sql);

            while (res.next()) {
                int a = res.getInt(1);
                barang.txtidBarang.setText(Integer.toString(a + 1));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}