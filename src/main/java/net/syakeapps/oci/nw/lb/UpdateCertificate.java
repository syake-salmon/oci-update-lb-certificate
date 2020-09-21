package net.syakeapps.oci.nw.lb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.LoggerFactory;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.loadbalancer.LoadBalancerClient;
import com.oracle.bmc.loadbalancer.model.CreateCertificateDetails;
import com.oracle.bmc.loadbalancer.model.Listener;
import com.oracle.bmc.loadbalancer.model.SSLConfiguration;
import com.oracle.bmc.loadbalancer.model.SSLConfigurationDetails;
import com.oracle.bmc.loadbalancer.model.UpdateListenerDetails;
import com.oracle.bmc.loadbalancer.requests.CreateCertificateRequest;
import com.oracle.bmc.loadbalancer.requests.GetLoadBalancerRequest;
import com.oracle.bmc.loadbalancer.requests.GetWorkRequestRequest;
import com.oracle.bmc.loadbalancer.requests.UpdateListenerRequest;
import com.oracle.bmc.loadbalancer.responses.CreateCertificateResponse;
import com.oracle.bmc.loadbalancer.responses.UpdateListenerResponse;
import com.oracle.bmc.waiter.FixedTimeDelayStrategy;
import com.oracle.bmc.waiter.MaxTimeTerminationStrategy;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import net.syakeapps.oci.parameter.Argument;
import net.syakeapps.oci.parameter.EnhancedOptionHandlerFilter;
import net.syakeapps.oci.util.BufferedReaderUtil;

/**
 * @see UpdateCertificate#main(String...)
 */
public class UpdateCertificate {

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(UpdateCertificate.class);

    private String[] rowArgument;
    private Argument argument;

    private AuthenticationDetailsProvider authProvider;

    private UpdateCertificate(String... args) {
        this.rowArgument = args;
    }

    /**
     * Upload PEMs to OCI for creating a certificate.
     * 
     * @param args string array containing the command line arguments.
     */
    public static void main(String... args) {
        try {
            // exec sequence
            new UpdateCertificate(args).run();
            LOG.info("[SUCCESS] Updating certificate is done.");
            System.exit(0);
        } catch (Exception e) {
            LOG.error("[FAILED] Updating certificate is done with some error. Check log output.", e);
            System.exit(9);
        }
    }

    private void run() throws Exception {
        setup();
        createCertificate();
        updateLbListener();
    }

    private void setup() throws Exception {
        // parse arguments into bean
        argument = new Argument();
        CmdLineParser parser = new CmdLineParser(argument);
        parser.parseArgument(rowArgument);

        if (argument.usageFlag()) {
            System.out.println("Usage:");
            System.out.println("  Exec 'java -jar' command.");
            System.out.println();
            System.out.println("Required:");
            parser.printUsage(new OutputStreamWriter(System.out), null, EnhancedOptionHandlerFilter.REQUIRED);
            System.out.println("");
            System.out.println("Optional:");
            parser.printUsage(new OutputStreamWriter(System.out), null, EnhancedOptionHandlerFilter.OPTIONAL);
            System.exit(0);
        }

        // change log level
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger root = context.getLogger(Logger.ROOT_LOGGER_NAME);
        if (argument.debugFlag()) {
            root.setLevel(Level.DEBUG);
        } else {
            root.setLevel(Level.INFO);
        }

        // acquire auth provider
        authProvider = new ConfigFileAuthenticationDetailsProvider(
                ConfigFileReader.parse(argument.configFile(), argument.profile()));
    }

    private void createCertificate() throws Exception {
        // build CreateCertificateDetails
        CreateCertificateDetails.Builder builder = CreateCertificateDetails.builder()
                .certificateName(argument.certName());

        if (StringUtils.isNotEmpty(argument.caCertFile())) {
            try (BufferedReader reader = new BufferedReader(new FileReader(argument.caCertFile()))) {
                builder.caCertificate(BufferedReaderUtil.bufferedReaderToString(reader));
            } catch (IOException e) {
                throw new Exception("An error occured while reading CA cert file. [" + argument.caCertFile() + "]", e);
            }
        }

        if (StringUtils.isNotEmpty(argument.privkeyFile())) {
            try (BufferedReader reader = new BufferedReader(new FileReader(argument.privkeyFile()))) {
                builder.privateKey(BufferedReaderUtil.bufferedReaderToString(reader));
            } catch (IOException e) {
                throw new Exception("An error occured while reading private key file. [" + argument.privkeyFile() + "]",
                        e);
            }
        }

        if (StringUtils.isNotEmpty(argument.pubCertFile())) {
            try (BufferedReader reader = new BufferedReader(new FileReader(argument.pubCertFile()))) {
                builder.publicCertificate(BufferedReaderUtil.bufferedReaderToString(reader));
            } catch (IOException e) {
                throw new Exception("An error occured while reading public cert file. [" + argument.pubCertFile() + "]",
                        e);
            }
        }

        if (StringUtils.isNotEmpty(argument.passphrase())) {
            builder.passphrase(argument.passphrase());
        }

        CreateCertificateDetails certDetails = builder.build();

        try (LoadBalancerClient client = new LoadBalancerClient(authProvider)) {
            // create cert
            CreateCertificateResponse response = client.createCertificate(CreateCertificateRequest.builder()
                    .loadBalancerId(argument.lbOCID()).createCertificateDetails(certDetails).build());

            // wait for completion
            client.getWaiters()
                    .forWorkRequest(
                            GetWorkRequestRequest.builder().workRequestId(response.getOpcWorkRequestId()).build(),
                            new MaxTimeTerminationStrategy(argument.maxWaitSec() * 1000),
                            new FixedTimeDelayStrategy(argument.waitIntervalSec() * 1000))
                    .execute();
        }
    }

    private void updateLbListener() throws Exception {
        // acquire listener by listener name
        Listener listener = null;

        try (LoadBalancerClient client = new LoadBalancerClient(authProvider)) {
            GetLoadBalancerRequest getLbRequest = GetLoadBalancerRequest.builder().loadBalancerId(argument.lbOCID())
                    .build();
            listener = client.getLoadBalancer(getLbRequest).getLoadBalancer().getListeners()
                    .get(argument.listenerName());
        }

        // acquire current listener SSL setting
        SSLConfiguration currentSslConfiguration = listener.getSslConfiguration();

        // build request details
        UpdateListenerDetails details = UpdateListenerDetails.builder()
                .defaultBackendSetName(listener.getDefaultBackendSetName()).port(listener.getPort())
                .protocol(listener.getProtocol())
                .sslConfiguration(SSLConfigurationDetails.builder().certificateName(argument.certName())
                        .verifyPeerCertificate(currentSslConfiguration.getVerifyPeerCertificate())
                        .verifyDepth(currentSslConfiguration.getVerifyDepth()).build())
                .build();

        try (LoadBalancerClient client = new LoadBalancerClient(authProvider)) {
            // update listener
            UpdateListenerResponse response = client
                    .updateListener(UpdateListenerRequest.builder().loadBalancerId(argument.lbOCID())
                            .listenerName(argument.listenerName()).updateListenerDetails(details).build());

            // wait for completion
            client.getWaiters()
                    .forWorkRequest(
                            GetWorkRequestRequest.builder().workRequestId(response.getOpcWorkRequestId()).build(),
                            new MaxTimeTerminationStrategy(argument.maxWaitSec() * 1000),
                            new FixedTimeDelayStrategy(argument.waitIntervalSec() * 1000))
                    .execute();
        }
    }
}
