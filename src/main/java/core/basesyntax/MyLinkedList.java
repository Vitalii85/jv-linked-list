package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
            return;
        }
        last.next = new Node<>(last, value, null);
        last = last.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        node.prev = new Node<>(node.prev, value, node);
        if (node == first) {
            first = node.prev;
            size++;
            return;
        }
        node.prev.prev.next = node.prev;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        deleteNode(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        while (node != null && !Objects.equals(node.item, object)) {
            node = node.next;
        }
        if (node != null) {
            deleteNode(node);
        }
        return node != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index passed is invalid, index: "
                    + index + ", list size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void deleteNode(Node<T> node) {
        if (node == first && node == last) {
            first = null;
            last = null;
            size = 0;
            return;
        }
        if (node == first) {
            node.next.prev = node.prev;
            first = node.next;
            size--;
            return;
        }
        if (node == last) {
            node.prev.next = node.next;
            last = node.prev;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
