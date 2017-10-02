/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.server;

import Interface.IEffectenbeurs;
import Interface.IFonds;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Sibe
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs {

   ArrayList<IFonds> koersen;
   
   private Registry registry = null;

   public MockEffectenbeurs() throws RemoteException
   {
       koersen = new ArrayList<>();
       koersen.add(new Fonds("Shell", 7.81));
       koersen.add(new Fonds("Unilever", 91.88));
       koersen.add(new Fonds("EA", 1.81));
       koersen.add(new Fonds("Dat eene bedrijf", 99.99));
   }

   @Override
   public ArrayList<IFonds> getKoersen() throws RemoteException
   {
       genereerKoersen();
       return koersen;
   }

   public void genereerKoersen()
   {
       Random r = new Random();
       for (IFonds f : koersen)
       {
           double nieuweKoers = f.getKoers() * (0.95 + (1.05 - 0.95) * r.nextDouble());
           f.setKoers(nieuweKoers);
       }
    
    }
}
