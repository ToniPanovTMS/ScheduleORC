package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main{


    static HashMap<String,Integer> Namber_of_phone = new HashMap<String, Integer>();
    static String separator = File.separator;
    static File parsing_of_table = new File("data"+separator+"debugging"+separator+"parsing_of_table.txt");
    static File surnames_phones = new File("data"+separator+"debugging"+separator+"surnames-phones.txt");
    static File URL = new File("data"+separator+"debugging"+separator+"URL.txt");
    static File parsing_of_table_element = new File("data"+separator+"debugging"+separator+"parsing_of_table_element.txt");
    static File saturday_phones = new File("data"+separator+"debugging"+separator+"saturday_phones.txt");
    static String[] Name_file = new String[]{"ch-queue-mon-until1500.sh",
            "ch-queue-mon-after1500.sh",
            "ch-queue-tue-until1500.sh",
            "ch-queue-tue-after1500.sh",
            "ch-queue-wed-until1500.sh",
            "ch-queue-wed-after1500.sh",
            "ch-queue-thu-until1500.sh",
            "ch-queue-thu-after1500.sh",
            "ch-queue-fri-until1500.sh",
            "ch-queue-fri-after1500.sh",
            "ch-queue-sat-until1500.sh"};
    static String text_for_log_file;

    public static void main(String[] args) throws IOException {
        /*
        JFrame frame = new JFrame("Генератор скріптов");
        frame.setContentPane(new frame());
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(dim.width / 2 - 500 / 2, dim.height / 2 - 350 / 2, 500, 350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         */
        start();
    }
    public static void start() throws IOException {
        FileReader fr = new FileReader(URL);
        BufferedReader reader = new BufferedReader(fr);
        String URL = reader.readLine();
        Document page = null;
        page = Jsoup.parse(new URL(URL), 100000000);
        Element tbody = page.select("tbody").first();

        try(FileWriter writer = new FileWriter(parsing_of_table, false))
        {
            writer.write(String.valueOf(tbody));
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        HashMap_input();

        for(String line : Name_file){
            all_info(line);
        }
        generate_log_file();
        LocalDate date =LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek.name().equals("SUNDAY")){
            saturday_phones_file_rebild();
        }
    }
    static void all_info(String name_file) throws IOException {
        int[][] matrix=new int[11][6];
        FileReader fr = new FileReader(parsing_of_table_element);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        text_for_log_file=text_for_log_file+name_file+"\n";
        int i=0;
        while (i<11) {
            line.trim();
            String[] words= line.split(",");
            for(int j=0;j<6;j++){
                matrix[i][j]= Integer.parseInt(words[j]);
            }
            i++;
            line = reader.readLine();
        }

        int[] install_namber= new int[6];
        int[] delide_namber= new int[6];
        int string_delite=-1;
        int string_create=-1;
        switch (name_file){
            case "ch-queue-mon-until1500.sh":{
                string_create=0;
                string_delite=10;
                break;
            }
            case "ch-queue-mon-after1500.sh":{
                string_create=1;
                string_delite=0;
                break;
            }
            case "ch-queue-tue-until1500.sh":{
                string_create=2;
                string_delite=1;
                break;
            }
            case "ch-queue-tue-after1500.sh":{
                string_create=3;
                string_delite=2;
                break;
            }
            case "ch-queue-wed-until1500.sh":{
                string_create=4;
                string_delite=3;
                break;
            }
            case "ch-queue-wed-after1500.sh":{
                string_create=5;
                string_delite=4;
                break;
            }
            case "ch-queue-thu-until1500.sh":{
                string_create=6;
                string_delite=5;
                break;
            }
            case "ch-queue-thu-after1500.sh":{
                string_create=7;
                string_delite=6;
                break;
            }
            case "ch-queue-fri-until1500.sh":{
                string_create=8;
                string_delite=7;
                break;
            }
            case "ch-queue-fri-after1500.sh":{
                string_create=9;
                string_delite=8;
                break;
            }
            case "ch-queue-sat-until1500.sh":{
                string_create=10;
                string_delite=9;
                break;
            }

        }
        for(int j=0;j<6;j++){
            install_namber[j]=matrix[string_create][j];
        }
        for(int j=0;j<6;j++){
            delide_namber[j]=matrix[string_delite][j];
        }
        if(string_delite==-1||string_create==-1){
            System.out.println("ошибка в файле parsing_of_table_element");
        }
        String str = new String(Files.readAllBytes(Paths.get(String.valueOf(parsing_of_table))));
        List<String> delete_list_name = new ArrayList<String>();
        List<String> delete_list_phone = new ArrayList<String>();
        List<String> install_list = new ArrayList<String>();
        if(name_file.equals("ch-queue-mon-until1500.sh")) {
            for(i=0;i<6;i++){
                char[] Array1 = str.split("\n")[install_namber[i]].toCharArray();
                String name_instal="";
                for(int j=0;j<Array1.length;j++){
                    if('А'<=Array1[j]&&Array1[j]<='я'){
                        name_instal=name_instal+Array1[j];
                    }
                }
                if(name_instal!="") {
                    text_for_log_file = text_for_log_file + name_instal + " " + Namber_of_phone.get(name_instal) + "\n";
                    install_list.add(String.valueOf(Namber_of_phone.get(name_instal)));
                }
            }
            delete_list_name=saturday_phones_namber();
            for (String input_of_file : delete_list_name){
                char[] Array1 = input_of_file.toCharArray();
                String name="";
                for(int j=0;j<Array1.length;j++){
                    if('А'<=Array1[j]&&Array1[j]<='я'){
                        name=name+Array1[j];
                    }
                }
                if(name!="") {
                    delete_list_phone.add(String.valueOf(Namber_of_phone.get(name)));
                }
            }
        }else{
            for(i=0;i<6;i++){
                char[] Array1 = str.split("\n")[install_namber[i]].toCharArray();
                String name_instal="";
                for(int j=0;j<Array1.length;j++){
                    if('А'<=Array1[j]&&Array1[j]<='я'){
                        name_instal=name_instal+Array1[j];
                    }
                }
                if(name_instal!=""){
                    text_for_log_file=text_for_log_file +name_instal+" "+Namber_of_phone.get(name_instal)+"\n";
                    install_list.add(String.valueOf(Namber_of_phone.get(name_instal)));
                }
                char[] Array2 = str.split("\n")[delide_namber[i]].toCharArray();
                String name_delite="";
                for(int j=0;j<Array2.length;j++){
                    if('А'<=Array2[j]&&Array2[j]<='я'){
                        name_delite=name_delite+Array2[j];
                    }
                }
                if(name_delite!=""){
                    delete_list_phone.add(String.valueOf(Namber_of_phone.get(name_delite)));
                }
            }
        }
        generate_file(delete_list_phone,install_list,name_file);
    }
    static void generate_file(List<String> delete_list,List<String> install_list,String name_file){
        String text ="#!/bin/bash"+"\n";
        for (String line : delete_list) {
            text=text+"/bin/mysql -u root -pVyiu28giOCd9 <<EOF\n" +
                    "USE asterisk\n" +
                    "DELETE FROM queues_details WHERE data = 'Local/"+line+"@from-queue/n,0' AND id = '1729'\n" +
                    "EOF"+"\n";
        }
        for (String line : install_list) {
            text=text+"/bin/mysql -u root -pVyiu28giOCd9 <<EOF\n" +
                    "USE asterisk\n" +
                    "INSERT INTO queues_details (id, keyword, data, flags) VALUES ('1729', 'member', 'Local/"+line+"@from-queue/n,0', '0')\n" +
                    "EOF"+"\n";
        }
        text+="/var/lib/asterisk/bin/amportal a r";
        try(FileWriter writer = new FileWriter("data"+separator+"script"+separator+name_file, false))
        {
            writer.write(text);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    static void HashMap_input() throws IOException {
        FileReader fr = new FileReader(surnames_phones);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        while (line != null) {
            String[] words= line.split("\t");
            if(words[0]!=null&&words[1]!=null){
                Namber_of_phone.put(words[0],Integer.valueOf(words[1]));
            }
            line = reader.readLine();
        }
    }
    static void generate_log_file(){
        try(FileWriter writer = new FileWriter("data"+separator+"log"+separator+"log.txt", false))
        {
            writer.write(text_for_log_file);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    static List<String> saturday_phones_namber() throws IOException {
        FileReader fr = new FileReader(saturday_phones);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        List<String> names = new ArrayList<String>();
        while (line != null) {
            names.add(line);
            line = reader.readLine();
        }
        return names;
    }
    static void saturday_phones_file_rebild() throws IOException {
        int[][] matrix=new int[11][6];
        FileReader fr = new FileReader(parsing_of_table_element);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        int i=0;
        while (i<11) {
            line.trim();
            String[] words= line.split(",");
            for(int j=0;j<6;j++){
                matrix[i][j]= Integer.parseInt(words[j]);
            }
            i++;
            line = reader.readLine();
        }
        int[] install_namber= new int[6];
        for(int j=0;j<6;j++){
            install_namber[j]=matrix[10][j];
        }
        String text_file="";
        String str = new String(Files.readAllBytes(Paths.get(String.valueOf(parsing_of_table))));
            for(i=0;i<6;i++){
                char[] Array1 = str.split("\n")[install_namber[i]].toCharArray();
                String name_instal="";
                for(int j=0;j<Array1.length;j++){
                    if('А'<=Array1[j]&&Array1[j]<='я'){
                        name_instal=name_instal+Array1[j];
                    }
                }
                text_file=text_file+name_instal+"\n";
            }
        try(FileWriter writer = new FileWriter("data"+separator+"debugging"+separator+"saturday_phones.txt", false))
        {
            writer.write(text_file);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
