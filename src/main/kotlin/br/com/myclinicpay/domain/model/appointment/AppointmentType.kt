package br.com.myclinicpay.domain.model.appointment

import com.fasterxml.jackson.annotation.JsonValue

enum class AppointmentType(@JsonValue val type: String) {
    SCHEDULE("schedule"),
    CONFIRMED("confirmed"),
    ABSENCE("absence"),
    CANCEL("cancel"),
    NOT_INFORMED("not_informed"),
    FEEDBACK_SESSION("feedback_session"),
    DELAYED("delayed"),
    ATTENDED("attended"),
    NOT_ATTENDED("not_attended"),
    SOCIAL("social")
}
