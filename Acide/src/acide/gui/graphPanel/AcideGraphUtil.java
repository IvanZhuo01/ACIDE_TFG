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
package acide.gui.graphPanel;

import acide.gui.consolePanel.tasks.AcideConsolePDGTask;
import acide.gui.consolePanel.tasks.AcideConsoleRDGTask;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.*;

public class AcideGraphUtil {

    public static void refreshGraphPanel(){
        //saves the send to console flag
        final boolean sendToConsole = AcideMainWindow
                .getInstance().getConsolePanel()
                .getProcessThread().getOutputGobbler()
                .get_sendToConsole();
        //updates the send to console to hide the output of the pdg command
        AcideMainWindow
                .getInstance().getConsolePanel()
                .getProcessThread().getOutputGobbler()
                .set_sendToConsole(false);

        //calls to the /pdg command and waits to the finalization
        String command;
        if (AcideMainWindow.getInstance().getGraphPanel().getRDG().isSelected()) {
            //sets the flag to generate the graph to true.
            AcideConsolePDGTask.getInstance().setGenerateGraph(false);
            //sets the flag to generate the graph to true.
            AcideConsoleRDGTask.getInstance().setGenerateGraph(true);
            command = "/rdg";
            AcideMainWindow
                    .getInstance().getConsolePanel()
                    .sendCommandToConsole(command, "");
            AcideMainWindow.getInstance().getConsolePanel()
                    .getProcessThread().getOutputGobbler()
                    .waitForTaskDone(1000);
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    //waits to the finalization of the generation of the graph,
                    //restore the send to console flag and updates the generate graph flag.
                    AcideConsoleRDGTask.getInstance().run();
                    AcideConsoleRDGTask.getInstance().waitForTaskDone(1000);
                    AcideMainWindow
                            .getInstance().getConsolePanel()
                            .getProcessThread().getOutputGobbler()
                            .set_sendToConsole(sendToConsole);
                    AcideConsoleRDGTask.getInstance().setGenerateGraph(false);
                }
            });

            AcideGraphPanel._canvas.setZoom(1, AcideGraphCanvas.CanvasPanel.Graph);
        }
        else{
            //sets the flag to generate the graph to true.
            AcideConsoleRDGTask.getInstance().setGenerateGraph(false);
            //sets the flag to generate the graph to true.
            AcideConsolePDGTask.getInstance().setGenerateGraph(true);
            command = "/pdg";
            AcideMainWindow
                    .getInstance().getConsolePanel()
                    .sendCommandToConsole(command, "");
            AcideMainWindow.getInstance().getConsolePanel()
                    .getProcessThread().getOutputGobbler()
                    .waitForTaskDone(1000);
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    //waits to the finalization of the generation of the graph,
                    //restore the send to console flag and updates the generate graph flag.
                    AcideConsolePDGTask.getInstance().waitForTaskDone(1000);
                    AcideMainWindow
                            .getInstance().getConsolePanel()
                            .getProcessThread().getOutputGobbler()
                            .set_sendToConsole(sendToConsole);
                    AcideConsolePDGTask.getInstance().setGenerateGraph(false);
                }
            });

            AcideGraphPanel._canvas.setZoom(1, AcideGraphCanvas.CanvasPanel.Graph);
        }
    }
}
