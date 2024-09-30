import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class test1 {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\target.md";
        Target target = new Target("hi");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            // 序列化
            oos.writeObject(target);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            // 反序列化
            Target otherTarget = (Target) ois.readObject();

            // 修改静态变量
            Target.staticVar += " world!";
            System.out.println("instVar = " + otherTarget.instVar); // 输出: hi
            System.out.println("staticVar = " + otherTarget.staticVar); // 输出: hello world!
            System.out.println("intValue = " + otherTarget.intValue); // 输出: 0
            System.out.println("doubleValue = " + otherTarget.doubleValue); // 输出: 0.0
            System.out.println("booValue = " + otherTarget.booValue); // 输出: false
            System.out.println("stringValue = " + otherTarget.stringValue); // 输出: null
            System.out.println("objValue = " + otherTarget.objValue); // 输出: null
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Target implements Serializable {
    public static String staticVar = "hello";
    public String instVar = "";
    public transient double doubleValue = 10.0;
    public transient int intValue = 100;
    public transient boolean booValue = true;
    public transient String stringValue = "hello world";
    public transient Object objValue = new Object();

    public Target() {
        System.out.println("invoke Target()");
    }

    public Target(String instVar) {
        this.instVar = instVar;
        System.out.println("invoke Target(String instVar)");
    }
}
