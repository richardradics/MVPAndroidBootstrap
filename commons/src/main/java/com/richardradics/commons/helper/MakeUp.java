package com.richardradics.commons.helper;

/**
 * Created by radicsrichard on 15. 03. 17..
 */
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

public class MakeUp
{
    private final Spannable sb;

    public MakeUp( String input )
    {
        sb = new SpannableString( input );
    }

    public MakeUp strikethrough( int start, int length )
    {
        final StrikethroughSpan span = new StrikethroughSpan();
        sb.setSpan( span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        return this;
    }

    public MakeUp underline( int start, int length )
    {
        final UnderlineSpan span = new UnderlineSpan();
        sb.setSpan( span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        return this;
    }

    public MakeUp boldify( int start, int length )
    {
        final StyleSpan span = new StyleSpan( android.graphics.Typeface.BOLD );
        sb.setSpan( span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        return this;
    }

    public MakeUp italize( int start, int length )
    {
        final StyleSpan span = new StyleSpan( Typeface.ITALIC );
        sb.setSpan( span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        return this;
    }

    public MakeUp colorize( int start, int length, int color )
    {
        final ForegroundColorSpan span = new ForegroundColorSpan( color );
        sb.setSpan( span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        return this;
    }

    public MakeUp mark( int start, int length, int color )
    {
        final BackgroundColorSpan span = new BackgroundColorSpan( color );
        sb.setSpan( span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        return this;
    }

    public MakeUp proportionate( int start, int length, float proportion )
    {
        final RelativeSizeSpan span = new RelativeSizeSpan( proportion );
        sb.setSpan( span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        return this;
    }

    public Spannable apply()
    {
        return sb;
    }
}