/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package con_controller;

import view.barang_sekolah;
import java.sql.SQLException;
/**
 *
 * @author astse
 */
public interface Interfaces {
    public void Simpan(barang_sekolah barang) throws SQLException;
    public void Ubah(barang_sekolah barang) throws SQLException;
    public void Hapus(barang_sekolah barang) throws SQLException;
    public void Tampil(barang_sekolah barang) throws SQLException;
    public void KlikTable(barang_sekolah barang) throws SQLException;
    public void Reset(barang_sekolah barang) throws SQLException;
}
