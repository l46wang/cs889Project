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
 *    AttributeSelectionPanel.java
 *    Copyright (C) 1999 University of Waikato, Hamilton, New Zealand
 *
 */

package cs889.gui.interactiveFeatureSelection;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.Messages;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.BorderFactory;

import cs889.gui.featureSelection.event.FeatureLoadEvent;
import cs889.gui.featureSelection.event.FeatureLoadListener;
import cs889.gui.interactiveFeatureSelection.AttributeSelectionPanel.AttributeTableModel;
import cs889.gui.services.BaseTreeGeneration;
import cs889.gui.utility.FeatureSelectionUtil;

/**
 * Creates a panel that displays the attributes contained in a set of
 * instances, letting the user toggle whether each attribute is selected
 * or not (eg: so that unselected attributes can be removed before
 * classification). <br>
 * Besides the All, None and Invert button one can also choose attributes which
 * names match a regular expression (Pattern button). E.g. for removing all
 * attributes that contain an ID and therefore unwanted information, one can
 * match all names that contain "id" in the name:<br> 
 * <pre>   (.*_id_.*|.*_id$|^id$)</pre> 
 * This does not match e.g. "humidity", which could be an attribute we would
 * like to keep.
 *
 * @author Len Trigg (trigg@cs.waikato.ac.nz)
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 7059 $
 */
public class Algorithm2AttributeSelectionPanel
  extends JPanel implements FeatureLoadListener{

  /** for serialization */
  private static final long serialVersionUID = 627131485290359196L;


  /**
   * A table model that looks at the names of attributes and maintains
   * a list of attributes that have been "selected".
   */
  class AttributeTableModel
    extends AbstractTableModel {

    /** for serialization */
    private static final long serialVersionUID = -4152987434024338064L;

    /** The instances who's attribute structure we are reporting */
    protected Instances m_Instances;
    

    public Instances getSelectedInstances() throws Exception{
    	return FeatureSelectionUtil.createA2Instances(this.m_Instances.numAttributes());
    }
    /**
     * Creates the tablemodel with the given set of instances.
     *
     * @param instances the initial set of Instances
     * @throws Exception 
     */
    public AttributeTableModel(Instances instances, int numSelections) throws Exception {
    	/** test purposes **/
        try {
			instances = FeatureSelectionUtil.createA2Instances(numSelections);
			setInstances(numSelections);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
    }

    /**
     * Sets the tablemodel to look at a new set of instances.
     *
     * @param instances the new set of Instances.
     */
    public void setInstances(int numSelection) {

      try {
		m_Instances = FeatureSelectionUtil.createA2Instances(numSelection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
    }
    
    /**
     * Gets the number of attributes.
     *
     * @return the number of attributes.
     */
    public int getRowCount() {
      
      return this.m_Instances.numAttributes();
    }
    
    /**
     * Gets the number of columns: 3
     *
     * @return 3
     */
    public int getColumnCount() {
      
      return 2;
    }
    
    /**
     * Gets a table cell
     *
     * @param row the row index
     * @param column the column index
     * @return the value at row, column
     */
    public Object getValueAt(int row, int column) {
      
      switch (column) {
	      case 0:
	    	  return m_Instances.attribute(row).name();
	      case 1:
	    	  String description = m_Instances.attribute(row).toString(); 
	    	  return  description.substring(description.indexOf("{")+1,description.indexOf("}") );	
	      default:
	    	  return null;
      }
    }
    
    /**
     * Gets the name for a column.
     *
     * @param column the column index.
     * @return the name of the column.
     */
    public String getColumnName(int column) {
      
      switch (column) {
      case 0:
    	  return new String("Feature Name");
      case 1:
    	  return new String("Feature Values");
      default:
    	  return null;
      }
    }
    
    /**
     * Sets the value at a cell.
     *
     * @param value the new value.
     * @param row the row index.
     * @param col the column index.
     */
    public void setValueAt(Object value, int row, int col) {
      
      try {
		Instances temp = this.getSelectedInstances();
		System.out.println("The selected attributes are:");
		for(int i = 0; i<temp.numAttributes(); i++){
			System.out.println("The attribute "+i+" "+temp.attribute(i).name());
		}
		System.out.println("================================");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    
    /**
     * Gets the class of elements in a column.
     *
     * @param col the column index.
     * @return the class of elements in the column.
     */
    public Class getColumnClass(int col) {
      return getValueAt(0, col).getClass();
    }

    /**
     * Returns true if the column is the "selected" column.
     *
     * @param row ignored
     * @param col the column index.
     * @return true if col == 1.
     */
    public boolean isCellEditable(int row, int col) {

      if (col == 2) { 
    	  return true;
      }
      
      return false;
    }
    

    



  }


  /** The table displaying attribute names and selection status */
  protected JTable m_Table = new JTable();

  /** The table model containing attribute names and selection status */
  protected AttributeTableModel m_Model;

  /** The current regular expression. */
  protected String m_PatternRegEx = "";
  
  
  protected JButton m_VisualizedTreeButton = new JButton("Visualized Rules");
  protected JButton m_VisualizedResButton = new JButton(" Results");
  
  /**
   * Creates the attribute selection panel with no initial instances.
   */
  public Algorithm2AttributeSelectionPanel() {
    this(true, true, true, true);
  }
  
  /**
   * Creates the attribute selection panel with no initial instances.
   * @param include true if the include button is to be shown
   * @param remove true if the remove button is to be shown
   * @param invert true if the invert button is to be shown
   * @param patter true if the pattern button is to be shown
   */
  public Algorithm2AttributeSelectionPanel(boolean include, boolean remove, boolean invert,
      boolean pattern) {

	setLayout(new BorderLayout());
    m_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    m_Table.setColumnSelectionAllowed(false); 
    m_Table.setPreferredScrollableViewportSize(new Dimension(400, 150));
    add(new JScrollPane(m_Table), BorderLayout.CENTER);
    JPanel panel = new JPanel(new FlowLayout());
    m_VisualizedTreeButton.setMnemonic('V');
    panel.add(m_VisualizedTreeButton);
    panel.add(m_VisualizedResButton);
    add(panel, BorderLayout.SOUTH);
    m_VisualizedTreeButton.addActionListener(
    		new ActionListener(){
    	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				BaseTreeGeneration.generateTreeA2();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    });
  }
  
  public Dimension getPreferredScrollableViewportSize() {
    return m_Table.getPreferredScrollableViewportSize();
  }
  
  public void setPreferredScrollableViewportSize(Dimension d) {
    m_Table.setPreferredScrollableViewportSize(d);
  }

  /**
   * Sets the instances who's attribute names will be displayed.
   *
   * @param newInstances the new set of instances
   */
  public void setInstances(Instances newInstances, int numSelection) {

    if (m_Model == null) {
      try {
		m_Model = new AttributeTableModel(newInstances, numSelection);
      } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
      m_Table.setModel(m_Model);
      TableColumnModel tcm = m_Table.getColumnModel();
      tcm.getColumn(0).setMinWidth(40);
      tcm.getColumn(1).setMinWidth(60);
    } else {
    	
      m_Model.setInstances(numSelection);
      //m_Table.clearSelection();
    }
    m_Table.sizeColumnsToFit(2);
    m_Table.revalidate();
    m_Table.repaint();
  }


  
  /**
   * Gets an array containing the indices of all selected attributes.
   *
   * @return the array of selected indices.
   */
//  public int [] getSelectedAttributes() {
//    
//    return (m_Model == null) ? null : m_Model.getSelectedAttributes();
//  }
//  
  public Instances getSelectedInstances() throws Exception{
	  return (m_Model == null) ? null : m_Model.getSelectedInstances();
  }
  /**
   * Set the selected attributes in the widget. Note that
   * setInstances() must have been called first.
   * 
   * @param selected an array of boolean indicating which attributes
   * are to have their check boxes selected.
   * @throws Exception if the supplied array of booleans does not have
   * the same number of elements as there are attributes.
   */
//  public void setSelectedAttributes(boolean[] selected) throws Exception {
//    if (m_Model != null) {
//      m_Model.setSelectedAttributes(selected);
//    }
//  }
  
  /**
   * Get the table model in use (or null if no instances
   * have been set yet).
   * 
   * @return the table model in use or null if no instances
   * have been seen yet.
   */
  public TableModel getTableModel() {
    return m_Model;
  }
  
  /**
   * Gets the selection model used by the table.
   *
   * @return a value of type 'ListSelectionModel'
   */
  public ListSelectionModel getSelectionModel() {

    return m_Table.getSelectionModel();
  }
  
	@Override
	public void connectionChange(FeatureLoadEvent evt) {
		// TODO Auto-generated method stub
		this.setInstances(evt.getInstances(), evt.getNumSelected());
	}
  
  /**
   * Tests the attribute selection panel from the command line.
   *
   * @param args must contain the name of an arff file to load.
   */
  public static void main(String[] args) {

    try {
      if (args.length == 0) {
	throw new Exception(Messages.getInstance().getString("AttributeSelectionPanel_Main_Exception_Text"));
      }
      Instances i = new Instances(new java.io.BufferedReader(
				  new java.io.FileReader(args[0])));
      AttributeSelectionPanel asp = new AttributeSelectionPanel();
      final javax.swing.JFrame jf =
	new javax.swing.JFrame(Messages.getInstance().getString("AttributeSelectionPanel_Main_JFrame_Text"));
      jf.getContentPane().setLayout(new BorderLayout());
      jf.getContentPane().add(asp, BorderLayout.CENTER);
      jf.addWindowListener(new java.awt.event.WindowAdapter() {
	public void windowClosing(java.awt.event.WindowEvent e) {
	  jf.dispose();
	  System.exit(0);
	}
      });
      jf.pack();
      jf.setVisible(true);
      asp.setInstances(i, 5);
    } catch (Exception ex) {
      ex.printStackTrace();
      System.err.println(ex.getMessage());
    }
  }

  
} // AttributeSelectionPanel
