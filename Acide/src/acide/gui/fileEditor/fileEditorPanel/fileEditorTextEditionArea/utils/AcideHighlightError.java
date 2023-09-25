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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.SwingUtilities;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.fileEditor.fileEditorPanel.errorpopup.AcidefileEditorPanelErrorpopup;
import acide.gui.mainWindow.AcideMainWindow;
import acide.log.AcideLog;
import acide.process.parser.AcideGrammarAnalyzer;
/**
 * ACIDE - A Configurable IDE file editor text edition area error highlighter.
 * 
 * @version 0.19
 * @see AcideHighlightError
 */
public class AcideHighlightError {

	private static AcideHighlightError _instance;
	/**
	 * Return the unique AcideHighlightError instance.
	 */
	public static AcideHighlightError getInstance() {

		if (_instance == null)
			_instance = new AcideHighlightError();
		return _instance;
	}
	/**
	 * Highlights the matching elements.
	 */
	public void ErrorHighLight() {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				AcideFileEditorPanel selectedFileEditorPanelIndex = AcideMainWindow.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel();
				
				// Obtener el documento del JTextPane
				StyledDocument document = selectedFileEditorPanelIndex.getStyledDocument();
				// Crear un estilo personalizado con subrayado rojo
				SimpleAttributeSet underlineStyle = new SimpleAttributeSet();
				StyleConstants.setForeground(underlineStyle, Color.RED);
				StyleConstants.setUnderline(underlineStyle, true);
				
				// Agregar el estilo personalizado a un nuevo estilo de carácter
				StyleContext styleContext = StyleContext.getDefaultStyleContext();
				Style customStyle = styleContext.addStyle("custom", null);
				customStyle.addAttributes(underlineStyle);
				// Subrayar cada palabra en rojo
				for (HashMap.Entry<String, String> entry : AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel().get_errors()
						.entrySet()) {
					// Obtener la línea y columna de inicio de la palabra
					String[] parts = entry.getKey().split(":");
					int startLine = Integer.parseInt(parts[0])-1 ;
					int startColumn = Integer.parseInt(parts[1]);
					int endColumn=Integer.parseInt(parts[2])+startColumn;
					try {
						int start = document.getDefaultRootElement().getElement(startLine).getStartOffset() + startColumn;
						int end = document.getDefaultRootElement().getElement(startLine).getStartOffset() + endColumn;
						document.setCharacterAttributes(start, end - start, customStyle, true);
					} catch (NullPointerException e) {
					    // Manejo de la excepción
					    System.err.println("Se ha producido una NullPointerException. Detalles: " + e.getMessage());
					    e.printStackTrace(); // Esto muestra el rastro de la pila en la consola
					    // Puedes realizar otras acciones aquí según tus necesidades
					}

				}
			}
		});
	}
	/**
	 * Clear all error Highlightings.
	 */
	public void clearErrorHighlight() {
		AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow.getInstance()
				.getFileEditorManager().getSelectedFileEditorPanel();
		if(!selectedFileEditorPanel
				.get_errors().isEmpty()) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow.getInstance().getFileEditorManager()
	                        .getSelectedFileEditorPanel();
	                StyledDocument document = selectedFileEditorPanel.getStyledDocument();

	                // Eliminar los atributos de estilo del documento
	                SimpleAttributeSet emptyStyle = new SimpleAttributeSet();
	                document.setCharacterAttributes(0, document.getLength(), emptyStyle, true);
	                try {
	                    selectedFileEditorPanel.getStyledDocument().processChangedLines(0, document.getLength());
	                } catch (BadLocationException e) {
	                    // Updates the log
	                    AcideLog.getLog().error(e.getMessage());
	                }

	            }
	        });
	        selectedFileEditorPanel.setErrors(new HashMap<String, String>());
		}
    }

}
