package org.kgsnipes.site.util;

import org.dom4j.Document;

public class SiteMonitorConfig {
	
	private static Document configDoc;

	public static Document getConfigDoc() {
		return configDoc;
	}

	public static void setConfigDoc(Document configDoc) {
		SiteMonitorConfig.configDoc = configDoc;
	}

	
	
}
