package net.archasmiel.skufapi.config;

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:.env")
class EnvConfig
