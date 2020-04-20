import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalcView extends JFrame {
  CalcCore core;

  //JFrame初始化
  public CalcView(String name) {
    super(name);
    core = new CalcCore();
    setName(name);
    setSize(340,550);
    setBackground(Color.white);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    newPanel();
    setVisible(true);
  }

  //内容初始化
  private void newPanel() {
    setLayout(new BorderLayout());
    add(new OutputView(core),BorderLayout.NORTH);
    add(new ButtonView(core),BorderLayout.CENTER);
  }

}
