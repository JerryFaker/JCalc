import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;

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
