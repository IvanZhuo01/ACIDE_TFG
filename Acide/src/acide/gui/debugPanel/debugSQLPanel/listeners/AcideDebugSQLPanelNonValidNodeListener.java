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
package acide.gui.debugPanel.debugSQLPanel.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE trace SQL color node listener.
 * 
 * @see ActionListener
 * @version 0.17
 * 
 */
public class AcideDebugSQLPanelNonValidNodeListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(AcideDebugSQLDebugWindow.getInstance().isVisible())
				AcideDebugSQLDebugWindow.getInstance().setAlwaysOnTop(false);

			// Gets the canvas
			AcideDebugCanvas canvas = AcideMainWindow.getInstance()
					.getDebugPanel().getDebugSQLPanel().getCanvas();
			String view =  canvas.getSelectedNode().getLabel().split("/")[0];
			if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel()
					.isDebugging() && !AcideDebugHelper.isRootView(view)) {
				AcideDebugSQLDebugWindow.getInstance().removeError();
				AcideDebugHelper.performViewNodeDebug(view, "nonvalid");
			}else{
				AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebugging(true);
				AcideDebugHelper.startViewNodeDebug(view, "nonvalid");
			}
			if(AcideDebugSQLDebugWindow.getInstance().isVisible())
				AcideDebugSQLDebugWindow.getInstance().setAlwaysOnTop(true);
		} catch (Exception ex) {
			AcideMainWindow.getInstance().getDebugPanel()
					.setCursor(Cursor.getDefaultCursor());
		}
	}
}
