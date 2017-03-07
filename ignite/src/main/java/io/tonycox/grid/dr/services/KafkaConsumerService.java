package io.tonycox.grid.dr.services;

import org.apache.ignite.Ignite;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;

/**
 * @author Anton Solovev
 * @since 03.03.17.
 */
public class KafkaConsumerService implements ConsumerService {

    private transient Subcriber subcriber;

    @IgniteInstanceResource
    private transient Ignite ignite;

    @Override
    public void cancel(ServiceContext ctx) {
        subcriber.stop();
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        subcriber = new Subcriber(ignite);
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        subcriber.start();
    }

    @Override
    public void poll(String topic) {
        subcriber.poll(topic);
    }
}
