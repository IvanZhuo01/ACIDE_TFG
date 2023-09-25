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
package acide.gui.debugPanel.debugCanvas.listeners;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.border.BevelBorder;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ImageIcon;

import acide.gui.debugPanel.debugCanvas.AcideDebugCanvas;
import acide.gui.debugPanel.debugDatalogPanel.AcideDebugDatalogDebugWindow;
import acide.gui.graphUtils.DirectedWeightedGraph;
import acide.gui.graphUtils.Node;
import acide.language.AcideLanguageManager;
import acide.gui.mainWindow.AcideMainWindow;
import acide.gui.debugPanel.debugSQLPanel.AcideDebugSQLDebugWindow;

/**
 * ACIDE - A Configurable IDE debug canvas mouse motion listener.
 * 
 * @version 0.15
 * @see MouseMotionListener
 */
public class AcideDebugCanvasMouseMotionListener implements MouseMotionListener {
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener target canvas.
	 */
	AcideDebugCanvas _canvas;
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener x position of the mouse.
	 */
	private int x=-1;
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener y position of the mouse.
	 */
	private int y=-1;
	
	private Node _selected =null;
	
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener the mouse was clicking a node.
	 */
	private boolean _nodeClicked=false;
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener node tooltip.
	 */
	private JPopupMenu _nodeToolTip=new JPopupMenu();
	/**
	 * ACIDE - A Configurable IDE debug canvas mouse motion listener node info icon
	 */
	private final static ImageIcon NODE_INFO = new ImageIcon("./resources/icons/panels/info.png");

	/**
	 * Creates a new ACIDE - A Configurable IDE debug canvas mouse motion listener.
	 * @param canvas the canvas to listen.
	 */
	public AcideDebugCanvasMouseMotionListener(AcideDebugCanvas canvas){
		this._canvas=canvas;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent ev) {
		if(x!=-1 || y!=-1){
			int difx=ev.getX()-x;
			int dify=ev.getY()-y;

			DirectedWeightedGraph graph = _canvas.get_graph();
			ArrayList<Node> nodes = graph.get_nodes();
			if(!_nodeClicked)
				for(Node n:nodes){
					//checks if the node is clicked
					if(ev.getX()>=n.getX() && ev.getX()<=n.getX()+(int)(_canvas.getNodeSize()*_canvas.getZoom()) &&
							ev.getY()>=n.getY() && ev.getY()<=n.getY()+(int)(_canvas.getNodeSize()*_canvas.getZoom())){
						_selected = n;
						_nodeClicked=true;
						break;
					}

					//checks if the labels of the nodes are shown and if they are checks if the label is clicked
					if(!_nodeClicked && _canvas.isShowingLabels()){
						Graphics g = _canvas.getGraphics();
						Rectangle2D rect = g.getFontMetrics().getStringBounds(n.getLabel(), g);
						int posxRect=(int)n.getX()-(int)((rect.getWidth()/2-(_canvas.getNodeSize()*_canvas.getZoom())/2));
						if(ev.getX()>=posxRect
								&& ev.getX()<=posxRect+rect.getWidth()
								&& ev.getY()>=(int)n.getY()- (5+(int)rect.getHeight())
								&& ev.getY()<=n.getY()){
							_selected = n;
							_nodeClicked=true;
							break;
						}
					}
				}
			//moves the selected node.
			if(_selected!=null)
				_selected.move(difx,dify);
			else
				//moves all the nodes.
				for(Node n:nodes)
					n.move(difx, dify);
			_canvas.repaint();
		}
		//saves the new value of x and y coordinates.
		x=ev.getX();
		y=ev.getY();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {				
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.x=-1;
		this.y=-1;		
		_nodeClicked=false;
		_selected =null;

		if(AcideMainWindow.getInstance().getDebugPanel().getTabbedPane()
				.getSelectedIndex()==AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanelIndex() ||
				AcideMainWindow.getInstance().getDebugPanel().getTabbedPane()
				.getSelectedIndex() == AcideMainWindow.getInstance().getDebugPanel().getDebugDatalogPanelIndex()){

			// Gets the graphs of the canvas
			DirectedWeightedGraph graph = _canvas.get_graph();

			// Gets the nodes of the graph
			ArrayList<Node> nodes = graph.get_nodes();

			for(int i=0;i<_nodeToolTip.getComponentCount();i++){
				_nodeToolTip.remove(i);
			}

			if(_nodeToolTip.isVisible())
				_nodeToolTip.setVisible(false);

			// Searches if a mouse is over a node
			for (Node n : nodes) {
				if (arg0.getX() >= n.getX() && arg0.getX() <= n.getX() + (int) (_canvas.getNodeSize() * _canvas.getZoom())
						&& arg0.getY() >= n.getY() && arg0.getY() <= n.getY() +
								(int) (_canvas.getNodeSize() * _canvas.getZoom())
						&& !_canvas.getSelectedNode().getLabel().equals(n.getLabel())) {

					JMenuItem item;
					String status=AcideLanguageManager.getInstance().getLabels().getString("s2395");// Gray or yellow

					if(n.getNodeColor().equals(Color.GREEN))
						status=AcideLanguageManager.getInstance().getLabels().getString("s2396");
					else{
						if(n.getNodeColor().equals(Color.ORANGE))
							status=AcideLanguageManager.getInstance().getLabels().getString("s2397");
						else {
							if(n.getNodeColor().equals(Color.RED))
								status=AcideLanguageManager.getInstance().getLabels().getString("s2398");
						}
					}

					item=new JMenuItem("<html><div>"+status+"</div></html>",NODE_INFO);

					HashMap<String,String> errors=(AcideMainWindow.getInstance().getDebugPanel().getTabbedPane()
							.getSelectedIndex()==AcideMainWindow.getInstance().getDebugPanel().getDebugSQLPanelIndex())?
							AcideDebugSQLDebugWindow.getInstance().getErrors():
							AcideDebugDatalogDebugWindow.getInstance().getErrors();

					if(errors.containsKey(n.getLabel().split("/")[0]))
						item=new JMenuItem("<html><div>"+status+"</div><div>"+
								errors.get(n.getLabel().split("/")[0])
										.substring(0,errors.get(n.getLabel().split("/")[0]).lastIndexOf(")")+1)
								+ "</div></html>",NODE_INFO);

					_nodeToolTip.add(item);
					_nodeToolTip.setBorder(new BevelBorder(BevelBorder.RAISED));
					_nodeToolTip.show(arg0.getComponent(),arg0.getX()+10,arg0.getY()+10);
					
					break;
				}
			}
		}

	}
}
