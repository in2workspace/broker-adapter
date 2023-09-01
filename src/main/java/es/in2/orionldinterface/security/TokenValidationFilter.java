package es.in2.orionldinterface.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Profile("!dev")
public class TokenValidationFilter implements Filter {

    @Value("${api.access-token}")
    private String validToken;

    @Override
    public void init(FilterConfig filterConfig) {
        // No special init
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String providedToken = httpRequest.getHeader("Authorization");

        log.debug("Provided Token: " + providedToken);

        if (!isValidToken(providedToken)) {
            log.debug("Authentication failed");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Unauthorized status code
            httpResponse.getWriter().write("Authentication failed");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // We don't need special cleaning
    }

    private boolean isValidToken(String providedToken) {
        return validToken.equals(providedToken);
    }
}
