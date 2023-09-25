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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.listeners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import javax.swing.Timer;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;
/**
 * ACIDE - A Configurable IDE file editor text edition area mouse motion listener
 * 
 * @version 0.19
 * @see MouseMotionListener
 */
public class AcideFileEditorMouseMotionListener implements MouseMotionListener {
	
	/**
	 * ACIDE - A Configurable IDE timer.
	 */
    private Timer _timer = null;
	/**
	 * ACIDE - A Configurable IDE last mouse position.
	 */
    private Point _lastMousePosition;
   
    public AcideFileEditorMouseMotionListener() {
    	_timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mostrar el mensaje después de que el temporizador haya transcurrido un segundo
            	AcideFileEditorPanel selectedFileEditorPanelIndex = AcideMainWindow.getInstance().getFileEditorManager()
                        .getSelectedFileEditorPanel();
                Point punto = _lastMousePosition;
                int offset = selectedFileEditorPanelIndex.getActiveTextEditionArea().viewToModel(punto);
                int line = selectedFileEditorPanelIndex.getActiveTextEditionArea().getDocument().getDefaultRootElement().getElementIndex(offset) + 1;
                int column = offset - selectedFileEditorPanelIndex.getActiveTextEditionArea().getDocument().getDefaultRootElement().getElement(line - 1).getStartOffset() + 1;
                HashMap<String, String> errors = selectedFileEditorPanelIndex.get_errors();
				for (HashMap.Entry<String, String> entry : errors.entrySet()) {
					// Obtener la línea y columna de inicio de la palabra
					String[] parts = entry.getKey().split(":");
					int wordLine = Integer.parseInt(parts[0]);
					int wordnColumnStart = Integer.parseInt(parts[1]);
					int wordColumnEnd=Integer.parseInt(parts[2])+wordnColumnStart;
	                if((line==wordLine)&&(column>=wordnColumnStart)&&(column<=wordColumnEnd)) {
	                	AcideMainWindow.getInstance().getErrorPopup().SetErrorLabel(entry.getValue());     
	                	AcideMainWindow.getInstance().getErrorPopup().setVisible(true);
	                	AcideMainWindow.getInstance().getErrorPopup().pack();
	                }
					
				}
				
            }
        });
    	_timer.setRepeats(false);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        Point currentMousePosition = mouseEvent.getPoint();
        AcideMainWindow.getInstance().getErrorPopup().setVisible(false);
        if (_lastMousePosition == null) {
        	_lastMousePosition = currentMousePosition;
        } else if (!_lastMousePosition.equals(currentMousePosition)) {
            // Si el ratón se ha movido, reinicia el temporizador
        	AcideMainWindow.getInstance().getErrorPopup().setLocation(mouseEvent.getLocationOnScreen());
        	//selectedFileEditorPanelIndex.getErrorPopup().setLocation(mouseEvent.getX(), mouseEvent.getX());
        	_lastMousePosition = currentMousePosition;
        	_timer.restart();
            
        }
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	


}
