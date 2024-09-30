import java.io.*;

public class test3 {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\book.md";
        Book book = new Book("Thinking in Java", 108.00);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            // 序列化
            oos.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            // 反序列化
            Book otherBook = (Book) ois.readObject();
            System.out.println("name = " + otherBook.name); // 输出: Thinking in Java
            System.out.println("price = " + otherBook.price); // 输出: 108.0
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Book implements Externalizable {
    public transient String name = "";
    public double price = 0.0;

    public Book() {
        System.out.println("invoke Book()");
    }

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeDouble(price);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = (String) in.readObject();
        this.price = in.readDouble();
    }
}
