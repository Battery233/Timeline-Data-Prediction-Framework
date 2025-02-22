package edu.cmu.cs.cs214.hw5.framework.gui.plugin;

import edu.cmu.cs.cs214.hw5.framework.gui.DataPluginPanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * java date picker usage reference
 * https://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker
 */
public class DatePanel extends JPanel {

    public DatePanel(DataPluginPanel parent, boolean isStart) {

        setLayout(new GridLayout(2, 1));
        String dateName = "start";
        if (!isStart) dateName = "end";
        JLabel dateLabel = new JLabel("Choose " + dateName + " date.");
        add(dateLabel);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.addActionListener(e -> {
            Date selected = (Date) datePicker.getModel().getValue();
            parent.onDateChosen(selected, isStart);
        });
        add(datePicker);
    }

    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }

}
