package net.syakeapps.oci.parameter;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * command-line arguments bean
 */
public class Argument {
    @Option(name = "--certificate-name", required = true, usage = "[TEXT] A friendly name for the certificate bundle.")
    private String certName;

    @Option(name = "--load-balancer-id", required = true, usage = "[TEXT] The OCID of load balancer on which to add the certificate bundle.")
    private String lbOCID;

    @Option(name = "--listener-name", required = true, usage = "[TEXT] The name of the listener to update.")
    private String listenerName;

    @Option(name = "--ca-certificate-file", usage = "[TEXT] The Certificate Authority certificate, or any interim certificate, that you received from your SSL certificate provider.")
    private String caCertFile;

    @Option(name = "--max-wait-seconds", usage = "[TEXT] The maximum time to wait for the work request.")
    private int maxWaitSec = 1200;

    @Option(name = "--passphrase", usage = "[TEXT] A passphrase for encrypted private keys. This is needed only if you created your certificate with a passphrase.")
    private String passphrase;

    @Option(name = "--private-key-file", usage = "[FILENAME] The SSL private key for your certificate, in PEM format.")
    private String privkeyFile;

    @Option(name = "--public-certificate-file", usage = "[FILENAME] The public certificate, in PEM format, that you received from your SSL certificate provider.")
    private String pubCertFile;

    @Option(name = "--wait-interval-seconds", usage = "[INTEGER] Check every --wait-interval-seconds to see whether the work request to see if it has reached the state.")
    private int waitIntervalSec = 30;

    @Option(name = "--config-file", usage = "[FILENAME] The path to the config file.")
    private String configFile = "~/.oci/config";

    @Option(name = "--profile", usage = "[TEXT] The profile in the config file to load.")
    private String profile = "DEFAULT";

    @Option(name = "-d", aliases = { "--debug" }, usage = "For debug level log output.")
    private boolean debugFlag = false;

    @Option(name = "-h", aliases = { "--help" }, help = true, usage = "print how-to-use and exit")
    private boolean usageFlag = false;

    private CmdLineParser parser;

    /**
     * @return certName
     */
    public String certName() {
        return certName;
    }

    /**
     * @return lbOCID
     */
    public String lbOCID() {
        return lbOCID;
    }

    /**
     * @return listenerName
     */
    public String listenerName() {
        return listenerName;
    }

    /**
     * @return caCertFile
     */
    public String caCertFile() {
        return caCertFile;
    }

    /**
     * @return maxWaitSec
     */
    public int maxWaitSec() {
        return maxWaitSec;
    }

    /**
     * @return passphrase
     */
    public String passphrase() {
        return passphrase;
    }

    /**
     * @return privkeyFile
     */
    public String privkeyFile() {
        return privkeyFile;
    }

    /**
     * @return pubCertFile
     */
    public String pubCertFile() {
        return pubCertFile;
    }

    /**
     * @return waitIntervalSec
     */
    public int waitIntervalSec() {
        return waitIntervalSec;
    }

    /**
     * @return configFile
     */
    public String configFile() {
        return configFile;
    }

    /**
     * @return profile
     */
    public String profile() {
        return profile;
    }

    /**
     * @return debugFlag
     */
    public boolean debugFlag() {
        return debugFlag;
    }

    /**
     * @return usageFlag
     */
    public boolean usageFlag() {
        return usageFlag;
    }

    /**
     * @return parser
     */
    public CmdLineParser getParser() { return parser; }

    /**
     * @param parser parser to set
     */
    public void setParser(CmdLineParser parser) { this.parser = parser; }

    /**
     * Print command-line usage to STDOUT.
     */
    public void printUsage() {
        System.out.println("Usage:");
        System.out.println("  Exec 'java -jar' command.");
        System.out.println();
        System.out.println("Options:");
        parser.printUsage(System.out);
    }
}
