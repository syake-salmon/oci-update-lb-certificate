package net.syakeapps.oci.parameter;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.spi.OptionHandler;

/**
 * enhanced {@linkplain OptionHandlerFilter} interface with
 * {@linkplain EnhancedOptionHandlerFilter#OPTIONAL}
 * 
 * @see OptionHandlerFilter
 * @author syakesalmon
 *
 */
public interface EnhancedOptionHandlerFilter {
    /**
     * @see OptionHandlerFilter#select(OptionHandler)
     * @param o
     * @return boolean
     */
    boolean select(OptionHandler o);

    /**
     * @see OptionHandlerFilter#ALL
     */
    OptionHandlerFilter ALL = o -> true;

    /**
     * @see OptionHandlerFilter#PUBLIC
     */
    OptionHandlerFilter PUBLIC = o -> !o.option.hidden();

    /**
     * @see OptionHandlerFilter#REQUIRED
     */
    OptionHandlerFilter REQUIRED = o -> o.option.required();

    /**
     * Print all {@linkplain Option#required() required}=false options.
     */
    OptionHandlerFilter OPTIONAL = o -> !o.option.required();
}
