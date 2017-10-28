/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.Jalan;
import model.Lampu;
import util.Posisi;
import util.Warna;
import view.UI;

/**
 *
 * @author bukanjoker
 */
public class Manager extends Thread {
    private Lampu[] lampu;
    private Jalan[] jalan;
    private int current = 0;
    private int nextCur = 0;
    private int iterasi;
    private String kondisi;
    private String waktu;
    private int buffer;
    private long overheat;
    private Serial serial;
    private UI gui;
    
    private long running,start;
    private int indx = 0;
    
    public Manager()
    {
        jalan = new Jalan[4];
        jalan[0] = new Jalan(Posisi.Selatan);
        jalan[1] = new Jalan(Posisi.Utara);
        jalan[2] = new Jalan(Posisi.Timur);
        jalan[3] = new Jalan(Posisi.Barat);
        
    }
    
    public void init()
    {        
        //inisiasi jalan & lampu
        initJalan();
        initLampu();
    }
    
    @Override
    public void run()
    {
        start = System.currentTimeMillis();
        
        System.out.println("POSISI HIJAU | AVG WAIT | JALAN 1 | JALAN 2 | JALAN 3 | JALAN 4");
        
        while (running < 1800000) 
        {
            if (jalan[0].getListMobil().size() != 0 || jalan[1].getListMobil().size() != 0 || jalan[2].getListMobil().size() != 0 || jalan[3].getListMobil().size() != 0) 
            {
                LampuAction();
            }
            else
            {
                delay(1000);
            }
            
            running = System.currentTimeMillis() - start;
        }
        System.out.println(running);
        stopRun();
    }
    
    public void HRRNCalculation(Jalan jalan)
    {
        long start = System.currentTimeMillis();
        
        //ratio = (wait+servis)/servis
        
        double servis = lampu[current].getDurasi();
        double wait = jalan.getWait();
        
        jalan.setWait(wait);
        
        double result = (wait+servis)/servis;
        
        jalan.setRatio(result);
        
        long end = System.currentTimeMillis();
        overheat = end - start;
        
    }
    
    public void LampuAction()
    {
        long durasi;
        long interval;
        
        //set warna
        for (int i = 0; i < jalan.length; i++) 
        {
            if (i != current) 
            {
                lampu[i].setWarna(Warna.merah);
                
            }
            else
            {
                lampu[i].setWarna(Warna.HIJAU);
                
                //print
                double rataTunggu = (
                    jalan[0].getWait()
                    +jalan[1].getWait()
                    +jalan[2].getWait()
                    +jalan[3].getWait()
                    )/4;
                System.out.println(indx+++". "+jalan[i].getPosisi()+"  |  "+rataTunggu*0.001+"  |  "+jalan[0].getListMobil().size()+" | "+jalan[1].getListMobil().size()+" | "+jalan[2].getListMobil().size()+" | "+jalan[3].getListMobil().size());
                
                //send serial
                if (serial.getConnected()) 
                {
                    serial.kirimData(String.valueOf(i));
                }
            }
        }
        
        //set gui
        delay(1000);
        gui.getTrafficlight()[0][current].setVisible(false);
        gui.getTrafficlight()[1][current].setVisible(true);
        delay(2000);
        gui.getTrafficlight()[1][current].setVisible(false);
        gui.getTrafficlight()[2][current].setVisible(true);
        
        //set delay
        durasi = lampu[current].getDurasi()-3000;
        
        while (durasi > 0)
        {
            if (jalan[current].getListMobil().size() == 0) 
            {
                interval = 10;
                delay(interval);
                durasi = durasi - interval;
            }
            else
            {
                interval = Math.round(jalan[current].getListMobil().get(0).getIntervalKeluar()*1000);
                
                if (durasi - interval > 0)
                {
                    delay(interval);
                    durasi = durasi - interval;
//                    double t = jalan[current].getDurasi() + jalan[current].getListMobil().get(0).getIntervalKeluar();
//                    jalan[current].setDurasi(t);
                    if (jalan[current].getListMobil().size() % 3 == 2) 
                    {
//                        System.out.println("out");
                        gui.getCars()[current][jalan[current].getListMobil().size()/3].setVisible(false);
                    }
                    jalan[current].getListMobil().remove(0);
                    jalan[current].setOut(jalan[current].getOut()+1);
                }
                else
                {
                    delay(durasi);
                    durasi = 0;
                }
            }
        }
        
        //set wait
        for (int i = 0; i < jalan.length; i++)
        {
            if (!jalan[i].getListMobil().isEmpty())
            {
                double wait = System.currentTimeMillis() - jalan[i].getListMobil().get(0).getWaktuDatang();
                jalan[i].setWait(wait);
            }
            else
            {
                jalan[i].setWait(0);
            }
        }
        
//        print();
        
        //set gui
        gui.getTrafficlight()[2][current].setVisible(false);
        gui.getTrafficlight()[1][current].setVisible(true);
        delay(2000);
        gui.getTrafficlight()[1][current].setVisible(false);
        gui.getTrafficlight()[0][current].setVisible(true);
        
        //set current
        if (kondisi == "dinamis") 
        {
            double ratioMax = 0;

            for (int i = 0; i < jalan.length; i++) 
            {
                if (i != current)
                {
                    //HRRN Calculation
                    HRRNCalculation(jalan[i]);
                    
                    //sort max ratio
                    if (jalan[i].getRatio() > ratioMax) 
                    {
                        ratioMax = jalan[i].getRatio();
                        nextCur = i;
                    }
                }
            }
        }
        else //statis
        {
            if (iterasi < jalan.length-1) 
            {
                iterasi++;
            }
            else
            {
                iterasi = 0;
            }
            nextCur = iterasi;
        }

        current = nextCur;
    }
    
    public void print()
    {
        //posisi lampu hijau & durasi
        System.out.println("LAMPU HIJAU:"+lampu[current].getPosisi()+", durasi: "+lampu[current].getDurasi()*0.001+"detik");
        
        //data yang ingin ditampilkan
        for (int i = 0; i < jalan.length; i++) 
        {
            System.out.print(
                    "["+jalan[i].getPosisi()+"]"
                    + "Buffer:"+jalan[i].getListMobil().size()
                    + " Ratio:"+jalan[i].getRatio()
                    + " Wait:"+jalan[i].getWait()/1000+" detik"
                    + " In/Out:"+jalan[i].getIn()+"/"+jalan[i].getOut()
//                    + " "+jalan[i].getDurasi()
                    );
            
            if (jalan[i].getListMobil().size() == buffer) 
            {
                System.out.println(" [DEADLOCK]");
            }
            else
            {
                System.out.println("");
            }
            
//            jalan[i].setDurasi(0);
        }
        
        //rata rata waktu tunggu
        double rataTunggu = (
                jalan[0].getWait()
                +jalan[1].getWait()
                +jalan[2].getWait()
                +jalan[3].getWait()
                )/4;
        
        System.out.println("Rata-Rata waktu tunggu: "+rataTunggu*0.001+"detik");
        System.out.println("");
    }
    
    public void initJalan()
    {
        for (int i = 0; i < jalan.length; i++) 
        {
            jalan[i].setGui(gui);
            jalan[i].setNomor(i);
            jalan[i].setWaktu(waktu);
            jalan[i].setBufferSize(buffer);
            jalan[i].start();
        }
    }
    
    public void initLampu()
    {
        lampu = new Lampu[4];
        lampu[0] = new Lampu(Posisi.Selatan);
        lampu[1] = new Lampu(Posisi.Utara);
        lampu[2] = new Lampu(Posisi.Timur);
        lampu[3] = new Lampu(Posisi.Barat);
    }
    
    public void stopRun()
    {
        for (int i = 0; i < jalan.length; i++) 
        {
            jalan[i].stop();
        }
        System.out.println("program stopped!");
    }
    
    public void setDurasi(long t1, long t2, long t3, long t4)
    {
        lampu[0].setDurasi(t1*1000);
        lampu[1].setDurasi(t2*1000);
        lampu[2].setDurasi(t3*1000);
        lampu[3].setDurasi(t4*1000);
    }
    
    public void setIntervalDtg(double t1, double t2, double t3, double t4)
    {
        jalan[0].setIntervalDtg(t1);
        jalan[1].setIntervalDtg(t2);
        jalan[2].setIntervalDtg(t3);
        jalan[3].setIntervalDtg(t4);
    }
    
    public void setIntervalKlr(double t1, double t2, double t3, double t4)
    {
        jalan[0].setIntervalKlr(t1);
        jalan[1].setIntervalKlr(t2);
        jalan[2].setIntervalKlr(t3);
        jalan[3].setIntervalKlr(t4);
    }
    
    public void delay(long t)
    {
        try {
            Thread.sleep(t);
        } catch (InterruptedException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void end()
    {
        for (int i = 0; i < jalan.length; i++)
        {
            jalan[i].stop();
        }
    }
    
    
    //setter & getter

    public Lampu[] getLampu() {
        return lampu;
    }

    public void setLampu(Lampu[] lampu) {
        this.lampu = lampu;
    }

    public Jalan[] getJalan() {
        return jalan;
    }

    public void setJalan(Jalan[] jalan) {
        this.jalan = jalan;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public long getOverheat() {
        return overheat;
    }

    public void setOverheat(long overheat) {
        this.overheat = overheat;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public Serial getSerial() {
        return serial;
    }

    public void setSerial(Serial serial) {
        this.serial = serial;
    }

    public UI getGui() {
        return gui;
    }

    public void setGui(UI gui) {
        this.gui = gui;
    }
}
