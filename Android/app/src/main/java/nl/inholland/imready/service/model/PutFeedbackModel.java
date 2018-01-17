package nl.inholland.imready.service.model;

/**
 * Created by Peter on 16/01/2018.
 */

public class PutFeedbackModel {
    private final Boolean approved;
    private final String feedback;

    public PutFeedbackModel(Boolean approved, String feedback) {
        this.approved = approved;
        this.feedback = feedback;
    }
}
