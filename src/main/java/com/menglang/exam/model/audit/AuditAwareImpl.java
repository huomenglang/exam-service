package com.menglang.exam.model.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (Objects.isNull(authentication)){
//            return Optional.of("System");
//        }
//        if (authentication.getPrincipal() instanceof JwtUser jwtUser) {
//            return Optional.ofNullable(jwtUser.getUserAccountId().toString());
//        } else {
//            return Optional.of(authentication.getName());
//        }
        return Optional.of("Anonymous");
    }
}

