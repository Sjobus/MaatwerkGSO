/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.server;

import Interface.IEffectenbeurs;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;

/**
 *
 * @author Sibe
 */
public class AEXServer {
    
    // zet het port nummer
    private static final int portNumber = 1099;
    
    // de binding naam voor de effectenbeurs.
    private static final String bindingName = "AexBanner";
    
    // referentie naar de registry en de beurs
    private Registry registry = null;
    private IEffectenbeurs beurs = null;
    
    public AEXServer(){
        
        //print port nummer
        System.out.println("Server Port Number: " + portNumber);
        
        // maak een beurs
        try
        {
            beurs = new MockEffectenbeurs();
            System.out.println("Server: Beurs aan gemaakt");
        } 
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot create beurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            beurs = null;
        }
        
        //maak een registry 
        try{
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry gemaakt op Portnummer: " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null; 
        }
        
        //bind beurs met gebruik van registry
        try{
            registry.rebind(bindingName, beurs);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind beurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
        
    }
    


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Server met gebruik van Registry start");
        
        //printIPAddresses();
        
        AEXServer server = new AEXServer();
    }
    
}
