package AnagramProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary 
{
    public static final int tableSize = 5001;
    private AnagramSet hashTable[] = new AnagramSet[tableSize];
    private int numWords;

    private final AnagramSet find(AnagramSet head, final LetterBag key)
    {
        while(head != null)
        {
            if(head.key == key)
            {
                return head;
            }
            head = head.next;
        }
        return null;
    }

    private final AnagramSet findWord(final String w)
    {
        LetterBag key = new LetterBag(w);
        int index = key.hashValue(tableSize);
        return find(hashTable[index], key);
    }

    private void insertNew(int index, AnagramSet toInsert)
    {
        // toInsert is added to the front of the list
        toInsert.next = hashTable[index];
        hashTable[index] = toInsert;
    }

    public Dictionary()
    {
        this.numWords = 0;
    }
    public Dictionary(String fileName) throws IOException
    {
        this.numWords = 0;
        String word;
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while((word = br.readLine()) != null)
        {
            this.insertWord(word);
        }

        br.close();
    }
    public Dictionary(String words[], int arraySize)
    {
        numWords = 0;
        for(int n = 0; n < arraySize; n++)
        {
            this.insertWord(words[n]);
        }
    }
    public Dictionary(final Dictionary d)
    {
        for(int outer = 0; outer < tableSize; outer++)
        {
            AnagramSet otherCurr = d.hashTable[outer];

            while(otherCurr != null)
            {
                AnagramSet thisNew = new AnagramSet(otherCurr.key);
                ArrayList<String> words = otherCurr.getWords();

                for(int inner = 0; inner < words.size(); inner++)
                {
                    thisNew.insert(words.get(inner));
                }

                insertNew(outer, thisNew);
                otherCurr = otherCurr.next;
            }
        }
        this.numWords = d.getNumWords();
    }
    
    public final int getNumWords()
    {
        return this.numWords;
    }

    public void clear ()
    {
        for(int n = 0; n < tableSize; n++)
        {
            while(this.hashTable[n] != null)
            {
                AnagramSet temp = hashTable[n].next;
                this.hashTable[n] = null;
                this.hashTable[n] = temp;
            }
        }
        this.numWords = 0;
    }

    // insert a word; return true if it wasn't already there, and return false it was already there
    public boolean insertWord(final String w)
    {
        final LetterBag key = new LetterBag(w);
        int index  = key.hashValue(tableSize);
        AnagramSet currAnagram = find(hashTable[index], key);

        if(currAnagram == null)
        {
            currAnagram = new AnagramSet(key);
            currAnagram.insert(w);
            this.numWords++;
            this.insertNew(index, currAnagram);
            return true;
        }
        else if(currAnagram.contains(w))
        {
            return false;
        }
        else 
        {
            currAnagram.insert(w);
            this.numWords++;
            return true;
        }
    }

    // insert multiple words and return how many weren't already there
    public int insertWords(final ArrayList<String> list)
    {
        int numInserted = 0;
        for(int n = 0; n < list.size(); n++)
        {
            if(insertWord(list.get(n)))
            {
                numInserted++;
            }
        }
        return numInserted;
    }

    // remove the word; return true if the word was there
    public boolean removeWord(String w)
    {
        final LetterBag currKey = new LetterBag(w);
        int index = currKey.hashValue(tableSize);
        AnagramSet currAnagram = hashTable[index];
        AnagramSet prevAnagram = null;

        while(currAnagram != null)
        {
            if(currAnagram.key == currKey)
            {
                if(currAnagram.contains(w))
                {
                    currAnagram.remove(w);
                    numWords--;
                }
                // If the AnagramSet is empty after removing, delete it.
                if(currAnagram.getSize() == 0)
                {
                    // If previous == null, nullify currAnagram
                    if(prevAnagram == null)
                    {
                        hashTable[index] = currAnagram.next;
                        currAnagram = null;
                    }
                    // Current anagram is the last one in the list
                    else if(currAnagram.next == null)
                    {
                        prevAnagram.next = null;
                        currAnagram = null;
                    }
                    // Somewhere in the middle of the list
                    else 
                    {
                        prevAnagram.next = currAnagram.next;
                        currAnagram = null;
                    }
                    return true;    // Word has been removed, return true.
                }
                return false;   // the word wasn't in the matching Anagram, return false.
            }
            prevAnagram = currAnagram;
            currAnagram = currAnagram.next;
        }
        return false;
    }

    public final boolean contains(final String word)
    {
        AnagramSet match = findWord(word);
        return (match == null)? false : match.contains(word);
    }

    // return words that are anagram for the given word
    public final ArrayList<String> getWords(final String word)
    {
        AnagramSet match = this.findWord(word);
        return (match == null)? new ArrayList<>() : match.getWords();
    }

    // return number of anagrams forms found for the given word
    public final int getNumAnagrams(final String word)
    {
        AnagramSet match = findWord(word);
        return (match == null)? 0 : match.getSize();
    }

    // print AnagramSets in each bucket of table
    public final void printDictionary(int limit)
    {   
        for(int n = 0; n < tableSize && limit > 0; n++)
        {
            AnagramSet currAnagram = hashTable[n];
            if(currAnagram != null)
            {
                System.out.print("\t" + n + "..." + "       ");
                while(currAnagram != null)
                {
                    currAnagram.print();
                    System.out.print(" ");
                    currAnagram = currAnagram.next;
                }
                limit--;
                System.out.print("\n\n");
            }
        }
        System.out.print("Number of words: " + numWords + "   " + "tableSize = " +
                        tableSize + "\n\n");
    }
}
