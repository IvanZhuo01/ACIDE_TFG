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
package acide.gui.menuBar.configurationMenu.themesMenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTML;

import com.jidesoft.utils.HtmlUtils;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.configuration.project.AcideProjectConfiguration;
import acide.gui.graphPanel.AcideGraphUtil;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.resources.exception.MissedPropertyException;
import acide.utils.IconsUtils;
import javafx.util.Pair;

/**
 * ACIDE - A Configurable IDE tool bar menu.
 * 
 * @version 0.11
 * @see JMenu
 */
public class AcideThemesMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE tool bar menu tool bar menu class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE themes menu name.
	 */
	public final static String THEME_MENU_NAME = "Themes";
	/**
	 * ACIDE - A Configurable IDE toolbar menu name.
	 */
	public final static String THEMES_CONFIGURATION = "Themes configuration";
	/**
	 * ACIDE - A Configurable IDE theme menu new theme menu item.
	 */
	private JMenuItem themesConfiguration;
	/**
	 * ACIDE - A Configurable IDE theme menu new theme menu item has been inserted.
	 */
	private boolean _themesConfigurationInserted;
	/**
	 * ACIDE - A Configurable IDE theme menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _themeSubmenuConfiguration;
	/**
	 * ACIDE - A Configurable IDE inserted menus hashmap.
	 */
	private HashMap<String, AcideInsertedMenu> _insertedMenus;
	/**
	 * ACIDE - A Configurable IDE inserted items hashmap.
	 */
	private HashMap<String, AcideInsertedItem> _insertedItems;
	/**
	 * ACIDE - A Configurable IDE array list of inserted objects.
	 */
	private ArrayList<AcideMenuObjectConfiguration> _insertedObjects;
	
	public static String activeTheme;

	/**
	 * Creates a new ACIDE - A Configurable IDE tool bar menu.
	 */
	public AcideThemesMenu() {

		_themesConfigurationInserted = false;

		_insertedItems = new HashMap<String, AcideInsertedItem>();

		_insertedMenus = new HashMap<String, AcideInsertedMenu>();

		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the tool bar menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE tool bar menu.
	 */
	public void addComponents() {
		File folder = new File("./configuration/themes");
		File[] themesFiles = folder.listFiles();
		Properties prop = new Properties();
		if (themesFiles.length != 0) {
			for (File arch : themesFiles) {
				String menName = arch.getName().replaceFirst("[.][^.]+$", "");
				JMenu sub= new JMenu();
				try {
					String x = AcideResourceManager.getInstance().getProperty("themeApplied");
					if(x.equals(menName)) {
						sub.setLabel("\u2713" + " " + x);
					}
					else
						sub.setLabel(menName);
				} catch (MissedPropertyException e2) {
					e2.printStackTrace();
				}
				//repaint();
				JMenuItem openMenuItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s335"),
						null);
				openMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
				openMenuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							FileInputStream in = new FileInputStream(arch.getPath());
							prop.load(in);

							String colorB = prop.getProperty("backgroundColor");
							String colorF = prop.getProperty("foregroundColor");
							Pair<Color, Color> colorGeneral = new Pair<Color, Color>(
									new Color(Integer.parseInt(colorB)), new Color(Integer.parseInt(colorF)));
							colorB = prop.getProperty("ConsolebackgroundColor");
							colorF = prop.getProperty("ConsoleforegroundColor");
							Pair<Color, Color> colorConsole = new Pair<Color, Color>(
									new Color(Integer.parseInt(colorB)), new Color(Integer.parseInt(colorF)));
							colorB = prop.getProperty("FileEditorbackgroundColor");
							colorF = prop.getProperty("FileEditorforegroundColor");
							Pair<Color, Color> colorFileEditor = new Pair<Color, Color>(
									new Color(Integer.parseInt(colorB)), new Color(Integer.parseInt(colorF)));
							String filePath = prop.getProperty("lexiconFileConfig");
							String consolePath = prop.getProperty("lexiconConsoleConfig");
							if(sub.getLabel().startsWith("\u2713")) activeTheme = sub.getLabel().substring(2);
							activeTheme = sub.getLabel();
							changeTheme(colorGeneral, colorConsole, colorFileEditor, filePath, consolePath);
							sub.setLabel("\u2713" + " " + sub.getLabel());
							
							in.close();
						} catch (IOException e1) {
						}
					}
				});
				if (!_insertedItems.containsKey(arch.getName().replaceFirst("[.][^.]+$", ""))) {
					sub.add(openMenuItem);
					JMenuItem b = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2386"));
					b.setHorizontalAlignment(SwingConstants.CENTER);
					b.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Object[] options1 = { AcideLanguageManager.getInstance().getLabels().getString("s2051"),
									AcideLanguageManager.getInstance().getLabels().getString("s2387"),
									AcideLanguageManager.getInstance().getLabels().getString("s40"),
									AcideLanguageManager.getInstance().getLabels().getString("s42") };

							JPanel panel = new JPanel();
							panel.add(new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2382")));
							JTextField textField = new JTextField(10);
							textField.setText(arch.getName().replaceFirst("[.][^.]+$", ""));
							panel.add(textField);
							boolean opcionesValidas = false;
							while (!opcionesValidas) {
								int result = JOptionPane.showOptionDialog(null, panel,
										AcideLanguageManager.getInstance().getLabels().getString("s2386"),
										JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1,
										options1[0]);
								if (result == 0) {
									File folder = new File("./configuration/themes");
									File[] themesFiles = folder.listFiles();
									int i = 0;
									boolean enUso = false;
									while (i < themesFiles.length && !enUso) {
										String name = themesFiles[i].getName();
										String newTheme = textField.getText() + ".properties";
										if (name.equalsIgnoreCase(newTheme))
											enUso = true;
										i++;
									}
									if (enUso) {
										JOptionPane.showMessageDialog(null,
												AcideLanguageManager.getInstance().getLabels().getString("s2384"));
									} else {
										File file = new File(
												"./configuration/themes/" + textField.getText() + ".properties");
										String ren = arch.getName().replaceFirst("[.][^.]+$", "");
										if(AcideThemesMenu.activeTheme.equals(ren)) {
											AcideThemesMenu.activeTheme = textField.getText();
											AcideResourceManager.getInstance().setProperty("themeApplied", AcideThemesMenu.activeTheme);
										}
										arch.renameTo(file);
										opcionesValidas = true;
									}
								}
								else
								if (result == 1) {
									arch.delete();
									opcionesValidas = true;
								}
								else
								if (result == 2) {
									String []nomFile = arch.getName().split("\\.");
									saveChangesOnTheme(nomFile[0]);
									opcionesValidas = true;
								}
								else{
									opcionesValidas = true;
								}
							}
							AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem()
									.removeAll();
							_insertedItems.clear();
							AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem()
									.addComponents();
							AcideMainWindow.getInstance().getMenu().getConfigurationMenu().repaint();
							// Apply changes to menuBar
							AcideMainWindow.getInstance().getMenu().paintMenuBar(
									AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getBackground(),
									AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getForeground());
						}
					});

					sub.add(b);
					add(sub);
					_insertedItems.put(arch.getName().replaceFirst("[.][^.]+$", ""), null);
				}
				
			}
		}

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getItemsManager()
				.getSubmenu(THEME_MENU_NAME).getItemsManager().managerIterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(THEMES_CONFIGURATION)) {
				// Adds the new tool bar menu item to the menu
				add(themesConfiguration);
				_themesConfigurationInserted = true;
			} else {
				if (ob.isSubmenu()) {
					add(_insertedMenus.get(ob.getName()));
				} else {
					add(_insertedItems.get(ob.getName()));
				}
			}
		}

		if (!_themesConfigurationInserted)
			add(themesConfiguration);

	}

	/**
	 * Builds the ACIDE - A Configurable IDE theme menu components.
	 */
	private void buildComponents() {

		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).hasSubmenu(THEME_MENU_NAME)) {
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(THEME_MENU_NAME));
		}

		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getItemsManager()
				.getSubmenu(THEME_MENU_NAME).getItemsManager().managerIterator();

		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (isOriginal(name)) {
				_insertedObjects.add(ob);
				if (ob.isSubmenu()) {
					AcideMenuSubmenuConfiguration obSubmenu = (AcideMenuSubmenuConfiguration) ob;
					_insertedMenus.put(ob.getName(), new AcideInsertedMenu(obSubmenu));
				} else {
					AcideMenuItemConfiguration obItem = (AcideMenuItemConfiguration) ob;
					_insertedItems.put(obItem.getName(),
							new AcideInsertedItem(IconsUtils.getIcon(obItem.getImage()), obItem));
				}
			}
		}

		// Creates the new theme menu item
		ImageIcon icon = IconsUtils.getIcon(AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(THEME_MENU_NAME)
				.getItem(THEMES_CONFIGURATION).getImage());
		if (icon != null)
			themesConfiguration = new JMenuItem(icon);
		else
			themesConfiguration = new JMenuItem();

		// Sets the new tool bar menu item name
		themesConfiguration.setName(THEME_MENU_NAME);
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE tool bar menu components with
	 * the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the new tool bar menu item text
		themesConfiguration.setText(AcideLanguageManager.getInstance().getLabels().getString("s2381"));

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).setText(ob.getName());
				_insertedMenus.get(ob.getName()).setTextOfMenuComponents();
			} else {
				_insertedItems.get(ob.getName()).setText(ob.getName());
			}
		}
	}

	/**
	 * Gets if the menu name given as parameter is original
	 * 
	 * @param name the name we want to check
	 * @return if the name given as parameter is original
	 */
	public boolean isOriginal(String name) {
		if (!(name.equals(THEMES_CONFIGURATION))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE tool bar menu menu item listeners.
	 */
	public void setListeners() {

		// Sets the new tool bar menu item action listener
		themesConfiguration
				// .addActionListener(new AcideNewToolBarMenuItemListener());
				.addActionListener(new AcideInsertedItemListener(AcideMenuItemsConfiguration.getInstance()
						.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(THEME_MENU_NAME)
						.getItem(THEMES_CONFIGURATION)));

	}

	/**
	 * Updates the ACIDE - A Configurable IDE tool bar menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {

		AcideMenuItemConfiguration themeConfiguration;

		_themeSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(THEME_MENU_NAME);

		// Sets the new tool bar menu item to visible or not visible
		themeConfiguration = _themeSubmenuConfiguration.getItem(THEMES_CONFIGURATION);
		themesConfiguration.setVisible(themeConfiguration.isVisible());

		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()) {
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()) {
				_insertedMenus.get(ob.getName()).updateComponentsVisibility();
				_insertedMenus.get(ob.getName()).setVisible(ob.isVisible());
			} else {
				_insertedItems.get(ob.getName()).setVisible(ob.isVisible());
			}
		}

		// Sets the tool bar menu to visible or not visible
		Boolean b = AcideMenuItemsConfiguration.getInstance().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(THEME_MENU_NAME).isVisible();
		_themeSubmenuConfiguration.setVisible(themesConfiguration.isVisible() && b);

		_themeSubmenuConfiguration.setErasable(false);

		try {
			// Save the configuration for the menu that could have been modified
			AcideMenuConfiguration.getInstance()
					.saveMenuConfigurationFile("./configuration/menu/lastModified.menuConfig");

			// Gets the the ACIDE - A Configurable IDE current menu
			// configuration
			String currentMenuConfiguration = AcideResourceManager.getInstance()
					.getProperty("currentMenuConfiguration");

			if (!currentMenuConfiguration.endsWith("lastModified.menuConfig")
					&& !currentMenuConfiguration.endsWith("newMenu.menuConfig")) {

				// Updates the the ACIDE - A Configurable IDE previous
				// menu
				// configuration
				AcideResourceManager.getInstance().setProperty("previousMenuConfiguration", currentMenuConfiguration);
			}

			// Updates the the ACIDE - A Configurable IDE current menu
			// configuration
			AcideResourceManager.getInstance().setProperty("currentMenuConfiguration",
					"./configuration/menu/lastModified.menuConfig");
		} catch (Exception exception2) {

			// Updates the log
			AcideLog.getLog().error(exception2.getMessage());
			exception2.printStackTrace();
		}

	}

	/**
	 * Returns the ACIDE - A Configurable IDE tool bar menu new tool bar menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE tool bar menu new tool bar menu item.
	 */
	public JMenuItem getThemeMenuItem() {
		return themesConfiguration;
	}

	private void changeTheme(Pair<Color, Color> colorGeneral, Pair<Color, Color> colorConsole,
			Pair<Color, Color> colorFileEditor, String pathFile, String pathConsole) {
		// Updates the log
		AcideLog.getLog().info("1043");

		Color backgroundColor = colorGeneral.getKey();
		Color foregroundColor = colorGeneral.getValue();

		Color consoleBack = colorConsole.getKey();
		Color consoleFore = colorConsole.getValue();

		Color fileBack = colorFileEditor.getKey();
		Color fileFore = colorFileEditor.getValue();

		// Apply the changes to the opened file editor panels
		Color darker = new Color((int) (backgroundColor.getRed() * 0.9), (int) (backgroundColor.getGreen() * 0.9),
				(int) (backgroundColor.getBlue() * 0.9));
		AcideMainWindow.getInstance().getFileEditorManager().setBackground(backgroundColor);
		AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().setOpaque(true);
		AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().setBackground(darker);
		AcideMainWindow.getInstance().getFileEditorManager().getTabbedPane().setForeground(foregroundColor);
		for (int index = 0; index < AcideMainWindow.getInstance().getFileEditorManager()
				.getNumberOfFileEditorPanels(); index++) {

			// Updates the ACIDE - A Configurable IDE file editor

			AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index).getActiveTextEditionArea()
					.setBackground(fileBack);
			AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index).getActiveTextEditionArea()
					.setForeground(fileFore);
			AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index).changeColor(fileBack,
					foregroundColor, darker);
			AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(index).setEditable(true);
		}

		// Apply changes to toolbar
		AcideMainWindow.getInstance().getToolBarPanel().changeColor(darker, foregroundColor);

		// Apply changes to statusBar
		AcideMainWindow.getInstance().getStatusBar().changeColor(darker, foregroundColor);

		// Apply changes to menuBar
		AcideMainWindow.getInstance().getMenu().paintMenuBar(darker, foregroundColor);

		// Apply changes to the database panel
		AcideMainWindow.getInstance().getDataBasePanel().changeColor(backgroundColor, foregroundColor);

		// Apply changes to the explorer panel
		AcideMainWindow.getInstance().getExplorerPanel().setBackgroundColor(backgroundColor, foregroundColor, darker);

		// Apply changes to debugPanel
		AcideMainWindow.getInstance().getDebugPanel().setBackgroundColor(backgroundColor, foregroundColor, darker);
		AcideMainWindow.getInstance().getGraphPanel().setBackgroundColor(backgroundColor, foregroundColor, darker);
		SwingUtilities.invokeLater(() -> AcideGraphUtil.refreshGraphPanel());

		// Apply changes to the console panel
		AcideMainWindow.getInstance().getConsolePanel().changeColor(consoleBack, consoleFore, darker);
		AcideResourceManager.getInstance().setProperty("consolePanel.backgroundColor",
				Integer.toString(consoleBack.getRGB()));
		AcideResourceManager.getInstance().setProperty("consolePanel.foregroundColor",
				Integer.toString(consoleFore.getRGB()));

		AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getLexiconMenu().documentLexicon(pathFile);
		AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getLexiconMenu().consoleLexicon(pathConsole);
		// Notify that main configuration has been changed
		AcideProjectConfiguration.getInstance().setIsModified(true);
	}

	public HashMap<String, AcideInsertedItem> getInsertedItem() {
		return this._insertedItems;
	}

	public void saveChangesOnTheme(String themeFile) {
		try {
			File file = new File("./configuration/themes/" + themeFile + ".properties");

			Properties prop = new Properties();

			// set the properties value
			prop.setProperty("backgroundColor",
					String.valueOf(AcideMainWindow.getInstance().getExplorerPanel().getBackgroundColor().getRGB()));
			prop.setProperty("foregroundColor",
					String.valueOf(AcideMainWindow.getInstance().getExplorerPanel().getForegroundColor().getRGB()));
			prop.setProperty("ConsolebackgroundColor", String.valueOf(
					AcideMainWindow.getInstance().getConsolePanel().getTextPane().getBackground().getRGB()));
			prop.setProperty("ConsoleforegroundColor", String.valueOf(
					AcideMainWindow.getInstance().getConsolePanel().getTextPane().getForeground().getRGB()));
			prop.setProperty("FileEditorbackgroundColor",
					String.valueOf(AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(0)
							.getActiveTextEditionArea().getBackground().getRGB()));
			prop.setProperty("FileEditorforegroundColor",
					String.valueOf(AcideMainWindow.getInstance().getFileEditorManager().getFileEditorPanelAt(0)
							.getActiveTextEditionArea().getForeground().getRGB()));

			prop.setProperty("lexiconFileConfig", AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel().getLexiconConfiguration().getPath());
			prop.setProperty("lexiconConsoleConfig",
					AcideMainWindow.getInstance().getConsolePanel().getLexiconConfiguration().getPath());

			// save properties to project root folder
			FileOutputStream in = new FileOutputStream(file.getPath());
			prop.store(in, null);
			in.close();
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem().removeAll();
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem().getInsertedItem().clear();
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getThemeMenuItem().addComponents();
			AcideMainWindow.getInstance().getMenu().getConfigurationMenu().repaint();
			// Apply changes to menuBar
			AcideMainWindow.getInstance().getMenu().paintMenuBar(
					AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getBackground().darker(),
					AcideMainWindow.getInstance().getMenu().getConfigurationMenu().getForeground());
		} catch (Exception io) {

		}
	}
}
