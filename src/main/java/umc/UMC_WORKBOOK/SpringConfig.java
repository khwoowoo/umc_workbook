package umc.UMC_WORKBOOK;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.transport.ProxyProvider;
import umc.UMC_WORKBOOK.repository.*;
import umc.UMC_WORKBOOK.service.PostService;
import umc.UMC_WORKBOOK.service.UserSerivce;

import javax.sql.DataSource;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.http.HttpClient;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Bean
    public UserSerivce userSerivce(){
        return new UserSerivce(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new JdbcTemplateUserRepository(dataSource);
        //return new MemoryUserRepository();
    }

    @Bean
    public PostService postService(){
        return  new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository(){
        return new JdbcTemplatePostRepository(dataSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.host.com", 8080));
        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);
    }


}
