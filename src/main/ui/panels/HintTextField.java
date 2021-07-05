package main.ui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;

/**
 * source:          http://javaswingcomponents.blogspot.com/2012/05/how-to-create-simple-hinttextfield-in.html
 * retrieved in:    1/7/2021
 */

public class HintTextField extends JTextField {

    Font gainFont = new Font("Dialog", Font.BOLD, 18);
    Font lostFont = new Font("Dialog", Font.PLAIN, 18);

    public HintTextField(String hint, int columnSize) {
        setColumns(columnSize);
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        setHorizontalAlignment(SwingConstants.CENTER);
        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setForeground(Color.BLACK);
                } else {
                    setText(getText());
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
                setFont(gainFont);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint)|| getText().length()==0) {
                    setText(hint);
                    setFont(lostFont);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setForeground(Color.GRAY);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setForeground(Color.BLACK);
                }
            }
        });

    }
    public void updateHint(String text) {
        //setColumns(columnSize - text.length());
    }
}