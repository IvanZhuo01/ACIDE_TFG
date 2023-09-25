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
package acide.gui.menuBar.configurationMenu.grammarMenu.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import acide.configuration.grammar.AcideGrammarConfiguration;
import acide.configuration.lexicon.AcideLexiconConfiguration;
import acide.configuration.lexicon.delimiters.AcideLexiconDelimitersManager;
import acide.configuration.lexicon.tokens.AcideLexiconTokenGroup;
import acide.configuration.lexicon.tokens.AcideLexiconTokenManager;
import acide.files.AcideFileExtensionFilterManager;
import acide.files.AcideFileManager;
import acide.files.bytes.AcideByteFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.fileEditor.fileEditorPanel.fileEditorTextEditionArea.utils.AcideHighlightError;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.parser.AcideGrammarAnalyzer;
import acide.process.parser.AcideGrammarFileCreationProcess;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE grammar configuration window.
 * 
 * @version 0.19
 * @see JFrame
 */
public class AcideGrammarConfigurationWindow extends JFrame {

	/**
	 * ACIDE - A Configurable IDE grammar configuration window class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window image icon.
	 */
	private static final String ICON = "./resources/images/icon.png";
	/**
	 * ACIDE - A Configurable IDE grammar configuration window categories panel.
	 */
	private JPanel _categoriesPanel;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window rules panel.
	 */
	private JPanel _rulesPanel;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window delimiter panel.
	 */
	private JPanel _delimiterPanel;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window delimiter text field.
	 */
	private JTextField _delimiterTxt;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window categories button
	 * panel.
	 */
	private JPanel _categoriesButtonPanel;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window rules button
	 * panel.
	 */
	private JPanel _rulesButtonPanel;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window categories text
	 * area.
	 */
	private JTextArea _categoriesTextArea;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window rules text area.
	 */
	private JTextArea _rulesTextArea;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window categories scroll
	 * pane.
	 */
	private JScrollPane _categoriesScrollPane;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window rules scroll
	 * pane.
	 */
	private JScrollPane _rulesScrollPane;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window accept button.
	 */
	private JButton _acceptButton;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window load lexicon
	 * button.
	 */
	private JButton _loadLexiconButton;
	/**
	 * ACIDE - A Configurable IDE grammar configuration combo box with all lexicon.
	 */
	private JComboBox<ComboBoxItem> _lexiconCbBox;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window load categories
	 * button.
	 */
	//private JButton _loadCategoriesButton;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window save categories
	 * button.
	 */
	//private JButton _saveCategoriesButton;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window load rules
	 * button.
	 */
	private JButton _loadRulesButton;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window save rules
	 * button.
	 */
	private JButton _saveRulesButton;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window verbose process
	 * check box.
	 */
	private JCheckBox _verboseProcessCheckBox;
	/**
	 * ACIDE - A Configurable IDE grammar configuration window is for modifying
	 * flag.
	 */
	private boolean _isForModifying;
	/**
	 * Creates a new ACIDE - A Configurable IDE grammar configuration window.
	 * 
	 * @param isForModifying
	 *            indicates if the window is used for modifying or creating a
	 *            grammar.
	 */
	public AcideGrammarConfigurationWindow(boolean isForModifying) {

		super();

		// Stores the is for modifying flag
		_isForModifying = isForModifying;

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s173"));

		// Builds the window components
		buildComponents();

		// Sets the listener of the window components
		setListeners();

		// Adds the components to the window
		addComponents();

		// Sets the window configuration
		setWindowConfiguration();
	}

	/**
	 * Sets the ACIDE - A Configurable IDE grammar configuration window
	 * configuration.
	 */
	private void setWindowConfiguration() {

		if (_isForModifying) {
			// Gets the ACIDE - A Configuration IDE current grammar
			// configuration
			String currentGrammarConfiguration = AcideMainWindow.getInstance()
					.getFileEditorManager().getSelectedFileEditorPanel()
					.getCurrentGrammarConfiguration().getPath();
			
			// Process the file grammar
			AcideByteFileManager.getInstance().processGrammarFile(currentGrammarConfiguration);
			
			// Gets the name
			int lastIndexOfSlash = currentGrammarConfiguration
					.lastIndexOf("\\");
			if (lastIndexOfSlash == -1)
				lastIndexOfSlash = currentGrammarConfiguration.lastIndexOf("/");
			String name = currentGrammarConfiguration.substring(
					lastIndexOfSlash + 1,
					currentGrammarConfiguration.length() - 4);

			// Sets the title for an existent grammar
			setTitle(AcideLanguageManager.getInstance().getLabels()
					.getString("s230")
					+ " - " + name);

			String jarPath = null;

			try {

				// Gets the ACIDE - A Configurable IDE jar path
				jarPath = AcideResourceManager.getInstance().getProperty(
						"jarPath");
			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s938"), "Error",
						JOptionPane.ERROR_MESSAGE);

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
			}

			if (jarPath != null && !jarPath.equals("null")) {
				// Sets the window icon image
				setIconImage(new ImageIcon(ICON).getImage());

				// The window is not resizable
				setResizable(false);

				// Packs the window components
				pack();

				// Centers the window
				setLocationRelativeTo(null);

				// Sets the window visible
				setVisible(true);

				// Disables the main window
				AcideMainWindow.getInstance().setEnabled(false);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s174"));
				
				// Gets its content
				String fileContent = AcideFileManager.getInstance().load(
						"src/acide/process/parser/grammar/lexicalCategories.txt");

				if (fileContent != null)
					// Updates the categories text area
					_categoriesTextArea.setText(fileContent);

				// Gets its content
				fileContent = AcideFileManager.getInstance().load(
						"src/acide/process/parser/grammar/delimiter.txt");
				
				if (!fileContent.equals(""))
					// Updates the statement delimiter deleting \n
					_delimiterTxt.setText(String.valueOf(fileContent.charAt(0)));
				
				// Gets its content
				fileContent = AcideFileManager.getInstance().load(
						"src/acide/process/parser/grammar/syntaxRules.txt");

				if (fileContent != null)
					// Updates the rules text area
					_rulesTextArea.setText(fileContent);
				
			} else {

				// Displays an warning message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2019"),
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2020"),
						JOptionPane.WARNING_MESSAGE);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s2021"));
			}

		} else {

			// Sets the title for a new grammar
			setTitle(AcideLanguageManager.getInstance().getLabels()
					.getString("s184"));

			// Sets the window icon image
			setIconImage(new ImageIcon(ICON).getImage());

			// The window is not resizable
			setResizable(false);

			// Packs the window components
			pack();

			// Centers the window
			setLocationRelativeTo(null);

			// Sets the window visible
			setVisible(true);

			// Disables the main window
			AcideMainWindow.getInstance().setEnabled(false);

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s174"));
		}
	}

	/**
	 * Builds the ACIDE - A Configurable IDE grammar configuration window
	 * components.
	 */
	private void buildComponents() {

		// Creates the categories panel
		_categoriesPanel = new JPanel(new GridBagLayout());

		// Sets the categories panel border
		_categoriesPanel.setBorder(BorderFactory.createTitledBorder(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s175"), TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION));

		// Creates the rules panel
		_rulesPanel = new JPanel(new GridBagLayout());

		// Sets the rules panel border
		_rulesPanel.setBorder(BorderFactory.createTitledBorder(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s176"), TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION));
		
		// Creates the delimiter panel
		_delimiterPanel = new JPanel(new GridBagLayout());
		
		// Sets the delimiter panel border
		_delimiterPanel.setBorder(BorderFactory.createTitledBorder(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s2435"), TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION));

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the categories button panel
		_categoriesButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the rules button panel
		_rulesButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		// Creates the delimiter text field
		_delimiterTxt = new JTextField();
		
		_delimiterTxt.setDocument(new JTextFieldLimit(1));
		
		// Sets the delimiter text field tool tip text
		_delimiterTxt.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2436"));
		
		// Creates the categories text area
		_categoriesTextArea = new JTextArea();

		// Sets the categories text area tool tip text
		_categoriesTextArea.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s179"));

		// Creates the categories scroll pane which contains the categories text
		// area
		_categoriesScrollPane = new JScrollPane(_categoriesTextArea);

		// Creates the rules text area
		_rulesTextArea = new JTextArea();

		// Sets the rules text area tool tip text
		_rulesTextArea.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s180"));

		// Creates the rules scroll pane which contains the rules text area
		_rulesScrollPane = new JScrollPane(_rulesTextArea);

		// Creates the accept button
		_acceptButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s194"));

		// Sets the accept button tool tip text
		_acceptButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s181"));

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s178"));

		// Sets the cancel button tool tip text
		_cancelButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s182"));

		// Creates the load lexicon button
		_loadLexiconButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s196"));
		
		// Set the load lexicon tool tip text
		_loadLexiconButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s193"));
		
		// Creates the combo box
		_lexiconCbBox = new JComboBox<ComboBoxItem>();
		
		// Add items to the combo box
		getAllLexicon();
		
		// Creates the load categories button
//		_loadCategoriesButton = new JButton(AcideLanguageManager.getInstance()
//				.getLabels().getString("s192"));

		// Sets the load categories tool tip text
//		_loadCategoriesButton.setToolTipText(AcideLanguageManager.getInstance()
//				.getLabels().getString("s193"));

		// Creates the save categories button
//		_saveCategoriesButton = new JButton(AcideLanguageManager.getInstance()
//				.getLabels().getString("s194"));

		// Sets the save categories button tool tip text
//		_saveCategoriesButton.setToolTipText(AcideLanguageManager.getInstance()
//				.getLabels().getString("s195"));

		// Creates the load rules button
		_loadRulesButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s196"));

		// Sets the load rules button tool tip text
		_loadRulesButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s197"));

		// Creates the save rules button
//		_saveRulesButton = new JButton(AcideLanguageManager.getInstance()
//				.getLabels().getString("s198"));

		// Sets the save rules button tool tip text
//		_saveRulesButton.setToolTipText(AcideLanguageManager.getInstance()
//				.getLabels().getString("s199"));

		// Creates the verbose process check box
		_verboseProcessCheckBox = new JCheckBox(AcideLanguageManager
				.getInstance().getLabels().getString("s1064"));
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE grammar
	 * configuration window with the layout.
	 */
	private void addComponents() {

		// Sets the layout
		setLayout(new GridBagLayout());
		
		// Adds the components to the window with the layout
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 600;
		constraints.ipady = 400;
		constraints.insets = new Insets(10, 10, 10, 10);

		// Adds the categories panel to the categories scroll pane
		_categoriesPanel.add(_categoriesScrollPane, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.ipadx = 600;
		constraints.ipady = 5;
		
		_delimiterPanel.add(_delimiterTxt, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;

		// Adds lexicon combo box to the categories button panel
		_categoriesButtonPanel.add(_lexiconCbBox);
		
		// Adds the new lexicon button to the categories button panel
		_categoriesButtonPanel.add(_loadLexiconButton);
		
		// Adds the load categories button to the categories button panel
//		_categoriesButtonPanel.add(_loadCategoriesButton);

		// Adds the save categories button to the categories button panel
//		_categoriesButtonPanel.add(_saveCategoriesButton);

		// Adds the categories button panel to the categories panel
		_categoriesPanel.add(_categoriesButtonPanel, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 0;
		constraints.ipadx = 600;
		constraints.ipady = 400;

		// Adds the rules scroll panel to the rules panel
		_rulesPanel.add(_rulesScrollPane, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.EAST;

		// Adds the load rules button to the rules button panel
		_rulesButtonPanel.add(_loadRulesButton);

		// Adds the save rules button to the rules button panel
//		_rulesButtonPanel.add(_saveRulesButton);

		// Adds the rules button panel to the rules panel
		_rulesPanel.add(_rulesButtonPanel, constraints);

		// Adds the accept button to the button panel
		_buttonPanel.add(_acceptButton);

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);
		
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;

		// Adds the categories panel to the window
		add(_categoriesPanel, constraints);
		constraints.weightx = 0.5;
		constraints.gridx = 1;

		// Adds the rules panel to the window
		add(_rulesPanel, constraints);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		add(_delimiterPanel, constraints);
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.weightx = 0;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 1;

		// Adds the verbose process check box to the window
		add(_verboseProcessCheckBox, constraints);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 2;

		// Adds the button panel to the window
		add(_buttonPanel, constraints);
	}

	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE grammar
	 * configuration window components.
	 */
	public void setListeners() {

		// Sets the accept button action listener
		_acceptButton.addActionListener(new AcceptButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());

		// Sets the load rules button action listener
		_loadRulesButton.addActionListener(new LoadRulesButtonAction());

		// Sets the save rules button action listener
//		_saveRulesButton.addActionListener(new SaveRulesButtonAction());

		// Set the load lexicon button action listener
		_loadLexiconButton.addActionListener(new LoadLexiconButtonAction());
		
		// Sets the load categories button action listener
//		_loadCategoriesButton
//				.addActionListener(new LoadCategoriesButtonAction());

		// Sets the save categories button action listener
//		_saveCategoriesButton
//				.addActionListener(new SaveCategoriesButtonAction());

		// Sets the window closing listener
		addWindowListener(new AcideWindowClosingListener());

		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
				"EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
	}

	/**
	 * Closes the ACIDE - A Configurable IDE grammar configuration window.
	 */
	private void closeWindow() {

		// Set the main window enabled again
		AcideMainWindow.getInstance().setEnabled(true);

		// Closes the window
		dispose();
		
		// Brings the main window to the front
		AcideMainWindow.getInstance().setAlwaysOnTop(true);

		// But not permanently
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}

	/**
	 * ACIDE - A Configurable IDE grammar configuration window accept button
	 * action listener.
	 * 
	 * @version 0.19
	 * @see ActionListener
	 */
	class AcceptButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// Gets the selected file editor panel
			AcideFileEditorPanel selectedFileEditorPanel = AcideMainWindow.getInstance()
					.getFileEditorManager().getSelectedFileEditorPanel();
			
			// Clear all the errors highlights
			AcideHighlightError.getInstance().clearErrorHighlight();
			
			// Creates the file content
			String textContent = "grammar Expr;" + System.lineSeparator() +_rulesTextArea.getText()
			+ System.lineSeparator() + _categoriesTextArea.getText();

			//int index = textContent.indexOf(";");
			//String firstLine = "grammar Expr;";
			//textContent = firstLine + textContent.substring(index + 1);
			//textContent += System.lineSeparator() + _categoriesTextArea.getText();
			
			// Saves the Expr.g4 file
			boolean isSaved = AcideFileManager.getInstance().write("Expr.g4",
					textContent);
			
			// Saves the lexical categories file
			isSaved = isSaved
					&& AcideFileManager.getInstance().write(
							AcideGrammarFileCreationProcess.DEFAULT_PATH +"lexicalCategories.txt",
							_categoriesTextArea.getText());

			// Saves the syntax rules file
			isSaved = isSaved
					&& AcideFileManager.getInstance().write(AcideGrammarFileCreationProcess.DEFAULT_PATH + "syntaxRules.txt",
							_rulesTextArea.getText());
			
			// Saves the delimiter file
			isSaved = isSaved 
					&&	AcideFileManager.getInstance().write(AcideGrammarFileCreationProcess.DEFAULT_PATH + "delimiter.txt",
								_delimiterTxt.getText());

			if (isSaved)

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s185"));
			else
				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s186"));
			
			// Closes the window
			closeWindow();

			// Selects the new grammar name
//			String newGrammarName = "";
			
			// Selects the action
			String action = "";
			
			// Selects the new grammar path
			String grammarPath;
			
			if (_isForModifying){
//				newGrammarName = "lastModified.txt";
				action = AcideLanguageManager.getInstance().getLabels()
				.getString("s29");
				grammarPath = AcideMainWindow.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanel().getCurrentGrammarConfiguration()
						.getPath();
			}
			else {
//				newGrammarName = "newGrammar.txt";
				action = AcideLanguageManager.getInstance().getLabels()
				.getString("s30");
				grammarPath = AcideGrammarConfiguration.DEFAULT_PATH + "newGrammar.txt";
			}
			
			
			// Save the grammar
			AcideByteFileManager.getInstance().saveGrammar(grammarPath);
			try {
				// Creates the process for the grammar file creation
				AcideGrammarFileCreationProcess process = new AcideGrammarFileCreationProcess(
						grammarPath, _verboseProcessCheckBox.isSelected(), action, true);

				process.setLock(AcideLanguageManager.getInstance().getLabels().getString("s2438"));
				
				// Starts the process
				process.start();
				
				selectedFileEditorPanel.set_grammarDelimiter(_delimiterTxt.getText());
			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, exception.getMessage(),
						AcideLanguageManager.getInstance().getLabels()
								.getString("s930"), JOptionPane.ERROR_MESSAGE);

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE grammar configuration window cancel button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class CancelButtonAction implements ActionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Closes the window
			closeWindow();

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s183"));
		}
	}

	/**
	 * ACIDE - A Configurable IDE grammar configuration window load rules button
	 * action listener.
	 * 
	 * @version 0.19
	 * @see ActionListener
	 */
	class LoadRulesButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Asks for the file path to the user
			String absolutePath = AcideFileManager.getInstance().askForFile(
					AcideFileOperation.OPEN, AcideFileTarget.FILES,
					AcideFileType.FILE, "", null);

			if (absolutePath != null) {
		        String syntaxContent = "";
		        String lexicalContent = "";
		        String delimiterContent = "";
		        
		        boolean syntax = false;
		        boolean lexical = false;
		        boolean delimiter = false;
		        
		        // Read contents of source
		        try {
		            BufferedReader reader = new BufferedReader(new FileReader(absolutePath));
		            String line;
		            while ((line = reader.readLine()) != null) {
		            	if(line.equals("grammar Expr;"))
		            		continue;
		            	if(line.equals("class ExprLexer extends Lexer;")){
		            		lexical = true;
		            		continue;
		            	}
		            	if(line.equals("class ExprParser extends Parser;")) {
		            		lexical = false;
		            		syntax = true;
		            		continue;
		            	}
		            	if(line.equals("Statement delimiter;")) {
		            		delimiter = true;
		            		syntax = false;
		            		continue;
		            	}
		            	if(delimiter)
		            		delimiterContent = line;
		            	if(syntax)
		            		syntaxContent += line + "\n";
		            	if(lexical)
		            		lexicalContent += line + "\n";
		            }
		            reader.close();
		        } catch (IOException e) {
		        	// Updates the log
		        	AcideLog.getLog().error(e.getMessage());
		        }
				
				// Updates the rules text area with the syntax content
				_rulesTextArea.setText(syntaxContent);
				
				// Updates the lexical text area with the lexical content
				_categoriesTextArea.setText(lexicalContent);
				
				// If delimiter content is empty
				if(!delimiterContent.equals(""))
					delimiterContent = String.valueOf(delimiterContent.charAt(0));
				
				_delimiterTxt.setText(delimiterContent);

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s200"));
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE grammar configuration window save rules button
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class SaveRulesButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Asks for the file path to the user
			String absolutePath = AcideFileManager.getInstance().askForFile(
					AcideFileOperation.SAVE, AcideFileTarget.FILES,
					AcideFileType.FILE, "", new AcideFileExtensionFilterManager(new String[] { ".txt" },
							AcideLanguageManager.getInstance().getLabels()
							.getString("s270")));

			if (absolutePath != null) {

				// If it does not contains the .txt extension
				if (!absolutePath.endsWith(".txt"))
					// Adds it
					absolutePath += ".txt";
				
				// Creates the file content
				String textContent = "header{\npackage acide.process.parser.grammar;\n}\n";
				textContent += "class ExprLexer extends Lexer;\n";
				textContent += _categoriesTextArea.getText();
				textContent += "\nclass ExprParser extends Parser;\n";
				textContent += "grammar Expr;" + System.lineSeparator() +_rulesTextArea.getText();
				textContent += "\nStatement delimiter;\n" + _delimiterTxt.getText();
				
				// Tries to save the file content
				boolean isSaved = AcideFileManager.getInstance().write(
						absolutePath, textContent);

				// If it was saved successfully
				if (isSaved)
					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s202")
									+ absolutePath);
				else
					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s203"));
			}
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE grammar configuration window load lexicon
	 * button action listener.
	 * 
	 * @version 0.19
	 * @see ActionListener
	 */
	class LoadLexiconButtonAction implements ActionListener{

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				// Creates the file input stream
				FileInputStream fileInputStream = new FileInputStream(((ComboBoxItem)_lexiconCbBox.getSelectedItem()).get_path());

				// Creates the XStream object
				XStream xstream = new XStream(new DomDriver());
				
				// Gets the lexicon configuration from the XML file
				AcideLexiconConfiguration lexiconConfiguration = (AcideLexiconConfiguration)xstream.fromXML(fileInputStream);
				
				// Gets the token manager from the lexicon configuration
				AcideLexiconTokenManager tokens = lexiconConfiguration.getTokenTypeManager();
				
				// Gets the delimiters manager from the lexicon configuration
				AcideLexiconDelimitersManager delimiters =  lexiconConfiguration.getDelimitersManager();
				
				// Parser the token and the delimiter of the lexicon
				TreeMap<String, String> res = new TreeMap<String, String>();
				for(int i = 0; i < tokens.getSize(); i++) {
					AcideLexiconTokenGroup group = tokens.getTokenGroupAt(i);
					for(int j = 0; j < group.getSize(); j++) {
						String token = group.getTokenAt(j).toString();
						if(!res.containsKey(token)) 
							res.put(token, "'" + token + "';");
					}
				}
				
				for(int i = 0; i < delimiters.getSize(); i++) {
					String delimiter = delimiters.getDelimiterAt(i).toString();
					if(!res.containsKey(delimiter))
						res.put(delimiter, "'" + delimiter + "';");
				}
				
				// Order the content
				String text = "";
				for(HashMap.Entry<String, String> entry : res.entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
				    text += String.format("%-15s: \t %s\n", key, value); 
				}
				
				// Set the content to the _categoriesTextArea
				_categoriesTextArea.setText(text);
				
			} catch (FileNotFoundException e) {
				// Updates the log
				AcideLog.getLog().error(e.getMessage());
			}
		}
		
	}

	/**
	 * ACIDE - A Configurable IDE grammar configuration window load categories
	 * button action listener.
	 * 
	 * @version 0.19
	 * @see ActionListener
	 */
	
//	class LoadCategoriesButtonAction implements ActionListener {
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
//		 * ActionEvent)
//		 */
//		@Override
//		public void actionPerformed(ActionEvent actionEvent) {
//
//			// Asks for the file path to the user
//			String absolutePath = AcideFileManager.getInstance().askForFile(
//					AcideFileOperation.OPEN, AcideFileTarget.FILES,
//					AcideFileType.FILE, "", null);
//
//			if (absolutePath != null) {
//
//				// Loads the file content
//				String fileContent = AcideFileManager.getInstance().load(
//						absolutePath);
//
//				// Updates the categories text area
//				_categoriesTextArea.setText(fileContent);
//
//				// Updates the log
//				AcideLog.getLog().info(
//						AcideLanguageManager.getInstance().getLabels()
//								.getString("s201"));
//			}
//		}
//	}

	/**
	 * ACIDE - A Configurable IDE grammar configuration window save categories
	 * button action listener.
	 * 
	 * @version 0.19
	 * @see ActionListener
	 */
//	class SaveCategoriesButtonAction implements ActionListener {
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
//		 * ActionEvent)
//		 */
//		@Override
//		public void actionPerformed(ActionEvent actionEvent) {
//
//			// Asks for the file path to the user
//			String absolutePath = AcideFileManager.getInstance().askForFile(
//					AcideFileOperation.SAVE, AcideFileTarget.FILES,
//					AcideFileType.FILE, "", null);
//
//			if (absolutePath != null) {
//
//				// Tries to save the file content
//				boolean isSaved = AcideFileManager.getInstance().write(
//						absolutePath, _categoriesTextArea.getText());
//
//				// If it was saved
//				if (isSaved)
//					// Updates the log
//					AcideLog.getLog().info(
//							AcideLanguageManager.getInstance().getLabels()
//									.getString("s204")
//									+ absolutePath);
//				else
//					// Updates the log
//					AcideLog.getLog().info(
//							AcideLanguageManager.getInstance().getLabels()
//									.getString("s205"));
//			}
//		}
//	}

	/**
	 * ACIDE - A Configurable IDE grammar configuration window escape key
	 * action.
	 * 
	 * @version 0.11
	 * @see AbstractAction
	 */
	class EscapeKeyAction extends AbstractAction {

		/**
		 * Escape key action serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Closes the window
			closeWindow();
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE grammar configuration window combo box item
	 * class.
	 * 
	 * @version 0.19
	 * 
	 */
	class ComboBoxItem{
		/**
		 * ACIDE - A Configurable IDE grammar configuration window combo box item
		 * name.
		 */
		private String _name;
		/**
		 * ACIDE - A Configurable IDE grammar configuration window combo box item
		 * path.
		 */
		private String _path;
		/**
		 * Creates a new ACIDE - A Configurable IDE grammar configuration window 
		 * combo box item.
		 */
		public ComboBoxItem(String n, String p) {
			_name = n;
			_path = p;
		}
		

		public String get_path() {
			return _path;
		}

		@Override
        public String toString() {
            return _name;
        }
	}
	
	/**
	 * ACIDE - A Configurable IDE grammar configuration window text field limit
	 * class.
	 * 
	 * @version 0.19
	 */
	class JTextFieldLimit extends PlainDocument {
		/**
		 * ACIDE - A Configurable IDE grammar configuration window text field limit
		 */
		  private int limit;

		  JTextFieldLimit(int limit) {
		   super();
		   this.limit = limit;
		   }

		  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		    if (str == null) return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}
	
	/**
	 * Get lexicon for the ACIDE - A Configurable IDE grammar
	 * configuration window JComboBox.
	 */
	private void getAllLexicon() {
		File files = new File(AcideLexiconConfiguration.DEFAULT_PATH);
		for(File file: files.listFiles()) {
			if(file.isFile() && file.getName().contains(".xml")) {
				_lexiconCbBox.addItem(new ComboBoxItem(file.getName(), file.getPath()));
			}
		}
	}
}
