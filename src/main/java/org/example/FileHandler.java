package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.users.BotUser;

import java.io.*;
import java.util.*;

public class FileHandler {
    private final static List<String> usersInSystem = new ArrayList<>();
    private static FileHandler self;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String saveDirectory;

    public static FileHandler getInstance() {
        if (self != null){
            return self;
        }
        long t = System.currentTimeMillis();
        self = new FileHandler();

        System.out.println("Time to build JSONSaver: " + (System.currentTimeMillis() - t));
        return self;
    }


    private FileHandler(){
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
        String configName = "config.jsh";
        File config = new File(saveDirectory + "/" + configName);
        if (config.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(config));
                reader.close();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(saveDirectory + "/" + configName));
                writer.write("errorCount 0");
                writer.close();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public int registerNewUser(BotUser user){
        File userSave = new File(saveDirectory + "/" + user.name + "@" + user.discordId + ".userSave");
        File userLog = new File(saveDirectory + "/" + user.name + "@" + user.discordId + ".userLog");
        if (userSave.exists()){
            return 0;
        }
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(user);
        }catch (Exception e){
            throw new RuntimeException("Cannot get String for new user registry"); //TODO: log error
        }
        try {
            BufferedWriter saveWriter = new BufferedWriter(new FileWriter(userSave));
            saveWriter.write(jsonString);
            saveWriter.close();
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(userLog));
            logWriter.write("Log for user " + user.name + "@" + user.discordId + " started");
            logWriter.close();
        }catch (Exception e){
            return -1;
            //TODO: log error
        }
        return 1;
    }

    public void serializeUser(BotUser user){
        long t = System.currentTimeMillis();
        File userSave = new File(saveDirectory + "/" + user.name + "@" + user.discordId + ".userSave");
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(user);
        }catch (Exception e){
            throw new RuntimeException("Cannot get String for serialisation"); //TODO: log error
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(userSave));
            writer.write(jsonString);
            writer.close();
        }catch (Exception e){
            throw new RuntimeException("Cannot write values for serialisation"); //TODO: log error
        }
        usersInSystem.remove(user.name);
        System.out.println("Time to serialize: " + (System.currentTimeMillis() - t));
    }
    public BotUser deserializeUser(String name, String id){
        long t = System.currentTimeMillis();
        File selectedFile = getFileFromSaveDirectory(name, id, ".userSave");
        BotUser botUser = deserializeUser(selectedFile);
        if(botUser == null){
            return null;
        }
        usersInSystem.add(botUser.name);
        System.out.println("Time to deserialize: " + (System.currentTimeMillis() - t));
        return botUser;
    }
    public BotUser deserializeUser(File userFile){
        BotUser user;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            user = objectMapper.readValue(reader.readLine(), BotUser.class);
        }catch (Exception e){
            return null;
            //throw new RuntimeException("Cannot deserialize user"); //TODO: log error
        }
        return user;
    }

    public File getFileFromSaveDirectory(String name, String id, String fileType) {
        long t = System.currentTimeMillis();
        File directory = new File(saveDirectory);
        File[] files = directory.listFiles();
        ArrayList<File> realFiles = new ArrayList<>();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().contains(fileType)) {
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

    public ArrayList<BotUser> deserializeAll() {
        File directory = new File(saveDirectory);
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Don't have any files in save directory");
        }
        List<File> userSaveFiles = new ArrayList<>();
        Iterator<File> fileIterator = Arrays.stream(files).iterator();
        while (fileIterator.hasNext()){
            File file = fileIterator.next();
            if (file.getName().contains(".userSave")){
                userSaveFiles.add(file);
            }
        }
        ArrayList<BotUser> allUsers = new ArrayList<>();
        for(File userSaveFile : userSaveFiles){
            allUsers.add(deserializeUser(userSaveFile));
        }
        return allUsers;
    }

    public boolean isUserRegistered(String name, String id){
        return getFileFromSaveDirectory(name, id, ".userSave") != null;
    }
    public boolean isUserInSystem(String username){
        return usersInSystem.contains(username);
    }

    public boolean isAnyUserInSystem() {
        System.out.println(usersInSystem);
        return !usersInSystem.isEmpty();
    }

    public String getSaveDirectory() {
        return saveDirectory;
    }


//    public static String getStackTraceAsString(Exception e) {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        return sw.toString();
//    }
}
