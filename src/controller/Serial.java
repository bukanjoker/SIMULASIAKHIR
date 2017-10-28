/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author bukanjoker
 */
import gnu.io.*;
import java.awt.HeadlessException;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Serial implements SerialPortEventListener {
    private Enumeration port = null;
    private final HashMap portMap = new HashMap();
    private CommPortIdentifier portIdentifier = null;
    private SerialPort serialPort = null;
    private InputStream inPut = null;
    private OutputStream outPut = null;
    private boolean serialConnected = false;
    final static int TIMEOUT = 2000;
    String dataIn = "";
    String statusPort = "";
    char dataSerial = ' ';
    String selectedPort;
    ArrayList<String> portName = new ArrayList<String>();
//    String dataComplete;

    public Serial() {
        
    }
    
    public void cekSerialPort() 
    {
        port = CommPortIdentifier.getPortIdentifiers();
        while (port.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier) port.nextElement();
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portName.add(curPort.getName());
//                System.out.println(curPort.getName());
//                window.jCommPort.addItem(curPort.getName());
                portMap.put(curPort.getName(), curPort);
            }
        }
    }
    
    public void connect(String port) 
    {
        selectedPort = port;
        portIdentifier = (CommPortIdentifier) portMap.get(selectedPort);
        CommPort commPort = null;
        try 
        {
            commPort = portIdentifier.open(null, TIMEOUT);
            serialPort = (SerialPort) commPort;
            setConnected(true);
            statusPort = "PORT connect successfully";            
            JOptionPane.showMessageDialog(null, statusPort);
//            window.bConn.setText("Disconnect");
        } 
        catch (PortInUseException e) 
        {
            statusPort = selectedPort + " is in use. (" + e.toString() + ")";
            JOptionPane.showMessageDialog(null, statusPort);
        } 
        catch (Exception e) 
        {
//            statusPort = "Failed to open " + selectedPort + "(" + e.toString() + ")";
            statusPort = "No PORT Selected";
            JOptionPane.showMessageDialog(null, statusPort);
        }
    }

    public void disconnect() 
    {
         try 
         {
            serialPort.removeEventListener();
            serialPort.close();
            inPut.close();
            setConnected(false);
            statusPort = "PORT disconnect successfully";            
            JOptionPane.showMessageDialog(null, statusPort);
//            window.bConn.setText("Connect");
        } 
         catch (IOException e) 
         {
            statusPort = "Failed to close " + serialPort.getName() + "(" + e.toString() + ")";
            JOptionPane.showMessageDialog(null, statusPort);
        } 
         catch (HeadlessException e)
         {
            statusPort = "Failed to close " + serialPort.getName() + "(" + e.toString() + ")";
            JOptionPane.showMessageDialog(null, statusPort);
        }
    }

    public boolean initIOStream() 
    {
        boolean successful = false;
        try 
        {
            inPut = serialPort.getInputStream();
            outPut = serialPort.getOutputStream();
            
            successful = true;
            return successful;
        } 
        catch (IOException e)
        {
            statusPort = "I/O Streams failed to open. (" + e.toString() + ")";
            JOptionPane.showMessageDialog(null, statusPort);
            return successful;
        }
    }

    public void sendChar(Byte a)
    {
        try 
        {
            outPut.write(a);
//            outPut.flush();
        } 
        catch (IOException ex)
        {
            System.out.println("Kirim Gagal");
        }
    }
    
    public void kirimData(String str)
    {
        for(int i=0; i<str.length();i++)
        {
            sendChar((byte)str.charAt(i));
        }
    }
    
    public void initListener() 
    {
        try 
        {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } 
        catch (TooManyListenersException e) 
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    @Override
    public void serialEvent(SerialPortEvent spe)
    {
//        UI main = new UI();
//        if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) 
//        {
//            kirimData("321");         
//        }
    }

    public ArrayList<String> getPortName() {
        return portName;
    }
    
    final public boolean getConnected() 
    {
        return serialConnected;
    }

    public void setConnected(boolean serialConnected) 
    {
        this.serialConnected = serialConnected;
    }
}
