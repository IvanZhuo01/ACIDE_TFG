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
package acide.gui.debugPanel.debugSQLPanel;

import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.utils.AcideEnterTextWindow;
import acide.gui.debugPanel.debugDatalogPanel.AcideDebugDatalogPanel;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.graphUtils.Node;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.menuBar.editMenu.listeners.AcideRedoAction;
import acide.language.AcideLanguageManager;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;
//import com.sun.deploy.security.SelectableSecurityManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class AcideDebugSQLDebugWindow extends JFrame {

    private JPanel tablePanel;
    private JPanel questionPanel;
    private JPanel errorPanel;
    private JPanel buttonPanel;
    private JPanel errButtonPanel;

    private JLabel infoRecibedLabel;
    private JLabel numTuplesLabel;
    private JLabel questionLabel;
    private JLabel errorsLabel;

    private JButton validButton;
    private JButton nonValidButton;
    private JButton missingTupleButton;
    private JButton wrongTupleButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton abortButton;

    private JButton okButton;
    private JButton showStatsButton;
    private JButton editTableButton;
    private JButton editViewButton;

    private String view;
    private String currentQuestion;
    private String questionType = "all";
    private HashMap<String,String> errors = new HashMap<>();

    private JScrollPane viewTable;
    private AcideDataBaseDataViewTable jTable;

    private AcideDatabaseDataView subsetView;

    private int currentState = 0;
    private int statesCounter = 0;

    private static final ImageIcon ICON = new ImageIcon(
            "./resources/images/icon.png");

    private static final ImageIcon UNDO = new ImageIcon(
            "./resources/icons/panels/undo.png");

    private static final ImageIcon REDO = new ImageIcon(
            "./resources/icons/panels/redo.png");

    private static AcideDebugSQLDebugWindow instance;

    public static AcideDebugSQLDebugWindow getInstance() {
        if (instance == null) {
            instance = new AcideDebugSQLDebugWindow();
        }
        return instance;
    }

    public AcideDebugSQLDebugWindow() {
        this.setTitle("Debug execution");

        this.setView(AcideDebugHelper.getSelectedViewName());

        this.setIconImage(ICON.getImage());

        setLocationRelativeTo(null);

        // Builds the window components
        buildComponents();

        // Sets the listener for the window components
        setListeners();

        // Adds the components to the window
        addComponents();

        // Sets the window configuration
        setWindowConfiguration();

        addWindowListener(new WindowCloser());
    }

    private void buildComponents() {
        infoRecibedLabel = new JLabel();
        numTuplesLabel = new JLabel();
        questionLabel = new JLabel();
        errorsLabel = new JLabel();

        validButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2348"));
        nonValidButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2349"));
        missingTupleButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2350"));
        wrongTupleButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2351"));
        undoButton = new JButton(UNDO);
        undoButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2419"));
        redoButton = new JButton(REDO);
        redoButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2420"));
        abortButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2352"));

        okButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2353"));
        showStatsButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2354"));
        editTableButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2355"));
        editViewButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2360"));

        questionLabel.setText("");
        infoRecibedLabel.setText("");
        numTuplesLabel.setText("");

        viewTable = new JScrollPane();

        //Creates the table panel
        tablePanel = new JPanel(new BorderLayout());

        //Creates the question panel
        questionPanel = new JPanel(new BorderLayout());

        //Creates the error panel
        errorPanel = new JPanel(new BorderLayout());

        // Creates the button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(validButton);
        buttonPanel.add(nonValidButton);
        buttonPanel.add(missingTupleButton);
        buttonPanel.add(wrongTupleButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(abortButton);

        // Creates the error button panel
        errButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        errButtonPanel.add(okButton);
        errButtonPanel.add(showStatsButton);
        errButtonPanel.add(editTableButton);
        errButtonPanel.add(editViewButton);
    }

    private void setListeners() {
        validButton.addActionListener(new ValidNodeAction());
        nonValidButton.addActionListener(new NonValidNodeAction());
        missingTupleButton.addActionListener(new MissingTupleAction());
        wrongTupleButton.addActionListener(new WrongTupleAction());
        undoButton.addActionListener(new UndoAction());
        redoButton.addActionListener(new RedoAction());
        abortButton.addActionListener(new AbortAction());

        okButton.addActionListener(new CloseAction());
        showStatsButton.addActionListener(new StatsAction());
        editTableButton.addActionListener(new EditTableAction());
        editViewButton.addActionListener(new EditViewAction());
    }

    private void addComponents() {
        // Sets the layout
        setLayout(new GridBagLayout());

        // Adds the components to the window with the layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 100;
        constraints.ipady = 10;

        // Adds the info label to the table panel
        infoRecibedLabel.setBorder(BorderFactory.createEmptyBorder(12,3,5,0));
        tablePanel.add(infoRecibedLabel, BorderLayout.NORTH);

        // Adds the question label to the main panel
        questionLabel.setBorder(BorderFactory.createEmptyBorder(0,3,5,0));
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        // Adds the errors label to the main panel
        errorPanel.add(errorsLabel, BorderLayout.NORTH);

        // Adds the table panel to the window
        add(tablePanel, constraints);

        constraints.gridy = 1;

        add(questionPanel,constraints);

        constraints.gridy = 2;

        add(errorPanel,constraints);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 3;

        // Adds the button panel to the window
        add(buttonPanel, constraints);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 4;

        // Adds the error button panel to the window
        add(errButtonPanel, constraints);

        // Sets the window closing listener
        addWindowListener(new AcideWindowClosingListener());

        // Puts the escape key in the input map of the window
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
                "EscapeKey");

        // Puts the escape key in the action map of the window
        getRootPane().getActionMap().put("EscapeKey", new CloseAction());
    }

    private void setWindowConfiguration(){
        // The window is not resizable
        setResizable(true);

        // Packs the window components
        pack();

    }

    /**
     * Closes the ACIDE - A Configurable IDE debug configuration window.
     */
    public void closeWindow() {
        if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging()) {
            if (!DesDatabaseManager.getInstance().debugCurrentSQLQuestion().getFirst().equals("$error"))
                AcideDebugHelper.performSQLDebug("abort");
        }
        AcideDebugHelper.refreshDebugSQLGraph();
        resetErrors();
        removeAllStates();

        // Enables the main window again
        AcideMainWindow.getInstance().setEnabled(true);

        //enable start debug button
        AcideDebugSQLPanel.startDebug.setEnabled(true);

        AcideMainWindow.getInstance().setAlwaysOnTop(true);
        AcideMainWindow.getInstance().setAlwaysOnTop(false);

        AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebugging(false);

        this.setVisible(false);
    }

    public void showWindow() {

        // Debug execution
        this.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2356"));

        checkQuestionType();

        if(AcideDebugHelper.isRootView(this.view))
            validButton.setVisible(false);
        else
            validButton.setVisible(true);

       if(this.currentState>1)
            undoButton.setEnabled(true);
        else
            undoButton.setEnabled(false);

        if(this.currentState<this.statesCounter)
            redoButton.setEnabled(true);
        else
            redoButton.setEnabled(false);

        viewTable.setVisible(true);
        numTuplesLabel.setVisible(true);
        questionLabel.setVisible(true);
        buttonPanel.add(this.undoButton,4);
        buttonPanel.setVisible(true);
        errButtonPanel.setVisible(false);

        if(errors.size()>0) {
            LinkedList<String> status = DesDatabaseManager.getInstance().getSQLNodeStates();

            boolean found = false;
            String info = "<html><br>" + AcideLanguageManager.getInstance().getLabels()
                    .getString("s2365");

            for (String node: status) {
                if (errors.containsKey(node)) {
                    info += "<br>" + " " + " " + " - " + errors.get(node);
                    found = true;
                }
            }

            info += "</html>";
            errorsLabel.setText(info);
            if (found)
                errorsLabel.setVisible(true);
            else
                errorsLabel.setVisible(false);
        }else
            errorsLabel.setVisible(false);

        setAlwaysOnTop(true);
        //setAlwaysOnTop(false);
        Cursor.getDefaultCursor();
        setResizable(true);
        if(questionType.equals("subset") && subsetView!=null){
            this.setLocation(300+subsetView.getWidth(), 300);
        }

        setWindowConfiguration();

        // Displays the window
        if(!AcideDebugSQLPanel.startDebug.isEnabled())
            this.setVisible(true);
        else this.setVisible(false);

    }

    public void stopDebug(String view,boolean nodeDebug){
        String type;
        if(DesDatabaseManager.getInstance().isTable(DesDatabaseManager.getInstance().currentDB(), view)){
            editTableButton.setVisible(true);
            editViewButton.setVisible(false);
            type = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2371");
        }else{
            editTableButton.setVisible(false);
            editViewButton.setVisible(true);
            type = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2370");
        }

        if(!nodeDebug) {
            if (this.currentState > 1)
                undoButton.setEnabled(true);
            else
                undoButton.setEnabled(false);
        }

        String info = AcideLanguageManager.getInstance().getLabels()
                .getString("s2345") + " " + type + " '" + view + "' <br>" +
                AcideLanguageManager.getInstance().getLabels().getString("s2346");

        if(errors.size() > 0){
            LinkedList<String> status = DesDatabaseManager.getInstance().getSQLNodeStates();

            info += "<br>" + AcideLanguageManager.getInstance().getLabels()
                    .getString("s2365");

            for (String node: status) {
                if (errors.containsKey(node))
                    info += "<br>" + " " + " " + " - " + errors.get(node);
            }
        }

        this.setTitle(AcideLanguageManager.getInstance().getLabels()
                .getString("s2344"));

        this.setView(view);
        setInfo(info);

        viewTable.setVisible(false);
        numTuplesLabel.setVisible(false);
        questionLabel.setVisible(false);
        questionPanel.setVisible(false);
        errorsLabel.setVisible(false);
        errorPanel.setVisible(false);
        buttonPanel.setVisible(false);
        if(!nodeDebug)
            errButtonPanel.add(this.undoButton);
        errButtonPanel.setVisible(true);

        AcideDebugSQLPanel.startDebug.setEnabled(true);
        AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebugging(false);

        this.setSize(new Dimension(350,200));
        this.setMinimumSize(new Dimension(350,150));
        setAlwaysOnTop(true);
        setWindowConfiguration();
        this.setVisible(true);
    }

    public void restoreDebug(){

        // Debug execution
        this.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2356"));

        checkQuestionType();

        editTableButton.setVisible(false);
        editViewButton.setVisible(false);

        if(this.currentState>1)
            undoButton.setEnabled(true);
        else
            undoButton.setEnabled(false);

        if(this.currentState<this.statesCounter)
            redoButton.setEnabled(true);
        else
            redoButton.setEnabled(false);

        if(errors.size()>0) {
            LinkedList<String> status = DesDatabaseManager.getInstance().getSQLNodeStates();

            boolean found = false;
            String info = "<html><br>" + AcideLanguageManager.getInstance().getLabels()
                    .getString("s2365");

            for (String node: status) {
                if (errors.containsKey(node)) {
                    info += "<br>" + " " + " " + " - " + errors.get(node);
                    found = true;
                }
            }

            info += "</html>";
            errorsLabel.setText(info);
            if(found) {
                errorsLabel.setVisible(true);
                errorPanel.setVisible(true);
            }else
                errorsLabel.setVisible(false);
        }else
            errorsLabel.setVisible(false);

        viewTable.setVisible(true);
        numTuplesLabel.setVisible(true);
        questionLabel.setVisible(true);
        questionPanel.setVisible(true);
        buttonPanel.add(this.undoButton,4);
        buttonPanel.setVisible(true);
        errButtonPanel.setVisible(false);

        AcideDebugSQLPanel.startDebug.setEnabled(false);
        AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebugging(true);

        setAlwaysOnTop(true);
        //setAlwaysOnTop(false);
        Cursor.getDefaultCursor();
        setResizable(true);
        if(questionType.equals("subset") && subsetView!=null){
            this.setLocation(300+subsetView.getWidth(), 300);
        }

        setWindowConfiguration();

        // Displays the window
        if(!AcideDebugSQLPanel.startDebug.isEnabled())
            this.setVisible(true);
        else this.setVisible(false);
    }

    /**
     * ACIDE - A Configurable IDE debug configuration window escape key
     * action.
     *
     * @version 0.11
     * @see AbstractAction
     */
    class CloseAction extends AbstractAction {

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // Closes the window
            closeWindow();
        }
    }

    class ValidNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            removeError();
            AcideDebugHelper.performSQLDebug("valid");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
        }
    }

    class NonValidNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            removeError();
            AcideDebugHelper.performSQLDebug("nonvalid");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
        }
    }

    class MissingTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            jTable.changeSelection(jTable.getRowCount()-1, 1, false, false);
            jTable.changeSelection(jTable.getRowCount()-1, jTable.getColumnCount()-1, true, true);
            String data = AcideDebugHelper.getDataFromSelectedTuple(jTable);
            // Check data is not null
            String trimmedData =  data.replace("'", "").replace(",", "");
            if(trimmedData.isEmpty()){
                JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance()
                        .getLabels().getString("s2379"));
            } else {
                addError(AcideDebugSQLDebugWindow.getInstance().getView(),AcideLanguageManager.getInstance()
                        .getLabels().getString("s2350") + " (" + data + ") " + AcideLanguageManager.getInstance()
                        .getLabels().getString("s2399") +" "+ AcideDebugSQLDebugWindow.getInstance().getView());
                AcideDebugHelper.performSQLDebug("missing(" +
                        AcideDebugSQLDebugWindow.getInstance().getView() + "(" + data + "))");
                if (AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging())
                    SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
            }
        }
    }

    class WrongTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addError(AcideDebugSQLDebugWindow.getInstance().getView(),AcideLanguageManager.getInstance()
                    .getLabels().getString("s2351") + " (" + AcideDebugHelper.getDataFromSelectedTuple(jTable) + ") "+
                    AcideLanguageManager.getInstance().getLabels().getString("s2399") +" "+
                    AcideDebugSQLDebugWindow.getInstance().getView());
            AcideDebugHelper.performSQLDebug("wrong(" +
                    AcideDebugSQLDebugWindow.getInstance().getView() + "(" + AcideDebugHelper.getDataFromSelectedTuple(jTable) +"))");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
        }
    }
    class UndoAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AcideDebugHelper.restoreSQLDebugState("undo",false);

            if (AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
            else
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().restoreDebug());
        }
    }
    class RedoAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            AcideDebugHelper.restoreSQLDebugState("redo",false);

            if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugSQLDebugWindow.getInstance().showWindow());
        }
    }

    class AbortAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            closeWindow();
        }
    }

    class StatsAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            SwingUtilities.invokeLater(() -> setAlwaysOnTop(false));
            LinkedList<String> info = DesDatabaseManager.getInstance().getSQLDebugStats();
            if(info.size() > 0 && info.get(0).equals("$error")){
                JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance()
                        .getLabels().getString("s2357"), AcideLanguageManager.getInstance()
                        .getLabels().getString("s2316"), JOptionPane.INFORMATION_MESSAGE);
            }else {
                String[] columnNames = {AcideLanguageManager.getInstance()
                        .getLabels().getString("s2358"), AcideLanguageManager.getInstance()
                        .getLabels().getString("s2359")};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                for (int i = 0; i < info.size(); i++) {
                    Object[] row = {info.get(i),info.get(i+1)};
                    tableModel.addRow(row);
                    i++;
                }
                JTable table = new JTable(tableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                final TableColumnModel columnModel = table.getColumnModel();
                for (int column = 0; column < table.getColumnCount(); column++) {
                    int width = 15; // Min width
                    for (int row = 0; row < table.getRowCount(); row++) {
                        TableCellRenderer renderer = table.getCellRenderer(row, column);
                        Component comp = table.prepareRenderer(renderer, row, column);
                        width = Math.max(comp.getPreferredSize().width +1 , width);
                    }
                    if(width > 300)
                        width=300;
                    columnModel.getColumn(column).setPreferredWidth(width);
                }

                JOptionPane.showMessageDialog(new Frame(), table, AcideLanguageManager.getInstance()
                        .getLabels().getString("s2343"), JOptionPane.INFORMATION_MESSAGE);
            }
            SwingUtilities.invokeLater(() -> setAlwaysOnTop(true));
        }
    }

    class EditViewAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            SwingUtilities.invokeLater(() -> setAlwaysOnTop(false));
            String db = DesDatabaseManager.getInstance().currentDB();
            String text = AcideDatabaseManager.getInstance().getSQLText(db, view);
            new AcideEnterTextWindow(text, AcideLanguageManager.getInstance()
                    .getLabels().getString("s2036"), true);
            SwingUtilities.invokeLater(() -> stopDebug(view,false));
        }
    }

    class EditTableAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AcideDebugHelper.showView(AcideDebugSQLDebugWindow.getInstance().getView());
        }
    }

    class WindowCloser extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent event){
            closeWindow();
        }
    }

    public void setInfo(String info){
        String text = "<html>";
        text += info;
        text += "</html>";
        infoRecibedLabel.setText(text);
        setWindowConfiguration();
    }

    public void setQuestion(String question){
        questionLabel.setText(question);
        setWindowConfiguration();
    }

    public void putView(String view, JScrollPane viewTable){
        try {
            if(this.view != null)
                tablePanel.remove(this.viewTable);
        }catch (Exception e){

        }

        setView(view);
        setViewTable(viewTable);
        setJTable(jTable);

        int width = (jTable.getColumnCount() - 1) * 150 + 30;
        int height = jTable.getRowHeight();

        if(jTable.getRowCount()>10)
            height*=10;
        else
            height*=jTable.getRowCount()+1;

        this.viewTable.setPreferredSize(new Dimension(width, height));
        // Adds the view viewTable to the main panel
        tablePanel.add(this.viewTable, BorderLayout.CENTER);

        // Adds the number of tuples label to the predicate panel
        numTuplesLabel.setBorder(BorderFactory.createEmptyBorder(12,3,5,0));
        numTuplesLabel.setText(AcideLanguageManager.getInstance().getLabels().getString("s2425")+": "
                +Integer.toString(jTable.getRowCount()-1));
        tablePanel.add(numTuplesLabel,BorderLayout.SOUTH);
    }

    public void setViewTable(JScrollPane viewTable){
        this.viewTable = viewTable;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setJTable(AcideDataBaseDataViewTable jTable){
        this.jTable = jTable;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentState(){ return this.currentState; }

    public int getStatesCounter(){return this.statesCounter;}

    public void removeAllStates(){
        currentState=0;
        for(int i=1;i<=statesCounter;i++)
            new File("./debug/"+"s"+i+".sds").delete();
        statesCounter=0;
    }

    public void removeLastStates(){
        for(int i=currentState+1;i<=statesCounter;i++)
            new File("./debug/"+"s"+i+".sds").delete();
        statesCounter=currentState;
    }

    public void updateStatusHandlers(String action){
        if(action.equals("save")){
            currentState++;
            statesCounter++;
        } else if(action.equals("unsave")){
            currentState--;
            statesCounter--;
        } else if(action.equals("undo"))
            currentState--;
        else if(action.equals("notundo"))
            currentState++;
        else if(action.equals("redo"))
            currentState++;
        else
            currentState--;
    }

    public JScrollPane getViewTable() {
        return viewTable;
    }

    public void resetErrors(){
        errors = new HashMap<>();
        errorsLabel.setText("");
        errorsLabel.setVisible(true);
    }

    public void addError(String view, String error) {

        errors.put(view, error);

        LinkedList<String> status = DesDatabaseManager.getInstance().getSQLNodeStates();

        String info = "<html><br>" + AcideLanguageManager.getInstance().getLabels()
                .getString("s2365");

        if (!status.get(0).equals("$error")) {
            for (String node: status) {
                if (errors.containsKey(node))
                    info += "<br>" + " " + " " + " - " + errors.get(node);
            }
        }
        info += "<br>" + " " + " " + " - " + errors.get(view);
        info += "</html>";

        errorsLabel.setText(info);
        setWindowConfiguration();
    }

    public void removeError(){
        if(errors.containsKey(this.view))
            errors.remove(this.view);
    }

    public HashMap<String,String> getErrors(){
        return errors;
    }

    public void setQuestionType(String type) {
        questionType = type;
    }

    private void checkQuestionType(){
        if(questionType.equals("in")){
            setQuestionIn();
        }else if(questionType.equals("subset")){
            setQuestionSubset();
        }else{
            setQuestionAll();
        }
    }

    private void setQuestionIn(){
        wrongTupleButton.setVisible(false);
        missingTupleButton.setVisible(false);

        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s62"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s63"));

        questionLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2369") + view + "?");

        setTupleFromQuestionIn();
    }

    private void setTupleFromQuestionIn(){
        String tupleContent = currentQuestion;

        // subtract in
        tupleContent = tupleContent.substring(tupleContent.indexOf("(")+1,tupleContent.lastIndexOf(")"));

        // subtract tuple content
        if(tupleContent.contains("("))
            tupleContent = tupleContent.substring(tupleContent.indexOf("(")+1,tupleContent.lastIndexOf(")"));

        Vector<Vector> data = ((AcideDatabaseDataView.MyTableModel) jTable.getModel()).getDataVector();
        for(int i = 0; i < data.size(); i++){
            if(AcideDebugHelper.parseTupleContent(data.get(i)).equals(tupleContent)){
                jTable.changeSelection(i, 1, false, false);
                jTable.changeSelection(i, jTable.getColumnCount()-1, true, true);
                break;
            }
        }
    }

    private void setQuestionAll(){
        wrongTupleButton.setVisible(true);
        missingTupleButton.setVisible(true);

        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2348"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2349"));
    }

    private void setQuestionSubset(){
        wrongTupleButton.setVisible(true);
        missingTupleButton.setVisible(false);

        String relation = currentQuestion.substring(currentQuestion.indexOf("(") + 1, currentQuestion.indexOf(","));

        String db = DesDatabaseManager.getInstance().currentDB();
        subsetView = AcideMainWindow.getInstance().getDataBasePanel().getDataView(db, relation);
        LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, relation);
        if(!info.isEmpty())
            subsetView.build(info);

        subsetView.setAlwaysOnTop(true);
        subsetView.setAlwaysOnTop(false);
        subsetView.setVisible(true);

        subsetView.setLocation(300, 300);

        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s62"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s63"));
        questionLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2378") + view + "?");
    }

    public AcideDatabaseDataView getSubsetView() {
        return subsetView;
    }

    public void setSubsetView(){
        subsetView = null;
    }

    /**
     * Sets the text of the components assigned to the ACIDE - A Configurable
     * IDE debug SQL debug Window.
     */
    public void setComponentsText() {
        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2348"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2349"));
        missingTupleButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2350"));
        wrongTupleButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2351"));
        undoButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2419"));
        redoButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2420"));
        abortButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2352"));
        okButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2353"));
        showStatsButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2354"));
        editTableButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2355"));
        editViewButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2360"));
    }
}
