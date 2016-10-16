package br.com.tick.rotation.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class DisplayUtil {

	static int heightScreenDefault = 1230;
	static int widthScreenDefault = 720;

	private static final int WIDTH_SCREEN_ACE = 320;
	private static final int HEIGHT_SCREEN_ACE = 455;

	private static Integer heightScreen;
	private static Integer widthScreen;

	public static Context context;

	public static void init(final Context ctx) {
		context = ctx;
		getHeightScreenNoStatusBar();
		getWidthScreen();
	}

	public static int getHeightScreenNoStatusBar() {

		final DisplayMetrics displaymetrics = getDisplayMetrics();

		int heightTopBar = getHeightStatusBar();

		heightScreen = displaymetrics.heightPixels - heightTopBar;

		return heightScreen;

	}

	public static int getWidthScreen() {
		final DisplayMetrics displaymetrics = getDisplayMetrics();

		widthScreen = displaymetrics.widthPixels;

		return widthScreen;

	}

	public static DisplayMetrics getDisplayMetrics() {
		final DisplayMetrics displaymetrics = new DisplayMetrics();

		if (context instanceof Activity) {
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		}

		return displaymetrics;
	}

	public static int getHeightStatusBar() {

		int result = 0;

		final int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

		if (resourceId > 0 && ((Build.VERSION.SDK_INT < 11 || Build.VERSION.SDK_INT > 13) && (context.getResources().getDimensionPixelSize(resourceId) != 48))) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}

		return result;

	}

	public static void setLayoutParams(final ViewGroup views) {
		
		DisplayUtil.setWidthHeightByHeight(views);
		DisplayUtil.setMarginsProportional(views);
		DisplayUtil.setPaddingProportional(views);
		
		for (int i = 0; i < views.getChildCount(); i++) {
			
			final View view = views.getChildAt(i);
			
//			DisplayUtil.setWidthHeightByHeight(view);
//			DisplayUtil.setMarginsProportional(view);
//			DisplayUtil.setPaddingProportional(view);
			
			if (view instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) view;
				setLayoutParams(viewGroup);
			} else if (view instanceof TextView) {
				
				DisplayUtil.setWidthHeightByHeight(view);
				DisplayUtil.setMarginsProportional(view);
				DisplayUtil.setPaddingProportional(view);
				
				TextView textView = (TextView) view;
				setTextSize(textView);
//				textView.setTypeface(Utils.getRobotoCondensed(), Typeface.NORMAL);
			}
			else {
				DisplayUtil.setWidthHeightByHeight(view);
				DisplayUtil.setMarginsProportional(view);
				DisplayUtil.setPaddingProportional(view);
			}
		}
	}

	public static void setLayoutParamsByWidth(final ViewGroup views) {
		for (int i = 0; i < views.getChildCount(); i++) {

			final View view = views.getChildAt(i);

			DisplayUtil.setWidthHeightByWidth(view);
			DisplayUtil.setMarginsProportional(view);
			DisplayUtil.setPaddingProportional(view);

			if (view instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) view;
				setLayoutParamsByWidth(viewGroup);
			} else if (view instanceof TextView) {
				TextView textView = (TextView) view;
				setTextSize(textView);
//				textView.setTypeface(Utils.getRobotoCondensed(), Typeface.NORMAL);
			}
		}
	}

	public static int calcByHeight(final double value) {
		return (int) Math.round((heightScreen * value) / heightScreenDefault);
	}

	public static int calcByWidth(final double value) {
		return (int) Math.round((widthScreen * value) / widthScreenDefault);
	}

	public static boolean setWidthHeightByHeight(final View view) {

		if (view != null) {

			final LayoutParams param = view.getLayoutParams();
			if (view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
				ViewPager.LayoutParams params = (ViewPager.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = calcByHeight(params.width);
				}
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByHeight(params.height);
				}
				view.setLayoutParams(params);
				return true;
			} else if (view.getParent() instanceof ViewGroup) {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = calcByHeight(params.width);
				}
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByHeight(params.height);
				}
				view.setLayoutParams(params);
				return true;
			} else {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				
				if(params != null) {
					if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
						params.width = calcByHeight(params.width);
					}
					if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
						params.height = calcByHeight(params.height);
					}
					
					view.setLayoutParams(params);
				}
				return true;
			}
		} else {
			return false;
		}

	}

	public static boolean setWidthHeightByWidth(final View view) {

		if (view != null) {

			final LayoutParams param = view.getLayoutParams();

			if (view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
				ViewPager.LayoutParams params = (ViewPager.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = calcByWidth(params.width);
				}
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByWidth(params.height);
				}
				view.setLayoutParams(params);
				return true;
			} else if (view.getParent() instanceof ViewGroup) {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = calcByWidth(params.width);
				}
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByWidth(params.height);
				}
				view.setLayoutParams(params);
				return true;
			} else {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = calcByWidth(params.width);
				}
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByWidth(params.height);
				}

				view.setLayoutParams(params);
				return true;
			}
		} else {
			return false;
		}

	}

	public static void setWidthByWidth(final View view, final int width) {

		if (view != null) {

			final LayoutParams param = view.getLayoutParams();

			if (view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
				ViewPager.LayoutParams params = (ViewPager.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = width;
				}
				view.setLayoutParams(params);
			} else if (view.getParent() instanceof ViewGroup) {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = width;
				}
			} else {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					params.width = width;
				}

				view.setLayoutParams(params);
			}

		}

	}

	public static boolean setHeightByWidth(final View view, final int height) {

		if (view != null) {

			final LayoutParams param = view.getLayoutParams();
			if (view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
				ViewPager.LayoutParams params = (ViewPager.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByWidth(height);
				}
				view.setLayoutParams(params);
				return true;
			} else if (view.getParent() instanceof ViewGroup) {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByWidth(height);
				}
				return true;
			} else {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByWidth(height);
				}

				view.setLayoutParams(params);
				return true;
			}
		} else {
			return false;
		}

	}

	public static boolean setHeightByHeight(final View view, final int height) {

		if (view != null) {

			final LayoutParams param = view.getLayoutParams();
			if (view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
				ViewPager.LayoutParams params = (ViewPager.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByHeight(height);
				}
				view.setLayoutParams(params);
				return true;
			} else if (view.getParent() instanceof ViewGroup) {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByHeight(height);
				}
				return true;
			} else {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					params.height = calcByHeight(height);
				}

				view.setLayoutParams(params);
				return true;
			}
		} else {
			return false;
		}

	}

	public static boolean setWidth(final View view, final boolean byHeight) {

		if (view != null) {
			final LayoutParams param = view.getLayoutParams();
			if (view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
				ViewPager.LayoutParams params = (ViewPager.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					if (byHeight) {
						params.width = calcByHeight(params.width);
					} else {
						params.width = calcByWidth(params.width);
					}
				}
				view.setLayoutParams(params);
				return true;
			} else if (view.getParent() instanceof ViewGroup) {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					if (byHeight) {
						params.width = calcByHeight(params.width);
					} else {
						params.width = calcByWidth(params.width);
					}
				}
				view.setLayoutParams(params);
				return true;
			} else {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.width != LayoutParams.WRAP_CONTENT && params.width != LayoutParams.MATCH_PARENT) {
					if (byHeight) {
						params.width = calcByHeight(params.width);
					} else {
						params.width = calcByWidth(params.width);
					}
				}
				view.setLayoutParams(params);
				return true;
			}
		} else {
			return false;
		}
	}

	public static boolean setHeight(final View view, final boolean byHeight) {

		if (view != null) {
			final LayoutParams param = view.getLayoutParams();
			if (view.getParent() instanceof ViewPager && param instanceof ViewPager.LayoutParams) {
				ViewPager.LayoutParams params = (ViewPager.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					if (byHeight) {
						params.height = calcByHeight(params.height);
					} else {
						params.height = calcByWidth(params.height);
					}
				}
				view.setLayoutParams(params);
				return true;
			} else if (view.getParent() instanceof ViewGroup) {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					if (byHeight) {
						params.height = calcByHeight(params.height);
					} else {
						params.height = calcByWidth(params.height);
					}
				}
				view.setLayoutParams(params);
				return true;
			} else {
				ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) param;
				if (params.height != LayoutParams.WRAP_CONTENT && params.height != LayoutParams.MATCH_PARENT) {
					if (byHeight) {
						params.height = calcByHeight(params.height);
					} else {
						params.height = calcByWidth(params.height);
					}
				}
				view.setLayoutParams(params);
				return true;
			}
		} else {
			return false;
		}

	}

	public static boolean setMarginsProportional(final View view) {

		if (view != null) {
			final LayoutParams param = view.getLayoutParams();

			if (view.getParent() instanceof ViewGroup && param instanceof ViewGroup.MarginLayoutParams) {
				ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) param;
				if (params.leftMargin != 0) {
					params.leftMargin = calcByWidth(params.leftMargin);
				}
				if (params.topMargin != 0) {
					params.topMargin = calcByHeight(params.topMargin);
				}
				if (params.rightMargin != 0) {
					params.rightMargin = calcByWidth(params.rightMargin);
				}
				if (params.bottomMargin != 0) {
					params.bottomMargin = calcByHeight(params.bottomMargin);
				}
				view.setLayoutParams(params);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean setPaddingProportional(final View view) {

		if (view != null) {
			int left = 0, top = 0, right = 0, bottom = 0;

			if (view.getPaddingLeft() != 0) {
				left = calcByWidth(view.getPaddingLeft());
			}
			if (view.getPaddingTop() != 0) {
				top = calcByHeight(view.getPaddingTop());
			}
			if (view.getPaddingRight() != 0) {
				right = calcByWidth(view.getPaddingRight());
			}
			if (view.getPaddingBottom() != 0) {
				bottom = calcByHeight(view.getPaddingBottom());
			}
			view.setPadding(left, top, right, bottom);

			return true;
		} else {
			return false;
		}

	}

	public static boolean setTextSize(final TextView textView) {

		if (textView != null) {
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, calcByWidth(textView.getTextSize()));
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean setTextSizeByHeight(final TextView textView) {

		if (textView != null) {
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, calcByHeight(textView.getTextSize()));
			return true;
		} else {
			return false;
		}
	}

	public static void setHeightScreenDefault(final int heightScreenDefault) {
		DisplayUtil.heightScreenDefault = heightScreenDefault;
	}

	public static void setWidthScreenDefault(final int widthScreenDefault) {
		DisplayUtil.widthScreenDefault = widthScreenDefault;
	}

	public static boolean isAce() {

		if (Build.VERSION.SDK_INT < 11 && getHeightScreenNoStatusBar() == HEIGHT_SCREEN_ACE && getWidthScreen() == WIDTH_SCREEN_ACE) {

			return true;
		} else {

			return false;
		}

	}
	
	public static void adjustPaintStroke(Paint paint) {
		paint.setStrokeWidth(calcByHeight(paint.getStrokeWidth()));
	}
}
