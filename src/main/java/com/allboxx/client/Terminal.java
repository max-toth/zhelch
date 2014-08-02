package com.allboxx.client;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by max_tolstykh on 02/08/14.
 */
public class Terminal extends InputStream {

    byte[] contents;
    int pointer = 0;

    public Terminal(final JTextField text) {

        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    contents = text.getText().getBytes();
                    pointer = 0;
                    text.setText("");
                }
                super.keyReleased(e);
            }
        });
    }

    @Override
    public int read() throws IOException {
        if (pointer >= contents.length) return -1;
        return this.contents[pointer++];
    }

    public static void main(String[] args) throws IOException {
        InputStream in = new Terminal( new JTextField() );
        int c;
        while( (c = in.read()) != -1){
            System.out.print(c);
        }
    }
}
