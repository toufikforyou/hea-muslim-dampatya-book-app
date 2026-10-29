package com.shahrinsiddeka.heamoslimdampatya;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.shahrinsiddeka.heamoslimdampatya.MainAdapter.ChapterModel;

import org.junit.Test;

import java.io.File;

public class BookTest {

    @Test
    public void banglaNumber_convertsEveryDigit() {
        assertEquals("০", Bangla.number(0));
        assertEquals("১২", Bangla.number(12));
        assertEquals("১০০", Bangla.number(100));
        assertEquals("-৫", Bangla.number(-5));
    }

    @Test
    public void indexOf_findsChaptersAndSkipsWriterPages() {
        assertEquals(0, Book.indexOf("1"));
        assertEquals(11, Book.indexOf("12"));
        assertEquals(-1, Book.indexOf(Book.ABOUT_WRITER.getFileName()));
        assertEquals(-1, Book.indexOf(Book.WRITER_WORDS.getFileName()));
        assertNull(Book.find(null));
    }

    @Test
    public void everyPage_hasAnAssetFile() {
        File assets = new File("src/main/assets");
        for (ChapterModel chapter : Book.CHAPTERS) {
            assertAssetExists(assets, chapter);
        }
        assertAssetExists(assets, Book.ABOUT_WRITER);
        assertAssetExists(assets, Book.WRITER_WORDS);
    }

    private static void assertAssetExists(File assets, ChapterModel chapter) {
        File file = new File(assets, chapter.getFileName() + ".txt");
        assertTrue("Missing asset for " + chapter.getTitle(), file.isFile());
    }
}
