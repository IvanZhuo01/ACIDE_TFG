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
package acide.gui.fileEditor.fileEditorPanel.popup;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ACIDE - A Configurable IDE editor panel popup menu listener.
 * 
 * @version 0.11
 * @see MouseAdapter
 */
public class AcideEditorPanelPopupMenuListener extends MouseAdapter {

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
	 * Shows the popup menu.
	 * 
	 * @param mouseEvent
	 *            mouse event.
	 */
	private void maybeShowPopup(MouseEvent mouseEvent) {

		// If it has to display the popup menu
		if (mouseEvent.isPopupTrigger()) {

			// Gets the selected file editor panel
			AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel();
			
			if (selectedFileEditorPanel.getActiveTextEditionArea().getSelectedText()!= null){
				selectedFileEditorPanel.getPopupMenu().getSenSelectedtextToConsoleMenuItem().setEnabled(true);
			}else{
				selectedFileEditorPanel.getPopupMenu().getSenSelectedtextToConsoleMenuItem().setEnabled(false);
			}

			// Disables the menu items
			disableMenuItems(selectedFileEditorPanel);

			// Check the system clipboard
			if (Toolkit.getDefaultToolkit().getSystemClipboard()
					.getContents(null) != null)
				// Enables the paste menu item in the popup menu
				selectedFileEditorPanel.getPopupMenu().getPasteMenuItem()
						.setEnabled(true);

			// Checks the selected editor
			if (selectedFileEditorPanel.getActiveTextEditionArea()
					.getSelectedText() != null) {

				// Enables the copy menu item
				selectedFileEditorPanel.getPopupMenu().getCopyMenuItem()
						.setEnabled(true);

				// Enables the cut menu item
				selectedFileEditorPanel.getPopupMenu().getCutMenuItem()
						.setEnabled(true);
			}

			if (!AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().isNewFile()
					&& !AcideMainWindow.getInstance().getFileEditorManager()
							.getSelectedFileEditorPanel().isLogFile()) {

				if (!selectedFileEditorPanel.isMainFile())
					// Enables the set main menu item
					selectedFileEditorPanel.getPopupMenu()
							.getSetMainFileMenuItem().setEnabled(true);
				if (selectedFileEditorPanel.isMainFile())
					// Enables the unset main menu item
					selectedFileEditorPanel.getPopupMenu()
							.getUnsetMainFileMenuItem().setEnabled(true);
				if (!selectedFileEditorPanel.isCompilableFile()
						|| (selectedFileEditorPanel.isCompilableFile() && selectedFileEditorPanel
								.isMainFile()))
					// Enables the set compilable menu item
					selectedFileEditorPanel.getPopupMenu()
							.getSetCompilableFileMenuItem().setEnabled(true);
				if (selectedFileEditorPanel.isCompilableFile()
						&& !selectedFileEditorPanel.isMainFile())
					// Enables the unset compilable menu item
					selectedFileEditorPanel.getPopupMenu()
							.getUnsetCompilableFileMenuItem().setEnabled(true);

				// Gets the file editor panel absolute path
				String fileAbsolutePath = AcideMainWindow
						.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(
								AcideMainWindow.getInstance()
										.getFileEditorManager()
										.getSelectedFileEditorPanelIndex())
						.getAbsolutePath();

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject()) {

					// Searches for the file in the project configuration list
					int fileProjectIndex = AcideProjectConfiguration
							.getInstance().getIndexOfFile(fileAbsolutePath);

					// If belongs to the project configuration
					if (fileProjectIndex != -1) {

						// Enables the delete file menu item
						selectedFileEditorPanel.getPopupMenu()
								.getDeleteFileMenuItem().setEnabled(true);

						// Enables the remove file menu item
						selectedFileEditorPanel.getPopupMenu()
								.getRemoveFileMenuItem().setEnabled(true);

						// Enables the add file menu item
						selectedFileEditorPanel.getPopupMenu()
								.getAddFileMenuItem().setEnabled(false);

					} else {

						// Disables the remove file menu item
						selectedFileEditorPanel.getPopupMenu()
								.getRemoveFileMenuItem().setEnabled(false);

						// Enables the delete file menu item
						selectedFileEditorPanel.getPopupMenu()
								.getDeleteFileMenuItem().setEnabled(false);

						// Disables the add file menu item
						selectedFileEditorPanel.getPopupMenu()
								.getAddFileMenuItem().setEnabled(true);
					}
				}
			}

			// Shows the popup menu
			AcideMainWindow
					.getInstance()
					.getFileEditorManager()
					.getSelectedFileEditorPanel()
					.getPopupMenu()
					.show(mouseEvent.getComponent(), mouseEvent.getX(),
							mouseEvent.getY());
		}
	}

	/**
	 * Disables the popup menu items, so it can enable the right ones
	 * afterwards.
	 * 
	 * @param selectedFileEditorPanel
	 *            selected file editor panel.
	 */
	public void disableMenuItems(AcideFileEditorPanel selectedFileEditorPanel) {

		// Disables the copy menu item
		selectedFileEditorPanel.getPopupMenu().getCopyMenuItem()
				.setEnabled(false);

		// Disables the cut menu item
		selectedFileEditorPanel.getPopupMenu().getCutMenuItem()
				.setEnabled(false);

		// Disables the paste menu item
		selectedFileEditorPanel.getPopupMenu().getPasteMenuItem()
				.setEnabled(false);

		// Disables the add file menu item
		selectedFileEditorPanel.getPopupMenu().getAddFileMenuItem()
				.setEnabled(false);

		// Disables the remove file menu item
		selectedFileEditorPanel.getPopupMenu().getRemoveFileMenuItem()
				.setEnabled(false);

		// Disables the delete file menu item
		selectedFileEditorPanel.getPopupMenu().getDeleteFileMenuItem()
				.setEnabled(false);

		// Disables the set compilable menu item
		selectedFileEditorPanel.getPopupMenu().getSetCompilableFileMenuItem()
				.setEnabled(false);

		// Disables the unset compilable menu item
		selectedFileEditorPanel.getPopupMenu().getUnsetCompilableFileMenuItem()
				.setEnabled(false);

		// Disables the set main menu item
		selectedFileEditorPanel.getPopupMenu().getSetMainFileMenuItem()
				.setEnabled(false);

		// Disables the unset main menu item
		selectedFileEditorPanel.getPopupMenu().getUnsetMainFileMenuItem()
				.setEnabled(false);
	}
}
