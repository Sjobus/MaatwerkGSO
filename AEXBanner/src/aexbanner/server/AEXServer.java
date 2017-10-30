/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.server;

import Interface.IEffectenbeurs;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static Constants.Constants.bindingName;
import static Constants.Constants.propertyName;
import static Constants.Constants.portNumber;
/**
 *
 * @author Sibe
 */
public class AEXServer {
    
    
    // referentie naar de registry en de beurs
    private Registry registry = null;
    private IEffectenbeurs beurs = null;
    
    private IRemotePublisherForDomain remotePublisherForDomain;
    
    
    public AEXServer(){        
     
        // maak een beurs
        try
        {
            beurs = new MockEffectenbeurs(this);            
            ((MockEffectenbeurs)beurs).ServerTaak();
        } 
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot create beurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            beurs = null;
        }
        
        try
        {
            remotePublisherForDomain = new RemotePublisher();
            remotePublisherForDomain.registerProperty(propertyName);
        }
        catch(RemoteException ex)
        {
            System.out.println("Server: RemotePublisherForDomain error: " + ex.getMessage());
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
            registry.rebind(bindingName, remotePublisherForDomain);
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
        AEXServer server = new AEXServer();
    }
    
    public IRemotePublisherForDomain getRemotePublisherForDomain()
    {
        return remotePublisherForDomain;
    }
    
}
