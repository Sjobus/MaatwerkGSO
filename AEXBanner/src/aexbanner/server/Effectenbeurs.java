/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.server;

import Interface.IEffectenbeurs;
import Interface.IFonds;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Sibe
 */
public class Effectenbeurs implements IEffectenbeurs {

    @Override
    public List<IFonds> getKoersen() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
