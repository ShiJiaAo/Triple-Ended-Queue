/**
 * Name: Shi Jiaao
 * Student ID: XXXXXXXXX
 */
import java.io.*;

public class tripleEndedQueue {
    /* Deque class is made with reference to the programs in the lecture notes */
    public static class Deque {
        public int[] arr;
        public int capacity;
        public int front;
        public int back;
        public int size;
        public final int INITSIZE = 1;

        public Deque() {
            arr = new int[INITSIZE];
            capacity = INITSIZE;
            size = 0;
            front = 0;
            back = 0;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getSize() {
            return size;
        }

        public boolean isEmpty() {
            return front == back;
        }

        public boolean isFull() {
            return (back + 1) % capacity == front;
        }

        public Integer get(int i) {
            if (isEmpty()) {
                return null;
            }
            return arr[(front + i) % capacity];
        }

        public void pushBack(int x) {
            if (isFull()) {
                enlargeArr();
            }
            arr[back] = x;
            back = (back + 1) % capacity;
            size++;
        }

        public void pushFront(int x) {
            if (isFull()) {
                enlargeArr();
            }
            front -= 1;
            if (front == -1) {
                front = capacity - 1;
            }
            arr[front] = x;
            size++;
        }

        public Integer pollFront() {
            if (isEmpty()) {
                return null;
            }
            Integer item = arr[front];
            front = (front + 1) % capacity;
            size--;
            return item;
        }

        public Integer pollBack() {
            if (isEmpty()) {
                return null;
            }
            back = back - 1;
            if (back == -1) {
                back = capacity - 1;
            }
            Integer item = arr[back];
            size--;
            return item;
        }

        public void enlargeArr() {
            int newSize = capacity * 2;
            int[] temp = new int[newSize];
            if (temp == null) {
                System.out.println("not enough memory!");
                System.exit(1);
            }
            for (int i = 0; i < capacity; i++) {
                temp[i] = arr[(front + i) % capacity];
            }
            front = 0;
            back = capacity - 1;
            arr = temp;
            capacity = newSize;
        }
    }

    public static class Teque {
        public Deque firstHalf;
        public Deque secondHalf;
        public int capacity;
        public int front;
        public int back;
        public int numItems;
        public final int INITSIZE = 2;

        public Teque() {
            firstHalf = new Deque();
            secondHalf = new Deque();
            capacity = INITSIZE;
            front = firstHalf.front;
            back = secondHalf.back;
            numItems = 0;
        }

        public void pushFront(int x) {
            firstHalf.pushFront(x);
            front = firstHalf.front;
            numItems++;
            rebalance();
        }

        public void pushBack(int x) {
            secondHalf.pushBack(x);
            back = secondHalf.back;
            numItems++;
            rebalance();
        }

        public void pushMiddle(int x) {
            secondHalf.pushFront(x);
            numItems++;
            rebalance();
        }

        public void rebalance() {
            /*
             * making sure that size of front deque is always equal to or 1 more than
             * size of back deque
             */
            while (firstHalf.getSize() < secondHalf.getSize()) {
                Integer insert = secondHalf.pollFront();
                firstHalf.pushBack(insert);
            }
            while (firstHalf.getSize() > secondHalf.getSize() + 1) {
                Integer insert = firstHalf.pollBack();
                secondHalf.pushFront(insert);
            }
        }

        public Integer get(int i) {
            if (numItems == 0) {
                return null;
            }
            if (i < firstHalf.getSize()) {
                // item i is in 1st half
                return firstHalf.get(i);
            } else {
                // item i is in 2nd half
                i -= firstHalf.getSize();
                return secondHalf.get(i);
            }
        }
    }

    public static void readCommand(BufferedReader br, PrintWriter pw, Teque Teque) throws IOException {
        String helper = br.readLine();
        String command[] = helper.split(" ");
        int cmdValue = Integer.parseInt(command[1]);
        // cmdValue means command value; the value given in the command operation

        if (command[0].charAt(0) == 'g') {
            int printValue = Teque.get(cmdValue);
            pw.println(printValue);
        } else if (command[0].charAt(5) == 'b') {
            Teque.pushBack(cmdValue);
        } else if (command[0].charAt(5) == 'f') {
            Teque.pushFront(cmdValue);
        } else if (command[0].charAt(5) == 'm') {
            Teque.pushMiddle(cmdValue);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        Teque teque = new Teque();

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            readCommand(br, pw, teque);
        }

        br.close();
        pw.close();
    }
}