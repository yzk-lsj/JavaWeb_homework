import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class test2 {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\target.md";
        User user = new User("qcer", "123456");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            // 序列化
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            // 反序列化
            User otherUser = (User) ois.readObject();
            System.out.println("username = " + otherUser.username);
            System.out.println("password = " + otherUser.password);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class User implements Serializable {
    public String username = "";
    public String password = "";

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(username);
        oos.writeObject(encrypt(password));
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.username = (String) ois.readObject();
        this.password = decrypt((String) ois.readObject());
    }

    private String encrypt(String plaintext) {
        // 实现加密逻辑
        return plaintext; // 示例：实际应用中需替换为加密算法
    }

    private String decrypt(String ciphertext) {
        // 实现解密逻辑
        return ciphertext; // 示例：实际应用中需替换为解密算法
    }
}
