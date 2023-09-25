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
package acide.gui.debugPanel.debugDatalogPanel.debugDatalogConfiguration;

import acide.files.AcideFileManager;
import acide.files.utils.AcideFileOperation;
import acide.files.utils.AcideFileTarget;
import acide.files.utils.AcideFileType;
import acide.gui.debugPanel.utils.AcideDebugHelper;
import acide.gui.listeners.AcideWindowClosingListener;
import acide.gui.mainWindow.AcideMainWindow;
import acide.language.AcideLanguageManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AcideDebugDatalogConfigurationWindow extends JFrame {

    private AcideDebugDatalogConfiguration configuration;

    // Labels for debug configuration GUI
    private JLabel trust_extensionLabel;
    private JLabel fileLabel;

    // Radio Buttons
    private JRadioButton trust_extensionYESRadio;
    private JRadioButton trust_extensionNORadio;

    // Group Radio Buttons
    private ButtonGroup trust_extensionGroup;

    // TextFields
    private JTextField fileTextField;

    // Panels
    private JPanel mainPanel;
    private JPanel buttonPanel;

    // Buttons
    private JButton browseFileButton;
    private JButton saveConfigurationButton;
    private JButton cancelConfigurationButton;
    private JButton defaultConfigurationButton;

    private static final ImageIcon ICON=new ImageIcon(
            "./resources/images/icon.png");

    // Instance
    private static AcideDebugDatalogConfigurationWindow instance;

    public static AcideDebugDatalogConfigurationWindow getInstance(){
        if(instance == null)
            instance=new AcideDebugDatalogConfigurationWindow();
        return instance;
    }

    AcideDebugDatalogConfigurationWindow(){

        // Set window title
        super(AcideLanguageManager.getInstance()
                .getLabels().getString("s2326"));

        configuration = AcideDebugDatalogConfiguration.getInstance();

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

        mainPanel=new JPanel(new GridBagLayout());

        mainPanel.setBorder(BorderFactory.createTitledBorder(null,
                AcideLanguageManager.getInstance().getLabels()
                        .getString("s5"), TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION));

        // Builds labels for configuration window
        trust_extensionLabel=new JLabel(AcideLanguageManager.getInstance()
                .getLabels().getString("s2405"));
        fileLabel=new JLabel(AcideLanguageManager.getInstance()
                .getLabels().getString("s2406"));

        // Creates the file text field
        fileTextField=new JTextField("");

        // Builds action buttons for configuration window
        browseFileButton=new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s948"));
        saveConfigurationButton=new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s154"));
        cancelConfigurationButton=new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s162"));
        defaultConfigurationButton=new JButton(AcideLanguageManager.getInstance()
                .getLabels().getString("s2337"));

        // Creates the button panel
        buttonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(defaultConfigurationButton);
        buttonPanel.add(saveConfigurationButton);
        buttonPanel.add(cancelConfigurationButton);

        // Builds radio buttons and groups them
        buildRadioButtons();
    }

    private void buildRadioButtons(){

        trust_extensionYESRadio=new JRadioButton();
        trust_extensionNORadio=new JRadioButton();

        trust_extensionYESRadio.setText(AcideLanguageManager.getInstance()
                    .getLabels().getString("s62"));
        trust_extensionNORadio.setText(AcideLanguageManager.getInstance()
                    .getLabels().getString("s63"));

        trust_extensionGroup=new ButtonGroup();

        trust_extensionGroup.add(trust_extensionYESRadio);
        trust_extensionGroup.add(trust_extensionNORadio);

    }

    private void setListeners(){
        browseFileButton.addActionListener(new FileButtonAction());
        saveConfigurationButton.addActionListener(new SaveAction());
        cancelConfigurationButton.addActionListener(new CloseAction());
        defaultConfigurationButton.addActionListener(new DefaultAction());
    }

    private void addComponents(){

        // Sets the layout
        setLayout(new GridBagLayout());

        // Adds the components to the main panel with the layout
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill= GridBagConstraints.BOTH;
        constraints.insets=new Insets(5,5,5,5);
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.ipady=10;

        // Adds the name label to the main panel
        mainPanel.add(trust_extensionLabel,constraints);

        constraints.gridx=1;
        constraints.ipadx=100;
        constraints.ipady=0;

        // Adds the extensional radio button yes to the main panel
        mainPanel.add(trust_extensionYESRadio,constraints);

        constraints.gridx=2;
        constraints.ipadx=100;
        constraints.ipady=0;

        // Adds the extensional radio button no to the main panel
        mainPanel.add(trust_extensionNORadio,constraints);

        constraints.gridx=0;
        constraints.gridy=1;
        constraints.ipady=10;

        // Adds the file label to the main panel
        mainPanel.add(fileLabel,constraints);

        constraints.gridx=1;
        constraints.ipadx=200;
        constraints.ipady=3;

        // Adds the file text field to the main panel
        mainPanel.add(fileTextField,constraints);

        constraints.gridx=2;
        constraints.ipadx=20;
        constraints.ipady=3;

        // Adds the browse file button to the main panel
        mainPanel.add(browseFileButton, constraints);

        constraints.gridx=0;
        constraints.gridy=0;

        add(mainPanel,constraints);

        constraints.insets=new Insets(0,0,0,0);
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.CENTER;
        constraints.gridx=0;
        constraints.gridy=2;

        // Adds the button panel to the Window
        add(buttonPanel,constraints);

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
    public void closeWindow(){

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
    public void showWindow(){

        // Update language in debug configuration window
        setComponentsText();

        trust_extensionYESRadio.setSelected((configuration.getTrust_extension()
                .equals(AcideDebugDatalogConfiguration.Trust_extension.YES))?true:false);

        trust_extensionNORadio.setSelected((configuration.getTrust_extension()
                .equals(AcideDebugDatalogConfiguration.Trust_extension.NO))?true:false);

        fileTextField.setText(configuration.getFile());

        // Displays the window
        setVisible(true);
    }

    public AcideDebugDatalogConfiguration getConfiguration(){
        return configuration;
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

        trust_extensionLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2405"));

        trust_extensionYESRadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s62"));
        trust_extensionNORadio.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s63"));

        fileLabel.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2406"));

        browseFileButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s948"));
        saveConfigurationButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s154"));
        cancelConfigurationButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s162"));
        defaultConfigurationButton.setText(AcideLanguageManager.getInstance()
                .getLabels().getString("s2337"));

        // Packs the window components
        pack();
    }


    /**
     * ACIDE - A Configurable IDE debug configuration window workspace
     * button action listener.
     *
     * @version 0.19
     * @see ActionListener
     */
    class FileButtonAction implements ActionListener{

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Ask to the user for a one file
            String absolutePath = AcideFileManager.getInstance().askForFile(
                    AcideFileOperation.OPEN, AcideFileTarget.FILES,
                    AcideFileType.FILE,"", null);

            if(absolutePath!=null){

                // Sets the workspace path
                configuration.setFile(absolutePath);

                // Update the workspace text field
                fileTextField.setText(configuration.getFile());

                // Validates the changes in the workspace text field
                fileTextField.validate();

                // Repaints the workspace text field
                fileTextField.repaint();
            }
        }
    }

    /**
     * ACIDE - A Configurable IDE save debug configuration button action
     * listener.
     *
     * @version 0.19
     * @see ActionListener
     */
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
        public void actionPerformed(ActionEvent e) {

            configuration.setTrust_extension((trust_extensionYESRadio.isSelected())?
                    AcideDebugDatalogConfiguration.Trust_extension.YES:
                        AcideDebugDatalogConfiguration.Trust_extension.NO);

            if(trust_extensionYESRadio.isSelected())
                AcideDebugHelper.setTrustedExtensionalNodes();
            else
                AcideDebugHelper.resetColorExtensionalNodes();

            configuration.setFile(fileTextField.getText());

            AcideDebugDatalogConfiguration.getInstance().saveConfiguration(configuration);

            // Closes the window
            closeWindow();

        }
    }

    /**
     * ACIDE - A Configurable IDE default debug configuration button action
     * listener.
     *
     * @version 0.19
     * @see ActionListener
     */
    class DefaultAction implements ActionListener{

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            configuration.setDefaultConfiguration();

            // Apply default configuration in window
            showWindow();

        }
    }

    /**
     * ACIDE - A Configurable IDE debug configuration window escape key
     * action.
     *
     * @version 0.19
     * @see AbstractAction
     */
    class CloseAction extends  AbstractAction {
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
        public void actionPerformed(ActionEvent e) {
            // Closes the window
            closeWindow();
        }
    }

}
