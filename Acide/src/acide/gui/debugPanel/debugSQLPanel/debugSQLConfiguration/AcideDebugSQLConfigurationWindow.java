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
package acide.gui.debugPanel.debugSQLPanel.debugSQLConfiguration;

import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AcideDebugSQLConfigurationWindow extends JFrame {

    private AcideDebugSQLConfiguration configuration;


    // Labels for debug configuration GUI
    private JLabel debugLabel;
    private JLabel orderLabel;
    private JLabel trust_tablesLabel;
    private JLabel trust_fileLabel;
    //private JLabel orable_fileLabel;

    // Radio Buttons
    private JRadioButton debugFullRadio;
    private JRadioButton debugPlainRadio;
    private JRadioButton orderCardinalityRadio;
    private JRadioButton orderTopDownRadio;
    private JRadioButton trust_tablesYesRadio;
    private JRadioButton trust_tablesNoRadio;

    private ButtonGroup debugGroup;
    private ButtonGroup orderGroup;
    private ButtonGroup trust_tablesGroup;

    // TextFields
    private JTextField trust_fileTextField;
    //private JTextField oracle_fileTextField;

    // Panels
    private JPanel mainPanel;
    private JPanel buttonPanel;

    // Button
    private JButton browseTrustFileButton;
    //private JButton browseOracleFileButton;
    private JButton saveConfigurationButton;
    private JButton cancelConfigurationButton;
    private JButton defaultConfigurationButton;

    private static final ImageIcon ICON = new ImageIcon(
            "./resources/images/icon.png");

    // Instance
    private static AcideDebugSQLConfigurationWindow instance;

    public static AcideDebugSQLConfigurationWindow getInstance(){
        if(instance == null){
            instance = new AcideDebugSQLConfigurationWindow();
        }
        return instance;
    }


    public AcideDebugSQLConfigurationWindow(){
        super(AcideLanguageManager.getInstance()
                .getLabels().getString("s2326"));

        configuration = AcideDebugSQLConfiguration.getInstance();

        this.setIconImage(ICON.getImage());

        // Builds the window components
        buildComponents();

        // Sets the listener for the window components
        setListeners();

        // Adds the components to the window
        addComponents();

        // Sets the window configuration
        setWindowConfiguration();
    }

    private void buildComponents(){
        mainPanel = new JPanel(new GridBagLayout());

        mainPanel.setBorder(BorderFactory.createTitledBorder(null,
                AcideLanguageManager.getInstance().getLabels()
                        .getString("s5"), TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION));

        // Builds labels for configuration window
        trust_tablesLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2333"));
        debugLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2329"));
        orderLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2332"));
        trust_fileLabel = new JLabel(AcideLanguageManager.getInstance()
               .getLabels().getString("s2334"));
        //orable_fileLabel = new JLabel(AcideLanguageManager.getInstance().getLabels().getString("s2335"));

        // Creates the trust_file text field
        trust_fileTextField = new JTextField("");

        // Creates the oracle_file text field
        //oracle_fileTextField = new JTextField("");

        // Builds action buttons for configuration window
        browseTrustFileButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s948"));
        //browseOracleFileButton = new JButton(AcideLanguageManager.getInstance().getLabels().getString("s948"));

        saveConfigurationButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s154"));
        cancelConfigurationButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s162"));
        defaultConfigurationButton = new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2337"));

        // Creates the button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(defaultConfigurationButton);
        buttonPanel.add(saveConfigurationButton);
        buttonPanel.add(cancelConfigurationButton);

        // Builds radio buttons and groups them
        buildRadioButtons();
    }

    private void buildRadioButtons(){
        debugFullRadio = new JRadioButton();
        debugPlainRadio = new JRadioButton();
        orderCardinalityRadio = new JRadioButton();
        orderTopDownRadio = new JRadioButton();
        trust_tablesYesRadio = new JRadioButton();
        trust_tablesNoRadio = new JRadioButton();

        debugGroup = new ButtonGroup();
        orderGroup = new ButtonGroup();
        trust_tablesGroup = new ButtonGroup();

        // s2327 = Full
        debugFullRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2327"));
        // s2328 = Plain
        debugPlainRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2328"));
        // s2330 = Cardinality
        orderCardinalityRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2330"));
        // s2331 = Topdown
        orderTopDownRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2331"));
        // s62 = Yes
        trust_tablesYesRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s62"));
        // s63 = No
        trust_tablesNoRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s63"));

        debugGroup.add(debugFullRadio);
        debugGroup.add(debugPlainRadio);
        orderGroup.add(orderCardinalityRadio);
        orderGroup.add(orderTopDownRadio);
        trust_tablesGroup.add(trust_tablesYesRadio);
        trust_tablesGroup.add(trust_tablesNoRadio);
    }

    private void setListeners() {
        browseTrustFileButton.addActionListener(new Trust_fileButtonAction());
        //browseOracleFileButton.addActionListener(new Oracle_fileButtonAction());
        defaultConfigurationButton.addActionListener(new SetDefaultAction());
        saveConfigurationButton.addActionListener(new SaveAction());
        cancelConfigurationButton.addActionListener(new CloseAction());
    }

    private void addComponents() {
        // Sets the layout
        setLayout(new GridBagLayout());

        // Adds the components to the window with the layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(trust_tablesLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 100;
        constraints.ipady = 0;

        // Adds the name text field to the main panel
        mainPanel.add(trust_tablesYesRadio, constraints);


        constraints.gridx = 2;
        constraints.ipadx = 100;
        constraints.ipady = 0;

        // Adds the name text field to the main panel
        mainPanel.add(trust_tablesNoRadio, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(trust_fileLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 200;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(trust_fileTextField, constraints);

        constraints.gridx = 2;
        constraints.ipadx = 20;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(browseTrustFileButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        //mainPanel.add(orable_fileLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 200;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        //mainPanel.add(oracle_fileTextField, constraints);

        constraints.gridx = 2;
        constraints.ipadx = 20;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        //mainPanel.add(browseOracleFileButton, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(debugLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(debugFullRadio, constraints);


        constraints.gridx = 2;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(debugPlainRadio, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.ipady = 10;

        // Adds the name label to the main panel
        mainPanel.add(orderLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(orderCardinalityRadio, constraints);


        constraints.gridx = 2;
        constraints.ipadx = 100;
        constraints.ipady = 3;

        // Adds the name text field to the main panel
        mainPanel.add(orderTopDownRadio, constraints);


        constraints.gridx = 0;
        constraints.gridy = 0;

        // Adds the main panel to the window
        add(mainPanel, constraints);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 5;

        // Adds the button panel to the window
        add(buttonPanel, constraints);

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
        setResizable(false);

        // Packs the window components
        pack();

        // Centers the window
        setLocationRelativeTo(null);

        // Disables the main window
        AcideMainWindow.getInstance().setEnabled(false);
    }

    /**
     * Closes the ACIDE - A Configurable IDE debug configuration window.
     */

    public void closeWindow() {

        // Enables the main window again
        AcideMainWindow.getInstance().setEnabled(true);

        // Closes the window
        setVisible(false);

        // Brings the main window to the front
        AcideMainWindow.getInstance().setAlwaysOnTop(true);

        // But not permanently
        AcideMainWindow.getInstance().setAlwaysOnTop(false);
    }

    /**
     * Displays the ACIDE - A Configurable IDE new project configuration window.
     */
    public void showWindow() {

        // Update language in debug configuration window
        setComponentsText();

        if(configuration.getTrust_tables().equals(AcideDebugSQLConfiguration.Trust_tables.YES))
            trust_tablesYesRadio.setSelected(true);
        else
            trust_tablesNoRadio.setSelected(true);

        if(configuration.getDebug().equals(AcideDebugSQLConfiguration.Debug.FULL))
            debugFullRadio.setSelected(true);
        else
            debugPlainRadio.setSelected(true);

        if(configuration.getOrder().equals(AcideDebugSQLConfiguration.Order.CARDINALITY))
            orderCardinalityRadio.setSelected(true);
        else
            orderTopDownRadio.setSelected(true);

        // Empties the name text field
        trust_fileTextField.setText(configuration.getTrust_file());

        // Empties the workspace text field
        //oracle_fileTextField.setText(configuration.getOracle_file());

        // Displays the window
        setVisible(true);
    }

    /**
     * Sets the text of the components assigned to the ACIDE - A Configurable IDE new project
     * configuration window.
     */
    private void setComponentsText(){

       super.setTitle(AcideLanguageManager.getInstance()
                .getLabels().getString("s2326"));

        mainPanel.setBorder(BorderFactory.createTitledBorder(null,
                AcideLanguageManager.getInstance().getLabels()
                        .getString("s5"), TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION));

        trust_tablesLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2333"));
        debugLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2329"));
        orderLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2332"));
        trust_fileLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2334"));

        browseTrustFileButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s948"));

        saveConfigurationButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s154"));
        cancelConfigurationButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s162"));
        defaultConfigurationButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2337"));

        debugFullRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2327"));

        debugPlainRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2328"));

        orderCardinalityRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2330"));

        orderTopDownRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2331"));

        trust_tablesYesRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s62"));

        trust_tablesNoRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s63"));

        // Packs the window components
        pack();
    }

    /**
     * ACIDE - A Configurable IDE debug configuration window workspace
     * button action listener.
     *
     * @version 0.11
     * @see ActionListener
     */
    class Trust_fileButtonAction implements ActionListener {

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            // Asks for the file path to the user
            String absolutePath = AcideFileManager.getInstance().askForFile(
                    AcideFileOperation.OPEN, AcideFileTarget.FILES,
                    AcideFileType.FILE, "", null);

            if (absolutePath != null) {

                // Sets the workspace path
                configuration.setTrust_file(absolutePath);

                // Updates the workspace text field
                trust_fileTextField.setText(configuration.getTrust_file());

                // Validates the changes in the workspace text field
                trust_fileTextField.validate();

                // Repaints the workspace text field
                trust_fileTextField.repaint();
            }
        }
    }

    /**
     * ACIDE - A Configurable IDE debug configuration window workspace
     * button action listener.
     *
     * @version 0.11
     * @see ActionListener
     */
    class Oracle_fileButtonAction implements ActionListener {

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            // Asks for the file path to the user
            String absolutePath = AcideFileManager.getInstance().askForFile(
                    AcideFileOperation.OPEN, AcideFileTarget.FILES,
                    AcideFileType.FILE, "", null);

            if (absolutePath != null) {

                // Sets the oracle path
                //configuration.setOracle_file(absolutePath);

                // Updates the oracle text field
                //oracle_fileTextField.setText(configuration.getOracle_file());

                // Validates the changes in the oracle text field
                //oracle_fileTextField.validate();

                // Repaints the oracle text field
                //oracle_fileTextField.repaint();
            }
        }
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

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // Closes the window
            closeWindow();
        }
    }

    class SaveAction extends AbstractAction{
        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (trust_tablesYesRadio.isSelected()) {
                configuration.setTrust_tables(AcideDebugSQLConfiguration.Trust_tables.YES);
                AcideDebugHelper.setTrustedTableNodes();
            }else{
                configuration.setTrust_tables(AcideDebugSQLConfiguration.Trust_tables.NO);
                AcideDebugHelper.resetColorTableNodes();
            }

            configuration.setTrust_file(trust_fileTextField.getText());

            //            if(oracle_fileTextField.getText() != null && !oracle_fileTextField.getText().isEmpty())
            //                configuration.setOracle_file(oracle_fileTextField.getText());

            if (debugFullRadio.isSelected())
                configuration.setDebug(AcideDebugSQLConfiguration.Debug.FULL);
            else
                configuration.setDebug(AcideDebugSQLConfiguration.Debug.PLAIN);

            if (orderCardinalityRadio.isSelected())
                configuration.setOrder(AcideDebugSQLConfiguration.Order.CARDINALITY);
            else
                configuration.setOrder(AcideDebugSQLConfiguration.Order.TOPDOWN);

            AcideDebugSQLConfiguration.getInstance().saveConfiguration(configuration);

            // Closes the window
            closeWindow();
        }
    }

    class SetDefaultAction extends  AbstractAction{
        /**
         * Escape key action serial version UID.
         */
        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            configuration.setDefaultConfiguration();
            AcideDebugSQLConfigurationWindow.getInstance().showWindow();
        }
    }
}
