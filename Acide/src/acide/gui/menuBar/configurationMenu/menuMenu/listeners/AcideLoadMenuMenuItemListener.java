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
package acide.gui.menuBar.configurationMenu.menuMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemInformation;
import acide.configuration.project.AcideProjectConfiguration;
import acide.files.AcideFileExtensionFilterManager;
import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.AcideMenuConfigurationWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE menu menu load menu menu item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideLoadMenuMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		// Asks the the file to the user
		String absolutePath = AcideFileManager.getInstance().askForFile(
				AcideFileOperation.OPEN,
				AcideFileTarget.FILES,
				AcideFileType.FILE,
				"./configuration/menu/",
				new AcideFileExtensionFilterManager(
						new String[] { "menuConfig" }, AcideLanguageManager
								.getInstance().getLabels().getString("s287")));

		if (absolutePath != null) {

			// Creates the menu item list
			ArrayList<AcideMenuItemInformation> menuItemList = null;

			try {

				// Loads the menu item list
				menuItemList = AcideMenuConfiguration.getInstance()
						.loadMenuConfigurationFile(absolutePath);

				// Updates the the ACIDE - A Configurable IDE current menu
				// configuration
				AcideResourceManager.getInstance().setProperty(
						"currentMenuConfiguration", absolutePath);

				// Sets the new menu item list
				AcideMenuConfiguration.getInstance().setMenuElementList(
						menuItemList);

				// Builds the menu
				AcideMainWindow.getInstance().getMenu()
						.updateComponentsVisibility();

				// Validates the changes in the main window
				AcideMainWindow.getInstance().validate();

				// Repaints the main window
				AcideMainWindow.getInstance().repaint();

				// Disables the save menu item
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
						.getMenuMenu().getSaveMenuMenuItem().setEnabled(false);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s289"));

				// The changes are saved now
				AcideMenuConfigurationWindow.setChangesAreSaved(true);

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);

			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s288")
						+ " " + absolutePath, AcideLanguageManager
						.getInstance().getLabels().getString("289"),
						JOptionPane.ERROR_MESSAGE);

				// Updates the log
				AcideLog.getLog().error(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s288")
								+ " " + absolutePath);
			}
		}
	}
}
