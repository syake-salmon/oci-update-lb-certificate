package com.syakeapps.jtp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.cal10n.LocLogger;

import com.syakeapps.jtp.annotation.Trace;
import com.syakeapps.jtp.logging.LoggerFactory;
import com.syakeapps.jtp.logging.Messages;

/**
 * Filter for block direct access to XHTML resources.
 */
@WebFilter(urlPatterns = "/*")
public class XHTMLProtectFilter implements Filter {

    private static final LocLogger LOG = LoggerFactory
            .getLogger(XHTMLProtectFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // nop
    }

    @Trace
    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getServletPath();
        LOG.debug("Request path => {}", path);
        if (path.endsWith(".xhtml")) {
            LOG.warn(Messages.WARN_ACCESS_TO_FORBIDDEN_RESOURCES, path);
            ((HttpServletResponse) response)
                    .sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // nop
    }
}
