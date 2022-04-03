package com.goldrush;

// https://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java

public class Util {    

    public enum OS {
        WINDOWS, LINUX, MAC, SOLARIS
    };// Operating systems.

    private OS os = null;
    private String curr = System.getProperty("user.dir") + "/src/main/resources/com/goldrush/";

    public Util(){
        String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("win")) {
            os = OS.WINDOWS;
        } else if (operSys.contains("nix") || operSys.contains("nux")
                || operSys.contains("aix")) {
            os = OS.LINUX;
        } else if (operSys.contains("mac")) {
            os = OS.MAC;
        } else if (operSys.contains("sunos")) {
            os = OS.SOLARIS;
        }
    }

    public OS getOS() {return os;}

    public String getImgLoc(String name){
        if(os == OS.WINDOWS) return curr + name + ".png";
        else return "file://" + curr + name + ".png";
    }
}