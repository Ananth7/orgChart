package personio.example.demo.request;

public enum OrgChartValidationState {
    VALID,
    INVALID_LOOP,
    INVALID_MULTIPLE_ROOTS,
    INVALID_DISCONNECTED_ORG
}
