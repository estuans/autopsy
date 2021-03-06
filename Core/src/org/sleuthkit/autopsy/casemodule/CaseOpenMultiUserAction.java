/*
 * Autopsy Forensic Browser
 *
 * Copyright 2017 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.casemodule;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.WindowManager;
import org.sleuthkit.autopsy.core.UserPreferences;

/**
 * The action associated with the Open Multi-User Case menu item via the
 * layer.xml file.
 *
 * This action should only be invoked in the event dispatch thread (EDT).
 */
@ActionID(category = "Case", id = "org.sleuthkit.autopsy.casemodule.CaseOpenMultiUserAction")
@ActionReference(path = "Menu/Case", position = 102)
@ActionRegistration(displayName = "#CTL_CaseOpenMultiUserAction", lazy = false)
@NbBundle.Messages({"CTL_CaseOpenMultiUserAction=Open Multi-User Case"})
public final class CaseOpenMultiUserAction extends CallableSystemAction implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static JDialog multiUserCaseWindow;

    private static final String DISPLAY_NAME = Bundle.CTL_CaseOpenMultiUserAction();

    public CaseOpenMultiUserAction() {
    }

    @Override
    public boolean isEnabled() {
        return UserPreferences.getIsMultiUserModeEnabled();
    }

    /**
     * Pops up a case selection panel to allow the user to select a multi-user
     * case to open.
     *
     * @param event The action event.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        WindowManager.getDefault().getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        if (multiUserCaseWindow == null) {
            multiUserCaseWindow = MultiUserCasesDialog.getInstance();
        }
        multiUserCaseWindow.setLocationRelativeTo(WindowManager.getDefault().getMainWindow());
        multiUserCaseWindow.setVisible(true);

        WindowManager.getDefault().getMainWindow().setCursor(null);
    }

    @Override
    public void performAction() {
        actionPerformed(null);
    }

    @Override
    public String getName() {
        return DISPLAY_NAME;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean asynchronous() {
        return false; // run on edt
    }
}
