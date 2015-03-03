/*
 * Copyright (c) 2014 Samsung Electronics Co., Ltd.
 * All rights reserved.
 *
 * This software is a confidential and proprietary information of Samsung
 * Electronics, Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Samsung Electronics.
 */
package br.com.tick.smashrotation.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class NoClickRelativeLayout extends RelativeLayout {

	public NoClickRelativeLayout(final Context context) {
		super(context);
	}

	public NoClickRelativeLayout(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent event) {
		
		super.dispatchTouchEvent(event);
		
		return true;
	}
	
}
