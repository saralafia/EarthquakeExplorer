package View;
/*
 * GEOG 258 - Final Project 
 * Earthquake Explorer
 * @author Behzad Vahedi, Sara Lafia
 */
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.net.*;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.LayerGroup;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapRectangleImpl;
import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import Controller.GeoJSONParser;
import Controller.Geocoder;
import Model.BoundingBox;
import Model.Buffer;
import Model.Earthquake;
import Model.OurPoint;
import Controller.Utility;

public class GUI extends JFrame  implements JMapViewerEventListener, MouseListener, MouseMotionListener  
{
	private static final long serialVersionUID = 1L;

	private JMapViewer mapv = null;

	private double cursorX,cursorY;
	private JButton zoomExtent;

	private JMenuBar mainMenuBar;

	private JMenu fileMenu;
	private JMenuItem loadMenuItem; 
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu viewMenu;
	private JMenuItem zoomExtentMenuItem;
	private JMenuItem showLayersMenuItem;

	private JMenu queryMenu;
	private JMenuItem quakeByAttributeMenuItem;
	private JMenuItem getInfoMenuItem;

	private JMenu helpMenu;
	private JMenuItem aboutUsMenuItem;
	private JMenuItem userGuideMenuItem;
	private JPanel panel, layersPanel, queryPanel, summaryPanel, timePanel, bboxPanel, placePanel;
	private JTabbedPane tabbedPane;
	private JRadioButton significantRadioButton, m45RadioButton, m25RadioButton, m1RadioButton, allRadioButton;
	private JButton querySummaryButton, totalSummaryButton, showResultButton, placeQueryButton, bboxQueryButton, showPlaceOnMap, bboxDrawButton;
	private JComboBox<String> timeComboBox;
	private ButtonGroup magButtonGroup;
	private JLabel summaryLabel, querySummaryLabel, totalSummaryLabel, timeLabel,queryLabel, bboxLabel, ulLabel, lrLabel, placeLabel, whithinLabel, placeNameLabel, kmLabel, lrXLabel, ulYLabel, ulXLabel, lrYLabel, drawLabel, reportLabel;
	private JTextField ulTextField, lrTextField, distanceTextField, placeNameTextField,ulXTextField, ulYTextField, lrXTextField, lrYTextField;
	private JTable querySummaryTable, totalSummaryTable;
	private JScrollPane jScrollPane1, jScrollPane2;

	private boolean getInfoSelected, aboutUsSelected, showMarkersSelected, quakesLoaded, quakeFound, bboxDrawButtonSelected;
	private int searchCount = 0;

	private ArrayList<Buffer> markersBuffer = new ArrayList<Buffer>();
	// TODO: should quakes be initialized?
	private ArrayList<Earthquake> quakes;
	private short radioButtonstate, timeComboBoxState, mouseClickedCount;
	private ArrayList<String> queryResultId = new ArrayList<String>();
	private OurPoint[] corners = new OurPoint[2];
	private ArrayList<Earthquake> queryQuakes = new ArrayList<Earthquake>();

	// getter for the map
	public JMapViewer getMapv() {
		return mapv;
	}

	public GUI() throws NumberFormatException, IOException
	{
		super("Earthquake Explorer - Lafia, Vahedi");
		setSize(1200, 840);
		initComponents();
	}

	@SuppressWarnings("serial")
	public void initComponents() throws NumberFormatException, IOException{



		//setPreferredSize();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		panel = new JPanel();
		tabbedPane = new JTabbedPane();
		layersPanel = new JPanel();
		queryPanel = new JPanel();
		summaryPanel = new JPanel();

		timeLabel = new JLabel();
		queryLabel = new JLabel();
		drawLabel = new JLabel();
		timePanel = new JPanel();
		significantRadioButton = new JRadioButton();
		m45RadioButton = new JRadioButton();
		m25RadioButton = new JRadioButton();
		m1RadioButton = new JRadioButton();
		allRadioButton = new JRadioButton();
		showResultButton = new JButton();
		timeComboBox = new JComboBox<>();
		magButtonGroup = new ButtonGroup();

		queryLabel = new JLabel();
		bboxPanel = new JPanel();
		bboxLabel = new JLabel();
		ulLabel = new JLabel();
		ulTextField = new JTextField();
		lrLabel = new JLabel();
		lrTextField = new JTextField();
		bboxQueryButton = new JButton();
		bboxDrawButton = new JButton();
		placePanel = new JPanel();
		placeLabel = new JLabel();
		whithinLabel = new JLabel();
		distanceTextField = new JTextField();
		placeNameLabel = new JLabel();
		placeNameTextField = new JTextField();
		placeQueryButton = new JButton();
		kmLabel = new JLabel();
		reportLabel = new JLabel("");
		ulLabel = new JLabel();
		lrLabel = new JLabel();
		lrXLabel = new JLabel();
		ulYLabel = new JLabel();
		ulXLabel = new JLabel();
		lrYLabel = new JLabel();
		ulXTextField = new JTextField(); 
		ulYTextField = new JTextField();
		lrXTextField = new JTextField();
		lrYTextField = new JTextField();
		showPlaceOnMap = new JButton();
		jScrollPane1 = new JScrollPane();
		jScrollPane2 = new JScrollPane(); 
		querySummaryButton = new JButton();
		querySummaryLabel = new JLabel();
		totalSummaryButton = new JButton();
		totalSummaryLabel = new JLabel();
		summaryLabel = new JLabel();
		querySummaryTable = new JTable();
		totalSummaryTable = new JTable();
		JScrollPane layerScrollPane = new JScrollPane();

		bboxDrawButton.setText("Draw bounding box");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMaximumSize(new java.awt.Dimension(1800, 1430));
		setResizable(false);


		//		panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		//		panel.setMaximumSize(new java.awt.Dimension(1300, 1350));
		//		panel.setPreferredSize(new java.awt.Dimension(1300, 1350));
		panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

		timeLabel.setText("<html><b>Choose Layer Criteria</b><br>First, select a time from the dropdown menu.<br>Then filter your event results from the list of earthquake magnitude ranges.</html>");

		javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
		panel.setLayout(panelLayout);
		panelLayout.setHorizontalGroup(
				panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 765, Short.MAX_VALUE)
				);
		panelLayout.setVerticalGroup(
				panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE)
				);

		tabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		tabbedPane.setPreferredSize(new java.awt.Dimension(450, 1350));

		layersPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		timeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Past Hour", "Past Day", "Past Week", "Past Month" }));
		timeComboBox.setPreferredSize(new java.awt.Dimension(300, 32));

		timePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

		magButtonGroup.add(significantRadioButton);
		significantRadioButton.setText("Significant Earthquakes");

		magButtonGroup.add(m45RadioButton);
		m45RadioButton.setText("M 4.5+ Earthquakes");

		magButtonGroup.add(m25RadioButton);
		m25RadioButton.setText("M 2.5+ Earthquakes");

		magButtonGroup.add(m1RadioButton);
		m1RadioButton.setText("M 1.0+ Earthquakes");

		magButtonGroup.add(allRadioButton);
		allRadioButton.setText("All Earthquakes");

		javax.swing.GroupLayout timePanelLayout = new javax.swing.GroupLayout(timePanel);
		timePanel.setLayout(timePanelLayout);
		timePanelLayout.setHorizontalGroup(
				timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(timePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(significantRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
								.addComponent(m45RadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(m25RadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(m1RadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(allRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
				);
		timePanelLayout.setVerticalGroup(
				timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(timePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(significantRadioButton)
						.addGap(27, 27, 27)
						.addComponent(m45RadioButton)
						.addGap(29, 29, 29)
						.addComponent(m25RadioButton)
						.addGap(30, 30, 30)
						.addComponent(m1RadioButton)
						.addGap(28, 28, 28)
						.addComponent(allRadioButton)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		showResultButton.setText("Show Results");
		showResultButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		showResultButton.setPreferredSize(new java.awt.Dimension(300, 33));


		javax.swing.GroupLayout layersPanelLayout = new javax.swing.GroupLayout(layersPanel);
		layersPanel.setLayout(layersPanelLayout);
		layersPanelLayout.setHorizontalGroup(
				layersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layersPanelLayout.createSequentialGroup()
						.addGroup(layersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layersPanelLayout.createSequentialGroup()
										.addGap(20, 20, 20)
										.addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layersPanelLayout.createSequentialGroup()
										.addGap(69, 69, 69)
										.addGroup(layersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(timeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(timePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(showResultButton, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGroup(layersPanelLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(reportLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(31, Short.MAX_VALUE))
				);
		layersPanelLayout.setVerticalGroup(
				layersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layersPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(timeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(timePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(36, 36, 36)
						.addComponent(showResultButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
						.addComponent(reportLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				);

		tabbedPane.addTab("Layers", layersPanel);

		queryPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		queryLabel.setText("<html><b>Query Your Events</b>" +
				"<br>Search for events by drawing upper left and lower right coordinates on the map"
				+ "or by place name, using a geocoding service and defining a search radius.</html>");
		queryLabel.setPreferredSize(new java.awt.Dimension(300, 250));
		queryLabel.setRequestFocusEnabled(false);

		bboxPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		bboxPanel.setMaximumSize(new java.awt.Dimension(300, 400));
		bboxPanel.setPreferredSize(new java.awt.Dimension(300, 400));

		bboxLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		bboxLabel.setText("By Bounding Box");

		ulLabel.setText("Upper Left Corner");

		lrLabel.setText("Lower Left Corner");

		bboxQueryButton.setText("Go!");

		lrXLabel.setText("Lon:");

		ulYLabel.setText("Lat:");

		ulXLabel.setText("Lon:");

		lrYLabel.setText("Lat:");
		drawLabel.setText("or click on map");

		javax.swing.GroupLayout bboxPanelLayout = new javax.swing.GroupLayout(bboxPanel);
		bboxPanel.setLayout(bboxPanelLayout);
		bboxPanelLayout.setHorizontalGroup(
				bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(bboxPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(bboxPanelLayout.createSequentialGroup()
										.addGroup(bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
														.addComponent(lrLabel)
														.addComponent(ulLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(bboxLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
												.addGroup(bboxPanelLayout.createSequentialGroup()
														.addComponent(ulXLabel)
														.addGap(18, 18, 18)
														.addComponent(ulXTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(ulYLabel)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(ulYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(bboxPanelLayout.createSequentialGroup()
														.addComponent(lrXLabel)
														.addGap(18, 18, 18)
														.addComponent(lrXTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(lrYLabel)
														.addGap(18, 18, 18)
														.addComponent(lrYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(bboxPanelLayout.createSequentialGroup()
										.addComponent(drawLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(bboxQueryButton)
												.addComponent(bboxDrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(38, 38, 38))))
				);
		bboxPanelLayout.setVerticalGroup(
				bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(bboxPanelLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(bboxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(ulLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(ulXLabel)
								.addComponent(ulXTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(ulYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(ulYLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(lrLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(3, 3, 3)
						.addGroup(bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(lrXLabel)
								.addComponent(lrXTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lrYLabel)
								.addComponent(lrYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(bboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(bboxDrawButton)
								.addComponent(drawLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(bboxQueryButton)
						.addGap(52, 52, 52))
				);

		placePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		placePanel.setMaximumSize(new java.awt.Dimension(300, 400));
		placePanel.setPreferredSize(new java.awt.Dimension(300, 400));

		placeLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		placeLabel.setText("By Place");

		whithinLabel.setText("Place Name:");

		placeNameLabel.setText("Search Distance:");


		placeQueryButton.setText("Go!");

		kmLabel.setText("Kilometers");

		showPlaceOnMap.setText("Show on the map");


		javax.swing.GroupLayout placePanelLayout = new javax.swing.GroupLayout(placePanel);
		placePanel.setLayout(placePanelLayout);
		placePanelLayout.setHorizontalGroup(
				placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(placePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(placeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(placePanelLayout.createSequentialGroup()
										.addGap(6, 6, 6)
										.addGroup(placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(placeNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(placePanelLayout.createSequentialGroup()
														.addGroup(placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(placePanelLayout.createSequentialGroup()
																		.addComponent(distanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(kmLabel)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(placeQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(showPlaceOnMap, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGroup(placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(whithinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(placeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
														.addGap(0, 18, Short.MAX_VALUE)))))
						.addContainerGap())
				);
		placePanelLayout.setVerticalGroup(
				placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(placePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(placeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(whithinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(placeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(showPlaceOnMap)
						.addGap(18, 18, 18)
						.addComponent(placeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(placePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(kmLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(distanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(placeQueryButton))
						.addContainerGap(37, Short.MAX_VALUE))
				);

		javax.swing.GroupLayout queryPanelLayout = new javax.swing.GroupLayout(queryPanel);
		queryPanel.setLayout(queryPanelLayout);
		queryPanelLayout.setHorizontalGroup(
				queryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(queryPanelLayout.createSequentialGroup()
						.addGroup(queryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(queryPanelLayout.createSequentialGroup()
										.addGap(20, 20, 20)
										.addComponent(queryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(queryPanelLayout.createSequentialGroup()
										.addGap(25, 25, 25)
										.addGroup(queryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(placePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(bboxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		queryPanelLayout.setVerticalGroup(
				queryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(queryPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(queryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(bboxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(placePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		tabbedPane.addTab("Query", queryPanel);
		summaryPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		querySummaryButton.setText("Show summary");

		totalSummaryButton.setText("Show summary");

		querySummaryLabel.setText("Selected Earthquakes");
		querySummaryLabel.setFont(new java.awt.Font("Tahoma", 1, 18));

		totalSummaryLabel.setText("All Earthquakes");
		totalSummaryLabel.setFont(new java.awt.Font("Tahoma", 1, 18));


		summaryLabel.setText("<html><b>Summary of Search Results</b>" +
				"<br>Attributes of earthquakes in the specified search area for a given time range, along with the attributes"
				+ " of all earthquakes in the time range, are returned for statistical comparison.</html>");
		summaryLabel.setMaximumSize(new java.awt.Dimension(300, 250));
		summaryLabel.setPreferredSize(new java.awt.Dimension(300, 250));

		jScrollPane1.setMaximumSize(new java.awt.Dimension(452, 295));
		jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 295));

		totalSummaryTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		totalSummaryTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
					{"  Max Magnitude", ""},
					{"  Min Magnitude", ""},
					{"  Max Depth", ""},
					{"  Min Depth", ""},
					{"  Most Recent", ""},
					{"  Least Recent", ""}
				},
				new String [] {
						"Attribte", "Value"
				}
				) {
			Class[] types = new Class [] {
					java.lang.String.class, java.lang.String.class
			};
			boolean[] canEdit = new boolean [] {
					false, false
			};

			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		});
		totalSummaryTable.setColumnSelectionAllowed(true);
		totalSummaryTable.setPreferredSize(new java.awt.Dimension(150, 180));
		totalSummaryTable.setRowHeight(30);
		totalSummaryTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane1.setViewportView(totalSummaryTable);
		totalSummaryTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		jScrollPane2.setMaximumSize(new java.awt.Dimension(452, 290));
		jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 290));

		querySummaryTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		querySummaryTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
					{"  Max Magnitude", ""},
					{"  Min Magnitude", ""},
					{"  Max Depth", ""},
					{"  Min Depth", ""},
					{"  Most Recent", ""},
					{"  Least Recent", ""}
				},
				new String [] {
						"Attribte", "Value"
				}
				) {
			Class[] types = new Class [] {
					java.lang.String.class, java.lang.String.class
			};
			boolean[] canEdit = new boolean [] {
					false, false
			};

			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		});
		querySummaryTable.setColumnSelectionAllowed(true);
		querySummaryTable.setRowHeight(30);
		querySummaryTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane2.setViewportView(querySummaryTable);
		querySummaryTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);


		javax.swing.GroupLayout summaryPanelLayout = new javax.swing.GroupLayout(summaryPanel);
		summaryPanel.setLayout(summaryPanelLayout);
		summaryPanelLayout.setHorizontalGroup(
				summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(summaryPanelLayout.createSequentialGroup()
						.addGap(20, 20, 20)
						.addGroup(summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addComponent(querySummaryButton, javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(totalSummaryButton, javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(summaryPanelLayout.createSequentialGroup()
										.addGroup(summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(summaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(totalSummaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(querySummaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap())
				);
		summaryPanelLayout.setVerticalGroup(
				summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(summaryPanelLayout.createSequentialGroup()
						.addGap(25, 25, 25)
						.addComponent(summaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(querySummaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(querySummaryButton))
						.addGap(20, 20, 20)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(43, 43, 43)
						.addGroup(summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(totalSummaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(totalSummaryButton))
						.addGap(20, 20, 20)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(32, Short.MAX_VALUE))
				);

		tabbedPane.addTab("Summary", summaryPanel);

		//mainMenuBar.setPreferredSize(new Dimension(226, 25));


		add(panel);
		add(tabbedPane);

		mapv = new JMapViewer();
		//mapv.setSize(767, 757);
		//mapv.setSize(panel.getSize());
		mapv.addJMVListener(this);
		mapv.addMouseListener(this);
		mapv.addMouseMotionListener(this);

		/*JComboBox<TileSource> tileSourceSelector = new JComboBox<>(new TileSource[] {
				new OsmTileSource.Mapnik(),
				new OsmTileSource.CycleMap(),
				new BingAerialTileSource(),
				new MapQuestOsmTileSource(),
				new MapQuestOpenAerialTileSource() });
		tileSourceSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				mapv.setTileSource((TileSource) e.getItem());
			}
		});*/


		//panel.add(mapv);


		//mapv.setDisplayPosition(new Coordinate(34.046608, -118.257570), 9);

		zoomExtent = new JButton("Zoom to Feature Extent");


		mainMenuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		loadMenuItem = new JMenuItem("Load");
		saveMenuItem = new JMenuItem("Save");
		exitMenuItem = new JMenuItem("Exit");
		viewMenu = new JMenu("View");
		zoomExtentMenuItem = new JMenuItem("Zoom to Extent");
		getInfoMenuItem = new JMenuItem("Get Info");
		helpMenu = new JMenu("Help");
		userGuideMenuItem = new JMenuItem("User Guide");
		aboutUsMenuItem = new JMenuItem("About Us");


		zoomExtentMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mapv.setDisplayToFitMapMarkers();

			}
		});

		mainMenuBar.add(fileMenu);
		mainMenuBar.add(viewMenu);
		mainMenuBar.add(helpMenu);
		fileMenu.add(loadMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		viewMenu.add(zoomExtentMenuItem);
		viewMenu.add(getInfoMenuItem);
		helpMenu.add(userGuideMenuItem);
		helpMenu.add(aboutUsMenuItem);

		mainMenuBar.setPreferredSize(new java.awt.Dimension(226, 25));
		setJMenuBar(mainMenuBar);

		// adding Action Listeners
		zoomExtent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mapv.setDisplayToFitMapMarkers();

			}
		});

		getInfoMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				List<MapMarker> markers = mapv.getMapMarkerList();
				markersBuffer.clear();

				for (int i=0; i < quakes.size(); i++){
					markersBuffer.add(new Buffer(new OurPoint(quakes.get(i).getX(),quakes.get(i).getY()),markers.get(i).getRadius()));
				}

				getInfoSelected = true;
			}
		});

		aboutUsMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = "C:\\Users\\Behzad\\workspace\\finalProjects\\src\\about_us.txt";
				String aboutUsMessage = "";
				String line = null;

				try {
					FileReader fileReader = 
							new FileReader(fileName);
					BufferedReader bufferedReader = 
							new BufferedReader(fileReader);

					while((line = bufferedReader.readLine()) != null) {
						aboutUsMessage += line + "\n";
					}   

					bufferedReader.close();
					fileReader.close();
				}
				catch(FileNotFoundException ex) {
					System.out.println(
							"Unable to open file '" + 
									fileName + "'");                
				}
				catch(IOException ex) {
					System.out.println(
							"Error reading file '" 
									+ fileName + "'");                  
				}


				JOptionPane.showMessageDialog(panel, aboutUsMessage, "About Us", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(
						panel,
						"Are you sure you want to exit the application?",
						"Exit Application",
						JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION)
					setVisible(false);
				dispose();

			}
		});

		userGuideMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = "C:\\Users\\Behzad\\workspace\\finalProjects\\src\\user_guide.txt";
				String aboutUsMessage = "";
				String line = null;

				try {
					FileReader fileReader = 
							new FileReader(fileName);
					BufferedReader bufferedReader = 
							new BufferedReader(fileReader);

					while((line = bufferedReader.readLine()) != null) {
						aboutUsMessage += line + "\n";
					}   

					bufferedReader.close();
					fileReader.close();
				}
				catch(FileNotFoundException ex) {
					System.out.println(
							"Unable to open file '" + 
									fileName + "'");                
				}
				catch(IOException ex) {
					System.out.println(
							"Error reading file '" 
									+ fileName + "'");                  
				}


				JOptionPane.showMessageDialog(panel, aboutUsMessage, "User Guide", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					String sFileName = "C:\\Users\\Behzad\\workspace\\finalProjects\\src\\results.csv";
					FileWriter writer = new FileWriter(sFileName);

					if (quakesLoaded)
					{
						writer.append("ID" + ",");
						writer.append("Place name" + ",");
						writer.append("Location" + ",");
						writer.append("Magnitude"+ ",");
						writer.append("Depth" + ",");
						writer.append("Time" + ",");
						writer.append('\n');
						for (int i=0; i< quakes.size(); i++)
						{
							writer.append(quakes.get(i).getId() + ",");
							writer.append(quakes.get(i).getPlace() + ",");
							writer.append(quakes.get(i).getMagnitude()+ ",");
							writer.append(quakes.get(i).getDepth() + ",");
							java.util.Date time = new java.util.Date((long)quakes.get(i).getTime());
							writer.append(time + ",");
							writer.append('\n');
						}

						writer.flush();
						writer.close();
						JOptionPane.showMessageDialog(panel, "Your data have been saved!");
					}
				}
				catch(IOException error)
				{
					error.printStackTrace();
				} 

			}
		});

		loadMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userLink = JOptionPane.showInputDialog("Enter the link:");
				try{

					GeoJSONParser test = new GeoJSONParser(userLink);
					quakes = GeoJSONParser.getQuakes();

					//ArrayList<Earthquake> earthquakes = test.getQuakes();
					for (int i=0;i<quakes.size();i++) {
						MapMarkerCircle circle = new MapMarkerCircle(new Coordinate(quakes.get(i).getY(), quakes.get(i).getX()), quakes.get(i).getMagnitude()/2);
						circle.setColor(Color.RED);
						mapv.addMapMarker(circle);
						reportLabel.setText(quakes.size() + " earthquakes loaded");

					}

				}
				catch (Exception error2){
					System.out.println(error2.getMessage());
				}

			}
		});
		//showResultButton.addActionListener(this);
		showResultButton.addActionListener(new CustomActionListener());
		showPlaceOnMap.addActionListener(new CustomActionListener());
		placeQueryButton.addActionListener(new CustomActionListener());
		bboxQueryButton.addActionListener(new CustomActionListener());
		bboxDrawButton.addActionListener(new CustomActionListener());
		querySummaryButton.addActionListener(new CustomActionListener());
		totalSummaryButton.addActionListener(new CustomActionListener());


		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
								.addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE))
						.addContainerGap(14, Short.MAX_VALUE))
				);

	}


	public static void main(String[] args) throws IOException{

		GUI viewer = new GUI();
		viewer.setVisible(true);
		viewer.mapv.setSize(viewer.panel.getSize());
		viewer.panel.add(viewer.mapv);
		JOptionPane.showMessageDialog(viewer.panel,  
				"You can view, get info, query, summarize and save near-real time earthquake information." + "\n" +
						"See the Help menu for more detail on how to use this application.", "Welcome to Earthquake Explorer", JOptionPane.INFORMATION_MESSAGE);


	}

	public void createMapMarker() {

		mapv.removeAllMapMarkers();
		//System.out.println(mapv.getMapMarkerList().size());

		String urlString = generateURL(timeComboBoxState,radioButtonstate);
		try{

			GeoJSONParser test = new GeoJSONParser(urlString);
			quakes = GeoJSONParser.getQuakes();

			//ArrayList<Earthquake> earthquakes = test.getQuakes();
			for (int i=0;i<quakes.size();i++) {
				MapMarkerCircle circle = new MapMarkerCircle(new Coordinate(quakes.get(i).getY(), quakes.get(i).getX()), quakes.get(i).getMagnitude()/2);
				circle.setColor(Color.RED);
				mapv.addMapMarker(circle);
				reportLabel.setText(quakes.size() + " earthquakes loaded");

			}

		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}

	}


	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void processCommand(JMVCommandEvent command) {
		if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
				command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if(getInfoSelected){

			Point p = e.getPoint();
			cursorX = mapv.getPosition(p).getLon();
			cursorY = mapv.getPosition(p).getLat();


			for (int i=0; i<markersBuffer.size(); i++)
			{
				if (markersBuffer.get(i).isInside(new OurPoint(cursorX, cursorY)))
				{
					java.util.Date time = new java.util.Date((long)quakes.get(i).getTime());

					//System.out.println("show info! please!");
					JOptionPane.showMessageDialog(this, "Coordinates: " + "(" +quakes.get(i).getX() + " , " + quakes.get(i).getY() + ")" + "\n" +
							"Location: " + quakes.get(i).getPlace() + "\n" +
							"Magnitude: " + quakes.get(i).getMagnitude() + " on the Richter scale" +  "\n" +
							"Depth: " + quakes.get(i).getDepth() + " Kilometers" +  "\n" +
							"Time: " + time
							, "Earthquake Information", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("C:\\Users\\Behzad\\workspace\\finalProjects\\quakeicon.png"));

				}
			}
			getInfoSelected=false;
		}

		else if (bboxDrawButtonSelected)
		{
			// TODO: check the error. It showed up after creating this block
			mouseClickedCount++;

			Point p = e.getPoint();

			// Transferring mouse coordinates from display coordinate system to geographic coordinate system
			cursorX = mapv.getPosition(p).getLon();
			cursorY = mapv.getPosition(p).getLat();


			if (mouseClickedCount%2 == 1) corners[0] = new OurPoint(cursorX,cursorY);

			else
			{ 
				corners[1] = new OurPoint(cursorX,cursorY);
				queryResultId.clear();
				//	queryQuakes.clear();
				quakeFound = false;
				searchCount = 0;

				mapv.addMapRectangle(new MapRectangleImpl(new Coordinate(corners[0].getY(), corners[0].getX()), new Coordinate(corners[1].getY(), corners[1].getX())));
				ulXTextField.setText(String.valueOf(corners[0].getX()));
				ulYTextField.setText(String.valueOf(corners[0].getY()));
				lrXTextField.setText(String.valueOf(corners[1].getX()));
				lrYTextField.setText(String.valueOf(corners[1].getY()));
				if (quakesLoaded)
				{
					BoundingBox userBBOX = new BoundingBox(corners[0], corners[1]);

					for (int i=0; i<quakes.size(); i++)
					{
						if (userBBOX.isInside(quakes.get(i)))
						{
							searchCount ++;
							queryResultId.add(quakes.get(i).getId());
							queryQuakes.add(quakes.get(i));
							quakeFound = true;
						}								
					}
					String message = searchCount + " earthquakes were found in the defined bounding box";
					if (quakeFound) JOptionPane.showMessageDialog(null, message);
					else JOptionPane.showMessageDialog(null, "No earthquake was found in the defined bounding box");


				}
				else JOptionPane.showMessageDialog(null, "There are no earthquakes currently loaded. Load a layer first!");
				mapv.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				mapv.removeAllMapRectangles();
				bboxDrawButtonSelected = false;

			}
		}

	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public String generateURL(short timeState, short magState) {
		String fixedPart = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";
		String appendPart = "";

		if (timeState == 1)
		{
			if (magState == 1) appendPart = "significant_hour";
			else if (magState == 2) appendPart = "4.5_hour";
			else if (magState == 3) appendPart = "2.5_hour";
			else if (magState == 4) appendPart = "1.0_hour";
			else if (magState == 5) appendPart = "all_hour";
		}
		else if (timeState == 2)
		{
			if (magState == 1) appendPart = "significant_day";
			else if (magState == 2) appendPart = "4.5_day";
			else if (magState == 3) appendPart = "2.5_day";
			else if (magState == 4) appendPart = "1.0_day";
			else if (magState == 5) appendPart = "all_day"; 
		}
		else if (timeState == 3)
		{
			if (magState == 1) appendPart = "significant_week";
			else if (magState == 2) appendPart = "4.5_week";
			else if (magState == 3) appendPart = "2.5_week";
			else if (magState == 4) appendPart = "1.0_week";
			else if (magState == 5) appendPart = "all_week";
		}
		else if (timeState == 4)
		{
			if (magState == 1) appendPart = "significant_month";
			else if (magState == 2) appendPart = "4.5_month";
			else if (magState == 3) appendPart = "2.5_month";
			else if (magState == 4) appendPart = "1.0_month";
			else if (magState == 5) appendPart = "all_month";
		}

		return fixedPart.concat(appendPart.concat(".geojson"));

	}


	class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(showResultButton)){

				reportLabel.setText("Loading results. Please wait . . . ");
				getInfoSelected = false;

				for (Enumeration<AbstractButton> buttons = magButtonGroup.getElements(); buttons.hasMoreElements();) 
				{
					AbstractButton button = buttons.nextElement();

					if (button.isSelected()) {
						if (button.getText().equals("Significant Earthquakes")) radioButtonstate = 1;

						else if (button.getText().equals("M 4.5+ Earthquakes")) radioButtonstate = 2;

						else if(button.getText().equals("M 2.5+ Earthquakes")) radioButtonstate = 3;

						else if (button.getText().equals("M 1.0+ Earthquakes")) radioButtonstate = 4;

						else if (button.getText().equals("All Earthquakes")) radioButtonstate = 5;
					}
				}

				//timeComboBoxState;
				String selectedAction = (String)timeComboBox.getSelectedItem();

				if(selectedAction.equals("Past Hour")) timeComboBoxState = 1;

				else if(selectedAction.equals("Past Day")) timeComboBoxState = 2;

				else if(selectedAction.equals("Past Week")) timeComboBoxState = 3;

				else if(selectedAction.equals("Past Month")) timeComboBoxState = 4;


				createMapMarker();
				quakesLoaded = true;
				// TODO: figure out why the below line is not working

			}

			if (e.getSource().equals(showPlaceOnMap))
			{
				String placeName = placeNameTextField.getText();
				if (!placeName.equals(""))
				{
					Geocoder geoCodedPlaceName = new Geocoder(placeName);
					OurPoint userPoint = geoCodedPlaceName.getGeocodedPoint();
					if (!(userPoint.getX() == -1))
					{

						//MapMarkerCircle placeNameCircle = new MapMarkerCircle(new Coordinate(userPoint.getY(), userPoint.getX()), quakes.get(i).getMagnitude()/2);
						mapv.addMapMarker(new MapMarkerDot(placeNameTextField.getText(),new Coordinate(userPoint.getY(), userPoint.getX()) ));
					}else{
						System.out.println("place name is invalid");
						JOptionPane.showMessageDialog(mapv, "place name is invalid");
					}
				}
			}


			if (e.getSource().equals(placeQueryButton)) 
			{
				searchCount = 0;
				queryResultId.clear();
				quakeFound = false;
				queryQuakes.clear();
				//queryQuakes = quakes.clone();
				String placeName = placeNameTextField.getText();
				if (!placeName.equals(""))
				{
					if (!distanceTextField.equals("")){
						Geocoder geoCodedPlaceName = new Geocoder(placeName);
						OurPoint userPoint = geoCodedPlaceName.getGeocodedPoint();
						if (!(userPoint.getX() == -1))
						{

							//MapMarkerCircle placeNameCircle = new MapMarkerCircle(new Coordinate(userPoint.getY(), userPoint.getX()), quakes.get(i).getMagnitude()/2);
							mapv.addMapMarker(new MapMarkerDot(placeNameTextField.getText(),new Coordinate(userPoint.getY(), userPoint.getX()) ));
						}else{
							System.out.println("place name is invalid");
							JOptionPane.showMessageDialog(mapv, "place name is invalid");
						}

						double distance = Double.parseDouble(distanceTextField.getText());
						Buffer searchBuffer = new Buffer(userPoint, distance);
						// TODO: how to handle this problem with try and catch (catch null pointer exception)
						MapMarkerCircle userCircle = new MapMarkerCircle(new Coordinate(userPoint.getY(), userPoint.getX()), distance/111);
						userCircle.setColor(Color.BLUE);
						mapv.addMapMarker(userCircle);
						if (quakesLoaded)
						{
							for (int i=0; i<quakes.size(); i++){
								if (searchBuffer.isInsideGeographic((OurPoint)quakes.get(i))) {
									queryResultId.add(quakes.get(i).getId());
									queryQuakes.add(quakes.get(i));
									quakeFound = true;
									searchCount ++;
								}
							}
							String message = searchCount + " earthquakes were found in your search area";
							if (quakeFound) JOptionPane.showMessageDialog(null, message);
							//System.out.println("quake found!");
							else JOptionPane.showMessageDialog(null, "No earthquake was found in your search area");
							mapv.removeMapMarker(userCircle);
						}
					}
				}
			}

			if (e.getSource().equals(bboxQueryButton))
			{
				searchCount = 0;
				queryResultId.clear();
				quakeFound = false;
				queryQuakes.clear();

				if (!ulXTextField.equals("")){
					if (!ulYTextField.equals("")){
						if(!lrXTextField.equals("")){
							if(!lrYTextField.equals(""))
							{
								if (quakesLoaded)
								{
									// TODO: try catch block here for cases when the value of the textfields is not able to be parsed to double
									//double ulX = Double.parseDouble(ulXTextField.getText());
									OurPoint ul = new OurPoint(Double.parseDouble(ulXTextField.getText()), Double.parseDouble(ulYTextField.getText()));
									OurPoint lr = new OurPoint(Double.parseDouble(lrXTextField.getText()), Double.parseDouble(lrYTextField.getText()));
									BoundingBox userBBOX = new BoundingBox(ul, lr);
									mapv.addMapRectangle(new MapRectangleImpl(new Coordinate(ul.getY(), ul.getX()), new Coordinate(lr.getY(), lr.getX())));

									for (int i=0; i<quakes.size(); i++)
									{
										if (userBBOX.isInside(quakes.get(i)))
										{
											searchCount ++;
											queryResultId.add(quakes.get(i).getId());
											queryQuakes.add(quakes.get(i));
											//mapv.setdis
											quakeFound = true;

										}								
									}
									String message = searchCount + " earthquakes were found in the defined bounding box";
									if (quakeFound) JOptionPane.showMessageDialog(null, message);
									//System.out.println("quake found!");
									else JOptionPane.showMessageDialog(null, "No earthquake was found in the defined bounding box");
									mapv.removeAllMapRectangles();
								}
							}
						}
					}
				}
			}

			if (e.getSource().equals(bboxDrawButton))
			{
				bboxDrawButtonSelected = true;
				mapv.removeAllMapRectangles();
				mapv.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}

			if (e.getSource().equals(querySummaryButton))
			{
				if (quakeFound)
				{
					ArrayList<Earthquake> selectedSortedByMag = (ArrayList<Earthquake>) queryQuakes.clone();
					ArrayList<Earthquake> selectedSortedByDepth = (ArrayList<Earthquake>) queryQuakes.clone();
					ArrayList<Earthquake> selectedSortedByTime = (ArrayList<Earthquake>) queryQuakes.clone();

					// sort ny Magnitude
					Collections.sort(selectedSortedByMag, new Comparator<Earthquake>() {

						@Override
						public int compare(Earthquake o1, Earthquake o2) {
							// TODO Auto-generated method stub
							if (o1.getMagnitude() > o2.getMagnitude()) return 1;
							else if (o1.getMagnitude() < o2.getMagnitude()) return -1;
							else return 0;
						}
					});

					querySummaryTable.setValueAt("  " + selectedSortedByMag.get(selectedSortedByMag.size()-1).getMagnitude(), 0, 1);
					querySummaryTable.setValueAt("  " + selectedSortedByMag.get(0).getMagnitude(), 1, 1);

					// sort by depth
					Collections.sort(selectedSortedByDepth, new Comparator<Earthquake>() {

						@Override
						public int compare(Earthquake o1, Earthquake o2) {
							// TODO Auto-generated method stub
							if (o1.getDepth() > o2.getDepth()) return 1;
							else if (o1.getDepth() < o2.getDepth()) return -1;
							else return 0;
						}
					});

					querySummaryTable.setValueAt("  " + selectedSortedByDepth.get(selectedSortedByDepth.size()-1).getDepth() + " KM", 2, 1);
					querySummaryTable.setValueAt("  " + selectedSortedByDepth.get(0).getDepth() + " KM", 3, 1);

					// sort by time
					Collections.sort(selectedSortedByTime, new Comparator<Earthquake>() {

						@Override
						public int compare(Earthquake o1, Earthquake o2) {
							// TODO Auto-generated method stub
							if (o1.getTime() > o2.getTime()) return 1;
							else if (o1.getTime() < o2.getTime()) return -1;
							else return 0;
						}
					});
					java.util.Date firstTime = new java.util.Date((long)selectedSortedByTime.get(selectedSortedByTime.size()-1).getTime());
					java.util.Date lastTime = new java.util.Date((long)selectedSortedByTime.get(0).getTime());

					querySummaryTable.setValueAt("  " + firstTime, 4, 1);
					querySummaryTable.setValueAt("  " + lastTime, 5, 1);
				}
				else JOptionPane.showMessageDialog(null, "There are no earthquakes found. Please refine your search.");
			}

			if (e.getSource().equals(totalSummaryButton))
			{
				if (quakesLoaded)
				{

					ArrayList<Earthquake> allSortedByMag = (ArrayList<Earthquake>) quakes.clone();
					ArrayList<Earthquake> allSortedByDepth = (ArrayList<Earthquake>) quakes.clone();
					ArrayList<Earthquake> allSortedByTime = (ArrayList<Earthquake>) quakes.clone();

					// sort by magnitude
					Collections.sort(allSortedByMag, new Comparator<Earthquake>() {

						@Override
						public int compare(Earthquake o1, Earthquake o2) {
							// TODO Auto-generated method stub
							if (o1.getMagnitude() > o2.getMagnitude()) return 1;
							else if (o1.getMagnitude() < o2.getMagnitude()) return -1;
							else return 0;
						}
					});

					totalSummaryTable.setValueAt("  " + allSortedByMag.get(allSortedByMag.size()-1).getMagnitude(), 0, 1);
					totalSummaryTable.setValueAt("  " + allSortedByMag.get(0).getMagnitude(), 1, 1);

					// sort by depth
					Collections.sort(allSortedByDepth, new Comparator<Earthquake>() {

						@Override
						public int compare(Earthquake o1, Earthquake o2) {
							// TODO Auto-generated method stub
							if (o1.getDepth() > o2.getDepth()) return 1;
							else if (o1.getDepth() < o2.getDepth()) return -1;
							else return 0;
						}
					});

					totalSummaryTable.setValueAt("  " + allSortedByDepth.get(allSortedByDepth.size()-1).getDepth() + " KM", 2, 1);
					totalSummaryTable.setValueAt("  " + allSortedByDepth.get(0).getDepth() + " KM", 3, 1);


					// sort by time
					Collections.sort(allSortedByTime, new Comparator<Earthquake>() {

						@Override
						public int compare(Earthquake o1, Earthquake o2) {
							// TODO Auto-generated method stub
							if (o1.getTime() > o2.getTime()) return 1;
							else if (o1.getTime() < o2.getTime()) return -1;
							else return 0;
						}
					});
					java.util.Date firstTime = new java.util.Date((long)allSortedByTime.get(allSortedByTime.size()-1).getTime());
					java.util.Date lastTime = new java.util.Date((long)allSortedByTime.get(0).getTime());

					totalSummaryTable.setValueAt("  " + firstTime, 4, 1);
					totalSummaryTable.setValueAt("  " + lastTime, 5, 1);

				}
				else JOptionPane.showMessageDialog(null, "There are no earthquakes loaded. Please select an earthquake layer first.");
			}
		}
	}

}