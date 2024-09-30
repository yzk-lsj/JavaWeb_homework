import java.io.*;

public class TestSerialization {
    public static void serialize() throws IOException {
        ZslTest zslTest = new ZslTest();
        zslTest.setName("linko");
        zslTest.setAge(18);
        zslTest.setScore(1000);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("ZslTest.txt"))) {
            objectOutputStream.writeObject(zslTest);
            System.out.println("序列化成功！已生成 ZslTest.txt 文件");
        }
    }

    public static void deserialize() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("ZslTest.txt"))) {
            ZslTest zslTest = (ZslTest) objectInputStream.readObject();
            System.out.println("反序列化结果为：");
            System.out.println(zslTest);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        serialize();
        deserialize();
    }
}
