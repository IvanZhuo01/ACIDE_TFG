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
package acide.gui.explorerPanel.listeners;

import acide.configuration.project.AcideProjectConfiguration;
import acide.files.project.AcideProjectFile;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * ACIDE - A Configurable IDE explorer panel popup menu listener.
 * 
 * @version 0.11
 * @see MouseAdapter
 */
public class AcideExplorerPanelPopupMenuListener extends MouseAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		maybeShowPopup(mouseEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		maybeShowPopup(mouseEvent);
	}

	/**
	 * Shows the popup menu
	 * 
	 * @param mouseEvent
	 *            mouse event
	 */
	private void maybeShowPopup(MouseEvent mouseEvent) {

		if (mouseEvent.isPopupTrigger()) {

			// If it is the default project
			if (AcideProjectConfiguration.getInstance().isDefaultProject()) {

				// Disables the save project menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getSaveProjectMenuItem().setEnabled(false);

				// Disables the new file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getNewProjectFileMenuItem().setEnabled(false);

				// Disables the add file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getAddFileMenuItem().setEnabled(false);

				// Disables the remove file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getRemoveFileMenuItem().setEnabled(false);

				// Disables the delete file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getDeleteFileMenuItem().setEnabled(false);

				// Disables the set main file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getSetMainFileMenuItem().setEnabled(false);

				// Disables the unset main file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getUnsetMainFileMenuItem().setEnabled(false);

				// Disables the set compilable file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getSetCompilableFileMenuItem().setEnabled(false);

				// Disables the unset compilable file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getUnsetCompilableFileMenuItem().setEnabled(false);

				// Disables the add folder menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getAddFolderMenuItem().setEnabled(false);

				// Disables the remove folder menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getRemoveFolderMenuItem().setEnabled(false);
			} else {

				// Not default project

				// Disables the save project menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getSaveProjectMenuItem().setEnabled(false);

				// Enables the new file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getNewProjectFileMenuItem().setEnabled(true);

				// Enables the add file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getAddFileMenuItem().setEnabled(true);

				// Disables the remove file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getRemoveFileMenuItem().setEnabled(false);

				// Disables the delete file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getDeleteFileMenuItem().setEnabled(false);

				// Disables the set main file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getSetMainFileMenuItem().setEnabled(false);

				// Disables the unset main file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getUnsetMainFileMenuItem().setEnabled(false);

				// Disables the set compilable file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getSetCompilableFileMenuItem().setEnabled(false);

				// Disables the unset compilable file menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getUnsetCompilableFileMenuItem().setEnabled(false);

				// Enables the add folder menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getAddFolderMenuItem().setEnabled(true);

				// Disables the remove folder menu item
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
						.getRemoveFolderMenuItem().setEnabled(false);

				// If the project configuration has been modified
				if (AcideProjectConfiguration.getInstance().isModified())

					// Enables the save project menu item
					AcideMainWindow.getInstance().getExplorerPanel()
							.getPopupMenu().getSaveProjectMenuItem()
							.setEnabled(true);

				// Gets the tree path
				TreePath path = AcideMainWindow.getInstance()
						.getExplorerPanel().getTree().getSelectionPath();
				DefaultMutableTreeNode filePath;
				AcideProjectFile file;

				if (path != null) {

					// Gets the file path from the explorer tree
					filePath = (DefaultMutableTreeNode) path
							.getLastPathComponent();

					// Builds the project file
					file = (AcideProjectFile) filePath.getUserObject();

					// If it is not a directory
					if (!file.isDirectory()) {

						// Enables the remove file menu item
						AcideMainWindow.getInstance().getExplorerPanel()
								.getPopupMenu().getRemoveFileMenuItem()
								.setEnabled(true);

						// Enables the delete file menu item
						AcideMainWindow.getInstance().getExplorerPanel()
								.getPopupMenu().getDeleteFileMenuItem()
								.setEnabled(true);

						// If it is not a MAIN FILE
						if (!file.isMainFile())

							// Enables the set main file menu item
							AcideMainWindow.getInstance().getExplorerPanel()
									.getPopupMenu().getSetMainFileMenuItem()
									.setEnabled(true);
						else
							// Enables the unset main file menu item
							AcideMainWindow.getInstance().getExplorerPanel()
									.getPopupMenu().getUnsetMainFileMenuItem()
									.setEnabled(true);

						// If it is not a COMPILABLE FILE or is COMPILABLE and a
						// MAIN FILE
						if (!file.isCompilableFile()
								|| (file.isCompilableFile() && file
										.isMainFile()))

							// Enables the set compilable file menu item
							AcideMainWindow.getInstance().getExplorerPanel()
									.getPopupMenu()
									.getSetCompilableFileMenuItem()
									.setEnabled(true);

						// If it is COMPILABLE FILE and it is not a MAIN FILE
						if (file.isCompilableFile() && !file.isMainFile())

							// Enables the unset compilable file menu item
							AcideMainWindow.getInstance().getExplorerPanel()
									.getPopupMenu()
									.getUnsetCompilableFileMenuItem()
									.setEnabled(true);
					} else {

						// Enables the remove folder menu item
						AcideMainWindow.getInstance().getExplorerPanel()
								.getPopupMenu().getRemoveFolderMenuItem()
								.setEnabled(true);
					}
				}
			}

			// Shows the popup menu
			AcideMainWindow
					.getInstance()
					.getExplorerPanel()
					.getPopupMenu()
					.show(mouseEvent.getComponent(), mouseEvent.getX(),
							mouseEvent.getY());
		}
	}
}
