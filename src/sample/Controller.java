package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Random;

public class Controller {
    @FXML GridPane buttonPane;
    @FXML Text roundNum;
    @FXML Text roundScore;
    @FXML Text roundLossAmount;
    @FXML Text roundGainAmount;
    @FXML Text roundLossCards;
    @FXML Text average;
    @FXML Button endRound;
    @FXML Button nextRound;
    @FXML Button playButton;
    @FXML TextField playerName;
    @FXML TextField gainAmountLow;
    @FXML TextField gainAmountHigh;
    @FXML TextField lossAmountLow;
    @FXML TextField lossAmountHigh;
    @FXML TextField lossCardLow;
    @FXML TextField lossCardHigh;
    @FXML VBox gamePane;
    @FXML VBox afterPracticePane;
    @FXML VBox titlePane;
    @FXML VBox playerPane;
    @FXML VBox endScorePane;
    @FXML VBox settingPane;
    @FXML VBox msesPane;
    @FXML VBox quesPane;
    @FXML VBox midusPane;
    @FXML VBox midus2Pane;
    @FXML VBox midus3Pane;
    @FXML VBox soepPane;
    @FXML TableView resultTable;
    @FXML ToggleGroup mq1;
    @FXML ToggleGroup mq2;
    @FXML ToggleGroup mq3;
    @FXML ToggleGroup mq4;
    @FXML ToggleGroup mq5;
    @FXML ToggleGroup mq6;
    @FXML ToggleGroup mq7;
    @FXML ToggleGroup mq8;
    @FXML ToggleGroup mq9;
    @FXML ToggleGroup mq10;
    @FXML ToggleGroup mq11;
    @FXML ToggleGroup mq12;
    @FXML ToggleGroup gender;
    @FXML ToggleGroup homeBuilding;
    @FXML ToggleGroup homeLarger;
    @FXML ToggleGroup ownHome;
    @FXML ChoiceBox msesChoice;
    @FXML ChoiceBox soepChoice;
    @FXML TextField numOfPerson;
    @FXML TextField highschool;
    @FXML Label roundLabel;
    @FXML Text instruct1;
    @FXML Text instruct2;
    @FXML Text instruct3;

    private Model model;

    private int gainAmountCounter = 0;
    private int lossAmountCounter = 0;
    private int lossCardCounter = 0;

    private int curRound = -5;
    private int curRoundScore = 0;
    private int curLossCards = 3;
    private int curGainAmount = 10;
    private int curLossAmount = 100;
    private int curSelected = 0;
    private double avgScore = 0;
    private int intAvgScore = 0;
    private boolean curCards[][];
    private Random r = new Random();
    private ObservableList<Record> records =  FXCollections.observableArrayList();
    private ObservableList<Integer> scale =  FXCollections.observableArrayList();

    public void addCards(int rows, int columns){
        curCards = new boolean[rows][columns];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 8; j++) {
                curCards[i][j] = true;
            }
        }
        for(int i = 0 ; i < curLossCards ; i++){
            int row = r.nextInt(4);
            int col = r.nextInt(8);
            while(!curCards[row][col]){
                row = r.nextInt(4);
                col = r.nextInt(8);
            }
            curCards[row][col] = false;
        }
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < columns ; j++){
                Button b = new Button();
                b.setPrefSize(999,999);
                if(curCards[i][j]) {
                    b.setId("hiddenGainCard");
                }else{
                    b.setId("hiddenLossCard");
                }
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        Button b = (Button) e.getSource();
                        if(b.getId().equalsIgnoreCase("hiddenGainCard")){
                            b.setId("gainCard");
                            curRoundScore += curGainAmount;
                            curSelected += 1;
                            roundScore.setText(String.valueOf(curRoundScore));
                        }else{
                            b.setId("lossCard");
                            curRoundScore -= curLossAmount;
                            curSelected += 1;
                            roundScore.setText(String.valueOf(curRoundScore));
                            endGame();
                        }
                        b.setDisable(true);
                    }
                });
                buttonPane.add(b,j,i);
            }
        }
    }

    public void nextRound(ActionEvent actionEvent) {
        startGame();
    }

    public void createTable(){
        avgScore = 0;
        for(int i = 0; i < records.size() ; i++){
            avgScore += Integer.parseInt(records.get(i).getScore());
        }
        avgScore = Math.ceil(avgScore / records.size());
        if(avgScore <= 0){
            avgScore = 0;
        }
        intAvgScore = (int) avgScore;
        average.setText(Integer.toString(intAvgScore));

        TableColumn<String, Record> roundCol = new TableColumn<>("Round");
        roundCol.setCellValueFactory(new PropertyValueFactory<>("round"));
        resultTable.getColumns().add(roundCol);

        TableColumn<String, Record> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        resultTable.getColumns().add(scoreCol);

        TableColumn<String, Record> cardCol = new TableColumn<>("Cards Selected");
        cardCol.setCellValueFactory(new PropertyValueFactory<>("cardselected"));
        resultTable.getColumns().add(cardCol);

        TableColumn<String, Record> lossCardCol = new TableColumn<>("Loss Cards");
        lossCardCol.setCellValueFactory(new PropertyValueFactory<>("losscards"));
        resultTable.getColumns().add(lossCardCol);

        TableColumn<String, Record> lossAmountCol = new TableColumn<>("Loss Amount");
        lossAmountCol.setCellValueFactory(new PropertyValueFactory<>("lossamount"));
        resultTable.getColumns().add(lossAmountCol);

        TableColumn<String, Record> gainAmountCol = new TableColumn<>("Gain Amount");
        gainAmountCol.setCellValueFactory(new PropertyValueFactory<>("gainamount"));
        resultTable.getColumns().add(gainAmountCol);

        TableColumn<String, Record> isWinCol = new TableColumn<>("Round Outcome");
        isWinCol.setCellValueFactory(new PropertyValueFactory<>("iswin"));
        resultTable.getColumns().add(isWinCol);

        resultTable.setItems(records);
    }

    public boolean checkRound(){
        if(lossCardCounter == 0){
            lossCardCounter = 1;
        }else{
            lossCardCounter = 0;
            if(lossAmountCounter == 0){
                lossAmountCounter = 1;
            }else{
                lossAmountCounter = 0;
                if(gainAmountCounter == 0){
                    gainAmountCounter = 1;
                }else{
                    gainAmountCounter = 0;
                    lossCardCounter = 0;
                    lossAmountCounter = 0;
                }
            }
        }
        return true;
    }

    public void initEnd(){
        createTable();
        scale.removeAll();
        for(int i = 1; i < 11; i++){
            scale.add(i);
        }
        msesChoice.setItems(scale);
        soepChoice.setItems(scale);
        gamePane.setVisible(false);
        quesPane.setVisible(false);
        msesPane.setVisible(true);
    }

    public void startGame(){
        if(curRound == -1){
            gamePane.setVisible(false);
            afterPracticePane.setVisible(true);
            curRound += 1;
            roundLabel.setText("Round");
        }else{
            if(curRound >= 24){
                initEnd();
            }else{
                if(curRound < 0){
                    roundLabel.setText("Practice Round");
                    roundNum.setText("");
                    curRound += 1;
                    curRoundScore = 0;
                    if(curRound == -4){
                        curGainAmount = model.getGainAmount(0);
                        curLossAmount = model.getLossAmount(0);
                        curLossCards = model.getLossCardNum(0);
                    }else if(curRound == -3){
                        curGainAmount = model.getGainAmount(0);
                        curLossAmount = model.getLossAmount(1);
                        curLossCards = model.getLossCardNum(1);
                    }else if(curRound == -2){
                        curGainAmount = model.getGainAmount(1);
                        curLossAmount = model.getLossAmount(1);
                        curLossCards = model.getLossCardNum(0);
                    }else if(curRound == -1){
                        curGainAmount = model.getGainAmount(1);
                        curLossAmount = model.getLossAmount(1);
                        curLossCards = model.getLossCardNum(1);
                    }
                    curSelected = 0;
                    roundScore.setText(String.valueOf(curRoundScore));
                    roundGainAmount.setText(String.valueOf(curGainAmount));
                    roundLossAmount.setText(String.valueOf(curLossAmount));
                    roundLossCards.setText(String.valueOf(curLossCards));
                    buttonPane.getChildren().clear();
                    nextRound.setDisable(true);
                    endRound.setDisable(false);
                    addCards(4,8);
                }else{
                    curRound += 1;
                    if(curRound >= 24){
                        nextRound.setText("Finish Game");
                    }
                    curRoundScore = 0;
                    curGainAmount = model.getGainAmount(gainAmountCounter);
                    curLossAmount = model.getLossAmount(lossAmountCounter);
                    curLossCards = model.getLossCardNum(lossCardCounter);
                    curSelected = 0;
                    roundNum.setText(String.valueOf(curRound));
                    roundScore.setText(String.valueOf(curRoundScore));
                    roundGainAmount.setText(String.valueOf(curGainAmount));
                    roundLossAmount.setText(String.valueOf(curLossAmount));
                    roundLossCards.setText(String.valueOf(curLossCards));
                    buttonPane.getChildren().clear();
                    nextRound.setDisable(true);
                    endRound.setDisable(false);
                    addCards(4,8);
                    checkRound();
                }
            }
        }

    }

    public void endGame(){
        if(curRound >= 0){
            recordRound();
        }
        for(Node b : buttonPane.getChildren()){
            b = (Button) b;
            if(b.getId().equalsIgnoreCase("hiddenGainCard")){
                b.setId("gainCard");
            }else if(b.getId().equalsIgnoreCase("hiddenLossCard")){
                b.setId("lossCard");
            }
            b.setDisable(true);
        }
        nextRound.setDisable(false);
        endRound.setDisable(true);
    }

    public void endRound(ActionEvent actionEvent) {
        endGame();
    }

    public void startTitle(ActionEvent actionEvent) {
        instruct1.setText("The Gain Card: For every gain card you turn over, your score increases by either "+model.getGainAmount(1)+" or "+model.getGainAmount(0)+" points in different rounds.");
        instruct2.setText("The Loss Card: For every loss card you turn over, your score decreases by either "+model.getLossAmount(1)+" or "+model.getLossAmount(0)+" points, depending on the round. Furthermore, the round immediately ends (you cannot turn over any more cards). There will be either "+model.getLossCardNum(1)+" or "+model.getLossCardNum(0)+" loss cards in different rounds.");
        playerPane.setVisible(true);
        titlePane.setVisible(false);
    }

    public void startMSES(ActionEvent actionEvent) {
        gamePane.setVisible(false);
        quesPane.setVisible(false);
        msesPane.setVisible(true);
    }

    public void startQUES(ActionEvent actionEvent) {
        msesPane.setVisible(false);
        midusPane.setVisible(false);
        quesPane.setVisible(true);
    }

    public void startMIDUS(ActionEvent actionEvent) {
        quesPane.setVisible(false);
        midus2Pane.setVisible(false);
        midusPane.setVisible(true);
    }

    public void startMIDUS2(ActionEvent actionEvent) {
        midusPane.setVisible(false);
        midus3Pane.setVisible(false);
        midus2Pane.setVisible(true);
    }

    public void startMIDUS3(ActionEvent actionEvent) {
        midus2Pane.setVisible(false);
        soepPane.setVisible(false);
        midus3Pane.setVisible(true);
    }

    public void startSOEP(ActionEvent actionEvent) {
        midus3Pane.setVisible(false);
        endScorePane.setVisible(false);
        soepPane.setVisible(true);
    }

    public void startResult(ActionEvent actionEvent) {
        soepPane.setVisible(false);
        endScorePane.setVisible(true);
        String[] record = new String[2];
        record[0] = "Payout";
        record[1] = Integer.toString(intAvgScore);
        model.recordRound(record);
        record = new String[2];
        record[0] = "MSES";
        record[1] = Integer.toString((Integer) msesChoice.getValue());
        model.recordRound(record);
        record = new String[2];
        record[0] = "Gender";
        record[1] = ((RadioButton)gender.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "Highschool";
        record[1] = highschool.getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "Number of persons in the household";
        record[1] = numOfPerson.getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "Is your home a building of its own? (not a condo or townhouse)";
        record[1] = ((RadioButton)homeBuilding.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "Do you or your family own the home?";
        record[1] = ((RadioButton)ownHome.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "Is your home larger than 100sqm?";
        record[1] = ((RadioButton)homeLarger.getSelectedToggle()).getText();
        model.recordRound(record);

        record = new String[2];
        record[0] = "MIDUS Question 1";
        record[1] = ((RadioButton)mq1.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 2";
        record[1] = ((RadioButton)mq2.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 3";
        record[1] = ((RadioButton)mq3.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 4";
        record[1] = ((RadioButton)mq4.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 5";
        record[1] = ((RadioButton)mq5.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 6";
        record[1] = ((RadioButton)mq6.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 7";
        record[1] = ((RadioButton)mq7.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 8";
        record[1] = ((RadioButton)mq8.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 9";
        record[1] = ((RadioButton)mq9.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 10";
        record[1] = ((RadioButton)mq10.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 11";
        record[1] = ((RadioButton)mq11.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 12";
        record[1] = ((RadioButton)mq12.getSelectedToggle()).getText();
        model.recordRound(record);
        record = new String[2];
        record[0] = "MIDUS Question 12";
        record[1] = ((RadioButton)mq12.getSelectedToggle()).getText();
        model.recordRound(record);

        record = new String[2];
        record[0] = "SOEP";
        record[1] = Integer.toString((Integer) soepChoice.getValue());
        model.recordRound(record);
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void backTitle(ActionEvent actionEvent) {
        playerPane.setVisible(false);
        settingPane.setVisible(false);
        titlePane.setVisible(true);
    }

    public void startRound(ActionEvent actionEvent) {
        model.startRecord(playerName.getText().trim());
        startGame();
        afterPracticePane.setVisible(false);
        gamePane.setVisible(true);
    }

    public void startPracticeRound(ActionEvent actionEvent) {
        startGame();
        playerPane.setVisible(false);
        gamePane.setVisible(true);
    }

    public void recordRound() {
        String[] record = new String[7];
        record[0] = Integer.toString(curRound);
        record[1] = Integer.toString(curRoundScore);
        record[2] = Integer.toString(curSelected);
        record[3] = Integer.toString(curLossCards);
        record[4] = Integer.toString(curLossAmount);
        record[5] = Integer.toString(curGainAmount);
        boolean roundWin = curSelected * curGainAmount == curRoundScore;
        if(roundWin){
            record[6] = "Win";
        }else{
            record[6] = "Lose";
        }
        model.recordRound(record);
        records.add(new Record(record[0],record[1],record[2],record[3],record[4],record[5],record[6]));
    }

    public void enableStart(KeyEvent inputMethodEvent) {
        if(playerName.getText().trim().isEmpty()){
            playButton.setDisable(true);
        }else{
            playButton.setDisable(false);
        }
    }

    public void saveSetting(ActionEvent actionEvent) {
        model.writeSettingsCSV(lossCardHigh.getText(),lossCardLow.getText(),lossAmountHigh.getText(),lossAmountLow.getText(),gainAmountHigh.getText(),gainAmountLow.getText());
        model.readSettings();
        settingPane.setVisible(false);
        titlePane.setVisible(true);
    }

    public void startSetting(ActionEvent actionEvent) {
        gainAmountHigh.setText(Integer.toString(model.getGainAmount(0)));
        gainAmountLow.setText(Integer.toString(model.getGainAmount(1)));
        lossAmountHigh.setText(Integer.toString(model.getLossAmount(0)));
        lossAmountLow.setText(Integer.toString(model.getLossAmount(1)));
        lossCardHigh.setText(Integer.toString(model.getLossCardNum(0)));
        lossCardLow.setText(Integer.toString(model.getLossCardNum(1)));

        titlePane.setVisible(false);
        settingPane.setVisible(true);
    }

    public class Record{

        private SimpleStringProperty round;
        private SimpleStringProperty score;
        private SimpleStringProperty cardselected;
        private SimpleStringProperty losscards;
        private SimpleStringProperty lossamount;
        private SimpleStringProperty gainamount;
        private SimpleStringProperty iswin;

        private Record(String round, String score, String cardselected, String losscards, String lossamount, String gainamount, String iswin) {
            this.round = new SimpleStringProperty(round);
            this.score = new SimpleStringProperty(score);
            this.cardselected = new SimpleStringProperty(cardselected);
            this.losscards = new SimpleStringProperty(losscards);
            this.lossamount = new SimpleStringProperty(lossamount);
            this.gainamount = new SimpleStringProperty(gainamount);
            this.iswin = new SimpleStringProperty(iswin);
        }

        public String getRound() {
            return round.get();
        }
        public void setRound(String round) {
            this.round.set(round);
        }

        public String getScore() {
            return score.get();
        }
        public void setScore(String score) {
            this.score.set(score);
        }

        public String getCardselected() {
            return cardselected.get();
        }
        public void setCardselected(String cardselected) {
            this.cardselected.set(cardselected);
        }

        public String getLosscards() {
            return losscards.get();
        }
        public void setLosscards(String losscards) {
            this.losscards.set(losscards);
        }

        public String getLossamount() {
            return lossamount.get();
        }
        public void setLossamount(String lossamount) {
            this.lossamount.set(lossamount);
        }

        public String getGainamount() {
            return gainamount.get();
        }
        public void setGainamount(String gainamount) {
            this.gainamount.set(gainamount);
        }

        public String getIswin() {
            return iswin.get();
        }
        public void setIswin(String iswin) {
            this.iswin.set(iswin);
        }
    }

}
