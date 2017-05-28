package tc.clt;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.List;
import java.util.function.Consumer;

public class Main {
    public final static String appName = "Command";
    public final static String appDesc = String.join("\n",
        "This is a template command-line tool.",
        "This is the docstring explaining the command.");
    public static void main(String[] argv) {
        int exitCode = 0;
        ArgumentParser parser = ArgumentParsers.newArgumentParser(appName)
                                    .description(appDesc);
        parser.addArgument("-o", "--output").type(String.class).metavar("OUTPUT")
            .dest("output").setDefault("/tmp")
            .help("Output destination path.");
        parser.addArgument("-n", "--noop").action(Arguments.storeTrue())
            .dest("noop")
            .help("Do not execute, dry run only.");
        parser.addArgument("-v", "--verbose").action(Arguments.storeTrue())
            .dest("verbose")
            .help("Run verbosely.");
        parser.addArgument("targets").nargs("*").metavar("TARGET")
            .help("Targets for this command.");
        try {
            final Namespace res = parser.parseArgs(argv);
            final boolean verbose = res.getBoolean("verbose");
            final boolean noop = res.getBoolean("noop");
            final String outPath = res.getString("output");
            final List<String> targets = res.getList("targets");
            if (verbose) {
                System.err.printf("Output path: %s\n", outPath);
            }
            Consumer<String> op = noop ?
                                      (s)->System.err.println("[noop]") :
                                      (s)->System.err.println(s);
            targets.forEach(op);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            exitCode = 1;
        }
        System.exit(exitCode);
    }
}
