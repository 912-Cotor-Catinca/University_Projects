package domain;

import java.util.List;

public class Vector {
    private List<Integer> elements;
    private int length;

    public Vector(List<Integer> elements) {
        this.elements = elements;
        this.length = elements.size();
    }

    public int get(int index) {
        return this.elements.get(index);
    }

    public List<Integer> getElements() {
        return elements;
    }

    public void setElements(List<Integer> elements1) {
        this.elements = elements1;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Vector{" + "elements=" + elements + ", length=" + length + "}";
    }
}
