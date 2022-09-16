package AnagramProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester 
{
    final static int maxWords = 200000;
    static String words[] = new String[maxWords];
    static String reverseWords[] = new String[maxWords];

    public static void main(String args[]) throws IOException
    {
        String fileName = "C:\\Users\\jagik\\OneDrive\\Computer Science\\Computer Science Classes\\CSCI 20\\AnagramSetProject2\\words.txt";
        int wordCount = readinWords(words, maxWords, fileName);

        System.out.println(wordCount);
    }

    public static int readinWords(String wordList[], int maxSize, String fileName) throws IOException
    {
        int i = 0;
        String s = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while((s = br.readLine()) != null && i < maxSize)
        {
            wordList[i] = s;
            i++;
        }

        br.close();
        return i;
    }
}
