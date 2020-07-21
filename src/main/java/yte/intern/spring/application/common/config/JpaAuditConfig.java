package yte.intern.spring.application.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
/*
	Burada @EnableJpaAuditing'i ayrı bir class'a almayı tercih ettim. Main class'ın üzerinde çok hoş durmuyordu,
	ve o class'ın mümkün olduğunca sade olması önemli. O yüzden dışarıya taşıdım.
 */
public class JpaAuditConfig {
}
