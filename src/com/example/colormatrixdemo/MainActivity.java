package com.example.colormatrixdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @Desp:把彩色图片变能灰色及把图片变暗
 * @author xiechengfa2000@163.com
 * @date 2015-6-9 下午9:28:25
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 把彩色图片换成灰色
		// 方法1：
		ImageView image1 = (ImageView) findViewById(R.id.imageView1);
		ColorMatrix matrix = new ColorMatrix();
		matrix.setSaturation(0);
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		image1.setColorFilter(filter);

		// 方法2：
		ImageView image2 = (ImageView) findViewById(R.id.imageView2);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.baby);
		image2.setImageBitmap(convertToBlackWhite(bmp));

		// 图片变暗
		// 方法1
		ImageView image3 = (ImageView) findViewById(R.id.imageView3);
		Drawable drawable = getResources().getDrawable(R.drawable.mm);
		drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		image3.setImageDrawable(drawable);

		// 方法2：没布局文件里的image4，要显示的图片作为background,把变暗的图片或颜色设为src,就可以实现变暗的效果
		// ImageView image4 = (ImageView) findViewById(R.id.imageView4);
	}

	/**
	 * 将彩色图转换为纯黑白二色
	 * 
	 * @param 位图
	 * @return 返回转换好的位图
	 */
	private Bitmap convertToBlackWhite(Bitmap bmp) {
		int width = bmp.getWidth(); // 获取位图的宽
		int height = bmp.getHeight(); // 获取位图的高
		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				// 分离三原色
				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				// 转化成灰度像素
				grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}

		// 新建图片
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
		// 设置图片数据
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
		return resizeBmp;
	}
}
