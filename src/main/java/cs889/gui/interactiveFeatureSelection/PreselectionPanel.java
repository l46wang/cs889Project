/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 * ConnectionPanel.java
 * Copyright (C) 2005 University of Waikato, Hamilton, New Zealand
 *
 */


package cs889.gui.interactiveFeatureSelection;

import weka.core.Instances;
import weka.gui.ListSelectorDialog;
import weka.gui.sql.event.ConnectionListener;
import weka.gui.sql.event.HistoryChangedListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel; 
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import cs889.gui.featureSelection.event.FeatureLoadEvent;
import cs889.gui.featureSelection.event.FeatureLoadListener;
import cs889.gui.services.BaseTreeGeneration;
import cs889.gui.services.FeatureSetRebuild;
import cs889.gui.utility.FeatureSelection;
import cs889.gui.utility.FeatureSelectionUtil;

/**
 * Enables the user to insert a database URL, plus user/password to connect
 * to this database.
 *
 * @author      FracPete (fracpete at waikato dot ac dot nz)
 * @version     $Revision: 7059 $
 */
public class PreselectionPanel 
  extends JPanel 
 {

  /** for serialization. */
  static final long serialVersionUID = 3499317023969723490L;
  
  static{
	  //initial features loading
	  Instances instances;
	try {
		instances = new Instances(new BufferedReader(new FileReader(FeatureSelection.fileName)));
		 FeatureSetRebuild.reorderFeaturesA1(instances);
		 FeatureSetRebuild.reorderFeaturesA2(instances);
		 FeatureSetRebuild.reorderUserSelectFeatures();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
  }
  
  /** the name of the history. */
  public final static String HISTORY_NAME = "connection";
  
  /** the parent frame. */
  protected JFrame m_Parent = null;
  


  
  /** the label for the Feature Selection. */
  protected JLabel m_FeatureSelection = new JLabel("Max No. Features to Select");

  /** the label for the Number of Features (5, 10, 15, 20) */
  protected String[] numFeatures = { "5", "10", "15", "20" };
  protected JComboBox m_ClassCombo = new JComboBox(numFeatures);
  
  
  //instances
  public static Instances a1SelectedInstances;
  public static Instances a2SelectedInstances;
  public static Instances mergedSelectedInstances;
  
  
  
  
  /** the button to start search.  */
  protected JButton m_Start = new JButton("Start Load Features");

//  public static int numFeaturesSelected = 0;
  public int getNumFeaturesSelected() {
	int index = m_ClassCombo.getSelectedIndex();
	return new Integer(numFeatures[index]);
 }



/** the connection listeners. */
  protected HashSet m_ConnectionListeners;

  /** the history listeners. */
//  protected HashSet m_HistoryChangedListeners;

  /** for connecting to the database. */
//  protected DbUtils m_DbUtils;

  /** the history of connections. */
//  protected DefaultListModel m_History = new DefaultListModel();

  /**
   * initializes the panel.
   * 
   * @param parent      the parent of this panel
   */
  public PreselectionPanel(JFrame parent) {
    super();
    
    m_Parent                  = parent;
    m_ConnectionListeners     = new HashSet();
    
    try {
//      m_DbUtils   = new DbUtils();
//      m_URL       = m_DbUtils.getDatabaseURL();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    createPanel();
  }

  /**
   * load the pre-ranked lists
   */
  // TO Be Implement.
  protected void createDefaultLists(){
	  //file two lists of attributes
	  System.out.println(m_ClassCombo.getSelectedIndex());
	  System.out.println(getNumFeaturesSelected());
	
	  try {
		this.notifyFeatureLoadListener(FeatureSelectionUtil.createInstances());
	  } catch (IOException e) {
			// TODO Auto-generated catch block
		  e.printStackTrace();
	  }

  }
  /**
   * builds the panel with all its components.
   */
  protected void createPanel() {
    JPanel        panel;
    JPanel        panel2;
    
    setLayout(new BorderLayout());
    panel2 = new JPanel(new FlowLayout());
    add(panel2, BorderLayout.WEST);
 
    // label
    m_FeatureSelection.setLabelFor(m_ClassCombo);
    m_FeatureSelection.setDisplayedMnemonic('M');
    
    panel2.add(m_FeatureSelection);
    panel2.add(m_ClassCombo);
    
    // buttons
    panel = new JPanel(new FlowLayout());
    panel2.add(panel);
    m_Start.setMnemonic('s');
    panel.add(m_Start);
    m_Start.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			createDefaultLists();
		}
    	
    });

    
  }

  
  public void addFeatureLoadListener(FeatureLoadListener l){
	  m_ConnectionListeners.add(l);
  }
  
  public void removeFeatureLoadListener(FeatureLoadListener l){
	  m_ConnectionListeners.add(l);
  }
  

  /**
   * notifies the connection listeners of the event.
   * 
   * @param type      the type of the action, CONNECT or DISCONNECT
   * @param ex        an optional exception that happened (indicates failure!)
   */
  protected void notifyFeatureLoadListener(Instances instances) {
    Iterator              iter;
    FeatureLoadListener    l;

    iter = m_ConnectionListeners.iterator();
    while (iter.hasNext()) {
      l = (FeatureLoadListener) iter.next();
      l.connectionChange(
          new FeatureLoadEvent(this, instances, this.getNumFeaturesSelected()));
      
     
      try {
    	  /**
           * Create feature sets for A1, A2 and merged feature set. 
           */
          
    	  a1SelectedInstances = FeatureSelectionUtil.createA1Instances(this.getNumFeaturesSelected());
    	  
    	  a2SelectedInstances = FeatureSelectionUtil.createA2Instances(this.getNumFeaturesSelected());
    	  
    	  mergedSelectedInstances = FeatureSelectionUtil.createInstances();
    	  
      } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	      }
	    }
  }

  /**
   * adds the given listener to the list of listeners.
   * 
   * @param l       the listener to add to the list
   */
  public void addHistoryChangedListener(HistoryChangedListener l) {
//    m_HistoryChangedListeners.add(l);
  }

  /**
   * removes the given listener from the list of listeners.
   * 
   * @param l       the listener to remove
   */
  public void removeHistoryChangedListener(HistoryChangedListener l) {
//    m_HistoryChangedListeners.remove(l);
  }
}
