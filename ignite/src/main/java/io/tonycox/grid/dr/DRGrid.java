package io.tonycox.grid.dr;

import io.tonycox.grid.ConfigurationBuilder;
import org.apache.ignite.Ignition;

/**
 * @author Anton Solovev
 * @since 30.01.17.
 */
public class DRGrid {
    public static void main(String[] args) {

        System.out.println("DR Cluster starts");

        ConfigurationBuilder prepared = ConfigurationBuilder.begin()
                .localhost("127.0.0.2")
                .configureIp("127.0.0.2:47500..47509");

        Ignition.start(prepared.build());
        Ignition.start(prepared.build());
        Ignition.start(prepared.build());
        Ignition.start(prepared.build());
    }
}
