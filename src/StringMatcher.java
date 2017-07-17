import java.util.*;
import java.lang.*;
/**
 * Created by LiuHuiwen on 2017/6/30.
 */
public class StringMatcher {
    public static void main(String args[]){
        assert false;
        String text = "314159265358979323846264338327950288";
        String pattern = "3";
//        int ans = Integer.parseInt(pattern);
//        System.out.println(ans);
//        System.out.println(pattern.substring(0,2));
//        List<Integer> k = new ArrayList<>(Arrays.asList(1,2,3,4));
//        System.out.println(k.get(0));
//        RabinKarp rbmatcher = new RabinKarp();
//        int testNum = 673535;
//        List<Integer> primeTable = rbmatcher.findMaxPrime(testNum);
//        System.out.println(primeTable);
//        List<Integer> matchshift = rbmatcher.matcher(text, pattern);
//        System.out.println(matchshift);
//        System.out.println("is"=="is");
        Automaton auto = new Automaton();
//        auto.initTable(pattern);
//        auto.showTable();
        List<Integer> matchshift = auto.kmp(text, pattern);
        System.out.println(matchshift);
    }
}
class RabinKarp{
    private int format = 10;
    public List<Integer> findPrime(int num){
        List<Integer> table = new ArrayList<Integer>();
        if(num < 2) return table;
        table.add(2);
        for(int i = 3; i <= num; i += 2){
            boolean isPrime = true;
            int j = 0, len = (int)Math.sqrt(i);
            while(j < table.size()&&table.get(j) <= len){
                if(i % table.get(j) == 0){
                    isPrime = false;
                    break;
                }
                j++;
            }
            if(isPrime) table.add(i);
        }
        return table;
    }
    public int findProperPrime(int num){
        List<Integer> lst = this.findPrime(num);
        return lst.get(lst.size()/2);
    }
    public int formatMod(int patternLen, int modNum){
        int ans = 1;
        try{
            for(int i = 0; i < patternLen - 1; i++){
                ans = ans*format;
            }
        }
        catch(Exception e){
            System.out.println("error in formatMod.");
        }
        return ans %  modNum;
    }
    public List<Integer> matcher(String text, String pattern){
        if(pattern.equals("")||text.length() < pattern.length()) return null;
        List<Integer> ans = new ArrayList<>();
        int intPattern = 0, headOfText = 0, patternLen = pattern.length();
        try{
            intPattern = Integer.parseInt(pattern);
            headOfText = Integer.parseInt(text.substring(0,patternLen));
        }
        catch(NumberFormatException e){
            System.out.println("number is too large.");
        }
        int modNum = this.findProperPrime(intPattern);
        int fingerprint = intPattern % modNum;
        int point = headOfText % modNum, formatModNum = this.formatMod(patternLen, modNum);
        if(point == fingerprint) ans.add(0);
        for(int i = 1; i <= text.length() - patternLen; i++){
            int tmp = (point - formatModNum*(text.charAt(i - 1) - 48)) % modNum;
            tmp = (tmp > 0)? tmp:tmp + modNum;
            point = (format*tmp + (text.charAt(i + patternLen - 1) - 48)) % modNum;
            if(point == fingerprint) ans.add(i);
        }
        for(int i = 0; i < ans.size(); i++){
            if(!text.substring(ans.get(i),ans.get(i) + patternLen).equals(pattern)) ans.remove(i);
        }
        return ans;
    }
}

class Automaton{
    private int format = 10;
    private int[][] table;
    private int[] prefix;
    private boolean isPrefixInit = false;
    private boolean isTableInit = false;
    private void initPrefix(String pattern){
        if(this.isPrefixInit) return;
         int len = pattern.length();
         this.prefix = new int[len + 1];
         this.prefix[0] = 0;
        this.prefix[1] = 0;
         int k = 0;
         for(int i = 2; i <= len; i++){
             while(k > 0 && pattern.charAt(i - 1) != pattern.charAt(k)){
                 k = this.prefix[k];
             }
             if(pattern.charAt(i - 1) == pattern.charAt(k)) k = k + 1;
             this.prefix[i] = k;
         }
        this.isPrefixInit = true;
    }
    public void initTable(String pattern){
        this.initPrefix(pattern);
        if(this.isTableInit) return;
        int len = pattern.length();
        this.table = new int[format][len + 1];
        for(int i = 0; i < format; i++){
            for(int j = 0; j <= len; j++){
                int k = (j == len)?this.prefix[j]:j;
                while(k > 0 && (char)(48 + i) != pattern.charAt(k)){
                    k = this.prefix[k];
                }
                if((char)(48 + i) == pattern.charAt(k)) table[i][j] = k + 1;
                else table[i][j] = 0;
            }
        }
        this.isTableInit = true;
    }
    public void showTable(){
        if(table == null){
            System.out.println("table is empty.");
        }
        else{
            for(int i = 0; i < table.length; i++){
                for(int j = 0; j < table[i].length; j++){
                    System.out.print(table[i][j]);
                }
                System.out.println();
            }
        }
    }
    public List<Integer> matcher(String text, String pattern){
        if(pattern.equals("")||text.length() < pattern.length()) return null;
        List<Integer> ans = new ArrayList<>();
        this.isTableInit = false;
        this.isPrefixInit = false;
        this.initTable(pattern);
        int state = 0;
        for(int i = 0; i < text.length(); i++){
            int j = (text.charAt(i) - 48);
            state = table[j][state];
            if(state == pattern.length()) ans.add(i - pattern.length() + 1);
        }
        return ans;
    }
    public List<Integer> kmp(String text, String pattern){
        this.isPrefixInit = false;
        this.initPrefix(pattern);
        int state = 0;
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < text.length(); i++){
            while(state > 0 && text.charAt(i) != pattern.charAt(state)){
                state = this.prefix[state];
            }
            if(text.charAt(i) == pattern.charAt(state)) ++state;
            else state = 0;
            if(state == pattern.length()){
                ans.add(i - pattern.length() + 1);
                state = this.prefix[state];
            }
        }
        return ans;
    }
}


