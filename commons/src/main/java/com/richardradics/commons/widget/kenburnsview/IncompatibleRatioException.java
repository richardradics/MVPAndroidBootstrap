package com.richardradics.commons.widget.kenburnsview;

/**
 * Created by radicsrichard on 15. 04. 24..
 */
public class IncompatibleRatioException extends RuntimeException {

    public IncompatibleRatioException() {
        super("Can't perform Ken Burns effect on rects with distinct aspect ratios!");
    }
}
