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
package acide.gui.consolePanel;

import java.awt.Cursor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;

import acide.configuration.console.AcideConsoleCommandsConfiguration;
import acide.configuration.console.commands.AcideConsoleCommandsManager;
import acide.configuration.lexicon.AcideLexiconConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.gui.consolePanel.listeners.AcideConsolePanelFocusListener;
import acide.gui.consolePanel.listeners.AcideConsolePanelKeyboardListener;
import acide.gui.consolePanel.listeners.AcideConsolePanelMouseListener;
import acide.gui.consolePanel.listeners.AcideConsolePanelPopupMenuListener;
import acide.gui.consolePanel.popup.AcideConsolePanelPopupMenu;
import acide.gui.consolePanel.tasks.AcideConsolePDGTask;
import acide.gui.consolePanel.utils.AcideConsolePanelCommandRecord;
import acide.gui.consolePanel.utils.AcideConsolePanelMouseWheelController;
import acide.gui.consolePanel.utils.AcideConsolePanelStyledDocument;
import acide.gui.consolePanel.utils.AcideConsoleWrapEditorKit;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.listeners.AcideConsolePanelSearchWindowMouseListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.console.AcideConsoleProcess;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

/**
 * ACIDE - A Configurable IDE console panel.
 * 
 * @version 0.11
 * @see JPanel
 */
public class AcideConsolePanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE console panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE console panel text component.
	 */
	private JTextPane _textPane;
	/**
	 * ACIDE - A Configurable IDE console panel scroll pane.
	 */
	private JScrollPane _scrollPane;
	/**
	 * ACIDE - A Configurable IDE console panel styled document.
	 */
	private AcideConsolePanelStyledDocument _styledDocument;
	/**
	 * ACIDE - A Configurable IDE console panel process thread.
	 */
	private AcideConsoleProcess _consoleProcess;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu.
	 */
	private AcideConsolePanelPopupMenu _popupMenu;
	/**
	 * ACIDE - A Configurable IDE console panel command to display.
	 */
	private String _command = "";
	/**
	 * ACIDE - A Configurable IDE console panel prompt caret position.
	 */
	private int _promptCaretPosition = 0;
	/**
	 * ACIDE - A Configurable IDE console panel user commands record.
	 */
	private AcideConsolePanelCommandRecord _commandRecord = new AcideConsolePanelCommandRecord();
	/**
	 * ACIDE - A Configurable IDE console panel lexicon configuration.
	 */
	private AcideLexiconConfiguration _lexiconConfiguration;
	/**
	 * ACIDE - A Configurable IDE console commands configuration.
	 */
	private AcideConsoleCommandsConfiguration _consoleCommandsConfiguration;
	/**
	 * ACIDE - A Configurable IDE console panel size.
	 */
	private int _size;
	/**
	 * ACIDE - A Configurable IDE console panel buffer size.
	 */
	private int _bufferSize;
	/**
	 * ACIDE - A Configurable IDE mouse wheel controller.
	 */
	private AcideConsolePanelMouseWheelController _mouseWheelController;
	/**
	 * ACIDE - A Configurable IDE console menu bar.
	 */
	private consolePanelBar menuBar;
	/**
	 * ACIDE - A Configurable IDE name of the container.
	 */
	private String _splitContainer;
	/**
	 * ACIDE - A Configurable IDE directory of the bin
	 */
	private final String _directory = ".\\bin\\";
	
	/**
	 * Creates a new ACIDE - A Configurable IDE console panel.
	 * 
	 * @param isEditable
	 *            determines if the ACIDE - A Configurable IDE is editable or
	 *            not.
	 */
	public AcideConsolePanel(boolean isEditable) {

		super();

		// Builds the components
		buildComponents(isEditable);

		// Adds the components
		addComponents();

		// Sets the listeners of the text pane
		setListeners();

		// Initializes the popup menu
		buildPopupMenu();

		// Inserts the commands history of the previous execution
		// fillHistory();
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console panel components.
	 * 
	 * @param isEditable
	 *            determines if the ACIDE - A Configurable IDE is editable or
	 *            not.
	 */
	private void buildComponents(boolean isEditable) {

		try {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s422"));

			// Builds the scroll pane
			_scrollPane = buildScrollPane(isEditable);

			// Configures the mouse wheel controller line by line
			setMouseWheelController(new AcideConsolePanelMouseWheelController(
					_scrollPane, 1));

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s424"));
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console panel scroll panel.
	 * 
	 * @param isEditable
	 *            determines if the ACIDE - A Configurable IDE is editable or
	 *            not.
	 * @return the ACIDE - A Configurable IDE console panel scroll panel.
	 */
	private JScrollPane buildScrollPane(boolean isEditable) {

		// Creates and loads the lexicon configuration from the lexicon assigner
		_lexiconConfiguration = new AcideLexiconConfiguration();

		// If the lexicon has to be applied to the console
		if (AcideWorkbenchConfiguration.getInstance()
				.getLexiconAssignerConfiguration().getApplyLexiconToConsole()) {

			// If there is no a lexicon configuration defined for the console
			if (AcideWorkbenchConfiguration.getInstance()
					.getLexiconAssignerConfiguration()
					.getConsoleLexiconConfiguration().matches(""))

				// Loads the default lexicon configuration by default
				_lexiconConfiguration
						.load(AcideLexiconConfiguration.DEFAULT_PATH
								+ AcideLexiconConfiguration.DEFAULT_NAME);
			else
				// Loads the defined lexicon configuration
				_lexiconConfiguration.load(AcideWorkbenchConfiguration
						.getInstance().getLexiconAssignerConfiguration()
						.getConsoleLexiconConfiguration());
		} else

			// Loads the default lexicon configuration by default
			_lexiconConfiguration.load(AcideLexiconConfiguration.DEFAULT_PATH
					+ AcideLexiconConfiguration.DEFAULT_NAME);

		// Creates and loads the console commands history
		_consoleCommandsConfiguration = new AcideConsoleCommandsConfiguration();

		_consoleCommandsConfiguration
				.load(AcideConsoleCommandsConfiguration.DEFAULT_PATH
						+ AcideConsoleCommandsConfiguration.DEFAULT_NAME);

		// Creates the styled document
		_styledDocument = new AcideConsolePanelStyledDocument(
				_lexiconConfiguration);

		// Creates the text component
		_textPane = new JTextPane(_styledDocument) {

			/**
			 * ACIDE - A Configurable IDE text pane class serial version UID.
			 */
			private static final long serialVersionUID = 1L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.Component#setSize(java.awt.Dimension)
			 */
			@Override
			public void setSize(Dimension dimension) {

				if (dimension.width < getParent().getSize().width)
					dimension.width = getParent().getSize().width;

				super.setSize(dimension);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.JEditorPane#getScrollableTracksViewportWidth()
			 */
			@Override
			public boolean getScrollableTracksViewportWidth() {

				// Enables / Disables the console panel line wrapping
				return AcideWorkbenchConfiguration.getInstance()
						.getConsolePanelConfiguration().getLineWrapping();
			}
		};

		_textPane.setEditorKit(new AcideConsoleWrapEditorKit());
		
		_textPane.setStyledDocument(_styledDocument);

		// Sets the editable property
		_textPane.setEditable(isEditable);

		// Sets the font by default to the text pane
		_textPane.setFont(new Font("Monospaced", Font.PLAIN, 12));

		// Sets the foreground color by default to the text pane
		_textPane.setForeground(Color.BLACK);

		// Sets the caret color by default to the text pane
		_textPane.setCaretColor(Color.BLACK);

		// Sets the background color by default to the text pane
		_textPane.setBackground(Color.WHITE);

		// This avoids the extra return carriages in the console
		// _textPane.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "none");

		return new JScrollPane(_textPane);

	}

	/**
	 * Adds the ACIDE - A Configurable IDE console panel components.
	 */
	private void addComponents() {

		// Sets the layout
		setLayout(new BorderLayout());

		// Adds the scroll panel to the panel
		add(_scrollPane);

		// Builds the menu bar
		buildMenuBar();

		// Adds the menu bar to the panel
		add(menuBar, BorderLayout.NORTH);

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s423"));
	}

	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE console panel text
	 * pane.
	 */
	private void setListeners() {

		// Sets the ACIDE - A Configurable IDE console panel keyboard listener
		_textPane.addKeyListener(new AcideConsolePanelKeyboardListener());

		// Sets the ACIDE - A Configurable IDE console panel focus listener
		_textPane.addFocusListener(new AcideConsolePanelFocusListener());

		// Sets the ACIDE - A Configurable IDE console panel mouse listener
		_textPane.addMouseListener(new AcideConsolePanelMouseListener());

		// Sets the ACIDE - A Configurable IDE console panel popup menu listener
		_textPane.addMouseListener(new AcideConsolePanelPopupMenuListener());

		// Sets the ACIDE - A Configurable IDE console panel search window mouse
		// listener
		_textPane
				.addMouseListener(new AcideConsolePanelSearchWindowMouseListener());
	}

	/**
	 * Updates the ACIDE - A Configurable IDE console panel look and feel.
	 */
	public void setLookAndFeel() {

		Font newFont = null;
		try {

			// Creates the new font to display
			newFont = new Font(AcideResourceManager.getInstance().getProperty(
					"consolePanel.fontName"),
					Integer.parseInt(AcideResourceManager.getInstance()
							.getProperty("consolePanel.fontStyle")),
					Integer.parseInt(AcideResourceManager.getInstance()
							.getProperty("consolePanel.fontSize")));

			// Sets the font type
			_textPane.setFont(newFont);

			// Sets the foreground color
			setForegroundColor(new Color(Integer.parseInt(AcideResourceManager
					.getInstance().getProperty("consolePanel.foregroundColor"))));

			// Sets the background color
			setBackgroundColor(new Color(Integer.parseInt(AcideResourceManager
					.getInstance().getProperty("consolePanel.backgroundColor"))));

			// Sets the caret color
			_textPane.setCaretColor(new Color(Integer
					.parseInt(AcideResourceManager.getInstance().getProperty(
							"consolePanel.foregroundColor"))));

			// Sets the buffer size
			_bufferSize = Integer.parseInt(AcideResourceManager.getInstance()
					.getProperty("consolePanel.bufferSize"));
			

		} catch (NumberFormatException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console panel popup menu.
	 */
	public void buildPopupMenu() {

		// Creates the popup menu
		_popupMenu = new AcideConsolePanelPopupMenu();
	}

	/**
	 * <p>
	 * Returns the ACIDE - A Configurable IDE console panel content.
	 * </p>
	 * 
	 * <p>
	 * Extracting the content from the document related to the JTextPane and not
	 * from the JTextPane itself, we avoid offset problems that have to do with
	 * the "\r\n" problem.
	 * </p>
	 * 
	 * @return the ACIDE - A Configurable IDE console panel content.
	 */
	public String getContent() {

		// Gets the document length
		int length = _textPane.getDocument().getLength();

		try {
			return _textPane.getDocument().getText(0, length);
		} catch (BadLocationException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}

		return "";
	}

	/**
	 * <p>
	 * Adds a text given as a parameter to the console text.
	 * </p>
	 * <p>
	 * Once the command has been sent to the console process, the reader in the
	 * console process sends the text to add to the console panel text as a
	 * response from the external shell.
	 * </p>
	 * 
	 * @param text
	 *            text to add.
	 * 
	 */
	public void addText(final String text) {

		// Erases the duplicated command
		String newText = eraseDuplicatedCommand(text);
		String finalText = "";

		try {

			// Gets the final text
			finalText = _textPane.getDocument()
					.getText(0, _textPane.getDocument().getLength())
					.concat(newText);

			// Avoids exceptions
			if (finalText != null && !finalText.equals("")) {

				String shellPath=AcideProjectConfiguration.getInstance().getShellPath();

				if(shellPath.trim().toLowerCase().endsWith("des.exe")
						|| shellPath.trim().toLowerCase().endsWith("des")){

					String lastLine=finalText.substring(finalText.lastIndexOf("\n"),finalText.length()).trim();

					// Enable debug panel components
					if((lastLine.startsWith("DES") || lastLine.startsWith("FDES")) && lastLine.endsWith(">")){
						AcideMainWindow.getInstance().getDebugPanel()
								.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						AcideMainWindow.getInstance().getDebugPanel().enableComponents();
					}
					else{
						//	Disable debug panel components
						AcideMainWindow.getInstance().getDebugPanel()
								.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						AcideMainWindow.getInstance().getDebugPanel().disableComponents();
					}
				}

				// Updates the text in the text pane
				_textPane.setText(finalText);

				// Adjust the content to the buffer size
				adjustContentToBufferSize();
			}
		} catch (BadLocationException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Adjusts the ACIDE - A Configurable IDE console panel content to the
	 * current buffer size.
	 */
	public void adjustContentToBufferSize() {

		// Gets the current number of lines
		int numberOfLines = _textPane.getDocument().getDefaultRootElement()
				.getElementCount();
		try {

			// If there is more content than the buffer limit
			if (numberOfLines > _bufferSize) {

				// Deletes the rest of the content
				_textPane.getDocument().remove(
						0,
						_textPane.getDocument().getDefaultRootElement()
								.getElement(numberOfLines - _bufferSize - 1)
								.getStartOffset());
			}

			// Gets the document length
			int documentLength = _textPane.getDocument().getLength();

			// Updates the prompt caret position at the end
			_promptCaretPosition = documentLength;

			// Updates the caret position at the end
			_textPane.setCaretPosition(_promptCaretPosition);

		} catch (BadLocationException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
		}
	}

	/**
	 * <p>
	 * Erases the duplicated command from the text to add to the console panel
	 * after sending a command to the console process.
	 * </p>
	 * <p>
	 * This phenomenon only seems to appear when the loaded shell is the
	 * operative system one, not with other different shells though.
	 * </p>
	 * 
	 * @param text
	 *            new text to add.
	 * @return the result string without the duplicated commands.
	 * 
	 */
	public String eraseDuplicatedCommand(String text) {

		try {
			if (!AcideResourceManager.getInstance()
					.getProperty("consolePanel.shellPath").contains("des.exe")) {

				// Avoids index out of bounds exceptions
				if (_command.length() <= text.length()) {

					// If the first thing of the new text is the command
					if (text.substring(0, _command.length()).equals(_command))

						// Skips it
						return text.substring(_command.length(), text.length());
				}
				return text;
			}
		} catch (MissedPropertyException e) {
			e.printStackTrace();
		}

		return text;
	}

	/**
	 * 
	 * Executes the console exit command.
	 */
	public void executeExitCommand() {

		try {

			// Gets the exit command
			_command = AcideResourceManager.getInstance().getProperty(
					"consolePanel.exitCommand");

			// If the writer is initialized
			if (_consoleProcess.getWriter() != null) {

				// Sends the exit command to the writer
				_consoleProcess.getWriter().write(_command + '\n');

				// Flushes the writer
				_consoleProcess.getWriter().flush();
			}

			// Kills the process anyway
			killShellProcess();

		} catch (Exception exception) {

			// The stream is closed

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			// exception.printStackTrace();
		}
	}

	/**
	 * Forces to the operative system to kill the shell process when the
	 * specified exit command is incorrect.
	 */
	public void killShellProcess() {

		String shellPath;
		try {

			// Gets the shell path
			shellPath = AcideResourceManager.getInstance().getProperty(
					"consolePanel.shellPath");

			// Gets the name of the current process
			int lastIndexOfSlash = shellPath.lastIndexOf(System.getProperty("file.separator"));
			if (lastIndexOfSlash == -1)
				lastIndexOfSlash = shellPath.lastIndexOf(System.getProperty("file.separator"));

			// Gets the shell name process
			String shellName = shellPath.substring(lastIndexOfSlash + 1);

			// Gets the operative system
			String operativeSystemName = System.getProperty("os.name");
			String command = "";

			// If it is WINDOWS
			if (operativeSystemName.toUpperCase().contains("WIN")) {
				// Windows XP home edition doesnt have taskkill command
				if (operativeSystemName.toUpperCase().contains("XP"))
					command += "tskill "
							+ shellName
									.substring(0, shellName.lastIndexOf("."));
				else
					command += "taskkill /im " + shellName + " /f";
			} else {
				command += "killall " + shellName;
			}

			Process killerProcess;
			try {

				String message = "";

				// Executes the killer process
				killerProcess = Runtime.getRuntime().exec(command);
				killerProcess.waitFor();

				if (killerProcess.exitValue() == 0)
					message += shellName + " succesfully killed";
				else
					message += "Unable to kill " + shellName + ". Exit code: "
							+ killerProcess.exitValue() + "n";

				// Updates the log
				AcideLog.getLog().info(message);

				// Destroy the cmd.exe process recently executed
				killerProcess.destroy();

			} catch (IOException exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();

			} catch (InterruptedException exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
			}

		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Executes a command in the console given as a parameter.
	 * 
	 * @param command
	 *            command to execute.
	 * @param parameter
	 *            parameter to use.
	 */
	public void executeCommand(String command, String parameter) {

		if (_consoleProcess.getWriter() != null) {

			try {

				// If there are opened file editors
				if (AcideMainWindow.getInstance().getFileEditorManager()
						.getNumberOfFileEditorPanels() > 0) {

					// Replaces the active file variable for its real value
					command = command.replace("$activeFile$", AcideMainWindow
							.getInstance().getFileEditorManager()
							.getSelectedFileEditorPanel().getAbsolutePath());

					// Replaces the active file path variable for its real value
					command = command
							.replace("$activeFilePath$", AcideMainWindow
									.getInstance().getFileEditorManager()
									.getSelectedFileEditorPanel().getFilePath());

					// Replaces the active files extension for its real value
					command = command.replace("$activeFileExt$",
							AcideMainWindow.getInstance()
									.getFileEditorManager()
									.getSelectedFileEditorPanel()
									.getFileExtension());

					// Replaces the active files name for its real value
					command = command.replace("$activeFileName$",
							AcideMainWindow.getInstance()
									.getFileEditorManager()
									.getSelectedFileEditorPanel()
									.getFileNameWithoutExtension());
				}

				// If it is the default project
				if (AcideProjectConfiguration.getInstance().isDefaultProject()) {

					// Gets the main file editor panel
					AcideFileEditorPanel mainFileEditorPanel = AcideMainWindow
							.getInstance().getFileEditorManager()
							.getMainFileEditorPanel();

					// If exists
					if (mainFileEditorPanel != null) {

						// Replaces the $mainFile$ variable for its real value
						command = command.replace("$mainFile$",
								mainFileEditorPanel.getAbsolutePath());

						// Replaces the $mainFilePath$ variable for its real
						// value
						command = command.replace("$mainFilePath$",
								mainFileEditorPanel.getFilePath());

						// Replaces the $mainFileExt$ variable for its real
						// value
						command = command.replace("$mainFileExt$",
								mainFileEditorPanel.getFileExtension());

						// Replaces the $mainFileName$ variable for its real
						// value
						command = command.replace("$mainFileName$",
								mainFileEditorPanel
										.getFileNameWithoutExtension());
					}
				} else {

					// Not default project

					// Searches for the MAIN file into the ACIDE - A
					// Configurable IDE
					// project configuration
					int mainFileEditorPanelIndex = -1;
					for (int index = 0; index < AcideProjectConfiguration
							.getInstance().getNumberOfFilesFromList(); index++) {
						if (AcideProjectConfiguration.getInstance()
								.getFileAt(index).isMainFile())
							mainFileEditorPanelIndex = index;
					}

					// If exists
					if (mainFileEditorPanelIndex != -1) {

						// Replaces the $mainFile$ variable for its real value
						command = command.replace("$mainFile$",
								AcideProjectConfiguration.getInstance()
										.getFileAt(mainFileEditorPanelIndex)
										.getAbsolutePath());

						// Replaces the $mainFilePath$ variable for its real
						// value
						command = command.replace("$mainFilePath$",
								AcideProjectConfiguration.getInstance()
										.getFileAt(mainFileEditorPanelIndex)
										.getRelativePath());

						// Replaces the $mainFileExt$ variable for its real
						// value
						command = command.replace("$mainFileExt$",
								AcideProjectConfiguration.getInstance()
										.getFileAt(mainFileEditorPanelIndex)
										.getFileExtension());

						// Replaces the $mainFileName$ variable for its real
						// value
						command = command.replace("$mainFileName$",
								AcideProjectConfiguration.getInstance()
										.getFileAt(mainFileEditorPanelIndex)
										.getFileName());
					} else {

						// Gets the main file editor panel
						AcideFileEditorPanel mainFileEditorPanel = AcideMainWindow
								.getInstance().getFileEditorManager()
								.getMainFileEditorPanel();

						// If exists
						if (mainFileEditorPanel != null) {

							// Replaces the $mainFile$ variable for its real
							// value
							command = command.replace("$mainFile$",
									mainFileEditorPanel.getAbsolutePath());

							// Replaces the $mainFilePath$ variable for its real
							// value
							command = command.replace("$mainFilePath$",
									mainFileEditorPanel.getFilePath());

							// Replaces the $mainFileExt$ variable for its real
							// value
							command = command.replace("$mainFileExt$",
									mainFileEditorPanel.getFileExtension());

							// Replaces the $mainFileName$ variable for its real
							// value
							command = command.replace("$mainFileName$",
									mainFileEditorPanel
											.getFileNameWithoutExtension());
						}
					}
				}

				// Erases the previous text after the prompt
				_textPane.setText(_textPane.getText().substring(0,
						_promptCaretPosition));

				// If the parameter is not ""
				if (!parameter.matches("")) {

					// If it is echo of the command
					if (Boolean.parseBoolean(AcideResourceManager.getInstance()
							.getProperty("consolePanel.isEchoCommand")))

						// Adds the command to the text pane text
						if (!_textPane.getText().contains(
								command + " " + parameter))
							_textPane.setText(_textPane.getText().concat(
									command + " " + parameter)
									+ "\n");
				} else {

					// If it is echo of the command
					if (Boolean.parseBoolean(AcideResourceManager.getInstance()
							.getProperty("consolePanel.isEchoCommand")))

						// Adds the command to the text pane text
						if (!_textPane.getText().contains(
								command + " " + parameter))
							_textPane.setText(_textPane.getText().concat(
									command)
									+ "\n");
				}

				// Sends the command to the console
				sendCommandToConsole(command, parameter);

			} catch (Exception exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Sends the command to the console writer so it can execute the command.
	 * 
	 * @param command
	 *            command to execute in the console writer.
	 * @param parameter
	 *            parameter to use.
	 */
	public void sendCommandToConsole(String command, String parameter) {

		try {

			if (_consoleProcess.getWriter() != null) {

				// Get the current shell
				String shellPath=AcideProjectConfiguration.getInstance().getShellPath();

				// If the parameter is not ""
				if (!parameter.matches("")) {

					// Updates the command to be executed
					_command = command + " " + parameter;

					if((shellPath.trim().toLowerCase().endsWith("des.exe")
							|| shellPath.trim().toLowerCase().endsWith("des"))
							|| ((!shellPath.trim().toLowerCase().endsWith("des.exe")
							&& !shellPath.trim().toLowerCase().endsWith("des"))
							&& (!command.startsWith("/tapi") && !command.matches("/pdg")
							&& !command.matches("/show_compilations")))) {

						// Sends the command to the output process
						_consoleProcess.getWriter().write(command + " " + parameter + '\n');
					}

				} else {

					// Updates the command to be executed
					_command = command;

					if((shellPath.trim().toLowerCase().endsWith("des.exe")
							|| shellPath.trim().toLowerCase().endsWith("des"))
							|| ((!shellPath.trim().toLowerCase().endsWith("des.exe")
							&& !shellPath.trim().toLowerCase().endsWith("des"))
							&& (!command.startsWith("/tapi") && !command.matches("/pdg")
							&& !command.matches("/show_compilations")))) {

						// Sends the command to the output shell
						_consoleProcess.getWriter().write(command + "\n");
					}
				}

				// Gets the OS name
				String OSName = System.getProperty("os.name");     
				
				// If it is not WINDOWS
                if (!OSName.toUpperCase().contains("WIN")) {

                    if (command.equals("/pwd")) {
                        _consoleProcess.getOutputGobbler().waitForTaskDone(350);
                    } else if (!command.startsWith("/show_")
                            && !command.startsWith("/open")
                            && !command.startsWith("/tapi")
                            && !command.startsWith("/list_")) {
                        _consoleProcess.getOutputGobbler().waitForTaskDone(100);
                    }

                }
			
				
				// Flushes the writer
				_consoleProcess.getWriter().flush();

				// Updates the command record
				if (_consoleProcess.getOutputGobbler().get_sendToConsole())
					updateCommandRecord();
			}

			// Generates the dependences graph
			if (command.matches("/pdg") //command.matches("/pdg")
					&& !command.matches("/tapi(\\p{Space})*/pdg")
					&& AcideProjectConfiguration.getInstance()
							.isGraphPanelShowed()) {
				if (AcideConsolePDGTask.getInstance().isGenerateGraph()) {
					new Thread(AcideConsolePDGTask.getInstance()).start();
					AcideConsolePDGTask.getInstance().setGenerateGraph(false);
				}

			}
			// if its consulting a datalog file and the graph panel is showed
			// calls to the /tapi /pdg command to generate the graph
			if (command.startsWith("/consult")
					&& command.endsWith(".dl")
					&& AcideProjectConfiguration.getInstance()
							.isGraphPanelShowed()) {

				new Thread(new Runnable() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						AcideMainWindow.getInstance().getConsolePanel()
								.getProcessThread().getOutputGobbler()
								.waitForTaskDone(1000);
						// saves the send to console preference and the generate
						// graph to restart them after /pdg
						final boolean sendToConsole = _consoleProcess
								.getOutputGobbler().get_sendToConsole();
						final boolean generateGraph = AcideConsolePDGTask
								.getInstance().isGenerateGraph();
						// sets the send to console to false so the output of
						// /pdg wont be printed.
						_consoleProcess.getOutputGobbler().set_sendToConsole(
								false);
						// setst the generate graph propperty to true to update
						// the graph with the /pdg output
						AcideConsolePDGTask.getInstance()
								.setGenerateGraph(true);
						if (AcideConsolePDGTask.getInstance().is_taskDone())
							AcideConsolePDGTask.getInstance().set_taskDone(
									false);
						sendCommandToConsole("/pdg", "");
						// waits to the end of the pdg task.
						AcideConsolePDGTask.getInstance().waitForTaskDone(5000);
						// Restarts the send to console and the generate graph
						// original property
						_consoleProcess.getOutputGobbler().set_sendToConsole(
								sendToConsole);
						AcideConsolePDGTask.getInstance().setGenerateGraph(
								generateGraph);
					}
				}).start();

			}
		} catch (IOException exception) {

			// The stream is closed

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			// exception.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Updates the ACIDE - A Configurable IDE console command record with the
	 * new executed command.
	 * </p>
	 * <p>
	 * In case the command contains more than one line, the method splits it
	 * into different lines and stores them as separated commands into the
	 * console command record.
	 * </p>
	 */
	public void updateCommandRecord() {

		// The blank command does not count
		if (!_command.matches("\n")) {

			// Splits the command in lines
			String[] lines = _command.split("\n");

			for (int index = 0; index < lines.length; index++) {

				// If the command record contains the command
				if (_commandRecord.contains(lines[index])) {

					// Decreases the command record maximum index
					_commandRecord.setMaximumIndex(_commandRecord
							.getMaximumIndex() - 1);

					// Removes the command from the command record
					_commandRecord.remove(lines[index]);
				}

				// Adds the command to the command record
				_commandRecord.add(lines[index]);

				// Increases the command record maximum index
				_commandRecord
						.setMaximumIndex(_commandRecord.getMaximumIndex() + 1);

				// The current command record current index is the maximum
				// index
				_commandRecord
						.setCurrentIndex(_commandRecord.getMaximumIndex());

			}

			// The last command in execution was the last line of it
			_command = lines[lines.length - 1];
		}
	}

	/**
	 * <p>
	 * Updates the ACIDE - A Configurable IDE console command record with the
	 * new command.
	 * </p>
	 * 
	 * @param command
	 *            command to insert in command record.
	 *            <p>
	 *            In case the command contains more than one line, the method
	 *            splits it into different lines and stores them as separated
	 *            commands into the console command record.
	 *            </p>
	 */
	public void updateCommandRecord(String command, String parameter) {

		if (!parameter.matches("")) {

			command = command + " " + parameter;
		}

		// The blank command does not count
		if (!command.matches("\n")) {

			// Splits the command in lines
			String[] lines = command.split("\n");

			for (int index = 0; index < lines.length; index++) {

				// If the command record contains the command
				if (_commandRecord.contains(lines[index])) {

					// Decreases the command record maximum index
					_commandRecord.setMaximumIndex(_commandRecord
							.getMaximumIndex() - 1);

					// Removes the command from the command record
					_commandRecord.remove(lines[index]);
				}

				// Adds the command to the command record
				_commandRecord.add(lines[index]);

				// Increases the command record maximum index
				_commandRecord
						.setMaximumIndex(_commandRecord.getMaximumIndex() + 1);

				// The current command record current index is the maximum
				// index
				_commandRecord
						.setCurrentIndex(_commandRecord.getMaximumIndex());
			}

			if (lines.length != 0)
				// The last command in execution was the last line of it
				command = lines[lines.length - 1];
		}
	}

	/**
	 * Clears the console text buffer, leaving only the prompt ready for the
	 * next command execution.
	 */
	public void clearConsoleBuffer() {

		// Gets the text from the beginning to the prompt caret position
		String text = _textPane.getText().substring(0, _promptCaretPosition);

		// Splits the text in lines
		String[] lines = text.split("\n");

		// Puts only the last line
		_textPane.setText(lines[lines.length - 1]);

		// Updates the prompt caret position
		_promptCaretPosition = _textPane.getText().length();

		// Updates the caret position
		_textPane.setCaretPosition(_promptCaretPosition);
	}
	
	/**
	 * Clears all the console text buffer.
	 */
	public void clearAllConsoleBuffer() {

		//clears the buffer
		_textPane.setText("");

		// Updates the prompt caret position
		_promptCaretPosition = 0;

		// Updates the caret position
		_textPane.setCaretPosition(_promptCaretPosition);
	}

	/**
	 * Resets the ACIDE - A Configurable IDE console panel.
	 */
	public void resetConsole() {
		// Kills the previous process
		executeExitCommand();

		if(!_lexiconConfiguration.getName().equals(AcideLexiconConfiguration.DEFAULT_NAME.split("\\.")[0])) {
			AcideWorkbenchConfiguration.getInstance()
					.getLexiconAssignerConfiguration().setApplyLexiconToConsole(true);
		} else {

			// If the lexicon has to be applied to the console
			if (AcideWorkbenchConfiguration.getInstance()
					.getLexiconAssignerConfiguration().getApplyLexiconToConsole()) {

				// If there is no a lexicon configuration defined for the console
				if (AcideWorkbenchConfiguration.getInstance()
						.getLexiconAssignerConfiguration()
						.getConsoleLexiconConfiguration().matches(""))

					// Loads the default lexicon configuration by default
					_lexiconConfiguration
							.load(AcideLexiconConfiguration.DEFAULT_PATH
									+ AcideLexiconConfiguration.DEFAULT_NAME);
				else
					// Loads the defined lexicon configuration
					_lexiconConfiguration.load(AcideWorkbenchConfiguration
							.getInstance().getLexiconAssignerConfiguration()
							.getConsoleLexiconConfiguration());
			} else

				// Loads the default lexicon configuration by default
				_lexiconConfiguration.load(AcideLexiconConfiguration.DEFAULT_PATH
						+ AcideLexiconConfiguration.DEFAULT_NAME);
		}

		// Applies the highlighting
		resetStyledDocument();

		// Sets the text pane as ""
		_textPane.setText("");

		// Creates a new console process
		_consoleProcess = new AcideConsoleProcess();

		// Starts the console process
		_consoleProcess.start();

		//Runtime.getRuntime().addShutdownHook(_consoleProcess);
	}

	/**
	 * <p>
	 * Zooms in or out the font size of the ACIDE - A Configurable IDE console
	 * panel text pane depending on a boolean variable given as a parameter.
	 * </p>
	 * <p>
	 * Also the increment is given as parameter as well.
	 * </p>
	 * 
	 * @param zoom
	 *            increment to apply.
	 * @param hasToIncrement
	 *            indicates if the zoom is in or out.
	 */
	public void zoomFont(int zoom, boolean hasToIncrement) {

		// Gets the current font
		Font currentFont = _textPane.getFont();

		Font newFont;

		// If it is zoom out
		if (hasToIncrement)
			newFont = new Font(currentFont.getFontName(),
					currentFont.getStyle(), currentFont.getSize() + zoom);
		else
			newFont = new Font(currentFont.getFontName(),
					currentFont.getStyle(), currentFont.getSize() - zoom);

		// Sets the new font
		_textPane.setFont(newFont);
	}

	/**
	 * Selects a text in the ACIDE - A Configurable IDE console panel text pane.
	 * 
	 * @param start
	 *            selection start.
	 * @param length
	 *            selection length.
	 */
	public void selectText(int start, int length) {

		// Sets the selection start
		_textPane.setSelectionStart(start);

		// Sets the selection end
		_textPane.setSelectionEnd(start + length);
	}

	/**
	 * Resets the styled document of the text pane in the ACIDE - A Configurable
	 * IDE console panel.
	 */
	public void resetStyledDocument() {

		// Initializes the document
		_styledDocument.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 120);
	}

	/**
	 * Shows the ACIDE - A Configurable IDE console panel.
	 */
	public void showConsolePanel() {

		JSplitPane container = AcideMainWindow.getInstance()
				.getSpecificSplitPane(_splitContainer);
		container.setDividerLocation(_size);
		setVisible(true);

		AcideMainWindow.getInstance().updateVisibility();

	}

	/**
	 * Disposes the ACIDE - A Configurable IDE console panel.
	 */
	public void disposeConsolePanel() {

		JSplitPane container = AcideMainWindow.getInstance()
				.getSpecificSplitPane(_splitContainer);
		_size = container.getDividerLocation();
		if (container.getLeftComponent().getClass() == this.getClass()) {
			if (!container.getRightComponent().isVisible())
				_size = 300;
		} else if (container.getRightComponent().getClass() == this.getClass())
			if (!container.getLeftComponent().isVisible())
				_size = 300;
		setVisible(false);

		AcideMainWindow.getInstance().updateVisibility();
	}

	/**
	 * Inserts the commands history of the previous execution of ACIDE - A
	 * Configurable IDE
	 */
	public void fillHistory() {
		_commandRecord = new AcideConsolePanelCommandRecord();

		for (int index = 0; index < AcideMainWindow.getInstance()
				.getConsolePanel().getConsoleCommandsConfiguration()
				.getConsoleCommandsManager().getSize(); index++) {

			// Gets the command value
			String value = AcideMainWindow.getInstance().getConsolePanel()
					.getConsoleCommandsConfiguration()
					.getConsoleCommandsManager().getCommandAt(index);

			value += "\n";

			updateCommandRecord(parseAcideVariables(value), "");

		}

		AcideMainWindow.getInstance().getConsolePanel()
				.getConsoleCommandsConfiguration()
				.setConsoleCommandsManager(new AcideConsoleCommandsManager());

		AcideMainWindow.getInstance().getConsolePanel()
				.getConsoleCommandsConfiguration().save();
	}

	/**
	 * Parses the ACIDE - A Configurable IDE variables to real paths in order to
	 * send them properly to the ACIDE - A Configurable IDE console panel for
	 * its execution.
	 * 
	 * @param buttonAction
	 *            raw command to execute from ACIDE - A Configurable IDE console
	 *            panel.
	 * 
	 * @return the parsed string that contains to command to execute in the
	 *         ACIDE - A Configurable IDE console panel.
	 */
	private String parseAcideVariables(String buttonAction) {

		// Gets the command to execute
		String command = buttonAction;

		// If there are opened file editors
		if (AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels() > 0) {

			// Replaces the active file variable for its real value
			command = command.replace("$activeFile$", AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getAbsolutePath());

			// Replaces the active file path variable for its real value
			command = command.replace("$activeFilePath$", AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getFilePath());

			// Replaces the active files extension for its real value
			command = command.replace("$activeFileExt$", AcideMainWindow
					.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getFileExtension());

			// Replaces the active files name for its real value
			command = command
					.replace("$activeFileName$", AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getSelectedFileEditorPanel()
							.getFileNameWithoutExtension());
		}

		// If it is the default project
		if (AcideProjectConfiguration.getInstance().isDefaultProject()) {

			// Gets the main file editor panel
			AcideFileEditorPanel mainFileEditorPanel = AcideMainWindow
					.getInstance().getFileEditorManager()
					.getMainFileEditorPanel();

			// If exists
			if (mainFileEditorPanel != null) {

				// Replaces the $mainFile$ variable for its real value
				command = command.replace("$mainFile$",
						mainFileEditorPanel.getAbsolutePath());

				// Replaces the $mainFilePath$ variable for its real value
				command = command.replace("$mainFilePath$",
						mainFileEditorPanel.getFilePath());

				// Replaces the $mainFileExt$ variable for its real value
				command = command.replace("$mainFileExt$",
						mainFileEditorPanel.getFileExtension());

				// Replaces the $mainFileName$ variable for its real value
				command = command.replace("$mainFileName$",
						mainFileEditorPanel.getFileNameWithoutExtension());
			}
		} else {

			// Not default project

			// Searches for the MAIN file into the ACIDE - A Configurable IDE
			// project configuration
			int mainFileEditorPanelIndex = -1;
			for (int index = 0; index < AcideProjectConfiguration.getInstance()
					.getNumberOfFilesFromList(); index++) {
				if (AcideProjectConfiguration.getInstance().getFileAt(index)
						.isMainFile())
					mainFileEditorPanelIndex = index;
			}

			// If exists
			if (mainFileEditorPanelIndex != -1) {

				// Replaces the $mainFile$ variable for its real value
				command = command.replace(
						"$mainFile$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getAbsolutePath());

				// Replaces the $mainFilePath$ variable for its real value
				command = command.replace(
						"$mainFilePath$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getRelativePath());

				// Replaces the $mainFileExt$ variable for its real value
				command = command.replace(
						"$mainFileExt$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getFileExtension());

				// Replaces the $mainFileName$ variable for its real value
				command = command.replace(
						"$mainFileName$",
						AcideProjectConfiguration.getInstance()
								.getFileAt(mainFileEditorPanelIndex)
								.getFileName());
			}
		}

		return command;
	}

	/**
	 * Returns the prompt caret position.
	 * 
	 * @return the prompt caret position.
	 */
	public int getPromptCaretPosition() {
		return _promptCaretPosition;
	}

	/**
	 * Sets a new value to the font name.
	 * 
	 * @param fontName
	 *            new value to set.
	 */
	public void setFontName(Font fontName) {
		_textPane.setFont(fontName);
		repaint();
	}

	/**
	 * Sets a new value to the foreground color.
	 * 
	 * @param foregroundColor
	 *            new value to set.
	 */
	public void setForegroundColor(Color foregroundColor) {
		_textPane.setForeground(foregroundColor);
		_textPane.setCaretColor(foregroundColor);
		repaint();
	}

	/**
	 * Sets a new value to the background color.
	 * 
	 * @param backgroundColor
	 *            new value to set.
	 */
	public void setBackgroundColor(Color backgroundColor) {
		_textPane.setBackground(backgroundColor);
		repaint();
	}

	/**
	 * Returns the default styled document.
	 * 
	 * @return the default styled document.
	 */
	public DefaultStyledDocument getDefaultStyledDocument() {
		return _styledDocument;
	}

	/**
	 * Sets a new value to the default styled document.
	 * 
	 * @param defaultStyledDocument
	 *            new value to set.
	 */
	public void setDefaultStyledDocument(
			AcideConsolePanelStyledDocument defaultStyledDocument) {
		_styledDocument = defaultStyledDocument;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console process thread.
	 * 
	 * @return the ACIDE - A Configurable IDE console process thread.
	 */
	public AcideConsoleProcess getProcessThread() {
		if (_consoleProcess != null)
			return _consoleProcess;
		else
			return new AcideConsoleProcess();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel command record.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel command record.
	 */
	public AcideConsolePanelCommandRecord getCommandRecord() {
		return _commandRecord;
	}

	/**
	 * Returns the console panel popup menu.
	 * 
	 * @return the console panel popup menu.
	 */
	public AcideConsolePanelPopupMenu getPopupMenu() {
		return _popupMenu;
	}

	/**
	 * Sets a new value to the command.
	 * 
	 * @param command
	 *            new value to set.
	 */
	public void setCommand(String command) {
		_command = command;
	}

	/**
	 * Sets a new value to the prompt caret position.
	 * 
	 * @param promptCaretPosition
	 *            new value to set.
	 */
	public void setPromptCaretPosition(int promptCaretPosition) {
		_promptCaretPosition = promptCaretPosition;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel text pane.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel text pane.
	 */
	public JTextComponent getTextPane() {
		return _textPane;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel lexicon
	 * configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel lexicon
	 *         configuration.
	 */
	public AcideLexiconConfiguration getLexiconConfiguration() {
		return _lexiconConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console commands configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE console commands configuration.
	 */
	public AcideConsoleCommandsConfiguration getConsoleCommandsConfiguration() {
		return _consoleCommandsConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel size.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel size.
	 */
	public int getConsoleSize() {
		return _size;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE console panel size.
	 * 
	 * @param size
	 *            new value to set.
	 */
	public void setConsoleSize(int size) {
		_size = size;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel buffer size.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel buffer size.
	 */
	public int getBufferSize() {
		return _bufferSize;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE console panel buffer
	 * size.
	 * 
	 * @param bufferSize
	 *            new value to set.
	 */
	public void setBufferSize(int bufferSize) {
		_bufferSize = bufferSize;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE mouse wheel
	 * controller.
	 * 
	 * @param mouseWheelController
	 *            new value to set.
	 */
	public void setMouseWheelController(
			AcideConsolePanelMouseWheelController mouseWheelController) {
		_mouseWheelController = mouseWheelController;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE mouse wheel controller.
	 * 
	 * @return the ACIDE - A Configurable IDE mouse wheel controller.
	 */
	public AcideConsolePanelMouseWheelController getMouseWheelController() {
		return _mouseWheelController;
	}

	/**
	 * Gets the ACIDE - A Configurable IDE console panel menu bar
	 * 
	 * @return the ACIDE - A Configurable IDE console panel menu bar
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * Gets the ACIDE - A Configurable IDE console panel split container
	 * 
	 * @return the ACIDE - A Configurable IDE console panel split container
	 */
	public String getSplitContainer() {
		return _splitContainer;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE console panel split container
	 * 
	 * @param name
	 *            new split container
	 */
	public void setSplitContainer(String name) {
		_splitContainer = name;
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console panel menu bar
	 * 
	 */
	public void buildMenuBar() {

		menuBar = new consolePanelBar();

		// Creates the icon for the panel
		JLabel menu = new JLabel();
		menu.setIcon(new ImageIcon(
				"./resources/icons/menu/view/showConsolePanel.png"));
		// Adds the icon to the panel
		menuBar.add(menu);

		// Creates the label for the name of the panel
		JLabel name = new JLabel();
		name.setForeground(this.menuBar.getColorFont());
		// Adds the label of the name of the panel
		menuBar.add(name);
		// Sets the text of the name of the panel
		setTextMenuBar();

		// Creates the close view button
		JButton close = new JButton();
		// Sets the icon of the close view button
		close.setIcon(new ImageIcon(
				"./resources/icons/panels/closeViewPanel.png"));
		// Adds the listener of the close button
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				disposeConsolePanel();
				AcideProjectConfiguration.getInstance()
						.setIsConsolePanelShowed(false);
				AcideMainWindow
						.getInstance()
						.getMenu()
						.getViewMenu()
						.getShowConsolePanelCheckBoxMenuItem()
						.setSelected(
								AcideProjectConfiguration.getInstance()
										.isConsolePanelShowed());

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);
			}
		});
		// Sets the margin of the close button
		close.setMargin(new Insets(0, 0, 0, 0));
		// Aligns the button to the right margin
		menuBar.add(Box.createHorizontalGlue());
		// Adds the close button
		menuBar.add(close);

		menuBar.setName("AcideConsolePanel");
	}

	/**
	 * Sets the the text of the ACIDE - A Configurable IDE console panel menu
	 * bar
	 * 
	 */
	public void setTextMenuBar() {
		String name = AcideLanguageManager.getInstance().getLabels()
				.getString("s223");
		((JLabel) menuBar.getComponent(1)).setText(" " + name);
	}

	@Override
	public String getName() {
		return "AcideConsolePanel";
	}

	/**
	 * Interrupts the ACIDE - A Configurable IDE shell process. For this purpose
	 * there are some resources needed in windows OS. The command tasklist must
	 * be installed, and the executables SendSignalCtrlC must be located in
	 * their correct place
	 */
	public void interruptShellProcess() {
		String shellPath;
		try {

			// Gets the shell path
			shellPath = AcideResourceManager.getInstance().getProperty(
					"consolePanel.shellPath");

			// Gets the name of the current process
			int lastIndexOfSlash = shellPath.lastIndexOf("\\");
			if (lastIndexOfSlash == -1)
				lastIndexOfSlash = shellPath.lastIndexOf("/");

			// Gets the shell name process
			String shellName = shellPath.substring(lastIndexOfSlash + 1);
//			String directory = ".\binaries";

			// Gets the operative system
			String OSName = System.getProperty("os.name");
			String command = "";

			// If it is WINDOWS
			if (OSName.toUpperCase().contains("WIN")) {

//				// Gets the System Architecture
////				String arch = System.getProperty("os.arch");
//				boolean is64bit = false;
//				if (System.getProperty("os.name").contains("Windows")) {
//				    is64bit = (System.getenv("ProgramFiles(x86)") != null);
//				} else {
//				    is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
//				}
//				
//				String pid = getProcessID(shellName);
//
//				if (pid != null && !pid.equals(""))
//					// Checks the architecture
//					if (!is64bit)
//						command += "cmd /c start " + _directory + "SendSignalCtrlC_32.exe " + pid;
//					else
//						command += "cmd /c start " + _directory + "SendSignalCtrlC_64.exe " + pid;

			} else {
				// If it's UNIX
				command += "killall -s SIGINT " + shellName;
//			}

			Process interruptProcess;
			try {

				String message = "";

				
				// Executes the interrupt process
				interruptProcess = Runtime.getRuntime().exec(command);
					
				interruptProcess.waitFor();
				interruptProcess.getOutputStream().flush();
				
				_consoleProcess.getWriter().flush();
				

				if (interruptProcess.exitValue() == 0)
					message += shellName + " succesfully interrupted";
				else
					message += "Unable to interrupt " + shellName
							+ ". Exit code: " + interruptProcess.exitValue()
							+ "n";

				// Updates the log
				AcideLog.getLog().info(message);
				

				// Destroy the interrupt process recently executed
				interruptProcess.destroy();
				

			} catch (IOException exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();

			} catch (InterruptedException exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
			}
			
			}

		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	public void changeColor(Color background, Color foreground, Color darker) {
		this._textPane.setBackground(background);
		this._textPane.setForeground(foreground);
		this.menuBar.setBackColor(darker);
		for(Component m:this.menuBar.getComponents()) {
			m.setForeground(foreground);
		}
		this.menuBar.repaint();
		Component[] men = this._popupMenu.getComponents();
		for(Component c:men) {
			c.getClass().cast(c).setBackground(darker);
			c.getClass().cast(c).setForeground(foreground);
			((JComponent) c.getClass().cast(c)).setOpaque(true);
		}
	}
	
	class consolePanelBar extends JMenuBar{
		private static final long serialVersionUID = 1L;
		private Color background;
		private Color foreground;
		
		public consolePanelBar() {
			super();
			try {
			String colorB = AcideResourceManager.getInstance().getProperty("databasePanel.backgroundColor");
			String colorF = AcideResourceManager.getInstance().getProperty("databasePanel.foregroundColor");
			Color b = new Color(Integer.parseInt(colorB));
			Color darker = new Color((int) (b.getRed() *0.9), (int) (b.getGreen() *0.9), (int) (b.getBlue() *0.9));
			this.background = darker;
			this.foreground = (new Color(Integer.parseInt(colorF)));
			}catch(Exception e){
				this.background = Color.WHITE;
			}
		}
		
		public Color getColorFont() {
			return foreground;
		}
		
		public Color getBackColor() {
			return background;
		}
		public void setBackColor(Color background) {
			this.background = background;
		}
		
		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent((java.awt.Graphics) g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(background);
			g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		}
	}
	
	/*public JScrollPane getScrollPane() {
		return this._scrollPane;
	}*/

}
