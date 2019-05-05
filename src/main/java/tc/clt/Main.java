package tc.clt;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.List;
import java.util.function.Consumer;

public class Main {
    public final static String appName = Main.class.getName();
    public final static String appDesc = String.join("\n",
            "This is a template command-line tool.",
            "This is the docstring explaining the command.");

    public static void main(String[] argv) {
        ArgumentParser parser = ArgumentParsers.newFor(appName).build()
                .defaultHelp(true)
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
        Namespace res = null;
        try {
            res = parser.parseArgs(argv);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        final boolean verbose = res.getBoolean("verbose");
        final String outPath = res.getString("output");
        if (verbose) {
            System.err.printf("Output path: %s\n", outPath);
        }
        final List<String> targets = res.getList("targets");
        final boolean noop = res.getBoolean("noop");
        Consumer<String> op = noop ?
                (s) -> System.err.println("[noop]") :
                (s) -> System.err.println(s);
        targets.forEach(op);
        System.exit(0);
    }
}
