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
package acide.gui.menuBar.configurationMenu.grammarMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import acide.configuration.menu.AcideInsertedItem;
import acide.configuration.menu.AcideInsertedItemListener;
import acide.configuration.menu.AcideInsertedMenu;
import acide.configuration.menu.AcideMenuConfiguration;
import acide.configuration.menu.AcideMenuItemConfiguration;
import acide.configuration.menu.AcideMenuItemsConfiguration;
import acide.configuration.menu.AcideMenuObjectConfiguration;
import acide.configuration.menu.AcideMenuSubmenuConfiguration;
import acide.gui.fileEditor.fileEditorPanel.AcideFileEditorPanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.configurationMenu.AcideConfigurationMenu;
import acide.gui.menuBar.listeners.AcideMenuBarMouseClickListener;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;
import acide.utils.IconsUtils;

/**
 * ACIDE - A Configurable IDE grammar analyze menu.
 * 
 * @version 0.19
 * @see ActionListener
 */
public class AcideGrammarAnalyzeMenu extends JMenu {

	/**
	 * ACIDE - A Configurable IDE grammar menu class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE grammar analyze menu name.
	 */
	public final static String ANALYZE_MENU_NAME = "Analyze";
	/**
	 * ACIDE - A Configurable IDE grammar analyze menu incremental analyze.
	 */
	public final static String INCREMENTAL_ANALYSIS = "Incremental Analysis";
	/**
	 * ACIDE - A Configurable IDE grammar analyze menu complete text analyze.
	 */
	public static final String COMPLETE_TEXT_ANALYSIS = "Complete Text Analysis";
	/**
	 * ACIDE - A Configurable IDE grammar analyze menu incremental analyze check box menu item.
	 */
	private JCheckBoxMenuItem _incrementalAnalysisMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar analyze menu incremental analyze menu item has been inserted.
	 */
	private boolean _incrementalAnalysisInserted;
	/**
	 * ACIDE - A Configurable IDE grammar analyze menu complete text analyze check box menu item.
	 */
	private JCheckBoxMenuItem _completeTextAnalysisMenuItem;
	/**
	 * ACIDE - A Configurable IDE grammar analyze menu complete text analyze menu item has been inserted.
	 */
	private boolean _completeTextAnalysisInserted;
	/**
	 * ACIDE - A Configurable IDE grammar menu configuration menu.
	 */
	private AcideMenuSubmenuConfiguration _grammarSubmenuConfiguration;
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

	/**
	 * Creates a new ACIDE - A Configurable IDE grammar analyzer menu.
	 */
	public AcideGrammarAnalyzeMenu() {
		
		_incrementalAnalysisInserted = false;
		_completeTextAnalysisInserted = false;
		
		_insertedItems = new HashMap<String, AcideInsertedItem>();
		
		_insertedMenus = new HashMap<String, AcideInsertedMenu>();
		
		_insertedObjects = new ArrayList<AcideMenuObjectConfiguration>();

		// Builds the menu components
		buildComponents();

		// Adds the components to the menu
		addComponents();

		// Sets the text of the grammar menu components
		setTextOfMenuComponents();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE grammar analyzer menu.
	 */
	private void addComponents() {
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getItemsManager()
				.getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME)
				.getSubmenu(ANALYZE_MENU_NAME)
				.getItemsManager().managerIterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (name.equals(COMPLETE_TEXT_ANALYSIS)){
				// Adds the complete text analysis menu item to the menu
				add(_completeTextAnalysisMenuItem);
				_completeTextAnalysisInserted = true;
			}else if (name.equals(INCREMENTAL_ANALYSIS)){
				// Adds the incremental analysis menu item to the menu
				add(_incrementalAnalysisMenuItem);
				_incrementalAnalysisInserted = true;
			}else {
				if (ob.isSubmenu()){
					add(_insertedMenus.get(ob.getName()));
				}else{
					add(_insertedItems.get(ob.getName()));
				}
			}
		}
		
		if (!_completeTextAnalysisInserted)
			add(_completeTextAnalysisMenuItem);
		if (!_incrementalAnalysisInserted)
			add(_incrementalAnalysisMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE grammar analyzer menu components.
	 */
	private void buildComponents() {
		if (!AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME)
				.hasSubmenu(ANALYZE_MENU_NAME)){
			AcideMenuItemsConfiguration.getInstance().getMenuItemsManager()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
					.insertObject(new AcideMenuSubmenuConfiguration(ANALYZE_MENU_NAME));
		}
		
		Iterator<Object> it = AcideMenuItemsConfiguration.getInstance()
				.getMenuItemsManager().getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME)
				.getItemsManager().getSubmenu(ANALYZE_MENU_NAME)
				.getItemsManager().managerIterator();
		
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = (AcideMenuObjectConfiguration) it.next();
			String name = ob.getName();
			if (isOriginal(name)){
				_insertedObjects.add(ob);
				if (ob.isSubmenu()){
					AcideMenuSubmenuConfiguration obSubmenu = (AcideMenuSubmenuConfiguration) ob;
					_insertedMenus.put(ob.getName(), new AcideInsertedMenu(obSubmenu));
				}else {
					AcideMenuItemConfiguration obItem = (AcideMenuItemConfiguration) ob;
					_insertedItems.put(obItem.getName(), new AcideInsertedItem(obItem));
				}
			}
		}

		// Creates the complete text analysis menu item
		_completeTextAnalysisMenuItem = new JCheckBoxMenuItem();
		
		// Sets the complete text analysis menu item name
		_completeTextAnalysisMenuItem.setName(COMPLETE_TEXT_ANALYSIS);
		
		// Creates the incremental analysis menu item
		_incrementalAnalysisMenuItem = new JCheckBoxMenuItem();
		
		// Sets the incremental analysis menu item name
		_incrementalAnalysisMenuItem.setName(INCREMENTAL_ANALYSIS);
		
		setListeners();
	}

	/**
	 * Sets the text of the ACIDE - A Configurable IDE grammar anlyze menu components
	 * with the labels in the selected language to display.
	 */
	public void setTextOfMenuComponents() {

		// Sets the complete text analysis menu item text
		_completeTextAnalysisMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2439"));

		// Sets the incremental analysis menu item accelerator
		_incrementalAnalysisMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2440"));
		
		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()){
				_insertedMenus.get(ob.getName()).setText(ob.getName());
				_insertedMenus.get(ob.getName()).setTextOfMenuComponents();
			}else{
				_insertedItems.get(ob.getName()).setText(ob.getName());
			}
		}
	}
	
	/**
	 * Gets if the menu name given as parameter is original
	 * @param name
	 * 		the name we want to check
	 * @return
	 * 		if the name given as parameter is original
	 */
	public boolean isOriginal(String name){
		if (!(name.equals(COMPLETE_TEXT_ANALYSIS))
			&& !(name.equals(INCREMENTAL_ANALYSIS))
			){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Sets the ACIDE - A Configurable IDE grammar analyze menu item listeners.
	 */
	public void setListeners() {

		// Sets the complete text analysis menu item action listener
		_completeTextAnalysisMenuItem
				//.addActionListener(new AcideCompleteTextAnalysisMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME)
				.getSubmenu(ANALYZE_MENU_NAME)
				.getItem(COMPLETE_TEXT_ANALYSIS)));

		// Sets the incremental analysis menu item action listener
		_incrementalAnalysisMenuItem
				//.addActionListener(new AcideIncrementalAnalysisMenuItemListener());
			.addActionListener(new AcideInsertedItemListener(
				AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME)
				.getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME)
				.getSubmenu(ANALYZE_MENU_NAME)
				.getItem(INCREMENTAL_ANALYSIS)));
		
		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()){
				_insertedMenus.get(ob.getName()).addMouseListener(new AcideMenuBarMouseClickListener());
				_insertedMenus.get(ob.getName()).setListeners();
			}else{
				AcideInsertedItem aux = _insertedItems.get(ob.getName());
				aux.addActionListener((new AcideInsertedItemListener(aux)));
			}
		}
	}

	/**
	 * Updates the ACIDE - A Configurable IDE grammar analyze menu components visibility
	 * with the menu configuration.
	 */
	public void updateComponentsVisibility() {
		
		AcideMenuItemConfiguration completeTextAnalysisConfiguration;
		AcideMenuItemConfiguration incrementalAnalysisConfiguration;
		
		_grammarSubmenuConfiguration = AcideMenuItemsConfiguration.getInstance()
					.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME);
			
		
		// Sets the complete text analysis menu item to visible or not visible
		completeTextAnalysisConfiguration = _grammarSubmenuConfiguration.getItem(COMPLETE_TEXT_ANALYSIS);
		_completeTextAnalysisMenuItem.setVisible(completeTextAnalysisConfiguration.isVisible());
			
		// Sets the incremental analysis menu item to visible or not visible
		incrementalAnalysisConfiguration = _grammarSubmenuConfiguration.getItem(INCREMENTAL_ANALYSIS);
		_incrementalAnalysisMenuItem.setVisible(incrementalAnalysisConfiguration.isVisible());

		
		Iterator<AcideMenuObjectConfiguration> it = _insertedObjects.iterator();
		while (it.hasNext()){
			AcideMenuObjectConfiguration ob = it.next();
			if (ob.isSubmenu()){
				_insertedMenus.get(ob.getName()).updateComponentsVisibility();
				_insertedMenus.get(ob.getName()).setVisible(ob.isVisible());
			}else{
				_insertedItems.get(ob.getName()).setVisible(ob.isVisible());
			}
		}
		
		// Sets the grammar analyze menu to visible or not visible
		Boolean b = AcideMenuItemsConfiguration.getInstance()
				.getSubmenu(AcideConfigurationMenu.CONFIGURATION_MENU_NAME).getSubmenu(AcideGrammarMenu.GRAMMAR_MENU_NAME).getSubmenu(ANALYZE_MENU_NAME).isVisible();
		_grammarSubmenuConfiguration.setVisible((_incrementalAnalysisMenuItem.isVisible()
						|| _completeTextAnalysisMenuItem.isVisible() && b));
		_grammarSubmenuConfiguration.setErasable(false);
		
				
		try{			
			//Save the configuration for the menu that could have been modified
			AcideMenuConfiguration.getInstance()
				.saveMenuConfigurationFile("./configuration/menu/lastModified.menuConfig");
			
			// Gets the the ACIDE - A Configurable IDE current menu
			// configuration
			String currentMenuConfiguration = AcideResourceManager
					.getInstance().getProperty("currentMenuConfiguration");

			if (!currentMenuConfiguration
					.endsWith("lastModified.menuConfig")
					&& !currentMenuConfiguration
							.endsWith("newMenu.menuConfig")) {

				// Updates the the ACIDE - A Configurable IDE previous
				// menu
				// configuration
				AcideResourceManager.getInstance().setProperty(
						"previousMenuConfiguration",
						currentMenuConfiguration);
			}
			
			// Updates the the ACIDE - A Configurable IDE current menu
			// configuration
			AcideResourceManager.getInstance().setProperty(
					"currentMenuConfiguration", "./configuration/menu/lastModified.menuConfig");
		}		
		catch (Exception exception2) {

			// Updates the log
			AcideLog.getLog().error(exception2.getMessage());
			exception2.printStackTrace();
		}
		

	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE grammar analyze menu complete text analysis check box
	 * menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE grammar analyze menu complete text analysis check box
	 *         menu item.
	 */
	public JCheckBoxMenuItem getCompleteTextAnalysisCheckBoxMenuItem() {
		return _completeTextAnalysisMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE grammar analyze menu incremental analysis check box
	 * menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE grammar analyze menu incremental analysis check box
	 *         menu item.
	 */
	public JCheckBoxMenuItem getIncrementalAnalysisCheckBoxMenuItem() {
		return _incrementalAnalysisMenuItem;
	}
}
