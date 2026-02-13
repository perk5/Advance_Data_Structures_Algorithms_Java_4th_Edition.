class Ceasar {

    int shift;

    protected char[] language;
    // protected char[] decrypt;

    public Ceasar(char[] alphabets, int shift) {

        this.shift = shift;
        this.language = alphabets;
        // encrypt  = new char[shift];
        // decrypt  = new char[shift];

        // for (int i = 0; i < alphabets.length; i++) {
        //     encrypt[i] = alphabets[(i + shift) % alphabets.length] ;
        // }
        // for (int i = 0; i < alphabets.length; i++) {
        //     decrypt[encrypt[i] - 'A'] = GREEK[i];
        // }
    }

    public int findIndex(char c){
        for(int i = 0; i < language.length; i++){
            if(language[i] == c){
                return i;
            }
        }
        return -1;
    }

    public String encrypt(String secret) {
        char[] mess = secret.toCharArray();
         
        for (int i = 0; i < mess.length; i++) {
            int index = findIndex(mess[i]);
            if(index != -1){
                mess[i] = language[(index + shift) % language.length];
            }
        }
        return new String(mess);
    }

    public String decrypt(String secret) {
        char[] mess = secret.toCharArray();
         
        for (int i = 0; i < mess.length; i++) {
            int index = findIndex(mess[i]);
            if(index != -1){
                mess[i] = language[(index - shift + language.length) % language.length];
            }
        }
        return new String(mess);
    }

}

public class CeasarCipher {

    public char[] English = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public int shift = 3;

    public void main(String args[]) {

        Ceasar cipher = new Ceasar(English, shift);
        System.out.println(cipher.encrypt("PRERAK"));
        System.out.println(cipher.decrypt("SUHUDN"));
    }
}
