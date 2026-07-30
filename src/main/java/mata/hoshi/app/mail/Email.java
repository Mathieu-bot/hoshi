package mata.hoshi.app.mail;

import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;
import mata.hoshi.app.PojaGenerated;

@PojaGenerated
public record Email(
    InternetAddress to,
    List<InternetAddress> cc,
    List<InternetAddress> bcc,
    String subject,
    String htmlBody,
    List<File> attachments) {}
