package progetto.interfaccia.EA;

import com.vividsolutions.jump.workbench.plugin.Extension;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class MyExtension extends Extension{
	public void configure (PlugInContext context) throws Exception{
		new NavEAPlugin().initialize(context);
	}
}
