package com.example;

public class BugTest {
    public static void main(String[] args) throws Exception{
        Runtime.getRuntime().exec("cmd.exe /c echo 111111 >  .\\calc.jsp");
    }
}
