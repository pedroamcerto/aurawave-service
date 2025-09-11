package com.aurawave.controller.scheduler;

import com.aurawave.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyValidityProductSchedulerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private VerifyValidityProductScheduler scheduler;

    @Test
    @DisplayName("Deve invocar service.validValidityProduct() quando o método agendado for chamado")
    void shouldInvokeService() {
        scheduler.verifyValidityProduct();

        verify(productService, times(1)).validValidityProduct();
        verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("Deve possuir @Scheduled com o cron esperado")
    void shouldHaveScheduledAnnotationWithExpectedCron() throws Exception {
        Method m = VerifyValidityProductScheduler.class.getMethod("verifyValidityProduct");
        Scheduled scheduled = m.getAnnotation(Scheduled.class);

        assertNotNull(scheduled, "@Scheduled não encontrado no método verifyValidityProduct()");
        assertEquals("0 0 9 * * ?", scheduled.cron(), "Cron inválido no @Scheduled");
    }
}