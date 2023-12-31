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
package acide.gui.explorerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import acide.configuration.lexicon.validExtensions.AcideValidExtensionsManager;
import acide.configuration.project.AcideProjectConfiguration;
import acide.files.project.AcideProjectFile;
import acide.gui.explorerPanel.listeners.AcideExplorerPanelFocusListener;
import acide.gui.explorerPanel.listeners.AcideExplorerPanelKeyboardListener;
import acide.gui.explorerPanel.listeners.AcideExplorerPanelMouseListener;
import acide.gui.explorerPanel.listeners.AcideExplorerPanelPopupMenuListener;
import acide.gui.explorerPanel.popup.AcideExplorerPanelPopupMenu;
import acide.gui.explorerPanel.utils.AcideExplorerTreeCellRenderer;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.log.AcideLog;
import acide.resources.AcideResourceManager;

/**
 * ACIDE - A Configurable IDE explorer panel.
 * 
 * @version 0.11
 * @see JPanel
 */
public class AcideExplorerPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE explorer panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE explorer panel tree of folders and files.
	 */
	private JTree _tree;
	/**
	 * ACIDE - A Configurable IDE explorer panel tree model for display the
	 * files.
	 */
	private DefaultMutableTreeNode _root;
	/**
	 * ACIDE - A Configurable IDE explorer panel tree model for display the
	 * directories.
	 */
	private DefaultTreeModel _treeModel;
	/**
	 * ACIDE - A Configurable IDE explorer panel popup menu.
	 */
	private AcideExplorerPanelPopupMenu _popupMenu;
	/**
	 * ACIDE - A Configurable IDE explorer panel size.
	 */
	private int _size;
	/**
	 * ACIDE - A Configurable IDE explorer panel default size.
	 */
	private int DEFAULT_SIZE = 150;
	/**
	 * ACIDE - A Configurable IDE explorer panel menu bar.
	 */
	private explorePanelBar menuBar;
	/**
	 * ACIDE - A Configurable IDE explorer panel name of the container.
	 */
	private String _splitContainer;

	/**
	 * Creates a new ACIDE - A Configurable IDE explorer panel.
	 */
	public AcideExplorerPanel() {

		// Sets the layout
		setLayout(new BorderLayout());
		
		try {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s324"));

			// Creates the popup menu
			buildPopupMenu();

			// Builds the tree
			_root = new DefaultMutableTreeNode("raiz");

			// Creates the tree model
			_treeModel = new DefaultTreeModel(_root);

			// Sets asks allows children as true
			_treeModel.setAsksAllowsChildren(true);

			// Creates the tree
			_tree = new JTree(_treeModel);

			// The root is not visible
			_tree.setRootVisible(false);

			// Sets the single selection mode
			_tree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);

			// Shows the root handles
			_tree.setShowsRootHandles(true);

			// Sets the cell renderer
			_tree.setCellRenderer(new AcideExplorerTreeCellRenderer());

			// Sets the auto scroll as true
			_tree.setAutoscrolls(true);

			// Sets the ACIDE - A Configurable IDE explorer panel listeners
			setListeners();
			
			// Builds the menu bar	
			buildMenuBar();
			
			try {

				// Sets the hand cursor
				_tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} catch (HeadlessException exception) {

				// Updates the log
				AcideLog.getLog().error(exception.getMessage());
				exception.printStackTrace();
			}
		} catch (Exception exception) {

			// Updates the log
			AcideLog.getLog().info(
					AcideLanguageManager.getInstance().getLabels()
							.getString("s325"));
			exception.printStackTrace();
		}

		// Updates the log
		AcideLog.getLog().info(
				AcideLanguageManager.getInstance().getLabels()
						.getString("s326"));

		// Adds the scroll pane
		add(new JScrollPane((JTree) _tree), BorderLayout.CENTER);
		
		// Adds the menu bar
		add(menuBar, BorderLayout.NORTH);
	}

	/**
	 * Sets the ACIDE - A Configurable IDE explorer panel listeners.
	 */
	private void setListeners() {

		// Sets the ACIDE - A Configurable IDE explorer panel focus listener
		_tree.addFocusListener(new AcideExplorerPanelFocusListener());

		// Sets the ACIDE - A Configurable IDE explorer panel popup menu
		// listener
		_tree.addMouseListener(new AcideExplorerPanelPopupMenuListener());

		// Sets the ACIDE - A Configurable IDE explorer double click listener
		_tree.addMouseListener(new AcideExplorerPanelMouseListener());

		// Sets the ACIDE - A Configurable IDE explorer panel keyboard listener
		_tree.addKeyListener(new AcideExplorerPanelKeyboardListener());
	}

	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel popup menu.
	 * 
	 * @see AcideExplorerPanelPopupMenu
	 */
	public void buildPopupMenu() {
		_popupMenu = new AcideExplorerPanelPopupMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 120);
	}

	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel tree.
	 * 
	 * @param node
	 *            node to add to the tree.
	 * @param file
	 *            file to add.
	 */
	public void buildTree(DefaultMutableTreeNode node, File file) {

		if (!file.isDirectory()) {
			if (AcideValidExtensionsManager.getInstance().isValidExtension(
					file.getName())) {
				DefaultMutableTreeNode son = new DefaultMutableTreeNode(file);
				node.add(son);
			}
		} else {
			DefaultMutableTreeNode son = new DefaultMutableTreeNode(file);
			node.add(son);
			File fileList[] = file.listFiles();
			for (int index = 0; index < fileList.length; index++)
				buildTree(son, fileList[index]);
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE explorer panel tree root.
	 * 
	 * @return the ACIDE - A Configurable IDE explorer panel tree root.
	 */
	public DefaultMutableTreeNode getRoot() {
		return _root;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE explorer panel tree.
	 * 
	 * @return the ACIDE - A Configurable IDE explorer panel tree.
	 */
	public JTree getTree() {
		return _tree;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE explorer panel tree.
	 * 
	 * @param tree
	 *            new value to set.
	 */
	public void setTree(JTree tree) {
		_tree = tree;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE explorer panel root.
	 * 
	 * @param root
	 *            new value to set.
	 */
	public void setRoot(DefaultMutableTreeNode root) {
		_root = root;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE explorer panel tree model.
	 * 
	 * @return the ACIDE - A Configurable IDE explorer panel tree model.
	 */
	public DefaultTreeModel getTreeModel() {
		return _treeModel;
	}

	/**
	 * Set a new value to the ACIDE - A Configurable IDE explorer panel tree
	 * model.
	 * 
	 * @param treeModel
	 *            new value to set.
	 */
	public void setTreeModel(DefaultMutableTreeNode treeModel) {
		_treeModel = new DefaultTreeModel(treeModel);
	}

	/**
	 * Closes the ACIDE - A Configurable IDE explorer panel.
	 */
	public void disposeExplorerPanel() {
		
		JSplitPane container = AcideMainWindow.getInstance().getSpecificSplitPane(_splitContainer);
		_size = container.getDividerLocation();
		if (container.getLeftComponent().getClass() == this.getClass()) {
			if (!container.getRightComponent().isVisible())
				_size = 300;
		} else if (container.getRightComponent().getClass() == this.getClass())
			if (!container.getLeftComponent().isVisible())
				_size = 300;
		setVisible(false);
		
		AcideMainWindow.getInstance().updateVisibility();

	}

	/**
	 * Shows the ACIDE - A Configurable IDE explorer panel.
	 */
	public void showExplorerPanel() {
		
		if (_size <= 1) _size = DEFAULT_SIZE;
		
		JSplitPane container = AcideMainWindow.getInstance().getSpecificSplitPane(_splitContainer);		
		container.setDividerLocation(_size);
		setVisible(true);
		
		AcideMainWindow.getInstance().updateVisibility();
		
	}

	/**
	 * Expands the ACIDE - A Configurable IDE explorer panel tree.
	 */
	public void expandTree() {

		int row = 0;
		while (row < getTree().getRowCount()) {
			getTree().expandRow(row);
			row++;
		}
	}

	/**
	 * Returns the ACIDE - A Configurable IDE explorer panel size.
	 * 
	 * @return the ACIDE - A Configurable IDE explorer panel size.
	 */
	public int getExplorerSize() {
		return _size;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE explorer panel size.
	 * 
	 * @param explorerSize
	 *            new value to set.
	 */
	public void setExplorerSize(int explorerSize) {
		_size = explorerSize;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE explorer panel popup menu.
	 * 
	 * @return the ACIDE - A Configurable IDE explorer panel popup menu.
	 * @see AcideExplorerPanelPopupMenu
	 */
	public AcideExplorerPanelPopupMenu getPopupMenu() {
		return _popupMenu;
	}

	/**
	 * Selects the explorer tree node that matches with the currently selected
	 * file editor panel.
	 */
	public void selectTreeNodeFromFileEditor() {

		// If it is not the default project
		if (!AcideProjectConfiguration.getInstance().isDefaultProject()) {

			// Editor selected?
			if (AcideMainWindow.getInstance().getFileEditorManager()
					.getSelectedFileEditorPanel() != null) {

				// Creates the ACIDE - A Configurable IDE project file
				AcideProjectFile file = new AcideProjectFile();
				int fileIndex = -1;

				// Searches for the file in the explorer tree
				for (int index1 = 0; index1 < AcideProjectConfiguration
						.getInstance().getNumberOfFilesFromList(); index1++) {

					// Does the file belong to the project?
					if (AcideProjectConfiguration
							.getInstance()
							.getFileAt(index1)
							.getAbsolutePath()
							.equals(AcideMainWindow.getInstance()
									.getFileEditorManager()
									.getSelectedFileEditorPanel()
									.getAbsolutePath())) {

						// Gets the explorer file from the project configuration
						file = AcideProjectConfiguration.getInstance()
								.getFileAt(index1);

						for (int index2 = 0; index2 < AcideProjectConfiguration
								.getInstance().getNumberOfFilesFromList() + 1; index2++) {

							if (AcideMainWindow.getInstance()
									.getExplorerPanel().getTree()
									.getPathForRow(index2)
									.getLastPathComponent().toString()
									.equals(file.getLastPathComponent())) {

								// Gets the index
								fileIndex = index2;
							}
						}
					}
				}

				// Gets the current selection in the tree
				TreePath currentSelection = AcideMainWindow.getInstance()
						.getExplorerPanel().getTree().getPathForRow(fileIndex);

				// Selects the node in the explorer tree
				AcideMainWindow.getInstance().getExplorerPanel().getTree()
						.setSelectionPath(currentSelection);
			}
		}
	}

	/**
	 * Searches for a file into the list of files. If finds it then it returns
	 * the node itself and null in other case.
	 * 
	 * @param directoryList
	 *            list of files.
	 * @param fileName
	 *            file name to search for.
	 * @return the file itself if it exists, and null in the opposite case.
	 */
	public DefaultMutableTreeNode searchDirectoryList(
			final ArrayList<DefaultMutableTreeNode> directoryList,
			final String fileName) {

		int index = 0;

		boolean found = false;

		while (index < directoryList.size() && !found) {

			DefaultMutableTreeNode node = directoryList.get(index);
			AcideProjectFile file = (AcideProjectFile) node.getUserObject();

			if (file.getName().equals(fileName)) {
				found = true;
				return directoryList.get(index);
			} else
				index++;
		}
		return null;
	}

	/**
	 * Rebuilds the ACIDE - A Configurable IDE explorer tree with the new
	 * project name when a project has been renamed.
	 */
	public void updateExplorerTree() {

		// Creates the new project file
		AcideProjectFile projectFile = new AcideProjectFile();
		projectFile.setAbsolutePath(AcideProjectConfiguration.getInstance()
				.getProjectPath());
		projectFile.setName(AcideProjectConfiguration.getInstance().getName());
		projectFile.setParent(null);
		projectFile.setIsDirectory(true);

		// Enables add file menu item in the explorer popup menu
		AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
				.getAddFileMenuItem().setEnabled(true);

		// Enables the remove file menu item in the explorer popup menu
		AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
				.getRemoveFileMenuItem().setEnabled(true);

		// Builds the EXPLORER TREE with all the associated files
		AcideMainWindow.getInstance().getExplorerPanel().getRoot()
				.removeAllChildren();

		// Creates the new explorer node
		DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(
				projectFile);

		// Adds the new node to the explorer tree root
		AcideMainWindow.getInstance().getExplorerPanel().getRoot()
				.add(defaultMutableTreeNode);

		// Creates the directory list
		ArrayList<DefaultMutableTreeNode> directoryList = new ArrayList<DefaultMutableTreeNode>();

		for (int index = 0; index < AcideProjectConfiguration.getInstance()
				.getNumberOfFilesFromList(); index++) {

			// Gets the node
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(
					AcideProjectConfiguration.getInstance().getFileAt(index));
			
			// Checks if the file really exists
			File file = new File(AcideProjectConfiguration.getInstance()
					.getFileAt(index).getAbsolutePath());

			// If exists
			if (file.exists()) {

				// Directory?
				if (AcideProjectConfiguration.getInstance().getFileAt(index)
						.isDirectory()) {

					// Allows children in the tree
					node.setAllowsChildren(true);

					// Adds the node
					directoryList.add(node);
				} else
					// No children are allowed
					node.setAllowsChildren(false);

				// If the file already exists in the level above
				if (AcideProjectConfiguration
						.getInstance()
						.getFileAt(index)
						.getParent()
						.equals(AcideProjectConfiguration.getInstance()
								.getName())) {

					// Adds the new node
					defaultMutableTreeNode.add(node);
				} else {

					// Searches for the node
					DefaultMutableTreeNode defaultMutableTreeNode1 = AcideMainWindow
							.getInstance()
							.getExplorerPanel()
							.searchDirectoryList(
									directoryList,
									AcideProjectConfiguration.getInstance()
											.getFileAt(index).getParent());

					// Adds the new node
					defaultMutableTreeNode1.add(node);
				}
			}
		}

		// Updates the explorer tree
		AcideMainWindow.getInstance().getExplorerPanel().getTreeModel()
				.reload();

		// Repaint the explorer tree
		AcideMainWindow.getInstance().getExplorerPanel().expandTree();

		// Enables the add file menu item in the popup menu
		AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
				.getAddFileMenuItem().setEnabled(true);

		// Enables the save project menu item in the popup menu
		AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
				.getSaveProjectMenuItem().setEnabled(true);

		// If it has more than 0 files associated
		if (AcideProjectConfiguration.getInstance().getNumberOfFilesFromList() > 0)

			// Allows to remove files in the EXPLORER menu
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getRemoveFileMenuItem().setEnabled(true);
		else
			// Removing files in the EXPLORER menu is not allowed
			AcideMainWindow.getInstance().getExplorerPanel().getPopupMenu()
					.getRemoveFileMenuItem().setEnabled(false);
	}
	
	@Override
	public void setCursor(Cursor cursor){
		super.setCursor(cursor);
		_tree.setCursor(cursor);
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE explorer panel menu bar.
	 * 
	 * @return the ACIDE - A Configurable IDE explorer panel menu bar.
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	/**
	 * Gets the ACIDE - A Configurable IDE explorer panel container.
	 * 
	 * @return the ACIDE - A Configurable IDE explorer panel container.
	 */
	public String getSplitContainer() {
		return _splitContainer;
	}
	
	/**
	 * Sets a new value to the ACIDE - A Configurable IDE explorer panel container.
	 * 
	 * @param splitContainer new value to set.
	 */
	public void setSplitContainer(String splitContainer) {
		_splitContainer = splitContainer;
	}
	
	/**
	 * Builds the ACIDE - A Configurable IDE explorer panel menu bar
	 */
	public void buildMenuBar() {
		
		menuBar = new explorePanelBar();
		
		// Creates the icon for the panel
		JLabel menu = new JLabel();
		menu.setIcon(new ImageIcon("./resources/icons/menu/view/showExplorerPanel.png"));
		// Adds the icon to the panel
		menuBar.add(menu);
		
		// Creates the label for the name of the panel
		JLabel name = new JLabel();
		name.setForeground(this.menuBar.getColorFont());
		// Adds the label of the name of the panel
		menuBar.add(name);
		// Sets the text of the name of the panel
		setTextMenuBar();
		
		// Creates the close view button
		JButton close = new JButton();
		// Sets the icon of the close view button
		close.setIcon(new ImageIcon("./resources/icons/panels/closeViewPanel.png"));
		// Adds the listener of the close button
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				disposeExplorerPanel();
				AcideProjectConfiguration.getInstance().setIsExplorerPanelShowed(false);
				AcideMainWindow.getInstance().getMenu().getViewMenu()
					.getShowExplorerPanelCheckBoxMenuItem().setSelected(
							AcideProjectConfiguration.getInstance().isExplorerPanelShowed());
				
				// If it is not the default project
				if (!AcideProjectConfiguration.getInstance().isDefaultProject())

					// The project has been modified
					AcideProjectConfiguration.getInstance().setIsModified(true);
			}
		});
		// Sets the margin of the close button
		close.setMargin(new Insets(0, 0, 0, 0));
		// Aligns the button to the right margin
		menuBar.add(Box.createHorizontalGlue());
		// Adds the close button
		menuBar.add(close);
		
		menuBar.setName("AcideExplorerPanel");

	}
	
	/**
	 * Sets the text with the name of the ACIDE - A Configurable IDE explorer panel
	 * menu bar
	 */
	public void setTextMenuBar() {
		String name = AcideLanguageManager.getInstance().getLabels().getString("s221");
		((JLabel) menuBar.getComponent(1)).setText(" " + name);
	}
	
	@Override
	public String getName() {
		return "AcideExplorerPanel";
	}
	
	public void setBackgroundColor(Color backgroundColor, Color foreGroundColor, Color darker) {
		this._tree.setBackground(backgroundColor);
		this._tree.setForeground(foreGroundColor);
		this.menuBar.setBackColor(darker);
		for(Component m:this.menuBar.getComponents()) {
			m.setForeground(foreGroundColor);
		}
		this.menuBar.repaint();
		Component[] men = this._popupMenu.getComponents();
		for(Component c:men) {
			c.getClass().cast(c).setBackground(darker);
			c.getClass().cast(c).setForeground(foreGroundColor);
			((JComponent) c.getClass().cast(c)).setOpaque(true);
		}
		((AcideExplorerTreeCellRenderer)this._tree.getCellRenderer()).setForegroundColor(foreGroundColor);
		repaint();
	}
	public Color getBackgroundColor() {
		return this._tree.getBackground();
	}
	
	public Color getForegroundColor() {
		return this._tree.getForeground();
	}
	
	class explorePanelBar extends JMenuBar{
		private static final long serialVersionUID = 1L;
		private Color background;
		private Color foreground;
		
		public explorePanelBar() {
			super();
			try {
			String colorB = AcideResourceManager.getInstance().getProperty("databasePanel.backgroundColor");
			String colorF = AcideResourceManager.getInstance().getProperty("databasePanel.foregroundColor");
			Color b = new Color(Integer.parseInt(colorB));
			Color darker = new Color((int) (b.getRed() *0.9), (int) (b.getGreen() *0.9), (int) (b.getBlue() *0.9));
			this.background = darker;
			this.foreground = (new Color(Integer.parseInt(colorF)));
			}catch(Exception e){
				this.background = Color.WHITE;
			}
		}
		
		public Color getColorFont() {
			return foreground;
		}
		
		public Color getBackColor() {
			return background;
		}
		public void setBackColor(Color background) {
			this.background = background;
		}
		
		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent((java.awt.Graphics) g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(background);
			g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		}
	}
	
	
}
