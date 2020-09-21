// Renat Norderhaug CS 326 Hmwk #8
// 12/11/18

// importing java libraries

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

// class for the color board which instantaties the interface

class color_Board extends JComponent {
    Color newColor = Color.white;


    public void paint(Graphics graph) {
        Dimension d = getSize();
        graph.setColor(newColor);
        graph.fillRect(1, 1, d.width-2, d.height-2);
    }
}

public class hw8 extends JFrame{
// it is made protected so it cannot be accessed outside of this class
    protected JLabel labelRed, labelGreen, labelBlue;
    protected JTextField redField, greenField, blueField;
    protected JButton minusRed, positiveRed, minusGreen, positiveGreen, minusBlue, positiveBlue, buttonSave, buttonReset;
    protected JList listColors;

    // default for the array size
    protected int red = 255, g = 255, b = 255; protected int numLines = 0;
    // string array for the colors
    protected String[] colors; protected int[][] colorValues;

    public static void main(String argv []) throws IOException{
    // the class gets instantiated here
        new hw8("Color Samples:");
    }

    public hw8(String title) throws IOException {
        super(title);
        setBounds(0, 0, 320, 325);
        addWindowListener(new WindowDestroyer());

        //  These buttons are allocated here and put together
        colorBoard = new color_Board();
        labelRed = new JLabel("Red:");
        labelGreen = new JLabel("Green:");
        labelBlue = new JLabel("Blue:");

        redField = new JTextField("");
        greenField = new JTextField("");
        blueField = new JTextField("");

        minusRed = new JButton("-");
        minusGreen = new JButton("-");
        minusBlue = new JButton("-");

        positiveRed = new JButton("+");
        positiveGreen = new JButton("+");
        positiveBlue = new JButton("+");

        buttonSave = new JButton("Save");
        buttonReset = new JButton("Reset");
        listColors = new JList();
        listColors.addListSelectionListener(new ListHandler());
        JScrollPane colorPane = new JScrollPane(listColors);

        getContentPane().setLayout(null);
        getContentPane().add(colorBoard);

        getContentPane().add(redField);
        getContentPane().add(greenField);
        getContentPane().add(blueField);

        getContentPane().add(labelRed);
        getContentPane().add(labelGreen);
        getContentPane().add(labelBlue);

        getContentPane().add(positiveRed);
        getContentPane().add(positiveGreen);
        getContentPane().add(positiveBlue);


        getContentPane().add(minusRed);
        getContentPane().add(minusGreen);
        getContentPane().add(minusBlue);


        getContentPane().add(buttonSave);
        getContentPane().add(buttonReset);
        getContentPane().add(listColors);
        getContentPane().add(colorPane);

        // set the bounds of the Coloring board
        colorBoard.setBounds(10, 10, 200, 125);
        labelRed.setBounds(10, 115, 100, 100);
        labelGreen.setBounds(10, 145, 100, 100);
        labelBlue.setBounds(10, 175, 100, 100);

        redField.setBounds(55, 155, 40, 20);
        greenField.setBounds(55, 185, 40, 20);
        blueField.setBounds(55, 215, 40, 20);

        minusRed.setBounds(110, 155, 40, 20);
        minusRed.setBorder(null);
        minusGreen.setBounds(110, 185, 40, 20);
        minusGreen.setBorder(null);
        minusBlue.setBounds(110, 215, 40, 20);
        minusBlue.setBorder(null);

        positiveRed.setBounds(165, 155, 40, 20);
        positiveRed.setBorder(null);
        positiveGreen.setBounds(165, 185, 40, 20);
        positiveGreen.setBorder(null);
        positiveBlue.setBounds(165, 215, 40, 20);
        positiveBlue.setBorder(null);

        buttonSave.setBounds(35, 255, 60, 20);
        buttonReset.setBounds(110, 255, 60, 20);
        listColors.setBounds(220, 10, 75, 265);
        colorPane.setBounds(220, 10, 75, 265);

        minusRed.addActionListener(new ActionHandler());
        minusGreen.addActionListener(new ActionHandler());
        minusBlue.addActionListener(new ActionHandler());

        positiveRed.addActionListener(new ActionHandler());
        positiveGreen.addActionListener(new ActionHandler());
        positiveBlue.addActionListener(new ActionHandler());

        buttonSave.addActionListener(new ActionHandler());
        buttonReset.addActionListener(new ActionHandler());
        setVisible(true);

        // Finds the # of lines that come from a certain text file with the color's.
        LineNumberReader lnr = new LineNumberReader(new FileReader(new File("colors.txt")));
        lnr.skip(Long.MAX_VALUE);
        numLines = lnr.getLineNumber() + 1;
        lnr.close();

        colors = new String[numLines];
        colorValues = new int[numLines][3];

         // reading in the "colors" from colors.txt
        FileInputStream istream = new FileInputStream("colors.txt");
        InputStreaminusRedeader reader = new InputStreaminusRedeader(istream);
        StreamTokenizer tokens = new StreamTokenizer(reader);
        tokens.nextToken();

        for(int i = 0; i < numLines; i++) {
            colors[i] = (String) tokens.sval;
            tokens.nextToken();
            for(int j = 0; j < 3; j++) {
                colorValues[i][j] = (int) tokens.nval; tokens.nextToken();
            }
        }
        // closes the reading in of the file
        istream.close();
        listColors.setListData(colors);
    }
 // class which handles the GUI interface closing event
    private class WindowDestroyer extends WindowAdapter{
        public void windowClosing(WindowEvent e) {
            try{
                FileOutputStream ostream = new FileOutputStream("colors.txt");
                PrintWriter writer = new PrintWriter(ostream);
                String color;
                int colorRed, colorGreen, colorBlue;
                for(int i = 0; i < numLines; i++){
                    color = colors[i];
                    colorRed = colorValues[i][0];
                    colorGreen = colorValues[i][1];
                    colorBlue = colorValues[i][2];

                    writer.println(color + " " + colorRed + " " + colorGreen + " " + colorBlue);

                }

            writer.flush();
            ostream.close();
        }
        catch(IOException some_var){
        }

        System.exit(0);
        }
    }

// the list handler handles the certain index for the colors
    private class ListHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if(event.getSource() == listColors)
                if(!event.getValueIsAdjusting()) {
                    int i = listColors.getSelectedIndex();
                    setTitle("Color Sampler");
                    red = colorValues[i][0];
                    green = colorValues[i][1];
                    blue = colorValues[i][2];
                    redField.setText(Integer.toString(red));
                    greenField.setText(Integer.toString(green));
                    blueField.setText(Integer.toString(blue));
                    colorBoard.newColored= new Color(red, green, blue);
                    colorBoard.repaint();
                }
        }
    }
// handles what happens when you scroll ur mouse over
    private class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int i = listColors.getSelectedIndex();

            // if minus red
            if(event.getSource() == minusRed){
                red-= 5;
                if(red< 0)
                    red= 0;
                redField.setText(Integer.toString(red));
                if(colorValues[i][0] != red){
                    setTitle("Color Sampler*");
                    colorBoard.newColor = new Color(red, green, blue);
                    colorBoard.repaint();
                }
            }

            /// if the greenMinus
            else if(event.getSource() == minusGreen) {
                green -= 5;
                if(green < 0)
                    green = 0;
                greenField.setText(Integer.toString(green));
                if(colorValues[i][0] != green) {
                    setTitle("Color Sampler*");
                    colorBoard.newColor = new Color(red, green, blue);
                    colorBoard.repaint();
                }
            }

            // if its blueMinus
            else if(event.getSource() == minusBlue) {
                blue -= 5;
                if(blue < 0)
                    blue = 0;
                blueField.setText(Integer.toString(b));
                if(colorValues[i][0] != blue) {
                    setTitle("Color Sampler*");
                    colorBoard.newColor = new Color(red, green, blue);
                    colorBoard.repaint();
                }
            }

            // if its RedPositive
            else if(event.getSource() == positiveRed) {
                red+= 5;
                if(red> 255)
                    red= 255;
                redField.setText(Integer.toString(r));
                if(colorValues[i][0] != red) {
                    setTitle("Color Sampler*");
                    colorBoard.newColor = new Color(red, green, blue);
                    colorBoard.repaint();
                }
            }

            // If it is positive green
            else if(event.getSource() == positiveGreen) {
                green += 5;
                if(green > 255)
                    green = 255;
                greenField.setText(Integer.toString(green));
                if(colorValues[i][0] != green) {
                    setTitle("Color Sampler*");
                    colorBoard.newColor = new Color(red, green, blue);
                    colorBoard.repaint();
                }
            }

            // if it is positive blue
            else if(event.getSource() == positiveBlue) {
                blue += 5;
                if(blue > 255)
                    blue = 255;
                blueField.setText(Integer.toString(blue));
                if(colorValues[i][0] != blue) {
                    setTitle("Color Sampler*");
                    colorBoard.newColor = new Color(red, green, blue);
                    colorBoard.repaint();
                }
            }
            // if you'd like to save the value
            else if(event.getSource() == buttonSave){
                setTitle("Color Sampler");
                colorValues[i][0] = red;
                colorValues[i][1] = green;
                colorValues[i][2] = blue;
            }

            // if you'd want to reset the colors.
            else if(event.getSource() == buttonReset) {
                setTitle("Color Sampler");
                red= colorValues[i][0];
                green = colorValues[i][1];
                blue = colorValues[i][2];
                redField.setText(Integer.toString(red));
                greenField.setText(Integer.toString(green));
                blueField.setText(Integer.toString(blue));
                colorBoard.newColor = new Color(red, green, blue);
                colorBoard.repaint();
            }
   // arbitrary case to set the title of the Sampler.
            if(colorValues[i][0] == red && colorValues[i][1] == green && colorValues[i][2] == blue)
                setTitle("HW #8 Color Sampler");
            }
        }
}
