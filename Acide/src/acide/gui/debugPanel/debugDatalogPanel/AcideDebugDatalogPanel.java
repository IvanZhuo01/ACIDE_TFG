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
package acide.gui.debugPanel.debugDatalogPanel;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugDatalogPanel.listeners.*;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.debugPanel.utils.AcideDebugPanelHighLighter;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class AcideDebugDatalogPanel extends JPanel {

    /**
     * ACIDE - A Configurable IDE debug Datalog panel selected Debug node color.
     */
    private Color _selectedDebugNodeColor=Color.YELLOW;

    /**
     * ACIDE - A Configurable IDE debug Datalog panel debug canvas.
     */
    public static AcideDebugCanvas _canvas;

    /**
     * ACIDE - A Configurable IDE debug datalog panel query.
     */
    private String _query;

    /**
     * ACIDE - A Configurable IDE debug datalog panel debugging status.
     */
    private boolean _debugging;

    /**
     * ACIDE - A Configurable IDE debug datalog panel popup menu.
     */
    public JPopupMenu _popUp = null;

    /**
     * ACIDE - A Configurable IDE debug datalog panel wrong node menuItem.
     */
    private JMenuItem _wrongNodeItem;
    /**
     * ACIDE - A Configurable IDE debug datalog panel nonvalid node menuItem.
     */
    private JMenuItem _nonvalidNodeItem;
    /**
     * ACIDE - A Configurable IDE debug datalog panel valid node menuItem.
     */
    private JMenuItem _validNodeItem;
    /**
     * ACIDE - A Configurable IDE debug datalog panel missing node menuItem.
     */
    private JMenuItem _missingNodeItem;

    /**
     * ACIDE - A Configurable IDE debug datalog panel error node menuItem.
     */
    private JMenuItem _errorNodeItem;

    /**
     * ACIDE - A Configurable IDE debug datalog panel undo node menuItem.
     */
    private JMenuItem _undoNodeItem;

    /**
     * ACIDE - A Configurable IDE debug datalog panel redo node menuItem.
     */
    private JMenuItem _redoNodeItem;

    /**
     * ACIDE - A Configurable IDE debug datalog panel contents menuItem.
     */
    private JMenuItem _contents;

    /**
     * ACIDE - A Configurable IDE debug datalog panel Color Node.
     */
    private JMenuItem _nodeState;

    /**
     * ACIDE - A Configurable IDE debug datalog panel edit predicate menuItem.
     */
    private JMenuItem _editIntensionalPredicate;

    /**
     * ACIDE - A Configurable IDE debug datalog panel high lighter.
     */
    private AcideDebugPanelHighLighter _highlighter;

    /**
     * ACIDE - A Configurable IDE debug datalog panel predicate location
     */
    private HashMap<String,LinkedList<String>> _predicateLocation;

    /**
     * ACIDE - A Configurable IDE debug Datalog panel button panel.
     */
    private JPanel _mainButtonPanel;

    /**
     * ACIDE - A Configurable IDE debug datalog panel refresh button.
     */
    public static JButton _refreshDatalog =new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel zoom in button.
     */
    private JButton _zoomIn=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel zoom out button.
     */
    private JButton _zoomOut=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel zoom spinner.
     */
    private static JSpinner _zoomSpinner;

    /**
     * ACIDE - A Configurable IDE debug datalog panel predicate label.
     */
    private JLabel _predicateLabel;

    /**
     * ACIDE - A Configurable IDE debug datalog panel predicate box.
     */
    private JComboBox _predicateBox;

    /**
     * ACIDE - A Configurable IDE debug datalog panel predicate model
     */
    private HashMap<String,LinkedList<String>> _predicateModel;

    /**
     * ACIDE - A Configurable IDE debug datalog panel show label check box.
     */
    private JCheckBox _showLabelsMenuItem=new JCheckBox();

    /**
     * ACIDE - A Configurable IDE debug datalog panel start debug button.
     */
    public static JButton _startDebug=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel configure debug button.
     */
    public static JButton _configDebug=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel first node button.
     */
    private JButton _firstNodeButton=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel last node button.
     */
    private JButton _lastNodeButton=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel prev node button.
     */
    private JButton _prevNodeButton=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel next node button.
     */
    private JButton _nextNodeButton=new JButton();

    /**
     * ACIDE - A Configurable IDE debug datalog panel locate button.
     */
    private JButton _locateButton;

    /**
     * ACIDE - A Configurable IDE debug datalog panel show rules check box.
     */
    private JCheckBox _showRulesMenuItem=new JCheckBox();

    /**
     * ACIDE - A Configurable IDE debug datalog panel refresh button icon.
     */
    private final static ImageIcon REFRESH_IMAGE=new ImageIcon(
            "./resources/icons/panels/refresh.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel add button icon.
     */
    private final static ImageIcon ADD_IMAGE=new ImageIcon(
            "./resources/icons/graph/add.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel minus button icon.
     */
    private final static ImageIcon MINUS_IMAGE=new ImageIcon(
            "./resources/icons/graph/minus.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel configure debug button icon.
     */
    private final static ImageIcon CONFIG_DEBUG=new ImageIcon(
            "./resources/icons/menu/configuration/console/configure.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel start debug button icon.
     */
    private final static ImageIcon START_DEBUG=new ImageIcon(
            "./resources/icons/editor/compilable.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel to the first button icon.
     */
    private final static ImageIcon TO_THE_FIRST_IMAGE=new ImageIcon(
            "./resources/icons/menu/configuration/menu/double_left_arrow.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel to the last button icon.
     */
    private final static ImageIcon TO_THE_LAST_IMAGE=new ImageIcon(
            "./resources/icons/menu/configuration/menu/double_right_arrow.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel to the left button icon.
     */
    private final static ImageIcon TO_THE_LEFT_IMAGE=new ImageIcon(
            "./resources/icons/menu/configuration/menu/left_arrow.png");

    /**
     * ACIDE - A Configurable IDE debug datalog panel to the right button icon.
     */
    private final static ImageIcon TO_THE_RIGHT_IMAGE=new ImageIcon(
            "./resources/icons/menu/configuration/menu/right_arrow.png");

    public AcideDebugDatalogPanel(){

        _debugging=false;

        //Set the layout of the panel
        setLayout(new BorderLayout());

        //Builds the canvas
        buildCanvas();
        //Builds the button panel
        buildButtons();

        // Sets the ACIDE - A Configurable IDE of debug datalog panel listeners
        setListeners();

        //Adds the canvas to sp and sp to panel
        ScrollPane sp = new ScrollPane();
        sp.add(_canvas);
        add(sp,BorderLayout.CENTER);

        //Adds the button panel to panel
        add(_mainButtonPanel,BorderLayout.SOUTH);

        //Builds the high lighter
        setHighLighter(new AcideDebugPanelHighLighter());

        //Inits the PopUp panel
        popUpInit();
    }

    /**
     * Build the canvas of the ACIDE - A Configurable IDE of debug Datalog panel.
     */
    private void buildCanvas(){

        _canvas=new AcideDebugCanvas();
        _canvas.setBounds(this.getBounds());
        _canvas.setVisible(true);
        _canvas.setSelectedNodeColor(_selectedDebugNodeColor);
        _canvas.repaint();

    }

    /**
     * Builds the buttons for the ACIDE - A Configurable IDE debug Datalog panel.
     */
    private void buildButtons(){

        //Builds the button panel
        _mainButtonPanel=new JPanel();
        //Adds the layout to the button panel
        _mainButtonPanel.setLayout(new BorderLayout());

        //Creates the sub button panel 1
        JPanel subButtonPanel1=new JPanel();
        //Adds the layout to sub button panel 1
        subButtonPanel1.setLayout(new FlowLayout());
        subButtonPanel1.setOpaque(false);
        //Adds sub button panel 1 to the main button panel
        _mainButtonPanel.add(subButtonPanel1,BorderLayout.NORTH);

        //Customize refresh button
        _refreshDatalog.setIcon(REFRESH_IMAGE);
        _refreshDatalog.setPreferredSize(new Dimension((int)(1.5 * _refreshDatalog
                    .getIcon().getIconWidth()),(int)_refreshDatalog.getPreferredSize()
                    .getHeight()));
        _refreshDatalog.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2044"));
        _refreshDatalog.addActionListener(new AcideDebugDatalogPanelRefreshListener());
        _refreshDatalog.setEnabled(false);
        subButtonPanel1.add(_refreshDatalog);

        //Customize start debug button
        _startDebug.setIcon(START_DEBUG);
        _startDebug.setPreferredSize(new Dimension((int)(1.5*_startDebug
                .getIcon().getIconWidth()),(int)_startDebug.getPreferredSize()
                .getHeight()));
        _startDebug.setToolTipText(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2325"));
        _startDebug.addActionListener(new AcideDebugDatalogPanelStartDebugListener());
        _startDebug.setEnabled(false);
        subButtonPanel1.add(_startDebug);

        //Customize configure debug button
        _configDebug.setIcon(CONFIG_DEBUG);
        _configDebug.setPreferredSize(new Dimension((int)(1.5*_configDebug
                .getIcon().getIconWidth()),(int)_configDebug.getPreferredSize()
                .getHeight()));
        _configDebug.setToolTipText(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2326"));
        _configDebug.addActionListener(new AcideDebugDatalogPanelConfigureDebugListener());
        _configDebug.setEnabled(true);
        subButtonPanel1.add(_configDebug);

        //Create zoom spinner
        SpinnerModel model = new SpinnerNumberModel(
                (int) _canvas.getZoom()*100,0,Integer.MAX_VALUE,1);
        _zoomSpinner=new JSpinner(model);
        ((JSpinner.DefaultEditor)_zoomSpinner.getEditor()).getTextField().setColumns(4);
        _zoomSpinner.addChangeListener(new AcideDebugDatalogPanelZoomSpinnerListener());
        subButtonPanel1.add(_zoomSpinner);

        //Customize zoom in button
        _zoomIn.setIcon(ADD_IMAGE);
        _zoomIn.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2389"));
        _zoomIn.setPreferredSize(new Dimension((int)(1.5 * _zoomIn.getIcon()
                .getIconWidth()),(int)(_zoomIn.getPreferredSize().getHeight())));
        _zoomIn.addActionListener(new AcideDebugDatalogPanelZoomInListener());
        subButtonPanel1.add(_zoomIn);

        //Customize zoom out button
        _zoomOut.setIcon(MINUS_IMAGE);
        _zoomOut.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2390"));
        _zoomOut.setPreferredSize(new Dimension((int)(1.5 * _zoomOut.getIcon()
                .getIconWidth()),(int)(_zoomOut.getPreferredSize().getHeight())));
        _zoomOut.addActionListener(new AcideDebugDatalogPanelZoomOutListener());
        subButtonPanel1.add(_zoomOut);

        //Customize show label check box
        _showLabelsMenuItem.setText(AcideLanguageManager.getInstance().getLabels()
                .getString("s2263"));
        _showLabelsMenuItem.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2421"));
        _showLabelsMenuItem.setFont(_showLabelsMenuItem.getFont().deriveFont(10f));
        _showLabelsMenuItem.addActionListener(new AcideDebugDatalogPanelShowLabelsListener());
        _showLabelsMenuItem.setSelected(true);
        subButtonPanel1.add(_showLabelsMenuItem);

        //Creates the sub button panel 2
        JPanel subButtonPanel2=new JPanel();
        //Adds the layout to sub button panel 2
        subButtonPanel2.setLayout(new FlowLayout());
        subButtonPanel2.setOpaque(false);
        //Adds sub button panel 2 to the main button panel
        _mainButtonPanel.add(subButtonPanel2,BorderLayout.SOUTH);

        //Builds the predicate label
        _predicateLabel=new JLabel(AcideLanguageManager.getInstance()
            .getLabels().getString("s2408"));
        _predicateLabel.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2409"));
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
                        .getDebugDatalogPanel().getPredicateBox();

                //Removes the previous predicates
                predicateBox.removeAllItems();

                // Adds the new predicates
                for (String p : predicates)
                    _predicateBox.addItem(p);

                _predicateBox.setPopupVisible(false);
                _predicateBox.setPopupVisible(true);

                // Check if there are no predicates to select
                if(_predicateBox.getItemCount()==1){

                    // Disable refresh button
                    _refreshDatalog.setEnabled(false);
                    // Disable show view button
                    _startDebug.setEnabled(false);

                    // Clean canvas
                    _canvas.set_graph(new DirectedWeightedGraph());
                    _canvas.repaint();
                }

            }
        });

        _predicateBox.addActionListener(new AcideDebugDatalogPanelViewBoxListener());

        subButtonPanel2.add(_predicateBox);

        //Customize first node button
        _firstNodeButton.setIcon(TO_THE_FIRST_IMAGE);
        _firstNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2391"));
        _firstNodeButton.setPreferredSize(new Dimension((int)(1.5*_firstNodeButton.getIcon()
                .getIconWidth()),(int)(_firstNodeButton.getPreferredSize().getHeight())));
        _firstNodeButton.addActionListener(new AcideDebugDatalogPanelFirstNodeListener());
        subButtonPanel2.add(_firstNodeButton);

        //Customize prev node button
        _prevNodeButton.setIcon(TO_THE_LEFT_IMAGE);
        _prevNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2392"));
        _prevNodeButton.setPreferredSize(new Dimension((int)(1.5*_prevNodeButton.getIcon()
                .getIconWidth()),(int)(_prevNodeButton.getPreferredSize().getHeight())));
        _prevNodeButton.addActionListener(new AcideDebugDatalogPanelPreviousNodeListener());
        subButtonPanel2.add(_prevNodeButton);

        //Customize next node button
        _nextNodeButton.setIcon(TO_THE_RIGHT_IMAGE);
        _nextNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2393"));
        _nextNodeButton.setPreferredSize(new Dimension((int)(1.5*_nextNodeButton.getIcon()
                .getIconWidth()),(int)(_nextNodeButton.getPreferredSize().getHeight())));
        _nextNodeButton.addActionListener(new AcideDebugDatalogPanelNextNodeListener());
        subButtonPanel2.add(_nextNodeButton);

        //Customize last node button
        _lastNodeButton.setIcon(TO_THE_LAST_IMAGE);
        _lastNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2394"));
        _lastNodeButton.setPreferredSize(new Dimension((int)(1.5*_lastNodeButton.getIcon()
                .getIconWidth()),(int)(_lastNodeButton.getPreferredSize().getHeight())));
        _lastNodeButton.addActionListener(new AcideDebugDatalogPanelLastNodeListener());
        subButtonPanel2.add(_lastNodeButton);

        //Create locate button
        _locateButton=new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2295"));
        _locateButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2407"));
        _locateButton.setFont(_locateButton.getFont().deriveFont(10f));
        _locateButton.addActionListener(new AcideDebugDatalogPanelLocateListener());
        subButtonPanel2.add(_locateButton);

        //Customize show rules check box
        _showRulesMenuItem.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2279"));
        _showRulesMenuItem.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2422"));
        _showRulesMenuItem.setFont(_showRulesMenuItem.getFont().deriveFont(10f));
        _showRulesMenuItem.addActionListener(new AcideDebugDatalogPanelShowRulesListener());
        _showRulesMenuItem.setSelected(false);
        subButtonPanel2.add(_showRulesMenuItem);
    }

    /**
     * Initialization the popUp panel for the ACIDE - A Configurable IDE debug datalog panel.
     */
    private void popUpInit() {
        // we create the popUp menu
        _popUp = new JPopupMenu();
        // Option show contents
        _contents = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2347"));
        _contents.addActionListener(new AcideDebugDatalogPanelShowPredicateListener());
        _popUp.add(_contents);
        // Option edit view
        _editIntensionalPredicate = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2416"));
        _editIntensionalPredicate.addActionListener(new AcideDebugDatalogPanelEditIntensionalPredicateListener());
        _popUp.add(_editIntensionalPredicate);
        // Option non valid node
        _nonvalidNodeItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2319"));
        _nonvalidNodeItem.addActionListener(new AcideDebugDatalogPanelNonValidNodeListener());
        _popUp.add(_nonvalidNodeItem);
        // option valid node
        _validNodeItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2320"));
        _validNodeItem.addActionListener(new AcideDebugDatalogPanelValidNodeListener());
        _popUp.add(_validNodeItem);
        // Option missing node
        _missingNodeItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2322"));
        _missingNodeItem.addActionListener(new AcideDebugDatalogPanelMissingNodeListener());
        _popUp.add(_missingNodeItem);
        // option wrong node
        _wrongNodeItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2324"));
        _wrongNodeItem.addActionListener(new AcideDebugDatalogPanelWrongNodeListener());
        _popUp.add(_wrongNodeItem);

        _errorNodeItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2368"));
        _errorNodeItem.addActionListener(new AcideDebugDatalogPanelShowErrorsListener());
        _popUp.add(_errorNodeItem);

        _undoNodeItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2419"));
        _undoNodeItem.addActionListener(new AcideDebugDatalogPanelUndoListener());
        _popUp.add(_undoNodeItem);

        _redoNodeItem = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2420"));
        _redoNodeItem.addActionListener(new AcideDebugDatalogPanelRedoListener());
        _popUp.add(_redoNodeItem);

        _nodeState = new JMenuItem(AcideLanguageManager.getInstance().getLabels().getString("s2375"));
        _nodeState.setEnabled(false);
        _popUp.add(_nodeState);
    }


    /**
     * Save locations and models for the ACIDE - A Configurable IDE debug Datalog panel.
     */
    public void saveInaccesibleInfo(){

        //Clear all locations saved
        _predicateLocation = new HashMap<>();

        //Clear all models saved
        _predicateModel=new HashMap<>();

        //Get predicates involved in debugging
        LinkedList<String> predicates=DesDatabaseManager.getInstance()
                .executeCommand("/tapi /pdg "+_predicateBox.getSelectedItem().toString());

        if(!predicates.get(0).equals("$error")){

            for(int i=0;i<predicates.size();i++){

                if(predicates.get(i).equals("$"))
                    break;

                LinkedList<String> model=DesDatabaseManager.getInstance()
                        .getTableModel(predicates.get(i).split("/")[0]);

                if(!model.get(0).equals("$error")) {
                    //Save predicate model
                    _predicateModel.put(predicates.get(i), model);
                }

                //Save predicate location
                _predicateLocation.put(predicates.get(i),DesDatabaseManager.getInstance()
                        .executeCommand("/tapi /list_sources " + predicates.get(i)));
            }
        }
    }

    public void this_mousePressed(MouseEvent e){showPopupMenu(e);}

    public void setSelectedNode(MouseEvent e){

        // Gets the graph of the canvas
        DirectedWeightedGraph graph = _canvas.get_graph();
        // Gets the nodes of the graph
        ArrayList<Node> nodes = graph.get_nodes();
        // Searches if a node has been clicked
        for (Node n : nodes) {
            if (e.getX() >= n.getX()
                    && e.getX() <= n.getX() + (int) (_canvas.getNodeSize() * _canvas.getZoom())
                    && e.getY() >= n.getY()
                    && e.getY() <= n.getY() + (int) (_canvas.getNodeSize() * _canvas.getZoom())) {
                _canvas.setSelectedNode(n);
                // Gets the selected node name
                String selected = n.getLabel();
                // Gets the highlighter
                AcideDebugPanelHighLighter highLighter = AcideMainWindow.getInstance().getDebugPanel()
                        .getDebugDatalogPanel().getHighLighter();
                // Resets the highlights
                highLighter.resetLines();
                highLighter.unHighLight();
                // Highlights the lines corresponding to the new selected node
                if(_showRulesMenuItem.isSelected())
                    highLighter.highLight(selected);
            }
        }
        SwingUtilities.invokeLater(() -> AcideDebugHelper.updateDatalogCanvas(_canvas));
    }

    public void showPopupMenu(MouseEvent e){
        setSelectedNode(e);
        if(SwingUtilities.isRightMouseButton(e)){
            _contents.setText(AcideLanguageManager.getInstance().getLabels().getString("s2347"));
            _editIntensionalPredicate.setText(AcideLanguageManager.getInstance().getLabels().getString("s2416"));
            _nonvalidNodeItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2319"));
            _validNodeItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2320"));
            _missingNodeItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2322"));
            _wrongNodeItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2324"));
            _errorNodeItem.setText(AcideLanguageManager.getInstance().getLabels().getString("s2368"));

            // if is extensional predicate not show edit predicate
            String db = DesDatabaseManager.getInstance().currentDB();
            if(DesDatabaseManager.getInstance().isExtensional(db, _canvas.getSelectedNode().getLabel()))
                _editIntensionalPredicate.setVisible(false);
            else
                _editIntensionalPredicate.setVisible(true);

            if((_canvas.getSelectedNode().getLabel().equals(_canvas.getRootNode().getLabel()) ||
                    !_canvas.getSelectedNode().getLabel().equals(_canvas.getRootNode().getLabel()) || isDebugging()) &&
                    !AcideDebugHelper.hasRedNode(_canvas) && (_canvas.getSelectedNode().getNodeColor().equals(Color.YELLOW)
            || _canvas.getSelectedNode().getNodeColor().equals(Color.GRAY))){
                _nonvalidNodeItem.setEnabled(true);
                if(_canvas.getSelectedNode().getLabel().equals(_canvas.getRootNode().getLabel())) {
                    _validNodeItem.setEnabled(false);
                    _validNodeItem.setVisible(false);
                }else{
                    _validNodeItem.setVisible(true);
                    _validNodeItem.setEnabled(true);
                }
                _missingNodeItem.setEnabled(true);
                _wrongNodeItem.setEnabled(true);
            }else{
                _nonvalidNodeItem.setEnabled(false);
                _validNodeItem.setEnabled(false);
                _missingNodeItem.setEnabled(false);
                _wrongNodeItem.setEnabled(false);
            }

            if(AcideDebugHelper.isRedPredicateNode(_canvas.getSelectedNode())){
                if(AcideDebugDatalogDebugWindow.getInstance().getErrors().size() > 0)
                    _errorNodeItem.setEnabled(true);
                else
                    _errorNodeItem.setEnabled(false);
            }else{
                _errorNodeItem.setVisible(false);
            }
            if(AcideDebugDatalogDebugWindow.getInstance().getCurrentState()>1)
                _undoNodeItem.setEnabled(true);
            else
                _undoNodeItem.setEnabled(false);
            if(AcideDebugDatalogDebugWindow.getInstance().getCurrentState()<AcideDebugDatalogDebugWindow
                    .getInstance().getStatesCounter())
                _redoNodeItem.setEnabled(true);
            else
                _redoNodeItem.setEnabled(false);

            checkNodeState();
            // we show the popUp in the position of mouse
            _popUp.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void checkNodeState(){
        Color c = _canvas.getSelectedNode().getNodeColor();
        if(c.equals(Color.RED)){
            _nodeState.setText("<html><strong>" + AcideLanguageManager.getInstance()
                    .getLabels().getString("s2374") + "</strong></html>");
        }else if(c.equals(Color.ORANGE)){
            _nodeState.setText("<html><strong>" + AcideLanguageManager.getInstance()
                    .getLabels().getString("s2373") + "</strong></html>");
        }else if(c.equals(Color.GREEN)){
            _nodeState.setText("<html><strong>" + AcideLanguageManager.getInstance()
                    .getLabels().getString("s2372") + "</strong></html>");
        }else{
            _nodeState.setText("<html><strong>" + AcideLanguageManager.getInstance()
                    .getLabels().getString("s2375") + "</strong></html>");
        }
    }

    public void undo(){
        if(AcideDebugDatalogDebugWindow.getInstance().getCurrentState()>1) {
            _undoNodeItem.setEnabled(true);
            _undoNodeItem.doClick();
        }else _undoNodeItem.setEnabled(false);
    }

    public void redo(){
        if(AcideDebugDatalogDebugWindow.getInstance().getCurrentState()<AcideDebugDatalogDebugWindow
                .getInstance().getStatesCounter()) {
            _redoNodeItem.setEnabled(true);
            _redoNodeItem.doClick();
        }else _redoNodeItem.setEnabled(false);
    }

    /**
     * Returns the ACIDE - A Configurable IDE debug datalog panel canvas.
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel canvas.
     */
    public AcideDebugCanvas getCanvas(){return _canvas;}

    /**
     * Returns the ACIDE - A Configurable IDE debug datalog panel query text.
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel query text.
     */
    public String getQuery(){return _query;}

    /**
     * Sets a new value to the ACIDE - A Configurable IDE debug datalog
     * panel query text.
     *
     * @param query
     *          the new value to set.
     */
    public void setQuery(String query){this._query = query;}

    /**
     * Sets availability to the ACIDE - A Configurable IDE debug datalog panel locate
     * button
     *
     * @param enable
     *              button availability
     */
    public void enableLocateButton(boolean enable){this._locateButton.setEnabled(enable);}

    /**
     * Returns the ACIDE - A Configurable IDE debug datalog panel zoom spinner.
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel zoom spinner
     */
    public static JSpinner getZoomSpinner(){return _zoomSpinner;}

    /**
     * Sets a new value to the ACIDE - A Configurable IDE debug datalog
     * panel zoom spinner
     *
     * @param zoomSpinner
     *              the new value to set.
     */
    public void setZoomSpinner(JSpinner zoomSpinner){this._zoomSpinner=zoomSpinner;}

    /**
     * Returns the ACIDE - A Configurable IDE debug datalog panel high lighter.
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel high lighter
     */
    public AcideDebugPanelHighLighter getHighLighter(){return _highlighter;}

    /**
     * Sets a new value to the ACIDE - A Configurable IDE debug datalog panel
     * high lighter.
     *
     * @param highLighter
     *              the new value to set.
     */
    public void setHighLighter(AcideDebugPanelHighLighter highLighter){this._highlighter=highLighter;}

    /**
     * Return the ACIDE - A Configurable IDE debug datalog panel predicate location.
     *
     * @param predicate
     *              predicate to highlight.
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel predicate location.
     */
    public LinkedList<String> getPredicateLocation(String predicate){
        return this._predicateLocation.get(predicate);
    }

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
     * Returns the ACIDE - A Configurable IDE debug datalog predicate model
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel predicate model.
     */
    public HashMap<String,LinkedList<String>> getPredicatesModels(){return this._predicateModel;}

    /**
     * Sets a new value to the ACIDE - A Configurable IDE debug datalog panel
     * predicate model.
     *
     * @param predicateModel
     *              the new value to set.
     */
    public void setPredicatesModels(HashMap<String,LinkedList<String>> predicateModel){this._predicateModel=predicateModel;}

    /**
     * Returns the ACIDE - A Configurable IDE debug datalog panel predicate model.
     *
     * @param predicate
     *          predicate whose model is to be sought
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel predicate model.
     */
    public LinkedList<String> getPredicateModel(String predicate){
        return this._predicateModel.get(predicate);
    }

    /**
     *
     * Returns the ACIDE - A Configurable IDE debug datalog panel show rules
     * item.
     *
     * @return the ACIDE - A Configurable IDE debug datalog panel show rules
     *         item.
     */
    public JCheckBox getShowRulesMenuItem(){ return _showRulesMenuItem;}

    /**
     * Sets a new value to the ACIDE - A Configurable IDE debug datalog panel
     * show rules item.
     *
     * @param showRulesMenuItem
     *              the new value to set.
     */
    public void setShowRulesMenuItem(JCheckBox showRulesMenuItem){this._showRulesMenuItem=showRulesMenuItem;}

    class AcideDebugDatalogPanelClickListener extends MouseAdapter {
        private AcideDebugDatalogPanel adaptee;

        AcideDebugDatalogPanelClickListener(AcideDebugDatalogPanel adaptee){this.adaptee = adaptee;}

        public void mousePressed(MouseEvent e){
            if(e.getClickCount()>=2){
                String predicate;
                if(_canvas.getSelectedNode().getLabel().contains("/")){
                    predicate = _canvas.getSelectedNode().getLabel();
                    AcideDebugHelper.showPredicate(predicate);
                }

            }else if(SwingUtilities.isRightMouseButton(e)) {

                adaptee.this_mousePressed(e);
            }
        }

        public void mouseReleased(MouseEvent e){adaptee.setSelectedNode(e);}
    }

    private void setListeners(){
        _canvas.addMouseListener(new AcideDebugDatalogPanelClickListener(this));
    }

    public boolean isDebugging(){return _debugging;}

    public void setDebugging(boolean debugging){this._debugging = debugging;}

    public void setBackgroundColor(Color backgroundColor, Color foregroundcolor,Color darker){
        this.setBackground(backgroundColor);
        this._mainButtonPanel.setBackground(darker);
        this._mainButtonPanel.setForeground(foregroundcolor);
        Component[] c=this._mainButtonPanel.getComponents();
        for(Component c1: ((JPanel)c[1]).getComponents()) {
            if(c1 instanceof JLabel)
                ((JLabel)c1).setForeground(foregroundcolor);
        }
        SwingUtilities.invokeLater(() -> AcideDebugHelper.updateDatalogCanvas(_canvas));
    }

    /**
     * Sets the text of the components assigned to the ACIDE - A Configurable
     * IDE debug datalog panel.
     */
    public void setComponentsText(){
        _refreshDatalog.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2044"));
        _startDebug.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2325"));
        _configDebug.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2326"));
        _zoomIn.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2389"));
        _zoomOut.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2390"));
        _showLabelsMenuItem.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2263"));
        _showLabelsMenuItem.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2421"));
        _predicateLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2408"));
        _predicateLabel.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2409"));
        _firstNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2391"));
        _lastNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2394"));
        _prevNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2392"));
        _nextNodeButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2393"));
        _locateButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2295"));
        _locateButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2407"));
        _showRulesMenuItem.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2279"));
        _showRulesMenuItem.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2422"));
    }

    /**
     * Disable components assigned to the ACIDE - A Configurable IDE debug datalog panel.
     */
    public void disableComponents(){
        _canvas.setEnabled(false);
        for(Component p:_mainButtonPanel.getComponents()){
            for(Component c: ((JPanel)p).getComponents()){
                c.setEnabled(false);
            }
        }
    }

    /**
     * Enable components assigned to the ACIDE - A Configurable IDE debug datalog panel.
     */
    public void enableComponents(){
        _canvas.setEnabled(true);
        for(Component p:_mainButtonPanel.getComponents()){
            for(Component c: ((JPanel)p).getComponents()){
                if(!c.equals(_refreshDatalog) && !c.equals(_startDebug))
                    c.setEnabled(true);
                else
                    if(_predicateBox.getSelectedIndex()!=0)
                        c.setEnabled(true);
            }
        }
    }
}
