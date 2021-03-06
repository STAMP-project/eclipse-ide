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

package eu.stamp.eclipse.plugin.dspot.wizard;

import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;

import eu.stamp.eclipse.plugin.dspot.launch.DSpotJob;
import eu.stamp.eclipse.plugin.dspot.properties.DSpotProperties;

/**
 * 
 */
public class DSpotWizard extends Wizard{

	private final List<DSpotPage> pagesList;
	
	public DSpotWizard(List<DSpotPage> pagesList) {
		this.pagesList = pagesList;
	}
	
	@Override
	public Image getDefaultPageImage() {
		final URL iconURL = FileLocator.find(
				Platform.getBundle(DSpotProperties.PLUGIN_ID),
				new Path("images/stamp.png"),null);
		ImageDescriptor descriptor = ImageDescriptor.createFromURL(iconURL);
		Image image = descriptor.createImage();
		DSpotDialog.IMAGE = image;
		return image;
	}
	
	@Override
	public void addPages() {
		addPage(new DSpotPage1("first page")); // special page
		for(DSpotPage page : pagesList) addPage(page);
	}
	
	@Override
	public String getWindowTitle() {
       return "DSpot Wizard";
	}
	
	@Override
	public boolean performFinish() {
		DSpotJob job = new DSpotJob("DSpot working");
		job.schedule();
		return true;
	}
	
}