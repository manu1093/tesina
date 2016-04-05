/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.prova;

import com.vividsolutions.jump.workbench.plugin.Extension;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

/**
 *
 * @author manu
 */
public class ProvaExtension extends Extension{

    public void configure(PlugInContext context) throws Exception {
       new ProvaPlugIn().initialize(context);
    }
    
}
