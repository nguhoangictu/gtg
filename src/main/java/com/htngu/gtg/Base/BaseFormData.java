package com.htngu.gtg.Base;

public class BaseFormData<K, T> {

    private K status;
    private T data;

    public BaseFormData(K status, T data) {
        this.status = status;
        this.data = data;
    }

    public K getStatus() {
        return status;
    }

    public void setStatus(K status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
