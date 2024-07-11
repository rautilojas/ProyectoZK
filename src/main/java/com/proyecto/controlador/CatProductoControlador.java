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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

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
	
	public void onClick$btnAgregar(Event evt) {
		agregarProducto();
	}
	
	private Textbox txtNombre;
    private Textbox txtDescripcion;
    private Textbox txtPrecio;
    
    private void mostrarDatosProducto(CatProducto producto) {
        txtNombre.setValue(producto.getNombre());
        txtDescripcion.setValue(producto.getDescripcion());
        txtPrecio.setValue(String.valueOf(producto.getPrecio()));
    }

    public void onSelect$lbxCatProducto(Event event) { 
        Listitem selectedItem = lbxCatProducto.getSelectedItem();
        if (selectedItem != null) {
            CatProducto productoSeleccionado = selectedItem.getValue();
            mostrarDatosProducto(productoSeleccionado); 
        }
    }
    
    public void onClick$btnGuardar(Event evt) {
        Listitem selectedItem = lbxCatProducto.getSelectedItem();
        if (selectedItem != null) {
            CatProducto producto = selectedItem.getValue();

            producto.setNombre(txtNombre.getValue());
            producto.setDescripcion(txtDescripcion.getValue());
            try {
                producto.setPrecio(Integer.parseInt(txtPrecio.getValue()));
            } catch (NumberFormatException e) {
                Messagebox.show("Error: El precio debe ser un número entero.");
                return;
            }

            try {
                CatProductoDao.getInstance().update(producto);
                cargarCatProducto();
                Messagebox.show("Producto actualizado correctamente.");
            } catch (SQLException | NamingException e) {
                Messagebox.show("Error al actualizar el producto.");
                e.printStackTrace();
            }
        }
    }
	
	private void agregarProducto() {
	    String nombre = txtNombre.getValue();
	    String descripcion = txtDescripcion.getValue();
	    int precio;
	    try {
	        precio = Integer.parseInt(txtPrecio.getValue());
	    } catch (NumberFormatException e) {
	        System.out.println("Error: Precio no válido");
	        return; 
	    }
	    CatProducto nuevoProducto = new CatProducto();
	    nuevoProducto.setNombre(nombre);
	    nuevoProducto.setDescripcion(descripcion);
	    nuevoProducto.setPrecio(precio);
	    try {
	        int result = CatProductoDao.getInstance().insert(nuevoProducto);
	        if (result > 0) {
	            System.out.println("Producto agregado con éxito");
	            txtNombre.setValue("");
	            txtDescripcion.setValue("");
	            txtPrecio.setValue("");
	            cargarCatProducto();
	        } else {
	            System.out.println("Error al agregar el producto");
	        }
	    } catch (SQLException | NamingException e) {
	        e.printStackTrace();
	    }
	}

	
	private void eliminarProducto() {
	    Listitem selectedItem = lbxCatProducto.getSelectedItem();
	    if (selectedItem == null) {
	        System.out.println("Error: No hay producto seleccionado");
	        return; 
	    }
	    CatProducto productoEliminar = selectedItem.getValue();
	    Messagebox.show("¿Está seguro de que desea eliminar el producto: " + productoEliminar.getNombre() + "?", 
	                    "Confirmar eliminación", 
	                    Messagebox.YES | Messagebox.NO, 
	                    Messagebox.QUESTION, 
	                    event -> {
	                        if (event.getName().equals("onYes")) {
	                            try {
	                                int result = CatProductoDao.getInstance().eliminar(productoEliminar.getId());
	                                if (result > 0) {
	                                    System.out.println("Producto eliminado con éxito");
	                                    cargarCatProducto();
	                                } else {
	                                    System.out.println("Error al eliminar el producto");
	                                }
	                            } catch (SQLException | NamingException e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    });
	}
	public void onClick$btnEliminar(Event evt) {
		eliminarProducto();
	}
}