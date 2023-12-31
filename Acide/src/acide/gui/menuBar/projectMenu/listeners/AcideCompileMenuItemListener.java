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
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.files.project.AcideProjectFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import acide.log.AcideLog;

/**
 * ACIDE -A Configurable IDE project menu compile menu item listener.
 * 
 * @version 0.11
 * @see ActionListener
 */
public class AcideCompileMenuItemListener implements ActionListener {

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

		// Saves the file editor panel configuration
		AcideWorkbenchConfiguration.getInstance().saveFileEditorOpenedFilesConfiguration();

		try {

			// Is it possible to compile
			if (AcideProjectConfiguration.getInstance().getCompileAllFiles()) {

				String fileToCompile = "";

				for (int index = 0; index < AcideProjectConfiguration
						.getInstance().getNumberOfFilesFromList(); index++) {

					// If it is a compilable file
					if (AcideProjectConfiguration.getInstance()
							.getFileAt(index).isCompilableFile()) {

						fileToCompile = "\"" + fileToCompile + "\""
								+ "\""
								+ AcideProjectConfiguration.getInstance()
										.getFileAt(index).getAbsolutePath()
								+ "\""
								+ AcideProjectConfiguration.getInstance()
										.getFileSeparator();
					}
				}

				if (fileToCompile.length() > 0) {
					fileToCompile = fileToCompile.substring(0,
							fileToCompile.length() - 1);

					// If the compiler path has been defined
					if (AcideProjectConfiguration.getInstance()
							.getCompilerPath() != null)

						// Executes the compilation
						Runtime.getRuntime().exec(
								AcideProjectConfiguration.getInstance()
										.getCompilerPath()
										+ " "
										+ AcideProjectConfiguration
												.getInstance()
												.getCompilerArguments()
										+ " "
										+ fileToCompile);
				}
			} else {

				// Gets the project file extension 
				String extension = AcideProjectConfiguration.getInstance()
						.getFileExtension();

				for (int index = 0; index < AcideProjectConfiguration
						.getInstance().getNumberOfFilesFromList(); index++) {

					// Gets the file from the project configuration at the index
					AcideProjectFile file = AcideProjectConfiguration
							.getInstance().getFileAt(index);

					// Is not a directory
					if (!file.isDirectory()) {

						// Gets the file absolute path
						String fileAbsolutePath = file.getAbsolutePath();

						// Gets the file extension
						String fileExtension = fileAbsolutePath.substring(
								fileAbsolutePath.lastIndexOf(".") + 1,
								fileAbsolutePath.length());

						if (fileExtension.equals(extension)) {

							if (AcideProjectConfiguration.getInstance()
									.getCompilerPath() != null) {

								// Executes the compilation
								Runtime.getRuntime().exec(
										AcideProjectConfiguration.getInstance()
												.getCompilerPath()
												+ " "
												+ AcideProjectConfiguration
														.getInstance()
														.getCompilerArguments()
												+ " \""
												+ fileAbsolutePath
												+ "\"");
								System.out.println(AcideProjectConfiguration.getInstance()
										.getCompilerPath()
										+ " "
										+ AcideProjectConfiguration
												.getInstance()
												.getCompilerArguments()
										+ " \""
										+ fileAbsolutePath
										+ "\"");
							}
						}
					}
				}
			}
		} catch (IOException exception) {

			// Displays an error message
			JOptionPane.showMessageDialog(null, exception.getMessage());

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
		}
	}
}
