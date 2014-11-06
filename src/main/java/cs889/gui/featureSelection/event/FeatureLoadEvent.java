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
 * ConnectionEvent.java
 * Copyright (C) 2005 University of Waikato, Hamilton, New Zealand
 *
 */

package cs889.gui.featureSelection.event;

import weka.core.Instances;

import java.util.EventObject;

/**
 * An event that is generated when a connection is established or dropped.
 *
 * @see         FeatureLoadListener
 * @author      FracPete (fracpete at waikato dot ac dot nz)
 * @version     $Revision: 1.2 $
 */
public class FeatureLoadEvent
  extends EventObject {

  /** for serialization */
  private static final long serialVersionUID = 5420308930427835037L;
  

  
  /** the type of event, CONNECT or DISCONNECT */
 // protected int m_Type;
  
  /** the databaseutils instance reponsible for the connection */
  //protected DbUtils m_DbUtils;

  /** a possible exception that occurred if not successful */
  //protected Exception m_Exception;
  
  protected Instances instances; 
  protected int numSelections; 
  

  
  /**
   * constructs the event
   * @param source        the source that generated this event
   * @param type          whether CONNECT or DISCONNECT happened
   * @param utils         the DatabaseUtils isntance responsible for the
   *                      connection
   * @param ex            a possible exception, if not successful
   */
  public FeatureLoadEvent(Object source, Instances ins, int num) {
    super(source);
    this.instances = ins; 
    this.numSelections = num;
  }
  

  
  public Instances getInstances(){
	  return this.instances;
  }
  

  public int getNumSelected(){
	  return this.numSelections;
  }




  /**
   * returns the event in a string representation
   * @return        the event in a string representation
   */
  public String toString() {
    String        result="";
    for(int i = 0; i<this.numSelections; i++){
    	result = result+ instances.attribute(i).name();
    }
    
    return result;
  }
}
