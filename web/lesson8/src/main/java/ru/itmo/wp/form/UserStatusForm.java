package ru.itmo.wp.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserStatusForm {
    @NotNull
    @NotEmpty
    private String disabled;
    private long userId;

    @AssertTrue
    private boolean isDisabledValid() {
        return "Enable".equals(disabled) || "Disable".equals(disabled);
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String enabled) {
        this.disabled = enabled;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
