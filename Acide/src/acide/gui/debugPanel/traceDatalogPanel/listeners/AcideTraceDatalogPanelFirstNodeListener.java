/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 *
 * Copyright (C) 2007-2023
 * Authors:
 * 		- Fernando S�enz P�rez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan Jos� Ortiz S�nchez.
 *          - Delf�n Rup�rez Ca�as.
 *      - Version 0.7:
 *          - Miguel Mart�n L�zaro.
 *      - Version 0.8:
 *      	- Javier Salcedo G�mez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Guti�rrez Garc�a-Pardo.
 *      	- Elena Tejeiro P�rez de �greda.
 *      	- Andr�s Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Sem�ramis Guti�rrez Quintana
 *      	- Juan Jes�s Marqu�s Ortiz
 *      	- Fernando Ord�s Lorente
 *      - Version 0.17
 *      	- Sergio Dom�nguez Fuentes
 * 		- Version 0.18
 * 			- Sergio Garc�a Rodr�guez
 * 		- Version 0.19
 * 			- Carlos Gonz�lez Torres
 * 			- Cristina Lara L�pez
 * 			- Yuejie Xu
 * 			- Yihang Zhuo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package acide.gui.debugPanel.traceDatalogPanel.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * 
 * ACIDE - A Configurable IDE trace datalog first node listener.
 * 
 * @version 0.16
 * 
 */
public class  AcideTraceDatalogPanelFirstNodeListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		AcideMainWindow
		.getInstance()
		.getDebugPanel()
		.setCursor(
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try{
		// Gets the canvas
		AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel()
				.getTraceDatalogPanel().getCanvas();
		// Updates the selected node
		int selectedNodeIndex = canvas.get_pathGraphindex();
		do{			
			canvas.retardSelectedNode();
			selectedNodeIndex = canvas.get_pathGraphindex();
		}while(selectedNodeIndex > 0);
		canvas.repaint();
		String selected = canvas.getSelectedNode().getLabel();
		// Updates the highlights
		AcideDebugPanelHighLighter highLighter = AcideMainWindow.getInstance()
				.getDebugPanel().getTraceDatalogPanel().getHighLighter();
		highLighter.resetLines();
		highLighter.unHighLight();
		highLighter.highLight(selected);
		}catch(Exception e){
			
		}
		AcideMainWindow
		.getInstance()
		.getDebugPanel()
		.setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

}
