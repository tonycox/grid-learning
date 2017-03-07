package io.tonycox.grid.dr.services;

import io.tonycox.grid.configs.KafkaFabrique;
import org.apache.ignite.Ignite;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.SpringResource;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Collections;

/**
 * @author Anton Solovev
 * @since 03.03.17.
 */
public class Subcriber {

    private final Ignite ignite;
    private boolean runnable = false;

    public Subcriber(Ignite ignite) {
        this.ignite = ignite;
    }

    public void stop() {
        runnable = false;
    }

    public void start() {
        runnable = true;
    }

    public void poll(String topic) {
        IgniteRunnable job = new IgniteRunnable() {
            @SpringResource(resourceName = "kafka")
            private transient KafkaFabrique kafka;

            @Override
            public void run() {
                Consumer<String, String> consumer = kafka.consumer();
                consumer.subscribe(Collections.singletonList(topic));
                while (runnable) {
                    ConsumerRecords<String, String> poll = consumer.poll(1000);
                    poll.forEach(System.out::println);
                }
            }
        };
        ignite.compute().broadcast(job);
    }
}
