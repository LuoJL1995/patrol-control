package com.hik.icv.patrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//WebApplicationInitializer可以看做是Web.xml的替代，它是一个接口。通过实现WebApplicationInitializer，在其中可以添加servlet，listener等，在加载Web项目的时候会加载这个接口实现类，从而起到web.xml相同的作用
public class PatrolControlApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PatrolControlApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PatrolControlApplication.class);
        app.setRegisterShutdownHook(false);
        app.run(args);
    }

}
