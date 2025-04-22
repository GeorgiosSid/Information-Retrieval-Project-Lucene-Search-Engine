package org.main;

import FrontEnd.SearchEngine;
import com.opencsv.exceptions.CsvValidationException;
import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        new SearchEngine();
    }
}