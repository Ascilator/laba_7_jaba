import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WinMain {
    private JFrame frame;
    private BufferedImage image;
    private int appWidth;
    private int appHeight;
    private int xShift;
    private int yShift;
    private JRadioButton rbRed;
    private JRadioButton rbBlue;
    private JRadioButton rbGreen;
    private Color color;
    private JScrollPane jsp;
    private JPanel myPanel;
    private JButton buttonSave;
    private JButton buttonOpen;
    private JButton buttonNew;
//    private ArrayList<ColoredCoordinate> memoryImage;

    public WinMain() {
        frame = new JFrame("Paint 2000 II xl gold edition remastered");
//        memoryImage = new ArrayList<>();
        color = Color.BLUE;
        xShift = 0;
        yShift = 0;
        appWidth = 800;
        appHeight = 600;
        myPanel = new MyPanel();
        myPanel.setPreferredSize(new Dimension(appWidth + 1000, appHeight + 1000));
        //myPanel.setBackground(color);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image = new BufferedImage(appWidth + 1000, appHeight + 1000, BufferedImage.TYPE_INT_ARGB);
        jsp = new JScrollPane(myPanel); //,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setPreferredSize(new Dimension(appWidth, appHeight));
        myPanel.revalidate();

        rbBlue = new JRadioButton("blue");
        rbBlue.setSelected(true);
        rbRed = new JRadioButton("red");
        rbRed.setSelected(false);
        rbGreen = new JRadioButton("green");
        rbGreen.setSelected(false);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbBlue);
        buttonGroup.add(rbGreen);
        buttonGroup.add(rbRed);
        rbBlue.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.BLUE;
            }
        });
        rbRed.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.RED;
            }
        });
        rbGreen.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.GREEN;
            }
        });
        JPanel rbPane = new JPanel();
        rbPane.add(rbRed);
        rbPane.add(rbBlue);
        rbPane.add(rbGreen);
        rbPane.setBackground(Color.DARK_GRAY);


        myPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Graphics g = image.getGraphics();
                g.setColor(color);
                g.fillRect(e.getX() + xShift, e.getY() + yShift, 5, 5);
                myPanel.repaint();
//                memoryImage.add(new ColoredCoordinate(color, e.getX(), e.getY()));
            }
        });

        myPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Graphics g = image.getGraphics();
                g.setColor(color);
                g.fillRect(e.getX() + xShift, e.getY() + yShift, 5, 5);
                myPanel.repaint();
            }
        });

        buttonSave = new JButton("Save");
        buttonOpen = new JButton("Open");
        buttonNew = new JButton("New");
        buttonSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("img.png");
                    JFileChooser jfc = new JFileChooser();
                    int returnVal = jfc.showSaveDialog(buttonSave);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        file = jfc.getSelectedFile();
                    }
                    ImageIO.write(image, "png", file);
                    JOptionPane.showMessageDialog(null, "Image successfully save to '" + file.getName() + "'");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Something went wrong, please do try again.");
                }
            }
        });
        buttonOpen.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file;
                    JFileChooser jfc = new JFileChooser();
                    int returnVal = jfc.showOpenDialog(buttonSave);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        file = jfc.getSelectedFile();
                        image = ImageIO.read(file);
                        myPanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                        myPanel.revalidate();
                        JOptionPane.showMessageDialog(null, "Opened successfully");
                        myPanel.repaint();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Something went wrong, please do try again.");
                }
            }
        });
        buttonNew.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Create new image?");
                if (answer == 0) {
                    image = new BufferedImage(appWidth + 1000, appHeight + 1000, BufferedImage.TYPE_INT_ARGB);
                    myPanel.setPreferredSize(new Dimension(appWidth + 1000, appHeight + 1000));
                    var g = image.getGraphics();
                    g.fillRect(0, 0, image.getWidth(), image.getHeight());
                    myPanel.repaint();
                    myPanel.revalidate();
                }
            }
        });
        rbPane.add(buttonNew);
        rbPane.add(buttonSave);
        rbPane.add(buttonOpen);

/*        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                appWidth = frame.getWidth();
                appHeight = frame.getHeight();
                if (appWidth > image.getWidth() && appHeight > image.getHeight()) {
                    image = new BufferedImage(appWidth, appHeight, BufferedImage.TYPE_INT_ARGB);
                } else if (appWidth > image.getWidth()) {
                    image = new BufferedImage(appWidth, image.getHeight(), BufferedImage.TYPE_INT_ARGB);
                } else if (appHeight > image.getHeight()) {
                    image = new BufferedImage(image.getWidth(), appHeight, BufferedImage.TYPE_INT_ARGB);
                }
                var g = image.getGraphics();
                for (ColoredCoordinate coord : memoryImage) {
                    g.setColor(coord.color);
                    g.fillRect(coord.x + xShift, coord.y + yShift, 5, 5);
                }
                myPanel.repaint();
                myPanel.revalidate();

            }
        });*/

        frame.setResizable(true);
        frame.add(rbPane, BorderLayout.NORTH);
//        frame.add(myPanel, BorderLayout.CENTER);
        frame.add(jsp, BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension(appWidth, appHeight));
        frame.setVisible(true);

    }

    private class MyPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }


/*
    private class ColoredCoordinate {
        private Color color;
        private int x;
        private int y;

        public ColoredCoordinate(Color color, int x, int y) {
            this.color = color;
            this.x = x;
            this.y = y;
        }
    }*/
}
