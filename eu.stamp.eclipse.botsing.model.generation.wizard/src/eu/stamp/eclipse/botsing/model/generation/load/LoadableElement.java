/*******************************************************************************
 * Copyright (c) 2019 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * 	Ricardo Jose Tejada Garcia (Atos) - main developer
 * 	Jesús Gorroñogoitia (Atos) - architect
 * Initially developed in the context of STAMP EU project https://www.stamp-project.eu
 *******************************************************************************/
package eu.stamp.eclipse.botsing.model.generation.load;

import java.util.Map;

public abstract class LoadableElement {
	
  protected final String key;
  
  public LoadableElement(String key) {
	  this.key = key;
  }
  
  public void load(Map<String,String> map) { loadValue(map.get(key)); }
      
  protected abstract void loadValue(String value);
}