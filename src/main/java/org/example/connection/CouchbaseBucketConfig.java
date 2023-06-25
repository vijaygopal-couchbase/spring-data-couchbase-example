package org.example.connection;

import com.couchbase.client.core.env.SecurityConfig;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.example.entity.Airline;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

@Configuration
public class CouchbaseBucketConfig extends AbstractCouchbaseConfiguration {

private final CouchbaseClusterProps clusterProperties;

    public CouchbaseBucketConfig(CouchbaseClusterProps clusterProperties) {
        this.clusterProperties = clusterProperties;
    }

    @Override
    public String getConnectionString() {
        return clusterProperties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return clusterProperties.getUserName();
    }

    @Override
    public String getPassword() {
        return clusterProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return clusterProperties.getBucketTravelSample().getName();
    }

    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping baseMapping) {
        try {
            baseMapping
                    .mapEntity(Airline.class, airlineTemplate());
        } catch (Exception e) {
            throw e;
        }
    }

    @Bean("airlineFactory")
    public CouchbaseClientFactory airlineFactory() {
        ClusterEnvironment clusterEnvironment = ClusterEnvironment.builder()
                        .securityConfig(SecurityConfig.enableTls(false))
        .build();
        return new SimpleCouchbaseClientFactory(getConnectionString(), authenticator(),
                clusterProperties.getBucketTravelSample().getName(),
                clusterProperties.getScopeName(),
                clusterEnvironment
        );
    }

    @Bean("airlineTemplate")
    public CouchbaseTemplate airlineTemplate(){
        return new CouchbaseTemplate(airlineFactory(), new MappingCouchbaseConverter());
    }
}
