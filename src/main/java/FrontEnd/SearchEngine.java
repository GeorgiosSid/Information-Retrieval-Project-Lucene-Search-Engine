package FrontEnd;

import BackEnd.BackEndEngine;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;

public class SearchEngine extends JFrame implements ActionListener {
    private JTextField searchField;
    private JComboBox<String> searchHistoryDropdown;
    private JTextPane resultsArea;
    private JButton showMoreButton,groupButton,clearHistory,historyButton,searchButton,getSimilarButton;
    private ArrayList<String> inputQuery=new ArrayList<String>();
    private String query;
    private Integer QueryType,showMoreCounter=0,artistcounter=0;
    private BackEndEngine backEndEngine = new BackEndEngine();
    private String previousAnswer="";
    private  HTMLDocument doc;
    private Style style1;
    private JCheckBox checkBox;

    public SearchEngine() throws CsvValidationException, IOException {
        super("4674_4789 Search Engine");

        searchField = new JTextField(20);
        searchField.setComponentPopupMenu(createPopupMenu());
        searchButton = new JButton("Search");
        resultsArea=new JTextPane();
        resultsArea.setEditable(false);
        resultsArea.setContentType("text/html");
        doc=new HTMLDocument();
        resultsArea.setDocument(doc);
        checkBox = new JCheckBox("Incognito search");

        searchHistoryDropdown=new JComboBox<>(this.backEndEngine.getRecommendQuery().toArray(new String[this.backEndEngine.getRecommendQuery().size()]));
        searchHistoryDropdown.setPreferredSize(new Dimension(400,20 )); // Set preferred width and height
        searchHistoryDropdown.setEditable(false);

        ImageIcon icon = new ImageIcon("C://Programming//Lucene//Project_4674_4789//unnamed.png");
        setIconImage(icon.getImage());
        setVisible(true);

        style1 = doc.addStyle("regular", null);
        StyleConstants.setForeground(style1, Color.BLACK);
        StyleConstants.setFontSize(style1, 14);

        showMoreButton = new JButton("Show More");
        showMoreButton.setEnabled(false);

        historyButton = new JButton("History");
        historyButton.addActionListener(this);

        clearHistory=new JButton("Clear History");
        clearHistory.addActionListener(this);
        clearHistory.setEnabled(false);

        groupButton=new JButton("Group by");
        groupButton.setEnabled(false);

        getSimilarButton=new JButton("Get Similar");
        getSimilarButton.setEnabled(true);

        searchButton.addActionListener(this);
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {searchButton.setBackground(UIManager.getColor("Button.background"));}

            public void mousePressed(MouseEvent e) {
                Point point = searchButton.getLocation();
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        searchButton.setLocation(point.x, point.y - 50);
                    } else {
                        searchButton.setLocation(point.x, point.y + 50);
                    }
                }
                searchButton.setLocation(point);
            }
        });

        getSimilarButton.addActionListener(this);
        getSimilarButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                getSimilarButton.setBackground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {getSimilarButton.setBackground(UIManager.getColor("Button.background"));}

            public void mousePressed(MouseEvent e) {
                Point point = getSimilarButton.getLocation();
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        getSimilarButton.setLocation(point.x, point.y - 50);
                    } else {
                        getSimilarButton.setLocation(point.x, point.y + 50);
                    }
                }
                getSimilarButton.setLocation(point);
            }
        });

        showMoreButton.addActionListener(this);
        showMoreButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                showMoreButton.setBackground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {showMoreButton.setBackground(UIManager.getColor("Button.background"));}

            public void mousePressed(MouseEvent e) {
                Point point = showMoreButton.getLocation();
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        showMoreButton.setLocation(point.x, point.y - 50);
                    } else {
                        showMoreButton.setLocation(point.x, point.y + 50);
                    }
                }
                showMoreButton.setLocation(point);
            }
        });

        historyButton.addActionListener(this);
        historyButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                historyButton.setBackground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                historyButton.setBackground(UIManager.getColor("Button.background"));
            }

            public void mousePressed(MouseEvent e) {
                Point point = historyButton.getLocation();
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        historyButton.setLocation(point.x, point.y - 50);
                    } else {
                        historyButton.setLocation(point.x, point.y + 50);
                    }
                }
                historyButton.setLocation(point);
            }
        });

        clearHistory.addActionListener(this);
        clearHistory.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clearHistory.setBackground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {clearHistory.setBackground(UIManager.getColor("Button.background"));}

            public void mousePressed(MouseEvent e) {
                Point point = clearHistory.getLocation();
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        clearHistory.setLocation(point.x, point.y - 50);
                    } else {
                        clearHistory.setLocation(point.x, point.y + 50);
                    }
                }
                clearHistory.setLocation(point);
            }
        });

        groupButton.addActionListener(this);
        groupButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                groupButton.setBackground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {groupButton.setBackground(UIManager.getColor("Button.background"));}

            public void mousePressed(MouseEvent e) {
                Point point = groupButton.getLocation();
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        groupButton.setLocation(point.x, point.y - 50);
                    } else {
                        groupButton.setLocation(point.x, point.y + 50);
                    }
                }
                groupButton.setLocation(point);
            }
        });

        searchHistoryDropdown.addActionListener(e -> {
            String selectedItem = (String) searchHistoryDropdown.getSelectedItem();
            
            StringSelection selection = new StringSelection(selectedItem);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        });

        checkBox.addActionListener(this);

        // Layout components
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(211, 127, 82));

        JLabel titleLabel = new JLabel("Google Engine");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(searchHistoryDropdown);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(253, 237, 236));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        JPanel showMorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        showMorePanel.setOpaque(false);
        showMorePanel.add(showMoreButton);
        showMorePanel.add(historyButton);
        showMorePanel.add(clearHistory);
        showMorePanel.add(groupButton);
        showMorePanel.add(getSimilarButton);
        showMorePanel.add(checkBox);
        bottomPanel.add(showMorePanel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);

        // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.paste();
            }
        });
        popupMenu.add(pasteItem);

        return popupMenu;
    }

    private boolean hasReservedCharacters(String input) {
        String[] reservedCharacters = {"+","-","&&", "||", "!", "{", "}","[", "]", "^", "\"", "~", "?","@",":","#","$","%"};

        for (String character : reservedCharacters) if (input.contains(character)) return true;

        return false;
    }

    private static boolean isEnglishWord(String str) {
        return str.matches("[a-zA-Z]*");
    }

    private boolean checkNotEnglish(String[] words){
        for(int index=0;index<words.length;index++){
            String cleanWord = words[index].replaceAll("[\\p{P}\\p{S}\\d]", "");

            if(!isEnglishWord(cleanWord)) return  true;
        }
        return false;
    }

    private boolean isValidInput(String input) {
        String[] words = input.split("\\s+");

        if (input == null || input.trim().isEmpty()|| words.length<6 || words.length==8 || words.length==9 || checkNotEnglish(words)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid search query.");
            this.checkBox.setText("");
            return false;
        }

        else{
            if(words.length>10 || !words[0].equalsIgnoreCase("search")){
                JOptionPane.showMessageDialog(this, "Please enter a valid search query.The starting word must be 'search'.");
                this.checkBox.setText("");
                return false;
            }

            if(words[1].equalsIgnoreCase("prefix") || words[1].equalsIgnoreCase("fuzzy")){
                if(!(words[3].equals("on") || words[5].equals("show") || words.length==7)){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search query.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[4].equalsIgnoreCase("title")|| words[4].equalsIgnoreCase("text") || words[4].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search-field .The search-fields is : title , text , artist.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[6].equalsIgnoreCase("title")|| words[6].equalsIgnoreCase("artist") || words[6].equalsIgnoreCase("link"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid field in order to retrieve information. You may choose from: title, link, or artist.");
                    this.checkBox.setText("");
                    return false;
                }

                if(words[1].equalsIgnoreCase("prefix"))this.QueryType=3;
                else this.QueryType=4;

                if(hasReservedCharacters(words[2]) || words[2].contains("*") || words[2].equalsIgnoreCase("*")){
                    JOptionPane.showMessageDialog(this, "Please enter a valid term to search. The term has invalid characters.");
                    this.checkBox.setText("");
                    return false;
                }

                this.inputQuery.add(words[4]);
                this.inputQuery.add(words[2]);
            }

            if(words.length==10){
                if(!(words[2].equalsIgnoreCase("on") || words[6].equalsIgnoreCase("on") || words[8].equalsIgnoreCase("show"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search query.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[3].equalsIgnoreCase("title") || words[3].equalsIgnoreCase("text") || words[3].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search-field .The search-fields is : title , text , artist.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[4].equalsIgnoreCase("%and%") || words[4].equalsIgnoreCase("%or%") || words[4].equalsIgnoreCase("%not%") )){
                    JOptionPane.showMessageDialog(this, "Please enter a valid relOp( '%and%' , '%or%' , '%not%')");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[7].equalsIgnoreCase("title") || words[7].equalsIgnoreCase("text") || words[7].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search-field .The search-fields is : title , text , artist.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[9].equalsIgnoreCase("title") || words[9].equalsIgnoreCase("link") || words[9].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid field in order to retrieve information. You may choose from: title, link, or artist.");
                    this.checkBox.setText("");
                    return false;
                }

                this.QueryType=5;

                if(words[4].equals("%not%"))this.backEndEngine.setRelOp(0);
                if(words[4].equals("%and%"))this.backEndEngine.setRelOp(1);
                if(words[4].equals("%or%"))this.backEndEngine.setRelOp(2);

                if(hasReservedCharacters(words[1]) || hasReservedCharacters(words[5])){
                    JOptionPane.showMessageDialog(this, "Please enter a valid terms to search .The terms have invalid characters");
                    this.checkBox.setText("");
                    return false;
                }

                if(words[1].equalsIgnoreCase("*")|| words[5].equalsIgnoreCase("*")){
                    JOptionPane.showMessageDialog(this, "Please enter a valid terms to search .The terms have invalid characters");
                    this.checkBox.setText("");
                    return false;
                }

                this.inputQuery.add(words[3]);
                this.inputQuery.add(words[7]);
                this.inputQuery.add(words[1]);
                this.inputQuery.add(words[5]);
            }

            if(words.length==6){
                if(!(words[2].equalsIgnoreCase("on"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search query.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[3].equalsIgnoreCase("title") || words[3].equalsIgnoreCase("text") || words[3].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search-field.The field is : title ,text, artist");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[4].equalsIgnoreCase("show"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search query.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[5].equalsIgnoreCase("title") || words[5].equalsIgnoreCase("link") || words[5].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid field to retrieve information . The field is : title , link , artist");
                    this.checkBox.setText("");
                    return false;
                }

                if(words[1].contains("*"))this.QueryType=2;
                else this.QueryType=1;

                if(hasReservedCharacters(words[1])){
                    JOptionPane.showMessageDialog(this, "Please enter a valid term to search .The term has invalid characters");
                    this.checkBox.setText("");
                    return false;
                }

                if(words[1].equalsIgnoreCase("*")){
                    JOptionPane.showMessageDialog(this, "Please enter a valid term to search .The term has invalid characters");
                    this.checkBox.setText("");
                    return false;
                }

                this.inputQuery.add(words[3]);
                this.inputQuery.add(words[1]);
            }

            if(words.length==7 && !words[1].equalsIgnoreCase("prefix") && !words[1].equalsIgnoreCase("fuzzy")){
                if(!(words[3].equalsIgnoreCase("on"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search query.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[4].equalsIgnoreCase("title") || words[4].equalsIgnoreCase("text") || words[4].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid field.The field is : title ,text, artist");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[5].equalsIgnoreCase("show"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid search query.");
                    this.checkBox.setText("");
                    return false;
                }

                if(!(words[6].equalsIgnoreCase("title") || words[6].equalsIgnoreCase("link") || words[6].equalsIgnoreCase("artist"))){
                    JOptionPane.showMessageDialog(this, "Please enter a valid field in order to retrieve information. You may choose from: title, link, or artist.");
                    this.checkBox.setText("");
                    return false;
                }

                if(hasReservedCharacters(words[1]) || hasReservedCharacters(words[2])|| words[1].contains("*")|| words[2].contains("*")){
                    JOptionPane.showMessageDialog(this, "Please enter a valid phrase to search .The phrase has invalid characters");
                    this.checkBox.setText("");
                    return false;
                }

                if(words[1].equalsIgnoreCase("*")|| words[2].equalsIgnoreCase("*")){
                    JOptionPane.showMessageDialog(this, "Please enter a valid phrase to search .The phrase has invalid characters");
                    this.checkBox.setText("");
                    return false;
                }

                this.QueryType=6;
                this.inputQuery.add(words[4]);
                this.inputQuery.add(words[1]);
                this.inputQuery.add(words[2]);

            }
        }
        return true;
    }

    public void actionPerformed(ActionEvent e) {
        String query=searchField.getText().trim();

        if(e.getSource()==checkBox){
            if(checkBox.isSelected()){
                this.resultsArea.setBackground(Color.GRAY);
                this.searchField.setBackground(Color.GRAY);
                this.searchField.setText("");
                this.resultsArea.setText("");
            }
            else {
                this.resultsArea.setBackground(Color.white);
                this.searchField.setBackground(Color.white);
                this.searchField.setText("");
                this.resultsArea.setText("");
            }
        }

        if(e.getSource()==getSimilarButton){
            this.resultsArea.setText("");
            String[] quer= query.split("\\s+");
            if(quer.length!=1 || hasReservedCharacters(quer[0]) || quer[0].contains("*")|| quer[0].contains("_") || checkNotEnglish(quer)){
                JOptionPane.showMessageDialog(this, "Please enter a valid field in order to get words from semantic analyze.");
                this.checkBox.setText("");
            }
            else {
                try {
                    displaySimilar(quer[0],quer[0].toLowerCase());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if (e.getSource() == searchButton) {
            clearHistory.setEnabled(false);
            this.inputQuery.clear();
            resultsArea.setText("");
            this.showMoreCounter=0;

            if (isValidInput(query)) {
                this.backEndEngine.setGivenSearch(query);
                this.backEndEngine.setInputQuery(this.inputQuery);

                try {
                    searchQuery();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    String[] temp=query.split("\\s+");
                    if(checkWhichdisplay(query)){
                        this.previousAnswer="";
                        displayColoredResults(temp[temp.length-1]);
                    }
                    else displaySimpleResults(temp[temp.length-1]);

                    this.backEndEngine.getRecommendQuery();

                    if(temp[temp.length-1].equalsIgnoreCase("link"))groupButton.setEnabled(false);

                } catch (IOException | InvalidTokenOffsetsException ex) {
                    throw new RuntimeException(ex);
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
            DefaultComboBoxModel<String> newModel=new DefaultComboBoxModel<>(this.backEndEngine.getRecommendQuery().toArray(new String[this.backEndEngine.getRecommendQuery().size()]));
            searchHistoryDropdown.setModel(newModel);
        }

        if (e.getSource() == showMoreButton) {
            clearHistory.setEnabled(false);
            try {
                String[] temp=query.split("\\s+");
                if(checkWhichdisplay(query)){
                    displayColoredResults(temp[temp.length-1]);
                }
                else displaySimpleResults(temp[temp.length-1]);

                if(temp[temp.length-1].equalsIgnoreCase("link"))groupButton.setEnabled(false);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (BadLocationException | InvalidTokenOffsetsException ex) {
                throw new RuntimeException(ex);
            }

        }

        if(e.getSource()==historyButton){
            resultsArea.setText("");
            this.showMoreCounter=0;
            showMoreButton.setEnabled(false);
            clearHistory.setEnabled(true);
            groupButton.setEnabled(false);

            try {
                showExtendedHistory();
            } catch (IOException | BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource()==clearHistory){
            resultsArea.setText(" ");

            try {
                this.backEndEngine.clearHistory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            this.showMoreCounter=0;
            showMoreButton.setEnabled(false);
            clearHistory.setEnabled(false);
            groupButton.setEnabled(false);

        }

        if(e.getSource()==groupButton){
            try {
                try {
                    String[] temp=query.split("\\s+");
                    groupResults(temp[temp.length-1]);
                } catch (InvalidTokenOffsetsException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }

            this.showMoreCounter=0;
            showMoreButton.setEnabled(false);
            clearHistory.setEnabled(false);
            groupButton.setEnabled(false);
        }
    }

    private  void showExtendedHistory() throws IOException, BadLocationException {
        resultsArea.setText("");
        BufferedReader br = new BufferedReader(new FileReader("C://Programming//Lucene//Project_4674_4789//history.txt"));
        String line;

        while ((line = br.readLine()) != null) doc.insertString(doc.getLength(),line+"\n",style1);

        br.close();
    }

    private void searchQuery() throws ParseException, IOException {this.backEndEngine.executeQuery(this.QueryType,checkBox.isSelected());}

    private void displaySimpleResults(String field) throws IOException, BadLocationException, InvalidTokenOffsetsException {
        ArrayList<String> results = this.backEndEngine.getResults(field);

        if (results.size() == 0) {
            JOptionPane.showMessageDialog(this, "No results found");
            return;
        }

        if(field.equalsIgnoreCase("artist")){
            Map<String,Long> counted=results.stream().collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new, Collectors.counting()));
            ArrayList<String> resultsName= new ArrayList<>(counted.keySet());
            ArrayList<Long> numberAppierence= new ArrayList<>(counted.values());

            showMoreButton.setEnabled(true);
            groupButton.setEnabled(true);

            for (int index = 0; index < 10; index++) {
                if (this.showMoreCounter == resultsName.size()) {
                    if (resultsName.size() > 10) {
                        JOptionPane.showMessageDialog(this, "No more results. All " + this.showMoreCounter + " results found.");
                        showMoreButton.setEnabled(false);
                        break;
                    }

                    showMoreButton.setEnabled(false);
                    break;
                }
                doc.insertString(doc.getLength(),resultsName.get(showMoreCounter).replace("\n"," (")+numberAppierence.get(showMoreCounter)+") \n",style1);
                this.showMoreCounter += 1;
                this.artistcounter+=1;
            }
        }
        else {
            showMoreButton.setEnabled(true);
            groupButton.setEnabled(true);

            for (int index = 0; index < 10; index++) {
                if (this.showMoreCounter == results.size()) {
                    if (results.size() > 10) {
                        JOptionPane.showMessageDialog(this, "No more results. All " + this.showMoreCounter + " results found.");
                        showMoreButton.setEnabled(false);
                        break;
                    }
                    showMoreButton.setEnabled(false);
                    break;
                }
                doc.insertString(doc.getLength(), results.get(showMoreCounter), style1);
                this.showMoreCounter += 1;
            }
        }
    }

    private void displayColoredResults(String field) throws InvalidTokenOffsetsException, IOException, BadLocationException {
        ArrayList<String> results = this.backEndEngine.getResultsColored(field);

        if (results.size() == 0) {
            JOptionPane.showMessageDialog(this, "No results found");
            return;
        }

        showMoreButton.setEnabled(true);
        groupButton.setEnabled(true);

        StringBuilder stringBuilder = new StringBuilder();

        if(!this.previousAnswer.isEmpty())stringBuilder.append(this.previousAnswer);

        for (int index = 0; index < 10;) {
            if (this.showMoreCounter == results.size()) {
                if (results.size() > 10) {
                    JOptionPane.showMessageDialog(this, "No more results. All results found.");
                    showMoreButton.setEnabled(false);
                    break;
                }
                showMoreButton.setEnabled(false);
                break;
            }

            if(field.equalsIgnoreCase("artist")){
                if(!stringBuilder.toString().contains(results.get(showMoreCounter))){
                    stringBuilder.append(results.get(showMoreCounter));
                    stringBuilder.append("<br>");
                    this.artistcounter++;
                    index++;
                }
                this.showMoreCounter += 1;
            }
            else {
                stringBuilder.append(results.get(showMoreCounter));
                stringBuilder.append("<br>");
                index++;
                this.showMoreCounter += 1;
            }
        }
        this.previousAnswer=stringBuilder.toString();
        resultsArea.setText(stringBuilder.toString());
    }

    private boolean checkWhichdisplay(String query){
        String[] words = query.split("\\s+");

        if(this.QueryType==1 ||this.QueryType==2)
            if(words[3].equalsIgnoreCase(words[5]) && (words[3].equalsIgnoreCase("title")||words[3].equalsIgnoreCase("artist")))return true;

        if(this.QueryType==3)
            if(words[4].equalsIgnoreCase(words[6]) && (words[4].equalsIgnoreCase("title")||words[4].equalsIgnoreCase("artist")))return true;

        if(this.QueryType==5)
            if((words[9].equalsIgnoreCase(words[3]) && words[9].equalsIgnoreCase(words[7])) && (words[9].equalsIgnoreCase("title") || words[9].equalsIgnoreCase("artist")))return true;

        if(this.QueryType==6)
            if(words[4].equalsIgnoreCase(words[6])&&(words[6].equalsIgnoreCase("title")|| words[6].equalsIgnoreCase("artist")))return true;

        return false;
    }

    private String randomColorSelection(){
        Random rant = new Random();
        return "color:rgb("+rant.nextInt(255)+","+rant.nextInt(255)+","+rant.nextInt(255)+")";
    }

    private void groupResults(String field) throws BadLocationException, InvalidTokenOffsetsException, IOException {
        this.resultsArea.setText("");
        doc.remove(0, doc.getLength());
        this.resultsArea.setCaretPosition(0);
        int counter=0;

        ArrayList<String> results = this.backEndEngine.getResults(field);

        if(field.equalsIgnoreCase("artist")){
            Set<String> set = new LinkedHashSet<>(results);
            results.clear();
            results.addAll(set);
            counter=this.artistcounter;
        }
        else counter=this.showMoreCounter;

        HashMap<Character, ArrayList<String>> groups = new HashMap<Character,ArrayList<String>>();
        for (int i = 0; i <counter; i++) {
            String s = results.get(i);
            char c = s.charAt(0);

            if (!groups.containsKey(c))groups.put(c, new ArrayList<>());

            groups.get(c).add(s);
        }

        StringBuilder sb = new StringBuilder();
        ArrayList<Character> sortedKeys = new ArrayList<>(groups.keySet());
        Collections.sort(sortedKeys);

        for (char key : sortedKeys) {
            ArrayList<String> group = groups.get(key);
            String randomColorSelection=randomColorSelection();

            for (String s : group) sb.append("<p style='").append(randomColorSelection).append("'>").append(s).append("</p>");

            sb.append("<br>");
        }
        this.resultsArea.setText(sb.toString());
        this.artistcounter=0;
    }

    private void displaySimilar(String notLower ,String target) throws BadLocationException {
        Collection<String> sm=this.backEndEngine.getSimilar(target);
        String[] words= new String[1];

        for (String word : sm) {
            words[0]=word;
            if(!hasReservedCharacters(word) && !checkNotEnglish(words) && !word.contains("*"))doc.insertString(doc.getLength(),"The word '" + notLower + "' is semantically related to '" + word+"'\n", style1);
        }
    }
}