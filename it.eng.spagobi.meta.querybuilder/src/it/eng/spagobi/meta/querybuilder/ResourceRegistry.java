/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

**/
package it.eng.spagobi.meta.querybuilder;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;

/**
 * @author Andrea Gioia (andrea.gioia@eng.it)
 *
 */
public class ResourceRegistry {
	
	public static final String IMAGE_ROOT_FOOLDER = "icons";
	public static final String CONF_ROOT_FOOLDER = "conf";
	
	public static Map<String,String> images;
	
	static {
		images = new HashMap<String,String>();
		images.put("checked", "checked.png");
		images.put("unchecked", "unchecked.png");
	}
	
	public static Image getImage(String imageKey) {
		Image image;
		
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID); 
		URL imageURL = BundleUtility.find(bundle, getImagePath(imageKey)); 
		image = ImageDescriptor.createFromURL(imageURL).createImage(); 
		
		return image;
	}
	
	private static String getImagePath(String imageKey) {
		String imagePath;
		
		imagePath = null;
		if(images.containsKey(imageKey)) {
			imagePath =  IMAGE_ROOT_FOOLDER + "/" + images.get(imageKey);
		} else {
			imagePath =  IMAGE_ROOT_FOOLDER + "/" + imageKey;
		}
		
		return imagePath;
	}
}
