/*
 * ACIDE - A Configurable IDE
 * Official web site: http://acide.sourceforge.net
 *
 * Copyright (C) 2007-2023
 * Authors:
 * 		- Fernando Sáenz Pérez (Team Director).
 *      - Version from 0.1 to 0.6:
 *      	- Diego Cardiel Freire.
 *			- Juan José Ortiz Sánchez.
 *          - Delfín Rupérez Cañas.
 *      - Version 0.7:
 *          - Miguel Martín Lázaro.
 *      - Version 0.8:
 *      	- Javier Salcedo Gómez.
 *      - Version from 0.9 to 0.11:
 *      	- Pablo Gutiérrez García-Pardo.
 *      	- Elena Tejeiro Pérez de Ágreda.
 *      	- Andrés Vicente del Cura.
 *      - Version from 0.12 to 0.16
 *      	- Semíramis Gutiérrez Quintana
 *      	- Juan Jesús Marqués Ortiz
 *      	- Fernando Ordás Lorente
 *      - Version 0.17
 *      	- Sergio Domínguez Fuentes
 * 		- Version 0.18
 * 			- Sergio García Rodríguez
 * 		- Version 0.19
 * 			- Carlos González Torres
 * 			- Cristina Lara López
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

package acide.gui.menuBar.configurationMenu.grammarMenu.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acide.files.bytes.AcideByteFileManager;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideHighlightError;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.parser.AcideGrammarAnalyzer;
import acide.process.parser.AcideGrammarFileCreationProcess;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

/**																
 * ACIDE - A Configurable IDE analysis text menu item listener.										
 *					
 * @version 0.19	
 * @see ActionListener																													
 */
public class AcideAnalyzeTextMenuItemListener implements ActionListener{

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		action(actionEvent);
	}

	public static void action(ActionEvent actionEvent){
		// Gets the selected file editor panel
		AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow.getInstance()
				.getFileEditorManager().getSelectedFileEditorPanel();
		
		// Clear all the errors highlights
		AcideHighlightError.getInstance().clearErrorHighlight();
		
		if(!AcideMainWindow.getInstance().getFirstAnalysis()) {
			
			try {
				// Get the previous analyze
				String previous = AcideResourceManager.getInstance().getProperty("previousAnalyze");
				if(!selectedFileEditorPanel.getCurrentGrammarConfiguration().getName().equals(previous)) {
					compileAndAnalyze();
				}
				else {
					analyze();
				}
			} catch (MissedPropertyException e) {
				// Updates the log
				AcideLog.getLog().error(e.getMessage());
			}
		}
		else {
			compileAndAnalyze();
			
			// Set first analysis to false
			AcideMainWindow.getInstance().setFirstAnalysis(false);
		}
	}
	
	
	/**
	 * Invoke the acide grammar analyzer
	 * 
	 */
	private static void analyze() {
		// Get the file editor panel analyzer
		AcideGrammarAnalyzer analyzer = new AcideGrammarAnalyzer();
		
		analyzer.setLock(AcideLanguageManager.getInstance().getLabels().getString("s2439"));
		
		// Analyze the text
		analyzer.start();
	}
	
	/**
	 * Invoke the acide grammar file creation process
	 * 
	 */
	private static void compileAndAnalyze() {
		// Gets the selected file editor panel
		AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow.getInstance()
				.getFileEditorManager().getSelectedFileEditorPanel();
		
		String absolutePath = selectedFileEditorPanel.getCurrentGrammarConfiguration().getPath();
		
		// Process the file grammar
		AcideByteFileManager.getInstance().processGrammarFile(absolutePath);
		
		AcideGrammarFileCreationProcess process = new AcideGrammarFileCreationProcess(
				absolutePath, false, AcideLanguageManager.getInstance().getLabels()
				.getString("s35"), true);
		
		process.setLock(AcideLanguageManager.getInstance().getLabels().getString("s2439"));
		
		// Starts the process
		process.start();
	}
	
}
