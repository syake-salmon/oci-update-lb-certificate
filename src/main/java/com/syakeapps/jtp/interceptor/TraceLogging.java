package com.syakeapps.jtp.interceptor;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.cal10n.LocLogger;

import com.syakeapps.jtp.annotation.Trace;
import com.syakeapps.jtp.logging.LoggerFactory;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Trace
public class TraceLogging {

    @AroundInvoke
    public Object trace(InvocationContext context) throws Exception {
        LocLogger log = LoggerFactory.getLogger(context.getTarget().getClass());

        log.trace("{} ENTER", context.getMethod().getName());
        long start = System.currentTimeMillis();
        Object result = context.proceed();
        log.trace("{} EXIT ({} mills)", context.getMethod().getName(),
                System.currentTimeMillis() - start);

        return result;
    }
}
