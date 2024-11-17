package com.example.selenium.common;



public class Directory {

    public static boolean deleteDirectory(String path){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "rd", "/s", "/q", path);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Directory deleted successfully.");
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
