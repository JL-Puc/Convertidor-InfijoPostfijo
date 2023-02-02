package dominio;

import excepciones.StackException;

public class Stack<T> {
    private int maxSize;
    private T stackArray[];
    private int top;

    public Stack(int maxSize ) {
        this.maxSize = maxSize;
        this.stackArray = (T[]) new Object[maxSize];
        this.top = -1;
    }

    public void push(T dato ) throws StackException {
        if(isFull()) {
            throw new StackException("Stack overflow");
        }
        stackArray[++top] = dato;
    }

    public T pop( ) throws StackException {
        if( isEmpty()) {
            throw new StackException("Stack underflow");
        }
        return (stackArray[top--]);
    }

    public boolean isEmpty( ) {
        return (top == -1);
    }

    public boolean isFull( ) {
        return (top == maxSize-1);
    }

    public T peek() {
        T item;
        if( top == -1){
         item = null;
            return item;
        }
        item = (stackArray[top]);
        return (item);
    }

}
