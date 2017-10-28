/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.Posisi;
import util.Warna;

/**
 *
 * @author bukanjoker
 */
public class Lampu {
    private Warna warna;
    private Posisi posisi;
    private long durasi;

    public Lampu(Posisi posisi) {
        this.warna = Warna.merah;
        this.posisi = posisi;
    }

    
    //getter & setter
    
    public Warna getWarna() {
        return warna;
    }

    public void setWarna(Warna warna) {
        this.warna = warna;
    }

    public Posisi getPosisi() {
        return posisi;
    }

    public void setPosisi(Posisi posisi) {
        this.posisi = posisi;
    }

    public long getDurasi() {
        return durasi;
    }

    public void setDurasi(long durasi) {
        this.durasi = durasi;
    }
}
