package com.example.ui;

import com.example.core.Calculator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

public class App {
    final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        try {
            if (args != null && args.length > 0) {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("warn"))
                        PropertyConfigurator.configure(Loader.getResource("log4j-warn.properties"));
                    else if (args[1].equalsIgnoreCase("debug"))
                        PropertyConfigurator.configure(Loader.getResource("log4j-debug.properties"));
                    else if (args[1].equalsIgnoreCase("info"))
                        PropertyConfigurator.configure(Loader.getResource("log4j-info.properties"));
                    else
                        PropertyConfigurator.configure(Loader.getResource("log4j-debug.properties"));
                } else
                    PropertyConfigurator.configure(Loader.getResource("log4j-debug.properties"));
                String exp = args[0];
                Calculator calculator = new Calculator();
                System.out.println("Result: " + calculator.calculate(exp.trim(), null));
            } else
                System.out.println("Please provide input. First argument is expression. Second argument can be warn, info or debug. Second argument is optional.");
        } catch (ArithmeticException ae) {
            logger.error("Error : " + ae);
            System.out.println("Number is divide by zero. Please check your expression");
        } catch (Exception e) {
            logger.error("Error : " + e);
        }
    }
}
