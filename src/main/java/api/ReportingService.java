package api;

public class ReportingService {
    private static ReportingService instance;
    private String testRunId;
    private String testId;
    private String status;
    private String testSessionId;
    private String testRunResult;
    private String testExecutionResult;

    private ReportingService() {
    }

    public static ReportingService getInstance() {
        if (instance == null) {
            synchronized (ReportingService.class) {
                if (instance == null)
                    instance = new ReportingService();
            }
        }
        return instance;
    }

    public String getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(String testRunId) {
        this.testRunId = testRunId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestSessionId() {
        return testSessionId;
    }

    public void setTestSessionId(String testSessionId) {
        this.testSessionId = testSessionId;
    }

    public String getTestRunResult() {
        return testRunResult;
    }

    public void setTestRunResult(String testRunResult) {
        this.testRunResult = testRunResult;
    }

    public String getTestExecutionResult() {
        return testExecutionResult;
    }

    public void setTestExecutionResult(String testExecutionResult) {
        this.testExecutionResult = testExecutionResult;
    }
}
