package rw.mugaboandre.rentalhub.core.util.utilClass;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuditorAware implements AuditorAware<String> {

    private final HttpServletRequest request;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("system");
    }

    public Optional<String> getCurrentIp() {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return Optional.ofNullable(ip);
    }
}
