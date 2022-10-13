/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package con_controller;

import view.form_kasir;
import java.sql.SQLException;

/**
 *
 * @author astse
 */
public interface Interfaces2{
    public void Simpan(form_kasir kasir) throws SQLException;
    public void Batal(form_kasir kasir) throws SQLException;
    public void Tampil(form_kasir kasir) throws SQLException;
    public void KlikTable(form_kasir kasir) throws SQLException;
}
