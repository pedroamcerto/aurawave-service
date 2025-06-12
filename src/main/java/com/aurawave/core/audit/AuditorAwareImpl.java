package com.aurawave.core.audit;

import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() { return Optional.of("user"); }
}
