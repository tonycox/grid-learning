package io.tonycox.grid.kafka;

/**
 * @author Anton Solovev
 * @since 01.03.17.
 */
public class KafkaCluster {
    public static void main(String[] args) {
        EmbeddedKafka embeddedKafka = new EmbeddedKafka(3, 2182, 9092);
        embeddedKafka.setUp();
    }
}
