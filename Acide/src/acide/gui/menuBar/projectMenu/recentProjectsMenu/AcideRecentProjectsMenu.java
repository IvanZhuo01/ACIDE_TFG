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
package acide.gui.menuBar.projectMenu.recentProjectsMenu;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import acide.configuration.project.AcideProjectConfiguration;
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE recent projects menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideRecentProjectsMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE recent projects menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE recent projects menu clear recent projects
	 * menu item.
	 */
	private JMenuItem _clearRecentprojects;

	/**
	 * Creates a new ACIDE - A Configurable IDE recent projects menu.
	 */
	public AcideRecentProjectsMenu() {

		// Creates the clear recent projects menu item
		_clearRecentprojects = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s1039"));

		// Adds the listener to the clear recent projects menu item
		_clearRecentprojects.addActionListener(new ClearListMenuItemAction());

		// Builds the recent projects menu
		build();
	}

	/**
	 * Builds the ACIDE - A Configurable IDE recent projects menu.
	 */
	public void build() {

		// Removes all the menu items
		removeAll();

		// If the recent file list is empty
		if (AcideWorkbenchConfiguration.getInstance()
				.getRecentProjectsConfiguration().getList().size() == 0) {

			// Creates the menu item
			JMenuItem recentProjectMenuItem = new JMenuItem(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s1040"));

			// Disables it
			recentProjectMenuItem.setEnabled(false);

			// Adds it to the menu
			add(recentProjectMenuItem);

		} else {

			// Builds the menu with the recent file list in the workbench
			// configuration
			for (String filePath : AcideWorkbenchConfiguration.getInstance()
					.getRecentProjectsConfiguration().getList()) {

				// If the file exists
				if (new File(filePath).exists()) {
					
					// Creates the menu item
					JMenuItem recentProjectMenuItem = new JMenuItem(filePath);

					// Adds the action listener to the menu item
					recentProjectMenuItem
							.addActionListener(new RecentFileMenuItemAction());

					// Adds the recent project menu item to the menu
					add(recentProjectMenuItem);
				}
			}
		}

		// Adds a separator
		add(new JSeparator());

		// Adds the clear recent project menu item
		add(_clearRecentprojects);

		// Validates the changes in the menu
		revalidate();

		// Repaints the menu
		repaint();
	}

	/**
	 * ACIDE - A Configurable IDE recent projects menu recent file menu item
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class RecentFileMenuItemAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			/*
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
*/
			AcideMainWindow.getInstance().getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			AcideMainWindow.getInstance().getGlassPane().setVisible(true);
			// Asks for saving the project configuration
			if (AcideProjectConfiguration.getInstance()
					.askForSavingProjectConfiguration()) {

				// Saves the file editor configuration
				if (AcideMainWindow.getInstance().getFileEditorManager()
						.askForSavingModifiedFiles()) {

					// Gets the recent file menu item source
					JMenuItem recentFileMenuItem = (JMenuItem) actionEvent
							.getSource();

					// Gets the file path
					String filePath = recentFileMenuItem.getText();

					// If the file exists
					if (new File(filePath).exists()) {

						// Gets the number of file editors
						int numberOfFileEditorPanels = AcideMainWindow
								.getInstance().getFileEditorManager()
								.getNumberOfFileEditorPanels();

						// Closes all the files
						for (int index = 0; index < numberOfFileEditorPanels; index++) {

							// Closes the tab defined by index at the tabbed
							// pane
							AcideMainWindow.getInstance()
									.getFileEditorManager().getTabbedPane()
									.setSelectedIndex(0);

							// Closes the tab defined by index at the tabbed
							// pane
							AcideMainWindow.getInstance()
									.getFileEditorManager().getTabbedPane()
									.remove(0);

							// Closes the tab defined by index at the tabbed
							// pane
							AcideMainWindow.getInstance()
									.getFileEditorManager().getTabbedPane()
									.validate();
						}

						// Open the project
						AcideMainWindow.getInstance().getMenu()
								.getProjectMenu().openProject(filePath);
					} else

						// Displays an error message
						JOptionPane.showMessageDialog(null,
								AcideLanguageManager.getInstance().getLabels()
										.getString("s1046"), "Error",
								JOptionPane.ERROR_MESSAGE);
				}
			}
			/*
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		*/
			/*AcideMainWindow.getInstance().getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			AcideMainWindow.getInstance().getGlassPane().removeMouseListener(mouseAdapter);
			AcideMainWindow.getInstance().getGlassPane().setVisible(false);*/
		}
	}

	/**
	 * ACIDE - A Configurable IDE recent projects menu clear list menu item
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ClearListMenuItemAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Clears the recent file list
			AcideWorkbenchConfiguration.getInstance()
					.getRecentProjectsConfiguration().getList().clear();

			// Rebuilds the recent projects menu
			build();
		}
	}
}
