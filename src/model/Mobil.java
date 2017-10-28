/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;
import jdistlib.Beta;
import jdistlib.Gamma;
import jdistlib.InvGamma;
import jdistlib.Kumaraswamy;
import jdistlib.LogNormal;
import jdistlib.Logistic;
import jdistlib.evd.Fretchet;
import jdistlib.evd.GEV;
import jdistlib.evd.GeneralizedPareto;
import util.Posisi;

/**
 *
 * @author bukanjoker
 */
public class Mobil {
    private double intervalDatang, intervalKeluar;
    private long waktuDatang;
    private Posisi posisi;
    
    private double alpha,beta,gamma,delta,lamda,xi,miu,sigma,k,m,a,b;
    private String waktu;

    public Mobil() 
    {
    }
    
    public double random(String pil)
    {
        double val = 0;
        if (pil == "default")
        {
            val = (new Random().nextInt(300)+1)*0.01;
        }
        else if (pil == "gamma")
        {
            //shape = alpha, scale = beta
            val = new Gamma(alpha, beta).random();
        }
        else if (pil == "gen.pareto")
        {
            //shape = k, scale = sigma, location = miu
            val = new GeneralizedPareto(miu, sigma, k).random();
        }
        else if (pil == "log.logistic")
        {
            //shape = alpha, scale = beta
            val = new Logistic(alpha, beta).random();
        }
        else if (pil == "inv.gaussian")
        {
            Random rand = new Random();
            double v = rand.nextGaussian();
            double y = v*v;
            double x = miu + (miu*miu*y)/(2*lamda) - (miu/(2*lamda)) * Math.sqrt(4*miu*lamda*y + miu*miu*y*y);
            double test = rand.nextDouble();
            if (test <= (miu)/(miu+x)) 
            {
                val = x;
            }
            else
            {
                val = (miu*miu)/x;
            }
        }
        else if (pil == "person5")
        {
            //personV == invGamma, shape = alpha, scale = beta
            val = new InvGamma(alpha, beta).random();
        }
        else if (pil == "gev")
        {
            //shape = k, scale = sigma, location = miu
            val = new GEV(miu, sigma, k).random();
        }
        else if (pil == "beta")
        {
            //a,b continuous boundary parameters
            val = new Beta(a, b).random();
        }
        else if (pil == "kumaraswamy")
        {
            //a,b continuous boundary parameters
            val = new Kumaraswamy(a, b).random();
        }
        else if (pil == "fretchet")
        {
            //location = gamma, scale = beta, shape = gamma
            val = new Fretchet(gamma, beta, alpha).random();
        }
        else if (pil == "lognormal")
        {
            val = new LogNormal(sigma, miu).random();
        }
        
        return Math.abs(val);
    }
    
    public void setRandomDatang()
    {
        String random = "default";
        
        if (waktu == "pagi") 
        {
            if (posisi == Posisi.Utara) 
            {
                lamda = 4.0928;
                miu = 4.9426;
                gamma = -0.09513;
                random = "inv.gaussian";
            }
            else if (posisi == Posisi.Selatan)
            {
                alpha = 0.96921;
                beta = 0.29954;
                gamma = 0.60058;
                random = "fretchet";
            }
            else if (posisi == Posisi.Timur)
            {
                k = -0.25963;
                sigma = 1.1727;
                miu = 0.29727;
                random = "gen.pareto";
            }
            else if (posisi == Posisi.Barat)
            {
                alpha = 1.4325;
                beta = 1.6291;
                gamma = 0;
                random = "gamma";
            }
        }
        else if (waktu == "siang")
        {
            if (posisi == Posisi.Utara) 
            {
                k = -0.06173;
                sigma = 2.4319;
                miu = 0.38561;
                random = "gen.pareto";
            }
            else if (posisi == Posisi.Selatan)
            {
                alpha = 1.1639;
                beta = 0.92674;
                gamma = 0;
                random = "person5";
            }
            else if (posisi == Posisi.Timur)
            {
                alpha = 3.7771;
                beta = 0.83092;
                gamma = -0.01931;
                random = "log.logistic";
            }
            else if (posisi == Posisi.Barat)
            {
                k = 0.31958;
                sigma = 0.55925;
                miu = 0.34322;
                random = "gen.pareto";
            }
        }
        else if (waktu == "sore")
        {
            if (posisi == Posisi.Utara) 
            {
                k = 0.39667;
                sigma = 1.8341;
                miu = 2.0655;
                random = "gev";
            }
            else if (posisi == Posisi.Selatan)
            {
                k = 0.39667;
                sigma = 1.8341;
                miu = 2.0655;
                random = "gev";
            }
            else if (posisi == Posisi.Timur)
            {
                k = -0.20781;
                sigma = 1.122;
                miu = 0.33447;
                random = "gen.pareto";
            }
            else if (posisi == Posisi.Barat)
            {
                a = 0.19436;
                b = 2.999;
                random = "default";
            }
        }
        
        intervalDatang = random(random);
    }
    
    public void setRandomKeluar()
    {
        String random = "default";
        
        if (waktu == "pagi") 
        {
            if (posisi == Posisi.Utara) 
            {
                k = -0.04889;
                sigma = 0.97756;
                miu = 0.60407;
                random = "gen.pareto";
            }
            else if (posisi == Posisi.Selatan)
            {
                k = -0.07431;
                sigma = 1.1501;
                miu = 0.3906;
                random = "gen.pareto";
            }
            else if (posisi == Posisi.Timur)
            {
                k = 0.04833;
                sigma = 0.76455;
                miu = 0.31187;
                random = "gen.pareto";
            }
            else if (posisi == Posisi.Barat)
            {
                lamda = 1.1007;
                miu = 0.73985;
                gamma = 0.34592;
                random = "inv.gaussian";
            }
        }
        else if (waktu == "siang")
        {
            if (posisi == Posisi.Utara) 
            {
                alpha = 3.1271;
                beta = 1.3587;
                gamma = 0;
                random = "log.logistic";
            }
            else if (posisi == Posisi.Selatan)
            {
                k = -0.186612;
                sigma = 0.63721;
                miu = 0.26535;
                random = "gen.pareto";
            }
            else if (posisi == Posisi.Timur)
            {
                alpha = 1.1639;
                beta = 0.92674;
                gamma = 0.1852;
                random = "gamma";
            }
            else if (posisi == Posisi.Barat)
            {
                k = 0.16794;
                sigma = 0.26467;
                miu = 0.68367;
                random = "gev";
            }
        }
        else if (waktu == "sore")
        {
            if (posisi == Posisi.Utara) 
            {
                lamda = 6.9353;
                miu = 1.6307;
                gamma = -0.3898;
                random = "inv.gaussian";
            }
            else if (posisi == Posisi.Selatan)
            {
                lamda = 0.65846;
                miu = 0.68173;
                gamma = 0.25697;
                random = "inv.gaussian";
            }
            else if (posisi == Posisi.Timur)
            {
                a = 0.16821;
                b = 84.412;
                random = "kumaraswamy";
            }
            else if (posisi == Posisi.Barat)
            {
                k = -0.26058;
                sigma = 0.94208;
                miu = 0.26639;
                random = "gen.pareto";
            }
        }
        
        intervalKeluar = random(random);
    }
    
    
    
    //getter & setter 

    public double getIntervalDatang() {
        return intervalDatang;
    }

    public void setIntervalDatang(double intervalDatang) {
        this.intervalDatang = intervalDatang;
    }

    public double getIntervalKeluar() {
        return intervalKeluar;
    }

    public void setIntervalKeluar(double intervalKeluar) {
        this.intervalKeluar = intervalKeluar;
    }

    public long getWaktuDatang() {
        return waktuDatang;
    }

    public void setWaktuDatang(long waktuDatang) {
        this.waktuDatang = waktuDatang;
    }

    public Posisi getPosisi() {
        return posisi;
    }

    public void setPosisi(Posisi posisi) {
        this.posisi = posisi;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getLamda() {
        return lamda;
    }

    public void setLamda(double lamda) {
        this.lamda = lamda;
    }

    public double getXi() {
        return xi;
    }

    public void setXi(double xi) {
        this.xi = xi;
    }

    public double getMiu() {
        return miu;
    }

    public void setMiu(double miu) {
        this.miu = miu;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
