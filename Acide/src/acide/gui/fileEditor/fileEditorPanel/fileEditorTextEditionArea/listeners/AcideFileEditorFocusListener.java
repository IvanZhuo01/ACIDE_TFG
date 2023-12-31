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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.fileEditor.fileEditorPanel.errorpopup.AcidefileEditorPanelErrorpopup;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.mainWindow.utils.AcideLastElementOnFocus;

/**
 * ACIDE - A Configurable IDE file editor text edition area focus listener.
 * 
 * @version 0.11
 * @see FocusListener
 */
public class AcideFileEditorFocusListener implements FocusListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent focusEvent) {
		// Dispatches the event
		dispatchEvent(focusEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	/**
	 * Disable the error popup
	 */
	@Override
	public void focusLost(FocusEvent focusEvent) {
		// Dispatches the event
		dispatchEvent(focusEvent);
         Timer _timer = null;
    	_timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                 Component oppositeComponent = focusEvent.getOppositeComponent();
                 if (oppositeComponent == null || oppositeComponent != AcideMainWindow.getInstance().getErrorPopup()) {            
                	 AcideMainWindow.getInstance().getErrorPopup().setVisible(false);
                 }
            }
        });
    	_timer.start();
    	_timer.setRepeats(false);
	}
	
	/**
	 * Dispatches the focus event.
	 * 
	 * @param focusEvent
	 *            focus event.
	 */
	private void dispatchEvent(FocusEvent focusEvent) {

		// Updates the last element on focus in the main window
		AcideMainWindow.getInstance().setLastElementOnFocus(
				AcideLastElementOnFocus.FILE_EDITOR);
	}
}
