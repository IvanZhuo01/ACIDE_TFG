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
package acide.gui.databasePanel.constraintsMenu;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;

/**
 * ACIDE - A Configurable IDE constraints window IC panel.
 * 
 * @version 0.16
 * @see JPanel
 */
public class AcideIntegrityConstraintsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTable _table;

	private String _tableName;
	
	private String _database;
	
	private JScrollPane _tableScrollPane;
	
	private LinkedList<String> _names;
	
	private JPanel _buttonPanel;
	
	private JButton _applyButton;
	
	private JButton _closeButton;
	
	
	public AcideIntegrityConstraintsPanel(String database, String name){
		
		_tableName = name;
		
		_database = database;
		
		_names = new LinkedList<String>();
		
		// Initializes the values of some components
		initializeConstraints();
		
		// Builds the panel components
		buildComponents();
		
		// Adds the components to the window
		addComponents();

		// Sets the action listeners of the window components
		setListeners();
	}

	/**
	 * Adds the existing integrity constraints to its corresponding list
	 */
	private void initializeConstraints() {
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();

		LinkedList<String> icList = des.getIntConst(_database, _tableName);
		
		clearNames(icList);
	
	}

	/**
	 * Removes the ":-" symbol of each integrity constraint and store them in a list
	 * @param icList
	 */
	private void clearNames(LinkedList<String> icList) {
		
		for(int i = 0 ; i<icList.size(); i++){
			String aux = icList.get(i);
			aux = aux.substring(3, aux.length()-1);
			_names.add(aux);
		}
		
	}

	private void buildComponents() {
		
		_buttonPanel = new JPanel();
		
		_applyButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s335"));
		
		_closeButton = new JButton(AcideLanguageManager
				.getInstance().getLabels().getString("s1048"));
		
		// Creates the editable table to be shown
		buildTable();
		
        _tableScrollPane = new JScrollPane(_table);

	}
	
	private void addComponents() {
		
		setLayout(new BorderLayout());

		add(_tableScrollPane, BorderLayout.CENTER);
		
		_buttonPanel.add(_applyButton);
		
		_buttonPanel.add(_closeButton);

		add(_buttonPanel, BorderLayout.SOUTH);
		
	}

	/**
	 * Builds an editable table filled with the possible existing integrity constraints
	 */
	private void buildTable() {
		
		MyTableModel model = new MyTableModel();
		model.build();
		
		_table = new JTable(model);

		_table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        _table.setFillsViewportHeight(true);
        
        //Fiddle with the names column's cell editors/renderers.
        setUpCheckBoxColumn(_table, _table.getColumnModel().getColumn(1));
        
        TableCellRenderer rendererFromHeader = _table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);	
	}

	private void setUpCheckBoxColumn(JTable _table, TableColumn column) {
		
		JCheckBox cBox = new JCheckBox();
		cBox.setHorizontalAlignment(JCheckBox.CENTER);
		
		column.setCellEditor(new DefaultCellEditor(cBox));
	}

	private void setListeners() {

		_applyButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveChanges();
			}
		});

		_closeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();	
			}
		});
		
	}
	
	private void closeWindow() {
		// Enables the main window again
		AcideMainWindow.getInstance().setEnabled(true);

		// Closes the window
		((AcideConstraintDefinitionWindow)getParent().getParent().getParent().getParent().getParent()).dispose();

		// Brings the main window to the front
		AcideMainWindow.getInstance().setAlwaysOnTop(true);

		// But not permanently
		AcideMainWindow.getInstance().setAlwaysOnTop(false);
		
	}
	
	/**
	 * Saves the modifications performed on the current shown table
	 */
	public void saveChanges(){
		
		AcideDatabaseManager des = AcideDatabaseManager.getInstance();
		LinkedList<Boolean> restrictions = getRestrictions();
		_names.clear();
		
		for(int i=0; i<restrictions.size();i++){
			String restr = (String) _table.getValueAt(i, 0);
			String command = ":-" + restr;
			String result = "";
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			if (restrictions.get(i)){
				result = des.createRestriction(command);
				_names.add(restr);
			}
			else
				result = des.dropRestriction(command);
			
			if (result.contains("$success")){
				AcideDataBasePanel panel = AcideMainWindow.getInstance().getDataBasePanel();
				panel.updateDataBaseTree();
				JOptionPane.showMessageDialog(null,AcideLanguageManager
						.getInstance().getLabels().getString("s2315"), AcideLanguageManager
						.getInstance().getLabels().getString("s2316"),JOptionPane.INFORMATION_MESSAGE);
			}
			else if (result.contains("Syntax error")){
				JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance().getLabels().getString("s2314"));
			}
			else {
				JOptionPane.showMessageDialog(null,result, AcideLanguageManager.getInstance()
						.getLabels().getString("s157"), JOptionPane.OK_OPTION);
			}
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
			
	}
	
	/**
	 * Gets all the restrictions in the table that have a check of validity
	 * @return String restriction
	 */
	public LinkedList<Boolean> getRestrictions(){
		
		LinkedList<Boolean> restrictions = new LinkedList<Boolean>();
		
		for(int i = 0; i<_table.getRowCount()-1; i++){
			boolean rest = (Boolean) _table.getValueAt(i, 1);
				restrictions.add(rest);
		}
		
		return restrictions;
		
	}

	/**
	 * Updates the panel by rebuilding the structures that deal with functional dependencies values
	 */
    public void updatePanel() {
    	
    	_names.clear();
    	
    	initializeConstraints();

		((MyTableModel) _table.getModel()).fireTableDataChanged();
	
	}

    /**
     * Private class implemented to build the grid of a table with specific values
     * @see AbstractTableModel
     */
	@SuppressWarnings("unchecked")
	private class MyTableModel extends AbstractTableModel{
		
		private static final long serialVersionUID = 1L;
		
		private String[] headers = {AcideLanguageManager.getInstance().getLabels().getString("s2306")
				,AcideLanguageManager.getInstance().getLabels().getString("s2035")};
		
		private Object[][] data;
		
		
		public void build(){
			
			data = new Object[_names.size()+1][headers.length];
			
			for(int i=0; i<_names.size(); i++){
				data[i][0] = _names.get(i);
				data[i][1] = Boolean.TRUE;
			}
			
			data[_names.size()][0] = "";
			data[_names.size()][1] = Boolean.FALSE;
			
		}
		
		public int getColumnCount() {
		    return headers.length;
		}

		public int getRowCount() {
		    return data.length;
		}

		public String getColumnName(int col) {
		    return headers[col];
		}

		public Object getValueAt(int row, int col) {
		    return data[row][col];
		}
		
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
        public boolean isCellEditable(int row, int col) {
        	return true;
        }
        
        public void setValueAt(Object value, int row, int col) {
        	
            data[row][col] = value;
            fireTableCellUpdated(row, col);
            // If the cell being edited is the last row of the table then create a new one
            if(row == data.length-1)
            	addRow();
        }
        
        public void fireTableDataChanged(){
        	build();
        }
        
        public void addRow(){
        	String newValue = (String) getValueAt(data.length-1,0);
        	_names.add(newValue);
        	build();
        	repaint();
        }
	}
}
