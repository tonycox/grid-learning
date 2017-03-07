package io.tonycox.grid.dr;

import io.tonycox.grid.configs.KafkaFabrique;
import io.tonycox.grid.configs.SpringIgniteConfiguration;
import io.tonycox.grid.dr.services.ConsumerService;
import io.tonycox.grid.dr.services.KafkaConsumerService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @author Anton Solovev
 * @since 30.01.17.
 */
public class DRGrid {
    public static void main(String[] args) {

        System.out.println("DR Cluster starts");

        @Configuration
        class DRConfig extends SpringIgniteConfiguration {
            @Override
            public String localHost() {
                return "127.0.0.2";
            }

            @Override
            public List<String> discoverIps() {
                return Collections.singletonList(localHost() + ":47500..47509");
            }
        }

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
                DRConfig.class, KafkaFabrique.class);

        appContext.getBean(IgniteSpringBean.class);
        appContext.getBean(IgniteSpringBean.class);
        Ignite ignite = appContext.getBean(IgniteSpringBean.class);

        ignite.services().deployClusterSingleton(ConsumerService.NAME, new KafkaConsumerService());
    }
}
