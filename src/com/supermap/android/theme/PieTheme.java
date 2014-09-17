package com.supermap.android.theme;


import android.graphics.Color;
import com.supermap.data.GeoRegion;
import com.supermap.data.Recordset;
import com.supermap.mapping.dyn.DynPieChart;
import com.supermap.mapping.dyn.DynamicStyle;
import com.supermap.mapping.dyn.DynamicView;

// ��״ͼ��
public class PieTheme {

	Recordset mRecordset;
	DynamicView mDynamicLayer;
	
	/**
	 * ���캯��
	 * @param dynamicLayer
	 * @param recordset
	 */
	public PieTheme(DynamicView dynamicLayer, Recordset recordset) {
		init(dynamicLayer, recordset);
	}
	
	private void init(DynamicView dynamicLayer, Recordset recordset) {
		mDynamicLayer = dynamicLayer;
		mRecordset = recordset;
	}
	
	/**
	 * ����Ĭ�ϳ���ı�ͼ
	 */
	public void creat() {
		double pop_0_14 = mRecordset.getDouble("POP_0_14");
		double pop_15_64 = mRecordset.getDouble("POP_15_64");
		double pop_65 = mRecordset.getDouble("POP_65PLUS");
		
		String name = mRecordset.getString("NAME");
		
		int color1 = Color.rgb(155, 187, 89);
		int color2 = Color.rgb(79, 129, 189);
		int color3 = Color.rgb(192, 80, 77);
		
		String[] tiles = {"<15", "15-65", ">65"};
		double[] pieValues = {pop_0_14, (int)pop_15_64/3, pop_65};
		int[] pieColors = {color2, color1,color3};
		
		DynPieChart pieChart = new DynPieChart();
		pieChart.setChartSize(300, 300);
		
		pieChart.addChartData(tiles, pieValues, pieColors);
		pieChart.setChartTile("2012��"+name+"ȫʡ����ṹ", Color.BLACK, 20);
		pieChart.setAxesColor(Color.BLACK);
		pieChart.setShowLegend(true);
		pieChart.setShowLabels(true);
		
		GeoRegion region= (GeoRegion)mRecordset.getGeometry();
		pieChart.addPoint(region.getInnerPoint());
		DynamicStyle style = new DynamicStyle();
		style.setBackColor(Color.WHITE);
		style.setAlpha(220);
		pieChart.setStyle(style);
		mDynamicLayer.addElement(pieChart);
		mDynamicLayer.refresh();
	}
	
	/**
	 * ����ֱ�ӳ���ı�ͼ
	 * @param width
	 * @param height
	 */
	public void creat(int width, int height) {
		while (!mRecordset.isEOF()) {
			double pop_0_14 = mRecordset.getDouble("POP_0_14");
			double pop_15_64 = mRecordset.getDouble("POP_15_64");
			double pop_65 = mRecordset.getDouble("POP_65PLUS");
			
			int color1 = Color.rgb(155, 187, 89);
			int color2 = Color.rgb(79, 129, 189);
			int color3 = Color.rgb(192, 80, 77);
			
			String[] tiles = {"<15", "15-65", ">65"};
			double[] pieValues = {pop_0_14, pop_15_64/3, pop_65};
			int[] pieColors = {color2, color1, color3};
			
			DynPieChart pieChart = new DynPieChart();
			pieChart.setChartSize(width, height);
			
			pieChart.addChartData(tiles, pieValues, pieColors);
		
			pieChart.setChartTile("2012��ȫʡ����ṹ", Color.argb(0, 0, 0, 0), 20);
			pieChart.setShowLegend(false);
			pieChart.setShowLabels(false);
			
			GeoRegion region= (GeoRegion)mRecordset.getGeometry();
			pieChart.addPoint(region.getInnerPoint());
			DynamicStyle style = new DynamicStyle();
			style.setAlpha(0);
			pieChart.setStyle(style);
			mDynamicLayer.addElement(pieChart);

			mRecordset.moveNext();
		}
		
		mDynamicLayer.refresh();
	}
}
