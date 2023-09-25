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

import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.DesDatabaseManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 *
 * ACIDE - A Configurable IDE debug datalog panel locate listener.
 *
 * @version 0.19
 * @see ActionListener
 *
 */
public class AcideDebugDatalogPanelLocateListener implements ActionListener {

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Gets the lines to locate the predicate
        LinkedList<String> l= DesDatabaseManager.getInstance()
                .executeCommand("/tapi /list_sources " + AcideMainWindow.getInstance().getDebugPanel()
                        .getDebugDatalogPanel().getCanvas().getSelectedNode().getLabel());

        if(AcideMainWindow.getInstance().getDebugPanel().getTabbedPane()
                .getSelectedIndex()==AcideMainWindow.getInstance().getDebugPanel()
                .getDebugDatalogPanelIndex() && DesDatabaseManager.getInstance().isDatalogSessionDebugging())
            l = AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                    .getPredicateLocation(AcideMainWindow.getInstance().getDebugPanel()
                            .getDebugDatalogPanel().getCanvas().getSelectedNode().getLabel());

        String result = "";
        for (String s : l) {
            result += s + "\n";
        }

        // Parses the result of the command
        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                .getHighLighter().parseListSources(result);

        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                .getHighLighter().updateCarretPosition();
    }
}
