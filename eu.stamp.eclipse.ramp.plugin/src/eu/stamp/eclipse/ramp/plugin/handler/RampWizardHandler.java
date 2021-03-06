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
package eu.stamp.eclipse.ramp.plugin.handler;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

import eu.stamp.eclipse.ramp.plugin.classpth.ClassPathCreator;
import eu.stamp.eclipse.botsing.model.generation.handler.StampHandler;
import eu.stamp.eclipse.ramp.plugin.constants.RampPluginConstants;
import eu.stamp.eclipse.ramp.plugin.wizard.RampWizard;

/**
 * Handler for opening the Evosuite wizard
 */
public class RampWizardHandler extends StampHandler {
	
	private ExecutionEvent event;
	
	private IJavaProject project;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		this.event = event;
		project = getProject();
		
		if(project == null) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),"No project selected",
					"There is no project selected, please select a project and open this dialog again");
			return null;
		}
		try {
			if(!project.getProject().hasNature(RampPluginConstants.MAVEN_NATURE)) {
				MessageDialog.openError(HandlerUtil.getActiveShell(event),"Project is not maven",
						"The selected project must be maven");
				return null;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		ClassPathCreator classPathCreator = new ClassPathCreator(project,HandlerUtil.getActiveShell(event));
		classPathCreator.createClassPathFile();
		
	    openWizard(classPathCreator.getClassCompactlist());
		return null;
	}
	
	private void openWizard(String classList) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					WizardDialog wizardDialog = new WizardDialog(
							HandlerUtil.getActiveShell(event),new RampWizard(project,classList));
					 wizardDialog.open();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
