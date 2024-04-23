package com.udlive.flink.utils;

import java.util.Scanner;

public class FilePathUtils {

    public static String getFilePathFromConsole() {
        System.out.println("Type absolute path to telemetry.dat");
        Scanner myObj = new Scanner(System.in);
        return myObj.nextLine();
    }
}
