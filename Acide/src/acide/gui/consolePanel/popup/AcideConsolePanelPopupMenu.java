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
package acide.gui.consolePanel.popup;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import acide.gui.consolePanel.popup.listeners.AcideClearConsoleBufferMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideCloseConsoleMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideConsoleDisplayOptionsMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideConsolePanelSearchMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideControlCMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideCopyMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideCutMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideDocumentLexiconMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideLineWrappingMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcidePasteMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideResetMenuItemAction;
import acide.gui.consolePanel.popup.listeners.AcideSaveConsoleIntoFileMenuItemAction;
import acide.language.AcideLanguageManager;

/**
 * ACIDE - A Configurable IDE console panel popup menu.
 * 
 * @version 0.11
 * @see JPopupMenu
 * @see JMenuItem
 */
public class AcideConsolePanelPopupMenu extends JPopupMenu {

	/**
	 * ACIDE - A Configurable IDE console panel popup menu class serial version
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu copy menu item image
	 * icon.
	 */
	private final static ImageIcon COPY_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/copy.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu paste menu item image
	 * icon.
	 */
	private final static ImageIcon PASTE_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/paste.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu cut menu item image
	 * icon.
	 */
	private final static ImageIcon CUT_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/cut.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu search menu item
	 * image icon.
	 */
	private final static ImageIcon SEARCH_IMAGE = new ImageIcon(
			"./resources/icons/menu/edit/search.png");

	/**
	 * ACIDE - A Configurable IDE console panel popup menu control+C menu item
	 * image icon.
	 */
	private final static ImageIcon CONTROL_C_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/controlC.png");

	/**
	 * ACIDE - A Configurable IDE console panel popup menu clear console buffer
	 * menu item image icon.
	 */
	private final static ImageIcon CLEAR_CONSOLE_BUFFER_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/clearConsoleBuffer.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu reset console menu
	 * item image icon.
	 */
	private final static ImageIcon RESET_CONSOLE_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/resetConsole.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu close console menu
	 * item image icon.
	 */
	private final static ImageIcon CLOSE_CONSOLE_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/closeConsole.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu shell display options
	 * menu item image icon.
	 */
	private final static ImageIcon CONSOLE_DISPLAY_OPTIONS_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/consoleDisplayOptions.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu document lexicon menu
	 * item image icon.
	 */
	private final static ImageIcon DOCUMENT_LEXICON_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/documentLexicon.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu save console content
	 * into file menu item image icon.
	 */
	private final static ImageIcon SAVE_CONSOLE_CONTENT_INTO_FILE_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/saveContentIntoFile.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu console line
	 * wrapping
	 */
	private final static ImageIcon LINE_WRAPPING_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/console/lineWrapping.png");
	/**
	 * ACIDE - A Configurable IDE console panel popup menu copy menu item.
	 */
	private JMenuItem _copyMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu cut menu item.
	 */
	private JMenuItem _cutMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu paste menu item.
	 */
	private JMenuItem _pasteMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu shell display options
	 * menu item.
	 */
	private JMenuItem _consoleDisplayOptionsMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu document lexicon menu
	 * item.
	 */
	private JMenuItem _documentLexiconMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu reset menu item.
	 */
	private JMenuItem _resetMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu control+C menu item.
	 */
	private JMenuItem _controlCMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu clear console buffer
	 * menu item.
	 */
	private JMenuItem _clearConsoleBufferMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu search menu item.
	 */
	private JMenuItem _searchMenuItem;
	/**
	 * ACIDE - A Configurable IDE console panel popup menu close console menu
	 * item.
	 */
	private JMenuItem _closeConsoleMenuItem;
	/**
	 * ACIDE - A Configurable IDE console menu save console content into file
	 * menu item.
	 */
	private JMenuItem _saveConsoleContentIntoFile;
	/**
	 * ACIDE - A Configurable IDE console line wrapping check box menu item
	 */
	private JCheckBoxMenuItem _lineWrappingMenuItem;


	/**
	 * Creates a new ACIDE - A Configurable IDE console panel popup menu.
	 */
	public AcideConsolePanelPopupMenu() {

		// Builds the components
		buildComponents();

		// Adds the components to the popup menu
		addComponents();

		// Sets the listeners of the window components
		setListeners();
	}

	/**
	 * Adds the components to the ACIDE - A Configurable IDE console panel popup
	 * menu.
	 */
	private void addComponents() {

		// Adds the console display options to the popup menu
		add(_consoleDisplayOptionsMenuItem);
		
		// Adds the console line wrapping to the popup menu
		add(_lineWrappingMenuItem);

		// Adds a separator to the popup menu
		addSeparator();

		// Adds the save console content into file popup menu
		add(_saveConsoleContentIntoFile);

		// Adds a separator to the popup menu
		addSeparator();

		// Adds the document lexicon menu item to the popup menu
		add(_documentLexiconMenuItem);

		// Adds a separator to the popup menu
		addSeparator();

		// Adds the copy menu item to the popup menu
		add(_copyMenuItem);

		// Adds the cut menu item to the popup menu
		add(_cutMenuItem);

		// Adds the paste menu item to the popup menu
		add(_pasteMenuItem);

		// Adds a separator to the popup menu
		addSeparator();
		
		String OSName = System.getProperty("os.name");

		// If it is WINDOWS
		if (!OSName.toUpperCase().contains("WIN")) {

			// Adds the controlC menu item to the popup menu
			 add(_controlCMenuItem);
		 
		}

		// Adds the reset menu item to the popup menu
		add(_resetMenuItem);

		// Adds the clear console buffer menu item to the popup menu
		add(_clearConsoleBufferMenuItem);

		// Adds a separator to the popup menu
		addSeparator();

		// Adds the search menu item to the popup menu
		add(_searchMenuItem);

		// Adds a separator to the popup menu
		addSeparator();

		// Adds the close console menu item to the popup menu
		add(_closeConsoleMenuItem);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE console panel popup menu
	 * components.
	 */
	private void buildComponents() {

		// Creates the console display options menu item
		_consoleDisplayOptionsMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s986"),
				CONSOLE_DISPLAY_OPTIONS_IMAGE);

		// Creates the save console content into file menu item
		_saveConsoleContentIntoFile = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s2013"),
				SAVE_CONSOLE_CONTENT_INTO_FILE_IMAGE);

		// Creates the document lexicon menu item
		_documentLexiconMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s1093"),
				DOCUMENT_LEXICON_IMAGE);

		// Creates the copy menu item
		_copyMenuItem = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s187"), COPY_IMAGE);

		// Creates the cut menu item
		_cutMenuItem = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s188"), CUT_IMAGE);

		// Creates the paste menu item
		_pasteMenuItem = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s189"), PASTE_IMAGE);

		String OSName = System.getProperty("os.name");

		// If it is WINDOWS
		if (!OSName.toUpperCase().contains("WIN")) {
		
			// Creates the controlC menu item
			_controlCMenuItem = new JMenuItem(AcideLanguageManager.getInstance()
					.getLabels().getString("s2289"), CONTROL_C_IMAGE);
			
		}

		// Creates the reset menu item
		_resetMenuItem = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s987"), RESET_CONSOLE_IMAGE);

		// Creates the clear console buffer menu item
		_clearConsoleBufferMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s999"),
				CLEAR_CONSOLE_BUFFER_IMAGE);

		// Creates the search menu item
		_searchMenuItem = new JMenuItem(AcideLanguageManager.getInstance()
				.getLabels().getString("s556"), SEARCH_IMAGE);
		
		// Creates the console line wrapping check box menu item
		_lineWrappingMenuItem = new JCheckBoxMenuItem(AcideLanguageManager.getInstance().
				getLabels().getString("s2014"), LINE_WRAPPING_IMAGE);
		
		// Selected by default
		_lineWrappingMenuItem.setSelected(true);
		

		// Sets the search menu item accelerator
		if (AcideLanguageManager.getInstance().getCurrentLocale()
				.equals(new Locale("en", "EN"))
			|| AcideLanguageManager.getInstance().getCurrentLocale()
			.equals(new Locale("fr", "FR")))
			_searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F, ActionEvent.CTRL_MASK
							+ ActionEvent.SHIFT_MASK));
		
		else
			_searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_B, ActionEvent.CTRL_MASK
							+ ActionEvent.SHIFT_MASK));

		// Creates the close console menu item
		_closeConsoleMenuItem = new JMenuItem(AcideLanguageManager
				.getInstance().getLabels().getString("s1099"),
				CLOSE_CONSOLE_IMAGE);
	}

	/**
	 * Sets the listeners of the window components.
	 */
	public void setListeners() {

		// Sets the console display options menu item action listener
		_consoleDisplayOptionsMenuItem
				.addActionListener(new AcideConsoleDisplayOptionsMenuItemAction());

		// Sets the save console content into file menu item action listener
		_saveConsoleContentIntoFile
				.addActionListener(new AcideSaveConsoleIntoFileMenuItemAction());

		// Sets the document lexicon menu item action listener
		_documentLexiconMenuItem
				.addActionListener(new AcideDocumentLexiconMenuItemAction());

		// Sets the copy menu item action listener
		_copyMenuItem.addActionListener(new AcideCopyMenuItemAction());

		// Sets the cut menu item action listener
		_cutMenuItem.addActionListener(new AcideCutMenuItemAction());

		// Sets the paste menu item action listener
		_pasteMenuItem.addActionListener(new AcidePasteMenuItemAction());

		String OSName = System.getProperty("os.name");

		// If it is WINDOWS
		if (!OSName.toUpperCase().contains("WIN")) {
		
			// Sets the controlC menu item action listener
			_controlCMenuItem.addActionListener(new AcideControlCMenuItemAction());
		
		}

		// Sets the reset menu item action listener
		_resetMenuItem.addActionListener(new AcideResetMenuItemAction());

		// Sets the clear console buffer menu item action listener
		_clearConsoleBufferMenuItem
				.addActionListener(new AcideClearConsoleBufferMenuItemAction());

		// Sets the search menu item action listener
		_searchMenuItem
				.addActionListener(new AcideConsolePanelSearchMenuItemAction());

		// Sets the close console menu item action listener
		_closeConsoleMenuItem
				.addActionListener(new AcideCloseConsoleMenuItemAction());
		
		// Sets the console line wrapping check box menu item action listener
		_lineWrappingMenuItem
				.addActionListener(new AcideLineWrappingMenuItemAction());
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel popup menu copy menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel popup menu copy menu
	 *         item.
	 */
	public JMenuItem getCopyMenuItem() {
		return _copyMenuItem;
	}

	public JMenuItem get_searchMenuItem() {
		return _searchMenuItem;
	}

	public void set_searchMenuItem(JMenuItem _searchMenuItem) {
		this._searchMenuItem = _searchMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel popup menu copy menu
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel popup menu copy menu
	 *         item.
	 */
	public JMenuItem getCutMenuItem() {
		return _cutMenuItem;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE console panel popup menu paste
	 * menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel popup menu paste
	 *         menu item.
	 */
	public JMenuItem getPasteMenuItem() {
		return _pasteMenuItem;
	}
	
	/**
	 * Returns the ACIDE - A Configurable IDE console panel popup menu line
	 * wrapping check box menu item.
	 * 
	 * @return the ACIDE - A Configurable IDE console panel popup menu line
	 *         wrapping check box menu item.
	 */
	public JCheckBoxMenuItem getLineWrappingMenuItem() {
		return _lineWrappingMenuItem;
	}
	
	/**
	 * Sets the ACIDE - A Configurable IDE console panel popup menu line
	 * wrapping check box menu item.
	 * 
	 * @param lineWrappingMenuItem
	 * 		new check box menu item to set
	 */
	public void setLineWrappingMenuItem(JCheckBoxMenuItem lineWrappingMenuItem) {
		_lineWrappingMenuItem = lineWrappingMenuItem;
	}
}
