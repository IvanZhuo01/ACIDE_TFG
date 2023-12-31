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
package acide.gui.menuBar.configurationMenu.lexiconMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import acide.language.AcideLanguageManager;
import acide.gui.mainWindow.AcideMainWindow;

/**
 * ACIDE - A Configurable IDE lexicon menu new lexicon menu item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideNewLexiconMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		action(actionEvent);
	}
	
	public static void action(ActionEvent actionEvent){
		String lexiconConfiguration = "";
		String lexiconConfigurationName = "";

		// Asks to the user
		lexiconConfigurationName = JOptionPane.showInputDialog(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s453"), AcideLanguageManager.getInstance()
						.getLabels().getString("s454"),
				JOptionPane.INFORMATION_MESSAGE);

		// If it is ok
		if (lexiconConfigurationName != null
				&& !lexiconConfigurationName.equals("")) {

			// If it contains the .xml extension
			if (lexiconConfigurationName.contains(".xml"))

				// Compounds the full path
				lexiconConfiguration = "./configuration/lexicon/"
						+ lexiconConfigurationName;
			else
				// Adds it to the path
				lexiconConfiguration = "./configuration/lexicon/"
						+ lexiconConfigurationName + ".xml";

			// Creates the new lexicon configuration
			AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getLexiconConfiguration()
					.newLexicon(lexiconConfiguration);

		} else {

			if (lexiconConfigurationName != null)
				
				// Displays a "missing name" warning message
				JOptionPane
						.showMessageDialog(null, AcideLanguageManager
								.getInstance().getLabels().getString("s976"),
								AcideLanguageManager.getInstance().getLabels()
										.getString("s972"),
								JOptionPane.WARNING_MESSAGE);
		}
	}
}
