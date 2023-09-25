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
package acide.gui.debugPanel.utils;

import acide.gui.databasePanel.AcideDataBasePanel;
import acide.gui.databasePanel.dataView.AcideDataBaseDataViewTable;
import acide.gui.databasePanel.dataView.AcideDatabaseDataView;
import acide.gui.databasePanel.dataView.menuBar.editMenu.gui.AcideDataViewReplaceWindow;
import acide.gui.databasePanel.utils.AcideTree;
import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugCanvas.tasks.AcideDebugCanvasParseTask;
import acide.gui.debugPanel.debugDatalogPanel.AcideDebugDatalogDebugWindow;
import acide.gui.debugPanel.debugDatalogPanel.AcideDebugDatalogPanel;
import acide.gui.debugPanel.debugDatalogPanel.debugDatalogConfiguration.AcideDebugDatalogConfiguration;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLPanel;
import acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration.AcideDebugSQLConfiguration;
import acide.gui.graphCanvas.AcideGraphCanvas;
import acide.gui.graphCanvas.tasks.AcideGraphCanvasParseTask;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;
import acide.main.AcideMain;
import acide.process.console.AcideDatabaseManager;
import acide.process.console.DesDatabaseManager;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class AcideDebugHelper {

    public static void updateSQLCanvas(AcideDebugCanvas canvas){
        AcideDebugPanelHighLighter highLighter = AcideMainWindow
                .getInstance().getDebugPanel().getDebugSQLPanel()
                .getHighLighter();
        canvas.repaint();
        try {
            if (canvas.getSelectedNode() == null)
                canvas.setSelectedNode(canvas.getRootNode());
            String selected = canvas.getSelectedNode().getLabel();

            // Updates the highlights
            highLighter.resetLines();
            highLighter.unHighLight();
            if(canvas.getSelectedNode().getNodeColor().equals(Color.GRAY))
                highLighter.highLight(selected);
        }catch (Exception e){
            // TODO
        }

        if (AcideMainWindow.getInstance().getDebugPanel()
                .getDebugSQLPanel().getShowSQLMenuItem().isSelected()) {
            AcideMainWindow
                    .getInstance()
                    .getDebugPanel()
                    .setCursor(
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Selects the node on the database panel tree
            String query = canvas.getSelectedNode().getLabel();
            query = query.substring(0, query.lastIndexOf("/"));
            selectSQLTEXT(query);
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }

    public static void updateDatalogCanvas(AcideDebugCanvas canvas) {
        AcideDebugPanelHighLighter highLighter = AcideMainWindow
                .getInstance().getDebugPanel().getDebugDatalogPanel()
                .getHighLighter();
        canvas.repaint();
        try {
            if (canvas.getSelectedNode() == null)
                canvas.setSelectedNode(canvas.getRootNode());

            String selected = canvas.getSelectedNode().getLabel();

            // Updates the highlights
            highLighter.resetLines();
            highLighter.unHighLight();
            if (canvas.getSelectedNode().getNodeColor().equals(Color.GRAY)
            && AcideMainWindow.getInstance().getDebugPanel()
                    .getDebugDatalogPanel().getShowRulesMenuItem().isSelected()) {
                highLighter.highLight(selected);

                //Update carret Position
                AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                        .getHighLighter().updateCarretPosition();
            }
        } catch (Exception e) {
            // TODO
        }

        if (AcideMainWindow.getInstance().getDebugPanel()
                .getDebugDatalogPanel().getShowRulesMenuItem().isSelected()) {

            AcideMainWindow.getInstance().getDebugPanel().setCursor(
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Gets the selected node of the canvas
            String selected = AcideMainWindow.getInstance().getDebugPanel()
                    .getDebugDatalogPanel().getCanvas().getSelectedNode().getLabel();

            // Unhighlight the previous highlights
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                    .getHighLighter().unHighLight();

            // Highlights the selected predicate in datalog program
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                    .getHighLighter().highLight(selected);

            //Update carret Position
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                    .getHighLighter().updateCarretPosition();

            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }

    public static void updateCanvasTrace(AcideDebugCanvas canvas){
        AcideDebugPanelHighLighter highLighter = AcideMainWindow
                .getInstance().getDebugPanel().getTraceSQLPanel()
                .getHighLighter();
        canvas.repaint();
        try {
            String selected = canvas.getSelectedNode().getLabel();

            // Updates the highlights
            highLighter.resetLines();
            highLighter.unHighLight();
            highLighter.highLight(selected);
        }catch (Exception e){
            // TODO
        }
        if (AcideMainWindow.getInstance().getDebugPanel()
                .getTraceSQLPanel().getShowSQLMenuItem().isSelected()) {
            AcideMainWindow
                    .getInstance()
                    .getDebugPanel()
                    .setCursor(
                            Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Selects the node on the database panel tree
            String query = canvas.getSelectedNode().getLabel();
            query = query.substring(0, query.lastIndexOf("/"));
            selectSQLTEXT(query);
            AcideMainWindow.getInstance().getDebugPanel()
                    .setCursor(Cursor.getDefaultCursor());
        }
    }

    public static String obtainSQLResult(final Thread t, String result, LinkedList<String> l, String consult){
        t.start();
        // Gets the RDG output for the query
        try{

            l = DesDatabaseManager.getInstance().executeCommand(
                    "/tapi /rdg " + consult.substring(0, consult.lastIndexOf("(")>0?consult.lastIndexOf("("):consult.length()));
        }
        catch(Exception ie){
            ie.printStackTrace();
        }
        StringBuilder resultBuilder = new StringBuilder();
        for (String s : l) {
            resultBuilder.append(s).append("\n");
        }
        result = resultBuilder.toString();
        return result;
    }

    public static AcideTree getRoot(String query){
        if(query.indexOf('/')>=0)
            query = query.substring(0, query.lastIndexOf("/"));
        // gets the tree of the database panel
        AcideTree tree = AcideMainWindow.getInstance()
                .getDataBasePanel().getTree();
        // searches for the root node on the tree
        TreePath path = TreeUtils
                .searchForNode((TreeNode) tree.getModel()
                        .getRoot(), query);
        // selects the node
        if (path != null)
            tree.setSelectionPath(path);
        return tree;
    }

    public static void selectSQLTEXT(String query) {
        AcideDataBasePanel dataBasePanel = AcideMainWindow.getInstance().getDataBasePanel();
        AcideTree tree = dataBasePanel.getTree();
        // Searches for the table/view node on the database tree
        TreePath path = TreeUtils.searchForNodeV2((TreeNode) tree.getModel()
                .getRoot(), query, tree, new TreePath((TreeNode) tree
                .getModel().getRoot()), true);
        if (path != null) {
            TreeNode node = (TreeNode) path.getLastPathComponent();
            tree.setSelectionPath(path);
            // Searches for the child node SQL Text on the children of the node
            // (only for views)
            Enumeration<TreeNode> e = node.children();
            while (e.hasMoreElements()) {
                TreeNode node2 = e.nextElement();
                if (node2.toString().equals(
                        AcideLanguageManager.getInstance().getLabels()
                                .getString("s2036"))) {
                    path = path.pathByAddingChild(node2);
                    // Adds the child node of the SQL Text node with the sql
                    // definition of the view
                    if (!node2.isLeaf()) {
                        tree.setSelectionPath(path);
                        path = path.pathByAddingChild(node2.getChildAt(0));
                    }
                }
            }
            tree.scrollPathToVisible(path);
            tree.setSelectionPath(path);
        }
    }

    public static void showView(String view){
        String db = DesDatabaseManager.getInstance().currentDB();
        AcideDatabaseDataView panelDv = AcideMainWindow.getInstance().getDataBasePanel().getDataView(db, view);
        LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, view);
        if(!info.isEmpty())
            panelDv.build(info);
        panelDv.setAlwaysOnTop(true);
        //panelDv.setAlwaysOnTop(false);
        panelDv.setVisible(true);
    }

    public static void showPredicate(String predicate){
        String db = DesDatabaseManager.getInstance().currentDB();
        AcideDatabaseDataView panelDv = AcideMainWindow.getInstance().getDataBasePanel().getDataView(db, predicate);
        LinkedList<String> info = AcideDatabaseManager.getInstance().getAllTuples(predicate);
        if (!info.isEmpty())
            panelDv.build(info);
        panelDv.setAlwaysOnTop(true);
        //panelDv.setAlwaysOnTop(false);
        panelDv.setVisible(true);
    }

    public static void resetColorViewNodes(){
        boolean isDebugging = AcideMainWindow.getInstance().getDebugPanel()
                .getDebugSQLPanel().isDebugging();
        if(!isDebugging) {
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            for (Node n : debugCanvas.get_graph().get_nodes()) {
                debugCanvas.setSelectedNode(n);
                debugCanvas.setColorSelectedNode(Color.GRAY);
            }
            updateSQLCanvas(debugCanvas);
        }
    }

    public static void resetColorPredicateNodes(){
        boolean isDebugging = AcideMainWindow.getInstance().getDebugPanel()
                .getDebugDatalogPanel().isDebugging();
        if(!isDebugging){
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugDatalogPanel().getCanvas();
            for(Node n: debugCanvas.get_graph().get_nodes()){
                debugCanvas.setSelectedNode(n);
                debugCanvas.setColorSelectedNode(Color.GRAY);
            }
            updateDatalogCanvas(debugCanvas);
        }
    }

    public static void setTrustedTableNodes(){
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging()) {
            String db = DesDatabaseManager.getInstance().currentDB();
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            Node rootNode=debugCanvas.getRootNode();
            if(rootNode!=null){
                for (Node n : debugCanvas.get_graph().get_nodes()) {
                    if (DesDatabaseManager.getInstance().isTable(db, n.getLabel().split("/")[0])) {
                        debugCanvas.setSelectedNode(n);
                        debugCanvas.setColorSelectedNode(Color.GREEN);
                    }
                }
                debugCanvas.setSelectedNode(rootNode);
                updateSQLCanvas(debugCanvas);
            }
        }
    }

    public static void setTrustedExtensionalNodes(){
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging()){
            String db = DesDatabaseManager.getInstance().currentDB();
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugDatalogPanel().getCanvas();
            Node rootNode = debugCanvas.getRootNode();
            if(rootNode!=null){
                for(Node n : debugCanvas.get_graph().get_nodes()){
                    if(DesDatabaseManager.getInstance().isExtensional(db,n.getLabel().split("/")[0])){
                        debugCanvas.setSelectedNode(n);
                        debugCanvas.setColorSelectedNode(Color.GREEN);
                    }
                }
                debugCanvas.setSelectedNode(rootNode);
                updateDatalogCanvas(debugCanvas);
            }
        }
    }

    public static void resetColorTableNodes(){
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging()) {
            String db = DesDatabaseManager.getInstance().currentDB();
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            Node rootNode=debugCanvas.getRootNode();
            if(rootNode!=null){
                for (Node n : debugCanvas.get_graph().get_nodes()) {
                    if (DesDatabaseManager.getInstance().isTable(db, n.getLabel().split("/")[0])) {
                        debugCanvas.setSelectedNode(n);
                        debugCanvas.setColorSelectedNode(Color.GRAY);
                    }
                }
                debugCanvas.setSelectedNode(rootNode);
                updateSQLCanvas(debugCanvas);
            }
        }
    }

    public static void resetColorExtensionalNodes(){
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging()){
            String db = DesDatabaseManager.getInstance().currentDB();
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugDatalogPanel().getCanvas();
            Node rootNode = debugCanvas.getRootNode();
            if(rootNode!=null){
                for(Node n : debugCanvas.get_graph().get_nodes()){
                    if(DesDatabaseManager.getInstance().isExtensional(db,n.getLabel().split("/")[0])){
                        debugCanvas.setSelectedNode(n);
                        debugCanvas.setColorSelectedNode(Color.GRAY);
                    }
                }
                debugCanvas.setSelectedNode(rootNode);
                updateDatalogCanvas(debugCanvas);
            }
        }
    }

    public static void startSQLDebug(){
        try {
            // Gets the canvas
            AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            if (isSQLSessionDebugging()){
                LinkedList<String> currentQuestion = DesDatabaseManager.getInstance().debugCurrentSQLQuestion();
                AcideDebugSQLDebugWindow.getInstance().setCurrentQuestion(currentQuestion.getFirst());
                String nextView = parseSQLCurrentQuestion(currentQuestion);
                updateViewHighlight(nextView);
                AcideDebugSQLDebugWindow.getInstance().putView(nextView, getViewTable(nextView));
            }else {
                AcideDebugSQLDebugWindow.getInstance().setVisible(false);
                // Puts the wait cursor
                AcideDataViewReplaceWindow.getInstance().setCursor(
                        Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                paintTrustedTables(canvas);
                if (canvas.getSelectedNode() == null || !AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging())
                    canvas.setSelectedNode(canvas.getRootNode());
                String view = getSelectedViewName();

                AcideDebugSQLPanel.startDebug.setEnabled(false);

                AcideDebugSQLDebugWindow.getInstance().setQuestionType("all");
                AcideDebugSQLDebugWindow.getInstance().resetErrors();
                AcideDebugSQLDebugWindow.getInstance().removeAllStates();
                AcideDebugSQLDebugWindow.getInstance().putView(view, getViewTable(view));
            }
            updateSQLDebugWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
            // TODO
        }
    }

    public static void startDatalogDebug(){
        try {
            // Gets the canvas
            AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugDatalogPanel().getCanvas();
            if (isDatalogSessionDebugging()){
                LinkedList<String> currentQuestion = DesDatabaseManager.getInstance().debugCurrentDatalogQuestion();
                AcideDebugDatalogDebugWindow.getInstance().setCurrentQuestion(currentQuestion.getFirst());
                String nextPredicate = parseDatalogCurrentQuestion(currentQuestion);
                updatePredicateHighlight(nextPredicate);
                AcideDebugDatalogDebugWindow.getInstance().putPredicate(nextPredicate, getPredicateTable(nextPredicate));
            }else {
                AcideDebugDatalogDebugWindow.getInstance().setVisible(false);
                // Puts the wait cursor
                AcideDataViewReplaceWindow.getInstance().setCursor(
                        Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                paintTrustedExtensionalPredicates(canvas);

                if (canvas.getSelectedNode() == null || !AcideMainWindow.getInstance().getDebugPanel()
                        .getDebugDatalogPanel().isDebugging())
                    canvas.setSelectedNode(canvas.getRootNode());
                String predicate = getSelectedPredicateName();
                updatePredicateHighlight(predicate);
                AcideDebugDatalogPanel._startDebug.setEnabled(false);

                AcideDebugDatalogDebugWindow.getInstance().setQuestionType("all");
                AcideDebugDatalogDebugWindow.getInstance().resetErrors();
                AcideDebugDatalogDebugWindow.getInstance().removeAllStates();
                AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().saveInaccesibleInfo();
                AcideDebugDatalogDebugWindow.getInstance().putPredicate(predicate, getPredicateTable(predicate));
            }
            updateDatalogDebugWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
            // TODO
        }
    }

    /**
     * Parse debug answer updating graph and check if debug must continue
     *
     * @param info
     * @return continueDebugging if continues debugging
     */
    private static String updateDebugSQLState(LinkedList<String> info){
        // Puts the wait cursor
        AcideDataViewReplaceWindow.getInstance().setCursor(
                Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if(info.size() > 0 && info.get(0).equals("$error")){
            String debugError = "debugError" + getMessageError(info);
            return debugError;
        }else {
            String errorView = "";
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugSQLPanel().getCanvas();
            List<Node> nodes = debugCanvas.get_graph().get_nodes();
            if (info.size() % 2 == 0) {
                for (int i = 0; i < info.size(); i++) {
                    String view = info.get(i);
                    i++;
                    String state = info.get(i);

                    for (Node n : nodes) {
                        if (n.getLabel().split("/")[0].equals(view)) {
                            debugCanvas.setSelectedNode(n);
                            if (state.equals("nonvalid"))
                                debugCanvas.setColorSelectedNode(Color.ORANGE);
                            else if (state.equals("valid"))
                                debugCanvas.setColorSelectedNode(Color.GREEN);
                            else{
                                debugCanvas.setColorSelectedNode(Color.RED);
                                errorView = view;
                            }
                        }
                    }
                }
            }

            AcideDebugHelper.updateSQLCanvas(debugCanvas);

            return errorView;
        }
    }

    /**
     * Parse debug answer updating graph and check if debug must continue
     *
     * @param info
     * @return debug error or wrong predicate
     */
    private static String updateDebugDatalogState(LinkedList<String> info){
        //Puts the default cursor
        AcideDataViewReplaceWindow.getInstance().setCursor(
                Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        if(info.size() > 0 && info.get(0).equals("$error")){
            String debugError="debugError"+getMessageError(info);
            return debugError;
        }else{
            String errorPredicate = "";
            AcideDebugCanvas debugCanvas = AcideMainWindow.getInstance()
                    .getDebugPanel().getDebugDatalogPanel().getCanvas();
            List<Node> nodes = debugCanvas.get_graph().get_nodes();
            if(info.size() % 2 == 0){
                for(int i = 0; i< info.size(); i++){
                    String predicate = info.get(i);
                    i++;
                    String state = info.get(i);

                    for(Node n: nodes){
                        if(n.getLabel().split("/")[0].equals(predicate)){
                            debugCanvas.setSelectedNode(n);
                            if(state.equals("nonvalid"))
                                debugCanvas.setColorSelectedNode(Color.ORANGE);
                            else if(state.equals("valid"))
                                debugCanvas.setColorSelectedNode(Color.GREEN);
                            else{
                                debugCanvas.setColorSelectedNode(Color.RED);
                                errorPredicate = n.getLabel();
                            }
                        }
                    }
                }
            }

            updateDatalogCanvas(debugCanvas);

            return errorPredicate;
        }
    }

    /**
     * Parse current view debugging
     *
     * @param currentQuestion
     * @return desired view
     */
    public static String parseSQLCurrentQuestion(LinkedList<String> currentQuestion){
        String str = currentQuestion.getFirst();
        if(str.startsWith("subset(")) {
            AcideDebugSQLDebugWindow.getInstance().setQuestionType("subset");
            str = str.substring(str.lastIndexOf(",") +1, str.lastIndexOf(")"));
        }else{
            if(str.startsWith("in("))
                AcideDebugSQLDebugWindow.getInstance().setQuestionType("in");
            else
                AcideDebugSQLDebugWindow.getInstance().setQuestionType("all");
            while (str.contains("(")) {
                if (str.contains(")"))
                    str = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
                else
                    str = str.split("\\(")[0];
            }
        }
        return str;
    }

    public static String parseDatalogCurrentQuestion(LinkedList<String> currentQuestion) {
        String str = currentQuestion.getFirst();

        if (str.startsWith("subset(")) {
            AcideDebugDatalogDebugWindow.getInstance().setQuestionType("subset");
            str = str.substring(str.lastIndexOf(",") + 1, str.lastIndexOf(")"));
        }else if(str.startsWith("nonsubset(")){
            AcideDebugDatalogDebugWindow.getInstance().setQuestionType("nonsubset");
            str = str.substring(str.lastIndexOf(",") + 1, str.lastIndexOf(")"));
        }else{
            if(str.startsWith("all("))
                AcideDebugDatalogDebugWindow.getInstance().setQuestionType("all");
            else if(str.startsWith("empty("))
                AcideDebugDatalogDebugWindow.getInstance().setQuestionType("empty");
            else
                AcideDebugDatalogDebugWindow.getInstance().setQuestionType("nonempty");

            str = str.substring(str.indexOf("(")+1,str.indexOf(")"));
        }

        return str;
    }

    public static JScrollPane getViewTable(String view) {
        String db = DesDatabaseManager.getInstance().currentDB();
        AcideDatabaseDataView viewWindow = new AcideDatabaseDataView(db, view);
        viewWindow.setVisible(false);
        viewWindow.setLocation(AcideDebugSQLDebugWindow.getInstance().getLocation());
        LinkedList<String> info = AcideDatabaseManager.getInstance().getSelectAll(db, view);
        viewWindow.build(info);
        if(!info.isEmpty()) {
            if(info.getLast().equals("$"))
                info.removeLast();
            if(!isLastRowEmpty(viewWindow.getTable())){
                if(!db.contains("$des"))
                    info.addFirst("");
                for (int i = 0; i < viewWindow.getTable().getColumnCount(); i++) {
                    if (i == 0)
                        info.add("$");
                    else
                        info.add("");
                }
            }
            viewWindow.build(info);
        }
        viewWindow.setState(viewWindow.NORMAL);
        JScrollPane scrollPane = viewWindow.getSrollPane();
        Dimension d = new Dimension(viewWindow.getTable().getWidth(),viewWindow.getTable().getHeight());
        scrollPane.setSize(d);
        AcideDebugSQLDebugWindow.getInstance().setJTable(viewWindow.get_jTable());
        viewWindow.closeWindow();
        return scrollPane;
    }

    public static JScrollPane getPredicateTable(String predicate){
        String db = DesDatabaseManager.getInstance().currentDB();
        AcideDatabaseDataView predicateWindow = new AcideDatabaseDataView(db, predicate);
        predicateWindow.setVisible(false);
        predicateWindow.setLocation(AcideDebugDatalogDebugWindow.getInstance().getLocation());
        LinkedList<String> info = DesDatabaseManager.getInstance().getAllTuples(predicate);
        predicateWindow.build(info);
        if(!info.isEmpty()){
            if(info.getLast().equals("$"))
                info.removeLast();
            if(!isLastRowEmpty(predicateWindow.getTable())){
                if(!db.contains("$des"))
                    info.addFirst("");
                for(int i =0; i < predicateWindow.getTable().getColumnCount();i++){
                    if(i == 0)
                        info.add("$");
                    else
                        info.add("");
                }
            }
            predicateWindow.build(info);
        }
        predicateWindow.setState(predicateWindow.NORMAL);
        JScrollPane scrollPane = predicateWindow.getSrollPane();
        Dimension d = new Dimension(predicateWindow.getTable().getWidth(),predicateWindow.getTable().getHeight());
        scrollPane.setSize(d);
        AcideDebugDatalogDebugWindow.getInstance().setJTable(predicateWindow.get_jTable());
        predicateWindow.closeWindow();
        return scrollPane;
    }

    public static JScrollPane getSolutionsTable(String predicate){
        String db = DesDatabaseManager.getInstance().currentDB();
        AcideDatabaseDataView solutionsWindow = new AcideDatabaseDataView(db, predicate);
        solutionsWindow.setVisible(false);
        solutionsWindow.setLocation(AcideDebugDatalogDebugWindow.getInstance().getLocation());
        LinkedList<String> info = DesDatabaseManager.getInstance().getSolutions(predicate);
        solutionsWindow.build(info);
        if(!info.isEmpty()){
            if(info.getLast().equals("$"))
                info.removeLast();
            if(!isLastRowEmpty(solutionsWindow.getTable())){
                if(!db.contains("$des"))
                    info.addFirst("");
                for(int i =0; i < solutionsWindow.getTable().getColumnCount();i++){
                    if(i == 0)
                        info.add("$");
                    else
                        info.add("");
                }
            }
            solutionsWindow.build(info);
        }
        solutionsWindow.setState(solutionsWindow.NORMAL);
        JScrollPane scrollPane = solutionsWindow.getSrollPane();
        Dimension d = new Dimension(solutionsWindow.getTable().getWidth(),solutionsWindow.getTable().getHeight());
        scrollPane.setSize(d);
        AcideDebugDatalogDebugWindow.getInstance().setJTableSolutions(solutionsWindow.get_jTable());
        solutionsWindow.closeWindow();
        return scrollPane;
    }

    public static void performSQLDebug(String action){
        AcideDebugSQLDebugWindow.getInstance().setVisible(false);
        LinkedList<String> consoleInfo;
        updateViewHighlight(AcideDebugSQLDebugWindow.getInstance().getView());
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().isDebugging()){
            AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebugging(true);
            consoleInfo = DesDatabaseManager.getInstance().startSQLDebug(getSelectedViewName(),
                    AcideDebugSQLConfiguration.getInstance().getDebugConfiguration(), action);

        } else {
            if(AcideDebugSQLDebugWindow.getInstance().getSubsetView() != null){
                AcideDebugSQLDebugWindow.getInstance().getSubsetView().closeWindow();
                AcideDebugSQLDebugWindow.getInstance().setSubsetView();
            }
            consoleInfo = DesDatabaseManager.getInstance().debugCurrentSQLAnswer(
                    AcideDebugSQLDebugWindow.getInstance().getCurrentQuestion(), action);
        }

        AcideDebugSQLDebugWindow.getInstance().removeLastStates();
        AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers("save");
        LinkedList<String> result=DesDatabaseManager.getInstance().saveDebugState(AcideDebugSQLDebugWindow
                .getInstance().getCurrentState());
        if(!result.isEmpty())
            AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers("unsave");

        nextSQLMovement(consoleInfo, false);
    }

    public static void performDatalogDebug(String action){
        AcideDebugDatalogDebugWindow.getInstance().setVisible(false);
        LinkedList<String> consoleInfo;
        updatePredicateHighlight(AcideDebugDatalogDebugWindow.getInstance().getPredicate());
        if(!AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().isDebugging()){
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().setDebugging(true);
            consoleInfo = DesDatabaseManager.getInstance().startDatalogDebug(getSelectedPredicateName(),
                    AcideDebugDatalogConfiguration.getInstance().getDebugConfiguration(),action);

        }else {
            consoleInfo = DesDatabaseManager.getInstance().debugCurrentDatalogAnswer(
                    AcideDebugDatalogDebugWindow.getInstance().getCurrentQuestion(),action);
        }

        AcideDebugDatalogDebugWindow.getInstance().removeLastStates();
        AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers("save");
        LinkedList<String> result=DesDatabaseManager.getInstance().saveDebugState(AcideDebugDatalogDebugWindow
        .getInstance().getCurrentState());
        if(!result.isEmpty())
            AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers("unsave");

        nextDatalogMovement(consoleInfo,false);
    }

    public static void restoreSQLDebugState(String action,boolean nodeDebug){
        AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers(action);
        LinkedList<String> result=DesDatabaseManager.getInstance().restoreDebugState(AcideDebugSQLDebugWindow
                .getInstance().getCurrentState());
        if(result.isEmpty()) {
            if(action.equals("undo"))
                AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel()
                        .getCanvas().getSelectedNode().setNodeColor(Color.GRAY);
            LinkedList<String> consoleInfo = DesDatabaseManager.getInstance().getSQLNodeStates();
            nextSQLMovement(consoleInfo,nodeDebug);
        } else
            AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers("not" + action);
    }

    public static void restoreDatalogDebugState(String action,boolean nodeDebug){
        AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers(action);
        LinkedList<String> result=DesDatabaseManager.getInstance().restoreDebugState(AcideDebugDatalogDebugWindow
                .getInstance().getCurrentState());
        if(result.isEmpty()){
            if(action.equals("undo"))
                AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                .getCanvas().getSelectedNode().setNodeColor(Color.GRAY);
            LinkedList<String> consoleInfo = DesDatabaseManager.getInstance().getDatalogNodeStates();
            nextDatalogMovement(consoleInfo,nodeDebug);
        }else
            AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers("not"+action);
    }

    public static void startViewNodeDebug(String node, String action){
        // Puts the wait cursor
        AcideDataViewReplaceWindow.getInstance().setCursor(
                Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        AcideDebugSQLDebugWindow.getInstance().setQuestionType("all");
        if(!action.startsWith("missing") && !action.startsWith("wrong"))
            AcideDebugSQLDebugWindow.getInstance().resetErrors();
        AcideDebugSQLDebugWindow.getInstance().removeAllStates();
        LinkedList<String> consoleInfo = DesDatabaseManager.getInstance().
                startSQLDebug(node, AcideDebugSQLConfiguration.getInstance().getDebugConfiguration(), action);
        AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers("save");
        LinkedList<String> result=DesDatabaseManager.getInstance().saveDebugState(AcideDebugSQLDebugWindow
                .getInstance().getCurrentState());
        if(!result.isEmpty())
            AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers("unsave");
        checkSQLError(updateDebugSQLState(consoleInfo));
        nextSQLMovement(consoleInfo, true);
    }

    public static void startPredicateNodeDebug(String node, String action){
        // Puts the wait cursor
        AcideDataViewReplaceWindow.getInstance().setCursor(
                Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        AcideDebugDatalogDebugWindow.getInstance().setQuestionType("all");
        if(!action.startsWith("missing") && !action.startsWith("wrong"))
            AcideDebugDatalogDebugWindow.getInstance().resetErrors();
        AcideDebugDatalogDebugWindow.getInstance().removeAllStates();
        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().saveInaccesibleInfo();
        LinkedList<String> consoleInfo = DesDatabaseManager.getInstance()
                .startDatalogDebug(node,AcideDebugDatalogConfiguration.getInstance().getDebugConfiguration(),action);
        AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers("save");
        LinkedList<String> result = DesDatabaseManager.getInstance().saveDebugState(AcideDebugDatalogDebugWindow
        .getInstance().getCurrentState());
        if(!result.isEmpty())
            AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers("unsave");
        checkDatalogError(updateDebugDatalogState(consoleInfo));
        nextDatalogMovement(consoleInfo,true);
    }

    public static void performViewNodeDebug(String node, String action){
        if(isRootView(node)){
            refreshDebugSQLGraph();
            performSQLDebug("abort");
            startViewNodeDebug(node, action);
        } else {
            LinkedList<String> consoleInfo = DesDatabaseManager.getInstance().
                    setViewNodeState(node, action);
            AcideDebugSQLDebugWindow.getInstance().removeLastStates();
            AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers("save");
            LinkedList<String> result=DesDatabaseManager.getInstance().saveDebugState(AcideDebugSQLDebugWindow
                    .getInstance().getCurrentState());
            if(!result.isEmpty())
                AcideDebugSQLDebugWindow.getInstance().updateStatusHandlers("unsave");
            checkSQLError(updateDebugSQLState(consoleInfo));
            nextSQLMovement(consoleInfo, true);
        }
    }

    public static void performPredicateNodeDebug(String node, String action){
        if(isRootPredicate(node)){
            refreshDebugDatalogGraph();
            performDatalogDebug("abort");
            startPredicateNodeDebug(node,action);
        }else{
            LinkedList<String> consoleInfo = DesDatabaseManager.getInstance().setPredicateNodeState(node,action);
            AcideDebugDatalogDebugWindow.getInstance().removeLastStates();
            AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers("save");
            LinkedList<String> result=DesDatabaseManager.getInstance().saveDebugState(AcideDebugDatalogDebugWindow
                    .getInstance().getCurrentState());
            if(!result.isEmpty())
                AcideDebugDatalogDebugWindow.getInstance().updateStatusHandlers("unsave");
            checkDatalogError(updateDebugDatalogState(consoleInfo));
            nextDatalogMovement(consoleInfo,true);
        }
    }

    private static void checkSQLError(String info){
        if (info.startsWith("debugError")) {
            info = info.replace("debugError", "");
            JOptionPane.showMessageDialog(null, info);
            AcideDebugSQLDebugWindow.getInstance().closeWindow();
            AcideDebugSQLPanel.startDebug.setEnabled(true);
            AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebugging(false);
        }
    }

    private static void checkDatalogError(String info){
        if(info.startsWith("debugError")){
            info = info.replace("debugError","");
            JOptionPane.showMessageDialog(null,info);
            AcideDebugDatalogDebugWindow.getInstance().closeWindow();
            AcideDebugDatalogPanel._startDebug.setEnabled(true);
            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().setDebugging(false);
        }
    }

    private static void nextSQLMovement(LinkedList<String> info, boolean nodeDebug){
        // Info empty is abort
        if(!info.isEmpty()) {
            String errorView = updateDebugSQLState(info);

            if (errorView.equals("")) {
                LinkedList<String> currentQuestion = DesDatabaseManager.getInstance().debugCurrentSQLQuestion();
                AcideDebugSQLDebugWindow.getInstance().setCurrentQuestion(currentQuestion.getFirst());
                String nextView = parseSQLCurrentQuestion(currentQuestion);
                updateViewHighlight(nextView);
                AcideDebugSQLDebugWindow.getInstance().putView(nextView, getViewTable(nextView));
                AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel()
                        .getCanvas().getSelectedNode().setNodeColor(Color.YELLOW);
                if(!nodeDebug)
                    updateSQLDebugWindow();
            } else if (errorView.startsWith("debugError")) {
                errorView = errorView.replace("debugError","");
                JOptionPane.showMessageDialog(null, errorView);
                AcideDebugSQLDebugWindow.getInstance().closeWindow();
                AcideDebugSQLPanel.startDebug.setEnabled(true);
                AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().setDebugging(false);
            } else {
                AcideDebugSQLDebugWindow.getInstance().stopDebug(errorView,nodeDebug);
            }
        }
    }

    private static void nextDatalogMovement(LinkedList<String> info, boolean nodeDebug){

        // Info empty is abort
        if(!info.isEmpty()){
            String errorPredicate = updateDebugDatalogState(info);
            if(errorPredicate.equals("")){
                LinkedList<String> currentQuestion = DesDatabaseManager.getInstance().debugCurrentDatalogQuestion();
                AcideDebugDatalogDebugWindow.getInstance().setCurrentQuestion(currentQuestion.getFirst());
                String nextPredicate = parseDatalogCurrentQuestion(currentQuestion);
                updatePredicateHighlight(nextPredicate);
                AcideDebugDatalogDebugWindow.getInstance().putPredicate(nextPredicate, getPredicateTable(nextPredicate));
                AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                        .getCanvas().getSelectedNode().setNodeColor(Color.YELLOW);
                if(!nodeDebug)
                    updateDatalogDebugWindow();
            }
            else if(errorPredicate.startsWith("debugError")){
                errorPredicate =errorPredicate.replace("debugError","");
                JOptionPane.showMessageDialog(null,errorPredicate);
                AcideDebugDatalogDebugWindow.getInstance().closeWindow();
                AcideDebugDatalogPanel._startDebug.setEnabled(true);
                AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().setDebugging(false);
            } else{
                AcideDebugDatalogDebugWindow.getInstance().stopDebug(errorPredicate,nodeDebug);
            }
        }
    }

    public static void updateSQLDebugWindow() {
        String view = AcideDebugSQLDebugWindow.getInstance().getView();
        String db = DesDatabaseManager.getInstance().currentDB();
        String type = DesDatabaseManager.getInstance().isTable(db, view) ? AcideLanguageManager.getInstance().getLabels()
                .getString("s2371") : AcideLanguageManager.getInstance().getLabels()
                .getString("s2370");
        AcideDebugSQLDebugWindow.getInstance().setInfo(AcideLanguageManager.getInstance().getLabels()
                .getString("s2376") + type + " " + view);
        AcideDebugSQLDebugWindow.getInstance().setQuestion(AcideLanguageManager.getInstance().getLabels()
                .getString("s2377") + type +"?");
    }

    public static void updateDatalogDebugWindow(){
        String predicate = AcideDebugDatalogDebugWindow.getInstance().getPredicate();
        AcideDebugDatalogDebugWindow.getInstance().setInfo(AcideLanguageManager.getInstance().getLabels()
                .getString("s2376") + AcideLanguageManager.getInstance().getLabels().getString("s2408")
                .toLowerCase() +" "+ predicate.split("/")[0]);
        AcideDebugDatalogDebugWindow.getInstance().setQuestion(AcideLanguageManager.getInstance().getLabels()
                .getString("s2377")+ AcideLanguageManager.getInstance().getLabels().getString("s2408")
                .toLowerCase() + "?");
    }

    // Gets the view selected in the SQL Debug SQL panel canvas
    public static String getSelectedViewName(){
    	if(AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas().getSelectedNode() != null)
    	    return AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas().getSelectedNode()
                .getLabel().split("/")[0];
    	else
    	    return "";
    }

    // Gets the predicate selected in the Datalog Debug Datalog panel canvas
    public static String getSelectedPredicateName(){
        if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().getCanvas().getSelectedNode() != null)
            return AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().getCanvas().getSelectedNode()
                    .getLabel();
        else
            return "";
    }

    public static String getDataFromSelectedTuple(JTable table){
        Vector<?> data = (Vector<?>) ((AcideDatabaseDataView.MyTableModel) table.getModel()).getDataVector().get(table.getSelectedRow());
        return parseTupleContent(data);
    }

    public static String parseTupleContent(Vector data){
        String tuple = "";
        for(Object value : data){
            if(value != null){
                if(isNumeric(value.toString()))
                    tuple += value + ",";
                else
                    tuple += "'" + value.toString() + "',";
            }
        }
        // Remove last comma ','
        if(!tuple.isEmpty()){
            tuple = tuple.substring(0, tuple.length() - 1);
        }
        return tuple;
    }

    public static String getUserInputSQLTuple(String view, String action){
        String db = DesDatabaseManager.getInstance().currentDB();
        AcideDatabaseDataView window = new AcideDatabaseDataView(db, view);
        window.setVisible(false);
        window.setLocation(AcideDebugSQLDebugWindow.getInstance().getLocation());
        LinkedList<String> info;
        String title;
        if(action.equals("missing")) {
            // builds only the model
            info = DesDatabaseManager.getInstance().getTableModel(view);
            int size = info.size() / 2;
            for (int i = 0; i < size; i++)
                info.add("");
            window.setIsReadOnly(false);
            title = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2366");

        }
        else {
            info = AcideDatabaseManager.getInstance().getSelectAll(db, view);
            window.setIsReadOnly(true);
            title = AcideLanguageManager.getInstance().getLabels()
                    .getString("s2367");
        }
        window.build(info);
        JScrollPane srollPane = window.getSrollPane();
        AcideDataBaseDataViewTable a = window.getTable();
        int width = (a.getColumnCount() - 1) * 150 + 30;
        int height = a.getRowHeight() * a.getRowCount();
        if(height < 80){
            height = 80;
        }
        srollPane.setPreferredSize(new Dimension(width, height));
        window.closeWindow();
        ImageIcon icon = new ImageIcon("./resources/images/acideLogo.png");
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);
        if(!action.equals("missing")){

            for( MouseListener listener : a.getMouseListeners() ) {
                a.removeMouseListener(listener);
            }
            a.changeSelection(1, 1, false, false);
            a.changeSelection(1, a.getColumnCount()-1, true, true);
            a.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int fila = a.rowAtPoint(e.getPoint());
                    a.changeSelection(fila, 1, false, false);
                    a.changeSelection(fila, a.getColumnCount()-1, true, true);
                }
            });
        }
        int input = JOptionPane.showConfirmDialog(null, srollPane, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
        if(input == 0)
            return getDataFromSelectedTuple(window.getTable());
        else
            return "";
    }

    public static String getUserInputDatalogTuple(String predicate, String action){
        String db = DesDatabaseManager.getInstance().currentDB();
        AcideDatabaseDataView window = new AcideDatabaseDataView(db, predicate);
        window.setVisible(false);
        window.setLocation(AcideDebugDatalogDebugWindow.getInstance().getLocation());
        LinkedList<String> info;
        String title;
        if(action.equals("missing")) {
            // builds only the model
            LinkedList<String> model =new LinkedList<>();
            info = DesDatabaseManager.getInstance().getAllTuples(predicate);
            int arity =Integer.parseInt(predicate.substring(predicate.lastIndexOf("/") + 1));
            for(int i=0;i<=(arity*2);i++){
                model.add(info.get(i));
            }
            for (int i = 0; i < arity; i++)
                model.add("");
            window.setIsReadOnly(false);
            window.setIsDebugging(true);
            title = AcideLanguageManager.getInstance().getLabels().getString("s2366");
            window.build(model);
        }
        else {
            info = DesDatabaseManager.getInstance().getAllTuples(predicate);
            window.setIsReadOnly(true);
            title = AcideLanguageManager.getInstance().getLabels().getString("s2367");
            window.build(info);
        }

        JScrollPane scrollPane = window.getSrollPane();
        AcideDataBaseDataViewTable a = window.getTable();
        int width = (a.getColumnCount() - 1) * 150 + 30;
        int height = a.getRowHeight() * a.getRowCount();
        if(height < 80){
            height = 80;
        }
        scrollPane.setPreferredSize(new Dimension(width, height));
        window.closeWindow();
        ImageIcon icon = new ImageIcon("./resources/images/acideLogo.png");
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);
        if(!action.equals("missing")){

            for( MouseListener listener : a.getMouseListeners() ) {
                a.removeMouseListener(listener);
            }
            a.changeSelection(1, 1, false, false);
            a.changeSelection(1, a.getColumnCount()-1, true, true);
            a.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int fila = a.rowAtPoint(e.getPoint());
                    a.changeSelection(fila, 1, false, false);
                    a.changeSelection(fila, a.getColumnCount()-1, true, true);
                }
            });
        }
        int input = JOptionPane.showConfirmDialog(null, scrollPane, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if(input == 0)
            return getDataFromSelectedTuple(window.getTable());
        else
            return "";
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void updateViewHighlight(String view){
        // Gets the canvas
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getCanvas();
        // select node
        List<Node> nodes = canvas.get_graph().get_nodes();
        Node node = null;

        for (Node n : nodes) {
            if (n.getLabel().split("/")[0].equals(view))
                node = n;
        }

        if (node != null)
            canvas.setSelectedNode(node);

        updateSQLCanvas(canvas);
    }

    private static void updatePredicateHighlight(String predicate){
        // Gets the canvas
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugDatalogPanel().getCanvas();
        // Select node
        List<Node> nodes = canvas.get_graph().get_nodes();
        Node node = null;
        for (Node n: nodes){
            if(n.getLabel().equals(predicate))
                node = n;
        }

        if(node != null)
            canvas.setSelectedNode(node);

        updateDatalogCanvas(canvas);
    }

    private static String getMessageError(LinkedList<String> info){
        String result = "";
        for(String line: info){
            if(!line.equals("$error") && !isNumeric(line))
                result += line;
        }
        return result;
    }

    public static void paintTrustedTables(AcideDebugCanvas canvas){
        String db = DesDatabaseManager.getInstance().currentDB();
        if(AcideDebugSQLConfiguration.getInstance().getTrust_tables()
                .equals(AcideDebugSQLConfiguration.Trust_tables.YES)) {
            for (Node n : canvas.get_graph().get_nodes()) {
                if (DesDatabaseManager.getInstance().isTable(db, n.getLabel().split("/")[0])) {
                    n.setNodeColor(Color.GREEN);
                }
            }
        }
        updateSQLCanvas(canvas);
    }

    public static void paintTrustedExtensionalPredicates(AcideDebugCanvas canvas){
        String db = DesDatabaseManager.getInstance().currentDB();
        if(AcideDebugDatalogConfiguration.getInstance().getTrust_extension()
                .equals(AcideDebugDatalogConfiguration.Trust_extension.YES)){
            for(Node n : canvas.get_graph().get_nodes()){
                if(DesDatabaseManager.getInstance().isExtensional(db,n.getLabel().split("/")[0])){
                    n.setNodeColor(Color.GREEN);
                }
            }
        }
        updateDatalogCanvas(canvas);
    }

    public static boolean hasRedNode(AcideDebugCanvas canvas){
        for(Node n : canvas.get_graph().get_nodes()){
            if(n.getNodeColor().equals(Color.RED))
                return true;
        }
        return false;
    }

    public static boolean hasColoredNodes(AcideDebugCanvas canvas){
        for(Node n : canvas.get_graph().get_nodes()){
            if(n.getNodeColor().equals(Color.RED) || n.getNodeColor().equals(Color.ORANGE)
                    || n.getNodeColor().equals(Color.GREEN))
                return true;
        }
        return false;
    }

    public static boolean isRedViewNode(Node node){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas();
        for(Node n : canvas.get_graph().get_nodes()){
            if(n.getNodeColor().equals(Color.RED) && n.getLabel().equals(node.getLabel()))
                return true;
        }
        return false;
    }

    public static  boolean isRedPredicateNode(Node node){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().getCanvas();
        for(Node n : canvas.get_graph().get_nodes()){
            if (n.getNodeColor().equals(Color.RED) && n.getLabel().equals(node.getLabel()))
                return true;
        }
        return false;
    }

    public static boolean isRootView(String view){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanel().getCanvas();
        return canvas.getRootNode().getLabel().split("/")[0].equals(view);
    }

    public static boolean isRootPredicate(String predicate){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().getCanvas();
        return canvas.getRootNode().getLabel().equals(predicate);
    }

    public static boolean isLastRowEmpty(JTable table){
        table.changeSelection(table.getRowCount()-1, 1, false, false);
        table.changeSelection(table.getRowCount()-1, table.getColumnCount()-1, true, true);
        String data = AcideDebugHelper.getDataFromSelectedTuple(table)
                .replace("'", "").replace(",", "") .replace(" ", "");
        table.changeSelection(table.getRowCount()-1, 1, false, false);
        return data.isEmpty();
    }

    public static void refreshDebugSQLGraph(){
        String consult = AcideMainWindow.getInstance().getDebugPanel()
                .getDebugSQLPanel().getQuery();
        // Gets the trace SQL output for query
        LinkedList<String> l = DesDatabaseManager.getInstance().executeCommand(
                "/tapi /trace_sql " + consult);
        StringBuilder result = new StringBuilder();
        for (String s : l) {
            result.append(s).append("\n");
        }
        // Parses the result and generates the path graph (/pdg -> /rdg v0.17)
        final Thread t = new Thread(new AcideDebugCanvasParseTask(result.toString(),
                AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
                .getInstance().getDebugPanel().getDebugSQLPanel()
                .getCanvas(), AcideDebugCanvasParseTask.DESTINY_PATH,consult,false));
        result = new StringBuilder(AcideDebugHelper.obtainSQLResult(t, result.toString(), l, consult));
        // Parses the result and generates the graph (/pdg -> /rdg v0.17)
        new Thread(new AcideDebugCanvasParseTask(result.toString(),
                AcideGraphCanvasParseTask.PARSE_TAPI_RDG, AcideMainWindow
                .getInstance().getDebugPanel().getDebugSQLPanel()
                .getCanvas(), AcideDebugCanvasParseTask.DESTINY_MAIN,consult,false))
                .start();
        AcideDebugSQLPanel._canvas.setZoom(1, AcideGraphCanvas.CanvasPanel.DebugSQL);
    }

    public static void refreshDebugDatalogGraph(){
        String query= AcideMainWindow.getInstance().getDebugPanel()
                    .getDebugDatalogPanel().getQuery();

        int arity = Integer.parseInt(query.substring(query.lastIndexOf("/") + 1));

        // Builds the query
        query = query.substring(0, query.lastIndexOf("/")) + "(";

        for (int i = 0; i < arity; i++) {
            query += "X" + i;
            if (i + 1 < arity)
                query += ",";
        }

        query += ")";

        final String queryText=query;

        // Gets the result of the /tapi /trace_datalog command
        LinkedList<String> l = DesDatabaseManager.getInstance()
                .executeCommand("/tapi /trace_datalog "+queryText);

        String result = "";
        for(String s:l){
            result+=s+"\n";
        }

        // Parses the result and generates the path graph
        AcideDebugCanvasParseTask pathParseTask=new AcideDebugCanvasParseTask(result,
                AcideGraphCanvasParseTask.PARSE_TAPI_PDG, AcideMainWindow.getInstance()
                .getDebugPanel().getDebugDatalogPanel().getCanvas(),
                AcideDebugCanvasParseTask.DESTINY_PATH,queryText,true);

        final Thread t = new Thread(pathParseTask);
        t.start();

        new Thread(new Runnable() {
            /*
             * (non-Javadoc)
             *
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                try {
                    // Enable the refresh button, start debug button and configuration debug button
                    AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                            ._refreshDatalog.setEnabled(true);
                    AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                            ._startDebug.setEnabled(true);
                    AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                            ._configDebug.setEnabled(true);

                    t.join();

                    if (pathParseTask.isSuccess()) {

                        // Enable the refresh button, start debug button and configuration debug button
                        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                                ._refreshDatalog.setEnabled(true);
                        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                                ._startDebug.setEnabled(true);
                        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                                ._configDebug.setEnabled(true);


                        // Gets the result of the /tapi /pdg command
                        LinkedList<String> l = DesDatabaseManager.getInstance()
                                .executeCommand("/tapi /pdg " + queryText.substring(0,
                                        queryText.lastIndexOf("(")));

                        String result = "";
                        for (String s : l) {
                            result += s + "\n";
                        }

                        // Parses the result and generates the graph
                        new Thread(new AcideDebugCanvasParseTask(result, AcideGraphCanvasParseTask.PARSE_TAPI_PDG,
                                AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().getCanvas(),
                                AcideDebugCanvasParseTask.DESTINY_MAIN, queryText, true)).start();
                    } else
                        AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel().getCanvas()
                                .set_graph(new DirectedWeightedGraph());

                }
                catch (InterruptedException e){
                    System.err.println(e);
                }
            }
        }).start();

        // Checks if the show rule property is active
        if(AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                .getShowRulesMenuItem().isSelected()) {

            new Thread(new Runnable() {
                /*
                 * (non-Javadoc)
                 *
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run() {
                    try {
                        t.join();

                        if (pathParseTask.isSuccess()) {
                            AcideDebugDatalogPanel._refreshDatalog.setEnabled(true);

                            // Gets the root node of the canvas
                            String selected = AcideMainWindow.getInstance().getDebugPanel()
                                    .getDebugDatalogPanel().getCanvas().getRootNode().getLabel();

                            // Unhighlight the previous highlights
                            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                                    .getHighLighter().unHighLight();

                            // Highlights the root node
                            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                                    .getHighLighter().highLight(selected);

                            //Update carret Position
                            AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanel()
                                    .getHighLighter().updateCarretPosition();

                            AcideDebugHelper.getRoot(selected);
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
            }).start();
        }
        AcideDebugDatalogPanel._canvas.setZoom(1, AcideGraphCanvas.CanvasPanel.DebugData);
    }

    public static boolean isSQLSessionDebugging(){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugSQLPanel().getCanvas();
        return DesDatabaseManager.getInstance().isSQLSessionDebugging() && hasColoredNodes(canvas);
    }

    public static boolean isDatalogSessionDebugging(){
        AcideDebugCanvas canvas = AcideMainWindow.getInstance()
                .getDebugPanel().getDebugDatalogPanel().getCanvas();
        return DesDatabaseManager.getInstance().isDatalogSessionDebugging() && hasColoredNodes(canvas);
    }

}
