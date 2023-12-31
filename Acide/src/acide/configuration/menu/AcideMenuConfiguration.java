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
package acide.configuration.menu;

import java.util.ArrayList;

import acide.language.AcideLanguageManager;

import acide.configuration.menu.exception.AcideMenuConfigurationFileFormatException;
import acide.files.AcideFileManager;

/**
 * ACIDE - A Configurable IDE menu configuration.
 * 
 * @version 0.11
 */
public class AcideMenuConfiguration {

	/**
	 * ACIDE - A Configurable IDE menu configuration default path;
	 */
	public static final String DEFAULT_PATH = ".configuration/menu/allOn.menuConfig";
	/**
	 * ACIDE - A Configurable IDE menu configuration unique class instance;
	 */
	private static AcideMenuConfiguration _instance;

	/**
	 * ACIDE - A Configurable IDE menu configuration item list.
	 */
	private static ArrayList<AcideMenuItemInformation> _menuItemList;

	/**
	 * Creates a new ACIDE - A Configurable IDE menu configuration.
	 */
	public AcideMenuConfiguration() {

		// Creates the menu item list
		_menuItemList = new ArrayList<AcideMenuItemInformation>();
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu configuration unique class
	 * instance.
	 * 
	 * @return the ACIDE - A Configurable IDE menu configuration unique class
	 *         instance
	 */
	public static AcideMenuConfiguration getInstance() {

		if (_instance == null)
			_instance = new AcideMenuConfiguration();
		return _instance;
	}

	/**
	 * Saves the ACIDE - A Configurable IDE menu configuration into a configuration
	 * file.
	 * 
	 * @param fileName file name
	 */
	public void saveMenuConfigurationFile(String fileName) {

		String fileContent = "";

		for (AcideMenuItemInformation menuElement : _menuItemList)
			fileContent += menuElement.getName() + " = " + menuElement.getIsDisplayed() + System.lineSeparator();

		// Saves its content
		AcideFileManager.getInstance().write(fileName, fileContent);
	}

	/**
	 * Loads the ACIDE - A Configurable IDE menu configuration from a file and
	 * stores the info in the menu item information array list.
	 * 
	 * @param filePath file path.
	 * @return the new menu item list with the loaded values from the menu
	 *         configuration file given as a parameter.
	 * 
	 * @throws Exception when any problem occurs during the process.
	 */
	public ArrayList<AcideMenuItemInformation> loadMenuConfigurationFile(String filePath) throws Exception {

		// Clear the list
		_menuItemList.clear();

		// Loads its content
		String fileContent = AcideFileManager.getInstance().load(filePath);

		// If there were problems during the loading process
		if (fileContent == null)

			// Loads the default file content
			fileContent = AcideFileManager.getInstance().load(DEFAULT_PATH);

		// Split the file content by lines
		String[] menuItems = fileContent.split("\n");

		// For each one of the lines, builds the menu item information
		// and adds it to the menu item list.
		for (int index = 0; index < menuItems.length; index++) {

			// Gets the equals symbol index
			int equalsSymbolIndex = menuItems[index].lastIndexOf(" = ");

			// If the format is incorrect
			if (equalsSymbolIndex == -1)
				throw new AcideMenuConfigurationFileFormatException(
						AcideLanguageManager.getInstance().getLabels().getString("s533"));
			else {

				// Gets the name
				String name = menuItems[index].substring(0, equalsSymbolIndex);

				// Gets the is displayed string
				String isDisplayedString = menuItems[index].substring(equalsSymbolIndex + 3, menuItems[index].length());

				boolean isDisplayed = false;

				// Makes the String to boolean conversion
				if (isDisplayedString.contains("true"))
					isDisplayed = true;
				else if (isDisplayedString.contains("false"))
					isDisplayed = false;
				else
					throw new AcideMenuConfigurationFileFormatException(
							AcideLanguageManager.getInstance().getLabels().getString("s533"));

				// Adds the new menu item information
				_menuItemList.add(new AcideMenuItemInformation(name, isDisplayed));
			}
		}

		// Returns the the new menu item list with the loaded values
		return _menuItemList;
	}

	/**
	 * Enables all the menu bar items.
	 */
	public void allMenuItemsEnabled() {

		for (AcideMenuItemInformation menuElement : _menuItemList)
			menuElement.setIsDisplayed(true);
	}

	/**
	 * Disables all the menu bar items <b>except the menu menu item option</b>,
	 * which has to be always displayed.
	 */
	public void allMenuItemsDisabled() {

		for (AcideMenuItemInformation menuItem : _menuItemList)
			if (!menuItem.getName().matches("menu"))
				menuItem.setIsDisplayed(false);
	}

	/**
	 * Returns the menu item display flag of the menu item from the list specified
	 * by the parameter menuItemName.
	 * 
	 * @param menuItemName menu item name to get its display flag
	 * 
	 * @return the menu item display flag of the menu item from the list specified
	 *         by the parameter menuItemName
	 */
	public boolean getIsDisplayed(String menuItemName) {

		for (AcideMenuItemInformation menuItem : _menuItemList)
			if (menuItem.getName().equals(menuItemName))
				return menuItem.getIsDisplayed();
		return false;
	}

	/**
	 * Set a new value to the display flag of the menu item from the list specified
	 * by the parameter menuItemName.
	 * 
	 * @param menuItemName menu item name to set the new value for his display flag
	 * 
	 * @param value        value for the display flag of menuItem
	 */
	public void setIsDisplayed(String menuItemName, boolean value) {

		for (AcideMenuItemInformation menuItem : _menuItemList)
			if (menuItem.getName().equals(menuItemName)) {
				menuItem.setIsDisplayed(value);
			}
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE menu configuration menu
	 * item list.
	 * 
	 * @param menuItemList new values to set
	 */
	public void setMenuElementList(ArrayList<AcideMenuItemInformation> menuItemList) {

		_menuItemList = menuItemList;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE menu configuration menu item list.
	 * 
	 * @return the ACIDE - A Configurable IDE menu configuration menu item list
	 */
	public ArrayList<AcideMenuItemInformation> getMenuItemList() {
		return _menuItemList;
	}

	public boolean isOnConfigFile(String menuItemName) {
		for (AcideMenuItemInformation menuItem : _menuItemList) {
			if (menuItem.getName().equals(menuItemName)) {
				return true;
			}
		}
		return false;
	}

}
