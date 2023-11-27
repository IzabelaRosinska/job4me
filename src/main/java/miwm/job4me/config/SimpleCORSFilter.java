package miwm.job4me.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import static miwm.job4me.messages.AppMessages.*;

@Service
public class SimpleCORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //List<String> headers = new ArrayList<>(FRONT_BRANCH_HOST_AZURE, FRONT_BRANCH_HOST_AZURE, FRONT_HOST, LINKEDIN_HOST, "https://mango-moss-0c13e2b03-40.westeurope.3.azurestaticapps.net");
        response.setHeader("Access-Control-Allow-Origin", FRONT_BRANCH_HOST_AZURE);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        response.setHeader("Access-Control-Expose-Headers", "Content-Length, Authorization");
        filterChain.doFilter(request, response);
    }
}

