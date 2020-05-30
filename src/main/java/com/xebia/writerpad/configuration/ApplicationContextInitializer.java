package com.xebia.writerpad.configuration;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextInitializer
{
    @Bean
    public StringSimilarityService stringSimilarityService()
    {
        SimilarityStrategy strategy = new JaroWinklerStrategy();

        return new StringSimilarityServiceImpl(strategy);
    }
}
