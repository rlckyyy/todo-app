package relucky.code.technicaltask2.infrastructure.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    private @Value("${minio.url}") String url;
    private @Value("${minio.access-key}") String accessKey;
    private @Value("${minio.secret-key}") String secretKey;
    private @Value("${minio.port}") Integer port;

    @Bean
    public MinioClient getMinioClient(){
        return MinioClient.builder()
                .endpoint(url, port, false)
                .credentials(accessKey,secretKey)
                .build();
    }
}
