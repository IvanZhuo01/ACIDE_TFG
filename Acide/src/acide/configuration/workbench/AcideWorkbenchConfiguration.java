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
package acide.configuration.workbench;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import acide.configuration.debug.AcideDebugDatalogConfiguration;
import acide.configuration.debug.AcideDebugSQLConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.configuration.toolBar.AcideToolBarConfiguration;
import acide.configuration.workbench.consolePanel.AcideConsolePanelConfiguration;
import acide.configuration.workbench.fileEditor.AcideFileEditorConfiguration;
import acide.configuration.workbench.lexiconAssigner.AcideLexiconAssignerConfiguration;
import acide.configuration.workbench.recentFiles.AcideRecentFilesConfiguration;
import acide.configuration.workbench.recentProjects.AcideRecentProjectsConfiguration;
import acide.configuration.workbench.utils.AcideFileEditorLoader;
import acide.files.AcideFileManager;
import acide.files.bytes.AcideByteFileManager;
import acide.files.project.AcideProjectFile;
import acide.gui.debugPanel.debugDatalogPanel.debugDatalogConfiguration.AcideDebugDatalogConfigurationWindow;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.themesMenu.AcideThemesMenu;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.process.parser.AcideGrammarFileCreationProcess;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * <p>
 * ACIDE - A Configurable IDE workbench configuration.
 * </p>
 * <p>
 * Loads the workbench configuration from the workbench configuration file at
 * the beginning of the application, and stores the workbench configuration when
 * the user close the project or the application.
 * </p>
 * 
 * @version 0.11
 */
public class AcideWorkbenchConfiguration {

	/**
	 * ACIDE - A Configurable IDE workbench configuration file path constant.
	 */
	public static final String FILE_PATH = "./configuration/workbench/configuration.xml";
	/**
	 * ACIDE - A Configurable IDE workbench configuration unique class instance.
	 */
	private static AcideWorkbenchConfiguration _instance;
	/**
	 * Flag which is used to determine if the workbench configuration has been
	 * finally loaded. The method updatesEditorAndProjectState in the class
	 * AcideFileEditorPanelDocumentListener doesn't check the TestPlaf for the
	 * closing buttons avoiding the exceptions.
	 */
	private boolean _workbenchLoaded = false;
	/**
	 * ACIDE - A Configurable IDE file editor configuration.
	 */
	private AcideFileEditorConfiguration _fileEditorConfiguration;
	/**
	 * ACIDE - A Configurable IDE console panel configuration.
	 */
	private AcideConsolePanelConfiguration _consolePanelConfiguration;
	/**
	 * ACIDE - A Configurable IDE lexicon assigner configuration.
	 */
	private AcideLexiconAssignerConfiguration _lexiconAssignerConfiguration;
	/**
	 * ACIDE - A Configurable IDE recent files configuration.
	 */
	private AcideRecentFilesConfiguration _recentFilesConfiguration;
	/**
	 * ACIDE - A Configurable IDE recent projects configuration.
	 */
	private AcideRecentProjectsConfiguration _recentProjectsConfiguration;

	/**
	 * Returns the Acide - A Configurable IDE workbench configuration unique class
	 * instance.
	 * 
	 * @return the Acide - A Configurable IDE workbench configuration unique class
	 *         instance.
	 */
	public static AcideWorkbenchConfiguration getInstance() {

		if (_instance == null)
			_instance = new AcideWorkbenchConfiguration();
		return _instance;
	}

	/**
	 * Creates a new ACIDE - A Configurable IDE workbench configuration.
	 */
	public AcideWorkbenchConfiguration() {

		// Creates the file editor configuration
		_fileEditorConfiguration = new AcideFileEditorConfiguration();

		// Creates the console configuration
		_consolePanelConfiguration = new AcideConsolePanelConfiguration();

		// Creates the lexicon assigner configuration
		_lexiconAssignerConfiguration = new AcideLexiconAssignerConfiguration();

		// Creates the recent file configuration
		_recentFilesConfiguration = new AcideRecentFilesConfiguration();

		// Creates the recent projects configuration
		_recentProjectsConfiguration = new AcideRecentProjectsConfiguration();
	}

	/**
	 * <p>
	 * Loads the ACIDE - A Configurable IDE workbench configuration.
	 * </p>
	 * <p>
	 * Retrieves the data from the workbench configuration file. Also prevents
	 * errors during the loading process that allows to the application to start
	 * properly even though there are any format errors in the ACIDE - A
	 * Configurable IDE workbench configuration file.
	 * </p>
	 */
	public void load() {

		String workbenchConfigurationPath = null;

		try {

			// Gets the ACIDE - A Configurable IDE workbench configuration path
			workbenchConfigurationPath = AcideResourceManager.getInstance().getProperty("workbenchConfiguration");
			// AcideMain.frame.repaint();
		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());

			// Displays an error message
			JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s960")
					+ workbenchConfigurationPath + AcideLanguageManager.getInstance().getLabels().getString("s959"));

			// Exits the application
			System.exit(0);
		}

		// If the ACIDE - A Configurable IDE workbench configuration file exists

		// Loads the ACIDE - A Configurable ID workbench configuration from the
		// file
		AcideWorkbenchConfiguration workbenchConfiguration = load(workbenchConfigurationPath);

		// Creates the file editor configuration
		AcideFileEditorConfiguration fileEditorConfiguration = null;

		// Creates the console panel configuration
		AcideConsolePanelConfiguration consolePanelConfiguration = null;

		// Creates the lexicon assigner configuration
		AcideLexiconAssignerConfiguration lexiconAssignerConfiguration = null;

		// Creates the recent files configuration
		AcideRecentFilesConfiguration recentFilesConfiguration = null;

		// Creates the recent projects configuration
		AcideRecentProjectsConfiguration recentProjectsConfiguration = null;

		// If the ACIDE - A Configurable IDE workbench configuration file format
		// is correct
		if (workbenchConfiguration != null) {

			// Gets the file editor configuration
			fileEditorConfiguration = workbenchConfiguration.getFileEditorConfiguration();

			// Gets the console panel configuration
			consolePanelConfiguration = workbenchConfiguration.getConsolePanelConfiguration();

			// Gets the lexicon assigner configuration
			lexiconAssignerConfiguration = workbenchConfiguration.getLexiconAssignerConfiguration();

			// Gets the recent files configuration
			recentFilesConfiguration = workbenchConfiguration.getRecentFilesConfiguration();

			// Gets the recent projects configuration
			recentProjectsConfiguration = workbenchConfiguration.getRecentProjectsConfiguration();
		} else

			// Displays a warning message informing about the problem and the
			// changes
			JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2012"),
					AcideLanguageManager.getInstance().getLabels().getString("s2011"), JOptionPane.WARNING_MESSAGE);

		// If the lexicon assigner configuration was created successfully
		if (lexiconAssignerConfiguration != null)

			// Sets the lexicon assigner configuration
			_lexiconAssignerConfiguration = lexiconAssignerConfiguration;

		// Loads the lexicon assigner configuration
		loadLexiconAssignerConfiguration();

		// Loads the project configuration
		loadProjectConfiguration();

		// Loads the tool bar configuration
		loadToolBarConfiguration();

		// Loads the menu configuration
		loadMenuConfiguration();

		// Loads the language configuration
		loadLanguageConfiguration();

		// Loads the explorer panel configuration
		loadExplorerConfiguration();

		// Loads the debug configuration
		loadDebugConfiguration();

		// load database panel configuration
		loadDatabaseConfiguration();

		// If the console panel configuration was loaded successfully
		if (consolePanelConfiguration != null)

			// Sets the console panel configuration
			_consolePanelConfiguration = consolePanelConfiguration;

		// Loads the console panel configuration
		loadConsolePanelConfiguration();

		// If the recent files configuration was loaded successfully
		if (recentFilesConfiguration != null)

			// Sets the recent files configuration
			_recentFilesConfiguration = recentFilesConfiguration;

		// Loads the recent files configuration
		loadRecentFilesConfiguration();

		// If the recent projects configuration was loaded successfully
		if (recentProjectsConfiguration != null)

			// Sets the recent projects configuration
			_recentProjectsConfiguration = recentProjectsConfiguration;

		// Loads the recent projects configuration
		loadRecentProjectsConfiguration();

		// If the file editor configuration was loaded successfully
		if (fileEditorConfiguration != null)

			// Sets the file editor configuration
			_fileEditorConfiguration = fileEditorConfiguration;

		// Loads the file editor configuration
		loadFileEditorConfiguration();

		// load menu bar configuration
		loadThemeConfiguration();

		// Loads the main window configuration
		loadMainWindowConfiguration();
		
		// Loads the grammar configuration
		loadGrammarConfiguration();
	}

	private void loadGrammarConfiguration() {
		// Gets the selected file editor index from the workbench configuration
		int selectedFileEditorIndex = AcideMainWindow.getInstance()
				.getFileEditorManager().getFileEditorPanelAt(
						AcideWorkbenchConfiguration.getInstance()
								.getFileEditorConfiguration()
								.getSelectedFileEditorPanelName());
		
		// Get the selected file editor panel with the index
		AcideFileEditorPanel selectedFileEditor = AcideMainWindow.getInstance()
				.getFileEditorManager().getFileEditorPanelAt(selectedFileEditorIndex);
		
		// Updates the previousFileEditorPanel
		AcideResourceManager.getInstance().setProperty("previousFileEditorPanel"
				, selectedFileEditor.getCurrentGrammarConfiguration().getName());
	}

	private void loadDatabaseConfiguration() {
		String colorB;
		try {

			colorB = AcideResourceManager.getInstance().getProperty("databasePanel.backgroundColor");
			String colorF = AcideResourceManager.getInstance().getProperty("databasePanel.foregroundColor");
			AcideMainWindow.getInstance().getDataBasePanel().changeColor(new Color(Integer.parseInt(colorB)),
					new Color(Integer.parseInt(colorF)));

		} catch (MissedPropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadThemeConfiguration() {
		String colorB;
		try {
			AcideThemesMenu.activeTheme = AcideResourceManager.getInstance().getProperty("themeApplied");
			File t = new File("./configuration/themes/" + AcideThemesMenu.activeTheme + ".properties");
			FileInputStream in = new FileInputStream(t.getPath());
			Properties prop = new Properties();
			prop.load(in);
			colorB = prop.getProperty("backgroundColor");
			String colorF = prop.getProperty("foregroundColor");
			Color b = new Color(Integer.parseInt(colorB));
			Color darker = new Color((int) (b.getRed() * 0.9), (int) (b.getGreen() * 0.9), (int) (b.getBlue() * 0.9));
			AcideMainWindow.getInstance().getMenu().paintMenuBar(darker, new Color(Integer.parseInt(colorF)));
			AcideMainWindow.getInstance().getStatusBar().changeColor(darker, new Color(Integer.parseInt(colorF)));
			AcideMainWindow.getInstance().getToolBarPanel().changeColor(darker, new Color(Integer.parseInt(colorF)));
			AcideMainWindow.getInstance().getDebugPanel().setBackgroundColor(new Color(Integer.parseInt(colorB)),
					new Color(Integer.parseInt(colorF)), darker);
			AcideMainWindow.getInstance().getGraphPanel().setBackgroundColor(new Color(Integer.parseInt(colorB)),
					new Color(Integer.parseInt(colorF)), darker);
			// Apply themes configuration to file editor panel
			String colorBFile = prop.getProperty("FileEditorbackgroundColor");
			Color fileBack = new Color(Integer.parseInt(colorBFile));
			String colorFFile = prop.getProperty("FileEditorforegroundColor");
			Color fileFore = new Color(Integer.parseInt(colorFFile));
			AcideMainWindow.getInstance().getFileEditorManager().setBackground(new Color(Integer.parseInt(colorB)));
			AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().setOpaque(true);
			AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().setBackground(darker);
			AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane()
					.setForeground(new Color(Integer.parseInt(colorF)));
			for (int index = 0; index < AcideMainWindow.getInstance().getFileEditorManager()
					.getNumberOfFileEditorPanels(); index++) {

				// Updates the ACIDE - A Configurable IDE file editor

				AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index)
						.getActiveTextEditionArea().setBackground(fileBack);

				AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index)
						.getActiveTextEditionArea().setForeground(fileFore);
				AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index).changeColor(fileBack,
						new Color(Integer.parseInt(colorF)), darker);
				AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index).setEditable(true);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	/**
	 * Load the ACIDE - A Configurable IDE workbench configuration from an XML file.
	 * 
	 * @param configurationFilePath configuration file path.
	 * 
	 * @return the ACIDE - A Configurable IDE workbench configuration from an XML
	 *         file. If there is any problem during the loading process then returns
	 *         <b>null</b>.
	 */
	public AcideWorkbenchConfiguration load(String configurationFilePath) {

		// Creates the ACIDE - A Configurable IDE workbench configuration
		AcideWorkbenchConfiguration workbenchConfiguration = null;

		// If the name is already set by the user
		if ((configurationFilePath != null) && (!configurationFilePath.trim().equalsIgnoreCase(""))) {

			try {

				// Creates the XStream to handle XML files
				XStream xStream = new XStream(new DomDriver());

				// Creates the file input stream to read the file
				FileInputStream fileInputStream = new FileInputStream(configurationFilePath);

				// Gets the workbench configuration from the XML
				workbenchConfiguration = (AcideWorkbenchConfiguration) xStream.fromXML(fileInputStream);

				// Closes the file input stream
				fileInputStream.close();

			} catch (Exception exception) {

				// Updates the log
				AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s990"));
				exception.printStackTrace();

				return null;
			}
		}

		return workbenchConfiguration;
	}

	/**
	 * Loads the ACIDE - A Configurable IDE file editor configuration.
	 */
	public void loadFileEditorConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1033"));

		// Loads the file editor configuration
		AcideFileEditorLoader.getInstance().run(_fileEditorConfiguration);

	}

	/**
	 * Loads the ACIDE - A Configurable IDE lexicon assigner configuration.
	 */
	public void loadLexiconAssignerConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1092"));
	}

	/**
	 * Loads the ACIDE - A Configurable IDE recent files configuration.
	 */
	public void loadRecentFilesConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1071"));

		// Builds the open recent files menu
		AcideMainWindow.getInstance().getMenu().getFileMenu().getOpenRecentFilesMenu().build();
	}

	/**
	 * Loads the ACIDE - A Configurable IDE recent projects configuration.
	 */
	public void loadRecentProjectsConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1079"));

		// Builds the open recent project menu
		AcideMainWindow.getInstance().getMenu().getProjectMenu().getOpenRecentProjectsMenu().build();
	}

	/**
	 * Loads the ACIDE - A Configurable IDE console configuration and applies it to
	 * the ACIDE - A Configurable IDE console pane in the ACIDE - A Configurable IDE
	 * main window.
	 */
	public void loadConsolePanelConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1031"));

		// Sets the shell path in the resource manager
		AcideResourceManager.getInstance().setProperty("consolePanel.shellPath",
				AcideProjectConfiguration.getInstance().getShellPath());

		// Sets the shell directory in the ACIDE - A Configurable IDE resource
		// manager
		AcideResourceManager.getInstance().setProperty("consolePanel.shellDirectory",
				AcideProjectConfiguration.getInstance().getShellDirectory());

		// Sets the echo command in the ACIDE - A Configurable IDE resource
		// manager
		AcideResourceManager.getInstance().setProperty("consolePanel.isEchoCommand",
				String.valueOf(AcideProjectConfiguration.getInstance().getIsEchoCommand()));

		// Sets the exit command in the ACIDE - A Configurable IDE resource
		// manager
		AcideResourceManager.getInstance().setProperty("consolePanel.exitCommand",
				AcideProjectConfiguration.getInstance().getExitCommand());

		// Sets the font name in the ACIDE - A Configurable IDE resource manager
		AcideResourceManager.getInstance().setProperty("consolePanel.fontName",
				AcideProjectConfiguration.getInstance().getFontName());

		// Sets the font size in the ACIDE - A Configurable IDE resource manager
		AcideResourceManager.getInstance().setProperty("consolePanel.fontSize",
				String.valueOf(AcideProjectConfiguration.getInstance().getFontSize()));

		// Sets the font style in the ACIDE - A Configurable IDE resource
		// manager
		AcideResourceManager.getInstance().setProperty("consolePanel.fontStyle",
				String.valueOf(AcideProjectConfiguration.getInstance().getFontStyle()));

		// Sets the foreground color in the ACIDE - A Configurable IDE resource
		// manager
		AcideResourceManager.getInstance().setProperty("consolePanel.foregroundColor",
				Integer.toString(AcideProjectConfiguration.getInstance().getForegroundColor().getRGB()));

		// Sets the background color in the ACIDE - A Configurable IDE resource
		// manager
		AcideResourceManager.getInstance().setProperty("consolePanel.backgroundColor",
				Integer.toString(AcideProjectConfiguration.getInstance().getBackgroundColor().getRGB()));

		// Sets the buffer size in the ACIDE - A Configurable IDE resource
		// manager
		AcideResourceManager.getInstance().setProperty("consolePanel.bufferSize",
				Integer.toString(AcideProjectConfiguration.getInstance().getBufferSize()));

		// Resets the shell in the ACIDE - A Configurable IDE console panel
		AcideMainWindow.getInstance().getConsolePanel().resetConsole();

		// Sets the ACIDE - A Configurable IDE console panel look and feel
		AcideMainWindow.getInstance().getConsolePanel().setLookAndFeel();

		// Loads the console configuration from the ACIDE - A Configurable
		// IDE
		// workbench configuration
		AcideMainWindow.getInstance().getConsolePanel().getLexiconConfiguration()
				.load(_consolePanelConfiguration.getLexiconConfiguration());

		// Loads the commands history of the previous execution
		AcideMainWindow.getInstance().getConsolePanel().getConsoleCommandsConfiguration()
				.load(_consolePanelConfiguration.getCommandsConfiguration());

		// Fills the history with the previous execution
		AcideMainWindow.getInstance().getConsolePanel().fillHistory();

		// Applies the new highlighting
		AcideMainWindow.getInstance().getConsolePanel().resetStyledDocument();

		// Activates the console line wrapping
		AcideMainWindow.getInstance().getConsolePanel().getPopupMenu().getLineWrappingMenuItem().setSelected(true);

		// Puts the default value in the console panel configuration
		AcideWorkbenchConfiguration.getInstance().getConsolePanelConfiguration().setLineWrapping(true);

		// Puts the default value in the console line wrapping check box menu
		// item
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getConsoleMenu()
				.getConsoleLineWrappingCheckBoxMenuItem().setSelected(true);

		// Apply themes configuration to console panel
		Color b = AcideProjectConfiguration.getInstance().getBackgroundColor();
		Color darker = new Color((int) (b.getRed() * 0.9), (int) (b.getGreen() * 0.9), (int) (b.getBlue() * 0.9));
		AcideMainWindow.getInstance().getConsolePanel().changeColor(
				AcideProjectConfiguration.getInstance().getBackgroundColor(),
				AcideProjectConfiguration.getInstance().getForegroundColor(), darker);
	}

	/**
	 * Loads the ACIDE - A Configurable menu configuration.
	 */
	private void loadMenuConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1034"));

		String currentMenuConfiguration = null;

		try {

			// Sets the ACIDE - A Configurable IDE current menu from the
			// project configuration
			AcideResourceManager.getInstance().setProperty("currentMenuConfiguration",
					AcideProjectConfiguration.getInstance().getMenuConfiguration());

			// Gets the ACIDE - A Configurable IDE current menu configuration
			currentMenuConfiguration = AcideResourceManager.getInstance().getProperty("currentMenuConfiguration");

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());

			// Gets the menu configuration name
			String name;
			int index = currentMenuConfiguration.lastIndexOf("\\");
			if (index == -1)
				index = currentMenuConfiguration.lastIndexOf("/");
			name = "./configuration/menu/"
					+ currentMenuConfiguration.substring(index + 1, currentMenuConfiguration.length());

			try {

				// Information message
				JOptionPane.showMessageDialog(null,
						AcideLanguageManager.getInstance().getLabels().getString("s2151") + currentMenuConfiguration
								+ AcideLanguageManager.getInstance().getLabels().getString("s957") + name);

				// Updates the ACIDE - A Configurable IDE current menu
				// configuration
				AcideResourceManager.getInstance().setProperty("currentMenuConfiguration", name);
			} catch (Exception exception1) {

				// Updates the log
				AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s127"));

				try {

					// Information message
					JOptionPane.showMessageDialog(null,
							AcideLanguageManager.getInstance().getLabels().getString("s958") + currentMenuConfiguration
									+ AcideLanguageManager.getInstance().getLabels().getString("s959"));

					// Updates the the ACIDE - A Configurable IDE current menu
					// configuration
					AcideResourceManager.getInstance().setProperty("currentMenuConfiguration",
							"./configuration/menu/defaultAllOn.menuConfig");
				} catch (HeadlessException exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				} catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}
			}
		}

		String currentMenuNewConfiguration = null;

		try {

			// Sets the ACIDE - A Configurable IDE current menu new
			// configuration from the
			// project configuration
			AcideResourceManager.getInstance().setProperty("currentMenuNewConfiguration",
					AcideProjectConfiguration.getInstance().getMenuNewConfiguration());

			// Gets the ACIDE - A Configurable IDE current menu new
			// configuration
			currentMenuNewConfiguration = AcideResourceManager.getInstance().getProperty("currentMenuNewConfiguration");

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());

			// Gets the menu new configuration name
			String name2;
			int index2 = currentMenuNewConfiguration.lastIndexOf("\\");
			if (index2 == -1)
				index2 = currentMenuNewConfiguration.lastIndexOf("/");
			name2 = "./configuration/menu/"
					+ currentMenuNewConfiguration.substring(index2 + 1, currentMenuNewConfiguration.length());

			try {

				// Information message
				JOptionPane.showMessageDialog(null,
						AcideLanguageManager.getInstance().getLabels().getString("s2151") + currentMenuNewConfiguration
								+ AcideLanguageManager.getInstance().getLabels().getString("s957") + name2);

				// Updates the ACIDE - A Configurable IDE current menu
				// new configuration
				AcideResourceManager.getInstance().setProperty("currentMenuNewConfiguration", name2);
			} catch (Exception exception1) {

				// Updates the log
				AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s127"));

				try {

					// Information message
					JOptionPane.showMessageDialog(null,
							AcideLanguageManager.getInstance().getLabels().getString("s2151")
									+ currentMenuNewConfiguration
									+ AcideLanguageManager.getInstance().getLabels().getString("s959"));

					// Updates the the ACIDE - A Configurable IDE current menu
					// new configuration
					AcideResourceManager.getInstance().setProperty("currentMenuNewConfiguration",
							"./configuration/menu/defaultAllOn.xml");
				} catch (HeadlessException exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				} catch (Exception exception2) {

					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}
			}
		}
	}

	/**
	 * Loads the ACIDE - A Configurable IDE file editor configuration.
	 * 
	 * @param fileEditorConfiguration new value to set.
	 */
	public void loadFileEditorConfiguration(AcideFileEditorConfiguration fileEditorConfiguration) {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1033"));

		// Sets the file editor configuration
		_fileEditorConfiguration = fileEditorConfiguration;

		// Loads the file editor configuration
		AcideFileEditorLoader.getInstance().run(_fileEditorConfiguration);
	}

	/**
	 * Loads the ACIDE - A Configurable IDE lexicon assigner configuration.
	 * 
	 * @param lexiconAssignerConfiguration new value to set.
	 */
	public void loadLexiconAssignerConfiguration(AcideLexiconAssignerConfiguration lexiconAssignerConfiguration) {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1092"));

		// Sets the lexicon assigner configuration
		_lexiconAssignerConfiguration = lexiconAssignerConfiguration;
	}

	/**
	 * Loads the ACIDE - A Configurable IDE recent files configuration.
	 * 
	 * @param recentFilesConfiguration new value to set.
	 */
	public void loadRecentFilesConfiguration(AcideRecentFilesConfiguration recentFilesConfiguration) {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1071"));

		// Sets the recent files configuration
		_recentFilesConfiguration = recentFilesConfiguration;

		// Builds the open recent files menu
		AcideMainWindow.getInstance().getMenu().getFileMenu().getOpenRecentFilesMenu().build();
	}

	/**
	 * Loads the ACIDE - A Configurable IDE recent projects configuration.
	 * 
	 * @param recentProjectsConfiguration new value to set.
	 */
	public void loadRecentProjectsConfiguration(AcideRecentProjectsConfiguration recentProjectsConfiguration) {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1079"));

		// Sets the recent projects configuration
		_recentProjectsConfiguration = recentProjectsConfiguration;

		// Builds the open recent project menu
		AcideMainWindow.getInstance().getMenu().getProjectMenu().getOpenRecentProjectsMenu().build();
	}

	/**
	 * Saves the ACIDE - A Configurable IDE workbench configuration into its
	 * configuration file.
	 */
	public void save() {

		// Creates the XStream to handle XML files
		XStream xStream = new XStream(new DomDriver());

		try {

			// Creates the file output stream to write on the file
			FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH);

			// Parses to XML format
			xStream.toXML(this, fileOutputStream);

			// Closes the file output stream
			fileOutputStream.close();
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Loads the ACIDE - A Configurable IDE project workbench configuration.
	 */
	private void loadProjectConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1092"));

		String projectConfiguration = null;

		try {

			// Gets the ACIDE - A Configurable IDE project configuration
			projectConfiguration = AcideResourceManager.getInstance().getProperty("projectConfiguration");

			// Loads the project configuration
			AcideProjectConfiguration.getInstance().load(projectConfiguration);
		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());

			// Loads the default configuration
			AcideProjectConfiguration.getInstance().load(AcideProjectConfiguration.DEFAULT_PATH);

			// Displays an error message
			JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s960")
					+ projectConfiguration + AcideLanguageManager.getInstance().getLabels().getString("s959"));
		}
	}

	/**
	 * Loads the ACIDE - A Configurable IDE debug workbench configuration.
	 */
	private void loadDebugConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s2338"));

		String debugConfiguration = null;

		try {

			// Gets the ACIDE - A Configurable IDE project configuration
			debugConfiguration = AcideResourceManager.getInstance().getProperty("debugConfigurationPath");

			// Loads the project configuration
			AcideDebugDatalogConfiguration.getInstance().load(debugConfiguration.substring(
					0,debugConfiguration.lastIndexOf(".")) +"Datalog" +debugConfiguration
					.substring(debugConfiguration.lastIndexOf(".")));

			AcideDebugSQLConfiguration.getInstance().load(debugConfiguration.substring(
					0,debugConfiguration.lastIndexOf(".")) +"SQL" +debugConfiguration
					.substring(debugConfiguration.lastIndexOf(".")));

		} catch (MissedPropertyException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());

			// Loads the default configuration
			AcideDebugDatalogConfiguration.getInstance().setDefaultConfiguration();
			AcideDebugSQLConfiguration.getInstance().setDefaultConfiguration();

			// Displays an error message
			JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2339")
					+ AcideLanguageManager.getInstance().getLabels().getString("s959"));
		}
	}

	/**
	 * Loads the ACIDE - A Configurable tool bar configuration.
	 */
	private void loadToolBarConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1072"));

		String currentToolBarConfiguration = null;

		try {

			// Sets the ACIDE - A Configurable IDE current tool bar from the
			// project configuration
			AcideResourceManager.getInstance().setProperty("currentToolBarConfiguration",
					AcideProjectConfiguration.getInstance().getToolBarConfiguration());

			// Gets the ACIDE - A Configurable IDE current tool bar
			// configuration
			currentToolBarConfiguration = AcideResourceManager.getInstance().getProperty("currentToolBarConfiguration");

			// Loads the ACIDE - A Configurable IDE tool bar configuration the
			// current tool bar configuration
			AcideToolBarConfiguration.getInstance().load(currentToolBarConfiguration);

		} catch (Exception exception) {

			try {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());

				// Gets the tool bar configuration name
				String name;
				int index = currentToolBarConfiguration.lastIndexOf("\\");
				if (index == -1)
					index = currentToolBarConfiguration.lastIndexOf("/");
				name = "./configuration/toolbar/"
						+ currentToolBarConfiguration.substring(index + 1, currentToolBarConfiguration.length());

				// Loads the ACIDE - A Configurable IDE tool bar configuration
				// the
				// current tool bar configuration
				AcideToolBarConfiguration.getInstance().load(name);

				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);

				// Information message
				JOptionPane.showMessageDialog(null,
						AcideLanguageManager.getInstance().getLabels().getString("s958") + currentToolBarConfiguration
								+ AcideLanguageManager.getInstance().getLabels().getString("s957") + name);

				// Updates the ACIDE - A Configurable IDE current tool bar
				// configuration
				AcideResourceManager.getInstance().setProperty("currentToolBarConfiguration", name);

			} catch (Exception exception1) {

				// Updates the log
				AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s127"));

				try {

					// Loads the default ACIDE - A Configurable IDE tool bar
					// configuration
					AcideToolBarConfiguration.getInstance().load("./configuration/toolbar/default.toolbarConfig");

					// If it is not the default project
					if (!AcideProjectConfiguration.getInstance().isDefaultProject())

						// The project has been modified
						AcideProjectConfiguration.getInstance().setIsModified(true);

					// Information message
					JOptionPane.showMessageDialog(null,
							AcideLanguageManager.getInstance().getLabels().getString("s958")
									+ currentToolBarConfiguration
									+ AcideLanguageManager.getInstance().getLabels().getString("s959"));

					// Updates the the ACIDE - A Configurable IDE current tool
					// bar configuration
					AcideResourceManager.getInstance().setProperty("currentToolBarConfiguration",
							"./configuration/toolbar/default.toolbarConfig");

				} catch (HeadlessException exception2) {
					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();

				} catch (Exception exception2) {
					// Updates the log
					AcideLog.getLog().error(exception2.getMessage());
					exception2.printStackTrace();
				}
			}

			// Updates the log
			AcideLog.getLog().error(AcideLanguageManager.getInstance().getLabels().getString("s127"));
		}
	}

	/**
	 * <p>
	 * Loads the ACIDE - A Configurable IDE language workbench configuration.
	 * </p>
	 * <p>
	 * In order to apply the specific language, the menu item action will be
	 * performed, so it is mandatory that the ACIDE - A Configurable IDE menu has
	 * been created previously.
	 * </p>
	 * 
	 * @return the ACIDE - A Configurable IDE language configuration.
	 */
	public void loadLanguageConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1030"));

		// // If the ACIDE - A Configurable IDE language is Spanish
		// if
		// (AcideProjectConfiguration.getInstance().getLanguageConfiguration()
		// .equals("spanish"))
		//
		// // Performs the Spanish menu item action
		// AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
		// .getLanguageMenu().applyLanguage("spanish");
		//
		// // If the ACIDE - A Configurable IDE language is English
		// if
		// (AcideProjectConfiguration.getInstance().getLanguageConfiguration()
		// .equals("english"))
		//
		// // Performs the English menu item action
		// AcideMainWindow.getInstance().getMenu().getConfigurationMenu()
		// .getLanguageMenu().applyLanguage("english");
	}

	/**
	 * <p>
	 * Loads the ACIDE - A Configurable IDE main window configuration.
	 * </p>
	 * <p>
	 * Sets the values from the ACIDE - A Configurable IDE project configuration and
	 * applies them to the ACIDE - A Configurable IDE main window.
	 * </p>
	 */
	public void loadMainWindowConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1035"));

		// If the ACIDE - A Configurable IDE explorer panel has not to be showed
		if (!AcideProjectConfiguration.getInstance().isExplorerPanelShowed()) {

			// Hides the explorer panel
			AcideMainWindow.getInstance().getExplorerPanel().disposeExplorerPanel();
		} else {

			// Displays the explorer panel
			AcideMainWindow.getInstance().getExplorerPanel().showExplorerPanel();
		}

		// Updates the show explorer panel check box menu item state
		AcideMainWindow.getInstance().getMenu().getViewMenu().getShowExplorerPanelCheckBoxMenuItem()
				.setSelected(AcideProjectConfiguration.getInstance().isExplorerPanelShowed());

		// If the ACIDE - A Configurable IDE console panel has not to be showed
		if (!AcideProjectConfiguration.getInstance().isConsolePanelShowed())

			// Hides the console panel
			AcideMainWindow.getInstance().getConsolePanel().disposeConsolePanel();
		else

			// Shows the console panel
			AcideMainWindow.getInstance().getConsolePanel().showConsolePanel();

		// Updates the show console panel check box menu item state
		AcideMainWindow.getInstance().getMenu().getViewMenu().getShowConsolePanelCheckBoxMenuItem()
				.setSelected(AcideProjectConfiguration.getInstance().isConsolePanelShowed());

		// Updates the show graph panel check box menu item state
		if (!AcideProjectConfiguration.getInstance().isGraphPanelShowed())

			// Hides the graph panel
			AcideMainWindow.getInstance().getGraphPanel().disposeGraphPanel();
		else
			AcideMainWindow.getInstance().getGraphPanel().loadGraphPanel();

		// Shows the graph panel
		AcideMainWindow.getInstance().getMenu().getViewMenu().getShowGraphPanelCheckBoxMenuItem()
				.setSelected(AcideProjectConfiguration.getInstance().isGraphPanelShowed());

		// Updates the show debug panel check box menu item state
		if (!AcideProjectConfiguration.getInstance().isDebugPanelShowed())

			// Hides the debug panel
			AcideMainWindow.getInstance().getDebugPanel().disposeDebugPanel();
		else
			AcideMainWindow.getInstance().getDebugPanel().showDebugPanel();

		// Shows the debug panel
		AcideMainWindow.getInstance().getMenu().getViewMenu().getShowDebugPanelCheckBoxMenuItem()
				.setSelected(AcideProjectConfiguration.getInstance().isDebugPanelShowed());

		// Updates the show database panel check box menu item state
		if (!AcideProjectConfiguration.getInstance().isDatabasePanelShowed())

			// Hides the database panel
			AcideMainWindow.getInstance().getDataBasePanel().disposeDataBasePanel();
		else
			// Shows the database panel
			AcideMainWindow.getInstance().getDataBasePanel().showDataBasePanel();

		// Updates the show database panel check box menu item state
		AcideMainWindow.getInstance().getMenu().getViewMenu().getShowDataBasePanelCheckBoxMenuItem()
				.setSelected(AcideProjectConfiguration.getInstance().isDatabasePanelShowed());


		// Sets the ACIDE - A Configurable IDE main window preferred size
		AcideMainWindow.getInstance()
				.setPreferredSize(new Dimension(AcideProjectConfiguration.getInstance().getWindowWidth(),
						AcideProjectConfiguration.getInstance().getWindowHeight()));

		// Sets the ACIDE - A Configurable IDE main window location
		AcideMainWindow.getInstance().setLocation(AcideProjectConfiguration.getInstance().getXCoordinate(),
				AcideProjectConfiguration.getInstance().getYCoordinate());

		// Sets the ACIDE - A Configurable IDE main window split panel vertical
		// files divider location
		AcideMainWindow.getInstance().getVerticalFilesSplitPane()
				.setDividerLocation(AcideProjectConfiguration.getInstance().getVerticalFilesSplitPaneDividerLocation());

		// Sets the ACIDE - A Configurable IDE main window split panel vertical
		// database divider location
		AcideMainWindow.getInstance().getVerticalDataBaseSplitPane().setDividerLocation(
				AcideProjectConfiguration.getInstance().getVerticalDataBaseSplitPaneDividerLocation());

		// Sets the ACIDE - A Configurable IDE main window split panel
		// horizontal divider location
		AcideMainWindow.getInstance().getHorizontalSplitPane()
				.setDividerLocation(AcideProjectConfiguration.getInstance().getHorizontalSplitPanelDividerLocation());

		// Sets the ACIDE - A Configurable IDE main window split panel vertical
		// divider location
		AcideMainWindow.getInstance().getVerticalSplitPane()
				.setDividerLocation(AcideProjectConfiguration.getInstance().getVerticalGraphSplitPaneDividerLocation());

		// Sets the ACIDE - A Configurable IDE main window split panel
		// horizontal
		// divider location
		AcideMainWindow.getInstance().getHorizontalGraphSplitPane().setDividerLocation(
				AcideProjectConfiguration.getInstance().getHorizontalGraphSplitPaneDividerLocation());

		// Updates the ACIDE - A Configurable IDE main window
		AcideMainWindow.getInstance().validate();

		// Packs the ACIDE - A Configurable IDE main window
		AcideMainWindow.getInstance().pack();

		// Repaint the ACIDE - A Configurable IDE main window
		AcideMainWindow.getInstance().repaint();
	}

	/**
	 * Loads the explorer files and builds the explorer in the main window.
	 */
	public void loadExplorerConfiguration() {

		// Updates the log
		AcideLog.getLog().info(AcideLanguageManager.getInstance().getLabels().getString("s1032"));

		try {

			// If it is the default project
			if (AcideProjectConfiguration.getInstance().isDefaultProject()) {

				// Sets the title with the <empty> tag
				AcideMainWindow.getInstance().setTitle(AcideMainWindow.getInstance().getTitle() + " - <empty>");
			} else {

				// Sets all the features for the main window to allow the
				// options for an open project configuration
				String name = AcideProjectConfiguration.getInstance().getName();

				// Creates the new project file
				AcideProjectFile newProjectFile = new AcideProjectFile();
				newProjectFile.setAbsolutePath(name);
				newProjectFile.setName(name);
				newProjectFile.setParent(null);
				newProjectFile.setIsDirectory(true);

				// Enables add file menu item in the explorer popup menu
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu().getAddFileMenuItem().setEnabled(true);

				// Enables the remove file menu item in the explorer popup menu
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu().getRemoveFileMenuItem()
						.setEnabled(true);

				// Sets the project title
				AcideMainWindow.getInstance().setTitle(AcideLanguageManager.getInstance().getLabels().getString("s425")
						+ " - " + AcideProjectConfiguration.getInstance().getName());

				// Builds the explorer tree with all the associated files
				AcideMainWindow.getInstance().getExplorerPanel().getRoot().removeAllChildren();

				// Creates the new explorer node
				DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(newProjectFile);

				// Adds the new node to the explorer tree root
				AcideMainWindow.getInstance().getExplorerPanel().getRoot().add(rootNode);

				// Creates the directory list
				ArrayList<DefaultMutableTreeNode> directoryList = new ArrayList<DefaultMutableTreeNode>();

				for (int index = 0; index < AcideProjectConfiguration.getInstance()
						.getNumberOfFilesFromList(); index++) {

					// Gets the node
					DefaultMutableTreeNode fileProjectNode = new DefaultMutableTreeNode(
							AcideProjectConfiguration.getInstance().getFileAt(index));

					// If is a directory
					if (AcideProjectConfiguration.getInstance().getFileAt(index).isDirectory()) {

						// Allows children in the tree
						fileProjectNode.setAllowsChildren(true);

						// Adds the node to the directory list
						directoryList.add(fileProjectNode);

					} else {
						// No children are allowed
						fileProjectNode.setAllowsChildren(false);
					}
					// If the file already exists in the level above
					if (AcideProjectConfiguration.getInstance().getFileAt(index).getParent()
							.equals(AcideProjectConfiguration.getInstance().getName())) {

						// Adds the new node
						rootNode.add(fileProjectNode);
					} else {

						// Searches for the node
						DefaultMutableTreeNode parentNode = AcideMainWindow.getInstance().getExplorerPanel()
								.searchDirectoryList(directoryList,
										AcideProjectConfiguration.getInstance().getFileAt(index).getParent());

						// Adds the new node
						if (parentNode != null)
							parentNode.add(fileProjectNode);
					}
				}

				// Updates the explorer tree
				AcideMainWindow.getInstance().getExplorerPanel().getTreeModel().reload();

				// Repaint the explorer tree
				AcideMainWindow.getInstance().getExplorerPanel().expandTree();

				// Enables the add file menu item in the popup menu
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu().getAddFileMenuItem().setEnabled(true);

				// Enables the save project menu item in the popup menu
				AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu().getSaveProjectMenuItem()
						.setEnabled(true);

				// If it has more than 0 files associated
				if (AcideProjectConfiguration.getInstance().getNumberOfFilesFromList() > 0)

					// Allows to remove files in the EXPLORER menu
					AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu().getRemoveFileMenuItem()
							.setEnabled(true);
				else
					// Removing files in the EXPLORER menu is not allowed
					AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu().getRemoveFileMenuItem()
							.setEnabled(false);

				// Saves the ACIDE - A Configurable IDE project configuration
				AcideProjectConfiguration.getInstance().setIsFirstSave(true);

				// Updates the ACIDE - A Configurable IDE project configuration
				AcideProjectConfiguration.getInstance()
						.setProjectPath(AcideResourceManager.getInstance().getProperty("projectConfiguration"));

				String colorB = AcideResourceManager.getInstance().getProperty("explorerPanel.backgroundColor");
				String colorF = AcideResourceManager.getInstance().getProperty("explorerPanel.foregroundColor");
				Color b = new Color(Integer.parseInt(colorB));
				Color darker = new Color((int) (b.getRed() * 0.9), (int) (b.getGreen() * 0.9),
						(int) (b.getBlue() * 0.9));
				AcideMainWindow.getInstance().getExplorerPanel().setBackgroundColor(new Color(Integer.parseInt(colorB)),
						new Color(Integer.parseInt(colorF)), darker);
			}
		} catch (NumberFormatException exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Closes the new and log tab in the editor and saves the state of each one of
	 * the opened files in the ACIDE -A Configurable IDE file editor into the ACIDE
	 * -A Configurable IDE file editor configuration.
	 * </p>
	 */
	public void saveFileEditorOpenedFilesConfiguration() {

		try {

			// SPECIAL CASE: New file
			int newFileIndex = AcideMainWindow.getInstance().getFileEditorManager().getNewFileIndex();

			// If it has new file opened
			if (newFileIndex != -1) {

				// Closes the new file so it will not be saved
				// in the configuration
				AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().remove(newFileIndex);
			}

			// SPECIAL CASE: Log file
			int logFileIndex = AcideMainWindow.getInstance().getFileEditorManager().getLogFileIndex();

			// If it has log file opened
			if (logFileIndex != -1) {

				// Closes the log file so it will not be saved in the
				// project configuration
				AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().remove(logFileIndex);
			}

			// Saves the editor configuration configuration
			_fileEditorConfiguration.save();

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Saves the configurations of the ACIDE - A Configurable IDE components once
	 * the opened modified file editor panels has been saved or not.
	 */
	public void saveComponentsConfiguration() {

		// Saves the console panel configuration
		saveConsolePanelConfiguration();

		// Saves the menu configuration
		saveMenuConfiguration();

		// Saves the tool bar configuration
		saveToolBarConfiguration();

		// Stores the configuration of the files
		saveFileEditorOpenedFilesConfiguration();

	}

	/**
	 * Saves the ACIDE - A Configurable IDE console panel configuration.
	 */
	private void saveConsolePanelConfiguration() {

		// Saves the console configuration from the current ACIDE - A
		// Configurable IDE console panel
		_consolePanelConfiguration.setLexiconConfiguration(
				AcideMainWindow.getInstance().getConsolePanel().getLexiconConfiguration().getPath());
		// Closes the console panel
		AcideMainWindow.getInstance().getConsolePanel().executeExitCommand();
	}

	/**
	 * Saves the ACIDE - A Configurable IDE tool bar configuration.
	 */
	private void saveToolBarConfiguration() {

		try {
			// Gets the ACIDE - A Configurable IDE current tool bar
			// configuration
			String currentToolBarConfiguration = AcideResourceManager.getInstance()
					.getProperty("currentToolBarConfiguration");

			if ((currentToolBarConfiguration.endsWith("lastModified.toolbarConfig"))
					|| currentToolBarConfiguration.endsWith("newToolBar.toolbarConfig")) {

				// Gets the ACIDE - A Configurable IDE previous tool bar
				// configuration
				String previousToolBarConfiguration = AcideResourceManager.getInstance()
						.getProperty("previousToolBarConfiguration");

				// Updates the ACIDE - A Configurable IDE current tool bar
				// configuration
				AcideResourceManager.getInstance().setProperty("currentToolBarConfiguration",
						previousToolBarConfiguration);
			}

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());

			// Displays an error message
			JOptionPane.showMessageDialog(null, exception.getMessage(),
					AcideLanguageManager.getInstance().getLabels().getString("s294"), JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves the ACIDE - A Configurable IDE menu configuration.
	 */
	private void saveMenuConfiguration() {

		try {
			// Gets the ACIDE - A Configurable IDE current menu configuration
			String currentMenuConfiguration = AcideResourceManager.getInstance()
					.getProperty("currentMenuConfiguration");

			if ((currentMenuConfiguration.endsWith("lastModified.menuConfig"))
					|| (currentMenuConfiguration.endsWith("newMenu.menuConfig"))) {

				// Gets the ACIDE - A Configurable IDE previous menu
				// configuration
				String previousMenuConfiguration = AcideResourceManager.getInstance()
						.getProperty("previousMenuConfiguration");

				// Updates the ACIDE - A Configurable IDE current menu
				// configuration
				AcideResourceManager.getInstance().setProperty("currentMenuConfiguration", previousMenuConfiguration);
			}

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());

			// Displays an error message
			JOptionPane.showMessageDialog(null, exception.getMessage(),
					AcideLanguageManager.getInstance().getLabels().getString("s294"), JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves the ACIDE - A Configurable IDE default configuration.
	 */
	public void saveDefaultConfiguration() {

		try {

			// Sets the the ACIDE - A Configurable IDE language
			// configuration
			AcideProjectConfiguration.getInstance()
					.setLanguageConfiguration(AcideResourceManager.getInstance().getProperty("language"));

			// Sets the ACIDE - A Configurable IDE current menu
			// configuration
			AcideProjectConfiguration.getInstance()
					.setMenuConfiguration(AcideResourceManager.getInstance().getProperty("currentMenuConfiguration"));

			// Sets the ACIDE - A Configurable IDE current tool bar
			// configuration
			AcideProjectConfiguration.getInstance().setToolBarConfiguration(
					AcideResourceManager.getInstance().getProperty("currentToolBarConfiguration"));

			// Sets the is explorer panel showed flag as true
			AcideProjectConfiguration.getInstance().setIsExplorerPanelShowed(AcideMainWindow.getInstance().getMenu()
					.getViewMenu().getShowExplorerPanelCheckBoxMenuItem().isSelected());

			// Sets the is console panel showed flag as true
			AcideProjectConfiguration.getInstance().setIsConsolePanelShowed(AcideMainWindow.getInstance().getMenu()
					.getViewMenu().getShowConsolePanelCheckBoxMenuItem().isSelected());

			// Sets the is database panel showed flag as true
			AcideProjectConfiguration.getInstance().setIsDatabasePanelShowed(AcideMainWindow.getInstance().getMenu()
					.getViewMenu().getShowDataBasePanelCheckBoxMenuItem().isSelected());

			// Sets the is graph panel showed flag as true
			AcideProjectConfiguration.getInstance().setIsGraphPanelShowed(AcideMainWindow.getInstance().getMenu()
					.getViewMenu().getShowGraphPanelCheckBoxMenuItem().isSelected());

			// Sets the is debug panel showed flag as true
			AcideProjectConfiguration.getInstance().setIsDebugPanelShowed(AcideMainWindow.getInstance().getMenu()
					.getViewMenu().getShowDebugPanelCheckBoxMenuItem().isSelected());

			// Sets the ACIDE - A Configurable IDE main window width
			AcideProjectConfiguration.getInstance().setWindowWidth(AcideMainWindow.getInstance().getWidth());

			// Sets the ACIDE - A Configurable IDE main window height
			AcideProjectConfiguration.getInstance().setWindowHeight(AcideMainWindow.getInstance().getHeight());

			// Sets the ACIDE - A Configurable IDE main window x coordinate
			AcideProjectConfiguration.getInstance().setXCoordinate(AcideMainWindow.getInstance().getX());

			// Sets the ACIDE - A Configurable IDE main window y coordinate
			AcideProjectConfiguration.getInstance().setYCoordinate(AcideMainWindow.getInstance().getY());

			// Sets the ACIDE - A Configurable IDE main window vertical
			// files split pane divider location
			AcideProjectConfiguration.getInstance().setVerticalFilesSplitPaneDividerLocation(
					AcideMainWindow.getInstance().getVerticalFilesSplitPane().getDividerLocation());

			// Sets the ACIDE - A Configurable IDE main window horizontal
			// graph split pane divider location
			AcideProjectConfiguration.getInstance().setHorizontalGraphSplitPaneDividerLocation(
					AcideMainWindow.getInstance().getHorizontalGraphSplitPane().getDividerLocation());

			// Sets the ACIDE - A Configurable IDE main window vertical
			// database split pane divider location
			AcideProjectConfiguration.getInstance().setVerticalDataBaseSplitPaneDividerLocation(
					AcideMainWindow.getInstance().getVerticalDataBaseSplitPane().getDividerLocation());

			// Sets the ACIDE - A Configurable IDE main window vertical
			// graph pane divider location
			AcideProjectConfiguration.getInstance().setVerticalGraphSplitPaneDividerLocation(
					AcideMainWindow.getInstance().getVerticalSplitPane().getDividerLocation());

			// Sets the ACIDE - A Configurable IDE main window horizontal
			// split pane divider location
			AcideProjectConfiguration.getInstance().setHorizontalSplitPaneDividerLocation(
					AcideMainWindow.getInstance().getHorizontalSplitPane().getDividerLocation());

			// Sets the ACIDE - A Configurable IDE console panel shell path
			AcideProjectConfiguration.getInstance()
					.setShellPath(AcideResourceManager.getInstance().getProperty("consolePanel.shellPath"));

			// Sets the ACIDE - A Configurable IDE console panel shell
			// directory
			AcideProjectConfiguration.getInstance()
					.setShellDirectory(AcideResourceManager.getInstance().getProperty("consolePanel.shellDirectory"));

			// Sets the ACIDE - A Configurable IDE console panel exit
			// command
			AcideProjectConfiguration.getInstance()
					.setExitCommand(AcideResourceManager.getInstance().getProperty("consolePanel.exitCommand"));

			// Sets the ACIDE - A Configurable IDE console panel is echo
			// command
			AcideProjectConfiguration.getInstance().setIsEchoCommand(
					Boolean.parseBoolean(AcideResourceManager.getInstance().getProperty("consolePanel.isEchoCommand")));

			// Sets the ACIDE - A Configurable IDE console panel foreground
			// color
			AcideProjectConfiguration.getInstance().setForegroundColor(new Color(
					Integer.parseInt(AcideResourceManager.getInstance().getProperty("consolePanel.foregroundColor"))));

			// Sets the ACIDE - A Configurable IDE console panel background
			// color
			AcideProjectConfiguration.getInstance().setBackgroundColor(new Color(
					Integer.parseInt(AcideResourceManager.getInstance().getProperty("consolePanel.backgroundColor"))));

			// Sets the ACIDE - A Configurable IDE console panel buffer
			// size
			AcideProjectConfiguration.getInstance().setBufferSize(
					Integer.parseInt(AcideResourceManager.getInstance().getProperty("consolePanel.bufferSize")));

			// Sets the ACIDE - A Configurable IDE console panel font name
			AcideProjectConfiguration.getInstance()
					.setFontName(AcideResourceManager.getInstance().getProperty("consolePanel.fontName"));

			// Sets the ACIDE - A Configurable IDE console panel font style
			AcideProjectConfiguration.getInstance().setFontStyle(
					Integer.parseInt(AcideResourceManager.getInstance().getProperty("consolePanel.fontStyle")));

			// Sets the ACIDE - A Configurable IDE console panel font size
			AcideProjectConfiguration.getInstance().setFontSize(
					Integer.parseInt(AcideResourceManager.getInstance().getProperty("consolePanel.fontSize")));

			// Saves the configuration into the file
			String fileContent = AcideProjectConfiguration.getInstance().save();

			// Writes the file content into it
			AcideFileManager.getInstance().write(AcideProjectConfiguration.getInstance().getProjectPath(), fileContent);

		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().error(exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE workbench configuration
	 * loaded flag.
	 * 
	 * @param loaded new value to set.
	 */
	public void setWorkbenchLoaded(boolean loaded) {
		_workbenchLoaded = loaded;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE workbench configuration loaded flag.
	 * 
	 * @return the ACIDE - A Configurable IDE workbench configuration loaded flag.
	 */
	public boolean isWorkbenchLoaded() {
		return _workbenchLoaded;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE file editor configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE file editor configuration.
	 */
	public AcideFileEditorConfiguration getFileEditorConfiguration() {
		return _fileEditorConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE file editor configuration.
	 * 
	 * @param fileEditorConfiguration new value to set.
	 */
	public void setFileEditorConfiguration(AcideFileEditorConfiguration fileEditorConfiguration) {
		_fileEditorConfiguration = fileEditorConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE recent projects configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE recent projects configuration.
	 */
	public AcideRecentProjectsConfiguration getRecentProjectsConfiguration() {
		return _recentProjectsConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE recent projects
	 * configuration.
	 * 
	 * @param recentProjectsConfiguration new value to set.
	 */
	public void setRecentProjectsConfiguration(AcideRecentProjectsConfiguration recentProjectsConfiguration) {
		_recentProjectsConfiguration = recentProjectsConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE recent files configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE recent files configuration.
	 */
	public AcideRecentFilesConfiguration getRecentFilesConfiguration() {
		return _recentFilesConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE recent files
	 * configuration.
	 * 
	 * @param recentFilesConfiguration new value to set.
	 */
	public void setRecentFilesConfiguration(AcideRecentFilesConfiguration recentFilesConfiguration) {
		_recentFilesConfiguration = recentFilesConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE lexicon assigner configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE lexicon assigner configuration.
	 */
	public AcideLexiconAssignerConfiguration getLexiconAssignerConfiguration() {
		return _lexiconAssignerConfiguration;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE lexicon assigner
	 * configuration.
	 * 
	 * @param lexiconAssignerConfiguration new value to set.
	 */
	public void setRecentProjectsConfiguration(AcideLexiconAssignerConfiguration lexiconAssignerConfiguration) {
		_lexiconAssignerConfiguration = lexiconAssignerConfiguration;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel configuration.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel configuration.
	 */
	public AcideConsolePanelConfiguration getConsolePanelConfiguration() {
		return _consolePanelConfiguration;
	}

	/**
	 * Sets a new value to the the ACIDE - A Configurable IDE console panel
	 * configuration.
	 * 
	 * @param consolePanelConfiguration new value to set.
	 */
	public void setConsolePanelConfiguration(AcideConsolePanelConfiguration consolePanelConfiguration) {
		_consolePanelConfiguration = consolePanelConfiguration;
	}
}
