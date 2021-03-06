package capstone.lip.landinformationportal.business.validation;

import capstone.lip.landinformationportal.common.constant.FeedbackStatusConstant;
import capstone.lip.landinformationportal.common.constant.ValidateMessageCommon;
import capstone.lip.landinformationportal.common.entity.Feedback;

public class FeedbackValidation extends StringValidation {

    public String isValidFeedback(Feedback feedback) {
//        if (feedback.getFeedBackID() == null) {
//            return ValidateMessageCommon.NULL;
//        }
//        if (feedback.getFeedBackID() <=0) {
//            return ValidateMessageCommon.NULL;
//        }
        if (feedback.getFeedbackTitle() == null || feedback.getFeedbackContent() == null) {
            return ValidateMessageCommon.NULL;
        }
        if (feedback.getFeedbackTitle().isEmpty() || feedback.getFeedbackContent().isEmpty()) {
            return ValidateMessageCommon.EMPTY;
        }
        String error = isSpecialCharOnly(feedback.getFeedbackTitle());
        if (!error.isEmpty()) {
            return error;
        }
        error = isValidStatus(feedback);
        if (!error.isEmpty()) {
            return error;
        }
        if (feedback.getUser() == null) {
            return "User not found";
        }
        return "";
    }

    public String isValidFeedbackReply(Feedback feedback) {
        if (feedback.getFeedbackAdminReply() == null) {
            return ValidateMessageCommon.NULL;
        }
        if (feedback.getFeedbackAdminReply().isEmpty()) {
            return ValidateMessageCommon.EMPTY;
        }
        return "";
    }

    public String isValidStatus(Feedback feedback) {
        if (feedback.getFeedbackStatus() == null) {
            return ValidateMessageCommon.NULL;
        }
        if (feedback.getFeedbackStatus().isEmpty()) {
            return ValidateMessageCommon.EMPTY;
        }
        if (!feedback.getFeedbackStatus().equals(FeedbackStatusConstant.OPEN) && !feedback.getFeedbackStatus().equals(FeedbackStatusConstant.CLOSE)) {
            return "Invalid status";
        }
        return "";
    }
}
