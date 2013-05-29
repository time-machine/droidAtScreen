package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Shows a table with all device properties.
 */
public class PropertiesCommand extends CommandWithTarget<DeviceFrame> {
  public PropertiesCommand(DeviceFrame target) {
    super(target);
    updateButton(target);
  }

  @Override
  protected void updateButton(DeviceFrame target) {
    setIcon("list");
    setTooltip("Shows all device properties");
  }

  @Override
  protected void doExecute(Application app, DeviceFrame target) {
    Map<String, String> properties = target.getDevice().getProperties();
    final String toolTipText = "Click on a row to view the complete property " +
        "value";
    PropertiesModel model = new PropertiesModel(properties);
    JTable tbl = new JTable(model) {
      @Override
      public String getToolTipText(MouseEvent event) {
        return toolTipText;
      }
    };
    tbl.getTableHeader().setToolTipText(toolTipText);
    tbl.setRowSelectionAllowed(true);
    tbl.getSelectionModel().addListSelectionListener(model);
    tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tbl.setShowHorizontalLines(true);
    tbl.setFillsViewportHeight(true);
    tbl.setPreferredScrollableViewportSize(new Dimension(400, 200));

    JScrollPane pane = new JScrollPane(tbl);
    JOptionPane.showMessageDialog(app.getAppFrame(), pane, "Device Properties",
        JOptionPane.PLAIN_MESSAGE);
  }

  class PropertiesModel extends AbstractTableModel implements
      ListSelectionListener {
    private List<String> names = new ArrayList<String>();
    private List<String> values = new ArrayList<String>();

    PropertiesModel(Map<String, String> properties) {
      for (String name : new TreeSet<String>(properties.keySet())) {
        names.add(name);
        values.add(properties.get(name));
      }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if (lsm.isSelectionEmpty()) return;
      int row = lsm.getMinSelectionIndex();

      JTextArea txt = new JTextArea((String)getValueAt(row, 1));
      txt.setRows(6);
      txt.setColumns(36);
      txt.setLineWrap(true);
      txt.setWrapStyleWord(true);
      txt.setEditable(false);

      JOptionPane.showMessageDialog(getApplication().getAppFrame(), txt,
          (String)getValueAt(row, 0), JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public Object getValueAt(int row, int col) {
      if (col == 0) return names.get(row);

    }
  }
}
