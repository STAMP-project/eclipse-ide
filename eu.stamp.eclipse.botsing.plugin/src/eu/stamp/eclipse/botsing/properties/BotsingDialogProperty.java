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
package eu.stamp.eclipse.botsing.properties;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

/**
 *  The properties in a dialog contains two states temporal and final
 *  the temporal state is destroyed when cancel is pressed and it becomes
 *  the final state when ok, the temporal state is set by the widgets
 */
public class BotsingDialogProperty extends AbstractBotsingProperty {

	private AbstractBotsingProperty property;
	
	public BotsingDialogProperty(AbstractBotsingProperty property) {
		super(property.getDefaultValue(),
				property.getKey(),property.getName(),false,true); // properties in the dialog are optional
		this.property = property;
	}

	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public void setData(String data) {
        property.setData(data);
        callListeners();
	}

	@Override
	public void createControl(Composite composite) {
        property.createControl(composite);
	}
	
	@Override
	public void appendToConfiguration(ILaunchConfigurationWorkingCopy configuration) {
		property.appendToConfiguration(configuration);
	}
	
	@Override
	public void load(ILaunchConfigurationWorkingCopy configuration) {
		super.load(configuration);
		save();
		property.load(configuration);
	}
	
	public void save() { this.data = property.getData(); }
	
	public AbstractBotsingProperty getCoreProperty() {
		return property;
	}

}
