package com.shahrinsiddeka.heamoslimdampatya;

import androidx.annotation.Nullable;

import com.shahrinsiddeka.heamoslimdampatya.MainAdapter.ChapterModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** The table of contents of the book. Each file name maps to assets/&lt;file&gt;.txt */
public final class Book {

    public static final List<ChapterModel> CHAPTERS = Collections.unmodifiableList(Arrays.asList(
            new ChapterModel("স্বামী স্ত্রীর সম্পর্ক", "1"),
            new ChapterModel("মহব্বাত", "2"),
            new ChapterModel("পরিবারের পর্দা", "3"),
            new ChapterModel("দায়িত্ব", "4"),
            new ChapterModel("সংসার জীবন", "5"),
            new ChapterModel("বৈশিষ্ট্য অনুযায়ী দাম্পত্য", "6"),
            new ChapterModel("আদর্শ দাম্পত্য", "7"),
            new ChapterModel("আদর্শ মা- বাবা হোন", "8"),
            new ChapterModel("সন্তান সন্ততি", "9"),
            new ChapterModel("বিবাহ বিচ্ছেদ", "10"),
            new ChapterModel("পুরুষের বহু বিবাহ", "11"),
            new ChapterModel("এক নজরে", "12")
    ));

    public static final ChapterModel ABOUT_WRITER = new ChapterModel("লেখকের পরিচয়", "0");
    public static final ChapterModel WRITER_WORDS = new ChapterModel("লেখকের কথা", "110");

    private Book() {
    }

    /** Index of the chapter in {@link #CHAPTERS}, or -1 for pages outside the chapter list. */
    public static int indexOf(String file) {
        for (int i = 0; i < CHAPTERS.size(); i++) {
            if (CHAPTERS.get(i).getFileName().equals(file)) {
                return i;
            }
        }
        return -1;
    }

    @Nullable
    public static ChapterModel find(@Nullable String file) {
        int index = file == null ? -1 : indexOf(file);
        return index >= 0 ? CHAPTERS.get(index) : null;
    }
}
