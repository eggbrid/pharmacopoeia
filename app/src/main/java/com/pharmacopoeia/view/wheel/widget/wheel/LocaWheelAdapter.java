package com.pharmacopoeia.view.wheel.widget.wheel;

import java.util.List;

public class LocaWheelAdapter<T> implements WheelAdapter {


    /**
     * The default items length
     */
    public static final int DEFAULT_LENGTH = -1;

    // items
    private List<T> items;
    // length
    private int length;
    // format
    private String format;
    private String values = null;
    private int index;

    /**
     * Constructor
     *
     * @param items  the items
     * @param length the max items length
     */
    public LocaWheelAdapter(List<T> items, int length) {
        this.items = items;
        this.length = length;
    }

    /**
     * Contructor
     *
     * @param items the items
     */
    public LocaWheelAdapter(List<T> items) {
        this.items = items;
        this.length = length;
    }

    public String getItem(int index) {
        if (index >= 0 && index < items.size()) {
            setIndex(index);
            setValue(items.get(index).toString());
            return getValues();
        }
        return null;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    // 返回当前选中的�?
    public String getValues() {
        return values;
    }

    public void setValue(String value) {
        this.values = value;
    }

    public int getItemsCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public int getMaximumLength() {
        return length;
    }

}

