package com.bladestepapp.lifexpactivityservicetest.e2e.annotation;

import com.bladestepapp.lifexpactivityservicetest.config.WireMockContainerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для тестов, требующих внешние сервисы.
 * Добавляет WireMock контейнер для мокирования внешних сервисов.
 * Должна использоваться вместе с @E2ETest.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(WireMockContainerConfig.class)
public @interface ExternalServiceTest {
} 