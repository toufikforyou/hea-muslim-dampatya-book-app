package com.shahrinsiddeka.heamoslimdampatya;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.shahrinsiddeka.heamoslimdampatya.MainAdapter.ChapterModel;

import java.util.ArrayList;
import java.util.List;

/** Shows a chapter paragraph by paragraph, followed by a footer to move to the next chapter. */
public class ParagraphAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface FooterListener {
        void onOpenChapter(ChapterModel chapter);

        void onBackToContents();
    }

    private static final int TYPE_PARAGRAPH = 0;
    private static final int TYPE_FOOTER = 1;

    private final List<String> paragraphs = new ArrayList<>();
    private final FooterListener footerListener;
    @Nullable
    private final ChapterModel previous;
    @Nullable
    private final ChapterModel next;

    private float textSize;
    private ReaderColors colors;

    public ParagraphAdapter(FooterListener listener, @Nullable ChapterModel previous, @Nullable ChapterModel next,
                            float textSize, ReaderColors colors) {
        this.footerListener = listener;
        this.previous = previous;
        this.next = next;
        this.textSize = textSize;
        this.colors = colors;
    }

    public void setParagraphs(List<String> items) {
        paragraphs.clear();
        paragraphs.addAll(items);
        notifyDataSetChanged();
    }

    public int getParagraphCount() {
        return paragraphs.size();
    }

    public void setStyle(float textSize, ReaderColors colors) {
        this.textSize = textSize;
        this.colors = colors;
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        return position < paragraphs.size() ? TYPE_PARAGRAPH : TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return paragraphs.isEmpty() ? 0 : paragraphs.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_FOOTER) {
            return new FooterHolder(inflater.inflate(R.layout.item_reader_footer, parent, false));
        }
        return new ParagraphHolder(inflater.inflate(R.layout.item_paragraph, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ParagraphHolder) {
            TextView textView = ((ParagraphHolder) holder).text;
            textView.setText(paragraphs.get(position));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            textView.setTextColor(colors.text);
        } else {
            bindFooter((FooterHolder) holder);
        }
    }

    private void bindFooter(FooterHolder holder) {
        holder.message.setTextColor(colors.secondaryText);
        if (next != null) {
            holder.next.setVisibility(View.VISIBLE);
            holder.next.setText(holder.itemView.getContext().getString(R.string.next_chapter, next.getTitle()));
            holder.next.setOnClickListener(v -> footerListener.onOpenChapter(next));
        } else {
            holder.next.setVisibility(View.GONE);
        }
        if (previous != null) {
            holder.previous.setVisibility(View.VISIBLE);
            holder.previous.setOnClickListener(v -> footerListener.onOpenChapter(previous));
        } else {
            holder.previous.setVisibility(View.GONE);
        }
        holder.contents.setOnClickListener(v -> footerListener.onBackToContents());
    }

    static class ParagraphHolder extends RecyclerView.ViewHolder {
        final TextView text;

        ParagraphHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView;
            // Long press copies a paragraph, so a favourite line can be shared easily.
            text.setOnLongClickListener(v -> {
                Context context = v.getContext();
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText(context.getString(R.string.app_name), text.getText()));
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    Toast.makeText(context, R.string.paragraph_copied, Toast.LENGTH_SHORT).show();
                }
                return true;
            });
        }
    }

    static class FooterHolder extends RecyclerView.ViewHolder {
        final TextView message;
        final MaterialButton next;
        final MaterialButton previous;
        final MaterialButton contents;

        FooterHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.footerMessage);
            next = itemView.findViewById(R.id.nextButton);
            previous = itemView.findViewById(R.id.previousButton);
            contents = itemView.findViewById(R.id.contentsButton);
        }
    }
}
