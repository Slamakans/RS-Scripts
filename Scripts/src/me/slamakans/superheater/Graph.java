package me.slamakans.superheater;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Graph {
	public static final List<Integer> data = new ArrayList<Integer>();
	private static long counter = 1;
	private static long rate = 5;
	
	public static void add(int rate){
		if(rate < 250) return;
		data.add(rate);
	}
	
	public static boolean validate(){
		if(data.size() >= 100)
			data.remove(data.get(0));
		return ++counter % rate == 0;
	}
	
	public static void render(Graphics g, int x, int y, int w, int h, float scale){
		if(data.size() == 0) return;
		try{
			int min = Collections.min(data)-15;
			int max = Collections.max(data)+15;
			if(max - min != 0)
				scale = (float) h / (max - min);
			else scale = 1;
			
			float inc = (float) (w+2)/data.size();
			int[] xvalues = new int[data.size() + 2];
			int[] yvalues = new int[data.size() + 2];
			for(int i = 0; i < xvalues.length - 2; i++){
				xvalues[i] = x+(int)(inc*i);
//				System.out.println("x[" + i + "]=" + xvalues[i]);
			}
			
			for(int i = 0; i < yvalues.length - 2; i++){
				yvalues[i] =  y-(int) ((data.get(i)-min)*scale);
//				System.out.println("y[" + i + "]=" + yvalues[i]);
			}
			
//			int l = xvalues.length;
//			int[] xx = new int[(l * 2) + 2];
//			for(int i = 0; i < l*2; i++){
//				xx[i] = i < l ? xvalues[i] : xvalues[(i-(l*2)+1)*-1];
//			}
//			
//			int[] yy = new int[(l * 2) + 2];
//			for(int i = 0; i < l*2; i++){
//				yy[i] = i < l ? yvalues[i] : yvalues[(i-(l*2)+1)*-1];
//			}
			
			xvalues[xvalues.length - 2] = x + w;
			xvalues[xvalues.length - 1] = x;
			yvalues[yvalues.length - 2] = y;
			yvalues[yvalues.length - 1] = y;
			
			g.setColor(Color.white);
			g.fillPolygon(xvalues, yvalues, xvalues.length);
			
			g.setColor(Color.green);
			g.drawString("Å‚: " + max, x-60, y-20);
			
			g.setColor(Color.red);
			g.drawString("Å’á: " + min, x-60, y);
			
			g.setColor(Color.black);
			g.drawRect(x, y-h-1, w, h+2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}