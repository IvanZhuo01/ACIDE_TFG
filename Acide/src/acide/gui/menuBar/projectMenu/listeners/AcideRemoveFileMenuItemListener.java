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
package acide.gui.menuBar.projectMenu.listeners;

import acide.configuration.project.AcideProjectConfiguration;
import acide.files.AcideFileManager;
import acide.files.project.AcideProjectFile;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import acide.language.AcideLanguageManager;

/**
 * ACIDE -A Configurable IDE project menu remove file menu item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideRemoveFileMenuItemListener implements ActionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		action(actionEvent);
	}
	
	public static void action(ActionEvent actionEvent){
		// Are you sure?
		int returnValue = JOptionPane.showConfirmDialog(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s623"));

		// If it is OK
		if (returnValue == JOptionPane.OK_OPTION) {

			// Gets the selection over the explorer tree
			TreePath currentSelection = AcideMainWindow.getInstance()
					.getExplorerPanel().getTree().getSelectionPath();

			// If something has been selected
			if (currentSelection != null) {

				// Gets the select node in the explorer tree
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection
						.getLastPathComponent();

				// Transform the node into a project file
				AcideProjectFile currentFile = (AcideProjectFile) currentNode
						.getUserObject();

				// If it is a file and not a directory
				if (!currentFile.isDirectory()) {

					// Gets the node parent
					MutableTreeNode parent = (MutableTreeNode) (currentNode
							.getParent());

					// If it has parent
					if (parent != null) {

						// Removes the node
						AcideMainWindow.getInstance().getExplorerPanel()
								.getTreeModel()
								.removeNodeFromParent(currentNode);

						// Searches for the file in the project configuration
						int fileIndex = -1;
						for (int index = 0; index < AcideProjectConfiguration
								.getInstance().getNumberOfFilesFromList(); index++) {

							if (AcideProjectConfiguration.getInstance()
									.getFileAt(index).getAbsolutePath()
									.equals(currentFile.getAbsolutePath())) {

								// Found it
								fileIndex = index;
							}
						}

						// Removes the file from the project configuration
						AcideProjectConfiguration.getInstance().removeFileAt(
								fileIndex);

						// Updates the status message in the status bar
						AcideMainWindow.getInstance().getStatusBar()
								.setStatusMessage(" ");

						// Searches for the file in the editor
						int fileEditorPanelIndex = -1;
						for (int index = 0; index < AcideMainWindow
								.getInstance().getFileEditorManager()
								.getNumberOfFileEditorPanels(); index++) {
							if (AcideMainWindow.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(index)
									.getAbsolutePath()
									.equals(currentFile.getAbsolutePath()))

								// Found it
								fileEditorPanelIndex = index;
						}

						// If it exists
						if (fileEditorPanelIndex != -1) {

							// Is the file modified?
							if (AcideMainWindow.getInstance()
									.getFileEditorManager()
									.isRedButton(fileEditorPanelIndex)) {

								// Do you want to save it?
								returnValue = JOptionPane.showConfirmDialog(
										null, AcideLanguageManager
												.getInstance().getLabels()
												.getString("s643"),
										AcideLanguageManager.getInstance()
												.getLabels().getString("s953"),
										JOptionPane.YES_NO_OPTION);

								// If yes
								if (returnValue == JOptionPane.OK_OPTION) {

									// Saves the file
									boolean savingResult = AcideFileManager
											.getInstance()
											.write(AcideMainWindow
													.getInstance()
													.getFileEditorManager()
													.getFileEditorPanelAt(
															fileEditorPanelIndex)
													.getAbsolutePath(),
													AcideMainWindow
															.getInstance()
															.getFileEditorManager()
															.getFileEditorPanelAt(
																	fileEditorPanelIndex)
															.getTextEditionAreaContent());

									// If it could save it
									if (savingResult) {

										// Sets the green button
										AcideMainWindow
												.getInstance()
												.getFileEditorManager()
												.setGreenButtonAt(
														fileEditorPanelIndex);
									}
								}
							}
							

							// Closes the editor tab
							AcideMainWindow.getInstance().getFileEditorManager()
									.getTabbedPane().remove(fileEditorPanelIndex);

							// Validates the changes in the file editor manager
							// tabbed pane
							AcideMainWindow.getInstance().getFileEditorManager()
									.getTabbedPane().validate();
						}
					}

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);
				}
			}
		}

		// If there are more opened files
		if (AcideProjectConfiguration.getInstance().getNumberOfFilesFromList() > 0) {

			// Updates the selected file editor index
			AcideMainWindow
					.getInstance()
					.getFileEditorManager()
					.updateRelatedComponentsAt(
							AcideMainWindow.getInstance()
									.getFileEditorManager()
									.getSelectedFileEditorPanelIndex());

			// Enables the remove file menu item in the explorer popup menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getRemoveFileMenuItem().setEnabled(true);

			// Enables the delete file menu item in the explorer popup menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getDeleteFileMenuItem().setEnabled(true);
		} else {

			// Disables the remove file menu item in the explorer popup menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getRemoveFileMenuItem().setEnabled(false);

			// Disables the delete file menu item in the explorer popup menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getDeleteFileMenuItem().setEnabled(false);
		}
	}
}
