package com.supermap.android.theme;

import java.math.BigDecimal;

import android.graphics.Color;

import com.supermap.data.GeoRegion;
import com.supermap.data.Recordset;
import com.supermap.mapping.dyn.DynBarChart;
import com.supermap.mapping.dyn.DynamicStyle;
import com.supermap.mapping.dyn.DynamicView;

// ��״ͼ��
public class BarTheme {
	Recordset mRecordset;
	DynamicView mDynamicLayer;
	/**
	 * ���캯��
	 * @param dynamicLayer
	 * @param recordset
	 */
	public BarTheme(DynamicView dynamicLayer, Recordset recordset) {
		init(dynamicLayer, recordset);
	}
	
	private void init(DynamicView dynamicLayer, Recordset recordset) {
		mDynamicLayer = dynamicLayer;
		mRecordset = recordset;
	}
	
	/**
	 * ����Ĭ�ϳߴ����״ͼ
	 */
	public void creat() {
		double n = format(mRecordset.getDouble("N_FERTILIZER"));
		double p = format(mRecordset.getDouble("P_FERTILIZER"));
		double k = format(mRecordset.getDouble("K_FERTILIZER"));
		double compound = format(mRecordset.getDouble("COMPOUNDFERTILIZER"));
		
		String name = mRecordset.getString("NAME");
		
		int color1 = Color.rgb(213, 132, 66);
		int color2 = Color.rgb(190, 81, 76);
		int color3 = Color.rgb(76, 132, 176);
		int color4 = Color.rgb(155,188,88);

		String[] tiles = {"����", "�׷�", "�ط�","���Ϸ�"};
		
		DynBarChart barChart = new DynBarChart();
		barChart.setChartTile("2012��"+name+"����ʹ����", Color.BLACK, 15);
		barChart.setAxesColor(Color.BLACK);
		barChart.setChartSize(400, 400);
		barChart.setShowGrid(false, false);
		
		double[] value1 = {n,0,0,0};
		double[] value2 = {0,p,0,0};
		double[] value3 = {0,0,k,0};
		double[] value4 = {0,0,0,compound};
		
		barChart.addChartData("����", value1, color1, false);
		barChart.addChartData("�׷�", value2, color2, false);
		barChart.addChartData("�ط�", value3, color3, false);
		barChart.addChartData("���Ϸ�", value4, color4, false);
		
		barChart.setYTitle("����ʹ����");
		for(int i=0;i<4;i++){
			barChart.setXAxisLabel(i+1, tiles[i]);
		}
		
		barChart.setBarSpacing(0.0f);
		
		GeoRegion region= (GeoRegion)mRecordset.getGeometry();
		barChart.addPoint(region.getInnerPoint());
		DynamicStyle style = new DynamicStyle();
		style.setBackColor(Color.WHITE);
		style.setAlpha(220);

		barChart.setStyle(style);
		mDynamicLayer.addElement(barChart);
		mDynamicLayer.refresh();
	}
	
	/**
	 * ����ָ���������״ͼ
	 * @param width
	 * @param height
	 */
	public void creat(int width, int height) {
		while (!mRecordset.isEOF()) {
			double n = format(mRecordset.getDouble("N_FERTILIZER"));
			double p = format(mRecordset.getDouble("P_FERTILIZER"));
			double k = format(mRecordset.getDouble("K_FERTILIZER"));
			double compound = format(mRecordset.getDouble("COMPOUNDFERTILIZER"));

			int color = Color.rgb(175, 97, 31);

			String[] tiles = {"����", "�׷�", "�ط�","���Ϸ�"};
		
			DynBarChart barChart = new DynBarChart();

			barChart.setAxesColor(Color.BLACK);
			barChart.setChartSize(width, height);
			barChart.setShowGrid(false, false);
			barChart.setShowLabels(false);
			barChart.setShowLegend(false);
			
			barChart.addChartData("����", new double[]{n,p,k,compound}, color);
			barChart.setYTitle("����ʹ����");
			for(int i=0;i<4;i++){
				barChart.setXAxisLabel(i+1, tiles[i]);
			}

			barChart.setBarSpacing(0.5f);
			
			GeoRegion region= (GeoRegion)mRecordset.getGeometry();
			barChart.addPoint(region.getInnerPoint());
			DynamicStyle style = new DynamicStyle();
			style.setAlpha(0);

			barChart.setStyle(style);
			mDynamicLayer.addElement(barChart);
			
			mRecordset.moveNext();
		}
		
		mDynamicLayer.refresh();
	}
	
	/**
	 * ת������
	 * @param d
	 * @return
	 */
	private double format(double d) {
		BigDecimal b = new BigDecimal(d); 
		double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return result;
	}
}
