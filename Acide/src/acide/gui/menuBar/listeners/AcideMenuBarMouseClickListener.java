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
package acide.gui.menuBar.listeners;

import acide.gui.mainWindow.AcideMainWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ACIDE - A Configurable IDE menu bar mouse click listener.
 * 
 * @version 0.11
 * @see MouseAdapter
 */
public class AcideMenuBarMouseClickListener extends MouseAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent mouseEvent) {

		// Configures the file menu
		AcideMainWindow.getInstance().getMenu().getFileMenu().configure();

		// Configures the edit menu
		AcideMainWindow.getInstance().getMenu().getEditMenu().configure();

		// Configures the project menu
		AcideMainWindow.getInstance().getMenu().getProjectMenu().configure();

		// Configures the lexicon menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getLexiconMenu().configure();

		// Configures the grammar menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getGrammarMenu().configure();

		// Configures the file editor menu
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
				.getFileEditorMenu().configure();

		// Updates the is console panel focused variable for the copy, cut and
		// paste menu items
		AcideMainWindow
				.getInstance()
				.getMenu()
				.setIsConsoleFocused(
						AcideMainWindow.getInstance().getConsolePanel()
								.getTextPane().isFocusOwner());
		// Validates the changes in the menu
		AcideMainWindow.getInstance().getMenu().validate();

		// Repaints the menu
		AcideMainWindow.getInstance().getMenu().repaint();
	}
}
