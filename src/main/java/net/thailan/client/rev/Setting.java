package net.thailan.client.rev;

public interface Setting<T> {
    public boolean setValue(T t);
    public T getValue();
}