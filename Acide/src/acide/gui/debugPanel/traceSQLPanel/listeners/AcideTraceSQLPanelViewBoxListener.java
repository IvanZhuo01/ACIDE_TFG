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
package acide.gui.debugPanel.traceSQLPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComboBox;

import acide.gui.debugPanel.debugCanvas.tasks.AcideDebugCanvasParseTask;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.debugPanel.traceSQLPanel.AcideTraceSQLPanel;
import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

/**
 * 
 * ACIDE - A Configurable IDE trace SQL panel view box listener.
 * 
 * @version 0.15
 * @see ActionListener
 */
public class AcideTraceSQLPanelViewBoxListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		@SuppressWarnings("unchecked")
		JComboBox cb = (JComboBox) ev.getSource();
		if (cb.getSelectedIndex() < 1)
			return;
		// Gets the label of the selected item
		String query = (String) cb.getSelectedItem();

		if (query != null) {
			//enable the refresh button
			AcideTraceSQLPanel.refreshSQL.setEnabled(true);
			//enable the show view button
			AcideTraceSQLPanel.showView.setEnabled(true);
			// Updates the query
			AcideMainWindow.getInstance().getDebugPanel().getTraceSQLPanel()
					.setQuery(query);
			// Gets the trace SQL output for the trace sql query
			LinkedList<String> l = DesDatabaseManager.getInstance()
					.executeCommand("/tapi /trace_sql " + query);
			String result = "";
			for (String s : l) {
				result += s + "\n";
			}
			// Parses the output and generates the path grap (modifiaction 0.17 with /rdg)
			new Thread(new AcideDebugCanvasParseTask(result,
					AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
							.getInstance().getDebugPanel().getTraceSQLPanel()
							.getCanvas(),
					AcideDebugCanvasParseTask.DESTINY_PATH,query,false)).start();

			// Gets the PDG output for the query
			l = DesDatabaseManager.getInstance().executeCommand(
					"/tapi /rdg " + query);
			result = "";
			for (String s : l) {
				result += s + "\n";
			}
			// Parses the result and generates the graph
			final Thread t = new Thread(new AcideDebugCanvasParseTask(result,
					AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
							.getInstance().getDebugPanel().getTraceSQLPanel()
							.getCanvas(),
					AcideDebugCanvasParseTask.DESTINY_MAIN,query,false));
			t.start();
			if (AcideMainWindow.getInstance().getDebugPanel()
					.getTraceSQLPanel().getShowSQLMenuItem().isSelected()) {
				new Thread(new Runnable() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						try {
							t.join();
							// gets the label of the root node
							String query = AcideMainWindow.getInstance()
									.getDebugPanel().getTraceSQLPanel()
									.getCanvas().getRootNode().getLabel();
							AcideDebugHelper.getRoot(query);
						} catch (Exception e) {
							System.err.println(e);
						}
					}
				}).start();
			}
		}
	}
}
