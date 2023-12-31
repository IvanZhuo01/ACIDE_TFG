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
package acide.gui.menuBar.configurationMenu.databasePanelMenu.showDetails.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.tree.DefaultMutableTreeNode;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.popup.AcideDataBasePanelTablesPopupMenu;
import acide.gui.databasePanel.popup.AcideDataBasePanelViewsPopupMenu;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE Name menu item listener.
 * 
 * @version 0.14
 * @see ActionListener
 */
public class AcideShowNameMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);
	}
	
	public static void action(ActionEvent event) {
		
		AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
		
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
		.getDatabasePanelMenu().getShowDetailsMenu().getNameMenuItem().setSelected(true);
		AcideDataBasePanelTablesPopupMenu.getInstance().getShowDetails().getNameMenuItem().setSelected(true);
		AcideDataBasePanelViewsPopupMenu.getInstance().getShowDetails().getNameMenuItem().setSelected(true);
		panel.setNameTables(true);
		
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
		.getDatabasePanelMenu().getShowDetailsMenu().getNameFieldsMenuItem().setSelected(false);
		AcideDataBasePanelTablesPopupMenu.getInstance().getShowDetails().getNameFieldsMenuItem().setSelected(false);
		AcideDataBasePanelViewsPopupMenu.getInstance().getShowDetails().getNameFieldsMenuItem().setSelected(false);
		panel.setNameFieldsTables(false);
		
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
		.getDatabasePanelMenu().getShowDetailsMenu().getNameFieldsTypesMenuItem().setSelected(false);
		AcideDataBasePanelTablesPopupMenu.getInstance().getShowDetails().getNameFieldsTypesMenuItem().setSelected(false);
		AcideDataBasePanelViewsPopupMenu.getInstance().getShowDetails().getNameFieldsTypesMenuItem().setSelected(false);
		panel.setNameFieldsTypesTables(false);
		
		DefaultMutableTreeNode nodoBase = (DefaultMutableTreeNode) panel.getTree().getModel().getChild(panel.getTree().getModel().getRoot(), 0);
		
		try{
			DefaultMutableTreeNode nodoDes = (DefaultMutableTreeNode) nodoBase.getFirstChild();
			DefaultMutableTreeNode nodoTables = (DefaultMutableTreeNode) nodoDes.getFirstChild();
			panel.updateDataBaseTree(nodoTables);
			panel.updateDataBaseTree((DefaultMutableTreeNode) nodoTables.getNextSibling());
			
		} catch (NoSuchElementException e){
				
			}
	}

}
