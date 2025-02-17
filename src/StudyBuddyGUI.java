import managers.*;

import javax.swing.*;

public class StudyBuddyGUI {

    public StudyBuddyGUI() {

        // Create and configure the frame
        JFrame frame = new JFrame();
        FrameManager.configureFrame(frame, "StudyBuddy");

        // Title Panel created and configured
        PanelManager.createTitlePanel(frame);

        // Content panel created and populated with subject list
        JPanel contentPanel = PanelManager.createContentPanel("Subjects", null, null, frame, PanelManager.ContentType.SUBJECTS);

        // Makes the frame visibility
        frame.setVisible(true);

    }



}
