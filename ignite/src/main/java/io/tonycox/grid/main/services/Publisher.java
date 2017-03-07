package io.tonycox.grid.main.services;

import io.tonycox.grid.configs.KafkaFabrique;
import org.apache.ignite.Ignite;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.SpringResource;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import javax.cache.Cache;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Anton Solovev
 * @since 02.03.17.
 */
public class Publisher {

    private final Ignite ignite;
    private int partitions;

    public Publisher(Ignite ignite) {
        this.ignite = ignite;
    }

    public void publish(final String topic, Map<String, Collection<Cache.Entry<?, ?>>> updates) {
        IgniteRunnable job = new IgniteRunnable() {
            @SpringResource(resourceName = "kafka")
            private transient KafkaFabrique kafka;

            @Override
            public void run() {

                Producer<String, String> producer = kafka.producer();
                partitions = producer.partitionsFor(topic).size();
                try {
                    Future<RecordMetadata> future = producer.send(new ProducerRecord(topic, 0, null, null));
                    long offset = future.get().offset();
                    int part = Long.hashCode(offset) % partitions;
                    String value = updates.toString();

                    producer.send(new ProducerRecord<>(topic, part, String.valueOf(offset), value));
                    producer.flush();
                    System.out.println(value + " sent in topic " + topic);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        ignite.compute().broadcast(job);
    }
}
