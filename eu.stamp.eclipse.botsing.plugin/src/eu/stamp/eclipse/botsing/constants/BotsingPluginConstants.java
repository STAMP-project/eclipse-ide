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
package eu.stamp.eclipse.botsing.constants;

public abstract class BotsingPluginConstants {
 
	public static final String BOTSING_PLUGIN_ID = "eu.stamp.eclipse.botsing.plugin";
	public static final String BOTSING_LAUNCH_ID = "eu.stamp.eclipse.botsing.plugin.launchConfigurationType2";
    public static final String BOTSING_MAIN = "eu.stamp.eclipse.botsing.call.Invocator";
	//public static final String BOTSING_MAIN = "eu.stamp.botsing.Botsing";
	public static final String BOTSING_JAR_URL = "platform:/plugin/eu.stamp.eclipse.botsing.plugin/lib/botsing-reproduction-1.0.7.jar";
	public static final String BOTSING_INVOCATION_JAR_URL = "platform:/plugin/eu.stamp.eclipse.botsing.plugin/lib/BotsingCall.jar";
	public static final String JUNIT_JAR_URL = "platform:/plugin/eu.stamp.eclipse.botsing.plugin/lib/junit-4.12.jar";
    public static final String HAMCREST_JAR_URL = "platform:/plugin/eu.stamp.eclipse.botsing.plugin/lib/hamcrest-core-1.3.jar";
    public static final String MOCKITO_JAR_URL = "platform:/plugin/eu.stamp.eclipse.botsing.plugin/lib/mockito-all-1.9.5.jar";
    public static final String ATTR_MODEL_SER = "BehaviorModelGenerationConfiguration";
}
