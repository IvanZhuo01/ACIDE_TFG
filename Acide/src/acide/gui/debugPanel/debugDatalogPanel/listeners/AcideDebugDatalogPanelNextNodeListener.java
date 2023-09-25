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
package acide.gui.debugPanel.debugDatalogPanel.listeners;

import acide.gui.debugPanel.debugDatalogPanel.AcideDebugDatalogDebugWindow;
import acide.gui.debugPanel.debugDatalogPanel.AcideDebugDatalogPanel;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ACIDE - A Configurable IDE debug datalog panel next node listener.
 *
 * @version 0.19
 * @see ActionListener
 *
 */
public class AcideDebugDatalogPanelNextNodeListener implements ActionListener {

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        AcideMainWindow.getInstance().getDebugPanel()
                .setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            //Updates the selected node
            AcideDebugDatalogPanel._canvas.advanceSelectedNode();
            AcideDebugDatalogPanel._canvas.repaint();

            //Updates the highlights
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                    .getHighLighter().resetLines();
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                    .getHighLighter().unHighLight();
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                    .getHighLighter().highLight(AcideDebugDatalogPanel._canvas
                    .getSelectedNode().getLabel());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        AcideMainWindow.getInstance().getDebugPanel()
                .setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }
}
