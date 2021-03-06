/*
 * DexPatcher - Copyright 2015-2017 Rodrigo Balerdi
 * (GNU General Public License version 3 or later)
 *
 * DexPatcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 */

package lanchon.dexpatcher.core;

import java.util.HashMap;
import java.util.Map;

import lanchon.dexpatcher.core.logger.BasicLogger;
import lanchon.dexpatcher.core.logger.Logger;

import static lanchon.dexpatcher.core.logger.Logger.Level.*;

public class Context {

	public static final Logger.Level DEFAULT_LOG_LEVEL = WARN;
	public static final String DEFAULT_ANNOTATION_PACKAGE = "lanchon.dexpatcher.annotation";

	private Logger logger;
	private String annotationPackage = DEFAULT_ANNOTATION_PACKAGE;
	private boolean dexTagSupported;
	private String sourceCodeRoot;

	private Map<String, Action> actionMap;
	private String tagTypeDescriptor;

	public Context() {
		this(DEFAULT_LOG_LEVEL);
	}

	public Context(Logger.Level logLevel) {
		Logger logger = new BasicLogger();
		logger.setLogLevel(logLevel);
		this.logger = logger;
	}

	public Context(Logger logger) {
		this.logger = logger;
	}

	// Properties

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getAnnotationPackage() {
		return annotationPackage;
	}

	public void setAnnotationPackage(String value) {
		annotationPackage = value;
		Action[] actions = Action.values();
		actionMap = new HashMap<>(actions.length);
		for (Action action : actions) {
			actionMap.put(getMarkerTypeDescriptor(action.getMarker()), action);
		}
		tagTypeDescriptor = getMarkerTypeDescriptor(Marker.TAG);
	}

	public boolean isDexTagSupported() {
		return dexTagSupported;
	}

	public void setDexTagSupported(boolean dexTagSupported) {
		this.dexTagSupported = dexTagSupported;
	}

	public String getSourceCodeRoot() {
		return sourceCodeRoot;
	}

	public void setSourceCodeRoot(String sourceCodeRoot) {
		this.sourceCodeRoot = sourceCodeRoot;
	}

	// Extras

	private String getMarkerTypeDescriptor(Marker marker) {
		return Util.getTypeDescriptorFromName(annotationPackage + "." + marker.getClassName());
	}

	public Action getActionFromMarkerTypeDescriptor(String typeDescriptor) {
		return actionMap.get(typeDescriptor);
	}

	public String getTagTypeDescriptor() {
		return tagTypeDescriptor;
	}

}
