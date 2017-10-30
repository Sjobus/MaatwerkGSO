/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.client;

import Interface.IEffectenbeurs;
import Interface.IFonds;
import aexbanner.server.MockEffectenbeurs;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import Interface.IEffectenbeurs;
import Interface.IFonds;
import java.util.logging.Level;
import java.util.logging.Logger;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import static Constants.Constants.propertyName;
import static Constants.Constants.bindingName;
import static Constants.Constants.LOCAL_HOST;
import static Constants.Constants.portNumber;
import java.beans.PropertyChangeEvent;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Sibe
 */
public class BannerController extends UnicastRemoteObject implements IRemotePropertyListener {

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;
    private Timer pollingTimer;
    
    
    //referenties naar de registry en de beurs
    private Registry registry = null;
    
    private IRemotePublisherForListener remoteListner;
        
    public BannerController(AEXBanner banner) throws RemoteException
    {       
        this.banner = banner;
        try
        {
            registry = LocateRegistry.getRegistry(LOCAL_HOST,portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }
        
       try
       { 
           // Print result locating registry
           if (registry != null) {
               this.remoteListner = (IRemotePublisherForListener) registry.lookup(bindingName);
               System.out.println("Client: Registry located");
           } 
           else 
           {
               System.out.println("Client: Cannot locate registry");
               System.out.println("Client: Registry is null pointer");
               throw new RemoteException();
           }
       }
       catch(RemoteException | NotBoundException ex)
       {
           System.out.println("Client: RemoteException: " + ex.getMessage());
       }
       
       try
       {
           remoteListner.subscribeRemoteListener(this, propertyName);
       }
       catch(RemoteException ex)
       {
           System.out.println("Client: RemoteException: " + ex.getMessage());
       }
    }   

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
    }
    
    private String koersString(List<IFonds> fonds)
    {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");
        for (IFonds f : fonds)
        {
            sb.append(f.getNaam() + " " + df.format(f.getKoers()) + " ");
        }
        return sb.toString();
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent event) throws RemoteException
    {
        banner.setKoersen(koersString((List<IFonds>)event.getNewValue()));
    }

}
