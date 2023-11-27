package relucky.code.technicaltask2.infrastructure.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${minio.url}")
    private String url;
    @Value("${minio.access-key}")
    private String accessKey;
    @Value("${minio.secret-key}")
    private String secretKey;
    @Value("${minio.port}")
    private Integer port;

    @Bean
    public MinioClient getMinioClient(){
        return MinioClient.builder()
                .endpoint(url, port, false)
                .credentials(accessKey,secretKey)
                .build();
    }
}
