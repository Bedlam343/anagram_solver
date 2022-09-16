package AnagramProject;

import java.util.ArrayList;

public class LetterBag 
{
    private int counts[] = new int[26];
    private int size;
    public int myNumber;
    public static int mystery = 1;

    private static boolean inRange(char c)
    {
        return (c >= 'a' && c <= 'z');
    }

    public LetterBag()  // no-parameter constructor for an empty LetterBag
    {
        this.clear();
        myNumber = mystery;
        mystery++;
    }
    public LetterBag(final ArrayList<Character> list)   // initialize counts using letters in list
    {
        this.clear();
        for(int n = 0; n < list.size(); n++)
        {
            if(inRange(list.get(n)))
            {
                counts[list.get(n) - 'a']++;
                size++;
            }
        }
        myNumber = mystery;
        mystery++;
    }
    public LetterBag(final String s)    // similar to ArrayList constructor, except initializing with a string
    {
        this.clear();
        for(int n = 0; n < s.length(); n++)
        {
            if(inRange(s.charAt(n)))
            {
                counts[s.charAt(n) - 'a']++;
                size++;
            }
        }
        myNumber = mystery;
        mystery++;
    }

    public final int getCurrentSize()   // return current size of LetterBag
    {
        return this.size;
    }

    public final boolean isEmpty()  // return true if this.size is zero
    {
        return this.size == 0;
    }

    public LetterBag add(char c)    // insert c into the current object and return the object -- if c is out of range, do nothing
    {
        if(inRange(c))
        {
            this.counts[c - 'a']++;
            this.size++;
        }
        System.out.println("\n" + this.myNumber + "\n");
        return this;
    }
    public LetterBag add(final LetterBag other) // insert contents of other into current object and return the current object
    {
        for(int n = 0; n < 26; n++)
        {
            this.counts[n] = this.counts[n] + other.counts[n];
        }
        this.size = this.size + other.size;
        return this;
    }

    // add c to copy of current object and return the copy
    public final LetterBag getSum(char c)
    {
        LetterBag temp = new LetterBag(this.toArrayList());
        temp.add(c);
        return temp;
    }
    public final LetterBag getSum(final LetterBag other)
    {
        LetterBag temp = new LetterBag(this.toArrayList());
        temp.add(other);
        return temp;
    }

    // remove one occurrence of c from the copy of current object and return the copy
    // if  is out of range or count is equal to zero, do nothing.
    public LetterBag remove(char c)
    {
        if(inRange(c) && this.counts[c - 'a'] > 0)
        {
            this.counts[c - 'a']--;
            size--;
        }
        return this;
    }
    public LetterBag remove(final LetterBag other)
    {
        for(int n = 0; n < 26; n++)
        {
            if(this.counts[n] <= other.counts[n])
            {
                this.size = this.size - this.counts[n];
                this.counts[n] = 0;
            }
            else
            {
                this.counts[n] = this.counts[n] - other.counts[n];
                this.size = this.size - other.counts[n];
            }
        }
        return this;
    }

    public final LetterBag getDifference(char c)
    {
        LetterBag temp = new LetterBag(this.toArrayList());
        temp.remove(c);
        return temp;
    }
    /*public final LetterBag getDifference(final LetterBag other)
    {
        LetterBag temp = new LetterBag(this.toArrayList());
        temp.remove(other);
        return this;
    }*/

    // remove all occurences of c from the current object.
    // if c is out of range or count is zero, do nothing.
    public void removeAll(char c)
    {
        if(inRange(c))
        {
            this.size = this.size - this.counts[c - 'a'];
            this.counts[c - 'a'] = 0;
        }
    }

    // remove all occurrences of all letters from the current object and set size to zero.
    public void clear()
    {
        this.size = 0;
        for(int n = 0; n < 26; n++)
        {
            this.counts[n] = 0;
        }
    }

    // return the number of occurrences of c in the current object
    public final int getFrequency(char c)
    {
        if(inRange(c))
        {
            return this.counts[c - 'a'];
        }
        return 0;
    }

    // return an ArrayList with the letters in this object, in sorted order and lowercase
    public final ArrayList<Character> toArrayList()
    {
        ArrayList<Character> list = new ArrayList<Character>(this.size);
        for(int n = 0; n < 26; n++)
        {
            for(int m = 0; m < this.counts[n]; m++)
            {
                list.add((char)('a' + n));
            }
        }
        return list;
    }

    // return a string with the letters in this object, in sorted order and lowercase
    public final String toString()
    {
        String string = "";
        for(int n = 0; n < 26; n++)
        {
            for(int m = 0; m < this.counts[n]; m++)
            {
                string = string + (char)('a' + n);
            }
        }
        return string;
    }

    public final boolean isProperSubbag(final LetterBag other)
    {
        for(int n = 0; n < 26; n++)
        {
            if(other.counts[n] > this.counts[n])
            {
                return false;
            }
        }
        return true;
    }

    // public final boolean isSubbag(final Letterbag other) {}

    public final boolean equal(final LetterBag other)
    {
        for(int n = 0; n < 26; n++)
        {
            if(this.counts[n] != other.counts[n])
            {
                return false;
            }
        }
        return true;
    }

    public final int hashValue(int tableSize)
    {
        int h = counts[0];
        for(int i = 1; i < 26; i++)
        {
            h *= 37;
            h += counts[i];
            h %= tableSize;
        }
        return h;
    }
}
