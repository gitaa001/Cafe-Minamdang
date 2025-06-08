package com.cafeminamdang.view;

public enum Role {
    BUSINESS_OWNER("Business Owner"),
    BRANCH_MANAGER("Branch Manager"),
    PURCHASING("Purchasing");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}