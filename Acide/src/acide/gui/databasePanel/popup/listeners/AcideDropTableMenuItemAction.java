/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 *
 * Copyright (C) 2007-2023
 * Authors:
 * 		- Fernando Sáenz Pérez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan José Ortiz Sánchez.
 *          - Delfín Rupérez Cañas.
 *      - Version 0.7:
 *          - Miguel Martín Lázaro.
 *      - Version 0.8:
 *      	- Javier Salcedo Gómez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Gutiérrez García-Pardo.
 *      	- Elena Tejeiro Pérez de Ágreda.
 *      	- Andrés Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      - Version 0.17
 *      	- Sergio Domínguez Fuentes
 * 		- Version 0.18
 * 			- Sergio García Rodríguez
 * 		- Version 0.19
 * 			- Carlos González Torres
 * 			- Cristina Lara López
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
package acide.gui.databasePanel.popup.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.databasePanel.utils.DataBasePanelUtils;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE drop table menu item.
 * 
 * @version 0.15
 * @see ActionListener
 */
public class AcideDropTableMenuItemAction implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
		
		int response = JOptionPane.showConfirmDialog(null,AcideLanguageManager.getInstance()
				.getLabels().getString("s2093"), AcideLanguageManager.getInstance()
				.getLabels().getString("s2050"), JOptionPane.OK_OPTION);
		
		if (response == 0){
		
			AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
			
			TreePath tree = panel.getTree().getSelectionPath();
			
			AcideDatabaseManager des =AcideDatabaseManager.getInstance();
			
			String table = tree.getLastPathComponent().toString();
			
			String database = tree.getParentPath().getParentPath().getLastPathComponent().toString();
			
			if (table.contains("(")){
				table = table.substring(0, table.indexOf("("));
			}
			LinkedList<String> res = des.dropTable(database, table);
			
			Iterator<String> it =  res.iterator();
			
			String result ="";
			
			boolean found = false;
			
			while (it.hasNext() && !found){
			
				result=it.next();
				
				found = result.contains("$success") || result.contains("KO") || result.contains("Dangling");
			}
			
			if (res.isEmpty() || result.contains("$success") || result.contains("KO")){
				DataBasePanelUtils.updateDataBasePanelTable();
			} else if (result.contains("Dangling")){
				JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
						.getLabels().getString("s2050"), JOptionPane.WARNING_MESSAGE);
				DataBasePanelUtils.updateDataBasePanelTable();
			}
			else{
				JOptionPane.showMessageDialog(null,AcideLanguageManager.getInstance()
						.getLabels().getString("s2095"), AcideLanguageManager.getInstance()
						.getLabels().getString("s2050"), JOptionPane.WARNING_MESSAGE);
				}
			}
	}

}
