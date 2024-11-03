//Hi Halie, please ignore this class--- the test is in the main method of SpellDictionary.java

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class MainTest {
    SpellDictionary dictionary = new SpellDictionary("word.txt");

    // Deletions
    @Test
    public void testDeletionMiddle() {
        List<String> nearMisses = dictionary.nearMisses("apple");
        assertTrue(nearMisses.contains("appple"));
    }

    @Test
    public void testDeletionEnd() {
        List<String> nearMisses = SpellDictionary.nearMisses("test");
        assertTrue(nearMisses.contains("tes"));
    }

    // Insertions
    @Test
    public void testInsertionBeginning() {
        List<String> nearMisses = SpellDictionary.nearMisses("apple");
        assertTrue(nearMisses.contains("pple"));
    }

    @Test
    public void testInsertionMiddle() {
        List<String> nearMisses = SpellDictionary.nearMisses("world");
        assertTrue(nearMisses.contains("wrld"));
    }

    // Substitutions
    @Test
    public void testSubstitutionVowel() {
        List<String> nearMisses = SpellDictionary.nearMisses("beak");
        assertTrue(nearMisses.contains("beak"));
    }

    @Test
    public void testSubstitutionConsonant() {
        List<String> nearMisses = SpellDictionary.nearMisses("world");
        assertTrue(nearMisses.contains("forld"));
    }

    // Transpositions
    @Test
    public void testTranspositionAdjacent() {
        List<String> nearMisses = SpellDictionary.nearMisses("world");
        assertTrue(nearMisses.contains("wolrd"));
    }

    @Test
    public void testTranspositionEnd() {
        List<String> nearMisses = SpellDictionary.nearMisses("spells");
        assertTrue(nearMisses.contains("spellt"));
    }

    // Splits
    @Test
    public void testSplitMiddle() {
        List<String> nearMisses = SpellDictionary.nearMisses("software");
        assertTrue(nearMisses.contains("soft ware"));
    }

    @Test
    public void testSplitCompoundWord() {
        List<String> nearMisses = SpellDictionary.nearMisses("airplane");
        assertTrue(nearMisses.contains("air plane"));
    }
}
