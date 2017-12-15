package nl.inholland.imready.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import nl.inholland.imready.R;
import nl.inholland.imready.model.enums.BlockType;

public class BlockUtil {
    public static Drawable getDrawableIcon(Context context, BlockType type) {
        int drawableResource = R.drawable.ic_home;
        switch (type) {
            case LIVING:
                drawableResource = R.drawable.ic_home;
                break;
            case INSURANCE:
                drawableResource = R.drawable.ic_account;
                break;
            case MONEY:
                drawableResource = R.drawable.ic_coin;
                break;
            case HEALTH:
                drawableResource = R.drawable.ic_hospital;
                break;
            case SOCIAL:
                drawableResource = R.drawable.ic_people;
                break;
            case WORK:
                drawableResource = R.drawable.ic_business;
                break;
            case EDUCATION:
                drawableResource = R.drawable.ic_school;
                break;
            case FAMILY:
                drawableResource = R.drawable.ic_people;
                break;
            case RIGHTSANDOBLIGATIONS:
                drawableResource = R.drawable.ic_gavel;
                break;
            case TREATMENTPLAN:
                drawableResource = R.drawable.ic_event;
                break;
            case ADD:
                drawableResource = R.drawable.ic_add;
                break;
        }
        return context.getDrawable(drawableResource);
    }
}
