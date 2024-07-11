package com.proyecto.dao;

import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.proyecto.model.CatProducto;
import com.proyecto.utils.Commons;

public class CatProductoDao {
	
	private DataSource ds;
	private QueryRunner qr;
	private static CatProductoDao dao;
	
	private final String SQL_INSERT = "INSERT INTO cat_producto" +
									"(id, nombre, descripcion, precio)" +
									"VALUES(?,?,?,?)\n";
	
	private final String SQL_SELECT = "SELECT id, nombre, descripcion, precio FROM cat_producto";
	
	private final String SQL_DELETE = "DELETE FROM cat_producto WHERE id = ?";
	
	private final String SQL_UPDATE = "UPDATE cat_producto SET nombre = ?, descripcion = ?, precio = ? WHERE id = ?";
	
	public CatProductoDao () throws NamingException {
		this.ds = Commons.getDS();
		this.qr = new QueryRunner(ds);
		
	}
	
	public static CatProductoDao getInstance() throws NamingException {
		if(dao == null) {
			dao = new CatProductoDao();
		}
		return dao;
	}
	
	public int insert(CatProducto producto) throws SQLException {
		int result = 0;
		Object[] params = {producto.getId(),producto.getNombre(),producto.getDescripcion(),producto.getPrecio()};
		result = qr.update(SQL_INSERT, params);
		return result;
	}
	
	public List<CatProducto> getLstProductos() throws SQLException{
		ResultSetHandler<List<CatProducto>> rsh = new BeanListHandler<CatProducto>(CatProducto.class);
		return qr.query(SQL_SELECT, rsh);
	}
	
	public int eliminar(int idProducto) throws SQLException {
	    return qr.update(SQL_DELETE, idProducto);
	}
	
	 public int update(CatProducto producto) throws SQLException {
	        Object[] params = {producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getId()};
	        return qr.update(SQL_UPDATE, params);
	    }


}