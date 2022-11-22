package com.company;


public class ArrayList<T> {
    private final int INIT_SIZE = 16;
    private final int CUT_RATE = 4;
    private Object[] array = new Object[INIT_SIZE];
    private static int pointer = 0;
    private int size = 0;
    protected transient int modCount = 0;
    

    public void add(T item) {
        if(pointer == array.length-1)
            resize(array.length*2);
        array[pointer++] = item;
    }

    public void addindex(int index, T item) {
        modCount++;
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.array).length)
            elementData = (Object[]) grow(size + 1);
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = item;
        size = s + 1;
    }


    public T get(int index) {
        return (T) array[index];
    }

    public void remove(int index) {
        for (int i = index; i < pointer; i++)
            array[i] = array[i + 1];
        array[pointer] = null;
        pointer--;
        if (array.length > INIT_SIZE && pointer < array.length / CUT_RATE)
            resize(array.length / 2);

    }
    public static int size() {
        return pointer;
    }
    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, pointer);
        array = newArray;
    }
    private T grow(int i) {
        return grow(size + 1);
    }

    public boolean remove(T item) {
        final Object[] es = array;
        final int size = this.size;
        int i = 0;
        found: {
            if (item == null) {
                for (; i < size; i++)
                    if (es[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (item.equals(es[i]))
                        break found;
            }
            return false;
        }
        fastRemove(item, i);
        return true;
    }
    private void fastRemove(T item, int i) {
        modCount++;
        Object[] elementData = new Object[0];
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(item, i + 1, item, i, newSize - i);
        elementData[size = newSize] = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        modCount++;
        final Object[] es = array;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }
    public boolean contains(T item) {
        return indexOf(item) >= 0;
    }

    int indexOfRange(T item, int start, int end) {
        Object[] es = array;
        if (item == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (item.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }


    public int indexOf(T item) {
        return indexOfRange(item, 0, size);
    }






}
