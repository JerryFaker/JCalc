import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {

  public static void main(String[] args) {
    new Calculator();
  }

  public Calculator() {
    new CalcView("JCalc");
  }
}
