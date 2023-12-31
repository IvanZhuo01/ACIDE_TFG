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
package acide.gui.listeners;

import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.editMenu.gui.AcideReplaceWindow;
import acide.gui.menuBar.editMenu.gui.AcideSearchWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ACIDE - A Configurable IDE search/replace window mouse listener.
 * 
 * @version 0.11
 * @see MouseAdapter
 */
public class AcideSearchReplaceWindowMouseListener extends MouseAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

		// Updates the search window variables
		AcideSearchWindow.getInstance().initializeVariables();

		// Updates the replace window variables
		AcideReplaceWindow.getInstance().initializeVariables();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent mouseEvent) {

		String selectedText = null;

		// Gets the selected file editor panel index
		int numeditor = AcideMainWindow.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanelIndex();

		// Gets the selected text
		selectedText = AcideMainWindow.getInstance().getFileEditorManager()
				.getFileEditorPanelAt(numeditor).getActiveTextEditionArea()
				.getSelectedText();

		// If there is selected text
		if (selectedText != null) {

			// Selects the selected text radio button in the search window
			AcideSearchWindow.getInstance().getSelectedTextRadioButton()
					.setSelected(true);

			// Deselects the both directions radio button in the search window
			AcideSearchWindow.getInstance().getBothDirectionsRadioButton()
					.setEnabled(false);
		} else {

			// Selects the current document radio button in the search window
			AcideSearchWindow.getInstance().getCurrentDocumentRadioButton()
					.setSelected(true);

			// Selects the both directions radio button in the search window
			AcideSearchWindow.getInstance().getBothDirectionsRadioButton()
					.setEnabled(true);
		}

		// Updates the search window variables
		AcideSearchWindow.getInstance().initializeVariables();
		
		// If there is selected text
		if (selectedText != null) {

			// Selects the selected text radio button in the replace window
			AcideReplaceWindow.getInstance().getSelectedTextRadioButton()
					.setSelected(true);

			// Deselects the both directions radio button in the replace window
			AcideReplaceWindow.getInstance().getBothDirectionsRadioButton()
					.setEnabled(false);
		} else {

			// Selects the current document radio button in the replace window
			AcideReplaceWindow.getInstance().getCurrentDocumentRadioButton()
					.setSelected(true);

			// Selects the both directions radio button in the replace window
			AcideReplaceWindow.getInstance().getBothDirectionsRadioButton()
					.setEnabled(true);
		}

		// Updates the replace window variables
		AcideReplaceWindow.getInstance().initializeVariables();
	}
}
