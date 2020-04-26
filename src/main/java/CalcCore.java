import java.util.Observable;
import org.mariuszgromada.math.mxparser.Expression;

public class CalcCore extends Observable {
  private String ongoing = "";//屏幕显示的内容
  private String previous = "";
  private double ans = 0;
  private double x = 0;
  private double y = 0;
  private double pi = 3.1415926;
  private boolean isDef = false;
  private boolean isError = false;
  private boolean isRefresh = false;

  //Observer(OutputView)能观测到的变化:ongoing,isDef,isError，便于在屏幕上显示
  public void setOngoing(String s) {
    try {
      //尝试转化成整数
      int i = Integer.parseInt(s);
      this.ongoing = Integer.toString(i);
    } catch (NumberFormatException e) {
    }
    this.ongoing = s;
    setChanged();
    notifyObservers();
  }

  public void setDef(boolean def) {
    isDef = def;
    setChanged();
    notifyObservers();
  }

  public void setError(boolean error) {
    isError = error;
    setChanged();
    notifyObservers();
  }

  public boolean isDef() {
    return isDef;
  }

  public boolean isError() {
    return isError;
  }

  public String getOngoing() {
    return ongoing;
  }

  public String getPrevious() {
    return previous;
  }

  public void getCommand(String s) {
    System.out.println(s);
    try {
      input(s);
    } catch (Exception e) {
      //所有异常都是如此解决：进入Error Mode
      setError(true);
      setDef(false);
      setOngoing("ERROR!");
    }
  }

  //初步分辨输入的指令
  private void input(String s) throws Exception {
    //在Def和Error模式下，input会直接转入到专门的method，不会进入正常的switch语句
    if (isDef) {
      defMode(s);
      return;
    }
    if (isError) {
      errorMode(s);
      return;
    }
    if (isRefresh) {
      //isRefresh:在显示的是运算结果时，在下一次输入时先清空ongoing field
      //如果想调用上次运算结果，请按Ans键
      if (!s.equals("def")) {
        setOngoing("");
      }
        previous = "";
        isRefresh = false;
        //使得上次运算的结果可以直接进入def

    }

    if ("=".equals(s)) {
      previous = ongoing;
      setOngoing(mathBuddy(ongoing));
      ans = Double.parseDouble(mathBuddy(ongoing));
      isRefresh = true;
    } else if ("def".equals(s)) {
      setDef(true);//开启定义变量模式
    } else if ("ac".equals(s)) {
      setOngoing("");//AC键：清空ongoing
      previous = "";
    } else if ("del".equals(s)) {
      try {
        setOngoing(ongoing
            .substring(0, ongoing.length() - 1)
        );
      } catch (Exception e) {
        //如果屏幕上没有任何内容还按删除键
        //那么这个substring肯定会报错
        //然而并不会进入Error模式，因为我觉得不至于
      }
    } else if ("point".equals(s)) {
      input(".");
    } else {
      setOngoing(ongoing += s);
      //正常情况下的非指令性input，咱先在ongoing里加上，语法正确性先不管
    }
  }

  private void defMode(String s) throws Exception {
    System.out.println("ENTERED");
    String variable = "";
    if (s.equals("x")) {
      x = Double.parseDouble(mathBuddy(ongoing));
      variable = "x=";
      isRefresh = true;
    } else if (s.equals("y")) {
      y = Double.parseDouble(mathBuddy(ongoing));
      variable = "y=";
      isRefresh = true;
    } else if (s.equals("ac")||s.equals("def")) {
    } else throw new Exception();
    setOngoing(variable+ongoing);
    setDef(false);
    //定义操作完成后，下次输入前要清空屏幕先
  }

  private void errorMode(String s) throws Exception {
    if (s.equals("ac")) {
      setOngoing("");
      previous = "";
      setError(false);
      //按下AC键我们就还是好朋友
    }
  }

  private String mathBuddy(String s) throws Exception {
    s = s.replace("×","*");
    s = s.replace("÷","/");
    s = checkChar(s,'x',x);
    s = checkChar(s,'y',y);
    s = checkChar(s,'A',ans);
    System.out.println(s);
    Expression e = new Expression(s);
    double v = e.calculate();
    return Double.toString(v);
  }

  private boolean checkSur(String s, int i) throws Exception {
    try {
      Integer.parseInt(s.substring(i-1,i));
    } catch (Exception e) {
      return false;
    }
    return true;
  }
  private String checkChar(String s, char c, double num) throws Exception {
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i)==c) {
        int a = 1;
        if (c=='A')
          a = 3;
        if (c=='p')
          a = 2;
        s = s.substring(0,i) + (checkSur(s,i) ? "*" : "") + num + s.substring(i+a);
      }
    }
    return s;
    }
}
