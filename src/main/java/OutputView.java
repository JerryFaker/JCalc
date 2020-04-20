import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//这个JPanel class对应的是传统计算器中液晶屏的那一部分
class OutputView extends JPanel implements Observer {
  private CalcCore core;
  //屏幕由三部分组成：上面一部分显示状态，中间空白填充，下面显示运算
  private JPanel top = new JPanel();
  private JPanel middle = new JPanel();
  private JPanel bottom = new JPanel();
  private Font font;//屏幕的专有字体，一切为逼格服务
  private JLabel ongoing;
  private JLabel status1;
  private JLabel status2;

  public OutputView(CalcCore core) {
    //设置Panel
    super();
    this.core = core;
    this.core.addObserver(this);
    setPreferredSize(new Dimension(340,190));
    setBackground(new Color(238,238,238));
    setChildPanel();
    setFont();
    //设置ongoing label的一些参数
    ongoing = new JLabel();
    ongoing.setText("0");
    ongoing.setPreferredSize(new Dimension(300,50));
    ongoing.setHorizontalAlignment(JLabel.RIGHT);
    ongoing.setVerticalAlignment(JLabel.NORTH);
    ongoing.setFont(font.deriveFont(45f));
    bottom.add(ongoing);
    status1 = new JLabel();
    status2 = new JLabel();
    try {
      Image def = ImageIO
          .read(this.getClass().getClassLoader().getResourceAsStream("images/defmode.png"))
          .getScaledInstance(40,23, Image.SCALE_SMOOTH);
      Image error = ImageIO
          .read(this.getClass().getClassLoader().getResourceAsStream("images/warning.png"))
          .getScaledInstance(26,23, Image.SCALE_SMOOTH);
      status1.setIcon(new ImageIcon(error));
      status2.setIcon(new ImageIcon(def));
    } catch (IOException e) {
      e.printStackTrace();
    }
    top.add(status1);
    top.add(status2);
    status1.setVisible(false);
    status2.setVisible(false);
    top.setLayout(new FlowLayout(FlowLayout.LEFT,20,20));
    //add(label);
  }

  private void setFont() {
    try {
      InputStream in  = this.getClass().getClassLoader().getResourceAsStream("Futura-Medium.ttf");
      //InputStream in = new FileInputStream("/src/main/java/Futura-Medium.ttf");
      System.out.println(in);
      font = Font
          .createFont
              (Font.TRUETYPE_FONT,
                  in);
    } catch (FontFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  private void setChildPanel() {
    top.setPreferredSize(new Dimension(300,70));
    middle.setPreferredSize(new Dimension(300,30));
    bottom.setPreferredSize(new Dimension(300,90));
    setLayout(new BorderLayout());
    add(top,BorderLayout.NORTH);
    add(middle,BorderLayout.CENTER);
    add(bottom,BorderLayout.SOUTH);
    bottom.setLayout(new FlowLayout(FlowLayout.RIGHT,25,10));
  }

  public void update(Observable o, Object arg) {
    core = (CalcCore)o;
    ongoing.setText(core.getOngoing());
    status1.setVisible(core.isError());
    status2.setVisible(core.isDef());
  }
}