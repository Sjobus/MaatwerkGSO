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

/**
 *
 * @author Sibe
 */
public class BannerController {

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;
    private Timer pollingTimer;
    
    // Set binding name voor de aex banner
    private static final String bindingName = "AexBanner";
    
    //referenties naar de registry en de beurs
    private Registry registry = null;
        
    public BannerController(AEXBanner banner){
        
        RMIClient("127.0.0.1",1099);
        this.banner = banner;
        
        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    StringBuilder sb = new StringBuilder();
                    DecimalFormat df = new DecimalFormat("#.00");
                    for (IFonds f : effectenbeurs.getKoersen()) {
                        sb.append(f.getNaam() + " " + df.format(f.getKoers()) + " ");
                    }
                    banner.setKoersen(sb.toString());
                } catch (RemoteException ex) {
                    System.out.println("Client: Fout bij de run in banner controller.");
                    System.out.println("Client: error: " + ex.getMessage());
                }
            };
        };
        pollingTimer.scheduleAtFixedRate(task, 1000,2000);
        // TODO
    }
    
    public void RMIClient(String ipAddress, int portNumber){
        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);
        
        try
        {
            registry = LocateRegistry.getRegistry(ipAddress,portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }
        
        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else 
        {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }
        
        //Bind beurs using registry
        if(registry != null)
        {
            try
            {
                System.out.println("voor regestry lookup");
                effectenbeurs = (IEffectenbeurs) registry.lookup(bindingName);                
                System.out.println("na regestry lookup");
            }
            catch(RemoteException ex)
            {
                System.out.println("Client: Cannot bind beurs");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                effectenbeurs = null;
            }
            catch(NotBoundException ex)
            {
                System.out.println("Client: Cannot bind beurs");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                effectenbeurs = null;
            }
        }
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
    }

}
