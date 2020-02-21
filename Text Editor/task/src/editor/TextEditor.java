package editor;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu searchMenu;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem startSearch;
    private JMenuItem previousMatch;
    private JMenuItem nextMatch;
    private JMenuItem menuRegEx;
    private JPanel contentPane;
    private  JTextField searchField;
    private ImageIcon imgSave;
    private JButton btnSave;
    private ImageIcon imgOpen;
    private JButton btnOpen;
    private ImageIcon imgSearch;
    private JButton btnSearch;
    private ImageIcon imgPrevious;
    private JButton btnPrevious;
    private ImageIcon imgNext;
    private JButton btnNext;
    private JCheckBox regEx;
    private JFileChooser jfc;
    private JTextArea textArea;
    private JScrollPane scrollableTextArea;
    private List<Integer> positions;
    private String searchWord;

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(700, 300, 600, 400);
        setTitle("Text editor");

        //create menu bar and file menu
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        //create items for file menu
        openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        //create search menu
        searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);
        menuBar.add(searchMenu);

        startSearch = new JMenuItem("Start Search");
        startSearch.setName("MenuStartSearch");
        previousMatch = new JMenuItem("Previous Match");
        previousMatch.setName("MenuPreviousMatch");
        nextMatch = new JMenuItem("Next Match");
        nextMatch.setName("MenuNextMatch");
        menuRegEx = new JMenuItem("Use regular expressions");
        menuRegEx.setName("MenuUseRegExp");

        searchMenu.add(startSearch);
        searchMenu.add(previousMatch);
        searchMenu.add(nextMatch);
        searchMenu.add(menuRegEx);

        //initializing content pane for group layout
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        searchField = new JTextField();
        searchField.setName("SearchField");
        //create buttons and add icons on buttons
        imgSave = new ImageIcon("Text Editor/task/src/save.png");
        btnSave = new JButton(imgSave);
        btnSave.setName("SaveButton");

        imgOpen = new ImageIcon("Text Editor/task/src/open-folder.png");
        btnOpen = new JButton(imgOpen);
        btnOpen.setName("OpenButton");

        imgSearch = new ImageIcon("Text Editor/task/src/magnifying-glass.png");
        btnSearch = new JButton(imgSearch);
        btnSearch.setName("StartSearchButton");

        imgPrevious = new ImageIcon("Text Editor/task/src/previous-button.png");
        btnPrevious = new JButton(imgPrevious);
        btnPrevious.setName("PreviousMatchButton");

        imgNext = new ImageIcon("Text Editor/task/src/next-button.png");
        btnNext = new JButton(imgNext);
        btnNext.setName("NextMatchButton");

        //initializing checkbox
        regEx = new JCheckBox("Use regex");
        regEx.setName("UseRegExCheckbox");

        //initializing file chooser for save and load buttons
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setName("FileChooser");
        jfc.setDialogTitle("Choose file");


        //initializing text area and  scroll pane and wrap text area by scroll pane
        textArea = new JTextArea();
        textArea.setName("TextArea");
        scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //customize group layout to order elements
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(5)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollableTextArea, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                                                .addGap(6)
                                                .addComponent(searchField, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnPrevious, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(regEx)
                                                .addGap(9)))
                                .addGap(8))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(searchField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnPrevious, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(regEx))
                                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                                .addGap(11)
                                .addComponent(scrollableTextArea, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                .addContainerGap())
        );

        //duplicate checkbox for regular expressions
        menuRegEx.addActionListener(actionEvent -> {
            regEx.doClick();
        });

        //open file
        btnOpen.addActionListener(actionEvent -> {
           openFile();
        });

        //duplicate open file action by menu item open
        openMenuItem.addActionListener(actionEvent -> {
           btnOpen.doClick();
        });

        //save file
        btnSave.addActionListener(actionEvent -> {
           saveFile();
        });


        //duplicate save file action by menu item save
        saveMenuItem.addActionListener(actionEvent -> {
           btnSave.doClick();
        });

        //initializing list of caret positions
        positions = new ArrayList<>();

        //search word in text using swing worker
        btnSearch.addActionListener(actionEvent -> {
            search();
        });

        //duplicate start search button from menu item
        startSearch.addActionListener(actionEvent -> {
           btnSearch.doClick();
        });

        //search of next word
        btnNext.addActionListener(actionEvent -> {
            nextMatch();
        });

        //duplicate search of next matched word
        nextMatch.addActionListener(actionEvent -> {
            btnNext.doClick();
        });

        //search of previous matched word
        btnPrevious.addActionListener(actionEvent -> {
            previousMatch();
        });

        //duplicate search of previous matched word
        previousMatch.addActionListener(actionEvent -> {
            btnPrevious.doClick();
        });

        //close GUI from menu item
        exitMenuItem.addActionListener(actionEvent -> {
            dispose();
        });

        setJMenuBar(menuBar);
        contentPane.setLayout(gl_contentPane);
        add(jfc);
        setVisible(true);
    }

    private void search(){
        SwingWorker<Integer, Object> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() {
                positions.clear();
                int position = 0; //caret position at the beginning of the word
                searchWord = "";
                String text;
                //search word like a substring
                if (!regEx.isSelected()) {
                    searchWord = searchField.getText(); // take a word from search field
                    text = textArea.getText(); // take text from text area
                    do {
                        position = text.indexOf(searchWord, position); //get position
                        if (position >= 0){ // add position while text has matched words
                            positions.add(position);
                        }
                        position++;
                    }while (position > 0);
                }else { //search with regular expression
                    Pattern pattern = Pattern.compile(searchField.getText(), Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(textArea.getText());
                    while (matcher.find()) {
                        if (positions.isEmpty()) {
                            searchWord = matcher.group();
                        }
                        positions.add(matcher.start());
                    }
                }
                if (!positions.isEmpty()) {
                    position = positions.get(0); //start movement of caret from first suitable position
                }
                return position;
            }
            @Override
            protected void done() {
                int position;
                try {
                    position = get(); //get index from doInBackGround
                    textArea.setCaretPosition(position + searchWord.length());
                    textArea.select(position, position + searchWord.length());
                    textArea.grabFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void nextMatch(){
        int index = positions.indexOf(textArea.getCaretPosition() - searchWord.length()) + 1; //move caret position at the end of a file cyclically
        if (index > positions.size() - 1) { //if caret achieves the end of a file, start movement from the beginning
            index = 0;
        }
        textArea.setCaretPosition(positions.get(index) + searchWord.length());
        textArea.select(positions.get(index), positions.get(index) + searchWord.length());
        textArea.grabFocus();
    }

    private void previousMatch(){
        int index = positions.indexOf(textArea.getCaretPosition() - searchWord.length()) - 1; //move caret position at the beginning of a file cyclically
        if (index < 0) { //if caret achieves the beginning of a file, start movement from the end
            index = positions.size() - 1;
        }
        textArea.setCaretPosition(positions.get(index) + searchWord.length());
        textArea.select(positions.get(index), positions.get(index) + searchWord.length());
        textArea.grabFocus();
    }

    private void openFile(){
        try{
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                textArea.setText(Files.readString(Paths.get(selectedFile.getAbsolutePath()))); // set text from a file to text area
            }
        } catch (IOException e) {
            textArea.setText(""); //if an exception happens leave a file clear
            e.printStackTrace();
        }
    }

    private void saveFile(){
        File selectedFile = null;
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
        }
        try(FileWriter fileWriter = new FileWriter(selectedFile.getAbsolutePath())) {
            fileWriter.write(textArea.getText()); //write text to a selected file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
