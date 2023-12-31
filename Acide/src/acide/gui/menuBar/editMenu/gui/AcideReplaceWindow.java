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
package acide.gui.menuBar.editMenu.gui;

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
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.editMenu.utils.AcideSearchDirection;
import acide.gui.menuBar.editMenu.utils.AcideSearchEngine;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;

import com.jidesoft.swing.AutoCompletionComboBox;

/**
 * ACIDE - A Configurable IDE replace window.
 * 
 * @version 0.11
 * @see JFrame
 */
@SuppressWarnings("rawtypes")
public class AcideReplaceWindow extends JFrame {

	/**
	 * The dialog with the find of the search has been displayed
	 */
	private boolean _finishedF3;
	
	private int _cont;
	
	private JProgressBar _barDo;
	
	private static int _valorBar;
	
	public JProgressBar getBarDo() {
		return _barDo;
	}

	public void setBarDo(JProgressBar barDo) {
		this._barDo = barDo;
	}

	private boolean _replaceAll;
	
	/**
	 * ACIDE - A Configurable IDE replace window search history
	 */
	private static Vector<String> _list;
	
	/**
	 * ACIDE - A Configurable IDE replace window replace history
	 */
	private static Vector<String> _listReplace;
	/**
	 * ACIDE - A Configurable IDE replace window text to search
	 */
	private static String _textToSearch;
	/**
	 * ACIDE - A Configurable IDE replace window text to replace
	 */
	private static String _textToReplace;
	
	/**
	 * ACIDE - A Configurable IDE replace window serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE replace window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE replace window unique class instance.
	 */
	private static AcideReplaceWindow _instance;
	/**
	 * ACIDE - A Configurable IDE replace window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE replace window direction panel.
	 */
	private JPanel _directionPanel;
	/**
	 * ACIDE - A Configurable IDE replace window option panel.
	 */
	private JPanel _optionPanel;
	/**
	 * ACIDE - A Configurable IDE replace window scope panel.
	 */
	private JPanel _scopePanel;
	/**
	 * ACIDE - A Configurable IDE replace window search label.
	 */
	private JLabel _searchLabel;
	/**
	 * ACIDE - A Configurable IDE replace window recent searchs label.
	 */
	private JLabel _recentSearchesLabel;
	/**
	 * ACIDE - A Configurable IDE search window recent replaces label.
	 */
	private JLabel _recentReplacesLabel;
	/**
	 * ACIDE - A Configurable IDE replace window search text field.
	 */
	private JTextField _searchTextField;
	/**
	 * ACIDE - A Configurable IDE replace window replace label.
	 */
	private JLabel _replaceLabel;
	/**
	 * ACIDE - A Configurable IDE replace window replace text field.
	 */
	private JTextField _replaceTextField;
	/**
	 * ACIDE - A Configurable IDE replace window case sensitive check box.
	 */
	private JCheckBox _caseSensitiveCheckBox;
	/**
	 * ACIDE - A Configurable IDE replace window regular expressions check box.
	 */
	private JCheckBox _regularExpressionsCheckBox;
	/**
	 * ACIDE - A Configurable IDE replace window complete words check box.
	 */
	private JCheckBox _completeWordsCheckBox;
	/**
	 * ACIDE - A Configurable IDE replace window respect capitalization check
	 * box.
	 */
	private JCheckBox _respectCapitalizationCheckBox;
	/**
	 * ACIDE - A Configurable IDE replace window forward radio button.
	 */
	private JRadioButton _forwardRadioButton;
	/**
	 * ACIDE - A Configurable IDE replace window backward radio button.
	 */
	private JRadioButton _backwardRadioButton;
	/**
	 * ACIDE - A Configurable IDE replace window both directions radio button.
	 */
	private JRadioButton _bothDirectionsRadioButton;
	/**
	 * ACIDE - A Configurable IDE replace window current document radio button.
	 */
	private JRadioButton _currentDocumentRadioButton;
	/**
	 * ACIDE - A Configurable IDE replace window all documents radio button.
	 */
	private JRadioButton _allDocumentsRadioButton;
	/**
	 * ACIDE - A Configurable IDE replace window selected text radio button.
	 */
	private JRadioButton _selectedTextRadioButton;
	/**
	 * ACIDE - A Configurable IDE replace window search button.
	 */
	private JButton _searchButton;
	/**
	 * ACIDE - A Configurable IDE replace window replace button.
	 */
	private JButton _replaceButton;
	/**
	 * ACIDE - A Configurable IDE replace window replace all button.
	 */
	private JButton _replaceAllButton;
	/**
	 * ACIDE - A Configurable IDE replace window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE replace window search result position.
	 */
	private int _searchResultPosition = -1;
	/**
	 * ACIDE - A Configurable IDE replace window initial position for the
	 * selected text search.
	 */
	private int _initialPosition = 0;
	/**
	 * ACIDE - A Configurable IDE replace window final position for the selected
	 * text search.
	 */
	private int _finalPosition = 0;
	/**
	 * ACIDE - A Configurable IDE replace window selected text for the selected
	 * text search.
	 */
	private String _selectedText = null;
	/**
	 * ACIDE - A Configurable IDE replace window all documents counter.
	 */
	private int _allDocumentsCounter = 0;
	/**
	 * ACIDE - A Configurable IDE replace window current position.
	 */
	private int _currentPosition = -1;
	/**
	 * ACIDE - A Configurable IDE replace window all documents current index.
	 */
	private static int _allDocumentsCurrentIndex = -1;
	/**
	 * ACIDE - A Configurable IDE replace window is end flag.
	 */
	private static boolean _isEnd = false;
	/**
	 * ACIDE - A Configurable IDE replace window is cycle flag.
	 */
	private static boolean _isCycle = false;
	/**
	 * ACIDE - A Configurable IDE replace window is first flag.
	 */
	private static boolean _isFirst = true;
	/**
	 * ACIDE - A Configurable IDE replace window current document index.
	 */
	private int _currentDocumentIndex = -1;
	/**
	 * ACIDE - A Configurable IDE replace window is first replacement flag.
	 */
	private static boolean _isFirstReplacement = true;
	/**
	 * ACIDE - A Configurable IDE replace window original position for the
	 * replacements.
	 */
	private int _originalPosition = -1;
	/**
	 * ACIDE - A Configurable IDE replace window search engine.
	 */
	private AcideSearchEngine _searchEngine = AcideSearchEngine.getInstance();
	
	/**
	 * ACIDE - A Configurable IDE replace window comboBox for recent
	 * searchs.
	 */
	private AutoCompletionComboBox _comboBox;
	/**
	 * ACIDE - A Configurable IDE replace window comboBox for recent
	 * searchs.
	 */
	private AutoCompletionComboBox _comboBoxReplace;
	/**
	 * ACIDE - A Configurable IDE replace window comboBox for special
	 * searchs.
	 */
	private JComboBox _comboBoxSpecialSearch;
	/**
	 * ACIDE - A Configurable IDE replace window comboBox for special
	 * replacements.
	 */
	private JComboBox _comboBoxSpecialReplace;
	/**
	 * <p>
	 * ACIDE - A Configurable IDE replace window replace text.
	 * </p>
	 * <p>
	 * In order to replace the text, the text used for doing it can be parsed in
	 * order to respect the capitalization in beforehand.
	 * </p>
	 */
	private String _replaceText = "";

	/**
	 * Returns the ACIDE - A Configurable IDE replace window unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window unique class
	 *         instance.
	 */
	public static AcideReplaceWindow getInstance() {
		if (_instance == null)
			_instance = new AcideReplaceWindow();
		return _instance;
	}
	
	public void setValueBar(int i){
		_barDo.setValue(i);
	}
	
	public void setProgress(int i){
		//setValueBar(i); 
		_cont = i;
		//new Thread(new thread1()).start(); 
	}

	/**
	 * Initializes the ACIDE - A Configurable IDE replace window.
	 */
	public void initialize() {
		_instance = null;
		_list = getList();
		_listReplace = getListReplace();
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE replace window.
	 */
	public AcideReplaceWindow() {

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
	 * Builds the ACIDE - A Configurable IDE search/replace window components.
	 */
	private void buildComponents() {

		// Builds the search/replace text fields
		buildSearchReplaceTextFields();

		// Builds the direction panel
		buildDirectionPanel();

		// Builds the options panel
		buildOptionsPanel();

		// Builds the scope panel
		buildScopePanel();
		
		_barDo = new JProgressBar(0, 100);
		
		String[] comboBoxStrings = new String[3];
		
		comboBoxStrings[0] = AcideLanguageManager.getInstance().getLabels().getString("s2216");
		
		comboBoxStrings[1] = AcideLanguageManager.getInstance().getLabels().getString("s2217");
		
		comboBoxStrings[2] = AcideLanguageManager.getInstance().getLabels().getString("s2218");
		
		_comboBoxSpecialSearch = new JComboBox(comboBoxStrings);
		
		_comboBoxSpecialSearch.setSelectedIndex(0);
		
		_comboBoxSpecialReplace = new JComboBox(comboBoxStrings);
		
		_comboBoxSpecialReplace.setSelectedIndex(0);
	

		// Builds the button panel
		buildButtonPanel();
	}

	/**
	 * Sets the ACIDE - A Configurable IDE search/replace window configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the search window title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s572"));

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
	
	public Vector<String> getList() {
		if (_list == null){
			_list = new Vector<String>();
		}
		return _list;
	}

	public void setList(Vector<String> _list) {
		AcideReplaceWindow._list = _list;
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
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;

		// Adds the replace label to the window
		add(_searchLabel, constraints);
		
//		constraints.gridx = 1;
//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		constraints.anchor = GridBagConstraints.EAST;
//		add(_comboBoxSpecialSearch, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridwidth = 2;

		// Adds the replace text field to the window
		add(_searchTextField, constraints);
		
		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.NONE;
		
		//Adds the recent searchs label to the window
		add(_recentSearchesLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;
		add(_comboBoxSpecialSearch, constraints);
		
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 3;
		
		// Adds the recent searchs combo box to the window
		add(_comboBox, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.gridy = 4;
		constraints.gridwidth = 1;

		// Adds the replaced label to the window
		add(_replaceLabel, constraints);
		
//		constraints.gridx = 1;
//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		constraints.anchor = GridBagConstraints.EAST;
//		add(_comboBoxSpecialReplace, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.gridy = 5;

		// Adds the replaced text field to the window
		add(_replaceTextField, constraints);
		
		constraints.gridy = 6;
		constraints.fill = GridBagConstraints.NONE;
		
		//Adds the recent searchs label to the window
		add(_recentReplacesLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;
		add(_comboBoxSpecialReplace, constraints);
		
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 7;
		
		// Adds the recent searchs combo box to the window
		add(_comboBoxReplace, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 8;
		constraints.gridwidth = 2;

		// Adds the option panel to the window
		add(_optionPanel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridy = 9;
		constraints.gridwidth = 1;

		// Adds the scope panel to the window
		add(_scopePanel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = 9;

		// Adds the direction panel to the window
		add(_directionPanel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 10;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.WEST;
		
		add(_barDo, constraints);

		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridx = 0;
		constraints.gridy = 11;
		constraints.gridwidth = 2;

		// Adds the button panel to the window
		add(_buttonPanel, constraints);

		// Validates the changes in the search/replace window
		repaint();
	}

	/**
	 * Creates the search/replace labels and text fields.
	 */
	private void buildSearchReplaceTextFields() {

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
		
		// Creates the replace label
		_replaceLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s558"), JLabel.CENTER);

		// Creates the replace text field
		//_replaceTextField = new JSuggestField(this, getListReplace());
		_replaceTextField = new JTextField();
		
		_comboBoxReplace = new AutoCompletionComboBox(getListReplace());
		
		// Creates the recent replacements label
		_recentReplacesLabel = new JLabel(AcideLanguageManager.getInstance()
			.getLabels().getString("s2168"), JLabel.CENTER);
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

		// Creates the replace button
		_replaceButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s572"));

		// Enables it
		_replaceButton.setEnabled(true);

		// Creates the replace all button
		_replaceAllButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s571"));

		// Enables it
		_replaceAllButton.setEnabled(true);

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s42"));

		// Adds the search button to the button panel
		_buttonPanel.add(_searchButton);

		// Adds the replace button to the button panel
		_buttonPanel.add(_replaceButton);

		// Adds the replace all button to the button panel
		_buttonPanel.add(_replaceAllButton);

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);
	}

	/**
	 * Creates the scope panel and its components.
	 */
	private void buildScopePanel() {

		// Creates the scope panel
		_scopePanel = new JPanel(new GridLayout(0, 1));

		// Sets the scope panel border
		_scopePanel.setBorder(BorderFactory.createTitledBorder(null,
				AcideLanguageManager.getInstance().getLabels()
						.getString("s563"), TitledBorder.LEADING,
				TitledBorder.DEFAULT_POSITION));

		// Creates the current document radio button
		_currentDocumentRadioButton = new JRadioButton(AcideLanguageManager
				.getInstance().getLabels().getString("s565"), true);

		// Creates the all documents radio button
		_allDocumentsRadioButton = new JRadioButton(AcideLanguageManager
				.getInstance().getLabels().getString("s566"), false);

		// Creates the selected text radio button
		_selectedTextRadioButton = new JRadioButton(AcideLanguageManager
				.getInstance().getLabels().getString("s564"), false);

		// Adds the selected text radio button to the scope panel
		_scopePanel.add(_selectedTextRadioButton);

		// Adds the current document radio button to the scope panel
		_scopePanel.add(_currentDocumentRadioButton);

		// Adds the all documents radio button to the scope panel
		_scopePanel.add(_allDocumentsRadioButton);

		// Creates a button group
		ButtonGroup buttonGroup = new ButtonGroup();

		// Adds the selected text radio button to the button group
		buttonGroup.add(_selectedTextRadioButton);

		// Adds the current document radio button to the button group
		buttonGroup.add(_currentDocumentRadioButton);

		// Adds the all documents radio button to the button group
		buttonGroup.add(_allDocumentsRadioButton);
	}

	/**
	 * Creates the options panel and its components.
	 */
	private void buildOptionsPanel() {

		// Creates the option panel
		_optionPanel = new JPanel(new GridBagLayout());

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

		// Creates the respect capitalization check box
		_respectCapitalizationCheckBox = new JCheckBox(AcideLanguageManager
				.getInstance().getLabels().getString("s1096"), false);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;

		// Adds the case sensitive check box to the option panel
		_optionPanel.add(_caseSensitiveCheckBox, constraints);

		constraints.gridy = 1;

		// Adds the regular expressions check box to the option panel
		_optionPanel.add(_regularExpressionsCheckBox, constraints);

		constraints.gridy = 2;

		// Adds the complete words check box to the option panel
		_optionPanel.add(_completeWordsCheckBox, constraints);

		constraints.gridy = 3;

		// Adds the respect capitalization check box to the option panel
		//_optionPanel.add(_respectCapitalizationCheckBox, constraints);
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
	}

	/**
	 * Sets the listeners of the window components.
	 */
	private void setListeners() {

		// Sets the search button action listener
		_searchButton.addActionListener(new SearchButtonAction());

		// When the enter key is pressed the executes the search button action
		_searchTextField.registerKeyboardAction(new SearchButtonAction(),
				"EnterKey", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		// Sets the replace button action listener
		_replaceButton.addActionListener(new ReplaceButtonAction());

		// Sets the replace all button action listener
		_replaceAllButton.addActionListener(new ReplaceAllButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());

		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
				"EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
		
		_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_searchTextField.setText((String)((JComboBox)e.getSource()).getSelectedItem());
			   }
			 });
		
		_comboBoxReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_replaceTextField.setText((String)((JComboBox)e.getSource()).getSelectedItem());
			   }
			 });
		
		_comboBoxSpecialSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String aux = (String)((JComboBox) e.getSource()).getSelectedItem();
				String special = AcideLanguageManager
						.getInstance().getLabels().getString("s2216");
				String t = AcideLanguageManager
						.getInstance().getLabels().getString("s2217");
				String p = AcideLanguageManager
						.getInstance().getLabels().getString("s2218");
				if (!aux.equals(special)){
					if (aux.equals(t)){
						_searchTextField.setText(_searchTextField.getText() + "^t");
						_searchTextField.requestFocus();
					}
					if (aux.equals(p)){
						_searchTextField.setText(_searchTextField.getText() + "^p");
						_searchTextField.requestFocus();
					}
					_comboBoxSpecialSearch.setSelectedIndex(0);
				}
					
			}
		});
		
		_comboBoxSpecialReplace.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String aux = (String)((JComboBox) e.getSource()).getSelectedItem();
				String special = AcideLanguageManager
						.getInstance().getLabels().getString("s2216");
				String t = AcideLanguageManager
						.getInstance().getLabels().getString("s2217");
				String p = AcideLanguageManager
						.getInstance().getLabels().getString("s2218");
				if (!aux.equals(special)){
					if (aux.equals(t)){
						_replaceTextField.setText(_replaceTextField.getText() + "^t");
						_replaceTextField.requestFocus();
					}
					if (aux.equals(p)){
						_replaceTextField.setText(_replaceTextField.getText() + "^p");
						_replaceTextField.requestFocus();
					}
					_comboBoxSpecialReplace.setSelectedIndex(0);
				}
					
			}
		});
	}

	/**
	 * Checks if a string given as a parameter is capitalized. A string is
	 * capitalized if its first letter is in upper case and the rest are in
	 * lower case.
	 * 
	 * @param string
	 *            string to capitalize.
	 * 
	 * @return returns true if the string given as a parameter is capitalized
	 *         and false in other case.
	 */
	public boolean isCapitalized(String string) {

		return (Character.isUpperCase(string.charAt(0)) && isLowerCase(string
				.substring(1)));
	}

	/**
	 * Checks if a string given as a parameter contains all its letters in upper
	 * case, including all the separate words in it.
	 * 
	 * @param string
	 *            string to analyze.
	 * 
	 * @return returns true if a string given as a parameter contains all its
	 *         letters in upper case and false in other case.
	 */
	public boolean isUpperCase(String string) {
		String[] words = string.split(" ");

		for (int i = 0; i < words.length; i++) {

			for (int j = 0; j < words[i].length(); j++) {
				if (Character.isLowerCase(words[i].charAt(j))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if a string given as a parameter contains all its letters in lower
	 * case, including all the separate words in it.
	 * 
	 * @param string
	 *            string to analyze.
	 * 
	 * @return returns true if a string given as a parameter contains all its
	 *         letters in lower case and false in other case.
	 */
	public boolean isLowerCase(String string) {

		String[] words = string.split(" ");

		for (int i = 0; i < words.length; i++) {

			for (int j = 0; j < words[i].length(); j++) {
				if (Character.isUpperCase(words[i].charAt(j))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the string to use for the replacements in order to respect the
	 * capitalization.
	 * 
	 * @param selectedText
	 *            selected text to be replaced.
	 * 
	 * @return the string to use for the replacements in order to respect the
	 *         capitalization.
	 */
	private String respectCapitalization(String selectedText) {

		// Gets the separate words from the original text to be replaced
		String[] replacedWords = selectedText.split(" ");

		// Gets the separate words to replace
		String[] replaceWords = selectedText.split(" ");

		// Creates the new string with the text to replace
		for (int index = 0; index < replaceWords.length; index++) {
			replaceWords[index] = replaceWords[index].replaceAll(
					replacedWords[index], _textToReplace);
		}

		// Creates the new string
		String newString = "";

		for (int index = 0; index < replacedWords.length; index++) {

			if (isLowerCase(replacedWords[index])) {

				// Puts it in lower case
				replaceWords[index] = replaceWords[index].toLowerCase();
			}
			if (isUpperCase(replacedWords[index])) {

				// Puts it in upper case
				replaceWords[index] = replaceWords[index].toUpperCase();
			}
			if (isCapitalized(replacedWords[index])) {

				// Capitalizes it
				replaceWords[index] = capitalize(replaceWords[index]);
			}
		}

		// Builds the new string
		for (int index = 0; index < replaceWords.length; index++) {

			if (index != replaceWords.length - 1)
				newString = newString + replaceWords[index] + " ";
			else
				newString = newString + replaceWords[index];
		}

		// In other case the text to replace is the original
		return newString;
	}

	/**
	 * Capitalizes a string given as a parameter.
	 * 
	 * @param string
	 *            string to capitalize.
	 * 
	 * @return the capitalized string given as a parameter.
	 */
	private String capitalize(String string) {

		if (string.length() == 0)
			return string;

		// Separates the string into different words
		String[] words = string.split(" ");

		for (int index = 0; index < words.length; index++) {

			words[index] = words[index].substring(0, 1).toUpperCase()
					+ words[index].substring(1).toLowerCase();
		}

		String finalString = "";

		// Compounds the final string
		for (int index = 0; index < words.length; index++) {

			if (index != words.length - 1)
				finalString = finalString + words[index] + " ";
			else
				finalString = finalString + words[index];
		}

		return finalString;
	}

	/**
	 * Counts the number of times a substring occurs in a provided string.
	 * 
	 * @param source
	 *            The <code>String</code> object that will be searched in.
	 * @param substring
	 *            The string whose occurrences will we counted.
	 * @param matchCase
	 *            A <code>boolean</code> indicating if the match is going to be
	 *            performed in a case-sensitive manner or not.
	 * @return An <code>integer</code> value containing the number of
	 *         occurrences of the substring.
	 * @since 1.0
	 */
	public static int countMatches(String source, String substring,
			boolean matchCase) {
		if (null == source) {
			return 0;
		}

		if (null == substring) {
			return 0;
		}

		int current_index = 0;
		int substring_index = 0;
		int count = 0;

		if (!matchCase) {
			source = source.toLowerCase();
			substring = substring.toLowerCase();
		}

		while (current_index < source.length() - 1) {
			substring_index = source.indexOf(substring, current_index);

			if (-1 == substring_index) {
				break;
			} else {
				current_index = substring_index + substring.length();
				count++;
			}
		}

		return count;
	}
	
	public boolean isAcabadoF3() {
		return _finishedF3;
	}

	public void setAcabadoF3(boolean acabadoF3) {
		this._finishedF3 = acabadoF3;
	}
	

	public Vector<String> getListReplace() {
		if (_listReplace == null){
			_listReplace = new Vector<String>();
		}
		return _listReplace;
	}

	public void setListReplace(Vector<String> _listReplace) {
		AcideReplaceWindow._listReplace = _listReplace;
	}

	/**
	 * Initializes the ACIDE - A Configurable IDE replace window variables.
	 */
	public void initializeVariables() {

		// Sets the current position as -2
		_currentPosition = -2;

		// Sets the is cycle as false
		_isCycle = false;

		// Sets the is end as false
		_isEnd = false;

		// Sets search engine temporal position as -2
		_searchEngine.setTemporalPosition(-2);

		// Sets the search engine is cycle as false
		_searchEngine.setIsCycle(false);

		// Sets the is cycle as false
		_isCycle = false;

		// Sets the selected text as null
		_selectedText = null;

		// Sets the is first as true
		_isFirst = true;

		// Sets the is first replacement as true
		_isFirstReplacement = true;
		
		_valorBar = 0;
				
		_finishedF3 = false;
		_replaceAll = false;
		setProgress(0);
		Runnable runnable = new thread1();
		Thread hilo = new Thread(runnable);
		hilo.start();
		
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window search button.
	 * 
	 * @return the ACIDE - A Configurable IDE search window search button.
	 */
	public JButton getSearchButton() {
		return _searchButton;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window is cycle flag.
	 * 
	 * @return the ACIDE - A Configurable IDE search window is cycle flag.
	 */
	public boolean getIsCycle() {
		return _isCycle;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE search window is cycle
	 * flag.
	 * 
	 * @param isCycle
	 *            new value to set.
	 */
	public void setIsCycle(boolean isCycle) {
		_isCycle = isCycle;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window is end flag.
	 * 
	 * @return the ACIDE - A Configurable IDE search window is end flag.
	 */
	public boolean isEnd() {
		return _isEnd;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE search window is end
	 * flag.
	 * 
	 * @param isEnd
	 *            new value to set.
	 */
	public void setIsEnd(boolean isEnd) {
		_isEnd = isEnd;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window current position.
	 * 
	 * @return the ACIDE - A Configurable IDE search window current position.
	 */
	public int getCurrentPosition() {
		return _currentPosition;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE search window current
	 * position.
	 * 
	 * @param currentPosition
	 *            new value to set.
	 */
	public void setCurrentPosition(int currentPosition) {
		_currentPosition = currentPosition;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window current document
	 * radio button.
	 * 
	 * @return the ACIDE - A Configurable IDE search window current document
	 *         radio button.
	 */
	public JRadioButton getCurrentDocumentRadioButton() {
		return _currentDocumentRadioButton;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window both directions
	 * radio button.
	 * 
	 * @return the ACIDE - A Configurable IDE search window both directions
	 *         radio button.
	 */
	public JRadioButton getBothDirectionsRadioButton() {
		return _bothDirectionsRadioButton;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE search window selected text radio
	 * button.
	 * 
	 * @return the ACIDE - A Configurable IDE search window selected text radio
	 *         button.
	 */
	public JRadioButton getSelectedTextRadioButton() {
		return _selectedTextRadioButton;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE search window is first
	 * flag.
	 * 
	 * @param isFirst
	 *            new value to set.
	 */
	public static void setIsFirst(boolean isFirst) {
		_isFirst = isFirst;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window search text field.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window search text field.
	 */
	public JTextField getSearchTextField() {
		return _searchTextField;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window selected text.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window selected text.
	 */
	public String getSelectedText() {
		return _selectedText;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE replace window
	 * selected text.
	 * 
	 * @param selectedText
	 *            new value to set.
	 */
	public void setSelectedText(String selectedText) {
		_selectedText = selectedText;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window is first
	 * replacement flag.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window is first
	 *         replacement flag.
	 */
	public static boolean getIsFirstReplacement() {
		return _isFirstReplacement;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE replace window is
	 * first replacement flag.
	 * 
	 * @param isFirstReplacement
	 *            new value to set.
	 */
	public static void setIsFirstReplacement(boolean isFirstReplacement) {
		_isFirstReplacement = isFirstReplacement;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window forward radio
	 * button.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window forward radio
	 *         button.
	 */
	public JRadioButton getForwardRadioButton() {
		return _forwardRadioButton;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window final position.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window final position.
	 */
	public int getFinalPosition() {
		return _finalPosition;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE replace window final
	 * position.
	 * 
	 * @param finalPosition
	 *            new value to set.
	 */
	public void setFinalPosition(int finalPosition) {
		_finalPosition = finalPosition;
	}

	/**
	 * Sets the ACIDE - A Configurable IDE replace window in the foreground or
	 * to the background.
	 * 
	 * @param isOnTop
	 *            new value to set.
	 */
	public void setOnTop(boolean isOnTop) {
		setAlwaysOnTop(isOnTop);
	}

	/**
	 * Returns the ACIDE - A Configurable IDE replace window search engine.
	 * 
	 * @return the ACIDE - A Configurable IDE replace window search engine.
	 */
	public AcideSearchEngine getSearchEngine() {
		return _searchEngine;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE replace window search
	 * engine.
	 * 
	 * @param searchEngine
	 *            new value to set.
	 */
	public void setSearchEngine(AcideSearchEngine searchEngine) {
		_searchEngine = searchEngine;
	}
	
	public void searchAction(){

		_textToSearch = _searchTextField.getText();
		
		if (!getList().contains(_textToSearch)){
			getList().add(_textToSearch);
		}
		
		_textToSearch = _textToSearch.replaceAll("\\^p","\n");
		_textToSearch = _textToSearch.replaceAll("\\^t", "\t");
		
		// It is not the first replacement
		_isFirstReplacement = false;

		// Selects the search direction
		AcideSearchDirection direction = AcideSearchDirection.FORWARD;
		if (_forwardRadioButton.isSelected())
			direction = AcideSearchDirection.FORWARD;
		if (_backwardRadioButton.isSelected())
			direction = AcideSearchDirection.BACKWARD;
		if (_bothDirectionsRadioButton.isSelected())
			direction = AcideSearchDirection.BOTH;

		// If the complete words check words is selected
		if (_completeWordsCheckBox.isSelected())

			// The regular expressions check box is not selected
			_regularExpressionsCheckBox.setSelected(false);

		// If the search text field is empty
		if (_textToSearch.equals("")) {

			AcideReplaceWindow.getInstance().setOnTop(false);

			// Displays a message
			JOptionPane.showMessageDialog(null, AcideLanguageManager
					.getInstance().getLabels().getString("s585"));

			AcideReplaceWindow.getInstance().setOnTop(true);

			// Updates the status message in the ACIDE - A Configurable
			// status bar
			AcideMainWindow
					.getInstance()
					.getStatusBar()
					.setStatusMessage(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s585"));
		}

		// Gets the selected file editor panel index
		int selectedFileEditorPanelIndex = AcideMainWindow.getInstance()
				.getFileEditorManager().getSelectedFileEditorPanelIndex();
		
		AcideMainWindow.getInstance().getFileEditorManager()
		.getFileEditorPanelAt(selectedFileEditorPanelIndex)
		.getActiveTextEditionArea().requestFocusInWindow();

		// Current document search
		if (_currentDocumentRadioButton.isSelected()) {

			// The all documents current index is -1
			_allDocumentsCurrentIndex = -1;

			// The counter is 0
			_allDocumentsCounter = 0;

			// The search result position is -1
			_searchResultPosition = -1;

			// There is no selected text
			_selectedText = null;

			// Gets the selected file editor panel index
			selectedFileEditorPanelIndex = AcideMainWindow.getInstance()
					.getFileEditorManager()
					.getSelectedFileEditorPanelIndex();
			
			// Brings the main window to foreground
			//AcideMainWindow.getInstance().setAlwaysOnTop(true);
			
			// Updates the status message in the status bar in the
			// ACIDE - A Configurable IDE main window
			AcideMainWindow
					.getInstance()
					.getStatusBar()
					.setStatusMessage(
							AcideLanguageManager.getInstance()
									.getLabels().getString("s2144")
									+ " "
									+ AcideMainWindow.getInstance().getFileEditorManager()
									.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex));
			
			// Brings the main window to foreground
			//AcideMainWindow.getInstance().setAlwaysOnTop(false);
			
		/*	AcideSearchInformationWindow.getInstance().setLabel(
					AcideLanguageManager.getInstance()
					.getLabels().getString("s2144")
					+ " "
					+ AcideMainWindow.getInstance().getFileEditorManager()
					.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex) + "...");
			AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/

			// The result position is the caret position
			_searchResultPosition = AcideMainWindow.getInstance()
					.getFileEditorManager()
					.getFileEditorPanelAt(selectedFileEditorPanelIndex)
					.getActiveTextEditionArea().getCaretPosition();

			// If it is backwards
			if (direction == AcideSearchDirection.BACKWARD) {

				// The result position is the selection start
				_searchResultPosition = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getSelectionStart();
			}

			// Gets the selected file editor panel index
			selectedFileEditorPanelIndex = AcideMainWindow.getInstance()
					.getFileEditorManager()
					.getSelectedFileEditorPanelIndex();

			// Performs the search
			_searchResultPosition = _searchEngine.search(
					_searchResultPosition,
					_textToSearch,
					AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(
									selectedFileEditorPanelIndex)
							.getTextEditionAreaContent(),
					_caseSensitiveCheckBox.isSelected(),
					_regularExpressionsCheckBox.isSelected(),
					_completeWordsCheckBox.isSelected(), direction);

		//	AcideSearchInformationWindow.getInstance().setVisible(false);
			
			// If the regular expression is wrong
			if (_searchResultPosition == -2){
				
				// Puts the default cursor
				AcideReplaceWindow.getInstance().setCursor(
						Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				AcideMainWindow.getInstance().setCursor(
						Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				return;
			}
			
			// If it found something out
			if (_searchResultPosition != -1) {
				
				if (!_replaceAll){
					AcideReplaceWindow.getInstance()
						.setProgress(100);
					Runnable runnable = new thread1();
					Thread hilo = new Thread(runnable);
					hilo.start();
					//repaint();
				}

				_finishedF3 = false;

				// If the regular expressions check box is selected
				if (_regularExpressionsCheckBox.isSelected()) {

					// Shows the search in the file editor
					AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(
									AcideMainWindow
											.getInstance()
											.getFileEditorManager()
											.getSelectedFileEditorPanelIndex())
							.selectText(
									_searchResultPosition,
									_searchEngine.getRegularExpresion()
											.length());
					
					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s577")
									+ " "
									+ _searchEngine.getRegularExpresion()
									+ " "
									+ AcideLanguageManager.getInstance()
											.getLabels().getString("s574"));
					
					// Updates the status message in the ACIDE - A
					// Configurable IDE status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s577")
											+ " "
											+ _searchEngine
													.getRegularExpresion()
											+ " "
											+ AcideLanguageManager
													.getInstance()
													.getLabels()
													.getString("s574"));
				}
				else {
					

				// Shows the search in the file editor
				AcideMainWindow
						.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(
								AcideMainWindow.getInstance()
										.getFileEditorManager()
										.getSelectedFileEditorPanelIndex())
						.selectText(_searchResultPosition,
								_textToSearch.length());

				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s583")
								+ _textToSearch
								+ AcideLanguageManager.getInstance()
										.getLabels().getString("s574"));

				// Updates the status message in the ACIDE -A Configurable
				// IDE status bar
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s583")
										+ " "
										+ _textToSearch
										+ " "
										+ AcideLanguageManager
												.getInstance().getLabels()
												.getString("s574"));				
				}					
					
			} 
			else {
				if (!_replaceAll){
					AcideReplaceWindow.getInstance()
						.setProgress(100);
					Runnable runnable = new thread1();
					Thread hilo = new Thread(runnable);
					hilo.start();
					//repaint();
				}
				if (!_finishedF3){
				_finishedF3 = true;
				
				// Updates the log
				AcideLog.getLog().info(
						AcideLanguageManager.getInstance().getLabels()
								.getString("s573"));

				AcideReplaceWindow.getInstance().setOnTop(false);

				// Displays a message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s573"));

				AcideReplaceWindow.getInstance().setOnTop(true);

				// Updates the status message in the ACIDE - A Configurable
				// IDE status bar
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s573"));
				}	
			}
		}

		// Selected text search
		if (_selectedTextRadioButton.isSelected()) {

			// The counter is 0
			_allDocumentsCounter = 0;

			// The all documents current index is -1
			_allDocumentsCurrentIndex = -1;

			// Gets the selected file editor panel index
			selectedFileEditorPanelIndex = AcideMainWindow.getInstance()
					.getFileEditorManager()
					.getSelectedFileEditorPanelIndex();
			
			// Brings the main window to foreground
			//AcideMainWindow.getInstance().setAlwaysOnTop(true);
			
			// Updates the status message in the status bar in the
			// ACIDE - A Configurable IDE main window
			AcideMainWindow
					.getInstance()
					.getStatusBar()
					.setStatusMessage(
							AcideLanguageManager.getInstance()
									.getLabels().getString("s2145")
									+ " "
									+ AcideMainWindow.getInstance().getFileEditorManager()
									.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex));
			
			// Brings the main window to foreground
			//AcideMainWindow.getInstance().setAlwaysOnTop(false);
			
			/*AcideSearchInformationWindow.getInstance().setLabel(
					AcideLanguageManager.getInstance()
					.getLabels().getString("s2145")
					+ " "
					+ AcideMainWindow.getInstance().getFileEditorManager()
					.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex) + "...");
			AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/

			// If there is no selected text
			if (_selectedText == null) {

				// Gets the selected text
				_selectedText = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getSelectedText();

				// The initial position is the selection start
				_initialPosition = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getSelectionStart();

				// The final position is the selection end
				_finalPosition = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getSelectionEnd();

				// The search result position is 0
				_searchResultPosition = 0;

				// If it is backwards
				if (direction == AcideSearchDirection.BACKWARD)

					// The search result position is the final position
					_searchResultPosition = _finalPosition;

				if ((_regularExpressionsCheckBox.isSelected())
						&& (direction != AcideSearchDirection.BACKWARD))

					// The search result position is the initial position
					_searchResultPosition = _initialPosition;
			} else {

				// Calculates the result position
				_searchResultPosition = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getCaretPosition()
						- _initialPosition;

				// If it is backwards
				if (direction == AcideSearchDirection.BACKWARD) {

					// Calculates the result position
					_searchResultPosition = AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(
									selectedFileEditorPanelIndex)
							.getActiveTextEditionArea().getSelectionStart()
							- _initialPosition;
				}
			}

			// If the selected text is null
			if (_selectedText == null) {

				AcideReplaceWindow.getInstance().setOnTop(false);

				// Displays a message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s616"));

				AcideReplaceWindow.getInstance().setOnTop(true);

				// Updates the status message in the ACIDE - A Configurable
				// IDE status bar
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s616"));
			}

			else {

				// Performs the search
				_searchResultPosition = _searchEngine.search(
						_searchResultPosition, _textToSearch,
						_selectedText, _caseSensitiveCheckBox.isSelected(),
						_regularExpressionsCheckBox.isSelected(),
						_completeWordsCheckBox.isSelected(), direction);

				//AcideSearchInformationWindow.getInstance().setVisible(false);

				// If the regular expression is wrong
				if (_searchResultPosition == -2){
					
					// Puts the default cursor
					AcideReplaceWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					AcideMainWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					return;
				}
				
				// If it found something out
				if (_searchResultPosition != -1) {
					
					if (!_replaceAll){
						AcideReplaceWindow.getInstance()
							.setProgress(100);
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//repaint();
					}

					// Shows the search in the file editor
					AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(
									AcideMainWindow
											.getInstance()
											.getFileEditorManager()
											.getSelectedFileEditorPanelIndex())
							.selectText(
									_searchResultPosition
											+ _initialPosition,
									_textToSearch.length());
					
					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s583")
									+ " "
									+ _textToSearch
									+ " "
									+ AcideLanguageManager.getInstance()
											.getLabels().getString("s574"));

					// Updates the status message in the ACIDE - A
					// Configurable IDE status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s583")
											+ " "
											+ _textToSearch
											+ " "
											+ AcideLanguageManager
													.getInstance()
													.getLabels()
													.getString("s574"));

					// If the regular expressions check box is selected
					if (_regularExpressionsCheckBox.isSelected()) {

						// Shows the search in the file editor
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										AcideMainWindow
												.getInstance()
												.getFileEditorManager()
												.getSelectedFileEditorPanelIndex())
								.selectText(
										_searchResultPosition
												+ _initialPosition,
										_searchEngine.getRegularExpresion()
												.length());

						// Updates the log
						AcideLog.getLog().info(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s329")
										+ " "
										+ _searchEngine
												.getRegularExpresion()
										+ " "
										+ AcideLanguageManager
												.getInstance().getLabels()
												.getString("s574"));

						// Updates the status message in the ACIDE - A
						// Configurable IDE status bar
						AcideMainWindow
								.getInstance()
								.getStatusBar()
								.setStatusMessage(
										AcideLanguageManager.getInstance()
												.getLabels()
												.getString("s329")
												+ " "
												+ _searchTextField
														.getText()
												+ " "
												+ AcideLanguageManager
														.getInstance()
														.getLabels()
														.getString("s574"));

					}
				}

				else {
					
					if (!_replaceAll){
						AcideReplaceWindow.getInstance()
							.setProgress(100);
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//repaint();
					}

					// There is no selected text
					_selectedText = null;

					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s573"));

					AcideReplaceWindow.getInstance().setOnTop(false);

					// Displays a message
					JOptionPane.showMessageDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s573"));
					
					AcideReplaceWindow.getInstance().setOnTop(true);


					// Updates the status message in the ACIDE - A
					// Configurable IDE status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s573"));

					AcideReplaceWindow.getInstance().setOnTop(false);

					// Shows a confirm dialog
					int returnValue = JOptionPane.showConfirmDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s575"));
					
					AcideReplaceWindow.getInstance().setOnTop(true);

					
					// If it is ok
					if (returnValue == JOptionPane.OK_OPTION) {

						// Selects the current document radio button
						_currentDocumentRadioButton.setSelected(true);

						// If it is not backwards
						if (direction != AcideSearchDirection.BACKWARD)

							// The search result position is the final
							// position
							_searchResultPosition = _finalPosition;
						else

							// The search result position is the initial
							// position
							_searchResultPosition = _initialPosition;

						// Performs the search button action
						//_searchButton.doClick();
						searchAction();

					}
				}
			}
		}
		// All documents search
		if (_allDocumentsRadioButton.isSelected()) {

			// There is no selected text
			_selectedText = null;

			// Gets the selected file editor panel index
			selectedFileEditorPanelIndex = AcideMainWindow.getInstance()
					.getFileEditorManager().getNumberOfFileEditorPanels();

			if (!_isCycle && _currentPosition == -2) {

				// Gets the all documents current index
				_allDocumentsCurrentIndex = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getSelectedFileEditorPanelIndex();

				// Updates the current document index
				_currentDocumentIndex = _allDocumentsCurrentIndex;

				// The current position is the caret position
				_currentPosition = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(_allDocumentsCurrentIndex)
						.getActiveTextEditionArea().getCaretPosition();
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(true);
				// Updates the status message in the status bar in the
				// ACIDE - A Configurable IDE main window
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s2144")
										+ " "
										+ AcideMainWindow.getInstance().getFileEditorManager()
										.getTabbedPane().getTitleAt(_allDocumentsCurrentIndex));
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(false);
				
				/*AcideSearchInformationWindow.getInstance().setLabel(
						AcideLanguageManager.getInstance()
						.getLabels().getString("s2144")
						+ " "
						+ AcideMainWindow.getInstance().getFileEditorManager()
						.getTabbedPane().getTitleAt(_allDocumentsCurrentIndex) + "...");
				AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/
			}

			// If it is not the end
			if (!_isEnd) {

				// If it is forwards
				if (direction == AcideSearchDirection.FORWARD)

					// The search result position is the caret position
					_searchResultPosition = AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(_allDocumentsCurrentIndex)
							.getActiveTextEditionArea().getCaretPosition();

				// If it is backwards
				if (direction == AcideSearchDirection.BACKWARD)

					// The search result position is the selection start
					_searchResultPosition = AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(_allDocumentsCurrentIndex)
							.getActiveTextEditionArea().getSelectionStart();

				// If it is both directions
				if (direction == AcideSearchDirection.BOTH) {

					// The search result position is the caret position
					_searchResultPosition = AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(_allDocumentsCurrentIndex)
							.getActiveTextEditionArea().getCaretPosition();
				}

				// Gets the auxiliary direction
				AcideSearchDirection auxiliaryDirection = direction;

				// If it is both
				if (direction == AcideSearchDirection.BOTH)

					// By default is forwards
					auxiliaryDirection = AcideSearchDirection.FORWARD;

				// Performs the search
				_searchResultPosition = _searchEngine.search(
						_searchResultPosition,
						_textToSearch,
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										_allDocumentsCurrentIndex)
								.getTextEditionAreaContent(),
						_caseSensitiveCheckBox.isSelected(),
						_regularExpressionsCheckBox.isSelected(),
						_completeWordsCheckBox.isSelected(),
						auxiliaryDirection);

				//AcideSearchInformationWindow.getInstance().setVisible(false);

				if ((_isCycle && _allDocumentsCurrentIndex == _currentDocumentIndex)
						&& (_searchResultPosition >= _currentPosition)){

					// It is the search end
					_isEnd = true;
					if (!_replaceAll){
						AcideReplaceWindow.getInstance()
						.setProgress(100);
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//	repaint();
					}
				}
				else if ((_isCycle && _allDocumentsCurrentIndex == _currentDocumentIndex)
						&& (_searchResultPosition == -1)){

					// It is the search end
					_isEnd = true;
					if (!_replaceAll){
						AcideReplaceWindow.getInstance()
							.setProgress(100);
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//repaint();
					}
				}
				
				// If the regular expression is wrong
				if (_searchResultPosition == -2){
					
					// Puts the default cursor
					AcideReplaceWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					AcideMainWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					return;
				}

				// If it found something out
				if (_searchResultPosition != -1) {

					// Increases the counter
					_allDocumentsCounter++;
					
					if (!_replaceAll){
						AcideReplaceWindow.getInstance()
							.setProgress(100);
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//	repaint();
					}

					// If the regular expressions are not selected
					if (!_regularExpressionsCheckBox.isSelected()) {

						// Brings the main window to foreground
						AcideMainWindow.getInstance().setAlwaysOnTop(true);

						// Shows the search in the file editor
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										_allDocumentsCurrentIndex)
								.selectText(_searchResultPosition,
										_textToSearch.length());

						// Brings the main window to background
						AcideMainWindow.getInstance().setAlwaysOnTop(false);

						// Updates the log
						AcideLog.getLog().info(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s583")
										+ " "
										+ _textToSearch
										+ " "
										+ AcideLanguageManager
												.getInstance().getLabels()
												.getString("s574"));

						// Updates the status message in the ACIDE - A
						// Configurable IDE status bar
						AcideMainWindow
								.getInstance()
								.getStatusBar()
								.setStatusMessage(
										AcideLanguageManager.getInstance()
												.getLabels()
												.getString("s583")
												+ " "
												+ _searchTextField
														.getText()
												+ " "
												+ AcideLanguageManager
														.getInstance()
														.getLabels()
														.getString("s574"));

					} else {

						// Brings the main window to foreground
						AcideMainWindow.getInstance().setAlwaysOnTop(true);

						// Shows the search in the file editor
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										_allDocumentsCurrentIndex)
								.selectText(
										_searchResultPosition,
										_searchEngine.getRegularExpresion()
												.length());

						// Brings the main window to background
						AcideMainWindow.getInstance().setAlwaysOnTop(false);

						// Updates the log
						AcideLog.getLog().info(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s577")
										+ " "
										+ _searchEngine
												.getRegularExpresion()
										+ " "
										+ AcideLanguageManager
												.getInstance().getLabels()
												.getString("s577"));

						// Updates the status message in the ACIDE - A
						// Configurable IDE status bar
						AcideMainWindow
								.getInstance()
								.getStatusBar()
								.setStatusMessage(
										AcideLanguageManager.getInstance()
												.getLabels()
												.getString("s577")
												+ " "
												+ _searchEngine
														.getRegularExpresion()
												+ " "
												+ AcideLanguageManager
														.getInstance()
														.getLabels()
														.getString("s577"));
					}

				} else {

					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s573"));

					// Updates the status message in the ACIDE - A
					// Configurable IDE status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s573"));

					// Sets the caret position at the first position
					AcideMainWindow
							.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(_allDocumentsCurrentIndex)
							.getActiveTextEditionArea().setCaretPosition(0);

					// Updates the current index
					if (_forwardRadioButton.isSelected())
						_allDocumentsCurrentIndex++;
					else if (_backwardRadioButton.isSelected())
						_allDocumentsCurrentIndex--;
					else
						_allDocumentsCurrentIndex++;
					
					_valorBar++;
					if (!_replaceAll){
						AcideReplaceWindow.getInstance()
						.setProgress((_valorBar/selectedFileEditorPanelIndex));
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//repaint();
					}

					// If it is forwards
					if (direction == AcideSearchDirection.FORWARD) {

						// If there are no more editors beyond
						if (_allDocumentsCurrentIndex >= selectedFileEditorPanelIndex) {

							// It is the end
							_isEnd = true;
							AcideReplaceWindow.getInstance()
							.setProgress(100);
							Runnable runnable = new thread1();
							Thread hilo = new Thread(runnable);
							hilo.start();
							//repaint();
						} else {

							// Sets the selected file editor panel
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.setSelectedFileEditorPanelAt(
											_allDocumentsCurrentIndex);

							// Sets the caret position at the first position
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(
											_allDocumentsCurrentIndex)
									.getActiveTextEditionArea()
									.setCaretPosition(0);

							// Performs the search button action
							//_searchButton.doClick();
							searchAction();
						}
					}

					// If it is backwards
					if (direction == AcideSearchDirection.BACKWARD) {

						// If the current does not exist
						if (_allDocumentsCurrentIndex < 0) {

							// It is the search end
							_isEnd = true;
							if(!_replaceAll){
								AcideReplaceWindow.getInstance()
								.setProgress(100);
								Runnable runnable = new thread1();
								Thread hilo = new Thread(runnable);
								hilo.start();
								//repaint();
							}
						} else {

							// Sets the selected file editor panel
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.setSelectedFileEditorPanelAt(
											_allDocumentsCurrentIndex);

							// Sets the caret position at the end
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(
											_allDocumentsCurrentIndex)
									.getActiveTextEditionArea()
									.setCaretPosition(
											AcideMainWindow
													.getInstance()
													.getFileEditorManager()
													.getFileEditorPanelAt(
															_allDocumentsCurrentIndex)
													.getActiveTextEditionArea()
													.getText().length() - 1);

							// Performs the search button action
							//_searchButton.doClick();
							searchAction();
						}
					}

					// If the direction is both
					if (direction == AcideSearchDirection.BOTH) {

						// If there is a cycle
						if (_allDocumentsCurrentIndex >= selectedFileEditorPanelIndex) {

							// The current index is 0 again
							_allDocumentsCurrentIndex = 0;

							// There is a cycle
							_isCycle = true;

							// Sets the selected file editor panel
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.setSelectedFileEditorPanelAt(
											_allDocumentsCurrentIndex);

							// Sets the caret position in the first position
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(
											_allDocumentsCurrentIndex)
									.getActiveTextEditionArea()
									.setCaretPosition(0);

							// Performs the search button action
							//_searchButton.doClick();
							searchAction();

						} else {

							// Sets the selected file editor panel
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.setSelectedFileEditorPanelAt(
											_allDocumentsCurrentIndex);

							// Sets the caret position in the first position
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(
											_allDocumentsCurrentIndex)
									.getActiveTextEditionArea()
									.setCaretPosition(0);

							// Performs the search button action
							//_searchButton.doClick();
							searchAction();
						}
					}
				}
			}

			// If it is the end
			if (_isEnd && _isFirst) {

				// If it did not find anything
				if (_allDocumentsCounter == 0) {

					AcideReplaceWindow.getInstance().setOnTop(false);

					// Displays a message
					JOptionPane.showMessageDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s576"));

					AcideReplaceWindow.getInstance().setOnTop(true);

					// Updates the status message in the ACIDE - A
					// Configurable IDE status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s576"));
				} else {

					AcideReplaceWindow.getInstance().setOnTop(false);

					// Displays a message
					JOptionPane.showMessageDialog(null,
							AcideLanguageManager.getInstance().getLabels()
									.getString("s586"));

					AcideReplaceWindow.getInstance().setOnTop(true);

					// Updates the status message in the ACIDE - A
					// Configurable IDE status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s586"));
				}

				// It is not the first
				_isFirst = false;
			}
		}

	}

	/**
	 * ACIDE - A Configurable IDE search button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class SearchButtonAction implements ActionListener {

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
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			searchAction();
			
			
			// Puts the default cursor
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ACIDE - A Configurable IDE replace button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ReplaceButtonAction implements ActionListener {

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
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			_textToSearch = _searchTextField.getText();
			
			_textToReplace = _replaceTextField.getText();
			
			if (!getList().contains(_textToSearch)){
				getList().add(_textToSearch);
			}
			
			if (!getListReplace().contains(_textToReplace)){
				getListReplace().add(_textToReplace);
			}
			
			_textToSearch = _textToSearch.replaceAll("\\^p","\n");
			_textToSearch = _textToSearch.replaceAll("\\^t", "\t");
			
			_textToReplace = _textToReplace.replaceAll("\\^p","\n");
			_textToReplace = _textToReplace.replaceAll("\\^t", "\t");
			
			// If the selected text radio button is selected
			if (_selectedTextRadioButton.isSelected()) {

				// Gets the selected file editor panel index
				int selectedFileEditorPanelIndex = AcideMainWindow
						.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanelIndex();
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(true);
				
				// Updates the status message in the status bar in the
				// ACIDE - A Configurable IDE main window
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s2146")
										+ " "
										+ AcideMainWindow.getInstance().getFileEditorManager()
										.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex));
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(false);
				
				/*AcideSearchInformationWindow.getInstance().setLabel(
						AcideLanguageManager.getInstance()
						.getLabels().getString("s2146")
						+ " "
						+ AcideMainWindow.getInstance().getFileEditorManager()
						.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex) + "...");
				AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/

				String selectedText = null;

				// If it is the first replacement
				if (_isFirstReplacement)

					// Performs the search button action
					//_searchButton.doClick();
					searchAction();
				
				//AcideSearchInformationWindow.getInstance().setVisible(false);

				// Gets the selected text
				selectedText = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getSelectedText();
				
				// If the regular expression is wrong
				if (_searchResultPosition == -2){
					
					// Puts the default cursor
					AcideReplaceWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					AcideMainWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					return;
				}

				// If it found anything out
				if (_searchResultPosition != -1) {

					// If it found anything out
					if (selectedText != null) {

						int caretPosition;

						// If it is forwards
						if (_forwardRadioButton.isSelected()) {

							// The caret position is the selection end
							caretPosition = AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(
											selectedFileEditorPanelIndex)
									.getActiveTextEditionArea()
									.getSelectionEnd();
						} else {

							// The caret position is the selection start
							caretPosition = AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(
											selectedFileEditorPanelIndex)
									.getActiveTextEditionArea()
									.getSelectionStart();
						}

						// Gets the replace text
						_replaceText = "" + _textToReplace;

						// If the respect capitalization is selected
						if (_respectCapitalizationCheckBox.isSelected())

							// Gets the replace text to use
							_replaceText = ""
									+ respectCapitalization(selectedText);

						// Replaces the selection
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										selectedFileEditorPanelIndex)
								.getActiveTextEditionArea()
								.replaceSelection(_replaceText);

						// If it is forwards
						if (_forwardRadioButton.isSelected())

							// Gets the selected text from the replace text
							// field
							_selectedText = _selectedText.replaceFirst(
									_textToSearch, _replaceText);

						// Sets the caret position
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										selectedFileEditorPanelIndex)
								.getActiveTextEditionArea()
								.setCaretPosition(caretPosition);

						// Updates the log
						AcideLog.getLog().info(
								AcideLanguageManager.getInstance().getLabels()
										.getString("s583")
										+ " "
										+ _textToSearch
										+ " "
										+ AcideLanguageManager.getInstance()
												.getLabels().getString("s580")
										+ " " + _replaceText);

						// Updates the status message in the ACIDE - A
						// Configurable IDE status bar
						AcideMainWindow
								.getInstance()
								.getStatusBar()
								.setStatusMessage(
										AcideLanguageManager.getInstance()
												.getLabels().getString("s583")
												+ " "
												+ _textToSearch
												+ " "
												+ AcideLanguageManager
														.getInstance()
														.getLabels()
														.getString("s580")
												+ " " + _replaceText);

						// It is not the first replacement
						_isFirstReplacement = false;
						
						// Gets the current content
						String fileContent = AcideMainWindow.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(selectedFileEditorPanelIndex)
								.getTextEditionAreaContent();
						
						// If has been changes in the file
						if (!AcideMainWindow.getInstance().getFileEditorManager()
								.getFileEditorPanelAt(selectedFileEditorPanelIndex)
								.isEqualToFileDiskCopy(fileContent)) {

							// Sets the red icon to the close button
							AcideMainWindow.getInstance().getFileEditorManager()
									.setRedButtonAt(selectedFileEditorPanelIndex);
							
							
							// Do you want to save it?
							AcideReplaceWindow.getInstance().setAlwaysOnTop(false);
							int returnValue = JOptionPane.showConfirmDialog(null,
									AcideLanguageManager.getInstance().getLabels()
											.getString("s643"),
									AcideLanguageManager.getInstance().getLabels()
											.getString("s953"),
									JOptionPane.YES_NO_CANCEL_OPTION);

							// If it is OK
							if (returnValue == JOptionPane.OK_OPTION) {

								// Saves the file editor panel
								AcideMainWindow.getInstance().getMenu()
										.getFileMenu().saveFile(selectedFileEditorPanelIndex);
							}  
							AcideReplaceWindow.getInstance().setAlwaysOnTop(true);	
						} else {

							// Sets the green icon to the close button
							AcideMainWindow.getInstance().getFileEditorManager()
									.setGreenButtonAt(selectedFileEditorPanelIndex);
						}

						// Performs the search button action
						//_searchButton.doClick();
						searchAction();
					}
				}
			}
			if (_currentDocumentRadioButton.isSelected()
					|| _allDocumentsRadioButton.isSelected()) {

				// Gets the selected file editor panel index
				int selectedFileEditorPanelIndex = AcideMainWindow
						.getInstance().getFileEditorManager()
						.getSelectedFileEditorPanelIndex();

				// If it is the first replacement
				if (_isFirstReplacement) {

					// Stores the original caret position
					_originalPosition = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(selectedFileEditorPanelIndex)
							.getActiveTextEditionArea().getCaretPosition();

					// Performs the search button action
					//_searchButton.doClick();
					searchAction();
				}
				
				// If the regular expression is wrong
				if (_searchResultPosition == -2){
					
					// Puts the default cursor
					AcideReplaceWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					AcideMainWindow.getInstance().setCursor(
							Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					return;
				}

				// Gets the selected text from the file editor
				String selectedText = AcideMainWindow.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getSelectedText();
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(true);
				
				// Updates the status message in the status bar in the
				// ACIDE - A Configurable IDE main window
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s2147")
										+ " "
										+ AcideMainWindow.getInstance().getFileEditorManager()
										.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex));
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(false);
				
				/*AcideSearchInformationWindow.getInstance().setLabel(
						AcideLanguageManager.getInstance()
						.getLabels().getString("s2147")
						+ " "
						+ AcideMainWindow.getInstance().getFileEditorManager()
						.getTabbedPane().getTitleAt(selectedFileEditorPanelIndex) + "...");
				AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/

				// If it found anything out
				if (selectedText != null) {

					// Gets the replace text
					_replaceText = "" + _textToReplace;

					// If the respect capitalization is selected
					if (_respectCapitalizationCheckBox.isSelected())

						// Gets the replace text to use
						_replaceText = "" + respectCapitalization(selectedText);

					// Replaces the string
					AcideMainWindow.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(selectedFileEditorPanelIndex)
							.getActiveTextEditionArea()
							.replaceSelection(_replaceText);

					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s579")
									+ " "
									+ _textToSearch
									+ " "
									+ AcideLanguageManager.getInstance()
											.getLabels().getString("s580")
									+ " " + _replaceText);

					// Updates the status message in the ACIDE - A Configurable
					// IDE status bar
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s579")
											+ " "
											+ _textToSearch
											+ " "
											+ AcideLanguageManager
													.getInstance().getLabels()
													.getString("s580")
											+ " "
											+ _replaceText);

					// It is not the first replacement
					_isFirstReplacement = false;
					
					// Gets the current content
					String fileContent = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(selectedFileEditorPanelIndex)
							.getTextEditionAreaContent();
					
					// If has been changes in the file
					if (!AcideMainWindow.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(selectedFileEditorPanelIndex)
							.isEqualToFileDiskCopy(fileContent)) {

						// Sets the red icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setRedButtonAt(selectedFileEditorPanelIndex);
						
						
						/*// Do you want to save it?
						AcideReplaceWindow.getInstance().setAlwaysOnTop(false);
						int returnValue = JOptionPane.showConfirmDialog(null,
								AcideLanguageManager.getInstance().getLabels()
										.getString("s643"),
								AcideLanguageManager.getInstance().getLabels()
										.getString("s953"),
								JOptionPane.YES_NO_CANCEL_OPTION);

						// If it is OK
						if (returnValue == JOptionPane.OK_OPTION) {

							// Saves the file editor panel
							AcideMainWindow.getInstance().getMenu()
									.getFileMenu().saveFile(selectedFileEditorPanelIndex);
						} 
						AcideReplaceWindow.getInstance().setAlwaysOnTop(true);*/
					} else {

						// Sets the green icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setGreenButtonAt(selectedFileEditorPanelIndex);
					}

					// Performs the search button action
					//_searchButton.doClick();
					searchAction();
					
					//AcideSearchInformationWindow.getInstance().setVisible(false);
				}

				// If there is no selected text in the file editor
				if (AcideMainWindow.getInstance().getFileEditorManager()
						.getFileEditorPanelAt(selectedFileEditorPanelIndex)
						.getActiveTextEditionArea().getSelectedText() == null) {

					if (_originalPosition != -1) {
						
						// Restores the original caret position
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										selectedFileEditorPanelIndex)
								.getActiveTextEditionArea()
								.setCaretPosition(_originalPosition);
					}
				}
			}
			
			// Puts the default cursor
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
		}
	}

	/**
	 * ACIDE - A Configurable IDE replace window replace all button action
	 * listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ReplaceAllButtonAction implements ActionListener {

		/**
		 * Local number of replacements.
		 */
		private int _localNumberOfReplacements = 0;
		/**
		 * Global number of replacements.
		 */
		private int _globalNumberOfReplacements = 0;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			
			_replaceAll = true;
			// Puts the wait cursor
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			_textToSearch = _searchTextField.getText();
			
			_textToReplace = _replaceTextField.getText();
			
			if (!getList().contains(_textToSearch)){
				getList().add(_textToSearch);
			}
			
			if (!getListReplace().contains(_textToReplace)){
				getListReplace().add(_textToReplace);
			}
			
			_textToSearch = _textToSearch.replaceAll("\\^p","\n");
			_textToSearch = _textToSearch.replaceAll("\\^t", "\t");
			
			_textToReplace = _textToReplace.replaceAll("\\^p","\n");
			_textToReplace = _textToReplace.replaceAll("\\^t", "\t");
			
			_forwardRadioButton.setSelected(true);
			
			// Gets the original caret position
			int originalCaretPosition = AcideMainWindow.getInstance()
					.getFileEditorManager().getSelectedFileEditorPanel()
					.getActiveTextEditionArea().getCaretPosition();

			// Gets the selected file editor panel index
			int selectedFileEditorPanelIndex = AcideMainWindow.getInstance()
					.getFileEditorManager().getSelectedFileEditorPanelIndex();

			// Initializes the number of replacements
			_globalNumberOfReplacements = 0;

			// Gets the replace text
			_replaceText = "" + _textToReplace;			

			// If the selected text radio button is selected
			if (_selectedTextRadioButton.isSelected()) {
				// Initializes the variables
				String selectedEditorText = null;
				
				_bothDirectionsRadioButton.setSelected(true);

				// Gets the selected editor index
				int selectedEditorIndex = AcideMainWindow.getInstance()
						.getFileEditorManager().getSelectedFileEditorPanelIndex();

				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(true);
				
				// Updates the status message in the status bar in the
				// ACIDE - A Configurable IDE main window
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s2146")
										+ " "
										+ AcideMainWindow.getInstance().getFileEditorManager()
										.getTabbedPane().getTitleAt(selectedEditorIndex));
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(false);
				
				/*AcideSearchInformationWindow.getInstance().setLabel(
						AcideLanguageManager.getInstance()
						.getLabels().getString("s2146")
						+ " "
						+ AcideMainWindow.getInstance().getFileEditorManager()
						.getTabbedPane().getTitleAt(selectedEditorIndex) + "...");
				AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/

				// If there is anything selected
				if (AcideMainWindow.getInstance().getFileEditorManager()
						.getFileEditorPanelAt(selectedEditorIndex)
						.getActiveTextEditionArea().getSelectedText() != null) {

					// Gets the number of replacements
					_globalNumberOfReplacements = countMatches(AcideMainWindow
							.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(selectedEditorIndex)
							.getActiveTextEditionArea().getSelectedText(),
							_textToSearch,
							_caseSensitiveCheckBox.isSelected());
					
					// Gets the selection start
					int selectionTextStart = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(selectedEditorIndex)
							.getActiveTextEditionArea().getSelectionStart();
					
				
					// Gets the selected editor text
					selectedEditorText = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(selectedEditorIndex)
							.getActiveTextEditionArea().getSelectedText();

					// Gets the text from the selected file editor panel
					String text = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(selectedEditorIndex)
							.getActiveTextEditionArea().getText();
					
					int sizeText = selectedEditorText.length();
					
					int sizeTextToSearch = _textToSearch.length();
					
					int sizeTextToReplace = _textToReplace.length();
					
					int sizeNewText = sizeText - sizeTextToSearch + sizeTextToReplace;
					
					int selectionNewTextEnd = selectionTextStart + sizeNewText;
					
					// If there are replacements
					if (_globalNumberOfReplacements > 0) {
						for (int i = _globalNumberOfReplacements; i > 0; i--){
							
							AcideReplaceWindow.getInstance()
							.setProgress((_globalNumberOfReplacements - i/_globalNumberOfReplacements));
							Runnable runnable = new thread1();
							Thread hilo = new Thread(runnable);
							hilo.start();
							//repaint();
						String selectedText = null;

						// If it is the first replacement
						if (_isFirstReplacement)

							// Performs the search button action
							//_searchButton.doClick();
							searchAction();
						
						_searchResultPosition = _searchResultPosition + selectionTextStart;
						
						
						// If the regular expression is wrong
						if (_searchResultPosition == -2){
							
							// Puts the default cursor
							AcideReplaceWindow.getInstance().setCursor(
									Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							
							AcideMainWindow.getInstance().setCursor(
									Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							
							return;
						}
						
						// Shows the search in the file editor
						AcideMainWindow
								.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(
										AcideMainWindow.getInstance()
												.getFileEditorManager()
												.getSelectedFileEditorPanelIndex())
								.selectText(_searchResultPosition,
										_textToSearch.length());

						// Gets the selected text
						selectedText = AcideMainWindow.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(selectedFileEditorPanelIndex)
								.getActiveTextEditionArea().getSelectedText();
						

						// If it found anything out
						if (_searchResultPosition != -1) {

							// If it found anything out
							if (selectedText != null) {

								// Gets the replace text
								_replaceText = "" + _textToReplace;

								// If the respect capitalization is selected
								if (_respectCapitalizationCheckBox.isSelected())

									// Gets the replace text to use
									_replaceText = ""
											+ respectCapitalization(selectedText);

								// Replaces the selection
								AcideMainWindow
										.getInstance()
										.getFileEditorManager()
										.getFileEditorPanelAt(
												selectedFileEditorPanelIndex)
										.getActiveTextEditionArea()
										.replaceSelection(_replaceText);

								// If it is forwards
								if (_bothDirectionsRadioButton.isSelected()){
																
									// Gets the text from the selected file editor panel
									text = AcideMainWindow.getInstance()
											.getFileEditorManager()
											.getFileEditorPanelAt(selectedEditorIndex)
											.getActiveTextEditionArea().getText();
									
									selectionTextStart = _searchResultPosition+ _replaceText.length();
									
									_selectedText = text
											.substring(selectionTextStart, selectionNewTextEnd);
								}

								// Sets the caret position
								AcideMainWindow
										.getInstance()
										.getFileEditorManager()
										.getFileEditorPanelAt(
												selectedFileEditorPanelIndex)
										.getActiveTextEditionArea()
										.setCaretPosition(0);
								
								// It is not the first replacement
								_isFirstReplacement = false;
								
								// Gets the text from the selected file editor panel
								text = AcideMainWindow.getInstance()
										.getFileEditorManager()
										.getFileEditorPanelAt(selectedEditorIndex)
										.getActiveTextEditionArea().getText();
								
								sizeText = _selectedText.length();
							
								sizeNewText = sizeText - sizeTextToSearch + sizeTextToReplace;
								
								selectionNewTextEnd = selectionTextStart + sizeNewText;
								if (i > 1) {
									// Performs the search button action
									//_searchButton.doClick();
									searchAction();
								}
						
								
							}
						}
						}
						
						AcideReplaceWindow.getInstance()
						.setProgress(100);
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//repaint();
						//AcideSearchInformationWindow.getInstance().setVisible(false);
					}

					// Gets the current content
					String fileContent = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(selectedEditorIndex)
							.getTextEditionAreaContent();
					
					// If has been changes in the file
					if (!AcideMainWindow.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(selectedEditorIndex)
							.isEqualToFileDiskCopy(fileContent)) {

						// Sets the red icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setRedButtonAt(selectedEditorIndex);
						
					
					} else {

						// Sets the green icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setGreenButtonAt(selectedEditorIndex);
					}
				}
				
				// Sets the original caret position
				AcideMainWindow
						.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(
								selectedFileEditorPanelIndex)
						.getActiveTextEditionArea()
						.setCaretPosition(originalCaretPosition);
					
			}
			
			// If the current document radio button is selected
			if (_currentDocumentRadioButton.isSelected()){
				//TODO reemplazamiento en current document
				int _replacementsBeforeCaretPosition = 0;
				
				// Gets the selected editor index
				int selectedEditorIndex = AcideMainWindow.getInstance()
						.getFileEditorManager().getSelectedFileEditorPanelIndex();
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(true);
				
				// Updates the status message in the status bar in the
				// ACIDE - A Configurable IDE main window
				AcideMainWindow
						.getInstance()
						.getStatusBar()
						.setStatusMessage(
								AcideLanguageManager.getInstance()
										.getLabels().getString("s2147")
										+ " "
										+ AcideMainWindow.getInstance().getFileEditorManager()
										.getTabbedPane().getTitleAt(selectedEditorIndex));
				
				// Brings the main window to foreground
				//AcideMainWindow.getInstance().setAlwaysOnTop(false);
				
				/*AcideSearchInformationWindow.getInstance().setLabel(
						AcideLanguageManager.getInstance()
						.getLabels().getString("s2146")
						+ " "
						+ AcideMainWindow.getInstance().getFileEditorManager()
						.getTabbedPane().getTitleAt(selectedEditorIndex) + "...");
				AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/
				
				// Gets the original caret position
				int caretPosition = AcideMainWindow.getInstance()
						.getFileEditorManager().getSelectedFileEditorPanel()
						.getActiveTextEditionArea().getCaretPosition();
				
				AcideMainWindow.getInstance()
				.getFileEditorManager().getSelectedFileEditorPanel()
				.getActiveTextEditionArea().setCaretPosition(0);

				// Gets the number of replacements
				_globalNumberOfReplacements = countMatches(
						AcideMainWindow.getInstance().getFileEditorManager()
								.getFileEditorPanelAt(selectedEditorIndex)
								.getActiveTextEditionArea().getText(),
						_textToSearch,
						_caseSensitiveCheckBox.isSelected());

				// If there are replacements
				if (_globalNumberOfReplacements > 0) {

					
					// Gets the selected editor index
					selectedEditorIndex = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getSelectedFileEditorPanelIndex();

					
					for (int i = _globalNumberOfReplacements; i > 0; i--){
						
						AcideReplaceWindow.getInstance()
						.setProgress((_globalNumberOfReplacements - i/_globalNumberOfReplacements));
						Runnable runnable = new thread1();
						Thread hilo = new Thread(runnable);
						hilo.start();
						//repaint();
						// Gets the selected file editor panel index
						selectedFileEditorPanelIndex = AcideMainWindow
								.getInstance().getFileEditorManager()
								.getSelectedFileEditorPanelIndex();

						// If it is the first replacement
						if (_isFirstReplacement) {

							// Stores the original caret position
							_originalPosition = caretPosition;

							// Performs the search button action
							//_searchButton.doClick();
							searchAction();
						}
					
						//AcideSearchInformationWindow.getInstance().setVisible(false);
						
						// If the regular expression is wrong
						if (_searchResultPosition == -2){
							
							// Puts the default cursor
							AcideReplaceWindow.getInstance().setCursor(
									Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							
							AcideMainWindow.getInstance().setCursor(
									Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							
							return;
						}
						
						if (_searchResultPosition < originalCaretPosition){
							_replacementsBeforeCaretPosition++;
						}

						// Gets the selected text from the file editor
						String selectedText = AcideMainWindow.getInstance()
								.getFileEditorManager()
								.getFileEditorPanelAt(selectedFileEditorPanelIndex)
								.getActiveTextEditionArea().getSelectedText();
						
						
						// If it found anything out
						if (selectedText != null) {

							// Gets the replace text
							_replaceText = "" + _textToReplace;

							// If the respect capitalization is selected
							if (_respectCapitalizationCheckBox.isSelected())

								// Gets the replace text to use
								_replaceText = "" + respectCapitalization(selectedText);

							// Replaces the string
							AcideMainWindow.getInstance().getFileEditorManager()
									.getFileEditorPanelAt(selectedFileEditorPanelIndex)
									.getActiveTextEditionArea()
									.replaceSelection(_replaceText);
							
							// Sets the caret position
							AcideMainWindow
									.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(
											selectedFileEditorPanelIndex)
									.getActiveTextEditionArea()
									.setCaretPosition(_searchResultPosition + _replaceText.length());
							
							// It is not the first replacement
							_isFirstReplacement = false;
							
							if (i > 1){
							// Performs the search button action
							//_searchButton.doClick();
							searchAction();
							}
							
							//AcideSearchInformationWindow.getInstance().setVisible(false);
						}
					}
					
					AcideReplaceWindow.getInstance()
					.setProgress(100);
					Runnable runnable = new thread1();
					Thread hilo = new Thread(runnable);
					hilo.start();
					//repaint();
					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s582")
									+ _textToSearch
									+ AcideLanguageManager.getInstance()
											.getLabels().getString("s580")
									+ _replaceText);
					
					// Gets the current content
					String fileContent = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(selectedFileEditorPanelIndex)
							.getTextEditionAreaContent();
					
					// If has been changes in the file
					if (!AcideMainWindow.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(selectedFileEditorPanelIndex)
							.isEqualToFileDiskCopy(fileContent)) {

						// Sets the red icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setRedButtonAt(selectedFileEditorPanelIndex);
						
					} else {

						// Sets the green icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setGreenButtonAt(selectedFileEditorPanelIndex);
					}
				}
				
				int auxCaret = _originalPosition + 
						(_textToReplace.length() - _textToSearch.length()) * _replacementsBeforeCaretPosition;
				
				if (auxCaret < 0){
					auxCaret = 0;
				}
				// Sets the original caret position
				AcideMainWindow
						.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(
								selectedFileEditorPanelIndex)
						.getActiveTextEditionArea()
						.setCaretPosition(auxCaret);
			}
			
			// If the all documents radio button is selected
			if (_allDocumentsRadioButton.isSelected()){
				
				// Gets the original caret position
				int caretPosition = 0;
				int _replacementsBeforeCaretPosition = 0;
				
				// Gets the original selected editor index
				int originalSelectedEditorIndex = AcideMainWindow.getInstance()
						.getFileEditorManager().getSelectedFileEditorPanelIndex();

				// Initializes the number of replacements
				_globalNumberOfReplacements = 0;
				
				int _auxNumberOfDocument = 0;
				int _auxNumberTotals = 0;
				
				// For each one of the opened editors
				for (int editorIndex = 0; editorIndex < AcideMainWindow
						.getInstance().getFileEditorManager()
						.getNumberOfFileEditorPanels(); editorIndex++) {
					
					// Gets the number of replacements
					_auxNumberOfDocument = countMatches(AcideMainWindow
							.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(editorIndex)
							.getActiveTextEditionArea().getText(),
							_textToSearch,
							_caseSensitiveCheckBox.isSelected());
					
					_auxNumberTotals += _auxNumberOfDocument;
					
				}

				// For each one of the opened editors
				for (int editorIndex = 0; editorIndex < AcideMainWindow
						.getInstance().getFileEditorManager()
						.getNumberOfFileEditorPanels(); editorIndex++) {
					
					_replacementsBeforeCaretPosition = 0;

					// Sets the selected editor at the current editor
					AcideMainWindow.getInstance().getFileEditorManager()
							.setSelectedFileEditorPanelAt(editorIndex);
					
					// Brings the main window to foreground
					//AcideMainWindow.getInstance().setAlwaysOnTop(true);
					
					// Updates the status message in the status bar in the
					// ACIDE - A Configurable IDE main window
					AcideMainWindow
							.getInstance()
							.getStatusBar()
							.setStatusMessage(
									AcideLanguageManager.getInstance()
											.getLabels().getString("s2147")
											+ " "
											+ AcideMainWindow.getInstance().getFileEditorManager()
											.getTabbedPane().getTitleAt(editorIndex));
					
					// Brings the main window to foreground
					//AcideMainWindow.getInstance().setAlwaysOnTop(false);
					
					/*AcideSearchInformationWindow.getInstance().setLabel(
							AcideLanguageManager.getInstance()
							.getLabels().getString("s2147")
							+ " "
							+ AcideMainWindow.getInstance().getFileEditorManager()
							.getTabbedPane().getTitleAt(editorIndex) + "...");
					AcideSearchInformationWindow.getInstance().setAlwaysOnTop(true);*/
					
					// Gets the original caret position
					caretPosition = AcideMainWindow.getInstance()
							.getFileEditorManager().getSelectedFileEditorPanel()
							.getActiveTextEditionArea().getCaretPosition();
					
					AcideMainWindow.getInstance()
					.getFileEditorManager().getSelectedFileEditorPanel()
					.getActiveTextEditionArea().setCaretPosition(0);
					
					_currentDocumentRadioButton.setSelected(true);

					// Gets the number of replacements
					_localNumberOfReplacements = countMatches(AcideMainWindow
							.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(editorIndex)
							.getActiveTextEditionArea().getText(),
							_textToSearch,
							_caseSensitiveCheckBox.isSelected());

					// If there are replacements
					if (_localNumberOfReplacements > 0) {

						// Updates the number of replacements
						_globalNumberOfReplacements += _localNumberOfReplacements;
						
				
						for (int i = _localNumberOfReplacements; i > 0; i--){
							
							AcideReplaceWindow.getInstance()
							.setProgress((_localNumberOfReplacements - 1/_auxNumberTotals));
							Runnable runnable = new thread1();
							Thread hilo = new Thread(runnable);
							hilo.start();
							//repaint();
							// If it is the first replacement
							if (_isFirstReplacement) {

								// Stores the original caret position
								_originalPosition = caretPosition;

								// Performs the search button action
								//_searchButton.doClick();
								searchAction();
							}
						
							//AcideSearchInformationWindow.getInstance().setVisible(false);
							
							// If the regular expression is wrong
							if (_searchResultPosition == -2){
								
								// Puts the default cursor
								AcideReplaceWindow.getInstance().setCursor(
										Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								
								AcideMainWindow.getInstance().setCursor(
										Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								
								return;
							}
							if (_searchResultPosition < _originalPosition){
								_replacementsBeforeCaretPosition++;
							}

							// Gets the selected text from the file editor
							String selectedText = AcideMainWindow.getInstance()
									.getFileEditorManager()
									.getFileEditorPanelAt(editorIndex)
									.getActiveTextEditionArea().getSelectedText();
							
							
							// If it found anything out
							if (selectedText != null) {

								// Gets the replace text
								_replaceText = "" + _textToReplace;

								// If the respect capitalization is selected
								if (_respectCapitalizationCheckBox.isSelected())

									// Gets the replace text to use
									_replaceText = "" + respectCapitalization(selectedText);

								// Replaces the string
								AcideMainWindow.getInstance().getFileEditorManager()
										.getFileEditorPanelAt(editorIndex)
										.getActiveTextEditionArea()
										.replaceSelection(_replaceText);
								
								// Sets the caret position
								AcideMainWindow
										.getInstance()
										.getFileEditorManager()
										.getFileEditorPanelAt(
												editorIndex)
										.getActiveTextEditionArea()
										.setCaretPosition(_searchResultPosition + _replaceText.length());
								
								caretPosition = AcideMainWindow.getInstance()
										.getFileEditorManager().getSelectedFileEditorPanel()
										.getActiveTextEditionArea().getCaretPosition();
								
								// It is not the first replacement
								_isFirstReplacement = false;
								
								if (i > 1){
								// Performs the search button action
								//_searchButton.doClick();
								searchAction();
								}
								
								//AcideSearchInformationWindow.getInstance().setVisible(false);
						}
					}
						
						
				
					}//fin if _localNumberOfReplacements > 0
				
					// Updates the log
					AcideLog.getLog().info(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s582")
									+ _textToSearch
									+ AcideLanguageManager.getInstance()
											.getLabels().getString("s580")
									+ _replaceText);
					
					// Gets the current content
					String fileContent = AcideMainWindow.getInstance()
							.getFileEditorManager()
							.getFileEditorPanelAt(editorIndex)
							.getTextEditionAreaContent();
					
					// If has been changes in the file
					if (!AcideMainWindow.getInstance().getFileEditorManager()
							.getFileEditorPanelAt(editorIndex)
							.isEqualToFileDiskCopy(fileContent)) {

						// Sets the red icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setRedButtonAt(editorIndex);
						
					} else {

						// Sets the green icon to the close button
						AcideMainWindow.getInstance().getFileEditorManager()
								.setGreenButtonAt(editorIndex);
					}
			
				int auxCaret = _originalPosition + 
						(_textToReplace.length() - _textToSearch.length()) * _replacementsBeforeCaretPosition;
				
				if (auxCaret < 0){
					auxCaret = 0;
				}
				// Sets the original caret position
				AcideMainWindow
						.getInstance()
						.getFileEditorManager()
						.getFileEditorPanelAt(
								editorIndex)
						.getActiveTextEditionArea()
						.setCaretPosition(auxCaret);
				
				_isFirstReplacement = true;
				
				
				
				}//B�squeda para cada documento
				
				AcideReplaceWindow.getInstance()
				.setProgress(100);
				Runnable runnable = new thread1();
				Thread hilo = new Thread(runnable);
				hilo.start();
				//	repaint();
				// Sets the original selected editor index
				AcideMainWindow.getInstance().getFileEditorManager()
						.setSelectedFileEditorPanelAt(originalSelectedEditorIndex);

				// Updates the related components
				AcideMainWindow.getInstance().getFileEditorManager()
						.updateRelatedComponentsAt(originalSelectedEditorIndex);
				
				_allDocumentsRadioButton.setSelected(true);
			}
			
			
			// Informs of the number of replacements
						JOptionPane.showMessageDialog(
								AcideReplaceWindow.getInstance(),
								AcideLanguageManager.getInstance().getLabels()
										.getString("s1000")
										+ " " + _globalNumberOfReplacements, AcideLanguageManager
										.getInstance().getLabels().getString("s572"),
								JOptionPane.INFORMATION_MESSAGE);

						// Updates the status message in the status bar
						AcideMainWindow
								.getInstance()
								.getStatusBar()
								.setStatusMessage(
										AcideLanguageManager.getInstance().getLabels()
												.getString("s1000")
												+ _globalNumberOfReplacements);

						// Restores the original selected file editor panel
						AcideMainWindow.getInstance().getFileEditorManager()
								.setSelectedFileEditorPanelAt(selectedFileEditorPanelIndex);

						// Restores the original caret position
						AcideMainWindow.getInstance().getFileEditorManager()
								.getSelectedFileEditorPanel().getActiveTextEditionArea()
								.setCaretPosition(originalCaretPosition);

						// Puts the default cursor
						AcideReplaceWindow.getInstance().setCursor(
								Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						
						AcideMainWindow.getInstance().setCursor(
								Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						
						_replaceAll = false;
			
		}
		/*public void actionPerformed(ActionEvent actionEvent) {

			// Puts the wait cursor
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			_textToSearch = _searchTextField.getText();
			
			_textToReplace = _replaceTextField.getText();
			
			if (!getList().contains(_textToSearch)){
				getList().add(_textToSearch);
			}
			
			if (!getListReplace().contains(_textToReplace)){
				getListReplace().add(_textToReplace);
			}
			
			_textToSearch = _textToSearch.replaceAll("\\^p","\n\n");
			_textToSearch = _textToSearch.replaceAll("\\^t", "\t");
			
			_textToReplace = _textToReplace.replaceAll("\\^p","\n\n");
			_textToReplace = _textToReplace.replaceAll("\\^t", "\t");

			// Gets the original caret position
			int caretPosition = AcideMainWindow.getInstance()
					.getFileEditorManager().getSelectedFileEditorPanel()
					.getActiveTextEditionArea().getCaretPosition();

			// Gets the selected file editor panel index
			int selectedFileEditorPanelIndex = AcideMainWindow.getInstance()
					.getFileEditorManager().getSelectedFileEditorPanelIndex();

			// Initializes the number of replacements
			_globalNumberOfReplacements = 0;

			// Gets the replace text
			_replaceText = "" + _textToReplace;

			// If the respect capitalization is selected
			if (_respectCapitalizationCheckBox.isSelected())

				// Gets the replace text to use
				_replaceText = ""
						+ respectCapitalization(_textToSearch);

			// If the selected text radio button is selected
			if (_selectedTextRadioButton.isSelected())

				// Performs the selected text replace
				selectedTextReplace();

			// If the current document radio button is selected
			if (_currentDocumentRadioButton.isSelected())

				// Performs the current document replace
				currentDocumentReplace();

			// If the all documents radio button is selected
			if (_allDocumentsRadioButton.isSelected())

				// Performs the all document replace
				allDocumentsReplace();

			// Informs of the number of replacements
			JOptionPane.showMessageDialog(
					AcideReplaceWindow.getInstance(),
					AcideLanguageManager.getInstance().getLabels()
							.getString("s1000")
							+ _globalNumberOfReplacements, AcideLanguageManager
							.getInstance().getLabels().getString("s572"),
					JOptionPane.INFORMATION_MESSAGE);

			// Updates the status message in the status bar
			AcideMainWindow
					.getInstance()
					.getStatusBar()
					.setStatusMessage(
							AcideLanguageManager.getInstance().getLabels()
									.getString("s1000")
									+ _globalNumberOfReplacements);

			// Restores the original selected file editor panel
			AcideMainWindow.getInstance().getFileEditorManager()
					.setSelectedFileEditorPanelAt(selectedFileEditorPanelIndex);

			// Restores the original caret position
			AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getActiveTextEditionArea()
					.setCaretPosition(caretPosition);

			// Puts the default cursor
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));	
		}*/

		

		/**
		 * Performs the selected text replace.
		 */
//		private void selectedTextReplace() {
//
//			// Initializes the variables
//			String selectedEditorText = null;
//			String textReplaced = null;
//			String textBeforeSelectedText = null;
//			String textAfterSelectedText = null;
//
//			// Gets the selected editor index
//			int selectedEditorIndex = AcideMainWindow.getInstance()
//					.getFileEditorManager().getSelectedFileEditorPanelIndex();
//
//			// Gets the selected editor text size
//			int selectedEditorTextSize = AcideMainWindow.getInstance()
//					.getFileEditorManager()
//					.getFileEditorPanelAt(selectedEditorIndex)
//					.getActiveTextEditionArea().getText().length();
//			
//			// Brings the main window to foreground
//			AcideMainWindow.getInstance().setAlwaysOnTop(true);
//			
//			// Updates the status message in the status bar in the
//			// ACIDE - A Configurable IDE main window
//			AcideMainWindow
//					.getInstance()
//					.getStatusBar()
//					.setStatusMessage(
//							AcideLanguageManager.getInstance()
//									.getLabels().getString("s2146")
//									+ " "
//									+ AcideMainWindow.getInstance().getFileEditorManager()
//									.getTabbedPane().getTitleAt(selectedEditorIndex));
//			
//			// Brings the main window to foreground
//			AcideMainWindow.getInstance().setAlwaysOnTop(false);
//
//			// If there is anything selected
//			if (AcideMainWindow.getInstance().getFileEditorManager()
//					.getFileEditorPanelAt(selectedEditorIndex)
//					.getActiveTextEditionArea().getSelectedText() != null) {
//
//				// Gets the number of replacements
//				_globalNumberOfReplacements = countMatches(AcideMainWindow
//						.getInstance().getFileEditorManager()
//						.getFileEditorPanelAt(selectedEditorIndex)
//						.getActiveTextEditionArea().getSelectedText(),
//						_textToSearch,
//						_caseSensitiveCheckBox.isSelected());
//
//				// If there are replacements
//				if (_globalNumberOfReplacements > 0) {
//
//					// Gets the selection start
//					int selectionStart = AcideMainWindow.getInstance()
//							.getFileEditorManager()
//							.getFileEditorPanelAt(selectedEditorIndex)
//							.getActiveTextEditionArea().getSelectionStart();
//
//					// Gets the selected editor text
//					selectedEditorText = AcideMainWindow.getInstance()
//							.getFileEditorManager()
//							.getFileEditorPanelAt(selectedEditorIndex)
//							.getActiveTextEditionArea().getSelectedText();
//
//					// Gets the selection size
//					int selectionSize = selectionStart
//							+ selectedEditorText.length();
//
//					// Gets the text from the selected file editor panel
//					String text = AcideMainWindow.getInstance()
//							.getFileEditorManager()
//							.getFileEditorPanelAt(selectedEditorIndex)
//							.getActiveTextEditionArea().getText();
//
//					// Gets the text before the selected text
//					textBeforeSelectedText = text.substring(0, selectionStart);
//
//					// Gets the text after the selected text
//					textAfterSelectedText = text.substring(selectionSize,
//							selectedEditorTextSize);
//
//					// If the case sensitive check box is selected
//					if (_caseSensitiveCheckBox.isSelected()) {
//
//						// Replaces all the occurrences without taking care of
//						// the case sensitive
//						textReplaced = textBeforeSelectedText
//								//.replaceAll(
//								//_textToSearch, _replaceText)
//								+ selectedEditorText.replaceAll(
//										_textToSearch,
//										_replaceText)
//								+ textAfterSelectedText
//								//.replaceAll(
//								//		_textToSearch, _replaceText)
//								;
//					} else
//
//						// Prepends the case insensitive pattern modifier
//						// (?i)
//						// before our regex to indicate that we don�t care
//						// about the case sensitivity of the regex.
//						textReplaced = textBeforeSelectedText
//						//.replaceAll("(?i)"
//							//	+ _textToSearch, _replaceText)
//								+ selectedEditorText.replaceAll("(?i)"
//										+ _textToSearch,
//										_replaceText)
//								+ textAfterSelectedText
//								//.replaceAll("(?i)"
//									//	+ _textToSearch,
//										//_replaceText)
//								;
//
//					// Updates the selected editor text with the text
//					AcideMainWindow.getInstance().getFileEditorManager()
//							.getFileEditorPanelAt(selectedEditorIndex)
//							.getActiveTextEditionArea().setText(textReplaced);
//				}
//				
//				// Gets the current content
//				String fileContent = AcideMainWindow.getInstance()
//						.getFileEditorManager()
//						.getFileEditorPanelAt(selectedEditorIndex)
//						.getTextEditionAreaContent();
//				
//				// If has been changes in the file
//				if (!AcideMainWindow.getInstance().getFileEditorManager()
//						.getFileEditorPanelAt(selectedEditorIndex)
//						.isEqualToFileDiskCopy(fileContent)) {
//
//					// Sets the red icon to the close button
//					AcideMainWindow.getInstance().getFileEditorManager()
//							.setRedButtonAt(selectedEditorIndex);
//					
//					
//					/*// Do you want to save it?
//					AcideReplaceWindow.getInstance().setAlwaysOnTop(false);
//					int returnValue = JOptionPane.showConfirmDialog(null,
//							AcideLanguageManager.getInstance().getLabels()
//									.getString("s643"),
//							AcideLanguageManager.getInstance().getLabels()
//									.getString("s953"),
//							JOptionPane.YES_NO_CANCEL_OPTION);
//
//					// If it is OK
//					if (returnValue == JOptionPane.OK_OPTION) {
//
//						// Saves the file editor panel
//						AcideMainWindow.getInstance().getMenu()
//								.getFileMenu().saveFile(selectedEditorIndex);
//					} 
//					AcideReplaceWindow.getInstance().setAlwaysOnTop(true);*/
//				} else {
//
//					// Sets the green icon to the close button
//					AcideMainWindow.getInstance().getFileEditorManager()
//							.setGreenButtonAt(selectedEditorIndex);
//				}
//			}
//		}

		/**
		 * Performs the current document replace.
		 */
//		private void currentDocumentReplace() {
//
//			String selectedEditorText = null;
//
//			// Gets the selected editor index
//			int selectedEditorIndex = AcideMainWindow.getInstance()
//					.getFileEditorManager().getSelectedFileEditorPanelIndex();
//			
//			// Brings the main window to foreground
//			AcideMainWindow.getInstance().setAlwaysOnTop(true);
//			
//			// Updates the status message in the status bar in the
//			// ACIDE - A Configurable IDE main window
//			AcideMainWindow
//					.getInstance()
//					.getStatusBar()
//					.setStatusMessage(
//							AcideLanguageManager.getInstance()
//									.getLabels().getString("s2147")
//									+ " "
//									+ AcideMainWindow.getInstance().getFileEditorManager()
//									.getTabbedPane().getTitleAt(selectedEditorIndex));
//			
//			// Brings the main window to foreground
//			AcideMainWindow.getInstance().setAlwaysOnTop(false);
//
//			// Gets the number of replacements
//			_globalNumberOfReplacements = countMatches(
//					AcideMainWindow.getInstance().getFileEditorManager()
//							.getFileEditorPanelAt(selectedEditorIndex)
//							.getActiveTextEditionArea().getText(),
//					_textToSearch,
//					_caseSensitiveCheckBox.isSelected());
//
//			// If there are replacements
//			if (_globalNumberOfReplacements > 0) {
//
//				// Gets the selected editor index
//				selectedEditorIndex = AcideMainWindow.getInstance()
//						.getFileEditorManager()
//						.getSelectedFileEditorPanelIndex();
//
//				// Gets the selected editor text
//				selectedEditorText = AcideMainWindow.getInstance()
//						.getFileEditorManager()
//						.getFileEditorPanelAt(selectedEditorIndex)
//						.getActiveTextEditionArea().getText();
//
//				// If the case sensitive check box
//				if (_caseSensitiveCheckBox.isSelected())
//
//					// Replaces all the occurrences without taking care of
//					// the case sensitive
//					AcideMainWindow
//							.getInstance()
//							.getFileEditorManager()
//							.getFileEditorPanelAt(selectedEditorIndex)
//							.getActiveTextEditionArea()
//							.setText(
//									selectedEditorText.replaceAll(
//											_textToSearch,
//											_replaceText));
//				else
//					// Prepends the Case-insensitve pattern modifier (?i)
//					// before our regex to indicate that we don�t care
//					// about the case sensitivity of the regex.
//					AcideMainWindow
//							.getInstance()
//							.getFileEditorManager()
//							.getFileEditorPanelAt(selectedEditorIndex)
//							.getActiveTextEditionArea()
//							.setText(
//									selectedEditorText.replaceAll("(?i)"
//											+ _textToSearch,
//											_replaceText));
//				// Updates the log
//				AcideLog.getLog().info(
//						AcideLanguageManager.getInstance().getLabels()
//								.getString("s582")
//								+ _textToSearch
//								+ AcideLanguageManager.getInstance()
//										.getLabels().getString("s580")
//								+ _replaceText);
//			}
//			
//			// Gets the current content
//			String fileContent = AcideMainWindow.getInstance()
//					.getFileEditorManager()
//					.getFileEditorPanelAt(selectedEditorIndex)
//					.getTextEditionAreaContent();
//			
//			// If has been changes in the file
//			if (!AcideMainWindow.getInstance().getFileEditorManager()
//					.getFileEditorPanelAt(selectedEditorIndex)
//					.isEqualToFileDiskCopy(fileContent)) {
//
//				// Sets the red icon to the close button
//				AcideMainWindow.getInstance().getFileEditorManager()
//						.setRedButtonAt(selectedEditorIndex);
//				
//				
//				/*// Do you want to save it?
//				AcideReplaceWindow.getInstance().setAlwaysOnTop(false);
//				int returnValue = JOptionPane.showConfirmDialog(null,
//						AcideLanguageManager.getInstance().getLabels()
//								.getString("s643"),
//						AcideLanguageManager.getInstance().getLabels()
//								.getString("s953"),
//						JOptionPane.YES_NO_CANCEL_OPTION);
//
//				// If it is OK
//				if (returnValue == JOptionPane.OK_OPTION) {
//
//					// Saves the file editor panel
//					AcideMainWindow.getInstance().getMenu()
//							.getFileMenu().saveFile(selectedEditorIndex);
//				} 
//				AcideReplaceWindow.getInstance().setAlwaysOnTop(true);*/
//			} else {
//
//				// Sets the green icon to the close button
//				AcideMainWindow.getInstance().getFileEditorManager()
//						.setGreenButtonAt(selectedEditorIndex);
//			}
//		}

		/**
		 * Performs the all documents replace.
		 */
//		private void allDocumentsReplace() {
//
//			String selectedEditorContent = null;
//			
//			// Gets the original caret position
//			int caretPosition = 0;
//			
//			// Gets the original selected editor index
//			int originalSelectedEditorIndex = AcideMainWindow.getInstance()
//					.getFileEditorManager().getSelectedFileEditorPanelIndex();
//
//			// Initializes the number of replacements
//			_globalNumberOfReplacements = 0;
//
//			// For each one of the opened editors
//			for (int editorIndex = 0; editorIndex < AcideMainWindow
//					.getInstance().getFileEditorManager()
//					.getNumberOfFileEditorPanels(); editorIndex++) {
//
//				// Gets the selected editor content
//				selectedEditorContent = AcideMainWindow.getInstance()
//						.getFileEditorManager()
//						.getFileEditorPanelAt(editorIndex)
//						.getTextEditionAreaContent();
//		
//
//				// Sets the selected editor at the current editor
//				AcideMainWindow.getInstance().getFileEditorManager()
//						.setSelectedFileEditorPanelAt(editorIndex);
//				
//				// Brings the main window to foreground
//				AcideMainWindow.getInstance().setAlwaysOnTop(true);
//				
//				// Updates the status message in the status bar in the
//				// ACIDE - A Configurable IDE main window
//				AcideMainWindow
//						.getInstance()
//						.getStatusBar()
//						.setStatusMessage(
//								AcideLanguageManager.getInstance()
//										.getLabels().getString("s2147")
//										+ " "
//										+ AcideMainWindow.getInstance().getFileEditorManager()
//										.getTabbedPane().getTitleAt(editorIndex));
//				
//				// Brings the main window to foreground
//				AcideMainWindow.getInstance().setAlwaysOnTop(false);
//				
//				// Gets the original caret position
//				caretPosition = AcideMainWindow.getInstance()
//						.getFileEditorManager().getSelectedFileEditorPanel()
//						.getActiveTextEditionArea().getCaretPosition();
//
//				// Gets the number of replacements
//				_localNumberOfReplacements = countMatches(AcideMainWindow
//						.getInstance().getFileEditorManager()
//						.getFileEditorPanelAt(editorIndex)
//						.getActiveTextEditionArea().getText(),
//						_textToSearch,
//						_caseSensitiveCheckBox.isSelected());
//
//				// If there are replacements
//				if (_localNumberOfReplacements > 0) {
//
//					// Updates the number of replacements
//					_globalNumberOfReplacements += _localNumberOfReplacements;
//
//					// If the case sensitive check box is selected
//					if (_caseSensitiveCheckBox.isSelected())
//
//						// Replaces all the occurrences without taking care of
//						// the case sensitive
//						AcideMainWindow
//								.getInstance()
//								.getFileEditorManager()
//								.getFileEditorPanelAt(editorIndex)
//								.getActiveTextEditionArea()
//								.setText(
//										selectedEditorContent.replaceAll(
//												_textToSearch,
//												_replaceText));
//					else
//						// Prepends the Case-insensitve pattern modifier
//						// (?i)
//						// before our regex to indicate that we don�t care
//						// about the case sensitivity of the regex.
//						AcideMainWindow
//								.getInstance()
//								.getFileEditorManager()
//								.getFileEditorPanelAt(editorIndex)
//								.getActiveTextEditionArea()
//								.setText(
//										selectedEditorContent.replaceAll("(?i)"
//												+ _textToSearch,
//												_replaceText));
//					
//					selectedEditorContent = AcideMainWindow.getInstance()
//							.getFileEditorManager()
//							.getFileEditorPanelAt(editorIndex)
//							.getTextEditionAreaContent();
//
//					
//					// If has been changes in the file
//					if (!AcideMainWindow.getInstance().getFileEditorManager()
//							.getFileEditorPanelAt(editorIndex)
//							.isEqualToFileDiskCopy(selectedEditorContent)) {
//
//						// Sets the red icon to the close button
//						AcideMainWindow.getInstance().getFileEditorManager()
//								.setRedButtonAt(editorIndex);
//						
//					} else {
//
//						// Sets the green icon to the close button
//						AcideMainWindow.getInstance().getFileEditorManager()
//								.setGreenButtonAt(editorIndex);
//					}
//				}
//				
//				AcideMainWindow.getInstance().getFileEditorManager()
//					.getSelectedFileEditorPanel()
//					.getActiveTextEditionArea().setCaretPosition(caretPosition);
//			}
//			
//
//
//			// Sets the original selected editor index
//			AcideMainWindow.getInstance().getFileEditorManager()
//					.setSelectedFileEditorPanelAt(originalSelectedEditorIndex);
//
//			// Updates the related components
//			AcideMainWindow.getInstance().getFileEditorManager()
//					.updateRelatedComponentsAt(originalSelectedEditorIndex);
//		}
	}

	/**
	 * ACIDE - A Configurable IDE replace window cancel button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class CancelButtonAction implements ActionListener {

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
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Closes the window
			AcideReplaceWindow.getInstance().dispose();
		}
	}

	/**
	 * ACIDE - A Configurable IDE replace window escape key action.
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
			AcideReplaceWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			AcideMainWindow.getInstance().setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			// Closes the window
			AcideReplaceWindow.getInstance().dispose();
		}
	}
	
	public static class thread1 implements Runnable{
		public void run(){
			getInstance().setValueBar(getInstance()._cont); 
			getInstance().getBarDo().repaint();
			getInstance().repaint();
			getInstance().validate();
			//System.out.println(getInstance().cont);
			} 
		} 
}
