/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Posisi;
import view.UI;

/**
 *
 * @author bukanjoker
 */
public class Jalan extends Thread {
    private ArrayList<Mobil> listMobil;
    private double ratio;
    private Posisi posisi;
    private int bufferSize;
    private double wait;
    private int in,out;
    private String waktu;
    private double intervalDtg=0,intervalKlr=0;
    
    private UI gui;
    private int nomor;
//    private double durasi;

    public Jalan(Posisi posisi) 
    {
        listMobil = new ArrayList<Mobil>();
        this.posisi = posisi;
    }
    
    @Override
    public void run()
    {
        while (true)
        {
            add();
            if (listMobil.size() % 3 == 0)
            {
                gui.getCars()[nomor][listMobil.size()/3-1].setVisible(true);
            }
            
            if (listMobil.size() == bufferSize) 
            {
                gui.getCars()[nomor][10].setVisible(true);
            }
            else
            {
                gui.getCars()[nomor][10].setVisible(false);
            }
        }
    }
    
    public void add()
    {
        if (listMobil.size() < bufferSize) 
        {
            //mobil inisiasi
            Mobil m = new Mobil();
            m.setPosisi(posisi);
            m.setWaktu(waktu);
            m.setRandomDatang();
            m.setRandomKeluar();
            
            if (intervalDtg != 0) 
            {
                m.setIntervalDatang(intervalDtg);
            }
            if (intervalKlr != 0) 
            {
                m.setIntervalKeluar(intervalKlr);
            }
            
            try 
            {
                sleep(Math.round(m.getIntervalDatang()*1000));
            }
            catch (InterruptedException ex) {
                Logger.getLogger(Jalan.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            m.setWaktuDatang(System.currentTimeMillis());
            listMobil.add(m);
            in++; //hitung banyak mobil yang masuk
//            System.out.println("Mobil["+posisi+"]masuk. Jumlah:"+listMobil.size());
        }
    }
    
    
    
    //getter & setter
    
    public double getWait() {
        return wait;
    }

    public void setWait(double wait) {
        this.wait = wait;
    }
    
    public ArrayList<Mobil> getListMobil() {
        return listMobil;
    }

    public void setListMobil(ArrayList<Mobil> listMobil) {
        this.listMobil = listMobil;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Posisi getPosisi() {
        return posisi;
    }

    public void setPosisi(Posisi posisi) {
        this.posisi = posisi;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public double getIntervalDtg() {
        return intervalDtg;
    }

    public void setIntervalDtg(double intervalDtg) {
        this.intervalDtg = intervalDtg;
    }

    public double getIntervalKlr() {
        return intervalKlr;
    }

    public void setIntervalKlr(double intervalKlr) {
        this.intervalKlr = intervalKlr;
    }
//
//    public double getDurasi() {
//        return durasi;
//    }
//
//    public void setDurasi(double durasi) {
//        this.durasi = durasi;
//    }

    public UI getGui() {
        return gui;
    }

    public void setGui(UI gui) {
        this.gui = gui;
    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }
    
    
}
