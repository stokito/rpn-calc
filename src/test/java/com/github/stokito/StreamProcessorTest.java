package com.github.stokito;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Locale.*;
import static org.junit.jupiter.api.Assertions.*;

public class StreamProcessorTest {

    @Test
    public void process() {
        InputStream in = inputStreamFromString("> 5\n" +
                "5\n" +
                "> 8\n" +
                "8\n" +
                "> +\n" +
                "13\n" +
                "> \n");

        assertDialog(in, ROOT);
    }

    @Test
    public void processWithError() {
        InputStream in = inputStreamFromString("> 5\n" +
                "5\n" +
                "> +\n" +
                "java.lang.Exception: Not enough operands\n" +
                "> \n");
        assertDialog(in, ROOT);
        in = inputStreamFromString("> bad_number\n" +
                "java.text.ParseException: Unparseable number: \"bad_number\"\n" +
                "> \n");
        assertDialog(in, ROOT);
    }

    @Test
    public void processWithOtherLocale() {
        InputStream in;
        in = inputStreamFromString("> 5,1\n" +
                "5,1\n" +
                "> \n");
        assertDialog(in, FRENCH);
    }

    @Test
    public void processFiles() {
        InputStream in = this.getClass().getResourceAsStream("/input1.txt");
        assertDialog(in, ROOT);
        in = this.getClass().getResourceAsStream("/input2.txt");
        assertDialog(in, ROOT);
        in = this.getClass().getResourceAsStream("/input3.txt");
        assertDialog(in, ROOT);
        in = this.getClass().getResourceAsStream("/input4.txt");
        assertDialog(in, ROOT);
    }

    private ByteArrayInputStream inputStreamFromString(String initialString) {
        return new ByteArrayInputStream(initialString.getBytes());
    }

    private void assertDialog(InputStream in, Locale ROOT) {
        NumberFormat formatter = NumberFormat.getInstance(ROOT);
        Calc calc = new Calc();
        calc.setFormatter(formatter);
        String expectedInput = "";
        String expectedOutput = "";
        Scanner scan = new Scanner(in);
        while (scan.hasNextLine()) {
            String inputLine = scan.nextLine();
            if (inputLine.startsWith("> ")) {
                expectedOutput += "> ";
                String input = inputLine.substring(2);
                if (!input.isBlank()) {
                    expectedInput += input + "\r\n";
                }
            } else {
                expectedOutput += inputLine + "\r\n";
            }
        }
        InputStream expectedInputStream = inputStreamFromString(expectedInput);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos, true, UTF_8);
        StreamProcessor streamProcessor = new StreamProcessor(calc, expectedInputStream, out, formatter);
        streamProcessor.process();
        String actualOutput = baos.toString(UTF_8);
        assertEquals(expectedOutput, actualOutput);
    }
}