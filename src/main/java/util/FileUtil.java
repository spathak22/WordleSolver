package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Map readJsonFile(String datasetPath, String fileName){
        Map node = null;
        try {
            node = objectMapper.readValue(new File(datasetPath+fileName), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static void WriteJsonFile(String datasetPath, String fileName, Map map){
        try {
            objectMapper.writeValue(new File(datasetPath+fileName), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void csvToJson(String datasetPath, String inputFile, String outputFile){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(datasetPath+inputFile))) {
            String line;
            sb.append("{ \n");
            while ((line = br.readLine()) != null) {
                sb.append('\"' +line +",\n");
            }
            sb.append("} \n");
            BufferedWriter writer = new BufferedWriter(new FileWriter(datasetPath+outputFile));
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
