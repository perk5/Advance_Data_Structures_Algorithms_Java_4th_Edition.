import java.util.*;

public class BobAlicePackets {
    public static class Message {
        int index;
        String message;

        Message(int i, String message) {
            this.index = i;
            this.message = message;
        }
    }

    public static class DisplayMessage {
        public static String correctMessage(List<Message> mess) {

            StringBuilder s = new StringBuilder();
            boolean swapped = true;
            int count = 1;
            while (swapped) {
                swapped = false;
                Iterator<Message> m = mess.iterator();
                while (m.hasNext()) {
                    Message a = m.next();
                    if (a.index == count) {
                        swapped = true;
                        s.append(a.message);
                        m.remove();
                        count++;
                    }

                }

            }
            return s.toString();

        }
    }

    public static void main(String[] args) {
        List<Message> m = new ArrayList<>();
        m.add(new Message(2, "B"));
        m.add(new Message(1, "A"));
        m.add(new Message(3, "C"));
        m.add(new Message(4, "D"));

        DisplayMessage Dm = new DisplayMessage();
        String mess = Dm.correctMessage(m);

        System.out.println(mess);

    }
}
