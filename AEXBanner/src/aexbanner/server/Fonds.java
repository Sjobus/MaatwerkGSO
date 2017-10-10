/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner.server;

import Interface.IFonds;

/**
 *
 * @author Sibe
 */
class Fonds implements IFonds  {

    String naam;
    double koers;

    public Fonds(String naam, double koers) {
        this.naam = naam;
        this.koers = koers;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    @Override
    public double getKoers() {
        return koers;
    }

    public void setKoers(double nieuweKoers) {
        this.koers = nieuweKoers;
    }

}
