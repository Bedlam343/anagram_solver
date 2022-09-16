package AnagramProject;

import java.util.ArrayList;

public class AnagramSet
{
    public final LetterBag key;
    public AnagramSet next;
    private ArrayList<String> words;

    public AnagramSet(final LetterBag theKey)
    {
        key = theKey;
        next = null;
        words = new ArrayList<String>();
    }

    public void insert(final String s)
    {
        int size = words.size();
        for(int n = 0; n < size; n++)
        {
            int compare = words.get(n).compareTo(s);
            if(compare > 0)
            {
                words.add(n, s);
                return;
            }
            else if (compare == 0)
            {
                return;
            }
        }
        words.add(s);
    }

    public void remove(final String s)
    {
        int left = 0, right = getSize() - 1;
        while(left <= right)
        {
            int mid = left + (right - left) / 2;
            int compare = words.get(mid).compareTo(s);

            if(compare == 0)
            {
                words.remove(mid);
                return;
            }
            else if(compare > 0)
            {
                right = mid - 1;
            }
            else 
            {
                left = mid + 1;
            }
        }
    }

    public final int getSize()
    {
        return words.size();
    }

    public final boolean contains(final String s)
    {
        int left = 0, right = getSize() - 1;
        while(left <= right)
        {
            int mid = left + (right - left) / 2;
            int compare = words.get(mid).compareTo(s);
            
            if(compare == 0)
            {
                return true;
            }
            else if(compare > 0)
            {
                right = mid - 1;
            }
            else 
            {
                left = mid + 1;
            }
        }
        return false;
    }

    public void clear()
    {
        words.clear();
    }

    public final ArrayList<String> getWords()
    {
        return words;
    }

    public final void print()
    {
        System.out.print("{" + this.key.toString() + ": ");
        for(int n = 0; n < this.getSize(); n++)
        {
            System.out.print(this.words.get(n));
            if(n < this.getSize() - 1)
            {
                System.out.print(", ");
            }
        }
        System.out.print("}");
    }
}