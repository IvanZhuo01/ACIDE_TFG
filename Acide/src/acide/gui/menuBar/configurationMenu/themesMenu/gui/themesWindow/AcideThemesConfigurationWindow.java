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
package acide.gui.menuBar.configurationMenu.themesMenu.gui.themesWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;

import acide.configuration.lexicon.AcideLexiconConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.configuration.workbench.AcideWorkbenchConfiguration;
import acide.configuration.workbench.fileEditor.AcideFileEditorConfiguration;
import acide.configuration.workbench.fileEditor.AcideFileEditorPanelConfiguration;
import acide.gui.graphPanel.AcideGraphUtil;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.configurationMenu.menuMenu.gui.configurationPanel.AcideConfigurationMenuPanel;
import acide.gui.menuBar.configurationMenu.themesMenu.AcideThemesMenu;
import acide.gui.toolBarPanel.consolePanelToolBar.AcideConsolePanelToolBar;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.AcidePreviewPanel;

/**
 * ACIDE - A Configurable IDE file editor display options window.
 * 
 * @version 0.11
 * @see JFrame
 */
@SuppressWarnings("rawtypes")
public class AcideThemesConfigurationWindow extends JFrame {

	/**
	 * ACIDE - A Configurable IDE file editor display options window class serial
	 * version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE file editor display options window image icon.
	 */
	private static final ImageIcon ICON = new ImageIcon("./resources/images/icon.png");
	/**
	 * ACIDE - A Configurable IDE file editor display options window menu item image
	 * icon.
	 */
	private final static ImageIcon COLOR_PALETTE_IMAGE = new ImageIcon("./resources/icons/buttons/colorPalette.png");
	/**
	 * Current font size of the file editor in the main window.
	 */
	private int _initialSize = 12;
	/**
	 * Current font style of the output in the main window.
	 */
	private int _initialStyle = Font.PLAIN;
	/**
	 * Current font name of the output in the main window.
	 */
	private String _initialFontName = "Monospaced";
	/**
	 * Current foreground color of the output in the main window.
	 */
	private Color _initialForegroundColor = Color.BLACK;
	/**
	 * Current background color of the output in the main window.
	 */
	private Color _initialBackgroundColor = Color.WHITE;
	/**
	 * Where the sample text is displayed.
	 */
	private AcidePreviewPanel _displayArea;
	/**
	 * ACIDE - A Configurable IDE file editor display options window preview panel
	 * which contains the display area.
	 */
	private JPanel _previewPanel;
	/**
	 * ACIDE - A Configurable IDE file editor display options window font size combo
	 * box.
	 */
	private JComboBox _fontSizeComboBox;
	/**
	 * ACIDE - A Configurable IDE file editor display options window font name combo
	 * box.
	 */
	private JComboBox _fontNameComboBox;
	/**
	 * ACIDE - A Configurable IDE file editor display options window controls panel.
	 */
	private JPanel _controlsPanel;
	/**
	 * ACIDE - A Configurable IDE file editor display options window color buttons
	 * panel.
	 */
	private JPanel _colorButtonsPanel;
	/**
	 * ACIDE - A Configurable IDE file editor display options window button panel.
	 */
	private JPanel _buttonPanel;
	/**
	 * ACIDE - A Configurable IDE file editor display save options panel.
	 */
	private JPanel _SavePanel;

	private JPanel _PanelSouth;

	/**
	 * ACIDE - A Configurable IDE file editor display options window accept button.
	 */
	private JButton _acceptButton;
	/**
	 * ACIDE - A Configurable IDE file editor display options window cancel button.
	 */
	private JButton _cancelButton;
	/**
	 * ACIDE - A Configurable IDE file editor display options window background
	 * color label.
	 */

	private JLabel _backgroundColorLabel;
	/**
	 * ACIDE - A Configurable IDE file editor display options window foreground
	 * color label.
	 */
	private JLabel _foregroundColorLabel;
	/**
	 * ACIDE - A Configurable IDE file editor display options window font style
	 * combo box.
	 */
	private JComboBox _fontStyleComboBox;
	/**
	 * ACIDE - A Configurable IDE file editor display options window foreground
	 * color button.
	 */
	private JButton _foregroundColorButton;
	/**
	 * ACIDE - A Configurable IDE file editor display options window background
	 * color button.
	 */
	private JButton _backgroundColorButton;
	/**
	 * ACIDE - A Configurable IDE console display options window restore default
	 * configuration.
	 */
	private JButton _restoreDefaultConfiguration;

	/**
	 * ACIDE - A Configurable IDE console display options window save theme
	 */
	private JTextField _themeName;

	/**
	 * Creates a new ACIDE - A Configurable IDE file editor display options window.
	 */
	public AcideThemesConfigurationWindow(JFrame parent) {

		super();

		if (AcideMainWindow.getInstance().getFileEditorManager().getNumberOfFileEditorPanels() > 0) {

			// Gets the initial size from the selected file editor
			_initialSize = AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel()
					.getActiveTextEditionArea().getFont().getSize();

			// Gets the initial style from the selected file editor
			_initialStyle = AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel()
					.getActiveTextEditionArea().getFont().getStyle();

			// Gets the initial font name from the selected file editor
			_initialFontName = AcideMainWindow.getInstance().getFileEditorManager().getSelectedFileEditorPanel()
					.getActiveTextEditionArea().getFont().getFamily();

			// Gets the initial foreground color from the selected file editor
			_initialForegroundColor = AcideMainWindow.getInstance().getExplorerPanel().getTree().getForeground();

			// Gets the initial background color from the selected file editor
			_initialBackgroundColor = AcideMainWindow.getInstance().getExplorerPanel().getTree().getBackground();
		}

		// Builds the window components
		buildComponents();

		// Sets the listeners of the window components
		setListeners();

		// Adds the components to the window
		addComponents();

		// Sets the window configuration
		setWindowConfiguration();

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1042"));
	}

	/**
	 * Sets the ACIDE - A Configurable IDE file editor display options window
	 * configuration.
	 */
	private void setWindowConfiguration() {

		// Sets the window title
		setTitle(AcideLanguageManager.getInstance().getLabels().getString("s2381"));

		// Sets the window icon image
		setIconImage(ICON.getImage());

		// The window is not resizable
		setResizable(false);

		// Packs the window components
		pack();

		// Centers the window
		setLocationRelativeTo(null);

		// Sets the window as visible
		setVisible(true);

		// Disables the main window
		AcideMainWindow.getInstance().setEnabled(false);
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE file editor display
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
		// _controlsPanel.add(_fontNameLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		// Adds the font combo box to the controls panel
		// _controlsPanel.add(_fontNameComboBox, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;

		// Adds the font size label to the controls panel
		// _controlsPanel.add(_fontSizeLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;

		// Adds the size slider to the controls panel
		// _controlsPanel.add(_fontSizeComboBox, constraints);

		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 2;

		// Adds the font style label to the controls panel
		// _controlsPanel.add(_fontStyleLabel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;

		// Adds the font style combo box to the controls panel
		// _controlsPanel.add(_fontStyleComboBox, constraints);

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
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 5;

		// Adds the restore default configuration button to the controls panel
		_controlsPanel.add(_restoreDefaultConfiguration, constraints);

		// Adds the controls panel to the window
		add(_controlsPanel, BorderLayout.NORTH);

		// Adds the preview panel to the window
		add(_previewPanel, BorderLayout.CENTER);

		this._PanelSouth.setLayout(new BorderLayout());

		_PanelSouth.add(_SavePanel, BorderLayout.NORTH);
		_PanelSouth.add(_buttonPanel, BorderLayout.SOUTH);

		add(_PanelSouth, BorderLayout.SOUTH);

	}

	/**
	 * Builds the ACIDE - A Configurable IDE file editor display options window
	 * components.
	 */
	private void buildComponents() {

		// Creates the south panel
		_PanelSouth = new JPanel(new GridBagLayout());

		// Creates the controls panel
		_controlsPanel = new JPanel(new GridBagLayout());

		// Sets the controls panel border
		_controlsPanel.setBorder(
				BorderFactory.createTitledBorder(AcideLanguageManager.getInstance().getLabels().getString("s1010")));

		// Creates the color buttons panel
		_colorButtonsPanel = new JPanel(new GridBagLayout());

		// Creates the foreground color label
		_foregroundColorLabel = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s984"));

		// Creates the foreground color button
		_foregroundColorButton = new JButton(COLOR_PALETTE_IMAGE);

		// Creates the background color label
		_backgroundColorLabel = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s985"));

		// Creates the background color button
		_backgroundColorButton = new JButton(COLOR_PALETTE_IMAGE);

		// Creates the preview panel
		_previewPanel = new JPanel();

		// Sets the preview panel border
		_previewPanel.setBorder(
				BorderFactory.createTitledBorder(AcideLanguageManager.getInstance().getLabels().getString("s1011")));

		// Creates a panel where display the fonts
		_displayArea = new AcidePreviewPanel(_initialFontName, _initialStyle, _initialSize, _initialForegroundColor,
				_initialBackgroundColor);

		// Adds the display area to the preview panel
		_previewPanel.add(_displayArea);

		// Creates the restore default configuration
		_restoreDefaultConfiguration = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s1095"));

		//
		// Builds the button panel
		buildButtonPanel();

		// Builds the save panel
		buildSavePanel();
	}

	/**
	 * Builds the ACIDE - A Configurable IDE file editor display options window
	 * button panel.
	 */
	private void buildButtonPanel() {

		// Creates the button panel
		_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Creates the accept button
		_acceptButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s445"));

		// Adds the accept button to the button panel
		_buttonPanel.add(_acceptButton);

		// Creates the cancel button
		_cancelButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s446"));

		// Adds the cancel button to the button panel
		_buttonPanel.add(_cancelButton);

	}

	private void buildSavePanel() {
		// Creates the button panel
		_SavePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		_SavePanel.setBorder(
				BorderFactory.createTitledBorder(AcideLanguageManager.getInstance().getLabels().getString("s40")));

		// Creates the save name label
		JLabel labelSave = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2382"));
		_SavePanel.add(labelSave);

		// Creates the cancel button
		_themeName = new JTextField();
		_themeName.setPreferredSize(new Dimension(100, 20));

		// Adds the cancel button to the button panel
		_SavePanel.add(_themeName);


	}

	/**
	 * Sets the listeners of the ACIDE - A Configurable IDE file editor display
	 * options window components.
	 */
	public void setListeners() {

		// Sets the foreground color button action listener
		_foregroundColorButton.addActionListener(new ForegroundColorButtonAction());

		// Sets the background color button action listener
		_backgroundColorButton.addActionListener(new BackgroundColorButtonAction());

		// Sets the accept button action listener
		_acceptButton.addActionListener(new AcceptButtonAction());

		// Sets the cancel button action listener
		_cancelButton.addActionListener(new CancelButtonAction());

		// Sets the restore default configuration action listener
		_restoreDefaultConfiguration.addActionListener(new RestoreDefaultConfigurationButtonAction());

		// Sets the window closing listener
		addWindowListener(new AcideWindowClosingListener());

		// Puts the escape key in the input map of the window
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "EscapeKey");

		// Puts the escape key in the action map of the window
		getRootPane().getActionMap().put("EscapeKey", new EscapeKeyAction());
	}

	/**
	 * Closes the ACIDE - A Configurable IDE file editor display options window.
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
	 * ACIDE - A Configurable IDE file editor display options window foreground
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
			Color foregroundColor = JColorChooser.showDialog(null,
					AcideLanguageManager.getInstance().getLabels().getString("s992"), _initialForegroundColor);

			// If the user has selected any
			if (foregroundColor != null)

				// Updates the display area foreground color
				_displayArea.setForegroundColor(foregroundColor);
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window background
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
			Color backgroundColor = JColorChooser.showDialog(null,
					AcideLanguageManager.getInstance().getLabels().getString("s991"), _initialBackgroundColor);

			// If the user has selected any
			if (backgroundColor != null)

				// Updates the display area background color
				_displayArea.setBackgroundColor(backgroundColor);
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window accept button
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
			AcideLog.getLog().info("1043");
			if(!_themeName.getText().isEmpty()){
				int ventanaYesNotCancel = JOptionPane.showConfirmDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2385")
						, AcideLanguageManager.getInstance().getLabels().getString("s2385") , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(ventanaYesNotCancel == 0) {
					saveTheme();
				}
			}

			//Set backGround darker color
			Color darker = new Color((int) (_displayArea.getBackground().getRed() *0.9), (int) (_displayArea.getBackground().getGreen() *0.9), (int) (_displayArea.getBackground().getBlue() *0.9));
			
			// Apply the changes to the opened file editor panels
			AcideMainWindow.getInstance().getFileEditorManager().setBackground(_displayArea.getBackground());
			AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().setOpaque(true);
			AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().setBackground(darker);
			AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane()
					.setForeground(_displayArea.getForeground());
			if(!AcideProjectConfiguration.getInstance().isFileEditorIsModified())
			for (int index = 0; index < AcideMainWindow.getInstance().getFileEditorManager()
					.getNumberOfFileEditorPanels(); index++) {

				// Updates the ACIDE - A Configurable IDE file editor

				AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index)
						.getActiveTextEditionArea().setBackground(_displayArea.getBackground());
				AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index)
						.getActiveTextEditionArea().setForeground(_displayArea.getForeground());
				AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index)
						.changeColor(_displayArea.getBackground(), _displayArea.getForeground(), darker);
			}

			// Apply changes to toolbar
			AcideMainWindow.getInstance().getToolBarPanel().changeColor(darker, _displayArea.getForeground());

			// Apply changes to statusBar
			AcideMainWindow.getInstance().getStatusBar().changeColor(darker, _displayArea.getForeground());

			// Apply changes to menuBar
			AcideMainWindow.getInstance().getMenu().paintMenuBar(darker, _displayArea.getForeground());

			// Apply changes to the database panel
			AcideMainWindow.getInstance().getDataBasePanel().changeColor(_displayArea.getBackground(), _displayArea.getForeground());

			// Apply changes to the explorer panel
			AcideMainWindow.getInstance().getExplorerPanel().setBackgroundColor(_displayArea.getBackground(),
					_displayArea.getForeground(), darker);

			// Apply changes to debugPanel
			AcideMainWindow.getInstance().getDebugPanel().setBackgroundColor(_displayArea.getBackground(),
					_displayArea.getForeground(), darker);
			
			// Apply changes to graph panel
			AcideMainWindow.getInstance().getGraphPanel().setBackgroundColor(_displayArea.getBackground(),
					_displayArea.getForeground(), darker);
			SwingUtilities.invokeLater(() -> AcideGraphUtil.refreshGraphPanel());
			
			// Apply changes to the console panel
			if(!AcideProjectConfiguration.getInstance().isConsoleIsModified()) {
			AcideMainWindow.getInstance().getConsolePanel().changeColor(_displayArea.getBackground(),
					_displayArea.getForeground(), darker);
			AcideResourceManager.getInstance().setProperty("consolePanel.backgroundColor",
					Integer.toString(_displayArea.getBackground().getRGB()));
			AcideResourceManager.getInstance().setProperty("consolePanel.foregroundColor",
					Integer.toString(_displayArea.getForeground().getRGB()));
			}

			// Notify that main configuration has been changed
			AcideProjectConfiguration.getInstance().setIsModified(true);

			// Closes the window
			closeWindow();
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window cancel button
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
			AcideLog.getLog().info("1044");

			// Closes the window
			closeWindow();
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window escape key
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
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			// Closes the window
			closeWindow();
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window font style
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

			if (selectedItem.equals(AcideLanguageManager.getInstance().getLabels().getString("s413")))
				_displayArea.setFontStyle(Font.PLAIN);
			else if (selectedItem.equals(AcideLanguageManager.getInstance().getLabels().getString("s414")))
				_displayArea.setFontStyle(Font.ITALIC);
			else if (selectedItem.equals(AcideLanguageManager.getInstance().getLabels().getString("s415")))
				_displayArea.setFontStyle(Font.BOLD);
			else if (selectedItem.equals(AcideLanguageManager.getInstance().getLabels().getString("s416")))
				_displayArea.setFontStyle(Font.BOLD + Font.ITALIC);
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window font size combo
	 * box action listener.
	 * 
	 * @version 0.11
	 * @see ActionListener
	 */
	class FontSizeComboBoxListener implements ActionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			try {

				// Try to parse it
				int newValue = Integer.parseInt((String) _fontSizeComboBox.getSelectedItem());

				if (newValue > 0)

					// Updates the display area
					_displayArea.setFontSize(newValue);
				else
					// Displays an error message
					JOptionPane.showMessageDialog(null,
							AcideLanguageManager.getInstance().getLabels().getString("s2003"), "Error",
							JOptionPane.ERROR_MESSAGE);

			} catch (Exception exception) {

				// Displays an error message
				JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2003"),
						"Error", JOptionPane.ERROR_MESSAGE);

				// Updates the log
				AcideLog.getLog().info(exception.getMessage());
			}
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window font combo box
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
			_displayArea.setFontName((String) _fontNameComboBox.getSelectedItem());
		}
	}

	/**
	 * ACIDE - A Configurable IDE file editor display options window restore default
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
		}
	}

	public void saveTheme() {
		File folder = new File("./configuration/themes");
		File[] themesFiles = folder.listFiles();
		int i = 0; boolean enUso = false;
		while (i < themesFiles.length && !enUso) {
			String name = themesFiles[i].getName();
			String newTheme = _themeName.getText() + ".properties";
			if(name.equalsIgnoreCase(newTheme)) enUso = true;
			i++;
		}
		if (enUso) {
			JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2384"));
		} else {
			try {
				File file = new File("./configuration/themes/" + _themeName.getText() + ".properties");

				Properties prop = new Properties();
				
				// set the properties value
				prop.setProperty("backgroundColor", String.valueOf(_displayArea.getBackground().getRGB()));
				prop.setProperty("foregroundColor", String.valueOf(_displayArea.getForeground().getRGB()));
				prop.setProperty("ConsolebackgroundColor", String.valueOf(_displayArea.getBackground().getRGB()));
				prop.setProperty("ConsoleforegroundColor", String.valueOf(_displayArea.getForeground().getRGB()));
				prop.setProperty("FileEditorbackgroundColor", String.valueOf(_displayArea.getBackground().getRGB()));
				prop.setProperty("FileEditorforegroundColor", String.valueOf(_displayArea.getForeground().getRGB()));
				
				if(AcideProjectConfiguration.getInstance().isConsoleIsModified()) {
					prop.setProperty("ConsolebackgroundColor", String.valueOf(AcideMainWindow.getInstance().getConsolePanel().getTextPane().getBackground().getRGB()));
					prop.setProperty("ConsoleforegroundColor", String.valueOf(AcideMainWindow.getInstance().getConsolePanel().getTextPane().getForeground().getRGB()));
				}
				
				if(AcideProjectConfiguration.getInstance().isFileEditorIsModified()) {
					prop.setProperty("FileEditorbackgroundColor", String.valueOf(AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(0)
							.getActiveTextEditionArea().getBackground().getRGB()));
					prop.setProperty("FileEditorforegroundColor", String.valueOf(AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(0)
							.getActiveTextEditionArea().getForeground().getRGB()));
				}
				
				prop.setProperty("lexiconFileConfig", AcideMainWindow.getInstance()
						.getFileEditorManager().getSelectedFileEditorPanel().getLexiconConfiguration().getPath());
				prop.setProperty("lexiconConsoleConfig", AcideMainWindow.getInstance().getConsolePanel().getLexiconConfiguration().getPath());

				// save properties to project root folder
				FileOutputStream in = new FileOutputStream(file.getPath());
				prop.store(in, null);
				in.close();
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem().removeAll();
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem().getInsertedItem().clear();
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem()
						.addComponents();
				AcideMainWindow.getInstance().getMenu().getConfigurationMenu().repaint();
				// Apply changes to menuBar
				AcideMainWindow.getInstance().getMenu().paintMenuBar(AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getBackground().darker(),
						AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getForeground());
			} catch (IOException io) {

			}
		}
	}
}
