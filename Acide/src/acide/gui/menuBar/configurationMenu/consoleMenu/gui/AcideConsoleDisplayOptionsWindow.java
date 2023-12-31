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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.AcidePreviewPanel;

/**
 * ACIDE - A Configurable IDE console display options window.
 * 
 * @version 0.11
 * @see JFrame
 */
@SuppressWarnings("rawtypes")
public class AcideConsoleDisplayOptionsWindow extends JFrame {

	/**
	 * ACIDE - A Configurable IDE console display options window class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE console display options window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon(
			"./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE console display options menu item image icon.
	 */
	private final static ImageIcon COLOR_PALETTE_IMAGE = new ImageIcon(
			"./resources/icons/buttons/colorPalette.png");
	/**
	 * Current font size of the console in the ACIDE - A Configurable IDE main
	 * window.
	 */
	private int _initialSize = AcideMainWindow.getInstance().getConsolePanel()
			.getTextPane().getFont().getSize();
	/**
	 * Current font style of the console in the ACIDE - A Configurable IDE main
	 * window.
	 */
	private int _initialStyle = AcideMainWindow.getInstance().getConsolePanel()
			.getTextPane().getFont().getStyle();
	/**
	 * Current font name of the console in the ACIDE - A Configurable IDE main
	 * window.
	 */
	private String _initialFontname = AcideMainWindow.getInstance()
			.getConsolePanel().getTextPane().getFont().getFamily();
	/**
	 * Current foreground color of the console in the ACIDE - A Configurable IDE
	 * main window.
	 */
	private Color _initialForegroundColor = AcideMainWindow.getInstance()
			.getConsolePanel().getTextPane().getForeground();
	/**
	 * Current background color of the console in the ACIDE - A Configurable IDE
	 * main window.
	 */
	private Color _initialBackgroundColor = AcideMainWindow.getInstance()
			.getConsolePanel().getTextPane().getBackground();
	/**
	 * Current buffer size of the console in the ACIDE - A Configurable IDE main
	 * window.
	 */
	private int _initialBufferSize = AcideMainWindow.getInstance()
			.getConsolePanel().getBufferSize();
	/**
	 * Where the sample text is displayed.
	 */
	private AcidePreviewPanel _displayArea;
	/**
	 * ACIDE - A Configurable IDE console display options window preview panel
	 * which contains the display area.
	 */
	private JPanel _previewPanel;
	/**
	 * ACIDE - A Configurable IDE console display options window font size combo
	 * box.
	 */
	private JComboBox _fontSizeComboBox;
	/**
	 * ACIDE - A Configurable IDE console display options window font name combo
	 * box.
	 */
	private JComboBox _fontNameComboBox;
	/**
	 * ACIDE - A Configurable IDE console display options window controls panel.
	 */
	private JPanel _controlsPanel;
	/**
	 * ACIDE - A Configurable IDE console display options window color buttons
	 * panel.
	 */
	private JPanel _colorButtonsPanel;
	/**
	 * ACIDE - A Configurable IDE console display options window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE console display options window accept button.
	 */
	private JButton _acceptButton;
	/**
	 * ACIDE - A Configurable IDE console display options window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE console display options window font name
	 * label.
	 */
	private JLabel _fontNameLabel;
	/**
	 * ACIDE - A Configurable IDE console display options window background
	 * label.
	 */
	private JLabel _backgroundColorLabel;
	/**
	 * ACIDE - A Configurable IDE console display options window foreground
	 * label.
	 */
	private JLabel _foregroundColorLabel;
	/**
	 * ACIDE - A Configurable IDE console display options window font size
	 * label.
	 */
	private JLabel _fontSizeLabel;
	/**
	 * ACIDE - A Configurable IDE console display options window font type
	 * label.
	 */
	private JLabel _fontStyleLabel;
	/**
	 * ACIDE - A Configurable IDE console display options window font style
	 * combo box.
	 */
	private JComboBox _fontStyleComboBox;
	/**
	 * ACIDE - A Configurable IDE console display options window foreground
	 * button.
	 */
	private JButton _foregroundColorButton;
	/**
	 * ACIDE - A Configurable IDE console display options window background
	 * button.
	 */
	private JButton _backgroundColorButton;
	/**
	 * ACIDE - A Configurable IDE console display options window restore default
	 * configuration.
	 */
	private JButton _restoreDefaultConfiguration;
	/**
	 * ACIDE - A Configurable IDE console display options window buffer size
	 * label.
	 */
	private JLabel _bufferSizeLabel;
	/**
	 * ACIDE - A Configurable IDE console display options window buffer size
	 * text field.
	 */
	private JTextField _bufferSizeTextField;

	/**
	 * Creates a new ACIDE - A Configurable IDE console display options window.
	 */
	public AcideConsoleDisplayOptionsWindow() {

		super();

		// Builds the window components
		buildComponents();

		// Sets the listeners of the window components
		setListeners();

		// Adds the components to the window
		addComponents();

		// Sets the window configuration
		setWindowConfiguration();

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s978"));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE console display options window
	 * configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the window title
		setTitle(AcideLanguageManager.getInstance().getLabels()
				.getString("s977"));

		// Sets the window icon image
		setIconImage(ICON.getImage());

		// Sets the window not resizable
		setResizable(false);

		// Packs the window components
		pack();

		// Centers the window
		setLocationRelativeTo(null);

		// Sets the window visible
		setVisible(true);

		// Disables the main window
		AcideMainWindow.getInstance().setEnabled(false);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console display options window
	 * components.
	 */
	private void buildComponents() {

		// Creates the controls panel
		_controlsPanel = new JPanel(new GridBagLayout());

		// Sets the controls panel border
		_controlsPanel.setBorder(BorderFactory
				.createTitledBorder(AcideLanguageManager.getInstance()
						.getLabels().getString("s1010")));

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the color buttons panel
		_colorButtonsPanel = new JPanel(new GridBagLayout());

		// Creates the font name label
		_fontNameLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s981"));

		// Creates the font combo box
		getFontComboBox();

		// Creates the font style label
		_fontStyleLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s983"), JLabel.CENTER);

		// Create the font style combo box
		getFontStyleComboBox();

		// Creates the font size lable
		_fontSizeLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s982"));

		// Creates the font size combo box
		createFontSizeComboBox();

		// Creates the foreground color label
		_foregroundColorLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s984"));

		// Creates the foreground color button
		_foregroundColorButton = new JButton(COLOR_PALETTE_IMAGE);

		// Creates the background color label
		_backgroundColorLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s985"));

		// Creates the background color button
		_backgroundColorButton = new JButton(COLOR_PALETTE_IMAGE);

		// Creates the accept button
		_acceptButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s445"));

		// Adds the accept button to the button panel
		_buttonPanel.add(_acceptButton);

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s446"));

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);

		// Creates the preview panel
		_previewPanel = new JPanel();

		// Sets the preview panel border
		_previewPanel.setBorder(BorderFactory
				.createTitledBorder(AcideLanguageManager.getInstance()
						.getLabels().getString("s1011")));

		// Creates a panel where display the fonts
		_displayArea = new AcidePreviewPanel(_initialFontname, _initialStyle,
				_initialSize, _initialForegroundColor, _initialBackgroundColor);

		// Adds the display area to the preview panel
		_previewPanel.add(_displayArea);

		// Creates the restore default configuration
		_restoreDefaultConfiguration = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s1095"));

		// Creates the buffer size label
		_bufferSizeLabel = new JLabel(AcideLanguageManager.getInstance()
				.getLabels().getString("s2017"));

		// Creates the buffer size text field
		_bufferSizeTextField = new JTextField(
				Integer.toString(_initialBufferSize), 7);
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE console display
	 * options window with the layout.
	 */
	private void addComponents() {

		// Sets the layout
		setLayout(new BorderLayout());

		// Adds the components to the window with the layout
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.gridx = 0;
		constraints.gridy = 0;

		// Adds the font name label to the controls panel
		_controlsPanel.add(_fontNameLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		// Adds the font name combo box to the controls panel
		_controlsPanel.add(_fontNameComboBox, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;

		// Adds the font size label to the controls panel
		_controlsPanel.add(_fontSizeLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;

		// Adds the size slider to the controls panel
		_controlsPanel.add(_fontSizeComboBox, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 2;

		// Adds the font style label to the controls panel
		_controlsPanel.add(_fontStyleLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;

		// Adds the font style combo box to the controls panel
		_controlsPanel.add(_fontStyleComboBox, constraints);

		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = 0;

		// Adds the foreground color label to the color buttons panel
		_colorButtonsPanel.add(_foregroundColorLabel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 1;
		constraints.gridy = 0;

		// Adds the foreground color button to the color buttons panel
		_colorButtonsPanel.add(_foregroundColorButton, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 0;
		constraints.gridy = 1;

		// Adds the background color label to the color buttons panel
		_colorButtonsPanel.add(_backgroundColorLabel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 1;
		constraints.gridy = 1;

		// Adds the background color button to the color buttons panel
		_colorButtonsPanel.add(_backgroundColorButton, constraints);

		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 4;

		// Adds the color buttons panel to the controls panel
		_controlsPanel.add(_colorButtonsPanel, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 5;

		// Adds the buffer size label to the controls panel
		_controlsPanel.add(_bufferSizeLabel, constraints);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 1;
		constraints.gridy = 5;

		// Adds the buffer size text field to the controls panel
		_controlsPanel.add(_bufferSizeTextField, constraints);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 6;

		// Adds the restore default configuration button to the controls panel
		_controlsPanel.add(_restoreDefaultConfiguration, constraints);

		// Adds the controls to the window
		add(_controlsPanel, BorderLayout.NORTH);

		// Adds the preview panel to the window
		add(_previewPanel, BorderLayout.CENTER);

		// Adds the button panel to the window
		add(_buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE console display
	 * options window components.
	 */
	public void setListeners() {

		// Sets the foreground color button action listener
		_foregroundColorButton
				.addActionListener(new ForegroundColorButtonAction());

		// Sets the background color button action listener
		_backgroundColorButton
				.addActionListener(new BackgroundColorButtonAction());

		// Sets the accept button action listener
		_acceptButton.addActionListener(new AcceptButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());

		// Sets the font style combo box action listener
		_fontStyleComboBox.addActionListener(new FontStyleComboBoxAction());

		// Sets the size slider change listener
		_fontSizeComboBox.addActionListener(new FontSizeComboBoxAction());

		// Sets the font name combo box action listener
		_fontNameComboBox.addActionListener(new FontComboBoxAction());

		// Sets the restore default configuration action listener
		_restoreDefaultConfiguration
				.addActionListener(new RestoreDefaultConfigurationButtonAction());

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
	 * Creates the font style combo box.
	 */
	@SuppressWarnings("unchecked")
	public void getFontStyleComboBox() {

		// Creates the font style combo box
		_fontStyleComboBox = new JComboBox();

		// Adds the font plain item to the font style combo box
		_fontStyleComboBox.addItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s413"));

		// Adds the font italic item to the font style combo box
		_fontStyleComboBox.addItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s414"));

		// Adds the font bold item to the font style combo box
		_fontStyleComboBox.addItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s415"));

		// Adds the font bold + italic item to the font style combo box
		_fontStyleComboBox.addItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s416"));

		// Enables the font style combo box
		_fontStyleComboBox.setEnabled(true);

		// Sets the font style combo box tool tip text
		_fontStyleComboBox.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s400"));

		// Selects the item that corresponds
		_fontStyleComboBox.setSelectedItem(_initialStyle);

		// Sets the selected item
		SwingUtilities.invokeLater(new Runnable() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {

				switch (_initialStyle) {
				case Font.PLAIN:
					_fontStyleComboBox.setSelectedItem(AcideLanguageManager
							.getInstance().getLabels().getString("s413"));
					break;
				case Font.ITALIC:
					_fontStyleComboBox.setSelectedItem(AcideLanguageManager
							.getInstance().getLabels().getString("s414"));
					break;
				case Font.BOLD:
					_fontStyleComboBox.setSelectedItem(AcideLanguageManager
							.getInstance().getLabels().getString("s415"));
					break;
				case Font.BOLD + Font.ITALIC:
					_fontStyleComboBox.setSelectedItem(AcideLanguageManager
							.getInstance().getLabels().getString("s416"));
					break;
				}
			}
		});
	}

	/**
	 * Creates and configures the font size combo box.
	 */
	@SuppressWarnings("unchecked")
	public void createFontSizeComboBox() {

		// Creates the values for the combo box
		String[] values = { "8", "9", "10", "11", "12", "14", "16", "20", "24",
				"32", "48", "72" };

		// Creates the font size combo box
		_fontSizeComboBox = new JComboBox(values);

		// Sets the font size combo box as editable
		_fontSizeComboBox.setEditable(true);

		// Selects the font size combo box selected item as the initial size
		_fontSizeComboBox.setSelectedItem(String.valueOf(_initialSize));
	}

	/**
	 * Creates the font combo box.
	 */
	@SuppressWarnings("unchecked")
	public void getFontComboBox() {

		// Gets all the font families
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();

		// Makes the vector with all fonts that can display the basic chars
		Vector<String> availableFonts = new Vector<String>(fontNames.length);

		for (String fontName : fontNames) {

			Font font = new Font(fontName, Font.PLAIN, 12);

			// Display only fonts that have the alphabetic characters
			if (font.canDisplay('a'))
				availableFonts.add(fontName);
		}

		// Creates the font name combo box
		_fontNameComboBox = new JComboBox(availableFonts);

		// Selects the item that corresponds
		_fontNameComboBox.setSelectedItem(_initialFontname);
	}

	/**
	 * Closes the ACIDE - A Configurable IDE console display options window.
	 */
	public void closeWindow() {

		// Enables the main window again
		AcideMainWindow.getInstance().setEnabled(true);

		// Closes window
		dispose();

		// Brings the main window to the front
		AcideMainWindow.getInstance().setAlwaysOnTop(true);

		// But not permanently
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
	}

	/**
	 * ACIDE - A Configurable IDE console display options window foreground
	 * color button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class ForegroundColorButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Ask for the color to the user
			Color foregroundColor = JColorChooser.showDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s992"), _initialForegroundColor);

			// If the user has selected any
			if (foregroundColor != null)

				// Updates the display area foreground color
				_displayArea.setForegroundColor(foregroundColor);
		}
	}

	/**
	 * ACIDE - A Configurable IDE console display options window background
	 * color button action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class BackgroundColorButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Ask for the color to the user
			Color backgroundColor = JColorChooser.showDialog(
					null,
					AcideLanguageManager.getInstance().getLabels()
							.getString("s991"), _initialBackgroundColor);

			// If the user has selected any
			if (backgroundColor != null)

				// Updates the display area background color
				_displayArea.setBackgroundColor(backgroundColor);
		}
	}

	/**
	 * ACIDE - A Configurable IDE console display options window accept button
	 * action listener.
	 * 
	 * @version 0.11
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

			// Updates the log
			AcideLog.getLog().info("989");

			// Sets the new font style
			AcideMainWindow
					.getInstance()
					.getConsolePanel()
					.getTextPane()
					.setFont(
							new Font(_displayArea.getFontName(), _displayArea
									.getFontStyle(), _displayArea.getFontSize()));

			// Sets the new background color
			AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.setBackground(_displayArea.getBackground());

			// Sets the new foreground color
			AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.setForeground(_displayArea.getForeground());

			// Sets the new caret color
			AcideMainWindow.getInstance().getConsolePanel().getTextPane()
					.setCaretColor(_displayArea.getForeground());

			// Sets the new buffer size
			AcideMainWindow
					.getInstance()
					.getConsolePanel()
					.setBufferSize(
							Integer.parseInt(_bufferSizeTextField.getText()));

			// Adjusts the panel content to the new buffer size
			AcideMainWindow.getInstance().getConsolePanel()
					.adjustContentToBufferSize();

			// Updates the font name in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.fontName", _displayArea.getFontName());

			// Updates the font style in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.fontStyle",
					String.valueOf(_displayArea.getFontStyle()));

			// Updates the font size in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.fontSize",
					String.valueOf(_displayArea.getFontSize()));

			// Updates the foreground color in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.foregroundColor",
					Integer.toString(_displayArea.getForeground().getRGB()));

			// Updates the background color in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.backgroundColor",
					Integer.toString(_displayArea.getBackground().getRGB()));

			// Updates the buffer size in the resource manager
			AcideResourceManager.getInstance().setProperty(
					"consolePanel.bufferSize", _bufferSizeTextField.getText());

			// If it is not the default project
			if (!AcideProjectConfiguration.getInstance().isDefaultProject())

				// The project has been modified
				AcideProjectConfiguration.getInstance().setIsModified(true);
			
			//The console panel display has been change
			AcideProjectConfiguration.getInstance().setConsoleIsModified(true);

			// Closes the window
			closeWindow();
		}
	}

	/**
	 * ACIDE - A Configurable IDE console display options window cancel button
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

			// Updates the log
			AcideLog.getLog().info("988");

			// Closes the window
			closeWindow();
		}
	}

	/**
	 * ACIDE - A Configurable IDE console display options window escape key
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
	 * ACIDE - A Configurable IDE console display options window font style
	 * combo box action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class FontStyleComboBoxAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			String selectedItem = (String) _fontStyleComboBox.getSelectedItem();

			if (selectedItem.equals(AcideLanguageManager.getInstance()
					.getLabels().getString("s413")))
				_displayArea.setFontStyle(Font.PLAIN);
			else if (selectedItem.equals(AcideLanguageManager.getInstance()
					.getLabels().getString("s414")))
				_displayArea.setFontStyle(Font.ITALIC);
			else if (selectedItem.equals(AcideLanguageManager.getInstance()
					.getLabels().getString("s415")))
				_displayArea.setFontStyle(Font.BOLD);
			else if (selectedItem.equals(AcideLanguageManager.getInstance()
					.getLabels().getString("s416")))
				_displayArea.setFontStyle(Font.BOLD + Font.ITALIC);
		}
	}

	/**
	 * ACIDE - A Configurable IDE console display options window font size combo
	 * box action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class FontSizeComboBoxAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event
		 * .ChangeEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			try {

				// Try to parse it
				int newValue = Integer.parseInt((String) _fontSizeComboBox
						.getSelectedItem());

				if (newValue > 0)

					// Updates the display area
					_displayArea.setFontSize(newValue);
				else
					// Displays an error message
					JOptionPane.showMessageDialog(null, AcideLanguageManager
							.getInstance().getLabels().getString("s2003"),
							"Error", JOptionPane.ERROR_MESSAGE);

			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager
						.getInstance().getLabels().getString("s2003"), "Error",
						JOptionPane.ERROR_MESSAGE);

				// Updates the log
				AcideLog.getLog().info(exception.getMessage());
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE console display options window font combo box
	 * action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class FontComboBoxAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			_displayArea.setFontName((String) _fontNameComboBox
					.getSelectedItem());
		}
	}

	/**
	 * ACIDE - A Configurable IDE console display options window restore default
	 * configuration action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class RestoreDefaultConfigurationButtonAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Sets the default font name
			_displayArea.setFontName("monospaced");

			// Sets the default font style
			_displayArea.setFontStyle(Font.PLAIN);

			// Sets the default font size
			_displayArea.setFontSize(12);

			// Sets the background color
			_displayArea.setBackground(Color.WHITE);

			// Sets the foreground color
			_displayArea.setForeground(Color.BLACK);

			// Sets the buffer size
			_bufferSizeTextField.setText("1024");
		}
	}
}
