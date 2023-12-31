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
package acide.gui.fileEditor.fileEditorPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

import acide.configuration.grammar.AcideGrammarConfiguration;
import acide.configuration.lexicon.AcideLexiconConfiguration;
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.consolePanel.AcideConsolePanel;
import acide.gui.fileEditor.fileEditorManager.utils.gui.AcideLineNumberComponent2;
import acide.gui.fileEditor.fileEditorPanel.errorpopup.AcidefileEditorPanelErrorpopup;
import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.AcideFileEditorTextEditionArea;
import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideFileEditorStyledDocument;
import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideTextComponent;
import acide.gui.fileEditor.fileEditorPanel.listeners.AcideFileEditorPanelDocumentListener;
import acide.gui.fileEditor.fileEditorPanel.popup.AcideFileEditorPopupMenu;
import acide.gui.menuBar.configurationMenu.consoleMenu.gui.AcideExternalCommandConfigurationWindow;
import acide.gui.menuBar.projectMenu.gui.executionWindow.AcideExecutionConfigurationWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.parser.AcideGrammarAnalyzer;

/**
 * ACIDE - A Configurable IDE file editor panel.
 * 
 * @version 0.19
 * @see JPanel
 */
public class AcideFileEditorPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE file editor panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file editor panel constant that indicates the
	 * number of text editors for the file editor panel.
	 */
	private static final int NUM_TEXT_EDITORS = 2;
	/**
	 * ACIDE - A Configurable IDE file editor panel text edition area.
	 */
	private ArrayList<AcideFileEditorTextEditionArea> _textEditionAreaList;
	/**
	 * ACIDE - A Configurable IDE file editor panel file path.
	 */
	private String _path;
	/**
	 * ACIDE - A Configurable IDE file editor panel last change in the editor.
	 */
	private long _lastChange;
	/**
	 * ACIDE - A Configurable IDE file editor panel last size of the editor.
	 */
	private long _lastSize;
	/**
	 * ACIDE - A Configurable IDE file editor panel popup menu.
	 */
	private AcideFileEditorPopupMenu _popupMenu;
	/**
	 * ACIDE - A Configurable IDE file editor panel error pop up windows
	 */
	private AcidefileEditorPanelErrorpopup _errorpopup;
	/**
	 * ACIDE - A Configurable IDE file editor panel styled document for the files.
	 */
	private AcideFileEditorStyledDocument _styledDocument;
	/**
	 * ACIDE - A Configurable IDE file editor panel document listener.
	 */
	private AcideFileEditorPanelDocumentListener _documentListener;
	/**
	 * ACIDE - A Configurable IDE file editor panel flag that indicates the active
	 * text edition area index.
	 */
	private int _activeTextEditionAreaIndex;
	/**
	 * ACIDE - A Configurable IDE file editor panel horizontal split panel.
	 */
	private JSplitPane _horizontalSplitPane;
	/**
	 * ACIDE - A Configurable IDE file editor panel flag that indicates if the file
	 * is main.
	 */
	private boolean _isMainFile;
	/**
	 * ACIDE - A Configurable IDE file editor panel flag that indicates if the file
	 * is compilable.
	 */
	private boolean _isCompilableFile;
	/**
	 * ACIDE - A Configurable IDE file editor panel icon.
	 */
	private ImageIcon _icon;
	/**
	 * ACIDE - A Configurable IDE file editor panel file disk copy.
	 * 
	 * Used to determine the color of the close button.
	 */
	private String _fileDiskCopy;
	/**
	 * ACIDE - A Configurable IDE file editor panel lexicon configuration.
	 */
	private AcideLexiconConfiguration _lexiconConfiguration;
	/**
	 * ACIDE - A Configurable IDE file editor panel previous grammar configuration.
	 */
	private AcideGrammarConfiguration _previousGrammarConfiguration;
	/**
	 * ACIDE - A Configurable IDE file editor panel current grammar configuration.
	 */
	private AcideGrammarConfiguration _currentGrammarConfiguration;
	/**
	 * ACIDE - A Configurable IDE file editor panel errors.
	 */
	private HashMap<String, String> _errors;
	/**
	 * ACIDE - A Configurable IDE file editor panel delimiter.
	 */
	private String _grammarDelimiter;
	/**
	 * Current encode format
	 */

	private String encodeFormat = "UTF-8";
	/**
	 * Creates a new ACIDE - A Configurable IDE file editor panel.
	 * 
	 * @param lastSize                     last size.
	 * @param lastChange                   last change.
	 * 
	 * @param activeTextEditionAreaIndex   active text edition area index
	 * @param isEditable                   is editable flag.
	 * @param filePath                     file path.
	 * @param splitPaneDividerLocation     split pane divider location.
	 * @param lexiconConfiguration         lexicon configuration.
	 * @param currentGrammarConfiguration  current grammar configuration.
	 * @param previousGrammarConfiguration previous grammar configuration.
	 */
	public AcideFileEditorPanel(String filePath, boolean isEditable, long lastChange, long lastSize,
			int activeTextEditionAreaIndex, int splitPaneDividerLocation,
			AcideLexiconConfiguration lexiconConfiguration, AcideGrammarConfiguration currentGrammarConfiguration,
			AcideGrammarConfiguration previousGrammarConfiguration) {

		super();

		try {

			// Sets the layout
			setLayout(new BorderLayout());
			
			// Initialize the error
			_errors = new HashMap<String, String>();

			// Stores the active text edition area index
			_activeTextEditionAreaIndex = activeTextEditionAreaIndex;

			// Stores the path
			_path = filePath;

			// Stores the last change
			_lastChange = lastChange;

			// Stores the last size
			_lastSize = lastSize;

			// Stores the lexicon configuration
			_lexiconConfiguration = lexiconConfiguration;

			// Stores the grammar configuration
			_currentGrammarConfiguration = currentGrammarConfiguration;

			// Stores the previous grammar configuration
			_previousGrammarConfiguration = previousGrammarConfiguration;

			// Updates the log
			AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s317"));

			// Creates the text edition panel list
			_textEditionAreaList = new ArrayList<AcideFileEditorTextEditionArea>();

			// Creates the document listener
			_documentListener = new AcideFileEditorPanelDocumentListener();

			// Creates the styled document
			_styledDocument = new AcideFileEditorStyledDocument(_lexiconConfiguration);

			// Puts the name property
			_styledDocument.putProperty("name", getFileNameWithExtension());

			// Adds the document listener to the styled document
			_styledDocument.addDocumentListener(_documentListener);

			// creates the error message windows
			//_errorpopup=new AcidefileEditorPanelErrorpopup();
			

			// Builds the popup menu
			buildPopupMenu();
			
			

			// Creates the number of text editors
			for (int index = 0; index < NUM_TEXT_EDITORS; index++) {

				// Adds the new file editor text edition area to the list
				_textEditionAreaList.add(new AcideFileEditorTextEditionArea(_styledDocument));

			}

			// Creates the split horizontal pane
			_horizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
					_textEditionAreaList.get(0).getScrollPane(), _textEditionAreaList.get(1).getScrollPane());

			// Sets the horizontal split pane divider location
			_horizontalSplitPane.setDividerLocation(splitPaneDividerLocation);

			// Sets the horizontal split pane divider location as true
			_horizontalSplitPane.setContinuousLayout(true);

			// Adds the horizontal split pane
			add(_horizontalSplitPane);

			// Updates the log
			AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s318"));

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s319"));
			exception.printStackTrace();
		}
	}

	/**
	 * Builds the file ACIDE - A Configurable IDE file editor panel popup menu.
	 */
	public void buildPopupMenu() {
		_popupMenu = new AcideFileEditorPopupMenu();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel active text edition
	 * panel.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel active text edition
	 *         panel.
	 */
	public AcideTextComponent getActiveTextEditionArea() {
		return _textEditionAreaList.get(_activeTextEditionAreaIndex).getTextComponent();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel active text edition
	 * panel line number component.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel active text edition
	 *         panel line number component.
	 */
	public AcideLineNumberComponent2 getActiveTextEditionAreaLineNumberComponent() {
		return _textEditionAreaList.get(_activeTextEditionAreaIndex).getLineNumberPanel();
	}

	/**
	 * Sets the file content given as a parameter into all the text edition areas of
	 * the file editor panel.
	 * 
	 * @param fileContent file content to set.
	 */
	public void setFileContent(final String fileContent) {

		for (int index = 0; index < NUM_TEXT_EDITORS; index++) {

			// Removes the document listener
			_textEditionAreaList.get(index).getTextComponent().getDocument().removeDocumentListener(_documentListener);

			//Sets the text content, codified utf8 predefined
			//ByteBuffer buffer = StandardCharsets.UTF_8.encode(fileContent); 

			//String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
			_textEditionAreaList.get(index).getTextComponent().setText(fileContent);

			// Adds the document listener
			_textEditionAreaList.get(index).getTextComponent().getDocument().addDocumentListener(_documentListener);
		}

		// Updates the file disk copy with the new content
		setFileDiskCopy(fileContent);

		// Validates the changes in the file editor panel
		revalidate();
	}
	public void setEncodeText(final String fileContent) {
		// Updates the file disk copy with the new content
		setFileDiskCopy(fileContent);
		for (int index = 0; index < NUM_TEXT_EDITORS; index++) {

			// Removes the document listener
			_textEditionAreaList.get(index).getTextComponent().getDocument().removeDocumentListener(_documentListener);

			//Sets the text content, codified utf8 predefined
			//ByteBuffer buffer = StandardCharsets.UTF_8.encode(fileContent); 

			//String utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
			_textEditionAreaList.get(index).getTextComponent().setText(fileContent);

			// Adds the document listener
			_textEditionAreaList.get(index).getTextComponent().getDocument().addDocumentListener(_documentListener);
		}


		// Validates the changes in the file editor panel
		revalidate();
	}

	/**
	 * Selects a text in both editors.
	 * 
	 * @param start  selection start.
	 * @param length selection length.
	 */
	public void selectText(int start, int length) {

		// Sets the selection start
		getActiveTextEditionArea().setSelectionStart(start);

		// Sets the selection end
		getActiveTextEditionArea().setSelectionEnd(start + length);
	}

	/**
	 * Resets the styled document of the text edition areas in the file editor
	 * panel.
	 */
	public void resetStyledDocument() {

		// Initializes the document
		_styledDocument.init();
	}

	/**
	 * Sets the caret at the start of a line.
	 * 
	 * @param line line to put the caret on.
	 */
	public void goToLine(int line) {

		// Gets the active text edition area
		AcideTextComponent activeTextEditionArea = getActiveTextEditionArea();

		// Calculates the line
		Element root = activeTextEditionArea.getDocument().getDefaultRootElement();
		line = Math.max(line, 1);
		line = Math.min(line, root.getElementCount());

		// Puts the caret at the beginning of the line
		activeTextEditionArea.setCaretPosition(root.getElement(line - 1).getStartOffset());
	}

	/**
	 * <p>
	 * Returns the ACIDE - A Configurable IDE file editor panel text edition area
	 * content.
	 * </p>
	 * <p>
	 * Returns the content from the document to avoid "\r\n" extra.
	 * </p>
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel text edition area
	 *         content.
	 */
	public String getTextEditionAreaContent() {

		// Gets the document length
		int documentLength = _textEditionAreaList.get(_activeTextEditionAreaIndex).getTextComponent().getDocument()
				.getLength();

		try {
			return _textEditionAreaList.get(_activeTextEditionAreaIndex).getTextComponent().getDocument().getText(0,
					documentLength);
		} catch (BadLocationException exception) {

			// Updates the log
			AcideLog.getLog().info(exception.getMessage());
			exception.printStackTrace();
		}

		return "";
	}

	/**
	 * <p>
	 * Returns the ACIDE - A Configurable IDE file editor panel text edition area
	 * content selected by the user.
	 * </p>
	 * <p>
	 * Returns the selected content from the document.
	 * </p>
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel text edition area
	 *         content selected by the user.
	 */
	public String getTextEditionAreaContentSelected() {
		String content = "";
		content = _textEditionAreaList.get(_activeTextEditionAreaIndex).getTextComponent().getSelectedText();

		return content;
	}

	/**
	 * <p>
	 * Returns the ACIDE - A Configurable IDE caret position of the file editor
	 * panel text edition area.
	 * </p>
	 * <p>
	 * Returns the caret position from the document.
	 * </p>
	 * 
	 * @return the ACIDE - A Configurable IDE caret position file editor panel text
	 *         edition area.
	 */
	public int getTextEditionAreaCaretPosition() {
		return _textEditionAreaList.get(_activeTextEditionAreaIndex).getTextComponent().getCaretPosition();
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel file
	 * path.
	 * 
	 * @param path new value to set.
	 */
	public void setAbsolutePath(String path) {

		// Updates the path
		_path = path;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel absolute file path.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel absolute file path.
	 */
	public String getAbsolutePath() {
		return _path;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel relative file path.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel relative file path.
	 */
	public String getFilePath() {

		int index = _path.lastIndexOf("\\");
		if (index == -1)
			index = _path.lastIndexOf("/");

		// If this is the new or the log tab returns empty path
		// in order to avoid exceptions
		if (index == -1)
			return "";
		return _path.substring(0, index);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel file extension.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel file extension.
	 */
	public String getFileExtension() {
		return _path.substring(_path.lastIndexOf(".") + 1);
	}

	/**
	 * <p>
	 * Returns the ACIDE - A Configurable IDE file editor panel file name without
	 * the file extension.
	 * </p>
	 * <p>
	 * This method is exclusively invoked by those methods and classes that executes
	 * commands in the ACIDE - A Configurable IDE console panel.
	 * </p>
	 * <p>
	 * For instance {@link AcideConsolePanel},
	 * {@link AcideExternalCommandConfigurationWindow} and
	 * {@link AcideExecutionConfigurationWindow}
	 * </p>
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel file name without
	 *         the file extension.
	 */
	public String getFileNameWithoutExtension() {

		if (_path != null) {
			int index = _path.lastIndexOf("\\");
			if (index == -1)
				index = _path.lastIndexOf("/");

			// If the file doesn't have an extension
			if (_path.lastIndexOf(".") == -1)
				return _path.substring(index + 1, _path.length());
			return _path.substring(index + 1, _path.lastIndexOf("."));
		}
		return "";
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel file name, extension
	 * included.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel file name, extension
	 *         included.
	 */
	public String getFileNameWithExtension() {

		if (_path != null) {
			int index = _path.lastIndexOf("\\");
			if (index == -1)
				index = _path.lastIndexOf("/");

			return _path.substring(index + 1, _path.length());
		}
		return "";
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel last change.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel last change.
	 */
	public long getLastChange() {
		return _lastChange;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel last
	 * change.
	 * 
	 * @param lastChange New value to set.
	 */
	public void setLastChange(long lastChange) {
		_lastChange = lastChange;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel last size.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel last size.
	 */
	public long getLastSize() {
		return _lastSize;
	}

	/**
	 * Sets the modifiable feature of the editors to false or true.
	 * 
	 * @param editable new value to set.
	 */
	public void setEditable(boolean editable) {
		_textEditionAreaList.get(_activeTextEditionAreaIndex).getTextComponent().setEditable(editable);
	}

	/**
	 * Sets a new value to the last size.
	 * 
	 * @param lastSize new value to set.
	 */
	public void setLastSize(long lastSize) {
		_lastSize = lastSize;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel is compilable file
	 * flag.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel is compilable file
	 *         flag.
	 */
	public boolean isCompilableFile() {
		return _isCompilableFile;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel is
	 * compilable flag.
	 * 
	 * @param compilableFile new value to set.
	 */
	public void setCompilableFile(boolean compilableFile) {
		_isCompilableFile = compilableFile;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel is main file flag.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel is main file flag.
	 */
	public boolean isMainFile() {
		return _isMainFile;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel is main
	 * file flag.
	 * 
	 * @param mainFile new value to set.
	 */
	public void setMainFile(boolean mainFile) {
		_isMainFile = mainFile;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel icon.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel icon.
	 */
	public ImageIcon getIcon() {
		return _icon;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel icon.
	 * 
	 * @param icon new value to set.
	 */
	public void setIcon(ImageIcon icon) {
		_icon = icon;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel styled document.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel styled document.
	 */
	public AcideFileEditorStyledDocument getStyledDocument() {
		return _styledDocument;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel active text edition
	 * area index.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel active text edition
	 *         area index.
	 */
	public int getActiveTextEditionAreaIndex() {
		return _activeTextEditionAreaIndex;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel popup menu.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel popup menu.
	 */
	public AcideFileEditorPopupMenu getPopupMenu() {
		return _popupMenu;
	}
	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel error popup .
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel error popup .
	 */
	/*public AcidefileEditorPanelErrorpopup getErrorPopup() {
		return _errorpopup;
	}*/
	

	/**
	 * Returns true if the opened file in the editor is the new file and false in
	 * other case.
	 * 
	 * @return true if the opened file in the editor is the new file and false in
	 *         other case.
	 */
	public boolean isNewFile() {
		return _path.equals(AcideLanguageManager.getInstance().getLabels().getString("s79"));
	}

	/**
	 * Returns true if the opened file in the editor is the log tab and false in
	 * other case.
	 * 
	 * @return true if the opened file in the editor is the log tab and false in
	 *         other case.
	 */
	public boolean isLogFile() {
		return _path.equals("Log");
	}

	/**
	 * Sets a new value to the active text edition area.
	 * 
	 * @param activeTextEditionAreaIndex new value to set.
	 */
	public void setActiveTextEditionAreaIndex(int activeTextEditionAreaIndex) {

		// Stores the new active text edition area index
		_activeTextEditionAreaIndex = activeTextEditionAreaIndex;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel text edition panel
	 * list.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel text edition panel
	 *         list.
	 */
	public ArrayList<AcideFileEditorTextEditionArea> getTextEditionPanelList() {
		return _textEditionAreaList;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel file disk copy.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel file disk copy.
	 */
	public String getFileDiskCopy() {
		return _fileDiskCopy;
	}

	/**
	 * Sets a new value for the ACIDE - A Configurable IDE file editor panel file
	 * disk copy.
	 * 
	 * @param fileDiskCopy new value to set.
	 */
	public void setFileDiskCopy(String fileDiskCopy) {
		_fileDiskCopy = "" + fileDiskCopy;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel horizontal split
	 * panel.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel horizontal split
	 *         panel.
	 */
	public JSplitPane getHorizontalSplitPane() {
		return _horizontalSplitPane;
	}

	/**
	 * Checks if the file content given as a parameter is equal to the file copy
	 * disk. This method is used to determine if the file has been modified in order
	 * to set the green button or the red button to an opened file in the file
	 * editor.
	 * 
	 * @param fileContent text to compare within.
	 * 
	 * @return true if the text given as a parameter is equal to the file disk copy
	 *         and false in other case.
	 */
	public boolean isEqualToFileDiskCopy(String fileContent) {

		if (_fileDiskCopy != null)
			return _fileDiskCopy.equals(fileContent);
		return true;
	}

	/**
	 * Applies the look and feel to the file editor in both text edition areas.
	 */
	public void setLookAndFeel() {

		for (int index = 0; index < NUM_TEXT_EDITORS; index++) {

			// Sets the look and feel
			_textEditionAreaList.get(index).setLookAndFeel();
		}
	}

	/**
	 * Zooms in or out the font size of all the text editors depending on a boolean
	 * variable given as a parameter. Also the increment is given as parameter as
	 * well.
	 * 
	 * @param zoom           increment to apply.
	 * @param hasToIncrement indicates if the zoom is in or out.
	 */
	public void zoomFont(int zoom, boolean hasToIncrement) {

		// Gets the current font
		Font currentFont = _textEditionAreaList.get(0).getTextComponent().getFont();

		// Creates the new font
		Font newFont;

		// If it is zoom out
		if (hasToIncrement) {

			// Sets the new font
			newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), currentFont.getSize() + zoom);
		} else {

			// Sets the new font
			newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), currentFont.getSize() - zoom);
		}

		// Updates the ACIDE - A Configurable IDE file editor
		// configuration font size
		AcideWorkbenchConfiguration.getInstance().getFileEditorConfiguration().setFontSize(newFont.getSize());

		for (int index = 0; index < NUM_TEXT_EDITORS; index++) {

			// Sets the new font
			_textEditionAreaList.get(index).getTextComponent().setFont(newFont);
		}
	}

	/**
	 * Sets the edition mode in the text edition area for both text editors.
	 * 
	 * @param editionMode new value to set.
	 */
	public void setEditionMode(final boolean editionMode) {

		for (int index = 0; index < _textEditionAreaList.size(); index++)
			_textEditionAreaList.get(index).getTextComponent().setEditionMode(editionMode);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel edition mode in the
	 * text edition area.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel edition mode in the
	 *         text edition area.
	 */
	public boolean getEditionMode() {
		return getActiveTextEditionArea().isOverwriteMode();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel lexicon
	 * configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel lexicon
	 *         configuration.
	 */
	public AcideLexiconConfiguration getLexiconConfiguration() {
		return _lexiconConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel lexicon
	 * configuration.
	 * 
	 * @param lexiconConfiguration new value to set.
	 */
	public void setLexiconConfiguration(AcideLexiconConfiguration lexiconConfiguration) {
		_lexiconConfiguration = lexiconConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel current grammar
	 * configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel current grammar
	 *         configuration.
	 */
	public AcideGrammarConfiguration getCurrentGrammarConfiguration() {
		return _currentGrammarConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor panel previous grammar
	 * configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor panel previous grammar
	 *         configuration.
	 */
	public AcideGrammarConfiguration getPreviousGrammarConfiguration() {
		return _previousGrammarConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel current
	 * grammar configuration.
	 * 
	 * @param currentGrammarConfiguration new value to set.
	 */
	public void setCurrentGrammarConfiguration(AcideGrammarConfiguration currentGrammarConfiguration) {
		_currentGrammarConfiguration = currentGrammarConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor panel previous
	 * grammar configuration.
	 * 
	 * @param previousGrammarConfiguration new value to set.
	 */
	public void setPreviousGrammarConfiguration(AcideGrammarConfiguration previousGrammarConfiguration) {
		_previousGrammarConfiguration = previousGrammarConfiguration;
	}

	public void changeColor(Color background, Color foreground, Color darker) {
		Component[] men = this._popupMenu.getComponents();
		for (Component c : men) {
			c.getClass().cast(c).setBackground(darker);
			c.getClass().cast(c).setForeground(foreground);
			((JComponent) c.getClass().cast(c)).setOpaque(true);
		}
	}
	
	public void changeEncode(String encodeFormat) {
		this.encodeFormat = encodeFormat;
	}
	
	public String getEncode() {
		return this.encodeFormat;
	}

	public HashMap<String, String> get_errors() {
		return _errors;
	}	
	
	public void setErrors(HashMap<String, String> _errors) {
		this._errors = _errors;
	}

	public String get_grammarDelimiter() {
		return _grammarDelimiter;
	}

	public void set_grammarDelimiter(String _grammarDelimiter) {
		this._grammarDelimiter = _grammarDelimiter;
	}
	
	
}
