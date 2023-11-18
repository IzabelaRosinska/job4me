package miwm.job4me.messages;

public class EmailMessages {
    public static String employerJobFairParticipationRequestEmailSubject(String employerName, String jobFairName) {
        return String.format("Zgłoszenie udziału %s w targach pracy %s", employerName, jobFairName);
    }

    public static String employerJobFairParticipationRequestEmailText(String employerName, Long jobFairId, String jobFairName) {
        return String.format("Firma %s zgłosiła chęć udziału w targach pracy #%d %s. Prosimy o sprawdzenie zgłoszenia.", employerName, jobFairId, jobFairName);
    }
}
