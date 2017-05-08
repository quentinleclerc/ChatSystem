package network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpGetter {

        public InetAddress getIP() {
            Enumeration<NetworkInterface> e = null;
            InetAddress ip = null;
            try {
                e = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException se) {
                se.printStackTrace();
            }
            while(e.hasMoreElements())
            {
                NetworkInterface n = e.nextElement();
                boolean connected = false;
                try {
                    connected = n.isUp() & !n.isLoopback();
                } catch (SocketException se) {
                    se.printStackTrace();
                }
                if (connected){
                    Enumeration<InetAddress> ee = n.getInetAddresses();
                    while (ee.hasMoreElements())
                    {
                        ip = ee.nextElement();
                    }
                }
            }
            return ip;
        }



}
