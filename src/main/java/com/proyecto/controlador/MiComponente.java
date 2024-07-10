package com.proyecto.controlador;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MiComponente extends SelectorComposer<Window> {

    @Wire
    Textbox txtNombre;

    public void buscarUsuario() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/baseprueba");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cat_producto WHERE nombre='" + txtNombre.getValue() + "'");

            // Procesar resultados...

        } catch (Exception e) {
            // Manejar errores...
        }
    }
}
