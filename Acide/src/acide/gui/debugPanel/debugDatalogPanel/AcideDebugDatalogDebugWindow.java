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

import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.utils.AcideEnterTextWindow;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.process.console.DesDatabaseManager;
import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;
import sun.security.util.AuthResources_de;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class AcideDebugDatalogDebugWindow extends JFrame{

    private JPanel predicatePanel;
    private JPanel questionPanel;
    private JPanel errorPanel;
    private JPanel buttonPanel;
    private JPanel errButtonPanel;

    private JLabel infoReceibedLabel;
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
    private JButton editExtensionalButton;
    private JButton editIntensionalButton;

    private String predicate;
    private String currentQuestion;
    private String questionType="all";
    private HashMap<String,String> errors =new HashMap<>();

    private JScrollPane predicateTable;
    private JScrollPane solutionsTable;
    private AcideDataBaseDataViewTable jTable;
    private AcideDataBaseDataViewTable jTableSolutions;

    private int currentState = 0;
    private int statesCounter = 0;

    private static final ImageIcon ICON = new ImageIcon(
            "./resources/images/icon.png");

    private static final ImageIcon UNDO = new ImageIcon(
            "./resources/icons/panels/undo.png");

    private static final ImageIcon REDO = new ImageIcon(
            "./resources/icons/panels/redo.png");

    private static AcideDebugDatalogDebugWindow instance;

    public static AcideDebugDatalogDebugWindow getInstance(){
        if(instance == null){
            instance = new AcideDebugDatalogDebugWindow();
        }
        return instance;
    }

    public AcideDebugDatalogDebugWindow(){
        this.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2356"));

        this.setPredicate(AcideDebugHelper.getSelectedPredicateName());

        // The window is placed in the center of screen
        setLocationRelativeTo(null);

        // Builds the window components
        buildComponents();

        // Sets the listener for the window components
        setListeners();

        // Adds the components to the window
        addComponents();
    }

    private void buildComponents(){

        infoReceibedLabel = new JLabel();
        numTuplesLabel = new JLabel();
        questionLabel = new JLabel();
        errorsLabel = new JLabel();

        infoReceibedLabel.setText("");
        numTuplesLabel.setText("");
        questionLabel.setText("");

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
        undoButton.setEnabled(false);
        redoButton = new JButton(REDO);
        redoButton.setToolTipText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2420"));
        redoButton.setEnabled(false);
        abortButton = new JButton(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2352"));

        okButton = new JButton(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2353"));
        showStatsButton = new JButton(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2354"));
        editExtensionalButton = new JButton(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2411"));
        editIntensionalButton = new JButton(AcideLanguageManager.getInstance()
                    .getLabels().getString("s2413"));

        predicateTable = new JScrollPane();
        solutionsTable = new JScrollPane();

        //Creates the predicate panel
        predicatePanel = new JPanel(new BorderLayout());

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
        errButtonPanel.add(editExtensionalButton);
        errButtonPanel.add(editIntensionalButton);

    }

    private void setListeners(){

       validButton.addActionListener(new ValidNodeAction());
       nonValidButton.addActionListener(new NonValidNodeAction());
       missingTupleButton.addActionListener(new MissingTupleAction());
       wrongTupleButton.addActionListener(new WrongTupleAction());
       undoButton.addActionListener(new UndoAction());
       redoButton.addActionListener(new RedoAction());
       abortButton.addActionListener(new AbortAction());

       okButton.addActionListener(new CloseAction());
       showStatsButton.addActionListener(new StatsAction());
       editIntensionalButton.addActionListener(new EditIntensionalAction());
       editExtensionalButton.addActionListener(new EditExtensionalAction());
    }

    private void addComponents(){

        // Sets the layout
        setLayout(new GridBagLayout());

        // Adds the components to the window with the layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 100;
        constraints.ipady = 10;

        // Adds the info label to the predicate panel
        infoReceibedLabel.setBorder(BorderFactory.createEmptyBorder(12,3,5,0));
        predicatePanel.add(infoReceibedLabel,BorderLayout.NORTH);

        //Adds the question label to the predicate panel
        questionLabel.setBorder(BorderFactory.createEmptyBorder(0,3,5,0));
        questionPanel.add(questionLabel,BorderLayout.NORTH);

        //Adds the error label to the predicate panel
        errorPanel.add(errorsLabel,BorderLayout.NORTH);

        // Adds the predicate panel to the window
        add(predicatePanel,constraints);

        constraints.gridy = 1;

        // Adds the predicate panel to the window
        add(questionPanel,constraints);

        constraints.gridy = 2;

        // Adds the predicate panel to the window
        add(errorPanel,constraints);

        constraints.insets = new Insets(0,0,0,0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 3;

        // Adds the button panel to the window
        add(buttonPanel,constraints);

        constraints.gridy = 4;

        // Adds the error panel to the window
        add(errButtonPanel,constraints);

        // Sets the window closing listener
        addWindowListener(new AcideWindowClosingListener());

        // Puts the escape key in the input map of the window
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false),
                "EscapeKey");

        // Puts the escape key in the action map of the window
        getRootPane().getActionMap().put("EscapeKey",new CloseAction());

    }

    private void setWindowConfiguration(){
        // The window is resizable
        setResizable(true);

        // Packs the window components
        pack();
    }

    /**
     * Closes the ACIDE - A Configurable IDE start debug window.
     */
    public void closeWindow(){
        if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging()){
            if(DesDatabaseManager.getInstance().debugCurrentDatalogQuestion().isEmpty() ||
            !DesDatabaseManager.getInstance().debugCurrentDatalogQuestion().get(0).equals("$error") ||
                    !DesDatabaseManager.getInstance().debugCurrentDatalogQuestion().get(0).equals("[]")){
                AcideDebugHelper.performDatalogDebug("abort");
            }
        }
        AcideDebugHelper.refreshDebugDatalogGraph();
        resetErrors();

        // Enables the main window again
        AcideMainWindow.getInstance().setEnabled(true);

        // Enable start debug button
        AcideDebugDatalogPanel._startDebug.setEnabled(true);

        AcideMainWindow.getInstance().setAlwaysOnTop(true);
        AcideMainWindow.getInstance().setAlwaysOnTop(false);

        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().setDebugging(false);

        questionPanel.setVisible(true);
        errorPanel.setVisible(true);

        this.setVisible(false);

    }

    public void showWindow(){

        this.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2356"));

        if(AcideDebugHelper.isRootPredicate(this.predicate))
            validButton.setVisible(false);
        else
            validButton.setVisible(true);

        this.solutionsTable.setVisible(false);

        checkQuestionType();

        if(currentState>1)
            undoButton.setEnabled(true);
        else
            undoButton.setEnabled(false);

        if(currentState<statesCounter)
            redoButton.setEnabled(true);
        else
            redoButton.setEnabled(false);

        questionLabel.setVisible(true);
        predicateTable.setVisible(true);
        numTuplesLabel.setVisible(true);
        buttonPanel.add(undoButton,4);
        buttonPanel.setVisible(true);
        errButtonPanel.setVisible(false);

        if(errors.size()>0) {

            LinkedList<String> status = DesDatabaseManager.getInstance().getDatalogNodeStates();

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

        setWindowConfiguration();

        //Displays the window
        if(!AcideDebugDatalogPanel._startDebug.isEnabled())
            this.setVisible(true);
        else this.setVisible(false);

    }

    public void stopDebug(String predicate,boolean nodeDebug){

        if(DesDatabaseManager.getInstance().isExtensional(DesDatabaseManager.getInstance().currentDB(),
                predicate.split("/")[0])) {
            editIntensionalButton.setVisible(false);
            editIntensionalButton.setEnabled(false);
            editExtensionalButton.setVisible(true);
            editExtensionalButton.setEnabled(true);
        }else{
            editIntensionalButton.setVisible(true);
            editIntensionalButton.setEnabled(true);
            editExtensionalButton.setVisible(false);
            editExtensionalButton.setEnabled(false);
        }
        if(!nodeDebug) {
            if (currentState > 1)
                undoButton.setEnabled(true);
            else
                undoButton.setEnabled(false);
        }

        String info = AcideLanguageManager.getInstance().getLabels().getString("s2417")+
                " '"+ predicate.split("/")[0] + "' <br>"+ AcideLanguageManager
                .getInstance().getLabels().getString("s2346");

        if(errors.size()>0){
            LinkedList<String> status = DesDatabaseManager.getInstance().getDatalogNodeStates();

            info += "<br>" + AcideLanguageManager.getInstance().getLabels()
                    .getString("s2365");

            for (String node: status) {
                if (errors.containsKey(node)) {
                    info += "<br>" + " " + " " + " - " + errors.get(node);
                }
            }
        }

        this.setTitle(AcideLanguageManager.getInstance().getLabels().getString("s2344"));
        this.setPredicate(predicate);
        setInfo(info);
        predicateTable.setVisible(false);
        numTuplesLabel.setVisible(false);
        questionLabel.setVisible(false);
        questionPanel.setVisible(false);
        solutionsTable.setVisible(false);
        errorsLabel.setVisible(false);
        errorPanel.setVisible(false);
        buttonPanel.setVisible(false);
        if(!nodeDebug)
            errButtonPanel.add(undoButton);
        else
            errButtonPanel.remove(undoButton);
        errButtonPanel.setVisible(true);

        AcideDebugDatalogPanel._startDebug.setEnabled(true);
        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().setDebugging(false);

        this.setSize(new Dimension(350,200));
        this.setMinimumSize(new Dimension(350,150));
        setAlwaysOnTop(true);
        setWindowConfiguration();
        this.setVisible(true);
    }

    public void restoreDebug(){

        this.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2356"));

        if(AcideDebugHelper.isRootPredicate(this.predicate))
            validButton.setVisible(false);
        else
            validButton.setVisible(true);

        this.solutionsTable.setVisible(false);

        checkQuestionType();

        if(currentState>1)
            undoButton.setEnabled(true);
        else
            undoButton.setEnabled(false);

        if(currentState<statesCounter)
            redoButton.setEnabled(true);
        else
            redoButton.setEnabled(false);

        questionLabel.setVisible(true);
        questionPanel.setVisible(true);
        predicateTable.setVisible(true);
        numTuplesLabel.setVisible(true);
        buttonPanel.add(undoButton,4);
        buttonPanel.setVisible(true);
        errButtonPanel.setVisible(false);

        if(errors.size()>0) {
            LinkedList<String> status = DesDatabaseManager.getInstance().getDatalogNodeStates();

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
            if (found) {
                errorsLabel.setVisible(true);
                errorPanel.setVisible(true);
            }else
                errorsLabel.setVisible(false);
        }else
            errorsLabel.setVisible(false);

        AcideDebugDatalogPanel._startDebug.setEnabled(false);
        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().setDebugging(true);

        setAlwaysOnTop(true);
        //setAlwaysOnTop(false);
        Cursor.getDefaultCursor();

        setResizable(true);

        setWindowConfiguration();

        //Displays the window
        if(!AcideDebugDatalogPanel._startDebug.isEnabled())
            this.setVisible(true);
        else this.setVisible(false);
    }

    public void setInfo(String info){
        String text="<html>";
        text += info;
        text += "</html>";
        infoReceibedLabel.setText(text);
        setWindowConfiguration();
    }

    public String getQuestion(){
        return questionType;
    }

    public void setQuestion(String question){
        questionLabel.setText(question);
        setWindowConfiguration();
    }

    public void putPredicate(String predicate, JScrollPane predicateTable){
        try{
            if(this.predicate != null)
                predicatePanel.remove(this.predicateTable);
        }catch(Exception e){}

        setPredicate(predicate);
        setPredicateTable(predicateTable);
        setJTable(jTable);

        int width = (jTable.getColumnCount() - 1) * 150 + 30;
        int height = jTable.getRowHeight();

        if(jTable.getRowCount()>10)
            height*=10;
        else
            height*=jTable.getRowCount()+1;

        predicateTable.setPreferredSize(new Dimension(width,height));

        predicatePanel.add(this.predicateTable,BorderLayout.CENTER);

        // Adds the number of tuples label to the predicate panel
        numTuplesLabel.setBorder(BorderFactory.createEmptyBorder(12,3,5,0));
        numTuplesLabel.setText(AcideLanguageManager.getInstance().getLabels().getString("s2425")+": "
                +Integer.toString(jTable.getRowCount()-1));
        predicatePanel.add(numTuplesLabel,BorderLayout.SOUTH);
    }

    public String getPredicate(){return this.predicate;}

    public void setPredicate(String predicate){this.predicate = predicate;}

    public void setQuestionType(String question){this.questionType = question;}

    public String getCurrentQuestion(){return currentQuestion;}

    public void setCurrentQuestion(String currentQuestion){this.currentQuestion = currentQuestion;}

    public void setPredicateTable(JScrollPane predicateTable){this.predicateTable = predicateTable;}

    public void setSolutionsTable(JScrollPane solutionsTable){this.solutionsTable = solutionsTable;}

    public void setJTable(AcideDataBaseDataViewTable jTable){this.jTable = jTable;}

    public void setJTableSolutions(AcideDataBaseDataViewTable jTableSolutions){this.jTableSolutions=jTableSolutions;}

    public HashMap<String,String> getErrors(){
        return this.errors;
    }

    public void resetErrors(){
        errors = new HashMap<>();
        errorsLabel.setText("");
        errorsLabel.setVisible(true);
    }

    public void addError(String predicate, String error){

        errors.put(predicate.split("/")[0],error);

        LinkedList<String> status=DesDatabaseManager.getInstance().getDatalogNodeStates();

        String info ="<html>";
        info += "<br>" + AcideLanguageManager.getInstance().getLabels()
                .getString("s2365");

        if (!status.get(0).equals("$error")) {

            for (String node: status) {
                if (errors.containsKey(node))
                    info += "<br>" + " " + " " + " - " + errors.get(node);
            }
        }

        info += "<br>" + " " + " " + " - " + errors.get(predicate.split("/")[0]);
        info +="</html>";

        errorsLabel.setText(info);
        setWindowConfiguration();
    }

    public void removeError(){
        if(errors.containsKey(this.predicate.split("/")[0]))
            errors.remove(this.predicate.split("/")[0]);
    }

    public int getCurrentState(){return currentState;}

    public int getStatesCounter(){return statesCounter;}

    public void removeAllStates(){
        currentState=0;
        for(int i=1;i<=statesCounter;i++)
            new File("./debug/s"+i+".sds").delete();

        statesCounter=0;
    }

    public void removeLastStates(){
        for(int i=currentState+1;i<=statesCounter;i++)
            new File("./debug/s"+i+".sds").delete();
        statesCounter=currentState;
    }

    public void updateStatusHandlers(String action){
        if(action.equals("save")){
            currentState++;
            statesCounter++;
        }else if(action.equals("unsave")){
            currentState--;
            statesCounter--;
        }else if(action.equals("undo"))
            currentState--;
        else if(action.equals("notundo"))
            currentState++;
        else if(action.equals("redo"))
            currentState++;
        else if(action.equals("notredo"))
            currentState--;
    }

    private void checkQuestionType(){
        try{
            questionPanel.remove(this.solutionsTable);
        }catch(Exception e){}

        if(questionType.equals("subset") || questionType.equals("nonsubset"))
            setQuestionSubset();
        else if(questionType.equals("empty"))
            setQuestionEmpty();
        else if(questionType.equals("nonempty"))
            setQuestionNonEmpty();
        else
            setQuestionAll();
   }

   private void setQuestionSubset(){
       wrongTupleButton.setVisible(false);
       missingTupleButton.setVisible(false);

       validButton.setText(AcideLanguageManager.getInstance()
               .getLabels().getString("s62"));
       nonValidButton.setText(AcideLanguageManager.getInstance()
               .getLabels().getString("s63"));

       questionLabel.setText(AcideLanguageManager.getInstance().getLabels().getString("s2418") +
               this.predicate.split("/")[0] + "?");

       setSolutionsTable(AcideDebugHelper.getSolutionsTable(this.predicate));

       solutionsTable.setVisible(true);
       solutionsTable.setEnabled(false);
       jTableSolutions.setEnabled(false);
       jTableSolutions.clearSelection();

       int width = (jTableSolutions.getColumnCount() - 1) * 150 + 30;
       int height = jTableSolutions.getRowHeight();

       if(jTableSolutions.getRowCount()>10)
           height*=10;
       else
           height*=jTableSolutions.getRowCount()+1;

       solutionsTable.setPreferredSize(new Dimension(width,height));

       questionPanel.add(this.solutionsTable,BorderLayout.CENTER);
   }

   private void setQuestionEmpty(){
        wrongTupleButton.setVisible(false);
        missingTupleButton.setVisible(false);

        validButton.setText(AcideLanguageManager.getInstance().getLabels().getString("s62"));
        nonValidButton.setText(AcideLanguageManager.getInstance().getLabels().getString("s63"));

        questionLabel.setText(AcideLanguageManager.getInstance().getLabels().getString("s2414"));
   }

   private void setQuestionNonEmpty(){
       wrongTupleButton.setVisible(false);
       missingTupleButton.setVisible(false);

       validButton.setText(AcideLanguageManager.getInstance().getLabels().getString("s62"));
       nonValidButton.setText(AcideLanguageManager.getInstance().getLabels().getString("s63"));

       questionLabel.setText(AcideLanguageManager.getInstance().getLabels().getString("s2415"));
   }

    private  void setQuestionAll(){

        wrongTupleButton.setVisible(true);
        missingTupleButton.setVisible(true);

        validButton.setText(AcideLanguageManager.getInstance().getLabels().getString("s2348"));
        nonValidButton.setText(AcideLanguageManager.getInstance().getLabels().getString("s2349"));

    }

    class ValidNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            removeError();
            if(!questionType.equals("nonsubset"))
                AcideDebugHelper.performDatalogDebug("valid");
            else
                AcideDebugHelper.performDatalogDebug("nonvalid");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugDatalogDebugWindow.getInstance().showWindow());
        }
    }

    class NonValidNodeAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            removeError();
            if(questionType.equals("nonsubset"))
                AcideDebugHelper.performDatalogDebug("valid");
            else
                AcideDebugHelper.performDatalogDebug("nonvalid");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugDatalogDebugWindow.getInstance().showWindow());
        }
    }

    class MissingTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            jTable.changeSelection(jTable.getRowCount()-1, 1, false, false);
            jTable.changeSelection(jTable.getRowCount()-1, jTable.getColumnCount()-1, true, true);
            String data = AcideDebugHelper.getDataFromSelectedTuple(jTable);
            // Check data is not null
            String trimmedData =  data.replace("'", "").replace(",", "");
            if(trimmedData.isEmpty()){
                JOptionPane.showMessageDialog(null, AcideLanguageManager.getInstance()
                        .getLabels().getString("s2379"));
            } else {
                addError(AcideDebugDatalogDebugWindow.getInstance()
                        .getPredicate().split("/")[0],AcideLanguageManager.getInstance()
                        .getLabels().getString("s2350") + " (" + data + ") " + AcideLanguageManager.getInstance()
                        .getLabels().getString("s2412") +" "+ AcideDebugDatalogDebugWindow.getInstance()
                        .getPredicate().split("/")[0]);
                AcideDebugHelper.performDatalogDebug("missing(" +
                        AcideDebugDatalogDebugWindow.getInstance().getPredicate().split("/")[0] + "(" + data + "))");
                if (AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging())
                    SwingUtilities.invokeLater(() -> AcideDebugDatalogDebugWindow.getInstance().showWindow());
            }
        }
    }

    class WrongTupleAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            addError(AcideDebugDatalogDebugWindow.getInstance().getPredicate().split("/")[0],
                    AcideLanguageManager.getInstance().getLabels().getString("s2351") +
                            " (" + AcideDebugHelper.getDataFromSelectedTuple(jTable)
                    + ") "+ AcideLanguageManager.getInstance().getLabels().getString("s2412") +" "+
                    AcideDebugDatalogDebugWindow.getInstance().getPredicate().split("/")[0]);
            AcideDebugHelper.performDatalogDebug("wrong(" +
                    AcideDebugDatalogDebugWindow.getInstance().getPredicate().split("/")[0] + "(" + AcideDebugHelper
                    .getDataFromSelectedTuple(jTable) +"))");
            if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugDatalogDebugWindow.getInstance().showWindow());
        }
    }

    class UndoAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e){
            AcideDebugHelper.restoreDatalogDebugState("undo",false);

            if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugDatalogDebugWindow.getInstance().showWindow());
            else
                SwingUtilities.invokeLater(() -> AcideDebugDatalogDebugWindow.getInstance().restoreDebug());
        }
    }

    class RedoAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e){
            AcideDebugHelper.restoreDatalogDebugState("redo", false);

            if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging())
                SwingUtilities.invokeLater(() -> AcideDebugDatalogDebugWindow.getInstance().showWindow());
        }
    }

    class AbortAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            closeWindow();
        }
    }

    /**
     * ACIDE - A Configurable IDE debug window escape key action.
     *
     * @version 0.19
     * @see AbstractAction
     */
    class CloseAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            // Closes the window
            closeWindow();
        }
    }

    class  StatsAction extends  AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> setAlwaysOnTop(false));
            LinkedList<String> info = DesDatabaseManager.getInstance().getDatalogDebugStats();
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

    class EditIntensionalAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> setAlwaysOnTop(false));
            String rules = DesDatabaseManager.getInstance().getRules(predicate);
            new AcideEnterTextWindow(rules, AcideLanguageManager.getInstance().getLabels()
                    .getString("s2037"), true);
            SwingUtilities.invokeLater(() -> stopDebug(predicate,false));
        }
    }

    class EditExtensionalAction extends AbstractAction{

        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            AcideDebugHelper.showPredicate(predicate);
        }
    }

    /**
     * Sets the text of the components assigned to the ACIDE - A Configurable
     * IDE debug datalog debug Window.
     */
    public void setComponentsText(){
        this.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2356"));
        validButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2348"));
        nonValidButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2349"));
        missingTupleButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2350"));
        wrongTupleButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2351"));
        abortButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2352"));
        okButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2353"));
        showStatsButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2354"));
        editExtensionalButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2411"));
        editIntensionalButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2413"));
    }
}
