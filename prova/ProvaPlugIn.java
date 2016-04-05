/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progetto.prova;
import com.vividsolutions.jump.workbench.plugin.*;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.PopupMenu;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
/**
 *
 * @author manu
 */
public class ProvaPlugIn extends AbstractPlugIn {
    private Attuatore a;
    private Button tipiDiStrade,nomeStrade;
    @SuppressWarnings("deprecation")
	@Override
    public void initialize(PlugInContext context) throws Exception {
           context.getFeatureInstaller().addMainMenuItem(this,new String[] { "Tools"  }, "Carica dati Test", false, null, null);
           
}
    @Override
    public boolean execute(PlugInContext context) throws Exception {
       // JInternalFrame i=new JInternalFrame("mio plug");
          a=new Attuatore(context);
   /*     tipiDiStrade=new Button("tipi di strade");
        nomeStrade=new Button("nomeStrade");
        i.setVisible(true);   
        i.setSize(300, 300);
        i.setSelected(true);
    i.setLayout(new FlowLayout());
    i.add(tipiDiStrade);  
    i.add(nomeStrade);
    nomeStrade.addMouseListener(new GestoreEventi());
    tipiDiStrade.addMouseListener(new GestoreEventi());
    context.getWorkbenchFrame().addInternalFrame(i);*/
   
return true;
}
    
private class GestoreEventi implements MouseListener{

        public void mouseClicked(MouseEvent me) {
            
            if(me.getSource()==tipiDiStrade){                
                a.tipostrade();
            }
            if(me.getSource()==nomeStrade){
            	a.nomeStrade();
            }
        }

        public void mousePressed(MouseEvent me) {
           
        }

        public void mouseReleased(MouseEvent me) {
            
        }

        public void mouseEntered(MouseEvent me) {
            
        }

        public void mouseExited(MouseEvent me) {
            
        }
    
}

}

