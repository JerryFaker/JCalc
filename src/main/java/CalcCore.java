import java.util.Observable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class CalcCore extends Observable {
  private String ongoing = "";//屏幕显示的内容
  private double ans = 0;
  private double x = 0;
  private double y = 0;
  private double pi = 3.1415926;
  private boolean isDef = false;
  private boolean isError = false;
  private boolean isRefresh = false;
  private ScriptEngineManager mgr = new ScriptEngineManager();
  ScriptEngine engine = mgr.getEngineByName("JavaScript");

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
        System.out.println("tiaoguorefresh");
      }
        isRefresh = false;
        //使得上次运算的结果可以直接进入def

    }

    switch (s) {
      case "=": {
        setOngoing(mathBuddy(ongoing));
        ans = Double.parseDouble(mathBuddy(ongoing));
        isRefresh = true;
        break;
      }
      case "def": {
        setDef(true);//开启定义变量模式
        break;
      }
      case "ac": {
        setOngoing("");//AC键：清空ongoing
        break;
      }
      case "del": {
        try {
          setOngoing(ongoing
              .substring(0,ongoing.length()-1)
          );
        } catch (Exception e) {
          //如果屏幕上没有任何内容还按删除键
          //那么这个substring肯定会报错
          //然而并不会进入Error模式，因为我觉得不至于
        }
        break;
      }
      case "point": {
        input(".");
        break;
      }
      default: {
        setOngoing(ongoing+=s);
        //正常情况下的非指令性input，咱先在ongoing里加上，语法正确性先不管
        break;
      }
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
      setError(false);
      //按下AC键我们就还是好朋友
    }
  }

  private String mathBuddy(String s) throws Exception {
    s = s.replace("×","*");
    s = s.replace("÷","/");
    s = s.replace("x",Double.toString(x));
    s = s.replace("y",Double.toString(y));
    s = s.replace("pi",Double.toString(pi));
    s = s.replace("Ans",Double.toString(ans));
    System.out.println(engine.eval(s).toString());
    return engine.eval(s).toString();
  }
}
