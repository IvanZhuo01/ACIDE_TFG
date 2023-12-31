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
package acide.gui.fileEditor.fileEditorManager.utils.logic.closeButton;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.UIResource;

import acide.gui.fileEditor.fileEditorManager.utils.logic.closeButton.listeners.AcideFileEditorCloseButtonActionListener;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE file editor close button.
 * 
 * Implements used UIResource when the close button is added to the TabbedPane.
 * 
 * @version 0.11
 * @see UIResource
 */
public class AcideFileEditorCloseButton extends JButton implements UIResource {

	/**
	 * ACIDE - A Configurable IDE file editor close button class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Red icon for the closing button.
	 */
	private static final String RED_ICON = "./resources/icons/editor/closeModified.png";
	/**
	 * Green icon for the closing button.
	 */
	private static final String GREEN_ICON = "./resources/icons/editor/closeNotModified.png";
	/**
	 * Path of the displayed icon.
	 */
	private String _selectedIcon = "";

	/**
	 * Creates a new close button.
	 * 
	 * @param index
	 *            editor index.
	 */
	public AcideFileEditorCloseButton(int index) {

		super(new AcideFileEditorCloseButtonActionListener(index));

		// Sets the green icon
		_selectedIcon = GREEN_ICON;
		
		// Sets the icon
		setIcon(new ImageIcon(_selectedIcon));

		// Sets the tool tip text
		setToolTipText(AcideLanguageManager.getInstance().getLabels()
				.getString("s316"));

		// Sets its margin
		setMargin(new Insets(0, 0, 0, 0));
		
		// Validates the changes
		validate();
	}

	/**
	 * Sets the color of the button to red.
	 */
	public void setRedCloseButton() {
		_selectedIcon = RED_ICON;
		setIcon(new ImageIcon(RED_ICON));
	}

	/**
	 * Sets the color of the button to green.
	 */
	public void setGreenCloseButton() {
		_selectedIcon = GREEN_ICON;
		setIcon(new ImageIcon(GREEN_ICON));
	}

	/**
	 * Returns true if the button is red and false in other case.
	 * 
	 * @return true if the button is red and false in other case.
	 */
	public boolean isRedButton() {
		return _selectedIcon.matches(RED_ICON);
	}
}
