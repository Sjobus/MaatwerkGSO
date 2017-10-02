/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.server;

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

/**
 *
 * @author Sibe
 */
public class AEXServer {
    
    // zet het port nummer
    private static final int portNumber = 1099;
    
    // de binding naam voor de effectenbeurs.
    private static final String bindingName = "Aex Banner Server";
    
    // referentie naar de registry en de beurs
    private Registry registry = null;
    private MockEffectenbeurs beurs = null;
    
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
    
    // Print IP addresses and network interfaces
    private static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    System.out.println("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        try {
            System.out.println("Server: Full list of network interfaces:");
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    System.out.println("        " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException ex) {
            System.out.println("Server: Cannot retrieve network interface list");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Server met gebruik van Registry start");
        
        printIPAddresses();
        
        AEXServer server = new AEXServer();
    }
    
}
