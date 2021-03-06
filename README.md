# JCalc

JCalc是基于Java Swing的GUI计算器软件。作为实验作业的一部分，其所有功能都为了满足：

1. 具备基本的加减乘除平方功能；支持由括号和运算符组成的表达式运算；

2. 使用GUI界面元素设计用户友好的界面；

3. 能够存储多个计算结果，并提取出来作为下一个计算的操作数；

4. 能够处理计算过程中的异常情况；

5. 使用Eclipse、NetBeans或Intellj作为开发环境，使用Maven管理项目，使用Git进行源代码控制；

   <img src="https://github.com/JerryFaker/JCalc/blob/master/README.assets/Screenshot.png" alt="image-20200421010527897" style="zoom:20%;" />

## 功能

- 数学运算 —— 通过按钮操作输入简单数学表达式得出运算结果（不支持键盘输入），支持浮点数。

- 错误处理 —— 当计算器出现运算错误时，会显示报错界面，界面左上角亮起报错指示灯。这时只要按AC键即可重置。

- 计算结果储存 —— JCalc内置有3个参数：x, y, ans。ans会自动保存为上次计算的结果。x和y默认为0。

  JCalc支持含有参数的运算，当参数前缀数字时，会自动补齐乘号。在输入表达式，或者得出计算结果后，按DEF键即可进入赋值模式，界面左上角亮起赋值模式指示灯，这时按x或y即可对参数赋值。如果想要退出赋值模式，可以按AC或者再次按DEF键。
