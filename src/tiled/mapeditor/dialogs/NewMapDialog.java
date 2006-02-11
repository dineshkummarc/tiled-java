/*
 *  Tiled Map Editor, (c) 2004-2006
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 * 
 *  Adam Turk <aturk@biggeruniverse.com>
 *  Bjorn Lindeijer <b.lindeijer@xs4all.nl>
 */

package tiled.mapeditor.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import tiled.core.*;
import tiled.util.TiledConfiguration;
import tiled.mapeditor.widget.IntegerSpinner;
import tiled.mapeditor.widget.VerticalStaticJPanel;

public class NewMapDialog extends JDialog implements ActionListener
{
    private Map newMap;
    private IntegerSpinner mapWidth, mapHeight;
    private IntegerSpinner tileWidth, tileHeight;
    private JComboBox mapTypeChooser;

    public NewMapDialog(JFrame parent) {
        super(parent, "New Map", true);
        init();
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void init() {
        // Load dialog defaults

        TiledConfiguration conf = TiledConfiguration.getInstance();
        int defaultMapWidth = conf.getIntValue("tiled.newmapdialog.mapwidth", 64);
        int defaultMapHeight = conf.getIntValue("tiled.newmapdialog.mapheight", 64);
        int defaultTileWidth = conf.getIntValue("tiled.newmapdialog.tilewidth", 35);
        int defaultTileHeight = conf.getIntValue("tiled.newmapdialog.tileheight", 35);

        // Create the primitives

        mapWidth = new IntegerSpinner(defaultMapWidth, 1);
        mapHeight = new IntegerSpinner(defaultMapHeight, 1);
        tileWidth = new IntegerSpinner(defaultTileWidth, 1);
        tileHeight = new IntegerSpinner(defaultTileHeight, 1);

        // Map size fields

        JPanel mapSize = new VerticalStaticJPanel();
        mapSize.setLayout(new GridBagLayout());
        mapSize.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Map size"),
                    BorderFactory.createEmptyBorder(0, 5, 5, 5)));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5, 0, 0, 0);
        mapSize.add(new JLabel("Width: "), c);
        c.gridy = 1;
        mapSize.add(new JLabel("Height: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1; c.gridy = 0; c.weightx = 1;
        mapSize.add(mapWidth, c);
        c.gridy = 1;
        mapSize.add(mapHeight, c);

        // Tile size fields

        JPanel tileSize = new VerticalStaticJPanel();
        tileSize.setLayout(new GridBagLayout());
        tileSize.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Tile size"),
                    BorderFactory.createEmptyBorder(0, 5, 5, 5)));
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        tileSize.add(new JLabel("Width: "), c);
        c.gridy = 1;
        tileSize.add(new JLabel("Height: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1; c.gridy = 0; c.weightx = 1;
        tileSize.add(tileWidth, c);
        c.gridy = 1;
        tileSize.add(tileHeight, c);

        // OK and Cancel buttons

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        JPanel buttons = new VerticalStaticJPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createGlue());
        buttons.add(okButton);
        buttons.add(Box.createRigidArea(new Dimension(5, 0)));
        buttons.add(cancelButton);

        // Map type and name inputs

        mapTypeChooser = new JComboBox();
        mapTypeChooser.addItem("Orthogonal");
        mapTypeChooser.addItem("Isometric");
        mapTypeChooser.addItem("Shifted (iso and hex)");
        // TODO: Enable when view is implemented
        //mapTypeChooser.addItem("Oblique");
        mapTypeChooser.addItem("Hexagonal (experimental)");

        JPanel miscPropPanel = new VerticalStaticJPanel();
        miscPropPanel.setLayout(new GridBagLayout());
        miscPropPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        miscPropPanel.add(new JLabel("Map type: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1; c.gridy = 0; c.weightx = 1;
        miscPropPanel.add(mapTypeChooser, c);

        // Putting two size panels next to eachother

        JPanel sizePanels = new JPanel();
        sizePanels.setLayout(new BoxLayout(sizePanels, BoxLayout.X_AXIS));
        sizePanels.add(mapSize);
        sizePanels.add(Box.createRigidArea(new Dimension(5, 0)));
        sizePanels.add(tileSize);

        // Main panel

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(miscPropPanel);
        mainPanel.add(sizePanels);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(Box.createGlue());
        mainPanel.add(buttons);

        getContentPane().add(mainPanel);
        getRootPane().setDefaultButton(okButton);
    }

    public Map create() {
        setVisible(true);
        return newMap;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            int w = mapWidth.intValue();
            int h = mapHeight.intValue();
            int twidth = tileWidth.intValue();
            int theight = tileHeight.intValue();
            int orientation = Map.MDO_ORTHO;
            String mapTypeString = (String)mapTypeChooser.getSelectedItem();

            if (mapTypeString.equals("Isometric")) {
                orientation = Map.MDO_ISO;
            } else if (mapTypeString.equals("Oblique")) {
                orientation = Map.MDO_OBLIQUE;
            } else if (mapTypeString.equals("Hexagonal (experimental)")) {
                orientation = Map.MDO_HEX;
            } else if (mapTypeString.equals("Shifted (iso and hex)")) {
                orientation = Map.MDO_SHIFTED;
            }

            newMap = new Map(w, h);
            newMap.addLayer();
            newMap.setTileWidth(twidth);
            newMap.setTileHeight(theight);
            newMap.setOrientation(orientation);

            // Save dialog options

            TiledConfiguration conf = TiledConfiguration.getInstance();
            conf.addConfigPair("tiled.newmapdialog.mapwidth", Integer.toString(mapWidth.intValue()));
            conf.addConfigPair("tiled.newmapdialog.mapheight", Integer.toString(mapHeight.intValue()));
            conf.addConfigPair("tiled.newmapdialog.tilewidth", Integer.toString(tileWidth.intValue()));
            conf.addConfigPair("tiled.newmapdialog.tileheight", Integer.toString(tileHeight.intValue()));

            conf.flush();
        }
        dispose();
    }
}