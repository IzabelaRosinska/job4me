package miwm.job4me.messages;

public class EmailMessages {
    public static String employerJobFairParticipationRequestEmailSubject(String employerName, String jobFairName) {
        return String.format("Zgłoszenie udziału %s w targach pracy %s", employerName, jobFairName);
    }

    public static String employerJobFairParticipationRequestEmailText(String employerName, Long jobFairId, String jobFairName) {
        return String.format("Firma %s zgłosiła chęć udziału w targach pracy #%d %s. Prosimy o sprawdzenie zgłoszenia.", employerName, jobFairId, jobFairName);
    }

    public static String employerJobFairParticipationAcceptEmailSubject(String employerName, String jobFairName) {
        return String.format("Zgłoszenie udziału %s w targach pracy %s zostało zaakceptowane", employerName, jobFairName);
    }

    public static String employerJobFairParticipationAcceptEmailText(String employerName, Long jobFairId, String jobFairName) {
        return String.format("Firma %s zgłoszona na targi pracy #%d %s została zaakceptowana. Uczestnictwo w targach pracy zostało przypisane w systemie.", employerName, jobFairId, jobFairName);
    }

    public static String employerJobFairParticipationRejectEmailSubject(String employerName, String jobFairName) {
        return String.format("Zgłoszenie udziału %s w targach pracy %s zostało odrzucone", employerName, jobFairName);
    }

    public static String employerJobFairParticipationRejectEmailText(String employerName, Long jobFairId, String jobFairName) {
        return String.format("Firma %s zgłoszona na targi pracy #%d %s została odrzucona. Uczestnictwo w targach pracy nie zostało przypisane w systemie.", employerName, jobFairId, jobFairName);
    }

    public static String employerJobFairParticipationDeleteEmailSubject(String employerName, String jobFairName) {
        return String.format("Usunięto udział %s w targach pracy %s", employerName, jobFairName);
    }

    public static String employerJobFairParticipationDeleteEmailText(String employerName, Long jobFairId, String jobFairName) {
        return String.format("Organizator usunął firmę %s z listy uczestników targów pracy #%d %s. Uczestnictwo w targach pracy zostało usunięte z systemu.", employerName, jobFairId, jobFairName);
    }

    public static String organizerJobFairParticipationDeleteEmailSubject(String employerName, String jobFairName) {
        return String.format("Odwołano udział %s w targach pracy %s", employerName, jobFairName);
    }

    public static String organizerJobFairParticipationDeleteEmailText(String employerName, Long jobFairId, String jobFairName) {
        return String.format("Firma %s odwołała swój udział w targach pracy #%d %s. Uczestnictwo w targach pracy zostało usunięte z systemu.", employerName, jobFairId, jobFairName);
    }

    public static String resetPasswordEmailText() {
        return "Zresetuj swoje hasło\n Kliknij na poniższy link, aby zresetować swoje hasło.\n\n";
    }

    public static String resetPasswordEmailSubject(String email) {
        return String.format("Reset hasła użytkownika %s", email);
    }

    public static String confirmRegistrationEmailText() {
        return "Witaj w Job4Me!\n Kliknij w poniższy link, aby aktywować swoje konto\n\n";
    }

    public static String confirmRegistrationEmailSubject() {
        return "Potwierdzenie rejestracji konta w Job4Me";
    }
}
