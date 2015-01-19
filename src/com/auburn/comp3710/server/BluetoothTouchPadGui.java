package com.auburn.comp3710.server;

import javax.bluetooth.BluetoothStateException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by Robert on 4/9/2014.
 */
public class BluetoothTouchPadGui extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 200;

    private static final boolean RESIZABLE = false;
    private static final String DISCOVER_BUTTON_ACTION = "DISCOVER";
    private static final String DISCONNECT_BUTTON_ACTION = "DISCONNECT";

    private BluetoothTouchPadServer server;

    //private JPanel primaryContainer;
    private JPanel buttonContainer;
    private JScrollPane logContainer;

    private JButton discoverButton;
    private JButton disconnectButton;

    private JTextArea logArea;

    private TextAreaOutputStream outputStream;

    public BluetoothTouchPadGui() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        Dimension frameDimensions = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;


        setTitle("BluetoothTouchPad Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameDimensions);
        setLocation(screenWidth / 4, screenHeight / 8);

        setLayout(new BorderLayout());

        setUpButtonContainer();
        setUpLogContainer();

        setResizable(RESIZABLE);
        setVisible(true);

    }

    public static void startServerGui() {
        BluetoothTouchPadGui server = new BluetoothTouchPadGui();
    }
    
    public static void main(String[] args) {
    	startServerGui();
    	
    }

    private void setUpButtonContainer() {
        buttonContainer = new JPanel(new FlowLayout());
        setUpButtons();
        getContentPane().add(buttonContainer, BorderLayout.NORTH);
        //add(buttonContainer);
    }

    private void setUpButtons() {
        setUpDiscoverButton();
        setUpDisconnectButton();
        buttonContainer.add(discoverButton);
        buttonContainer.add(disconnectButton);
    }

    private void setUpDiscoverButton() {
        discoverButton = new JButton();
        discoverButton.setText("Discover Devices");

        discoverButton.addActionListener(this);
        discoverButton.setActionCommand(DISCOVER_BUTTON_ACTION);
    }

    private void setUpDisconnectButton() {
        disconnectButton = new JButton();
        disconnectButton.setText("Disconnect");

        disconnectButton.addActionListener(this);
        disconnectButton.setActionCommand(DISCONNECT_BUTTON_ACTION);
        disconnectButton.setEnabled(false);
    }

    private void setUpLogContainer() {
        setUpLogArea();
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setAutoscrolls(false);

        logContainer = scrollPane;

        outputStream = new TextAreaOutputStream(logArea);

        PrintStream con = new PrintStream(outputStream);
        System.setOut(con);
        System.setErr(con);

        getContentPane().add(logContainer, BorderLayout.CENTER);
    }

    private void setUpLogArea() {

        logArea = new JTextArea();

        logArea.setEditable(true);
        logArea.setAutoscrolls(false);
        DefaultCaret caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        Document document = logArea.getDocument();
        document.addDocumentListener(new ScrollingDocumentListener());

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(DISCOVER_BUTTON_ACTION)) {
            performDiscoverAction();
        } else if(e.getActionCommand().equals(DISCONNECT_BUTTON_ACTION)) {
            performDisconnectAction();
        }
    }

    private void performDiscoverAction() {
        if(server == null) {
            try {
                server = new BluetoothTouchPadServer();

            } catch (BluetoothStateException e) {
                System.out.println("Could not start BlueTooth server");
            } catch (IOException e) {
                System.out.println("IO Error - could not start bluetooth server:\n" + e);
            }
        }

        server.startServer();

        discoverButton.setEnabled(false);
        disconnectButton.setEnabled(true);
    }

    private void performDisconnectAction() {
        discoverButton.setEnabled(true);
        disconnectButton.setEnabled(false);

        try {
            server.stopServer();
        } catch(IOException e) {
            System.out.println("IO Exception occurred while closing connection:\n" + e);
        }


    }

    /**
     * Source borrowed from:
     * http://stackoverflow.com/questions/4045722/how-to-make-jtextpane-autoscroll-only-when-scroll-bar-is-at-bottom-and-scroll-lo
     */
    // ScrollingDocumentListener takes care of re-scrolling when appropriate
    class ScrollingDocumentListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        public void insertUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        public void removeUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        private void maybeScrollToBottom() {
            JScrollBar scrollBar = logContainer.getVerticalScrollBar();
            boolean scrollBarAtBottom = isScrollBarFullyExtended(scrollBar);
            boolean scrollLock = Toolkit.getDefaultToolkit()
                    .getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
            if (scrollBarAtBottom && !scrollLock) {
                // Push the call to "scrollToBottom" back TWO PLACES on the
                // AWT-EDT queue so that it runs *after* Swing has had an
                // opportunity to "react" to the appending of new text:
                // this ensures that we "scrollToBottom" only after a new
                // bottom has been recalculated during the natural
                // revalidation of the GUI that occurs after having
                // appending new text to the JTextArea.
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                scrollToBottom(logArea);
                            }
                        });
                    }
                });
            }
        }

        public boolean isScrollBarFullyExtended(JScrollBar vScrollBar) {
            BoundedRangeModel model = vScrollBar.getModel();
            return (model.getExtent() + model.getValue()) == model.getMaximum();
        }

        public void scrollToBottom(JComponent component) {
            Rectangle visibleRect = component.getVisibleRect();
            visibleRect.y = component.getHeight() - visibleRect.height;
            component.scrollRectToVisible(visibleRect);
        }


    }
}
