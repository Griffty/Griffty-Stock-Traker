package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.users.BotUser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonSaveHandler {
    private final static List<String> usersInSystem = new ArrayList<>();
    private static JsonSaveHandler self;
    private final int versionID = 0;
    private int errorCount;
    private final String saveDirectory;
    private final String configName = "config.jsh";
    public static JsonSaveHandler getInstance() {
        if (self != null){
            return self;
        }
        long t = System.currentTimeMillis();
        self = new JsonSaveHandler();

        System.out.println("Time to build JSONSaver: " + (System.currentTimeMillis() - t));
        return self;
    }


    private JsonSaveHandler(){
        // Json handler constructor
        String saveDirectory1 = null;
        if (System.getProperty("os.name").contains("Linux")){
            saveDirectory1 = SensitiveInformation.LinuxSaveDir;
        }
        if (System.getProperty("os.name").contains("Windows")){
            saveDirectory1 = SensitiveInformation.WindowsSaveDir;
        }
        System.out.println(saveDirectory1);
        saveDirectory = saveDirectory1;
        if (!new File(saveDirectory).exists()){
            throw new RuntimeException("CANNOT REACH SAVE DIRECTORY");
        }
        File config = new File(saveDirectory + "/" + configName);
        if (config.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(config));
                if (Integer.parseInt(reader.readLine().substring(2)) != (versionID)){
                    throw new RuntimeException("Version ID doesn't mach provided in config");
                }
                errorCount = Integer.parseInt(reader.readLine().substring(11));
                reader.close();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(saveDirectory + "/" + configName));
                writer.write("v " + versionID);
                writer.newLine();
                writer.write("errorCount 0");
                writer.close();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public int registerNewUser(BotUser user){
        long t = System.currentTimeMillis();
        File userSave = new File(saveDirectory + "/" + user.name + "@" + user.discordId + ".userSave");
        if (userSave.exists()){
            return 0;
        }
        ObjectMapper objectMapper = CreateObjectMapper();

        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(user);
        }catch (Exception e){
            return saveErrorInfo(user, jsonString, getStackTraceAsString(e));
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(userSave));
            writer.write("v"+versionID);
            writer.newLine();
            writer.write(jsonString);
            writer.close();
        }catch (Exception e){
            return saveErrorInfo(user, "v"+versionID, getStackTraceAsString(e));
        }
        System.out.println("Time to register: " + (System.currentTimeMillis() - t));
        return 1;
    }

    private ObjectMapper CreateObjectMapper() {
        return new ObjectMapper();
    }

    public int serializeUser(BotUser user){
        long t = System.currentTimeMillis();
        File userSave = new File(saveDirectory + "/" + user.name + "@" + user.discordId + ".userSave");
        String version;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userSave));
            version = reader.readLine();
        }catch (Exception e){
            return saveErrorInfo(user, "Tried to get Version", getStackTraceAsString(e));
        }

        ObjectMapper objectMapper = CreateObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(user);
        }catch (Exception e){
            return saveErrorInfo(user, jsonString, getStackTraceAsString(e));
        }
        String formatedString = getFormatedString(jsonString);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(userSave));
            writer.write(version);
            writer.newLine();
            writer.write(formatedString);
            writer.close();
        }catch (Exception e){
            return saveErrorInfo(user, formatedString, getStackTraceAsString(e));
        }
        System.out.println("Time to serialize: " + (System.currentTimeMillis() - t));
        return 1;
    }
    public BotUser deserializeUser(String name, String id){
        long t = System.currentTimeMillis();
        File selectedFile = getUserFile(name, id);
        BotUser user = null;
        try {
            assert selectedFile != null;
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            reader.readLine();
            ObjectMapper objectMapper = CreateObjectMapper();
            user = objectMapper.readValue(reader.readLine(), BotUser.class);
        }catch (Exception e){
            saveErrorInfo(user, "Trying to deserialize user", getStackTraceAsString(e));
            return null;
        }
        System.out.println("Time to deserialize: " + (System.currentTimeMillis() - t));
        return user;
    }
    public boolean isUserRegistered(String name, String id){
        return getUserFile(name, id) != null;
    }

    private File getUserFile(String name, String id) {
        long t = System.currentTimeMillis();
        File directory = new File(saveDirectory);
        File[] files = directory.listFiles();
        ArrayList<File> realFiles = new ArrayList<>();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            if (file.isFile()) {
                realFiles.add(file);
            }
        }
        if (realFiles.isEmpty()){
            return null;
        }
        for (File file : realFiles){
            if (id.equals("?")){
                System.out.println(name);
                if (file.getName().contains(name)){
                    System.out.println("Time to find file: " + (System.currentTimeMillis() - t));
                    return file;
                }
            }else {
                if (file.getName().contains(id) && file.getName().contains(name)) {
                    System.out.println("Time to find file: " + (System.currentTimeMillis() - t));
                    return file;
                }
            }
        }

        return null;
    }

    public int saveErrorInfo(BotUser user, String additionalInfo, String stackTrace){
        errorCount++;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveDirectory + "/error" + errorCount + ".err"));
            writer.write("USER INFO\n");
            writer.newLine();
            if (user != null){
                writer.write(user.toString());
                writer.newLine();
            }
            writer.write("\n\nADDITIONAL INFO\n");
            writer.newLine();
            writer.write(additionalInfo);
            writer.newLine();
            writer.write("\n\nERROR STACK TRACE\n");
            writer.newLine();
            writer.write(stackTrace);
            writer.close();
            writer = new BufferedWriter(new FileWriter(saveDirectory + "/" + configName));
            writer.write("v " + versionID);
            writer.newLine();
            writer.write("errorCount " + errorCount);
            writer.close();
            return errorCount * -1;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public boolean isUserInSystem(String userID){
        return usersInSystem.contains(userID);
    }

    //TODO: add formatting for indents to make save files more human readable
    private static String getFormatedString(String jsonString) {
        return jsonString;
    }

    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
