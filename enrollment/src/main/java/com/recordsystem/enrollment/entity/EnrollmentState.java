package com.recordsystem.enrollment.entity;

public enum EnrollmentState {
    OPEN,
    CORRECTION,
    CLOSED;

    public boolean available() {
        return this == OPEN || this == CORRECTION;
    }
}
