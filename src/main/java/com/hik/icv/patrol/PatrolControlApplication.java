package com.hik.icv.patrol;

import com.hik.icv.patrol.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//WebApplicationInitializer可以看做是Web.xml的替代，它是一个接口。通过实现WebApplicationInitializer，在其中可以添加servlet，listener等，在加载Web项目的时候会加载这个接口实现类，从而起到web.xml相同的作用
public class PatrolControlApplication implements CommandLineRunner {


    @Autowired
    NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(PatrolControlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.startServer();
    }

}
