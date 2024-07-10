package com.proyecto.controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.proyecto.dao.CatProductoDao;
import com.proyecto.model.CatProducto;

public class CatProductoControlador extends BaseControlador{
	
	private Listbox lbxCatProducto;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		cargarCatProducto();
	}
	
	private void cargarCatProducto() {
		List<CatProducto> lst = null;
		try {
			lst = CatProductoDao.getInstance().getLstProductos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
			if (lst != null && lst.size() > 0) {
				lbxCatProducto.getItems().clear();
				Listitem item;
				Listcell cell;
				for(CatProducto _prod : lst) {
					item = new Listitem();
					item.setParent(lbxCatProducto);
					item.setValue(_prod);
					
					cell = new Listcell();
					cell.setParent(item);
					cell.setLabel(String.valueOf(_prod.getId()));
					
					cell = new Listcell();
					cell.setParent(item);
					cell.setLabel(_prod.getNombre());
					
					cell = new Listcell();
					cell.setParent(item);
					cell.setLabel(_prod.getDescripcion());
					
					cell = new Listcell();
					cell.setParent(item);
					cell.setLabel(Integer.toString(_prod.getPrecio()));
				}
				lbxCatProducto.invalidate();
		}	
	}
	
	public void onClick$btnGuardar(Event evt) {
		guardarProducto();
	}
	
	private void guardarProducto() {
		System.out.println("----Guardando----s");
		CatProducto prod = new CatProducto();
		prod.setId(3);
		prod.setNombre("Chocolate rosa");
		prod.setDescripcion("Baño");
		prod.setPrecio((int) 2.50);
		
		int result;
		try {
			result = CatProductoDao.getInstance().insert(prod);
			if(result == 0) {
				System.out.println("Ha ocurrido un error, intente de nuevo");
			} else {
				System.out.println("Producto agregado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}