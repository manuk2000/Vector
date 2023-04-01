import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.lang.model.element.Element;

public class Vector<E> {

    private int capacityIncreament = 10;
    private int ElementCount = 0;
    private Object[] elementData;

    Vector(int initCapasity) {
        if (initCapasity < 0) {
            throw new IllegalArgumentException("Illegal Capasity: " + initCapasity);
        }
        this.elementData = new Object[initCapasity];
    }

    Vector() {
        this(10);
    }

    Vector(Collection<? extends E> o) {
        Object[] a = o.toArray();
        ElementCount = a.length;
        if (o.getClass() == ArrayList.class) {
            elementData = a;
        } else {
            elementData = Arrays.copyOf(a, ElementCount, Object[].class);
        }
        capacityIncreament = ElementCount;
    }

    public void setSize(int minCapacity) {
        Object[] newarr = new Object[2];
        if (minCapacity > elementData.length) {
            newarr = new Object[minCapacity];
        }

        newarr = Arrays.copyOf(elementData, newarr.length);
        for (int i = elementData.length; i < newarr.length; i++) {
            newarr[i] = null;
        }
        elementData = newarr;
        capacityIncreament = minCapacity;
    }

    public int capasity() {
        return elementData.length;
    }

    public int size() {
        return ElementCount;
    }

    public boolean isEmpty() {
        return elementData.length == 0;
    }

    public Enumaeration<E> elements() {
        return new Enumaeration<E>() {
            int count = 0;
            int lastIndex = lastIndexOf(lastElement());
            public boolean hasMoreElements() {
                return (count < lastIndex);
            }

            public E nextElement() {
                if (count <= lastIndex) {
                    return (E) elementData[count++];
                }
                return null;
            }
        };
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public int indexOf(Object o) {
        return indexOf(o, 0);
    }

    public int indexOf(Object o, int startIndex) {
        if (o == null)
            return -1;
        for (int i = startIndex; i < ElementCount; i++) {
            if (elementData[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        return lastIndexOf(o, ElementCount - 1);
    }

    public int lastIndexOf(Object o, int startIndex) {
        if (o == null || startIndex > elementData.length - 1)
            return -1;
        for (int i = startIndex; i >= 0; i--) {
            if (elementData[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public E elementAt(int index) {
        if (index > elementData.length - 1) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementData.length);
        }

        return elementData(index);
    }

    private E elementData(int index) {
        return (E) elementData[index];
    }

    public E frstElement() {
        if (elementData.length == 0) {
            throw new NoSuchElementException();
        }
        return (E) elementData[0];
    }

    public E lastElement() {
        if (elementData.length == 0) {
            throw new NoSuchElementException();
        }
        return (E) elementData[ElementCount - 1];
    }

    public void setElementAt(E o, int index) {
        if (capacityIncreament <= index) {
            throw new NoSuchElementException();
        }
        ++ElementCount;
        elementData[index] = o;
    }

    public void removeElementAt(int index) {
        if (index >= elementData.length || index <= 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (index == elementData.length - 1) {
            System.arraycopy(elementData, index, elementData, index + 1, elementData.length - 1 - index);
        }
        ElementCount--;
        elementData[ElementCount + 1] = null;

    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < elementData.length; i++) {
            res.append(elementData[i].toString());
        }
        return res.toString();
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor = 0;
        int lastIndex = lastIndexOf(lastElement());

        @Override
        public boolean hasNext() {
            return cursor <= lastIndex;
        }

        @Override
        public E next() {
            if (cursor > lastIndex) {
                throw new NoSuchElementException();
            }
            return elementAt(cursor++);
        }

        @Override
        public void remove() {
            if (cursor < 1) {
                throw new IllegalStateException();
            }
            --ElementCount;
            elementData[--cursor] = null;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            while (hasNext())
                action.accept(next());
        }
    }
}
