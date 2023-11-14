package com.atahanuz;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class FTPTransferGUI extends JFrame {
    private JButton transferButton;
    private JTextArea logArea;
    private JTextField serverUrlField;
    private List<File> fileList;
    JTextField filePathField;

    public FTPTransferGUI() {
        super("File2Android by Atahan Uz");

        fileList = new ArrayList<>();

        // Layout for the main frame
        setLayout(new BorderLayout());


        JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.add(new JLabel("FTP Server URL:"));
        serverUrlField = new JTextField("192.168.1.44:2221", 20);
        JButton myButton = new JButton("-> TUTORIAL <-");
        // add action listener to open a link
        myButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/atahanuz/file2android/").toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        urlPanel.add(serverUrlField);
        urlPanel.add(myButton);
        add(urlPanel, BorderLayout.NORTH);








        // Panel for the manual file add and drag-and-drop area
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Manual File Add Panel
        JPanel filePathPanel = new JPanel();
        filePathPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filePathPanel.add(new JLabel("Add File by Path:"));
        filePathField = new JTextField(20);
        filePathPanel.add(filePathField);

        JButton addFileButton = new JButton("Add File");
        addFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFileManually();
            }
        });
        filePathPanel.add(addFileButton);
        centerPanel.add(filePathPanel, BorderLayout.NORTH);

        // Drag and Drop Panel
        ImageIcon backgroundImageIcon;
        Image backgroundImage= null;
        try {
            backgroundImageIcon = new ImageIcon(getClass().getClassLoader().getResource("drag.png"));
            backgroundImage = backgroundImageIcon.getImage();
            // Use your backgroundImage as needed
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the error appropriately - the image might not be found
        }
        Image finalBackgroundImage = backgroundImage;
        JPanel dropPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the image scaled to the size of the panel
                g.drawImage(finalBackgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        dropPanel.setBorder(BorderFactory.createTitledBorder("Drop Files Here"));
        dropPanel.setLayout(new BorderLayout());


        dropPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setMultiSelectionEnabled(true);
                    int result = fileChooser.showOpenDialog(dropPanel);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File[] selectedFiles = fileChooser.getSelectedFiles();
                        processFiles(Arrays.asList(selectedFiles));
                    }
                }
            }
        });


        setupDragAndDrop(dropPanel);
        centerPanel.add(dropPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);



        // Transfer Button
        transferButton = new JButton("Transfer");
        transferButton.setSize(new Dimension(50, 50));
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transferFiles();
            }
        });

// Create a panel to hold the button with a margin
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // Right alignment, no horizontal gap, no vertical gap
        buttonPanel.add(transferButton); // Add the button to the panel

// Set an empty border to the main frame for the right margin of the button
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Top, left, bottom, right margins

// Add the panel with the button to the EAST
        add(buttonPanel, BorderLayout.EAST);








        // Log area
        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        // Transfer button
        transferButton = new JButton("Transfer");
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transferFiles();
            }
        });
        add(transferButton, BorderLayout.EAST);



        // set size


        // Default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(600, 600);
        setVisible(true);
    }


    private void processFiles(List<File> files) {
        fileList.addAll(files);
        for (File file : files) {
            long size = file.length();
            String readableSize;

            if (size < 1024) {
                // If size is less than 1024 bytes, show in bytes
                readableSize = size + " bytes";
            } else if (size < 1024 * 1024) {
                // If size is less than 1024 kilobytes, show in kilobytes
                readableSize = String.format("%.2f KB", size / 1024.0);
            } else if (size < 1024 * 1024 * 1024) {
                // If size is less than 1024 megabytes, show in megabytes
                readableSize = String.format("%.2f MB", size / (1024.0 * 1024));
            } else {
                // If size is 1024 megabytes and above, show in gigabytes
                readableSize = String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
            }

            logArea.append(file.getAbsolutePath() + " - Size: " + readableSize + "\n");
        }
    }

    private void setupDragAndDrop(JPanel panel) {
        panel.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            public boolean importData(TransferHandler.TransferSupport support) {
                try {
                    List<File> droppedFiles = (List<File>) support.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    fileList.addAll(droppedFiles);

                    for (File file : droppedFiles) {
                        long size = file.length();
                        String readableSize;

                        if (size < 1024) {
                            // If size is less than 1024 bytes, show in bytes
                            readableSize = size + " bytes";
                        } else if (size < 1024 * 1024) {
                            // If size is less than 1024 kilobytes, show in kilobytes
                            readableSize = String.format("%.2f KB", size / 1024.0);
                        } else if (size < 1024 * 1024 * 1024) {
                            // If size is less than 1024 megabytes, show in megabytes
                            readableSize = String.format("%.2f MB", size / (1024.0 * 1024));
                        } else {
                            // If size is 1024 megabytes and above, show in gigabytes
                            readableSize = String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
                        }

                        logArea.append(file.getAbsolutePath() + " - Size: " + readableSize + "\n");

                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }


    private void addFileManually() {
        String path = filePathField.getText();
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file.exists() && !file.isDirectory()) {
                fileList.add(file);
                logArea.append(file.getAbsolutePath() + " added manually.\n");
            } else {
                logArea.append("Invalid file path or file is a directory.\n");
            }
        }
    }

    private void transferFiles() {
        if(fileList.isEmpty()) {
            logArea.append("No files to transfer.\n");
            return;
        }
        logArea.append("####################\nStarting file transfer...\n");
        for (File file : fileList) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);

            transferFileToFtpServer(file);

            System.out.flush();
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            logArea.append(baos.toString());
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FTPTransferGUI();
            }
        });
    }

    // Method to handle file transfer (to be implemented)
    private void transferFileToFtpServer(File file) {
        String serverUrl = serverUrlField.getText();
        String absolutepath = file.getAbsolutePath();
        System.out.println("Transferring: " + absolutepath);
        FtpFileUpload.uploadFileViaFTP(absolutepath, serverUrl);
    }
}
