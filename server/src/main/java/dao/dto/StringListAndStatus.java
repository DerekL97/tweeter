package dao.dto;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class StringListAndStatus {

    private List<String> strings;
    private Status status;

    public StringListAndStatus(List<String> strings, Status status) {
        this.strings = strings;
        this.status = status;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
