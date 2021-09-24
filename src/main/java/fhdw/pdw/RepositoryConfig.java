package fhdw.pdw;

import fhdw.pdw.repository.SoftDeleteRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "fhdw.pdw.repository",
    repositoryBaseClass = SoftDeleteRepositoryImpl.class)
public class RepositoryConfig {}
