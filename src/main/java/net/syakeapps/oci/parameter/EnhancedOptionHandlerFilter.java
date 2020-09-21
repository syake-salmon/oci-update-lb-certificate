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
    OptionHandlerFilter ALL = new OptionHandlerFilter() {
        public boolean select(OptionHandler o) {
            return true;
        }
    };

    /**
     * @see OptionHandlerFilter#PUBLIC
     */
    OptionHandlerFilter PUBLIC = new OptionHandlerFilter() {
        public boolean select(OptionHandler o) {
            return !o.option.hidden();
        }
    };

    /**
     * @see OptionHandlerFilter#REQUIRED
     */
    OptionHandlerFilter REQUIRED = new OptionHandlerFilter() {
        public boolean select(OptionHandler o) {
            return o.option.required();
        }
    };

    /**
     * Print all {@linkplain Option#required() required}=false options.
     */
    OptionHandlerFilter OPTIONAL = new OptionHandlerFilter() {
        public boolean select(OptionHandler o) {
            return !o.option.required();
        }
    };
}