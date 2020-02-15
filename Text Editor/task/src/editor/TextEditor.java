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
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(700, 300, 600, 400);
        setTitle("Text editor");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuLoad");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JTextField searchField= new JTextField();
        searchField.setName("SearchField");

        ImageIcon imgSave = new ImageIcon("C:\\Users\\nefed\\IdeaProjects\\Text Editor\\Text Editor\\task\\src\\editor\\save.png");
        JButton btnSave = new JButton(imgSave);
        btnSave.setName("SaveButton");

        ImageIcon imgOpen = new ImageIcon("C:\\Users\\nefed\\IdeaProjects\\Text Editor\\Text Editor\\task\\src\\editor\\open-folder.png");
        JButton btnOpen = new JButton(imgOpen);
        btnOpen.setName("OpenButton");

        ImageIcon imgSearch = new ImageIcon("C:\\Users\\nefed\\IdeaProjects\\Text Editor\\Text Editor\\task\\src\\editor\\magnifying-glass.png");
        JButton btnSearch = new JButton(imgSearch);
        btnSearch.setName("StartSearchButton");

        ImageIcon imgPrevious = new ImageIcon("C:\\Users\\nefed\\IdeaProjects\\Text Editor\\Text Editor\\task\\src\\editor\\previous-button.png");
        JButton btnPrevious = new JButton(imgPrevious);
        btnPrevious.setName("PreviousMatchButton");

        ImageIcon imgNext = new ImageIcon("C:\\Users\\nefed\\IdeaProjects\\Text Editor\\Text Editor\\task\\src\\editor\\next-button.png");
        JButton btnNext = new JButton(imgNext);
        btnNext.setName("NextMatchButton");

        JCheckBox regEx = new JCheckBox("Use regex");
        regEx.setName("UseRegExCheckbox");



        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //group layout is used
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



        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setName("FileChooser");
        jfc.setDialogTitle("Open file");

        btnOpen.addActionListener(actionEvent -> {
            try{
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                textArea.setText(Files.readString(Paths.get(selectedFile.getAbsolutePath())));
            }
            } catch (IOException e) {
                textArea.setText("");
                e.printStackTrace();
            }
        });


        openMenuItem.addActionListener(actionEvent -> {
            try{
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    textArea.setText(Files.readString(Paths.get(selectedFile.getAbsolutePath())));
                }
            } catch (IOException e) {
                textArea.setText("");
                e.printStackTrace();
            }
        });


        btnSave.addActionListener(actionEvent -> {
            File selectedFile = jfc.getSelectedFile();
            try(FileWriter fileWriter = new FileWriter(selectedFile.getAbsolutePath())) {
                fileWriter.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        saveMenuItem.addActionListener(actionEvent -> {
            File selectedFile = jfc.getSelectedFile();
            try(FileWriter fileWriter = new FileWriter(selectedFile.getAbsolutePath())) {
                fileWriter.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        LinkedList<Integer> list = new LinkedList<>();

        btnSearch.addActionListener(actionEvent -> {
            SwingWorker<Integer, Object> worker = new SwingWorker<>() {
                @Override
                protected Integer doInBackground() throws Exception {
                    Pattern pattern = Pattern.compile(searchField.getText(), Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(textArea.getText());
                    int index = 0;
                    if (matcher.find()) {
                        index = matcher.start();
                        list.add(index);
                        String foundText = matcher.group();
                        textArea.setCaretPosition(index + foundText.length());
                        textArea.select(index, index + foundText.length());
                        textArea.grabFocus();
                    }
                    return index;
                }

                @Override
                protected void done() {
                }
            };
            worker.execute();
        });


        btnNext.addActionListener(actionEvent -> {
            SwingWorker<Integer, Object> worker = new SwingWorker<>() {
                @Override
                protected Integer doInBackground() throws Exception {
                    Pattern pattern = Pattern.compile(searchField.getText(), Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(textArea.getText());
                    int index = 0;
                    if (matcher.find(textArea.getCaretPosition())) {
                        index = matcher.start();
                        list.add(index);
                        String foundText = matcher.group();
                        textArea.setCaretPosition(index + foundText.length());
                        textArea.select(index, index + foundText.length());
                        textArea.grabFocus();
                    }
                    return index;
                }

                @Override
                protected void done() {
                }
            };
            worker.execute();
        });

        btnPrevious.addActionListener(actionEvent -> {
            SwingWorker<Integer, Object> worker = new SwingWorker<>() {
                @Override
                protected Integer doInBackground() throws Exception {
                    Pattern pattern = Pattern.compile(searchField.getText(), Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(textArea.getText());
                    if (list.size() > 0) {
                        list.removeLast();
                    }
                    int index = 0;
                    System.out.println(textArea.getCaretPosition());
                    System.out.println(textArea.getText());

                    if (!list.isEmpty() && matcher.find(list.getLast())) {
                        System.out.println(list.size());
                        index = matcher.start();
                        String foundText = matcher.group();
                        textArea.setCaretPosition(index + foundText.length());
                        textArea.select(index, index + foundText.length());
                        textArea.grabFocus();
                    }
                    return index;
                }

                @Override
                protected void done() {
                }
            };
            worker.execute();
        });


        exitMenuItem.addActionListener(actionEvent -> {
            dispose();
        });


        setJMenuBar(menuBar);
        contentPane.setLayout(gl_contentPane);
        setVisible(true);
    }
}
