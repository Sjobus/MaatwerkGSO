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

/**
 *
 * @author Sibe
 */
public class BannerController {

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;
    private Timer pollingTimer;

    public BannerController(AEXBanner banner) throws RemoteException {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                DecimalFormat df = new DecimalFormat("#.00");
                for (IFonds f : effectenbeurs.getKoersen()) {
                    sb.append(f.getNaam() + " " + df.format(f.getKoers()) + " ");
                }
                banner.setKoersen(sb.toString());
            };
        };
        pollingTimer.scheduleAtFixedRate(task, 1000,2000);
        // TODO
    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
    }

}
