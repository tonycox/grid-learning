package io.tonycox.grid.main;

import io.tonycox.grid.ConfigurationBuilder;
import org.apache.ignite.Ignition;

/**
 * @author Anton Solovev
 * @since 01.03.17.
 */
public class MainGrid {
    public static void main(String[] args) {

        System.out.println("Main Cluster starts");

        ConfigurationBuilder prepared = ConfigurationBuilder.begin()
                .localhost("127.0.0.1")
                .configureIp("127.0.0.1:47500..47509");

        Ignition.start(prepared.build());
        Ignition.start(prepared.build());
        Ignition.start(prepared.build());
        Ignition.start(prepared.build());
    }
}
