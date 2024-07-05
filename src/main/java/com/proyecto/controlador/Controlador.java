package com.proyecto.controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Label;

public class Controlador extends BaseControlador{
	
	private Label lblSaludo;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		lblSaludo.setValue("Hola");
	}
	public void onClick$btnSaludo(Event evt) {
		lblSaludo.setValue("Noo mis botones de gomita");
		lblSaludo.setStyle("color: blue");
	}

}
