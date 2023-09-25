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
package acide.gui.debugPanel.debugSQLPanel.listeners;

import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class AcideDebugSQLPanelShowErrorsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        if(AcideDebugSQLDebugWindow.getInstance().isVisible())
            AcideDebugSQLDebugWindow.getInstance().setAlwaysOnTop(false);

        String info = "<html>";
        if(AcideDebugSQLDebugWindow.getInstance().getErrors().size() > 0){
            LinkedList<String> status = DesDatabaseManager.getInstance().getSQLNodeStates();

            info += "<br>" + AcideLanguageManager.getInstance().getLabels()
                    .getString("s2365");

            for (String node: status) {
                if (AcideDebugSQLDebugWindow.getInstance().getErrors().containsKey(node))
                    info += "<br>" + " " + " " + " - " + AcideDebugSQLDebugWindow.getInstance()
                            .getErrors().get(node);
            }
        }
        info += "</html>";
        JLabel label = new JLabel(info);

        JOptionPane.showMessageDialog(null, label);
        if(AcideDebugSQLDebugWindow.getInstance().isVisible())
            AcideDebugSQLDebugWindow.getInstance().setAlwaysOnTop(true);
    }
}
