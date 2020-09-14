package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

import javax.swing.*;

public class PixelArtMaker implements MouseListener, ActionListener
{
    private JFrame window;
    private GridInputPanel gip;
    private GridPanel gp;
    private JButton saveButton;
    private JButton loadButton;
    private FileWriter fw;


    ColorSelectionPanel csp;

    public void start() throws IOException
    {
        gip = new GridInputPanel(this);
        window = new JFrame("Pixel Art");
        window.setLayout(new FlowLayout());
        window.setResizable(false);
        saveButton = new JButton();
        saveButton.setText("Save");
        loadButton = new JButton();
        loadButton.setText("Load Image");
        window.add(gip);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    public void submitGridData(int w, int h, int r, int c)
    {
        gp = new GridPanel(w, h, r, c);
        csp = new ColorSelectionPanel();
        window.remove(gip);
        window.add(gp);
        window.add(csp);
        gp.repaint();
        gp.addMouseListener(this);
        window.add(saveButton);
        window.add(loadButton);
        window.pack();
    }

    public static void main(String[] args) throws IOException
    {
        new PixelArtMaker().start();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    private static void save(GridPanel data)
    {
        try (FileOutputStream fos = new FileOutputStream(new File("/Users/davidfrieder/Desktop/level4-module0-DavidAndBlake/src/_02_Pixel_Art/Pixel_Image_Data.dat")); ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(data);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static GridPanel load()
    {
        try (FileInputStream fis = new FileInputStream(new File("/Users/davidfrieder/Desktop/level4-module0-DavidAndBlake/src/_02_Pixel_Art/Pixel_Image_Data.dat")); ObjectInputStream ois = new ObjectInputStream(fis))
        {
            return (GridPanel) ois.readObject();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e)
        {
            // This can occur if the object we read from the file is not
            // an instance of any recognized class
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void mousePressed(MouseEvent e)
    {
        gp.setColor(csp.getSelectedColor());
        System.out.println(csp.getSelectedColor());
        gp.clickPixel(e.getX(), e.getY());
        gp.repaint();
        System.out.println(e.getX() + "\n" + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==(saveButton))
        {
            save(gp);
            System.out.println("jgfu");
        }
        if (e.getSource()==(loadButton)){
            window.remove(gp);
            gp = load();
            gp.addMouseListener(this);
            window.add(gp);
            window.pack();
        }
    }
}
