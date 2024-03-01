package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.List;

public class Test<T, E> implements fanxing<T, E> {


    @Override
    public void method1(T t) {
    }

    @Override
    public void method2(E e) {

    }


    public static void method3(List<Integer> array) {
        System.out.println(array);
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test6.txt"));
//        Book user = new Book(1, "zs");
//        Book user2 = new Book(2, "zs");
//        List<Book> list = new ArrayList<>();
//        list.add(user);
//        list.add(user2);
//        oos.writeObject(list);
//        oos.close();
//
//
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test6.txt"));
//        List<Book> list1 = (List<Book>) ois.readObject();    // 强转
//        for (Book book : list1) {
//            System.out.println(book);
//        }
//        ois.close();

        Class<?> aClass = Class.forName("org.example.Book");
        Method method1 = aClass.getMethod("getName", null);
        System.out.println(aClass);
        System.out.println(method1);

        Constructor<?> constructor = aClass.getConstructor(int.class, String.class);
        Book o = (Book) constructor.newInstance(1, "22");
        String invoke = (String) method1.invoke(o);
        System.out.println(invoke);
        System.out.println(o);

    }

    @org.junit.jupiter.api.Test
    public void ipTest() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress);

        System.out.println(inetAddress.getHostAddress());

        System.out.println(inetAddress.getHostName());
    }

    @org.junit.jupiter.api.Test
    public void UdpTest() throws IOException {

        DatagramSocket socket = new DatagramSocket();

        String msg = "hello world";
        byte[] buf = msg.getBytes();

        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");

        int port = 10086;

        DatagramPacket packet = new DatagramPacket(buf, buf.length, inetAddress, port);

        socket.send(packet);

        socket.close();

    }

    @org.junit.jupiter.api.Test
    public void TcpTest() throws IOException {
        Socket socket = new Socket("127.0.0.1", 10086);

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("Hello 你好".getBytes());

        socket.close();
    }



    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    @org.junit.jupiter.api.Test
    private void reverse(int[] nums, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            int temp = nums[j];
            nums[j] = nums[i];
            nums[i] = temp;
        }
    }

    public void moveZeroes(int[] nums) {
        int[] keys = new int[]{};
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == 0){
                for(int j = i; j<nums.length - 1; j++){
                    nums[j] = nums[j+1];
                }
                nums[nums.length - 1] = 0;
                i--;
            }
            if (keys == nums){
                break;
            }
            keys = nums.clone();
        }
    }

    @org.junit.jupiter.api.Test
    public void test() {
        int[] nums = new int[]{0,0,1};
        moveZeroes(nums);
        for (int num : nums) {
            System.out.println(num);
        }


    }
}
