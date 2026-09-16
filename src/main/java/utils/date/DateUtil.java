package utils.date;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@UtilityClass
public class DateUtil {

    private final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Get date as String by offset
     *
     * @param offset 0 = today, 1 = tomorrow, -1 = yesterday, etc.
     * @return date as String (yyyy-MM-dd format)
     */
    public String getDateAsString(int offset) {
        LocalDate date = LocalDate.now().plusDays(offset);
        String result = date.format(FORMATTER);
        logger.debug("Generated date with offset {}: {}", offset, result);
        return result;
    }

    /**
     * Get date as String by offset with custom format
     *
     * @param offset  0 = today, 1 = tomorrow, -1 = yesterday, etc.
     * @param pattern date format pattern (e.g., "dd/MM/yyyy", "MM-dd-yyyy")
     * @return date as String in custom format
     */
    public String getDateAsString(int offset, String pattern) {
        LocalDate date = LocalDate.now().plusDays(offset);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(pattern);
        String result = date.format(customFormatter);
        logger.debug("Generated date with offset {} and pattern {}: {}", offset, pattern, result);
        return result;
    }

    /**
     * Get date as java.util.Date by offset
     *
     * @param offset 0 = today, 1 = tomorrow, -1 = yesterday, etc.
     * @return Date object
     */
    public Date getDateAsDate(int offset) {
        LocalDate localDate = LocalDate.now().plusDays(offset);
        Date result = java.sql.Date.valueOf(localDate);
        logger.debug("Generated Date object with offset {}: {}", offset, result);
        return result;
    }
}
