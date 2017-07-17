import java.util.*;

public class Main {
    public static void main(String args[]){
        student s1 = new student("zan");
        student s2 = new student("li");
        student s3 = Main.foo(s1, s2);
        System.out.println(s3.name);
    }
    public static student foo(student s1, student s2){
        student s3 = s2;
        s2 = s1;
        s1 = s3;
        return s1;
    }
}

class student{
    public String name;
    student(String name){
        this.name = name;
    }
}




