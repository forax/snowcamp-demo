package io.snowcamp.papaya.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Configuration {
    private int port;

    public int getPort() {
        return port;
    }

    @XmlElement
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Configuration [port=" + port + "]" ;
    }
}
