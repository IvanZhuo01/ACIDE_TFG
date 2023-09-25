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
package acide.gui.graphCanvas.listeners;

import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.process.console.AcideDatabaseManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class AcideGraphCanvasMouseListener extends MouseAdapter {

    /**
     * ACIDE - A Configurable IDE debug canvas mouse listener target canvas.
     */
    private AcideGraphCanvas _canvas;

    /**
     *
     * Creates a new ACIDE - A Configurable IDE debug canvas mouse listener.
     *
     * @param canvas
     *            the target canvas.
     */
    public AcideGraphCanvasMouseListener(AcideGraphCanvas canvas) {
        this._canvas = canvas;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent ev) {
        try{

            // Checks the number of clicks
            if (ev.getClickCount() >= 2) {
                // Gets the graph of the canvas
                DirectedWeightedGraph graph = _canvas.get_graph();
                // Gets the nodes of the graph
                ArrayList<Node> nodes = graph.get_nodes();
                // Searches if a node has been clicked
                for (Node n : nodes) {
                    if (ev.getX() >= n.getX()
                            && ev.getX() <= n.getX()
                            + (int) (_canvas.getNodeSize() * _canvas
                            .getZoom())
                            && ev.getY() >= n.getY()
                            && ev.getY() <= n.getY()
                            + (int) (_canvas.getNodeSize() * _canvas
                            .getZoom())) {
                        String view;
                        if(n.getLabel().contains("/")) {
                            view = n.getLabel().split("/")[0];
                            AcideDatabaseDataView panelDv = AcideMainWindow.getInstance().getDataBasePanel().getDataView("$des", view);
                            LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll("$des", view);
                            if(!info.isEmpty())
                                panelDv.build(info);
                            panelDv.setAlwaysOnTop(true);
                            panelDv.setAlwaysOnTop(false);
                            panelDv.setVisible(true);
                        }
                    }
                }
            }
        }catch(Exception ex){

            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }

    }
}
