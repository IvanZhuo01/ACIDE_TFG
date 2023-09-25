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
package acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.listeners;

import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Element;
import javax.swing.text.Utilities;

import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideHighlightError;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.toolBarPanel.consolePanelToolBar.AcideSendFileToConsoleButtonAction;
import acide.log.AcideLog;
import acide.process.parser.AcideGrammarAnalyzer;

/**
 * ACIDE - A Configurable IDE file editor text edition area keyboard listener.
 * 
 * @version 0.19
 * @see FocusListener
 */
public class AcideFileEditorKeyboardListener extends KeyAdapter {

	int arr[] = new int[2];

	private static AcideMainWindow acideWindow;
//	private static JProgressBar _progress;

	/**
	 * ACIDE - A Configurable IDE splash screen window main panel.
	 */
//	private static JPanel _mainPanel;

	public AcideFileEditorKeyboardListener() {
		AcideFileEditorKeyboardListener.acideWindow = AcideMainWindow.getInstance();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEveznt)
	 */

	/*
	 * private static void executeTimeSplash() {
	 * 
	 * try { SwingUtilities.invokeAndWait(new Runnable() {
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/*
	 * public void run() {
	 * 
	 * // Shows the splash screen AcideImageSplashScreenWindow.getInstance()
	 * .showSplashScreenWindow();
	 * 
	 * } }); } catch (InterruptedException exception) {
	 * 
	 * exception.printStackTrace();
	 * 
	 * } catch (InvocationTargetException exception) {
	 * 
	 * exception.printStackTrace(); }
	 * 
	 * SwingUtilities.invokeLater(new Runnable() {
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/*
	 * public void run() {
	 * 
	 * // Sends the file content to the console
	 * AcideMainWindow.getInstance().getFileEditorManager()
	 * .getSelectedFileEditorPanel().getPopupMenu()
	 * .getSendFileContentToConsoleMenuItem().doClick();
	 * 
	 * // Init to refresh buttons console
	 * AcideSendFileToConsoleButtonAction.initRefresh();
	 * 
	 * // Closes the splash screen AcideImageSplashScreenWindow.getInstance()
	 * .closeSplashScreenWindow();
	 * 
	 * } }); }
	 * 
	 * public void iterate(final int finalValue) { // _progress.setValue(count); int
	 * count = 0; while ((finalValue <= 1000) && (count < finalValue)) {
	 * _progress.setIndeterminate(false); try { Thread.sleep(100);
	 * _progress.setValue(count); } catch (InterruptedException e) {
	 * e.printStackTrace(); } count += 90; }
	 * 
	 * }
	 * 
	 * private void buildComponents() {
	 * 
	 * // Gets the content pane //_mainPanel = (JPanel) getContentPane();
	 * 
	 * // Sets the window border
	 * _mainPanel.setBorder(BorderFactory.createLineBorder(new Color(30, 70, 115),
	 * 2));
	 * 
	 * 
	 * // Creates the image label _image = new JLabel(IMAGE);
	 * 
	 * 
	 * _progress = new JProgressBar(0, 1000); _progress.setValue(0);
	 * _progress.setStringPainted(true); _progress.setVisible(true); }
	 * 
	 * private void addComponents() {
	 * 
	 * // Sets the layout _mainPanel.setLayout(new BorderLayout());
	 * 
	 * _mainPanel.add(_progress, BorderLayout.SOUTH); }
	 * 
	 * private void setWindowConfiguration() {
	 * 
	 * // Applies the layout acideWindow.pack();
	 * 
	 * // Centers the window acideWindow.setLocationRelativeTo(null); }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {

		// If it is F9
		if (keyEvent.getKeyCode() == KeyEvent.VK_F9) {
			AcideFileEditorKeyboardListener.acideWindow.setCursor(new Cursor(Cursor.WAIT_CURSOR));

			/*
			 * // Builds the components buildComponents();
			 * 
			 * // Adds the components addComponents();
			 * 
			 * // Sets the window configuration setWindowConfiguration();
			 */

			// AcideImageSplashScreenWindow.getInstance().showSplashScreenWindow();
			/*
			 * JOptionPane.showMessageDialog(null,
			 * AcideLanguageManager.getInstance().getLabels() .getString("s2005"));
			 */
			// iterate(1000);
			// executeTimeSplash();

			// Sends the file content to the console
			AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel().getPopupMenu()
					.getSendFileContentToConsoleMenuItem().doClick();

			// Init to refresh buttons console
			AcideSendFileToConsoleButtonAction.initRefresh();

			// Closes the splash screen
			/*
			 * AcideImageSplashScreenWindow.getInstance() .closeSplashScreenWindow();
			 */

		}

		AcideFileEditorKeyboardListener.acideWindow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		// Gets the selected file editor panel
		AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow.getInstance().getFileEditorManager()
				.getSelectedFileEditorPanel();

		AcideHighlightError.getInstance().clearErrorHighlight();
		AcideMainWindow.getInstance().getErrorPopup().setVisible(false);
		
		// Get the delimiter
		String delimiter = selectedFileEditorPanel.get_grammarDelimiter();
		if (AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getGrammarMenu()
				.getAnalyzeMenu().getCompleteTextAnalysisCheckBoxMenuItem().isSelected()) {
			// If it is enter
			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
				// Get the file editor panel analyzer
				AcideGrammarAnalyzer analyzer = new AcideGrammarAnalyzer();
				// Analyze the text
				analyzer.start();

			}
			if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				int caretPosition = selectedFileEditorPanel.getActiveTextEditionArea().getCaretPosition();
				int line = selectedFileEditorPanel.getActiveTextEditionArea().getDocument().getDefaultRootElement()
						.getElementIndex(caretPosition) + 1;
				int column = caretPosition - selectedFileEditorPanel.getActiveTextEditionArea().getDocument()
						.getDefaultRootElement().getElement(line - 1).getStartOffset();
				if (column == 0) {
					AcideGrammarAnalyzer analyzer = new AcideGrammarAnalyzer();
					analyzer.start();

				}
			}
		} else if(AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getGrammarMenu()
				.getAnalyzeMenu().getIncrementalAnalysisCheckBoxMenuItem().isSelected()){
			
			if (!delimiter.equals("")&&delimiter.charAt(0) == keyEvent.getKeyChar()) {

				// Get the fragment of text to analyze
				int endCaretPosition = selectedFileEditorPanel.getActiveTextEditionArea().getCaretPosition();
				int startCaretPosition = selectedFileEditorPanel.getActiveTextEditionArea().getCaretPosition() - 1;

				try {
					// Find the previos delimiter to obtain the fragment of text
					while (startCaretPosition >= 0 && selectedFileEditorPanel.getActiveTextEditionArea()
							.getText(startCaretPosition, 1).charAt(0) != keyEvent.getKeyChar()) {
						startCaretPosition--;
					}

					// Get the file editor panel analyzer
					AcideGrammarAnalyzer analyzer = new AcideGrammarAnalyzer();
					String text = selectedFileEditorPanel.getActiveTextEditionArea().getText(startCaretPosition + 1,
							endCaretPosition - startCaretPosition-1)+keyEvent.getKeyChar();	
					int line = selectedFileEditorPanel.getActiveTextEditionArea().getDocument().getDefaultRootElement()
							.getElementIndex(startCaretPosition+1) + 1;
					int column = (startCaretPosition+1) - selectedFileEditorPanel.getActiveTextEditionArea().getDocument()
							.getDefaultRootElement().getElement(line - 1).getStartOffset();
					if(column==0) {
						for(int i=0;i<line-1;i++) {
							text="\n"+text;
						}
					}
					else if (column !=0) {
						for(int i=0;i<column;i++) {
							text=" "+text;
						}
						for(int i=0;i<line-1;i++) {
							text="\n"+text;
						}
					}
					analyzer.setText(text);
					
					// Analyze the text
					analyzer.start();
					

				} catch (BadLocationException e) {
					// Updates the log
					AcideLog.getLog().error(e.getMessage());
				}
			}
		}
		

		/*
		 * if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) { if
		 * (AcideMainWindow.getInstance().getMenu().getConfigurationMenu().
		 * getGrammarMenu() .getAutoAnalysisCheckBoxMenuItem().isSelected()) {
		 * AcideFileEditorPanel selectedFileEditorPanel =
		 * AcideMainWindow.getInstance().getFileEditorManager()
		 * .getSelectedFileEditorPanel(); Caret caret =
		 * selectedFileEditorPanel.getActiveTextEditionArea().getCaret(); String m =
		 * selectedFileEditorPanel.getTextEditionAreaContent(); int caretPosition =
		 * caret.getDot(); try { int lineStartOffset =
		 * Utilities.getRowStart(selectedFileEditorPanel.getActiveTextEditionArea(),
		 * caretPosition); String deletedCharacter =
		 * selectedFileEditorPanel.getActiveTextEditionArea().getText(caretPosition, 1);
		 * 
		 * if (deletedCharacter.equals("\n")) { AcideHighlightError errorhighlighter =
		 * AcideHighlightError.getInstance(); errorhighlighter.clearErrorHighlight();
		 * selectedFileEditorPanel.setErrors(new HashMap<String, String>());
		 * AcideGrammarAnalyzer analyzer = new AcideGrammarAnalyzer(); analyzer.start();
		 * } } catch (BadLocationException e) { AcideLog.getLog().error(e.getMessage());
		 * } } }
		 */
		/*
		 * if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) { if
		 * (AcideMainWindow.getInstance().getMenu().getConfigurationMenu().
		 * getGrammarMenu() .getAutoAnalysisCheckBoxMenuItem().isSelected()) {
		 * AcideFileEditorPanel selectedFileEditorPanel =
		 * AcideMainWindow.getInstance().getFileEditorManager()
		 * .getSelectedFileEditorPanel(); Caret caret =
		 * selectedFileEditorPanel.getActiveTextEditionArea().getCaret(); String m =
		 * selectedFileEditorPanel.getTextEditionAreaContent(); int caretPosition =
		 * caret.getDot(); try { int lineStartOffset =
		 * Utilities.getRowStart(selectedFileEditorPanel.getActiveTextEditionArea(),
		 * caretPosition); String deletedCharacter =
		 * selectedFileEditorPanel.getActiveTextEditionArea().getText(caretPosition, 1);
		 * 
		 * if (deletedCharacter.equals("\n")) { AcideHighlightError errorhighlighter =
		 * AcideHighlightError.getInstance(); errorhighlighter.clearErrorHighlight();
		 * selectedFileEditorPanel.setErrors(new HashMap<String, String>());
		 * AcideGrammarAnalyzer analyzer = new AcideGrammarAnalyzer(); analyzer.start();
		 * } } catch (BadLocationException e) { AcideLog.getLog().error(e.getMessage());
		 * } } }
		 */

		// para actualizar posicion la lista de errores en caso de pulsar Delete
		/*
		 * if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) { if
		 * (AcideMainWindow.getInstance().getMenu().getConfigurationMenu().
		 * getGrammarMenu() .getAutoAnalysisCheckBoxMenuItem().isSelected()) {
		 * AcideFileEditorPanel selectedFileEditorPanel =
		 * AcideMainWindow.getInstance().getFileEditorManager()
		 * .getSelectedFileEditorPanel(); int caretPosition =
		 * selectedFileEditorPanel.getActiveTextEditionArea().getCaretPosition(); int
		 * line = selectedFileEditorPanel.getActiveTextEditionArea().getDocument().
		 * getDefaultRootElement() .getElementIndex(caretPosition) + 1; int column =
		 * caretPosition -
		 * selectedFileEditorPanel.getActiveTextEditionArea().getDocument()
		 * .getDefaultRootElement().getElement(line - 1).getStartOffset();
		 * 
		 * int
		 * linestart=selectedFileEditorPanel.getActiveTextEditionArea().getDocument().
		 * getDefaultRootElement()
		 * .getElementIndex(selectedFileEditorPanel.getActiveTextEditionArea().
		 * getSelectionStart()) + 1; int
		 * columnstart=selectedFileEditorPanel.getActiveTextEditionArea().
		 * getSelectionStart()
		 * -selectedFileEditorPanel.getActiveTextEditionArea().getDocument()
		 * .getDefaultRootElement().getElement(linestart - 1).getStartOffset();
		 * 
		 * int lineend=selectedFileEditorPanel.getActiveTextEditionArea().getDocument().
		 * getDefaultRootElement()
		 * .getElementIndex(selectedFileEditorPanel.getActiveTextEditionArea().
		 * getSelectionEnd()) + 1; int
		 * columnend=selectedFileEditorPanel.getActiveTextEditionArea().getSelectionEnd(
		 * ) -selectedFileEditorPanel.getActiveTextEditionArea().getDocument()
		 * .getDefaultRootElement().getElement(lineend - 1).getStartOffset();
		 * 
		 * if (line!=1 || column >0) { HashMap<String, String> errors =
		 * selectedFileEditorPanel.get_errors(); HashMap<String, String> errorsUpdated =
		 * new HashMap<String, String>(); Iterator<HashMap.Entry<String, String>>
		 * iterator = errors.entrySet().iterator();
		 * 
		 * while (iterator.hasNext()) { HashMap.Entry<String, String> entry =
		 * iterator.next(); String[] parts = entry.getKey().split(":"); int wordLine =
		 * Integer.parseInt(parts[0]); int wordnColumnStart =
		 * Integer.parseInt(parts[1]); int errorLengh = Integer.parseInt(parts[2]); int
		 * wordnColumnEnd = Integer.parseInt(parts[2]) + wordnColumnStart; String msg =
		 * entry.getValue(); if (column == 0) { if (wordLine > line) {
		 * 
		 * wordLine--; String errorActualido = wordLine + ":" + wordnColumnStart + ":" +
		 * errorLengh; errorsUpdated.put(errorActualido, msg); } else if(wordLine<line){
		 * errorsUpdated.put(entry.getKey(), msg);
		 * 
		 * } else if (wordLine == line) { Element root =
		 * selectedFileEditorPanel.getActiveTextEditionArea().getDocument()
		 * .getDefaultRootElement(); Element lineElement; if (line == 1) { lineElement =
		 * root.getElement(line - 1); } else {
		 * 
		 * lineElement = root.getElement(line - 2); }
		 * 
		 * int startOffset = lineElement.getStartOffset(); int endOffset =
		 * lineElement.getEndOffset(); int length = endOffset - startOffset; if (length
		 * == 1) { wordLine--; String errorActualido = wordLine + ":" + wordnColumnStart
		 * + ":" + errorLengh; errorsUpdated.put(errorActualido, msg);
		 * 
		 * } else if (length > 1) { wordnColumnStart += (length-1); wordLine--; String
		 * errorActualido = wordLine + ":" + wordnColumnStart + ":" + errorLengh;
		 * errorsUpdated.put(errorActualido, msg); }
		 * 
		 * } } else if (column > 0) { // cuando borras en las posiciones del error if
		 * (line == wordLine && column > wordnColumnStart && column <= wordnColumnEnd) {
		 * continue; } else if (line == wordLine && column <= wordnColumnStart) {
		 * wordnColumnStart --; String errorActualido = wordLine + ":" +
		 * wordnColumnStart + ":" + errorLengh; errorsUpdated.put(errorActualido, msg);
		 * 
		 * }else if (line == wordLine && column > wordnColumnEnd) {
		 * errorsUpdated.put(entry.getKey(), msg); } else if (line<wordLine) {
		 * errorsUpdated.put(entry.getKey(), msg); } else if (line>wordLine) {
		 * errorsUpdated.put(entry.getKey(), msg); }
		 * 
		 * 
		 * }
		 * 
		 * } selectedFileEditorPanel.setErrors(errorsUpdated); }
		 * 
		 * } } // para actualizar posicion la lista de errores en caso de pulsar Enter
		 * /*if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) { if
		 * (AcideMainWindow.getInstance().getMenu().getConfigurationMenu().
		 * getGrammarMenu() .getAutoAnalysisCheckBoxMenuItem().isSelected()) {
		 * AcideFileEditorPanel selectedFileEditorPanel =
		 * AcideMainWindow.getInstance().getFileEditorManager()
		 * .getSelectedFileEditorPanel(); int caretPosition =
		 * selectedFileEditorPanel.getActiveTextEditionArea().getCaretPosition(); int
		 * line = selectedFileEditorPanel.getActiveTextEditionArea().getDocument().
		 * getDefaultRootElement() .getElementIndex(caretPosition) + 1; int column =
		 * caretPosition -
		 * selectedFileEditorPanel.getActiveTextEditionArea().getDocument()
		 * .getDefaultRootElement().getElement(line - 1).getStartOffset();
		 * HashMap<String, String> errors = selectedFileEditorPanel.get_errors();
		 * HashMap<String, String> errorsUpdated = new HashMap<String, String>();
		 * Iterator<HashMap.Entry<String, String>> iterator =
		 * errors.entrySet().iterator(); while (iterator.hasNext()) {
		 * HashMap.Entry<String, String> entry = iterator.next(); String[] parts =
		 * entry.getKey().split(":"); String msg = entry.getValue(); int wordLine =
		 * Integer.parseInt(parts[0]); int wordnColumnStart =
		 * Integer.parseInt(parts[1]); int errorLengh = Integer.parseInt(parts[2]) ; int
		 * wordnColumnEnd = Integer.parseInt(parts[2]) + wordnColumnStart; if (line ==
		 * wordLine && column > wordnColumnStart && column <= wordnColumnEnd) {
		 * continue; } else if(wordLine<line) { errorsUpdated.put(entry.getKey(), msg);
		 * } else if(wordLine==line&&wordnColumnStart<column) {
		 * errorsUpdated.put(entry.getKey(), msg); } else if (wordLine >=
		 * line&&column==0||wordLine > line) { wordLine++; String errorActualido =
		 * wordLine + ":" + wordnColumnStart + ":" + errorLengh;
		 * errorsUpdated.put(errorActualido, msg); }
		 * 
		 * else if(wordLine==line&&column<wordnColumnEnd) { wordLine++; String
		 * errorActualido = wordLine + ":" + (wordnColumnStart-column) + ":" +
		 * errorLengh; errorsUpdated.put(errorActualido, msg); }
		 * 
		 * 
		 * } selectedFileEditorPanel.setErrors(errorsUpdated);
		 * 
		 * } }
		 */

	}
}
