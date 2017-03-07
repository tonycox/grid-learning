package io.tonycox.grid.dr;

import io.tonycox.grid.configs.IgniteCfgBuilder;
import io.tonycox.grid.dr.services.ConsumerService;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

/**
 * @author Anton Solovev
 * @since 02.03.17.
 */
public class ClientReader {
    public static void main(String[] args) {
        IgniteConfiguration cfg = IgniteCfgBuilder.begin()
                .isClient(true)
                .localhost("127.0.0.4")
                .findIp("127.0.0.2:47500..47509")
                .build();

        Ignite ignite = Ignition.start(cfg);

        ConsumerService consumer = ignite.services()
                .serviceProxy(ConsumerService.NAME, ConsumerService.class, false);

        consumer.poll("topic");
    }
}
