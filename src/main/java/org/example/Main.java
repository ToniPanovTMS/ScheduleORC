package org.example;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {
    static HashMap<String, Integer> Namber_of_phone = new HashMap();
    static String separator;
    static File parsing_of_table;
    static File surnames_phones;
    static File URL;
    static File parsing_of_table_element;
    static File name_files;
    static ArrayList<String> Name_file = new ArrayList<String>();
    static int quantity_files;
    static String text_for_log_file;

    public static void main(String[] args) throws IOException {
        Namber_of_phone_input();
        name_files_input();
        start();
    }

    public static void start() throws IOException {
        FileReader fr = new FileReader(Main.URL);
        BufferedReader reader = new BufferedReader(fr);
        String URL = reader.readLine();
        Document page = null;
        page = Jsoup.parse(new URL(URL), 100000000);
        Element tbody = page.select("tbody").first();

        try {
            FileWriter writer = new FileWriter(parsing_of_table, false);

            try {
                writer.write(String.valueOf(tbody));
            } catch (Throwable throwable) {
                try {
                    writer.close();
                } catch (Throwable var9) {
                    throwable.addSuppressed(var9);
                }

                throw throwable;
            }

            writer.close();
        } catch (IOException var11) {
            System.out.println(var11.getMessage());
        }
        for(int i = 0; i < quantity_files; ++i) {
            String name_file = Name_file.get(i);
            all_info(name_file, i);
        }
        generate_log_file();
    }

    static void all_info(String name_file, int index_of_file) throws IOException {
        int[] install_namber = new int[6];
        FileReader fr = new FileReader(parsing_of_table_element);
        BufferedReader reader = new BufferedReader(fr);
        String line;
        text_for_log_file = text_for_log_file + name_file + "\n";

        int i=0;
        while((line= reader.readLine()) != null) {
            if(i==index_of_file){
                line.trim();
                String[] words = line.split(",");
                for(int j = 0; j < words.length; ++j) {
                    install_namber[j] = Integer.parseInt(words[j]);
                }
               break;
            }
            i++;
        }
        String str = new String(Files.readAllBytes(Paths.get(String.valueOf(parsing_of_table))));
        List<String> install_list = new ArrayList();
        char[] Array1;
        String name_instal;
        for(i = 0; i < 6; ++i) {
            Array1 = str.split("\n")[install_namber[i]].toCharArray();
            name_instal = "";
            for(int j = 0; j < Array1.length; ++j) {
                if (1040 <= Array1[j] && Array1[j] <= 1103) {
                    name_instal = name_instal + Array1[j];
                }
            }
            if (name_instal != "") {
                text_for_log_file = text_for_log_file + name_instal + " " + Namber_of_phone.get(name_instal) + "\n";
                install_list.add(String.valueOf(Namber_of_phone.get(name_instal)));
            }

        }

        generate_file(install_list, name_file);
    }

    static void generate_file(List<String> install_list, String name_file) {
        String text = "#!/bin/bash\n/bin/mysql -u root -pVyiu28giOCd9 <<EOF\nUSE asterisk\n";
        String namber = "'";
        String line;
        if (install_list == null) {
            namber = "null";
        } else {
            for(Iterator var4 = install_list.iterator(); var4.hasNext(); namber = namber + line + "-") {
                line = (String)var4.next();
            }
        }

        namber = namber.substring(0, namber.length() - 1) + "'";
        text = text + "update queues_details set data = " + namber + " where id = '1729'\n";
        text = text + "EOF\n/var/lib/asterisk/bin/amportal a r";

        try {
            FileWriter writer = new FileWriter("data" + separator + "script" + separator + name_file, false);

            try {
                writer.write(text);
            } catch (Throwable var8) {
                try {
                    writer.close();
                } catch (Throwable var7) {
                    var8.addSuppressed(var7);
                }

                throw var8;
            }

            writer.close();
        } catch (IOException var9) {
            System.out.println(var9.getMessage());
        }

    }

    static void Namber_of_phone_input() throws IOException {
        FileReader fr = new FileReader(surnames_phones);
        BufferedReader reader = new BufferedReader(fr);

        for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            String[] words = line.split("\t");
            if (words[0] != null && words[1] != null) {
                Namber_of_phone.put(words[0], Integer.valueOf(words[1]));
            }
        }

    }
    static void name_files_input() throws IOException {
        FileReader fr = new FileReader(name_files);
        BufferedReader reader = new BufferedReader(fr);
        String str;
        while(( str = reader.readLine()) != null ){
            if(!str.isEmpty()){
                Name_file.add(str);
            }}
        quantity_files= Name_file.size();
    }

    static void generate_log_file() {
        try {
            FileWriter writer = new FileWriter("data" + separator + "log" + separator + "log.txt", false);

            try {
                writer.write(text_for_log_file);
            } catch (Throwable var4) {
                try {
                    writer.close();
                } catch (Throwable var3) {
                    var4.addSuppressed(var3);
                }

                throw var4;
            }

            writer.close();
        } catch (IOException var5) {
            System.out.println(var5.getMessage());
        }

    }

    static {
        separator = File.separator;
        parsing_of_table = new File("data" + separator + "debugging" + separator + "parsing_of_table.txt");
        surnames_phones = new File("data" + separator + "debugging" + separator + "surnames-phones.txt");
        URL = new File("data" + separator + "debugging" + separator + "URL.txt");
        parsing_of_table_element = new File("data" + separator + "debugging" + separator + "parsing_of_table_element.txt");
        name_files = new File("data" + separator + "debugging" + separator + "Name_files.txt");
    }
}
