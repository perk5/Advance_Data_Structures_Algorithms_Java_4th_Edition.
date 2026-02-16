class GameEntry {
    protected String name;
    protected int score;

    GameEntry(String n, int s) {
        this.name = n;
        this.score = s;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String traverse() {
        return "(" + name + ", " + score + ")";
    }
}

class Score {
    public static final int maxEntries = 10;
    protected int n;
    protected GameEntry[] entries;

    public Score() {
        entries = new GameEntry[maxEntries];
        n = 0;
    }

    public String traverse() {
        String s = "[";
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                s += " , ";
            }
            s += entries[i].score;

        }
        return s + "]";
    }

    public void add(GameEntry e) {
        int newScore = e.getScore();

        if (n == maxEntries) {
            if (newScore <= entries[n - 1].getScore()) {
                return;
            }
        } else {
            n++;
        }
        int i = n - 1;
        for (; (i >= 1) && (newScore > entries[i - 1].getScore()); i--) {
            entries[i] = entries[i - 1];
        }
        entries[i] = e;

    }

    public void addElement(GameEntry e) {
        // int newScore = e.getScore();
        if (n == entries.length) {
            return;
        } else {
            entries[n] = e;
            n++;
        }
    }

    public void removeElement(int i) {
        if (i < 0 || i >= n) {
            return;
        }

        entries[i] = entries[n - 1];
        entries[n - 1] = null;
        n--;
    }
}

class Game {
    public static void main(String args[]) {
        GameEntry PS = new GameEntry("Prerak", 200);
        GameEntry RJ = new GameEntry("Raj", 300);
        GameEntry MS = new GameEntry("Mausam", 500);
        GameEntry DS = new GameEntry("Dishant", 180);

        // System.out.println(GE.traverse());

        Score Sc = new Score();
        Sc.add(PS);
        Sc.add(RJ);
        Sc.add(MS);
        Sc.addElement(DS);
        Sc.removeElement(2);
        System.out.println(Sc.traverse());
        System.out.println(Sc.n);
    }
}