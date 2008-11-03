/*
 * ParallaxEditorPanel.java
 *
 * Created on 31 October 2008, 16:13
 */

package tiled.mapeditor.widget;

import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import tiled.core.Map;
import tiled.core.MapChangeListener;
import tiled.core.MapChangedEvent;
import tiled.core.MapLayer;
import tiled.core.MapParallaxChangeEvent;
import tiled.core.MapParallaxChangeListener;
import tiled.core.TileSet;
import tiled.mapeditor.MapEditor;

/**
 *
 * @author  upachler
 */
public class ParallaxEditorPanel extends javax.swing.JPanel {
	private Integer[] minRanges = {-1000, -500, -200, -100, -50, -20, -10, 0};
	private Integer[] maxRanges = {10, 20, 50, 100, 200, 500, 1000};
	
	private MapEditor editor;
	private Map currentMap;
	private MapChangeListener mapChangeListener = new MapChangeListener() {

		public void layerAdded(MapChangedEvent e) {
			int layerIndex = e.getLayerIndex();
			MapLayer layer = currentMap.getLayer(layerIndex);
			
			LayerParallaxDistancePanel p = new LayerParallaxDistancePanel(layer, editor.getUndoSupport());
			layerEditPanel.add(p, layerIndex);
		}

		public void layerRemoved(MapChangedEvent e) {
			layerEditPanel.remove(e.getLayerIndex());
		}
		
		public void layerMoved(MapChangedEvent e){
			LayerParallaxDistancePanel p = (LayerParallaxDistancePanel)layerEditPanel.getComponent(e.getOldLayerIndex());
			layerEditPanel.remove(e.getOldLayerIndex());
			layerEditPanel.add(p, e.getLayerIndex());
		}
		
		// empty methods
		public void mapChanged(MapChangedEvent e) {}
		public void tilesetAdded(MapChangedEvent e, TileSet tileset) {}
		public void tilesetRemoved(MapChangedEvent e, int index) {}
		public void tilesetsSwapped(MapChangedEvent e, int index0, int index1) {}
	};
	
	private MapParallaxChangeListener mapParallaxChangeListener = new MapParallaxChangeListener() {
		public void parallaxParameterChanged(MapParallaxChangeEvent e) {
			if(e.getChangeType() != MapParallaxChangeEvent.ChangeType.EYE_VIEWPLANE_DISTANCE)
				return;
			eyeViewplaneDistanceTextField.setText(Float.toString(e.getMap().getEyeDistance()));
		}
	};
    /** Creates new form ParallaxEditorPanel */
    public ParallaxEditorPanel(MapEditor editor) {
        initComponents();
		this.editor = editor;
		minRangeComboBox.setModel(new DefaultComboBoxModel(minRanges));
		maxRangeComboBox.setModel(new DefaultComboBoxModel(maxRanges));
		
		setCurrentMap(editor.getCurrentMap());
        updateUIState();
    }

	private int lower_bound(Integer[] sortedRange, int value) {
		for(int i=0; i<sortedRange.length; ++i){
			if(!(sortedRange[i]<value))
				return i;
		}
		return sortedRange.length;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        eyeViewplaneDistanceTextField = new javax.swing.JTextField();
        maxRangeComboBox = new javax.swing.JComboBox();
        minRangeComboBox = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        layerEditPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("min:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 1, 1);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setText("max:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 1, 1);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setText("eye-viewplane distance:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 1, 1);
        jPanel2.add(jLabel6, gridBagConstraints);

        eyeViewplaneDistanceTextField.setDocument(new FloatDocument());
        eyeViewplaneDistanceTextField.setText("1000");
        eyeViewplaneDistanceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eyeViewplaneDistanceTextFieldActionPerformed(evt);
            }
        });
        eyeViewplaneDistanceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                eyeViewplaneDistanceTextFieldFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 1, 1);
        jPanel2.add(eyeViewplaneDistanceTextField, gridBagConstraints);

        maxRangeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxRangeComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(maxRangeComboBox, gridBagConstraints);

        minRangeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minRangeComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(minRangeComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        layerEditPanel.setMinimumSize(new java.awt.Dimension(150, 100));
        layerEditPanel.setLayout(new javax.swing.BoxLayout(layerEditPanel, javax.swing.BoxLayout.Y_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(layerEditPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jPanel4, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel1.setText("Layer-Viewplane distances:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 2, 2);
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void minRangeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minRangeComboBoxActionPerformed
	int min = Integer.parseInt(minRangeComboBox.getSelectedItem().toString());
	int max = Integer.parseInt(maxRangeComboBox.getSelectedItem().toString());
	updateEditRanges(min, max);
}//GEN-LAST:event_minRangeComboBoxActionPerformed

private void maxRangeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxRangeComboBoxActionPerformed
	int min = Integer.parseInt(minRangeComboBox.getSelectedItem().toString());
	int max = Integer.parseInt(maxRangeComboBox.getSelectedItem().toString());
	updateEditRanges(min, max);
}//GEN-LAST:event_maxRangeComboBoxActionPerformed

private void eyeViewplaneDistanceTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eyeViewplaneDistanceTextFieldFocusLost
	updateMapFromUI();
}//GEN-LAST:event_eyeViewplaneDistanceTextFieldFocusLost

private void eyeViewplaneDistanceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eyeViewplaneDistanceTextFieldActionPerformed
	updateMapFromUI();
}//GEN-LAST:event_eyeViewplaneDistanceTextFieldActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField eyeViewplaneDistanceTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel layerEditPanel;
    private javax.swing.JComboBox maxRangeComboBox;
    private javax.swing.JComboBox minRangeComboBox;
    // End of variables declaration//GEN-END:variables

	public void setCurrentMap(Map m) {
		if(currentMap == m)
			return;
		
		if(currentMap!=null){	// unregister from old map
			currentMap.removeMapChangeListener(mapChangeListener);			
		}

		currentMap = m;
		
		recreateLayerPanelsFromMap();
		
		int minValue = 0;
		int maxValue = 0;
		if(currentMap!=null){
			m.addMapChangeListener(mapChangeListener);
			m.addMapParallaxChangeListener(mapParallaxChangeListener);
			eyeViewplaneDistanceTextField.setText(Float.toString(currentMap.getEyeDistance()));
			for(MapLayer l : currentMap.getLayerVector()){
//				if(l.isViewPlaneInfinitelyFarAway())
//					continue;
				int d = 0;
//				d = l.getDistanceFromViewPlane();
				minValue = java.lang.Math.min(minValue, d);
				maxValue = java.lang.Math.max(maxValue, d);
			}
		}
		int minRangeIndex = lower_bound(minRanges, minValue);
		int maxRangeIndex = lower_bound(maxRanges, maxValue);
		if(minRanges[minRangeIndex] == maxRanges[maxRangeIndex])
			++maxRangeIndex;
		minRangeComboBox.setSelectedIndex(minRangeIndex);
		maxRangeComboBox.setSelectedIndex(maxRangeIndex);
		updateEditRanges(minRanges[minRangeIndex], maxRanges[maxRangeIndex]);
		
        updateUIState();
	}

	private void updateEditRanges(int min, int max) {
		for(Component c : layerEditPanel.getComponents()){
			try{
				((LayerParallaxDistancePanel)c).setEditRange(min, max);
			}catch(ClassCastException ccx){
			}
		}
	}

	private void updateMapFromUI() {
		float eyeDistance = 0.0f;
		try {
			eyeDistance = Float.parseFloat(eyeViewplaneDistanceTextField.getText());
		} catch(NumberFormatException nfx){
		}
		currentMap.setEyeDistance(eyeDistance);
	}

	private void updateUIState(){
		boolean enabled = currentMap != null;
		minRangeComboBox.setEnabled(enabled);
		maxRangeComboBox.setEnabled(enabled);
		eyeViewplaneDistanceTextField.setEnabled(enabled);
	}
	
	private void recreateLayerPanelsFromMap(){
		layerEditPanel.removeAll();
		if(currentMap == null)
			return;
		
		for(MapLayer l : currentMap.getLayerVector())
		{
			LayerParallaxDistancePanel p = new LayerParallaxDistancePanel(l, editor.getUndoSupport());
			layerEditPanel.add(p);
		}
	}
}
