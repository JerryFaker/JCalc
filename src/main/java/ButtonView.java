import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//实现计算器所有按键的class
//因为窗口大小恒定，所以没有用GridBag
class ButtonView extends JPanel {
  CalcCore core;

  //初始化按钮Panel
  public ButtonView(CalcCore core) {
    super();
    this.core = core;
    setButtons();
    setBackground(Color.white);
    setPreferredSize(new Dimension(300,350));
    for(CalcButton button: buttons) {
      add(button);
    }
  }

  private ArrayList<CalcButton> buttons = new ArrayList<CalcButton>();

  //初始化所有按钮
  private void setButtons() {
    String[] words =
        {"ac","(",")","del","÷","pi",
            "7","8","9","×","y","4","5",
            "6","-","x","1","2","3",
            "+","def","Ans","0","point","="};

    for(String s: words) {
      buttons.add(new CalcButton(s.equals("=") ? 60 : 40,s));//在UI里面等号按键要稍大一些
    }
  }

  //为Button专门extend了一个JButton class,用来给按钮添加一些私货属性
  class CalcButton extends JButton {
    public CalcButton(int width,String s) {
      super();
      //因为界面大小是恒定的，所以按钮的大小也是一个恒定的正方形
      //传入一个width参数是为了更改图片的大小
      setPreferredSize(new Dimension(60,60));
      setActionCommand(s);
      addActionListener(new Listener());
      try {
        Image img = ImageIO
            .read(this.getClass().getClassLoader().getResourceAsStream("images/"+s+".png"))
            .getScaledInstance
                (width,width,java.awt.Image.SCALE_SMOOTH);
        Image img1 = ImageIO
            .read(this.getClass().getClassLoader().getResourceAsStream("images/"+s+"_pressed.png"))
            .getScaledInstance
                (width,width,java.awt.Image.SCALE_SMOOTH);
        //所有按钮都分为按下/空闲两种不同图片存放在子目录下
        setIcon(new ImageIcon(img));
        setPressedIcon(new ImageIcon(img1));
        setContentAreaFilled(false);
        setBorderPainted(false);
      } catch (IOException e) {
        setText(s);
        //假若非常不幸，没有读取出图片，依然可以做出一个简陋的带文字的按钮
        e.printStackTrace();
      }
    }
  }

  class Listener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      core.getCommand(e.getActionCommand());
    }
  }
}


