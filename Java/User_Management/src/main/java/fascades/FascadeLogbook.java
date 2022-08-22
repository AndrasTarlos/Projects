package fascades;

import log.LogBook;

import javax.swing.*;

public class FascadeLogbook {
    LogBook logBook = LogBook.getLogBookInstance();

    /**
     * Retruns all entries from the logbook
     * @return DefaultListModel<String>
     */
    public DefaultListModel<String> getAllEntries() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s: logBook.getEntries()) {
            model.addElement(s);
        }
        return model;
    }
}
