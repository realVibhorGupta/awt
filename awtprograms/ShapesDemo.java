/* 
 * Swing version.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* 
 * This displays a framed area containing one of 
 * each shape you can draw.
 */
public class ShapesDemo extends JApplet {

    //Executed only when this program is run as an applet.
    public void init() {
        ShapesPanel shapesPanel = new ShapesPanel();
        getContentPane().add(shapesPanel, BorderLayout.CENTER);
    }

    //Executed only when this program is run as an applicaiton.
    public static void main(String[] args) {
        JFrame f = new JFrame("ShapesDemo");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        ShapesPanel shapesPanel = new ShapesPanel();
        f.getContentPane().add(shapesPanel, BorderLayout.CENTER);
        f.setSize(new Dimension(550, 200));
        f.setVisible(true);
    }
}

class ShapesPanel extends JPanel {
    final int maxCharHeight = 15;
    final Color bg = Color.lightGray;
    final Color fg = Color.black;
    
    public ShapesPanel() {
        //Initialize drawing colors, border, opacity.
        setBackground(bg);
        setForeground(fg);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createLoweredBevelBorder()));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);      //clears the background

        Insets insets = getInsets();
        int currentWidth = getWidth() - insets.left - insets.right;
        int currentHeight = getHeight() - insets.top - insets.bottom;
        int gridWidth = currentWidth / 7;
        int gridHeight = currentHeight / 2;

        Color fg3D = Color.lightGray;
        int firstX = insets.left + 3;
        int firstY = insets.top + 3;
        int x = firstX;
        int y = firstY;
        int stringY = gridHeight - 7;
        int rectWidth = gridWidth - 2*x;
        int rectHeight = stringY - maxCharHeight - y;

        // drawLine(x1, y1, x2, y2) 
        g.drawLine(x, y+rectHeight-1, x + rectWidth, y);
        g.drawString("drawLine", x, stringY);
        x += gridWidth;

        // drawRect(x, y, w, h) 
        g.drawRect(x, y, rectWidth, rectHeight);
        g.drawString("drawRect", x, stringY);
        x += gridWidth;

        // draw3DRect(x, y, w, h, raised) 
        g.setColor(fg3D);
        g.draw3DRect(x, y, rectWidth, rectHeight, true);
        g.setColor(fg);
        g.drawString("draw3DRect", x, stringY);
        x += gridWidth;

        // drawRoundRect(x, y, w, h, arcw, arch) 
        g.drawRoundRect(x, y, rectWidth, rectHeight, 10, 10);
        g.drawString("drawRoundRect", x, stringY);
        x += gridWidth;

        // drawOval(x, y, w, h) 
        g.drawOval(x, y, rectWidth, rectHeight);
        g.drawString("drawOval", x, stringY);
        x += gridWidth;

        // drawArc(x, y, w, h) 
        g.drawArc(x, y, rectWidth, rectHeight, 90, 135);
        g.drawString("drawArc", x, stringY);
        x += gridWidth;

        // drawPolygon(xPoints, yPoints, numPoints) 
        int x1Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y1Points[] = {y, y+rectHeight, y+rectHeight, y};
        g.drawPolygon(x1Points, y1Points, x1Points.length); 
        g.drawString("drawPolygon", x, stringY);

        // NEW ROW
        x = firstX;
        y += gridHeight;
        stringY += gridHeight;

        // drawPolyline(xPoints, yPoints, numPoints) 
        // Note: drawPolygon would close the polygon.
        int x2Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y2Points[] = {y, y+rectHeight, y+rectHeight, y};
        g.drawPolyline(x2Points, y2Points, x2Points.length); 
        g.drawString("drawPolyline", x, stringY);
        x += gridWidth;

        // fillRect(x, y, w, h)
        g.fillRect(x, y, rectWidth, rectHeight);
        g.drawString("fillRect", x, stringY);
        x += gridWidth;

        // fill3DRect(x, y, w, h, raised) 
        g.setColor(fg3D);
        g.fill3DRect(x, y, rectWidth, rectHeight, true);
        g.setColor(fg);
        g.drawString("fill3DRect", x, stringY);
        x += gridWidth;

        // fillRoundRect(x, y, w, h, arcw, arch)
        g.fillRoundRect(x, y, rectWidth, rectHeight, 10, 10);
        g.drawString("fillRoundRect", x, stringY);
        x += gridWidth;

        // fillOval(x, y, w, h)
        g.fillOval(x, y, rectWidth, rectHeight);
        g.drawString("fillOval", x, stringY);
        x += gridWidth;

        // fillArc(x, y, w, h)
        g.fillArc(x, y, rectWidth, rectHeight, 90, 135);
        g.drawString("fillArc", x, stringY);
        x += gridWidth;

        // fillPolygon(xPoints, yPoints, numPoints) 
        int x3Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y3Points[] = {y, y+rectHeight, y+rectHeight, y};
        g.fillPolygon(x3Points, y3Points, x3Points.length); 
        g.drawString("fillPolygon", x, stringY);
    }
}
