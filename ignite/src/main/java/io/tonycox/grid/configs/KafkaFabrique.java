package io.tonycox.grid.configs;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

/**
 * @author Anton Solovev
 * @since 02.03.17.
 */
@Configuration("kafka")
public class KafkaFabrique implements Serializable {

    public KafkaFabrique() {
    }

    @Bean
    public Producer<String, String> producer() {
        try (InputStream props = Resources.getResource("producer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            return new KafkaProducer<>(properties);
        } catch (IOException io) {
            throw new RuntimeException("wooops bad config");
        }
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Consumer<String, String> consumer() {
        try (InputStream props = Resources.getResource("consumer.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            return new KafkaConsumer<>(properties);
        } catch (IOException io) {
            throw new RuntimeException("wooops bad config");
        }
    }

}
