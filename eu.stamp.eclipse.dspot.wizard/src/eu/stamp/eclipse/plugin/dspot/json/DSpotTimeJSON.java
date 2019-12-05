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
package eu.stamp.eclipse.plugin.dspot.json;

import java.util.List;

public class DSpotTimeJSON {
public String projectName;
public List<DSpotClassTime>classTimes;

public class DSpotClassTime {

public String fullQualifiedName;
public int timeInMs;
}

public int getClassTime(String fullQualifiedName) { // TODO
for(DSpotClassTime time : classTimes)if(time.fullQualifiedName
.equalsIgnoreCase(fullQualifiedName)) return time.timeInMs; 
return 0;
}
}
