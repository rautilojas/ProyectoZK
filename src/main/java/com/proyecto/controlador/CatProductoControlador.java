package com.proyecto.controlador;

import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.proyecto.model.CatProducto;

public class CatProductoControlador extends BaseControlador{
	
	private Listbox lbxCatProducto;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	private void cargarProducto() {
		List<CatProducto> lst = new ArrayList<CatProducto>();
		CatProducto prod = new CatProducto();
		prod.setId(1);
		prod.setNombre("Vainilla");
		prod.setDescripcion("Helado de vainilla");
		prod.setPrecio(2);
		lst.add(prod);
		
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
		}
	}
}
