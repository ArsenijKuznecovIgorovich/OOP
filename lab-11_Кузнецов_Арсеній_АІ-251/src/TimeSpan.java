import exceptions.IllegalInputTimeSpanException;
import exceptions.IllegalScaleFactorException;
import exceptions.NegativeTimeSpanException;

/**
 * Інтервал часу у годинах та хвилинах.
 * Хвилини завжди нормалізовані (0..59).
 */
public class TimeSpan {

    private int hours;
    private int minutes;

    /** Створює інтервал із годин та хвилин (хвилини повинні бути в межах 0..59). */
    public TimeSpan(int hours, int minutes) {
        validate(hours, minutes);
        this.hours = hours;
        this.minutes = minutes;
    }

    /** Створює інтервал із загальної кількості хвилин. */
    public TimeSpan(int totalMinutes) {
        if (totalMinutes < 0) {
            throw new IllegalInputTimeSpanException(
                    "Total minutes must not be negative: " + totalMinutes);
        }
        setTotalMinutes(totalMinutes);
    }

    private static void validate(int hours, int minutes) {
        if (hours < 0) {
            throw new IllegalInputTimeSpanException("Hours must not be negative: " + hours);
        }
        if (minutes < 0) {
            throw new IllegalInputTimeSpanException("Minutes must not be negative: " + minutes);
        }
        if (minutes > 59) {
            throw new IllegalInputTimeSpanException("Minutes must be in range 0..59: " + minutes);
        }
    }

    private void setTotalMinutes(int totalMinutes) {
        if (totalMinutes < 0) {
            throw new NegativeTimeSpanException("TimeSpan cannot be negative");
        }
        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getTotalMinutes() {
        return hours * 60 + minutes;
    }

    public double getTotalHours() {
        return getTotalMinutes() / 60.0;
    }

    public void add(int hours, int minutes) {
        validate(hours, minutes);
        setTotalMinutes(getTotalMinutes() + hours * 60 + minutes);
    }

    public void add(TimeSpan timespan) {
        if (timespan == null) {
            throw new IllegalInputTimeSpanException("TimeSpan must not be null");
        }
        setTotalMinutes(getTotalMinutes() + timespan.getTotalMinutes());
    }

    /** Віднімає інтервал. Якщо результат від'ємний - викидає виключення, а значення не змінюється. */
    public void subtract(TimeSpan timespan) {
        if (timespan == null) {
            throw new IllegalInputTimeSpanException("TimeSpan must not be null");
        }
        int result = getTotalMinutes() - timespan.getTotalMinutes();
        if (result < 0) {
            throw new NegativeTimeSpanException("Subtraction gives a negative TimeSpan: "
                    + this + " - " + timespan);
        }
        setTotalMinutes(result);
    }

    /** Множить інтервал на додатний множник. */
    public void scale(int factor) {
        if (factor <= 0) {
            throw new IllegalScaleFactorException("Scale factor must be positive: " + factor);
        }
        setTotalMinutes(getTotalMinutes() * factor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSpan other)) return false;
        return getTotalMinutes() == other.getTotalMinutes();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getTotalMinutes());
    }

    @Override
    public String toString() {
        return hours + "h " + minutes + "m";
    }
}
