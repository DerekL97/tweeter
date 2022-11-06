package net.response;

public class GetCountResponse extends Response{
    private int count;

    public GetCountResponse(boolean success) {
        super(success);
    }

    public GetCountResponse(boolean success, int count) {
        super(success);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
