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
package acide.gui.databasePanel.utils;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.NoSuchElementException;

public class DataBasePanelUtils {

    public static void updateDataBasePanelView(){
        AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();

        DefaultMutableTreeNode nodeBase = (DefaultMutableTreeNode) panel.getTree().getModel()
                .getChild(panel.getTree().getModel().getRoot(), 0);

        try{
            DefaultMutableTreeNode nodoDes = (DefaultMutableTreeNode) nodeBase.getFirstChild();
            DefaultMutableTreeNode nodoTables = (DefaultMutableTreeNode) nodoDes.getFirstChild();
            panel.updateDataBaseTree((DefaultMutableTreeNode) nodoTables.getNextSibling());
        } catch (NoSuchElementException e){

        }
    }

    public static void updateDataBasePanelTable(){
        AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();

        DefaultMutableTreeNode nodeBase = (DefaultMutableTreeNode) panel.getTree().getModel()
                .getChild(panel.getTree().getModel().getRoot(), 0);

        try{
            DefaultMutableTreeNode nodoDes = (DefaultMutableTreeNode) nodeBase.getFirstChild();
            DefaultMutableTreeNode nodoTables = (DefaultMutableTreeNode) nodoDes.getFirstChild();
            panel.updateDataBaseTree(nodoTables);
        } catch (NoSuchElementException e){

        }
    }

    public static void pasteDataBasePanelTable(Clipboard clipboard){
        try {
            String oldTable = (String) clipboard.getData(DataFlavor.stringFlavor);
            String newTable = (String) JOptionPane.showInputDialog(null,
                    AcideLanguageManager.getInstance().getLabels().getString("s2119"),
                    AcideLanguageManager.getInstance().getLabels().getString("s2120"),
                    JOptionPane.PLAIN_MESSAGE, null, null, oldTable);

            if ((newTable != null) && (newTable.length() > 0)) {

                AcideDatabaseManager des = AcideDatabaseManager.getInstance();

                int option = AcideDatabaseCopyTableOption.getInstance().getOption();

                String res = des.pasteTable(newTable, oldTable, option);

                if (res.contains("success"))
                    DataBasePanelUtils.updateDataBasePanelTable();

                else JOptionPane.showMessageDialog(null, res,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
