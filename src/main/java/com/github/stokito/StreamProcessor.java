package com.github.stokito;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Scanner;

public class StreamProcessor {
    private static final String CRLF = "\r\n";
    private final Calc calc;
    private final InputStream in;
    private final PrintStream out;
    private final NumberFormat formatter;

    public StreamProcessor(Calc calc, InputStream in, PrintStream out, NumberFormat formatter) {
        this.calc = calc;
        this.in = in;
        this.out = out;
        this.formatter = formatter;
    }

    void process() {
        Scanner scan = new Scanner(in);
        askForInput();
        while (scan.hasNextLine()) {
            String inputLine = scan.nextLine();
            if (isCommand(inputLine, "quit")) {
                return;
            } else if (isCommand(inputLine, "clear")) {
                calc.clear();
                askForInput();
            } else if (isCommand(inputLine, "debug")) {
                printValuesStack();
                askForInput();
            } else if (isCommand(inputLine, "help")) {
                printSupportedOperations();
                askForInput();
            } else {
                try {
                    Double result = calc.eval(inputLine);
                    if (result != null) {
                        println(formatter.format(result));
                    }
                } catch (Exception e) {
                    println(e.toString());
                }
                askForInput();
            }
        }
    }

    /** Match a command even if it wasn't fully written i.e. "quit", "qu" or just "q" */
    private boolean isCommand(String inputLine, String command) {
        return command.indexOf(inputLine.trim().toLowerCase()) == 0;
    }

    private void printSupportedOperations() {
        calc.getOps().keySet().stream().sorted().forEach(this::println);
    }

    private void printValuesStack() {
        for (Double val : calc.getValues()) {
            String format = formatter.format(val);
            println(format);
        }
    }

    private void println(String format) {
        out.print(format);
        out.print(CRLF);
    }

    private void askForInput() {
        out.print("> ");
    }
}