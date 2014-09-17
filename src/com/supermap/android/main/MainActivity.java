package com.supermap.android.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.supermap.android.app.MyApplication;
import com.supermap.android.theme.BarTheme;
import com.supermap.android.theme.PieTheme;
import com.supermap.data.Color;
import com.supermap.data.ColorGradientType;
import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.Environment;
import com.supermap.data.GeoStyle;
import com.supermap.data.Recordset;
import com.supermap.data.TextStyle;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.dyn.DynamicView;
import com.supermap.mapping.Layer;
import com.supermap.mapping.Layers;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;
import com.supermap.mapping.ThemeLabel;
import com.supermap.mapping.ThemeLabelItem;
import com.supermap.mapping.ThemeRange;
import com.supermap.mapping.ThemeRangeItem;
import com.supermap.mapping.ThemeUnique;
import com.supermap.mapping.ThemeUniqueItem;
import com.supermap.themedemo.R;
/**
 * <p>
 * Title:ר��ͼ
 * </p>
 * 
 * <p>
 * Description:
 * ============================================================================>
 * ------------------------------��Ȩ����----------------------------
 * ���ļ�Ϊ SuperMap iMobile ��ʾDemo�Ĵ��� 
 * ��Ȩ���У�������ͼ����ɷ����޹�˾
 * ----------------------------------------------------------------
 * ----------------------------SuperMap iMobile ��ʾDemo˵��---------------------------
 * 
 * 1��Demo��飺
 *   չʾ��ǩר��ͼ���ֶ���ɫר��ͼ����ֵר��ͼ����״ͼ����״ͼ��������
 * 2��Demo���ݣ�����Ŀ¼��"/SuperMap/Demos/Data/ThemeData/Statistics.smwu"
 *           ��ͼ���ݣ�"Statistics.smwu", "Statistics.udb", "Statistics.udd"
 *           ���Ŀ¼��"/SuperMap/License/"
 * 3���ؼ�����/��Ա: 
 *		ThemeLabel.setLabelExpression();     ����
 *		ThemeLabel.setRangeExpression();     ����
 *		ThemeLabel.addToHead(); 		                 ����
 *		ThemeLabelItem.setStyle();	                               ����
 *		ThemeLabelItem.setVisible();         ����
 *
 *      ThemeRange.addToTail();              ����
 *		ThemeRangeItem.setCaption();	                ����
 *      ThemeRangeItem.setEnd();             ����
 *      ThemeRangeItem.setStart();           ����
 *      ThemeRangeItem.setVisible();         ����
 *      ThemeRangeItem.setStyle();           ����
 *      
 *      DynPieChart.addChartData();          ����
 *      DynPieChart.setChartTitle();         ����
 *      DynPieChart.setAxesColor();          ����
 *      DynPieChart.setShowLegend();         ����
 *      DynPieChart.setShowLabels();         ����
 *      DynPieChart.addPoint();              ����
 *      DynPieChart.setStyle();              ����
 *
 *		DynBarChart.setChartTile();          ����
 *		DynBarChart.setAxesColor();          ����
 *		DynBarChart.setChartSize();          ����
 *		DynBarChart.setShowGrid();           ����
 *
 * 4������չʾ
 *   ��ǩר��ͼ���ֶ���ɫר��ͼ����ֵר��ͼ����״ͼ����״ͼ��
 * ------------------------------------------------------------------------------
 * ============================================================================>
 * </p> 
 * 
 * <p>
 * Company: ������ͼ����ɷ����޹�˾
 * </p>
 * 
 */
public class MainActivity extends Activity {
	
	// ���尴ť�ؼ�
	private ImageButton btn_label    = null;
	private ImageButton btn_range    = null;
	private ImageButton btn_unique   = null;
	private ImageButton btn_pie      = null;
	private ImageButton btn_bar      = null;
	private ImageButton btn_clear    = null;
	private ImageButton btn_entire   = null;
	private ImageButton btn_zoomOut  = null;
	private ImageButton btn_zoomIn   = null;
    private TextView    tv_chartName = null;
    
	// �����ͼ�ؼ�
	Workspace     workspace  = null;
	MapView       mapView    = null;
	MapControl    mapControl = null;
	private DynamicView m_DynamicLayer     = null;	
	private Layer       m_UnifiedMapLayer  = null;
	private Layer       m_RangeLayer       = null;
	private Layer       m_UniqueLayer      = null;
	
	// ���岼������
	boolean isPieCreated    = false;
	boolean isBarCreated    = false;
	boolean isExitEnable     = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		System.out.println("onCreate");
        //��onCreate�е��ó�ʼ������������������ܲ�������
        Environment.initialization(this);
		setContentView(R.layout.main);
			  	
		openWorkspace();
        
		// ��ȡ����Ӷ�̬�㵽��ͼ
		m_DynamicLayer  = new DynamicView(this, mapView.getMapControl().getMap());
		mapView.addDynamicView(m_DynamicLayer);
		
		initView();
	}
	
	/**
	 * ��ʼ���ؼ����󶨼�����
	 */
	private void initView(){
		btn_label = (ImageButton) findViewById(R.id.btn_label);
		btn_label.setOnClickListener(new ButtonOnClickListener());
		
		btn_range = (ImageButton) findViewById(R.id.btn_range);
		btn_range.setOnClickListener(new ButtonOnClickListener());
		
		btn_unique = (ImageButton) findViewById(R.id.btn_unique);
		btn_unique.setOnClickListener(new ButtonOnClickListener());
		
		btn_pie = (ImageButton) findViewById(R.id.btn_pie);
		btn_pie.setOnClickListener(new ButtonOnClickListener());
		
		btn_bar = (ImageButton) findViewById(R.id.btn_bar);
		btn_bar.setOnClickListener(new ButtonOnClickListener());
		
		btn_clear = (ImageButton) findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(new ButtonOnClickListener());
		
		btn_entire = (ImageButton) findViewById(R.id.btn_entire);
		btn_entire.setOnClickListener(new ButtonOnClickListener());
		
		btn_zoomOut = (ImageButton) findViewById(R.id.btn_zoomOut);
		btn_zoomOut.setOnClickListener(new ButtonOnClickListener());
		
		btn_zoomIn = (ImageButton) findViewById(R.id.btn_zoomIn);
		btn_zoomIn.setOnClickListener(new ButtonOnClickListener());
		
		tv_chartName = (TextView) findViewById(R.id.chartName);
		 
	}
	
	/**
	 * ��ť����¼�������
	 *
	 */
    class ButtonOnClickListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_label:
				showUnifiedMap();	               // ��ʾͳһ����ǩר��ͼ				
				break;
				
			case R.id.btn_range:
				showThemeRangeMap();               // ��ʾ�ֶ���ɫר��ͼ				
				break;
				
			case R.id.btn_unique:
				showThemeUniqueMap();              // ��ʾ��ֵר��ͼ				
				break;
				
			case R.id.btn_pie:
				showPieTheme();                    // ��ʾ��ͼ				
				break;
				
			case R.id.btn_bar:
				showBarTheme();                    // ��ʾ��״ͼ				
				break;
				
			case R.id.btn_clear:
				clearTheme();                      // ���ר��ͼ								
				break;
				
			case R.id.btn_entire:
				mapControl.getMap().viewEntire();
				m_DynamicLayer.refresh();
				mapControl.getMap().refresh();
				break;
				
			case R.id.btn_zoomOut:
				mapControl.getMap().zoom(0.5);
				m_DynamicLayer.refresh();
				mapControl.getMap().refresh();
				break;
				
			case R.id.btn_zoomIn:
				mapControl.getMap().zoom(2);
				m_DynamicLayer.refresh();
				mapControl.getMap().refresh();
				break;

			default:
				break;
			}
			
		}
	}
    
    /**
     * �򿪹����ռ�
     */
	private void openWorkspace() {
		
		mapView = (MapView) findViewById(R.id.mapView);
		mapControl = mapView.getMapControl();
		
		workspace = MyApplication.getInstance().getOpenedWorkspace();
		mapControl.getMap().setWorkspace(workspace);		
		mapControl.getMap().open(workspace.getMaps().get(0));
		mapControl.getMap().zoom(2);
		mapControl.getMap().getLayers().get("Provinces_R@Data#1").setVisible(false);
	}
	
	private void showInfo(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	
	/**
	 * ��ʾͳһ����ǩר��ͼ
	 */
	private void showUnifiedMap() {
		m_DynamicLayer.clear();                           // ����������ܴ��ڵı�ͼ����״ͼ����ֹ����
		m_DynamicLayer.refresh();
		if (m_UnifiedMapLayer == null) {
			creatUnifiedMap();                             // ����ͳһ����ǩר��ͼ
			mapControl.getMap().refresh();
		} else {
			if (!(m_UnifiedMapLayer.isVisible())) {
				m_UnifiedMapLayer.setVisible(true);
				mapControl.getMap().refresh();
			}
		}
		
	}
	
	/**
	 * ����ͳһ����ǩר��ͼ
	 */
	private void creatUnifiedMap() {
        ThemeLabel themeLabelMap = new ThemeLabel();
        themeLabelMap.setLabelExpression("NAME");
        themeLabelMap.setRangeExpression("id");

        // Ϊ��ǩר��ͼ�ı�ǩ����ͳһ��ʽ
        ThemeLabelItem themeLabelItem1 = new ThemeLabelItem();
        themeLabelItem1.setVisible(true);
        TextStyle textStyle1 = new TextStyle();
        textStyle1.setForeColor(new Color(0, 10, 10));
        textStyle1.setFontName("����");
        themeLabelItem1.setStyle(textStyle1);

        // ��ӱ�ǩר��ͼ�����ǩר��ͼ������
        themeLabelMap.addToHead(themeLabelItem1);

        Dataset dataset = workspace.getDatasources().get(0).getDatasets().get(0);
        if (dataset != null) {
        	m_UnifiedMapLayer = mapControl.getMap().getLayers().add(dataset,themeLabelMap, true);
		}
	}
	
	/**
	 * ��ʾ��ֵר��ͼ
	 */
	private void showThemeUniqueMap() { 
		m_DynamicLayer.clear();                        // ����������ܴ��ڵı�ͼ����״ͼ����ֹ����
		m_DynamicLayer.refresh();
		// �ȹرտ�������ʾ�ķֶ���ɫר��ͼ
		if(m_RangeLayer != null && m_RangeLayer.isVisible())
			m_RangeLayer.setVisible(false);
		
		if(m_UniqueLayer == null){
		    creatThemeUniqueMap();                      // ������ֵר��ͼ
		    
		}else {
			if (!(m_UniqueLayer.isVisible())) {
				m_UniqueLayer.setVisible(true);
				mapControl.getMap().refresh();
			}
		}
    }
	
	/**
	 * ���쵥ֵר��ͼ
	 */
	public void creatThemeUniqueMap() {
		ThemeUnique themeUnique = new ThemeUnique();

		// ���õ���ר��ͼ��ӵ���ͼ
		Dataset dataset = workspace.getDatasources().get(0).getDatasets()
				.get("Provinces_R");
		if(dataset == null){
			
			return;
		}
		
		// �������ݼ��е�ID��������ֵר��ͼ
		themeUnique = ThemeUnique.makeDefault((DatasetVector) dataset, "ID",
				ColorGradientType.TERRAIN);

		// ���ø���������ʾ���
		int nCount = themeUnique.getCount();
		for (int i = 0; i < nCount; i++) {
			ThemeUniqueItem Item = themeUnique.getItem(i);
			Item.getStyle().setLineColor(new Color(100, 100, 100));
		}

		m_UniqueLayer = mapControl.getMap().getLayers().add(dataset, themeUnique, true);
		tv_chartName.setText("ȫ����������ֵר��ͼ");
		mapControl.getMap().refresh();
	}
	
	/**
	 * ��ʾ�ֶ���ɫר��ͼ
	 */
	public void showThemeRangeMap(){
		m_DynamicLayer.clear();                         // ����������ܴ��ڵı�ͼ����״ͼ����ֹ����
		m_DynamicLayer.refresh();
		// �ȹرտ����Ѿ���ʾ�ĵ�ֵר��ͼ
		if(m_UniqueLayer != null && m_UniqueLayer.isVisible())
			m_UniqueLayer.setVisible(false);
		
		// �ֶ���ɫר��ͼֻ����һ��,֮��ֻ��������ʾ���
		if(m_RangeLayer == null){
		    creatThemeRangeMap();                        // �����ֶ���ɫר��ͼ
		   
		}else {
			if (!(m_RangeLayer.isVisible())) {
				m_RangeLayer.setVisible(true);
				mapControl.getMap().refresh();
			}
		}
	}
	   
	/**
	 * �����ֶ�ר��ͼ
	 */
	public void creatThemeRangeMap() {
		
		Dataset dataset = workspace.getDatasources().get(0).getDatasets()
				.get("Provinces_R");
		
		if(dataset == null){
			return;
		}
		
		// ����2000���GDP("GDP_2000")�����÷ֶ�ר��ͼ
		tv_chartName.setText("2000���ʡ�ݵ�GDP�ֲ�");
		ThemeRange themeRangeMap = new ThemeRange();
		themeRangeMap.setRangeExpression("GDP_2000");      // ���÷ֶ�ר��ͼ���ʽ�ֶ�

		// �����ʽ����
		GeoStyle geoStyle = new GeoStyle();
		geoStyle.setLineColor(new Color(255, 255, 255));
		geoStyle.setLineWidth(0.3);

		// GDPС������ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem1 = new ThemeRangeItem();
		themeRangeItem1.setCaption("GDPС�������");
		themeRangeItem1.setEnd(500);
		themeRangeItem1.setStart(0);
		themeRangeItem1.setVisible(true);
		geoStyle.setFillForeColor(new Color(209, 182, 210));
		themeRangeItem1.setStyle(geoStyle);

		// GDPС��һǧ�ڴ�������ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem2 = new ThemeRangeItem();
		themeRangeItem2.setCaption("GDPС��һǧ�ڴ��������");
		themeRangeItem2.setEnd(1000);
		themeRangeItem2.setStart(500);
		themeRangeItem2.setVisible(true);
		geoStyle.setFillForeColor(new Color(205, 167, 183));
		themeRangeItem2.setStyle(geoStyle);

		// GDPС��һǧ���ڴ���һǧ�ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem3 = new ThemeRangeItem();
		themeRangeItem3.setCaption("GDPС��һǧ���ڴ���һǧ��");
		themeRangeItem3.setEnd(1500);
		themeRangeItem3.setStart(1000);
		themeRangeItem3.setVisible(true);
		geoStyle.setFillForeColor(new Color(183, 128, 151));
		themeRangeItem3.setStyle(geoStyle);

		// GDPС�ڶ�ǧ �ڴ���һǧ���ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem4 = new ThemeRangeItem();
		themeRangeItem4.setCaption("GDPС�ڶ�ǧ�ڴ���һǧ����");
		themeRangeItem4.setEnd(2000);
		themeRangeItem4.setStart(1500);
		themeRangeItem4.setVisible(true);
		geoStyle.setFillForeColor(new Color(164, 97, 136));
		themeRangeItem4.setStyle(geoStyle);

		// GDPС�ڶ�ǧ���� ���ڶ�ǧ�ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem5 = new ThemeRangeItem();
		themeRangeItem5.setCaption("GDPС�ڶ�ǧ���� ���ڶ�ǧ��");
		themeRangeItem5.setEnd(2500);
		themeRangeItem5.setStart(2000);
		themeRangeItem5.setVisible(true);
		geoStyle.setFillForeColor(new Color(94, 53, 77));
		themeRangeItem5.setStyle(geoStyle);

		// GDPС����ǧ �ڴ��ڶ�ǧ���ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem6 = new ThemeRangeItem();
		themeRangeItem6.setCaption("GDPС����ǧ �ڴ��ڶ�ǧ����");
		themeRangeItem6.setEnd(3000);
		themeRangeItem6.setStart(2500);
		themeRangeItem6.setVisible(true);
		geoStyle.setFillForeColor(new Color(255, 0, 0));
		themeRangeItem6.setStyle(geoStyle);

		// GDPС����ǧ�� �ڴ�����ǧ�ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem7 = new ThemeRangeItem();
		themeRangeItem7.setCaption("GDPС����ǧ�� �ڴ�����ǧ��");
		themeRangeItem7.setEnd(3500);
		themeRangeItem7.setStart(3000);
		themeRangeItem7.setVisible(true);
		geoStyle.setFillForeColor(new Color(0, 183, 239));
		themeRangeItem7.setStyle(geoStyle);

		// GDPС����ǧ �ڴ�����ǧ���ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem8 = new ThemeRangeItem();
		themeRangeItem8.setCaption("GDPС����ǧ �ڴ�����ǧ����");
		themeRangeItem8.setEnd(4000);
		themeRangeItem8.setStart(3500);
		themeRangeItem8.setVisible(true);
		geoStyle.setFillForeColor(new Color(244, 219, 24));
		themeRangeItem8.setStyle(geoStyle);

		// GDPС����ǧ���� ������ǧ�ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem9 = new ThemeRangeItem();
		themeRangeItem9.setCaption("GDPС����ǧ���� ������ǧ��");
		themeRangeItem9.setEnd(4500);
		themeRangeItem9.setStart(4000);
		themeRangeItem9.setVisible(true);
		geoStyle.setFillForeColor(new Color(26, 168, 38));
		themeRangeItem9.setStyle(geoStyle);

		// GDPС����ǧ �ڴ�����ǧ���ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem10 = new ThemeRangeItem();
		themeRangeItem10.setCaption("GDPС����ǧ �ڴ�����ǧ����");
		themeRangeItem10.setEnd(5000);
		themeRangeItem10.setStart(4500);
		themeRangeItem10.setVisible(true);
		geoStyle.setFillForeColor(new Color(115, 35, 175));
		themeRangeItem10.setStyle(geoStyle);

		// GDP������ǧ�ڵķֶ�ר��ͼ���������
		ThemeRangeItem themeRangeItem11 = new ThemeRangeItem();
		themeRangeItem11.setCaption("GDP������ǧ��");
		themeRangeItem11.setEnd(Double.MAX_VALUE);
		themeRangeItem11.setStart(5000);
		themeRangeItem11.setVisible(true);
		geoStyle.setFillForeColor(new Color(56, 98, 183));
		themeRangeItem11.setStyle(geoStyle);

		// ���ר��ͼ����ֶ�ר��ͼ������
		themeRangeMap.addToHead(themeRangeItem1);
		themeRangeMap.addToTail(themeRangeItem2);
		themeRangeMap.addToTail(themeRangeItem3);
		themeRangeMap.addToTail(themeRangeItem4);
		themeRangeMap.addToTail(themeRangeItem5);
		themeRangeMap.addToTail(themeRangeItem6);
		themeRangeMap.addToTail(themeRangeItem7);
		themeRangeMap.addToTail(themeRangeItem8);
		themeRangeMap.addToTail(themeRangeItem9);
		themeRangeMap.addToTail(themeRangeItem10);
		themeRangeMap.addToTail(themeRangeItem11);

		// ��ʾ
		m_RangeLayer = mapControl.getMap().getLayers()
				.add(dataset, themeRangeMap, true);
		mapControl.getMap().refresh();
	}
	
	/**
	 * ��ʾ��ͼ
	 */
    public void showPieTheme() {
    	    m_DynamicLayer.clear();            // ����������ܴ��ڵı�ͼ����״ͼ����ֹ����
			Datasource datasource = workspace.getDatasources().get(0);
			Dataset dataset = datasource.getDatasets().get(0);
			DatasetVector datasetVector = (DatasetVector) dataset;

			Recordset recordset = datasetVector.getRecordset(false,
					CursorType.STATIC);
			PieTheme popAgeTheme = new PieTheme(m_DynamicLayer,
					recordset);
			popAgeTheme.creat(60,60);

			m_DynamicLayer.refresh();
    }
    
    /**
     * ��ʾ��״ͼ
     */
    public void showBarTheme() {
    	    m_DynamicLayer.clear();            // ����������ܴ��ڵı�ͼ����״ͼ����ֹ����
			Datasource datasource3 = workspace.getDatasources().get(0);
			Dataset dataset3 = datasource3.getDatasets().get(0);
			DatasetVector datasetVector3 = (DatasetVector) dataset3;

			Recordset recordset3 = datasetVector3.getRecordset(false,
					CursorType.STATIC);
			BarTheme barTheme = new BarTheme(m_DynamicLayer, recordset3);
			barTheme.creat(100, 80);

			m_DynamicLayer.refresh();
    }
    
    /**
     * ���ר��ͼ
     */
    public void clearTheme() {
    	
		isBarCreated = false;
		isPieCreated = false;
		
		Layers m_Layers = mapControl.getMap().getLayers();
		
		// �����ǩ���ֶΡ���ֵר��ͼ
		if(m_UnifiedMapLayer !=null)
		m_Layers.remove(m_UnifiedMapLayer.getName());
		if(m_RangeLayer !=null)
		m_Layers.remove(m_RangeLayer.getName());
		if(m_UniqueLayer !=null)
		m_Layers.remove(m_UniqueLayer.getName());
		
		m_UnifiedMapLayer  = null;
		m_RangeLayer       = null;
		m_UniqueLayer      = null;
		// ��ն�̬��, �������ͼ����״ͼ
    	if(m_DynamicLayer != null ){
		    m_DynamicLayer.clear();
		    m_DynamicLayer.refresh();
    	}
		mapControl.getMap().refresh();
		
		
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(!isExitEnable){
				Toast.makeText(this, "�ٰ�һ���˳�����", 1500).show();
				isExitEnable = true;
			}else{
				mapControl.getMap().close();
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
