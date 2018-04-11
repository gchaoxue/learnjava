package tutorials.templatetest;

public class TempB<T extends TempA<T>> {
    private T data;
    public void setData(T data) {
        this.data = data;
    }
    public T getData() {
        return this.data;
    }
}
