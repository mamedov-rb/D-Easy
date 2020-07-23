package com.rmamedov.deasy.addressetl.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.rmamedov.deasy.addressetl.model.AddressCheckResult;
import com.rmamedov.deasy.addressetl.serialize.AddressCheckResultSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HazelcastConfig {

    public static final String ADDRESS_CHECK_RESULT_MAP = "address-check-result-map";
    public static final String ADDRESS_CHECK_RESULT_CACHE = "address-check-result-cache";

    private final AddressCheckResultSerializer checkResultSerializer;

    @Bean
    public IMap<String, AddressCheckResult> addressCheckResultIMap() {
        return hazelcastInstance().getMap(ADDRESS_CHECK_RESULT_MAP);
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        final Config config = new Config(ADDRESS_CHECK_RESULT_CACHE);
        config.addMapConfig(mapConfig());
        config.getSerializationConfig().addSerializerConfig(serializerConfig());
        return Hazelcast.newHazelcastInstance(config);
    }

    private SerializerConfig serializerConfig() {
        return new SerializerConfig()
                .setTypeClass(AddressCheckResult.class)
                .setImplementation(checkResultSerializer);
    }

    private MapConfig mapConfig() {
        final MapConfig mapConfig = new MapConfig(ADDRESS_CHECK_RESULT_MAP);
        mapConfig.setTimeToLiveSeconds(360);
        mapConfig.setMaxIdleSeconds(180);
        return mapConfig;
    }
}
