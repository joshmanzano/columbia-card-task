package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class Model {

    private Controller controller;
    int lossCardNum[];
    int lossAmount[];
    int gainAmount[];
    String name;

    public Model() {
        lossCardNum = new int[2];
        lossAmount = new int[2];
        gainAmount = new int[2];
        readSettings();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getLossCardNum(int round) {
        return lossCardNum[round];
    }

    public int getLossAmount(int round) {
        return lossAmount[round];
    }

    public int getGainAmount(int round) {
        return gainAmount[round];
    }

    public void readSettings() {
        Reader reader;
        try {
            new File("data").mkdirs();
            reader = Files.newBufferedReader(new File("data/settings.csv").toPath());
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build();

            String[] lines = csvReader.readNext();
            lossCardNum[0] = Integer.parseInt(lines[0]);
            lossCardNum[1] = Integer.parseInt(lines[1]);
            lossAmount[0] = Integer.parseInt(lines[2]);
            lossAmount[1] = Integer.parseInt(lines[3]);
            gainAmount[0] = Integer.parseInt(lines[4]);
            gainAmount[1] = Integer.parseInt(lines[5]);

        } catch (IOException | CsvValidationException e) {
            System.out.println("Creating data directory.");
            writeSettingsCSV();
            readSettings();
        }

    }

    public void writeSettingsCSV() {
        // num of loss cards: h 3, l 1
        // gain amount: h 30, l 10
        // loss amount: h 750, l 250
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("data/settings.csv"));
            String[] input = new String[] {"loss cards (high)","loss cards (low)","loss amount (high)","loss amount (low)","gain amount (high)","gain amount (low)"};
            writer.writeNext(input);
            input = new String[] {"3","1","750","250","30","10"};
            writer.writeNext(input);

            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeSettingsCSV(String lossCardH, String lossCardL, String lossAmountH, String lossAmountL, String gainAmountH, String gainAmountL) {
        // num of loss cards: h 3, l 1
        // gain amount: h 30, l 10
        // loss amount: h 750, l 250
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("data/settings.csv"));
            String[] input = new String[] {"loss cards (high)","loss cards (low)","loss amount (high)","loss amount (low)","gain amount (high)","gain amount (low)"};
            writer.writeNext(input);
            input = new String[] {lossCardH,lossCardL,lossAmountH,lossAmountL,gainAmountH,gainAmountL};
            writer.writeNext(input);

            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startRecord(String name) {
        this.name = name;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("data/"+name+".csv",true));
            writer.writeNext(new String[] {"Round","Score","Cards selected","Loss cards","Loss amount","Gain amount","Round Outcome"});
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void recordRound(String[] record) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("data/"+name+".csv",true));
            writer.writeNext(record);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
