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
package acide.gui.menuBar.configurationMenu.consoleMenu.gui;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import com.jidesoft.swing.AutoCompletionComboBox;

import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.editMenu.gui.AcideReplaceWindow;
import acide.gui.menuBar.editMenu.utils.AcideSearchDirection;
import acide.gui.menuBar.editMenu.utils.AcideSearchEngine;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

/**
 * ACIDE - A Configurable IDE console panel search window.
 * 
 * @version 0.11
 * @see JFrame
 */
public class AcideConsolePanelSearchWindow extends JFrame {

	/**
	 * ACIDE - A Configurable IDE console panel search window class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE panel search window text to search
	 */
	private static String _textToSearch;
	
	/**
	 * ACIDE - A Configurable IDE console panel search window search history
	 */
	private static Vector<String> _list;
	
	/**
	 * ACIDE - A Configurable IDE console panel search window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE console panel search window unique class
	 * instance.
	 */
	private static AcideConsolePanelSearchWindow _instance;
	/**
	 * ACIDE - A Configurable IDE console panel search window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE console panel search window option panel.
	 */
	private JPanel _optionPanel;
	/**
	 * ACIDE - A Configurable IDE search/replace window direction panel.
	 */
	private JPanel _directionPanel;
	/**
	 * ACIDE - A Configurable IDE console panel search window replace label.
	 */
	private JLabel _searchLabel;
	/**
	 * ACIDE - A Configurable IDE search window recent searches label.
	 */
	private JLabel _recentSearchesLabel;
	/**
	 * ACIDE - A Configurable IDE console panel search window replace text
	 * field.
	 */
	private JTextField _searchTextField;
	/**
	 * ACIDE - A Configurable IDE console panel search window case sensitive
	 * check box.
	 */
	private JCheckBox _caseSensitiveCheckBox;
	/**
	 * ACIDE - A Configurable IDE console panel search window regular
	 * expressions check box.
	 */
	private JCheckBox _regularExpressionsCheckBox;
	/**
	 * ACIDE - A Configurable IDE console panel search window complete words
	 * check box.
	 */
	private JCheckBox _completeWordsCheckBox;
	/**
	 * ACIDE - A Configurable IDE search/replace window forward radio button.
	 */
	private JRadioButton _forwardRadioButton;
	/**
	 * ACIDE - A Configurable IDE search/replace window backward radio button.
	 */
	private JRadioButton _backwardRadioButton;
	/**
	 * ACIDE - A Configurable IDE search/replace window both directions radio
	 * button.
	 */
	private JRadioButton _bothDirectionsRadioButton;
	/**
	 * ACIDE - A Configurable IDE search window comboBox for recent
	 * searches.
	 */
	private AutoCompletionComboBox _comboBox;
	
	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window
	 * both directions radio button.
	 * 
	 * @return the ACIDE - A Configurable IDE both directions radio button.
	 */
	public JRadioButton get_bothDirectionsRadioButton() {
		return _bothDirectionsRadioButton;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE console panel search window
	 * both directions radio button.
	 * 
	 * @param _bothDirectionsRadioButton
	 *            new value to set.
	 */
	public void set_bothDirectionsRadioButton(
			JRadioButton _bothDirectionsRadioButton) {
		this._bothDirectionsRadioButton = _bothDirectionsRadioButton;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window
	 * backward direction radio button.
	 * 
	 * @return the ACIDE - A Configurable IDE backward direction radio button.
	 */
	public JRadioButton get_backwardRadioButton() {
		return _backwardRadioButton;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE console panel search window
	 * backward direction radio button.
	 * 
	 * @param _backwardRadioButton
	 *            new value to set.
	 */
	public void set_backwardRadioButton(JRadioButton _backwardRadioButton) {
		this._backwardRadioButton = _backwardRadioButton;
	}
	
	/**
	 * ACIDE - A Configurable IDE search/replace window both backwards directions radio
	 * button.
	 */
	private JRadioButton _bothDirectionsBackwardsRadioButton;

	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window
	 * both directions backwards radio button.
	 * 
	 * @return the ACIDE - A Configurable IDE both directions backwards radio button.
	 */
	public JRadioButton get_bothDirectionsBackwardsRadioButton() {
		return _bothDirectionsBackwardsRadioButton;
	}
	
		/**
		 * Sets a new value to the ACIDE - A Configurable IDE console panel search window
		 * both directions backwards radio button.
		 * 
		 * @param _bothDirectionsBackwardsRadioButton
		 *            new value to set.
		 */
	public void set_bothDirectionsBackwardsRadioButton(
			JRadioButton _bothDirectionsBackwardsRadioButton) {
		this._bothDirectionsBackwardsRadioButton = _bothDirectionsBackwardsRadioButton;
	}

	/**
	 * ACIDE - A Configurable IDE console panel search window search button.
	 */
	private JButton _searchButton;
	/**
	 * ACIDE - A Configurable IDE console panel search window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE console panel search engine
	 */
	private AcideSearchEngine _searchEngine;

	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window unique
	 * class instance.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel search window unique
	 *         class instance.
	 */
	public static AcideConsolePanelSearchWindow getInstance() {
		if (_instance == null)
			_instance = new AcideConsolePanelSearchWindow();
		return _instance;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE console panel search window.
	 */
	public AcideConsolePanelSearchWindow() {

		_searchEngine = AcideSearchEngine.getInstance();
		// Builds the window components
		buildComponents();

		// Adds the components to the window
		addComponents();

		// Sets the listeners of the window components
		setListeners();

		// Sets the window configuration
		setWindowConfiguration();
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console panel search window
	 * components.
	 */
	private void buildComponents() {

		// Builds the search/replace text fields
		buildSearchTextFields();

		// Builds the direction panel
		buildDirectionPanel();

		// Builds the options panel
		buildOptionsPanel();

		// Builds the button panel
		buildButtonPanel();
	}

	/**
	 * Sets the ACIDE - A Configurable IDE console panel search window
	 * configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the search window title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s556"));

		// Sets the window icon image
		setIconImage(ICON.getImage());

		// The window is not resizable
		setResizable(false);

		// Packs the window components
		pack();

		// Always at the front
		setAlwaysOnTop(true);

		// Centers the window
		setLocationRelativeTo(null);
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE console panel search
	 *  window list.
	 * @return the ACIDE - A Configurable IDE console panel search
	 *  window list.
	 */
	public Vector<String> getList() {
		if (_list == null){
			_list = new Vector<String>();
		}
		return _list;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE console panel search
	 * list window.
	 * 
	 * @param _list
	 *            new value to set.
	 */
	public void setList(Vector<String> _list) {
		AcideConsolePanelSearchWindow._list = _list;
	}

	/**
	 * Adds the components to the window.
	 */
	public void addComponents() {

		// Sets the layout
		setLayout(new GridBagLayout());

		// Adds the components to the window with the layout
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 0;

		// Adds the replace label to the window
		add(_searchLabel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 1;

		// Adds the replace text field to the window
		add(_searchTextField, constraints);
		
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		
		//Adds the recent searches label to the window
		add(_recentSearchesLabel, constraints);
		
		constraints.gridy = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		// Adds the recent searches combo box to the window
		add(_comboBox, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridwidth = 1;
		constraints.gridy = 4;

		// Adds the option panel to the window
		add(_optionPanel, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 1;

		// Adds the direction panel to the window
		add(_directionPanel, constraints);

		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 5;

		// Adds the button panel to the window
		add(_buttonPanel, constraints);

		// Validates the changes in the search/replace window
		validate();
	}

	/**
	 * Creates the search labels and text fields.
	 */
	private void buildSearchTextFields() {

		// Creates the search label
		_searchLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s557"), JLabel.CENTER);

		// Creates the search text field
		//_searchTextField = new JSuggestField(this, getList());
		_searchTextField = new JTextField();
		
		_comboBox = new AutoCompletionComboBox(getList());
		
		
		// Creates the recent searches label
		_recentSearchesLabel = new JLabel(AcideLanguageManager.getInstance()
			.getLabels().getString("s2168"), JLabel.CENTER);
	}

	/**
	 * Creates the direction panel and its components.
	 */
	private void buildDirectionPanel() {

		// Creates the direction panel
		_directionPanel = new JPanel(new GridLayout(0, 1));

		// Sets the direction panel border
		_directionPanel.setBorder(BorderFactory.createTitledBorder(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s567"), TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION));

		// Creates the forward radio button
		_forwardRadioButton = new JRadioButton(AcideLanguageManager
				.getInstance().getLabels().getString("s568"), true);

		// Creates the backward radio button
		_backwardRadioButton = new JRadioButton(AcideLanguageManager
				.getInstance().getLabels().getString("s569"), false);

		// Creates the both directions radion button
		_bothDirectionsRadioButton = new JRadioButton(AcideLanguageManager
				.getInstance().getLabels().getString("s570"), false);
		
		// Creates the both directions radion button
		_bothDirectionsBackwardsRadioButton = new JRadioButton(AcideLanguageManager
				.getInstance().getLabels().getString("s570"), false);

		// Adds the forward radio button to the direction panel
		_directionPanel.add(_forwardRadioButton);

		// Adds the backward radio button to the direction panel
		_directionPanel.add(_backwardRadioButton);

		// Adds the both directions radio button to the direction panel
		_directionPanel.add(_bothDirectionsRadioButton);

		// Creates a button group
		ButtonGroup buttonGroup = new ButtonGroup();

		// Adds the forward radio button to the button group
		buttonGroup.add(_forwardRadioButton);

		// Adds the backward radio button to the button group
		buttonGroup.add(_backwardRadioButton);

		// Adds the both directions radio button to the button group
		buttonGroup.add(_bothDirectionsRadioButton);
		
		// Adds the both directions backwards radio button to the button group
		buttonGroup.add(_bothDirectionsBackwardsRadioButton);
	}

	/**
	 * Creates the button panel and its components.
	 */
	private void buildButtonPanel() {

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the search button
		_searchButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s556"));

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s42"));

		// Adds the search button to the button panel
		_buttonPanel.add(_searchButton);

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);
	}

	/**
	 * Creates the options panel and its components.
	 */
	private void buildOptionsPanel() {

		// Creates the option panel
		_optionPanel = new JPanel(new GridLayout(0, 1));

		// Sets the option panel border
		_optionPanel.setBorder(BorderFactory.createTitledBorder(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s559"), TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION));

		// Creates the case sensitive check box
		_caseSensitiveCheckBox = new JCheckBox(AcideLanguageManager
				.getInstance().getLabels().getString("s560"), false);

		// Creates the regular expressions check box
		_regularExpressionsCheckBox = new JCheckBox(AcideLanguageManager
				.getInstance().getLabels().getString("s561"), false);

		// Creates the complete words check box
		_completeWordsCheckBox = new JCheckBox(AcideLanguageManager
				.getInstance().getLabels().getString("s562"), false);

		// Adds the case sensitive check box to the option panel
		_optionPanel.add(_caseSensitiveCheckBox);

		// Adds the regular expressions check box to the option panel
		_optionPanel.add(_regularExpressionsCheckBox);

		// Adds the complete words check box to the option panel
		_optionPanel.add(_completeWordsCheckBox);
	}

	/**
	 * Sets the listeners of the window components.
	 */
	private void setListeners() {

		// Sets the search button action listener
		_searchButton.addActionListener(new SearchButtonListener());

		// When the enter key is pressed the executes the search button action
		_searchButton.registerKeyboardAction(new SearchButtonListener(),
				"EnterKey", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonListener());

		// Sets the window closing listener
		addWindowListener(new AcideWindowClosingListener());

		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
		
		_comboBox.addActionListener(new ActionListener() {
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e) {
				_searchTextField.setText((String)((JComboBox)e.getSource()).getSelectedItem());
			   }
			 });
	}

	/**
	 * Initializes the ACIDE - A Configurable IDE console panel search window
	 * instance.
	 * 
	 * Points it to null, so the next time the method getInstance() is invoked,
	 * it will generate the window.
	 */
	public void initialize() {
		_instance = null;
		_list = getList();
	}

	/**
	 * Initializes the ACIDE - A Configurable IDE console panel search window
	 * variables.
	 */
	public void initializeVariables() {

		// Sets the search engine temporal position as -2
		_searchEngine.setTemporalPosition(-2);

		// Sets the search engine is cycle flag as false
		_searchEngine.setIsCycle(false);
	}

	/**
	 * Sets the window in the front of all the opened windows.
	 * 
	 * @param alwaysOnTop
	 *            true if it set in the front and false in other case.
	 */
	public void bringToFront(boolean alwaysOnTop) {
		setAlwaysOnTop(alwaysOnTop);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window search
	 * button.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel search window search
	 *         button.
	 */
	public JButton getSearchButton() {
		return _searchButton;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window search
	 * text field.
	 * 
	 * @return the the ACIDE - A Configurable IDE console panel search window
	 *         search text field.
	 */
	public JTextField getSearchTextField() {
		return _searchTextField;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window
	 * complete words check box.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel search window
	 *         complete words check box.
	 */
	public JCheckBox getCompleteWordsCheckBox() {
		return _completeWordsCheckBox;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window
	 * regular expressions check box.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel search window
	 *         regular expressions check box.
	 */
	public JCheckBox getRegularExpressionsCheckBox() {
		return _regularExpressionsCheckBox;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel search window case
	 * sensitive check box.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel search window case
	 *         sensitive check box.
	 */
	public JCheckBox getCaseSensitiveCheckBox() {
		return _caseSensitiveCheckBox;
	}

	/**
	 * <p>
	 * Performs the current document search always backwards.
	 * </p>
	 * <p>
	 * The reason why the search is always backwards lies in the fact that the
	 * caret position in the ACIDE - A Configurable IDE console panel is always
	 * after the prompt and it is can not be place before it.
	 * </p>
	 * 
	 * @param searchDirection
	 *            search direction.
	 */
	@SuppressWarnings("incomplete-switch")
	public void search(AcideSearchDirection searchDirection) {

		int initialPosition = -1;
		int resultPosition = -1;

		// Initializes the variables
		initializeVariables();

		// If there is something selected
		if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
				.getSelectedText() != null) {

			switch (searchDirection) {

			case BACKWARD:

				// The initial position is the selection start
				initialPosition = AcideMainWindow.getInstance()
						.getConsolePanel().getTextPane().getSelectionStart();
				break;

			case BOTH:
			case FORWARD:

				// The initial position is the selection end
				initialPosition = AcideMainWindow.getInstance()
						.getConsolePanel().getTextPane().getSelectionEnd();
				break;
			}

		} else
			// Gets the initial position from the caret position of the selected
			// editor
			initialPosition = AcideMainWindow.getInstance().getConsolePanel()
					.getTextPane().getCaretPosition();

		// Performs the backwards search storing the result position
		resultPosition = _searchEngine.search(
				initialPosition, _searchTextField.getText(),
				AcideMainWindow.getInstance().getConsolePanel().getContent(),
				_caseSensitiveCheckBox.isSelected(),
				_regularExpressionsCheckBox.isSelected(),
				_completeWordsCheckBox.isSelected(), searchDirection);

		// If it found something
		if (resultPosition != -1) {

			// If the regular expressions check box is selected
			if (_regularExpressionsCheckBox.isSelected()) {

				// Selects the text in the editor from the regular
				// expression
				AcideMainWindow
						.getInstance()
						.getConsolePanel()
						.selectText(
								resultPosition,
								_searchEngine
										.getRegularExpresion().length());

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s577")
								+ " "
								+ _searchEngine
										.getRegularExpresion()
								+ " "
								+ AcideLanguageManager.getInstance()
										.getLabels().getString("s574"));

				// Updates the status message in the status bar
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance().getLabels()
										.getString("s577")
										+ " "
										+ _searchEngine
												.getRegularExpresion()
										+ " "
										+ AcideLanguageManager.getInstance()
												.getLabels().getString("s574"));
			} else {

				// Selects the text in the console panel text pane
				AcideMainWindow
						.getInstance()
						.getConsolePanel()
						.selectText(resultPosition,
								_searchTextField.getText().length());

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s583")
								+ _searchTextField.getText()
								+ AcideLanguageManager.getInstance()
										.getLabels().getString("s574"));

				// Updates the status message in the status bar
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance().getLabels()
										.getString("s583")
										+ " "
										+ _searchTextField.getText()
										+ " "
										+ AcideLanguageManager.getInstance()
												.getLabels().getString("s574"));
			}
		} else {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s573"));

			// Brings the ACIDE - A Configurable IDE console panel search window
			// background
			AcideConsolePanelSearchWindow.getInstance().bringToFront(false);

			// Displays a warning message
			JOptionPane.showMessageDialog(null, AcideLanguageManager
					.getInstance().getLabels().getString("s573"));

			// Brings the ACIDE - A Configurable IDE console panel search window
			// foreground
			AcideConsolePanelSearchWindow.getInstance().bringToFront(true);

			// Updates the status message in the status bar
			AcideMainWindow
					.getInstance()
					.getStatusBar()
					.setStatusMessage(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s573"));
		}
	}

	/**
	 * ACIDE - A Configurable IDE console panel search window search button
	 * listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class SearchButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			// Puts the wait cursor
			AcideConsolePanelSearchWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			_textToSearch = _searchTextField.getText();
			
			if (!getList().contains(_textToSearch)){
				getList().add(_textToSearch);
			}
			
			_textToSearch = _textToSearch.replaceAll("\\^p","\n");
			_textToSearch = _textToSearch.replaceAll("\\^t", "\t");

			// Gets the search direction
			AcideSearchDirection searchDirection = AcideSearchDirection.FORWARD;

			// If the forward radio button is selected
			if (_forwardRadioButton.isSelected())

				// The search direction is forwards
				searchDirection = AcideSearchDirection.FORWARD;

			// If the backward radio button is selected
			if (_backwardRadioButton.isSelected())

				// The search direction is backwards
				searchDirection = AcideSearchDirection.BACKWARD;

			// If the both directions radio button is selected
			if (_bothDirectionsRadioButton.isSelected())

				// The search direction is both directions
				searchDirection = AcideSearchDirection.BOTH;
			
			// If the both directions backwards radio button is selected
			if (_bothDirectionsBackwardsRadioButton.isSelected())

				// The search direction is both directions
				searchDirection = AcideSearchDirection.BOTHBACK;

			// If the complete words is selected
			if (_completeWordsCheckBox.isSelected())

				// Regular expressions are disabled
				_regularExpressionsCheckBox.setSelected(false);

			// If the search text field is empty
			if (_textToSearch.equals("")) {

				// Brings the ACIDE - A Configurable IDE console panel search
				// window background
				AcideConsolePanelSearchWindow.getInstance().bringToFront(false);

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s585"));

				// Brings the ACIDE - A Configurable IDE console panel search
				// window foreground
				AcideConsolePanelSearchWindow.getInstance().bringToFront(true);

				// Updates the status message in the status bar
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance().getLabels()
										.getString("s585"));

			} else {
				
				// Performs the search
				//search(searchDirection);
				int resultPosition = -1;

				// Initializes the variables
				//initializeVariables();

				// If there is something selected
				if (AcideMainWindow.getInstance().getConsolePanel().getTextPane()
						.getSelectedText() != null) {

					switch (searchDirection) {

					case BACKWARD:
					case BOTHBACK:

						// The initial position is the selection start
						resultPosition = AcideMainWindow.getInstance()
								.getConsolePanel().getTextPane().getSelectionStart();
						break;

					case BOTH:
					case FORWARD:

						// The initial position is the selection end
						resultPosition = AcideMainWindow.getInstance()
								.getConsolePanel().getTextPane().getSelectionEnd();
						break;
					}

				} else
					// Gets the initial position from the caret position of the selected
					// editor
					resultPosition = AcideMainWindow.getInstance().getConsolePanel()
							.getTextPane().getCaretPosition();

				// Performs the backwards search storing the result position
				resultPosition = _searchEngine.search(
						resultPosition, _textToSearch,
						AcideMainWindow.getInstance().getConsolePanel().getContent(),
						_caseSensitiveCheckBox.isSelected(),
						_regularExpressionsCheckBox.isSelected(),
						_completeWordsCheckBox.isSelected(), searchDirection);
				
				// If the regular expression is wrong
				if (resultPosition == -2){
					
					// Puts the default cursor
					AcideReplaceWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					AcideMainWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					return;
				}

				// If it found something
				if (resultPosition != -1) {

					// If the regular expressions check box is selected
					if (_regularExpressionsCheckBox.isSelected()) {

						// Selects the text in the editor from the regular
						// expression
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.selectText(
										resultPosition,
										_searchEngine
												.getRegularExpresion().length());

						// Updates the log
						AcideLog.getLog().info(
								AcideLanguageManager.getInstance().getLabels()
										.getString("s577")
										+ " "
										+ _searchEngine
												.getRegularExpresion()
										+ " "
										+ AcideLanguageManager.getInstance()
												.getLabels().getString("s574"));

						// Updates the status message in the status bar
						AcideMainWindow
								.getInstance()
								.getStatusBar()
								.setStatusMessage(
										AcideLanguageManager.getInstance().getLabels()
												.getString("s577")
												+ " "
												+ _searchEngine
														.getRegularExpresion()
												+ " "
												+ AcideLanguageManager.getInstance()
														.getLabels().getString("s574"));
					} else {

						// Selects the text in the console panel text pane
						AcideMainWindow
								.getInstance()
								.getConsolePanel()
								.selectText(resultPosition,
										_textToSearch.length());

						// Updates the log
						AcideLog.getLog().info(
								AcideLanguageManager.getInstance().getLabels()
										.getString("s583")
										+ _textToSearch
										+ AcideLanguageManager.getInstance()
												.getLabels().getString("s574"));

						// Updates the status message in the status bar
						AcideMainWindow
								.getInstance()
								.getStatusBar()
								.setStatusMessage(
										AcideLanguageManager.getInstance().getLabels()
												.getString("s583")
												+ " "
												+ _textToSearch
												+ " "
												+ AcideLanguageManager.getInstance()
														.getLabels().getString("s574"));
					}
				} else {

					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s573"));

					// Brings the ACIDE - A Configurable IDE console panel search window
					// background
					AcideConsolePanelSearchWindow.getInstance().bringToFront(false);

					// Displays a warning message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s573"));

					// Brings the ACIDE - A Configurable IDE console panel search window
					// foreground
					AcideConsolePanelSearchWindow.getInstance().bringToFront(true);

					// Updates the status message in the status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance().getLabels()
											.getString("s573"));
				}

				// Puts the default cursor
				AcideConsolePanelSearchWindow.getInstance().setCursor(
						Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				AcideMainWindow.getInstance().setCursor(
						Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE console panel search window cancel button
	 * listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class CancelButtonListener implements ActionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			// Puts the default cursor
			AcideConsolePanelSearchWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Closes the window
			dispose();
		}
	}
	
	/**
	 * ACIDE - A Configurable IDE console panel search window escape key action.
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
			
			// Puts the default cursor
			AcideConsolePanelSearchWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Closes the window
			dispose();
		}
	}

}
