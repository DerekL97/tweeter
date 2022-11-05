package net.response;

public class GetCountResponse extends Response{
    int count;

    public GetCountResponse(boolean success) {
        super(success);
    }

    public GetCountResponse(boolean success, int count) {
        super(success);
        this.count = count;
    }

}
