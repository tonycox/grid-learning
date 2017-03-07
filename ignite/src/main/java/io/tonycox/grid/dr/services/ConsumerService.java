package io.tonycox.grid.dr.services;

import org.apache.ignite.services.Service;

/**
 * @author Anton Solovev
 * @since 01.03.17.
 */
public interface ConsumerService extends Service {
    String NAME = "consumer";

    void poll(String topic);
}
