/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 *
 * @author Sibe
 */
public interface IEffectenbeurs extends Remote{
    
    public List<IFonds> getKoersen() throws RemoteException;
}
