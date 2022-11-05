package net.response;

import java.util.List;
import java.util.Objects;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse<T> extends Response {

    private List<T> page;
    private final boolean hasMorePages;

    public PagedResponse(boolean success, List<T> page, boolean hasMorePages) {
        super(success);
        this.page = page;
        this.hasMorePages = hasMorePages;
    }

    public PagedResponse(boolean success, String message, List<T> page, boolean hasMorePages) {
        super(success, message);
        this.page = page;
        this.hasMorePages = hasMorePages;
    }



    /**
     * An indicator of whether more data is available from the server. A value of true indicates
     * that the result was limited by a maximum value in the request and an additional request
     * would return additional data.
     *
     * @return true if more data is available; otherwise, false.
     */
    public boolean getHasMorePages() {
        return hasMorePages;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }
    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        PagedResponse that = (PagedResponse) param;

        return (Objects.equals(page, that.page) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }



    @Override
    public int hashCode() {
        return Objects.hash(page);
    }
}
