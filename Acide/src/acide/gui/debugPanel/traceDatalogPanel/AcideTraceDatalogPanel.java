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
package acide.gui.debugPanel.traceDatalogPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.*;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugDatalogPanel.AcideDebugDatalogPanel;
import acide.gui.debugPanel.debugDatalogPanel.listeners.AcideDebugDatalogPanelViewBoxListener;
import acide.gui.debugPanel.traceDatalogPanel.listeners.*;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.debugPanel.utils.AcideQueryDialog;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;

/**
 * ACIDE - A Configurable IDE trace datalog panel.
 * 
 * @version 0.15
 * @see JPanel
 */
public class AcideTraceDatalogPanel extends JPanel {

	/**
	 * ACIDE - A Configurable IDE trace datalog panel class serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel debug canvas.
	 */
	public static AcideDebugCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel button panel.
	 */
	private JPanel _mainButtonPanel;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel zoom spinner.
	 */
	private static JSpinner _zoomSpinner;

	/**
	 * ACIDE - A Configurable IDE debug datalog panel predicate label.
	 */
	private JLabel _predicateLabel;

	/**
	 * ACIDE - A Configurable IDE trace datalog panel show label check box.
	 */
	private JCheckBox _showLabelsMenuItem;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel predicate box.
	 */
	private JComboBox _predicateBox;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel query.
	 */
	private String _query;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel show rules check box.
	 */
	private JCheckBox _showRulesMenuItem;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel high lighter.
	 */
	private AcideDebugPanelHighLighter _highLighter;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel locate button.
	 */
	private JButton _locateButton;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel zoom in button.
	 */
	private JButton _zoomin;

	/**
	 * ACIDE - A Configurable IDE trace datalog panel zoom out button.
	 */
	private JButton _zoomout;

	/**
	 * ACIDE - A Configurable IDE trace datalog panel first node button.
	 */
	private JButton _firstNodeButton;

	/**
	 * ACIDE - A Configurable IDE trace datalog panel prev node button.
	 */
	private JButton _prevNodeButton;

	/**
	 * ACIDE - A Configurable IDE trace datalog panel next node button.
	 */
	private JButton _nextNodeButton;

	/**
	 * ACIDE - A Configurable IDE trace datalog panel last node button.
	 */
	private JButton _lastNodeButton;
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the first button icon
	 */
	private final static ImageIcon TO_THE_FIRST_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/double_left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the last button icon
	 */
	private final static ImageIcon TO_THE_LAST_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/double_right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the right button icon
	 */
	private final static ImageIcon TO_THE_RIGHT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/right_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel to the left button icon
	 */
	private final static ImageIcon TO_THE_LEFT_IMAGE = new ImageIcon(
			"./resources/icons/menu/configuration/menu/left_arrow.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel add button icon
	 */
	private final static ImageIcon ADD_IMAGE = new ImageIcon(
			"./resources/icons/graph/add.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel minus button icon
	 */
	private final static ImageIcon MINUS_IMAGE = new ImageIcon(
			"./resources/icons/graph/minus.png");
	/**
	 * ACIDE - A Configurable IDE trace datalog panel refresh button icon
	 */
	private final static ImageIcon REFRESH_IMAGE = new ImageIcon(
			"./resources/icons/panels/refresh.png");
	
	// builds the refresh button
	public static JButton refreshDatalog = new JButton();
			
	/**
	 * Creates a new ACIDE - A Configurable IDE trace datalog panel.
	 */
	public AcideTraceDatalogPanel() {
		// Sets the layout of the panel
		setLayout(new BorderLayout());
		// Builds the canvas
		buildCanvas();
		// Builds the button panel
		buildButtons();
		// Sets the ACIDE - A Configurable IDE of trace datalog panel listeners
		setListeners();
		// Adds the canvas to sp and sp to panel
		ScrollPane sp = new ScrollPane();
		sp.add(_canvas);
		add(sp, BorderLayout.CENTER);
		// Adds the button panel
		add(_mainButtonPanel, BorderLayout.SOUTH);
		// Builds the high lighter
		setHighLighter(new AcideDebugPanelHighLighter());
	}

	/**
	 * Builds the canvas of the the ACIDE - A Configurable IDE trace datalog
	 * panel.
	 */
	public void buildCanvas() {
		this._canvas = new AcideDebugCanvas();
		_canvas.setBounds(this.getBounds());
		_canvas.setVisible(true);
		_canvas.repaint();
	}
	
	/**
	 * Builds the buttons for the ACIDE - A Configurable IDE trace datalog
	 * panel.
	 */
	public void buildButtons() {
		// builds the button panel
		_mainButtonPanel = new JPanel();
		// adds the layout to the button panel
		_mainButtonPanel.setLayout(new BorderLayout());
		// creates the sub button panel 1
		JPanel subButtonPanel1 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel1.setLayout(new FlowLayout());
		subButtonPanel1.setOpaque(false);
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel1, BorderLayout.NORTH);
		refreshDatalog.setIcon(REFRESH_IMAGE);
		refreshDatalog.setPreferredSize(new Dimension((int) (1.5 * refreshDatalog.getIcon()
				.getIconWidth()), (int) refreshDatalog.getPreferredSize().getHeight()));
		// adds the action listener to the refresh button
		refreshDatalog.addActionListener(new AcideTraceDatalogPanelRefreshListener());
		//sets tooltip button 
		refreshDatalog.setToolTipText(AcideLanguageManager.getInstance().getLabels().getString("s2044"));
		//unable the button
		refreshDatalog.setEnabled(false);
		// adds the refresh button
		subButtonPanel1.add(refreshDatalog);
		// creates the spinner model for the zoom spinner
		SpinnerModel model = new SpinnerNumberModel(
				(int) _canvas.getZoom() * 100, 0, Integer.MAX_VALUE, 1);
		// creates the zoom spinner with the model
		_zoomSpinner = new JSpinner(model);
		((JSpinner.DefaultEditor) _zoomSpinner.getEditor()).getTextField()
				.setColumns(4);
		// adds the action listener to the zoom spinner
		_zoomSpinner
				.addChangeListener(new AcideTraceDatalogPanelZoomSpinnerListener());
		// adds the zoom spinner to the button panel
		subButtonPanel1.add(_zoomSpinner);
		// creates the zoom in button
		_zoomin = new JButton(ADD_IMAGE);
		_zoomin.setPreferredSize(new Dimension((int) (1.5 * _zoomin.getIcon()
				.getIconWidth()), (int) _zoomin.getPreferredSize().getHeight()));
		// adds the zoom in action listener
		_zoomin.addActionListener(new AcideTraceDatalogPanelZoomInListener());
		// adds the zoom in button to the button panel
		subButtonPanel1.add(_zoomin);
		// creates the zoom out button
		_zoomout = new JButton(MINUS_IMAGE);
		_zoomout.setPreferredSize(new Dimension((int) (1.5 * _zoomout.getIcon()
				.getIconWidth()), (int) _zoomout.getPreferredSize().getHeight()));
		// adds the zoom out action listener
		_zoomout.addActionListener(new AcideTraceDatalogPanelZoomOutListener());
		// adds the zoom out button to the button panel
		subButtonPanel1.add(_zoomout);
		// builds the show labels check box
		_showLabelsMenuItem = new JCheckBox();
		// sets the default selected option
		_showLabelsMenuItem.setSelected(true);
		// sets the text of the show labels check box
		_showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2263"));
		_showLabelsMenuItem.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2421"));
		_showLabelsMenuItem
				.setFont(_showLabelsMenuItem.getFont().deriveFont(10f));
		// adds the action listener to the show labels check box
		_showLabelsMenuItem
				.addActionListener(new AcideTraceDatalogPanelShowLabelsListener());
		// adds the show labels check box to the button panel
		subButtonPanel1.add(_showLabelsMenuItem);
		// creates the sub button panel 2
		JPanel subButtonPanel2 = new JPanel();
		// adds the layout to the sub button panel
		subButtonPanel2.setLayout(new FlowLayout());
		subButtonPanel2.setOpaque(false);
		// adds the sub button panel to the main button panel
		_mainButtonPanel.add(subButtonPanel2, BorderLayout.SOUTH);

		//Builds the predicate label
		_predicateLabel=new JLabel(AcideLanguageManager.getInstance().getLabels()
					.getString("s2408"));
		_predicateLabel.setToolTipText(AcideLanguageManager.getInstance().getLabels()
					.getString("s2409"));
		_predicateLabel.setFont(_predicateLabel.getFont().deriveFont(10f));
		subButtonPanel2.add(_predicateLabel);

		//Create predicate box
		_predicateBox=new JComboBox();
		_predicateBox.addItem("         ");
		//Adds listeners to the predicates combo box
		_predicateBox.addFocusListener( new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

				ArrayList<String> predicates = new ArrayList<String>();
				predicates.add("        ");

				//Gets the views from the database
				LinkedList<String> views = DesDatabaseManager.getInstance()
						.executeCommand("/tapi /list_views");

				// Checks if there are no loaded views
				if(views.size()==0) {

					//Gets the predicates from the database
					LinkedList<String> l = DesDatabaseManager.getInstance()
							.executeCommand("/tapi /pdg");

					//Parses the output from the database
					for (String p : l) {

						// Checks if the predicates output has ended
						if (p.equals("$"))
							break;
						// Adds the actual's predicate name to the list
						predicates.add(p);
					}
				}

				// Gets the debug datalog panel list of predicates
				JComboBox predicateBox = AcideMainWindow.getInstance().getDebugPanel()
						.getTraceDatalogPanel().getPredicateBox();

				//Removes the previous predicates
				predicateBox.removeAllItems();

				// Adds the new predicates
				for (String p : predicates) {
					_predicateBox.addItem(p);
				}
				_predicateBox.setPopupVisible(false);
				_predicateBox.setPopupVisible(true);

				// Check if there are no predicates to select
				if(_predicateBox.getItemCount()==1){
					// Disable refresh button
					refreshDatalog.setEnabled(false);
					// Clean canvas
					_canvas.set_graph(new DirectedWeightedGraph());
					_canvas.repaint();
				}
			}
		});

		_predicateBox.addActionListener(new AcideTraceDatalogPanelViewBoxListener());

		subButtonPanel2.add(_predicateBox);

		// Creates the first node button
		_firstNodeButton = new JButton(TO_THE_FIRST_IMAGE);
		_firstNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * _firstNodeButton.getIcon().getIconWidth()),
				(int) _firstNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the first node button
		_firstNodeButton
				.addActionListener(new AcideTraceDatalogPanelFirstNodeListener());
		// adds the first node button to the button panel
		subButtonPanel2.add(_firstNodeButton);
		// creates the previous node button
		_prevNodeButton = new JButton(TO_THE_LEFT_IMAGE);
		_prevNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * _prevNodeButton.getIcon().getIconWidth()),
				(int) _prevNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the previous node button
		_prevNodeButton
				.addActionListener(new AcideTraceDatalogPanelPreviousNodeListener());
		// adds the previous node button to the button panel
		subButtonPanel2.add(_prevNodeButton);
		// creates the next node button
		_nextNodeButton = new JButton(TO_THE_RIGHT_IMAGE);
		_nextNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * _nextNodeButton.getIcon().getIconWidth()),
				(int) _nextNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the next node button
		_nextNodeButton
				.addActionListener(new AcideTraceDatalogPanelNexNodeListener());
		// adds the next node button to the button panel
		subButtonPanel2.add(_nextNodeButton);
		// Creates the last node button
		_lastNodeButton = new JButton(TO_THE_LAST_IMAGE);
		_lastNodeButton.setPreferredSize(new Dimension(
				(int) (1.5 * _lastNodeButton.getIcon().getIconWidth()),
				(int) _lastNodeButton.getPreferredSize().getHeight()));
		// adds the action listener to the last node button
		_lastNodeButton
				.addActionListener(new AcideTraceDatalogPanelLastNodeListener());
		// adds the last node button to the button panel
		subButtonPanel2.add(_lastNodeButton);
		// Creates locate button
		_locateButton = new JButton(AcideLanguageManager.getInstance()
				.getLabels().getString("s2295"));
		_locateButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2407"));
		_locateButton.setFont(_locateButton.getFont().deriveFont(10f));
		// adds the action listener to the locate button
		_locateButton.addActionListener(new AcideTraceDatalogPanelLocateListener());
		// adds the locate button to the button panel
		subButtonPanel2.add(_locateButton);
		// Builds the show rules check box
		_showRulesMenuItem = new JCheckBox();
		// sets the default selected option
		_showRulesMenuItem.setSelected(false);
		// sets the text of the show rules check box
		_showRulesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2279"));
		_showRulesMenuItem.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2422"));
		_showRulesMenuItem.setFont(_showRulesMenuItem.getFont().deriveFont(10f));

		_showRulesMenuItem
				.addActionListener(new AcideTraceDatalogPanelShowRulesListener());
		// showRulesMenuItem.addActionListener(arg0)
		subButtonPanel2.add(_showRulesMenuItem);

	}


	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog panel canvas.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog panel canvas.
	 */
	public AcideDebugCanvas getCanvas() {
		return _canvas;
	}

	/**
	 * Sets a new value to the the ACIDE - A Configurable IDE trace datalog
	 * panel canvas.
	 * 
	 * @param canvas
	 *            the new value to set
	 */
	public void setCanvas(AcideDebugCanvas canvas) {
		this._canvas = canvas;
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog panel show rules
	 * item.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog panel show rules
	 *         item.
	 */
	public JCheckBox getShowRulesMenuItem() {
		return _showRulesMenuItem;
	}

	/**
	 * Sets a new value to the the ACIDE - A Configurable IDE show rules item.
	 * 
	 * @param showRulesMenuItem
	 *            the new value to set
	 */
	public void setShowRulesMenuItem(JCheckBox showRulesMenuItem) {
		this._showRulesMenuItem = showRulesMenuItem;
	}

	/**
	 * Returns the  ACIDE - A Configurable IDE trace datalog panel query
	 * text.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog panel query
	 *         text.
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace datalog
	 * panel query text.
	 * 
	 * @param query
	 *            the new value to set.
	 */
	public void setQuery(String query) {
		this._query = query;
	}

	/**
	 * Sets availability to the ACIDE - A Configurable IDE trace datalog panel locate
	 * button
	 *
	 * @param enable
	 *              button availability
	 */
	public void enableLocateButton(boolean enable){this._locateButton.setEnabled(enable);}

	/**
	 * Returns the the ACIDE - A Configurable IDE trace datalog panel
	 * highligther.
	 * 
	 * @return the the ACIDE - A Configurable IDE trace datalog panel
	 *         highligther.
	 */
	public AcideDebugPanelHighLighter getHighLighter() {
		return _highLighter;
	}

	/**
	 * Sets a new value to the the the ACIDE - A Configurable IDE trace datalog
	 * panel highligther.
	 * 
	 * @param highLighter
	 *            the new value to set.
	 */
	public void setHighLighter(AcideDebugPanelHighLighter highLighter) {this._highLighter = highLighter;}

	/**
	 * Returns the ACIDE - A Configurable IDE debug datalog panel predicate box.
	 *
	 * @return the ACIDE - A Configurable IDE debug datalog panel predicate box
	 */
	public JComboBox getPredicateBox(){return this._predicateBox;}

	/**
	 * Sets a new value to the ACIDE - A Cofigurable IDE debug datalog panel predicate
	 * box.
	 *
	 * @param predicateBox
	 *          the new value to set.
	 */
	public void setPredicateBox(JComboBox predicateBox){this._predicateBox = predicateBox;}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog panel zoom spinner.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog panel zoom spinner.
	 */
	public static JSpinner getZoomSpinner() {
		return _zoomSpinner;
	}

	/**
	 * Sets a new value to the ACIDE - A Configurable IDE trace datalog panel
	 * zoom spinner.
	 * 
	 * @param zoomSpinner
	 *            the new value to set
	 */
	public void setZoomSpinner(JSpinner zoomSpinner) { this._zoomSpinner = zoomSpinner; }

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog canvas.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog canvas.
	 */
	public AcideDebugCanvas getDebugDatalogCanvas() {
		return _canvas;
	}

	class AcideTraceDatalogPanelClickListener extends MouseAdapter {

		public void mousePressed(MouseEvent e){
			if(e.getClickCount()>=2){
				String predicate;
				if(_canvas.getSelectedNode().getLabel().contains("/")){
					predicate = _canvas.getSelectedNode().getLabel();
					AcideDebugHelper.showPredicate(predicate);
				}

			}
		}

	}
	private void setListeners(){
		_canvas.addMouseListener(new AcideTraceDatalogPanelClickListener());
	}

	/**
	 * Sets the text of the components assigned to the ACIDE - A Configurable
	 * IDE trace datalog panel.
	 */
	public void setComponentsText() {
		_predicateLabel.setText(AcideLanguageManager.getInstance()
					.getLabels().getString("s2408"));
		_predicateLabel.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2409"));
		_showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2263"));
		_showLabelsMenuItem.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2421"));
		_showRulesMenuItem.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2279"));
		_showRulesMenuItem.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2422"));
		_locateButton.setText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2295"));
		_locateButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2407"));
		_zoomin.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2389"));
		_zoomout.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2390"));
		_firstNodeButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2391"));
		_prevNodeButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2392"));
		_nextNodeButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2393"));
		_lastNodeButton.setToolTipText(AcideLanguageManager.getInstance()
				.getLabels().getString("s2394"));
	}

	/**
	 * Returns the ACIDE - A Configurable IDE trace datalog show labels.
	 * 
	 * @return the ACIDE - A Configurable IDE trace datalog show labels.
	 */	
	public JCheckBox getShowLabelsMenuItem() {
		return _showLabelsMenuItem;
	}
	public void setBackgroundColor(Color backgroundColor, Color foregroundColor, Color darker) {
		this.setBackground(backgroundColor);
		this._mainButtonPanel.setBackground(darker);
		this._mainButtonPanel.setForeground(foregroundColor);
		SwingUtilities.invokeLater(() -> AcideDebugHelper.updateDatalogCanvas(_canvas));
	}

	/**
	 * Disable components assigned to the ACIDE - A Configurable IDE trace Datalog panel.
	 */
	public void disableComponents(){
		_canvas.setEnabled(false);
		for(Component p:_mainButtonPanel.getComponents()){
			for(Component c:((JPanel)p).getComponents()){
				c.setEnabled(false);
			}
		}
	}

	/**
	 * Enable components assigned to the ACIDE - A Configurable IDE trace Datalog panel.
	 */
	public void enableComponents(){
		_canvas.setEnabled(true);
		for(Component p:_mainButtonPanel.getComponents()){
			for(Component c:((JPanel)p).getComponents()){
				if(!c.equals(refreshDatalog))
					c.setEnabled(true);
				else
					if(_predicateBox.getSelectedIndex()!=0)
						c.setEnabled(true);
			}
		}
	}
}
